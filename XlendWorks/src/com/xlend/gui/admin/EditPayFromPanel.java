package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Cbitems;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditPayFromPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner codeSpinner;
    private JTextField paySourceField;

    public EditPayFromPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID", "Code", "Payment Source"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(codeSpinner = new SelectedNumberSpinner(0, 0, 1000, 1), 5),
            paySourceField = new JTextField(32)
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Cbitems itm = (Cbitems) getDbObject();
        if (itm != null) {
            idField.setText(itm.getCbitemId().toString());
            codeSpinner.setValue(itm.getId());
            paySourceField.setText(itm.getVal());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Cbitems itm = (Cbitems) getDbObject();
        if (itm == null) {
            itm = new Cbitems(null);
            itm.setCbitemId(0);
            isNew = true;
        }
        itm.setId((Integer)codeSpinner.getValue());
        itm.setName("paidfrom");
        itm.setVal(paySourceField.getText());
        return saveDbRecord(itm, isNew);
    }
}
