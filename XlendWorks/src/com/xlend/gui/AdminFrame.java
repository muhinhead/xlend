package com.xlend.gui;

import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
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

    private DbTableGridPanel usersPanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane admTab = new JTabbedPane();
        admTab.add(getUsersPanel(), "User List");
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
                if (id > 0) {
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
                }
            }
        };
    }

    private JPanel getUsersPanel() {
        if (null == usersPanel) {
            try {
                usersPanel = new DbTableGridPanel(
                        addUserAction(), editUserAction(), delUserAction(),
                        exchanger.getTableBody(SELECT_FROM_USERS));
                //TODO: more complex logic based on current user's responsibility
                usersPanel.getDelAction().setEnabled(XlendWorks.getCurrentUser().getManager() == 1);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }
        return usersPanel;
    }
}
