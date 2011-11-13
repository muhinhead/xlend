package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.gui.contract.EditContractDialog;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
import com.xlend.remote.IMessageSender;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.rmi.RemoteException;
import java.util.HashMap;
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

/**
 *
 * @author Admin
 */
public class WorkFrame extends JFrame implements WindowListener {

    protected IMessageSender exchanger;
//    private final Properties props;
    private JPanel statusPanel = new JPanel();
    private JLabel statusLabel1 = new JLabel();
    private JLabel statusLabel2 = new JLabel();
    private JLabel statusLabel3 = new JLabel();
//    private DbTableGridPanel usersPanel = null;
    private DbTableGridPanel contractsPanel = null;
    private JTabbedPane mainPanel;
    private ToolBarButton aboutButton;
    private ToolBarButton exitButton;
    private JToolBar toolBar;
//    protected WorkFrame _this;

    public WorkFrame(String title, IMessageSender exch) {
        super(title);
//        _this = this;
        addWindowListener(this);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.exchanger = exch;
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

    public WorkFrame(IMessageSender exch) {
        this("Works", exch);
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
                setVisible(false);
            }
        });

        toolBar = new JToolBar();
        toolBar.add(aboutButton);
        toolBar.add(exitButton);
        getContentPane().add(toolBar, BorderLayout.NORTH);

        mainPanel = getMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        getContentPane().add(statusPanel, BorderLayout.SOUTH);

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
                setVisible(false);
            }
        });
        m.add(mi);
        bar.add(m);

        setJMenuBar(bar);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane workTab = new JTabbedPane();
        workTab.add(getContractsPanel(), "Contracts");
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

    protected void updateGrid(DbTableView view, DbTableDocument doc, String select)
            throws RemoteException {
        int row = view.getSelectedRow();
        doc.setBody(exchanger.getTableBody(select));
        view.getController().updateExcept(null);
        row = row < view.getRowCount() ? row : row - 1;
        if (row >= 0) {
            view.setRowSelectionInterval(row, row);
        }
    }

//    private void hideWindow() {
//        setVisible(false);
//    }
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

    private JPanel getContractsPanel() {
        if (contractsPanel == null) {
            HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
            maxWidths.put(0, 40);
            maxWidths.put(1, 150);
            contractsPanel = createGridPanel(exchanger,Selects.SELECT_FROM_CONTRACTS,
                    addContractAction(), editContractAction(),
                    delContractAction(), maxWidths);
        }
        return contractsPanel;

    }

    public static DbTableGridPanel createGridPanel(IMessageSender exchanger, String select,
            AbstractAction add, AbstractAction edit, AbstractAction del, HashMap<Integer, Integer> maxWidths) {
        DbTableGridPanel targetPanel = null;
        try {
            targetPanel = new DbTableGridPanel(
                    add, edit, del, exchanger.getTableBody(select), maxWidths);
            if (del != null) {
                targetPanel.getDelAction().setEnabled(XlendWorks.getCurrentUser().getManager() == 1);
            }
        } catch (RemoteException ex) {
            ex.printStackTrace();
        }
        return targetPanel;
    }

    private AbstractAction addContractAction() {
        return new AbstractAction("New Contract") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditContractDialog("New Contract", null);
                    if (EditContractDialog.okPressed) {
                        updateGrid(contractsPanel.getTableView(),
                                contractsPanel.getTableDoc(), Selects.SELECT_FROM_CONTRACTS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction editContractAction() {
        return new AbstractAction("Edit Contract") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = contractsPanel.getSelectedID();
                if (id > 0) {
                    try {
                        Xcontract xcontract = (Xcontract) exchanger.loadDbObjectOnID(Xcontract.class, id);
                        new EditContractDialog("Edit Contract", xcontract);
                        if (EditContractDialog.okPressed) {
                            updateGrid(contractsPanel.getTableView(),
                                    contractsPanel.getTableDoc(), Selects.SELECT_FROM_CONTRACTS);
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                        errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction delContractAction() {
        return new AbstractAction("Delete Contract") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = contractsPanel.getSelectedID();
                try {
                    Xcontract xcontract = (Xcontract) exchanger.loadDbObjectOnID(Xclient.class, id);
                    if (yesNo("Attention!", "Do you want to delete contract  [" + xcontract.getContractref() + "]?")
                            == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xcontract);
                        updateGrid(contractsPanel.getTableView(),
                                contractsPanel.getTableDoc(), Selects.SELECT_FROM_CONTRACTS);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }
}
