package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.remote.IMessageSender;
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
        maxWidths.put(1, 100);
        maxWidths.put(4, 200);
        maxWidths.put(5, 200);
    }

    public OrdersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ORDERS, maxWidths);
    }

    public OrdersGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths);
    }

    
    @Override
    protected AbstractAction addAction() {
        //TODO: add order action
        return null;
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
