package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.EditPanelWithPhoto;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.gui.hr.WagesGrid;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

/**
 *
 * @author Nick Mukhin
 */
public class EditTimeSheetPanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel employeeCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel clientCbModel = new DefaultComboBoxModel();
    private DefaultComboBoxModel siteCbModel = new DefaultComboBoxModel();
    private JTextField idField;
    private JSpinner weekendSp;
    private JComboBox employeeRefBox;
    private JComboBox clientRefBox;
    private JComboBox siteRefBox;
    private JCheckBox clockSheetChB;
    private AbstractAction clientLookupAction;
    private AbstractAction employeeLookup;
    private Xemployee xemployee;

    public EditTimeSheetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        employeeCbModel = new DefaultComboBoxModel();
        clientCbModel = new DefaultComboBoxModel();
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            employeeCbModel.addElement(itm);
        }
        for (ComboItem itm : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            clientCbModel.addElement(itm);
        }
        siteCbModel.addElement(new ComboItem(0, "-- unknown --"));
        String[] titles = new String[]{
            "ID:", "Operator:", "Week Ending:", "Customer:", "Site:", "Clock Sheet:"
        };
        labels = createLabelsArray(titles);
        edits = new JComponent[]{
            idField = new JTextField(),
            employeeRefBox = new JComboBox(employeeCbModel),
            weekendSp = new SelectedDateSpinner(),
            clientRefBox = new JComboBox(clientCbModel),
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
                editPanel.add(comboPanelWithLookupBtn(clientRefBox, clientLookupAction = clientRefLookup()));
            } else {
                editPanel.add(edits[i]);
            }
        }
        clientRefBox.addActionListener(getClientRefChangedAction());
//        weekendSp.setEditor(new JSpinner.DateEditor(weekendSp, "dd/MM/yyyy"));
        add(getTabbedPanel(), BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        Xtimesheet ts = (Xtimesheet) getDbObject();
        if (ts != null) {
            Date dt;
            idField.setText(ts.getXtimesheetId().toString());
            selectComboItem(employeeRefBox, ts.getXemployeeId());
            selectComboItem(clientRefBox, ts.getXclientId());
            selectComboItem(siteRefBox, ts.getXsiteId());
            clockSheetChB.setSelected(ts.getClocksheet() != null && ts.getClocksheet() == 1);
            if (ts.getWeekend() != null) {
                weekendSp.setValue(new Date(ts.getWeekend().getTime()));
            }
            imageData = (byte[]) ts.getImage();
            setImage(imageData);
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
        ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
        if (itm.getId() == 0) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    clientCbModel.addElement(ci);
                    clientRefBox.setSelectedItem(ci);
                }
            }
        } else {
            Date weekend = (Date) weekendSp.getValue();

            if (weekend.getDay() != 0) {
                GeneralFrame.errMessageBox("Error:", "Date entered is not Sunday!");
            } else {
                try {
                    Integer id = ((ComboItem) clientRefBox.getSelectedItem()).getId();
                    ts.setXclientId(id != null && id > 0 ? id : null);
                    id = ((ComboItem) employeeRefBox.getSelectedItem()).getId();
                    ts.setXemployeeId(id != null && id > 0 ? id : null);
                    id = ((ComboItem) siteRefBox.getSelectedItem()).getId();
                    ts.setXsiteId(id != null && id > 0 ? id : null);
                    ts.setClocksheet(clockSheetChB.isSelected() ? 1 : 0);
                    ts.setWeekend(new java.sql.Date(((Date) weekendSp.getValue()).getTime()));
                    ts.setImage(imageData);
                    setDbObject(DashBoard.getExchanger().saveDbObject(ts));
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

    private AbstractAction clientRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Client Lookup", clientRefBox,
                            new ClientsGrid(DashBoard.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP, false),
                            new String[]{"clientcode", "companyname"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
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

    private JComponent getTabbedPanel() {
        JTabbedPane tp = new JTabbedPane();
        WagesGrid wagesGrid = null;
        Xtimesheet ts = (Xtimesheet) getDbObject();
        int timesheet_id = ts == null ? 0 : ts.getXtimesheetId();
        try {
            wagesGrid = new WagesGrid(DashBoard.getExchanger(),
                    Selects.SELECT_WAGE4TIMESHEET.replace("#", "" + timesheet_id), false);
            tp.add(wagesGrid, "Days of week");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        Dimension pref = new Dimension(600, 200);
        wagesGrid.setPreferredSize(pref);
        return tp;
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
        ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
        siteCbModel.removeAllElements();
        for (ComboItem ci : XlendWorks.loadAllClientSites(DashBoard.getExchanger(), itm.getId())) {
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
}
