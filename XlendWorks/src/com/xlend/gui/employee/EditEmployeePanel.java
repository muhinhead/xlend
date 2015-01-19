/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.admin.XlendMasterTableView;
import com.xlend.gui.assign.EmployeeAssignmentDialog;
import com.xlend.gui.assign.EmployeeAssignmentPanel;
import com.xlend.gui.hr.PPEissueItemsGrid;
import com.xlend.gui.hr.PPEissues2employeeGrid;
import com.xlend.gui.hr.TimeSheetsGrid;
import com.xlend.gui.site.IncidentsGrid;
import com.xlend.mvc.Controller;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xposition;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
class EditEmployeePanel extends EditPanelWithPhoto {

    static boolean okReasonPressed;
    private SelectedNumberSpinner overSizeSP;
    private SelectedNumberSpinner shoesSizeSP;
    private SelectedDateSpinner medExpSP;
    private JLabel medExpStatusLBL;
    private JLabel ratingLabel;
    private JButton penaltyBtn;

    private class EditMemoDialog extends PopupDialog {

        private JButton okBtn;
        private JButton cancelBtn;

        EditMemoDialog(JTextArea tf) {
            super(null, "Reason for Dismissal", tf);
        }

        @Override
        protected void fillContent() {
            super.fillContent();
            okReasonPressed = false;
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.add(new JPanel(), BorderLayout.NORTH);
            centerPanel.add(new JPanel(), BorderLayout.WEST);
            centerPanel.add(new JPanel(), BorderLayout.EAST);
            JScrollPane sp;
            centerPanel.add(sp = new JScrollPane((JTextArea) getObject(),
                    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
            sp.setPreferredSize(new Dimension(300, 200));
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            cancelBtn = new JButton(new AbstractAction("Cancel") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    okReasonPressed = false;
                    dispose();
                }
            });
            okBtn = new JButton(new AbstractAction("Ok") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    okReasonPressed = true;
                    dispose();
                }
            });
            btnPanel.add(okBtn);
            btnPanel.add(cancelBtn);
            getContentPane().add(centerPanel, BorderLayout.CENTER);
            getContentPane().add(btnPanel, BorderLayout.SOUTH);
            getRootPane().setDefaultButton(okBtn);
        }
    }
    private static DefaultComboBoxModel positionCbModel;
    private static DefaultComboBoxModel wageCategoryDbModel;
    private static ComboItem[] durations = new ComboItem[]{
        new ComboItem(1, "1 month"),
        new ComboItem(2, "2 month"),
        new ComboItem(3, "3 month"),
        new ComboItem(6, "6 month"),
        new ComboItem(12, "1 year"),
        new ComboItem(0, "Permanent")
    };
    private JTextField idField;
    private JTextField clockNumField;
    private JTextField firstNameField;
    private JTextField surNameField;
    private JTextField nickNameField;
    private JTextField idNumField;
    private JTextField phone0NumField;
    private JTextField phone1NumField;
    private JTextField phone2NumField;
    private JTextField relation1Field;
    private JTextField relation2Field;
    private JTextField addressField;
    private JComboBox contractLenCB;
    private JSpinner contractStartSP;
    private JSpinner contractEndSP;
    private JSpinner rateSP;
    private JComboBox positionCB;
    private JCheckBox deceasedCB, dismissedCB, abscondedCB, managementCb;
    private JTextField taxnumField;
    private ImageIcon currentPicture2;
    protected JPanel picPanel2;
    protected JPopupMenu picturePopMenu2;
    protected byte[] imageData2;
    private ImageIcon currentPicture3;
    protected JPanel picPanel3;
    protected JPopupMenu picturePopMenu3;
    protected byte[] imageData3;
    private JSpinner deceasedDateSP;
    private JSpinner dismissedDateSP;
    private JSpinner abscondedDateSP;
    private JCheckBox resignedCB;
    private JSpinner resignedDateSP;
    private JComboBox wageCategoryCB;
    private JTextField bankNameTF;
    private JTextField bankAccNrTF;
    private JTextField bankBranchCodeTF;
    private JTextField bankName2TF;
    private JTextField bankAccNr2TF;
    private JTextField bankBranchCode2TF;
    private JTextField bankName3TF;
    private JTextField bankAccNr3TF;
    private JTextField bankBranchCode3TF;
    private JSpinner emplStartSP;
    private JLabel siteAssignLbl;
    private JLabel machineAssignLbl;
    private JLabel managementTeamLbl;
    private JEditorPane imagePanel2;
    private JEditorPane imagePanel3;
    private JTextArea whyTextArea;
    private JTextArea notesTextArea;
    private AbstractAction openInWindowAction2;
    private AbstractAction printImageAction2;
    private AbstractAction replaceImageAction2;
    private AbstractAction saveImageToFileAction2;
    private AbstractAction removeImageFromDbAction2;
    private AbstractAction openInWindowAction3;
    private AbstractAction printImageAction3;
    private AbstractAction replaceImageAction3;
    private AbstractAction saveImageToFileAction3;
    private AbstractAction removeImageFromDbAction3;
    private JButton assignmentsBtn;
    private AbstractAction assignmentAction;
    private AbstractAction wageCategoryCBaction;
    private AbstractAction contractLenCBAction;
    private ChangeListener deceasedCbListener;
    private ChangeListener dismissedCbListener;
    private ChangeListener abscondedCbListener;
    private ChangeListener resignedCbListener;
    private ChangeListener contractStartSPListener;
    private JButton loadButton2;
    private ActionListener loadButton2Action;
    private ActionListener loadButton3Action;
    private JButton loadButton3;
    private MouseAdapter imagePanel2MouseAdapter;
    private MouseAdapter imagePanel3MouseAdapter;
    private PopupListener imagePanel2PopupListener;
    private PopupListener imagePanel3PopupListener;
    private JButton whyBtn;

    public EditEmployeePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:", // "Clock Nr:", 
            "Name:",//"Surname:", "Nickname:"
            "SA ID or Passport.Nr", // "Tax Nr:"
            "Phone Nr:",
            "Alter.Phone 1:", // "Relation to person:"
            "Alter.Phone 2:", // "Relation to person:"
            "Home Address:",
            "Work Position:",
            "Contract Duration:", //"Contract Start:","Contract End:"
            "Rate of Pay (R/hour):",//Wage category:
            "Assigned to site:", "",
            "", "", "",
            "", "", ""
        };
        whyTextArea = new JTextArea();
//        ComboItem[] durations = new ComboItem[]{
//            new ComboItem(1, "1 month"),
//            new ComboItem(2, "2 month"),
//            new ComboItem(3, "3 month"),
//            new ComboItem(6, "6 month"),
//            new ComboItem(12, "1 year"),
//            new ComboItem(0, "Permanent")
//        };
        if (null == positionCbModel) {
            positionCbModel = new DefaultComboBoxModel();
            for (ComboItem itm : XlendWorks.loadAllPositions()) {
                positionCbModel.addElement(itm);
            }
        }
        if (null == wageCategoryDbModel) {
            wageCategoryDbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadWageCategories()) {
                wageCategoryDbModel.addElement(ci);
            }
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Clock Nr:", SwingConstants.RIGHT),
                clockNumField = new JTextField(),
                new JLabel("Performance rating:", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{
                    new JPanel(),
                    ratingLabel = new JLabel("100%", SwingConstants.CENTER),
                    penaltyBtn = new JButton(new AbstractAction("Penalty") {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Xemployee xemp = (Xemployee) getDbObject();
                            if (xemp != null) {
                                new PenaltyListDialog(null, xemp.getXemployeeId());
                                updateRating(xemp);
                            } else {
                                GeneralFrame.errMessageBox("Attention!", "Save employee record please first");
                            }
                        }
                    })
                })
            }),
            getGridPanel(new JComponent[]{
                firstNameField = new JTextField(),
                new JLabel("Surname:", SwingConstants.RIGHT),
                surNameField = new JTextField(),
                new JLabel("Nickname:", SwingConstants.RIGHT),
                nickNameField = new JTextField()
            }),
            getGridPanel(new JComponent[]{
                idNumField = new JTextField(),
                new JLabel("Tax Nr:", SwingConstants.RIGHT),
                taxnumField = new JTextField()
            }),
            //            getGridPanel(phone0NumField = new JTextField(), 3),
            getGridPanel(new JComponent[]{
                phone0NumField = new JTextField(),
                managementTeamLbl = new JLabel("Is in management team:", SwingConstants.RIGHT),
                managementCb = new JCheckBox()
            }),
            getGridPanel(new JComponent[]{
                phone1NumField = new JTextField(),
                new JLabel("Relation to person:", SwingConstants.RIGHT),
                relation1Field = new JTextField()
            }),
            getGridPanel(new JComponent[]{
                phone2NumField = new JTextField(),
                new JLabel("Relation to person:", SwingConstants.RIGHT),
                relation2Field = new JTextField()
            }),
            addressField = new JTextField(),
            positionCB = new JComboBox(positionCbModel),
            getGridPanel(new JComponent[]{
                contractLenCB = new JComboBox(durations),
                new JLabel("Start Date:", SwingConstants.RIGHT),
                contractStartSP = new SelectedDateSpinner(),
                new JLabel("End Date:", SwingConstants.RIGHT),
                contractEndSP = new SelectedDateSpinner()
            }),
            getGridPanel(new JComponent[]{
                rateField(),
                new JLabel("Wage Category:", SwingConstants.RIGHT),
                wageCategoryCB = new JComboBox(wageCategoryDbModel),
                new JLabel(" Employment Start Date:", SwingConstants.RIGHT),
                emplStartSP = new SelectedDateSpinner()
            }, 5),
            getGridPanel(new JComponent[]{
                siteAssignLbl = new JLabel(),
                getBorderPanel(new JComponent[]{new JLabel("machine:"), machineAssignLbl = new JLabel()}),
                assignmentsBtn = new JButton(assignmentAction = getAssignmentsAction("Assignments..."))
            }),
            new JLabel("Bank Accounts:", SwingConstants.CENTER),
            getGridPanel(new JComponent[]{
                new JLabel("Bank Name", SwingConstants.CENTER),
                new JLabel("Account #", SwingConstants.CENTER),
                new JLabel("Branch code/name", SwingConstants.CENTER)
            }),
            getGridPanel(new JComponent[]{
                bankNameTF = new JTextField(10),
                bankAccNrTF = new JTextField(10),
                bankBranchCodeTF = new JTextField(10)
            }),
            getGridPanel(new JComponent[]{
                bankName2TF = new JTextField(10),
                bankAccNr2TF = new JTextField(10),
                bankBranchCode2TF = new JTextField(10)
            }),
            getGridPanel(new JComponent[]{
                bankName3TF = new JTextField(10),
                bankAccNr3TF = new JTextField(10),
                bankBranchCode3TF = new JTextField(10)
            }),
            getGridPanel(new JComponent[]{
                deceasedCB = new JCheckBox("Deceased"),
                dismissedCB = new JCheckBox("Dismissed"),
                abscondedCB = new JCheckBox("Absconded"),
                resignedCB = new JCheckBox("Resigned")
            }),
            getGridPanel(new JComponent[]{
                deceasedDateSP = new SelectedDateSpinner(),
                getBorderPanel(new JComponent[]{
                    new JPanel(),
                    dismissedDateSP = new SelectedDateSpinner(),
                    whyBtn = new JButton(new AbstractAction("Why") {
                        @Override
                        public void actionPerformed(ActionEvent ae) {
                            new EditMemoDialog(whyTextArea);
                        }
                    })
                }),
                abscondedDateSP = new SelectedDateSpinner(),
                resignedDateSP = new SelectedDateSpinner()
            })
        };
        ratingLabel.setBorder(BorderFactory.createEtchedBorder());
        wageCategoryCB.addActionListener(wageCategoryCBaction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem ci = (ComboItem) wageCategoryCB.getSelectedItem();
                managementCb.setVisible(ci.getId() == 1);
                managementTeamLbl.setVisible(ci.getId() == 1);
            }
        });
        siteAssignLbl.setBorder(BorderFactory.createEtchedBorder());
        machineAssignLbl.setBorder(BorderFactory.createEtchedBorder());

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        deceasedDateSP.setVisible(false);
        dismissedDateSP.setVisible(false);
        whyBtn.setVisible(false);
        abscondedDateSP.setVisible(false);
        resignedDateSP.setVisible(false);
        for (JSpinner sp : new JSpinner[]{contractStartSP, contractEndSP,
            emplStartSP, deceasedDateSP, dismissedDateSP, abscondedDateSP,
            resignedDateSP, medExpSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }

        contractLenCB.setAction(contractLenCBAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adjustEndDate();
            }
        });

        deceasedCB.addChangeListener(deceasedCbListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                deceasedDateSP.setVisible(deceasedCB.isSelected());
            }
        });
        dismissedCB.addChangeListener(dismissedCbListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                boolean isSelected = dismissedCB.isSelected();
                dismissedDateSP.setVisible(isSelected);
                whyBtn.setVisible(isSelected);
            }
        });
        abscondedCB.addChangeListener(abscondedCbListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                abscondedDateSP.setVisible(abscondedCB.isSelected());
            }
        });
        resignedCB.addChangeListener(resignedCbListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                resignedDateSP.setVisible(resignedCB.isSelected());
            }
        });
        contractStartSP.addChangeListener(contractStartSPListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                adjustEndDate();
            }
        });

        JPanel downPanel = new JPanel(new BorderLayout());//new GridLayout(1, 2, 5, 5));
        downPanel.add(getDetailsPanel(), BorderLayout.CENTER);
        JScrollPane sp = new JScrollPane(notesTextArea = new JTextArea(),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        notesTextArea.setWrapStyleWord(true);
        notesTextArea.setLineWrap(true);
        sp.setPreferredSize(new Dimension(350, sp.getPreferredSize().height));
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notes"));
        downPanel.add(sp, BorderLayout.EAST);
        add(downPanel, BorderLayout.CENTER);
    }

    private AbstractAction getAssignmentsAction(String title) {
        return new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ok = true;
                if (getDbObject() == null) {
                    ok = false;
                    if (JOptionPane.showConfirmDialog(null, "Do you want to save employee before assign him?",
                            "Attention!", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        try {
                            ok = save();
                        } catch (Exception ex) {
                            XlendWorks.logAndShowMessage(ex);
                        }
                    }
                }
                if (ok) {
                    Xemployee emp = (Xemployee) getDbObject();
                    EmployeeAssignmentPanel.setXemployee(emp);
                    new EmployeeAssignmentDialog("Assignments of "
                            + emp.getFirstName() + " " + emp.getSurName() + " (" + emp.getClockNum() + ")", getDbObject());
                    fillAssignmentInfo();
                    EmployeeAssignmentPanel.setXemployee(null);
                }
            }
        };
    }

    private JComponent rateField() {
        rateSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, .1);
        if (XlendWorks.getCurrentUser().getSupervisor() == 1) {
            return rateSP;
        } else {
            JLabel lbl = new JLabel("*********", SwingConstants.LEFT);
            lbl.setBorder(BorderFactory.createEtchedBorder());
            return lbl;
        }
    }

    private void adjustEndDate() {
        ComboItem citm = (ComboItem) contractLenCB.getSelectedItem();
        setEndDateVisible(citm.getId() > 0);
        if (citm.getId() > 0) {
            Date startDate = (Date) contractStartSP.getValue();
            if (startDate != null) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(startDate);
                cal.add(Calendar.MONTH, citm.getId());
                contractEndSP.setValue(cal.getTime());
            }
        }
    }

    private JTabbedPane getDetailsPanel() {
        JTabbedPane tp = new MyJideTabbedPane();
        tp.setPreferredSize(new Dimension(tp.getPreferredSize().width, 200));

        try {
            Xemployee emp = (Xemployee) getDbObject();
            int employee_id = emp == null ? 0 : emp.getXemployeeId();
            TimeSheetsGrid tsSheet = new TimeSheetsGrid(XlendWorks.getExchanger(),
                    Selects.SELECT_TIMESHEETS4EMPLOYEE.replace("#", "" + employee_id), false);
            tp.add(tsSheet, "Time Sheeets");
            JPanel ppesPanel = new JPanel(new BorderLayout());

            PPEissueItemsGrid itemsGrid = new PPEissueItemsGrid(XlendWorks.getExchanger(), 0);
            Controller detailController = itemsGrid.getController();
            XlendMasterTableView masterView = new XlendMasterTableView(XlendWorks.getExchanger(), detailController, "xppeissue_id", 0);

            PPEissues2employeeGrid issuesGrid = new PPEissues2employeeGrid(XlendWorks.getExchanger(), employee_id, masterView);

            ppesPanel.add(issuesGrid, BorderLayout.WEST);
            JPanel itemShell = new JPanel(new BorderLayout());
            itemShell.add(itemsGrid, BorderLayout.WEST);
            ppesPanel.add(itemShell, BorderLayout.CENTER);

            tp.add(ppesPanel, "PPE Issued");
            IncidentsGrid incSheet = new IncidentsGrid(XlendWorks.getExchanger(), employee_id);
            tp.add(incSheet, "Incidents");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }

        return tp;
    }

    private void setEndDateVisible(boolean visible) {
        contractEndSP.setVisible(visible);
        labels[16].setVisible(visible);
    }

    @Override
    public void loadData() {
        Xemployee emp = (Xemployee) getDbObject();
        if (emp != null) {
            Date dt;
            idField.setText(emp.getXemployeeId().toString());
            clockNumField.setText(emp.getClockNum());
            firstNameField.setText(emp.getFirstName());
            surNameField.setText(emp.getSurName());
            nickNameField.setText(emp.getNickName());
            idNumField.setText(emp.getIdNum());
            nickNameField.setText(emp.getNickName());
            taxnumField.setText(emp.getTaxnum());
            phone0NumField.setText(emp.getPhone0Num());
            phone1NumField.setText(emp.getPhone1Num());
            phone2NumField.setText(emp.getPhone2Num());
            relation1Field.setText(emp.getRelation1());
            relation2Field.setText(emp.getRelation2());
            addressField.setText(emp.getAddress());
            bankNameTF.setText(emp.getBankName());
            bankAccNrTF.setText(emp.getBankAccount());
            bankBranchCodeTF.setText(emp.getBranchCodeName());
            bankName2TF.setText(emp.getBankName2());
            bankAccNr2TF.setText(emp.getBankAccount2());
            bankBranchCode2TF.setText(emp.getBranchCodeName2());
            bankName3TF.setText(emp.getBankName3());
            bankAccNr3TF.setText(emp.getBankAccount3());
            bankBranchCode3TF.setText(emp.getBranchCodeName3());
            selectComboItem(positionCB, emp.getXpositionId());
            selectComboItem(contractLenCB, emp.getContractLen());
            selectComboItem(wageCategoryCB, emp.getWageCategory());
            if (emp.getOverallSize() != null) {
                overSizeSP.setValue(emp.getOverallSize());
            }
            if (emp.getShoeSize() != null) {
                shoesSizeSP.setValue(emp.getShoeSize());
            }
            if (emp.getMedicalExpires() != null) {
                dt = new java.util.Date(emp.getMedicalExpires().getTime());
                medExpSP.setValue(dt);
            }
            if (emp.getContractStart() != null) {
                dt = new java.util.Date(emp.getContractStart().getTime());
                contractStartSP.setValue(dt);
            }
            if (emp.getContractEnd() != null) {
                dt = new java.util.Date(emp.getContractEnd().getTime());
                contractEndSP.setValue(dt);
            }
            if (emp.getEmploymentStart() != null) {
                dt = new java.util.Date(emp.getEmploymentStart().getTime());
                emplStartSP.setValue(dt);
            }
            if (emp.getDeceasedDate() != null) {
                dt = new java.util.Date(emp.getDeceasedDate().getTime());
                deceasedDateSP.setValue(dt);
            }
            if (emp.getDismissedDate() != null) {
                dt = new java.util.Date(emp.getDismissedDate().getTime());
                dismissedDateSP.setValue(dt);
            }
            whyTextArea.setText(emp.getWhyDismissed());
            if (emp.getAbscondedDate() != null) {
                dt = new java.util.Date(emp.getAbscondedDate().getTime());
                abscondedDateSP.setValue(dt);
            }
            if (emp.getResignedDate() != null) {
                dt = new java.util.Date(emp.getResignedDate().getTime());
                resignedDateSP.setValue(dt);
            }
            if (emp.getRate() != null) {
                rateSP.setValue(emp.getRate());
            }
            if (emp.getContractLen() != null) {
                setEndDateVisible(emp.getContractLen() > 0);
            }
            if (emp.getDeceased() != null && emp.getDeceased() == 1) {
                deceasedCB.setSelected(true);
            }
            if (emp.getDismissed() != null && emp.getDismissed() == 1) {
                dismissedCB.setSelected(true);
            }
            if (emp.getAbsconded() != null && emp.getAbsconded() == 1) {
                abscondedCB.setSelected(true);
            }
            if (emp.getResigned() != null && emp.getResigned() == 1) {
                resignedCB.setSelected(true);
            }
            imageData = (byte[]) emp.getPhoto();
            setImage(imageData);
            imageData2 = (byte[]) emp.getPhoto2();
            setImage2(imageData2);
            imageData3 = (byte[]) emp.getPhoto3();
            setImage3(imageData3);
            if (emp.getClockNum().equals("000") && emp.getFirstName().equals("NO OPERATOR")) {
                clockNumField.setEnabled(false);
                firstNameField.setEnabled(false);
                surNameField.setEnabled(false);
            }
            managementCb.setSelected(emp.getManagement() != null && emp.getManagement() == 1);
            notesTextArea.setText(emp.getNotes());
            updateRating(emp);
            fillAssignmentInfo();
        }
    }

    private void updateRating(Xemployee emp) {
        String srating = XlendWorks.getThisYearRating(emp.getXemployeeId());
        ratingLabel.setText(srating + "%");
    }

    @Override
    public boolean save() throws Exception {
        Xemployee emp = (Xemployee) getDbObject();
        boolean isNew = false;
        if (emp == null) {
            emp = new Xemployee(null);
            emp.setXemployeeId(0);
            isNew = true;
        }
        ComboItem itm = (ComboItem) positionCB.getSelectedItem();
        if (itm.getId() == 0) { // add new position
            EditPositionDialog ep = new EditPositionDialog("New Position", null);
            if (EditPositionDialog.okPressed) {
                Xposition pos = (Xposition) ep.getEditPanel().getDbObject();
                if (pos != null) {
                    itm = new ComboItem(pos.getXpositionId(), pos.getPos());
                    positionCbModel.addElement(itm);
                    positionCB.setSelectedItem(itm);
                }
            }
        } else {
            try {
                java.util.Date dt;
                emp.setXpositionId(itm.getId());
                emp.setNew(isNew);
                emp.setAddress(addressField.getText());
                emp.setClockNum(clockNumField.getText());
                emp.setContractLen(((ComboItem) contractLenCB.getSelectedItem()).getId());
                emp.setWageCategory(((ComboItem) wageCategoryCB.getSelectedItem()).getId());
                if (emp.getContractLen() > 0) {
                    emp.setContractEnd(new java.sql.Date(((java.util.Date) contractEndSP.getValue()).getTime()));
                } else {
                    emp.setContractEnd(null);
                }
                if (contractStartSP.getValue() != null) {
                    emp.setContractStart(new java.sql.Date(((java.util.Date) contractStartSP.getValue()).getTime()));
                } else {
                    emp.setContractStart(null);
                }
                if (emplStartSP.getValue() != null) {
                    emp.setEmploymentStart(new java.sql.Date(((java.util.Date) emplStartSP.getValue()).getTime()));
                } else {
                    emp.setEmploymentStart(null);
                }
                emp.setFirstName(firstNameField.getText());
                emp.setIdNum(idNumField.getText());
                emp.setNickName(nickNameField.getText());
                emp.setTaxnum(taxnumField.getText());
                emp.setSurName(surNameField.getText());
                emp.setPhone0Num(phone0NumField.getText());
                emp.setPhone1Num(phone1NumField.getText());
                emp.setPhone2Num(phone2NumField.getText());
                emp.setRate((Double) rateSP.getValue());
                emp.setRelation1(relation1Field.getText());
                emp.setRelation2(relation2Field.getText());
                itm = (ComboItem) positionCB.getSelectedItem();
                if (itm.getId() > 0) {
                    emp.setXpositionId(itm.getId());
                } else {
                    emp.setXpositionId(null);
                }
                emp.setDeceased(deceasedCB.isSelected() ? 1 : 0);

                if (deceasedCB.isSelected() && deceasedDateSP.getValue() != null) {
                    dt = (Date) deceasedDateSP.getValue();
                    emp.setDeceasedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setDeceasedDate(null);
                }
                if (okReasonPressed) {
                    emp.setWhyDismissed(whyTextArea.getText());
                }
                emp.setDismissed(dismissedCB.isSelected() ? 1 : 0);
                if (dismissedCB.isSelected() && dismissedDateSP.getValue() != null) {
                    dt = (Date) dismissedDateSP.getValue();
                    emp.setDismissedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setDismissedDate(null);
                }
                emp.setAbsconded(abscondedCB.isSelected() ? 1 : 0);
                if (abscondedCB.isSelected() && abscondedDateSP.getValue() != null) {
                    dt = (Date) abscondedDateSP.getValue();
                    emp.setAbscondedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setAbscondedDate(null);
                }
                emp.setResigned(resignedCB.isSelected() ? 1 : 0);
                if (resignedCB.isSelected() && resignedDateSP.getValue() != null) {
                    dt = (Date) resignedDateSP.getValue();
                    emp.setResignedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setResignedDate(null);
                }
                emp.setBankName(bankNameTF.getText());
                emp.setBankAccount(bankAccNrTF.getText());
                emp.setBranchCodeName(bankBranchCodeTF.getText());
                emp.setBankName2(bankName2TF.getText());
                emp.setBankAccount2(bankAccNr2TF.getText());
                emp.setBranchCodeName2(bankBranchCode2TF.getText());
                emp.setBankName3(bankName3TF.getText());
                emp.setBankAccount3(bankAccNr3TF.getText());
                emp.setBranchCodeName3(bankBranchCode3TF.getText());

                emp.setPhoto(imageData);
                emp.setPhoto2(imageData2);
                emp.setPhoto3(imageData3);
                emp.setManagement(managementCb.isSelected() ? 1 : 0);
                emp.setNotes(notesTextArea.getText());
                dt = (Date) medExpSP.getValue();
                emp.setMedicalExpires(new java.sql.Date(dt.getTime()));
                emp.setOverallSize((Integer) overSizeSP.getValue());
                emp.setShoeSize((Integer) shoesSizeSP.getValue());
                setDbObject(XlendWorks.getExchanger().saveDbObject(emp));
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    @Override
    protected String getImagePanelLabel() {
        return null;
    }

    private JComponent getRightUpperPanel2() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        if (picPanel2 == null) {
            rightPanel.add(getPicPanel2(), BorderLayout.CENTER);
            picPanel2.setPreferredSize(new Dimension(500, picPanel2.getPreferredSize().height));
        }
        return rightPanel;
    }

    private JComponent getRightUpperPanel3() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        if (picPanel3 == null) {
            rightPanel.add(getPicPanel3(), BorderLayout.CENTER);
            picPanel3.setPreferredSize(new Dimension(500, picPanel3.getPreferredSize().height));
        }
        return rightPanel;
    }

    @Override
    protected JComponent getRightUpperPanel() {
        JPanel rightUpperPanel = new JPanel(new BorderLayout());
        JTabbedPane picsTabs = new MyJideTabbedPane();
        picsTabs.add(super.getRightUpperPanel(), "Photo 1");
        picsTabs.add(getRightUpperPanel2(), "Photo 2");
        picsTabs.add(getRightUpperPanel3(), "Photo 3");
        //return picsTabs;
        rightUpperPanel.add(picsTabs, BorderLayout.CENTER);
        JPanel downPanel = new JPanel(new BorderLayout());
        JPanel leftLabelPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        leftLabelPanel.add(new JLabel("Overall Size:", SwingConstants.RIGHT));
        leftLabelPanel.add(new JLabel("Shoe Size:", SwingConstants.RIGHT));
        downPanel.add(leftLabelPanel, BorderLayout.WEST);
        JPanel centerFldPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        centerFldPanel.add(overSizeSP = new SelectedNumberSpinner(10, 10, 200, 1));
        centerFldPanel.add(new JLabel("Medical Expires On:"));
        centerFldPanel.add(shoesSizeSP = new SelectedNumberSpinner(1, 1, 12, 1));
        centerFldPanel.add(getBorderPanel(new JComponent[]{medExpSP = new SelectedDateSpinner()}));
        downPanel.add(centerFldPanel, BorderLayout.CENTER);
        JPanel rightPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        rightPanel.add(new JLabel("Med.Exp.Status:"));
        rightPanel.add(medExpStatusLBL = new JLabel(""));
        medExpStatusLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        downPanel.add(rightPanel, BorderLayout.EAST);
        rightUpperPanel.add(downPanel, BorderLayout.SOUTH);

        medExpSP.addChangeListener(expDateSPchangeListener());

        return rightUpperPanel;
    }

    protected ChangeListener expDateSPchangeListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                repaintLicFields();
            }
        };
    }

    protected void repaintLicFields() {
        Date expDt = (Date) medExpSP.getValue();
        Date today = Calendar.getInstance().getTime();
        long diff = (expDt.getTime() - today.getTime()) / (1000 * 3600 * 24);
        medExpStatusLBL.setForeground(Color.black);
        medExpStatusLBL.setBackground(Color.white);
        if (expDt.before(today)) {
            medExpStatusLBL.setForeground(Color.red);
            medExpStatusLBL.setText("Expired");
        } else if (diff <= 60) {
            medExpStatusLBL.setForeground(Color.blue);
            medExpStatusLBL.setText("2 Month Warning");
        } else {
            medExpStatusLBL.setText("Current");
        }
    }

    private JPopupMenu getPhotoPopupMenu2() {
        if (null == picturePopMenu2) {
            picturePopMenu2 = new JPopupMenu();
            picturePopMenu2.add(openInWindowAction2 = new AbstractAction("Open in window") {
                public void actionPerformed(ActionEvent e) {
                    viewDocumentImage(currentPicture2);
                }
            });
            picturePopMenu2.add(printImageAction2 = new AbstractAction("Print image") {
                public void actionPerformed(ActionEvent e) {
//                    if (imagePanel2 != null) {
//                        try {
//                            imagePanel2.print();
//                        } catch (PrinterException ex) {
//                            XlendWorks.logAndShowMessage(ex);
//                        }
//                    }
                    new PrintUtilities(imagePanel2).print();
                }
            });
            picturePopMenu2.add(replaceImageAction2 = new AbstractAction("Replace image") {
                public void actionPerformed(ActionEvent e) {
                    loadDocImageFromFile2();
                }
            });
            picturePopMenu2.add(saveImageToFileAction2 = new AbstractAction("Save image to file") {
                public void actionPerformed(ActionEvent e) {
                    exportDocImage(imageData2);
                }
            });
            picturePopMenu2.add(removeImageFromDbAction2 = new AbstractAction("Remove image from DB") {
                public void actionPerformed(ActionEvent e) {
                    noImage2();
                }
            });
        }
        return picturePopMenu2;
    }

    private JPopupMenu getPhotoPopupMenu3() {
        if (null == picturePopMenu3) {
            picturePopMenu3 = new JPopupMenu();
            picturePopMenu3.add(new AbstractAction("Open in window") {
                public void actionPerformed(ActionEvent e) {
                    viewDocumentImage(currentPicture3);
                }
            });
            picturePopMenu3.add(new AbstractAction("Print image") {
                public void actionPerformed(ActionEvent e) {
//                    if (imagePanel3 != null) {
//                        try {
//                            imagePanel3.print();
//                        } catch (PrinterException ex) {
//                            XlendWorks.logAndShowMessage(ex);
//                        }
//                    }
                    new PrintUtilities(imagePanel3).print();
                }
            });
            picturePopMenu3.add(new AbstractAction("Replace image") {
                public void actionPerformed(ActionEvent e) {
                    loadDocImageFromFile3();
                }
            });
            picturePopMenu3.add(new AbstractAction("Save image to file") {
                public void actionPerformed(ActionEvent e) {
                    exportDocImage(imageData3);
                }
            });
            picturePopMenu3.add(new AbstractAction("Remove image from DB") {
                public void actionPerformed(ActionEvent e) {
                    noImage3();
                }
            });
        }
        return picturePopMenu3;
    }

    public JPanel getPicPanel2() {
        if (picPanel2 == null) {
            picPanel2 = new JPanel(new BorderLayout());
            noImage2();
        }
        return picPanel2;
    }

    public JPanel getPicPanel3() {
        if (picPanel3 == null) {
            picPanel3 = new JPanel(new BorderLayout());
            noImage3();
        }
        return picPanel3;
    }

    private JButton getLoadPictureButton2() {
        JButton loadButton = new JButton("Choose picture...");
        loadButton.addActionListener(loadButton2Action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDocImageFromFile2();
            }
        });
        return loadButton;
    }

    private JButton getLoadPictureButton3() {
        JButton loadButton = new JButton("Choose picture...");
        loadButton.addActionListener(loadButton3Action = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadDocImageFromFile3();
            }
        });
        return loadButton;
    }

    private void loadDocImageFromFile2() {
        JFileChooser chooser = new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setFileFilter(new PagesPanel.PagesDocFileFilter());
        chooser.setDialogTitle("Import File");
        chooser.setApproveButtonText("Import");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            setImage2(Util.readFile(f.getAbsolutePath()));
        }
    }

    private void loadDocImageFromFile3() {
        JFileChooser chooser = new JFileChooser(DashBoard.readProperty("imagedir", "./"));
        chooser.setFileFilter(new PagesPanel.PagesDocFileFilter());
        chooser.setDialogTitle("Import File");
        chooser.setApproveButtonText("Import");
        int retVal = chooser.showOpenDialog(null);
        if (retVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            setImage3(Util.readFile(f.getAbsolutePath()));
        }
    }

    private void noImage2() {
        imageData2 = null;
        picPanel2.setVisible(false);
        picPanel2.removeAll();
        JPanel insPanel = new JPanel();
        insPanel.add(loadButton2 = getLoadPictureButton2());
        picPanel2.add(insPanel);
        picPanel2.setVisible(true);
        currentPicture2 = null;
    }

    private void noImage3() {
        imageData3 = null;
        picPanel3.setVisible(false);
        picPanel3.removeAll();
        JPanel insPanel = new JPanel();
        insPanel.add(loadButton3 = getLoadPictureButton3());
        picPanel3.add(insPanel);
        picPanel3.setVisible(true);
        currentPicture3 = null;
    }

    protected void setImage2(byte[] imageData) {
        this.imageData2 = imageData;
        if (imageData2 != null) {
            setPhoto2();
        }
    }

    protected void setImage3(byte[] imageData) {
        this.imageData3 = imageData;
        if (imageData3 != null) {
            setPhoto3();
        }
    }

    private void setPhoto2() {
        picPanel2.setVisible(false);
        picPanel2.removeAll();
        String tmpImgFile = "$$$2.img";
        currentPicture2 = new ImageIcon(imageData2);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = new Dimension(screenSize.width / 3, screenSize.height / 3);
        JScrollPane sp = null;
        int height = 1;
        int wscale = 1;
        int hscale = 1;
        int width = 0;
        Util.writeFile(new File(tmpImgFile), imageData2);
        width = currentPicture2.getImage().getWidth(null);
        height = currentPicture2.getImage().getHeight(null);
        wscale = width / (d.width - 70);
        hscale = height / (d.height - 70);
        wscale = wscale <= 0 ? 1 : wscale;
        hscale = hscale <= 0 ? 1 : hscale;
        int scale = wscale < hscale ? wscale : hscale;
        StringBuffer html = new StringBuffer("<html>");
        html.append("<img margin=20 src='file:" + tmpImgFile + "' " + "width="
                + width / scale + " height=" + height / scale + "></img>");
        imagePanel2 = new JEditorPane("text/html", html.toString());
        imagePanel2.setEditable(false);
        picPanel2.add(sp = new JScrollPane(imagePanel2), BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(300, 250));
        imagePanel2.addMouseListener(imagePanel2MouseAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage(currentPicture2);
                }
            }
        });
        imagePanel2.addMouseListener(imagePanel2PopupListener = new PopupListener(getPhotoPopupMenu2()));
        new File(tmpImgFile).deleteOnExit();
        picPanel2.setVisible(true);
    }

    private void setPhoto3() {
        picPanel3.setVisible(false);
        picPanel3.removeAll();
        String tmpImgFile = "$$$3.img";
        currentPicture3 = new ImageIcon(imageData3);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension d = new Dimension(screenSize.width / 3, screenSize.height / 3);
        JScrollPane sp = null;
        int height = 1;
        int wscale = 1;
        int hscale = 1;
        int width = 0;
        Util.writeFile(new File(tmpImgFile), imageData3);
        width = currentPicture3.getImage().getWidth(null);
        height = currentPicture3.getImage().getHeight(null);
        wscale = width / (d.width - 70);
        hscale = height / (d.height - 70);
        wscale = wscale <= 0 ? 1 : wscale;
        hscale = hscale <= 0 ? 1 : hscale;
        int scale = wscale < hscale ? wscale : hscale;
        StringBuffer html = new StringBuffer("<html>");
        html.append("<img margin=20 src='file:" + tmpImgFile + "' " + "width="
                + width / scale + " height=" + height / scale + "></img>");
        imagePanel3 = new JEditorPane("text/html", html.toString());
        imagePanel3.setEditable(false);
        picPanel3.add(sp = new JScrollPane(imagePanel3), BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(300, 250));
        imagePanel3.addMouseListener(imagePanel3MouseAdapter = new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage(currentPicture3);
                }
            }
        });
        imagePanel3.addMouseListener(imagePanel3PopupListener = new PopupListener(getPhotoPopupMenu3()));
        new File(tmpImgFile).deleteOnExit();
        picPanel3.setVisible(true);
    }

//    private AbstractAction updateEndContract() {
//        return new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                syncEndContract();
//            }
//        };
//
//    }
//    private void syncEndContract() {
//        ComboItem ci = (ComboItem) contractLenCB.getSelectedItem();
//        if (ci.getId() > 0 && contractStartSP.getValue() != null) {
//            java.util.Date dt = (java.util.Date) contractStartSP.getValue();
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(dt);
//            calendar.add(Calendar.MONTH, ci.getId());
//            Date dt2 = calendar.getTime();
//            contractEndSP.setValue(dt2);
//        }
//    }
//    private ChangeListener startContractChangeListener() {
//        return new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                SpinnerModel dateModel = contractStartSP.getModel();
//                if (dateModel instanceof SpinnerDateModel) {
//                    syncEndContract();
//                }
//            }
//        };
//    }
    private void fillAssignmentInfo() {
        String[] lbls = XlendWorks.findCurrentAssignment((Xemployee) getDbObject());
        if (lbls != null && lbls.length > 1) {
            siteAssignLbl.setText(lbls[0]);
            machineAssignLbl.setText(lbls[1]);
        } else {
            siteAssignLbl.setText("unassigned yet");
            machineAssignLbl.setText("unassigned yet");
        }
    }

    @Override
    public void freeResources() {
        //TODO
        if (picturePopMenu2 != null) {
            picturePopMenu2.removeAll();
        }
        openInWindowAction2 = null;
        printImageAction2 = null;
        replaceImageAction2 = null;
        saveImageToFileAction2 = null;
        removeImageFromDbAction2 = null;
        if (picturePopMenu3 != null) {
            picturePopMenu3.removeAll();
        }
        openInWindowAction3 = null;
        printImageAction3 = null;
        replaceImageAction3 = null;
        saveImageToFileAction3 = null;
        removeImageFromDbAction3 = null;

        assignmentsBtn.removeActionListener(assignmentAction);
        assignmentAction = null;
        wageCategoryCB.removeActionListener(wageCategoryCBaction);
        wageCategoryCBaction = null;
        contractLenCB.removeActionListener(contractLenCBAction);
        contractLenCBAction = null;
        deceasedCB.removeChangeListener(deceasedCbListener);
        deceasedCbListener = null;
        dismissedCB.removeChangeListener(dismissedCbListener);
        dismissedCbListener = null;
        abscondedCB.removeChangeListener(abscondedCbListener);
        abscondedCbListener = null;
        resignedCB.removeChangeListener(resignedCbListener);
        resignedCbListener = null;
        contractStartSP.removeChangeListener(contractStartSPListener);
        contractStartSPListener = null;
        loadButton2.removeActionListener(loadButton2Action);
        loadButton2Action = null;
        loadButton3.removeActionListener(loadButton3Action);
        loadButton3Action = null;
        if (imagePanel2 != null) {
            imagePanel2.removeMouseListener(imagePanel2MouseAdapter);
            imagePanel2.removeMouseListener(imagePanel2PopupListener);
            imagePanel2MouseAdapter = null;
            imagePanel2PopupListener = null;
        }
        if (imagePanel3 != null) {
            imagePanel3.removeMouseListener(imagePanel3MouseAdapter);
            imagePanel3.removeMouseListener(imagePanel3PopupListener);
            imagePanel3MouseAdapter = null;
            imagePanel3PopupListener = null;
        }
    }
}
