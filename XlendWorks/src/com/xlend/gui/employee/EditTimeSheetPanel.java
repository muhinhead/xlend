package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.gui.work.OrdersGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.Xwage;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditTimeSheetPanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel employeeCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel orderCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel siteCbModel = new DefaultComboBoxModel();
    private JTextField idField;
    private JSpinner weekendSp;
    private JComboBox employeeRefBox;
    private JComboBox orderRefBox;
    private JComboBox siteRefBox;
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
    private JSpinner[] sps;
    private JTextField[] detailsFlds;
    private Xwage[] dayWages;
    private AbstractAction ordLookupAction;
    private AbstractAction employeeLookup;
    private Xemployee xemployee;

    public EditTimeSheetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        dayWages = new Xwage[7];
        sps = new JSpinner[]{
            monNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            monOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            monDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            tueNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            tueOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            tueDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            wedNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            wedOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            wedDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            thuNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            thuOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            thuDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            friNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            friOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            friDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            satNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            satOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            satDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            sunNormalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            sunOverSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5)),
            sunDoubleSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, 0.5))
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
        for (ComboItem itm : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            employeeCbModel.addElement(itm);
        }
        for (ComboItem itm : XlendWorks.loadAllOrders(DashBoard.getExchanger())) {
            orderCbModel.addElement(itm);
        }
        siteCbModel.addElement(new ComboItem(0, "-- unknown --"));
        String[] titles = new String[]{
            "ID:", "Operator:", "Week Ending:", "Order:", "Site:", "Clock Sheet:"
        };
        labels = createLabelsArray(titles);
        edits = new JComponent[]{
            idField = new JTextField(),
            employeeRefBox = new JComboBox(employeeCbModel),
            weekendSp = new SelectedDateSpinner(),
            orderRefBox = new JComboBox(orderCbModel),
            siteRefBox = new JComboBox(siteCbModel),
            clockSheetChB = new JCheckBox()
        };
        weekendSp.setEditor(new JSpinner.DateEditor(weekendSp, "dd/MM/yyyy"));
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
                editPanel.add(comboPanelWithLookupBtn(employeeRefBox, employeeLookup = employeeLookup()));
            } else if (i == 3) {
                editPanel.add(comboPanelWithLookupBtn(orderRefBox, ordLookupAction = orderLookup()));
            } else {
                editPanel.add(edits[i]);
            }
        }
        orderRefBox.addActionListener(getClientRefChangedAction());
        add(getDaysPanel(), BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        Xtimesheet ts = (Xtimesheet) getDbObject();
        if (ts != null) {
            Date dt;
            idField.setText(ts.getXtimesheetId().toString());
            selectComboItem(employeeRefBox, ts.getXemployeeId());
            selectComboItem(orderRefBox, ts.getXorderId());
            selectComboItem(siteRefBox, ts.getXsiteId());
            clockSheetChB.setSelected(ts.getClocksheet() != null && ts.getClocksheet() == 1);
            if (ts.getWeekend() != null) {
                weekendSp.setValue(new Date(ts.getWeekend().getTime()));
            }
            imageData = (byte[]) ts.getImage();
            setImage(imageData);
            try {
                DbObject[] ws = DashBoard.getExchanger().getDbObjects(Xwage.class, "xtimesheet_id=" + ts.getXtimesheetId(), "day");
                int i = 0;
                for (DbObject o : ws) {
                    Xwage w = (Xwage) o;
                    dayWages[i] = w;
                    sps[i * 3].setValue(w.getNormaltime());
                    sps[i * 3 + 1].setValue(w.getOvertime());
                    sps[i * 3 + 2].setValue(w.getDoubletime());
                    detailsFlds[i].setText(w.getStoppeddetails());
                    i++;
                }
            } catch (RemoteException ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
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

            if (weekend.getDay() != 0) {
                GeneralFrame.errMessageBox("Error:", "Date entered is not Sunday!");
            } else {
                try {
                    Integer id = ((ComboItem) orderRefBox.getSelectedItem()).getId();
                    ts.setXorderId(id != null && id > 0 ? id : null);
                    id = ((ComboItem) employeeRefBox.getSelectedItem()).getId();
                    ts.setXemployeeId(id != null && id > 0 ? id : null);
                    id = ((ComboItem) siteRefBox.getSelectedItem()).getId();
                    ts.setXsiteId(id != null && id > 0 ? id : null);
                    ts.setClocksheet(clockSheetChB.isSelected() ? 1 : 0);
                    ts.setWeekend(new java.sql.Date(((Date) weekendSp.getValue()).getTime()));
                    ts.setImage(imageData);
                    setDbObject(ts = (Xtimesheet) DashBoard.getExchanger().saveDbObject(ts));
                    for (int d = 0; d < 7; d++) {
                        if (dayWages[d] == null) {
                            Xwage wage = new Xwage(null);
                            wage.setXwageId(0);
                            wage.setNew(true);
                            wage.setXtimesheetId(ts.getXtimesheetId());
                            wage.setDay(new java.sql.Date(weekend.getTime() - (7 - d) * 1000 * 3600 * 24));
                            wage.setNormaltime((Double) sps[d * 3].getValue());
                            wage.setOvertime((Double) sps[d * 3 + 1].getValue());
                            wage.setDoubletime((Double) sps[d * 3 + 2].getValue());
                            wage.setStoppeddetails(detailsFlds[d].getText());
                            dayWages[d] = (Xwage) DashBoard.getExchanger().saveDbObject(wage);
                        } else {
                            dayWages[d].setXtimesheetId(ts.getXtimesheetId());
                            dayWages[d].setDay(new java.sql.Date(weekend.getTime() - (7 - d) * 1000 * 3600 * 24));
                            dayWages[d].setNormaltime((Double) sps[d * 3].getValue());
                            dayWages[d].setOvertime((Double) sps[d * 3 + 1].getValue());
                            dayWages[d].setDoubletime((Double) sps[d * 3 + 2].getValue());
                            dayWages[d].setStoppeddetails(detailsFlds[d].getText());
                            dayWages[d] = (Xwage) DashBoard.getExchanger().saveDbObject(dayWages[d]);
                        }
                    }
                    return true;
                } catch (Exception ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        }
        return false;
    }

    protected String getImagePanelLabel() {
        return "Scanned Timesheet";
    }

    private AbstractAction employeeLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Employee Lookup", employeeRefBox,
                            new EmployeesGrid(DashBoard.getExchanger(), Selects.SELECT_FROM_EMPLOYEE, true),
                            new String[]{"clock_num", "id_num", "first_name", "sur_name"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

    private ActionListener getClientRefChangedAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncCombos();
            }
        };
    }

    private void syncCombos() {
        ComboItem itm = (ComboItem) orderRefBox.getSelectedItem();
        siteCbModel.removeAllElements();
        for (ComboItem ci : XlendWorks.loadAllOrderSites(DashBoard.getExchanger(), itm.getId())) {
            siteCbModel.addElement(ci);
        }
        siteRefBox.setModel(siteCbModel);
    }

    public void setXemployee(Xemployee xemployee) {
        this.xemployee = xemployee;
        if (xemployee != null) {
            selectComboItem(employeeRefBox, xemployee.getXemployeeId());
            employeeRefBox.setEnabled(false);
        }
    }

    private AbstractAction orderLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Order Lookup", orderRefBox,
                            new OrdersGrid(DashBoard.getExchanger(), Selects.SELECT_ORDERS4LOOKUP, false),
                            new String[]{"companyname",
                                "ordernumber"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private Component getDaysPanel() {

        String[] days = new String[]{"", "Monday", "Tuesday", "Wednesday",
            "Thursday", "Friday", "Saturday", "Sunday"};
        String[] headers = new String[]{"Normal Time", "Overtime", "Double Time"};
        JPanel daysgridPanel = new JPanel(new BorderLayout());

        daysgridPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Days Time sheet"));
        JPanel uppanel = new JPanel(new BorderLayout());

        daysgridPanel.add(uppanel, BorderLayout.NORTH);
        JPanel leftPanel = new JPanel(new GridLayout(8, 1));

        uppanel.add(leftPanel, BorderLayout.WEST);
        JPanel centralPanel = new JPanel(new GridLayout(8, 3));
        uppanel.add(centralPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridLayout(8, 1));
        uppanel.add(rightPanel, BorderLayout.EAST);

        rightPanel.add(new JLabel("Details"));
        for (JTextField tf : detailsFlds) {
            rightPanel.add(tf);
        }

        for (String day : days) {
            leftPanel.add(new JLabel(day, SwingConstants.RIGHT));
        }

        for (int i = 0; i < 24; i++) {
            if (i < 3) {
                centralPanel.add(new JLabel(headers[i]));
            } else {
                centralPanel.add(sps[i - 3]);
            }
        }

        return daysgridPanel;
    }
}
