/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployee;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author nick
 */
class EditEmployeePanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel cbModel;
    private JTextField idField;
    private JTextField clockNumField;
    private JTextField firstNameField;
    private JTextField surNameField;
    private JTextField nickNameField;
    private JTextField idNumField;
    private JTextField phone0NumField;
    private JTextField phone1NumField;
    private JTextField phone2NumField;
    private JTextField relation1Field;
    private JTextField relation2Field;
    private JTextField addressField;
    private JComboBox contractLenCB;
    private JSpinner contractStartSP;
    private JSpinner contractEndSP;
    private JSpinner rateSP;
    private JComboBox positionCB;

    public EditEmployeePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        cbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllPositions(DashBoard.getExchanger())) {
            cbModel.addElement(itm);
        }
        String[] titles = new String[]{
            "ID:", "Clock Number:", "Name:", "Surname:",
            "SA ID or Passport Number:", "Nickname:",
            "Phone Number:",
            "Alternative Number 1:", " Relation to person:",
            "Alternative Number 2:", " Relation to person:",
            "Home Address:", "Work Position:",
            "Contract Duration:", "Contract Start Date:", "Contract End Date:",
            "Rate of Pay:"
        };
        labels = createLabelsArray(titles);
        ComboItem[] durations = new ComboItem[]{
            new ComboItem(1, "1 month"),
            new ComboItem(2, "2 month"),
            new ComboItem(3, "3 month"),
            new ComboItem(6, "6 month"),
            new ComboItem(12, "1 year"),
            new ComboItem(0, "Permanent"),
        };
        edits = new JComponent[]{
            idField = new JTextField(),
            clockNumField = new JTextField(),
            firstNameField = new JTextField(),
            surNameField = new JTextField(20),
            idNumField = new JTextField(),
            nickNameField = new JTextField(),
            phone0NumField = new JTextField(),
            phone1NumField = new JTextField(),
            relation1Field = new JTextField(),
            phone2NumField = new JTextField(),
            relation2Field = new JTextField(),
            addressField = new JTextField(),
            positionCB = new JComboBox(cbModel),
            contractLenCB = new JComboBox(durations),
            contractStartSP = new JSpinner(new SpinnerDateModel()),
            contractEndSP = new JSpinner(new SpinnerDateModel()),
            rateSP = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 10))
        };
        setEndDateVisible(true);

        idField.setEditable(false);
        organizePanels(13, 13);
        for (int i = 0; i < 7; i++) {
            lblPanel.add(labels[i]);
            if (i == 0 || i == 1 || i == 4 || i == 6) {
                JPanel idPanel = new JPanel(new GridLayout(1, 3, 5, 5));
                idPanel.add(edits[i]);
                for (int j = 1; j < 3; j++) {
                    idPanel.add(new JPanel());
                }
                editPanel.add(idPanel);
            } else {
                editPanel.add(edits[i]);
            }
        }
        lblPanel.add(labels[7]);

        JPanel alt1 = new JPanel(new GridLayout(1, 3, 5, 5));
        alt1.add(edits[7]);
        alt1.add(labels[8]);
        alt1.add(edits[8]);
        editPanel.add(alt1);

        JPanel alt2 = new JPanel(new GridLayout(1, 3, 5, 5));
        alt2.add(edits[9]);
        alt2.add(labels[10]);
        alt2.add(edits[10]);
        editPanel.add(alt2);

        lblPanel.add(labels[9]);

        for (int k = 11; k < 14; k++) {
            lblPanel.add(labels[k]);
            editPanel.add(edits[k]);
        }
        lblPanel.add(labels[14]);
        JPanel alt3 = new JPanel(new GridLayout(1, 3, 5, 5));
        alt3.add(edits[14]);
        alt3.add(labels[15]);
        alt3.add(edits[15]);
        editPanel.add(alt3);
        contractLenCB.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem citm = (ComboItem) contractLenCB.getSelectedItem();
                setEndDateVisible(citm.getId() > 0);
            }
        });
    }

    private void setEndDateVisible(boolean visible) {
        contractEndSP.setVisible(visible);
        labels[15].setVisible(visible);
    }

    @Override
    public void loadData() {
        Xemployee emp = (Xemployee) getDbObject();
        if (emp != null) {
            Date dt;
            idField.setText(emp.getXemployeeId().toString());
            clockNumField.setText(emp.getClockNum());
            firstNameField.setText(emp.getFirstName());
            surNameField.setText(emp.getSurName());
            nickNameField.setText(emp.getNickName());
            idNumField.setText(emp.getIdNum());
            nickNameField.setText(emp.getNickName());
            phone0NumField.setText(emp.getPhone0Num());
            phone1NumField.setText(emp.getPhone1Num());
            phone2NumField.setText(emp.getPhone2Num());
            relation1Field.setText(emp.getRelation1());
            relation2Field.setText(emp.getRelation2());
            addressField.setText(emp.getAddress());
            selectComboItem(positionCB, emp.getXpositionId());
            selectComboItem(contractLenCB, emp.getContractLen());
            if (emp.getContractStart() != null) {
                dt = new java.util.Date(emp.getContractStart().getTime());
                contractStartSP.setValue(dt);
            }
            if (emp.getContractEnd() != null) {
                dt = new java.util.Date(emp.getContractEnd().getTime());
                contractEndSP.setValue(dt);
            }
            if (emp.getRate() != null) {
                rateSP.setValue(emp.getRate());
            }
            if (emp.getContractLen() != null) {
                setEndDateVisible(emp.getContractLen() > 0);
            }
            imageData = (byte[]) emp.getPhoto();
            setImage(imageData);
        }
    }

    @Override
    public boolean save() throws Exception {
        //TODO: save 
        Xemployee emp = (Xemployee) getDbObject();
        boolean isNew = false;
        if (emp == null) {
            emp = new Xemployee(null);
            emp.setXemployeeId(0);
            isNew = true;
        }
        ComboItem itm = (ComboItem) positionCB.getSelectedItem();
        try {
            emp.setXpositionId(itm.getId());
            emp.setNew(isNew);
            emp.setAddress(addressField.getText());
            emp.setClockNum(clockNumField.getText());
            emp.setContractLen(((ComboItem)contractLenCB.getSelectedItem()).getId());
            if (emp.getContractLen() > 0) {
                emp.setContractEnd(new java.sql.Date(((java.util.Date) contractEndSP.getValue()).getTime()));
            } else {
                emp.setContractEnd(null);
            }
            if (contractStartSP.getValue() != null) {
                emp.setContractStart(new java.sql.Date(((java.util.Date) contractStartSP.getValue()).getTime()));
            } else {
                emp.setContractStart(null);
            }
            emp.setFirstName(firstNameField.getText());
            emp.setIdNum(idNumField.getText());
            emp.setNickName(nickNameField.getText());
            emp.setSurName(surNameField.getText());
            emp.setPhone0Num(phone0NumField.getText());
            emp.setPhone1Num(phone1NumField.getText());
            emp.setPhone2Num(phone2NumField.getText());
            emp.setRate((Integer)rateSP.getValue());
            emp.setRelation1(relation1Field.getText());
            emp.setRelation2(relation2Field.getText());
            itm = (ComboItem) positionCB.getSelectedItem();
            if (itm.getId()>0) {
                emp.setXpositionId(itm.getId());
            } else {
                emp.setXpositionId(null);
            }
            emp.setPhoto(imageData);
            setDbObject(DashBoard.getExchanger().saveDbObject(emp));
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
