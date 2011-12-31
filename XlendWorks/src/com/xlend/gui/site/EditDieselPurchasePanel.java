package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.work.SuppliersGrid;
import com.xlend.orm.Xdieselpchs;
import com.xlend.orm.Xsite;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Date;
import javax.swing.AbstractAction;
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
    private DefaultComboBoxModel paidMethodCbModel;
    private AbstractAction supplierLookupAction;
    private AbstractAction authorizedLookupAction;
    private AbstractAction paidByLookupAction;

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
            supplierLookupAction = supplierLookup()),
            getGridPanel(dateSP = new SelectedDateSpinner(), 3),
            comboPanelWithLookupBtn(authorizedCB = new JComboBox(authorizedCbModel),
            authorizedLookupAction = authorizedLookup()),
            getGridPanel(amtLitersSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            getGridPanel(amtRandsSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            comboPanelWithLookupBtn(paidByCB = new JComboBox(paidByCbModel), paidByLookupAction = paidByLookup()),
            getGridPanel(paidMethodCB = new JComboBox(XlendWorks.loadPayMethods(DashBoard.getExchanger())), 2)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits);
        setPreferredSize(new Dimension(500, getPreferredSize().height));
    }

    protected void organizePanels(String[] titles, JComponent[] edits) {
        super.organizePanels(titles.length, edits.length);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length; i++) {
            lblPanel.add(labels[i]);
        }
        for (int i = 0; i < edits.length; i++) {
            editPanel.add(edits[i]);
        }
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
            dps.setXsupplierId(ci == null ? null : ci.getId());
            ci = (ComboItem) authorizedCB.getSelectedItem();
            dps.setAuthorizerId(ci == null ? null : ci.getId());
            ci = (ComboItem) paidByCB.getSelectedItem();
            dps.setPaidbyId(ci == null ? null : ci.getId());
            ci = (ComboItem) paidMethodCB.getSelectedItem();
            dps.setXpaidmethodId(ci == null ? null : ci.getId());
            dps.setAmountLiters((Integer) amtLitersSP.getValue());
            dps.setAmountLiters((Integer) amtLitersSP.getValue());
            dps.setAmountRands((Integer) amtRandsSP.getValue());
            Date dt = (Date) dateSP.getValue();
            if (dt != null) {
                dps.setPurchased(new java.sql.Date(dt.getTime()));
            }
            try {
                dps.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(dps);
                setDbObject(saved);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    private AbstractAction supplierLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showSupplierLookup();
            }
        };
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

    private AbstractAction authorizedLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showAuthorizedLookup();
            }
        };
    }

    private void showAuthorizedLookup() {
        try {
            LookupDialog ld = new LookupDialog("Employee Lookup", authorizedCB,
                    new EmployeesGrid(DashBoard.getExchanger(), Selects.EMPLOYEES, true),
                    new String[]{"sur_name", "clock_num"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }

    private AbstractAction paidByLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showPaidByLookup();
            }
        };
    }

    private void showPaidByLookup() {
        try {
            LookupDialog ld = new LookupDialog("Employee Lookup", authorizedCB,
                    new EmployeesGrid(DashBoard.getExchanger(), Selects.EMPLOYEES, false),
                    new String[]{"sur_name", "clock_num"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
