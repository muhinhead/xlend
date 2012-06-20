package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachrentalrate;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class MachineRentalRatesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 300);
    }

    public MachineRentalRatesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_MACHINERANTALRATE, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Rental Rates List") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditMachineRentalRatesDialog ed = new EditMachineRentalRatesDialog("Add Rental Rates List", null);
                    if (EditMachineRentalRatesDialog.okPressed) {
                        Xmachrentalrate xmr = (Xmachrentalrate) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xmr.getXmachrentalrateId());
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
        return new AbstractAction("Edit Rental Rates List") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachrentalrate xmr = (Xmachrentalrate) exchanger.loadDbObjectOnID(Xmachrentalrate.class, id);
                        new EditMachineRentalRatesDialog("Edit Rental Rates List", xmr);
                        if (EditMachineRentalRatesDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id);
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Rental Rates List") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachrentalrate xmr = (Xmachrentalrate) exchanger.loadDbObjectOnID(Xmachrentalrate.class, id);
                        if (xmr != null) {
                            if (GeneralFrame.yesNo("Attention!", 
                                    "Do you want to delete this rates list?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(xmr);
                                GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                            }
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }
    
}
