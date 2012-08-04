package com.xlend.gui.parts;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xpartstocks;
import com.xlend.orm.Xstocks;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import javax.swing.*;

/**
 *
 * @author nick
 */
class EditPartStockPanel extends RecordEditPanel {

    public static int xpartID;
    private JTextField idField;
    private JComboBox warehouseCB;
    private JComboBox supplierCB;
    private JSpinner amountSP;
    private DefaultComboBoxModel supplierCbModel, warehouseCbModel;

    public EditPartStockPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Supplier:",
            "Warehouse:",
            "Amount:"
        };

        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, "--Add new supplier--"));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        warehouseCbModel = new DefaultComboBoxModel();
        warehouseCbModel.addElement(new ComboItem(0, "--Add new warehouse--"));
        for (ComboItem ci : XlendWorks.loadAllWarehouses(DashBoard.getExchanger())) {
            warehouseCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB)),
            getGridPanel(warehouseCB = new JComboBox(warehouseCbModel), 2),
            getGridPanel(amountSP = new SelectedNumberSpinner(0.0, 0.0, 1000000.00, 0.01), 5)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {

        Xpartstocks xps = (Xpartstocks) getDbObject();
        if (xps != null) {
            idField.setText(xps.getXpartstocksId().toString());
            selectComboItem(supplierCB, xps.getXsupplierId());
            selectComboItem(warehouseCB, xps.getXstocksId());
            amountSP.setValue(xps.getRest());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xpartstocks xps = (Xpartstocks) getDbObject();
        boolean isNew = false;
        if (xps == null) {
            isNew = true;
            xps = new Xpartstocks(null);
            xps.setXpartstocksId(0);
            xps.setXpartsId(xpartID);
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
            ci = (ComboItem) warehouseCB.getSelectedItem();
            if (ci.getValue().startsWith("--")) { // add new warehouse (stock)
                EditStockDialog ewd = new EditStockDialog("New Warehouse", null);
                if (ewd.okPressed) {
                    Xstocks xstock = (Xstocks) ewd.getEditPanel().getDbObject();
                    if (xstock != null) {
                        ci = new ComboItem(xstock.getXstocksId(), xstock.getName());
                        warehouseCbModel.addElement(ci);
                        warehouseCB.setSelectedItem(ci);
                    }
                }
            } else {
                xps.setXsupplierId(getSelectedCbItem(supplierCB));
                xps.setXstocksId(getSelectedCbItem(warehouseCB));
                xps.setRest((Double) amountSP.getValue());
                return saveDbRecord(xps, isNew);
            }
        }
        return false;
    }
}
