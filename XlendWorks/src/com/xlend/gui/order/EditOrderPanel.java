/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.order;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xorder;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
class EditOrderPanel extends RecordEditPanel {

    public EditOrderPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Client Name:", "Contract Ref:", "Vat number:","Registration No.:",
            "PO Number:", "PO Date:","Contact Person:", "Contact Phone:", "Contact Fax:",
            ""
        };
    }

    @Override
    public void loadData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean save() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
