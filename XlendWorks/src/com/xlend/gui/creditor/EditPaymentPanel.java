package com.xlend.gui.creditor;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xpayment;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditPaymentPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel paidFromCbModel;
    private DefaultComboBoxModel supplierCbModel;
    private DefaultComboBoxModel paidByCbModel;
    private JSpinner payDateSP;
    private JSpinner amountSP;
    private JComboBox suppliersCB;
    private JComboBox paidFromCB;
    private JComboBox paidByCB;
    private final String ADD_NEW_SUPPLIER = "--Add new supplier--";

    public EditPaymentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Supplier:",
            "Date of payment:",
            "Amount:",
            "Paid From:",
            "Paid By:"
        };
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, ADD_NEW_SUPPLIER));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        paidFromCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadPaidFromCodes(DashBoard.getExchanger())) {
            paidFromCbModel.addElement(ci);
        }
        paidByCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            paidByCbModel.addElement(itm);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            comboPanelWithLookupBtn(suppliersCB = new JComboBox(supplierCbModel), new SupplierLookupAction(suppliersCB)),
            getGridPanel(payDateSP = new SelectedDateSpinner(), 4),
            getGridPanel(amountSP = new SelectedNumberSpinner(0.0, 0.0, 1000000.0, .01), 4),
            getGridPanel(paidFromCB = new JComboBox(paidFromCbModel), 2),
            comboPanelWithLookupBtn(paidByCB = new JComboBox(paidByCbModel), new EmployeeLookupAction(paidByCB))
        };

        payDateSP.setEditor(new JSpinner.DateEditor(payDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(payDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xpayment xp = (Xpayment) getDbObject();
        if (xp != null) {
            idField.setText(xp.getXpaymentId().toString());
            if (xp.getXsupplierId() != null) {
                selectComboItem(suppliersCB, xp.getXsupplierId());
            }
            if (xp.getPaydate() != null) {
                java.util.Date dt = new java.util.Date(xp.getPaydate().getTime());
                payDateSP.setValue(dt);
            }
            amountSP.setValue(xp.getAmmount() == null ? 0 : xp.getAmmount());
            if (xp.getPaidfrom() != null) {
                selectComboItem(paidFromCB, xp.getPaidfrom());
            }
            if (xp.getPaydbyId() != null) {
                selectComboItem(paidByCB, xp.getPaydbyId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xpayment xp = (Xpayment) getDbObject();
        if (xp == null) {
            xp = new Xpayment(null);
            xp.setXpaymentId(0);
            isNew = true;
        }
        ComboItem ci = (ComboItem) suppliersCB.getSelectedItem();
        if (ci.getValue().equals(ADD_NEW_SUPPLIER)) { // add new supplier
            EditSupplierDialog esd = new EditSupplierDialog("New Supplier", null);
            if (esd.okPressed) {
                Xsupplier xsupplier = (Xsupplier) esd.getEditPanel().getDbObject();
                if (xsupplier != null) {
                    ci = new ComboItem(xsupplier.getXsupplierId(), xsupplier.getCompanyname());
                    supplierCbModel.addElement(ci);
                    suppliersCB.setSelectedItem(ci);
                }
            }
        } else {
            xp.setAmmount((Double) amountSP.getValue());
            xp.setXsupplierId(getSelectedCbItem(suppliersCB));
            Date dt = (java.util.Date) payDateSP.getValue();
            if (dt != null) {
                xp.setPaydate(new java.sql.Date(dt.getTime()));
            }
            xp.setPaydbyId(getSelectedCbItem(paidByCB));
            xp.setPaidfrom(getSelectedCbItem(paidFromCB));
            return saveDbRecord(xp, isNew);
        }
        return false;
    }
}
