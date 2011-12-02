package com.xlend.gui.employee;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xoperator;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditOperatorPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField firstNameField;
    private JTextField fullNameField;
    private JTextField lastNameField;
    private JTextField idNumField;
    private JTextField clockNumField;
    private JCheckBox clockSheetsCB;
    private JSpinner rateSpinner;
    private JComboBox positionComboBox;

    private JSpinner mondayNormTmSpinner;
    private JSpinner mondayOverTmSpinner;
    private JSpinner mondayDoubleTmSpinner;
    
    private JSpinner tuesdayNormTmSpinner;
    private JSpinner tuesdayOverTmSpinner;
    private JSpinner tuesdayDoubleTmSpinner;
    
    private JSpinner wednesdayNormTmSpinner;
    private JSpinner wednesdayOverTmSpinner;
    private JSpinner wednesdayDoubleTmSpinner;

    private JSpinner thursdayNormTmSpinner;
    private JSpinner thursdayOverTmSpinner;
    private JSpinner thursdayDoubleTmSpinner;

    private JSpinner fridayNormTmSpinner;
    private JSpinner fridayOverTmSpinner;
    private JSpinner fridayDoubleTmSpinner;
    
    private ImageIcon currentPicture;
    private DefaultComboBoxModel posModel;

    public EditOperatorPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "First Name:", "Full Name:", "Surename:",
            "ID Number:", "Clock Number:",
            "Operator Clocksheets:",
            "Rate:", "Position:"      
        };
        posModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllPositions(DashBoard.getExchanger())) {
            posModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            firstNameField = new JTextField(),
            fullNameField = new JTextField(),
            lastNameField = new JTextField(),
            idNumField = new JTextField(),
            clockNumField = new JTextField(),
            clockSheetsCB = new JCheckBox(),
            rateSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100000, 10)),
            positionComboBox = new JComboBox(posModel)
        };
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        super.organizePanels(labels, edits);
        int n=0; 
        for (String lbl : labels) {
            lblPanel.add(new JLabel(lbl,SwingConstants.RIGHT));
            editPanel.add(edits[n++]);
        }
    }
    
    @Override
    public void loadData() {
        Xoperator xoperator = (Xoperator) getDbObject();
        if (xoperator != null) {
            idField.setText(xoperator.getXoperatorId().toString());
//            contractRefField.setText(xcontract.getContractref());
//            selectComboItem(clientRefBox, xcontract.getXclientId());
//            descriptionField.setText(xcontract.getDescription());
        }
    }

    @Override
    public boolean save() throws Exception {
        //TODO: save()
        return false;
    }
}
