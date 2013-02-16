package com.xlend.gui.parts;

import com.xlend.gui.*;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xpartcategory;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.util.PopupListener;
import com.xlend.util.ToolBarButton;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.*;
import java.rmi.RemoteException;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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
    private JTree leftTree;
    private JScrollPane treeScrollPane;
    private PartsGrid gridView;
    private TitledBorder partsBorder;
    private AbstractAction addSubcategoryAction = null;
    private AbstractAction deleteSubcategoryAction = null;
    private AbstractAction editSubcategoryAction = null;
//    private AbstractAction delAct;
    private JMenuItem delCatMenuItem;
    private JLabel srcLabel;
    private JTextField srcField;

    protected AbstractAction getAddSubcategoryAction() {
        if (addSubcategoryAction == null) {
            addSubcategoryAction = new AbstractAction("Add subcategory") {
                @Override
                public void actionPerformed(ActionEvent e) {

                    EditCategoryPanel.parentID = getCurCategoryID();
                    if (EditCategoryPanel.parentID != 0) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) leftTree.getSelectionPaths()[0].getLastPathComponent();
                        EditCategoryDialog ed = new EditCategoryDialog("Add subcategory", null);
                        if (EditCategoryDialog.okPressed) {
                            Xpartcategory xpc = (Xpartcategory) ed.getEditPanel().getDbObject();
                            DefaultMutableTreeNode child;
                            selectedNode.add(child = new DefaultMutableTreeNode(new CategoryNode(xpc), true));
                            DefaultTreeModel model = (DefaultTreeModel) leftTree.getModel();
                            model.reload(selectedNode);
                            leftTree.expandPath(new TreePath(selectedNode.getPath()));
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "First select a category please");
                    }
                }
            };

        }
        return addSubcategoryAction;
    }

    protected AbstractAction getDeleteSubcategoryAction() {
        if (deleteSubcategoryAction == null) {
            deleteSubcategoryAction = new AbstractAction("Delete category") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EditCategoryPanel.parentID = getCurCategoryID();
                    if (EditCategoryPanel.parentID != 0) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) leftTree.getSelectionPaths()[0].getLastPathComponent();
                        CategoryNode cat = (CategoryNode) selectedNode.getUserObject();
                        try {
                            DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                            DashBoard.getExchanger().deleteObject(cat.getXpartCategory());
                            selectedNode.removeFromParent();
                            DefaultTreeModel model = (DefaultTreeModel) leftTree.getModel();
                            model.reload(parentNode);
                            leftTree.expandPath(new TreePath(parentNode.getPath()));
                        } catch (RemoteException ex) {
                            XlendWorks.logAndShowMessage(ex);
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "First select a category please");
                    }
                }
            };
        }
        return deleteSubcategoryAction;
    }

    protected AbstractAction getEditSubcategoryAction() {
        if (editSubcategoryAction == null) {
            editSubcategoryAction = new AbstractAction("Edit category") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EditCategoryPanel.parentID = getCurCategoryID();
                    if (EditCategoryPanel.parentID != 0) {
                        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) leftTree.getSelectionPaths()[0].getLastPathComponent();
                        CategoryNode cat = (CategoryNode) selectedNode.getUserObject();
                        Xpartcategory xpc = cat.getXpartCategory();
                        EditCategoryDialog ed = new EditCategoryDialog("Edit subcategory", xpc);
                        if (EditCategoryDialog.okPressed) {
                            cat.setXpartCategory((Xpartcategory) ed.getEditPanel().getDbObject());
                            selectedNode.setUserObject(cat);
                            DefaultTreeModel model = (DefaultTreeModel) leftTree.getModel();
                            model.reload(selectedNode);
                            leftTree.expandPath(new TreePath(selectedNode.getPath()));
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "First select a category please");
                    }
                }
            };
        }
        return editSubcategoryAction;
    }

    private KeyListener getSrcFieldKeyListener() {
        return new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                highlightFound();
            }
        };
    }

    private void highlightFound() {
        Component selectedPanel = gridView;
        if (selectedPanel instanceof GeneralGridPanel) {
            GeneralGridPanel selectedGridPanel = (GeneralGridPanel) selectedPanel;
            try {
                RowFilter<DbTableView.MyTableModel, Object> rf = RowFilter.regexFilter(srcField.getText());
                selectedGridPanel.getTableView().getSorter().setRowFilter(rf);
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
        }
    }

    private class CategoryNode {

        private Xpartcategory xpartCategory;

        CategoryNode(Xpartcategory xpc) {
            this.xpartCategory = xpc;
        }

        @Override
        public String toString() {
            return getXpartCategory().getName();
        }

        public Xpartcategory getXpartCategory() {
            return xpartCategory;
        }

        public void setXpartCategory(Xpartcategory xpartCategory) {
            this.xpartCategory = xpartCategory;
        }
    }

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

        toolBar = new JToolBar();

        refreshButton = new ToolBarButton("refresh.png");
        refreshButton.setToolTipText("Refresh data");
        aboutButton = new ToolBarButton("help.png");
        aboutButton.setToolTipText("About program");
        exitButton = new ToolBarButton("exit.png");
        exitButton.setToolTipText("Hide this window");

        toolBar = new JToolBar();
        toolBar.add(searchButton);
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

        exitButton.setToolTipText("Close this window");
        exitButton.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        getContentPane().add(toolBar, BorderLayout.NORTH);

        mainComponent = getMainPanel();
        getContentPane().add(mainComponent, BorderLayout.CENTER);
        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        buildMenu();
        activatePopup();
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

        m = createMenu("Edit", "Dictionary Edit Operations");
        mi = createMenuItem("Add subcategory", "Add new subcategory");
        mi.addActionListener(getAddSubcategoryAction());
        m.add(mi);
        mi = createMenuItem("Edit category", "Edit category name");
        mi.addActionListener(getEditSubcategoryAction());
        m.add(mi);
        delCatMenuItem = createMenuItem("Delete category", "Delete category");
        delCatMenuItem.setEnabled(false);
        delCatMenuItem.addActionListener(getDeleteSubcategoryAction());
        m.add(delCatMenuItem);

        bar.add(m);

        setJMenuBar(bar);
    }

    public void setStatusLabel1Text(String lbl) {
        statusLabel1.setText(lbl);
    }

    private JComponent getMainPanel() {
        JSplitPane splitPanelV = new JSplitPane(1);
        splitPanelV.setLeftComponent(treeScrollPane = new JScrollPane(getLeftTree()));
        treeScrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Categories"));
        try {
            splitPanelV.setRightComponent(gridView = new PartsGrid(DashBoard.getExchanger(), getCurCategoryID()));
            gridView.setBorder(partsBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Parts"));
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
            splitPanelV.setRightComponent(new JLabel("ERROR:" + ex.getMessage()));
        }

        splitPanelV.setDividerLocation(200);
        return splitPanelV;
    }

    private String getSelectedCategoryName() {
        TreePath[] paths = leftTree.getSelectionPaths();
        if (paths != null && paths.length > 0) {
            TreePath path = paths[0];
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            return selectedNode.getUserObject().toString();
        }
        return "";
    }

    private Xpartcategory getCurrentXpartcategory() {
        TreePath[] paths = leftTree.getSelectionPaths();
        if (paths != null && paths.length > 0) {
            TreePath path = paths[0];
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
            CategoryNode cat = (CategoryNode) selectedNode.getUserObject();
            return cat.getXpartCategory();
        }
        return null;
    }

    private int getCurCategoryID() {
        Xpartcategory xpc = getCurrentXpartcategory();
        if (xpc != null) {
            return xpc.getXpartcategoryId().intValue();
        }
        return 0;
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
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean pressed = searchButton.isSelected();
                srcLabel.setVisible(pressed);
                srcField.setVisible(pressed);
                if (pressed) {
                    srcField.requestFocus();
                }
            }
        };
    }

    public void activatePopup() {
        JPopupMenu pop = new JPopupMenu();

        pop.add(getAddSubcategoryAction());
        pop.add(getEditSubcategoryAction());
        pop.add(getDeleteSubcategoryAction());
        getLeftTree().addMouseListener(new PopupListener(pop));
        treeScrollPane.addMouseListener(new PopupListener(pop));
    }

    private JTree getLeftTree() {
        if (null == leftTree) {
            leftTree = new JTree();
            fillTree();

            leftTree.addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(TreeSelectionEvent e) {
                    Xpartcategory xpc = getCurrentXpartcategory();
                    if (xpc != null) {
                        deleteSubcategoryAction.setEnabled(xpc.getParentId() != null && xpc.getParentId() != 0);
                        delCatMenuItem.setEnabled(xpc.getParentId() != null && xpc.getParentId() != 0);
                    }
                    partsBorder.setTitle("Parts of category:" + getSelectedCategoryName());
                    gridView.selectCategoryID(getCurCategoryID());
                    gridView.repaint();
                }
            });
        }
        return leftTree;
    }

    private void fillTree() {
        try {
            DbObject[] obs = exchanger.getDbObjects(Xpartcategory.class, "name='" + getTitle() + "'", null);
            DefaultMutableTreeNode root = new DefaultMutableTreeNode(new CategoryNode((Xpartcategory) obs[0]), true);
            fillSubCategories(root);
            getLeftTree().setModel(new DefaultTreeModel(root));
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }

    private void fillSubCategories(DefaultMutableTreeNode parentNode) {
        CategoryNode catNode = (CategoryNode) parentNode.getUserObject();
        Xpartcategory xpc = catNode.getXpartCategory();
        try {
            for (DbObject o : exchanger.getDbObjects(Xpartcategory.class,
                    "parent_id=" + xpc.getXpartcategoryId(), null)) {
                DefaultMutableTreeNode node = new DefaultMutableTreeNode(new CategoryNode((Xpartcategory) o), true);
                parentNode.add(node);
                fillSubCategories(node);
            }
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }
}
