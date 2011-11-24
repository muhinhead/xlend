package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.orm.Xorder;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
class OrderItemsGrid extends GeneralGridPanel {
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xorder xorder = null;
    private static final String whereId = "xorder_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public OrderItemsGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECTORDERITEMS.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECTORDERITEMS.substring(0,p))) {
            xorder = (Xorder) exchanger.loadDbObjectOnID(
                    Xorder.class, Integer.parseInt(getSelect().substring(p + whereId.length()-1)));
        }
        
    }

    @Override
    protected AbstractAction addAction() {
        //TODO: add orderItem
        return null;
    }

    @Override
    protected AbstractAction editAction() {
        //TODO: edit orderItem
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        //TODO: delete orderItem
        return null;
    }

    /**
     * @return the xorder
     */
    public Xorder getXorder() {
        return xorder;
    }
    
}
