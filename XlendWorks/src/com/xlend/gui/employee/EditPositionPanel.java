package com.xlend.gui.employee;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xposition;
import com.xlend.orm.dbobject.DbObject;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
class EditPositionPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField positionNameField;

    public EditPositionPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Position:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            positionNameField = new JTextField(20)
        };
        labels = createLabelsArray(titles);
        idField.setEditable(false);
        organizePanels(2, 2);
        for (int i = 0; i < titles.length; i++) {
            lblPanel.add(labels[i]);
            if (i == 0) {
                JPanel idPanel = new JPanel(new GridLayout(1, 3, 5, 5));
                idPanel.add(edits[i]);
                for (int j = 1; j < 3; j++) {
                    idPanel.add(new JPanel());
                }
                editPanel.add(idPanel);
            } else {
                editPanel.add(edits[i]);
            }
        }
    }

    @Override
    public void loadData() {
        Xposition pos = (Xposition) getDbObject();
        if (pos != null) {
            idField.setText(pos.getXpositionId().toString());
            positionNameField.setText(pos.getPos());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xposition pos = (Xposition) getDbObject();
        boolean isNew = (pos == null);
        if (isNew) {
            pos = new Xposition(null);
            pos.setXpositionId(0);
        }
        try {
            pos.setPos(positionNameField.getText());
            setDbObject(DashBoard.getExchanger().saveDbObject(pos));
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
