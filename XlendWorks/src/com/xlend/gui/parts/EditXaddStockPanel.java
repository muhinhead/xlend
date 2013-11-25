package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.XaddstocksWithTrigger;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.sql.Connection;
import java.sql.Date;
import javax.swing.*;

/**
 *
 * @author nick
 */
class EditXaddStockPanel extends RecordEditPanel {

    public static int partID;
    private JTextField idField;
    private JSpinner purchaseDateSP;
    private JSpinner quantitySP;
    private JSpinner pricePerUnitSP;
    private JComboBox enteredByCB;
    private JComboBox supplierCB;
    private DefaultComboBoxModel enteredByCbModel;
    private DefaultComboBoxModel supplierCbModel;

    public EditXaddStockPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        //TODO
        String titles[] = new String[]{
            "ID:",
            "Purchase date:",
            "Entered by:",
            "Supplier:",
            "Quantity:",
            "Price per unit:"
        };
        enteredByCbModel = new DefaultComboBoxModel();
        supplierCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(Selects.activeEmployeeCondition)) {
            enteredByCbModel.addElement(ci);
        }
        supplierCbModel.addElement(new ComboItem(0, "--Add new supplier--"));
        for (ComboItem ci : XlendWorks.loadAllSuppliers()) {
            supplierCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(purchaseDateSP = new SelectedDateSpinner(), 5),
            comboPanelWithLookupBtn(enteredByCB = new JComboBox(enteredByCbModel), new EmployeeLookupAction(enteredByCB)),
            comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB)),
            getGridPanel(quantitySP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 0.01), 5),
            getGridPanel(pricePerUnitSP = new SelectedNumberSpinner(0.0, 0.0, 9999999.0, 0.01), 5)
        };
        idField.setEnabled(false);
        purchaseDateSP.setEditor(new JSpinner.DateEditor(purchaseDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(purchaseDateSP);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        XaddstocksWithTrigger ads = (XaddstocksWithTrigger) getDbObject();
        if (ads != null) {
            idField.setText(ads.getXaddstocksId().toString());
            if (ads.getPurchaseDate() != null) {
                Date dt = ads.getPurchaseDate();
                purchaseDateSP.setValue(new java.util.Date(dt.getTime()));
            }
            quantitySP.setValue(ads.getQuantity());
            selectComboItem(supplierCB, ads.getXsupplierId());
            selectComboItem(enteredByCB, ads.getEnteredbyId());
            pricePerUnitSP.setValue(ads.getPriceperunit());
        }
    }

    @Override
    public boolean save() throws Exception {
        XaddstocksWithTrigger ads = (XaddstocksWithTrigger) getDbObject();
        boolean isNew = false;
        if (ads == null) {
            ads = new XaddstocksWithTrigger((Connection) null);
            ads.setXaddstocksId(0);
            isNew = true;
        }
        ComboItem ci = (ComboItem) supplierCB.getSelectedItem();
        if (ci.getValue().startsWith("--")) { // add new supplier
            EditSupplierDialog esd = new EditSupplierDialog("New Supplier", null);
            if (esd.okPressed) {
                Xsupplier xsupplier = (Xsupplier) esd.getEditPanel().getDbObject();
                if (xsupplier != null) {
                    ci = new ComboItem(xsupplier.getXsupplierId(), xsupplier.getCompanyname());
                    supplierCbModel.addElement(ci);
                    supplierCB.setSelectedItem(ci);
                }
            }
        } else {
            Double qty = (Double) quantitySP.getValue();
            if (qty.doubleValue() <= 0.0) {
                GeneralFrame.errMessageBox("Attention!", "Enter positive quantity please");
                ((JSpinner.DefaultEditor) quantitySP.getEditor()).getTextField().requestFocus();
                return false;
            }
            java.util.Date dt = (java.util.Date) purchaseDateSP.getValue();
            ads.setPurchaseDate(new java.sql.Date(dt.getTime()));
            ads.setQuantity(qty);
            ads.setEnteredbyId(getSelectedCbItem(enteredByCB));
            ads.setXsupplierId(getSelectedCbItem(supplierCB));
            ads.setPriceperunit((Double) pricePerUnitSP.getValue());
            ads.setXpartsId(partID);
            return saveDbRecord(ads, isNew);
        }
        return false;
    }

    @Override
    public void freeResources() {
        //TODO
    }
}
