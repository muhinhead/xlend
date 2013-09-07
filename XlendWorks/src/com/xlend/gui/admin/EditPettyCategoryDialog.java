/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.admin;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditPettyCategoryDialog extends EditRecordDialog {

    public static boolean okPressed;
    
    public EditPettyCategoryDialog(String title, DbObject obj) {
        super(title, obj);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditPettyCategoryPanel((DbObject) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
