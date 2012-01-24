package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xdieselcard;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselCardPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private JSpinner litersSP;
    private JComboBox siteCB;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox machineCB;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox operatorCB;
    private DefaultComboBoxModel operatorCbModel;
    private JComboBox personIssCB;
    private DefaultComboBoxModel personIssCbModel;
    private AbstractAction machineLookupAction;
    private AbstractAction employeeLookupAction;
    private AbstractAction siteLookupAction;
    private AbstractAction persIssLookupAction;

    public EditDieselCardPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Machine/Truck/Vehicle:",
            "Operator:",
            "Site:",
            "Liters:",
            "Person Issuing:"
        };
        personIssCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            personIssCbModel.addElement(ci);
            operatorCbModel.addElement(ci);
        }
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger(), false)) {
            machineCbModel.addElement(ci);
        }
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(dateSP = new SelectedDateSpinner(), 3),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel),
            new MachineLookupAction(machineCB, null)),
            comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel),
            employeeLookupAction = new EmployeeLookupAction(operatorCB)),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            getGridPanel(litersSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            comboPanelWithLookupBtn(personIssCB = new JComboBox(personIssCbModel),
            persIssLookupAction = new EmployeeLookupAction(personIssCB))
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        setPreferredSize(new Dimension(500, getPreferredSize().height));
    }

    @Override
    public void loadData() {
        Xdieselcard xdc = (Xdieselcard) getDbObject();
        if (xdc != null) {
            idField.setText(xdc.getXdieselcardId().toString());
            if (xdc.getXmachineId() != null) {
                selectComboItem(machineCB, xdc.getXmachineId());
            }
            if (xdc.getOperatorId() != null) {
                selectComboItem(operatorCB, xdc.getOperatorId());
            }
            if (xdc.getXsiteId() != null) {
                selectComboItem(siteCB, xdc.getXsiteId());
            }
            if (xdc.getAmountLiters() != null) {
                litersSP.setValue(xdc.getAmountLiters());
            }
            if (xdc.getXsiteId() != null) {
                selectComboItem(siteCB, xdc.getXsiteId());
            }
            if (xdc.getPersonissId() != null) {
                selectComboItem(personIssCB, xdc.getPersonissId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xdieselcard xdc = (Xdieselcard) getDbObject();
        boolean isNew = false;
        if (xdc == null) {
            xdc = new Xdieselcard(null);
            xdc.setXdieselcardId(0);
            isNew = true;
        }
        xdc.setXmachineId(getSelectedCbItem(machineCB));
        xdc.setOperatorId(getSelectedCbItem(operatorCB));
        xdc.setXsiteId(getSelectedCbItem(siteCB));
        xdc.setPersonissId(getSelectedCbItem(personIssCB));
        xdc.setAmountLiters((Integer) litersSP.getValue());
        Date dt = (Date) dateSP.getValue();
        if (dt != null) {
            xdc.setCarddate(new java.sql.Date(dt.getTime()));
        }
        return saveDbRecord(xdc, isNew);
    }
//    private AbstractAction siteLookup() {
//        return new AbstractAction("...") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    LookupDialog ld = new LookupDialog("Site Lookup", siteCB,
//                            new SitesGrid(DashBoard.getExchanger(), Selects.SELECT_SITES4LOOKUP, true),
//                            new String[]{"name"});
//                } catch (RemoteException ex) {
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
//    }
}
