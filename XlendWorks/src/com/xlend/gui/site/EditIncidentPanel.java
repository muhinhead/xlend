package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xemployeeincident;
import com.xlend.orm.Xincidents;
import com.xlend.orm.Xmachineincident;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditIncidentPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP, incidentDateSP;
    private JPanel otherMachinesPanel, otherPeoplePanel;
    private JTextArea otherMachinesTA, otherPeopleTA;
    private JPanel peoplePanel, machinesPanel;//, mp;
    private ArrayList<MachineRowPanel> childMachineRows;
    private ArrayList<MachineRowPanel> machinesToDelete;
    private ArrayList<PeopleRowPanel> childPeopleRows;
    private ArrayList<PeopleRowPanel> peopleToDelete;
    private DefaultComboBoxModel siteCbModel, reportedByCbModel, reportedToCbModel, verifiedByCbModel;
    private JComboBox siteCB, reportedByCB, reportedToCB, verifiedByCB;
    private JTextField locationTF;
    private JTextArea descriptionTA, damagesTA;
    private JSpinner estCostSP;
    private JSpinner lostIncomeSP;
    private JRadioButton signedRB, unSignedRB, verifiedRB, unVerifiedRB;
    private JPanel vfComp;
    private JLabel vfLabel;

    private class MachineRowPanel extends JPanel {

        private DefaultComboBoxModel machineCbModel;
        private Xmachineincident xmi;
        private JCheckBox markCB;
        private JComboBox machineCB;

        MachineRowPanel(Xmachineincident xmi) {
            super(new BorderLayout(30, 20));
            this.xmi = xmi;
            machineCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                machineCbModel.addElement(ci);
            }
            machineCB = new JComboBox(machineCbModel);
            markCB = new JCheckBox();
            add(markCB, BorderLayout.WEST);
            add(comboPanelWithLookupBtn(machineCB, new MachineLookupAction(machineCB, null)));
            load();
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (getXmi() != null) {
                if (getXmi().getXmachineId() != null) {
                    selectComboItem(machineCB, getXmi().getXmachineId());
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getXmi() == null) {
                xmi = new Xmachineincident(null);
                xmi.setXmachineincidentId(0);
                Xincidents xi = (Xincidents) getDbObject();
                xmi.setXincidentsId(xi.getXincidentsId());
                isNew = true;
            }
            xmi.setXmachineId(getSelectedCbItem(machineCB));
            return saveDbRecord(getXmi(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xmi = (Xmachineincident) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xmi
         */
        public Xmachineincident getXmi() {
            return xmi;
        }
    }

    private class PeopleRowPanel extends JPanel {

        private DefaultComboBoxModel employeeCbModel;
        private Xemployeeincident xmi;
        private JCheckBox markCB;
        private JComboBox employeeCB;

        PeopleRowPanel(Xemployeeincident xmi) {
            super(new BorderLayout(30, 20));
            this.xmi = xmi;
            employeeCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
                employeeCbModel.addElement(ci);
            }
            employeeCB = new JComboBox(employeeCbModel);
            markCB = new JCheckBox();
            add(markCB, BorderLayout.WEST);
            add(comboPanelWithLookupBtn(employeeCB, new EmployeeLookupAction(employeeCB)));
            load();
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (getXmi() != null) {
                if (getXmi().getXemployeeId() != null) {
                    selectComboItem(employeeCB, getXmi().getXemployeeId());
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getXmi() == null) {
                xmi = new Xemployeeincident(null);
                xmi.setXemployeeincidentId(0);
                Xincidents xi = (Xincidents) getDbObject();
                xmi.setXincidentsId(xi.getXincidentsId());
                isNew = true;
            }
            xmi.setXemployeeId(getSelectedCbItem(employeeCB));
            return saveDbRecord(getXmi(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xmi = (Xemployeeincident) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xmi
         */
        public Xemployeeincident getXmi() {
            return xmi;
        }
    }

    public EditIncidentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childMachineRows = new ArrayList<MachineRowPanel>();
        childPeopleRows = new ArrayList<PeopleRowPanel>();
        machinesToDelete = new ArrayList<MachineRowPanel>();
        peopleToDelete = new ArrayList<PeopleRowPanel>();

        String titles[] = new String[]{
            "ID:", // "Date:", // "Date of incident:",
        //            "Machine(s)/truck(s) involved:",//"Other Vehicles involved"
        //            "Persons involved:", //"Other people involved:",
        //            "Site:", //"Location on site:"
        //            "Description",
        //            "Damages:",
        //            "Estimated cost:", //"Lost income:"
        //            "Reported by:", //"Reported to:"  //"Signed:
        //            "Is Verified" //"Verified by:"
        };

        JComponent edits[] = new JComponent[]{
            //            getGridPanel(new JComponent[]{
            //                getGridPanel(
            //                idField = new JTextField(),2),
            //                getGridPanel(new JComponent[]{new JLabel("Date:",SwingConstants.RIGHT),dateSP = new SelectedDateSpinner()}),
            //                new JLabel("Date of incident:", SwingConstants.RIGHT),
            //                getBorderPanel(new JComponent[]{incidentDateSP = new SelectedDateSpinner()})
            //            },7)
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Date:", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{dateSP = new SelectedDateSpinner()}),
                new JLabel("Date of incident:", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{incidentDateSP = new SelectedDateSpinner()})
            }, 7)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        incidentDateSP.setEditor(new JSpinner.DateEditor(incidentDateSP, "dd/MM/yyyy HH:mm"));
        Util.addFocusSelectAllAction(incidentDateSP);
        idField.setEnabled(false);
        HashSet<JComponent> excpt = new HashSet<JComponent>();
        for (int i = 2; i < edits.length; i++) {
            excpt.add(edits[i]);
        }
        organizePanels(titles, edits, excpt);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        centerPanel.setBorder(BorderFactory.createEtchedBorder());

//----------------------------------------------------------------------------------------
        JPanel leftCenterPanel = new JPanel();
        leftCenterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Machine(s)/truck(s) involved"));
        leftCenterPanel.setLayout(new BoxLayout(leftCenterPanel, BoxLayout.Y_AXIS));

        otherMachinesPanel = (JPanel) getBorderPanel(
                new JComponent[]{new JLabel("Other Vehicles involved:"),
                    new JScrollPane(otherMachinesTA = new JTextArea(3, 10),
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)});
        otherMachinesTA.setWrapStyleWord(true);
        otherMachinesTA.setLineWrap(true);

        machinesPanel = new JPanel();
        machinesPanel.setLayout(new BoxLayout(machinesPanel, BoxLayout.Y_AXIS));
        machinesPanel.add(otherMachinesPanel);//, BorderLayout.NORTH);
        JPanel machinesBorderPanel = new JPanel(new BorderLayout());
        machinesBorderPanel.add(machinesPanel, BorderLayout.NORTH);

        JPanel machinesButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        machinesButtonPanel.add(new JButton(new AbstractAction("+") {

            @Override
            public void actionPerformed(ActionEvent e) {
                childMachineRows.add(new MachineRowPanel(null));
                redrawMachineRows();
            }
        }));
        machinesButtonPanel.add(new JButton(new AbstractAction("x") {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ok = false;
                for (MachineRowPanel p : childMachineRows) {
                    if (p.isMarked()) {
                        machinesToDelete.add(p);
                        ok = true;
                    }
                }
                for (MachineRowPanel p : machinesToDelete) {
                    childMachineRows.remove(p);
                }
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "Check machine(s) to remove",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
                redrawMachineRows();
            }
        }));
        machinesBorderPanel.add(machinesButtonPanel, BorderLayout.SOUTH);

        leftCenterPanel.add(machinesBorderPanel);
//-----------------------------------------------------------------------------------------        

        JPanel rightCenterPanel = new JPanel();
        rightCenterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Persons involved"));
        rightCenterPanel.setLayout(new BoxLayout(rightCenterPanel, BoxLayout.Y_AXIS));

        otherPeoplePanel = (JPanel) getBorderPanel(
                new JComponent[]{new JLabel("Other People involved:"),
                    new JScrollPane(otherPeopleTA = new JTextArea(3, 10),
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)});
        otherPeopleTA.setWrapStyleWord(true);
        otherPeopleTA.setLineWrap(true);

        peoplePanel = new JPanel();
        peoplePanel.setLayout(new BoxLayout(peoplePanel, BoxLayout.Y_AXIS));
        peoplePanel.add(otherPeoplePanel);//, BorderLayout.NORTH);
        JPanel peopleBorderPanel = new JPanel(new BorderLayout());
        peopleBorderPanel.add(peoplePanel, BorderLayout.NORTH);

        JPanel peopleButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        peopleButtonPanel.add(new JButton(new AbstractAction("+") {

            @Override
            public void actionPerformed(ActionEvent e) {
                childPeopleRows.add(new PeopleRowPanel(null));
                redrawPeopleRows();
            }
        }));
        peopleButtonPanel.add(new JButton(new AbstractAction("x") {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ok = false;
                for (PeopleRowPanel p : childPeopleRows) {
                    if (p.isMarked()) {
                        peopleToDelete.add(p);
                        ok = true;
                    }
                }
                for (PeopleRowPanel p : peopleToDelete) {
                    childPeopleRows.remove(p);
                }
                if (!ok) {
                    JOptionPane.showMessageDialog(null, "Check people to remove",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
                redrawPeopleRows();
            }
        }));
        peopleBorderPanel.add(peopleButtonPanel, BorderLayout.SOUTH);

        rightCenterPanel.add(peopleBorderPanel);
//--------------------------------------------------------------------------------
        centerPanel.add(leftCenterPanel);
        centerPanel.add(rightCenterPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(getDownPanel(), BorderLayout.SOUTH);
        setPreferredSize(new Dimension(getPreferredSize().width, 500));
    }

    private JPanel getDownPanel() {
        siteCbModel = new DefaultComboBoxModel();
        reportedByCbModel = new DefaultComboBoxModel();
        reportedToCbModel = new DefaultComboBoxModel();
        verifiedByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            reportedByCbModel.addElement(ci);
            reportedToCbModel.addElement(ci);
            verifiedByCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        siteCB = new JComboBox(siteCbModel);
        reportedByCB = new JComboBox(reportedByCbModel);
        reportedToCB = new JComboBox(reportedToCbModel);
        verifiedByCB = new JComboBox(verifiedByCbModel);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(getGridPanel(new JComponent[]{
                    getBorderPanel(new JComponent[]{
                        new JLabel("Site:", SwingConstants.RIGHT),
                        comboPanelWithLookupBtn(siteCB, new SiteLookupAction(siteCB))
                    }),
                    getBorderPanel(new JComponent[]{
                        new JLabel("    Location:", SwingConstants.RIGHT),
                        locationTF = new JTextField()
                    })
                }), BorderLayout.NORTH);

        JPanel downCenterPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        JLabel lbl1;
        downCenterPanel.add(getBorderPanel(new JComponent[]{
                    lbl1 = new JLabel("Description:", SwingConstants.RIGHT),
                    new JScrollPane(descriptionTA = new JTextArea(3, 20))
                }));
        JLabel lbl2;
        downCenterPanel.add(getBorderPanel(new JComponent[]{
                    lbl2 = new JLabel("Damages:", SwingConstants.RIGHT),
                    new JScrollPane(damagesTA = new JTextArea(3, 20))
                }));
        panel.add(downCenterPanel);

        JPanel downDownPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JLabel lbl3;
        JLabel lbl4;

        downDownPanel.add(new JPanel());
        downDownPanel.add(getGridPanel(new JComponent[]{
                    getBorderPanel(new JComponent[]{
                        new JLabel(" Reported by:", SwingConstants.RIGHT),
                        comboPanelWithLookupBtn(reportedByCB, new EmployeeLookupAction(reportedByCB))
                    }),
                    getBorderPanel(new JComponent[]{
                        new JLabel(" Reported to:", SwingConstants.RIGHT),
                        comboPanelWithLookupBtn(reportedToCB, new EmployeeLookupAction(reportedToCB))
                    }),
                    getBorderPanel(new JComponent[]{
                        vfLabel = new JLabel(" Verified by:", SwingConstants.RIGHT),
                        vfComp = comboPanelWithLookupBtn(verifiedByCB, new EmployeeLookupAction(verifiedByCB))
                    })
                }));
        vfLabel.setVisible(false);
        vfComp.setVisible(false);
        downDownPanel.add(getGridPanel(new JComponent[]{
                    new JPanel(),
                    getGridPanel(new JComponent[]{
                        new JLabel("Signed:", SwingConstants.RIGHT),
                        signedRB = new JRadioButton("Y"),
                        unSignedRB = new JRadioButton("N")
                    }, 4),
                    getGridPanel(new JComponent[]{
                        new JLabel("Verified:", SwingConstants.RIGHT),
                        verifiedRB = new JRadioButton("Y"),
                        unVerifiedRB = new JRadioButton("N")
                    }, 4)
                }));
        downDownPanel.add(getGridPanel(new JComponent[]{
                    new JPanel(),
                    getBorderPanel(new JComponent[]{
                        new JPanel(),
                        lbl3 = new JLabel("Estimated cost: R", SwingConstants.RIGHT),
                        estCostSP = new SelectedNumberSpinner(0, 0, 999999999, 1)
                    }),
                    getBorderPanel(new JComponent[]{
                        new JPanel(),
                        lbl4 = new JLabel("Lost inclome: R", SwingConstants.RIGHT),
                        lostIncomeSP = new SelectedNumberSpinner(0, 0, 999999999, 1)
                    }),
                    new JPanel()
                }));

        ButtonGroup sb = new ButtonGroup();
        sb.add(signedRB);
        sb.add(unSignedRB);

        ButtonGroup vb = new ButtonGroup();
        vb.add(verifiedRB);
        vb.add(unVerifiedRB);

        lbl2.setPreferredSize(lbl1.getPreferredSize());
        panel.add(downDownPanel, BorderLayout.SOUTH);

        verifiedRB.addActionListener(verifiedAction());
        unVerifiedRB.addActionListener(verifiedAction());

        return panel;
    }

    private void redrawMachineRows() {
        machinesPanel.setVisible(false);
        machinesPanel.removeAll();
        for (MachineRowPanel p : childMachineRows) {
            machinesPanel.add(p);
        }
        machinesPanel.add(otherMachinesPanel);
        machinesPanel.repaint();
        machinesPanel.setVisible(true);
    }

    private void redrawPeopleRows() {
        peoplePanel.setVisible(false);
        peoplePanel.removeAll();
        for (PeopleRowPanel p : childPeopleRows) {
            peoplePanel.add(p);
        }
        peoplePanel.add(otherPeoplePanel);
        peoplePanel.repaint();
        peoplePanel.setVisible(true);
    }

    private ActionListener verifiedAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                vfComp.setVisible(verifiedRB.isSelected());
                vfLabel.setVisible(verifiedRB.isSelected());
            }
        };
    }

    @Override
    public void loadData() {
        Xincidents xi = (Xincidents) getDbObject();
        if (xi == null) {
            childPeopleRows.add(new PeopleRowPanel(null));
            childMachineRows.add(new MachineRowPanel(null));
        } else {
            idField.setText(xi.getXincidentsId().toString());
            if (xi.getIncidentdate() != null) {
                incidentDateSP.setValue(new java.util.Date(xi.getIncidentdate().getTime()));
            }
            if (xi.getReportdate() != null) {
                dateSP.setValue(new java.util.Date(xi.getReportdate().getTime()));
            }
            otherMachinesTA.setText(xi.getOthermachines());
            otherPeopleTA.setText(xi.getOtherpeople());
            if (xi.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xi.getXsiteId());
                selectComboItem(siteCB, xi.getXsiteId());
            }
            locationTF.setText(xi.getLocation());
            descriptionTA.setText(xi.getDescription());
            damagesTA.setText(xi.getDamages());
            if (xi.getReportedbyId() != null) {
                selectComboItem(reportedByCB, xi.getReportedbyId());
            }
            if (xi.getReportedtoId() != null) {
                selectComboItem(reportedToCB, xi.getReportedtoId());
            }
            if (xi.getVerifiedbyId() != null) {
                selectComboItem(verifiedByCB, xi.getVerifiedbyId());
            }
            if (xi.getIsSigned() != null && xi.getIsSigned() == 1) {
                signedRB.setSelected(true);
                unSignedRB.setSelected(false);
            }
            if (xi.getIsVerified() != null && xi.getIsVerified() == 1) {
                verifiedRB.setSelected(true);
                unVerifiedRB.setSelected(false);
            }
            if (xi.getEstimatedCost() != null) {
                estCostSP.setValue(xi.getEstimatedCost());
            }
            if (xi.getLostIncome() != null) {
                lostIncomeSP.setValue(xi.getLostIncome());
            }
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xmachineincident.class,
                        "xincidents_id=" + xi.getXincidentsId(), "xmachineincident_id");
                for (DbObject rec : recs) {
                    childMachineRows.add(new MachineRowPanel((Xmachineincident) rec));
                }
                recs = DashBoard.getExchanger().getDbObjects(Xemployeeincident.class,
                        "xincidents_id=" + xi.getXincidentsId(), "xemployeeincident_id");
                for (DbObject rec : recs) {
                    childPeopleRows.add(new PeopleRowPanel((Xemployeeincident) rec));
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
        }
        redrawPeopleRows();
        redrawMachineRows();
    }

    @Override
    public boolean save() throws Exception {
        Xincidents xi = (Xincidents) getDbObject();
        boolean isNew = false;
        if (xi == null) {
            xi = new Xincidents(null);
            xi.setXincidentsId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xi.setReportdate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) incidentDateSP.getValue();
        if (dt != null) {
            xi.setIncidentdate(new java.sql.Date(dt.getTime()));
        }
        xi.setOthermachines(otherMachinesTA.getText());
        xi.setOtherpeople(otherPeopleTA.getText());
        //selectComboItem(siteCB, xi.getXsiteId());
        xi.setXsiteId(getSelectedCbItem(siteCB));
        xi.setLocation(locationTF.getText());
        xi.setDescription(descriptionTA.getText());
        xi.setDamages(damagesTA.getText());
        xi.setReportedbyId(getSelectedCbItem(reportedByCB));
        xi.setReportedtoId(getSelectedCbItem(reportedToCB));
        xi.setVerifiedbyId(verifiedRB.isSelected() ? null : getSelectedCbItem(verifiedByCB));
        xi.setIsSigned(signedRB.isSelected() ? 1 : 0);
        xi.setIsVerified(verifiedRB.isSelected() ? 1 : 0);
        xi.setEstimatedCost((Integer) estCostSP.getValue());
        xi.setLostIncome((Integer) lostIncomeSP.getValue());
        boolean ok = saveDbRecord(xi, isNew);
        if (ok) {
            for (MachineRowPanel p : childMachineRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (MachineRowPanel p : machinesToDelete) {
                if (p.getXmi() != null) {
                    DashBoard.getExchanger().deleteObject(p.getXmi());
                }
            }
            for (PeopleRowPanel p : childPeopleRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (PeopleRowPanel p : peopleToDelete) {
                if (p.getXmi() != null) {
                    DashBoard.getExchanger().deleteObject(p.getXmi());
                }
            }
        }
        return ok;
    }
}
