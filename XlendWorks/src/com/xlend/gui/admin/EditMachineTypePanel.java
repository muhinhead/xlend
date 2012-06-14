package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xmachtype;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditMachineTypePanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField nameField;
    private JTextField classField;
    static Integer parentID;

    public EditMachineTypePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID", "Name", "Classify"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            nameField = new JTextField(32),
            getBorderPanel(new JComponent[]{classField = new JTextField(1)})
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xmachtype mt = (Xmachtype) getDbObject();
        if (mt != null) {
            idField.setText(mt.getXmachtypeId().toString());
            nameField.setText(mt.getMachtype());
            classField.setText(mt.getClassify());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean ok = true;
        boolean isNew = false;
        Xmachtype mt = (Xmachtype) getDbObject();
        if (mt == null) {
            mt = new Xmachtype(null);
            mt.setXmachtypeId(0);
            isNew = true;
        }
        mt.setMachtype(nameField.getText());
        mt.setClassify(classField.getText().length() > 1 ? classField.getText().substring(0, 1) : classField.getText());
        if (parentID != null) {
            mt.setParenttypeId(parentID);
        }
        ok = saveDbRecord(mt, isNew);
        if (ok) {
            parentID = null;
        }
        return ok;
    }
}
