package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xbateryissue;
import com.xlend.orm.Xbattery;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
class EditBatteryIssuePanel extends RecordEditPanel {

    private JTextField idField;
    private SelectedDateSpinner issueDateSP;
    private SelectedDateSpinner entryDateSP;
    private DefaultComboBoxModel issuedByCbModel;
    private DefaultComboBoxModel issuedToCbModel;
    private DefaultComboBoxModel machineCbModel;
    private DefaultComboBoxModel availableBattery1CbModel;
    private DefaultComboBoxModel availableBattery2CbModel;
    private JComboBox issuedByCB;
    private JComboBox issuedToCB;
    private JComboBox machineCB;
    private JLabel machineTypeLBL;
    private JComboBox battery1CB;
    private JComboBox battery2CB;

    public EditBatteryIssuePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date of Issue:", // "Date of Entry:"
            "Issued by:",
            "Issued to:",
            "Machine/Truck/Other:",
            "Battery 1 Code:",
            "Battery 2 Code:"
        };
        issuedByCbModel = new DefaultComboBoxModel();
        issuedToCbModel = new DefaultComboBoxModel();
        machineCbModel = new DefaultComboBoxModel();
        availableBattery1CbModel = new DefaultComboBoxModel();
        availableBattery2CbModel = new DefaultComboBoxModel();
        issuedByCbModel.addElement(new ComboItem(0, SELECT_HERE));
        issuedToCbModel.addElement(new ComboItem(0, SELECT_HERE));
        machineCbModel.addElement(new ComboItem(0, SELECT_HERE));
//        String[] batteryCodes = XlendWorks.loadAvailableBatteryCodes(DashBoard.getExchanger());
        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            issuedByCbModel.addElement(ci);
            issuedToCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllMachines()) {
            machineCbModel.addElement(ci);
        }
        availableBattery1CbModel.addElement(new ComboItem(0, "---"));
        availableBattery2CbModel.addElement(new ComboItem(0, "---"));
        for (ComboItem ci : XlendWorks.loadOldestAvailableBateries()) {
            availableBattery1CbModel.addElement(ci);
            availableBattery2CbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField()}),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{issueDateSP = new SelectedDateSpinner()}),
                new JLabel("Date of Entry:", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{entryDateSP = new SelectedDateSpinner()}),
                new JPanel()
            }),
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel),
                new EmployeeLookupAction(issuedByCB)), new JPanel()}),
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(issuedToCB = new JComboBox(issuedToCbModel),
                new EmployeeLookupAction(issuedToCB)), new JPanel()}),
            getGridPanel(new JComponent[]{comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel),
                new MachineLookupAction(machineCB, null)), machineTypeLBL = new JLabel("", SwingConstants.CENTER)}),
            getBorderPanel(new JComponent[]{battery1CB = new JComboBox(availableBattery1CbModel)}),
            getBorderPanel(new JComponent[]{battery2CB = new JComboBox(availableBattery2CbModel)})
        };
        machineCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                machineTypeLBL.setText(XlendWorks.getMachineType1(
                        getSelectedCbItem(machineCB)));
            }
        });
        battery1CB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer battery1ID = getSelectedCbItem(battery1CB);
                if (battery1ID != null && battery1ID > 0) {
                    battery2CB.setEnabled(true);
                    String value = getSelectedCbValue(battery1CB);
                    if (value.indexOf("(1 ") > 0) {
                        availableBattery2CbModel.removeAllElements();
                        availableBattery2CbModel.addElement(new ComboItem(0, "---"));
                        for (ComboItem ci : XlendWorks.loadOldestAvailableBateries()) {
                            if (ci.getId() != battery1ID) {
                                availableBattery2CbModel.addElement(ci);
                            }
                        }
                    }
                } else {
                    battery2CB.setEnabled(false);
                }
            }
        });

        machineTypeLBL.setBorder(BorderFactory.createEtchedBorder());
        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        entryDateSP.setEditor(new JSpinner.DateEditor(entryDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(entryDateSP);
        idField.setEnabled(false);
        idField.setPreferredSize(issueDateSP.getPreferredSize());
        battery2CB.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        Xbateryissue xb = (Xbateryissue) getDbObject();
        if (xb != null) {
            idField.setText(xb.getXbateryissueId().toString());
            issueDateSP.setValue(new java.util.Date(xb.getIssueDate().getTime()));
            entryDateSP.setValue(new java.util.Date(xb.getEntryDate().getTime()));
            selectComboItem(issuedByCB, xb.getIssuedBy());
            selectComboItem(issuedToCB, xb.getIssuedTo());
            selectComboItem(machineCB, xb.getXmachineId());
            try {
                DbObject[] itms = XlendWorks.getExchanger().getDbObjects(Xbattery.class,
                        "xbateryissue_id=" + xb.getXbateryissueId(), "xbattery_id");
                JComboBox[] cbs = new JComboBox[]{battery1CB, battery2CB};
                DefaultComboBoxModel[] cbms = new DefaultComboBoxModel[]{availableBattery1CbModel, availableBattery2CbModel};
                for (int i = 0; i < itms.length && i < 2; i++) {
                    Xbattery battery = (Xbattery) itms[i];
                    cbms[i].addElement(new ComboItem(battery.getXbatteryId(), battery.getBatteryCode()));
                    selectComboItem(cbs[i], battery.getXbatteryId());
                    cbs[i].setEnabled(false);
                }
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
            battery1CB.setEnabled(false);
            battery2CB.setEnabled(false);
        }
    }

    @Override
    public boolean save() throws Exception {
        Xbateryissue xb = (Xbateryissue) getDbObject();
        boolean isNew = false;
        if (xb == null) {
            xb = new Xbateryissue(null);
            xb.setXbateryissueId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        if (dt != null) {
            xb.setIssueDate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) entryDateSP.getValue();
        if (dt != null) {
            xb.setEntryDate(new java.sql.Date(dt.getTime()));
        }
        if (getSelectedCbItem(issuedByCB) == null) {
            issuedByCB.requestFocus();
            return false;
        }
        if (getSelectedCbItem(issuedToCB) == null) {
            issuedToCB.requestFocus();
            return false;
        }
        if (getSelectedCbItem(machineCB) == null) {
            machineCB.requestFocus();
            return false;
        }
        if (getSelectedCbItem(battery1CB) == null) {
            battery1CB.requestFocus();
            return false;
        }
        xb.setIssuedBy(getSelectedCbItem(issuedByCB));
        xb.setIssuedTo(getSelectedCbItem(issuedToCB));
        xb.setXmachineId(getSelectedCbItem(machineCB));

        boolean ok = saveDbRecord(xb, isNew);
        if (ok && isNew) {
            xb = (Xbateryissue) getDbObject();
            Integer issueID = xb.getXbateryissueId();
            Xbattery battery;
            Integer batId;
            int prevId = 0;
            for (JComboBox cb : new JComboBox[]{battery1CB, battery2CB}) {
                batId = getSelectedCbItem(cb);
                if (batId != null && batId > 0) {
                    battery = (Xbattery) XlendWorks.getExchanger().loadDbObjectOnID(Xbattery.class, batId);
                    if (battery != null) {
                        if (batId.intValue() == prevId) {
                            DbObject[] obs = XlendWorks.getExchanger().getDbObjects(Xbattery.class,
                                    "battery_code='" + battery.getBatteryCode() + "' "
                                    + "and xbateryissue_id is null "
                                    + "and xbattery_id<>" + battery.getXbatteryId(), null);
                            if (obs.length > 0) {
                                battery = (Xbattery) obs[0];
                            }
                        }
                        battery.setXbateryissueId(issueID);
                        XlendWorks.getExchanger().saveDbObject(battery);
                    }
                    prevId = batId.intValue();
                }
            }
        }
        return ok;
    }
}
