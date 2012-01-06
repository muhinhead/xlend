package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineGrid;
import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.orm.Xdieselcard;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
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
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), machineLookupAction = machinesLookup()),
            comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), employeeLookupAction = emplLookup(operatorCB)),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), siteLookupAction = siteLookup()),
            getGridPanel(litersSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            comboPanelWithLookupBtn(personIssCB = new JComboBox(personIssCbModel), persIssLookupAction = emplLookup(personIssCB))
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits);
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
            litersSP.setValue(xdc.getAmountLiters());
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
        ComboItem ci = (ComboItem) machineCB.getSelectedItem();
        xdc.setXmachineId(ci == null ? null : ci.getId());
        ci = (ComboItem) operatorCB.getSelectedItem();
        xdc.setOperatorId(ci == null ? null : ci.getId());
        ci = (ComboItem) siteCB.getSelectedItem();
        xdc.setXsiteId(ci == null ? null : ci.getId());
        ci = (ComboItem) personIssCB.getSelectedItem();
        xdc.setPersonissId(ci == null ? null : ci.getId());
        xdc.setAmountLiters((Integer) litersSP.getValue());
        Date dt = (Date) dateSP.getValue();
        if (dt != null) {
            xdc.setCarddate(new java.sql.Date(dt.getTime()));
        }
        try {
            xdc.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(xdc);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

    private AbstractAction machinesLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Machines Lookup", machineCB,
                            new MachineGrid(DashBoard.getExchanger(), Selects.SELECT_MASCHINES4LOOKUP, false),
                            new String[]{"tmvnr", "reg_nr"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction emplLookup(final JComboBox cb) {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Employee Lookup", cb,
                            new EmployeesGrid(DashBoard.getExchanger(), Selects.SELECT_FROM_EMPLOYEE, true),
                            new String[]{"clock_num", "id_num", "first_name", "sur_name"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction siteLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Site Lookup", siteCB,
                            new SitesGrid(DashBoard.getExchanger(), Selects.SELECT_SITES4LOOKUP, true),
                            new String[]{"name"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
