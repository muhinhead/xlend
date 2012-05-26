package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.EditSupplierDialog;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xfuel;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.sql.Date;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditFuelPanel extends RecordEditPanel {

    private DefaultComboBoxModel siteCbModel;
    private JComboBox siteCB;
    private DefaultComboBoxModel issuedToCbModel;
    private DefaultComboBoxModel issuedByCbModel;
    private DefaultComboBoxModel supplierCbModel;
    private final String ADD_NEW_SUPPLIER = "--Add new supplier--";
    private JTextField idField;
    private SelectedDateSpinner fuelDateSP;
    private JRadioButton cashRb;
    private JRadioButton asmtRb;
    private SelectedNumberSpinner amountSP;
    private SelectedNumberSpinner litersSP;
    private JComboBox issuedToCB;
    private JComboBox issuedByCB;
    private JComboBox supplierCB;
    private JPanel spl;

    public EditFuelPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Cash/Account:",
            "Amount:", //"Liters:",
            "Site",
            "Issued To:",
            "Issued By:",
            "Supplier:"
        };
        ComboItem unknown = new ComboItem(0, "--Unknown--");
        siteCbModel = new DefaultComboBoxModel();
        siteCbModel.addElement(unknown);
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        issuedToCbModel = new DefaultComboBoxModel();
        issuedByCbModel = new DefaultComboBoxModel();
        issuedToCbModel.addElement(unknown);
        issuedByCbModel.addElement(unknown);
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            issuedToCbModel.addElement(ci);
            issuedByCbModel.addElement(ci);
        }
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, ADD_NEW_SUPPLIER));
//        supplierCbModel.addElement(unknown);
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(fuelDateSP = new SelectedDateSpinner(), 4),
            getGridPanel(new JComponent[]{cashRb = new JRadioButton("Cash"), asmtRb = new JRadioButton("Account")}, 4),
            getGridPanel(new JComponent[]{amountSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, .1),
                new JLabel("Liters:", SwingConstants.RIGHT),
                litersSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, 1)}, 5),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            comboPanelWithLookupBtn(issuedToCB = new JComboBox(issuedToCbModel), new EmployeeLookupAction(issuedToCB)),
            comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel), new EmployeeLookupAction(issuedByCB)),
            spl = comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB))
        };
        cashRb.addChangeListener(yesNoSwitchedAction());
        asmtRb.addChangeListener(yesNoSwitchedAction());

        fuelDateSP.setEditor(new JSpinner.DateEditor(fuelDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(fuelDateSP);

        ButtonGroup group = new ButtonGroup();
        group.add(cashRb);
        group.add(asmtRb);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        asmtRb.setSelected(true);
    }

    private ChangeListener yesNoSwitchedAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                showHideSupplierCB();
            }
        };
    }

    private void showHideSupplierCB() {
        labels[labels.length - 1].setVisible(asmtRb.isSelected());
        spl.setVisible(asmtRb.isSelected());
    }

    @Override
    public void loadData() {
        Xfuel xfl = (Xfuel) getDbObject();
        if (xfl != null) {
            idField.setText(xfl.getXfuelId().toString());
            Date sdt = xfl.getFdate();
            if (sdt != null) {
                fuelDateSP.setValue(new java.util.Date(sdt.getTime()));
            }
            if (xfl.getIscache() != null && xfl.getIscache() == 1) {
                cashRb.setSelected(true);
            } else {
                asmtRb.setSelected(true);
            }
            if (xfl.getLiters()!=null) {
                litersSP.setValue(new Double((Integer)xfl.getLiters()));
            }
            if (xfl.getAmmount()!=null) {
                amountSP.setValue(xfl.getAmmount());
            }
            if (xfl.getIssuedbyId()!=null) {
                selectComboItem(issuedByCB, xfl.getIssuedbyId());
            }
            if (xfl.getIssuedtoId()!=null) {
                selectComboItem(issuedToCB, xfl.getIssuedtoId());
            }
            if (xfl.getXsupplierId() != null) {
                selectComboItem(supplierCB, xfl.getXsupplierId());
            }
            if (xfl.getXsiteId() != null) {
                selectComboItem(siteCB, xfl.getXsiteId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xfuel xfl = (Xfuel) getDbObject();
        boolean isNew = false;
        if (xfl == null) {
            xfl = new Xfuel(null);
            xfl.setXfuelId(0);
            isNew = true;
        }
        ComboItem ci = (ComboItem) supplierCB.getSelectedItem();
        if (asmtRb.isSelected() && ci.getValue().startsWith("--")) { // add new supplier
            EditSupplierDialog esd = new EditSupplierDialog("New Supplier", null);
            if (esd.okPressed) {
                Xsupplier xsupplier = (Xsupplier) esd.getEditPanel().getDbObject();
                if (xsupplier != null) {
                    ci = new ComboItem(xsupplier.getXsupplierId(), xsupplier.getCompanyname());
                    supplierCbModel.addElement(ci);
                    supplierCB.setSelectedItem(ci);
                }
            }
            return false;
        } else {
            java.util.Date dt = (java.util.Date) fuelDateSP.getValue();
            if (dt != null) {
                xfl.setFdate(new java.sql.Date(dt.getTime()));
            }
            xfl.setIscache(cashRb.isSelected() ? 1 : 0);
            Double val = (Double) amountSP.getValue();
            if (val != null) {
                xfl.setAmmount(val);
            } else {
                xfl.setAmmount(0.0);
            }
            val = (Double) litersSP.getValue();
            if (val != null) {
                Integer ival = val.intValue();
                xfl.setLiters(ival);
            }
            xfl.setXsiteId(getSelectedCbItem(siteCB));
            xfl.setXsupplierId(cashRb.isSelected()?null:getSelectedCbItem(supplierCB));
            xfl.setIssuedbyId(getSelectedCbItem(issuedByCB));
            xfl.setIssuedtoId(getSelectedCbItem(issuedToCB));
            return saveDbRecord(xfl, isNew);
        }
    }
}
