package com.xlend.gui.creditor;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.PurchaseLookupAction;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xcreditor;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

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
    private JRadioButton yesRb;
    private JRadioButton noRb;
    private JSpinner outstandingAmtSP;
    private JComboBox paidFromCB;

    public EditCreditorPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", "Supplier:",
            "Account Nr/Reference:", "Invoice Nr:",
            "Invoice Ammount:",
            "Paid:", "Outstanding Ammount:",
            "Paid From:"
        };
        String[] paidFromItems = new String[]{
            "TO MACHINE AND TRUCK",
            "TO TRUCK SALES",
            "T&F CONSTRUCTION",
            "XLEND PLANT",
            "EACSA"
        };
        supplierCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        consumeInvNrCbModel = new DefaultComboBoxModel();
        paidFromCbModel = new DefaultComboBoxModel();
        for (int i = 0; i < paidFromItems.length; i++) {
            paidFromCbModel.addElement(new ComboItem(i, paidFromItems[i]));
        }

        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            comboPanelWithLookupBtn(suppliersCB = new JComboBox(supplierCbModel), new SupplierLookupAction(suppliersCB)),
            getGridPanel(accNumField = new JTextField(), 3),
            getGridPanel(comboPanelWithLookupBtn(consumeInvNrCB = new JComboBox(consumeInvNrCbModel),
            new PurchaseLookupAction(consumeInvNrCB)), 3),
            getGridPanel(invoiceAmtSP = new SelectedNumberSpinner(0, 0, 10000000, 1), 3),
            getGridPanel(new JComponent[]{noRb = new JRadioButton("No"), yesRb = new JRadioButton("Yes")}, 8),
            getGridPanel(outstandingAmtSP = new SelectedNumberSpinner(0, 0, 10000000, 1), 3),
            getGridPanel(paidFromCB = new JComboBox(paidFromCbModel), 2)
        };
        ButtonGroup group = new ButtonGroup();
        group.add(noRb);
        group.add(yesRb);

        idField.setEnabled(false);
        suppliersCB.addActionListener(getSupplierCBaction());
        organizePanels(titles, edits, null);
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
            invoiceAmtSP.setValue(xcred.getInvoiceammount() == null ? 0 : xcred.getInvoiceammount());
            if (xcred.getPaid() != null && xcred.getPaid() == 1) {
                yesRb.setSelected(true);
            } else {
                noRb.setSelected(true);
            }
            outstandingAmtSP.setValue(xcred.getOutstandammount() == null ? 0 : xcred.getOutstandammount());
            if (xcred.getPaidfrom() != null) {
                selectComboItem(paidFromCB, xcred.getPaidfrom());
            }
        }
        syncPurchases();
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
        xcred.setAccnum(accNumField.getText());
        xcred.setXsupplierId(getSelectedCbItem(suppliersCB));
        xcred.setXconsumeId(getSelectedCbItem(consumeInvNrCB));
        xcred.setInvoiceammount((Integer)invoiceAmtSP.getValue());
        xcred.setPaid(yesRb.isSelected()?1:0);
        xcred.setOutstandammount((Integer)outstandingAmtSP.getValue());
        return saveDbRecord(xcred, isNew);
    }

    private AbstractAction getSupplierCBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncPurchases();
            }
        };
    }

    private void syncPurchases() {
        ComboItem ci = (ComboItem) suppliersCB.getSelectedItem();
        consumeInvNrCbModel.removeAllElements();
        if (ci != null) {
            for (ComboItem itm : XlendWorks.loadConsumeInvNumbersOnSupplier(DashBoard.getExchanger(), ci.getId())) {
                consumeInvNrCbModel.addElement(itm);
            }
        }
    }
}
