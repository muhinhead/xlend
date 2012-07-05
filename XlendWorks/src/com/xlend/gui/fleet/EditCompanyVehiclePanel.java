package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditCompanyVehiclePanel extends EditMachinePanel {

    public EditCompanyVehiclePanel(DbObject dbObject) {
        super(dbObject);
    }
    
    protected String getFleetNumberChar() {
        return "V";
    }
}
