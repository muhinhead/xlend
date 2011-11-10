package com.xlend.gui;

//import com.jtattoo.plaf.acryl.AcrylLookAndFeel;
//import com.jtattoo.plaf.aero.AeroLookAndFeel;
//import com.jtattoo.plaf.bernstein.BernsteinLookAndFeel;
//import com.jtattoo.plaf.hifi.HiFiLookAndFeel;
//import com.jtattoo.plaf.noire.NoireLookAndFeel;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Properties;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
//import sun.awt.AWTAccessor.WindowAccessor;

/**
 *
 * @author Admin
 */
public class WorkFrame extends JFrame {

    private IMessageSender exchanger;
    private final Properties props;
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JLabel statusLabel3 = new JLabel();
    private DbTableGridPanel usersPanel = null;
    private JTabbedPane mainPanel;
    private ToolBarButton aboutButton;
    private ToolBarButton exitButton;
    private JToolBar toolBar;

    public WorkFrame(String title, IMessageSender exch, Properties props) {
        super(title);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.exchanger = exch;
        this.props = props;
        fillContentPane();
        float width = Float.valueOf(DashBoard.readProperty("WindowWidth", "0.7"));
        float height = Float.valueOf(DashBoard.readProperty("WindowHeight", "0.8"));
        DashBoard.setSizes(this, width, height);
        DashBoard.centerWindow(this);
        setVisible(true);
    }

    public WorkFrame(IMessageSender exch, Properties props) {
        this("Works", exch, props);
    }

    public void setLookAndFeel(String lf) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(lf);
        SwingUtilities.updateComponentTreeUI(this);
        DashBoard.getProperties().setProperty("LookAndFeel", lf);
    }

    private void fillContentPane() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        getContentPane().setLayout(new BorderLayout());
        statusPanel.setBorder(BorderFactory.createEtchedBorder());
        statusPanel.setLayout(new BorderLayout());
        setStatusLabel1Text(" ");
        statusLabel1.setBorder(BorderFactory.createEtchedBorder());
        statusLabel2.setHorizontalTextPosition(SwingConstants.CENTER);
        statusLabel2.setText(" ");
//        statusPanel.add(statusLabel1,BorderLayout.WEST);
        statusPanel.add(statusLabel2, BorderLayout.CENTER);

        aboutButton = new ToolBarButton("help.png");
        aboutButton.setToolTipText("About program");
        exitButton = new ToolBarButton("exit.png");
        exitButton.setToolTipText("Hide this window");
        exitButton.addActionListener(new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                hideWindow();
            }
        });

        toolBar = new JToolBar();
        getContentPane().add(toolBar, BorderLayout.NORTH);

        mainPanel = getMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        buildMenu();

    }

    public static void errMessageBox(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void infoMessageBox(String title, String msg) {
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static int yesNo(String msg, String title) {
        int ok = JOptionPane.showConfirmDialog(null, title, msg,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return ok;
    }

    public static void notImplementedYet() {
        errMessageBox("Sorry!", "Not implemented yet");
    }

    public void setStatusLabel1Text(String lbl) {
        statusLabel1.setText(lbl);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = createMenu("File", "File Operations");
        JMenuItem mi = createMenuItem("Hide", "Hide this window");
        mi.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                hideWindow();
            }
        });
        m.add(mi);
        bar.add(m);

        setJMenuBar(bar);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane workTab = new JTabbedPane();
        workTab.add(new JPanel(), "Contracts");
        workTab.add(new JPanel(), "Quotas");
        workTab.add(new JPanel(), "Orders");
        return workTab;
    }

    protected JMenuItem createMenuItem(String label, String microHelp) {
        JMenuItem m = new JMenuItem(label);
        setMenuStatusMicroHelp(m, microHelp);
        return m;
    }

    protected JMenuItem createMenuItem(String label) {
        return createMenuItem(label, label);
    }

    protected JMenu createMenu(String label, String microHelp) {
        JMenu m = new JMenu(label);
        setMenuStatusMicroHelp(m, microHelp);
        return m;
    }

    protected JMenu createMenu(String label) {
        return createMenu(label, label);
    }

    protected void setMenuStatusMicroHelp(final JMenuItem m, final String msg) {
        m.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                statusLabel2.setText(msg == null ? m.getText() : msg);
            }
        });
    }

    private void hideWindow() {
        setVisible(false);
        DashBoard.saveProps();
    }
}
