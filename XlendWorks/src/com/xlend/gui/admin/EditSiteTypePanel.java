package com.xlend.gui.admin;

import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Cbitems;
import com.xlend.orm.dbobject.DbObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
public class EditSiteTypePanel extends RecordEditPanel {

    private JTextField idField;
//    private JSpinner codeSpinner;
    private JTextField codeField;
    private JTextField siteTypeField;

    public EditSiteTypePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID", "Code", "Site Type"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            //            getGridPanel(codeSpinner = new SelectedNumberSpinner(0, 0, 1000, 1), 5),
            getGridPanel(codeField = new JTextField(2), 5),
            siteTypeField = new JTextField(32)
        };
        codeField.setEditable(false);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        siteTypeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                codeField.setText("");
                if (siteTypeField.getText().trim().length() > 0) {
                    codeField.setText(siteTypeField.getText().trim().substring(0, 1));
                }
            }
//            @Override
//            public void keyPressed(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//
//            @Override
//            public void keyReleased(KeyEvent e) {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
        });
    }

    @Override
    public void loadData() {
        Cbitems itm = (Cbitems) getDbObject();
        if (itm != null) {
            idField.setText(itm.getCbitemId().toString());
//            codeSpinner.setValue(itm.getId());
            codeField.setText(itm.getVal().trim().substring(0, 1));
            siteTypeField.setText(itm.getVal());
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
        //        itm.setId((Integer)codeSpinner.getValue());
        byte[] byte_array = siteTypeField.getText().getBytes("US-ASCII");
        itm.setId((byte_array[1] << 8) + (byte_array[0] & 0xff));
        itm.setName("site_types");
        itm.setVal(siteTypeField.getText());
        return saveDbRecord(itm, isNew);
    }

    @Override
    public void freeResources() {
        //TODO
    }    
}