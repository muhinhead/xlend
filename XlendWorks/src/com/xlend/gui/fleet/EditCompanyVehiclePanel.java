package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditCompanyVehiclePanel extends EditMachinePanel {

    public EditCompanyVehiclePanel(DbObject dbObject) {
        super(dbObject);
    }

    protected String getFleetNumberChar() {
        return "P";
    }

    protected JComponent getConsumptionLabel() {
        return new JLabel("Consumption (km/litre):", SwingConstants.RIGHT);
    }
}
