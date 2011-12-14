package com.xlend.gui.fleet;

import com.xlend.orm.dbobject.DbObject;

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
}
