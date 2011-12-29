package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xmachineonsite;
import com.xlend.orm.Xsite;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachineOnSiteItemDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xsite xsite;

    public EditMachineOnSiteItemDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXorder() {
        EditMachineOnSiteItemPanel editPanel = (EditMachineOnSiteItemPanel) getEditPanel();
        editPanel.setXsite(xsite);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditMachineOnSiteItemPanel((Xmachineonsite) getObject()));
        setXorder();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditMachineOnSiteItemDialog.xsite = null;
    }

    private static String getItemId(Object ob) {
        Xmachineonsite itm = (Xmachineonsite) ob;
        if (itm != null) {
            return " " + itm.getXmachineonsateId();
        } else {
            return " null";
        }
    }
}
