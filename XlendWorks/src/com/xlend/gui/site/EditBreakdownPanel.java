/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xbreakconsume;
import com.xlend.orm.Xbreakdown;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author nick
 */
class EditBreakdownPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel machineCbModel;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel reportedToCbModel;
    private DefaultComboBoxModel reportedByCbModel;
    private DefaultComboBoxModel attendedByCbModel;
    private DefaultComboBoxModel vehicleByCbModel;
    private DefaultComboBoxModel operatorCbModel;
    private DefaultComboBoxModel purchasesCbModel;
    private JSpinner breakdownDateSP;
    private JComboBox machineCB;
    private JComboBox siteCB;
    private JComboBox reportedToCB;
    private JComboBox reportedByCB;
    private JComboBox attendedByCB;
    private JComboBox vehicleByCB;
    private JComboBox operatorCB;
    private JComboBox purchasesCB;
    private JSpinner repairDateSP;
    private JCheckBox problemRepairedCb;
//    private JTextField descrOfBreakdownField;
    private JCheckBox operatorFaultCb;
    private JSpinner km2siteSP;
    private JSpinner hrsOnJobSP;
    private JSpinner timeLeftSP;
    private JSpinner timeBackSP;
    private JCheckBox stayedOverCb;
    private JSpinner accomPriceSP;
//    private JTextField invoiceNumberField;
    private JSpinner amountSP;
    private static final String UNKNOWN = "--Unknown--";
    private BreakdownConsumesGrid purPanel;

    public EditBreakdownPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", //   "Breakdown Date:",
            "Machine/Truck/Other:",
            "Site",
            "Reported To:",
            "Reported By:",
            "Attended to by:",
            "With Vehicle:",
            "Repair Date:",
            "Problem Repaired?:",
            //            "Description of breakdown:",
            //            "Operator at fault?:",
            "Operator Clock Number:",
            //            "Purchases for repair:",
            "Kilometers to site (one way):",// "Hours on job:",
            "Time left:", //   "Time Back:",
            "Stayed Over?:",
            "Accomodation Price:", //            "Invoice Nr:"
        };
        machineCbModel = new DefaultComboBoxModel();
        vehicleByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
            vehicleByCbModel.addElement(ci);
        }
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadActiveSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        reportedToCbModel = new DefaultComboBoxModel();
        reportedByCbModel = new DefaultComboBoxModel();
        attendedByCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();

        reportedToCbModel.addElement(new ComboItem(0, UNKNOWN));
        reportedByCbModel.addElement(new ComboItem(0, UNKNOWN));
//        attendedByCbModel.addElement(new ComboItem(0, UNKNOWN));
//        operatorCbModel.addElement(new ComboItem(0, UNKNOWN));

        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            reportedToCbModel.addElement(ci);
            reportedByCbModel.addElement(ci);
            attendedByCbModel.addElement(ci);
            operatorCbModel.addElement(ci);
        }
        purchasesCbModel = new DefaultComboBoxModel(); // populated dinamicly depending on machineCB

        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Breakdown Date:", SwingConstants.RIGHT),
                breakdownDateSP = new SelectedDateSpinner()
            }),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            comboPanelWithLookupBtn(reportedToCB = new JComboBox(reportedToCbModel), new EmployeeLookupAction(reportedToCB)),
            comboPanelWithLookupBtn(reportedByCB = new JComboBox(reportedByCbModel), new EmployeeLookupAction(reportedByCB)),
            comboPanelWithLookupBtn(attendedByCB = new JComboBox(attendedByCbModel), new EmployeeLookupAction(attendedByCB)),
            comboPanelWithLookupBtn(vehicleByCB = new JComboBox(vehicleByCbModel), new MachineLookupAction(vehicleByCB, null)),
            getGridPanel(repairDateSP = new SelectedDateSpinner(), 3),
            getGridPanel(new JComponent[]{problemRepairedCb = new JCheckBox(), 
                new JLabel("Operator at fault?:", SwingConstants.RIGHT), operatorFaultCb = new JCheckBox()}),
            //            descrOfBreakdownField = new JTextField(40),
            //            getGridPanel(operatorFaultCb = new JCheckBox(), 3),
            comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)),
            //            comboPanelWithLookupBtn(purchasesCB = new JComboBox(purchasesCbModel), new PurchaseLookupAction(purchasesCB, null)),
            getGridPanel(new JComponent[]{
                km2siteSP = new SelectedNumberSpinner(0, 0, 10000, 1),
                new JLabel("Hours on job:", SwingConstants.RIGHT),
                hrsOnJobSP = new SelectedNumberSpinner(0, 0, 24, 1)
            }),
            getGridPanel(new JComponent[]{
                timeLeftSP = new SelectedNumberSpinner(0, 0, 12, 1),
                new JLabel("Time back:", SwingConstants.RIGHT),
                timeBackSP = new SelectedNumberSpinner(0, 0, 12, 1)
            }),
            getGridPanel(stayedOverCb = new JCheckBox(), 3),
            //            getGridPanel(accomPriceSP = new SelectedNumberSpinner(0, 0, 100000, 1), 3),
            getGridPanel(new JComponent[]{accomPriceSP = new SelectedNumberSpinner(0, 0, 100000, 1),//invoiceNumberField = new JTextField(),
                new JLabel("Amount:", SwingConstants.RIGHT),
                amountSP = new SelectedNumberSpinner(0.0, 0.0, 100000, .01)})
        };
        machineCB.addActionListener(getMachineCBaction());
        breakdownDateSP.setEditor(new JSpinner.DateEditor(breakdownDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(breakdownDateSP);
        repairDateSP.setEditor(new JSpinner.DateEditor(repairDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(repairDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
//        add(new JButton("!!!!"));
//        add(getPurchasesPanel());
        machineCB.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem mitm = (ComboItem) machineCB.getSelectedItem();
                BreakdownConsumesGrid.setXmachineID(mitm.getId());
//                EditBreakConsumeDialog.setXmachineID(mitm.getId());
            }
        });
    }

    private JComponent getPurchasesPanel(int xbreakdown_id) {
//        JComponent purPanel;
        String title = "Purchases";
        try {
            purPanel = new BreakdownConsumesGrid(DashBoard.getExchanger(),
                    Selects.SELECT_BREAKDOWNCONSUMES.replaceAll("#", "" + xbreakdown_id), 
                    getSelectedCbItem(machineCB), this);
            purPanel.setPreferredSize(new Dimension(purPanel.getPreferredSize().width, 200));
            purPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
//            JTextArea ta;
//            purPanel = new JScrollPane(ta = new JTextArea(ex.getMessage(), 5, 40),
//                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
//                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//            ta.setEditable(false);
//            ta.setWrapStyleWord(true);
//            ta.setLineWrap(true);
//            title = "Error!";
        }
        return purPanel;
    }

    @Override
    public void loadData() {
        Xbreakdown xbr = (Xbreakdown) getDbObject();
        int xbreakdownID = 0;
        if (xbr != null) {
            xbreakdownID = xbr.getXbreakdownId();
            idField.setText(xbr.getXbreakdownId().toString());
            if (xbr.getBreakdowndate() != null) {
                breakdownDateSP.setValue(new Date(xbr.getBreakdowndate().getTime()));
            }
            if (xbr.getXmachineId() != null) {
                selectComboItem(machineCB, xbr.getXmachineId());
            }
            if (xbr.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xbr.getXsiteId());
                selectComboItem(siteCB, xbr.getXsiteId());
            }
            if (xbr.getReportedbyId() != null) {
                selectComboItem(reportedByCB, xbr.getReportedbyId());
            }
            if (xbr.getReportedtoId() != null) {
                selectComboItem(reportedToCB, xbr.getReportedtoId());
            }
            if (xbr.getAttendedbyId() != null) {
                selectComboItem(attendedByCB, xbr.getAttendedbyId());
            }
            if (xbr.getVehicleId() != null) {
                selectComboItem(vehicleByCB, xbr.getVehicleId());
            }
            if (xbr.getRepairdate() != null) {
                repairDateSP.setValue(new Date(xbr.getRepairdate().getTime()));
            }
            problemRepairedCb.setSelected(xbr.getRepaired() != null && xbr.getRepaired() == 1);
//            descrOfBreakdownField.setText(xbr.getDescription());
            operatorFaultCb.setSelected(xbr.getOperatorfault() != null && xbr.getOperatorfault() == 1);
            if (xbr.getOperatorId() != null) {
                selectComboItem(operatorCB, xbr.getOperatorId());
            }
//            if (xbr.getXconsumeId() != null) {
//                selectComboItem(purchasesCB, xbr.getXconsumeId());
//            }
            km2siteSP.setValue(xbr.getKm2site1way() == null ? 0 : xbr.getKm2site1way());
            timeLeftSP.setValue(xbr.getTimeleft() == null ? 0 : xbr.getTimeleft());
            timeBackSP.setValue(xbr.getTimeback() == null ? 0 : xbr.getTimeback());
            stayedOverCb.setSelected(xbr.getStayedover() != null && xbr.getStayedover() == 1);
            hrsOnJobSP.setValue(xbr.getHoursonjob() == null ? 0 : xbr.getHoursonjob());
            accomPriceSP.setValue(xbr.getAccomprice() == null ? 0 : xbr.getAccomprice());
//            invoiceNumberField.setText(xbr.getInvoicenumber());
            amountSP.setValue(xbr.getAmount() == null ? 0 : xbr.getAmount());
        }
        add(getPurchasesPanel(xbreakdownID));
        syncPurchases();
    }

    @Override
    public boolean save() throws Exception {
        Xbreakdown xbr = (Xbreakdown) getDbObject();
        boolean isNew = false;
        if (xbr == null) {
            xbr = new Xbreakdown(null);
            xbr.setXbreakdownId(0);
            isNew = true;
        }
        xbr.setXmachineId(getSelectedCbItem(machineCB));
        xbr.setXsiteId(getSelectedCbItem(siteCB));
        Date dt = (Date) breakdownDateSP.getValue();
        if (dt != null) {
            xbr.setBreakdowndate(new java.sql.Date(dt.getTime()));
        }
        xbr.setReportedtoId(getSelectedCbItem(reportedToCB));
        xbr.setReportedbyId(getSelectedCbItem(reportedByCB));
        xbr.setAttendedbyId(getSelectedCbItem(attendedByCB));
        xbr.setVehicleId(getSelectedCbItem(vehicleByCB));
        xbr.setVehicleId(getSelectedCbItem(vehicleByCB));
        dt = (Date) repairDateSP.getValue();
        if (dt != null) {
            xbr.setRepairdate(new java.sql.Date(dt.getTime()));
        }
        xbr.setRepaired(problemRepairedCb.isSelected() ? 1 : 0);
        xbr.setOperatorfault(operatorFaultCb.isSelected() ? 1 : 0);
        xbr.setOperatorId(getSelectedCbItem(operatorCB));
        xbr.setKm2site1way((Integer) km2siteSP.getValue());
        xbr.setHoursonjob((Integer) hrsOnJobSP.getValue());
        xbr.setTimeback((Integer) timeBackSP.getValue());
        xbr.setTimeleft((Integer) timeLeftSP.getValue());
        xbr.setStayedover(stayedOverCb.isSelected() ? 1 : 0);
        xbr.setAccomprice((Integer) accomPriceSP.getValue());
        xbr.setAmount((Double) amountSP.getValue());
        boolean ok = saveDbRecord(xbr, isNew);
//        if (ok && BreakdownConsumesGrid.getNewPurchases() != null) {
//            xbr = (Xbreakdown) getDbObject();
//            try {
//                for (Xbreakconsume xbc : BreakdownConsumesGrid.getNewPurchases()) {
//                    xbc.setXbreakdownId(xbr.getXbreakdownId());
//                    xbc.setXbreakconsumeId(0);
//                    xbc.setNew(true);
//                    DashBoard.getExchanger().saveDbObject(xbc);
//                }
//            } catch (RemoteException ex) {
//                XlendWorks.logAndShowMessage(ex);
//                ok = false;
//            }
//        }
        return ok;
    }

    private AbstractAction getMachineCBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncPurchases();
            }
        };
    }

    private void syncPurchases() {
        ComboItem ci = (ComboItem) machineCB.getSelectedItem();
        purchasesCbModel.removeAllElements();
        if (ci != null) {
            PurchaseLookupAction.setXmachineID(ci.getId());
            for (ComboItem itm : XlendWorks.loadConsumesOnMachine(DashBoard.getExchanger(), ci.getId())) {
                purchasesCbModel.addElement(itm);
            }
        } else {
            PurchaseLookupAction.setXmachineID(null);
        }
    }
}
