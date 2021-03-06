package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xbreakdown;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
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
    private JSpinner standingHoursSP;
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
            "Machine/Truck/Other:",//            "Site",
            "Reported To:",//            "Reported By:",
            "Attended By:",//            "With Vehicle:",
            "Repair Date:", //"Machine/Truck standing (hours):",
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
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
            vehicleByCbModel.addElement(ci);
        }
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadActiveSites()) {
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

        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            reportedToCbModel.addElement(ci);
            reportedByCbModel.addElement(ci);
            attendedByCbModel.addElement(ci);
            operatorCbModel.addElement(ci);
        }
        purchasesCbModel = new DefaultComboBoxModel(); // populated dinamicly depending on machineCB

        JComponent edits[] = new JComponent[]{
            
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JPanel(),
                getBorderPanel(new JComponent[]{
                    new JPanel(),
                    new JLabel("Breakdown Date:", SwingConstants.RIGHT),
                    breakdownDateSP = new SelectedDateSpinner()
                })
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
                new JLabel("Site:",SwingConstants.RIGHT),
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB))
            }),
//            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
//            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(reportedToCB = new JComboBox(reportedToCbModel), new EmployeeLookupAction(reportedToCB)),
                new JLabel("Reported by:",SwingConstants.RIGHT),
                comboPanelWithLookupBtn(reportedByCB = new JComboBox(reportedByCbModel), new EmployeeLookupAction(reportedByCB))
            }),
//            comboPanelWithLookupBtn(reportedToCB = new JComboBox(reportedToCbModel), new EmployeeLookupAction(reportedToCB)),
//            comboPanelWithLookupBtn(reportedByCB = new JComboBox(reportedByCbModel), new EmployeeLookupAction(reportedByCB)),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(attendedByCB = new JComboBox(attendedByCbModel), new EmployeeLookupAction(attendedByCB)),
                new JLabel("With vehicle:",SwingConstants.RIGHT),
                comboPanelWithLookupBtn(vehicleByCB = new JComboBox(vehicleByCbModel), new MachineLookupAction(vehicleByCB, null))
            }),
//            comboPanelWithLookupBtn(attendedByCB = new JComboBox(attendedByCbModel), new EmployeeLookupAction(attendedByCB)),
//            comboPanelWithLookupBtn(vehicleByCB = new JComboBox(vehicleByCbModel), new MachineLookupAction(vehicleByCB, null)),
            getGridPanel(new JComponent[]{
                repairDateSP = new SelectedDateSpinner(),
                new JLabel("Machine/Truck standing (hours):",SwingConstants.RIGHT),
                standingHoursSP = new SelectedNumberSpinner(.0, .0, 99.99, .1)
            }),
            getGridPanel(new JComponent[]{problemRepairedCb = new JCheckBox(),
                new JLabel("Operator at fault?:", SwingConstants.RIGHT), operatorFaultCb = new JCheckBox()}),
            //            descrOfBreakdownField = new JTextField(40),
            //            getGridPanel(operatorFaultCb = new JCheckBox(), 3),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)),3),
            //            comboPanelWithLookupBtn(purchasesCB = new JComboBox(purchasesCbModel), new PurchaseLookupAction(purchasesCB, null)),
            getGridPanel(new JComponent[]{
                km2siteSP = new SelectedNumberSpinner(.0, .0, 10000.0, .1),
                new JLabel("Hours on job:", SwingConstants.RIGHT),
                hrsOnJobSP = new SelectedNumberSpinner(.0, .0, 10000.0, .1)
            }),
            getGridPanel(new JComponent[]{
                timeLeftSP = new SelectedDateSpinner(),//new SelectedNumberSpinner(0, 0, 12, 1),
                new JLabel("Time back:", SwingConstants.RIGHT),
                timeBackSP = new SelectedDateSpinner()//new SelectedNumberSpinner(0, 0, 12, 1)
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

        timeLeftSP.setEditor(new JSpinner.DateEditor(timeLeftSP, "HH:mm"));
        Util.addFocusSelectAllAction(timeLeftSP);
        timeBackSP.setEditor(new JSpinner.DateEditor(timeBackSP, "HH:mm"));
        Util.addFocusSelectAllAction(timeBackSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        machineCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem mitm = (ComboItem) machineCB.getSelectedItem();
                BreakdownConsumesGrid.setXmachineID(mitm.getId());
            }
        });
    }

    private JComponent getPurchasesPanel(int xbreakdown_id) {
//        JComponent purPanel;
        String title = "Purchases";
        try {
            purPanel = new BreakdownConsumesGrid(XlendWorks.getExchanger(),
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
            Timestamp dt;
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
            km2siteSP.setValue(xbr.getKm2site1way() == null ? 0.0 : xbr.getKm2site1way());

            //timeLeftSP.setValue(xbr.getTimeleft() == null ? 0 : xbr.getTimeleft());
            //timeBackSP.setValue(xbr.getTimeback() == null ? 0 : xbr.getTimeback());
            if (xbr.getTimeleft() != null) {
                dt = xbr.getTimeleft();
                timeLeftSP.setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
            }
            if(xbr.getTimeback()!=null) {
                dt = xbr.getTimeback();
                timeBackSP.setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
            }
            if (xbr.getStandingHours()!=null) {
                standingHoursSP.setValue(xbr.getStandingHours());
            }
            stayedOverCb.setSelected(xbr.getStayedover() != null && xbr.getStayedover() == 1);
            hrsOnJobSP.setValue(xbr.getHoursonjob() == null ? 0.0 : xbr.getHoursonjob());
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
        xbr.setKm2site1way((Double) km2siteSP.getValue());
        xbr.setHoursonjob((Double) hrsOnJobSP.getValue());

        //xbr.setTimeback((Integer) timeBackSP.getValue());
        dt = (Date) timeBackSP.getValue();
        xbr.setTimeback(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));

        //xbr.setTimeleft((Integer) timeLeftSP.getValue());
        dt = (Date) timeLeftSP.getValue();
        xbr.setTimeleft(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));

        xbr.setStayedover(stayedOverCb.isSelected() ? 1 : 0);
        xbr.setAccomprice((Integer) accomPriceSP.getValue());
        xbr.setAmount((Double) amountSP.getValue());
        xbr.setStandingHours((Double)standingHoursSP.getValue());
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
            for (ComboItem itm : XlendWorks.loadConsumesOnMachine(ci.getId())) {
                purchasesCbModel.addElement(itm);
            }
        } else {
            PurchaseLookupAction.setXmachineID(null);
        }
    }

    @Override
    public void freeResources() {
        //TODO
    }
}
