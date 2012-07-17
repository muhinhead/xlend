package com.xlend.gui.logistics;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xtripsheet;
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
public class TripSheetGrid extends GeneralGridPanel {
    
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
//        maxWidths.put(1, 300);
//        maxWidths.put(2, 500);
//        maxWidths.put(3, 200);
//        maxWidths.put(4, 200);
    }
    
    public TripSheetGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_TRIPSHEET, maxWidths, false);        
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Tripsheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditTripSheetDialog ed = new EditTripSheetDialog("New Tripsheet", null);
                    if (EditTripSheetDialog.okPressed) {
                        Xtripsheet xt = (Xtripsheet) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xt.getXtripsheetId(),getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Tripsheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xtripsheet xt = (Xtripsheet) exchanger.loadDbObjectOnID(Xtripsheet.class, id);
                        new EditTripSheetDialog("Edit Tripsheet", xt);
                        if (EditTripSheetDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id,getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Delete Tripsheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xtripsheet xt = (Xtripsheet) exchanger.loadDbObjectOnID(Xtripsheet.class, id);
                    if (xt !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete this tripsheet]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xt);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null,getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }
    
}
