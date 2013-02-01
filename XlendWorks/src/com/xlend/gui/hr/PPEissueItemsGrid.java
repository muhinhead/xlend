package com.xlend.gui.hr;

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
public class PPEissueItemsGrid extends GeneralGridPanel {

    protected static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(2, 250);
    }
    
    public PPEissueItemsGrid(IMessageSender exchanger, int xppeissue_id) throws RemoteException {
        super(exchanger,Selects.SELECT_FROM_PPEISSUEITEMS.replace("=0","="+xppeissue_id), maxWidths, true);
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
