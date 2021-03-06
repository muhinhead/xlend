package com.xlend.gui.assign;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.swing.*;

/**
 *
 * @author nick
 */
public class EmployeeAssignmentPanel extends RecordEditPanel {

    /**
     * @param aXemployee the xemployee to set
     */
    public static void setXemployee(Xemployee aXemployee) {
        xemployee = aXemployee;
    }
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JTextField idField;
    private JComboBox siteCB;
    private JComboBox machineCB;
    private JSpinner dateSP;
    private static Xemployee xemployee = null;

    public EmployeeAssignmentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", "Date:", "Site:", "Machine:"
        };
        siteCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadActiveSites()) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        machineCbModel.addElement(new ComboItem(0, "NO MACHINE"));
        for (ComboItem ci : XlendWorks.loadMachines(Selects.MACHINETVMS //Selects.NOT_ASSIGNED_MACHINES
                )) {
            machineCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 9),
            getGridPanel(dateSP = new SelectedDateSpinner(), 9),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel),
            new MachineLookupAction(machineCB, null/*Selects.notAssignedMachinesCondition*/)), 2)
        };
        idField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        organizePanels(titles, edits, null);
        add(getHistoryPanel(), BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        //nothing to do here, because this form for inserting records only
    }

    @Override
    public boolean save() throws Exception {
        try {
            Integer prevMachineID = null;
            Integer prevSiteID = null;
            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
            DbObject[] oldAssigns = XlendWorks.getExchanger().getDbObjects(Xopmachassing.class,
                    "xemployee_id=" + xemployee.getXemployeeId() + " and date_end is null", null);
            for (DbObject rec : oldAssigns) {
                Xopmachassing oldAssign = (Xopmachassing) rec;
                oldAssign.setDateEnd(now);
                oldAssign.setXmachineId(oldAssign.getXmachineId() == 0 ? null : oldAssign.getXmachineId());
                XlendWorks.getExchanger().saveDbObject(oldAssign);
                prevMachineID = oldAssign.getXmachineId();
                prevSiteID = oldAssign.getXsiteId();
            }
            Xopmachassing assign = new Xopmachassing(null);
            assign.setXopmachassingId(0);
            assign.setXemployeeId(xemployee.getXemployeeId());
            assign.setXsiteId(getSelectedCbItem(siteCB));
            if (machineCB.getSelectedIndex() > 0) {
                assign.setXmachineId(getSelectedCbItem(machineCB));
            } else {
                assign.setXmachineId(null);
            }
            assign.setDateStart(now);
            assign.setNew(true);
            if (prevMachineID != null && (assign.getXmachineId() == null || prevMachineID.intValue() != assign.getXmachineId().intValue())) {
                GeneralFrame.infoMessageBox("FYI", "The previous machine assigned will stay on site without operator");
                Xopmachassing assignPrevMachine = new Xopmachassing(null);
                assignPrevMachine.setXopmachassingId(0);
                assignPrevMachine.setXemployeeId(null);
                assignPrevMachine.setXmachineId(prevMachineID);
                assignPrevMachine.setDateStart(now);
                assignPrevMachine.setXsiteId(prevSiteID);
                XlendWorks.getExchanger().saveDbObject(assignPrevMachine);
            }
            XlendWorks.getExchanger().saveDbObject(assign);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private JComponent getHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Assignments History"));
        try {
//            AssignmentsGrid asg;
            historyPanel.add(new AssignmentsGrid(XlendWorks.getExchanger(),
                    Selects.SELECT_EMPLOYEE_ASSIGNMENTS.replace("#", xemployee.getXemployeeId().toString())) {
                @Override
                protected AbstractAction addAction() {
                    return new AbstractAction("Add new assignment") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                EmployeeAssignmentDialog dlg = (EmployeeAssignmentDialog) getOwnerDialog();
                                if (save()) {
                                    dlg.setOkPressed(true);
                                    dlg.dispose();
                                }
                            } catch (Exception ex) {
                                XlendWorks.log(ex);
                                GeneralFrame.errMessageBox("Error:", ex.getMessage());
                            }
                        }
                    };
                }
            });

        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return historyPanel;
    }

    @Override
    public void freeResources() {
        //TODO
    }    
}
