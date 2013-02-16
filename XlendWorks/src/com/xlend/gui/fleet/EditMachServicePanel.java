package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xmachservice;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachServicePanel extends RecordEditPanel {

    private DefaultComboBoxModel servicedByCbModel;
    private DefaultComboBoxModel assistedByCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox servicedByCB;
    private JComboBox assistedByCB;
    private JComboBox machineCB;
    private SelectedDateSpinner serviceDateSP;
    private SelectedDateSpinner entryDateSP;
    private JTextField idField;
    private JLabel machTyleLBL;
    private JCheckBox engineOilCB;
    private JCheckBox hydraulicOilCB;
    private JCheckBox brakeFluidCB;
    private JCheckBox transmissionOilCB;
    private JCheckBox gearboxOilCB;
    private JCheckBox antiFreezeCB;
    private JCheckBox diffCheck1CB;
    private JCheckBox diffCheck2CB;
    private JCheckBox diffCheck3CB;
    private JTextField engineOilField;
    private JTextField hydraulicOilField;
    private JTextField brakeFluidField;
    private JTextField transmissionOilField;
    private JTextField gearboxOilField;
    private JTextField antiFreezeField;
    private JTextField diffCheck1Field;
    private JTextField diffCheck2Field;
    private JTextField diffCheck3Field;

    public EditMachServicePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date of Service:", //"Date of Entry:"
            "Serviced by:",
            "Assisted by:",
            "Machine/Truck/Other:", //"machine type"
            ""
        };
        servicedByCbModel = new DefaultComboBoxModel();
        assistedByCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        servicedByCbModel.addElement(new ComboItem(0, "--select employee here--"));
        assistedByCbModel.addElement(new ComboItem(0, "--select employee here--"));
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            servicedByCbModel.addElement(ci);
            assistedByCbModel.addElement(ci);
        }
        machineCbModel.addElement(new ComboItem(0, "--select machine here--"));
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{serviceDateSP = new SelectedDateSpinner()}),
                getBorderPanel(new JComponent[]{new JPanel(),
                    new JLabel("Date of Entry:", SwingConstants.RIGHT),
                    entryDateSP = new SelectedDateSpinner()})
            }),
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(servicedByCB = new JComboBox(servicedByCbModel),
                new EmployeeLookupAction(servicedByCB)), new JPanel()}),
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(assistedByCB = new JComboBox(assistedByCbModel),
                new EmployeeLookupAction(assistedByCB)), new JPanel()}),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
                machTyleLBL = new JLabel("")
            }),
            new JPanel()
        };
        machTyleLBL.setBorder(BorderFactory.createEtchedBorder());

        serviceDateSP.setEditor(new JSpinner.DateEditor(serviceDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(serviceDateSP);
        entryDateSP.setEditor(new JSpinner.DateEditor(entryDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(entryDateSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel centerShell = new JPanel(new BorderLayout());
        JPanel checkBoxPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JPanel descrPanel = new JPanel(new GridLayout(9, 1, 10, 10));
        JPanel downGridShell = new JPanel(new BorderLayout());
        centerShell.add(downGridShell, BorderLayout.NORTH);
        downGridShell.add(checkBoxPanel, BorderLayout.WEST);
        downGridShell.add(descrPanel, BorderLayout.CENTER);
        add(centerShell);

        checkBoxPanel.add(engineOilCB = new JCheckBox("Engine Oil"));
        descrPanel.add(engineOilField = new JTextField());
        checkBoxPanel.add(hydraulicOilCB = new JCheckBox("Hydraulic Oil"));
        descrPanel.add(hydraulicOilField = new JTextField());
        checkBoxPanel.add(brakeFluidCB = new JCheckBox("Brake Fluid"));
        descrPanel.add(brakeFluidField = new JTextField());
        checkBoxPanel.add(transmissionOilCB = new JCheckBox("Transmission Oil"));
        descrPanel.add(transmissionOilField = new JTextField());
        checkBoxPanel.add(gearboxOilCB = new JCheckBox("Gearbox Oil"));
        descrPanel.add(gearboxOilField = new JTextField());
        checkBoxPanel.add(antiFreezeCB = new JCheckBox("Anti Freeze"));
        descrPanel.add(antiFreezeField = new JTextField());
        checkBoxPanel.add(diffCheck1CB = new JCheckBox("1st Diff Checked"));
        descrPanel.add(diffCheck1Field = new JTextField());
        checkBoxPanel.add(diffCheck2CB = new JCheckBox("2nd Diff Checked"));
        descrPanel.add(diffCheck2Field = new JTextField());
        checkBoxPanel.add(diffCheck3CB = new JCheckBox("3rd Diff Checked"));
        descrPanel.add(diffCheck3Field = new JTextField());
        setCBaction(engineOilCB, engineOilField);
        setCBaction(hydraulicOilCB, hydraulicOilField);
        setCBaction(brakeFluidCB, brakeFluidField);
        setCBaction(transmissionOilCB, transmissionOilField);
        setCBaction(gearboxOilCB, gearboxOilField);
        setCBaction(antiFreezeCB, antiFreezeField);
        setCBaction(diffCheck1CB, diffCheck1Field);
        setCBaction(diffCheck2CB, diffCheck2Field);
        setCBaction(diffCheck3CB, diffCheck3Field);
        machineCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String machTylpe = XlendWorks.getMachineType1(DashBoard.getExchanger(), getSelectedCbItem(machineCB));
                machTyleLBL.setText(machTylpe);
            }
        });
    }

    private static void setCBaction(final JCheckBox cb, final JTextField tf) {
        tf.setEnabled(false);
        cb.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setEnabled(cb.isSelected());
                if (!cb.isSelected()) {
                    tf.setText("");
                }
            }
        });
    }

    private static void selectCb4Field(String sValue, JCheckBox cb, JTextField fld) {
        boolean notEmpty = (sValue != null && sValue.trim().length() > 0);
        fld.setText(notEmpty ? sValue : "");
        fld.setEnabled(notEmpty);
        cb.setSelected(notEmpty);
    }

    @Override
    public void loadData() {
        Xmachservice ms = (Xmachservice) getDbObject();
        if (ms != null) {
            Date dt;
            idField.setText(ms.getXmachserviceId().toString());
            if (ms.getServicedate() != null) {
                dt = new Date(ms.getServicedate().getTime());
                serviceDateSP.setValue(dt);
            }
            if (ms.getEntrydate() != null) {
                dt = new Date(ms.getEntrydate().getTime());
                entryDateSP.setValue(dt);
            }
            selectComboItem(servicedByCB, ms.getServicedbyId());
            selectComboItem(assistedByCB, ms.getAssistedbyId());
            selectComboItem(machineCB, ms.getXmachineId());
            selectCb4Field(ms.getEngineOil(), engineOilCB, engineOilField);
            selectCb4Field(ms.getHydraulicOil(), hydraulicOilCB, hydraulicOilField);
            selectCb4Field(ms.getBrakeFluid(), brakeFluidCB, brakeFluidField);
            selectCb4Field(ms.getTransmissionOil(), transmissionOilCB, transmissionOilField);
            selectCb4Field(ms.getGearboxOil(), gearboxOilCB, gearboxOilField);
            selectCb4Field(ms.getAntiFreeze(), antiFreezeCB, antiFreezeField);
            selectCb4Field(ms.getDiffChecked1(), diffCheck1CB, diffCheck1Field);
            selectCb4Field(ms.getDiffChecked2(), diffCheck2CB, diffCheck2Field);
            selectCb4Field(ms.getDiffChecked3(), diffCheck3CB, diffCheck3Field);
            machTyleLBL.setText(XlendWorks.getMachineType1(DashBoard.getExchanger(),
                    getSelectedCbItem(machineCB)));
        }
    }

    @Override
    public boolean save() throws Exception {
        if (getSelectedCbItem(machineCB) == null) {
            GeneralFrame.errMessageBox("Attention!", "Select serviced machine please");
            machineCB.requestFocus();
            return false;
        }
        if (getSelectedCbItem(servicedByCB) == null) {
            GeneralFrame.errMessageBox("Attention!", "Select employee here please");
            servicedByCB.requestFocus();
            return false;
        }
        if (getSelectedCbItem(assistedByCB) == null) {
            GeneralFrame.errMessageBox("Attention!", "Select employee here please");
            assistedByCB.requestFocus();
            return false;
        }
        Xmachservice ms = (Xmachservice) getDbObject();
        boolean isNew = false;
        if (ms == null) {
            ms = new Xmachservice(null);
            ms.setXmachserviceId(0);
            isNew = true;
        }
        Date dt = (Date) serviceDateSP.getValue();
        if (dt != null) {
            ms.setServicedate(new java.sql.Date(dt.getTime()));
        }
        dt = (Date) entryDateSP.getValue();
        if (dt != null) {
            ms.setEntrydate(new java.sql.Date(dt.getTime()));
        }
        ms.setServicedbyId(getSelectedCbItem(servicedByCB));
        ms.setAssistedbyId(getSelectedCbItem(assistedByCB));
        ms.setXmachineId(getSelectedCbItem(machineCB));
        ms.setEngineOil(engineOilField.getText());
        ms.setHydraulicOil(hydraulicOilField.getText());
        ms.setBrakeFluid(brakeFluidField.getText());
        ms.setTransmissionOil(transmissionOilField.getText());
        ms.setGearboxOil(gearboxOilField.getText());
        ms.setAntiFreeze(antiFreezeField.getText());
        ms.setDiffChecked1(diffCheck1Field.getText());
        ms.setDiffChecked2(diffCheck2Field.getText());
        ms.setDiffChecked3(diffCheck3Field.getText());
        return saveDbRecord(ms, isNew);
    }
}
