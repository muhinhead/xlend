package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachine;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;

/**
 *
 * @author nick
 */
class EditMachinePanel extends EditPanelWithPhoto {

    private JTextField idField;
    private JFormattedTextField tmvnrTextField;
    private JTextField descriptionField;
    private JComboBox machineTypeCB;
    private DefaultComboBoxModel machineTypeCbModel;
    private JTextField type2Field;
    private JTextField regNrField;
    private JTextField oldRegNrField;
    private SelectedDateSpinner licenseDateSP;
    private SelectedDateSpinner expDateSP;
    private JComboBox licenseStatusCB;
    private DefaultComboBoxModel licenseStatusCbModel;
    private JFormattedTextField vehicleNrField;
    private JTextField engineNrField;
    private JTextField chassisNrField;
    private JSpinner classifySP;
    private JTextField insuranceNrField;
    private JComboBox insuranceTypeCB;
    private JSpinner insurabceAmtSP;
    private JSpinner depositAmtSP;
    private JSpinner contractFeeSP;
    private JSpinner monthlyPaySP;
    private SelectedDateSpinner payStartDateSP;
    private SelectedDateSpinner payEndDateSP;
    private JTabbedPane tabbedPane;

    public EditMachinePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        machineTypeCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllMachTypes(DashBoard.getExchanger())) {
            machineTypeCbModel.addElement(itm);
        }
        licenseStatusCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllLicStatuses(DashBoard.getExchanger())) {
            licenseStatusCbModel.addElement(itm);
        }
        String[] titles = new String[]{
            "ID:",
            "Classify:", "TMVnr:", "Description:",
            "Type:", "Type2:",
            "Reg.Nr:", "Old Reg.Nr:",
            "Lic.Date:", "Lic.Status:", "Exp.Date:",
            "VehicleDNr:", "Engine Nr:", "Chassis Nr:",
            "Insurance Nr:", "Insurance Type:", "Insurance Amt:",
            "Deposit Amt:", "Contract Fee:", "Monthly Pay:",
            "Pay Start Date:", "Pay End Date:"
        };
        labels = createLabelsArray(titles);
        edits = new JComponent[]{
            idField = new JTextField(), //1
            classifySP = new JSpinner(new SpinnerListModel(new String[]{"M", "T", "V", "Z"})), //2
            tmvnrTextField = new JFormattedTextField(createFormatter("U##")), //2
            descriptionField = new JTextField(40), //3
            machineTypeCB = new JComboBox(machineTypeCbModel), //4
            type2Field = new JTextField(), //4
            regNrField = new JTextField(), //5
            oldRegNrField = new JTextField(), //5
            licenseDateSP = new SelectedDateSpinner(), //6
            licenseStatusCB = new JComboBox(licenseStatusCbModel), //6
            expDateSP = new SelectedDateSpinner(), //6
            vehicleNrField = new JFormattedTextField(createFormatter("UUU###U")), //7
            engineNrField = new JTextField(), //7
            chassisNrField = new JTextField(), //7
            insuranceNrField = new JTextField(), //8
            insuranceTypeCB = new JComboBox(Selects.getStringArray(Selects.DISTINCT_INSURANCETYPES)), //8
            insurabceAmtSP = new SelectedNumberSpinner(0, 0, 1000000, 10), //8
            depositAmtSP = new SelectedNumberSpinner(0, 0, 1000000, 10), //9
            contractFeeSP = new SelectedNumberSpinner(0, 0, 1000000, 10), //9
            monthlyPaySP = new SelectedNumberSpinner(0, 0, 1000000, 10), //9
            payStartDateSP = new SelectedDateSpinner(), //10
            payEndDateSP = new SelectedDateSpinner() //10
        };
//        Dimension prefs = licenseStatusCB.getEditor().getEditorComponent().getPreferredSize();
//        licenseStatusCB.getEditor().getEditorComponent().setMaximumSize(new Dimension(100,prefs.height));
        insuranceTypeCB.setEditable(true);
        for (SelectedDateSpinner sp : new SelectedDateSpinner[]{
                    licenseDateSP, expDateSP, payStartDateSP, payEndDateSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "yyyy/MM/dd"));
            Util.addFocusSelectAllAction(sp);
        }
        idField.setEditable(false);

        ArrayList<JComponent[]> componentRows = new ArrayList<JComponent[]>();
        
        componentRows.add(new JComponent[]{labels[0], idField, labels[1],
                    new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel(), new JPanel()});
        componentRows.add(new JComponent[]{labels[1], classifySP, labels[2], tmvnrTextField,
                    labels[6], regNrField, labels[7], oldRegNrField});
        componentRows.add(new JComponent[]{labels[3], descriptionField});
        componentRows.add(new JComponent[]{labels[4], machineTypeCB, labels[5], type2Field, new JPanel(), new JPanel()});
        componentRows.add(new JComponent[]{labels[8], licenseDateSP, labels[9], licenseStatusCB, labels[10], expDateSP});
        componentRows.add(new JComponent[]{labels[11], vehicleNrField, labels[12], engineNrField, labels[13], chassisNrField});
        componentRows.add(new JComponent[]{labels[14], insuranceNrField, labels[15], insuranceTypeCB, labels[16], insurabceAmtSP});
        componentRows.add(new JComponent[]{labels[17], depositAmtSP, labels[18], contractFeeSP, new JPanel(), new JPanel()});
        componentRows.add(new JComponent[]{labels[19], monthlyPaySP, labels[20], payStartDateSP, labels[21], payEndDateSP});

        organizePanels(componentRows);

        add(getTabbedPanel(), BorderLayout.CENTER);
    }

    protected void organizePanels(ArrayList<JComponent[]> componentRows) {
        super.organizePanels(componentRows.size(), componentRows.size());
        for (JComponent[] compLine : componentRows) {
            lblPanel.add(compLine[0]);
            if (compLine.length > 2) {
                JPanel linePanel = new JPanel(new GridLayout(1, compLine.length - 1));
                for (int c = 1; c < compLine.length; c++) {
                    linePanel.add(compLine[c]);
                }
                editPanel.add(linePanel);
            } else {
                editPanel.add(compLine[1]);
            }
        }
    }

    @Override
    protected JComponent getRightUpperPanel() {
        return new JPanel();
    }

    @Override
    public void loadData() {
//        Xmachine machine = (Xmachine) getDbObject();
//        if (machine != null) {
//            Date dt;
//            idField.setText(machine.getXmachineId().toString());
//            classifySP.setValue(machine.getClassify());
//            tmvnrTextField.setText(machine.getTmvnr());
//            regNrField.setText(machine.getRegNr());
//            oldRegNrField.setText(machine.getOldregNr());
//        }
    }

    @Override
    public boolean save() throws Exception {
//        throw new UnsupportedOperationException("Not supported yet.");
        return false;
    }

    private JComponent getTabbedPanel() {
        tabbedPane = new JTabbedPane();
        tabbedPane.add(getPicPanel(), "Photo");
        tabbedPane.setPreferredSize(new Dimension(tabbedPane.getPreferredSize().width, 400));
        return tabbedPane;
    }
}
