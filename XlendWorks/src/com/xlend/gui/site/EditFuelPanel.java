package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.supplier.SupplierLookupAction;
import com.xlend.orm.Xfuel;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


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
    
    public EditFuelPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Cash/Account:",
            "Amount:",
//            "Liters:",
            "Site",
            "Issued To:",
            "Issued By:",
            "Supplier:"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        issuedToCbModel = new DefaultComboBoxModel();
        issuedByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            issuedToCbModel.addElement(ci);
            issuedByCbModel.addElement(ci);
        }
        supplierCbModel = new DefaultComboBoxModel();
        supplierCbModel.addElement(new ComboItem(0, ADD_NEW_SUPPLIER));
        for (ComboItem ci : XlendWorks.loadAllSuppliers(DashBoard.getExchanger())) {
            supplierCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(fuelDateSP = new SelectedDateSpinner(),4),
            getGridPanel(new JComponent[]{cashRb = new JRadioButton("cashe"), asmtRb = new JRadioButton("account")}, 4),
            getGridPanel(new JComponent[]{amountSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, .1),
                        new JLabel("Liters:", SwingConstants.RIGHT),
                        litersSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, 1)},5),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new EmployeeLookupAction(siteCB)),
            comboPanelWithLookupBtn(issuedToCB = new JComboBox(issuedToCbModel), new EmployeeLookupAction(issuedToCB)),
            comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel), new EmployeeLookupAction(issuedByCB)),
            comboPanelWithLookupBtn(supplierCB = new JComboBox(supplierCbModel), new SupplierLookupAction(supplierCB))
        };

        fuelDateSP.setEditor(new JSpinner.DateEditor(fuelDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(fuelDateSP);
        
        ButtonGroup group = new ButtonGroup();
        group.add(cashRb);
        group.add(asmtRb);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);        
    }

    @Override
    public void loadData() {
        //
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }
}
