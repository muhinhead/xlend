package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.hr.TimeSheetsGrid;
import com.xlend.gui.site.OperatorClockSheetGrid;
import com.xlend.gui.site.SiteDiaryGrid;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xhourcompare;
import com.xlend.orm.Xhourcompareday;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.PopupDialog;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
class EditHourComparePanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner monthYearSP;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel operatorCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox siteCB, operatorCB, machineCB;
    private JSpinner[] ocsSpinners;
    private JSpinner[] hmSpinners;
    private JSpinner[] drSpinners;
    private JSpinner[] stdSpinners;
    private JSpinner[] tshSpinners;
    private JSpinner[] tsnSpinners;
    private JSpinner[] invnSpinners;
    private JSpinner[][] grid;
    private JLabel[] dowLabels;
    private JLabel[] dayLabels;
    private String[] dows;

    private class SpecSiteDiaryGrid extends SiteDiaryGrid {

        public SpecSiteDiaryGrid(IMessageSender exchanger,
                Integer operatorID, Integer siteID, Integer machineID, int year, int month) throws RemoteException {
            super(exchanger, Selects.SELECT_FROM_SITE_DIARY
                    + " where xsite_id=" + siteID + " and xsitediary_id in "
                    + "(select xsitediary_id from xsitediarypart where operator_id=" + operatorID + " and xmachine_id=" + machineID
                    + " and year(partdate)=" + year + " and month(partdate)=" + month + ")");
        }
    }

    public EditHourComparePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        dows = new String[]{
            " Sun ", " Mon ", " Tue ", " Wed ", " Thu ", " Fri ", " Sat "
        };
        String titles[] = new String[]{
            "ID:",//  "Month/Year:",
            "Site:",
            "Operator:",
            "Machine/Truck:",
            "",
            "",
            "OCS:",
            "HM:",
            "DR:",
            "STD:",
            "TSH:",
            "TSN:",
            "INVN:"
        };
        ocsSpinners = new JSpinner[31];
        hmSpinners = new JSpinner[31];
        drSpinners = new JSpinner[31];
        stdSpinners = new JSpinner[31];
        tshSpinners = new JSpinner[31];
        tsnSpinners = new JSpinner[31];
        invnSpinners = new JSpinner[31];
        grid = new JSpinner[][]{
            ocsSpinners, hmSpinners, drSpinners, stdSpinners, tshSpinners, tsnSpinners, invnSpinners
        };
        for (int i = 0; i < 31; i++) {
            ocsSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
            hmSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 99.0, .5);
            drSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
            stdSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
            tshSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
            tsnSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
            invnSpinners[i] = new SelectedNumberSpinner(0.0, 0.0, 24.0, .5);
        }

        siteCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadActiveSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        dowLabels = new JLabel[31];
        dayLabels = new JLabel[31];
        int n = 0;
        for (int i = 0; i < 31; i++) {
            dayLabels[i] = new JLabel("" + (i + 1), SwingConstants.CENTER);
            dowLabels[i] = new JLabel(dows[n++]);
            n = n >= 7 ? 0 : n;
        }

        JComponent[] edits = new JComponent[]{
            getBorderPanel(new JComponent[]{getGridPanel(new JComponent[]{
                    idField = new JTextField(),
                    new JLabel("Month/Year:", SwingConstants.RIGHT),
                    monthYearSP = new SelectedDateSpinner()
                }, 16), new JPanel(), new JButton(getAutoFillAction())}),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)), 4),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)), 4),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)), 6),
            getGridPanel(dowLabels),
            getGridPanel(dayLabels),
            getGridPanel(ocsSpinners),
            getGridPanel(hmSpinners),
            getGridPanel(drSpinners),
            getGridPanel(stdSpinners),
            getGridPanel(tshSpinners),
            getGridPanel(tsnSpinners),
            getGridPanel(invnSpinners)
        };
        monthYearSP.setEditor(new JSpinner.DateEditor(monthYearSP, "MMM/yyyy"));
        Util.addFocusSelectAllAction(monthYearSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        monthYearSP.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                adjustDaysColumns();
//                updateSiteDiaryLine();
//                updateTimeSheetLine();
            }
        });
//        siteCB.addActionListener(syncHourLinesLineAction());
//        operatorCB.addActionListener(syncHourLinesLineAction());
//        machineCB.addActionListener(syncHourLinesLineAction());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        monthYearSP.setValue(cal.getTime());
        JPanel downBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        downBtnPanel.add(new JLabel("Details:"));
        downBtnPanel.add(new JButton(new AbstractAction("OCS") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    OperatorClockSheetGrid grd = new OperatorClockSheetGrid(DashBoard.getExchanger());
                    new RefGridDialog(null, "Operator Clock Sheet", grd);
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        }));

        downBtnPanel.add(new JButton(new AbstractAction("STD") {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date dt = (java.util.Date) monthYearSP.getValue();
                Calendar cal = Calendar.getInstance();
                cal.setTime(dt);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH) + 1;
                try {
                    SpecSiteDiaryGrid grd = new SpecSiteDiaryGrid(DashBoard.getExchanger(),
                            getSelectedCbItem(operatorCB), getSelectedCbItem(siteCB),
                            getSelectedCbItem(machineCB), year, month);
                    new RefGridDialog(null, "Site Diary", grd);
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        }));
        downBtnPanel.add(new JButton(new AbstractAction("TSH") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String select = Selects.SELECT_TIMESHEETS4EMPLOYEE.replaceAll(
                            "where ", "where t.xsite_id=" + getSelectedCbItem(siteCB) + " and ");
                    select = select.replaceAll("#", getSelectedCbItem(operatorCB).toString());
                    TimeSheetsGrid grd = new TimeSheetsGrid(DashBoard.getExchanger(), select, false);
                    new RefGridDialog(null, "Timesheet", grd);
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        }));
        add(downBtnPanel, BorderLayout.SOUTH);
    }

    private AbstractAction getAutoFillAction() {
        return new AbstractAction("Autofill") {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(null,
                        "Manual input will be overwritten. Are you sure?", "Warning!",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) 
                {
                    updateSiteDiaryLine();
                    updateTimeSheetLine();
                    updateOperatorClockSheetLine();
                }
            }
        };
    }

    private int daysInMonth() {
        int days = 0;
        java.util.Date dt = (java.util.Date) monthYearSP.getValue();
        if (dt != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
            int dow = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            for (int i = 0; i < 31; i++) {
                dowLabels[i].setText(dows[dow++]);
                dow = dow > 6 ? 0 : dow;
            }
        }
        return days;
    }

    private void adjustDaysColumns() {
        int maxDays = daysInMonth();
        for (JLabel l : dayLabels) {
            l.setVisible(true);
        }
        for (JLabel l : dowLabels) {
            l.setVisible(true);
        }
        for (JSpinner[] sparr : grid) {
            for (JSpinner sp : sparr) {
                sp.setVisible(true);
            }
            for (int i = maxDays; i < 31; i++) {
                sparr[i].setVisible(false);
            }
        }

        for (int i = maxDays; i < 31; i++) {
            dayLabels[i].setVisible(false);
            dowLabels[i].setVisible(false);
        }
    }

    //Sync data with xsitediarypart table
    private void updateSiteDiaryLine() {
        Date dt = (Date) monthYearSP.getValue();
        if (dt != null) {
            ComboItem[] itms = XlendWorks.loadSiteDiaryHrsWorked(
                    dt,
                    getSelectedCbItem(siteCB),
                    getSelectedCbItem(operatorCB),
                    getSelectedCbItem(machineCB));
            setValues2Spinners(stdSpinners, itms);
        }
    }

    //Sync with xtimesheet/xwage tables
    private void updateTimeSheetLine() {
        Date dt = (Date) monthYearSP.getValue();
        if (dt != null) {
            ComboItem[] itms = XlendWorks.loadTimeSheetHrsWorked(dt,
                    getSelectedCbItem(siteCB),
                    getSelectedCbItem(operatorCB));
            setValues2Spinners(tshSpinners, itms);
        }
    }

    //Sync with xopclocksheet table
    private void updateOperatorClockSheetLine() {
        Date dt = (Date) monthYearSP.getValue();
        if (dt != null) {
            ComboItem[][] ciArr = XlendWorks.loadOperatorHourMeterAndOcs(
                    dt,
                    getSelectedCbItem(siteCB),
                    getSelectedCbItem(operatorCB),
                    getSelectedCbItem(machineCB));
            setValues2Spinners(hmSpinners, ciArr[0]);
            setValues2Spinners(ocsSpinners, ciArr[1]);
        }
    }

    private static void setValues2Spinners(JSpinner[] spinners, ComboItem[] itms) {
        for (JSpinner sp : spinners) {
            sp.setValue(new Double(0.0));
        }
        for (ComboItem ci : itms) {
            try {
                spinners[ci.getId()].setValue(new Double(ci.getValue()));
            } catch (NumberFormatException ne) {
            }
        }
    }

    
    @Override
    public void loadData() {
        Xhourcompare xh = (Xhourcompare) getDbObject();
        if (xh != null) {
            idField.setText(xh.getXhourcompareId().toString());
            if (xh.getMonthYear() != null) {
                monthYearSP.setValue(new java.util.Date(xh.getMonthYear().getTime()));
            }
            if (xh.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xh.getXsiteId());
                selectComboItem(siteCB, xh.getXsiteId());
            }
            if (xh.getXmachineId() != null) {
                selectComboItem(machineCB, xh.getXmachineId());
            }
            if (xh.getOperatorId() != null) {
                selectComboItem(operatorCB, xh.getOperatorId());
            }
            try {
                DbObject[] oldItms = DashBoard.getExchanger().getDbObjects(
                        Xhourcompareday.class,
                        "xhourcompare_id=" + xh.getXhourcompareId(), "day_no");
                int d = 0;
                for (DbObject ob : oldItms) {
                    Xhourcompareday itm = (Xhourcompareday) ob;
                    ocsSpinners[d].setValue(itm.getOcs());
                    hmSpinners[d].setValue(itm.getHm());
                    drSpinners[d].setValue(itm.getDr());
                    stdSpinners[d].setValue(itm.getStd());
                    tshSpinners[d].setValue(itm.getTsh());
                    tsnSpinners[d].setValue(itm.getTsn());
                    invnSpinners[d].setValue(itm.getInvn());
                    d++;
                }
            } catch (RemoteException ex) {
                GeneralFrame.errMessageBox("Error:", "Server failure\nCheck your logs please");
                XlendWorks.log(ex);
            }
//            updateSiteDiaryLine();
//            updateTimeSheetLine();
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xhourcompare xh = (Xhourcompare) getDbObject();
        if (xh == null) {
            xh = new Xhourcompare(null);
            xh.setXhourcompareId(0);
            isNew = true;
        }
        Date dt = (Date) monthYearSP.getValue();
        if (dt != null) {
            xh.setMonthYear(new java.sql.Date(dt.getTime()));
        }
        xh.setXsiteId(getSelectedCbItem(siteCB));
        xh.setOperatorId(getSelectedCbItem(operatorCB));
        xh.setXmachineId(getSelectedCbItem(machineCB));

        return saveDbRecord(xh, isNew) && saveCompareItems();
    }

    private boolean saveCompareItems() {
        Xhourcompare xh = (Xhourcompare) getDbObject();
        if (xh != null) {
            try {
                DbObject[] oldItms = DashBoard.getExchanger().getDbObjects(
                        Xhourcompareday.class,
                        "xhourcompare_id=" + xh.getXhourcompareId(),
                        "day_no");
                int days = daysInMonth();
                for (int i = oldItms.length - 1; i >= days; i--) {
                    DashBoard.getExchanger().deleteObject(oldItms[i]);
                }
                for (int i = 0; i < days; i++) {
                    Xhourcompareday itm = null;
                    boolean isNew = (i >= oldItms.length);
                    if (!isNew) {
                        itm = (Xhourcompareday) oldItms[i];
                    } else {
                        itm = new Xhourcompareday(null);
                        itm.setXhourcomparedayId(0);
                        itm.setXhourcompareId(xh.getXhourcompareId());
                    }
                    itm.setNew(isNew);
                    itm.setDayNo(i + 1);
                    itm.setOcs((Double) ocsSpinners[i].getValue());
                    itm.setHm((Double) hmSpinners[i].getValue());
                    itm.setDr((Double) drSpinners[i].getValue());
                    itm.setStd((Double) stdSpinners[i].getValue());
                    itm.setTsh((Double) tshSpinners[i].getValue());
                    itm.setTsn((Double) tsnSpinners[i].getValue());
                    itm.setInvn((Double) invnSpinners[i].getValue());
                    DashBoard.getExchanger().saveDbObject(itm);
                }
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", "Server failure\nCheck your logs please");
                XlendWorks.log(ex);
            }
        }
        return false;
    }
}
