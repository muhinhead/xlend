package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.DieselCartLookupAction;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xdiesel2plant;
import com.xlend.orm.Xdiesel2plantitem;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DefaultFormatter;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselToPlantPanel extends RecordEditPanel {

    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel dieselCartCbModel;
    private JComboBox driverCB;
    private JComboBox dieselCartCB;
    private JTextField idField;
    private SelectedDateSpinner lastDateSP;
    private JLabel balanceInCartLBL;
    private ArrayList<ItemPanel> childRows;
    private ArrayList<ItemPanel> toDelete;
    private JPanel downGridPanel;
    private JCheckBox markAllCB;
    private JComponent hdrPanel;

    private class ItemPanel extends JPanel {

        private Xdiesel2plantitem item;
        private JCheckBox markCB;
        private JSpinner addDateSP;
        private DefaultComboBoxModel machineCbModel;
        private JComboBox machineCB;
        private DefaultComboBoxModel siteCbModel;
        private JComboBox siteCB;
        private DefaultComboBoxModel operatorCbModel;
        private JComboBox operatorCB;
        private JSpinner hourMeterSP;
        private DefaultComboBoxModel issuedByCbModel;
        private JComboBox issuedByCB;
        private JSpinner litersSP;
        private AbstractAction checkAssignmentsAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer machineID = getSelectedCbItem(machineCB);
                Integer siteID = getSelectedCbItem(siteCB);
                Integer operatorID = getSelectedCbItem(operatorCB);
                if (machineID != null && siteID != null && operatorID != null) {
                    try {
                        DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xopmachassing.class,
                                "xsite_id=" + siteID + " and xmachine_id=" + machineID
                                + " and xemployee_id=" + operatorID + " and date_end is null", null);
                        if (recs.length == 0) {
                            GeneralFrame.infoMessageBox("Attention!", "This operator was not assigned to this site and machne");
                        } else {
                            recs = DashBoard.getExchanger().getDbObjects(Xopmachassing.class,
                                    "xsite_id=" + siteID + " and xmachine_id=" + machineID
                                    + " and xemployee_id is null and date_end is null", null);
                            if (recs.length == 0) {
                                GeneralFrame.infoMessageBox("Attention!", "This machine was not assigned to this site");
                            } else {
                                recs = DashBoard.getExchanger().getDbObjects(Xopmachassing.class,
                                        "xsite_id=" + siteID + " and xmachine_id is null "
                                        + " and xemployee_id=" + operatorID + " and date_end is null", null);
                                if (recs.length == 0) {
                                    GeneralFrame.infoMessageBox("Attention!", "This operator was not assigned to this site");
                                }
                            }
                        }
                    } catch (RemoteException ex) {
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };

        ItemPanel(Xdiesel2plantitem item) {
            super(new BorderLayout());
            this.item = item;
            machineCbModel = new DefaultComboBoxModel();
            siteCbModel = new DefaultComboBoxModel();
            operatorCbModel = new DefaultComboBoxModel();
            issuedByCbModel = new DefaultComboBoxModel();
            machineCbModel.addElement(new ComboItem(0, "--select--"));
            siteCbModel.addElement(new ComboItem(0, "--select--"));
            operatorCbModel.addElement(new ComboItem(0, "--select--"));
            issuedByCbModel.addElement(new ComboItem(0, "--select--"));
            for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
                operatorCbModel.addElement(ci);
                issuedByCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                machineCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadSites(DashBoard.getExchanger(), null)) {
                if (!ci.getValue().startsWith("--")) {
                    siteCbModel.addElement(ci);
                }
            }
            add(markCB = new JCheckBox(), BorderLayout.WEST);
            double limit = Double.parseDouble(balanceInCartLBL.getText().replace(",", ".")) + .01;
            JPanel centerPanel = (JPanel) getGridPanel(new JComponent[]{
                        addDateSP = new SelectedDateSpinner(),
                        comboPanelWithLookupBtn(machineCB = new JComboBox(machineCbModel), new MachineLookupAction(machineCB, null)),
                        comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
                        comboPanelWithLookupBtn(operatorCB = new JComboBox(operatorCbModel), new EmployeeLookupAction(operatorCB)),
                        hourMeterSP = new SelectedNumberSpinner(.0, .0, 999999.9, .1),
                        comboPanelWithLookupBtn(issuedByCB = new JComboBox(issuedByCbModel), new EmployeeLookupAction(issuedByCB)),
                        litersSP = new SelectedNumberSpinner(.0, .0, limit, .1)
                    });

            addDateSP.setEditor(new JSpinner.DateEditor(lastDateSP, "dd/MM/yyyy hh:mm"));
            Util.addFocusSelectAllAction(addDateSP);
            add(centerPanel, BorderLayout.CENTER);
            setPreferredSize(new Dimension(500, getPreferredSize().height));
            load();

            siteCB.addActionListener(checkAssignmentsAction);
            operatorCB.addActionListener(checkAssignmentsAction);
            machineCB.addActionListener(checkAssignmentsAction);

            machineCB.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Integer machineID = getSelectedCbItem(machineCB);
                    final String UNKNOWN = "unknown previous reading";
                    if (machineID != null) {
                        Xdiesel2plantitem rec = getItem();
                        Integer xdiesel2plantitemID = (rec == null ? null : rec.getXdiesel2plantitemId());
                        String prevReading = XlendWorks.getPrevHourMeter(DashBoard.getExchanger(), xdiesel2plantitemID, machineID);
                        if (prevReading.trim().length() > 0) {
                            getHourMeterSP().setToolTipText("previous hour meter was " + prevReading);
                            Double curValue = (Double) getHourMeterSP().getValue();
                            SpinnerNumberModel model = (SpinnerNumberModel) getHourMeterSP().getModel();
                            Double prevValue = new Double(prevReading);
                            if (prevValue.doubleValue() > curValue.doubleValue()) {
                                getHourMeterSP().setValue(prevValue);
                            }
                            model.setMinimum(prevValue);
                        } else {
                            getHourMeterSP().setToolTipText(UNKNOWN);
                        }
                    } else {
                        getHourMeterSP().setToolTipText(UNKNOWN);
                    }
                }
            });

            litersSP.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    if (getLitersSP().isEnabled()) {
                        recalcDieselCartBalance();
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if ((Double) litersSP.getValue() >= 500.0) {
                                    GeneralFrame.infoMessageBox("Attention!",
                                            "Big value of " + litersSP.getValue() + " liters entered!");
                                }
                            }
                        });
                    }
                }
            });

            hourMeterSP.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    Integer machineID = getSelectedCbItem(machineCB);
                    if (machineID != null) {
                        Xdiesel2plantitem rec = getItem();
                        Integer xdiesel2plantitemID = (rec == null ? null : rec.getXdiesel2plantitemId());
                        String prevReading = XlendWorks.getPrevHourMeter(DashBoard.getExchanger(), xdiesel2plantitemID, machineID);
                        if (prevReading.trim().length() > 0) {
                            final double prevHM = Double.parseDouble(prevReading);
                            Double curHM = (Double) hourMeterSP.getValue();
                            if (curHM != null && curHM.doubleValue() - prevHM > 45) {
                                SwingUtilities.invokeLater(new Runnable() {
                                    @Override
                                    public void run() {
                                        GeneralFrame.infoMessageBox("Attention!",
                                                "Previous reading (" + prevHM + ") differs from current by more than 45 hours!");
                                    }
                                });
                            }
                        }
                    }
                }
            });

        }

        private void load() {
            if (getItem() != null) {
                if (getItem().getAddDate() != null) {
                    addDateSP.setValue(new java.util.Date(getItem().getAddDate().getTime()));
                }
                selectComboItem(machineCB, getItem().getXmachineId());
                selectComboItem(siteCB, getItem().getXsiteId());
                selectComboItem(operatorCB, getItem().getOperatorId());
                getHourMeterSP().setValue(getItem().getHourMeter());
                selectComboItem(issuedByCB, getItem().getIssuedbyId());
                getLitersSP().setValue(getItem().getLiters());
                getLitersSP().setEnabled(false);
                getHourMeterSP().setEnabled(false);
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getSelectedCbItem(machineCB) == null) {
                GeneralFrame.errMessageBox("AttentioN!", "Select machine please");
                machineCB.requestFocus();
                return false;
            }
            if (getSelectedCbItem(siteCB) == null) {
                GeneralFrame.errMessageBox("AttentioN!", "Select site please");
                siteCB.requestFocus();
                return false;
            }
            if (getSelectedCbItem(operatorCB) == null) {
                GeneralFrame.errMessageBox("AttentioN!", "Select operator please");
                operatorCB.requestFocus();
                return false;
            }
            if (getSelectedCbItem(issuedByCB) == null) {
                GeneralFrame.errMessageBox("AttentioN!", "Select issuer please");
                issuedByCB.requestFocus();
                return false;
            }
            if (getItem() == null) {
                item = new Xdiesel2plantitem(null);
                item.setXdiesel2plantitemId(0);
                Xdiesel2plant xp = (Xdiesel2plant) getDbObject();
                item.setXdiesel2plantId(xp.getXdiesel2plantId());
                isNew = true;
            }

            Date dt = (java.util.Date) addDateSP.getValue();
            if (dt != null) {
                item.setAddDate(new java.sql.Date(dt.getTime()));
            }
            item.setXmachineId(getSelectedCbItem(machineCB));
            item.setIssuedbyId(getSelectedCbItem(issuedByCB));
            item.setOperatorId(getSelectedCbItem(operatorCB));
            item.setXsiteId(getSelectedCbItem(siteCB));
            item.setHourMeter((Double) getHourMeterSP().getValue());
            item.setLiters((Double) getLitersSP().getValue());
            return saveDbRecord(getItem(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xdiesel2plantitem) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        /**
         * @return the item
         */
        public Xdiesel2plantitem getItem() {
            return item;
        }

        /**
         * @return the litersSP
         */
        public JSpinner getLitersSP() {
            return litersSP;
        }

        /**
         * @return the hourMeterSP
         */
        public JSpinner getHourMeterSP() {
            return hourMeterSP;
        }
    }

    public EditDieselToPlantPanel(DbObject dbObject) {
        super(dbObject);
    }

    private boolean notInDeleted(ItemPanel itp) {
        for (ItemPanel deleted : toDelete) {
            if (deleted == itp) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Diesel Cart:", // "Balance Available in Diesel Cart:"
            "Driver:"
        };
        driverCbModel = new DefaultComboBoxModel();
        dieselCartCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            driverCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllDieselCarts(DashBoard.getExchanger())) {
            dieselCartCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField()}),
            getBorderPanel(new JComponent[]{lastDateSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(dieselCartCB = new JComboBox(dieselCartCbModel), new DieselCartLookupAction(dieselCartCB)),
                new JLabel("   Current Balance Available in Diesel Cart:", SwingConstants.RIGHT),
                balanceInCartLBL = new JLabel("0.00", SwingConstants.RIGHT)
            }),
            getBorderPanel(new JComponent[]{comboPanelWithLookupBtn(
                driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB)),
                null,
                createItmBtnPanel()
            })
        };
        idField.setEnabled(false);
        idField.setPreferredSize(lastDateSP.getPreferredSize());
        lastDateSP.setEditor(new JSpinner.DateEditor(lastDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(lastDateSP);
        balanceInCartLBL.setPreferredSize(lastDateSP.getPreferredSize());
        balanceInCartLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        organizePanels(titles, edits, null);
        dieselCartCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recalcDieselCartBalance();
            }
        });

        try {
            dieselCartCB.setSelectedIndex(0);
        } catch (Exception e) {
//            System.out.println(e.getMessage());
        }

        JPanel downShellPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane;
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        JPanel rghtHdrPanel;

        hdrPanel = getBorderPanel(new JComponent[]{
                    markAllCB = new JCheckBox(),
                    rghtHdrPanel = new JPanel(new GridLayout(1, 7))
                });
        rghtHdrPanel.add(new JLabel("Date/Time", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Machine", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Site", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Operator", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Hour Meter", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Issued By", SwingConstants.CENTER));
        rghtHdrPanel.add(new JLabel("Liters Issued", SwingConstants.CENTER));
        downGridPanel.add(hdrPanel);

        markAllCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    p.mark(markAllCB.isSelected());
                }
            }
        });
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Fillings"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width * 2 / 3, 400));
    }

    private void recalcDieselCartBalance() {
        Integer dieaselCartID = getSelectedCbItem(dieselCartCB);
        if (dieaselCartID != null) {
            java.util.Date dt = new Date();
            Double balance = new Double(XlendWorks.calcDieselBalanceAtCart(DashBoard.getExchanger(), dieaselCartID, dt));
            double lastLiters = 0.0;
            for (ItemPanel itp : childRows) {
                if (itp.getItem() == null && itp.getLitersSP().getValue() != null) {
                    lastLiters = (Double) itp.getLitersSP().getValue();
                    balance -= lastLiters;
                }
            }
            balanceInCartLBL.setText(String.format("%.2f", balance));
            if (childRows.size() > 0) {
                balance += lastLiters;
                SpinnerNumberModel model = (SpinnerNumberModel) childRows.get(childRows.size() - 1).getLitersSP().getModel();
                model.setMaximum(balance + 0.01);
            }
        }
    }

    @Override
    public void loadData() {
        Xdiesel2plant xdp = (Xdiesel2plant) getDbObject();
        if (xdp != null) {
            idField.setText(xdp.getXdiesel2plantId().toString());
            lastDateSP.setValue(new java.util.Date(xdp.getLastDate().getTime()));
            selectComboItem(driverCB, xdp.getDriverId());
            selectComboItem(dieselCartCB, xdp.getXdieselcartId());
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xdiesel2plantitem.class,
                        "xdiesel2plant_id=" + xdp.getXdiesel2plantId(), "xdiesel2plantitem_id");
                for (DbObject rec : recs) {
                    childRows.add(new ItemPanel((Xdiesel2plantitem) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
            recalcDieselCartBalance();
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xdiesel2plant xdp = (Xdiesel2plant) getDbObject();
        if (xdp == null) {
            xdp = new Xdiesel2plant(null);
            xdp.setXdiesel2plantId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) lastDateSP.getValue();
        if (dt != null) {
            xdp.setLastDate(new java.sql.Date(dt.getTime()));
        }
        xdp.setDriverId(getSelectedCbItem(driverCB));
        xdp.setXdieselcartId(getSelectedCbItem(dieselCartCB));

        boolean ok = saveDbRecord(xdp, isNew);
        if (ok) {
            for (ItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (ItemPanel d : toDelete) {
                if (d.getItem() != null) {
                    DashBoard.getExchanger().deleteObject(d.getItem());
                }
            }
        }
        return ok;
    }

    private JPanel createItmBtnPanel() {
        JPanel itemBtnPanel = new JPanel(new GridLayout(1, 2));
        JButton addBtn;
        itemBtnPanel.add(addBtn = new JButton(new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (childRows.size() > 0) {
                    childRows.get(childRows.size() - 1).getLitersSP().setEnabled(false);
                    childRows.get(childRows.size() - 1).getHourMeterSP().setEnabled(false);
                }
                childRows.add(new ItemPanel(null));
                redrawRows();
            }
        }));
        addBtn.setToolTipText("Add Line");
        JButton delBtn;
        itemBtnPanel.add(delBtn = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (ItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (ItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
                if (getDbObject() != null) {
                    if (childRows.size() > 0) {
                        childRows.get(childRows.size() - 1).getLitersSP().setEnabled(true);
                        childRows.get(childRows.size() - 1).getHourMeterSP().setEnabled(true);
                    }
                }
            }
        }));
        delBtn.setToolTipText("Delete Item(s)");
        return itemBtnPanel;
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (ItemPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
        recalcDieselCartBalance();
    }
}
