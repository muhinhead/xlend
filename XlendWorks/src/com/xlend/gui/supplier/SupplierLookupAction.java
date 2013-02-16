package com.xlend.gui.supplier;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.work.SuppliersGrid;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class SupplierLookupAction extends AbstractAction {

    private JComboBox supplierCB;

    public SupplierLookupAction(JComboBox cBox) {
        super("...");
        this.supplierCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showSupplierLookup();
    }

    private void showSupplierLookup() {
        try {
            LookupDialog ld = new LookupDialog("Suppliers Lookup", supplierCB,
                    new SuppliersGrid(DashBoard.getExchanger(), Selects.SELECT_SUPPLIERS4LOOKUP, false),
                    new String[]{"companyname", "vatnr", "company_regnr", "contactperson"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
