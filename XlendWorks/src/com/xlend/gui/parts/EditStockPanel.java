package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xstocks;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
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
    private JComboBox siteCB;
    private DefaultComboBoxModel siteCbModel;
    
    public EditStockPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[] {
            "ID:",
            "Name:",
            "Description:",
            "Site:"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadSites(DashBoard.getExchanger(), "sitetype='D'")) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(nameField = new JTextField(), 2),
            descriptionField = new JTextField(40),
            getGridPanel(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel),
                new SiteLookupAction(siteCB, Selects.SELECT_DEPOTS4LOOKUP)),2)
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
            selectComboItem(siteCB, xstock.getXsiteId());
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
        xstock.setXsiteId(getSelectedCbItem(siteCB));
        return saveDbRecord(xstock, isNew);
    }
    
}
