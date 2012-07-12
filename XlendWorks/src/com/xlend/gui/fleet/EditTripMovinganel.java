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
public class EditTripMovinganel extends RecordEditPanel implements EditSubPanel {

    private DefaultComboBoxModel inSiteCbModel;
    private JComboBox inSiteCB;
    private JComboBox toSiteCB;
    private JSpinner distanceEmptySP;
    private JSpinner distanceLoadedSP;
    private JComboBox machineCB;
    private JComboBox operatorCB;

    public EditTripMovinganel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "Machine:",
            "Operator:",
            "From:",
            "To:",
            "Distance Travelled Empty (km):",
            "Distance Travelled Loaded (km):"
        };
        inSiteCbModel = new DefaultComboBoxModel();
        for (int i = 0; i < EditTripPanel.toSiteCbModel.getSize(); i++) {
            inSiteCbModel.addElement(EditTripPanel.toSiteCbModel.getElementAt(i));
        }
        edits = new JComponent[]{
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(EditTripPanel.machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            getGridPanel(comboPanelWithLookupBtn(operatorCB = new JComboBox(EditTripPanel.operatorCbModel), new EmployeeLookupAction(operatorCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(inSiteCB = new JComboBox(inSiteCbModel), new SiteLookupAction(inSiteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(toSiteCB = new JComboBox(EditTripPanel.toSiteCbModel), new SiteLookupAction(toSiteCB)), 2),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 5),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 5)
        };
        organizePanels(titles, edits, null);
        machineCB.addActionListener(EditTripPanel.syncOperatorAction(machineCB, operatorCB));        
    }

    @Override
    public void loadData() {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            selectComboItem(inSiteCB, trip.getInsiteId());
            selectComboItem(toSiteCB, trip.getTositeId());
            selectComboItem(machineCB, trip.getMachineId());
            if (trip.getDistanceEmpty() != null) {
                distanceEmptySP.setValue(trip.getDistanceEmpty());
            }
            if (trip.getDistanceLoaded() != null) {
                distanceLoadedSP.setValue(trip.getDistanceLoaded());
            }
            selectComboItem(operatorCB, trip.getOperatorId());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            trip.setInsiteId(getSelectedCbItem(inSiteCB));
            trip.setMachineId(getSelectedCbItem(machineCB));
            trip.setTositeId(getSelectedCbItem(toSiteCB));
            trip.setDistanceEmpty((Integer) distanceEmptySP.getValue());
            trip.setDistanceLoaded((Integer) distanceLoadedSP.getValue());
            trip.setOperatorId(getSelectedCbItem(operatorCB));
            return true;
        }
        return false;
    }

    @Override
    public void setMachineID(Integer machineID) {
        selectComboItem(machineCB, machineID);
    }
}
