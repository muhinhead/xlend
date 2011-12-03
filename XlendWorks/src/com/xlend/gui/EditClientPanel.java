package com.xlend.gui;

import com.xlend.orm.Clientprofile;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.GridLayout;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Admin
 */
public class EditClientPanel extends ProfilePanel {

    //private JComboBox clientGroupBox;
    private JSpinner salesPotentialSpinner;
    private JSpinner birthDaySpinner;
//    private JTextField spouseFirstNameField;
//    private JTextField spouseLastNameField;
//    private JSpinner spouseBirthDaySpinner;
//    private JTextField spouseEmailField;
    private JComboBox sourceTypeBox;
    private JTextField sourceDescrField;
    private JComboBox salesPersonBox;
    private ComboItem[] clientGroups;
    private ComboItem[] salesPersons;

    public EditClientPanel(Clientprofile clientProfile) {
        super(clientProfile);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{"", "Client Group:", "Birthday",
            "Spouse First Name:", "Spouse Last Name:", "Spouse Birthday:",
            "Spouse E-mail:", "Source Type:", "Other:", "Sales Potential:",
            "", "Sales Person:"
        };

        JComponent[] edits = new JComponent[]{
            new JPanel(),
//            clientGroupBox = new JComboBox(clientGroups = getClientGroups()),
            birthDaySpinner = new JSpinner(new SpinnerDateModel()),
//            spouseFirstNameField = new JTextField(),
//            spouseLastNameField = new JTextField(),
//            spouseBirthDaySpinner = new JSpinner(new SpinnerDateModel()),
//            spouseEmailField = new JTextField(),
            sourceTypeBox = new JComboBox(new String[]{"Phonebook", "Referral", "Location", "Others"}),
            sourceDescrField = new JTextField(),
            salesPotentialSpinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 100)),
            new JPanel(),
            salesPersonBox = new JComboBox(salesPersons = getSalesPersons())
        };
        JSpinner.DateEditor de1 = new JSpinner.DateEditor(birthDaySpinner, "yyyy/MM/dd");
        birthDaySpinner.setEditor(de1);
//        JSpinner.DateEditor de2 = new JSpinner.DateEditor(spouseBirthDaySpinner, "yyyy/MM/dd");
//        spouseBirthDaySpinner.setEditor(de2);

        organizePanels(labels.length, edits.length);
        for (int i = 0; i < labels.length; i++) {
            if (edits[i] instanceof JSpinner || (edits[i] instanceof JComboBox && edits[i] != salesPersonBox)) {
                JPanel edp = new JPanel(new GridLayout(1, 3));
                edp.add(edits[i]);
                edp.add(new JPanel());
                edp.add(new JPanel());
                editPanel.add(edp);
            } else {
                editPanel.add(edits[i]);
            }
            lblPanel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
    }

    @Override
    public void loadData() {
        Clientprofile cp = (Clientprofile) getDbObject();
        if (cp != null) {
//            clientGroupBox.setSelectedIndex(
//                    findItem(clientGroups, cp.getClientgroupId()));
            birthDaySpinner.setValue(cp.getBirthday() == null ? new Date()
                    : new Date(cp.getBirthday().getTime()));
//            spouseFirstNameField.setText(cp.getSpouseFirstName());
//            spouseLastNameField.setText(cp.getSpouseLastName());
//            spouseBirthDaySpinner.setValue(
//                    cp.getSpouseBirthday() == null ? new Date()
//                    : new Date(cp.getSpouseBirthday().getTime()));
//            spouseEmailField.setText(cp.getSpouseEmail());
            sourceTypeBox.setSelectedItem(cp.getSourceType());
            sourceDescrField.setText(cp.getSourceDescr());
            salesPotentialSpinner.setValue(
                    cp.getSalesPotential() == null ? 0
                    : cp.getSalesPotential());
            if (cp.getSalespersonId() != null) {
                salesPersonBox.setSelectedIndex(findItem(salesPersons, cp.getSalespersonId()));
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Clientprofile cp = (Clientprofile) getDbObject();
        if (cp == null) {
            cp = new Clientprofile(null);
            isNew = true;
        }
        cp.setProfileId(profile_id);
//        ComboItem ci = (ComboItem) clientGroupBox.getSelectedItem();
//        if (ci != null) {
//            cp.setClientgroupId(ci.getId());
//        }
        Date b = (Date) birthDaySpinner.getValue();
        if (b != null) {
            cp.setBirthday(new java.sql.Date(b.getTime()));
        }
        cp.setSalesPotential((Integer) salesPotentialSpinner.getValue());
        cp.setSourceDescr(sourceDescrField.getText());
        cp.setSourceType(sourceTypeBox.getSelectedItem() == null ? null
                : sourceTypeBox.getSelectedItem().toString());
//        b = (Date) spouseBirthDaySpinner.getValue();
//        if (b != null) {
//            cp.setSpouseBirthday(new java.sql.Date(b.getTime()));
//        }
//        cp.setSpouseFirstName(spouseFirstNameField.getText());
//        cp.setSpouseLastName(spouseLastNameField.getText());
//        cp.setSpouseEmail(spouseEmailField.getText());

//        ci = (ComboItem) salesPersonBox.getSelectedItem();
//        if (ci != null) {
//            cp.setSalespersonId(ci.getId());
//        }
        try {
            cp.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(cp);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

    protected static int findItem(ComboItem[] clientGroups, Integer clientgroupId) {
        int i = 0;

        for (ComboItem ci : clientGroups) {
            if (ci.getId() == clientgroupId) {
                return i;
            } else {
                ++i;
            }
        }
        return 0;
    }

    /**
     * @return the profile_id
     */
    public int getProfile_id() {
        return profile_id;
    }
}
