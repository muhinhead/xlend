package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class BookOutsGrid extends GeneralGridPanel {

    public BookOutsGrid(IMessageSender exchanger, int part_id) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BOOKOUTS.replaceAll("#", "" + part_id), null, false);
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
