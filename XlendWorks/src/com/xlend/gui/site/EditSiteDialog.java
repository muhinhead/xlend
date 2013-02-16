package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xorder xorder;

    public EditSiteDialog(String title, DbObject obj) {
        super(title, obj);
    }

    private void setXorder() {
        EditSitePanel editPanel = (EditSitePanel) getEditPanel();
        editPanel.setXorder(xorder);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSitePanel((Xsite) getObject()));
        setXorder();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditSiteDialog.xorder = null;
        EditSitePanel editPanel = (EditSitePanel) getEditPanel();
        editPanel.setXorder(null);
    }
}
