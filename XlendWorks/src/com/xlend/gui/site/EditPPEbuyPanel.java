package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xppebuy;
import com.xlend.orm.Xppebuyitem;
import com.xlend.orm.Xsitediaryitem;
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

/**
 *
 * @author Nick Mukhin
 */
class EditPPEbuyPanel extends RecordEditPanel {

    private DefaultComboBoxModel boughtByCbModel;
    private DefaultComboBoxModel supplierCbModel;
    private DefaultComboBoxModel authorizedByCbModel;
    private JTextField idField;
    private SelectedDateSpinner buyDateSP;
    private JComboBox boughtByCB;
    private JComboBox supplierCB;
    private JComboBox authorizedByCB;
    private JCheckBox markAllCB;
    private JPanel downGridPanel;
    private ArrayList<EditPPEbuyPanel.ItemPanel> childRows;
    private ArrayList<EditPPEbuyPanel.ItemPanel> toDelete;
    private JComponent hdrPanel;

    private class ItemPanel extends JPanel {

        private Xppebuyitem item;
        private JCheckBox markCB;
        private JComboBox ppeTypeCB;
        private DefaultComboBoxModel ppeTypeCbModel;
        private JSpinner qtySP;
        private JSpinner priceSP;
        private JLabel stockLevelLB;

        ItemPanel(Xppebuyitem item) {
            super(new BorderLayout());
            this.item = item;
            stockLevelLB = new JLabel("", SwingConstants.RIGHT);
            stockLevelLB.setBorder(BorderFactory.createEtchedBorder());
            ppeTypeCbModel = new DefaultComboBoxModel();
            ppeTypeCbModel.addElement(new ComboItem(0, "--select PPE here--"));
            for (ComboItem ci : XlendWorks.loadAllPPEtypes(DashBoard.getExchanger())) {
                ppeTypeCbModel.addElement(ci);
            }
            add(markCB = new JCheckBox(), BorderLayout.WEST);
            add(comboPanelWithLookupBtn(ppeTypeCB = new JComboBox(ppeTypeCbModel),
                    new PPElookupAction(ppeTypeCB)), BorderLayout.CENTER);
//            qtySP = new SelectedNumberSpinner(0, 0, 999999, 1);
            JPanel rightGridPanel = new JPanel(new GridLayout(1, 3));
            rightGridPanel.add(qtySP = new SelectedNumberSpinner(0, 0, 999999, 1));
            rightGridPanel.add(priceSP = new SelectedNumberSpinner(.0, .0, 999999.99, .1));
            rightGridPanel.add(stockLevelLB);
            add(rightGridPanel, BorderLayout.EAST);
            ppeTypeCB.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateStockLevels();
                }
            });
            load();
        }

        public void updateStockLevels() {
            Integer xppetypeID = getSelectedCbItem(ppeTypeCB);
            stockLevelLB.setText(XlendWorks.getStockLevels(DashBoard.getExchanger(), xppetypeID));
            priceSP.setValue(Double.parseDouble(XlendWorks.getLastPPEprice(DashBoard.getExchanger(), xppetypeID)));
        }

        private void load() {
            if (getItem() != null) {
                selectComboItem(ppeTypeCB, getItem().getXppetypeId());
                qtySP.setValue(getItem().getQuantity());
                priceSP.setValue(getItem().getPriceperunit());
            }
            updateStockLevels();
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getSelectedCbItem(ppeTypeCB) != null) {
                if (getItem() == null) {
                    item = new Xppebuyitem(null);
                    getItem().setXppebuyitemId(0);
                    Xppebuy xb = (Xppebuy) getDbObject();
                    getItem().setXppebuyId(xb.getXppebuyId());
                    isNew = true;
                }
                getItem().setXppetypeId(getSelectedCbItem(ppeTypeCB));
                getItem().setQuantity((Integer) qtySP.getValue());
                getItem().setPriceperunit((Double) priceSP.getValue());
                return saveDbRecord(getItem(), isNew);
            }
            return true;
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xppebuyitem) DashBoard.getExchanger().saveDbObject(dbOb);
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
        public Xppebuyitem getItem() {
            return item;
        }
    }

    public EditPPEbuyPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Bought by:",
            "Supplier:",
            "Authorized by:"
        };
        boughtByCbModel = new DefaultComboBoxModel();
        supplierCbModel = new DefaultComboBoxModel();
        authorizedByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            boughtByCbModel.addElement(ci);
            authorizedByCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 6),
            getGridPanel(buyDateSP = new SelectedDateSpinner(), 6),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(boughtByCB = new JComboBox(boughtByCbModel), new EmployeeLookupAction(boughtByCB)),
                new JPanel()}),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB)),
                new JPanel()
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(authorizedByCB = new JComboBox(authorizedByCbModel),
                new EmployeeLookupAction(authorizedByCB, "coalesce(wage_category,1)=1")),
                getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), createItmBtnPanel()})
            })
        };
        buyDateSP.setEditor(new JSpinner.DateEditor(buyDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(buyDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane;
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        JPanel rghtHdrPanel;

        hdrPanel = getBorderPanel(new JComponent[]{
                    markAllCB = new JCheckBox(),
                    rghtHdrPanel = new JPanel(new GridLayout(1, 4))
                });
        rghtHdrPanel.add(new JPanel());
        rghtHdrPanel.add(new JLabel("Quantity:", SwingConstants.RIGHT));
        rghtHdrPanel.add(new JLabel("Price per Unit:", SwingConstants.RIGHT));
        rghtHdrPanel.add(new JLabel("Stock Levels:", SwingConstants.RIGHT));
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
        scrollPane.setPreferredSize(new Dimension(d.width / 3, 400));
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
                for (ItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (ItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        delBtn.setToolTipText("Delete Item(s)");
        return itemBtnPanel;
    }

    @Override
    public void loadData() {

        Xppebuy xb = (Xppebuy) getDbObject();
        if (xb != null) {
            idField.setText(xb.getXppebuyId().toString());
            buyDateSP.setValue(new java.util.Date(xb.getBuydate().getTime()));
            selectComboItem(boughtByCB, xb.getBoughtbyId());
            selectComboItem(supplierCB, xb.getXsupplierId());
            selectComboItem(authorizedByCB, xb.getAuthorizedbyId());
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xppebuyitem.class,
                        "xppebuy_id=" + xb.getXppebuyId(), "xppebuyitem_id");
                for (DbObject rec : recs) {
                    childRows.add(new ItemPanel((Xppebuyitem) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xppebuy xb = (Xppebuy) getDbObject();
        boolean isNew = false;
        if (xb == null) {
            xb = new Xppebuy(null);
            xb.setXppebuyId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) buyDateSP.getValue();
        if (dt != null) {
            xb.setBuydate(new java.sql.Date(dt.getTime()));
        }
        xb.setBoughtbyId(getSelectedCbItem(boughtByCB));
        xb.setAuthorizedbyId(getSelectedCbItem(authorizedByCB));
        xb.setXsupplierId(getSelectedCbItem(supplierCB));
        boolean ok = saveDbRecord(xb, isNew);
        if (ok) {
            for (ItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (ItemPanel d : toDelete) {
                if (d.getItem() != null) {
                    DashBoard.getExchanger().deleteObject(d.getItem());
                }
            }
        }
        return ok;
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
}
