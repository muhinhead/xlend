package com.xlend.gui.logistics;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmPanel extends RecordEditPanel {

    private JPanel downGridPanel;
    private JPanel hdrPanel;
    private JScrollPane scrollPane;
    private ArrayList<RowPanel> childRows;
    private ArrayList<RowPanel> toDelete;
    private JCheckBox selectAllCB;

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (RowPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }

    private class RowPanel extends JPanel {

        private JCheckBox markCB;
        private JComboBox machineCB;
        private JComboBox fromSiteCB;
        private JComboBox toSiteCB;
        private JSpinner dateReqSP;
        private JSpinner dateMoveSP;
        private JComboBox lowbedCB;
        private JComboBox operatorCB;
        private JCheckBox completedCB;
        private DefaultComboBoxModel machineCbModel;
        private DefaultComboBoxModel operatorCbModel;
        private DefaultComboBoxModel siteFromCbModel;
        private DefaultComboBoxModel siteToCbModel;
        private DefaultComboBoxModel lowbedCbModel;
        private Xtransscheduleitm rec;

        RowPanel(Xtransscheduleitm xtransscheduleitm) {
            super(new GridLayout(1, 4, 5, 5));
            this.rec = xtransscheduleitm;
            markCB = new JCheckBox();
            machineCbModel = new DefaultComboBoxModel();
            operatorCbModel = new DefaultComboBoxModel();
            siteFromCbModel = new DefaultComboBoxModel();
            siteToCbModel = new DefaultComboBoxModel();
            lowbedCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                machineCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
                operatorCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
                siteFromCbModel.addElement(ci);
                siteToCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadAllLowbeds(DashBoard.getExchanger())) {
                lowbedCbModel.addElement(ci);
            }
            add(getBorderPanel(new JComponent[]{
                        markCB,
                        comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
                        comboPanelWithLookupBtn(fromSiteCB = new JComboBox(siteFromCbModel), new SiteLookupAction(fromSiteCB))
                    }));
            add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(toSiteCB = new JComboBox(siteToCbModel), new SiteLookupAction(toSiteCB)),
                        dateReqSP = new SelectedDateSpinner()
                    }));
            add(getGridPanel(new JComponent[]{
                        dateMoveSP = new SelectedDateSpinner(),
                        comboPanelWithLookupBtn(lowbedCB = new JComboBox(lowbedCbModel), new LowbedLookupAction(lowbedCB, null))
                    }));
            add(getBorderPanel(new JComponent[]{
                        new JPanel(),
                        comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)),
                        completedCB = new JCheckBox()
                    }));
            dateReqSP.setEditor(new JSpinner.DateEditor(dateReqSP, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateReqSP);
            dateMoveSP.setEditor(new JSpinner.DateEditor(dateMoveSP, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateMoveSP);

            load();
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (rec != null) {
                selectComboItem(machineCB, rec.getMachineId());
                selectComboItem(fromSiteCB, rec.getSiteFromId());
                selectComboItem(toSiteCB, rec.getSiteToId());
                if (rec.getDateRequired() != null) {
                    dateReqSP.setValue(new java.util.Date(rec.getDateRequired().getTime()));
                }
                if (rec.getDateMove() != null) {
                    dateMoveSP.setValue(new java.util.Date(rec.getDateMove().getTime()));
                }
                selectComboItem(lowbedCB, rec.getLowbedId());
                selectComboItem(operatorCB, rec.getOperatorId());
                completedCB.setSelected(rec.getIsCompleted() != null && rec.getIsCompleted() == 1);
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (rec == null) {
                rec = new Xtransscheduleitm(null);
                rec.setXtransscheduleitmId(0);
                isNew = true;
            }
            rec.setMachineId(getSelectedCbItem(machineCB));
            rec.setSiteFromId(getSelectedCbItem(fromSiteCB));
            rec.setSiteToId(getSelectedCbItem(toSiteCB));
            java.util.Date dt = (java.util.Date) dateReqSP.getValue();
            if (dt != null) {
                rec.setDateRequired(new java.sql.Date(dt.getTime()));
            }
            dt = (java.util.Date) dateMoveSP.getValue();
            if (dt != null) {
                rec.setDateMove(new java.sql.Date(dt.getTime()));
            }
            rec.setLowbedId(getSelectedCbItem(lowbedCB));
            rec.setOperatorId(getSelectedCbItem(operatorCB));
            rec.setIsCompleted(completedCB.isSelected() ? 1 : 0);
            rec = (Xtransscheduleitm) DashBoard.getExchanger().saveDbObject(rec);

            return saveDbRecord(rec, isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                rec = (Xtransscheduleitm) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }
    };
    private ArrayList<Xtransscheduleitm> records;

    public EditTransscheduleitmPanel(DbObject[] params) {
        super(params);
    }

    protected void fillContent() {
        childRows = new ArrayList<RowPanel>();
        toDelete = new ArrayList<RowPanel>();
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

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 7));

        hdrPanel.add(getGridPanel(new JComponent[]{getBorderPanel(new JComponent[]{selectAllCB = new JCheckBox(),new JLabel("Machine/Truck", SwingConstants.CENTER)}), new JLabel("From", SwingConstants.CENTER)}));
        hdrPanel.add(getGridPanel(new JComponent[]{new JLabel("To", SwingConstants.CENTER), new JLabel("Date Required", SwingConstants.CENTER)}));
        hdrPanel.add(getGridPanel(new JComponent[]{new JLabel("Date for Move", SwingConstants.CENTER), new JLabel("Lowbed", SwingConstants.CENTER)}));
        hdrPanel.add(getGridPanel(new JComponent[]{new JLabel("Operator", SwingConstants.CENTER), new JLabel("Completed", SwingConstants.RIGHT)}));

        selectAllCB.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (RowPanel p : childRows) {
                    p.mark(selectAllCB.isSelected());
                }
            }
        });
        
        downGridPanel.add(hdrPanel, BorderLayout.NORTH);
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);

        JPanel upperBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        upperBtnPanel.add(new JButton("Add"));
        upperBtnPanel.add(new JButton("Delete"));
        add(upperBtnPanel, BorderLayout.NORTH);
    }

    @Override
    public void loadData() {
        int i = 0;
        for (Xtransscheduleitm rec : records) {
            childRows.add(new RowPanel(rec));
        }
        redrawRows();
    }

    @Override
    public boolean save() throws Exception {
        for (RowPanel p : childRows) {
            if (!p.save()) {
                return false;
            }
        }
        for (RowPanel d : toDelete) {
            if (d.rec != null) {
                DashBoard.getExchanger().deleteObject(d.rec);
            }
        }
        return true;
    }
}
