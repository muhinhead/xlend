package com.xlend.gui.banking;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xbankbalance;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditBalancePanel extends RecordEditPanel {

    public EditBalancePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        
    }

    @Override
    public void loadData() {
        
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }
    
}
