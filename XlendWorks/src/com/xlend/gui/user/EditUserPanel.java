package com.xlend.gui.user;

//import com.csa.formutil.RecordEditPanel;
//import com.csa.orm.Userprofile;
//import com.csa.orm.dbobject.DbObject;
import com.xlend.gui.DashBoard;
import com.xlend.gui.ProfilePanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.admin.EditSheetAccessDialog;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditUserPanel extends ProfilePanel {

    private JTextField faxField;
    private JTextField webAddressField;
    private JTextField officeHoursField;
    private JRadioButton salerRB;
    private JRadioButton managerRB;
    private JRadioButton supervisorRB;
    private JTextField loginField;
    private JPasswordField passwordField;

    public EditUserPanel(Userprofile dbob) {
        super(dbob);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            //            "",
            "Login:", "Password:", "Fax:", "Web Address:", "Office Hours:",
            "Supervisor", "Manager:", "Clerk:", ""};
        JComponent[] edits = new JComponent[]{
            //            new JPanel(),
            loginField = new JTextField(),
            passwordField = new JPasswordField(),
            faxField = new JTextField(),
            webAddressField = new JTextField(),
            officeHoursField = new JTextField(),
            supervisorRB = new JRadioButton(),
            managerRB = new JRadioButton(),
            salerRB = new JRadioButton(),
            new JButton(detailedAccessAction())
        };
        ButtonGroup bg = new ButtonGroup();
        bg.add(supervisorRB);
        bg.add(managerRB);
        bg.add(salerRB);
        organizePanels(labels.length, edits.length);
        for (int i = 0; i < labels.length; i++) {
            editPanel.add(edits[i]);
            lblPanel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
        boolean isSupervisor = (XlendWorks.getCurrentUser().getSupervisor() != null
                && XlendWorks.getCurrentUser().getSupervisor() == 1);
        boolean isManager = (XlendWorks.getCurrentUser().getManager() != null
                && XlendWorks.getCurrentUser().getManager() == 1);
        loginField.setEnabled(isManager || isSupervisor);
        passwordField.setEnabled(isManager || isSupervisor);
        supervisorRB.setEnabled(isManager || isSupervisor);
        managerRB.setEnabled(isManager || isSupervisor);
        salerRB.setEnabled(isManager || isSupervisor);
    }

    private AbstractAction detailedAccessAction() {
        return new AbstractAction("Document access...") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (getDbObject() == null) {
                    JOptionPane.showMessageDialog(pagesdPanel, "Save user record first!",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                } else {
                    new EditSheetAccessDialog("Document access", getDbObject());
//                    if (EditSheetAccessDialog.okPressed) {
//                    }
                }
            }
        };
    }

    @Override
    public void loadData() {
        Userprofile up = (Userprofile) getDbObject();
        if (up != null) {
            faxField.setText(up.getFax());
            webAddressField.setText(up.getWebaddress());
            officeHoursField.setText(up.getOfficeHours());
            supervisorRB.setSelected(up.getSupervisor() != null && up.getSupervisor() == 1);
            salerRB.setSelected(up.getSalesperson() != null && up.getSalesperson() == 1);
            managerRB.setSelected(up.getManager() != null && up.getManager() == 1);
            loginField.setText(up.getLogin());
            passwordField.setText(up.getPwdmd5());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Userprofile up = (Userprofile) getDbObject();
        if (up == null) {
            up = new Userprofile(null);
            isNew = true;
        }
        up.setProfileId(profile_id);
        up.setFax(faxField.getText());
        up.setWebaddress(webAddressField.getText());
        up.setOfficeHours(officeHoursField.getText());
        up.setSupervisor(supervisorRB.isSelected() ? 1 : 0);
        up.setSalesperson(salerRB.isSelected() ? 1 : 0);
        up.setManager(managerRB.isSelected() ? 1 : 0);
        up.setLogin(loginField.getText());
        up.setPwdmd5(new String(passwordField.getPassword()));
        try {
            up.setNew(isNew);
            DbObject saved = XlendWorks.getExchanger().saveDbObject(up);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
