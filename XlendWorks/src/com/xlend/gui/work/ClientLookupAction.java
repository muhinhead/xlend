package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class ClientLookupAction extends AbstractAction {

    private JComboBox clientCB;

    public ClientLookupAction(JComboBox cBox) {
        super("...");
        clientCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            LookupDialog ld = new LookupDialog("Client Lookup", clientCB,
                    new ClientsGrid(XlendWorks.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP, false),
                    new String[]{"clientcode", "companyname", "contactname"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
