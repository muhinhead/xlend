package com.xlend.gui.assign;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xmachine;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.*;

/**
 *
 * @author nick
 */
class EditSiteAssignmentPanel extends RecordEditPanel {

    public static int xsiteID;
    private DefaultComboBoxModel operatorCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JTextField idField;
    private JComboBox operatorCB;
    private JComboBox machineCB;
    private JSpinner dateSP;

    public EditSiteAssignmentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", "Date:", "Machine:", "Operator:"
        };
        operatorCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        operatorCbModel.addElement(new ComboItem(0, "NO OPERATOR"));
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
        }
        machineCbModel.addElement(new ComboItem(0, "NO MACHINE"));
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger(), Selects.MACHINETVMS)) {
            machineCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(dateSP = new SelectedDateSpinner(), 4),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel),
            new MachineLookupAction(machineCB, Selects.notAssignedMachinesCondition)),
            comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel),
            new EmployeeLookupAction(operatorCB, Selects.notAssignedOperatorCondition))
        };
        idField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        organizePanels(titles, edits, null);

    }

    @Override
    public void loadData() {//not used yet, editing not allowed
        Xopmachassing assign = (Xopmachassing) getDbObject();
        if (assign != null) {
            idField.setText(assign.getXopmachassingId().toString());
            java.util.Date dt = new java.util.Date(assign.getDateStart().getTime());
            dateSP.setValue(dt);
            if (assign.getXemployeeId() != null) {
                selectComboItem(operatorCB, assign.getXemployeeId());
            }
            if (assign.getXmachineId() != null) {
                selectComboItem(machineCB, assign.getXmachineId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean ok = false;
        boolean isNew = false;
        Xopmachassing assign = (Xopmachassing) getDbObject();
        if (assign == null) {
            assign = new Xopmachassing(null);
            assign.setXopmachassingId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            assign.setDateStart(new java.sql.Date(dt.getTime()));
        }

        int machine_id = getSelectedCbItem(machineCB) == null ? 0 : getSelectedCbItem(machineCB).intValue();
        int operator_id = getSelectedCbItem(operatorCB) == null ? 0 : getSelectedCbItem(operatorCB).intValue();
        if (machine_id == 0 && operator_id == 0) {
            GeneralFrame.errMessageBox("Attention!", "Choose operator and/or machine please!");
        } else {
            assign.setXemployeeId(operator_id == 0 ? null : operator_id);
            assign.setXmachineId(machine_id == 0 ? null : machine_id);
            assign.setXsiteId(xsiteID);
            Xopmachassing previous = XlendWorks.findCurrentAssignment(DashBoard.getExchanger(), machine_id, operator_id);
            if (previous != null) { //just move operator/machine pair to another site
                if (previous.getXsiteId() == xsiteID) {
                    GeneralFrame.errMessageBox("Attention!", "This operator on this machine are already here");
                } else {
                    previous.setDateEnd(new java.sql.Date(dt.getTime()));
                    previous.setXemployeeId(previous.getXemployeeId());
                    previous = (Xopmachassing) DashBoard.getExchanger().saveDbObject(previous);
                    ok = true;
                }
            } else {
                previous = XlendWorks.findCurrentAssignment(DashBoard.getExchanger(), machine_id, 0);
                if (previous != null) { //move previous operator
                    Xemployee prevOperator = (Xemployee) DashBoard.getExchanger().loadDbObjectOnID(
                            Xemployee.class, previous.getXemployeeId());
                    if (prevOperator != null) {
                        EmployeeAssignmentPanel.setXemployee(prevOperator);
                        new EmployeeAssignmentDialog("Assignments of "
                                + prevOperator.getFirstName() + " "
                                + prevOperator.getSurName() + " ("
                                + prevOperator.getClockNum() + ")", prevOperator);
                        if (EmployeeAssignmentDialog.okPressed) {
                            ok = true;
                        }
                    } else {
                        previous = XlendWorks.findCurrentAssignment(DashBoard.getExchanger(), 0, operator_id);
                        if (previous != null) {
                            Xmachine prevMachine = (Xmachine) DashBoard.getExchanger().loadDbObjectOnID(
                                    Xmachine.class, previous.getXmachineId());
                            if (prevMachine != null) {
                                if (GeneralFrame.yesNo("Attention!", "Previous machine "
                                        + prevMachine.getClassify() + prevMachine.getTmvnr()
                                        + " will stay on its site w/o operator. Agree?") == JOptionPane.YES_OPTION) {
                                    previous.setDateEnd(new java.sql.Date(dt.getTime()));
                                    previous.setXemployeeId(null);
                                    ok = true;
                                }
                            } else {
                                ok = true;
                            }
                        } else {
                            ok = true;
                        }
                    }
                } else {
                    ok = true;
                }
            }
        }
        if (ok) {
            return saveDbRecord(assign, isNew);
        }
        return false;
    }
}
