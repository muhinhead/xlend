package com.xlend.gui.creditor;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.PurchaseLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xconsume;
import com.xlend.orm.Xcreditor;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
public class EditCreditorPanel extends RecordEditPanel {

    private DefaultComboBoxModel supplierCbModel;
    private DefaultComboBoxModel consumeInvNrCbModel;
    private DefaultComboBoxModel paidFromCbModel;
    private JTextField idField;
    private JComboBox suppliersCB;
    private JTextField accNumField;
    private JComboBox consumeInvNrCB;
    private JSpinner invoiceAmtSP;
    private JSpinner purchasedDateSP;
    private JRadioButton yesRb;
    private JRadioButton noRb;
    private JSpinner outstandingAmtSP;
    private JComboBox paidFromCB;
    private final String INVOICE_NOT_RECEIVED = "--Invoice not yet received--";
    private final String ADD_NEW_SUPPLIER = "--Add new supplier--";

    public EditCreditorPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", "Supplier:",
            "Account Nr/Reference:",
            "Purchased:",
            "Invoice Nr:",
            "Invoice Ammount:",
            "Paid:", "Outstanding Ammount:",
            "Paid From:"
        };
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, ADD_NEW_SUPPLIER));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        consumeInvNrCbModel = new DefaultComboBoxModel();
        consumeInvNrCbModel.addElement(new ComboItem(0, INVOICE_NOT_RECEIVED));
        paidFromCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadPaidFromCodes(DashBoard.getExchanger())) {
            paidFromCbModel.addElement(ci);
        }

        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            comboPanelWithLookupBtn(suppliersCB = new JComboBox(supplierCbModel), new SupplierLookupAction(suppliersCB)),
            getGridPanel(accNumField = new JTextField(), 4),
            getGridPanel(purchasedDateSP = new SelectedDateSpinner(), 4),
            getGridPanel(comboPanelWithLookupBtn(consumeInvNrCB = new JComboBox(consumeInvNrCbModel),
                new PurchaseLookupAction(consumeInvNrCB, null)), 2),
            getGridPanel(invoiceAmtSP = new SelectedNumberSpinner(0.0, 0.0, 10000000.0, .01), 4),
            getGridPanel(new JComponent[]{noRb = new JRadioButton("No"), yesRb = new JRadioButton("Yes")}, 8),
            getGridPanel(outstandingAmtSP = new SelectedNumberSpinner(0.0, 0.0, 10000000.0, .1), 4),
            getGridPanel(paidFromCB = new JComboBox(paidFromCbModel), 2)
        };

        purchasedDateSP.setEditor(new JSpinner.DateEditor(purchasedDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(purchasedDateSP);

        invoiceAmtSP.getModel().addChangeListener(invoiceAmtChanheAction());
        yesRb.addChangeListener(yesNoSwitchedAction());
        noRb.addChangeListener(yesNoSwitchedAction());

        ButtonGroup group = new ButtonGroup();
        group.add(noRb);
        group.add(yesRb);

        idField.setEnabled(false);
        outstandingAmtSP.setEnabled(false);
        suppliersCB.addActionListener(getSupplierCBaction());
        consumeInvNrCB.addActionListener(getInvoiceCBaction());
        organizePanels(titles, edits, null);
    }

    private ChangeListener yesNoSwitchedAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                calcOutstandingAmount(noRb.isSelected());
            }
        };
    }

    @Override
    public void loadData() {
        Xcreditor xcred = (Xcreditor) getDbObject();
        if (xcred != null) {
            idField.setText(xcred.getXcreditorId().toString());
            if (xcred.getXsupplierId() != null) {
                selectComboItem(suppliersCB, xcred.getXsupplierId());
            }
            accNumField.setText(xcred.getAccnum());
            if (xcred.getXconsumeId() != null) {
                selectComboItem(consumeInvNrCB, xcred.getXconsumeId());
            }
            if (xcred.getPurchased() != null) {
                purchasedDateSP.setValue(new java.util.Date(xcred.getPurchased().getTime()));
            }
            invoiceAmtSP.setValue(xcred.getInvoiceammount() == null ? 0 : xcred.getInvoiceammount());
            if (xcred.getPaid() != null && xcred.getPaid() == 1) {
                yesRb.setSelected(true);
            } else {
                noRb.setSelected(true);
            }
//            outstandingAmtSP.setValue(xcred.getOutstandammount() == null ? 0 : xcred.getOutstandammount());
            if (xcred.getPaidfrom() != null) {
                selectComboItem(paidFromCB, xcred.getPaidfrom());
            }
        }
        syncPurchases();
        if (xcred != null && xcred.getXconsumeId() != null) {
            selectComboItem(consumeInvNrCB, xcred.getXconsumeId());
        }
        calcOutstandingAmount(noRb.isSelected());
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xcreditor xcred = (Xcreditor) getDbObject();
        if (xcred == null) {
            xcred = new Xcreditor(null);
            xcred.setXcreditorId(0);
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
            xcred.setAccnum(accNumField.getText());
            xcred.setXsupplierId(getSelectedCbItem(suppliersCB));
            Date dt = (Date) purchasedDateSP.getValue();
            xcred.setPurchased(new java.sql.Date(dt.getTime()));
            xcred.setXconsumeId(getSelectedCbItem(consumeInvNrCB));
            xcred.setInvoiceammount((Double) invoiceAmtSP.getValue());
            xcred.setPaid(yesRb.isSelected() ? 1 : 0);
//            xcred.setOutstandammount((Double) outstandingAmtSP.getValue());
            xcred.setPaidfrom(getSelectedCbItem(paidFromCB));
            return saveDbRecord(xcred, isNew);
        }
        return false;
    }

    private AbstractAction getSupplierCBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncPurchases();
            }
        };
    }

    private AbstractAction getInvoiceCBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem ci = (ComboItem) consumeInvNrCB.getSelectedItem();
                java.sql.Date dt;
                Double amt;
                if (ci != null) {
                    try {
                        Xconsume xc = (Xconsume) DashBoard.getExchanger().loadDbObjectOnID(Xconsume.class, ci.getId());
                        if (xc != null) {
                            dt = xc.getInvoicedate();
                            amt = xc.getAmountRands();
                            purchasedDateSP.setValue(dt);
                            invoiceAmtSP.setValue(amt != null ? amt : 0.0);
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                    }
                }
                calcOutstandingAmount(noRb.isSelected());
            }
        };
    }

    private void syncPurchases() {
        ComboItem ci = (ComboItem) suppliersCB.getSelectedItem();
        consumeInvNrCbModel.removeAllElements();
        consumeInvNrCbModel.addElement(new ComboItem(0, INVOICE_NOT_RECEIVED));
        if (ci != null) {
            for (ComboItem itm : XlendWorks.loadConsumeInvNumbersOnSupplier(DashBoard.getExchanger(), ci.getId())) {
                consumeInvNrCbModel.addElement(itm);
            }
        }
    }

    private ChangeListener invoiceAmtChanheAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                calcOutstandingAmount(noRb.isSelected());
            }
        };
    }

    private void calcOutstandingAmount(boolean noRBselected) {
        double invAmt = (Double) invoiceAmtSP.getValue();
        double outAmt = 0.0;
        ComboItem ci = (ComboItem) suppliersCB.getSelectedItem();
        if (ci != null) {
//            Xcreditor xcred = (Xcreditor) getDbObject();
            outAmt = XlendWorks.calcOutstandingAmtSum(DashBoard.getExchanger(), ci.getId());//, xcred);
        }
        if (noRBselected) {//noRb.isSelected()) {
            outAmt += invAmt;
        }
        outstandingAmtSP.setValue(new Double(outAmt));
    }
}
