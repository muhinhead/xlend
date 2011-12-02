package com.xlend.gui.employee;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xoperator;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
    private JSpinner saturdayOverTmSpinner;
    private JSpinner saturDoubleTmSpinner;
    private JSpinner sundayOverTmSpinner;
    private JSpinner sunDoubleTmSpinner;
    private JSpinner totalNormTmSpinner;
    private JSpinner totalOverTmSpinner;
    private JSpinner totalDoubleTmSpinner;
    private ImageIcon currentPicture;
    private DefaultComboBoxModel posModel;
    private AbstractAction posLookupAction;
    private static String[] ceilTitles = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "Total"};

    public EditOperatorPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "First Name:", "Full Name:", "Surename:",
            "ID Number:", "Clock Number:",
            "Op.Clocksheets:",
            "Rate:", "Position:"};
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
            positionComboBox = new JComboBox(posModel), mondayNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            mondayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            mondayDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            tuesdayNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            tuesdayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            tuesdayDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            wednesdayNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            wednesdayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            wednesdayDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            thursdayNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            thursdayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            thursdayDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            fridayNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            fridayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            fridayDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            new JPanel(),
            saturdayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 11, 1)),
            saturDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            new JPanel(),
            sundayOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            sunDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 8, 1)),
            totalNormTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 40, 1)),
            totalOverTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 11, 1)),
            totalDoubleTmSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 3, 1))
        };
        idField.setEnabled(false);
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout(5, 20));
        lblPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        editPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        upPanel = new JPanel(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        upPanel.add(lblPanel, BorderLayout.WEST);
        upPanel.add(editPanel, BorderLayout.CENTER);
        upPanel.add(getRightUpperPanel(), BorderLayout.EAST);

        int l = 0;
        int e = 0;
        lblPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        JPanel idPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        idPanel.add(edits[e++]);
        for (int i = 1; i < 5; i++) {
            idPanel.add(new JPanel());
        }
        editPanel.add(idPanel);
        lblPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        JPanel namePanel = new JPanel(new GridLayout(1, 5, 5, 5));
        namePanel.add(edits[e++]);
        namePanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        namePanel.add(edits[e++]);
        namePanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        namePanel.add(edits[e++]);
        editPanel.add(namePanel);
        lblPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        JPanel numPanel = new JPanel(new GridLayout(1, 5, 5, 5));
        numPanel.add(edits[e++]);
        numPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        numPanel.add(edits[e++]);
        numPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));
        numPanel.add(edits[e++]);
        editPanel.add(numPanel);

        lblPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT));

        JPanel ratePanel = new JPanel(new BorderLayout());//GridLayout(1, 5, 5, 5));
        ratePanel.add(edits[e++], BorderLayout.WEST);
        JPanel posPanel = new JPanel(new BorderLayout());
        posPanel.add(new JLabel(labels[l++], SwingConstants.RIGHT), BorderLayout.WEST);
        posPanel.add(comboPanelWithLookupBtn((JComboBox) edits[e++], posLookupAction = posLookupAction()));
        ratePanel.add(posPanel);
        editPanel.add(ratePanel);

        add(getCenterPanel(labels, edits, e));
    }

//    protected JComponent getRightUpperPanel() {
//        return new JButton("!!!!");
//    }
    private JPanel weekDayPanel(String[] labels, String[] ceilLabels, JComponent[] edits, int e) {
        JPanel dayPanel = new JPanel(new BorderLayout());
        JPanel dayLabel = new JPanel(new GridLayout(3, 1));
        JPanel dayEdits = new JPanel(new GridLayout(3, 1));
        dayPanel.add(dayLabel, BorderLayout.WEST);
        dayPanel.add(dayEdits, BorderLayout.CENTER);
        for (int i = 0; i < 3; i++) {
            if (i==0 && edits[e] instanceof JPanel) {
                dayLabel.add(new JPanel());
            } else {
                dayLabel.add(new JLabel(ceilLabels[i], SwingConstants.RIGHT));
            }
        }
        for (int i = 0; i < 3; i++) {
            dayEdits.add(edits[e++]);
        }
        return dayPanel;
    }

    private JPanel getCenterPanel(String[] labels, JComponent[] edits, int e) {
        String[] ceilLabels = new String[]{"Normal Time", "Overtime", "Double Time"};
        JPanel centerPanel = new JPanel(new GridLayout(2, 4, 10, 10));

        for (int i = 0; i < ceilTitles.length; i++) {
            JPanel ceil = weekDayPanel(labels, ceilLabels, edits, e);
            ceil.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ceilTitles[i]));
            centerPanel.add(ceil);//monday);
            e += 3;
        }
        JPanel upCenter = new JPanel(new BorderLayout());
        upCenter.add(centerPanel, BorderLayout.NORTH);
        return upCenter;
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

    private AbstractAction posLookupAction() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
//                showClientLookup();
                System.out.println("!!!posLookupAction()!!!");
            }
        };
    }
}
