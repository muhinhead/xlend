package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.site.EditSiteDialog;
import com.xlend.gui.user.EditUserDialog;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
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

    private DbTableGridPanel usersPanel;
    private DbTableGridPanel sitesPanel;
    private DbTableGridPanel clientsPanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane admTab = new JTabbedPane();
        admTab.add(getUsersPanel(), "Users");
        admTab.add(getSitesPanel(), "Sites");
        admTab.add(getClientsPanel(), "Clients");
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
                                usersPanel.getTableDoc(), Selects.SELECT_FROM_USERS);
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
                                    usersPanel.getTableDoc(), Selects.SELECT_FROM_USERS);
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
                                    usersPanel.getTableDoc(), Selects.SELECT_FROM_USERS);
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

    private AbstractAction addClientAction() {
        return new AbstractAction("Add client") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditClientDialog("New Client", null);
                    if (EditClientDialog.okPressed) {
                        updateGrid(clientsPanel.getTableView(),
                                clientsPanel.getTableDoc(), Selects.SELECT_FROM_CLIENTS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction editClientAction() {
        return new AbstractAction("Edit Client") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = clientsPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Xclient xclient = (Xclient) exchanger.loadDbObjectOnID(Xclient.class, id);
                        new EditClientDialog("Edit Client", xclient);
                        if (EditClientDialog.okPressed) {
                            updateGrid(clientsPanel.getTableView(),
                                    clientsPanel.getTableDoc(), Selects.SELECT_FROM_CLIENTS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction delClientAction() {
        return new AbstractAction("Delete Client") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = clientsPanel.getSelectedID();
                try {
                    Xclient xclient = (Xclient) exchanger.loadDbObjectOnID(Xclient.class, id);
                    if (yesNo("Attention!", "Do you want to delete client [" + xclient.getCompanyname() + "]?")
                            == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xclient);
                        updateGrid(clientsPanel.getTableView(),
                                clientsPanel.getTableDoc(), Selects.SELECT_FROM_CLIENTS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

    private AbstractAction addSiteAction() {
        return new AbstractAction("Add Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditSiteDialog("New Site", null);
                    if (EditSiteDialog.okPressed) {
                        updateGrid(sitesPanel.getTableView(),
                                sitesPanel.getTableDoc(), Selects.SELECT_FROM_SITES);
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
                        new EditSiteDialog("Edit Site", xsite);
                        if (EditSiteDialog.okPressed) {
                            updateGrid(sitesPanel.getTableView(),
                                    sitesPanel.getTableDoc(), Selects.SELECT_FROM_SITES);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction delSiteAction() {
        return new AbstractAction("Delete Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = sitesPanel.getSelectedID();
                try {
                    Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                    if (yesNo("Attention!", "Do you want to delete site [" + xsite.getName() + "]?")
                            == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xsite);
                        updateGrid(sitesPanel.getTableView(),
                                sitesPanel.getTableDoc(), Selects.SELECT_FROM_SITES);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

    private JPanel getUsersPanel() {
        if (usersPanel == null) {
            usersPanel = createGridPanel(exchanger, Selects.SELECT_FROM_USERS, addUserAction(),
                    editUserAction(), delUserAction(), null);
        }
        return usersPanel;
    }

    private JPanel getSitesPanel() {
        if (sitesPanel == null) {
            HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
            maxWidths.put(0, 40);
            maxWidths.put(1, 300);
            maxWidths.put(2, 500);
            maxWidths.put(3, 200);
            maxWidths.put(4, 200);
            sitesPanel = createGridPanel(exchanger,Selects.SELECT_FROM_SITES, addSiteAction(),
                    editSiteAction(), delSiteAction(), maxWidths);
        }
        return sitesPanel;
    }

    private JPanel getClientsPanel() {
        if (clientsPanel == null) {
            HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
            maxWidths.put(0, 40);
            maxWidths.put(1, 100);
            maxWidths.put(4, 200);
            maxWidths.put(5, 200);
            clientsPanel = createGridPanel(exchanger,Selects.SELECT_FROM_CLIENTS, addClientAction(),
                    editClientAction(), delClientAction(), maxWidths);
        }
        return clientsPanel;
    }

}
