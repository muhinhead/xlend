package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.orm.Xsupplier;
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
public class SuppliersGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
//        maxWidths.put(1, 300);
//        maxWidths.put(2, 500);
//        maxWidths.put(3, 200);
//        maxWidths.put(4, 200);
    }

    public SuppliersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_SUPPLIERS, maxWidths, false);
    }

    public SuppliersGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, maxWidths, readonly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Supplier") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditSupplierDialog ed = new EditSupplierDialog("New Supplier", null);
                    if (EditSupplierDialog.okPressed) {
                        Xsupplier xsup = (Xsupplier) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xsup.getXsupplierId(), getPageSelector().getSelectedIndex());
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
                        Xsupplier xsup = (Xsupplier) exchanger.loadDbObjectOnID(Xsupplier.class, id);
                        new EditSupplierDialog("Edit Supplier", xsup);
                        if (EditSupplierDialog.okPressed) {
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
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xsupplier xsup = (Xsupplier) exchanger.loadDbObjectOnID(Xsupplier.class, id);
                    if (xsup != null && GeneralFrame.yesNo("Attention!", "Do you want to delete supplier ["
                            + xsup.getCompanyname() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xsup);
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
