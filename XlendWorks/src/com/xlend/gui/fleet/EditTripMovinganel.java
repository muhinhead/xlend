package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtrip;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripMovinganel extends RecordEditPanel implements EditSubPanel {

    private DefaultComboBoxModel inSiteCbModel;
    private JComboBox inSiteCB;
    private DefaultComboBoxModel toSiteCbModel;
    private JComboBox toSiteCB;
    private JSpinner distanceEmptySP;
    private JSpinner distanceLoadedSP;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox machineCB;

    public EditTripMovinganel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "Machine:",
            "From:",
            "To:",
            "Distance Travelled Empty (km):",
            "Distance Travelled Loaded (km):"
        };
        inSiteCbModel = new DefaultComboBoxModel();
        toSiteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                inSiteCbModel.addElement(ci);
                toSiteCbModel.addElement(ci);
            }
        }
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        edits = new JComponent[]{
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            getGridPanel(comboPanelWithLookupBtn(inSiteCB = new JComboBox(inSiteCbModel), new SiteLookupAction(inSiteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(toSiteCB = new JComboBox(toSiteCbModel), new SiteLookupAction(toSiteCB)), 2),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 5),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 5)
        };
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            if (trip.getInsiteId() != null) {
                selectComboItem(inSiteCB, trip.getInsiteId());
            }
            if (trip.getTositeId() != null) {
                selectComboItem(toSiteCB, trip.getTositeId());
            }
            if (trip.getMachineId() != null) {
                selectComboItem(machineCB, trip.getMachineId());
            }
            if (trip.getDistanceEmpty() != null) {
                distanceEmptySP.setValue(trip.getDistanceEmpty());
            }
            if (trip.getDistanceLoaded() != null) {
                distanceLoadedSP.setValue(trip.getDistanceLoaded());
            }
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
            return true;
        }
        return false;
    }
//    public Integer getTargetSiteID() {
//        return getSelectedCbItem(toSiteCB);
//    }

    @Override
    public void setMachineID(Integer machineID) {
       selectComboItem(machineCB, machineID);
    }
}
