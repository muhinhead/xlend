package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtripmoving;
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
public class EditTripMovinganel extends RecordEditPanel {
    private static int xtrip_id = 0;
    private JTextField idField;
    private DefaultComboBoxModel fromSiteCbModel;
    private JComboBox fromSiteCB;
    private DefaultComboBoxModel toSiteCbModel;
    private JComboBox toSiteCB;
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

    public EditTripMovinganel(DbObject dbObject) {
        super(dbObject);
    }
    
    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Machine:",
            "From:",
            "To:",
            "Distance Travelled Empty (km):",
            "Distance Travelled Loaded (km):"
        };
        fromSiteCbModel = new DefaultComboBoxModel();
        toSiteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                fromSiteCbModel.addElement(ci);
                toSiteCbModel.addElement(ci);
            }
        }
        machineCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
            machineCbModel.addElement(ci);
        }
        edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),2),
            getGridPanel(comboPanelWithLookupBtn(fromSiteCB = new JComboBox(fromSiteCbModel), new SiteLookupAction(fromSiteCB)),2),
            getGridPanel(comboPanelWithLookupBtn(toSiteCB = new JComboBox(toSiteCbModel), new SiteLookupAction(toSiteCB)),2),
            getGridPanel(distanceEmptySP = new SelectedNumberSpinner(0, 0, 10000, 1), 5),
            getGridPanel(distanceLoadedSP = new SelectedNumberSpinner(0, 0, 10000, 1), 5)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        
    }

    @Override
    public void loadData() {
        Xtripmoving xtrm = (Xtripmoving) getDbObject();
        if (xtrm != null) {
            idField.setText(xtrm.getXtripmovingId().toString());
            if (xtrm.getFromsiteId() != null) {
                selectComboItem(fromSiteCB, xtrm.getFromsiteId());
            }
            if (xtrm.getTositeId() != null) {
                selectComboItem(toSiteCB, xtrm.getTositeId());
            }
            if (xtrm.getXmachineId() != null) {
                selectComboItem(machineCB, xtrm.getXmachineId());
            }
            if (xtrm.getDistanceEmpty() != null) {
                distanceEmptySP.setValue(xtrm.getDistanceEmpty());
            }
            if (xtrm.getDistanceLoaded() != null) {
                distanceLoadedSP.setValue(xtrm.getDistanceLoaded());
            }            
            
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xtripmoving xtrm = (Xtripmoving) getDbObject();
        if (xtrm == null) {
            xtrm = new Xtripmoving(null);
            xtrm.setXtripmovingId(0);
            isNew = true;
        }
        xtrm.setXtripId(xtrip_id);
        xtrm.setXmachineId(getSelectedCbItem(machineCB));
        xtrm.setFromsiteId(getSelectedCbItem(fromSiteCB));
        xtrm.setTositeId(getSelectedCbItem(toSiteCB));
        xtrm.setDistanceEmpty((Integer) distanceEmptySP.getValue());
        xtrm.setDistanceLoaded((Integer) (distanceLoadedSP.isVisible() ? distanceLoadedSP.getValue() : null));
        xtrip_id = 0;
        return saveDbRecord(xtrm, isNew);
    }
    
}
