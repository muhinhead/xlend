package com.xlend.gui.work;

import com.xlend.gui.site.EditDieselCardDialog;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselcard;
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
class DieselCardsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);

    }

    public DieselCardsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESELCARD, maxWidths, false);
    }

    public DieselCardsGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, maxWidths, readonly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Diesel Issuing") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditDieselCardDialog ed = new EditDieselCardDialog("Add Issuing", null);
                    if (EditDieselCardDialog.okPressed) {
                        Xdieselcard xdc = (Xdieselcard) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xdc.getXdieselcardId());
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
                        Xdieselcard xdc = (Xdieselcard) exchanger.loadDbObjectOnID(Xdieselcard.class, id);
                        new EditDieselCardDialog("Edit Issuing", xdc);
                        if (EditDieselCardDialog.okPressed) {
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
                    Xdieselcard xdc = (Xdieselcard) exchanger.loadDbObjectOnID(Xdieselcard.class, id);
                    if (xdc != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete diesel issuing card?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xdc);
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
