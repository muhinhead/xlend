package com.xlend.contract.item;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField matNumberField;
    private JTextArea descriptionField;
//    private J

    public EditContractItemPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void loadData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean save() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
