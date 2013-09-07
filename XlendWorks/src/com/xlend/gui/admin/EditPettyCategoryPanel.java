/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xpettycategory;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Администратор
 */
class EditPettyCategoryPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextField categoryField;

    public EditPettyCategoryPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:", "Petty category:"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            categoryField = new JTextField(32)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xpettycategory xc = (Xpettycategory) getDbObject();
        if (xc != null) {
            idField.setText(xc.getXpettycategoryId().toString());
            categoryField.setText(xc.getCategoryName());
        }
    }

    @Override
    public boolean save() throws Exception {
         boolean isNew = false;
        Xpettycategory xc = (Xpettycategory) getDbObject();
        if (xc == null) {
            xc = new Xpettycategory(null);
            xc.setXpettycategoryId(0);
            isNew = true;
        }
        xc.setCategoryName(categoryField.getText());
        return saveDbRecord(xc, isNew);
    }
}
