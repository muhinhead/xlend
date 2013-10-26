/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.BankingFrame;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GetAdminPasswordDialog;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xpetty;
import com.xlend.orm.Xpettyitem;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditPettyPanel extends RecordEditPanel {

    private SelectedDateSpinner issueDateSP, receiptDateSP;
    private JTextField idField;
//    private JComboBox operatorNumberCB;
    private JTextField operatorNumberField;
    private JComboBox employeeInCB;
//    private JComboBox machineCB;
//    private JComboBox siteCB;
    private DefaultComboBoxModel employeeInCbModel;//, siteCbModel;
    private ComboItem[] employeesArray;
    private boolean enableCombo1Update = true;
    private boolean enableNumber1Update = true;
    private boolean enableCombo2Update = true;
    private boolean enableNumber2Update = true;
    private SelectedNumberSpinner amountSP;
    private JRadioButton loanRB;
    private JRadioButton pettyRB;
    private JRadioButton allowRB;
    private JTextField receipterNumberField;
    private JComboBox employeeOutCB;
//    private DefaultComboBoxModel employeeOutCbModel;
    private SelectedNumberSpinner balanceSP;
    private SelectedNumberSpinner balanceIssueSP;
    private JPanel downGridPanel;
    private ArrayList<PettyItemPanel> childRows;
    private ArrayList<PettyItemPanel> toDelete;
    private static String[] hdrs = new String[]{
        "Category", "Machine", "Site", "Amount(R)"
    };
    private JPanel hdrPanel;
    private JCheckBox selectAllCB;
    private JScrollPane scrollPane;
    private JButton addItmBtn;
    private JButton delItmBtn;
    private JTextArea notesArea;
//    private EmployeeLookupAction empOutLookupAction;
    private JToggleButton editToggleBtn;
//    private DefaultComboBoxModel machineCbModel;
    private EmployeeLookupAction empLookupAction;
//    private MachineLookupAction machineLookupAction;
//    private SiteLookupAction siteLookupAction;
    private SelectedNumberSpinner changeSP;
//    private SelectedNumberSpinner balancePersonSP;
    private double initialBalance;
    private boolean insideLoad = false;

    private class PettyItemPanel extends JPanel {

        private DefaultComboBoxModel pttyCategoryCbModel;
        private Xpettyitem xpettyItem;
        private JComboBox pttyCategoryCB;
        private JComboBox machineCB;
        private JComboBox siteCB;
        private MachineLookupAction machineLookupAction;
        private SelectedNumberSpinner amtSP;
        private JCheckBox markCB;
        private final PettyCatergoryLookupAction catLookupAction;
        private SiteLookupAction siteLookupAction;
        private DefaultComboBoxModel siteCbModel;
        private final DefaultComboBoxModel machineCbModel;

        public PettyItemPanel(Xpettyitem xpettyItem) {
            super(new BorderLayout(5, 5));
            this.xpettyItem = xpettyItem;
            pttyCategoryCbModel = new DefaultComboBoxModel(XlendWorks.loadAllXpettyCategories());
            pttyCategoryCB = new JComboBox(pttyCategoryCbModel);
            siteCbModel = new DefaultComboBoxModel(XlendWorks.loadActiveSites());
            markCB = new JCheckBox();
            machineCbModel = new DefaultComboBoxModel();
            machineCbModel.addElement(new ComboItem(0, "--NONE--"));
            for (ComboItem ci : XlendWorks.loadAllMachines()) {
                machineCbModel.addElement(ci);
            }
            machineCB = new JComboBox(machineCbModel);

            add(markCB, BorderLayout.WEST);
            add(getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(pttyCategoryCB = new JComboBox(pttyCategoryCbModel),
                catLookupAction = new PettyCatergoryLookupAction(pttyCategoryCB)),
                comboPanelWithLookupBtn(machineCB, machineLookupAction = new MachineLookupAction(machineCB, null)),
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), siteLookupAction = new SiteLookupAction(siteCB))
            }), BorderLayout.CENTER);
            add(amtSP = new SelectedNumberSpinner(0.0, 0.0, 9999999.0, 0.01), BorderLayout.EAST);

            load();
            amtSP.addChangeListener(recalcItemsAmountListener());
        }

        public void setEnabled(boolean enable) {
            markCB.setEnabled(enable);
            pttyCategoryCB.setEnabled(enable);
            catLookupAction.setEnabled(enable);
            machineCB.setEnabled(enable);
            siteCB.setEnabled(enable);
            machineLookupAction.setEnabled(enable);
            siteLookupAction.setEnabled(enable);
            amtSP.setEnabled(enable);
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (xpettyItem != null) {
                selectComboItem(pttyCategoryCB, xpettyItem.getXpettycategoryId());
                if (xpettyItem.getAmount() != null) {
                    amtSP.setValue(xpettyItem.getAmount());
                }
                selectComboItem(siteCB, xpettyItem.getXsiteId());
                selectComboItem(machineCB, xpettyItem.getXmachineId());
            } else {
                xpettyItem = new Xpettyitem(null);
            }
        }

        private boolean save() throws Exception {
            boolean isNew = false;
            if (xpettyItem == null || xpettyItem.getXpettyitemId() == null) {
                isNew = true;
                xpettyItem = new Xpettyitem(null);
                xpettyItem.setXpettyitemId(0);
                Xpetty xpetty = (Xpetty) getDbObject();
                xpettyItem.setXpettyId(xpetty.getXpettyId());
            }
            xpettyItem.setXsiteId(getSelectedCbItem(siteCB));
            xpettyItem.setXmachineId(getSelectedCbItem(machineCB));
            xpettyItem.setAmount((Double) amtSP.getValue());
            xpettyItem.setXpettycategoryId(getSelectedCbItem(pttyCategoryCB));
            return saveDbRecord(getXpettyItem(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xpettyItem = (Xpettyitem) XlendWorks.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xpettyItem
         */
        public Xpettyitem getXpettyItem() {
            return xpettyItem;
        }
    }

    public EditPettyPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<PettyItemPanel>();
        toDelete = new ArrayList<PettyItemPanel>();
        setLayout(new BorderLayout());
        JSplitPane splitter;
//        add(headerPanel, BorderLayout.NORTH);
        add(splitter = new JSplitPane());
        splitter.setTopComponent(getLeftPanel());
        splitter.setBottomComponent(getRightPanel());

        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        receiptDateSP.setEditor(new JSpinner.DateEditor(receiptDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(receiptDateSP);
        idField.setEnabled(false);
        balanceSP.setEnabled(false);
        balanceIssueSP.setEnabled(false);
        amountSP.addChangeListener(recalcItemsAmountListener());
        changeSP.addChangeListener(recalcItemsAmountListener());
    }

    @Override
    public void loadData() {
        insideLoad = true;
        java.util.Date dt = new java.util.Date();
        initialBalance = XlendWorks.getBalance4newXpetty(dt);
        BankingFrame.instance.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        double calcBalance;
        Xpetty xp = (Xpetty) getDbObject();
        if (xp != null) {
            idField.setText(xp.getXpettyId().toString());
            if (xp.getIssueDate() != null) {
                issueDateSP.setValue(dt = new java.util.Date(xp.getIssueDate().getTime()));
            }
            initialBalance = XlendWorks.getBalance4newXpetty(dt) - xp.getAmount() + xp.getChangeAmt();
//            calcBalance = initialBalance;

            //Math.round(100 * XlendWorks.getBalance4newXpetty(dt)) / 100;
            selectComboItem(employeeInCB, xp.getXemployeeInId());
//            selectComboItem(machineCB, xp.getXmachineId());
//            selectComboItem(siteCB, xp.getXsiteId());
            amountSP.setValue(xp.getAmount());

            loanRB.setSelected(xp.getIsLoan() != null && xp.getIsLoan() == 1);
            pettyRB.setSelected(xp.getIsPetty() != null && xp.getIsPetty() == 1);
            allowRB.setSelected(xp.getIsAllowance() != null && xp.getIsAllowance() == 1);
            notesArea.setText(xp.getNotes());
            if (xp.getReceiptDate() != null) {
                receiptDateSP.setValue(new java.util.Date(xp.getReceiptDate().getTime()));
            }
            selectComboItem(employeeOutCB, xp.getXemployeeOutId());
            if (xp.getBalance() != null && (long) (100 * initialBalance) != (long) (100 * xp.getBalance())) {
                GeneralFrame.errMessageBox("Attention!",
                        String.format("stored balance %.2f <> calculated %.2f", xp.getBalance(), initialBalance));
                try {
                    xp.setBalance(initialBalance);
                } catch (Exception ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
            balanceSP.setValue(initialBalance);
            changeSP.setValue(xp.getChangeAmt());
            try {
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xpettyitem.class, "xpetty_id=" + xp.getXpettyId(), "xpettyitem_id");
                for (DbObject rec : recs) {
                    childRows.add(new PettyItemPanel((Xpettyitem) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }

            syncOperatorNumField();
            syncReceipterNumFld();
        } else {
            balanceSP.setValue(initialBalance = XlendWorks.getBalance4newXpetty(dt));
        }

        enableEdit(XlendWorks.isCurrentAdmin());
        editToggleBtn.setSelected(XlendWorks.isCurrentAdmin());
        editToggleBtn.setEnabled(xp != null);
        BankingFrame.instance.setCursor(Cursor.getDefaultCursor());
        insideLoad = false;
    }

    @Override
    public boolean save() throws Exception {
        Xpetty xp = (Xpetty) getDbObject();
        boolean isNew = false;
        if (xp == null) {
            xp = new Xpetty(null);
            xp.setXpettyId(0);
        }
        isNew = xp.getXpettyId() == 0;
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        if (dt != null) {
            xp.setIssueDate(new java.sql.Date(dt.getTime()));
        }
        dt = (Date) receiptDateSP.getValue();
        if (dt != null) {
            xp.setReceiptDate(new java.sql.Date(dt.getTime()));
        }
        xp.setXemployeeOutId(getSelectedCbItem(employeeOutCB));
//        xp.setXsiteId(getSelectedCbItem(siteCB));
//        xp.setXmachineId(getSelectedCbItem(machineCB));
        xp.setIsLoan(loanRB.isSelected() ? 1 : 0);
        xp.setIsPetty(pettyRB.isSelected() ? 1 : 0);
        xp.setIsAllowance(allowRB.isSelected() ? 1 : 0);
        xp.setNotes(notesArea.getText());
        xp.setAmount((Double) amountSP.getValue());
        xp.setChangeAmt((Double) changeSP.getValue());
        xp.setXemployeeInId(getSelectedCbItem(employeeInCB));
        xp.setBalance((Double) balanceSP.getValue());
        boolean ok = saveDbRecord(xp, isNew);
        if (ok) {
            for (PettyItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (PettyItemPanel p : toDelete) {
                if (p.getXpettyItem() != null) {
                    XlendWorks.getExchanger().deleteObject(p.getXpettyItem());
                }
            }
        }
        if (ok && (Double) balanceIssueSP.getValue() > 0.005) {
            //GeneralFrame.errMessageBox("Attention!", "Balance is " + balanceIssueSP.getValue().toString());
            ShowPersonalBalance(
                    ((Double) balanceIssueSP.getValue()).doubleValue(),
                    xp.getXemployeeOutId(), null, xp.getNotes());
        }
        return ok;
    }

    private JPanel getLeftPanel() {
//        siteCbModel = new DefaultComboBoxModel(
//                XlendWorks.loadActiveSites(DashBoard.getExchanger()));
        employeeInCbModel = new DefaultComboBoxModel(
                employeesArray = XlendWorks.loadAllEmployees(Selects.activeEmployeeCondition));
        operatorNumberField = new JTextField();
//        machineCbModel = new DefaultComboBoxModel();
//        machineCbModel.addElement(new ComboItem(0, "--NONE--"));
//        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
//            machineCbModel.addElement(ci);
//        }
//        machineCB = new JComboBox(machineCbModel);

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JScrollPane sp;
        JPanel upperPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        JLabel hdrLbl;
        upperPanel.add(hdrLbl = new JLabel("Petty Out", SwingConstants.CENTER));
        hdrLbl.setFont(hdrLbl.getFont().deriveFont(Font.BOLD, 18));
        upperPanel.add(getGridPanel(new JComponent[]{
            getGridPanel(new JComponent[]{new JLabel("ID:", SwingConstants.RIGHT),
                idField = new JTextField(4)//, new JPanel()
            }), //new JPanel(),
            getBorderPanel(new JComponent[]{null, new JLabel("Date of Issue:", SwingConstants.RIGHT),
                issueDateSP = new SelectedDateSpinner()})
        }));

        upperPanel.add(getBorderPanel(new JComponent[]{
            getGridPanel(new JComponent[]{
                new JLabel("   Clock #:", SwingConstants.RIGHT),
                operatorNumberField
            }),
            getBorderPanel(new JComponent[]{
                new JLabel("   Name:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(employeeInCB = new JComboBox(employeeInCbModel),
                empLookupAction = new EmployeeLookupAction(employeeInCB))
            })
        }));

        upperPanel.add(new JPanel());
        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(),
            getBorderPanel(new JComponent[]{null, new JLabel("Amount(R):", SwingConstants.RIGHT),
                amountSP = new SelectedNumberSpinner(0.0, 0.0, 1000000.0, .01)}),
            new JPanel()
        }));

        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(), loanRB = new JRadioButton("LOAN"), new JPanel()
        }));
        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(), pettyRB = new JRadioButton("PETTY"), new JPanel()
        }));
        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(), allowRB = new JRadioButton("ALLOWANCE"), new JPanel()
        }));
        ButtonGroup group = new ButtonGroup();
        group.add(loanRB);
        group.add(pettyRB);
        group.add(allowRB);

        leftPanel.add(upperPanel, BorderLayout.NORTH);

        leftPanel.add(sp = new JScrollPane(notesArea = new JTextArea(5, 15)));
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notes"));

        employeeInCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (enableNumber1Update) {
                    syncOperatorNumField();
                }
            }
        });

        operatorNumberField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                if (enableCombo1Update) {
                    enableNumber1Update = false;
                    Integer id = null;
                    for (ComboItem ci : employeesArray) {
                        if (ci.getValue().toUpperCase().startsWith(
                                operatorNumberField.getText().toUpperCase())) {
                            id = ci.getId();
                            break;
                        }
                    }
                    if (id != null) {
                        selectComboItem(employeeInCB, id);
                    }
                    enableNumber1Update = true;
                }
            }
        });
        ((JSpinner.DefaultEditor) amountSP.getEditor()).getTextField().addCaretListener(new CaretListener() {
            @Override
            public void caretUpdate(CaretEvent ce) {
                recalc();
            }
        });
        return leftPanel;
    }

    private JPanel getRightPanel() {
        receipterNumberField = new JTextField(10);
//        employeeOutCbModel = new DefaultComboBoxModel(
//                XlendWorks.loadAllEmployees(
//                DashBoard.getExchanger(), Selects.activeEmployeeCondition));
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        JPanel upperPanel = new JPanel(new BorderLayout(10, 10));
        JPanel lblPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JPanel edPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JLabel hdrLbl;
        upperPanel.add(hdrLbl = new JLabel("Petty In", SwingConstants.CENTER));
        hdrLbl.setFont(hdrLbl.getFont().deriveFont(Font.BOLD, 18));
        upperPanel.add(hdrLbl, BorderLayout.NORTH);

        upperPanel.add(lblPanel, BorderLayout.WEST);
        upperPanel.add(edPanel, BorderLayout.CENTER);
        lblPanel.add(new JLabel("Date of Receipt:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("Clock#:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("Balance:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("   Outstanding Issue Balance:", SwingConstants.RIGHT));

        edPanel.add(getBorderPanel(new JComponent[]{
            receiptDateSP = new SelectedDateSpinner(),
            new JPanel(),
            editToggleBtn = new JToggleButton("edit")//getEditToggleAction())
        }));
        edPanel.add(getBorderPanel(new JComponent[]{
            receipterNumberField,
            getBorderPanel(new JComponent[]{
                new JLabel("   Name:", SwingConstants.RIGHT),
                employeeOutCB = new JComboBox(employeeInCbModel)
//                comboPanelWithLookupBtn(employeeOutCB = new JComboBox(employeeOutCbModel),
//                empOutLookupAction = new EmployeeLookupAction(employeeOutCB))
            })
        }));
        edPanel.add(getBorderPanel(new JComponent[]{balanceSP = new SelectedNumberSpinner(.0, .0, 99999999.0, .1)}));
        edPanel.add(getBorderPanel(new JComponent[]{
            balanceIssueSP = new SelectedNumberSpinner(.0, .0, 99999999.0, .1),
            new JPanel(),
            //            getGridPanel(new JComponent[]{
            //                new JLabel("per person:", SwingConstants.RIGHT),
            //                balancePersonSP = new SelectedNumberSpinner(.0, .0, 99999999.0, .1),
            //                new JPanel()
            //            }),
            getGridPanel(new JComponent[]{
                addItmBtn = new JButton(getAddLineAction()),
                delItmBtn = new JButton(getDeleteLineAction())
            })
        }));

        receipterNumberField.setPreferredSize(receiptDateSP.getPreferredSize());

        rightPanel.add(upperPanel, BorderLayout.NORTH);
        employeeOutCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (enableNumber2Update) {
                    syncReceipterNumFld();
                }
            }
        });

        receipterNumberField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
                if (enableCombo2Update) {
                    enableNumber2Update = false;
                    Integer id = null;
                    for (ComboItem ci : employeesArray) {
                        if (ci.getValue().toUpperCase().startsWith(
                                receipterNumberField.getText().toUpperCase())) {
                            id = ci.getId();
                            break;
                        }
                    }
                    if (id != null) {
                        selectComboItem(employeeOutCB, id);
                    }
                    enableNumber2Update = true;
                }
            }
        });

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 4));
        hdrPanel.add(getBorderPanel(new JComponent[]{selectAllCB = new JCheckBox(), new JLabel(hdrs[0], SwingConstants.CENTER)}));
        hdrPanel.add(new JLabel(hdrs[1], SwingConstants.RIGHT));
        hdrPanel.add(new JLabel(hdrs[2], SwingConstants.RIGHT));
        hdrPanel.add(new JLabel(hdrs[3], SwingConstants.RIGHT));
        selectAllCB.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (PettyItemPanel p : childRows) {
                    p.mark(selectAllCB.isSelected());
                }
            }
        });
        downGridPanel.add(hdrPanel);
        rightPanel.add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(3 * d.width / 5, 300));

        rightPanel.add(getBorderPanel(new JComponent[]{
            null, null, getGridPanel(new JComponent[]{
                new JLabel("Change (R):", SwingConstants.RIGHT),
                changeSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 0.01)
            })
        }), BorderLayout.SOUTH);

        editToggleBtn.addActionListener(getEditToggleAction());
        receipterNumberField.setEnabled(false);
        employeeOutCB.setEnabled(false);
        return rightPanel;
    }

    private void enableEdit(boolean enable) {
        for (JComponent c : new JComponent[]{
            operatorNumberField,
            employeeInCB,
            amountSP, loanRB, pettyRB, allowRB,
            issueDateSP, notesArea,
            addItmBtn, delItmBtn, receiptDateSP, selectAllCB
        }) {
            c.setEnabled(enable);
        }
        for (AbstractAction a : new AbstractAction[]{
            empLookupAction
        }) {
            a.setEnabled(enable);
        }
        for (PettyItemPanel p : childRows) {
            p.setEnabled(enable);
        }
        if (getDbObject() == null) {
            for (JComponent c : new JComponent[]{
                operatorNumberField,
                employeeInCB,
                amountSP, loanRB, pettyRB, allowRB,
                issueDateSP, notesArea,}) {
                c.setEnabled(true);
            }
            for (AbstractAction a : new AbstractAction[]{
                empLookupAction //, siteLookupAction, machineLookupAction
            }) {
                a.setEnabled(true);
            }
        }
    }

    private AbstractAction getEditToggleAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JToggleButton tBtn = (JToggleButton) ae.getSource();
                enableEdit(tBtn.isSelected());
            }
        };
    }

    public static boolean haveAdminRights() {
        boolean ok = XlendWorks.isCurrentAdmin();
        if (!ok) {
            JPasswordField pwdFld = new JPasswordField();
            new GetAdminPasswordDialog(pwdFld);
            String pwd = new String(pwdFld.getPassword());
            if (pwd.length() > 0) {
                try {
                    ok = XlendWorks.checkAdminPassword(pwd);
                    if (!ok) {
                        GeneralFrame.errMessageBox("Attempt failed", "Access denied");
                    }
                } catch (RemoteException ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
        }
        return ok;
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (PettyItemPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
        recalc();
    }

    private AbstractAction getAddLineAction() {
        return new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new PettyItemPanel(null));
                redrawRows();
            }
        };
    }

    private AbstractAction getDeleteLineAction() {
        return new AbstractAction("X") {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (PettyItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (PettyItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check item to remove",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    private void syncOperatorNumField() {
        enableCombo1Update = false;
        ComboItem ci = (ComboItem) employeeInCB.getSelectedItem();
        if (ci != null) {
            int p = ci.getValue().indexOf(" ");
            operatorNumberField.setText(ci.getValue().substring(0, p));
        }
        enableCombo1Update = true;
    }

    private void syncReceipterNumFld() {
        enableCombo2Update = false;
        ComboItem ci = (ComboItem) employeeOutCB.getSelectedItem();
        if (ci != null) {
            int p = ci.getValue().indexOf(" ");
            receipterNumberField.setText(ci.getValue().substring(0, p));
        }
        enableCombo2Update = true;
    }

    private ChangeListener recalcItemsAmountListener() {

        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                recalc();
            }
        };
    }

    private void recalc() {
        if (!insideLoad) {
            Double sum = 0.0;
            for (PettyItemPanel rp : childRows) {
                if (rp.amtSP.getValue() != null) {
                    sum += (Double) rp.amtSP.getValue();
                }
            }
            Double amtValue = (Double) amountSP.getValue();
            Double changeValue = (Double) changeSP.getValue();
            java.util.Date dt = (java.util.Date) issueDateSP.getValue();
            initialBalance = XlendWorks.getBalance4newXpetty(dt) - amtValue + changeValue;
            balanceSP.setValue(initialBalance);
            balanceIssueSP.setValue((amtValue == null ? 0
                    : amtValue.doubleValue()) - sum - changeValue.doubleValue());
        }
    }

    private void ShowPersonalBalance(double balance, Integer xemployeeOutId, Date dt, String notes) {
        new PersonalPettyBalance(new Object[]{balance, xemployeeOutId, dt, notes});
    }
}
