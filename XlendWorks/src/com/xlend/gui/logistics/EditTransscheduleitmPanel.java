package com.xlend.gui.logistics;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.LowBedLookupAction;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtransscheduleitm;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import com.xlend.util.WiderDropDownCombo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditTransscheduleitmPanel extends RecordEditPanel {

    private static String fixedAttribute;
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
        private WiderDropDownCombo machineCB;
        private WiderDropDownCombo fromSiteCB;
        private WiderDropDownCombo toSiteCB;
        private JSpinner dateReqSP;
        private JSpinner dateMoveSP;
        private WiderDropDownCombo lowbedCB;
        private WiderDropDownCombo operatorCB;
        private JCheckBox completedCB;
        private DefaultComboBoxModel machineCbModel;
        private DefaultComboBoxModel operatorCbModel;
        private DefaultComboBoxModel siteFromCbModel;
        private DefaultComboBoxModel siteToCbModel;
        private DefaultComboBoxModel lowbedCbModel;
        private Xtransscheduleitm rec;
        private MachineLookupAction machineLookup;
        private SiteLookupAction fromSiteLookup, toSiteLookup;
        private RowPanel firstRec;

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
                if (ci.getId() != 0) {
                    siteFromCbModel.addElement(ci);
                    siteToCbModel.addElement(ci);
                }
            }
            for (ComboItem ci : XlendWorks.loadAllLowbeds(DashBoard.getExchanger())) {
                lowbedCbModel.addElement(ci);
            }
            add(getBorderPanel(new JComponent[]{
                        markCB,
                        comboPanelWithLookupBtn(machineCB = new WiderDropDownCombo(machineCbModel),
                        machineLookup = new MachineLookupAction(machineCB, null)),
                        comboPanelWithLookupBtn(fromSiteCB = new WiderDropDownCombo(siteFromCbModel),
                        fromSiteLookup = new SiteLookupAction(fromSiteCB))
                    }));
            add(getGridPanel(new JComponent[]{
                        comboPanelWithLookupBtn(toSiteCB = new WiderDropDownCombo(siteToCbModel),
                        toSiteLookup = new SiteLookupAction(toSiteCB)),
                        dateReqSP = new SelectedDateSpinner()
                    }));
            add(getGridPanel(new JComponent[]{
                        dateMoveSP = new SelectedDateSpinner(),
                        comboPanelWithLookupBtn(lowbedCB = new WiderDropDownCombo(lowbedCbModel), new LowBedLookupAction(lowbedCB))
                    }));
            add(getBorderPanel(new JComponent[]{
                        new JPanel(),
                        comboPanelWithLookupBtn(operatorCB = new WiderDropDownCombo(operatorCbModel), new EmployeeLookupAction(operatorCB)),
                        completedCB = new JCheckBox()
                    }));
            dateReqSP.setEditor(new JSpinner.DateEditor(dateReqSP, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateReqSP);
            dateMoveSP.setEditor(new JSpinner.DateEditor(dateMoveSP, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateMoveSP);

            for (WiderDropDownCombo cb : new WiderDropDownCombo[]{
                        machineCB, fromSiteCB, toSiteCB, lowbedCB, operatorCB}) {
                cb.setPreferredSize(new Dimension(130, cb.getPreferredSize().height));
                cb.setWide(true);
            }
                    
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
                RecordEditPanel.addSiteItem(siteFromCbModel, rec.getSiteFromId());
                selectComboItem(fromSiteCB, rec.getSiteFromId());
                RecordEditPanel.addSiteItem(siteToCbModel, rec.getSiteToId());
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


            if (childRows.size() > 0) {
                firstRec = childRows.get(0);
            } else if (toDelete.size() > 0) {
                firstRec = toDelete.get(0);
            } else {
                firstRec = null;
            }
            if (firstRec != null && firstRec.rec != null && firstRec.rec.getDateRequired() != null) {
                dateReqSP.setValue(new java.util.Date(firstRec.rec.getDateRequired().getTime()));
                dateReqSP.setEnabled(false);
            }

            if (fixedAttribute != null) {
                if (fixedAttribute.equals("machine_id")) {
                    if (firstRec != null) {
                        selectComboItem(machineCB, firstRec.rec.getMachineId());
                    }
                    machineCB.setEnabled(false);
                    machineLookup.setEnabled(false);
                } else if (fixedAttribute.equals("site_from_id")) {
                    if (firstRec != null) {
                        selectComboItem(fromSiteCB, firstRec.rec.getSiteFromId());
                    }
                    fromSiteCB.setEnabled(false);
                    fromSiteLookup.setEnabled(false);
                } else if (fixedAttribute.equals("site_to_id")) {
                    if (firstRec != null) {
                        selectComboItem(toSiteCB, firstRec.rec.getSiteToId());
                    }
                    toSiteCB.setEnabled(false);
                    toSiteLookup.setEnabled(false);
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (rec == null) {
                rec = new Xtransscheduleitm(null);
                rec.setXtransscheduleitmId(0);
                isNew = true;
            } else {
                isNew = rec.isNew();
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

        hdrPanel.add(getGridPanel(new JComponent[]{getBorderPanel(new JComponent[]{selectAllCB = new JCheckBox(), new JLabel("Machine/Truck", SwingConstants.CENTER)}), new JLabel("From", SwingConstants.CENTER)}));
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
//        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
//        scrollPane.setMinimumSize(new Dimension(screenWidth - 20, 400));


        JPanel upperBtnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        upperBtnPanel.add(new JButton(addLineAction("Add")));
        upperBtnPanel.add(new JButton(delLineAction("Delete")));
        add(upperBtnPanel, BorderLayout.NORTH);
    }

    private AbstractAction addLineAction(final String title) {
        return new AbstractAction(title) {

            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new RowPanel(null));
                redrawRows();
            }
        };
    }

    private AbstractAction delLineAction(final String title) {
        return new AbstractAction(title) {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (RowPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (RowPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    @Override
    public void loadData() {
        int i = 0;
        for (Xtransscheduleitm rec : records) {
            childRows.add(new RowPanel(rec));
        }
//        scrollPane.setMinimumSize(new Dimension(hdrPanel.getMinimumSize().width, scrollPane.getMinimumSize().height));
        redrawRows();
    }

    @Override
    public boolean save() throws Exception {
        int n = 0;
        for (RowPanel p : childRows) {
            if (p.rec != null) {
                System.out.println("n=" + n + "rec.isNew=" + p.rec.isNew() + " rec.ID=" + p.rec.getXtransscheduleitmId());
            } else {
                System.out.println("rec is NULL!");
            }
            if (!p.save()) {
                return false;
            }
            System.out.println("OK!");
            n++;
        }
        for (RowPanel d : toDelete) {
            if (d.rec != null) {
                DashBoard.getExchanger().deleteObject(d.rec);
            }
        }
        return true;
    }

    /**
     * @return the fixedAttribute
     */
    public static String getFixedAttribute() {
        return fixedAttribute;
    }

    /**
     * @param aFixedAttribute the fixedAttribute to set
     */
    public static void setFixedAttribute(String attrName) {
        fixedAttribute = attrName;
    }

    public static void resetFixedAttribute() {
        fixedAttribute = null;
    }
}
