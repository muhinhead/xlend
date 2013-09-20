package com.xlend.gui.parts;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xpartcategory;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditCategoryPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField categoryNameField;
    public static int parentID;

    public EditCategoryPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:", "Category name:"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            categoryNameField = new JTextField(32)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xpartcategory xpc = (Xpartcategory) getDbObject();
        if (xpc != null) {
            idField.setText(xpc.getXpartcategoryId().toString());
            categoryNameField.setText(xpc.getName());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xpartcategory xpc = (Xpartcategory) getDbObject();
        if (xpc == null) {
            isNew = true;
            xpc = new Xpartcategory(null);
            xpc.setXpartcategoryId(0);
            xpc.setParentId(parentID);
            Xpartcategory parent = (Xpartcategory) XlendWorks.getExchanger().loadDbObjectOnID(Xpartcategory.class, parentID);
            xpc.setGroupId(parent.getGroupId());
        }
        xpc.setNew(isNew);
        xpc.setName(categoryNameField.getText());
        return saveDbRecord(xpc, isNew);
    }
}
