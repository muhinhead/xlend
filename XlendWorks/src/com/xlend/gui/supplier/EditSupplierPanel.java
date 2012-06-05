package com.xlend.gui.supplier;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.ConsumablesGrid;
import com.xlend.gui.site.FuelGrid;
import com.xlend.gui.work.PaymentsGrid;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.EmailFocusAdapter;
import com.xlend.util.MyJideTabbedPane;
import com.xlend.util.SelectedNumberSpinner;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
class EditSupplierPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField companyNameField;
    private JTextField contactPersonField;
    private JTextField phoneField;
    private JTextField faxField;
    private JTextField cellField;
    private JTextField emailField;
    private JTextField vatNrField;
    private JTextField companyRegNrField;
    private JTextArea productDescrField;
    private JTextArea addressField;
    private JTextArea bankingField;
    private JSpinner outStandAmtSP;
    private JLabel outStandAmtLB;

    public EditSupplierPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
//        throw new UnsupportedOperationException("Not supported yet.");
        String titles[] = new String[]{
            "ID:", "Company Name:", "Contact Person:",
            "Tel Nr:", "Fax Nr:", "Cell Nr:", "Email:",
            "Vat Nr:", "Company Reg.Nr:",
            "Product Description:", "Address:", "Banking details:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(new JComponent[]{companyNameField = new JTextField(40), getGridPanel(
                new JComponent[]{new JLabel("   Outstanding Amount:", SwingConstants.CENTER),
                    outStandAmtLB = new JLabel(),
                    outStandAmtSP = new SelectedNumberSpinner(0.0, 0.0, 1000000.0, 1)
//                        ,new JPanel()
                })}),
            getGridPanel(contactPersonField = new JTextField(), 2),
            getGridPanel(phoneField = new JTextField(), 3),
            getGridPanel(faxField = new JTextField(), 3),
            getGridPanel(cellField = new JTextField(), 3),
            getGridPanel(emailField = new JTextField(), 3),
            getGridPanel(vatNrField = new JTextField(), 4),
            getGridPanel(companyRegNrField = new JTextField(), 3),
            productDescrField = new JTextArea(7, 20),
            addressField = new JTextArea(7, 20),
            bankingField = new JTextArea(7, 20)
        };
        idField.setEnabled(false);

        outStandAmtSP.setVisible(false);//setEnabled(false);

        labels = createLabelsArray(titles);
        organizePanels(titles, edits);
        emailField.addFocusListener(new EmailFocusAdapter(labels[6], emailField));
    }

    protected void organizePanels(String[] titles, JComponent[] edits) {
        super.organizePanels(titles.length - 2, edits.length - 2);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length - 2; i++) {
            lblPanel.add(labels[i]);
        }
        for (int i = 0; i < edits.length - 2; i++) {
            editPanel.add(edits[i]);
        }
        JPanel centerPanel = new JPanel(new GridLayout(1, 3));

        JPanel descrPanel = new JPanel(new BorderLayout());
        descrPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), labels[9].getText()));
        descrPanel.add(new JScrollPane(productDescrField,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        productDescrField.setWrapStyleWord(true);
        productDescrField.setLineWrap(true);
        centerPanel.add(descrPanel);

        JPanel addressPanel = new JPanel(new BorderLayout());
        addressPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), labels[10].getText()));
        addressPanel.add(new JScrollPane(addressField,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        addressField.setWrapStyleWord(true);
        addressField.setLineWrap(true);
        centerPanel.add(addressPanel);

        JPanel bankingPanel = new JPanel(new BorderLayout());
        bankingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), labels[11].getText()));
        bankingPanel.add(new JScrollPane(bankingField,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        bankingField.setWrapStyleWord(true);
        bankingField.setLineWrap(true);
        centerPanel.add(bankingPanel);

        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    protected JComponent getRightUpperPanel() {
        JTabbedPane detailPanel = new MyJideTabbedPane();
        JPanel paymentPanel = new JPanel(new BorderLayout());
        JPanel fuelPanel = new JPanel(new BorderLayout());
        JPanel consPanel = new JPanel(new BorderLayout());
        Xsupplier sup = (Xsupplier) getDbObject();
        if (sup != null) {
            try {
                JScrollPane sp1 = new JScrollPane(new PaymentsGrid(DashBoard.getExchanger(),
                        Selects.SELECT_SUPPLIERS_PAYMENTS.replace("#",
                        sup.getXsupplierId().toString())));
                sp1.setPreferredSize(new Dimension(400, 300));
                paymentPanel.add(sp1, BorderLayout.NORTH);

                JScrollPane sp2 = new JScrollPane(
                        new FuelGrid(DashBoard.getExchanger(),
                        Selects.SELECT_SUPPLIERS_FUELS.replace("#",
                        sup.getXsupplierId().toString())));
                sp2.setPreferredSize(new Dimension(400, 300));
                fuelPanel.add(sp2, BorderLayout.NORTH);

                JScrollPane sp3 = new JScrollPane(
                        new ConsumablesGrid(DashBoard.getExchanger(),
                        Selects.SELECT_SUPPLIERS_CONSUMABLES.replace("#",
                        sup.getXsupplierId().toString()), true));
                sp3.setPreferredSize(new Dimension(400, 300));
                consPanel.add(sp3, BorderLayout.NORTH);

            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }
        detailPanel.add("Payemtns", paymentPanel);
        detailPanel.add("Fuel", fuelPanel);
        detailPanel.add("Consumables not paid", consPanel);
        return detailPanel;
    }

    @Override
    public void loadData() {
        Xsupplier sup = (Xsupplier) getDbObject();
        if (sup != null) {
            idField.setText(sup.getXsupplierId().toString());
            companyNameField.setText(sup.getCompanyname());
            contactPersonField.setText(sup.getContactperson());
            phoneField.setText(sup.getPhone());
            faxField.setText(sup.getFax());
            cellField.setText(sup.getCell());
            emailField.setText(sup.getEmail());
            vatNrField.setText(sup.getVatnr());
            companyRegNrField.setText(sup.getCompanyRegnr());
            productDescrField.setText(sup.getProductdesc());
            addressField.setText(sup.getAddress());
            bankingField.setText(sup.getBanking());
            double outAmt = XlendWorks.calcOutstandingAmtSum(DashBoard.getExchanger(), sup.getXsupplierId());
            outStandAmtSP.setValue(outAmt);
            if (outAmt > 0) {
                outStandAmtLB.setFont(outStandAmtLB.getFont().deriveFont(Font.BOLD, 16));
                outStandAmtLB.setForeground(Color.red);
            }
            outStandAmtLB.setText(((JSpinner.DefaultEditor) outStandAmtSP.getEditor()).getTextField().getText());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xsupplier sup = (Xsupplier) getDbObject();
        boolean isNew = false;
        if (sup == null) {
            sup = new Xsupplier(null);
            sup.setXsupplierId(0);
            isNew = true;
        }
        sup.setCompanyname(companyNameField.getText());
        sup.setContactperson(contactPersonField.getText());
        sup.setPhone(phoneField.getText());
        sup.setFax(faxField.getText());
        sup.setCell(cellField.getText());
        sup.setEmail(emailField.getText());
        sup.setVatnr(vatNrField.getText());
        sup.setCompanyRegnr(companyRegNrField.getText());
        sup.setProductdesc(productDescrField.getText());
        sup.setAddress(addressField.getText());
        sup.setBanking(bankingField.getText());
        try {
            sup.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(sup);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            XlendWorks.log(ex);
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
