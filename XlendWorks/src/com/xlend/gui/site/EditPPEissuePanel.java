package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xppeissue;
import com.xlend.orm.Xppeissueitem;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditPPEissuePanel extends RecordEditPanel {

    private DefaultComboBoxModel issuedByCbModel;
    private DefaultComboBoxModel issuedToCbModel;
    private DefaultComboBoxModel authorizedByCbModel;
    private JTextField idField;
    private SelectedDateSpinner issueDateSP;
    private JComboBox issuedByCB;
    private JComboBox issuedToCB;
    private JComboBox authorizedByCB;
    private JCheckBox markAllCB;
    private JPanel downGridPanel;
    private ArrayList<EditPPEissuePanel.ItemPanel> childRows;
    private ArrayList<EditPPEissuePanel.ItemPanel> toDelete;
    private JComponent hdrPanel;

    private class ItemPanel extends JPanel {

        private Xppeissueitem item;
        private JCheckBox markCB;
        private JComboBox ppeTypeCB;
        private DefaultComboBoxModel ppeTypeCbModel;
        private JSpinner qtySP;
        private JLabel stockLevelLB;

        ItemPanel(Xppeissueitem item) {
            super(new BorderLayout());
            this.item = item;
            stockLevelLB = new JLabel("", SwingConstants.RIGHT);
            stockLevelLB.setBorder(BorderFactory.createEtchedBorder());
            ppeTypeCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllPPEtypes(DashBoard.getExchanger())) {
                ppeTypeCbModel.addElement(ci);
            }
            add(markCB = new JCheckBox(), BorderLayout.WEST);
            add(comboPanelWithLookupBtn(ppeTypeCB = new JComboBox(ppeTypeCbModel),
                    new PPElookupAction(ppeTypeCB)), BorderLayout.CENTER);
            qtySP = new SelectedNumberSpinner(0, 0, 999999, 1);
            JPanel rightGridPanel = new JPanel(new GridLayout(1, 2));
            rightGridPanel.add(qtySP);
            rightGridPanel.add(stockLevelLB);
            add(rightGridPanel, BorderLayout.EAST);
            ppeTypeCB.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    updateStockLevels();
                }
            });
            load();
        }

        private void updateStockLevels() {
            String slevel = XlendWorks.getStockLevels(DashBoard.getExchanger(), getSelectedCbItem(ppeTypeCB));
            int ilevel = Integer.parseInt(slevel);
            SpinnerNumberModel model = (SpinnerNumberModel) qtySP.getModel();
            model.setMaximum(ilevel);
            stockLevelLB.setText(slevel);
        }

        private void load() {
            if (getItem() != null) {
                selectComboItem(ppeTypeCB, getItem().getXppetypeId());
                qtySP.setValue(getItem().getQuantity());
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getItem() == null) {
                item = new Xppeissueitem(null);
                getItem().setXppeissueitemId(0);
                Xppeissue xi = (Xppeissue) getDbObject();
                getItem().setXppeissueId(xi.getXppeissueId());
                isNew = true;
            }
            getItem().setXppetypeId(getSelectedCbItem(ppeTypeCB));
            getItem().setQuantity((Integer) qtySP.getValue());
            return saveDbRecord(getItem(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xppeissueitem) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        /**
         * @return the item
         */
        public Xppeissueitem getItem() {
            return item;
        }
    }

    public EditPPEissuePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Issued by:",
            "Issued to:",
            "Authorized by:"
        };
        issuedByCbModel = new DefaultComboBoxModel();
        issuedToCbModel = new DefaultComboBoxModel();
        authorizedByCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            issuedByCbModel.addElement(ci);
            issuedToCbModel.addElement(ci);
            authorizedByCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 6),
            getGridPanel(issueDateSP = new SelectedDateSpinner(), 6),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel), new EmployeeLookupAction(issuedByCB)),
                new JPanel()}),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(issuedToCB = new JComboBox(issuedToCbModel), new EmployeeLookupAction(issuedToCB)),
                new JPanel()
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(authorizedByCB = new JComboBox(authorizedByCbModel),
                new EmployeeLookupAction(authorizedByCB, "coalesce(wage_category,1)=1")),
                getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), createItmBtnPanel()})
            })
        };
        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane;
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        JPanel rghtHdrPanel;

        hdrPanel = getBorderPanel(new JComponent[]{
                    markAllCB = new JCheckBox(),
                    new JPanel(),
                    rghtHdrPanel = new JPanel(new GridLayout(1, 2))
                });
        rghtHdrPanel.add(new JLabel("Quantity:", SwingConstants.LEFT));
        rghtHdrPanel.add(new JLabel("Stock Levels:", SwingConstants.LEFT));
        downGridPanel.add(hdrPanel);

        markAllCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    p.mark(markAllCB.isSelected());
                }
            }
        });
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items Issued"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width / 3, 300));
    }
    
    private JPanel createItmBtnPanel() {
        JPanel itemBtnPanel = new JPanel(new GridLayout(1, 2));
        JButton addBtn;
        itemBtnPanel.add(addBtn=new JButton(new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new ItemPanel(null));
                redrawRows();
            }
        }));
        addBtn.setToolTipText("Add Item");
        JButton delBtn;
        itemBtnPanel.add(delBtn=new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (ItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        delBtn.setToolTipText("Delete Item(s)");
        return itemBtnPanel;
    }    

    @Override
    public void loadData() {
        Xppeissue xi = (Xppeissue) getDbObject();
        if (xi != null) {
            idField.setText(xi.getXppeissueId().toString());
            issueDateSP.setValue(new java.util.Date(xi.getIssuedate().getTime()));
            selectComboItem(issuedByCB, xi.getIssuedbyId());
            selectComboItem(issuedToCB, xi.getIssuedtoId());
            selectComboItem(authorizedByCB, xi.getAuthorizedbyId());
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xppeissueitem.class,
                        "xppeissue_id=" + xi.getXppeissueId(), "xppeissueitem_id");
                for (DbObject rec : recs) {
                    childRows.add(new ItemPanel((Xppeissueitem) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }            
        }
    }

    @Override
    public boolean save() throws Exception {
        Xppeissue xi = (Xppeissue) getDbObject();
        boolean isNew = false;
        if (xi == null) {
            xi = new Xppeissue(null);
            xi.setXppeissueId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        if (dt != null) {
            xi.setIssuedate(new java.sql.Date(dt.getTime()));
        }
        xi.setIssuedbyId(getSelectedCbItem(issuedByCB));
        xi.setIssuedtoId(getSelectedCbItem(issuedToCB));
        xi.setAuthorizedbyId(getSelectedCbItem(authorizedByCB));
        boolean ok = saveDbRecord(xi, isNew);
        if (ok) {
            for (ItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (ItemPanel d : toDelete) {
                if (d.getItem() != null) {
                    DashBoard.getExchanger().deleteObject(d.getItem());
                }
            }
        }
        return ok;        
    }
    
    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (ItemPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }    
}
