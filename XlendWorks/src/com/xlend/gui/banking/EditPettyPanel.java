/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.jidesoft.spinner.DateSpinner;
import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xpetty;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.Java2sAutoComboBox;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
class EditPettyPanel extends RecordEditPanel {

    private SelectedDateSpinner issueDateSP, receiptDateSP;
    private JTextField idField;
//    private JComboBox operatorNumberCB;
    private JTextField operatorNumberField;
    private JComboBox machineCB;
    private JComboBox employeeInCB;
    private JComboBox siteCB;
    private DefaultComboBoxModel employeeInCbModel, siteCbModel;
    private ComboItem[] employeesArray;
    private boolean enableCombo1Update = true;
    private boolean enableNumber1Update = true;
    private boolean enableCombo2Update = true;
    private boolean enableNumber2Update = true;
    private SelectedNumberSpinner amountSP;
    private JCheckBox loanCB;
    private JCheckBox pettyCB;
    private JCheckBox allowCB;
    private JTextField receipterNumberField;
    private JComboBox employeeOutCB;
    private DefaultComboBoxModel employeeOutCbModel;
    private SelectedNumberSpinner balanceSP;
    private SelectedNumberSpinner balanceIssueSP;

    public EditPettyPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        setLayout(new BorderLayout());
        JSplitPane splitter;
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
    }

    @Override
    public void loadData() {
        //TODO
    }

    @Override
    public boolean save() throws Exception {
        return true;
    }

    private JPanel getLeftPanel() {
        siteCbModel = new DefaultComboBoxModel(
                XlendWorks.loadActiveSites(DashBoard.getExchanger()));
        employeeInCbModel = new DefaultComboBoxModel(
                employeesArray = XlendWorks.loadAllEmployees(
                DashBoard.getExchanger(), Selects.activeEmployeeCondition));
        operatorNumberField = new JTextField();
        machineCB = new JComboBox(XlendWorks.loadAllMachines(DashBoard.getExchanger()));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JScrollPane sp;
        JPanel upperPanel = new JPanel(new GridLayout(8, 1, 10, 10));
        upperPanel.add(getGridPanel(new JComponent[]{
            getGridPanel(new JComponent[]{new JLabel("ID:", SwingConstants.RIGHT),
                idField = new JTextField(6)//, new JPanel()
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
                new EmployeeLookupAction(employeeInCB))
            })
        }));

        upperPanel.add(getGridPanel(new JComponent[]{
            getBorderPanel(new JComponent[]{
                new JLabel("  Machine/Truck/Other:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(machineCB, new MachineLookupAction(machineCB, null))
            }),
            getBorderPanel(new JComponent[]{
                new JLabel("Site:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(siteCB = new JComboBox(
                siteCbModel),
                new SiteLookupAction(siteCB))
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
            new JPanel(), loanCB = new JCheckBox("LOAN"), new JPanel()
        }));
        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(), pettyCB = new JCheckBox("PETTY"), new JPanel()
        }));
        upperPanel.add(getGridPanel(new JComponent[]{
            new JPanel(), allowCB = new JCheckBox("ALLOWANCE"), new JPanel()
        }));

        leftPanel.add(upperPanel, BorderLayout.NORTH);

        leftPanel.add(sp = new JScrollPane(new JTextArea(5, 15)));
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notes"));

        employeeInCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (enableNumber1Update) {
                    enableCombo1Update = false;
                    ComboItem ci = (ComboItem) employeeInCB.getSelectedItem();
                    if (ci != null) {
                        int p = ci.getValue().indexOf(" ");
                        operatorNumberField.setText(ci.getValue().substring(0, p));
                    }
                    enableCombo1Update = true;
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
        return leftPanel;
    }

    private JPanel getRightPanel() {
        receipterNumberField = new JTextField(10);
        employeeOutCbModel = new DefaultComboBoxModel(
                XlendWorks.loadAllEmployees(
                DashBoard.getExchanger(), Selects.activeEmployeeCondition));
        JPanel rightPanel = new JPanel(new BorderLayout(10, 10));
        JPanel upperPanel = new JPanel(new BorderLayout(10, 10));
        JPanel lblPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        JPanel edPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        upperPanel.add(lblPanel, BorderLayout.WEST);
        upperPanel.add(edPanel, BorderLayout.CENTER);
        lblPanel.add(new JLabel("Date of Receipt:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("Clock#:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("Balance:", SwingConstants.RIGHT));
        lblPanel.add(new JLabel("   Outstanding Issue Balance:", SwingConstants.RIGHT));

        edPanel.add(getBorderPanel(new JComponent[]{receiptDateSP = new SelectedDateSpinner()}));
        edPanel.add(getBorderPanel(new JComponent[]{
            receipterNumberField,
            getBorderPanel(new JComponent[]{
                new JLabel("   Name:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(employeeOutCB = new JComboBox(employeeOutCbModel),
                new EmployeeLookupAction(employeeOutCB))
            })
        }));
        edPanel.add(getBorderPanel(new JComponent[]{balanceSP = new SelectedNumberSpinner(.0, .0, 99999999.0, .1)}));
        edPanel.add(getBorderPanel(new JComponent[]{balanceIssueSP = new SelectedNumberSpinner(.0, .0, 99999999.0, .1)}));

        receipterNumberField.setPreferredSize(receiptDateSP.getPreferredSize());

        rightPanel.add(upperPanel, BorderLayout.NORTH);
                employeeOutCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (enableNumber2Update) {
                    enableCombo2Update = false;
                    ComboItem ci = (ComboItem) employeeOutCB.getSelectedItem();
                    if (ci != null) {
                        int p = ci.getValue().indexOf(" ");
                        receipterNumberField.setText(ci.getValue().substring(0, p));
                    }
                    enableCombo2Update = true;
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

        return rightPanel;
    }
}
