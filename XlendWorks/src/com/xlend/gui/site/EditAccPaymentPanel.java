/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.site;

import com.xlend.gui.RecordEditPanel;
import static com.xlend.gui.RecordEditPanel.getBorderPanel;
import static com.xlend.gui.RecordEditPanel.getGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xaccpayment;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.sql.Date;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditAccPaymentPanel extends RecordEditPanel {

    private JTextField idField;
    private JComboBox employeeCB;
    private SelectedNumberSpinner amountSP;
    private JComboBox siteCB;
    private SelectedDateSpinner date1SP;
    private SelectedDateSpinner date2SP;

    public EditAccPaymentPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Employee:",
            "Amount,R:",
            "Site:",
            "For period:"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(employeeCB = new JComboBox(XlendWorks.loadAllEmployees().toArray()),
                new EmployeeLookupAction(employeeCB))
            }),
            getGridPanel(amountSP = new SelectedNumberSpinner(0.0, 0.0, 999999.99, 1.0), 5),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(siteCB = new JComboBox(XlendWorks.loadAllSites()),
                new SiteLookupAction(siteCB))
            }),
            getBorderPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{
                    date1SP = new SelectedDateSpinner(),
                    new JLabel(" - ", SwingConstants.CENTER),
                    date2SP = new SelectedDateSpinner()
                })
            })
        };
        for (JSpinner sp : new JSpinner[]{date1SP, date2SP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }
        idField.setEnabled(false);
        date1SP.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent ce) {
                SelectedDateSpinner dt1sp = (SelectedDateSpinner) ce.getSource();
                java.util.Date dt1 = (java.util.Date) dt1sp.getValue();
                java.util.Date dt2 = (java.util.Date) date2SP.getValue();
                if ((long)dt2.getTime() - (long)dt1.getTime() < 1000L*3600*24*30) {
                    date2SP.setValue(new java.util.Date((long)dt1.getTime()+1000L*3600*24*30));
                }
            }
        });
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xaccpayment xp = (Xaccpayment) getDbObject();
        if (xp != null) {
            idField.setText(xp.getXaccpaymentId().toString());
            amountSP.setValue(xp.getAmount());
            Date dt = xp.getDate1();
            if (dt != null) {
                date1SP.setValue(new java.util.Date(dt.getTime()));
            }
            dt = xp.getDate2();
            if (dt != null) {
                date2SP.setValue(new java.util.Date(dt.getTime()));
            }
            selectComboItem(siteCB, xp.getXsiteId());
            selectComboItem(employeeCB, xp.getXemployeeId());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xaccpayment xp = (Xaccpayment) getDbObject();
        boolean isNew = (xp==null);
        if(isNew) {
            xp = new Xaccpayment(null);
            xp.setXaccpaymentId(0);
        }
        java.util.Date dt = (java.util.Date) date1SP.getValue();
        xp.setDate1(new java.sql.Date(dt.getTime()));
        dt = (java.util.Date) date2SP.getValue();
        xp.setDate2(new java.sql.Date(dt.getTime()));
        xp.setXsiteId(getSelectedCbItem(siteCB));
        xp.setXemployeeId(getSelectedCbItem(employeeCB));
        xp.setAmount((Double) amountSP.getValue());
        return saveDbRecord(xp, isNew);
    }

    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
