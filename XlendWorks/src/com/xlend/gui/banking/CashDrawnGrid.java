/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcashdrawn;
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
public class CashDrawnGrid extends GeneralGridPanel {
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public CashDrawnGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CASHDRAWN, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Cash Drawn") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    EditCashDrawnDialog ed = new EditCashDrawnDialog("New Cash Drawn", null);
                    if (EditCashDrawnDialog.okPressed) {
                        Xcashdrawn xd = (Xcashdrawn) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xd.getXcashdrawnId(), getPageSelector().getSelectedIndex());
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
         return new AbstractAction("Edit Cash Drawn") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xcashdrawn xd = (Xcashdrawn) exchanger.loadDbObjectOnID(Xcashdrawn.class, id);
                        new EditCashDrawnDialog("Edit Cash Drawn", xd);
                        if (EditCashDrawnDialog.okPressed) {
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
        return new AbstractAction("Delete Petty Cash") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0 && EditPettyPanel.haveAdminRights()) {
                    try {
                        Xcashdrawn xd = (Xcashdrawn) exchanger.loadDbObjectOnID(Xcashdrawn.class, id);
                        if (xd != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xd);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null,
                                    getPageSelector().getSelectedIndex());
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
