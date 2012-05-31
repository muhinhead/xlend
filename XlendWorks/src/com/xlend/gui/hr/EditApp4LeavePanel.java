package com.xlend.gui.hr;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xappforleave;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditApp4LeavePanel extends RecordEditPanel {

    private JComboBox applicantCB;
    private JComboBox approvedByCB;
    private DefaultComboBoxModel applicantCbModel;
    private DefaultComboBoxModel approvedByCbModel;
    private JTextField idField;
    private JSpinner appDateSP;
    private JSpinner fromDateSP;
    private JSpinner toDateSP;
    private JRadioButton sickRB;
    private JRadioButton annualRB;
    private JRadioButton specRB;
    private JRadioButton unpaidRB;
    private JSpinner totDaysSP;
    private JRadioButton signedRB;
    private JRadioButton notSignedRB;
    private JRadioButton approvedRB;
    private JRadioButton notApprovedRB;
    private JTextArea remarksArea;
    private EmployeeLookupAction approvedLookupAction;

    public EditApp4LeavePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Date:", //"From:"
            "Applicant:", //"To:"
            "Applied for:",//"Total days:"
            "", "", "", //"",
            "Signed:",
            "Approved:" //"Approved by:"
        };
        applicantCbModel = new DefaultComboBoxModel();
        approvedByCbModel = new DefaultComboBoxModel();
        approvedByCbModel.addElement(new ComboItem(0, "--"));
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            applicantCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger(), "coalesce(wage_category,1)=1")) {
            approvedByCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getBorderPanel(new JComponent[]{appDateSP = new SelectedDateSpinner(),
                new JLabel("From:", SwingConstants.RIGHT),
                fromDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(applicantCB = new JComboBox(applicantCbModel),
                new EmployeeLookupAction(applicantCB)),
                new JLabel("To:", SwingConstants.RIGHT),
                toDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{sickRB = new JRadioButton("Sick Leave"),
                new JLabel("Total days:", SwingConstants.RIGHT),
                totDaysSP = new SelectedNumberSpinner(0, 0, 365, 1)
            }),
            getBorderPanel(new JComponent[]{annualRB = new JRadioButton("Annual Leave")}),
            getBorderPanel(new JComponent[]{specRB = new JRadioButton("Special Leave")}),
            getBorderPanel(new JComponent[]{unpaidRB = new JRadioButton("Unpaid Leave")}),
            getBorderPanel(new JComponent[]{getGridPanel(new JComponent[]{
                    signedRB = new JRadioButton("Y"), notSignedRB = new JRadioButton("No")})}),
            getBorderPanel(new JComponent[]{
                getGridPanel(new JComponent[]{approvedRB = new JRadioButton("Y"), notApprovedRB = new JRadioButton("No")}),
                getBorderPanel(new JComponent[]{new JPanel(), new JLabel("Approved by:", SwingConstants.RIGHT),
                    comboPanelWithLookupBtn(approvedByCB = new JComboBox(approvedByCbModel),
                    approvedLookupAction = new EmployeeLookupAction(approvedByCB, "COALESCE(wage_category,1)=1"))})
            })
        };
        for (JSpinner sp : new JSpinner[]{appDateSP, fromDateSP, toDateSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }
        ButtonGroup yesNoGroup0 = new ButtonGroup();
        yesNoGroup0.add(sickRB);
        yesNoGroup0.add(annualRB);
        yesNoGroup0.add(specRB);
        yesNoGroup0.add(unpaidRB);
        ButtonGroup yesNoGroup1 = new ButtonGroup();
        yesNoGroup1.add(signedRB);
        yesNoGroup1.add(notSignedRB);
        ButtonGroup yesNoGroup2 = new ButtonGroup();
        yesNoGroup2.add(approvedRB);
        yesNoGroup2.add(notApprovedRB);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        JScrollPane sp = new JScrollPane(remarksArea = new JTextArea(8, 40),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        remarksArea.setWrapStyleWord(true);
        remarksArea.setLineWrap(true);
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Comments"));
        add(sp, BorderLayout.CENTER);

        approvedRB.addChangeListener(yesNoApprovedAction());
        fromDateSP.addChangeListener(calcDaysAction());
        toDateSP.addChangeListener(calcDaysAction());
        fromDateSP.setValue(Calendar.getInstance().getTime());
        toDateSP.setValue(Calendar.getInstance().getTime());
    }

    @Override
    public void loadData() {
        Xappforleave xl = (Xappforleave) getDbObject();
        if (xl != null) {
            idField.setText(xl.getXappforleaveId().toString());
            if (xl.getAppdate() != null) {
                appDateSP.setValue(new java.util.Date(xl.getAppdate().getTime()));
            }
            if (xl.getApplicantId() != null) {
                selectComboItem(applicantCB, xl.getApplicantId());
            }
            if (xl.getApprovedbyId() != null) {
                selectComboItem(approvedByCB, xl.getApprovedbyId());
            }
            if (xl.getDays() != null) {
                totDaysSP.setValue(xl.getDays());
            }
            if (xl.getFromdate() != null) {
                fromDateSP.setValue(new java.util.Date(xl.getFromdate().getTime()));
            }
            if (xl.getTodate() != null) {
                toDateSP.setValue(new java.util.Date(xl.getTodate().getTime()));
            }
            sickRB.setSelected(xl.getIsSickleave() != null && xl.getIsSickleave() == 1);
            annualRB.setSelected(xl.getIsAnnualleave() != null && xl.getIsAnnualleave() == 1);
            specRB.setSelected(xl.getIsSpecialleave() != null && xl.getIsSpecialleave() == 1);
            unpaidRB.setSelected(xl.getIsUnpaidleave() != null && xl.getIsUnpaidleave() == 1);
            signedRB.setSelected(xl.getIsSigned() != null && xl.getIsSigned() == 1);
            approvedRB.setSelected(xl.getIsApproved() != null && xl.getIsApproved() == 1);
            notSignedRB.setSelected(!signedRB.isSelected());
            notApprovedRB.setSelected(!approvedRB.isSelected());

            remarksArea.setText(xl.getRemarks());
        }
        enableApprovedComboBox(approvedRB.isSelected());
    }

    @Override
    public boolean save() throws Exception {
        Xappforleave xl = (Xappforleave) getDbObject();
        boolean isNew = false;
        if (xl == null) {
            xl = new Xappforleave(null);
            xl.setXappforleaveId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) appDateSP.getValue();
        if (dt != null) {
            xl.setAppdate(new java.sql.Date(dt.getTime()));
        }
        xl.setApplicantId(getSelectedCbItem(applicantCB));
        xl.setApprovedbyId(getSelectedCbItem(approvedByCB));
        xl.setDays((Integer) totDaysSP.getValue());
        dt = (java.util.Date) fromDateSP.getValue();
        if (dt != null) {
            xl.setFromdate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) toDateSP.getValue();
        if (dt != null) {
            xl.setTodate(new java.sql.Date(dt.getTime()));
        }
        xl.setIsAnnualleave(0);
        xl.setIsSickleave(0);
        xl.setIsSpecialleave(0);
        xl.setIsUnpaidleave(0);
        if (sickRB.isSelected()) {
            xl.setIsSickleave(1);
        } else if (annualRB.isSelected()) {
            xl.setIsAnnualleave(1);
        } else if (specRB.isSelected()) {
            xl.setIsSpecialleave(1);
        } else if (unpaidRB.isSelected()) {
            xl.setIsUnpaidleave(1);
        }
        xl.setIsSigned(signedRB.isSelected() ? 1 : 0);
        xl.setIsApproved(approvedRB.isSelected() ? 1 : 0);
        xl.setRemarks(remarksArea.getText());
        return saveDbRecord(xl, isNew);
    }

    private ChangeListener yesNoApprovedAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                enableApprovedComboBox(approvedRB.isSelected());
            }
        };
    }

    private void enableApprovedComboBox(boolean enable) {
        approvedByCB.setEnabled(enable);
        approvedLookupAction.setEnabled(enable);
        if (!enable) {
            approvedByCB.setSelectedIndex(0);
        }
    }

    private ChangeListener calcDaysAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                Date dtStart = (Date) fromDateSP.getValue();
                Date dtEnd = (Date) toDateSP.getValue();
                if (dtStart != null && dtEnd != null) {
                    Calendar calStart = Calendar.getInstance();
                    Calendar calEnd = Calendar.getInstance();
                    calStart.setTime(dtStart);
                    calEnd.setTime(dtEnd);
                    calStart.set(Calendar.HOUR_OF_DAY, 0);
                    calEnd.set(Calendar.HOUR_OF_DAY, 0);
                    calStart.set(Calendar.MINUTE, 0);
                    calEnd.set(Calendar.MINUTE, 0);
                    calStart.set(Calendar.SECOND, 0);
                    calEnd.set(Calendar.SECOND, 0);
                    calStart.set(Calendar.MILLISECOND, 0);
                    calEnd.set(Calendar.MILLISECOND, 0);
                    int diffDays = (int) ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / (24.0 * 60.0 * 60.0 * 1000.0));
                    totDaysSP.setValue(diffDays);
                }
            }
        };
    }
}
