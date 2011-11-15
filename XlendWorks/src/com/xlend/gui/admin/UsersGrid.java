package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.user.EditUserDialog;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class UsersGrid extends GeneralGridPanel {

    public UsersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_USERS, null);
    }

    protected AbstractAction addAction() {
        return new AbstractAction("Add User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditUserDialog("New User", null);
                    if (EditUserDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    protected AbstractAction editAction() {
        return new AbstractAction("Edit User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        Userprofile up = (Userprofile) exchanger.loadDbObjectOnID(Userprofile.class, id);
                        DbObject[] recs = new DbObject[]{pf, up};
                        new EditUserDialog("Edit User", recs);
                        if (EditUserDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    protected AbstractAction delAction() {
        return new AbstractAction("Delete User") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 1) {
                    try {
                        Profile pf = (Profile) exchanger.loadDbObjectOnID(Profile.class, id);
                        if (GeneralFrame.yesNo("Attention!",
                                "Do you want to delete user [" + pf.getFirstName()
                                + " " + pf.getLastName() + "]?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(pf);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                } else {
                    GeneralFrame.errMessageBox("Attention!", "You can't delete admin!");
                }
            }
        };
    }
}
