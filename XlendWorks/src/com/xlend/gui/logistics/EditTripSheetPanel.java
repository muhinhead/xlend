package com.xlend.gui.logistics;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.LowBedLookupAction;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xtripsheet;
import com.xlend.orm.Xtripsheetpart;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import com.xlend.util.WiderDropDownCombo;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EditTripSheetPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel authorizedCbModel;
    private DefaultComboBoxModel lowbedCbModel;
    private JSpinner dateSP;
    private JComboBox driverCB;
    private WiderDropDownCombo lowbedCB;
    private JComboBox authorizedCB;
    private static String[] hdrs = new String[]{
        "Date", "From", "To", "Loaded with", "Empty", "Time start", "Time end", "Km", "Assistant"
    };
    private JPanel downGridPanel;
    private ArrayList<RowPanel> childRows;
    private ArrayList<RowPanel> toDelete;
    private JCheckBox selectAllCB;
    private JPanel hdrPanel;
    private JScrollPane scrollPane;

    private class RowPanel extends JPanel {

        private DefaultComboBoxModel fromSiteCbModel, toSiteCbModel,
                assistantCbModel, loaded1CbModel, loaded2CbModel;
        private JCheckBox markCB;
        private JSpinner dateSP;
        private JComboBox fromSiteCB, toSiteCB;
        private JTextField fromPlaceField;
        private JTextField toPlaceField;
        private JComboBox loaded1CB, loaded2CB;
        private JCheckBox isEmptyCB;
        private JSpinner timeStartSP, timeEndSP;
        private JSpinner kilometersSP;
        private JComboBox assistantCB;
        private Xtripsheetpart xPart;

        private class CardPanel extends JPanel {

            private final JPanel comboPanel;
            private final JTextField textField;
            private final JComboBox siteCB;
            private final CardLayout clayaut;

            CardPanel(JComboBox siteCB, JTextField textField) {
                super(new CardLayout());
                clayaut = (CardLayout) (getLayout());
                this.siteCB = siteCB;
                this.comboPanel = comboPanelWithLookupBtn(siteCB, new SiteLookupAction(siteCB));
                this.textField = textField;
                add(this.comboPanel, "combo");
                add(this.textField, "text");
                init();
            }

            private void init() {
                textField.setToolTipText("double click to show site list");
                siteCB.setToolTipText("choose last item to enter other value");
                textField.addMouseListener(new MouseAdapter() {

                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            clayaut.show(CardPanel.this, "combo");
                            siteCB.requestFocus();
                        }
                    }
                });
                siteCB.addActionListener(new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ComboItem citm = (ComboItem) siteCB.getSelectedItem();
                        if (citm != null && citm.getId() == 0) {
                            clayaut.show(CardPanel.this, "text");
                            textField.requestFocus();
                        }
                    }
                });
            }
        }

        RowPanel(Xtripsheetpart part) {
            super(new GridLayout(2, 10));
            this.xPart = part;
            fromSiteCbModel = new DefaultComboBoxModel();
            toSiteCbModel = new DefaultComboBoxModel();
            assistantCbModel = new DefaultComboBoxModel();
            loaded1CbModel = new DefaultComboBoxModel();
            loaded2CbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
                if (!ci.getValue().startsWith("--")) {
                    fromSiteCbModel.addElement(ci);
                    toSiteCbModel.addElement(ci);
                }
            }
            fromSiteCbModel.addElement(new ComboItem(0, "--other--"));
            toSiteCbModel.addElement(new ComboItem(0, "--other--"));

            for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
                assistantCbModel.addElement(ci);
            }
            loaded2CbModel.addElement(new ComboItem(0, "-"));
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                loaded1CbModel.addElement(ci);
                loaded2CbModel.addElement(ci);
            }
            markCB = new JCheckBox();
            dateSP = new SelectedDateSpinner();
            dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
            Util.addFocusSelectAllAction(dateSP);
            fromSiteCB = new JComboBox(fromSiteCbModel);
            toSiteCB = new JComboBox(toSiteCbModel);
            loaded1CB = new JComboBox(loaded1CbModel);
            loaded2CB = new JComboBox(loaded2CbModel);
            isEmptyCB = new JCheckBox();
            timeStartSP = new SelectedDateSpinner();
            timeEndSP = new SelectedDateSpinner();
            kilometersSP = new SelectedNumberSpinner(0, 0, 10000, 1);
            assistantCB = new JComboBox(assistantCbModel);

            timeStartSP.setEditor(new JSpinner.DateEditor(timeStartSP, "HH:mm"));
            Util.addFocusSelectAllAction(timeStartSP);
            timeEndSP.setEditor(new JSpinner.DateEditor(timeEndSP, "HH:mm"));
            Util.addFocusSelectAllAction(timeEndSP);

            add(markCB);
            add(dateSP);

            fromPlaceField = new JTextField();
            toPlaceField = new JTextField();
            add(new CardPanel(fromSiteCB, fromPlaceField));
            add(new CardPanel(toSiteCB, toPlaceField));

            add(comboPanelWithLookupBtn(loaded1CB, new MachineLookupAction(loaded1CB, null)));

            add(isEmptyCB);
            add(timeStartSP);
            add(timeEndSP);
            add(kilometersSP);
            add(comboPanelWithLookupBtn(assistantCB, new EmployeeLookupAction(assistantCB)));

            for (int i = 0; i < 4; i++) {
                add(new JPanel());
            }
            add(comboPanelWithLookupBtn(loaded2CB, new MachineLookupAction(loaded2CB, null)));
            for (int i = 0; i < 5; i++) {
                add(new JPanel());
            }
            toSiteCB.setPreferredSize(new Dimension(dateSP.getPreferredSize().width, toSiteCB.getPreferredSize().height));
            fromSiteCB.setPreferredSize(new Dimension(dateSP.getPreferredSize().width, fromSiteCB.getPreferredSize().height));
            loaded1CB.setPreferredSize(new Dimension(dateSP.getPreferredSize().width, loaded1CB.getPreferredSize().height));
            loaded2CB.setPreferredSize(new Dimension(dateSP.getPreferredSize().width, loaded2CB.getPreferredSize().height));
            assistantCB.setPreferredSize(new Dimension(dateSP.getPreferredSize().width, assistantCB.getPreferredSize().height));
            load();
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        public void load() {
            if (getxPart() != null) {
                if (getxPart().getPartdate() != null) {
                    dateSP.setValue(new java.util.Date(getxPart().getPartdate().getTime()));
                }
                if (getxPart().getFromsiteId() != null && getxPart().getFromsiteId() != 0) {
                    RecordEditPanel.addSiteItem(fromSiteCbModel, getxPart().getFromsiteId());
                    selectComboItem(fromSiteCB, getxPart().getFromsiteId());
                } else {
                    selectComboItem(fromSiteCB, 0);
                    fromPlaceField.setText(getxPart().getFromplace());
                }
                if (getxPart().getTositeId() != null && getxPart().getTositeId() != 0) {
                    RecordEditPanel.addSiteItem(toSiteCbModel, getxPart().getTositeId());
                    selectComboItem(toSiteCB, getxPart().getTositeId());
                } else {
                    selectComboItem(toSiteCB, 0);
                    toPlaceField.setText(getxPart().getToplace());
                }
                if (getxPart().getAssistantId() != null) {
                    selectComboItem(assistantCB, getxPart().getAssistantId());
                }
                isEmptyCB.setSelected(getxPart().getIsempty() != null && getxPart().getIsempty() == 1);
                if (getxPart().getKilimeters() != null) {
                    kilometersSP.setValue(getxPart().getKilimeters());
                }
                if (getxPart().getLoaded1Id() != null) {
                    selectComboItem(loaded1CB, getxPart().getLoaded1Id());
                }
                if (getxPart().getLoaded2Id() != null) {
                    selectComboItem(loaded2CB, getxPart().getLoaded2Id());
                }
                if (getxPart().getTimestart() != null) {
                    timeStartSP.setValue(new java.util.Date(getxPart().getTimestart().getTime()));
                }
                if (getxPart().getTimeend() != null) {
                    timeEndSP.setValue(new java.util.Date(getxPart().getTimeend().getTime()));
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getxPart() == null) {
                xPart = new Xtripsheetpart(null);
                getxPart().setXtripsheetpartId(0);
                Xtripsheet xt = (Xtripsheet) getDbObject();
                getxPart().setXtripsheetId(xt.getXtripsheetId());
                isNew = true;
            }

            getxPart().setFromsiteId(getSelectedCbItem(fromSiteCB));
            if(fromPlaceField.getText().length()>0) {
                getxPart().setFromplace(fromPlaceField.getText());
            }
            getxPart().setTositeId(getSelectedCbItem(toSiteCB));
            if(toPlaceField.getText().length()>0) {
                getxPart().setToplace(toPlaceField.getText());
            }
            getxPart().setIsempty(isEmptyCB.isSelected() ? 1 : 0);
            getxPart().setKilimeters((Integer) kilometersSP.getValue());
            getxPart().setLoaded1Id(getSelectedCbItem(loaded1CB));
            getxPart().setLoaded2Id(getSelectedCbItem(loaded2CB));
            java.util.Date dt = (java.util.Date) dateSP.getValue();
            getxPart().setPartdate(new java.sql.Date(dt.getTime()));
            dt = (Date) timeStartSP.getValue();
            getxPart().setTimestart(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
            dt = (Date) timeEndSP.getValue();
            getxPart().setTimeend(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
            getxPart().setAssistantId(getSelectedCbItem(assistantCB));

            return saveDbRecord(getxPart(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xPart = (Xtripsheetpart) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xPart
         */
        public Xtripsheetpart getxPart() {
            return xPart;
        }
    }

    public EditTripSheetPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<RowPanel>();
        toDelete = new ArrayList<RowPanel>();
        String[] titles = new String[]{
            "ID:",
            "Date:", // "Driver:",
            "Lowbed:"// "Authorized By:"
        };
        driverCbModel = new DefaultComboBoxModel();
        authorizedCbModel = new DefaultComboBoxModel();
        lowbedCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllLowbeds(DashBoard.getExchanger())) {
            lowbedCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            driverCbModel.addElement(ci);
            authorizedCbModel.addElement(ci);
        }
        JButton addBtn = new JButton(getAddLineAction());
        JButton delBtn = new JButton(getDeleteLineAction());
        JLabel authLblb = new JLabel("  Authorized By:", SwingConstants.RIGHT);
        JLabel drvrLbl = new JLabel("Driver:", SwingConstants.RIGHT);

        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 10),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{dateSP = new SelectedDateSpinner()}),
                getBorderPanel(new JComponent[]{
                    drvrLbl, comboPanelWithLookupBtn(driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB))
                }),
                new JPanel(),
                getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), addBtn})
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(lowbedCB = new WiderDropDownCombo(lowbedCbModel), new LowBedLookupAction(lowbedCB)),
                getBorderPanel(new JComponent[]{
                    authLblb, comboPanelWithLookupBtn(authorizedCB = new JComboBox(authorizedCbModel), new EmployeeLookupAction(authorizedCB))
                }),
                new JPanel(),
                getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), delBtn})
            })
        };
        drvrLbl.setPreferredSize(authLblb.getPreferredSize());
        lowbedCB.setPreferredSize(new Dimension(200, lowbedCB.getPreferredSize().height));
        lowbedCB.setWide(true);
        addBtn.setPreferredSize(delBtn.getPreferredSize());

        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 10));
        hdrPanel.add(selectAllCB = new JCheckBox());

        selectAllCB.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (RowPanel p : childRows) {
                    p.mark(selectAllCB.isSelected());
                }
            }
        });

        for (String h : hdrs) {
            hdrPanel.add(new JLabel(h));
        }
        downGridPanel.add(hdrPanel);
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Route"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width - 50, 400));
    }

    private AbstractAction getAddLineAction() {
        return new AbstractAction("Add line") {

            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new RowPanel(null));
                redrawRows();
            }
        };
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (RowPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }

    private AbstractAction getDeleteLineAction() {
        return new AbstractAction("Delete line(s)") {

            @Override
            public void actionPerformed(ActionEvent e) {

                for (RowPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (RowPanel p : toDelete) {

                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    @Override
    public void loadData() {
        Xtripsheet xt = (Xtripsheet) getDbObject();
        if (xt != null) {
            idField.setText(xt.getXtripsheetId().toString());
            if (xt.getTripdate() != null) {
                dateSP.setValue(new java.util.Date(xt.getTripdate().getTime()));
            }
            if (xt.getAuthorizedId() != null) {
                selectComboItem(authorizedCB, xt.getAuthorizedId());
            }
            if (xt.getXlowbedId() != null) {
                selectComboItem(lowbedCB, xt.getXlowbedId());
            }
            if (xt.getDriverId() != null) {
                selectComboItem(driverCB, xt.getDriverId());
            }
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripsheetpart.class,
                        "xtripsheet_id=" + xt.getXtripsheetId(), "xtripsheetpart_id");
                for (DbObject rec : recs) {
                    childRows.add(new RowPanel((Xtripsheetpart) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
        }
    }

    @Override
    public boolean save() throws Exception {
        Xtripsheet xt = (Xtripsheet) getDbObject();
        boolean isNew = false;
        if (xt == null) {
            xt = new Xtripsheet(null);
            xt.setXtripsheetId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xt.setTripdate(new java.sql.Date(dt.getTime()));
        }
        xt.setXlowbedId(getSelectedCbItem(lowbedCB));
        xt.setAuthorizedId(getSelectedCbItem(authorizedCB));
        xt.setDriverId(getSelectedCbItem(driverCB));
        boolean ok = saveDbRecord(xt, isNew);
        if (ok) {
            for (RowPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (RowPanel d : toDelete) {
                if (d.getxPart() != null) {
                    DashBoard.getExchanger().deleteObject(d.getxPart());
                }
            }
        }
        return ok;
    }
}
