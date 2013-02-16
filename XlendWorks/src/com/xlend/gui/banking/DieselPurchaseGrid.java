package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselpurchase;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class DieselPurchaseGrid extends GeneralGridPanel {

    public DieselPurchaseGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESELPURCHASES, null, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Diesel Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditDieselPurchaseDialog ed = new EditDieselPurchaseDialog("Add Diesel Purchase", null);
                    if (EditDieselPurchaseDialog.okPressed) {
                        Xdieselpurchase xdp = (Xdieselpurchase) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xdp.getXdieselpurchaseId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Diesel Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xdieselpurchase xdp = (Xdieselpurchase) exchanger.loadDbObjectOnID(Xdieselpurchase.class, id);
                        new EditDieselPurchaseDialog("Edit Diesel Purchase", xdp);
                        if (EditDieselPurchaseDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Delete Diesel Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xdieselpurchase xdp = (Xdieselpurchase) exchanger.loadDbObjectOnID(Xdieselpurchase.class, id);
                    if (xdp != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xdp);
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
