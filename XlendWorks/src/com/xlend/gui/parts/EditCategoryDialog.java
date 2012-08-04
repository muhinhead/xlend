package com.xlend.gui.parts;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xpartcategory;
import com.xlend.orm.dbobject.DbObject;

/**
 *
 * @author Nick Mukhin
 */
public class EditCategoryDialog extends EditRecordDialog {
    public static boolean okPressed;
    private EditCategoryPanel edPanel;
    
    public EditCategoryDialog(String title, DbObject obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(edPanel = new EditCategoryPanel((Xpartcategory) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    public EditCategoryPanel getEditPanel() {
        return edPanel;
    }
}
