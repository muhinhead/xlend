package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xbookouts;
import com.xlend.orm.XbookoutsWithTrigger;
import com.xlend.orm.Xparts;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditXbookOutPanel extends RecordEditPanel {

    public static int partID;
    private JTextField idField;
    private JSpinner issueDateSP;
    private JSpinner quantitySP;
    private JComboBox siteCB;
    private JComboBox machineCB;
    private JComboBox issuedByCB;
    private JComboBox issuedToCB;
    private DefaultComboBoxModel siteCbModel;
    private DefaultComboBoxModel machineCbModel;
    private DefaultComboBoxModel issuedByCbModel;
    private DefaultComboBoxModel issuedToCbModel;

    public EditXbookOutPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        Double upperLimit = 9999999.99;
        if (partID != 0) {
            try {
                Xparts part = (Xparts) XlendWorks.getExchanger().loadDbObjectOnID(Xparts.class, partID);
                upperLimit = part.getQuantity();
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
        String titles[] = new String[]{
            "ID:",
            "Date of Issue:",
            "Quantity:",
            "Depot/Site:",
            "For Machine:",
            "Issued To:",
            "Issued By:"
        };
        siteCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        issuedByCbModel = new DefaultComboBoxModel();
        issuedToCbModel = new DefaultComboBoxModel();

        for (ComboItem ci : XlendWorks.loadSites("is_active=1 and sitetype='D'")) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(Selects.activeEmployeeCondition)) {
            issuedByCbModel.addElement(ci);
            issuedToCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            getGridPanel(issueDateSP = new SelectedDateSpinner(), 4),
            getGridPanel(quantitySP = new SelectedNumberSpinner(0.0, 0.0, upperLimit, 0.01), 4),
            comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB, Selects.SELECT_DEPOTS4LOOKUP)),
            getGridPanel(comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)), 2),
            comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel), new EmployeeLookupAction(issuedByCB)),
            comboPanelWithLookupBtn(issuedToCB = new JComboBox(issuedToCbModel), new EmployeeLookupAction(issuedToCB))
        };
        idField.setEnabled(false);
        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xbookouts bookOut = (Xbookouts) getDbObject();
        if (bookOut != null) {
            idField.setText(bookOut.getXbookoutsId().toString());
            if (bookOut.getIssueDate() != null) {
                Date dt = bookOut.getIssueDate();
                issueDateSP.setValue(new java.util.Date(dt.getTime()));
            }
            quantitySP.setValue(bookOut.getQuantity());
            selectComboItem(siteCB, bookOut.getXsiteId());
            selectComboItem(machineCB, bookOut.getXmachineId());
            selectComboItem(issuedByCB, bookOut.getIssuedbyId());
            selectComboItem(issuedToCB, bookOut.getIssuedtoId());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        XbookoutsWithTrigger bookOut = (XbookoutsWithTrigger) getDbObject();
        if (bookOut == null) {
            isNew = true;
            bookOut = new XbookoutsWithTrigger((Connection) null);
            bookOut.setXbookoutsId(0);
        }
        Double qty = (Double) quantitySP.getValue();
        if (qty.doubleValue() <= 0.0) {
            GeneralFrame.errMessageBox("Attention!", "Enter positive quantity please");
            ((JSpinner.DefaultEditor) quantitySP.getEditor()).getTextField().requestFocus();
            return false;
        }
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        bookOut.setIssueDate(new java.sql.Date(dt.getTime()));
        bookOut.setQuantity(qty);
        bookOut.setXsiteId(getSelectedCbItem(siteCB));
        bookOut.setXmachineId(getSelectedCbItem(machineCB));
        bookOut.setIssuedbyId(getSelectedCbItem(issuedByCB));
        bookOut.setIssuedtoId(getSelectedCbItem(issuedToCB));
        bookOut.setXpartsId(partID);
        return saveDbRecord(bookOut, isNew);
    }
}
