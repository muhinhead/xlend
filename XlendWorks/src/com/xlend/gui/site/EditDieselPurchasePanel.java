package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xdieselpchs;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
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
class EditDieselPurchasePanel extends RecordEditPanel {

    private JTextField idField;
    private JComboBox supplierCB;
    private DefaultComboBoxModel supplierCbModel;
    private JSpinner dateSP;
    private JComboBox authorizedCB;
    private DefaultComboBoxModel authorizedCbModel;
    private JSpinner amtLitersSP;
    private JSpinner amtRandsSP;
    private JComboBox paidByCB;
    private DefaultComboBoxModel paidByCbModel;
    private JComboBox paidMethodCB;
//    private DefaultComboBoxModel paidMethodCbModel;

    public EditDieselPurchasePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Supplier:",
            "Date:",
            "Authorized by:",
            "Amount in Liters:",
            "Amount in Rands:",
            "Paied by (Employee):",
            "Paid by (Method):"
        };
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, "--Add new supplier--"));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        authorizedCbModel = new DefaultComboBoxModel();
        paidByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            authorizedCbModel.addElement(ci);
            paidByCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel),
                new SupplierLookupAction(supplierCB)),
            getGridPanel(dateSP = new SelectedDateSpinner(), 3),
            comboPanelWithLookupBtn(authorizedCB = new JComboBox(authorizedCbModel),
                new EmployeeLookupAction(authorizedCB)),
            getGridPanel(amtLitersSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            getGridPanel(amtRandsSP = new SelectedNumberSpinner(0.0, 0.0, 10000.0, 0.01), 3),
            comboPanelWithLookupBtn(paidByCB = new JComboBox(paidByCbModel), 
                new EmployeeLookupAction(paidByCB)),
            getGridPanel(paidMethodCB = new JComboBox(XlendWorks.loadPayMethods(DashBoard.getExchanger())), 2)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        setPreferredSize(new Dimension(500, getPreferredSize().height));
    }

    @Override
    public void loadData() {
        Xdieselpchs dps = (Xdieselpchs) getDbObject();
        if (dps != null) {
            idField.setText(dps.getXdieselpchsId().toString());
            if (dps.getXsupplierId() != null) {
                selectComboItem(supplierCB, dps.getXsupplierId());
            }
            if (dps.getAuthorizerId() != null) {
                selectComboItem(authorizedCB, dps.getAuthorizerId());
            }
            if (dps.getPaidbyId() != null) {
                selectComboItem(paidByCB, dps.getPaidbyId());
            }
            if (dps.getPurchased() != null) {
                dateSP.setValue(new Date(dps.getPurchased().getTime()));
            }
            amtLitersSP.setValue(dps.getAmountLiters());
            amtRandsSP.setValue(dps.getAmountRands());
            if (dps.getXpaidmethodId() != null) {
                selectComboItem(paidMethodCB, dps.getXpaidmethodId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xdieselpchs dps = (Xdieselpchs) getDbObject();
        boolean isNew = false;
        if (dps == null) {
            dps = new Xdieselpchs(null);
            dps.setXdieselpchsId(0);
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
            dps.setXsupplierId(getSelectedCbItem(supplierCB));
            dps.setAuthorizerId(getSelectedCbItem(authorizedCB));
            dps.setPaidbyId(getSelectedCbItem(paidByCB));
            dps.setXpaidmethodId(getSelectedCbItem(paidMethodCB));
            dps.setAmountLiters((Integer) amtLitersSP.getValue());
            dps.setAmountRands((Double) amtRandsSP.getValue());
            Date dt = (Date) dateSP.getValue();
            if (dt != null) {
                dps.setPurchased(new java.sql.Date(dt.getTime()));
            }
            return saveDbRecord(dps, isNew);
        }
        return false;
    }
}
