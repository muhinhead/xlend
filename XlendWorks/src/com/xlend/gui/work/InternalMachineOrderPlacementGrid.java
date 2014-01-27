package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachineorder;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class InternalMachineOrderPlacementGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public InternalMachineOrderPlacementGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_MACHINEORDERS, maxWidths, false);
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Machine Order Placement") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    EditMachineOrderPlacementDialog ed = new EditMachineOrderPlacementDialog("New Machine Order Placement",null);
                    if (EditMachineOrderPlacementDialog.okPressed) {
                        Xmachineorder mo = (Xmachineorder) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), mo.getXmachineorderId(), 
                                getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
        /*
   return new AbstractAction("New Company Vehicle") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditCompanyVehicleDialog ed = new EditCompanyVehicleDialog("New Company Vehicle", null);
                    if (EditCompanyVehicleDialog.okPressed) {
                        Xmachine machine = (Xmachine) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), machine.getXmachineId(), getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
         */
//        return null;
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
