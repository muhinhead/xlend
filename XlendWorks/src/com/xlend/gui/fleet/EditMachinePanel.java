package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachine;
import com.xlend.orm.Xmachtype;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
class EditMachinePanel extends EditPanelWithPhoto {

    private JTextField idField;
    private JSpinner tmvnrTextSP;
    private JComboBox machineTypeCB;
    private DefaultComboBoxModel machineTypeCbModel;
    private JComboBox machType2CB;
    private DefaultComboBoxModel machineType2CbModel;
    private JTextField regNrField;
//    private SelectedDateSpinner licenseDateSP;
    private JCheckBox licensedChB;
    private SelectedDateSpinner expDateSP;
    private JLabel licenseStatusLBL;
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
    private boolean licensed;

    public EditMachinePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        machineTypeCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadRootMachTypes(DashBoard.getExchanger())) {
            machineTypeCbModel.addElement(itm);
        }
        licenseStatusCbModel = new DefaultComboBoxModel(new String[]{
                    "Current", "2 Month Warning", "Dealer Stock", "Expired"
                });
        machineType2CbModel = new DefaultComboBoxModel();
        String[] titles = new String[]{
            "ID:",
            "Fleet Number:(" + getFleetNumberChar() + ")",
            "Reg.Nr:",
            "Type:", "Type2:",
            "Licensed:", "Lic.Exp.Date:", "Lic.Status:",
            "Vehicle Nr:", "Engine Nr:", "Chassis Nr:",
            "Insurance Nr:", "Insurance Type:", "Insurance Amt:",
            "Deposit Amt:", "Contract Fee:", "Monthly Pay:",
            "Pay Start Date:", "Pay End Date:"
        };
        labels = createLabelsArray(titles);
        insurabceAmtSP = new SelectedNumberSpinner(0, 0, 100000, 10);
        depositAmtSP = new SelectedNumberSpinner(0, 0, 100000, 10);
        contractFeeSP = new SelectedNumberSpinner(0, 0, 100000, 10);
        monthlyPaySP = new SelectedNumberSpinner(0, 0, 100000, 10);
        edits = new JComponent[]{
            idField = new JTextField(),
            tmvnrTextSP = new SelectedNumberSpinner(0, 0, 1000, 1),
            machineTypeCB = new JComboBox(machineTypeCbModel),
            machType2CB = new JComboBox(machineType2CbModel),
            regNrField = new JTextField(),
            //            licenseDateSP = new SelectedDateSpinner(), 
            licensedChB = new JCheckBox("", true),
            licenseStatusLBL = new JLabel("Current"),//JComboBox(licenseStatusCbModel),
            expDateSP = new SelectedDateSpinner(),
            vehicleNrField = new JFormattedTextField(createFormatter("UUU###U")),
            engineNrField = new JTextField(),
            chassisNrField = new JTextField(),
            insuranceNrField = new JTextField(),
            insuranceTypeCB = new JComboBox(Selects.getStringArray(Selects.DISTINCT_INSURANCETYPES)),
            insurabceAmtSP,
            depositAmtSP,
            contractFeeSP,
            monthlyPaySP,
            payStartDateSP = new SelectedDateSpinner(),
            payEndDateSP = new SelectedDateSpinner()
        };

        expDateSP.addChangeListener(expDateSPchangeListener());
        licensedChB.addActionListener(licensedChBaction());
        licenseStatusLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
//        licenseStatusLBL.setForeground(Color.green);
        machineTypeCB.addActionListener(machType2CBreloadAction());
//        Dimension prefs = licenseStatusCB.getEditor().getEditorComponent().getPreferredSize();
//        licenseStatusCB.getEditor().getEditorComponent().setMaximumSize(new Dimension(100,prefs.height));
        insuranceTypeCB.setEditable(true);
        for (SelectedDateSpinner sp : new SelectedDateSpinner[]{
                    expDateSP, payStartDateSP, payEndDateSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
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
        componentRows.add(new JComponent[]{labels[idx++], licensedChB, labels[idx++], expDateSP, labels[idx++], licenseStatusLBL});
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
        Xmachine machine = (Xmachine) getDbObject();
        if (machine != null) {
            Date dt;
            idField.setText(machine.getXmachineId().toString());
            labels[1].setText("Fleet Number:(" + machine.getChassisNr() == null ? "?" : machine.getChassisNr() + ")");
            chassisNrField.setText(machine.getChassisNr());
            contractFeeSP.setValue(machine.getContractFee() == null ? 0 : machine.getContractFee());
            depositAmtSP.setValue(machine.getDepositAmt() == null ? 0 : machine.getDepositAmt());
            engineNrField.setText(machine.getEngineNr());
            if (machine.getExpdate() != null) {
                dt = new Date(machine.getExpdate().getTime());
                expDateSP.setValue(dt);
            }
            insurabceAmtSP.setValue(machine.getInsuranceAmt() == null ? 0 : machine.getInsuranceAmt());
            insuranceNrField.setText(machine.getInsuranceNr());
            insuranceTypeCB.setSelectedItem(machine.getInsuranceTp());
            monthlyPaySP.setValue(machine.getMonthlyPay() == null ? 0 : machine.getMonthlyPay());
            if (machine.getPaystart() != null) {
                dt = new Date(machine.getPaystart().getTime());
                payStartDateSP.setValue(dt);
            }
            if (machine.getPayend() != null) {
                dt = new Date(machine.getPayend().getTime());
                payEndDateSP.setValue(dt);
            }
            imageData = (byte[]) machine.getPhoto();
            setImage(imageData);
            regNrField.setText(machine.getRegNr());
            if (machine.getTmvnr() != null) {
                tmvnrTextSP.setValue(Integer.parseInt(machine.getTmvnr()));
            } else {
                tmvnrTextSP.setValue(0);
            }
            vehicleNrField.setText(machine.getVehicleidNr());
            machineTypeCB.setSelectedItem(labels);
            if (machine.getXmachtypeId() != null) {
                selectComboItem(machineTypeCB, machine.getXmachtypeId());
            }
            if (machine.getXmachtype2Id() != null) {
                selectComboItem(machType2CB, machine.getXmachtype2Id());
            }
            licensedChB.setSelected(licensed = (machine.getExpdate() != null));
            adjustLicenseFierlds();
            repaintLicFields();
        }
    }

    protected String getFleetNumberChar() {
        return "M";
    }

    protected void setClassify(Xmachine machine) throws SQLException, ForeignKeyViolationException {
        machine.setClassify(getFleetNumberChar());
    }

    @Override
    public boolean save() throws Exception {
        Date dt;
        Xmachine machine = (Xmachine) getDbObject();
        boolean isNew = false;
        if (machine == null) {
            machine = new Xmachine(null);
            machine.setXmachineId(0);
            isNew = true;
        }
        machine.setChassisNr(chassisNrField.getText());
        machine.setContractFee((Integer) contractFeeSP.getValue());
        setClassify(machine);
        machine.setDepositAmt((Integer) depositAmtSP.getValue());
        machine.setEngineNr(engineNrField.getText());
        if (!licensed) {
            machine.setExpdate(null);
        } else {
            dt = (Date) expDateSP.getValue();
            machine.setExpdate(dt == null ? null : new java.sql.Date(dt.getTime()));
        }
        machine.setInsuranceAmt((Integer) insurabceAmtSP.getValue());
        machine.setInsuranceNr(insuranceNrField.getText());
        machine.setInsuranceTp((String) insuranceTypeCB.getSelectedItem());
        machine.setMonthlyPay((Integer) monthlyPaySP.getValue());
        dt = (Date) payStartDateSP.getValue();
        machine.setPaystart(dt == null ? null : new java.sql.Date(dt.getTime()));
        dt = (Date) payEndDateSP.getValue();
        machine.setPayend(dt == null ? null : new java.sql.Date(dt.getTime()));
        machine.setRegNr(regNrField.getText());
        machine.setTmvnr(tmvnrTextSP.getValue().toString());
        machine.setVehicleidNr(vehicleNrField.getText());
        ComboItem ci = (ComboItem) machineTypeCB.getSelectedItem();
        machine.setXmachtypeId(ci == null ? null : ci.getId());
        ci = (ComboItem) machType2CB.getSelectedItem();
        machine.setXmachtype2Id(ci == null ? null : ci.getId());
        machine.setPhoto(imageData);
        try {
            machine.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(machine);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
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
                if (tp1 != null) {
                    try {
                        DbObject[] tp2list = DashBoard.getExchanger().getDbObjects(
                                Xmachtype.class, "parenttype_id=" + tp1.getId(), "machtype");
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

    private ActionListener licensedChBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                licensed = licensedChB.isSelected();
                adjustLicenseFierlds();
            }
        };
    }

    private void adjustLicenseFierlds() {
        expDateSP.setVisible(licensed);
        licenseStatusLBL.setVisible(licensed);
        labels[6].setVisible(licensed);
        labels[7].setVisible(licensed);
    }

    private void repaintLicFields() {
        Date expDt = (Date) expDateSP.getValue();
        Date today = Calendar.getInstance().getTime();
        long diff = (expDt.getTime() - today.getTime()) / (1000 * 3600 * 24);
        licenseStatusLBL.setForeground(Color.black);
        licenseStatusLBL.setBackground(Color.white);
        if (expDt.before(today)) {
            licenseStatusLBL.setForeground(Color.red);
            licenseStatusLBL.setText("Expired");
        } else if (diff <= 60) {
            licenseStatusLBL.setForeground(Color.blue);
            licenseStatusLBL.setText("2 Month Warning");
        } else {
            licenseStatusLBL.setText("Current");
        }
    }

    private ChangeListener expDateSPchangeListener() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                repaintLicFields();
            }
        };
    }
}
