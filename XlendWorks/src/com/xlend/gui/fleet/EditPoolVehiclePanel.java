package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
public class EditPoolVehiclePanel extends EditMachinePanel {

    public EditPoolVehiclePanel(DbObject dbObject) {
        super(dbObject);
    }
    
    protected String getFleetNumberChar() {
        return "P";
    }
}
