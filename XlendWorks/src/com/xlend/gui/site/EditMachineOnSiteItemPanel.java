package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xmachineonsite;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditMachineOnSiteItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JComboBox machineCB;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox employeeCB;
    private DefaultComboBoxModel employeeCbModel;
    private JSpinner estDateSpin;
    private JCheckBox workingCB;
    private JSpinner deestDateSpin;
    private Xsite xsite;
//    private AbstractAction machineLookupAction;
//    private AbstractAction employeeLookupAction;

    public EditMachineOnSiteItemPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadMachines(DashBoard.getExchanger(), getDbObject() == null)) {
            machineCbModel.addElement(itm);
        }
        employeeCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadEmployees(DashBoard.getExchanger(), getDbObject() == null)) {
            employeeCbModel.addElement(itm);
        }
        String titles[] = new String[]{
            "ID:", "Fleet#:", "Operator:", "Est.Date:", "Working:", "De-Est.Date:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 3),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), 
                new MachineLookupAction(machineCB, "m.classify in ('M','T')")),
            comboPanelWithLookupBtn(employeeCB = new JComboBox(employeeCbModel),
                new EmployeeLookupAction(employeeCB)),
            getGridPanel(estDateSpin = new SelectedDateSpinner(), 2),
            workingCB = new JCheckBox(),
            getGridPanel(deestDateSpin = new SelectedDateSpinner(), 2)
        };
        idField.setEnabled(false);
        labels = createLabelsArray(titles);
        estDateSpin.setEditor(new JSpinner.DateEditor(estDateSpin, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(estDateSpin);
        deestDateSpin.setEditor(new JSpinner.DateEditor(deestDateSpin, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(deestDateSpin);

        workingCB.setSelected(true);
        deestDateSpin.setVisible(false);
        labels[5].setVisible(false);
        workingCB.addActionListener(getWorkingCBaction());

        organizePanels(titles, edits);
        setPreferredSize(new Dimension(350, getPreferredSize().height));
    }

    protected void organizePanels(String[] titles, JComponent[] edits) {
        super.organizePanels(titles.length, edits.length);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length; i++) {
            lblPanel.add(labels[i]);
        }
        for (int i = 0; i < edits.length; i++) {
            editPanel.add(edits[i]);
        }
    }

    @Override
    public void loadData() {
        Xmachineonsite mos = (Xmachineonsite) getDbObject();
        if (mos != null) {
            idField.setText(mos.getXmachineonsateId().toString());
            if (mos.getXmachineId() != null) {
                selectComboItem(machineCB, mos.getXmachineId());
            }
            if (mos.getXemployeeId() != null) {
                selectComboItem(employeeCB, mos.getXemployeeId());
            }
            Date dt = (Date) mos.getEstdate();
            if (dt != null) {
                estDateSpin.setValue(dt);
            }
            dt = (Date) mos.getDeestdate();
            workingCB.setSelected(dt == null);
            deestDateSpin.setVisible(dt != null);
            labels[5].setVisible(dt != null);
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean valid = true;
        Xmachineonsite mos = (Xmachineonsite) getDbObject();
        Date dt1, dt2;
        boolean isNew = false;
        if (mos == null) {
            mos = new Xmachineonsite(null);
            mos.setXmachineonsateId(0);
            mos.setXsiteId(xsite.getXsiteId());
            isNew = true;
        }
        try {
            ComboItem ci = (ComboItem) machineCB.getSelectedItem();
            mos.setXmachineId(ci == null ? null : ci.getId());
            ci = (ComboItem) employeeCB.getSelectedItem();
            mos.setXemployeeId(ci == null ? null : ci.getId());
            dt1 = (java.util.Date) estDateSpin.getValue();
            mos.setEstdate(dt1 == null ? null : new java.sql.Date(dt1.getTime()));
            if (workingCB.isSelected()) {
                mos.setDeestdate(null);
            } else {
                dt2 = (java.util.Date) deestDateSpin.getValue();
                mos.setDeestdate(dt2 == null ? null : new java.sql.Date(dt2.getTime()));
                if (dt2.before(dt1)) {
                    valid = false;
                    throw new Exception("Est.date couldn't be after De-Est.date!");
                }
            }
            if (!isNew) {
                DbObject[] others = DashBoard.getExchanger().getDbObjects(
                        Xmachineonsite.class,
                        "xmachineonsate_id<>" + idField.getText()
                        + " and xemployee_id=" + mos.getXemployeeId(), null);
                if (others.length > 0) {
                    valid = false;
                    throw new Exception("Operator " + employeeCB.getSelectedItem() + " already works on another site or machine");
                }
                others = DashBoard.getExchanger().getDbObjects(
                        Xmachineonsite.class,
                        "xmachineonsate_id<>" + idField.getText()
                        + " and xmachine_id=" + mos.getXmachineId(), null);
                if (others.length > 0) {
                    valid = false;
                    throw new Exception("Machine " + machineCB.getSelectedItem() + " already works on another site");
                }
            }
            DbObject saved = DashBoard.getExchanger().saveDbObject(mos);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            if (valid) {
                XlendWorks.log(ex);
            }
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

    protected void setXsite(Xsite xsite) {
        this.xsite = xsite;
    }

    private ActionListener getWorkingCBaction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                deestDateSpin.setVisible(!workingCB.isSelected());
                labels[5].setVisible(!workingCB.isSelected());
            }
        };
    }

//    private AbstractAction machinesLookup() {
//        return new AbstractAction("...") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    LookupDialog ld = new LookupDialog("Machines Lookup", machineCB,
//                            new MachineGrid(DashBoard.getExchanger(), 
//                                    Selects.SELECT_MASCHINES4LOOKUP+" and m.classify in ('M','T')", false),
//                            new String[]{"tmvnr", "reg_nr"});
//                } catch (RemoteException ex) {
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
//    }
}
