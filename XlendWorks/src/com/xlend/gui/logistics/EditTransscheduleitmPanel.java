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
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmPanel extends RecordEditPanel {

    private JComboBox[] machineCBs;
    private JComboBox[] fromSiteCBs;
    private JComboBox[] toSiteCBs;
    private JSpinner[] dateReqSPs;
    private JSpinner[] dateMoveSPs;
    private JComboBox[] lowbedCBs;
    private JComboBox[] operatorCBs;
    private JCheckBox[] completedCBs;
    private DefaultComboBoxModel machineCbModel;
    private DefaultComboBoxModel operatorCbModel;
    private DefaultComboBoxModel siteFromCbModel;
    private DefaultComboBoxModel siteToCbModel;
    private DefaultComboBoxModel lowbedCbModel;
//    private class EditLine extends JPanel {
//
//        private final Xtransscheduleitm itm;
//
//        EditLine(Xtransscheduleitm itm) {
//            super(new BorderLayout());
//            this.itm = itm;
//            JPanel centerPanel = new JPanel(new GridLayout(1, 5, 5, 5));
//            JPanel rightPanel = new JPanel(new BorderLayout(5, 5));
//            
//        }
//    }

    public EditTransscheduleitmPanel(Object[] params) {
        super(params);
    }

    @Override
    protected void fillContent() {
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        operatorCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            operatorCbModel.addElement(ci);
        }
        siteFromCbModel = new DefaultComboBoxModel();
        siteToCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteFromCbModel.addElement(ci);
                siteToCbModel.addElement(ci);
            }
        }
        lowbedCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllLowbeds(DashBoard.getExchanger())) {
            lowbedCbModel.addElement(ci);
        }
        
        int lines = (params == null ? 2 : (params.length + 1));
        JPanel centerPanel = new JPanel(new GridLayout(lines, 4, 5, 5));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Machine/Truck"), new JLabel("From")}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("To"), new JLabel("Date Required")}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Date for Move"), new JLabel("Lowbed")}));
        centerPanel.add(getGridPanel(new JComponent[]{new JLabel("Operator"), new JLabel("Completed", SwingConstants.RIGHT)}));

        machineCBs = new JComboBox[lines - 1];
        fromSiteCBs = new JComboBox[lines - 1];
        toSiteCBs = new JComboBox[lines - 1];
        dateReqSPs = new JSpinner[lines - 1];
        dateMoveSPs = new JSpinner[lines - 1];
        lowbedCBs = new JComboBox[lines - 1];
        operatorCBs = new JComboBox[lines - 1];
        completedCBs = new JCheckBox[lines - 1];

        for (int i = 0; i < lines - 1; i++) {
            centerPanel.add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(machineCBs[i] = new JComboBox(machineCbModel), new MachineLookupAction(machineCBs[i], null)),
                        comboPanelWithLookupBtn(fromSiteCBs[i] = new JComboBox(siteFromCbModel), new SiteLookupAction(fromSiteCBs[i]))
                    }));
            centerPanel.add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(toSiteCBs[i] = new JComboBox(siteToCbModel), new SiteLookupAction(toSiteCBs[i])),
                        dateReqSPs[i] = new SelectedDateSpinner()
                    }));
            centerPanel.add(getGridPanel(new JComponent[]{
                        dateMoveSPs[i] = new SelectedDateSpinner(),
                        comboPanelWithLookupBtn(lowbedCBs[i] = new JComboBox(lowbedCbModel), new LowbedLookupAction(lowbedCBs[i], null))
                    }));
            centerPanel.add(getBorderPanel(new JComponent[]{
                        new JPanel(),
                        comboPanelWithLookupBtn(operatorCBs[i] = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCBs[i])),
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
        //TODO
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }
}
