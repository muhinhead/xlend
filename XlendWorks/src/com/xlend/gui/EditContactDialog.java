package com.xlend.gui;

import com.xlend.orm.Clientprofile;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditContactDialog extends ProfileDialog {

    public EditContactDialog(String string, DbObject[] obs) {
        super(null, string, obs);
    }

    protected ProfilePanel getAdditionalPanel() {
        return new EditClientPanel((Clientprofile) additionalProfile);
    }
}
