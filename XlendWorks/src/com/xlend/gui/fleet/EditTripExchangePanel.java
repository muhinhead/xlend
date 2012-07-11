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
public class EditTripExchangePanel extends RecordEditPanel implements EditSubPanel {

//    private static int xtrip_id = 0;
//    private JTextField idField;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox siteCB;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox machineCB;
    private DefaultComboBoxModel withMachineCbModel;
    private JComboBox withMachineCB;
    private JSpinner distanceEmptySP;
    private JSpinner distanceLoadedSP;

//    /**
//     * @param aXtrip_id the xtrip_id to set
//     */
//    public static void setXtrip_id(int aXtrip_id) {
//        xtrip_id = aXtrip_id;
//    }
    public EditTripExchangePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            //            "ID:",
            "Site:",
            "Machine:",
            "With Machine:",
            "Distance Travelled Empty (km):",
            "Distance Travelled Loaded (km):"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        machineCbModel = new DefaultComboBoxModel();
        withMachineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
            withMachineCbModel.addElement(ci);
        }
        edits = new JComponent[]{
            //            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)), 2),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            getGridPanel(comboPanelWithLookupBtn(withMachineCB = new JComboBox(withMachineCbModel), new MachineLookupAction(withMachineCB, null)), 2),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 5),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 5)
        };
//        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xtrip trip = (Xtrip) getDbObject();
        if (trip != null) {
            if (trip.getTositeId() != null) {
                selectComboItem(siteCB, trip.getTositeId());
            }
            if (trip.getMachineId() != null) {
                selectComboItem(machineCB, trip.getMachineId());
            }
            if (trip.getWithmachineId() != null) {
                selectComboItem(withMachineCB, trip.getWithmachineId());
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
            trip.setTositeId(getSelectedCbItem(siteCB));
            trip.setMachineId(getSelectedCbItem(machineCB));
            trip.setWithmachineId(getSelectedCbItem(withMachineCB));
            trip.setDistanceEmpty((Integer) distanceEmptySP.getValue());
            trip.setDistanceLoaded((Integer) distanceLoadedSP.getValue());
            return true;
        }
        return false;
    }
//    public Integer getTargetSiteID() {
//        return getSelectedCbItem(siteCB);
//    }

    @Override
    public void setMachineID(Integer machineID) {
       selectComboItem(machineCB, machineID);
    }
}
