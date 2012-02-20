package com.xlend.gui.fleet;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xlowbed;
import com.xlend.orm.Xtrip;
import com.xlend.orm.Xtripestablish;
import com.xlend.orm.Xtripexchange;
import com.xlend.orm.Xtripmoving;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripPanel extends RecordEditPanel {

    private static String[] detailPanelLabels = new String[]{
        "Establishing",
        "De-Establishing",
        "Moving",
        "Exchanging"
    };
    private static Integer xlowbed_id;
    private DefaultComboBoxModel fromSiteCbModel;
    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel assistantCbModel;
    private DefaultComboBoxModel toSiteCbModel;
    private JTextField idField;
    private JSpinner dateSP;
    private JCheckBox loadedCB;
    private JSpinner distanceSP;
    private JComboBox fromSiteCB;
    private JComboBox toSiteCB;
    private JComboBox driverCB;
    private JComboBox assistantCB;
    private JRadioButton establishRB;
    private JRadioButton deEstablishRB;
    private JRadioButton movingRB;
    private JRadioButton exchangingRB;
//    private int tripType = 0;
    private JPanel detailInfoPanel;
    private RecordEditPanel establishPanel;
    private RecordEditPanel deEstablishPanel;
    private RecordEditPanel movingPanel;
    private RecordEditPanel exchangePanel;

    public EditTripPanel(DbObject dbObject) {
        super(dbObject);
    }

    public static void setXlowbed(Integer xlbId) {
        xlowbed_id = xlbId;
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Leawing From:",
            "Traveling To:",
            "Loaded:",
            "Distance (km):",
            "Driver:",
            "Assistant:",
            "Trip Type:"
        };
        fromSiteCbModel = new DefaultComboBoxModel();
        toSiteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                toSiteCbModel.addElement(ci);
                fromSiteCbModel.addElement(ci);
            }
        }
        driverCbModel = new DefaultComboBoxModel();
        assistantCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            driverCbModel.addElement(ci);
            assistantCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(dateSP = new SelectedDateSpinner(), 3),
            comboPanelWithLookupBtn(fromSiteCB = new JComboBox(fromSiteCbModel), new SiteLookupAction(fromSiteCB)),
            comboPanelWithLookupBtn(toSiteCB = new JComboBox(toSiteCbModel), new SiteLookupAction(toSiteCB)),
            getGridPanel(loadedCB = new JCheckBox(), 3),
            getGridPanel(distanceSP = new SelectedNumberSpinner(0, 0, 10000, 1), 3),
            comboPanelWithLookupBtn(driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB)),
            comboPanelWithLookupBtn(assistantCB = new JComboBox(assistantCbModel), new EmployeeLookupAction(assistantCB)),
            getGridPanel(new JComponent[]{
                establishRB = new JRadioButton("Establishing"),
                deEstablishRB = new JRadioButton("De-Establishing"),
                movingRB = new JRadioButton("Moving:"),
                exchangingRB = new JRadioButton("Exchanging:")
            })
        };
        ButtonGroup group = new ButtonGroup();
        group.add(establishRB);
        group.add(deEstablishRB);
        group.add(movingRB);
        group.add(exchangingRB);

        ActionListener switchAction = typeSwitchAction();

        establishRB.addActionListener(switchAction);
        deEstablishRB.addActionListener(switchAction);
        movingRB.addActionListener(switchAction);
        exchangingRB.addActionListener(switchAction);

        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        add(detailsPanel());
    }

    private JPanel detailsPanel() {
        Xtrip xtr = (Xtrip) getDbObject();
        detailInfoPanel = new JPanel(new CardLayout());
        try {
            detailInfoPanel.add(establishPanel = new EditTripEstablishingPanel(XlendWorks.getTripEstablish(xtr)), detailPanelLabels[0]);
            establishPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[0]));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        try {
            detailInfoPanel.add(deEstablishPanel = new EditTripDeEstablishingPanel(XlendWorks.getTripDeEstablish(xtr)), detailPanelLabels[1]);
            deEstablishPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[1]));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        try {
            detailInfoPanel.add(movingPanel = new EditTripMovinganel(XlendWorks.getTripMove(xtr)), detailPanelLabels[2]);
            movingPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[2]));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        try {
            detailInfoPanel.add(exchangePanel = new EditTripExchangePanel(XlendWorks.getTripExchange(xtr)), detailPanelLabels[3]);
            exchangePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), detailPanelLabels[3]));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }

        return detailInfoPanel;
    }

    @Override
    public void loadData() {
        Xlowbed xlb = null;
        if (xlowbed_id != null) {
            try {
                xlb = (Xlowbed) DashBoard.getExchanger().loadDbObjectOnID(Xlowbed.class, xlowbed_id);
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }
        Xtrip xtr = (Xtrip) getDbObject();
        if (xtr != null) {
            idField.setText(xtr.getXtripId().toString());
            if (xtr.getTripDate() != null) {
                java.util.Date dt = new java.util.Date(xtr.getTripDate().getTime());
                dateSP.setValue(dt);
            }
            if (xtr.getFromsiteId() != null) {
                selectComboItem(fromSiteCB, xtr.getFromsiteId());
            }
            if (xtr.getTositeId() != null) {
                selectComboItem(toSiteCB, xtr.getTositeId());
            }
            loadedCB.setSelected(xtr.getLoaded() != null && xtr.getLoaded() != 0);
            if (xtr.getDistance() != null) {
                distanceSP.setValue(xtr.getDistance());
            }
            if (xtr.getDriverId() != null) {
                selectComboItem(driverCB, xtr.getDriverId());
            } else if (xlb != null) {
                selectComboItem(driverCB, xlb.getDriverId());
            }
            if (xtr.getAssistantId() != null) {
                selectComboItem(assistantCB, xtr.getAssistantId());
            } else if (xlb != null) {
                selectComboItem(assistantCB, xlb.getAssistantId());
            }
            if (xtr.getTripType() != null) {
                switch (xtr.getTripType()) {
                    case 0:
                        establishRB.setSelected(true);
                        break;
                    case 1:
                        deEstablishRB.setSelected(true);
                        break;
                    case 2:
                        movingRB.setSelected(true);
                        break;
                    case 3:
                        exchangingRB.setSelected(true);
                        break;
                }
            } else {
                establishRB.setSelected(true);
            }
            switchDetailPanel();
        } else {
            if (xlb != null) {
                selectComboItem(driverCB, xlb.getDriverId());
            }
            if (xlb != null) {
                selectComboItem(assistantCB, xlb.getAssistantId());
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xtrip xtr = (Xtrip) getDbObject();
        if (xtr == null) {
            xtr = new Xtrip(null);
            xtr.setXtripId(0);
            isNew = true;
        }
        if (dateSP.getValue() != null) {
            java.util.Date dt = (java.util.Date) dateSP.getValue();
            xtr.setTripDate(new java.sql.Date(dt.getTime()));
        }
        xtr.setXlowbedId(xlowbed_id);
        xtr.setAssistantId(getSelectedCbItem(assistantCB));
        xtr.setDriverId(getSelectedCbItem(driverCB));
        xtr.setLoaded(loadedCB.isSelected() ? 1 : 0);
        xtr.setDistance((Integer) distanceSP.getValue());
        xtr.setFromsiteId(getSelectedCbItem(fromSiteCB));
        xtr.setTositeId(getSelectedCbItem(toSiteCB));
        xtr.setTripType(deEstablishRB.isSelected() ? 1
                : movingRB.isSelected() ? 2 : exchangingRB.isSelected() ? 3 : 0);
        boolean ok = saveDbRecord(xtr, isNew);
        if (ok) {
            EditTripEstablishingPanel.setXtrip_id(xtr.getXtripId());
            EditTripDeEstablishingPanel.setXtrip_id(xtr.getXtripId());
            EditTripMovinganel.setXtrip_id(xtr.getXtripId());
            EditTripExchangePanel.setXtrip_id(xtr.getXtripId());
            if (xtr.getTripType() < 2) {
                if (xtr.getTripType() == 0) {
                    if (establishPanel.save()) {
                        deleteDeEstablishOnlyInfo(xtr.getXtripId());
                    }
                } else {
                    if (deEstablishPanel.save()) {
                        deleteEstablishOnlyInfo(xtr.getXtripId());
                    }
                }
                deleteMovingInfo(xtr.getXtripId());
                deleteExchangeInfo(xtr.getXtripId());
            } else if (xtr.getTripType() == 2) {
                if (movingPanel.save()) {
                    deleteExchangeInfo(xtr.getXtripId());
                    deleteEstablishInfo(xtr.getXtripId());
                }
            } else if (xtr.getTripType() == 3) {
                if (exchangePanel.save()) {
                    deleteMovingInfo(xtr.getXtripId());
                    deleteEstablishInfo(xtr.getXtripId());
                }
            }
        }
        return ok;
    }

    private ActionListener typeSwitchAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchDetailPanel();
            }
        };
    }

    private void switchDetailPanel() {
        int tripType = 0;
        CardLayout cl = (CardLayout) (detailInfoPanel.getLayout());
        if (deEstablishRB.isSelected()) {
            tripType = 1;
        } else if (movingRB.isSelected()) {
            tripType = 2;
        } else if (exchangingRB.isSelected()) {
            tripType = 3;
        }
        cl.show(detailInfoPanel, detailPanelLabels[tripType]);
    }

    private void deleteEstablishOnlyInfo(int xtrip_id) {
        try {
            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripestablish.class, "xtrip_id=" + xtrip_id + " and ifnull(distance_loaded,0)=0", null);
            for (DbObject rec : recs) {
                DashBoard.getExchanger().deleteObject(rec);
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
    }

    private void deleteDeEstablishOnlyInfo(int xtrip_id) {
        try {
            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripestablish.class, "xtrip_id=" + xtrip_id + " and ifnull(distance_loaded,0)<>0", null);
            for (DbObject rec : recs) {
                DashBoard.getExchanger().deleteObject(rec);
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
    }

    private void deleteChildInfo(Class cls, int xtrip_id) {
        try {
            DbObject[] recs = DashBoard.getExchanger().getDbObjects(cls, "xtrip_id=" + xtrip_id, null);
            for (DbObject rec : recs) {
                DashBoard.getExchanger().deleteObject(rec);
            }
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
    }

    private void deleteMovingInfo(int xtrip_id) {
        deleteChildInfo(Xtripmoving.class, xtrip_id);
    }

    private void deleteExchangeInfo(int xtrip_id) {
        deleteChildInfo(Xtripexchange.class, xtrip_id);
    }

    private void deleteEstablishInfo(int xtrip_id) {
        deleteChildInfo(Xtripestablish.class, xtrip_id);
    }
}
