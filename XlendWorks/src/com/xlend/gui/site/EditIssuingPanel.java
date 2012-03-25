package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xissuing;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditIssuingPanel extends RecordEditPanel {

    private class MoreCBchangeListener implements ChangeListener {

        private int idx;

        public MoreCBchangeListener(int id) {
            idx = id;
        }

        @Override
        public void stateChanged(ChangeEvent e) {
            JCheckBox cb = (JCheckBox) e.getSource();
            comboPanes[idx].setVisible(cb.isSelected());
            timesSPs[idx * 2 + 1].setVisible(cb.isSelected());
            hourSPs[idx * 2 + 1].setVisible(cb.isSelected());
            literSPs[idx * 2 + 1].setVisible(cb.isSelected());
        }
    }
    private JTextField idField;
    private DefaultComboBoxModel operatorCbModel;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JSpinner dateSP;
    private JComboBox siteCB;
    private JComboBox machineCB;
    private JComboBox operatorCB;
    private JSpinner[] daysSPs;
    private JSpinner[] timesSPs;
    private JSpinner[] hourSPs;
    private JSpinner[] literSPs;
    private DefaultComboBoxModel[] issuedByCbModels;
    private JComboBox[] issuedByCBs;
    private JPanel[] comboPanes;
    private JCheckBox[] moreCBs;
    private static String[] gridHeaders = new String[]{
        "Date", "Time", "Hours", "Liters", "Issued By"
    };
    private static final String[] days = new String[]{
        "Monday:", "Tuesday:", "Wednesday:",
        "Thursday:", "Friday:", "Saturday:", "Sunday:"
    };
    private JLabel[] daysLabels;

    public EditIssuingPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        daysLabels = new JLabel[7];
        for (int i = 0; i < 7; i++) {
            daysLabels[i] = new JLabel(days[i], SwingConstants.RIGHT);
        }
        daysSPs = new JSpinner[7];
        timesSPs = new JSpinner[14];
        hourSPs = new JSpinner[14];
        literSPs = new JSpinner[14];
        issuedByCBs = new JComboBox[14];
        comboPanes = new JPanel[7];
        moreCBs = new JCheckBox[7];
        String[] titles = new String[]{
            "ID:",
            "Date:",
            "Operator:",
            "Machine/Truck:",
            "Site:"
        };
        operatorCbModel = new DefaultComboBoxModel();
        siteCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        issuedByCbModels = new DefaultComboBoxModel[14];
        for (int i = 0; i < 14; i++) {
            issuedByCbModels[i] = new DefaultComboBoxModel();
        }
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
            for (int i = 0; i < 14; i++) {
                issuedByCbModels[i].addElement(ci);
            }
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 8),
            getGridPanel(dateSP = new SelectedDateSpinner(), 8),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)), 2)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        for (int i = 0; i < 14; i++) {
            if (i < 7) {
                daysSPs[i] = new SelectedDateSpinner();
                daysSPs[i].setEnabled(false);
                daysSPs[i].setEditor(new JSpinner.DateEditor(daysSPs[i], "dd/MM/yyyy"));
                Util.addFocusSelectAllAction(daysSPs[i]);
            }
            timesSPs[i] = new SelectedDateSpinner();
            timesSPs[i].setEditor(new JSpinner.DateEditor(timesSPs[i], "hh:mm:ss"));
            Util.addFocusSelectAllAction(timesSPs[i]);
            hourSPs[i] = new SelectedNumberSpinner(0, 0, Integer.MAX_VALUE, 1);
            hourSPs[i].setPreferredSize(daysSPs[0].getPreferredSize());
            literSPs[i] = new SelectedNumberSpinner(0, 0, Integer.MAX_VALUE, 1);
            literSPs[i].setPreferredSize(daysSPs[0].getPreferredSize());
            issuedByCBs[i] = new JComboBox(issuedByCbModels[i]);
        }

        JPanel gridPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        gridPanel.add(getGridPanel(new JComponent[]{new JLabel(gridHeaders[0]), new JLabel(gridHeaders[1]), new JLabel(gridHeaders[2]), new JLabel(gridHeaders[3])}));
        gridPanel.add(new JLabel(gridHeaders[4]));

        int s = 0;
        int d = 0;
        int t = 0;
        int h = 0;
        int l = 0;
        int cp = 0;
        for (int i = 1; i < 8; i++) {
            JPanel tmPanel = new JPanel(new GridLayout(2, 1));
            tmPanel.add(timesSPs[t++]);
            timesSPs[t].setVisible(false);
            tmPanel.add(timesSPs[t++]);
            JPanel hrPanel = new JPanel(new GridLayout(2, 1));
            hrPanel.add(hourSPs[h++]);
            hourSPs[h].setVisible(false);
            hrPanel.add(hourSPs[h++]);
            JPanel ltPanel = new JPanel(new GridLayout(2, 1));
            ltPanel.add(literSPs[l++]);
            literSPs[l].setVisible(false);
            ltPanel.add(literSPs[l++]);

            gridPanel.add(getGridPanel(new JComponent[]{
                        daysSPs[d++], tmPanel, hrPanel, ltPanel
                    }));

            JPanel isPanel = new JPanel(new GridLayout(2, 1));
            isPanel.add(comboPanelWithLookupBtn(issuedByCBs[s] = new JComboBox(issuedByCbModels[s]),
                    new EmployeeLookupAction(issuedByCBs[s])));
            ++s;
            isPanel.add(comboPanes[cp] = comboPanelWithLookupBtn(issuedByCBs[s] = new JComboBox(issuedByCbModels[s]),
                    new EmployeeLookupAction(issuedByCBs[s])));
            comboPanes[cp].setVisible(false);
            ++cp;
            ++s;
            gridPanel.add(isPanel);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel upCenterPanel = new JPanel(new BorderLayout());
        upCenterPanel.add(gridPanel, BorderLayout.CENTER);
        upCenterPanel.add(getDuplicateBtnsPanel(), BorderLayout.EAST);
        centerPanel.add(upCenterPanel, BorderLayout.NORTH);

        JPanel downLabelPanel = new JPanel(new GridLayout(8, 1));
        for (int i = 0; i <= days.length; i++) {
            if (i == 0) {
                downLabelPanel.add(new JPanel());
            } else {
                downLabelPanel.add(daysLabels[i - 1]);
            }
        }
        upCenterPanel.add(downLabelPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);

        dateSP.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                shiftDayLabels();
            }
        });
        dateSP.setValue(Calendar.getInstance().getTime());
    }

    private void shiftDayLabels() {
        Date weekend = (Date) dateSP.getValue();
        int d = weekend.getDay();
        for (int i = 0; i < 7; i++) {
            daysLabels[i].setText(days[d++]);
            Calendar cal = Calendar.getInstance();
            cal.setTime(weekend);
            cal.add(Calendar.DATE, i - 6);
            daysSPs[i].setValue(cal.getTime());
            d = (d >= 7 ? 0 : d);
        }
    }

    @Override
    public void loadData() {
        Xissuing xi = (Xissuing) getDbObject();
        if (xi != null) {
            idField.setText(xi.getXissuingId().toString());
            if (xi.getIssueddate() != null) {
                dateSP.setValue(new java.util.Date(xi.getIssueddate().getTime()));
            }
            if (xi.getOperatorId() != null) {
                selectComboItem(operatorCB, xi.getOperatorId());
            }
            if (xi.getXmachineId() != null) {
                selectComboItem(machineCB, xi.getXmachineId());
            }
            if (xi.getXsiteId() != null) {
                selectComboItem(siteCB, xi.getXsiteId());
            }
            if (xi.getTime1Start() != null) {
                timesSPs[0].setValue(new java.util.Date(xi.getTime1Start().getTime()));
            }
            if (xi.getTime2Start() != null) {
                timesSPs[2].setValue(new java.util.Date(xi.getTime2Start().getTime()));
            }
            if (xi.getTime3Start() != null) {
                timesSPs[4].setValue(new java.util.Date(xi.getTime3Start().getTime()));
            }
            if (xi.getTime4Start() != null) {
                timesSPs[6].setValue(new java.util.Date(xi.getTime4Start().getTime()));
            }
            if (xi.getTime5Start() != null) {
                timesSPs[8].setValue(new java.util.Date(xi.getTime5Start().getTime()));
            }
            if (xi.getTime6Start() != null) {
                timesSPs[10].setValue(new java.util.Date(xi.getTime6Start().getTime()));
            }
            if (xi.getTime7Start() != null) {
                timesSPs[12].setValue(new java.util.Date(xi.getTime7Start().getTime()));
            }
            
            if (xi.getHours1Start()!=null) {
                hourSPs[0].setValue(xi.getHours1Start());
            }
            if (xi.getHours2Start()!=null) {
                hourSPs[2].setValue(xi.getHours2Start());
            }
            if (xi.getHours3Start()!=null) {
                hourSPs[4].setValue(xi.getHours3Start());
            }
            if (xi.getHours4Start()!=null) {
                hourSPs[6].setValue(xi.getHours4Start());
            }
            if (xi.getHours5Start()!=null) {
                hourSPs[8].setValue(xi.getHours5Start());
            }
            if (xi.getHours6Start()!=null) {
                hourSPs[10].setValue(xi.getHours6Start());
            }
            if (xi.getHours7Start()!=null) {
                hourSPs[12].setValue(xi.getHours7Start());
            }

            if (xi.getLiters1Start()!=null) {
                literSPs[0].setValue(xi.getLiters1Start());
            }
            if (xi.getLiters2Start()!=null) {
                literSPs[2].setValue(xi.getLiters2Start());
            }
            if (xi.getLiters3Start()!=null) {
                literSPs[4].setValue(xi.getLiters3Start());
            }
            if (xi.getLiters4Start()!=null) {
                literSPs[6].setValue(xi.getLiters4Start());
            }
            if (xi.getLiters5Start()!=null) {
                literSPs[8].setValue(xi.getLiters5Start());
            }
            if (xi.getLiters6Start()!=null) {
                literSPs[10].setValue(xi.getLiters6Start());
            }
            if (xi.getLiters7Start()!=null) {
                literSPs[12].setValue(xi.getLiters7Start());
            }

            if (xi.getTime1End() != null || xi.getHours1End() != null || xi.getLiters1End() != null || xi.getIssuedby1End() != null) {
                moreCBs[0].setSelected(true);
            }
            if (xi.getTime2End() != null || xi.getHours2End() != null || xi.getLiters2End() != null || xi.getIssuedby2End() != null) {
                moreCBs[1].setSelected(true);
            }
            if (xi.getTime3End() != null || xi.getHours3End() != null || xi.getLiters3End() != null || xi.getIssuedby3End() != null) {
                moreCBs[2].setSelected(true);
            }
            if (xi.getTime4End() != null || xi.getHours4End() != null || xi.getLiters4End() != null || xi.getIssuedby4End() != null) {
                moreCBs[3].setSelected(true);
            }
            if (xi.getTime5End() != null || xi.getHours5End() != null || xi.getLiters5End() != null || xi.getIssuedby5End() != null) {
                moreCBs[4].setSelected(true);
            }
            if (xi.getTime6End() != null || xi.getHours6End() != null || xi.getLiters6End() != null || xi.getIssuedby6End() != null) {
                moreCBs[5].setSelected(true);
            }
            if (xi.getTime7End() != null || xi.getHours7End() != null || xi.getLiters7End() != null || xi.getIssuedby7End() != null) {
                moreCBs[6].setSelected(true);
            }

            if (xi.getTime1End() != null) {
                timesSPs[1].setValue(new java.util.Date(xi.getTime1End().getTime()));
            }
            if (xi.getTime2End() != null) {
                timesSPs[3].setValue(new java.util.Date(xi.getTime2End().getTime()));
            }
            if (xi.getTime3End() != null) {
                timesSPs[5].setValue(new java.util.Date(xi.getTime3End().getTime()));
            }
            if (xi.getTime4End() != null) {
                timesSPs[7].setValue(new java.util.Date(xi.getTime4End().getTime()));
            }
            if (xi.getTime5End() != null) {
                timesSPs[9].setValue(new java.util.Date(xi.getTime5End().getTime()));
            }
            if (xi.getTime6End() != null) {
                timesSPs[11].setValue(new java.util.Date(xi.getTime6End().getTime()));
            }
            if (xi.getTime7End() != null) {
                timesSPs[13].setValue(new java.util.Date(xi.getTime7End().getTime()));
            }

            if (xi.getHours1End() != null) {
                hourSPs[1].setValue(xi.getHours1End());
            }
            if (xi.getHours2End() != null) {
                hourSPs[3].setValue(xi.getHours2End());
            }
            if (xi.getHours3End() != null) {
                hourSPs[5].setValue(xi.getHours3End());
            }
            if (xi.getHours4End() != null) {
                hourSPs[7].setValue(xi.getHours4End());
            }
            if (xi.getHours5End() != null) {
                hourSPs[9].setValue(xi.getHours5End());
            }
            if (xi.getHours6End() != null) {
                hourSPs[11].setValue(xi.getHours6End());
            }
            if (xi.getHours7End() != null) {
                hourSPs[13].setValue(xi.getHours7End());
            }

            if (xi.getLiters1End() != null) {
                literSPs[1].setValue(xi.getLiters1End());
            }
            if (xi.getLiters2End() != null) {
                literSPs[3].setValue(xi.getLiters2End());
            }
            if (xi.getLiters3End() != null) {
                literSPs[5].setValue(xi.getLiters3End());
            }
            if (xi.getLiters4End() != null) {
                literSPs[7].setValue(xi.getLiters4End());
            }
            if (xi.getLiters5End() != null) {
                literSPs[9].setValue(xi.getLiters5End());
            }
            if (xi.getLiters6End() != null) {
                literSPs[11].setValue(xi.getLiters6End());
            }
            if (xi.getLiters7End() != null) {
                literSPs[13].setValue(xi.getLiters7End());
            }
            
            if (xi.getIssuedby1Start() != null) {
                selectComboItem(issuedByCBs[0], xi.getIssuedby1Start());
            }
            if (xi.getIssuedby2Start() != null) {
                selectComboItem(issuedByCBs[2], xi.getIssuedby2Start());
            }
            if (xi.getIssuedby3Start() != null) {
                selectComboItem(issuedByCBs[4], xi.getIssuedby3Start());
            }
            if (xi.getIssuedby4Start() != null) {
                selectComboItem(issuedByCBs[6], xi.getIssuedby4Start());
            }
            if (xi.getIssuedby5Start() != null) {
                selectComboItem(issuedByCBs[8], xi.getIssuedby5Start());
            }
            if (xi.getIssuedby6Start() != null) {
                selectComboItem(issuedByCBs[10], xi.getIssuedby6Start());
            }
            if (xi.getIssuedby7Start() != null) {
                selectComboItem(issuedByCBs[12], xi.getIssuedby7Start());
            }

            if (xi.getIssuedby1End() != null) {
                selectComboItem(issuedByCBs[1], xi.getIssuedby1End());
            }
            if (xi.getIssuedby2End() != null) {
                selectComboItem(issuedByCBs[3], xi.getIssuedby2End());
            }
            if (xi.getIssuedby3End() != null) {
                selectComboItem(issuedByCBs[5], xi.getIssuedby3End());
            }
            if (xi.getIssuedby4End() != null) {
                selectComboItem(issuedByCBs[7], xi.getIssuedby4End());
            }
            if (xi.getIssuedby5End() != null) {
                selectComboItem(issuedByCBs[9], xi.getIssuedby5End());
            }
            if (xi.getIssuedby6End() != null) {
                selectComboItem(issuedByCBs[11], xi.getIssuedby6End());
            }
            if (xi.getIssuedby7End() != null) {
                selectComboItem(issuedByCBs[13], xi.getIssuedby7End());
            }
            
        }
    }

    @Override
    public boolean save() throws Exception {
        Xissuing xi = (Xissuing) getDbObject();
        boolean isNew = false;
        if (xi == null) {
            xi = new Xissuing(null);
            xi.setXissuingId(0);
            isNew = true;
        }
        xi.setOperatorId(getSelectedCbItem(operatorCB));
        xi.setXmachineId(getSelectedCbItem(machineCB));
        xi.setXsiteId(getSelectedCbItem(siteCB));
        
        TimeZone tz = TimeZone.getDefault();
        long tdiff = tz.getRawOffset();
        
        Date dt = (Date) dateSP.getValue();
        if (dt != null) {
            xi.setIssueddate(new java.sql.Date(dt.getTime()));
        }

        dt = (Date) timesSPs[0].getValue();
        if (dt != null) {
            xi.setTime1Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[2].getValue();
        if (dt != null) {
            xi.setTime2Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[4].getValue();
        if (dt != null) {
            xi.setTime3Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[6].getValue();
        if (dt != null) {
            xi.setTime4Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[8].getValue();
        if (dt != null) {
            xi.setTime5Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[10].getValue();
        if (dt != null) {
            xi.setTime6Start(new Timestamp(dt.getTime()+tdiff));
        }
        dt = (Date) timesSPs[12].getValue();
        if (dt != null) {
            xi.setTime7Start(new Timestamp(dt.getTime()+tdiff));
        }

        if (moreCBs[0].isSelected()) {
            dt = (Date) timesSPs[1].getValue();
            if (dt != null) {
                xi.setTime1End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime1End(null);
        }
        if (moreCBs[1].isSelected()) {
            dt = (Date) timesSPs[3].getValue();
            if (dt != null) {
                xi.setTime2End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime2End(null);
        }
        if (moreCBs[2].isSelected()) {
            dt = (Date) timesSPs[5].getValue();
            if (dt != null) {
                xi.setTime3End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime3End(null);
        }
        if (moreCBs[3].isSelected()) {
            dt = (Date) timesSPs[7].getValue();
            if (dt != null) {
                xi.setTime4End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime4End(null);
        }
        if (moreCBs[4].isSelected()) {
            dt = (Date) timesSPs[9].getValue();
            if (dt != null) {
                xi.setTime5End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime5End(null);
        }
        if (moreCBs[5].isSelected()) {
            dt = (Date) timesSPs[11].getValue();
            if (dt != null) {
                xi.setTime6End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime6End(null);
        }
        if (moreCBs[6].isSelected()) {
            dt = (Date) timesSPs[13].getValue();
            if (dt != null) {
                xi.setTime7End(new Timestamp(dt.getTime()+tdiff));
            }
        } else {
            xi.setTime7End(null);
        }

        xi.setHours1Start((Integer) hourSPs[0].getValue());
        xi.setHours2Start((Integer) hourSPs[2].getValue());
        xi.setHours3Start((Integer) hourSPs[4].getValue());
        xi.setHours4Start((Integer) hourSPs[6].getValue());
        xi.setHours5Start((Integer) hourSPs[8].getValue());
        xi.setHours6Start((Integer) hourSPs[10].getValue());
        xi.setHours7Start((Integer) hourSPs[12].getValue());

        xi.setHours1End(moreCBs[0].isSelected() ? ((Integer) hourSPs[1].getValue()) : null);
        xi.setHours2End(moreCBs[1].isSelected() ? ((Integer) hourSPs[3].getValue()) : null);
        xi.setHours3End(moreCBs[2].isSelected() ? ((Integer) hourSPs[5].getValue()) : null);
        xi.setHours4End(moreCBs[3].isSelected() ? ((Integer) hourSPs[7].getValue()) : null);
        xi.setHours5End(moreCBs[4].isSelected() ? ((Integer) hourSPs[9].getValue()) : null);
        xi.setHours6End(moreCBs[5].isSelected() ? ((Integer) hourSPs[11].getValue()) : null);
        xi.setHours7End(moreCBs[6].isSelected() ? ((Integer) hourSPs[13].getValue()) : null);

        xi.setLiters1Start((Integer) literSPs[0].getValue());
        xi.setLiters2Start((Integer) literSPs[2].getValue());
        xi.setLiters3Start((Integer) literSPs[4].getValue());
        xi.setLiters4Start((Integer) literSPs[6].getValue());
        xi.setLiters5Start((Integer) literSPs[8].getValue());
        xi.setLiters6Start((Integer) literSPs[10].getValue());
        xi.setLiters7Start((Integer) literSPs[12].getValue());

        xi.setLiters1End(moreCBs[0].isSelected() ? ((Integer) literSPs[1].getValue()) : null);
        xi.setLiters2End(moreCBs[1].isSelected() ? ((Integer) literSPs[3].getValue()) : null);
        xi.setLiters3End(moreCBs[2].isSelected() ? ((Integer) literSPs[5].getValue()) : null);
        xi.setLiters4End(moreCBs[3].isSelected() ? ((Integer) literSPs[7].getValue()) : null);
        xi.setLiters5End(moreCBs[4].isSelected() ? ((Integer) literSPs[9].getValue()) : null);
        xi.setLiters6End(moreCBs[5].isSelected() ? ((Integer) literSPs[11].getValue()) : null);
        xi.setLiters7End(moreCBs[6].isSelected() ? ((Integer) literSPs[13].getValue()) : null);

        xi.setIssuedby1Start(getSelectedCbItem(issuedByCBs[0]));
        xi.setIssuedby2Start(getSelectedCbItem(issuedByCBs[2]));
        xi.setIssuedby3Start(getSelectedCbItem(issuedByCBs[4]));
        xi.setIssuedby4Start(getSelectedCbItem(issuedByCBs[6]));
        xi.setIssuedby5Start(getSelectedCbItem(issuedByCBs[8]));
        xi.setIssuedby6Start(getSelectedCbItem(issuedByCBs[10]));
        xi.setIssuedby7Start(getSelectedCbItem(issuedByCBs[12]));

        xi.setIssuedby1End(moreCBs[0].isSelected() ? getSelectedCbItem(issuedByCBs[1]) : null);
        xi.setIssuedby2End(moreCBs[1].isSelected() ? getSelectedCbItem(issuedByCBs[3]) : null);
        xi.setIssuedby3End(moreCBs[2].isSelected() ? getSelectedCbItem(issuedByCBs[5]) : null);
        xi.setIssuedby4End(moreCBs[3].isSelected() ? getSelectedCbItem(issuedByCBs[7]) : null);
        xi.setIssuedby5End(moreCBs[4].isSelected() ? getSelectedCbItem(issuedByCBs[9]) : null);
        xi.setIssuedby6End(moreCBs[5].isSelected() ? getSelectedCbItem(issuedByCBs[11]) : null);
        xi.setIssuedby7End(moreCBs[6].isSelected() ? getSelectedCbItem(issuedByCBs[13]) : null);

        return saveDbRecord(xi, isNew);
    }

    private JPanel getDuplicateBtnsPanel() {
        JPanel dp = new JPanel(new GridLayout(16, 1));
        dp.add(new JPanel());
        dp.add(new JPanel());
        for (int r = 0; r < 7; r++) {
            dp.add(moreCBs[r] = new JCheckBox("V"));
            moreCBs[r].addChangeListener(new MoreCBchangeListener(r));
            dp.add(new JPanel());
        }
        return dp;
    }
}
