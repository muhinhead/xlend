package com.xlend.gui.parts;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xstocks;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditStockPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField descriptionField;    
    
    public EditStockPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[] {
            "ID:",
            "Name:",
            "Description:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(nameField = new JTextField(), 2),
            descriptionField = new JTextField(40)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xstocks xstock = (Xstocks) getDbObject();
        if (xstock!=null) {
            idField.setText(xstock.getXstocksId().toString());
            nameField.setText(xstock.getName());
            descriptionField.setText(xstock.getDescription());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xstocks xstock = (Xstocks) getDbObject();
        if (xstock == null) {
            xstock = new Xstocks(null);
            xstock.setXstocksId(0);
            isNew= true;
        }
        xstock.setName(nameField.getText());
        xstock.setDescription(descriptionField.getText());
        return saveDbRecord(xstock, isNew);
    }
    
}
