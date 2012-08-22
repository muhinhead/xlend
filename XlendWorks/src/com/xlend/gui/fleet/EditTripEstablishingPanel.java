package com.xlend.gui.fleet;

import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtrip;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripEstablishingPanel extends RecordEditPanel implements EditSubPanel {

    private JComboBox siteCB;
    private JSpinner distanceEmptySP;
    private JSpinner distanceLoadedSP;
    private JComboBox machineCB;
    private JComboBox operatorCB;
    private JTextField otherSiteField;

    public EditTripEstablishingPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "Site:",
            "Machine:",
            "Operator:",
            "Distance Travelled Empty (km):",
            "Distance Travelled Loaded (km):"
        };
        edits = new JComponent[]{
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(siteCB = new JComboBox(EditTripPanel.toSiteCbModel), new SiteLookupAction(siteCB)), otherSiteField=new JTextField()}),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(EditTripPanel.machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(EditTripPanel.operatorCbModel), new EmployeeLookupAction(operatorCB)), 2),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 5),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 5)
        };
        otherSiteField.setVisible(false);
        siteCB.addActionListener(EditTripPanel.otherSiteAction(otherSiteField));
        organizePanels(titles, edits, null);
//        prepareFields();
        machineCB.addActionListener(EditTripPanel.syncOperatorAction(machineCB, operatorCB));
    }

    @Override
    public void loadData() {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            RecordEditPanel.addSiteItem(EditTripPanel.toSiteCbModel, trip.getTositeId());
            selectComboItem(siteCB, trip.getTositeId());
            selectComboItem(machineCB, trip.getMachineId());
            if (trip.getDistanceEmpty() != null) {
                distanceEmptySP.setValue(trip.getDistanceEmpty());
            }
            if (trip.getDistanceLoaded() != null) {
                distanceLoadedSP.setValue(trip.getDistanceLoaded());
            }
            selectComboItem(operatorCB, trip.getOperatorId());
            otherSiteField.setText(trip.getOthersite());
            if (trip.getTositeId()==null || trip.getTositeId()==0) {
                siteCB.setSelectedIndex(EditTripPanel.toSiteCbModel.getSize());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            trip.setTositeId(getSelectedCbItem(siteCB));
            trip.setMachineId(getSelectedCbItem(machineCB));
            trip.setDistanceEmpty((Integer) distanceEmptySP.getValue());
            trip.setDistanceLoaded((Integer) distanceLoadedSP.getValue());
            trip.setOperatorId(getSelectedCbItem(operatorCB));
            trip.setOthersite(otherSiteField.getText());
            return true;
        }
        return false;
    }

    @Override
    public void setMachineID(Integer machineID) {
        selectComboItem(machineCB, machineID);
    }
}
