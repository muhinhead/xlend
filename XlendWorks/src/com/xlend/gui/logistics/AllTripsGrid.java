package com.xlend.gui.logistics;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.EditTripDialog;
import com.xlend.orm.Xtrip;
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
public class AllTripsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public AllTripsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_ALL_TRIPS, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Trip") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditTripDialog ed = new EditTripDialog("Add Trip", null);
                    if (EditTripDialog.okPressed) {
                        Xtrip xt = (Xtrip) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xt.getXtripId(),getPageSelector().getSelectedIndex());
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
         return new AbstractAction("Edit Trip") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xtrip xt = (Xtrip) exchanger.loadDbObjectOnID(Xtrip.class, id);
                        new EditTripDialog("Edit Trip", xt);
                        if (EditTripDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id,getPageSelector().getSelectedIndex());
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
         return new AbstractAction("Delete Trip") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xtrip xt = (Xtrip) exchanger.loadDbObjectOnID(Xtrip.class, id);
                    if (xt !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete entry?") == JOptionPane.YES_OPTION) {
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
