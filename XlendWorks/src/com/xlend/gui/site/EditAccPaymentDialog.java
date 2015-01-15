/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import static com.xlend.gui.site.EditSiteDiaryDialog.okPressed;
import com.xlend.orm.Xaccpayment;
import com.xlend.orm.Xsitediary;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
class EditAccPaymentDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditAccPaymentDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditAccPaymentPanel((Xaccpayment) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
