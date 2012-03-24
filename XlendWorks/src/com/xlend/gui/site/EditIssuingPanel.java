package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditIssuingPanel extends RecordEditPanel {

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
        for (int i = 1; i < 8; i++) {
            JPanel tmPanel = new JPanel(new GridLayout(2, 1));
            tmPanel.add(timesSPs[t++]);
            tmPanel.add(timesSPs[t++]);
            JPanel hrPanel = new JPanel(new GridLayout(2, 1));
            hrPanel.add(hourSPs[h++]);
            hrPanel.add(hourSPs[h++]);
            JPanel ltPanel = new JPanel(new GridLayout(2, 1));
            ltPanel.add(literSPs[l++]);
            ltPanel.add(literSPs[l++]);

            gridPanel.add(getGridPanel(new JComponent[]{
                        daysSPs[d++], tmPanel, hrPanel, ltPanel
                    }));

            JPanel isPanel = new JPanel(new GridLayout(2, 1));
            isPanel.add(comboPanelWithLookupBtn(issuedByCBs[s] = new JComboBox(issuedByCbModels[s]),
                    new EmployeeLookupAction(issuedByCBs[s])));
            ++s;
            isPanel.add(comboPanelWithLookupBtn(issuedByCBs[s] = new JComboBox(issuedByCbModels[s]),
                    new EmployeeLookupAction(issuedByCBs[s])));
            ++s;
            gridPanel.add(isPanel);
        }

        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel upCenterPanel = new JPanel(new BorderLayout());
        upCenterPanel.add(gridPanel, BorderLayout.CENTER);
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
            cal.add(Calendar.DATE, i-6);
            daysSPs[i].setValue(cal.getTime());
            d = (d >= 7 ? 0 : d);
        }
    }

    @Override
    public void loadData() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }
}
