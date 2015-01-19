/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.fleet;

import com.xlend.gui.RecordEditPanel;
import static com.xlend.gui.RecordEditPanel.getBorderPanel;
import static com.xlend.gui.RecordEditPanel.getGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xmoveableassets;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.Java2sAutoComboBox;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
class EditMoveableAssetPanel extends RecordEditPanel {

    private JTextField idField;
    private Java2sAutoComboBox assetsCB;
    private JComboBox siteCB;
    private JComboBox employeeCB;
    private SelectedDateSpinner bookedOutSP;
    private JCheckBox returnedCB;
    private SelectedDateSpinner returnedSP;
    private SelectedDateSpinner deadlineSP;

    public EditMoveableAssetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Assets:",
            "Site:",
            "Booked out to:",
            "Booked out at:", //"Returned:"
            "Deadline for return:"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            assetsCB = new Java2sAutoComboBox(XlendWorks.loadDistinctAssets(), false),
            comboPanelWithLookupBtn(siteCB = new JComboBox(XlendWorks.loadAllSites()),
            new SiteLookupAction(siteCB)),
            comboPanelWithLookupBtn(employeeCB = new JComboBox(XlendWorks.loadAllEmployees().toArray()),
            new EmployeeLookupAction(employeeCB)),
            getBorderPanel(new JComponent[]{
                bookedOutSP = new SelectedDateSpinner(),
                returnedCB = new JCheckBox("returned"),
                returnedSP = new SelectedDateSpinner()
            }),
            getBorderPanel(new JComponent[]{
                deadlineSP = new SelectedDateSpinner()
            })
        };
        for (JSpinner sp : new JSpinner[]{bookedOutSP, returnedSP, deadlineSP}) {
            sp.setEditor(new JSpinner.DateEditor(sp, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(sp);
        }
        returnedCB.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                returnedSP.setVisible(returnedCB.isSelected());
            }
        });
        bookedOutSP.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                SelectedDateSpinner dt1sp = (SelectedDateSpinner) e.getSource();
                java.util.Date dt1 = (java.util.Date) dt1sp.getValue();
                java.util.Date dt2 = (java.util.Date) deadlineSP.getValue();
                if ((long) dt2.getTime() - (long) dt1.getTime() < 1000L * 3600 * 24 * 30) {
                    deadlineSP.setValue(new java.util.Date((long) dt1.getTime() + 1000L * 3600 * 24 * 30));
                }
            }
        });
        idField.setEnabled(false);
        returnedSP.setVisible(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xmoveableassets xm = (Xmoveableassets) getDbObject();
        if (xm != null) {
            idField.setText(xm.getXmoveableassetsId().toString());
            assetsCB.setSelectedItem(xm.getAssetName());
            selectComboItem(siteCB, xm.getXsiteId());
            selectComboItem(employeeCB, xm.getBookedTo());
            Date dt = xm.getBookedOut();
            if (dt != null) {
                bookedOutSP.setValue(new java.util.Date(dt.getTime()));
            }
            dt = xm.getReturned();
            returnedCB.setSelected(dt != null);
            if (dt != null) {
                returnedSP.setValue(new java.util.Date(dt.getTime()));
            }
            dt = xm.getDeadline();
            if (dt != null) {
                deadlineSP.setValue(new java.util.Date(dt.getTime()));
            }
        } else {
            java.util.Date dedDate = Calendar.getInstance().getTime();
            deadlineSP.setValue(new java.util.Date(dedDate.getTime() + 1000L * 3600 * 24 * 30));
        }
    }

    @Override
    public boolean save() throws Exception {
        Xmoveableassets xm = (Xmoveableassets) getDbObject();
        boolean isNew = (xm == null);
        if (isNew) {
            xm = new Xmoveableassets(null);
            xm.setXmoveableassetsId(0);
        }
        java.util.Date dt = (java.util.Date) bookedOutSP.getValue();
        xm.setBookedOut(new java.sql.Date(dt.getTime()));
        if (returnedCB.isSelected()) {
            dt = (java.util.Date) returnedSP.getValue();
            xm.setReturned(new java.sql.Date(dt.getTime()));
        } else {
            xm.setReturned(null);
        }
        dt = (java.util.Date) deadlineSP.getValue();
        xm.setDeadline(new java.sql.Date(dt.getTime()));
        xm.setAssetName((String) assetsCB.getSelectedItem());
        xm.setXsiteId(getSelectedCbItem(siteCB));
        xm.setBookedTo(getSelectedCbItem(employeeCB));
        return saveDbRecord(xm, isNew);
    }

    @Override
    public void freeResources() {
    }

}
