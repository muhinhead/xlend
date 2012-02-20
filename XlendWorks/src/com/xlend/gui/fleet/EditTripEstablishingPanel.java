package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtripestablish;
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
public class EditTripEstablishingPanel extends RecordEditPanel {
    
    private static int xtrip_id = 0;
    private JTextField idField;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox siteCB;
    private JSpinner distanceEmptySP;
    private JSpinner distanceLoadedSP;
    private DefaultComboBoxModel machineCbModel;
    private JComboBox machineCB;

    /**
     * @param aXtrip_id the xtrip_id to set
     */
    public static void setXtrip_id(int aXtrip_id) {
        xtrip_id = aXtrip_id;
    }
    
    public EditTripEstablishingPanel(DbObject dbObject) {
        super(dbObject);
    }
    
    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Site:",
            "Machine:",
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
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
            comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        prepareFields();
    }
    
    @Override
    public void loadData() {
        Xtripestablish xtre = (Xtripestablish) getDbObject();
        if (xtre != null) {
            idField.setText(xtre.getXtripestablishId().toString());
            if (xtre.getXsiteId() != null) {
                selectComboItem(siteCB, xtre.getXsiteId());
            }
            if (xtre.getXmachineId() != null) {
                selectComboItem(machineCB, xtre.getXmachineId());
            }
            if (xtre.getDistanceEmpty() != null) {
                distanceEmptySP.setValue(xtre.getDistanceEmpty());
            }
            if (xtre.getDistanceLoaded() != null) {
                distanceLoadedSP.setValue(xtre.getDistanceLoaded());
            }            
        }
    }
    
    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xtripestablish xtre = (Xtripestablish) getDbObject();
        if (xtre == null) {
            xtre = new Xtripestablish(null);
            xtre.setXtripestablishId(0);
            isNew = true;
        }
        xtre.setXtripId(xtrip_id);
        xtre.setXsiteId(getSelectedCbItem(siteCB));
        xtre.setXmachineId(getSelectedCbItem(machineCB));
        xtre.setDistanceEmpty((Integer) distanceEmptySP.getValue());
        xtre.setDistanceLoaded((Integer) (distanceLoadedSP.isVisible() ? distanceLoadedSP.getValue() : null));
        xtrip_id = 0;
        return saveDbRecord(xtre, isNew);
    }
    
    protected void prepareFields() {
        labels[labels.length - 1].setVisible(false);
        edits[edits.length - 1].setVisible(false);
    }
}
