package com.xlend.gui.site;

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
public class PurchaseLookupAction extends AbstractAction {

    private final JComboBox siteCB;
//    private final Integer xmachine_id;

    public PurchaseLookupAction(JComboBox cb) {
        super("...");
//        this.xmachine_id = xmachine_id;
        this.siteCB = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Puchases lookup", siteCB,
                    new ConsumablesGrid(DashBoard.getExchanger(), 
                            Selects.SELECT_FROM_CONSUMABLES, false),
                    new String[]{"invoicenumber","partnumber"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
