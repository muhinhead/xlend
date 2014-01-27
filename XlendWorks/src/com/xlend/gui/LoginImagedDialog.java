package com.xlend.gui;

import com.jtattoo.plaf.aero.AeroLookAndFeel;
import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.rmi.ExchangeFactory;
import com.xlend.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Nick Mukhin
 */
public class LoginImagedDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "Login.png";
//    private final String NMSOFTWARE = "Nick Mukhin (c)2013";
    private JPanel controlsPanel;
    private Java2sAutoComboBox loginField;
    private JPasswordField pwdField;
    private static boolean okPressed = false;

    /**
     * @return the okPressed
     */
    public static boolean isOkPressed() {
        return okPressed;
    }

    public LoginImagedDialog(Object obj) {
        super(null, "Login", obj);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        okPressed = false;
        buildMenu();
        try {
            String theme = DashBoard.readProperty("LookAndFeel",
                    "com.nilo.plaf.nimrod.NimRODLookAndFeel");
            if (theme.indexOf("HiFi") > 0) {
                HiFiLookAndFeel.setTheme("Default", "", XlendWorks.NMSOFTWARE);
            } else if (theme.indexOf("Noire") > 0) {
                NoireLookAndFeel.setTheme("Default", "", XlendWorks.NMSOFTWARE);
            } else if (theme.indexOf("Bernstein") > 0) {
                BernsteinLookAndFeel.setTheme("Default", "", XlendWorks.NMSOFTWARE);
            } else if (theme.indexOf("Aero") > 0) {
                AeroLookAndFeel.setTheme("Green", "", XlendWorks.NMSOFTWARE);
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

//        JApplet applet = new InternalApplet();
//        applet.init();
//
//        setContentPane(applet.getContentPane());
//
//        pack();
//        setLocationRelativeTo(null);
//        setVisible(true);
//
//        applet.start();

        loginField = new Java2sAutoComboBox(XlendWorks.loadAllLogins());
        loginField.setEditable(true);
        pwdField = new JPasswordField(20);
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendWorks.loadImage(BACKGROUNDIMAGE, this));
        addNotify();
        Insets insets = getInsets();
        int dashWidth = img.getWidth();
        int dashHeight = img.getHeight();
        int yShift = 14;
        int xShift = 20;

        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right,
                dashHeight + insets.top + insets.bottom + 20));
        LayerPanel layers = new LayerPanel();
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

        loginField.setBounds(290, 349, 225, 26);
        loginField.setBorder(null);
        main.add(loginField);
        pwdField.setBounds(290, 384, 225, 26);
        pwdField.setBorder(null);
        main.add(pwdField);

        JButton okButton = new ToolBarButton("Lock.png", true);
        img = new ImagePanel(XlendWorks.loadImage("Lock.png", this));

        okButton.setBounds(dashWidth - img.getWidth() - xShift, dashHeight - img.getHeight() - yShift,
                img.getWidth(), img.getHeight());

        main.add(okButton);
        okButton.addActionListener(okButtonListener());

        Preferences userPref = Preferences.userRoot();
        String pwdmd5 = userPref.get("DEVPWD", "");
        pwdField.setText(pwdmd5);
        if (pwdmd5.trim().length() > 0) {
            loginField.setSelectedItem(DashBoard.readProperty(DashBoard.LASTLOGIN, ""));
        }
        getRootPane().setDefaultButton(okButton);
        setResizable(false);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Settings");
        m.add(new JMenuItem(new AbstractAction("Connection") {
            @Override
            public void actionPerformed(ActionEvent e) {
                XlendWorks.configureConnection();
            }
        }));
        m.add(XlendWorks.appearanceMenu("Theme",this));
        bar.add(m);
        m.add(new JMenuItem(new AbstractAction("Export dump"){
            @Override
            public void actionPerformed(ActionEvent ae) {
                String dumpFile = XlendWorks.makeBackup();
                if (dumpFile!=null) {
                    JOptionPane.showMessageDialog(null, "Database dump exported to "
                            +dumpFile,
                    "Backup succeed", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }));
        bar.add(m);
        setJMenuBar(bar);
    }

//    protected JMenu appearanceMenu(String item) {
//        JMenu m;
//        JMenuItem it;
//        m = new JMenu(item);
//        it = m.add(new JMenuItem("Tiny"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("de.muntjak.tinylookandfeel.TinyLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Nimbus"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Nimrod"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Plastic"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("com.jgoodies.plaf.plastic.PlasticXPLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("HiFi"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    HiFiLookAndFeel.setTheme("Default", "", NMSOFTWARE);
//                    setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Noire"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    NoireLookAndFeel.setTheme("Default", "", NMSOFTWARE);
//                    setLookAndFeel("com.jtattoo.plaf.noire.NoireLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Bernstein"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    BernsteinLookAndFeel.setTheme("Default", "", NMSOFTWARE);
//                    setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Aero"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    AeroLookAndFeel.setTheme("Green", "", NMSOFTWARE);
//                    setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//
//        it = m.add(new JMenuItem("System"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Java"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        it = m.add(new JMenuItem("Motif"));
//        it.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//                } catch (Exception e1) {
//                }
//            }
//        });
//        return m;
//    }

//    private void setLookAndFeel(String lf, Component root) throws ClassNotFoundException,
//            InstantiationException, IllegalAccessException,
//            UnsupportedLookAndFeelException {
//        UIManager.setLookAndFeel(lf);
//        SwingUtilities.updateComponentTreeUI(root);
//        DashBoard.getProperties().setProperty("LookAndFeel", lf);
//        DashBoard.saveProps();
//    }

    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    private ActionListener okButtonListener() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String login = (String) loginField.getSelectedItem();
                String pwd = new String(pwdField.getPassword());
                try {
                    //TODO: check MD5(pwd) instead of pwd
                    DbObject[] users = XlendWorks.getExchanger().getDbObjects(Userprofile.class,
                            "login='" + login + "' and pwdmd5='" + pwd + "'", null);
                    okPressed = (users.length > 0);
                    if (isOkPressed()) {
                        Userprofile currentUser = (Userprofile) users[0];
                        try {
                            currentUser.setPwdmd5("");
                        } catch (Exception ex) {
                        }
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
        };
    }

    private class LayerPanel extends JLayeredPane {

        private LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }
}