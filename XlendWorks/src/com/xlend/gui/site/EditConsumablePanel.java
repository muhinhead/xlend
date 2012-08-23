package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xconsume;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class EditConsumablePanel extends RecordEditPanel {

    public static Xconsume sampleRecord;
    private JTextField idField;
    private DefaultComboBoxModel supplierCbModel;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel authorizedCbModel;
    private DefaultComboBoxModel requestedByCbModel;
    private DefaultComboBoxModel collectedByCbModel;
    private DefaultComboBoxModel paidByCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox supplierCB;
    private JComboBox requesterCB;
    private JSpinner invoiceDateSP;
    private JTextField invoiceNumField;
    private JComboBox authorizerCB;
    private JComboBox collectorCB;
    private JTextField descrField;
    private JTextField partNumberField;
    private JSpinner amtLitersSP;
    private JSpinner amtRandsSP;
    private JComboBox paidByCB;
    private JComboBox paidMethodCB;
    private JComboBox machineCB;
    private JComboBox siteCB;
    private JTextField cheqNumberField;
    private JTextField accNumField;

    public EditConsumablePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Supplier:",
            "Machine/Truck/Other:",
            "Site:",
            "Requested by:",
            "Invoice Date:",
            "Invoice Nr:",
            "Authorized by:",
            "Collected by:",
            "Description:",
            "Account Nr/Reference:",
            "Part Nr:",
            "Amount in Liters:",
            "Amount in Rands:",
            "Paid by (Employee):",
            "Paid by (Method):",
            "Cheque Nr:"
        };
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, "--Add new supplier--"));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        authorizedCbModel = new DefaultComboBoxModel();
        requestedByCbModel = new DefaultComboBoxModel();
        collectedByCbModel = new DefaultComboBoxModel();
        paidByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            authorizedCbModel.addElement(ci);
            requestedByCbModel.addElement(ci);
            collectedByCbModel.addElement(ci);
            paidByCbModel.addElement(ci);
        }
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB)),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            comboPanelWithLookupBtn(requesterCB = new JComboBox(requestedByCbModel), new EmployeeLookupAction(requesterCB)),
            getGridPanel(invoiceDateSP = new SelectedDateSpinner(), 3),
            getGridPanel(invoiceNumField = new JTextField(), 3),
            comboPanelWithLookupBtn(authorizerCB = new JComboBox(authorizedCbModel), new EmployeeLookupAction(authorizerCB)),
            comboPanelWithLookupBtn(collectorCB = new JComboBox(collectedByCbModel), new EmployeeLookupAction(collectorCB)),
            descrField = new JTextField(),
            getGridPanel(accNumField = new JTextField(), 3),
            getGridPanel(partNumberField = new JTextField(), 3),
            getGridPanel(amtLitersSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            getGridPanel(amtRandsSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, .01), 3),
            comboPanelWithLookupBtn(paidByCB = new JComboBox(paidByCbModel), new EmployeeLookupAction(paidByCB)),
            getGridPanel(paidMethodCB = new JComboBox(XlendWorks.loadPayMethods(DashBoard.getExchanger())), 2),
            getGridPanel(cheqNumberField = new JTextField(), 3)
        };
        paidMethodCB.addActionListener(getPaidMethodAction());
        invoiceDateSP.setEditor(new JSpinner.DateEditor(invoiceDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(invoiceDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        setPreferredSize(new Dimension(500, getPreferredSize().height));
    }

    @Override
    public void loadData() {
        Xconsume xcns = (Xconsume) getDbObject();
        if (xcns != null) {
            idField.setText(xcns.getXconsumeId().toString());
        } else if (sampleRecord != null) {
            xcns = sampleRecord;
            for (JComponent comp : new JComponent[]{supplierCB, requesterCB,
                invoiceDateSP,invoiceNumField,authorizerCB,collectorCB,
                accNumField,paidByCB,paidMethodCB,cheqNumberField}) {
                comp.setEnabled(false);
            }
        }
        if (xcns != null) {
            selectComboItem(supplierCB, xcns.getXsupplierId());
            selectComboItem(machineCB, xcns.getXmachineId());
            selectComboItem(requesterCB, xcns.getRequesterId());
            if (xcns.getInvoicedate() != null) {
                invoiceDateSP.setValue(new Date(xcns.getInvoicedate().getTime()));
            }
            invoiceNumField.setText(xcns.getInvoicenumber());
            selectComboItem(authorizerCB, xcns.getAuthorizerId());
            selectComboItem(collectorCB, xcns.getCollectorId());
            descrField.setText(xcns.getDescription());
            partNumberField.setText(xcns.getPartnumber());
            if (xcns.getAmountLiters() != null) {
                amtLitersSP.setValue(xcns.getAmountLiters());
            }
            if (xcns.getAmountRands() != null) {
                amtRandsSP.setValue(xcns.getAmountRands());
            }
            selectComboItem(paidByCB, xcns.getPayerId());
            selectComboItem(paidMethodCB, xcns.getXpaidmethodId());
            RecordEditPanel.addSiteItem(siteCbModel, xcns.getXsiteId());
            selectComboItem(siteCB, xcns.getXsiteId());
            accNumField.setText(xcns.getAccnum());
            cheqNumberField.setText(xcns.getChequenumber());
        }
        showChequeField();
    }

    @Override
    public boolean save() throws Exception {
        Xconsume xcns = (Xconsume) getDbObject();
        boolean isNew = false;
        if (xcns == null) {
            xcns = new Xconsume(null);
            xcns.setXconsumeId(0);
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
            xcns.setXsupplierId(getSelectedCbItem(supplierCB));
            xcns.setXmachineId(getSelectedCbItem(machineCB));
            xcns.setRequesterId(getSelectedCbItem(requesterCB));
            Date dt = (Date) invoiceDateSP.getValue();
            if (dt != null) {
                xcns.setInvoicedate(new java.sql.Date(dt.getTime()));
            }
            xcns.setInvoicenumber(invoiceNumField.getText());
            xcns.setAuthorizerId(getSelectedCbItem(authorizerCB));
            xcns.setCollectorId(getSelectedCbItem(collectorCB));
            xcns.setDescription(descrField.getText());
            xcns.setPartnumber(partNumberField.getText());
            xcns.setAmountLiters((Integer) amtLitersSP.getValue());
            xcns.setAmountRands((Double) amtRandsSP.getValue());
            xcns.setPayerId(getSelectedCbItem(paidByCB));
            xcns.setXpaidmethodId(getSelectedCbItem(paidMethodCB));
            xcns.setChequenumber(cheqNumberField.isVisible() ? cheqNumberField.getText() : null);
            xcns.setAccnum(accNumField.getText());
            xcns.setXsiteId(getSelectedCbItem(siteCB));
            return saveDbRecord(xcns, isNew);
        }
        return false;
    }

    private ActionListener getPaidMethodAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showChequeField();
            }
        };
    }

    private void showChequeField() {
        ComboItem ci = (ComboItem) paidMethodCB.getSelectedItem();
        boolean isCheque = (ci != null && ci.getValue().startsWith("Cheque"));
        cheqNumberField.setVisible(isCheque);
        labels[labels.length - 1].setVisible(isCheque);
    }
}
