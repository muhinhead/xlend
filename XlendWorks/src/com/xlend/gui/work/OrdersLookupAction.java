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
public class OrdersLookupAction extends AbstractAction {
    private JComboBox orderCB;
    
    public OrdersLookupAction(JComboBox cBox) {
        super("...");
        orderCB = cBox;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Order Lookup", orderCB,
                    new OrdersGrid(XlendWorks.getExchanger(), Selects.SELECT_ORDERS4LOOKUP, false),
                    new String[]{"companyname",
                "ordernumber"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
