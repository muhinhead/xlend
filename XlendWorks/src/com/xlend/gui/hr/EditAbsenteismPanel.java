package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xabsenteeism;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditAbsenteismPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel employeeCbModel;
    private DefaultComboBoxModel machineCbModel;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel reportedToCbModel;
    private DefaultComboBoxModel reportedByCbModel;
    private DefaultComboBoxModel grantedByCbModel;
    private JSpinner dateSP;
    private JComboBox employeeCB;
    private JComboBox machineCB;
    private JComboBox siteCB;
    private JComboBox reportedToCB;
    private JComboBox reportedByCB;
    private JComboBox grantedByCB;
    private JRadioButton standingYesRB;
    private JRadioButton standingNoRB;
    private JCheckBox notCommunicatedCB;
    private JRadioButton permGrantedYesRB;
    private JRadioButton permGrantedNoRB;
    private JTextArea reasonTextArea;
    private JRadioButton medicalCondRB;
    private JRadioButton funeralRB;
    private JRadioButton familyProblemRB;
    private JRadioButton inJailRB;
    private JRadioButton pdpExpiredRB;
    private JRadioButton licenseProblemRB;
    private JRadioButton ppeAndSafetyRB;
    private JRadioButton wageDisputeRB;
    private JRadioButton drunkOnSiteRB;
    private JRadioButton workAccidentRB;
    private JRadioButton noReasonRB;
    private JRadioButton[] reasonRBgroup;
    private JCheckBox medicalCertCB;
    private JCheckBox deathCertCB;
    private JLabel permGrantedLabel;

    public EditAbsenteismPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:", //   "Breakdown Date:",
            "Employee:",
            "Date:",
            "Site:",
            "Machine/Truck:",
            "Standing:",
            "Reported By:",
            "Reported To:",
            "Not Communicated:",
            "Permission Granted:",
            "By:"//,
//            "Reason:"
        };
        employeeCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        siteCbModel = new DefaultComboBoxModel();
        reportedToCbModel = new DefaultComboBoxModel();
        reportedByCbModel = new DefaultComboBoxModel();
        grantedByCbModel = new DefaultComboBoxModel();
        machineCbModel.addElement(new ComboItem(0, "NO MACHINE"));
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadActiveSites()) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        ComboItem unknown = new ComboItem(0, "--unknown--");
        reportedToCbModel.addElement(unknown);
        reportedByCbModel.addElement(unknown);
        for (ComboItem ci : XlendWorks.loadAllEmployees(Selects.activeEmployeeCondition)) {
            reportedToCbModel.addElement(ci);
            reportedByCbModel.addElement(ci);
            employeeCbModel.addElement(ci);
            grantedByCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JPanel(),
                medicalCondRB = new JRadioButton("Medical Conditions")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(employeeCB = new JComboBox(employeeCbModel),
                new EmployeeLookupAction(employeeCB)),
                new JPanel(),
                funeralRB = new JRadioButton("Funeral")
            }),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{dateSP = new SelectedDateSpinner()}),
                new JPanel(),
                familyProblemRB = new JRadioButton("Family Problems")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
                new JPanel(),
                inJailRB = new JRadioButton("In Jail")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
                new JPanel(),
                pdpExpiredRB = new JRadioButton("PDP expired")
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{
                    standingYesRB = new JRadioButton("Y"),
                    standingNoRB = new JRadioButton("N"),
                    new JPanel(), new JPanel()
                }),
                new JPanel(),
                licenseProblemRB = new JRadioButton("License problem")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(reportedByCB = new JComboBox(reportedByCbModel),
                new EmployeeLookupAction(reportedByCB)),
                new JPanel(),
                ppeAndSafetyRB = new JRadioButton("PPE & Safety")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(reportedToCB = new JComboBox(reportedToCbModel),
                new ClercLookupAction(reportedToCB)),
                new JPanel(),
                wageDisputeRB = new JRadioButton("Wage Dispute")
            }),
            getGridPanel(new JComponent[]{
                notCommunicatedCB = new JCheckBox(),
                new JPanel(),
                drunkOnSiteRB = new JRadioButton("Drunk on site")
            }),
            getGridPanel(new JComponent[]{
                getGridPanel(new JComponent[]{
                    permGrantedYesRB = new JRadioButton("Y"),
                    permGrantedNoRB = new JRadioButton("N"),
                    new JPanel(), new JPanel()
                }),
                new JPanel(),
                workAccidentRB = new JRadioButton("Work Accident")
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(grantedByCB = new JComboBox(grantedByCbModel),
                new ClercLookupAction(grantedByCB)),
                new JPanel(),
                noReasonRB = new JRadioButton("No Reason")
            })
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        permGrantedLabel = labels[9];

        JPanel downPanel = new JPanel(new GridLayout(1, 2));
        JScrollPane sp;
        downPanel.add(sp = new JScrollPane(reasonTextArea = new JTextArea(5, 30),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        reasonTextArea.setWrapStyleWord(true);
        reasonTextArea.setLineWrap(true);
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reason"));
        JPanel rightDownPanel = new JPanel(new BorderLayout());
        JPanel rdgridPanel = new JPanel(new GridLayout(3, 2));
        rdgridPanel.add(new JPanel());
        rdgridPanel.add(new JPanel());
        rdgridPanel.add(new JLabel("", SwingConstants.RIGHT));
        rdgridPanel.add(medicalCertCB = new JCheckBox("Medical Certificate"));
        rdgridPanel.add(new JLabel("", SwingConstants.RIGHT));
        rdgridPanel.add(deathCertCB = new JCheckBox("Death Certificate"));
        rightDownPanel.add(rdgridPanel, BorderLayout.WEST);
        downPanel.add(rightDownPanel);
        add(downPanel, BorderLayout.CENTER);

        ButtonGroup yesNoGroup1 = new ButtonGroup();
        yesNoGroup1.add(standingYesRB);
        yesNoGroup1.add(standingNoRB);
        ButtonGroup yesNoGroup2 = new ButtonGroup();
        yesNoGroup2.add(permGrantedYesRB);
        yesNoGroup2.add(permGrantedNoRB);
        permGrantedNoRB.setSelected(true);

        reasonRBgroup = new JRadioButton[]{
            medicalCondRB, funeralRB, familyProblemRB, inJailRB, pdpExpiredRB,
            licenseProblemRB, ppeAndSafetyRB, wageDisputeRB, drunkOnSiteRB,
            workAccidentRB, noReasonRB
        };

        ButtonGroup reasonGroup = new ButtonGroup();
        for (JRadioButton rb : reasonRBgroup) {
            reasonGroup.add(rb);
        }

        employeeCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isSalesCategory = (XlendWorks.getWageCategory(getSelectedCbItem(employeeCB)) == 1);
                permGrantedYesRB.setVisible(isSalesCategory);
                permGrantedNoRB.setVisible(isSalesCategory);
                permGrantedLabel.setEnabled(isSalesCategory);
            }
        });
    }

    @Override
    public void loadData() {
        Xabsenteeism xa = (Xabsenteeism) getDbObject();
        if (xa != null) {
            idField.setText(xa.getXabsenteeismId().toString());
            if (xa.getXemployeeId() != null) {
                selectComboItem(employeeCB, xa.getXemployeeId());
            }
            if (xa.getAbsentdate() != null) {
                dateSP.setValue(new Date(xa.getAbsentdate().getTime()));
            }
            if (xa.getXmachineId() != null) {
                selectComboItem(machineCB, xa.getXmachineId());
            }
            if (xa.getStanding() != null && xa.getStanding() == 1) {
                standingYesRB.setSelected(true);
            } else {
                standingNoRB.setSelected(true);
            }
            if (xa.getReportedbyId() != null) {
                selectComboItem(reportedByCB, xa.getReportedbyId());
            }
            if (xa.getReportedtoId() != null) {
                selectComboItem(reportedToCB, xa.getReportedtoId());
            }
            if (xa.getNotcommunicated() != null && xa.getNotcommunicated() == 1) {
                notCommunicatedCB.setSelected(true);
            }
            if (xa.getPermgranted() != null && xa.getPermgranted() == 1) {
                permGrantedYesRB.setSelected(true);
            } else {
                permGrantedNoRB.setSelected(true);
            }
            if (xa.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xa.getXsiteId());
                selectComboItem(siteCB, xa.getXsiteId());
            }
            if (xa.getGrantedbyId() != null) {
                selectComboItem(grantedByCB, xa.getGrantedbyId());
            }
            reasonTextArea.setText(xa.getReason());
            for (JRadioButton rb : reasonRBgroup) {
                rb.setSelected(false);
            }
            if (xa.getMedicalCond() != null && xa.getMedicalCond() == 1) {
                medicalCondRB.setSelected(true);
            } else if (xa.getFuneral() != null && xa.getFuneral() == 1) {
                funeralRB.setSelected(true);
            } else if (xa.getFamilyProblem() != null && xa.getFamilyProblem() == 1) {
                familyProblemRB.setSelected(true);
            } else if (xa.getInJail() != null && xa.getInJail() == 1) {
                inJailRB.setSelected(true);
            } else if (xa.getPdpExpired() != null && xa.getPdpExpired() == 1) {
                pdpExpiredRB.setSelected(true);
            } else if (xa.getLicenseProblem() != null && xa.getLicenseProblem() == 1) {
                licenseProblemRB.setSelected(true);
            } else if (xa.getPpeSafety() != null && xa.getPpeSafety() == 1) {
                ppeAndSafetyRB.setSelected(true);
            } else if (xa.getWageDispute() != null && xa.getWageDispute() == 1) {
                wageDisputeRB.setSelected(true);
            } else if (xa.getDrunkOnSite() != null && xa.getDrunkOnSite() == 1) {
                drunkOnSiteRB.setSelected(true);
            } else if (xa.getWorkAccident() != null && xa.getWorkAccident() == 1) {
                workAccidentRB.setSelected(true);
            } else {
                noReasonRB.setSelected(true);
            }
            if (xa.getMedicalCert() != null && xa.getMedicalCert() == 1) {
                medicalCertCB.setSelected(true);
            }
            if (xa.getDeathCert() != null && xa.getDeathCert() == 1) {
                deathCertCB.setSelected(true);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xabsenteeism xa = (Xabsenteeism) getDbObject();
        boolean isNew = false;
        if (xa == null) {
            xa = new Xabsenteeism(null);
            xa.setXabsenteeismId(0);
            isNew = true;
        }
        xa.setXmachineId(getSelectedCbItem(machineCB));
        xa.setXsiteId(getSelectedCbItem(siteCB));
        Date dt = (Date) dateSP.getValue();
        if (dt != null) {
            xa.setAbsentdate(new java.sql.Date(dt.getTime()));
        }
        xa.setXemployeeId(getSelectedCbItem(employeeCB));
        xa.setReportedtoId(getSelectedCbItem(reportedToCB));
        xa.setReportedbyId(getSelectedCbItem(reportedByCB));
        xa.setGrantedbyId(getSelectedCbItem(grantedByCB));
        xa.setNotcommunicated(notCommunicatedCB.isSelected() ? 1 : 0);
        xa.setPermgranted(permGrantedYesRB.isSelected() ? 1 : 0);
        xa.setStanding(standingYesRB.isSelected() ? 1 : 0);
        xa.setMedicalCond(medicalCondRB.isSelected() ? 1 : 0);
        xa.setFuneral(funeralRB.isSelected() ? 1 : 0);
        xa.setFamilyProblem(familyProblemRB.isSelected() ? 1 : 0);
        xa.setInJail(inJailRB.isSelected() ? 1 : 0);
        xa.setPdpExpired(pdpExpiredRB.isSelected() ? 1 : 0);
        xa.setLicenseProblem(licenseProblemRB.isSelected() ? 1 : 0);
        xa.setPpeSafety(ppeAndSafetyRB.isSelected() ? 1 : 0);
        xa.setWageDispute(wageDisputeRB.isSelected() ? 1 : 0);
        xa.setDrunkOnSite(drunkOnSiteRB.isSelected() ? 1 : 0);
        xa.setWorkAccident(workAccidentRB.isSelected() ? 1 : 0);
        xa.setNoReason(noReasonRB.isSelected() ? 1 : 0);
        xa.setMedicalCert(medicalCertCB.isSelected() ? 1 : 0);
        xa.setDeathCert(deathCertCB.isSelected() ? 1 : 0);
        xa.setReason(reasonTextArea.getText());
        return saveDbRecord(xa, isNew);
    }
}
