package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.assign.ChooseDepotForOperatorDialog;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xmachine;
//import com.xlend.orm.Xlowbed;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.Xtrip;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripPanel extends RecordEditPanel {

    private static String[] detailPanelLabels = new String[]{
        "Establishing",
        "De-Establishing",
        "Moving",
        "Exchanging"
    };
    private static Integer xlowbed_id;

    static class OtherFieldShowAction implements ActionListener {

        private JTextField otherField;

        public OtherFieldShowAction(JTextField otherFld) {
            this.otherField = otherFld;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            ComboItem ci = (ComboItem) toSiteCbModel.getSelectedItem();
            otherField.setVisible(ci.getId() < 0);
        }
    }
    private DefaultComboBoxModel lowbedCbModel;
    private DefaultComboBoxModel fromSiteCbModel;
    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel assistantCbModel;
    static DefaultComboBoxModel toSiteCbModel = null;
    static DefaultComboBoxModel machineCbModel = null;
    static DefaultComboBoxModel operatorCbModel = null;
    private JTextField idField;
    private JSpinner dateSP;
    private JComboBox lowbedCB;
    private JComboBox fromSiteCB;
    private JComboBox driverCB;
    private JComboBox assistantCB;
    private JCheckBox completeCB;
    private JRadioButton establishRB;
    private JRadioButton deEstablishRB;
    private JRadioButton movingRB;
    private JRadioButton exchangingRB;
    private JPanel detailInfoPanel;
    private EditTripEstablishingPanel establishPanel;
    private EditTripDeEstablishingPanel deEstablishPanel;
    private EditTripMovinganel movingPanel;
    private EditTripExchangePanel exchangePanel;
    private EditSubPanel[] subPanels;
    private LowBedLookupAction lbLookupAction;

    public EditTripPanel(DbObject dbObject) {
        super(dbObject);
    }

    public static void setXlowbed(Integer xlbId) {
        xlowbed_id = xlbId;
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Low Bed:",
            "Date:",
            "Leaving From:",
            "Driver:",
            "Assistant:",
            "Trip Type:"
        };
        lowbedCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllLowbeds()) {
            lowbedCbModel.addElement(ci);
        }
        if (machineCbModel == null) {
            machineCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllMachines()) {
                machineCbModel.addElement(ci);
            }
        }
        fromSiteCbModel = new DefaultComboBoxModel();
        boolean firstTime = false;
        if (toSiteCbModel == null) {
            toSiteCbModel = new DefaultComboBoxModel();
            firstTime = true;
        }
        for (ComboItem ci : XlendWorks.loadActiveSites()) {
            if (!ci.getValue().startsWith("--")) {
                if (firstTime) {
                    toSiteCbModel.addElement(ci);
                }
                fromSiteCbModel.addElement(ci);
            }
        }
        toSiteCbModel.addElement(new ComboItem(-1, "--Other--"));
        driverCbModel = new DefaultComboBoxModel();
        assistantCbModel = new DefaultComboBoxModel();
        firstTime = false;
        if (operatorCbModel == null) {
            operatorCbModel = new DefaultComboBoxModel();
            operatorCbModel.addElement(new ComboItem(0, "NO OPERATOR"));
            firstTime = true;
        }

        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            driverCbModel.addElement(ci);
            assistantCbModel.addElement(ci);
            if (firstTime) {
                operatorCbModel.addElement(ci);
            }
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            comboPanelWithLookupBtn(lowbedCB = new JComboBox(lowbedCbModel), lbLookupAction = new LowBedLookupAction(lowbedCB)),
            getGridPanel(dateSP = new SelectedDateSpinner(), 7),
            getGridPanel(comboPanelWithLookupBtn(fromSiteCB = new JComboBox(fromSiteCbModel), new SiteLookupAction(fromSiteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(assistantCB = new JComboBox(assistantCbModel), new EmployeeLookupAction(assistantCB)), 2),
            getGridPanel(new JComponent[]{
                establishRB = new JRadioButton("Establishing"),
                deEstablishRB = new JRadioButton("De-Establishing"),
                movingRB = new JRadioButton("Moving:"),
                exchangingRB = new JRadioButton("Exchanging:"),
                completeCB = new JCheckBox("Completed")
            })
        };
        ButtonGroup group = new ButtonGroup();
        group.add(establishRB);
        group.add(deEstablishRB);
        group.add(movingRB);
        group.add(exchangingRB);

        ActionListener switchAction = typeSwitchAction();

        establishRB.addActionListener(switchAction);
        deEstablishRB.addActionListener(switchAction);
        movingRB.addActionListener(switchAction);
        exchangingRB.addActionListener(switchAction);

        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

//        lowbedCB.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                syncDriverAndAssistant();
//            }
//        });

        add(detailsPanel());
    }

    public static ActionListener otherSiteAction(JTextField otherFld) {
        return new OtherFieldShowAction(otherFld);
    }

//    private void syncDriverAndAssistant() {
//        ComboItem itm = (ComboItem) lowbedCB.getSelectedItem();
//        if (itm == null) {
//            itm = (ComboItem) lowbedCbModel.getElementAt(0);
//        }
//        try {
//            Xlowbed lowbed = (Xlowbed) XlendWorks.getExchanger().loadDbObjectOnID(Xlowbed.class, itm.getId());
//            selectComboItem(driverCB, lowbed.getDriverId());
//            selectComboItem(assistantCB, lowbed.getAssistantId());
//        } catch (RemoteException ex) {
//            XlendWorks.log(ex);
//        }
//
//    }

    private JPanel detailsPanel() {
        Xtrip xtr = (Xtrip) getDbObject();
        detailInfoPanel = new JPanel(new CardLayout());
        detailInfoPanel.add(establishPanel = new EditTripEstablishingPanel(xtr), detailPanelLabels[0]);
        establishPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[0]));
        detailInfoPanel.add(deEstablishPanel = new EditTripDeEstablishingPanel(xtr), detailPanelLabels[1]);
        deEstablishPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[1]));
        detailInfoPanel.add(movingPanel = new EditTripMovinganel(xtr), detailPanelLabels[2]);
        movingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[2]));
        detailInfoPanel.add(exchangePanel = new EditTripExchangePanel(xtr), detailPanelLabels[3]);
        exchangePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[3]));
        subPanels = new EditSubPanel[]{
            establishPanel, deEstablishPanel, movingPanel, exchangePanel
        };
        return detailInfoPanel;
    }

    @Override
    public void loadData() {
//        Xlowbed 
        Xmachine xlb = null;
        if (xlowbed_id != null) {
            try {
                xlb = (Xmachine) XlendWorks.getExchanger().loadDbObjectOnID(Xmachine.class, xlowbed_id);
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
            lowbedCB.setEnabled(false);
            lbLookupAction.setEnabled(false);
            selectComboItem(lowbedCB, xlowbed_id);
        }
        Xtrip xtr = (Xtrip) getDbObject();
        if (xtr != null) {
            idField.setText(xtr.getXtripId().toString());

            selectComboItem(lowbedCB, xtr.getXlowbedId());
            if (xtr.getTripDate() != null) {
                java.util.Date dt = new java.util.Date(xtr.getTripDate().getTime());
                dateSP.setValue(dt);
            }
            if (xtr.getFromsiteId() != null) {
                RecordEditPanel.addSiteItem(fromSiteCbModel, xtr.getFromsiteId());
                selectComboItem(fromSiteCB, xtr.getFromsiteId());
            }

            if (xtr.getDriverId() != null) {
                selectComboItem(driverCB, xtr.getDriverId());
//            } else if (xlb != null) {
//                selectComboItem(driverCB, xlb.getDriverId());
            }
            if (xtr.getAssistantId() != null) {
                selectComboItem(assistantCB, xtr.getAssistantId());
//            } else if (xlb != null) {
//                selectComboItem(assistantCB, xlb.getAssistantId());
            }
            if (xtr.getTripType() != null) {
                switch (xtr.getTripType()) {
                    case 0:
                        establishRB.setSelected(true);
                        break;
                    case 1:
                        deEstablishRB.setSelected(true);
                        break;
                    case 2:
                        movingRB.setSelected(true);
                        break;
                    case 3:
                        exchangingRB.setSelected(true);
                        break;
                }
            } else {
                establishRB.setSelected(true);
            }
            completeCB.setSelected(xtr.getIsCopmplete() != null && xtr.getIsCopmplete() == 1);
            completeCB.setEnabled(!completeCB.isSelected());
            switchDetailPanel();
        } else {
            establishRB.setSelected(true);
//            syncDriverAndAssistant();
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean toAssign = false;
        if (completeCB.isEnabled() && completeCB.isSelected()) {
            toAssign = (GeneralFrame.yesNo("Attention!",
                    "Do you want to assign operator/machine pair to the site?") == JOptionPane.YES_OPTION);
            if (!toAssign) {
                return false;
            }
        }
        boolean isNew = false;
        Xtrip xtr = (Xtrip) getDbObject();
        if (xtr == null) {
            xtr = new Xtrip(null);
            xtr.setXtripId(0);
            isNew = true;
        }
        establishPanel.setDbObject(xtr);
        deEstablishPanel.setDbObject(xtr);
        movingPanel.setDbObject(xtr);
        exchangePanel.setDbObject(xtr);
        if (dateSP.getValue() != null) {
            java.util.Date dt = (java.util.Date) dateSP.getValue();
            xtr.setTripDate(new java.sql.Date(dt.getTime()));
        }
        xtr.setXlowbedId(getSelectedCbItem(lowbedCB));
        xtr.setAssistantId(getSelectedCbItem(assistantCB));
        xtr.setDriverId(getSelectedCbItem(driverCB));
        xtr.setFromsiteId(getSelectedCbItem(fromSiteCB));
        xtr.setTripType(deEstablishRB.isSelected() ? 1
                : movingRB.isSelected() ? 2 : exchangingRB.isSelected() ? 3 : 0);
        xtr.setIsCopmplete(completeCB.isSelected() ? 1 : 0);
        boolean ok = subPanels[xtr.getTripType()].save();
        if (toAssign) {
            try {
                java.sql.Date now = new java.sql.Date(Calendar.getInstance().getTimeInMillis());
                int machID = xtr.getMachineId() == null ? 0 : xtr.getMachineId().intValue();
                int opID = xtr.getOperatorId() == null ? 0 : xtr.getOperatorId().intValue();
                Xopmachassing previous = XlendWorks.findCurrentAssignment(machID, opID);
                if (previous == null) {
                    Xopmachassing previousOperatorsAssign = XlendWorks.findCurrentAssignment(0, xtr.getOperatorId());
                    if (previousOperatorsAssign != null) {
                        Xopmachassing assignPrevMachine = new Xopmachassing(null);
                        assignPrevMachine.setXopmachassingId(0);
                        assignPrevMachine.setNew(true);
                        assignPrevMachine.setXmachineId(previousOperatorsAssign.getXmachineId() == 0 ? null : previousOperatorsAssign.getXmachineId());
                        assignPrevMachine.setXemployeeId(null);
                        assignPrevMachine.setDateStart(now);
                        assignPrevMachine.setXsiteId(previousOperatorsAssign.getXsiteId());
                        XlendWorks.getExchanger().saveDbObject(assignPrevMachine);
//                        previousOperatorsAssign.setDateEnd(now);
//                        previousOperatorsAssign.setXemployeeId(previousOperatorsAssign.getXemployeeId() == 0 ? null : previousOperatorsAssign.getXemployeeId());
//                        previousOperatorsAssign.setXmachineId(previousOperatorsAssign.getXmachineId() == 0 ? null : previousOperatorsAssign.getXmachineId());
//                        DashBoard.getExchanger().saveDbObject(previousOperatorsAssign);
                        setEndTimeStamp(previousOperatorsAssign, now);
                    }
                    Xopmachassing previousMachineAssign = XlendWorks.findCurrentAssignment(xtr.getMachineId(), 0);
                    if (previousMachineAssign != null) {
                        Xemployee prevOperator = getOperator(previousMachineAssign.getXemployeeId());
                        if (prevOperator != null) {
                            new ChooseDepotForOperatorDialog(prevOperator);
                            Xopmachassing assignPrevOperator = new Xopmachassing(null);
                            assignPrevOperator.setXopmachassingId(0);
                            assignPrevOperator.setNew(true);
                            assignPrevOperator.setXemployeeId(prevOperator.getXemployeeId());
                            assignPrevOperator.setXmachineId(null);
                            assignPrevOperator.setDateStart(now);
                            assignPrevOperator.setXsiteId(ChooseDepotForOperatorDialog.getDepot_id());
                            XlendWorks.getExchanger().saveDbObject(assignPrevOperator);
                        }
//                        previousMachineAssign.setDateEnd(now);
//                        previousMachineAssign.setXemployeeId(previousMachineAssign.getXemployeeId() == 0 ? null : previousMachineAssign.getXemployeeId());
//                        previousMachineAssign.setXmachineId(previousMachineAssign.getXmachineId() == 0 ? null : previousMachineAssign.getXmachineId());
//                        DashBoard.getExchanger().saveDbObject(previousMachineAssign);
                        setEndTimeStamp(previousMachineAssign, now);
                    }
                } else {
//                    previous.setDateEnd(now);
//                    previous.setXemployeeId(previous.getXemployeeId() == 0 ? null : previous.getXemployeeId());
//                    previous.setXmachineId(previous.getXmachineId() == 0 ? null : previous.getXmachineId());
//                    DashBoard.getExchanger().saveDbObject(previous);
                    setEndTimeStamp(previous, now);
                }

                Xopmachassing assign = new Xopmachassing(null);
                assign.setXopmachassingId(0);
                assign.setNew(true);
                assign.setXsiteId(xtr.getTositeId());
                assign.setXmachineId(xtr.getMachineId());
                assign.setXemployeeId(xtr.getOperatorId());
                assign.setDateStart(now);
                XlendWorks.getExchanger().saveDbObject(assign);

            } catch (Exception ex) {
//                ex.pr
                ex.printStackTrace();
                XlendWorks.logAndShowMessage(ex);
                ok = false;
            }
        }
        return ok && saveDbRecord(xtr, isNew);
    }

    private void setEndTimeStamp(Xopmachassing previous, java.sql.Date now) throws Exception {
        previous.setDateEnd(now);
        previous.setXemployeeId(previous.getXemployeeId() == 0 ? null : previous.getXemployeeId());
        previous.setXmachineId(previous.getXmachineId() == 0 ? null : previous.getXmachineId());
        XlendWorks.getExchanger().saveDbObject(previous);
    }

    private ActionListener typeSwitchAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchDetailPanel();
            }
        };
    }

    private void switchDetailPanel() {
        int tripType = 0;
        CardLayout cl = (CardLayout) (detailInfoPanel.getLayout());
        if (deEstablishRB.isSelected()) {
            tripType = 1;
        } else if (movingRB.isSelected()) {
            tripType = 2;
        } else if (exchangingRB.isSelected()) {
            tripType = 3;
        }
        cl.show(detailInfoPanel, detailPanelLabels[tripType]);
    }

    private static Xemployee getOperator(Integer xemployeeId) throws RemoteException {
        return (Xemployee) XlendWorks.getExchanger().loadDbObjectOnID(Xemployee.class, xemployeeId);
    }

    public static void syncAssignedOperatorOnMachine(JComboBox machineCB, JComboBox operatorCB) throws RemoteException {
        ComboItem ci = (ComboItem) machineCB.getSelectedItem();
        if (ci != null) {
            DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xopmachassing.class, "xmachine_id="
                    + ci.getId() + " and xopmachassing_id=(select max(xopmachassing_id) "
                    + "from xopmachassing where xmachine_id=" + ci.getId() + ")", null);
            if (recs.length > 0) {
                Xopmachassing ass = (Xopmachassing) recs[0];
                selectComboItem(operatorCB, ass.getXemployeeId());
            } else {
                operatorCB.setSelectedIndex(0);
            }
        }
    }

    public static AbstractAction syncOperatorAction(final JComboBox machineCB, final JComboBox operatorCB) {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    syncAssignedOperatorOnMachine(machineCB, operatorCB);
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        };
    }
    @Override
    public void freeResources() {
        //TODO
    }
}
