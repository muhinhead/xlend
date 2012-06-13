package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.mvc.dbtable.DbMasterTableView;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class MachineTypeGrid extends GeneralGridPanel {

    public MachineTypeGrid(IMessageSender exchanger, String select, DbMasterTableView masterView) throws RemoteException {
        super(exchanger, select, null, false, masterView);
    }

    public MachineTypeGrid(IMessageSender exchanger, DbMasterTableView masterView) throws RemoteException {
        this(exchanger, Selects.SELECT_MACHTYPES, masterView);
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
