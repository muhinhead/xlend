/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xemployeepenalty;
import com.xlend.orm.Xmachine;

/**
 *
 * @author nick
 */
class EditPenaltyDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Integer xemployeeID;
    
    public EditPenaltyDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditPenaltyPanel((Xemployeepenalty) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
