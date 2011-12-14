package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachine;
import com.xlend.orm.Xmachtype;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
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
    private JSpinner tmvnrTextSP;
//    private JTextField descriptionField;
    private JComboBox machineTypeCB;
    private DefaultComboBoxModel machineTypeCbModel;
    private JComboBox machType2CB;
    private DefaultComboBoxModel machineType2CbModel;
    private JTextField regNrField;
//    private JTextField oldRegNrField;
    private SelectedDateSpinner licenseDateSP;
    private SelectedDateSpinner expDateSP;
    private JComboBox licenseStatusCB;
    private DefaultComboBoxModel licenseStatusCbModel;
    private JFormattedTextField vehicleNrField;
    private JTextField engineNrField;
    private JTextField chassisNrField;
//    private JSpinner classifySP;
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
        for (ComboItem itm : XlendWorks.loadRootMachTypes(DashBoard.getExchanger())) {
            machineTypeCbModel.addElement(itm);
        }
        licenseStatusCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllLicStatuses(DashBoard.getExchanger())) {
            licenseStatusCbModel.addElement(itm);
        }
        machineType2CbModel = new DefaultComboBoxModel();
        String[] titles = new String[]{
            "ID:",
            "Fleet Number:(M)",
            "Reg.Nr:", 
            "Type:", "Type2:",
            "Lic.Date:", "Lic.Status:", "Exp.Date:",
            "VehicleDNr:", "Engine Nr:", "Chassis Nr:",
            "Insurance Nr:", "Insurance Type:", "Insurance Amt:",
            "Deposit Amt:", "Contract Fee:", "Monthly Pay:",
            "Pay Start Date:", "Pay End Date:"
        };
        labels = createLabelsArray(titles);
        edits = new JComponent[]{
            idField = new JTextField(), 
            tmvnrTextSP = new SelectedNumberSpinner(0, 0, 1000, 1),
            machineTypeCB = new JComboBox(machineTypeCbModel), 
            machType2CB = new JComboBox(machineType2CbModel), 
            regNrField = new JTextField(), 
            licenseDateSP = new SelectedDateSpinner(), 
            licenseStatusCB = new JComboBox(licenseStatusCbModel), 
            expDateSP = new SelectedDateSpinner(), 
            vehicleNrField = new JFormattedTextField(createFormatter("UUU###U")), 
            engineNrField = new JTextField(), 
            chassisNrField = new JTextField(), 
            insuranceNrField = new JTextField(), 
            insuranceTypeCB = new JComboBox(Selects.getStringArray(Selects.DISTINCT_INSURANCETYPES)), 
            insurabceAmtSP = new SelectedNumberSpinner(0, 0, 1000000, 10), 
            depositAmtSP = new SelectedNumberSpinner(0, 0, 1000000, 10), 
            contractFeeSP = new SelectedNumberSpinner(0, 0, 1000000, 10), 
            monthlyPaySP = new SelectedNumberSpinner(0, 0, 1000000, 10), 
            payStartDateSP = new SelectedDateSpinner(), 
            payEndDateSP = new SelectedDateSpinner() 
        };
        machineTypeCB.addActionListener(machType2CBreloadAction());
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

        int idx = 0;
        JPanel idPanel = new JPanel(new BorderLayout());
        idPanel.add(idField, BorderLayout.WEST);
        JPanel tmvnrPanel = new JPanel(new BorderLayout());
        tmvnrPanel.add(tmvnrTextSP, BorderLayout.WEST);
        JPanel regNrPanel = new JPanel(new BorderLayout());
        regNrPanel.add(regNrField, BorderLayout.WEST);

        componentRows.add(new JComponent[]{labels[idx++], idPanel, labels[idx++], tmvnrPanel, labels[idx++], regNrPanel});
        componentRows.add(new JComponent[]{labels[idx++], machineTypeCB, labels[idx++], machType2CB, new JPanel(), new JPanel()});
        componentRows.add(new JComponent[]{labels[idx++], licenseDateSP, labels[idx++], licenseStatusCB, labels[idx++], expDateSP});
        componentRows.add(new JComponent[]{labels[idx++], vehicleNrField, labels[idx++], engineNrField, labels[idx++], chassisNrField});
        componentRows.add(new JComponent[]{labels[idx++], insuranceNrField, labels[idx++], insuranceTypeCB, labels[idx++], insurabceAmtSP});
        componentRows.add(new JComponent[]{labels[idx++], depositAmtSP, labels[idx++], contractFeeSP, new JPanel(), new JPanel()});
        componentRows.add(new JComponent[]{labels[idx++], monthlyPaySP, labels[idx++], payStartDateSP, labels[idx++], payEndDateSP});
        idField.setPreferredSize(tmvnrTextSP.getPreferredSize());
        regNrField.setPreferredSize(tmvnrTextSP.getPreferredSize());
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

    private ActionListener machType2CBreloadAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem tp1 = (ComboItem) machineTypeCB.getSelectedItem();
                machineType2CbModel.removeAllElements();
                if (tp1!=null) {
                    try {
                        DbObject[] tp2list = DashBoard.getExchanger().getDbObjects(
                                Xmachtype.class, "parenttype_id="+tp1.getId(), "machtype");
                        for (DbObject tp2 : tp2list) {
                            Xmachtype type2 = (Xmachtype) tp2;
                            machineType2CbModel.addElement(
                                    new ComboItem(type2.getXmachtypeId(), type2.getMachtype()));
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                    }
                }
            }
            
        };
    }
}
