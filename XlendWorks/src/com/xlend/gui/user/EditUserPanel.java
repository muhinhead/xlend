package com.xlend.gui.user;

//import com.csa.formutil.RecordEditPanel;
//import com.csa.orm.Userprofile;
//import com.csa.orm.dbobject.DbObject;
import com.xlend.gui.DashBoard;
import com.xlend.gui.ProfilePanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JLabel;
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
    private JTextField loginField;
    private JPasswordField passwordField;

    public EditUserPanel(Userprofile dbob) {
        super(dbob);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "",
            "Login:", "Password:", "Fax:", "Web Address:", "Office Hours:",
            "Manager:", "Clerk:"};
        JComponent[] edits = new JComponent[]{
            new JPanel(),
            loginField = new JTextField(),
            passwordField = new JPasswordField(),
            faxField = new JTextField(),
            webAddressField = new JTextField(),
            officeHoursField = new JTextField(),
            managerRB = new JRadioButton(),
            salerRB = new JRadioButton(),};
        ButtonGroup bg = new ButtonGroup();
        bg.add(managerRB);
        bg.add(salerRB);
        organizePanels(labels.length, edits.length);
        for (int i = 0; i < labels.length; i++) {
            editPanel.add(edits[i]);
            lblPanel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
        boolean isManager = (XlendWorks.getCurrentUser().getManager() != null 
                && XlendWorks.getCurrentUser().getManager() == 1);
        loginField.setEnabled(isManager);
        passwordField.setEnabled(isManager);
        managerRB.setEnabled(isManager);
        salerRB.setEnabled(isManager);
    }

    @Override
    public void loadData() {
        Userprofile up = (Userprofile) getDbObject();
        if (up != null) {
            faxField.setText(up.getFax());
            webAddressField.setText(up.getWebaddress());
            officeHoursField.setText(up.getOfficeHours());
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
        up.setSalesperson(salerRB.isSelected() ? 1 : 0);
        up.setManager(managerRB.isSelected() ? 1 : 0);
        up.setLogin(loginField.getText());
        up.setPwdmd5(new String(passwordField.getPassword()));
        try {
            up.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(up);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
