package com.xlend.gui.assign;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xmachine;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class MachineAssignmentPanel extends RecordEditPanel {

    /**
     * @param aXmachine the xemployee to set
     */
    public static void setXmachine(Xmachine aXmachine) {
        xmachine = aXmachine;
    }
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel operatorCbModel;
    private JTextField idField;
    private JComboBox siteCB;
    private JComboBox operatorCB;
    private JSpinner dateSP;
    private static Xmachine xmachine = null;

    public MachineAssignmentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", "Date:", "Site:", "Operator:"
        };
        siteCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        operatorCbModel.addElement(new ComboItem(0, "NO OPERATOR"));
//        почему комбобокс пустой ???
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            getGridPanel(dateSP = new SelectedDateSpinner(), 7),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel),
            new EmployeeLookupAction(operatorCB, Selects.notAssignedOperatorCondition)), 3)
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
        IMessageSender exchanger = DashBoard.getExchanger();
        Integer prevOperatorID = null;
        try {
            java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
//            System.out.println("CONDITION: xmachine_id=" + xmachine.getXmachineId());
            DbObject[] oldAssigns = exchanger.getDbObjects(Xopmachassing.class,
                    "xmachine_id=" + xmachine.getXmachineId() + " and date_end is null", null);
            for (DbObject rec : oldAssigns) {
                Xopmachassing oldAssign = (Xopmachassing) rec;
                prevOperatorID = oldAssign.getXemployeeId();
                prevOperatorID = (prevOperatorID == 0 ? null : prevOperatorID);
                oldAssign.setDateEnd(now);
                oldAssign.setXmachineId(oldAssign.getXmachineId() == 0 ? null : oldAssign.getXmachineId());
                oldAssign.setXemployeeId(prevOperatorID);
                exchanger.saveDbObject(oldAssign);
            }
            Xopmachassing assign = new Xopmachassing(null);
            assign.setXopmachassingId(0);
            assign.setXmachineId(xmachine.getXmachineId() == 0 ? null : xmachine.getXmachineId());
            assign.setXsiteId(getSelectedCbItem(siteCB));
            assign.setXemployeeId(getSelectedCbItem(operatorCB));
//            boolean ok = false;
            assign.setDateStart(now);
            assign.setNew(true);
            if (prevOperatorID != null && (assign.getXemployeeId() == null || prevOperatorID.intValue() != assign.getXemployeeId().intValue())) {
                DbObject[] prev = exchanger.getDbObjects(Xemployee.class, "xemployee_id=" + prevOperatorID, null);
                if (prev.length > 0) {
                    Xemployee prevOperator = (Xemployee) prev[0];
                    new ChooseDepotForOperatorDialog(prevOperator);
                    Xopmachassing assignPrevOperator = new Xopmachassing(null);
                    assignPrevOperator.setXopmachassingId(0);
                    assignPrevOperator.setNew(true);
                    assignPrevOperator.setXemployeeId(prevOperatorID);
                    assignPrevOperator.setXmachineId(null);
                    assignPrevOperator.setDateStart(now);
                    assignPrevOperator.setXsiteId(ChooseDepotForOperatorDialog.getDepot_id());
                    exchanger.saveDbObject(assignPrevOperator);
                }
//                ok = true;
//            } else {
//                ok = true;
            }
//            if (ok) {
            exchanger.saveDbObject(assign);
//            }
            return true;//ok;
        } catch (Exception ex) {
            throw ex;
        }
    }

    private JComponent getHistoryPanel() {
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Assignments History"));
        try {
            historyPanel.add(new AssignmentsGrid(DashBoard.getExchanger(),
                    Selects.SELECT_MACHINE_ASSIGNMENTS.replace("#", xmachine.getXmachineId().toString())) {
                @Override
                protected AbstractAction addAction() {
                    return new AbstractAction("Add new assignment") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                MachineAssignmentDialog dlg = (MachineAssignmentDialog) getOwnerDialog();
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
}
