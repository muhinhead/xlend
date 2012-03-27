package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xaccounts;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 *
 * @author Nick
 */
public class EditAccountPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel nameCbModel;
    private JComboBox namesCB;
    private JTextField accNumberField;
    private JTextField bankField;
    private JTextField branchField;

    public EditAccountPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Name:",
            "Account Nr:",
            "Bank:",
            "Branch:"
        };
        nameCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadDistinctAccNames(DashBoard.getExchanger())) {
            nameCbModel.addElement(ci.getValue());
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 3),
            namesCB = new JComboBox(nameCbModel),
            accNumberField = new JTextField(10),
            getGridPanel(bankField = new JTextField(5),2),
            getGridPanel(branchField = new JTextField(6),2)
        };
        namesCB.setEditable(true);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xaccounts xa = (Xaccounts) getDbObject();
        if (xa != null) {
            idField.setText(xa.getXaccountId().toString());
            namesCB.setSelectedItem(xa.getAccname());
            accNumberField.setText(xa.getAccnumber());
            bankField.setText(xa.getBank());
            branchField.setText(xa.getBranch());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xaccounts xa = (Xaccounts) getDbObject();
        boolean isNew = false;
        if (xa == null) {
            xa = new Xaccounts(null);
            xa.setXaccountId(0);
            isNew = true;
        }
        xa.setAccname((String)namesCB.getSelectedItem());
        xa.setAccnumber(accNumberField.getText());
        xa.setBank(bankField.getText());
        xa.setBranch(branchField.getText());
        return saveDbRecord(xa, isNew);
    }
}
