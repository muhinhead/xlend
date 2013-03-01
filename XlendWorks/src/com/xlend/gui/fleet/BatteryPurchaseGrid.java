package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;

/**
 *
 * @author Nick Mukhin
 */
public class BatteryPurchaseGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public BatteryPurchaseGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BATTPURCHASES, maxWidths, false);
        setBorder(BorderFactory.createTitledBorder("Input"));
    }
    @Override
    protected AbstractAction addAction() {
        return null;
    }

    @Override
    protected AbstractAction editAction() {
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        return null;
    }
}
