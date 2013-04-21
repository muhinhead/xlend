package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
public class EditPoolVehiclePanel extends EditMachinePanel {

    public EditPoolVehiclePanel(DbObject dbObject) {
        super(dbObject);
    }

    protected String getFleetNumberChar() {
        return "V";
    }
    
    protected JComponent getConsumptionLabel() {
        return new JLabel("Consumption (km/litre):", SwingConstants.RIGHT);
    }
}
