package com.xlend.gui.hr;

import com.xlend.gui.admin.XlendMasterTableView;
import com.xlend.gui.site.PPEissuesGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;

/**
 *
 * @author Nick Mukhin
 */
public class PPEissues2employeeGrid extends PPEissuesGrid {

    public PPEissues2employeeGrid(IMessageSender exchanger, int xemployee_id,XlendMasterTableView masterView) throws RemoteException {
        super(exchanger, xemployee_id, masterView);
//        setBorder(BorderFactory.createTitledBorder("Output"));
    }
}
