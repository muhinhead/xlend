package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.MyJideTabbedPane;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xlowbed;
import com.xlend.orm.Xmachine;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.rmi.RemoteException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditLowBedPanel extends RecordEditPanel {

    private DefaultComboBoxModel machineCbModel;
    private final String ADD_NEW_TRACK = "-- Add new track --";
    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel assistantCbModel;
    private JTextField idField;
    private JComboBox driverCB;
    private JComboBox assistantCB;
    private JComboBox machineCB;

    public EditLowBedPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Machine:",
            "Driver:",
            "Assistant:"
        };
        machineCbModel = new DefaultComboBoxModel();
        machineCbModel.addElement(new ComboItem(0, ADD_NEW_TRACK));
        for (ComboItem ci : XlendWorks.loadAllTracks()) {
            machineCbModel.addElement(ci);
        }
        driverCbModel = new DefaultComboBoxModel();
        assistantCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadAllEmployees()) {
            driverCbModel.addElement(itm);
            assistantCbModel.addElement(itm);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel),
            new MachineLookupAction(machineCB, null)), 4),
            getGridPanel(comboPanelWithLookupBtn(driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(assistantCB = new JComboBox(assistantCbModel), new EmployeeLookupAction(assistantCB)), 2)
        };

        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        add(getTabbedPanel(), BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
//        throw new UnsupportedOperationException("Not supported yet.");
        Xlowbed xlb = (Xlowbed) getDbObject();
        if (xlb != null) {
            idField.setText(xlb.getXlowbedId().toString());
            if (xlb.getXmachineId() != null) {
                selectComboItem(machineCB, xlb.getXmachineId());
            }
            if (xlb.getDriverId() != null) {
                selectComboItem(driverCB, xlb.getDriverId());
            }
            if (xlb.getAssistantId() != null) {
                selectComboItem(assistantCB, xlb.getAssistantId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xlowbed xlb = (Xlowbed) getDbObject();
        if (xlb == null) {
            xlb = new Xlowbed(null);
            xlb.setXlowbedId(0);
            isNew = true;
        }
        ComboItem ci = (ComboItem) machineCB.getSelectedItem();
        if (ci.getValue().equals(ADD_NEW_TRACK)) {
            EditTrackDialog etd = new EditTrackDialog("New Track", null);
            if (etd.okPressed) {
                Xmachine xtrack = (Xmachine) etd.getEditPanel().getDbObject();
                if (xtrack != null) {
                    ci = new ComboItem(xtrack.getXmachineId(), xtrack.getTmvnr() + xtrack.getRegNr());
                    machineCbModel.addElement(ci);
                    machineCbModel.setSelectedItem(ci);
                }
            }
        } else {
            xlb.setXmachineId(ci.getId());
            xlb.setDriverId(getSelectedCbItem(driverCB));
            xlb.setAssistantId(getSelectedCbItem(assistantCB));
            return saveDbRecord(xlb, isNew);
        }
        return false;
    }

    private Component getTabbedPanel() {
        JTabbedPane tp = new MyJideTabbedPane();
        try {
            JScrollPane sp = new JScrollPane(new TripsGrid(XlendWorks.getExchanger(), (Xlowbed) getDbObject(), this));
            sp.setPreferredSize(new Dimension(700, 200));
            tp.add(sp, "Trips");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return tp;
    }

    @Override
    public void freeResources() {
        //TODO
    }
}
