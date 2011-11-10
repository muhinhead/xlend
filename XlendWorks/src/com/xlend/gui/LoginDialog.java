package com.xlend.gui;

//import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
//import com.jtattoo.plaf.aero.AeroLookAndFeel;
//import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
//import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
//import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

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
//    private JCheckBox savePwdCB;
    private JComboBox loginField;
    private JPasswordField pwdField;
    private static IMessageSender exchanger;
    private static String currentLogin;

    public LoginDialog(Object[] params) {
        super(null, "Login", params);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        //super.fillContent();
        buildMenu();
        getContentPane().setLayout(new BorderLayout());
        try {
            String theme = DashBoard.readProperty("LookAndFeel",
                    "com.nilo.plaf.nimrod.NimRODLookAndFeel");
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
//        upFramePane.add(new JPanel(), BorderLayout.NORTH);
        upFramePane.add(new JPanel(), BorderLayout.WEST);
        upFramePane.add(new JPanel(), BorderLayout.EAST);
        upFramePane.add(upperPane, BorderLayout.CENTER);

        JPanel imgPanel = new JPanel();
        imgPanel.add(new ImagePanel(XlendWorks.loadImage("XcostBtn.jpg", this)));
        getContentPane().add(imgPanel, BorderLayout.NORTH);
        getContentPane().add(upFramePane, BorderLayout.CENTER);

        Object[] edits = (Object[]) getObject();
        exchanger = (IMessageSender) edits[2];

        JPanel labelPane = new JPanel(new GridLayout(3, 1, 10, 10));
        JPanel editPane = new JPanel(new GridLayout(3, 1, 10, 10));
        labelPane.add(new JLabel(" Login:", SwingUtilities.RIGHT));
        labelPane.add(new JLabel(" Password:", SwingUtilities.RIGHT));
//        labelPane.add(new JLabel(" Save password:", SwingUtilities.RIGHT));
        editPane.add(loginField = (JComboBox) edits[0]);
        editPane.add(pwdField = (JPasswordField) edits[1]);
//        editPane.add(savePwdCB = new JCheckBox());

        Preferences userPref = Preferences.userRoot();
//        String pwdmd5 = userPref.get(DashBoard.PWDMD5, "");
//        pwdField.setText(pwdmd5);
//        savePwdCB.setSelected(pwdmd5.length() > 0);
        //loginField.setText(DashBoard.readProperty(DashBoard.LASTLOGIN, ""));

        upperPane.add(labelPane, BorderLayout.WEST);
        upperPane.add(editPane, BorderLayout.CENTER);
        upperPane.add(new JPanel(), BorderLayout.EAST);

        JPanel buttonPane = new JPanel();
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        buttonPane.add(okButton = new JButton(okAction = new AbstractAction("OK") {

//            @Override
            public void actionPerformed(ActionEvent e) {
                String login = (String)loginField.getSelectedItem();
                String pwd = new String(pwdField.getPassword());
                try {
                    //TODO: check MD5(pwd) instead of pwd
                    DbObject[] users = exchanger.getDbObjects(Userprofile.class,
                            "login='" + login + "' and pwdmd5='" + pwd + "'", null);
                    okPressed = (users.length > 0);
                    if (okPressed) {
                        Userprofile currentUser = (Userprofile) users[0];
//                        if (!savePwdCB.isSelected()) {
                            try {
                                currentUser.setPwdmd5("");
                            } catch (Exception ex) {
                            }
//                        }
                        XlendWorks.setCurrentUser(currentUser);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Access denied", "Oops!", JOptionPane.ERROR_MESSAGE);
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

//            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        }));

        getRootPane().setDefaultButton(okButton);
        pack();
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Options");
        m.add(appearanceMenu("Theme"));
        bar.add(m);
        setJMenuBar(bar);
    }

    protected JMenu appearanceMenu(String item) {
        JMenu m;
        JMenuItem it;
        m = new JMenu(item);
        it = m.add(new JMenuItem("Tiny"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
//        it = m.add(new JMenuItem("Acryl"));
//        it.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    AcrylLookAndFeel.setTheme("Default", "LICENSE KEY HERE", DashBoard.XLEND_PLANT);
//                    setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
        it = m.add(new JMenuItem("Nimbus"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
//        it = m.add(new JMenuItem("Noire"));
//        it.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    NoireLookAndFeel.setTheme("Default", "LICENSE KEY HERE", DashBoard.XLEND_PLANT);
//                    setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("HiFi"));
//        it.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    HiFiLookAndFeel.setTheme("Default", "LICENSE KEY HERE", DashBoard.XLEND_PLANT);
//                    setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Bernstein"));
//        it.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    BernsteinLookAndFeel.setTheme("Default", "LICENSE KEY HERE", DashBoard.XLEND_PLANT);
//                    setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Aero"));
//        it.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    AeroLookAndFeel.setTheme("Green", "LICENSE KEY HERE", DashBoard.XLEND_PLANT);
//                    setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
        it = m.add(new JMenuItem("Nimrod"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("Plastic"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("System"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("Java"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        it = m.add(new JMenuItem("Motif"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
        return m;
    }

    private void setLookAndFeel(String lf) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(lf);
        SwingUtilities.updateComponentTreeUI(this);
        DashBoard.getProperties().setProperty("LookAndFeel", lf);
        DashBoard.saveProps();
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
