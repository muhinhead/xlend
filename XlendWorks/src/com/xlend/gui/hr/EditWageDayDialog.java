package com.xlend.gui.hr;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.Xwage;

/**
 *
 * @author Nick Mukhin
 */
class EditWageDayDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xtimesheet xtimesheet;

    public EditWageDayDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXorder() {
        EditWageDayPanel editPanel = (EditWageDayPanel) getEditPanel();
        editPanel.setXtimesheet(xtimesheet);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditWageDayPanel((Xwage) getObject()));
        setXorder();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditWageDayDialog.xtimesheet = null;
    }

//    private static String getItemId(Object ob) {
//        Xwage itm = (Xwage) ob;
//        if (itm != null) {
//            return " " + itm.getXwageId();
//        } else {
//            return " null";
//        }
//    }
}
