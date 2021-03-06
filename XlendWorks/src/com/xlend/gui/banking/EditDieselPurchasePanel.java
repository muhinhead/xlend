package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xdieselpurchase;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselPurchasePanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel supplierCbModel;
    private JComboBox supplierCB;
    private SelectedDateSpinner purchaseDateSP;
    private SelectedNumberSpinner litresPurchasedSP;
    private SelectedNumberSpinner randFactorSP;
    private JLabel totalRandLBL;
    private JLabel balanceLitresAvailableLBL;
    private ChangeListener recalcListener;
    private SelectedNumberSpinner paidSP;
    private JLabel balanceRandsAvailableLBL;

    public EditDieselPurchasePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Supplier:",
            "Date of Purchase:",
            "Litres Purchased:",
            "Rand Factor:",
            "Total,R:",
            "Paid,R",
            "Balance Available (litres):",
            "Balance (R):"
        };
        recalcListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                Double litres = (Double) litresPurchasedSP.getValue();
                Double price = (Double) randFactorSP.getValue();
                litres = litres == null ? 0.0 : litres;
                price = price == null ? 0.0 : price;
                totalRandLBL.setText(String.format("%.2f",
                        new Double(litres.doubleValue() * price.doubleValue())));
            }
        };

        supplierCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSuppliers()) {
            supplierCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getBorderPanel(new JComponent[]{comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel),
                new SupplierLookupAction(supplierCB))
            }),
            getGridPanel(purchaseDateSP = new SelectedDateSpinner(), 3),
            getGridPanel(litresPurchasedSP = new SelectedNumberSpinner(.0, .0, 999999.99, .1), 3),
            getGridPanel(randFactorSP = new SelectedNumberSpinner(.0, .0, 999999.99, .1), 3),
            getGridPanel(totalRandLBL = new JLabel("", SwingConstants.RIGHT), 3),
            getGridPanel(paidSP = new SelectedNumberSpinner(.0, .0, 999999.99, .1), 3),
            getGridPanel(balanceLitresAvailableLBL = new JLabel("", SwingConstants.RIGHT), 3),
            getGridPanel(balanceRandsAvailableLBL = new JLabel("", SwingConstants.RIGHT), 3),};
        litresPurchasedSP.addChangeListener(recalcListener);
        randFactorSP.addChangeListener(recalcListener);
        totalRandLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        balanceLitresAvailableLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        balanceRandsAvailableLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        purchaseDateSP.setEditor(new JSpinner.DateEditor(purchaseDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(purchaseDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        setPreferredSize(new Dimension(400, getPreferredSize().height));
        supplierCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalcSupplierBalance();
            }
        });
        supplierCB.setSelectedIndex(0);
    }

    private void recalcSupplierBalance() {
        Integer supplierID = getSelectedCbItem(supplierCB);
        if (supplierID != null) {
            java.util.Date dt = (java.util.Date) purchaseDateSP.getValue();
            Double lBalance = XlendWorks.calcDieselBalanceAtSupplier(supplierID, dt);
            balanceLitresAvailableLBL.setText(lBalance.toString());
            Double rBalance = XlendWorks.calcMoneyForDieselBalanceAtSupplier(supplierID, dt);
            if (rBalance < 0.0) {
                balanceRandsAvailableLBL.setForeground(Color.red);
            } else {
                balanceRandsAvailableLBL.setForeground(Color.blue);
            }
            balanceRandsAvailableLBL.setText(rBalance.toString());
        }
    }

    @Override
    public void loadData() {
        Xdieselpurchase xdp = (Xdieselpurchase) getDbObject();
        if (xdp != null) {
            idField.setText(xdp.getXdieselpurchaseId().toString());
            selectComboItem(supplierCB, xdp.getXsupplierId());
            purchaseDateSP.setValue(new java.util.Date(xdp.getPurchaseDate().getTime()));
            litresPurchasedSP.setValue(xdp.getLitres());
            randFactorSP.setValue(xdp.getRandFactor());
            paidSP.setValue(xdp.getPaid());

        }
        supplierCB.setSelectedIndex(0);
        recalcSupplierBalance();
    }

    @Override
    public boolean save() throws Exception {
        Xdieselpurchase xdp = (Xdieselpurchase) getDbObject();
        boolean isNew = false;
        if (xdp == null) {
            xdp = new Xdieselpurchase(null);
            xdp.setXdieselpurchaseId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) purchaseDateSP.getValue();
        if (dt != null) {
            xdp.setPurchaseDate(new java.sql.Date(dt.getTime()));
        }
        xdp.setRandFactor((Double) randFactorSP.getValue());
        xdp.setLitres((Double) litresPurchasedSP.getValue());
        Integer supplierID = getSelectedCbItem(supplierCB);
        xdp.setXsupplierId(supplierID);
        xdp.setPaid((Double) paidSP.getValue());

        Double rBalance = XlendWorks.calcMoneyForDieselBalanceAtSupplier(
                supplierID, isNew ? null : dt);

        Double litres = (Double) litresPurchasedSP.getValue();
        Double price = (Double) randFactorSP.getValue();

        Xsupplier supp = (Xsupplier) XlendWorks.getExchanger().loadDbObjectOnID(Xsupplier.class, supplierID);
        if ((-rBalance) + (litres * price - (Double) paidSP.getValue()) > supp.getCreditLimit()) {
            GeneralFrame.errMessageBox("Attention!", "Available credit limit R" + supp.getCreditLimit() + " exceeded!");
            return false;
        }

        return saveDbRecord(xdp, isNew);
    }

    @Override
    public void freeResources() {
        //TODO
    }    
}
