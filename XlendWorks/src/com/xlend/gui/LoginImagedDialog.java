package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.NoFrameButton;
import com.xlend.util.PopupDialog;
import com.xlend.util.TexturedPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.Naming;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
    private JPanel controlsPanel;
    private JComboBox loginField;
    private JPasswordField pwdField;
    private static IMessageSender exchanger;
//    private JMenuBar bar;
    private static boolean okPressed;

    public LoginImagedDialog(Object obj) {
        super(null, "Login", obj);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        okPressed = false;
        buildMenu();
//        new Object[]{loginField, pwdField, exchanger}
        Object[] obs = (Object[]) getObject();
        loginField = (JComboBox) obs[0];
        pwdField = (JPasswordField) obs[1];
        //loginField = new JComboBox(XlendWorks.loadAllLogins(DashBoard.getExchanger()));
        controlsPanel = new JPanel(new BorderLayout());
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        controlsPanel.add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(XlendWorks.loadImage(BACKGROUNDIMAGE, this));
        addNotify();
        Insets insets = getInsets();
        int dashWidth = img.getWidth();
        int dashHeight = img.getHeight();
        int yShift = 15;
        int xShift = 20;

        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right,
                dashHeight + insets.top + insets.bottom + 20));
        LayerPanel layers = new LayerPanel();
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);
        getContentPane().add(layers, BorderLayout.CENTER);

        loginField.setBounds(250, 262, 180, 27);
        main.add(loginField);
        pwdField.setBounds(250, 290, 180, 27);
        main.add(pwdField);

        JButton okButton = new NoFrameButton("Lock.jpg");
        img = new ImagePanel(XlendWorks.loadImage("Lock.jpg", this));


        okButton.setBounds(dashWidth - img.getWidth() - xShift, dashHeight - img.getHeight() - yShift, 
                img.getWidth(), img.getHeight());

        main.add(okButton);

        setResizable(false);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = new JMenu("Options");
        m.add(new JMenuItem(new AbstractAction("Settings") {

            @Override
            public void actionPerformed(ActionEvent e) {
                String newAddress = XlendWorks.serverSetup("Options");
                if (newAddress != null) {
                    DashBoard.getProperties().setProperty("ServerAddress", newAddress);
                    try {
                        DashBoard.setExchanger(exchanger =
                                (IMessageSender) Naming.lookup("rmi://"
                                + newAddress + "/XlendServer"));
                    } catch (Exception ex) {
                        XlendWorks.logAndShowMessage(ex);
                        System.exit(1);
                    }
                }
            }
        }));
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
//        ch.randelshofer.quaqua.QuaquaLookAndFeel
        it = m.add(new JMenuItem("Quaqua"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });

        it = m.add(new JMenuItem("Nimbus"));
        it.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                } catch (Exception e1) {
                }
            }
        });
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
//        throw new UnsupportedOperationException("Not supported yet.");
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
