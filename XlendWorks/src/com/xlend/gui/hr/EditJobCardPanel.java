package com.xlend.gui.hr;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.orm.Xjobcard;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditJobCardPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP, weekendSP;
    private DefaultComboBoxModel employeeCbModel;
    private JComboBox employeeCB;
    private DefaultComboBoxModel[] machineCbModels;
    private JComboBox[] machineCBs;
    private JTextArea[] commentsTAs;
    private String[] days;
    private JLabel[] dowsLbls;
    private static final int ARRLEN = 28;

    public EditJobCardPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {

        days = new String[]{
            "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturay"
        };
        String titles[] = new String[]{
            "ID:",
            "Date:", //"Name"
            "Week Ending:"
        };
        employeeCbModel = new DefaultComboBoxModel();
        machineCbModels = new DefaultComboBoxModel[ARRLEN];
        machineCBs = new JComboBox[ARRLEN];
        commentsTAs = new JTextArea[ARRLEN];
        dowsLbls = new JLabel[ARRLEN];
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            employeeCbModel.addElement(ci);
        }
        for (int i = 0; i < ARRLEN; i++) {
            machineCbModels[i] = new DefaultComboBoxModel();
            commentsTAs[i] = new JTextArea();
        }
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            for (int i = 0; i < ARRLEN; i++) {
                machineCbModels[i].addElement(ci);
            }
        }
        for (int i = 0; i < ARRLEN; i++) {
            machineCBs[i] = new JComboBox(machineCbModels[i]);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 6),
            getBorderPanel(new JComponent[]{
                dateSP = new SelectedDateSpinner(),
                new JPanel(),
                comboPanelWithLookupBtn(employeeCB = new JComboBox(employeeCbModel), new EmployeeLookupAction(employeeCB)),}),
            getGridPanel(weekendSP = new SelectedDateSpinner(), 6)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        weekendSP.setEditor(new JSpinner.DateEditor(weekendSP, "dd/MM/yyyy"));
        weekendSP.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                shiftDayLabels();
            }
        });
        Util.addFocusSelectAllAction(weekendSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel centerPanel = new JPanel(new GridLayout(8, 1, 5, 5));
        centerPanel.add(getGridPanel(new JComponent[]{
                    new JPanel(),
                    new JLabel("7:00 - 9:00", SwingConstants.CENTER),
                    new JLabel("9:15 - 12:30", SwingConstants.CENTER),
                    new JLabel("13:00 - 17:00", SwingConstants.CENTER),
                    new JLabel("other", SwingConstants.CENTER)
                }));
        for (int i = 0; i < 7; i++) {
            centerPanel.add(dayLine(i * 4));
        }
        add(centerPanel);
        shiftDayLabels();
    }

    private JComponent dayLine(int offset) {
        JPanel[] panels = new JPanel[4];
        for (int i = 0; i < 4; i++) {
            panels[i] = new JPanel(new BorderLayout());
            panels[i].add(new JScrollPane(commentsTAs[offset] = new JTextArea(3, 10)));
            panels[i].add(getBorderPanel(new JComponent[]{
                        new JLabel("machine:"),
                        comboPanelWithLookupBtn(machineCBs[offset], new MachineLookupAction(machineCBs[offset], null))
                    }), BorderLayout.SOUTH);
            offset++;
        }
        return getGridPanel(new JComponent[]{
                    dowsLbls[(offset - 1) / 4] = new JLabel(days[(offset - 1) / 4], SwingConstants.RIGHT),
                    panels[0], panels[1], panels[2], panels[3]
                });
    }

    private void shiftDayLabels() {
        Date weekend = (Date) weekendSP.getValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekend);
        Date dates[] = new Date[7];
        dates[6] = calendar.getTime();
        int d;
        for (d = 5; d >= 0; d--) {
            calendar.add(Calendar.DATE, -1);
            dates[d] = calendar.getTime();
        }

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            d = dates[i].getDay();
            dowsLbls[i].setText(days[d] + "," + df.format(dates[i]));
        }
    }

    @Override
    public void loadData() {
        Xjobcard xj = (Xjobcard) getDbObject();
        if (xj != null) {
            Integer machineIDs[] = new Integer[]{
                xj.getMachineId1Day1(),
                xj.getMachineId2Day1(),
                xj.getMachineId3Day1(),
                xj.getMachineId4Day1(),
                xj.getMachineId1Day2(),
                xj.getMachineId2Day2(),
                xj.getMachineId3Day2(),
                xj.getMachineId4Day2(),
                xj.getMachineId1Day3(),
                xj.getMachineId2Day3(),
                xj.getMachineId3Day3(),
                xj.getMachineId4Day3(),
                xj.getMachineId1Day4(),
                xj.getMachineId2Day4(),
                xj.getMachineId3Day4(),
                xj.getMachineId4Day4(),
                xj.getMachineId1Day5(),
                xj.getMachineId2Day5(),
                xj.getMachineId3Day5(),
                xj.getMachineId4Day5(),
                xj.getMachineId1Day6(),
                xj.getMachineId2Day6(),
                xj.getMachineId3Day6(),
                xj.getMachineId4Day6(),
                xj.getMachineId1Day7(),
                xj.getMachineId2Day7(),
                xj.getMachineId3Day7(),
                xj.getMachineId4Day7()
            };
            String comments[] = new String[]{
                xj.getJob1Day1(),
                xj.getJob2Day1(),
                xj.getJob3Day1(),
                xj.getJob4Day1(),
                xj.getJob1Day2(),
                xj.getJob2Day2(),
                xj.getJob3Day2(),
                xj.getJob4Day2(),
                xj.getJob1Day3(),
                xj.getJob2Day3(),
                xj.getJob3Day3(),
                xj.getJob4Day3(),
                xj.getJob1Day4(),
                xj.getJob2Day4(),
                xj.getJob3Day4(),
                xj.getJob4Day4(),
                xj.getJob1Day5(),
                xj.getJob2Day5(),
                xj.getJob3Day5(),
                xj.getJob4Day5(),
                xj.getJob1Day6(),
                xj.getJob2Day6(),
                xj.getJob3Day6(),
                xj.getJob4Day6(),
                xj.getJob1Day7(),
                xj.getJob2Day7(),
                xj.getJob3Day7(),
                xj.getJob4Day7()
            };
            idField.setText(xj.getXjobcardId().toString());
            if (xj.getCarddate() != null) {
                dateSP.setValue(new java.util.Date(xj.getCarddate().getTime()));
            }
            if (xj.getWeekEnding() != null) {
                weekendSP.setValue(new java.util.Date(xj.getWeekEnding().getTime()));
            }
            if (xj.getXemployeeId() != null) {
                selectComboItem(employeeCB, xj.getXemployeeId());
            }
            for (int i = 0; i < ARRLEN; i++) {
                selectComboItem(machineCBs[i], machineIDs[i]);
                commentsTAs[i].setText(comments[i]);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xjobcard xj = (Xjobcard) getDbObject();
        if (xj==null) {
            isNew = true;
            xj = new Xjobcard(null);
            xj.setXjobcardId(0);
        }
        xj.setXemployeeId(getSelectedCbItem(employeeCB));
        Date dt = (Date) weekendSP.getValue();
        if (dt != null) {
            xj.setWeekEnding(new java.sql.Date(dt.getTime()));
        }
        dt = (Date) dateSP.getValue();
        if (dt != null) {
            xj.setCarddate(new java.sql.Date(dt.getTime()));
        }
        int i=0;
        xj.setMachineId1Day1(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day1(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day1(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day1(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day2(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day2(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day2(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day2(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day3(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day3(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day3(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day3(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day4(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day4(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day4(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day4(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day5(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day5(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day5(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day5(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day6(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day6(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day6(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day6(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId1Day7(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId2Day7(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId3Day7(getSelectedCbItem(machineCBs[i++]));
        xj.setMachineId4Day7(getSelectedCbItem(machineCBs[i++]));
        i = 0;
        xj.setJob1Day1(commentsTAs[i++].getText());
        xj.setJob2Day1(commentsTAs[i++].getText());
        xj.setJob3Day1(commentsTAs[i++].getText());
        xj.setJob4Day1(commentsTAs[i++].getText());
        xj.setJob1Day2(commentsTAs[i++].getText());
        xj.setJob2Day2(commentsTAs[i++].getText());
        xj.setJob3Day2(commentsTAs[i++].getText());
        xj.setJob4Day2(commentsTAs[i++].getText());
        xj.setJob1Day3(commentsTAs[i++].getText());
        xj.setJob2Day3(commentsTAs[i++].getText());
        xj.setJob3Day3(commentsTAs[i++].getText());
        xj.setJob4Day3(commentsTAs[i++].getText());
        xj.setJob1Day4(commentsTAs[i++].getText());
        xj.setJob2Day4(commentsTAs[i++].getText());
        xj.setJob3Day4(commentsTAs[i++].getText());
        xj.setJob4Day4(commentsTAs[i++].getText());
        xj.setJob1Day5(commentsTAs[i++].getText());
        xj.setJob2Day5(commentsTAs[i++].getText());
        xj.setJob3Day5(commentsTAs[i++].getText());
        xj.setJob4Day5(commentsTAs[i++].getText());
        xj.setJob1Day6(commentsTAs[i++].getText());
        xj.setJob2Day6(commentsTAs[i++].getText());
        xj.setJob3Day6(commentsTAs[i++].getText());
        xj.setJob4Day6(commentsTAs[i++].getText());
        xj.setJob1Day7(commentsTAs[i++].getText());
        xj.setJob2Day7(commentsTAs[i++].getText());
        xj.setJob3Day7(commentsTAs[i++].getText());
        xj.setJob4Day7(commentsTAs[i++].getText());
        return saveDbRecord(xj, isNew);
    }
}
