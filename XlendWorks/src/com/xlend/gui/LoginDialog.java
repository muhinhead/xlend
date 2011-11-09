package com.xlend.gui;

import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author Nick Mukhin
 */
public class LoginDialog extends PopupDialog {

    private JButton okButton;
    private JButton cancelButton;
    private static boolean okPressed;
    private AbstractAction okAction;
    private AbstractAction cancelAction;
    private JCheckBox savePwdCB;
    private JTextField loginField;
    private JPasswordField pwdField;
    private static IMessageSender exchanger;

    public LoginDialog(Object[] params) {
        super(null, "Login", params);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        super.fillContent();
        try {
            String theme = MainFrame.readProperty("LookAndFeel", 
                    "com.nilo.plaf.nimrod.NimRODLookAndFeel");
            if (theme.contains("jtattoo")) {
                MainFrame.setSubThemes();
            }
            UIManager.setLookAndFeel(theme);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                SwingUtilities.updateComponentTreeUI(this);
            } catch (Exception ie) {
                XlendWorks.log(ie);
            }
        }

//        getContentPane().setLayout(new BorderLayout());
        JPanel upperPane = new JPanel(new BorderLayout());
        JPanel upFramePane = new JPanel(new BorderLayout());
        upFramePane.add(new JPanel(), BorderLayout.NORTH);
        upFramePane.add(new JPanel(), BorderLayout.WEST);
        upFramePane.add(new JPanel(), BorderLayout.EAST);
        upFramePane.add(upperPane, BorderLayout.CENTER);
        getContentPane().add(upFramePane, BorderLayout.CENTER);

        Object[] edits = (Object[]) getObject();
        exchanger = (IMessageSender) edits[2];

        JPanel labelPane = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel editPane = new JPanel(new GridLayout(3, 1, 10, 10));
        labelPane.add(new JLabel(" Login:", SwingUtilities.RIGHT));
        labelPane.add(new JLabel(" Password:", SwingUtilities.RIGHT));
        labelPane.add(new JLabel(" Save password:", SwingUtilities.RIGHT));
        editPane.add(loginField = (JTextField) edits[0]);
        editPane.add(pwdField = (JPasswordField) edits[1]);
        editPane.add(savePwdCB = new JCheckBox());

        Preferences userPref = Preferences.userRoot();
        String pwdmd5 = userPref.get(MainFrame.PWDMD5, "");
        pwdField.setText(pwdmd5);
        savePwdCB.setSelected(pwdmd5.length() > 0);
        loginField.setText(MainFrame.readProperty(MainFrame.LASTLOGIN, ""));

        upperPane.add(labelPane, BorderLayout.WEST);
        upperPane.add(editPane, BorderLayout.CENTER);
        upperPane.add(new JPanel(), BorderLayout.EAST);

        JPanel buttonPane = new JPanel();
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(okButton = new JButton(okAction = new AbstractAction("OK") {

            @Override
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                String pwd = new String(pwdField.getPassword());
                try {
                    //TODO: check MD5(pwd) instead of pwd
                    DbObject[] users = exchanger.getDbObjects(Userprofile.class,
                            "login='" + login + "' and pwdmd5='" + pwd + "'", null);
                    okPressed = (users.length > 0);
                    if (okPressed) {
                        Userprofile currentUser = (Userprofile) users[0];
                        if (!savePwdCB.isSelected()) {
                            try {
                                currentUser.setPwdmd5("");
                            } catch (Exception ex) {
                            }
                        }
                        XlendWorks.setCurrentUser(currentUser);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Unknown user", "Sorry!", JOptionPane.ERROR_MESSAGE);
                        loginField.requestFocus();
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Server not respondind, sorry", "Error:", JOptionPane.ERROR_MESSAGE);
                    XlendWorks.log(ex);
                    dispose();
                }

            }
        }));

        buttonPane.add(cancelButton = new JButton(cancelAction = new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        }));

        getRootPane().setDefaultButton(okButton);
        pack();
    }

    @Override
    public void freeResources() {
        okButton.removeActionListener(okAction);
        okAction = null;
        cancelButton.removeActionListener(cancelAction);
        cancelAction = null;
    }

    /**
     * @return the okPressed
     */
    public static boolean isOkPressed() {
        return okPressed;
    }
}
