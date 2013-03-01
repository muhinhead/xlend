package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.DieselCartLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xdieselcartissue;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditIssueToDieselPanel extends RecordEditPanel {

    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel supplierCbModel;
    private DefaultComboBoxModel dieselCartCbModel;
    private JComboBox driverCB;
    private JComboBox supplierCB;
    private JComboBox dieselCartCB;
    private JTextField idField;
    private JSpinner issueDateSP;
    private JSpinner litresIssuedSP;
    private JLabel balanceInCartLBL;
    private JLabel balanceSupplierLBL;
    private SupplierLookupAction suppLookup;
    private DieselCartLookupAction diesCartLookup;

    public EditIssueToDieselPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date of Issue:",
            "Driver:",
            "Diesel Cart:",
            "From Supplier:",
            "Ammount in Liters:",
            "Balance in Cart:",
            "Balance available at supplier:"
        };
        driverCbModel = new DefaultComboBoxModel();
        supplierCbModel = new DefaultComboBoxModel();
        dieselCartCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            driverCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllDieselCarts(DashBoard.getExchanger())) {
            dieselCartCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField()}),
            getBorderPanel(new JComponent[]{issueDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{comboPanelWithLookupBtn(
                driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB))
            }),
            getBorderPanel(new JComponent[]{comboPanelWithLookupBtn(dieselCartCB = new JComboBox(dieselCartCbModel),
                diesCartLookup = new DieselCartLookupAction(dieselCartCB))}),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel),
                suppLookup = new SupplierLookupAction(supplierCB))
            }),
            getBorderPanel(new JComponent[]{litresIssuedSP = new SelectedNumberSpinner(.0, .0, .0, .1)}),
            getBorderPanel(new JComponent[]{balanceInCartLBL = new JLabel("0", SwingConstants.RIGHT)}),
            getBorderPanel(new JComponent[]{balanceSupplierLBL = new JLabel("0", SwingConstants.RIGHT)})
        };
        balanceInCartLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        balanceSupplierLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        balanceInCartLBL.setToolTipText("Balance in cart at the moment of issue");
        balanceSupplierLBL.setToolTipText("Balance available at the moment of issue");

        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        idField.setEnabled(false);
        supplierCB.setPreferredSize(driverCB.getPreferredSize());
        idField.setPreferredSize(issueDateSP.getPreferredSize());
        litresIssuedSP.setPreferredSize(issueDateSP.getPreferredSize());
        balanceInCartLBL.setPreferredSize(issueDateSP.getPreferredSize());
        balanceSupplierLBL.setPreferredSize(issueDateSP.getPreferredSize());
        organizePanels(titles, edits, null);
        dieselCartCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalcDieselCartBalance();
            }
        });
        supplierCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalcSupplierBalance();
            }
        });
        dieselCartCB.setSelectedIndex(0);
    }

    private void recalcSupplierBalance() {
        Integer supplierID = getSelectedCbItem(supplierCB);
        if (supplierID != null) {
            java.util.Date dt = (java.util.Date) issueDateSP.getValue();
            Double balance = XlendWorks.calcDieselBalanceAtSupplier(
                           DashBoard.getExchanger(), supplierID, dt);
            SpinnerNumberModel spModel = (SpinnerNumberModel) litresIssuedSP.getModel();
            spModel.setMaximum(balance.doubleValue() + 0.01);
            balanceSupplierLBL.setText(balance.toString());
        }
    }

    private void recalcDieselCartBalance() {
        Integer dieaselCartID = getSelectedCbItem(dieselCartCB);
        if (dieaselCartID != null) {
            java.util.Date dt = (java.util.Date) issueDateSP.getValue();
            balanceInCartLBL.setText(XlendWorks.calcDieselBalanceAtCart(
                    DashBoard.getExchanger(), dieaselCartID, dt));
        }
    }

    @Override
    public void loadData() {
        Xdieselcartissue xi = (Xdieselcartissue) getDbObject();
        if (xi != null) {
            idField.setText(xi.getXdieselcartissueId().toString());
            issueDateSP.setValue(new java.util.Date(xi.getIssueDate().getTime()));
            selectComboItem(driverCB, xi.getDriverId());
            selectComboItem(dieselCartCB, xi.getXdieselcartId());
            selectComboItem(supplierCB, xi.getXsupplierId());
            litresIssuedSP.setValue(xi.getLiters());
            recalcDieselCartBalance();
            recalcSupplierBalance();
        }
        supplierCB.setSelectedIndex(0);
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xdieselcartissue xi = (Xdieselcartissue) getDbObject();
        if (xi == null) {
            xi = new Xdieselcartissue(null);
            xi.setXdieselcartissueId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        if (dt != null) {
            xi.setIssueDate(new java.sql.Date(dt.getTime()));
        }
        xi.setDriverId(getSelectedCbItem(driverCB));
        xi.setLiters((Double) litresIssuedSP.getValue());
        xi.setXdieselcartId(getSelectedCbItem(dieselCartCB));
        xi.setXsupplierId(getSelectedCbItem(supplierCB));
        return saveDbRecord(xi, isNew);
    }
}
