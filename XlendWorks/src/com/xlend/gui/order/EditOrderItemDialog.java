package com.xlend.gui.order;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xorderitem;

/**
 *
 * @author Nick Mukhin
 */
public class EditOrderItemDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xorder xorder;

    public EditOrderItemDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXorder() {
        EditOrderItemPanel editPanel = (EditOrderItemPanel) getEditPanel();
        editPanel.setXorder(xorder);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditOrderItemPanel((Xorderitem) getObject()));
        setXorder();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditOrderItemDialog.xorder = null;
    }

    private static String getItemId(Object ob) {
        Xorderitem itm = (Xorderitem) ob;
        if (itm != null) {
            return " " + itm.getXorderitemId();
        } else {
            return " null";
        }
    }
}
