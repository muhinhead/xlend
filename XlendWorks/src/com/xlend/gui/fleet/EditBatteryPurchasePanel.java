package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xbateryissue;
import com.xlend.orm.Xbattery;
import com.xlend.orm.Xbatterypurchase;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditBatteryPurchasePanel extends RecordEditPanel {

    public static final double TAX_COEF = 1.14;
    private DefaultComboBoxModel purchasedByCbModel;
    private DefaultComboBoxModel supplierCbModel;
    private static final String BATT_CODE = "Battery Code:";
    private static final String VAT_EXCL = "Vat Excl. Unit (R)";
    private static final String BATT_ID = "Battery ID #";
    private JTextField idField;
    private SelectedDateSpinner purchaseDateSP;
    private SelectedDateSpinner entryDateSP;
    private JComboBox purchasedByCB;
    private JComboBox supplierCB;
    private ArrayList<EditBatteryPurchasePanel.ItemPanel> childRows;
    private ArrayList<EditBatteryPurchasePanel.ItemPanel> toDelete;
    private JPanel downGridPanel;
    private JComponent hdrPanel;
    private JCheckBox markAllCB;
    private JLabel vatInclTotalLBL;
    private JLabel vatExclTotalLBL;
    private Double invInclTotal;
    private Double invExclTotal;

    private class ItemPanel extends JPanel {

        private Xbattery item;
        private JCheckBox markCB;
        private JComboBox batteryCodeCB;
        private JSpinner vatExclUnitSP;
        private JTextField batteryIDfield;
        private final JButton issuedBtn;

        ItemPanel(Xbattery item) {
            super(new BorderLayout());
            this.item = item;

            add(markCB = new JCheckBox(), BorderLayout.WEST);
            add(getGridPanel(new JComponent[]{
                        new JLabel(BATT_CODE, SwingConstants.RIGHT),
                        batteryCodeCB = new JComboBox(XlendWorks.loadDistinctBatteryCodes()),
                        new JLabel(VAT_EXCL, SwingConstants.RIGHT),
                        vatExclUnitSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, .1),
                        new JLabel(BATT_ID, SwingConstants.RIGHT),
                        batteryIDfield = new JTextField(),
                        getBorderPanel(new JComponent[]{issuedBtn = new JButton(new AbstractAction("Issued") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Integer issueID = getItem().getXbateryissueId();
                                try {
                                    Xbateryissue issue = (Xbateryissue) XlendWorks.getExchanger().loadDbObjectOnID(Xbateryissue.class, issueID);
                                    new EditBatteryIssueDialog("Edit Battery Issue", issue);
                                } catch (RemoteException ex) {
                                    Logger.getLogger(EditBatteryPurchasePanel.class.getName()).log(Level.SEVERE, null, ex);
                                }
                        }
                    })})
                    }));
            batteryCodeCB.setEditable(true);
            load();
            vatExclUnitSP.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    recalcTotals();
                }
            });
        }

        private void load() {
            if (getItem() != null) {
                batteryCodeCB.setSelectedItem(getItem().getBatteryCode());
                getVatExclUnitSP().setValue(getItem().getVatExclUnit());
                batteryIDfield.setText(getItem().getBatteryId());
            }
            boolean isIssued = getItem() != null && getItem().getXbateryissueId() != null && getItem().getXbateryissueId().intValue() != 0;
            issuedBtn.setEnabled(isIssued);
            markCB.setEnabled(!isIssued);
            if (!isIssued) {
                issuedBtn.setText("not issued");
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (batteryIDfield.getText().trim().length() > 0 && (Double) getVatExclUnitSP().getValue() > 0.0) {
                if (getItem() == null) {
                    item = new Xbattery(null);
                    getItem().setXbatteryId(0);
                    getItem().setXbateryissueId(0);
                    Xbatterypurchase xbp = (Xbatterypurchase) getDbObject();
                    getItem().setXbatterypurchaseId(xbp.getXbatterypurchaseId());
                    isNew = true;
                }
                getItem().setXbateryissueId(getItem().getXbateryissueId());
                JTextField ed = (JTextField) batteryCodeCB.getEditor().getEditorComponent();
                getItem().setBatteryCode(ed.getText());
                getItem().setVatExclUnit((Double) getVatExclUnitSP().getValue());
                getItem().setBatteryId(batteryIDfield.getText());
                return saveDbRecord(getItem(), isNew);
            }
            return true;
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xbattery) XlendWorks.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        /**
         * @return the item
         */
        protected Xbattery getItem() {
            return item;
        }

        /**
         * @return the vatExclUnitSP
         */
        protected JSpinner getVatExclUnitSP() {
            return vatExclUnitSP;
        }
    };

    public EditBatteryPurchasePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        String titles[] = new String[]{
            "ID:",
            "Date of Purchase:", // "Date of Entry:"
            "Purchased by:",
            "Supplier:", ""
        };
        purchasedByCbModel = new DefaultComboBoxModel();
        supplierCbModel = new DefaultComboBoxModel();
        purchasedByCbModel.addElement(new ComboItem(0, SELECT_HERE));
        supplierCbModel.addElement(new ComboItem(0, SELECT_HERE));
        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            purchasedByCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllSuppliers()) {
            supplierCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField()}),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{purchaseDateSP = new SelectedDateSpinner()}),
                new JLabel("Date of Entry:", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{entryDateSP = new SelectedDateSpinner()}),
                new JPanel()
            }),
            getGridPanel(comboPanelWithLookupBtn(purchasedByCB = new JComboBox(purchasedByCbModel),
            new EmployeeLookupAction(purchasedByCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel),
            new SupplierLookupAction(supplierCB)), 2),
            getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), createItmBtnPanel()})
        };
        purchaseDateSP.setEditor(new JSpinner.DateEditor(purchaseDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(purchaseDateSP);
        entryDateSP.setEditor(new JSpinner.DateEditor(entryDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(entryDateSP);
        idField.setEnabled(false);
        idField.setPreferredSize(purchaseDateSP.getPreferredSize());
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane;
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));

//        JPanel rghtHdrPanel;

        hdrPanel = getBorderPanel(new JComponent[]{markAllCB = new JCheckBox()});
        downGridPanel.add(hdrPanel);
        markAllCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    p.mark(markAllCB.isSelected());
                }
            }
        });
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items Bought"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width * 3 / 5, 400));
        add(getDownPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void loadData() {
        invInclTotal = new Double(0.0);
        invExclTotal = new Double(0.0);
        Xbatterypurchase xbp = (Xbatterypurchase) getDbObject();
        if (xbp != null) {
            idField.setText(xbp.getXbatterypurchaseId().toString());
            purchaseDateSP.setValue(new java.util.Date(xbp.getPurchaseDate().getTime()));
            entryDateSP.setValue(new java.util.Date(xbp.getEntryDate().getTime()));
            selectComboItem(supplierCB, xbp.getXsupplierId());
            selectComboItem(purchasedByCB, xbp.getPurchasedBy());
            try {
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xbattery.class,
                        "xbatterypurchase_id=" + xbp.getXbatterypurchaseId(), "xbattery_id");
                for (DbObject rec : recs) {
                    Xbattery battery = (Xbattery) rec;
                    childRows.add(new ItemPanel(battery));
                    invExclTotal += battery.getVatExclUnit();
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
        }
        invInclTotal = invExclTotal * TAX_COEF;
        vatInclTotalLBL.setText(String.format("%.2f", invInclTotal));
        vatExclTotalLBL.setText(String.format("%.2f", invExclTotal));
    }

    private void recalcTotals() {
        invExclTotal = 0.0;
        for (ItemPanel p : childRows) {
            invExclTotal += (Double) p.getVatExclUnitSP().getValue();
        }
        invInclTotal = invExclTotal * TAX_COEF;
        vatInclTotalLBL.setText(String.format("%.2f", invInclTotal));
        vatExclTotalLBL.setText(String.format("%.2f", invExclTotal));
    }

    @Override
    public boolean save() throws Exception {
        Xbatterypurchase xbp = (Xbatterypurchase) getDbObject();
        boolean isNew = false;
        if (xbp == null) {
            xbp = new Xbatterypurchase(null);
            xbp.setXbatterypurchaseId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) purchaseDateSP.getValue();
        if (dt != null) {
            xbp.setPurchaseDate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) entryDateSP.getValue();
        if (dt != null) {
            xbp.setEntryDate(new java.sql.Date(dt.getTime()));
        }
        xbp.setPurchasedBy(getSelectedCbItem(purchasedByCB));
        xbp.setXsupplierId(getSelectedCbItem(supplierCB));
        xbp.setInvoiceVatExcl(invExclTotal);
        xbp.setInvoiceVatIncl(invInclTotal);
        boolean ok = saveDbRecord(xbp, isNew);
        if (ok) {
            for (ItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (ItemPanel d : toDelete) {
                if (d.getItem() != null) {
                    XlendWorks.getExchanger().deleteObject(d.getItem());
                }
            }
        }
        return ok;
    }

    private JPanel createItmBtnPanel() {
        JPanel itemBtnPanel = new JPanel(new GridLayout(1, 2));
        JButton addBtn;
        itemBtnPanel.add(addBtn = new JButton(new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new ItemPanel(null));
                redrawRows();
            }
        }));
        addBtn.setToolTipText("Add Item");
        JButton delBtn;
        itemBtnPanel.add(delBtn = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isMarked = false;
                for (ItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                        isMarked = true;
                    }
                }
                if (toDelete.size() > 0) {
                    for (ItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else if (!isMarked) {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        delBtn.setToolTipText("Delete Item(s)");
        return itemBtnPanel;
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (ItemPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }

    private JComponent getDownPanel() {
        JComponent comp = getGridPanel(new JComponent[]{
                    new JPanel(),
                    new JLabel("Invoice Vat.Incl.Total (R):", SwingConstants.RIGHT),
                    vatInclTotalLBL = new JLabel("", SwingConstants.RIGHT),
                    new JLabel("Invoice Vat.Excl.Total (R):", SwingConstants.RIGHT),
                    vatExclTotalLBL = new JLabel("", SwingConstants.RIGHT),
                    new JPanel()
                });
        vatInclTotalLBL.setBorder(BorderFactory.createLoweredBevelBorder());
        vatExclTotalLBL.setBorder(BorderFactory.createLoweredBevelBorder());
        return comp;
    }
}
