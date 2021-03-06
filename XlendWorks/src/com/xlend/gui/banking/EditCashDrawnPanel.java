package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import static com.xlend.gui.RecordEditPanel.getBorderPanel;
import static com.xlend.gui.RecordEditPanel.getGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xcashdrawn;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditCashDrawnPanel extends RecordEditPanel {

    private JTextField idField;
    private SelectedDateSpinner dateSP;
    private SelectedNumberSpinner cashDrawnSP;
    private SelectedNumberSpinner addMoneySP;
    private JTextArea notesTA;
    private JLabel totalLbl;
    private double initialBalance;

    public EditCashDrawnPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Date:",
            "Cash Drawn (R):",
            "Additional Monies (R):",
            "Total Balance (R):"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getBorderPanel(new JComponent[]{
                dateSP = new SelectedDateSpinner()
            }),
            getBorderPanel(new JComponent[]{
                cashDrawnSP = new SelectedNumberSpinner(0.0, 0.0, 99999.99, 0.1)
            }),
            getBorderPanel(new JComponent[]{
                addMoneySP = new SelectedNumberSpinner(0.0, 0.0, 99999.99, 0.1)
            }),
            getGridPanel(totalLbl = new JLabel("      ", SwingConstants.RIGHT), 4)
        };
        idField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy HH:mm"));
        Util.addFocusSelectAllAction(dateSP);
        organizePanels(titles, edits, null);
        JScrollPane sp;
        add(sp = new JScrollPane(notesTA = new JTextArea(12, 50)));
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Notes"));
        totalLbl.setBorder(BorderFactory.createEtchedBorder());
        cashDrawnSP.addChangeListener(getSumListener());
        addMoneySP.addChangeListener(getSumListener());
    }

    @Override
    public void loadData() {
        Xcashdrawn cd = (Xcashdrawn) getDbObject();
        if (cd != null) {
            Date dt;
            idField.setText(cd.getXcashdrawnId().toString());
            if (cd.getCurDate() != null) {
                dateSP.setValue(dt = new java.util.Date(cd.getCurDate().getTime()));
            } else {
                dt = new Date();
            }
            double calculatedBalance = cd.getCashDrawn() + cd.getAddMonies()
                    + XlendWorks.getBalance4newXpetty(dt);

            if (cd.getBalance() != null && (long) (100 * calculatedBalance) != (long) (100 * cd.getBalance())) {
                GeneralFrame.errMessageBox("Attention!", String.format(
                        "the stored balance %.2f <> calculated %.2f", cd.getBalance(),
                        calculatedBalance));
            }
            initialBalance = calculatedBalance;
            cashDrawnSP.setValue(cd.getCashDrawn());
            addMoneySP.setValue(cd.getAddMonies());
            totalLbl.setText(String.format("%.2f", initialBalance).replace(',', '.'));
            notesTA.setText(cd.getNotes());
        } else {
            initialBalance = XlendWorks.getBalance4newXpetty();
            totalLbl.setText(String.format("%.2f", initialBalance).replace(',', '.'));
        }
    }

    private void calcSum() {
        if (getDbObject() == null) {
            dateSP.setValue(new java.util.Date());
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        double calcBalance = XlendWorks.getBalance4newXpetty(dt);
//                Math.round(100 * XlendWorks.getBalance4newXpetty(dt)) / 100;
        totalLbl.setText(String.format("%.2f", calcBalance
                + (Double) cashDrawnSP.getValue() + (Double) addMoneySP.getValue()).replace(',', '.'));
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xcashdrawn cd = (Xcashdrawn) getDbObject();
        if (cd == null) {
            cd = new Xcashdrawn(null);
            cd.setXcashdrawnId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            cd.setCurDate(new java.sql.Timestamp(dt.getTime()));
        }
        cd.setCashDrawn((Double) cashDrawnSP.getValue());
        cd.setAddMonies((Double) addMoneySP.getValue());
        cd.setNotes(notesTA.getText());
        cd.setBalance(new Double(totalLbl.getText()));
        return saveDbRecord(cd, isNew);
    }

    private ChangeListener getSumListener() {
        return new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent ce) {
                calcSum();
            }
        };
    }

    @Override
    public void freeResources() {
        //TODO
    }    
}
