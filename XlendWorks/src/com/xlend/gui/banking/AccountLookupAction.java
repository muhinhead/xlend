package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class AccountLookupAction extends AbstractAction {

    private final JComboBox accountCB;

    public AccountLookupAction(JComboBox cb) {
        super("...");
        this.accountCB = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Account Lookup", accountCB,
                    new AccountsGrid(DashBoard.getExchanger()),
                    new String[]{"accname","accnumber","bank","branch"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}