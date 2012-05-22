package com.xlend.gui.work;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xhourcompare;
import com.xlend.orm.Xhourcompareday;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
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
            ocsSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            hmSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            drSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            stdSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            tshSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            tsnSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
            invnSpinners[i] = new SelectedNumberSpinner(0, 0, 24, 1);
        }

        siteCbModel = new DefaultComboBoxModel();
        operatorCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger(), false)) {
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
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Month/Year:", SwingConstants.RIGHT),
                monthYearSP = new SelectedDateSpinner()
            }, 16),
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
            }
        });
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        monthYearSP.setValue(cal.getTime());
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

    @Override
    public void loadData() {
        Xhourcompare xh = (Xhourcompare) getDbObject();
        if (xh != null) {
            idField.setText(xh.getXhourcompareId().toString());
            if (xh.getMonthYear() != null) {
                monthYearSP.setValue(new java.util.Date(xh.getMonthYear().getTime()));
            }
            if (xh.getXsiteId() != null) {
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
                int d=0;
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
                    itm.setOcs((Integer) ocsSpinners[i].getValue());
                    itm.setHm((Integer) hmSpinners[i].getValue());
                    itm.setDr((Integer) drSpinners[i].getValue());
                    itm.setStd((Integer) stdSpinners[i].getValue());
                    itm.setTsh((Integer) tshSpinners[i].getValue());
                    itm.setTsn((Integer) tsnSpinners[i].getValue());
                    itm.setInvn((Integer) invnSpinners[i].getValue());
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
