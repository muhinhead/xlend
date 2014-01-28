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
import javax.swing.JOptionPane;

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
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit Machine Order Placement") {

            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachineorder mo = (Xmachineorder) exchanger.loadDbObjectOnID(Xmachineorder.class, id);
                        new EditMachineOrderPlacementDialog("Edit Machine Order Placement", mo);
                        if (EditMachineOrderPlacementDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Delete Machine Order Placement") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xmachineorder mo = (Xmachineorder) exchanger.loadDbObjectOnID(Xmachineorder.class, id);
                    if (mo != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this row?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(mo);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }    
}
