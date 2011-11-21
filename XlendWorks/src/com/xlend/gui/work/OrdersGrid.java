package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.order.EditOrderDialog;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class OrdersGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public OrdersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ORDERS, maxWidths);
    }

    public OrdersGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths);
    }

    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Order") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditOrderDialog("New Order", null);
                    if (EditOrderDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, 
                                getTableView(), getTableDoc(), getSelect());
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
        //TODO: edit order action
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        //TODO: del order action
        return null;
    }
    
}
