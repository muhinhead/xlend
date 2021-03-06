package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachtype;
import com.xlend.orm.dbobject.DbObject;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
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
    private JCheckBox isRatedCB;

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
            getBorderPanel(new JComponent[]{classField = new JTextField(1), new JPanel(), isRatedCB = new JCheckBox("Is Rated")})
        };
        if (MachineTypeGrid.papaType != null) {
            classField.setText(MachineTypeGrid.papaType.getClassify());
        }
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        int machineTypeID = 0;
        Xmachtype mt = (Xmachtype) getDbObject();
        if (mt != null) {
            machineTypeID = mt.getXmachtypeId();
            try {
                add(new MachineTypeGrid(XlendWorks.getExchanger(), Selects.SELECT_MACHTYPES.replaceAll(" is null", "=" + machineTypeID), null, false));
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
    }

    @Override
    public void loadData() {
        Xmachtype mt = (Xmachtype) getDbObject();
        if (mt != null) {
            idField.setText(mt.getXmachtypeId().toString());
            nameField.setText(mt.getMachtype());
            classField.setText(mt.getClassify());
            isRatedCB.setSelected(mt.getIsRated() != null && mt.getIsRated() == 1);
            isRatedCB.setVisible(mt.getParenttypeId() == null);
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
        if (parentID != null && mt.getXmachtypeId().intValue() != parentID.intValue()) {
            mt.setParenttypeId(parentID);
        }
        mt.setIsRated(isRatedCB.isSelected() ? 1 : 0);
        ok = saveDbRecord(mt, isNew);
        if (ok) {
            parentID = null;
        }
        return ok;
    }

    @Override
    public void freeResources() {
        //TODO
    }
}
