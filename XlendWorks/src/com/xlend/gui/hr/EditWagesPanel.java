package com.xlend.gui.hr;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xwagesum;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
class EditWagesPanel extends RecordEditPanel {

    public EditWagesPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadData() {
        //
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }

    
}
