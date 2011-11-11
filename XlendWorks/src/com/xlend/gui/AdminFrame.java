package com.xlend.gui;

import com.xlend.gui.site.EditSiteDialog;
import com.xlend.gui.user.EditUserDialog;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class AdminFrame extends WorkFrame {

    public static final String SELECT_FROM_USERS =
            "Select profile_id \"Id\","
            + "first_name \"First Name\",last_name \"Last Name\","
            + "city  \"City\", state  \"Distrikte\", email \"E-mail\" "
            + " from v_userprofile";
    public static final String SELECT_FROM_SITES =
            "Select xsite_id \"Id\", name \"Site Name\", description \"Description\", "
            + "CASEWHEN(dieselsponsor,'Yes','No') \"Diesel Sponsored\", "
            + "CASEWHEN(sitetype='W','Work Site',CASEWHEN(sitetype='A','Additional','unknown')) \"Type of Site\" "
            + "from xsite order by upper(name)";
    private DbTableGridPanel usersPanel;
    private DbTableGridPanel sitesPanel;
    private AdminFrame _this;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
        _this = this;
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane admTab = new JTabbedPane();
        admTab.add(getUsersPanel(), "User List");
        admTab.add(getSitesPanel(), "Sites List");
        return admTab;
    }

    private AbstractAction addUserAction() {
        return new AbstractAction("Add User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditUserDialog("New User", null);
                    if (EditUserDialog.okPressed) {
                        updateGrid(usersPanel.getTableView(),
                                usersPanel.getTableDoc(), SELECT_FROM_USERS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction editUserAction() {
        return new AbstractAction("Edit User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = usersPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        Userprofile up = (Userprofile) exchanger.loadDbObjectOnID(Userprofile.class, id);
                        DbObject[] recs = new DbObject[]{pf, up};
                        new EditUserDialog("Edit User", recs);
                        if (EditUserDialog.okPressed) {
                            updateGrid(usersPanel.getTableView(),
                                    usersPanel.getTableDoc(), SELECT_FROM_USERS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction delUserAction() {
        return new AbstractAction("Delete User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = usersPanel.getSelectedID();
                if (id > 1) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        if (yesNo("Attention!", "Do you want to delete user [" + pf.getFirstName() + " " + pf.getLastName() + "]?")
                                == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(pf);
                            updateGrid(usersPanel.getTableView(),
                                    usersPanel.getTableDoc(), SELECT_FROM_USERS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                } else {
                    errMessageBox("Attention!", "You can't delete admin!");
                }
            }
        };
    }
    
    private AbstractAction addSiteAction() {
        return new AbstractAction("Add Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditSiteDialog(_this, "New Site", null);
                    if (EditSiteDialog.okPressed) {
                        updateGrid(sitesPanel.getTableView(),
                                sitesPanel.getTableDoc(), SELECT_FROM_SITES);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction editSiteAction() {
        return new AbstractAction("Edit Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = sitesPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                        new EditSiteDialog(_this,"Edit Site", xsite);
                        if (EditSiteDialog.okPressed) {
                            updateGrid(sitesPanel.getTableView(),
                                    sitesPanel.getTableDoc(), SELECT_FROM_SITES);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    
    private JPanel getUsersPanel() {
        if (usersPanel == null) {
            usersPanel = createGridPanel(SELECT_FROM_USERS, addUserAction(), 
                    editUserAction(), delUserAction(), null);
        }
        return usersPanel;
    }

    private JPanel getSitesPanel() {
        if (sitesPanel == null) {
            HashMap<Integer, Integer> maxWidths =  new HashMap<Integer, Integer>();
            maxWidths.put(0, 40);
            maxWidths.put(1, 150);
            maxWidths.put(3, 100);
            maxWidths.put(4, 100);
            sitesPanel = createGridPanel(SELECT_FROM_SITES, addSiteAction(), 
                    editSiteAction(), null, maxWidths);
        }
        return sitesPanel;
    }

    private DbTableGridPanel createGridPanel(String select,
            AbstractAction add, AbstractAction edit, AbstractAction del,HashMap<Integer, Integer> maxWidths) {
        DbTableGridPanel targetPanel = null;
        try {
            targetPanel = new DbTableGridPanel(
                    add, edit, del, exchanger.getTableBody(select),maxWidths);
            if (del != null) {
                targetPanel.getDelAction().setEnabled(XlendWorks.getCurrentUser().getManager() == 1);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return targetPanel;
    }
}
