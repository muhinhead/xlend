package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xsitediary;
import com.xlend.orm.Xsitediaryitem;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDiaryPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private JSpinner weekEndingSP;
    private DefaultComboBoxModel managerCbModel;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox managerCB;
    private JComboBox siteCB;
    private JTextField siteForemanField;
    private JTextField siteNumberField;
    private static String[] hdrs = new String[]{
        "Date", "Truck/Machine", "Hrs.worked", "Hrs.standing", "Operator", "Comments"
    };
    private JPanel downGridPanel;
    private ArrayList<ItemPanel> childRows;
    private ArrayList<ItemPanel> toDelete;
    private JScrollPane scrollPane;
    private JCheckBox selectAllCB;
    private String[] days;
    private JLabel[] datesLbls;
    private JLabel[] dowsLbls;
    private JCheckBox markAllCB;
    private JComponent hdrPanel1;
    private JComponent hdrPanel2;

    private class ItemPanel extends JPanel {

        private boolean isNameChanged, isNumberChanged;
        private DefaultComboBoxModel machineCbModel, operatorCbModel;
        private Xsitediaryitem item;
        private JCheckBox markCB;
        private JLabel machineTypeLBL;
        private JComboBox machineCB;
        private JComboBox operatorNameCB;
        private JComboBox operatorNumberCB;
        private JTextField day1valueField;
        private JTextField day2valueField;
        private JTextField day3valueField;
        private JTextField day4valueField;
        private JTextField day5valueField;
        private JTextField day6valueField;
        private JTextField day7valueField;
        private DefaultComboBoxModel operatorNameCbModel;
        private DefaultComboBoxModel operatorNumberCbModel;

        ItemPanel(Xsitediaryitem item) {
            super(new BorderLayout());
            this.item = item;
            isNumberChanged = isNameChanged = false;
            machineTypeLBL = new JLabel("", SwingConstants.CENTER);
            machineTypeLBL.setBorder(BorderFactory.createEtchedBorder());

            machineCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                machineCbModel.addElement(ci);
            }
            markCB = new JCheckBox();

            operatorNameCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadEmployeeList(DashBoard.getExchanger(), "name")) {
                operatorNameCbModel.addElement(ci);
            }
            operatorNameCB = new WiderDropDownCombo(operatorNameCbModel);
            operatorNameCB.setPreferredSize(new Dimension(50, operatorNameCB.getPreferredSize().height));

//            operatorNumberCbModel = new DefaultComboBoxModel();
//            for (ComboItem ci : XlendWorks.loadEmployeeList(DashBoard.getExchanger(), "clock_num")) {
//                operatorNumberCbModel.addElement(ci);
//            }
            operatorNumberCB = new Java2sAutoComboBox(XlendWorks.loadEmployeeList(DashBoard.getExchanger(), "clock_num"));

            machineCB = new JComboBox(machineCbModel);

            day1valueField = new JTextField(6);
            day2valueField = new JTextField(6);
            day3valueField = new JTextField(6);
            day4valueField = new JTextField(6);
            day5valueField = new JTextField(6);
            day6valueField = new JTextField(6);
            day7valueField = new JTextField(6);

            add(markCB,BorderLayout.WEST);
            add(getGridPanel(new JComponent[]{
                        machineTypeLBL, machineCB, operatorNameCB, operatorNumberCB,
                        day1valueField, day2valueField, day3valueField, day4valueField, day5valueField, day6valueField, day7valueField
                    }));

            machineCB.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer machineID = getSelectedCbItem(machineCB);
                    machineTypeLBL.setText(XlendWorks.getMachineType1(DashBoard.getExchanger(), machineID));
                }
            });
            
            operatorNameCB.addActionListener(new AbstractAction() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!isNumberChanged) {
                        isNameChanged = true;
                        String clock_num = XlendWorks.getEmployeeClockNumOnID(DashBoard.getExchanger(), getSelectedCbItem(operatorNameCB));
                        operatorNumberCB.setSelectedItem(clock_num);
                        isNameChanged = false;
                    }
                }
            });
            final JTextComponent tc = (JTextComponent) operatorNumberCB.getEditor().getEditorComponent();
            tc.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    if (!isNameChanged) {
                        isNumberChanged = true;
                        Integer id = XlendWorks.getEmployeeOnClockNum(DashBoard.getExchanger(), tc.getText());
                        if (id != null) {
                            selectComboItem(operatorNameCB, id);
                        }
                        isNumberChanged = false;
                    }
                }
            });
            load();
        }

        private void load() {
            if (getItem() != null) {
                selectComboItem(machineCB, item.getXmachineId());
                selectComboItem(operatorNameCB, item.getOperatorId());
                day1valueField.setText(item.getDay1value());
                day2valueField.setText(item.getDay2value());
                day3valueField.setText(item.getDay3value());
                day4valueField.setText(item.getDay4value());
                day5valueField.setText(item.getDay5value());
                day6valueField.setText(item.getDay6value());
                day7valueField.setText(item.getDay7value());
            } else
                machineCB.setSelectedIndex(0);
            shiftDayLabels();
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getItem() == null) {
                item = new Xsitediaryitem(null);
                item.setXsitediaryitemId(0);
                Xsitediary xdiary = (Xsitediary) getDbObject();
                item.setXsitediaryId(xdiary.getXsitediaryId());
                isNew = true;
            }
            item.setOperatorId(getSelectedCbItem(operatorNameCB));
            item.setXmachineId(getSelectedCbItem(machineCB));
            item.setDay1value(day1valueField.getText());
            item.setDay2value(day2valueField.getText());
            item.setDay3value(day3valueField.getText());
            item.setDay4value(day4valueField.getText());
            item.setDay5value(day5valueField.getText());
            item.setDay6value(day6valueField.getText());
            item.setDay7value(day7valueField.getText());
            return saveDbRecord(getItem(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xsitediaryitem) DashBoard.getExchanger().saveDbObject(dbOb);
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
        public Xsitediaryitem getItem() {
            return item;
        }
    }

    public EditSiteDiaryPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        days = new String[]{
            "Sun", "Mon", "Tues", "Wed", "Thur", "Fri", "Sat"
        };

        String titles[] = new String[]{
            "ID:",
            "Date:",//   "Site",
            "Manager:",
            ""//Site Foreman:",
        //""//"Foreman No:"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        managerCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            managerCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{
                    dateSP = new SelectedDateSpinner(),
                    new JLabel("Week ending:", SwingConstants.RIGHT), weekEndingSP = new SelectedDateSpinner()
                }),
                new JLabel("Site:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB))
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(managerCB = new JComboBox(managerCbModel), new EmployeeLookupAction(managerCB)),
                new JLabel("Site Foreman:", SwingConstants.RIGHT), siteForemanField = new JTextField()}),
            getGridPanel(
            new JComponent[]{
                getBorderPanel(
                new JComponent[]{new JButton(getAddLineAction()), new JPanel(), new JButton(getDeleteLineAction())}),
                new JLabel("Foreman No:", SwingConstants.RIGHT), siteNumberField = new JTextField()
            })
        };

        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        weekEndingSP.setEditor(new JSpinner.DateEditor(weekEndingSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(weekEndingSP);

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));

        dowsLbls = new JLabel[7];
        for (int i = 0; i < days.length; i++) {
            dowsLbls[i] = new JLabel(days[i], SwingConstants.CENTER);
        }
        datesLbls = new JLabel[7];
        for (int i = 0; i < datesLbls.length; i++) {
            datesLbls[i] = new JLabel(".", SwingConstants.CENTER);
        }

        downGridPanel.add(
                hdrPanel1 = getBorderPanel(new JComponent[]{
                    markAllCB = new JCheckBox(),
                    getGridPanel(new JComponent[]{
                        new JPanel(), new JPanel(), new JPanel(), new JPanel(),
                        dowsLbls[0], dowsLbls[1], dowsLbls[2],
                        dowsLbls[3], dowsLbls[4], dowsLbls[5], dowsLbls[6]
                    })
                }));

        downGridPanel.add(
                hdrPanel2 = getBorderPanel(new JComponent[]{
                    new JPanel(),
                    getGridPanel(new JComponent[]{
                        new JLabel("PLT", SwingConstants.CENTER),
                        new JLabel("NR", SwingConstants.CENTER),
                        new JLabel("NAME", SwingConstants.CENTER),
                        new JLabel("NR", SwingConstants.CENTER),
                        datesLbls[0], datesLbls[1], datesLbls[2],
                        datesLbls[3], datesLbls[4], datesLbls[5], datesLbls[6]
                    })
                }));

        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Records"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(2 * d.width / 3, 400));

        weekEndingSP.getModel().addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                shiftDayLabels();
            }
        });
        Util.addFocusSelectAllAction(weekEndingSP);
        markAllCB.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    p.mark(markAllCB.isSelected());
                }
            }
        });
    }

    private void shiftDayLabels() {
        Date weekend = (Date) weekEndingSP.getValue();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekend);
        Date dates[] = new Date[7];
        dates[6] = calendar.getTime();
        int d;
        for (d = 5; d >= 0; d--) {
            calendar.add(Calendar.DATE, -1);
            dates[d] = calendar.getTime();
        }

        DateFormat df = new SimpleDateFormat("dd/MM");
        for (int i = 0; i < 7; i++) {
            d = dates[i].getDay();
            dowsLbls[i].setText(days[d]);
            datesLbls[i].setText(df.format(dates[i]));
        }
    }

    private AbstractAction getAddLineAction() {
        return new AbstractAction("Add line") {

            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new ItemPanel(null));
                redrawRows();
            }
        };
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel1);
        downGridPanel.add(hdrPanel2);
        for (ItemPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }

    private AbstractAction getDeleteLineAction() {
        return new AbstractAction("Delete line(s)") {

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
        };
    }

    @Override
    public void loadData() {
        Xsitediary xd = (Xsitediary) getDbObject();
        if (xd != null) {
            idField.setText(xd.getXsitediaryId().toString());
            if (xd.getDiarydate() != null) {
                dateSP.setValue(new java.util.Date(xd.getDiarydate().getTime()));
            }
            if (xd.getWeekend() != null) {
                weekEndingSP.setValue(new java.util.Date(xd.getWeekend().getTime()));
            }
            if (xd.getManagerId() != null) {
                selectComboItem(managerCB, xd.getManagerId());
            }
            if (xd.getSiteForeman() != null) {
                siteForemanField.setText(xd.getSiteForeman());
            }
            if (xd.getSiteNumber() != null) {
                siteNumberField.setText(xd.getSiteNumber());
            }
            if (xd.getXsiteId() != null) {
                RecordEditPanel.addSiteItem(siteCbModel, xd.getXsiteId());
                selectComboItem(siteCB, xd.getXsiteId());
            }
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xsitediaryitem.class,
                        "xsitediary_id=" + xd.getXsitediaryId(), "xsitediaryitem_id");
                for (DbObject rec : recs) {
                    childRows.add(new ItemPanel((Xsitediaryitem) rec));
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
        Xsitediary xd = (Xsitediary) getDbObject();
        boolean isNew = false;
        if (xd == null) {
            xd = new Xsitediary(null);
            xd.setXsitediaryId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xd.setDiarydate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) weekEndingSP.getValue();
        if (dt != null) {
            xd.setWeekend(new java.sql.Date(dt.getTime()));
        }
        xd.setManagerId(getSelectedCbItem(managerCB));
        xd.setSiteForeman(siteForemanField.getText());
        xd.setSiteNumber(siteNumberField.getText());
        xd.setXsiteId(getSelectedCbItem(siteCB));
        boolean ok = saveDbRecord(xd, isNew);
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
}
