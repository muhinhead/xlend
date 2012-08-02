package com.xlend.gui.parts;

import com.xlend.gui.AboutDialog;
import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ToolBarButton;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
public class PartsCategoriesFrame extends JFrame implements WindowListener {

    private JComponent mainComponent;
    private final IMessageSender exchanger;
    private final String rootCategory;
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JToolBar toolBar;
    private ToolBarButton aboutButton;
    private ToolBarButton exitButton;
    private ToolBarButton refreshButton;
    private ToolBarButton printButton;
    private JToggleButton searchButton;

    public PartsCategoriesFrame(String title, IMessageSender exch, String rootCategory) {
        super(title);
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.exchanger = exch;
        this.rootCategory = rootCategory;
        fillContentPane();
        float width = Float.valueOf(DashBoard.readProperty("WindowWidth", "0.8"));
        float height = Float.valueOf(DashBoard.readProperty("WindowHeight", "0.8"));
        boolean maximize = (width < 0 || width < 0);
        width = (width > 0.0 ? width : (float) 0.8);
        height = (height > 0.0 ? height : (float) 0.8);
        DashBoard.setSizes(this, width, height);
        DashBoard.centerWindow(this);
        if (maximize) {
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        //TODO:
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
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
        statusPanel.add(statusLabel2, BorderLayout.CENTER);

        printButton = new ToolBarButton("print.png");
        printButton.setToolTipText("Print current tab");
        printButton.addActionListener(getPrintAction());

        searchButton = new JToggleButton(new ImageIcon(Util.loadImage("search.png")));
        searchButton.setToolTipText("Search on fragment");
        searchButton.addActionListener(getSearchAction());

//        filterButton = new JToggleButton(new ImageIcon(Util.loadImage("filter.png")));
//        filterButton.setToolTipText("Filter on fragment");
//        filterButton.addActionListener(getFilterAction());
        toolBar = new JToolBar();
//        toolBar.add(searchButton);
//        toolBar.add(srcLabel = new JLabel("  Search for:"));
//        toolBar.add(srcField = new JTextField(20));
//        srcLabel.setVisible(false);
//        srcField.setVisible(false);
//        srcField.addKeyListener(getSrcFieldKeyListener());
//        srcField.setMaximumSize(srcField.getPreferredSize());

//        toolBar.add(filterButton);
//        toolBar.add(fltrLabel = new JLabel("  Filter on:"));
//        toolBar.add(fltrField = new JTextField(20));
//        fltrLabel.setVisible(false);
//        fltrField.setVisible(false);
//        fltrField.addKeyListener(getFilterFieldKeyListener());
//        fltrField.setMaximumSize(fltrField.getPreferredSize());

        refreshButton = new ToolBarButton("refresh.png");
        refreshButton.setToolTipText("Refresh data");
        aboutButton = new ToolBarButton("help.png");
        aboutButton.setToolTipText("About program");
        exitButton = new ToolBarButton("exit.png");
        exitButton.setToolTipText("Hide this window");

        toolBar = new JToolBar();
        toolBar.add(printButton);
        toolBar.add(refreshButton);
        toolBar.add(aboutButton);
        toolBar.add(exitButton);
        aboutButton.setToolTipText("About program...");
        aboutButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new AboutDialog();
            }
        });

        exitButton.setToolTipText("Close thiw window");

        getContentPane().add(toolBar, BorderLayout.NORTH);

        mainComponent = (JPanel) getMainPanel();
        getContentPane().add(mainComponent, BorderLayout.CENTER);
//        mainPanel.addChangeListener(new ChangeListener() {
//
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                searchButton.setSelected(false);
//                srcField.setText(null);
//                srcField.setVisible(false);
//                srcLabel.setVisible(false);
////                fltrField.setText(null);
////                fltrField.setVisible(false);
////                fltrLabel.setVisible(false);
//                highlightFound();
//            }
//        });

        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        buildMenu();
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

    protected void setMenuStatusMicroHelp(final JMenuItem m, final String msg) {
        m.addChangeListener(new ChangeListener() {

            public void stateChanged(ChangeEvent e) {
                statusLabel2.setText(msg == null ? m.getText() : msg);
            }
        });
    }

    protected JMenu createMenu(String label) {
        return createMenu(label, label);
    }

    private void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu m = createMenu("File", "File Operations");
        JMenuItem mi = createMenuItem("Hide", "Hide this window");
        mi.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        m.add(mi);
        bar.add(m);

        setJMenuBar(bar);
    }

    public void setStatusLabel1Text(String lbl) {
        statusLabel1.setText(lbl);
    }

    private JComponent getMainPanel() {
        JSplitPane splitPa1nelV = new JSplitPane(1);
        
        return splitPa1nelV;
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

    public static void notImplementedYet(String msg) {
        errMessageBox("Sorry!", "Not implemented yet " + msg);
    }

    public void setLookAndFeel(String lf) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException,
            UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(lf);
        SwingUtilities.updateComponentTreeUI(this);
        DashBoard.getProperties().setProperty("LookAndFeel", lf);
    }

    protected ActionListener getPrintAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                notImplementedYet();
            }
        };
    }

    private ActionListener getSearchAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                notImplementedYet();
            }
        };
    }
}
