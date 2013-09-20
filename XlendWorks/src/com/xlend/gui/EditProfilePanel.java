package com.xlend.gui;

import com.xlend.orm.Profile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.EmailFocusAdapter;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Admin
 */
class EditProfilePanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField address1Field;
    private JTextField address2Field;
    private JTextField cityField;
    private JComboBox stateBox;
    private JTextField zipCodeField;
    private JTextField phoneField;
    private JTextField cellPhoneField;
    private JTextField emailField;

    public EditProfilePanel(Profile profile) {
        super(profile);
    }

    private Object[] distinctStates() {
        return new String[]{
                    "Gauteng", "Freestate", "North-West",
                    "Limpopo", "Mpumalanga", "Kwazulu-Natal", "Northern Cape",
                    "Eastern Cape", "Western Cape"
                };
    }

    @Override
    protected void fillContent() {
        final int c = 2;
        String[] labels = new String[]{"ID:",
            "First Name:", "Last Name:", "Address (line1):", "Address (line2):", "City:",
            "Province:", "Zip Code:", "Phone:", "Cell Phone:", "E-mail:"};
        JComponent[] edits = new JComponent[]{idField = new JTextField(),
            firstNameField = new JTextField(),
            lastNameField = new JTextField(), address1Field = new JTextField(),
            address2Field = new JTextField(), cityField = new JTextField(),
            stateBox = new JComboBox(distinctStates()), zipCodeField = new JTextField(),
            phoneField = new JTextField(), cellPhoneField = new JTextField(),
            emailField = new JTextField()
        };
        stateBox.setEditable(false);

        setLayout(new BorderLayout());
        JPanel lblPanel = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        JPanel editPanel = new JPanel(new GridLayout(edits.length, 1, 5, 5));
        JPanel upPanel = new JPanel(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        upPanel.add(lblPanel, BorderLayout.WEST);
        upPanel.add(editPanel, BorderLayout.CENTER);
        upPanel.add(new JPanel(), BorderLayout.EAST);
        for (int i = 0; i < labels.length; i++) {
            if (i == 0 || i == 7) {
                JPanel idPanel = new JPanel(new GridLayout(1, c));
                idPanel.add(edits[i]);
                for (int j = 0; j < c; j++) {
                    idPanel.add(new JPanel());
                }
                edits[i].setEnabled(i != 0);
                editPanel.add(idPanel);
            } else {
                editPanel.add(edits[i]);
            }
            JLabel lbl = new JLabel(labels[i], SwingConstants.RIGHT);
            lblPanel.add(lbl);
            if (i == 10) {
                emailField.addFocusListener(new EmailFocusAdapter(lbl, emailField));
            }
        }
    }

    @Override
    public void loadData() {
        Profile profile = (Profile) getDbObject();
        if (profile != null) {
            idField.setText(profile.getProfileId().toString());
            firstNameField.setText(profile.getFirstName());
            lastNameField.setText(profile.getLastName());
            address1Field.setText(profile.getAddress1());
            address2Field.setText(profile.getAddress2());
            cityField.setText(profile.getCity());
            //stateBox.getEditor().setItem(profile.getState());
            stateBox.setSelectedItem(profile.getState());
            zipCodeField.setText(profile.getZipCode());
            phoneField.setText(profile.getPhone());
            cellPhoneField.setText(profile.getCellPhone());
            emailField.setText(profile.getEmail());
        }
    }

    @Override
    public boolean save() throws Exception {
        Profile profile = (Profile) getDbObject();
        if (profile == null) {
            profile = new Profile(null);
            profile.setProfileId(0);
            setDbObject(profile);
        }
        profile.setFirstName(firstNameField.getText());
        profile.setLastName(lastNameField.getText());
        profile.setAddress1(address1Field.getText());
        profile.setAddress2(address2Field.getText());
        profile.setCity(cityField.getText());
        profile.setState((String) stateBox.getSelectedItem());
        profile.setZipCode(zipCodeField.getText());
        profile.setPhone(phoneField.getText());
        profile.setCellPhone(cellPhoneField.getText());
        profile.setEmail(emailField.getText());
        try {
            DbObject saved = XlendWorks.getExchanger().saveDbObject(profile);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
