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
    private static Integer xmachine_id;

    public PurchaseLookupAction(JComboBox cb, Integer xmachineID) {
        super("...");
        this.xmachine_id = xmachineID;
        this.siteCB = cb;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Puchases lookup", siteCB,
                    new ConsumablesGrid(DashBoard.getExchanger(),
                    xmachine_id == null ? Selects.SELECT_FROM_CONSUMABLES
                    : Selects.SELECT_FROM_CONSUMABLES4MACHINE.replace("#", xmachine_id.toString()),
                    false),
                    new String[]{"invoicenumber", "partnumber"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }

    public static void setXmachineID(Integer xmachineID) {
        xmachine_id = xmachineID;
    }
}
