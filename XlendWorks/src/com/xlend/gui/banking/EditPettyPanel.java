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
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xpetty;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.Java2sAutoComboBox;
import com.xlend.util.SelectedDateSpinner;
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

    private SelectedDateSpinner issueDateSP;
    private JTextField idField;
//    private JComboBox operatorNumberCB;
    private JTextField operatorNumberField;
    private JComboBox machineCB;
    private JComboBox employeeInCB;
    private DefaultComboBoxModel employeeInCbModel;
    private ComboItem[] employeesArray;

    public EditPettyPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        setLayout(new BorderLayout());
        JSplitPane splitter;
        add(splitter = new JSplitPane());
        splitter.setTopComponent(getLeftPanel());

        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        idField.setEnabled(false);
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
        employeeInCbModel = new DefaultComboBoxModel(
                employeesArray = XlendWorks.loadAllEmployees(
                DashBoard.getExchanger(), Selects.activeEmployeeCondition));
//        operatorNumberCB = new Java2sAutoComboBox(XlendWorks.loadEmployeeList(DashBoard.getExchanger(), "clock_num"));
        operatorNumberField = new JTextField();
        machineCB = new JComboBox(XlendWorks.loadAllMachines(DashBoard.getExchanger()));

        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        JScrollPane sp;
        JPanel upperPanel = new JPanel(new GridLayout(10, 1, 10, 10));
        upperPanel.add(getGridPanel(new JComponent[]{
            getGridPanel(new JComponent[]{new JLabel("ID:", SwingConstants.RIGHT),
                idField = new JTextField(6)//, new JPanel()
            }), new JPanel(),
            getBorderPanel(new JComponent[]{null, new JLabel("Date of Issue:", SwingConstants.RIGHT),
                issueDateSP = new SelectedDateSpinner()})
        }));
        upperPanel.add(getBorderPanel(new JComponent[]{
            getGridPanel(new JComponent[]{
                new JLabel("Clock #:", SwingConstants.RIGHT),
                operatorNumberField
//                operatorNumberCB
            }),
            getBorderPanel(new JComponent[]{
                new JLabel("Name:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(employeeInCB = new JComboBox(employeeInCbModel),
                new EmployeeLookupAction(employeeInCB))
            })
        }));
        leftPanel.add(upperPanel, BorderLayout.NORTH);
        leftPanel.add(sp = new JScrollPane(new JTextArea(5, 15)));
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notes"));

//        final JTextComponent tc = (JTextComponent) operatorNumberCB.getEditor().getEditorComponent();
//        tc.
        operatorNumberField.addCaretListener(new CaretListener() {
            public void caretUpdate(CaretEvent e) {
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
            }
        });
//                .addKeyListener(new KeyAdapter() {
//            public void keyPressed(KeyEvent e) {
//                java.awt.EventQueue.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        Integer id = null;
//                        for (ComboItem ci : employeesArray) {
//                            if (ci.getValue().toUpperCase().startsWith(
//                                    operatorNumberField.getText().toUpperCase())) {
//                                id = ci.getId();
//                                break;
//                            }
//                        }
//                        if (id != null) {
//                            selectComboItem(employeeInCB, id);
//                        }
//                    }
//                });
//            }
//        });

        return leftPanel;
    }
}
