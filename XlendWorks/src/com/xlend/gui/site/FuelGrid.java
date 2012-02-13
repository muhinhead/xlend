package com.xlend.gui.site;

import com.xlend.gui.GeneralGridPanel;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xfuel;
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
public class FuelGrid extends GeneralGridPanel {

// private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
//
//    static {
//        maxWidths.put(0, 40);
//    }

    public FuelGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_FUELS, getMaxWidths(new int[]{40}), false);
    }
    
    public FuelGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, getMaxWidths(new int[]{40,70,70,100,100}), true);
    }
    
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Record") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditFuelDialog ed = new EditFuelDialog("Add Record", null);
                    if (EditFuelDialog.okPressed) {
                        Xfuel xb = (Xfuel) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xb.getXfuelId());
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
        return new AbstractAction("Edit Entry") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xfuel xfl  = (Xfuel) exchanger.loadDbObjectOnID(Xfuel.class, id);
                        new EditFuelDialog("Edit Entry", xfl);
                        if (EditFuelDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id);
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
        return new AbstractAction("Delete Entry") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xfuel xfl  = (Xfuel) exchanger.loadDbObjectOnID(Xfuel.class, id);
                    if (xfl !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete entry?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xfl);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }
    
}
