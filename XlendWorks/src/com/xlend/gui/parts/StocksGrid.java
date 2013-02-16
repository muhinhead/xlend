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
public class StocksGrid extends GeneralGridPanel {

    public StocksGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_ALL_STOCKS, null, false);
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
