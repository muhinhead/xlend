/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.TimeSheetsGrid;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xposition;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.PopupListener;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author nick
 */
class EditEmployeePanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel positionCbModel;
    private DefaultComboBoxModel wageCategoryDbModel;
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
    private JCheckBox deceasedCb, dismissedCb, abscondedCb;
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
    private JCheckBox resignedCb;
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
            "",
            "", "", "",
            "", "", ""
        };
        ComboItem[] durations = new ComboItem[]{
            new ComboItem(1, "1 month"),
            new ComboItem(2, "2 month"),
            new ComboItem(3, "3 month"),
            new ComboItem(6, "6 month"),
            new ComboItem(12, "1 year"),
            new ComboItem(0, "Permanent")
        };
        positionCbModel = new DefaultComboBoxModel();
        wageCategoryDbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllPositions(DashBoard.getExchanger())) {
            positionCbModel.addElement(itm);
        }
        for (ComboItem ci : XlendWorks.loadWageCategories(DashBoard.getExchanger())) {
            wageCategoryDbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Clock Nr:", SwingConstants.RIGHT),
                clockNumField = new JTextField()
            }, 5),
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
            getGridPanel(phone0NumField = new JTextField(), 3),
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
                rateSP = new SelectedNumberSpinner(0.0, 0.0, 100000.0, .1),
                new JLabel("Wage Category:", SwingConstants.RIGHT),
                wageCategoryCB = new JComboBox(wageCategoryDbModel)
            }, 5),
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
                deceasedCb = new JCheckBox("Deceased"),
                dismissedCb = new JCheckBox("Dismissed"),
                abscondedCb = new JCheckBox("Absconded"),
                resignedCb = new JCheckBox("Resigned")
            }),
            getGridPanel(new JComponent[]{
                deceasedDateSP = new SelectedDateSpinner(),
                dismissedDateSP = new SelectedDateSpinner(),
                abscondedDateSP = new SelectedDateSpinner(),
                resignedDateSP = new SelectedDateSpinner()
            })
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        deceasedDateSP.setVisible(false);
        dismissedDateSP.setVisible(false);
        abscondedDateSP.setVisible(false);
        resignedDateSP.setVisible(false);
        for (JSpinner sp : new JSpinner[]{contractStartSP, contractEndSP,
                    deceasedDateSP, dismissedDateSP, abscondedDateSP, resignedDateSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }

        contractLenCB.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                adjustEndDate();
            }
        });

        deceasedCb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                deceasedDateSP.setVisible(deceasedCb.isSelected());
            }
        });
        dismissedCb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                dismissedDateSP.setVisible(dismissedCb.isSelected());
            }
        });
        abscondedCb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                abscondedDateSP.setVisible(abscondedCb.isSelected());
            }
        });
        resignedCb.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                resignedDateSP.setVisible(resignedCb.isSelected());
            }
        });
        contractStartSP.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                adjustEndDate();
            }
        });
        
        add(getDetailsPanel(), BorderLayout.CENTER);
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
        JTabbedPane tp = new JTabbedPane();
        tp.setPreferredSize(new Dimension(tp.getPreferredSize().width, 200));

        try {
            Xemployee emp = (Xemployee) getDbObject();
            int employee_id = emp == null ? 0 : emp.getXemployeeId();
            TimeSheetsGrid tsSheet = new TimeSheetsGrid(DashBoard.getExchanger(),
                    Selects.SELECT_TIMESHEETS4EMPLOYEE.replace("#", "" + employee_id), false);
            tp.add(tsSheet, "Time Sheeets");
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
            if (emp.getContractStart() != null) {
                dt = new java.util.Date(emp.getContractStart().getTime());
                contractStartSP.setValue(dt);
            }
            if (emp.getContractEnd() != null) {
                dt = new java.util.Date(emp.getContractEnd().getTime());
                contractEndSP.setValue(dt);
            }
            if (emp.getDeceasedDate() != null) {
                dt = new java.util.Date(emp.getDeceasedDate().getTime());
                deceasedDateSP.setValue(dt);
            }
            if (emp.getDismissedDate() != null) {
                dt = new java.util.Date(emp.getDismissedDate().getTime());
                dismissedDateSP.setValue(dt);
            }
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
                deceasedCb.setSelected(true);
            }
            if (emp.getDismissed() != null && emp.getDismissed() == 1) {
                dismissedCb.setSelected(true);
            }
            if (emp.getAbsconded() != null && emp.getAbsconded() == 1) {
                abscondedCb.setSelected(true);
            }
            if (emp.getResigned() != null && emp.getResigned() == 1) {
                resignedCb.setSelected(true);
            }
            imageData = (byte[]) emp.getPhoto();
            setImage(imageData);
            imageData2 = (byte[]) emp.getPhoto2();
            setImage2(imageData2);
            imageData3 = (byte[]) emp.getPhoto3();
            setImage3(imageData3);
        }
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
                emp.setDeceased(deceasedCb.isSelected() ? 1 : 0);

                if (deceasedCb.isSelected() && deceasedDateSP.getValue() != null) {
                    dt = (Date) deceasedDateSP.getValue();
                    emp.setDeceasedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setDeceasedDate(null);
                }
                emp.setDismissed(dismissedCb.isSelected() ? 1 : 0);
                if (dismissedCb.isSelected() && dismissedDateSP.getValue() != null) {
                    dt = (Date) dismissedDateSP.getValue();
                    emp.setDismissedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setDismissedDate(null);
                }
                emp.setAbsconded(abscondedCb.isSelected() ? 1 : 0);
                if (abscondedCb.isSelected() && abscondedDateSP.getValue() != null) {
                    dt = (Date) abscondedDateSP.getValue();
                    emp.setAbscondedDate(new java.sql.Date(dt.getTime()));
                } else {
                    emp.setAbscondedDate(null);
                }
                emp.setResigned(resignedCb.isSelected() ? 1 : 0);
                if (resignedCb.isSelected() && resignedDateSP.getValue() != null) {
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
                setDbObject(DashBoard.getExchanger().saveDbObject(emp));
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
        JTabbedPane picsTabs = new JTabbedPane();
        picsTabs.add(super.getRightUpperPanel(), "Photo 1");
        picsTabs.add(getRightUpperPanel2(), "Photo 2");
        picsTabs.add(getRightUpperPanel3(), "Photo 3");
        return picsTabs;
    }

    private JPopupMenu getPhotoPopupMenu2() {
        if (null == picturePopMenu2) {
            picturePopMenu2 = new JPopupMenu();
            picturePopMenu2.add(new AbstractAction("Open in window") {

                public void actionPerformed(ActionEvent e) {
                    viewDocumentImage(currentPicture2);
                }
            });
            picturePopMenu2.add(new AbstractAction("Replace image") {

                public void actionPerformed(ActionEvent e) {
                    loadDocImageFromFile2();
                }
            });
            picturePopMenu2.add(new AbstractAction("Save image to file") {

                public void actionPerformed(ActionEvent e) {
                    exportDocImage(imageData2);
                }
            });
            picturePopMenu2.add(new AbstractAction("Remove image from DB") {

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
        loadButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                loadDocImageFromFile2();
            }
        });
        return loadButton;
    }

    private JButton getLoadPictureButton3() {
        JButton loadButton = new JButton("Choose picture...");
        loadButton.addActionListener(new ActionListener() {

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
        insPanel.add(getLoadPictureButton2());
        picPanel2.add(insPanel);
        picPanel2.setVisible(true);
        currentPicture2 = null;
    }

    private void noImage3() {
        imageData3 = null;
        picPanel3.setVisible(false);
        picPanel3.removeAll();
        JPanel insPanel = new JPanel();
        insPanel.add(getLoadPictureButton3());
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
        JEditorPane ed = new JEditorPane("text/html", html.toString());
        ed.setEditable(false);
        picPanel2.add(sp = new JScrollPane(ed), BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(300, 250));
        ed.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage(currentPicture2);
                }
            }
        });
        ed.addMouseListener(new PopupListener(getPhotoPopupMenu2()));
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
        JEditorPane ed = new JEditorPane("text/html", html.toString());
        ed.setEditable(false);
        picPanel3.add(sp = new JScrollPane(ed), BorderLayout.CENTER);
        sp.setPreferredSize(new Dimension(300, 250));
        ed.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    viewDocumentImage(currentPicture3);
                }
            }
        });
        ed.addMouseListener(new PopupListener(getPhotoPopupMenu3()));
        new File(tmpImgFile).deleteOnExit();
        picPanel3.setVisible(true);
    }

    private AbstractAction updateEndContract() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncEndContract();
            }
        };

    }

    private void syncEndContract() {
        ComboItem ci = (ComboItem) contractLenCB.getSelectedItem();
        if (ci.getId() > 0 && contractStartSP.getValue() != null) {
            java.util.Date dt = (java.util.Date) contractStartSP.getValue();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dt);
            calendar.add(Calendar.MONTH, ci.getId());
            Date dt2 = calendar.getTime();
            contractEndSP.setValue(dt2);
        }
    }

    private ChangeListener startContractChangeListener() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                SpinnerModel dateModel = contractStartSP.getModel();
                if (dateModel instanceof SpinnerDateModel) {
                    syncEndContract();
                }
            }
        };
    }
}
