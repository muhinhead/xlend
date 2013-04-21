package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
class EditTrackPanel extends EditMachinePanel {

    public EditTrackPanel(DbObject dbObject) {
        super(dbObject);
    }

    protected String getFleetNumberChar() {
        return "T";
    }

    protected JComponent getConsumptionLabel() {
        return new JLabel("Consumption (km/litre):", SwingConstants.RIGHT);
    }
}
