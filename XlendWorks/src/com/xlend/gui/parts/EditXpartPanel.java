package com.xlend.gui.parts;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xparts;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditXpartPanel extends RecordEditPanel {

    private static int categoryID;

    /**
     * @return the categoryID
     */
    public static int getCategoryID() {
        return categoryID;
    }

    /**
     * @param aCategoryID the categoryID to set
     */
    public static void setCategoryID(int aCategoryID) {
        categoryID = aCategoryID;
    }
//    private final String ADD_NEW_SUPPLIER = "--Add new supplier--";
    private JTextField idField;
    private JTextField partNoField;
    private JTextField descriptionField;
    private JComboBox storeCB;        //editable combo
    private JComboBox machineModleCB; //editable combo
    private JSpinner quantitySP;
    private JComboBox lastSupplierCB;
    private JComboBox prevSupplierCB;
    private JSpinner pricePerUnitSP;
    private JSpinner purchaseDateSP;
    private DefaultComboBoxModel lastSupplierCbModel;
    private DefaultComboBoxModel prevSupplierCbModel;

    public EditXpartPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Part No:", //            "Part Description:",
            "Store:",
            "Machine Model:",
            "Quantity:",
            "Last Supplier:", //            "Previous Supplier:",
            "Price Per Unit:",
            "Purchase Date:"
        };

        lastSupplierCbModel = new DefaultComboBoxModel();
        prevSupplierCbModel = new DefaultComboBoxModel();

        prevSupplierCbModel.addElement(new ComboItem(0, ""));
        for (ComboItem ci : XlendWorks.loadAllSuppliers()) {
            lastSupplierCbModel.addElement(ci);
            prevSupplierCbModel.addElement(ci);
        }

        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 10),
            getBorderPanel(new JComponent[]{partNoField = new JTextField(7),
                getBorderPanel(new JComponent[]{
                    new JLabel(" Part description:", SwingConstants.RIGHT),
                    descriptionField = new JTextField()
                })
            }),
            getGridPanel(storeCB = new JComboBox(XlendWorks.loadDistinctStoreNames()), 3),
            getGridPanel(machineModleCB = new JComboBox(XlendWorks.loadDistinctMachineModels()), 3),
            getGridPanel(quantitySP = new SelectedNumberSpinner(0.0, 0.0, 9999999.99, 0.01), 8),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(lastSupplierCB = new JComboBox(lastSupplierCbModel), new SupplierLookupAction(lastSupplierCB)),
                getBorderPanel(new JComponent[]{new JLabel("  Prev.Supplier:"), prevSupplierCB = new JComboBox(prevSupplierCbModel)})
            }),
            getGridPanel(pricePerUnitSP = new SelectedNumberSpinner(0.0, 0.0, 9999999.0, 0.01), 8),
            getGridPanel(purchaseDateSP = new SelectedDateSpinner(), 8)
        };
        idField.setEnabled(false);
        purchaseDateSP.setEditor(new JSpinner.DateEditor(purchaseDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(purchaseDateSP);
        storeCB.setEditable(true);
        machineModleCB.setEditable(true);
        prevSupplierCB.setEnabled(false);
//        purchaseDateSP.setEnabled(false);
        organizePanels(titles, edits, null);
        add(getHistoryPanel(), BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        Xparts part = (Xparts) getDbObject();
        if (part != null) {
            idField.setText(part.getXpartsId().toString());
            partNoField.setText(part.getPartnumber());
            descriptionField.setText(part.getDescription());
            storeCB.setSelectedItem(part.getStorename());
            machineModleCB.setSelectedItem(part.getMachinemodel());
            quantitySP.setValue(part.getQuantity());
            selectComboItem(lastSupplierCB, part.getLastsupplierId());
            selectComboItem(prevSupplierCB, part.getPrevsupplierId());
            pricePerUnitSP.setValue(part.getPriceperunit());
            if (part.getPurchased() != null) {
                java.util.Date dt = new java.util.Date(part.getPurchased().getTime());
                purchaseDateSP.setValue(dt);
            }
            quantitySP.setEnabled(false);
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xparts part = (Xparts) getDbObject();
        if (part == null) {
            isNew = true;
            part = new Xparts(null);
            part.setXpartsId(0);
            part.setXpartcategoryId(getCategoryID());
        }
        part.setPartnumber(partNoField.getText());
        part.setDescription(descriptionField.getText());
        JTextField ed = (JTextField) storeCB.getEditor().getEditorComponent();
        part.setStorename(ed.getText());
        ed = (JTextField) machineModleCB.getEditor().getEditorComponent();
        part.setMachinemodel(ed.getText());
        if (quantitySP.isEnabled()) {
            part.setQuantity((Double) quantitySP.getValue());
        }
        part.setPriceperunit((Double) pricePerUnitSP.getValue());
        if (!isNew) {
            if (part.getLastsupplierId() != null && getSelectedCbItem(lastSupplierCB).intValue() != part.getLastsupplierId().intValue()) {
                part.setPrevsupplierId(part.getLastsupplierId());
            }
        } else {
            part.setPrevsupplierId(getSelectedCbItem(prevSupplierCB));
        }
        part.setLastsupplierId(getSelectedCbItem(lastSupplierCB));
        java.util.Date dt = (java.util.Date) purchaseDateSP.getValue();
        if (dt != null) {
            part.setPurchased(new java.sql.Date(dt.getTime()));
        }
        return saveDbRecord(part, isNew);
    }

    private JPanel getHistoryPanel() {
        JPanel histPanel = new JPanel();
        Xparts part = (Xparts) getDbObject();
        histPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Parts Moving"));
        if (part != null) {
            try {
                JSplitPane sp = new JSplitPane(1);
                AddStocksGrid ag;
                sp.setLeftComponent(ag = new AddStocksGrid(XlendWorks.getExchanger(), part.getXpartsId(), this));
                ag.setBorder(BorderFactory.createTitledBorder("Entered"));
                BookOutsGrid bo;
                sp.setRightComponent(bo = new BookOutsGrid(XlendWorks.getExchanger(), part.getXpartsId(), this));
                bo.setBorder(BorderFactory.createTitledBorder("Dispatched"));
                sp.setDividerLocation(0.5);
                histPanel.add(sp);
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
        return histPanel;
    }


    @Override
    public void freeResources() {
        //TODO
    }
}
