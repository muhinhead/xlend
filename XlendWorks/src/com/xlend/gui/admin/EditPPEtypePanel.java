package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xppetype;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditPPEtypePanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField ppeTypeField;

    public EditPPEtypePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID", "PPE Type"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            ppeTypeField = new JTextField(32)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xppetype xt = (Xppetype) getDbObject();
        if (xt != null) {
            idField.setText(xt.getXppetypeId().toString());
            ppeTypeField.setText(xt.getXppetype());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xppetype xt = (Xppetype) getDbObject();
        if (xt == null) {
            xt = new Xppetype(null);
            xt.setXppetypeId(0);
            isNew = true;
        }
        xt.setXppetype(ppeTypeField.getText());
        if (xt.getStocklevel() == null) {
            xt.setStocklevel(0);
        }
        return saveDbRecord(xt, isNew);
    }
}
