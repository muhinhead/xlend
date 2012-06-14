package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xpaidmethod;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditPayMethodPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField methodField;

    public EditPayMethodPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID", "Pay Method"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            methodField = new JTextField(32)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xpaidmethod pm = (Xpaidmethod) getDbObject();
        if (pm != null) {
            idField.setText(pm.getXpaidmethodId().toString());
            methodField.setText(pm.getMethod());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xpaidmethod pm = (Xpaidmethod) getDbObject();
        if (pm == null) {
            pm = new Xpaidmethod(null);
            pm.setXpaidmethodId(0);
            isNew = true;
        }
        pm.setMethod(methodField.getText());
        return saveDbRecord(pm, isNew);
    }
}
