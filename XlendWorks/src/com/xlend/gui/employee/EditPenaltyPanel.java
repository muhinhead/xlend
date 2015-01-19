/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.gui.RecordEditPanel;
import static com.xlend.gui.RecordEditPanel.getGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployeepenalty;
import com.xlend.orm.Xincidents;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Calendar;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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
 * @author nick
 */
class EditPenaltyPanel extends RecordEditPanel {
    private JTextField idField;
    private JTextArea notesField;
    private SelectedNumberSpinner decrPointsSP;
    private JComboBox decreasedByCB;
    private DefaultComboBoxModel decreasedByCbModel;
    private SelectedDateSpinner decreasedAtSP;
    private JComboBox incidentsCB;
    private DefaultComboBoxModel incidentsCbModel;
    private JCheckBox forIncidentCP;

    public EditPenaltyPanel(DbObject obj) {
        super(obj);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:", // "Clock Nr:", 
            "Decrease points:",//"For incident:"
            "Decreased by:", //"Decreased at:"
        };
        decreasedByCbModel = new DefaultComboBoxModel(XlendWorks.loadAllEmployees().toArray());
        incidentsCbModel = new DefaultComboBoxModel(XlendWorks.loadAllCurrentYearIncidents(EditPenaltyDialog.xemployeeID));
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 8),
            getBorderPanel(new JComponent[]{
                decrPointsSP = new SelectedNumberSpinner(0, 0, 100, 1),
                getBorderPanel(new JComponent[]{
                    new JPanel(),
                    forIncidentCP = new JCheckBox("For incident"),
                    incidentsCB = new JComboBox(incidentsCbModel)
                })
            }),
            getBorderPanel(new JComponent[]{
                decreasedByCB = new JComboBox(decreasedByCbModel),
                new JLabel(" at:", SwingConstants.RIGHT),
                decreasedAtSP = new SelectedDateSpinner()
            })
        };
        idField.setEnabled(false);
        incidentsCB.setVisible(false);
        decreasedByCB.setEnabled(XlendWorks.isCurrentAdmin());
        selectComboItem(decreasedByCB, XlendWorks.getCurrentUser().getPK_ID());
        decreasedAtSP.setEditor(new JSpinner.DateEditor(decreasedAtSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(decreasedAtSP);
        
        organizePanels(titles, edits, null);
        JScrollPane sp;
        add(sp = new JScrollPane(notesField = new JTextArea(6,40),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);
        sp.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Reason"));
        forIncidentCP.addChangeListener(new ChangeListener(){
            @Override
            public void stateChanged(ChangeEvent e) {
                incidentsCB.setVisible(forIncidentCP.isSelected());
            }
        });
        incidentsCB.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer incidentID = getSelectedCbItem(incidentsCB);
                try {
                    Xincidents xi = (Xincidents) XlendWorks.getExchanger().loadDbObjectOnID(Xincidents.class, incidentID);
                    notesField.setText(xi.getDescription()+"\nDamages: "+xi.getDamages());
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                }
            }
        });
    }

    @Override
    public void loadData() {
        Xemployeepenalty xp = (Xemployeepenalty) getDbObject();
        if (xp != null) {
            idField.setText(xp.getXemployeepenaltyId().toString());
            java.sql.Date dt = xp.getDecreasedAt();
            decreasedAtSP.setValue(new java.util.Date(dt.getTime()));
            forIncidentCP.setSelected(xp.getXincidentsId()!=null);
            selectComboItem(incidentsCB, xp.getXincidentsId());
            selectComboItem(decreasedByCB, xp.getDecreasedBy());
            decrPointsSP.setValue(xp.getPoints());
            notesField.setText(xp.getNotes());
        } else
            selectComboItem(decreasedByCB, XlendWorks.getCurrentUser().getProfileId());
    }

    @Override
    public boolean save() throws Exception {
        Xemployeepenalty xp = (Xemployeepenalty) getDbObject();
        boolean isNew = (xp==null);
        if(isNew) {
            xp = new Xemployeepenalty(null);
            xp.setXemployeepenaltyId(0);
        }
        java.util.Date dt = (java.util.Date) decreasedAtSP.getValue();
        xp.setDecreasedAt(new java.sql.Date(dt.getTime()));
        if(forIncidentCP.isSelected()) {
            xp.setXincidentsId(getSelectedCbItem(incidentsCB));
        } else {
            xp.setXincidentsId(null);
        }
        xp.setDecreasedBy(getSelectedCbItem(decreasedByCB));
        xp.setPoints((Integer) decrPointsSP.getValue());
        xp.setNotes(notesField.getText());
        xp.setYear(XlendWorks.getYearFromDate(dt));
        xp.setXemployeeId(EditPenaltyDialog.xemployeeID);
        return saveDbRecord(xp, isNew);
    }

    @Override
    public void freeResources() {
    }
    
}
