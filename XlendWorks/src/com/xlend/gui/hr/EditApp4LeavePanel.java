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
import javax.swing.*;

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
    private SelectedDateSpinner fromDateSP;
    private SelectedDateSpinner toDateSP;
    private JRadioButton sickRB;
    private JRadioButton annualRB;
    private JRadioButton specRB;
    private JRadioButton unpaidRB;
    private JSpinner totDaysSP;
    private JRadioButton signedRB;
    private JRadioButton notSignedRB;
    private JRadioButton approvedRB;
    private JRadioButton notApprovedRB;

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
            approvedByCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getBorderPanel(new JComponent[]{appDateSP = new SelectedDateSpinner(),
                new JLabel("From:", SwingConstants.RIGHT),
                fromDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(applicantCB = new JComboBox(applicantCbModel), new EmployeeLookupAction(applicantCB)),
                new JLabel("To:", SwingConstants.RIGHT),
                toDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{sickRB = new JRadioButton("Sick Leave"),
                new JLabel("Total days:", SwingConstants.RIGHT),
                totDaysSP = new SelectedNumberSpinner(0, 0, 365, 1)}),
            //            getBorderPanel(new JComponent[]{sickCB = new JCheckBox("Sick Leave")}),
            getBorderPanel(new JComponent[]{annualRB = new JRadioButton("Annual Leave")}),
            getBorderPanel(new JComponent[]{specRB = new JRadioButton("Special Leave")}),
            getBorderPanel(new JComponent[]{unpaidRB = new JRadioButton("Unpaid Leave")}),
            getBorderPanel(new JComponent[]{getGridPanel(new JComponent[]{
                signedRB = new JRadioButton("Y"), notSignedRB = new JRadioButton("No")})}),
            getBorderPanel(new JComponent[]{
                getGridPanel(new JComponent[]{approvedRB = new JRadioButton("Y"), notApprovedRB = new JRadioButton("No")}),
                getBorderPanel(new JComponent[]{new JPanel(), new JLabel("Approved by:", SwingConstants.RIGHT),
                    comboPanelWithLookupBtn(approvedByCB = new JComboBox(approvedByCbModel), new EmployeeLookupAction(approvedByCB))})
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
    }

    @Override
    public void loadData() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean save() throws Exception {
        Xappforleave xa = (Xappforleave) getDbObject();
        boolean isNew = false;
        if (xa == null) {
            xa = new Xappforleave(null);
            xa.setXappforleaveId(0);
            isNew = true;
        }

        return false;
    }
}
