package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xaccounts;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class AccountsGrid extends GeneralGridPanel {

    public AccountsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ACCOUNTS, null, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Account") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditAccountDialog ed = new EditAccountDialog("New Account", null);
                    if (EditAccountDialog.okPressed) {
                        Xaccounts xa = (Xaccounts) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xa.getXaccountId(), getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit Account") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xaccounts xa = (Xaccounts) exchanger.loadDbObjectOnID(Xaccounts.class, id);
                        new EditAccountDialog("Edit Account", xa);
                        if (EditAccountDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xaccounts xa = (Xaccounts) exchanger.loadDbObjectOnID(Xaccounts.class, id);
                    if (xa != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this account?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xa);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
