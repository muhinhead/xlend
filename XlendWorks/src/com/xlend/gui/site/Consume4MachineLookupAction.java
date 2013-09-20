package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class Consume4MachineLookupAction extends AbstractAction {

    protected JComboBox consumeCB;

    public Consume4MachineLookupAction(JComboBox consumeCB) {
        super("...");
        this.consumeCB = consumeCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String select = Selects.SELECT_FROM_CONSUMABLES
                    + (EditBreakConsumeDialog.getXmachineID() == null ? "" : (" and con.xmachine_id="
                    + EditBreakConsumeDialog.getXmachineID()));
            LookupDialog ld = new LookupDialog("Purchases Lookup", consumeCB,
                    new ConsumablesGrid(XlendWorks.getExchanger(), select, false),
                    new String[]{"xconsume_id", "sup.companyname", "con.invoicenumber", "con.partnumber"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
