package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xlowbed;
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
public class TripsGrid extends GeneralGridPanel {

    private static Integer xlowbed_id;
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public TripsGrid(IMessageSender exchanger, Xlowbed xlowbed) throws RemoteException {
        super(exchanger,
                Selects.SELECT_TRIPS.replace("#", xlowbed == null ? "0" : (xlowbed_id = xlowbed.getXlowbedId()).toString()),
                maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Trip") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditTripDialog.xlowbed_id = xlowbed_id;
                    EditTripDialog ed = new EditTripDialog("New Trip", null);
                    if (EditTripDialog.okPressed) {
                        Xtrip xtrip = (Xtrip) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xtrip.getXtripId());
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
                        EditTripDialog.xlowbed_id = xlowbed_id;
                        Xtrip xtr = (Xtrip) exchanger.loadDbObjectOnID(Xtrip.class, id);
                        new EditTripDialog("Edit Trip", xtr);
                        if (EditTripDialog.okPressed) {
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
 return new AbstractAction("Delete Trip") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xtrip xtr = (Xtrip) exchanger.loadDbObjectOnID(Xtrip.class, id);
                    if (xtr != null && GeneralFrame.yesNo("Attention!", "Do you want to delete trip?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xtr);
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
