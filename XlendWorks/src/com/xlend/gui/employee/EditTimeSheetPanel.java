package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.work.OrdersGrid;
import com.xlend.gui.work.SitesGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.Xwage;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditTimeSheetPanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel employeeCbModel;
    private DefaultComboBoxModel orderCbModel;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel machineCbModel;
    private JTextField idField;
    private JSpinner weekendSp;
    private JComboBox employeeRefBox;
    private JComboBox orderRefBox;
    private JComboBox siteRefBox;
    private JComboBox machineBox;
    private JCheckBox clockSheetChB;
    private JSpinner monNormalSp;
    private JSpinner tueNormalSp;
    private JSpinner wedNormalSp;
    private JSpinner thuNormalSp;
    private JSpinner friNormalSp;
    private JSpinner satNormalSp;
    private JSpinner sunNormalSp;
    private JSpinner monOverSp;
    private JSpinner tueOverSp;
    private JSpinner wedOverSp;
    private JSpinner thuOverSp;
    private JSpinner friOverSp;
    private JSpinner satOverSp;
    private JSpinner sunOverSp;
    private JSpinner monDoubleSp;
    private JSpinner tueDoubleSp;
    private JSpinner wedDoubleSp;
    private JSpinner thuDoubleSp;
    private JSpinner friDoubleSp;
    private JSpinner satDoubleSp;
    private JSpinner sunDoubleSp;
    private JTextField monDetails;
    private JTextField tueDetails;
    private JTextField wedDetails;
    private JTextField thuDetails;
    private JTextField friDetails;
    private JTextField satDetails;
    private JTextField sunDetails;
    private JSpinner monDeductSp;
    private JSpinner tueDeductSp;
    private JSpinner wedDeductSp;
    private JSpinner thuDeductSp;
    private JSpinner friDeductSp;
    private JSpinner satDeductSp;
    private JSpinner sunDeductSp;
    private JSpinner[] sps;
    private JSpinner[] tsNumSps;
    private JTextField[] detailsFlds;
    private Xwage[] dayWages;
    private AbstractAction ordLookupAction;
    private AbstractAction siteLookupAction;
    private AbstractAction employeeLookup;
    private Xemployee xemployee;
    private ChangeListener deductListener;
//    private Double[] prevDeduct;
    private JLabel[] daysLabels;
    private JSpinner monTsNum;
    private JSpinner tueTsNum;
    private JSpinner wedTsNum;
    private JSpinner thuTsNum;
    private JSpinner friTsNum;
    private JSpinner satTsNum;
    private JSpinner sunTsNum;
    private String[] days;
    private String[] headers;

    public EditTimeSheetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        days = new String[]{//"", 
            "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};
        headers = new String[]{
            "Normal Time", "Overtime", "Double Time"//, "Deduct"
        };
        daysLabels = new JLabel[7];
        dayWages = new Xwage[7];
        sps = new JSpinner[]{
            monNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            monOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            monDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            monDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            tueNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            tueOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            tueDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            tueDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            wedNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            wedOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            wedDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            wedDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            thuNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            thuOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            thuDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            thuDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            friNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            friOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            friDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            friDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            satNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            satOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            satDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            //            satDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
            sunNormalSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            sunOverSp = new SelectedNumberSpinner(0, 0, 12, 0.5),
            sunDoubleSp = new SelectedNumberSpinner(0, 0, 12, 0.5), //            sunDeductSp = new SelectedNumberSpinner(0, 0, 24, 0.5),
        };
        tsNumSps = new JSpinner[]{
            monTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            tueTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            wedTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            thuTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            friTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            satTsNum = new SelectedNumberSpinner(0, 0, 99999, 1),
            sunTsNum = new SelectedNumberSpinner(0, 0, 99999, 1)
        };
        detailsFlds = new JTextField[]{
            monDetails = new JTextField(40),
            tueDetails = new JTextField(40),
            wedDetails = new JTextField(40),
            thuDetails = new JTextField(40),
            friDetails = new JTextField(40),
            satDetails = new JTextField(40),
            sunDetails = new JTextField(40)
        };

        employeeCbModel = new DefaultComboBoxModel();
        orderCbModel = new DefaultComboBoxModel();
        siteCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllEmployees()) {
            employeeCbModel.addElement(itm);
        }
        for (ComboItem itm : XlendWorks.loadAllOrders()) {
            orderCbModel.addElement(itm);
        }
        for (ComboItem itm : XlendWorks.loadSites("1=1")) {
            siteCbModel.addElement(itm);
        }
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
        }
        String[] titles = new String[]{
            "ID:", "Operator:", "Machine:", "Week Ending:", "Site:", "Order:", "Clock Sheet:"
        };
        labels = createLabelsArray(titles);
        edits = new JComponent[]{
            idField = new JTextField(),
            employeeRefBox = new JComboBox(employeeCbModel),
            comboPanelWithLookupBtn(machineBox = new JComboBox(machineCbModel), new MachineLookupAction(machineBox, null)),
            getGridPanel(weekendSp = new SelectedDateSpinner(), 3),
            siteRefBox = new JComboBox(siteCbModel),
            orderRefBox = new JComboBox(orderCbModel),
            clockSheetChB = new JCheckBox()
        };
        weekendSp.setEditor(new JSpinner.DateEditor(weekendSp, "dd/MM/yyyy"));

        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                shiftDayLabels();
            }
        };
        weekendSp.getModel().addChangeListener(changeListener);

        Util.addFocusSelectAllAction(weekendSp);

        idField.setEditable(false);
        organizePanels(labels.length + 2, labels.length + 2);
        for (int i = 0; i < labels.length; i++) {
            lblPanel.add(labels[i]);
            if (i == 0) {
                JPanel idPanel = new JPanel(new GridLayout(1, 3, 5, 5));
                idPanel.add(edits[i]);
                for (int j = 1; j < 3; j++) {
                    idPanel.add(new JPanel());
                }
                editPanel.add(idPanel);
            } else if (i == 1) {
                editPanel.add(comboPanelWithLookupBtn(employeeRefBox,
                        employeeLookup = new EmployeeLookupAction(employeeRefBox)));
            } else if (i == 4) {
                editPanel.add(comboPanelWithLookupBtn(siteRefBox, siteLookupAction = siteLookup()));
            } else if (i == 5) {
                editPanel.add(comboPanelWithLookupBtn(orderRefBox, ordLookupAction = orderLookup()));
            } else {
                editPanel.add(edits[i]);
            }
        }
        siteRefBox.addActionListener(getSiteRefChangedaCtion());
//        deductListener = getDeductListener();
//
//        for (int i = 3; i < sps.length; i += 4) {
//            sps[i].addChangeListener(deductListener);
//        }

        add(getDaysPanel(), BorderLayout.CENTER);
    }

    private Component getDaysPanel() {
        JPanel daysgridPanel = new JPanel(new BorderLayout());

        daysgridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Daily Time sheet"));
        JPanel uppanel = new JPanel(new BorderLayout());

        daysgridPanel.add(uppanel, BorderLayout.NORTH);
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));

        uppanel.add(leftPanel, BorderLayout.WEST);
        JPanel centralPanel = new JPanel(new GridLayout(8, 3));
        uppanel.add(centralPanel, BorderLayout.CENTER);

//        JPanel rightPanel = new JPanel(new GridLayout(8, 1));
//        uppanel.add(rightPanel, BorderLayout.EAST);
//
//        rightPanel.add(new JLabel("Details"));
//        rightPanel.add(new JLabel("TimeSheet No"));
//        for (JTextField tf : detailsFlds) {
//            rightPanel.add(tf);
//            rightPanel.add(new JPanel());
//        }

        JPanel rightPanel = new JPanel(new BorderLayout());
        uppanel.add(rightPanel, BorderLayout.EAST);
        JPanel westPanel = new JPanel(new GridLayout(8, 1));
        JPanel centerPanel = new JPanel(new GridLayout(8, 1));
        rightPanel.add(westPanel, BorderLayout.WEST);
        rightPanel.add(centerPanel, BorderLayout.CENTER);
        westPanel.add(new JLabel("Details", SwingConstants.CENTER));
        centerPanel.add(new JLabel("TimeSheet No"));
        int r = 0;
        for (JTextField tf : detailsFlds) {
            westPanel.add(tf);
            centerPanel.add(tsNumSps[r++]);
        }


        leftPanel.add(new JPanel());
        for (int l = 0; l < days.length; l++) {
            leftPanel.add(daysLabels[l] = new JLabel(days[l], SwingConstants.RIGHT));
        }

        for (int i = 0; i < 24; i++) {
            if (i < 3) {
                centralPanel.add(new JLabel(headers[i], SwingConstants.CENTER));
            } else {
                centralPanel.add(sps[i - 3]);
            }
        }

        return daysgridPanel;
    }

    @Override
    public void loadData() {
        Xtimesheet ts = (Xtimesheet) getDbObject();
        if (ts != null) {
            Date dt;
            idField.setText(ts.getXtimesheetId().toString());
            selectComboItem(employeeRefBox, ts.getXemployeeId());
            selectComboItem(machineBox, ts.getXmachineId());
            selectComboItem(orderRefBox, ts.getXorderId());
            RecordEditPanel.addSiteItem(siteCbModel, ts.getXsiteId());
            selectComboItem(siteRefBox, ts.getXsiteId());
            clockSheetChB.setSelected(ts.getClocksheet() != null && ts.getClocksheet() == 1);
            if (ts.getWeekend() != null) {
                weekendSp.setValue(new Date(ts.getWeekend().getTime()));
            }
            imageData = (byte[]) ts.getImage();
            setImage(imageData);
            try {
                DbObject[] ws = XlendWorks.getExchanger().getDbObjects(Xwage.class, "xtimesheet_id=" + ts.getXtimesheetId(), "day");
                int i = 0;
                for (DbObject o : ws) {
                    Xwage w = (Xwage) o;
                    dayWages[i] = w;
                    sps[i * 3].setValue(w.getNormaltime());
                    sps[i * 3 + 1].setValue(w.getOvertime());
                    sps[i * 3 + 2].setValue(w.getDoubletime());
//                    sps[i * 4 + 3].setValue(w.getDeduction());
                    detailsFlds[i].setText(w.getStoppeddetails());
                    tsNumSps[i].setValue(w.getTsnum());
                    i++;
                }
            } catch (RemoteException ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        shiftDayLabels();
    }

    @Override
    public boolean save() throws Exception {
        Xtimesheet ts = (Xtimesheet) getDbObject();
        boolean isNew = false;
        if (ts == null) {
            ts = new Xtimesheet(null);
            ts.setXtimesheetId(0);
            isNew = true;
        }
        ComboItem itm = (ComboItem) orderRefBox.getSelectedItem();
        if (itm.getId() == 0) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    orderCbModel.addElement(ci);
                    orderRefBox.setSelectedItem(ci);
                }
            }
        } else {
            Date weekend = (Date) weekendSp.getValue();

            try {
                ts.setXorderId(getSelectedCbItem(orderRefBox));
                ts.setXemployeeId(getSelectedCbItem(employeeRefBox));
                Integer xsiteID = getSelectedCbItem(siteRefBox);
                ts.setXsiteId(xsiteID);
                if (!XlendWorks.isActiveSite(xsiteID)) {
                    if (GeneralFrame.yesNo("Attention!",
                            "Inactive site selected. Activate it?") == JOptionPane.YES_OPTION) {
                        XlendWorks.activateSite(xsiteID);
                    }
                }
                ts.setXmachineId(getSelectedCbItem(machineBox));
                ts.setClocksheet(clockSheetChB.isSelected() ? 1 : 0);
                ts.setWeekend(new java.sql.Date(((Date) weekendSp.getValue()).getTime()));
                if (isNew) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String sd = dateFormat.format(weekend);
                    int qty = XlendWorks.getExchanger().getCount("select xtimesheet_id from xtimesheet "
                            + "where xmachine_id=" + ts.getXmachineId() + " and weekend='" + sd + "'");
                    if (qty > 0) {
                        if (GeneralFrame.yesNo("Attention!",
                                "Timesheet for this machine and date already exists! "
                                + "\nDo you really want to save duplicate?") != JOptionPane.YES_OPTION) {
                            return false;
                        }
                    }
                }
                ts.setImage(imageData);
                setDbObject(ts = (Xtimesheet) XlendWorks.getExchanger().saveDbObject(ts));
                for (int d = 0; d < 7; d++) {
                    double norm, over, dbl;
                    if (dayWages[d] == null) {
                        dayWages[d] = new Xwage(null);
                        dayWages[d].setXwageId(0);
                        dayWages[d].setNew(true);
                    }
                    dayWages[d].setXtimesheetId(ts.getXtimesheetId());
                    dayWages[d].setDay(new java.sql.Date(weekend.getTime() - (7 - d) * 1000 * 3600 * 24));
                    dayWages[d].setNormaltime(norm = (Double) sps[d * 3].getValue());
                    dayWages[d].setOvertime(over = (Double) sps[d * 3 + 1].getValue());
                    dayWages[d].setDoubletime(dbl = (Double) sps[d * 3 + 2].getValue());
                    dayWages[d].setStoppeddetails(detailsFlds[d].getText());
                    dayWages[d].setTsnum((Integer) tsNumSps[d].getValue());
                    if (norm + over + dbl > 24) {
                        GeneralFrame.errMessageBox("Error:", "Total day time exceeds 24h!");
                        ((JSpinner.DefaultEditor) sps[d * 4].getEditor()).getTextField().requestFocus();
                        return false;
                    } else {
                        dayWages[d] = (Xwage) XlendWorks.getExchanger().saveDbObject(dayWages[d]);
                    }
                }
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    protected String getImagePanelLabel() {
        return "Scanned Timesheet";
    }

//    private AbstractAction employeeLookup() {
//        return new AbstractAction("...") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    LookupDialog ld = new LookupDialog("Employee Lookup", employeeRefBox,
//                            new EmployeesGrid(DashBoard.getExchanger(), Selects.SELECT_FROM_EMPLOYEE, true),
//                            new String[]{"clock_num", "id_num", "first_name", "sur_name"});
//                } catch (RemoteException ex) {
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//
//            }
//        };
//    }
    private ActionListener getSiteRefChangedaCtion() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                syncOrderComboOnSiteCombo();
            }
        };
    }

    private void syncOrderComboOnSiteCombo() {
        ComboItem itm = (ComboItem) siteRefBox.getSelectedItem();
        if (itm != null && itm.getId() > 0) {
            Integer order_id = XlendWorks.getOrderIdOnSiteId(itm.getId());
            for (int i = 0; order_id != null && i < orderRefBox.getItemCount(); i++) {
                ComboItem citm = (ComboItem) orderRefBox.getItemAt(i);
                if (citm.getId() == order_id) {
                    orderRefBox.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    public void setXemployee(Xemployee xemployee) {
        this.xemployee = xemployee;
        if (xemployee != null) {
            selectComboItem(employeeRefBox, xemployee.getXemployeeId());
            employeeRefBox.setEnabled(false);
        }
    }

    private AbstractAction siteLookup() {
        return new AbstractAction("...") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Site Lookup", siteRefBox,
                            new SitesGrid(XlendWorks.getExchanger(), Selects.SELECT_ALLSITES4LOOKUP, true),
                            new String[]{"name"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction orderLookup() {
        return new AbstractAction("...") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Order Lookup", orderRefBox,
                            new OrdersGrid(XlendWorks.getExchanger(), Selects.SELECT_ORDERS4LOOKUP, false),
                            new String[]{"companyname",
                                "ordernumber"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

//    private ChangeListener getDeductListener() {
//        return new ChangeListener() {
//            @Override
//            public void stateChanged(ChangeEvent e) {
//                JSpinner sp = (JSpinner) e.getSource();
//                int idx = getDeductSpIndex(sp);
////                double diff = (Double) sp.getValue() - prevDeduct[idx];
//                JSpinner normSp = sps[idx * 4];
//                JSpinner overSp = sps[idx * 4 + 1];
//                JSpinner dblSp = sps[idx * 4 + 2];
//
//                if ((Double) normSp.getValue() >= diff) {
//                    normSp.setValue(new Double((Double) normSp.getValue() - diff));
//                } else if ((Double) overSp.getValue() >= diff) {
//                    overSp.setValue(new Double((Double) overSp.getValue() - diff));
//                } else if ((Double) dblSp.getValue() >= diff) {
//                    dblSp.setValue(new Double((Double) dblSp.getValue() - diff));
//                } else {
//                    sp.setValue(prevDeduct[idx]);
//                }
//
//                prevDeduct[idx] = (Double) sp.getValue();
//            }
//
//            private int getDeductSpIndex(JSpinner sp) {
//                int idx = 0;
//                for (int i = 3; i < sps.length; i += 4, idx++) {
//                    if (sps[i] == sp) {
//                        return idx;
//                    }
//                }
//                return -1;
//            }
//        };
//    }
    private void shiftDayLabels() {
        Date weekend = (Date) weekendSp.getValue();
        int d = weekend.getDay();
        for (int i = 0; i < 7; i++) {
            daysLabels[i].setText(days[d++]);
            d = (d >= 7 ? 0 : d);
        }
    }
}
