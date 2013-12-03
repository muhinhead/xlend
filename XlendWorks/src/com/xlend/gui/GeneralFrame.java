package com.xlend.gui;

import com.xlend.gui.reports.GeneralReportPanel;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView.MyTableModel;
import com.xlend.mvc.dbtable.ITableView;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ToolBarButton;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Admin
 */
public abstract class GeneralFrame extends JFrame implements WindowListener {

    private IMessageSender exchanger;
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JTabbedPane mainPanel;
    private ToolBarButton aboutButton;
    private ToolBarButton exitButton;
    private JToolBar toolBar;
    private ToolBarButton refreshButton;
    private ToolBarButton printButton;
    private JToggleButton searchButton;
//    private JToggleButton filterButton;
    private HashMap<DbTableGridPanel, String> grids = new HashMap<DbTableGridPanel, String>();
    private HashMap<GeneralReportPanel, String> reports = new HashMap<GeneralReportPanel, String>();
//    private HashMap<HTMLpanel, String> browsers = new HashMap<HTMLpanel, String>();
    private JLabel srcLabel;
    private JTextField srcField;
//    private JLabel fltrLabel;
//    private JTextField fltrField;

    public GeneralFrame(String title, IMessageSender exch) {
        super(title);
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.exchanger = exch;
        fillContentPane();
        float width = Float.valueOf(DashBoard.readProperty("WindowWidth", "0.8"));
        float height = Float.valueOf(DashBoard.readProperty("WindowHeight", "0.8"));
        boolean maximize = (width < 0 || width < 0);
        width = (width > 0.0 ? width : (float) 0.8);
        height = (height > 0.0 ? height : (float) 0.8);
        AbstractDashBoard.setSizes(this, width, height);
        AbstractDashBoard.centerWindow(this);
        if (maximize) {
            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        }
        setVisible(true);
    }

    public GeneralFrame(IMessageSender exch) {
        this("Works", exch);
    }

    protected abstract String[] getSheetList();

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
        statusPanel.add(statusLabel2, BorderLayout.CENTER);

        printButton = new ToolBarButton("print.png");
        printButton.setToolTipText("Print current tab");
        printButton.addActionListener(getPrintAction());

        searchButton = new JToggleButton(new ImageIcon(Util.loadImage("search.png")));
        getSearchButton().setToolTipText("Search on fragment");
        getSearchButton().addActionListener(getSearchAction());

//        filterButton = new JToggleButton(new ImageIcon(Util.loadImage("filter.png")));
//        filterButton.setToolTipText("Filter on fragment");
//        filterButton.addActionListener(getFilterAction());

        refreshButton = new ToolBarButton("refresh.png");
        refreshButton.setToolTipText("Refresh data");
        refreshButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                refreshGrids();
            }
        });
        aboutButton = new ToolBarButton("help.png");
        aboutButton.setToolTipText("About program");
        exitButton = new ToolBarButton("exit.png");
        exitButton.setToolTipText("Hide this window");
        exitButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        toolBar = new JToolBar();
        toolBar.add(getSearchButton());
        toolBar.add(srcLabel = new JLabel("  Filter:"));
        toolBar.add(srcField = new JTextField(20));
        srcLabel.setVisible(false);
        srcField.setVisible(false);
        srcField.addKeyListener(getSrcFieldKeyListener());
        srcField.setMaximumSize(srcField.getPreferredSize());

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

        mainPanel = getMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                getSearchButton().setSelected(false);
                srcField.setText(null);
                srcField.setVisible(false);
                srcLabel.setVisible(false);
//                fltrField.setText(null);
//                fltrField.setVisible(false);
//                fltrLabel.setVisible(false);
                highlightFound();
            }
        });

        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        buildMenu();

    }

    private KeyAdapter getSrcFieldKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                highlightFound();
            }
        };
    }

    private void highlightFound() {
        Component selectedPanel = mainPanel.getSelectedComponent();
        if (selectedPanel instanceof GeneralGridPanel) {
            GeneralGridPanel selectedGridPanel = (GeneralGridPanel) selectedPanel;
            try {
                RowFilter<MyTableModel, Object> rf = RowFilter.regexFilter("(?i)" + srcField.getText());
                selectedGridPanel.getTableView().getSorter().setRowFilter(rf);
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
//            selectedGridPanel.highlightSearch(srcField.getText());
        }
    }

    public void setVisible(boolean b) {
        if (b) {
            refreshGrids();
        }
        super.setVisible(b);
    }

    private ActionListener getSearchAction() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean pressed = getSearchButton().isSelected();
                srcLabel.setVisible(pressed);
                srcField.setVisible(pressed);
                if (pressed) {
                    srcField.requestFocus();
                }
            }
        };
    }

//    private ActionListener getFilterAction() {
//        return new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                boolean pressed = filterButton.isSelected();
//                fltrLabel.setVisible(pressed);
//                fltrField.setVisible(pressed);
//            }
//        };
//    }
    private void refreshGrids() {
        for (DbTableGridPanel grid : grids.keySet()) {
            try {
                updateGrid(getExchanger(), grid.getTableView(), grid.getTableDoc(), grids.get(grid), null,
                        grid.getPageSelector().getSelectedIndex());
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
        for (GeneralReportPanel report : reports.keySet()) {
            report.updateReport();
        }
//        for (HTMLpanel html : browsers.keySet()) {
//            try {
//                html.refresh();
//            } catch (IOException ex) {
//                XlendWorks.logAndShowMessage(ex);
//            }
//        }
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

    public void setStatusLabel1Text(String lbl) {
        statusLabel1.setText(lbl);
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

//        m = createMenu("Edit", "Edit operations");
//        mi = createMenuItem("Find...", "Search on fragment");
//        mi.addActionListener(getSearchAction());
//        m.add(mi);
//        bar.add(m);

        setJMenuBar(bar);
    }

    protected abstract JTabbedPane getMainPanel();

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

    public static void updateGrid(IMessageSender exchanger,
            ITableView view, DbTableDocument doc, String select, Integer id, int page)
            throws RemoteException {
        int row = view.getSelectedRow();
        try {
            if (select != null) {
                ((JComponent) view).setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));;
                if (page >= 0) {
                    doc.setBody(exchanger.getTableBody(select, page, GeneralGridPanel.PAGESIZE));
                } else {
                    doc.setBody(exchanger.getTableBody(select));
                }
                view.getController().updateExcept(null);
                if (id != null) {
                    DbTableGridPanel.selectRowOnId(view, id);
                } else {
                    row = row < view.getRowCount() ? row : row - 1;
                    if (row >= 0 && row < view.getRowCount()) {
                        view.setRowSelectionInterval(row, row);
                        if (view instanceof JTable) {
                            ((JTable)view).scrollRectToVisible(((JTable)view).getCellRect(row, 0, true));  
                        }
                    }
                }
            }
        } finally {
            ((JComponent) view).setCursor(Cursor.getDefaultCursor());
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        float xRatio = -1;
        float yRatio = -1;
        if (this.getExtendedState() != JFrame.MAXIMIZED_BOTH) {
            xRatio = DashBoard.getXratio(this);
            yRatio = DashBoard.getYratio(this);
        }
        DashBoard.getProperties().setProperty("WindowWidth", "" + xRatio);
        DashBoard.getProperties().setProperty("WindowHeight", "" + yRatio);
        DashBoard.saveProps();
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

//    private JPanel getContractsPanel() {
//        if (contractsPanel == null) {
//            HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
//            maxWidths.put(0, 40);
//            maxWidths.put(1, 150);
//            contractsPanel = createAndRegisterGrid(Selects.SELECT_FROM_CONTRACTS,
//                    addContractAction(), editContractAction(),
//                    delContractAction(), maxWidths);
//            grids.put(contractsPanel, Selects.SELECT_FROM_CONTRACTS);
//        }
//        return contractsPanel;
//    }
//    protected DbTableGridPanel createAndRegisterGrid(String select,
//            AbstractAction add, AbstractAction edit, AbstractAction del, HashMap<Integer, Integer> maxWidths) {
//        DbTableGridPanel panel = createGridPanel(getExchanger(), select, add, edit, del, maxWidths);
//        grids.put(panel, select);
//        return panel;
//    }
//    public static DbTableGridPanel createGridPanel(IMessageSender exchanger, String select,
//            AbstractAction add, AbstractAction edit, AbstractAction del, HashMap<Integer, Integer> maxWidths) {
//        DbTableGridPanel targetPanel = null;
//        try {
//            targetPanel = new DbTableGridPanel(
//                    add, edit, del, exchanger.getTableBody(select), maxWidths);
//            if (del != null) {
//                boolean enableDel = (XlendWorks.getCurrentUser().getManager() == 1 
//                        || XlendWorks.getCurrentUser().getSupervisor() == 1);
//                targetPanel.getDelAction().setEnabled(enableDel);
//            }
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//        }
//        return targetPanel;
//    }
    protected void registerGrid(GeneralGridPanel grid) {
        grids.put(grid, grid.getSelect());
    }

    protected void registerGrid(GeneralReportPanel repPanel) {
        reports.put(repPanel, repPanel.toString());
    }

//    protected void registerGrid(HTMLpanel browserPanel) {
//        browsers.put(browserPanel, browserPanel.getUrlString());
//    }
    
    /**
     * @return the exchanger
     */
    public IMessageSender getExchanger() {
        return exchanger;
    }

    protected ActionListener getPrintAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                notImplementedYet();
            }
        };
    }

    /**
     * @return the searchButton
     */
    protected JToggleButton getSearchButton() {
        return searchButton;
    }
}
