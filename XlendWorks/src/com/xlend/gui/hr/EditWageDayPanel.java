package com.xlend.gui.hr;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.Xwage;
import com.xlend.orm.dbobject.DbObject;
import java.awt.GridLayout;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Nick Mukhin
 */
class EditWageDayPanel extends RecordEditPanel {

    private Xtimesheet xtimesheet;
    private JTextField idField;
    private JComboBox dayCB;
    private JSpinner normalSp;
    private JSpinner overtimeSp;
    private JSpinner doubletimeSp;
    private JTextField detailsField;

    public EditWageDayPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Day:", "Normal Time:",
            "Overtime:",
            "Double time:",
            "Details:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            dayCB = new JComboBox(new String[]{
                "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"}),
            normalSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, .5)),
            overtimeSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, .5)),
            doubletimeSp = new JSpinner(new SpinnerNumberModel(0, 0, 8, .5)),
            detailsField = new JTextField(32)
        };
        labels = createLabelsArray(titles);
        idField.setEditable(false);
        organizePanels(6, 6);
        for (int i = 0; i < titles.length; i++) {
            lblPanel.add(labels[i]);
            if (i < 5) {
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
        Xwage wage = (Xwage) getDbObject();
        if (wage != null) {
            idField.setText(wage.getXwageId().toString());
            if (xtimesheet != null) {
                long millsecDiff = xtimesheet.getWeekend().getTime() - wage.getDay().getTime();
                int daysDiff = (int) (((millsecDiff / 1000) / 3600) / 24);
                if (6 - daysDiff >= 0 && 6 - daysDiff < 7) {
                    dayCB.setSelectedIndex(6 - daysDiff);
                }
            }
            normalSp.setValue(wage.getNormaltime());
            overtimeSp.setValue(wage.getOvertime());
            doubletimeSp.setValue(wage.getDoubletime());
            detailsField.setText(wage.getStoppeddetails());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xwage wage = (Xwage) getDbObject();
        boolean isNew = (wage == null);
        if (isNew) {
            wage = new Xwage(null);
            wage.setXwageId(0);
        }
        try {
            Date wend = xtimesheet.getWeekend();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(wend);
            int shift = dayCB.getSelectedIndex();
            calendar.add(Calendar.DATE, shift - 6);
            wage.setXtimesheetId(xtimesheet.getXtimesheetId());
            wage.setDay(new java.sql.Date(calendar.getTime().getTime()));
            wage.setNormaltime((Double) normalSp.getValue());
            wage.setOvertime((Double) overtimeSp.getValue());
            wage.setDoubletime((Double) doubletimeSp.getValue());
            wage.setStoppeddetails(detailsField.getText());
            setDbObject(DashBoard.getExchanger().saveDbObject(wage));
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

    void setXtimesheet(Xtimesheet xtimesheet) {
        this.xtimesheet = xtimesheet;
        if (xtimesheet != null) {
            loadData();
        }
    }
}
