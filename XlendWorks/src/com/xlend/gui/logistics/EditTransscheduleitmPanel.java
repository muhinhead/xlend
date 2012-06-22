package com.xlend.gui.logistics;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.LowbedLookupAction;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtransscheduleitm;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmPanel extends RecordEditPanel {

    private ArrayList<Xtransscheduleitm> records;
    private ArrayList<JComboBox> machineCBs;
    private ArrayList<JComboBox> fromSiteCBs;
    private JComboBox[] toSiteCBs;
    private JSpinner[] dateReqSPs;
    private JSpinner[] dateMoveSPs;
    private JComboBox[] lowbedCBs;
    private JComboBox[] operatorCBs;
    private JCheckBox[] completedCBs;
    private DefaultComboBoxModel[] machineCbModel;
    private DefaultComboBoxModel[] operatorCbModel;
    private DefaultComboBoxModel[] siteFromCbModel;
    private DefaultComboBoxModel[] siteToCbModel;
    private DefaultComboBoxModel[] lowbedCbModel;

    public EditTransscheduleitmPanel(DbObject[] params) {
        super(params);
    }

    @Override
    protected void fillContent() {
        records = new ArrayList<Xtransscheduleitm>();
        if (params != null) {
            for (DbObject o : params) {
                records.add((Xtransscheduleitm) o);
            }
        } else {
            Xtransscheduleitm rec = new Xtransscheduleitm(null);
            rec.setNew(true);
            try {
                rec.setXtransscheduleitmId(0);
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
            records.add(rec);
        }

        int lines = (params == null ? 2 : (params.length + 1));

        JPanel centerPanel = new JPanel(new GridLayout(lines, 4, 5, 5));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Machine/Truck", SwingConstants.CENTER), new JLabel("From", SwingConstants.CENTER)}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("To", SwingConstants.CENTER), new JLabel("Date Required", SwingConstants.CENTER)}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Date for Move", SwingConstants.CENTER), new JLabel("Lowbed", SwingConstants.CENTER)}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Operator", SwingConstants.CENTER), new JLabel("Completed", SwingConstants.RIGHT)}));

        machineCBs = new ArrayList<JComboBox>();//new JComboBox[lines - 1];
        fromSiteCBs = new ArrayList<JComboBox>();
        toSiteCBs = new JComboBox[lines - 1];
        dateReqSPs = new JSpinner[lines - 1];
        dateMoveSPs = new JSpinner[lines - 1];
        lowbedCBs = new JComboBox[lines - 1];
        operatorCBs = new JComboBox[lines - 1];
        completedCBs = new JCheckBox[lines - 1];

        machineCbModel = new DefaultComboBoxModel[lines - 1];
        operatorCbModel = new DefaultComboBoxModel[lines - 1];
        siteFromCbModel = new DefaultComboBoxModel[lines - 1];
        siteToCbModel = new DefaultComboBoxModel[lines - 1];
        lowbedCbModel = new DefaultComboBoxModel[lines - 1];

        for (int i = 0; i < lines - 1; i++) {
            machineCbModel[i] = new DefaultComboBoxModel();
            operatorCbModel[i] = new DefaultComboBoxModel();
            siteFromCbModel[i] = new DefaultComboBoxModel();
            siteToCbModel[i] = new DefaultComboBoxModel();
            lowbedCbModel[i] = new DefaultComboBoxModel();
        }
        
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            for (int i = 0; i < lines - 1; i++) {
                machineCbModel[i].addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            for (int i = 0; i < lines - 1; i++) {
                operatorCbModel[i].addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                for (int i = 0; i < lines - 1; i++) {
                    siteFromCbModel[i].addElement(ci);
                    siteToCbModel[i].addElement(ci);
                }
            }
        }
        for (ComboItem ci : XlendWorks.loadAllLowbeds(DashBoard.getExchanger())) {
            for (int i = 0; i < lines - 1; i++) {
                lowbedCbModel[i].addElement(ci);
            }
        }

        for (int i = 0; i < lines - 1; i++) {
            JComboBox mcb = new JComboBox(machineCbModel[i]);
            machineCBs.add(mcb);
            JComboBox fsb = new JComboBox(siteFromCbModel[i]);
            fromSiteCBs.add(fsb);
            centerPanel.add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(mcb, new MachineLookupAction(mcb, null)),
                        comboPanelWithLookupBtn(fsb, new SiteLookupAction(fsb))
                    }));
            centerPanel.add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(toSiteCBs[i] = new JComboBox(siteToCbModel[i]), new SiteLookupAction(toSiteCBs[i])),
                        dateReqSPs[i] = new SelectedDateSpinner()
                    }));
            centerPanel.add(getGridPanel(new JComponent[]{
                        dateMoveSPs[i] = new SelectedDateSpinner(),
                        comboPanelWithLookupBtn(lowbedCBs[i] = new JComboBox(lowbedCbModel[i]), new LowbedLookupAction(lowbedCBs[i], null))
                    }));
            centerPanel.add(getBorderPanel(new JComponent[]{
                        new JPanel(),
                        comboPanelWithLookupBtn(operatorCBs[i] = new JComboBox(operatorCbModel[i]), new EmployeeLookupAction(operatorCBs[i])),
                        completedCBs[i] = new JCheckBox()
                    }));
            dateReqSPs[i].setEditor(new JSpinner.DateEditor(dateReqSPs[i], "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateReqSPs[i]);
            dateMoveSPs[i].setEditor(new JSpinner.DateEditor(dateMoveSPs[i], "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateMoveSPs[i]);
        }
        if (params != null) {
            JPanel upperBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            upperBtnPanel.add(new JButton("Add"));
            upperBtnPanel.add(new JButton("Delete"));
            add(upperBtnPanel, BorderLayout.NORTH);
        }
        JPanel centerShellPanel = new JPanel(new BorderLayout());
        JScrollPane sp;
        centerShellPanel.add(centerPanel, BorderLayout.NORTH);
        if (params != null) {
            add(sp = new JScrollPane(centerShellPanel), BorderLayout.CENTER);
            sp.setPreferredSize(new Dimension(sp.getPreferredSize().width, 400));
        } else {
            add(centerShellPanel, BorderLayout.CENTER);
        }
    }

    @Override
    public void loadData() {
        int i = 0;
        for (Xtransscheduleitm rec : records) {
            selectComboItem(machineCBs.get(i), rec.getMachineId());
            selectComboItem(fromSiteCBs.get(i), rec.getSiteFromId());
            selectComboItem(toSiteCBs[i], rec.getSiteToId());
            if (rec.getDateRequired() != null) {
                dateReqSPs[i].setValue(new java.util.Date(rec.getDateRequired().getTime()));
            }
            if (rec.getDateMove() != null) {
                dateMoveSPs[i].setValue(new java.util.Date(rec.getDateMove().getTime()));
            }
            selectComboItem(lowbedCBs[i], rec.getLowbedId());
            selectComboItem(operatorCBs[i], rec.getOperatorId());
            completedCBs[i].setSelected(rec.getIsCompleted() != null && rec.getIsCompleted() == 1);
            i++;
        }
    }

    @Override
    public boolean save() throws Exception {
        int i = 0;
        for (Xtransscheduleitm rec : records) {
            rec.setMachineId(getSelectedCbItem(machineCBs.get(i)));
            rec.setSiteFromId(getSelectedCbItem(fromSiteCBs.get(i)));
            rec.setSiteToId(getSelectedCbItem(toSiteCBs[i]));
            java.util.Date dt = (java.util.Date) dateReqSPs[i].getValue();
            if (dt != null) {
                rec.setDateRequired(new java.sql.Date(dt.getTime()));
            }
            dt = (java.util.Date) dateMoveSPs[i].getValue();
            if (dt != null) {
                rec.setDateMove(new java.sql.Date(dt.getTime()));
            }
            rec.setLowbedId(getSelectedCbItem(lowbedCBs[i]));
            rec.setOperatorId(getSelectedCbItem(operatorCBs[i]));
            rec.setIsCompleted(completedCBs[i].isSelected() ? 1 : 0);
            rec = (Xtransscheduleitm) DashBoard.getExchanger().saveDbObject(rec);
            i++;
        }
        return true;
    }
}
