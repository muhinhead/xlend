package com.xlend.gui.user;

//import com.csa.orm.Userprofile;
import com.xlend.gui.ProfileDialog;
import com.xlend.gui.ProfilePanel;
import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditUserDialog extends ProfileDialog {

    public EditUserDialog(String string, DbObject[] obs) {
        super(null, string, obs);
    }

    protected ProfilePanel getAdditionalPanel() {
        return new EditUserPanel((Userprofile) additionalProfile);
    }
}
