package com.xlend.gui.hr;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.XlendWorks.XDate;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xwagesum;
import com.xlend.orm.Xwagesumitem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditWagesPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner weekendSp;
    private Xemployee[] employees;
    private JSpinner[] wagesSPs;
    private JSpinner[] hoursSPs;
    private JSpinner[] overSPs;
    private JSpinner[] dblSPs;
    private int[] xtimesheetIds;
//    private JComboBox timeSheetDatesSB;
//    private DefaultComboBoxModel timeSheetDatesCbModel;

    public EditWagesPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "Wage List ID:", //getDbObject() == null ? "Fill on Timesheet:" : 
            "Week Ending:"
        };
//        timeSheetDatesCbModel = new DefaultComboBoxModel(
//                XlendWorks.getNotFixedTimeSheetDates(DashBoard.getExchanger()));
        weekendSp = new SelectedDateSpinner();
        weekendSp.setEnabled(getDbObject() == null);
        weekendSp.setEditor(new JSpinner.DateEditor(weekendSp, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(weekendSp);

        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 10),
            getGridPanel(/*
             * getDbObject() == null ? (timeSheetDatesSB = new
             * JComboBox(timeSheetDatesCbModel)) :
             */weekendSp, 5)
        };
//        if (timeSheetDatesSB != null) {
//            timeSheetDatesSB.addActionListener(getDateCBaction());
//        }
        idField.setEnabled(false);
        labels = createLabelsArray(titles);
        organizePanels(titles, edits);
        JScrollPane sp = new JScrollPane(getGridPanel());
        add(sp, BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(sp.getPreferredSize().width + 50, 400));

        weekendSp.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                java.sql.Date xd = new java.sql.Date(((java.util.Date) weekendSp.getValue()).getTime());
                if (xd.getTime() == 0) {
                    resetNumData();
                } else {
                    int i = 0;
                    for (Xemployee empl : employees) {
                        Object[] vals = XlendWorks.getTimeSheetData(DashBoard.getExchanger(), xd, empl.getXemployeeId());
                        xtimesheetIds[i] = (Integer) vals[0];
                        hoursSPs[i].setValue(vals[1]);
                        overSPs[i].setValue(vals[2]);
                        dblSPs[i].setValue(vals[3]);
                        i++;
                    }
                }
            }
        });
    }

    protected void organizePanels(String[] titles, JComponent[] edits) {
        super.organizePanels(titles.length, edits.length);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length; i++) {
            lblPanel.add(labels[i]);
        }
        for (int i = 0; i < edits.length; i++) {
            editPanel.add(edits[i]);
        }
    }

    private JPanel getGridPanel() {
        String[] hdrs = new String[]{
            "Clock Nr.", "Name", "Weekly Wage", "Hours", "Overtime", "Doubletime"
        };
        JPanel wagesgridPanel = new JPanel(new BorderLayout());
        wagesgridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Empoyee List"));
        try {
            DbObject[] emplRecs = DashBoard.getExchanger().getDbObjects(Xemployee.class,
                    "wage_category in (2,3) and "
                    + "clock_num!='000' and "
                    + "upper(clock_num) not like 'S%' and "
                    + "coalesce(deceased,0)+coalesce(dismissed,0)+coalesce(absconded,0)+coalesce(resigned,0)=0",
                    "extractchars(clock_num),extractnum(clock_num)");
            JPanel uppanel = new JPanel(new GridLayout(emplRecs.length + 1, 6));
            for (String hdr : hdrs) {
                uppanel.add(new JLabel(hdr));
            }
            employees = new Xemployee[emplRecs.length];
            wagesSPs = new JSpinner[emplRecs.length];
            hoursSPs = new JSpinner[emplRecs.length];
            overSPs = new JSpinner[emplRecs.length];
            dblSPs = new JSpinner[emplRecs.length];
            xtimesheetIds = new int[emplRecs.length];

            for (int i = 0; i < emplRecs.length; i++) {
                employees[i] = (Xemployee) emplRecs[i];

                wagesSPs[i] = new SelectedNumberSpinner(0, 0, 10000, 1.0);
                hoursSPs[i] = new SelectedNumberSpinner(0, 0, 168, 0.5);
                overSPs[i] = new SelectedNumberSpinner(0, 0, 168, 0.5);
                dblSPs[i] = new SelectedNumberSpinner(0, 0, 168, 0.5);

//                hoursSPs[i].setEnabled(false);
//                overSPs[i].setEnabled(false);
//                dblSPs[i].setEnabled(false);

                JLabel l = new JLabel(employees[i].getClockNum());
                l.setBorder(BorderFactory.createEtchedBorder());
                uppanel.add(l);
                l = new JLabel(employees[i].getFirstName().substring(0, 1) + "." + employees[i].getSurName());
                l.setBorder(BorderFactory.createEtchedBorder());
                uppanel.add(l);
                uppanel.add(wagesSPs[i]);
                uppanel.add(hoursSPs[i]);
                uppanel.add(overSPs[i]);
                uppanel.add(dblSPs[i]);
            }
            wagesgridPanel.add(uppanel, BorderLayout.NORTH);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }

        return wagesgridPanel;
    }

    @Override
    public void loadData() {
        Xwagesum xs = (Xwagesum) getDbObject();
        if (xs != null) {
            weekendSp.setValue(new java.util.Date(xs.getWeekend().getTime()));
            idField.setText(xs.getXwagesumId().toString());
            for (int i = 0; i < employees.length; i++) {
                DbObject[] obs;
                try {
                    obs = DashBoard.getExchanger().getDbObjects(Xwagesumitem.class,
                            "xwagesum_id=" + xs.getXwagesumId()
                            + " and xemployee_id=" + employees[i].getXemployeeId(), null);
                    if (obs.length > 0) {
                        Xwagesumitem itm = (Xwagesumitem) obs[0];
                        Double w = new Double(itm.getWeeklywage());
                        wagesSPs[i].setValue(w);
                        hoursSPs[i].setValue(itm.getNormaltime());
                        overSPs[i].setValue(itm.getOvertime());
                        dblSPs[i].setValue(itm.getDoubletime());
                    }
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xwagesum xs = (Xwagesum) getDbObject();
        boolean isNew = false;
        if (xs == null) {
            xs = new Xwagesum(null);
            xs.setXwagesumId(0);
            isNew = true;
//            XDate dt = (XDate) timeSheetDatesSB.getSelectedItem();
            java.util.Date dt = (java.util.Date) weekendSp.getValue();
            xs.setWeekend(new java.sql.Date(dt.getTime()));
        }
        try {
            if (isNew) {
                xs.setNew(isNew);
                xs = (Xwagesum) DashBoard.getExchanger().saveDbObject(xs);
                setDbObject(xs);
            }
            for (int i = 0; i < employees.length; i++) {
                Xwagesumitem itm = null;
                if (isNew) {
                    itm = new Xwagesumitem(null);
                    itm.setXwagesumitemId(0);
                    itm.setNew(true);
                } else {
                    DbObject[] obs = DashBoard.getExchanger().getDbObjects(Xwagesumitem.class,
                            "xwagesum_id=" + xs.getXwagesumId()
                            + " and xemployee_id=" + employees[i].getXemployeeId(), null);
                    if (obs.length > 0) {
                        itm = (Xwagesumitem) obs[0];
                    } else {
                        itm = new Xwagesumitem(null);
                        itm.setXwagesumitemId(0);
                        itm.setNew(true);
                    }
                }
                itm.setXwagesumId(xs.getXwagesumId());
                itm.setXemployeeId(employees[i].getXemployeeId());
                itm.setXtimesheetId(xtimesheetIds[i]);
                Double w = (Double) wagesSPs[i].getValue();
                itm.setWeeklywage(w.intValue());
                itm.setNormaltime((Double) hoursSPs[i].getValue());
                itm.setOvertime((Double) overSPs[i].getValue());
                itm.setDoubletime((Double) dblSPs[i].getValue());
                itm = (Xwagesumitem) DashBoard.getExchanger().saveDbObject(itm);
            }
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

//    private ActionListener getDateCBaction() {
//        return new AbstractAction() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                java.sql.Date xd = (java.sql.Date) timeSheetDatesSB.getSelectedItem();
//                if (xd.getTime() == 0) {
//                    resetNumData();
//                } else {
//                    int i = 0;
//                    for (Xemployee empl : employees) {
//                        Object[] vals = XlendWorks.getTimeSheetData(DashBoard.getExchanger(), xd, empl.getXemployeeId());
//                        xtimesheetIds[i] = (Integer) vals[0];
//                        hoursSPs[i].setValue(vals[1]);
//                        overSPs[i].setValue(vals[2]);
//                        dblSPs[i].setValue(vals[3]);
//                        i++;
//                    }
//                }
//            }
//        };
//    }
    private void resetNumData(JSpinner[] sps) {
        for (JSpinner sp : sps) {
            sp.setValue(0.0);
        }
    }

    private void resetNumData() {
        resetNumData(wagesSPs);
        resetNumData(hoursSPs);
        resetNumData(overSPs);
        resetNumData(dblSPs);
    }
}
