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
import java.sql.Timestamp;
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
        private JTextField loaded1Field, loaded2Field;
        private JCheckBox isEmptyCB;
        private JSpinner timeStartSP, timeEndSP;
        private JSpinner kilometersSP;
        private JComboBox assistantCB;
        private Xtripsheetpart tripSheetPart;

        private class CardPanel extends JPanel {

            private final JPanel comboPanel;
            private final JTextField textField;
            private final JComboBox comboBox;
            private final CardLayout clayaut;

            CardPanel(JComboBox cbBox, JTextField textField) {
                super(new CardLayout());
                clayaut = (CardLayout) (getLayout());
                this.comboBox = cbBox;
                if (cbBox == fromSiteCB || cbBox == toSiteCB) {
                    this.comboPanel = comboPanelWithLookupBtn(cbBox, new SiteLookupAction(cbBox));
                } else if (cbBox == loaded1CB || cbBox == loaded2CB) {
                    this.comboPanel = comboPanelWithLookupBtn(cbBox, new MachineLookupAction(cbBox, null));
                } else {
                    this.comboPanel = new JPanel();
                    comboPanel.add(cbBox);
                }
                this.textField = textField;
                add(this.comboPanel, "combo");
                add(this.textField, "text");
                init();
            }

            private void init() {
                textField.setToolTipText("double click to show a list");
                comboBox.setToolTipText("choose last item to enter other value");
                textField.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 2) {
                            clayaut.show(CardPanel.this, "combo");
                            comboBox.requestFocus();
                        }
                    }
                });
                comboBox.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ComboItem citm = (ComboItem) comboBox.getSelectedItem();
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
            this.tripSheetPart = part;
            fromSiteCbModel = new DefaultComboBoxModel();
            toSiteCbModel = new DefaultComboBoxModel();
            assistantCbModel = new DefaultComboBoxModel();
            loaded1CbModel = new DefaultComboBoxModel();
            loaded2CbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadActiveSites()) {
                if (!ci.getValue().startsWith("--")) {
                    fromSiteCbModel.addElement(ci);
                    toSiteCbModel.addElement(ci);
                }
            }

            for (ComboItem ci : XlendWorks.loadAllEmployees()) {
                assistantCbModel.addElement(ci);
            }
//            loaded2CbModel.addElement(new ComboItem(0, "-"));
            for (ComboItem ci : XlendWorks.loadAllMachines()) {
                loaded1CbModel.addElement(ci);
                loaded2CbModel.addElement(ci);
            }
            for (DefaultComboBoxModel model : new DefaultComboBoxModel[]{fromSiteCbModel, toSiteCbModel, loaded1CbModel, loaded2CbModel}) {
                model.addElement(new ComboItem(0, "--other--"));
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

            add(new CardPanel(fromSiteCB, fromPlaceField = new JTextField()));
            add(new CardPanel(toSiteCB, toPlaceField = new JTextField()));

            add(new CardPanel(loaded1CB, loaded1Field = new JTextField()));

            add(isEmptyCB);
            add(timeStartSP);
            add(timeEndSP);
            add(kilometersSP);
            add(comboPanelWithLookupBtn(assistantCB, new EmployeeLookupAction(assistantCB)));

            for (int i = 0; i < 4; i++) {
                add(new JPanel());
            }
            add(new CardPanel(loaded2CB, loaded2Field = new JTextField()));

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
            if (getTripSheetPart() != null) {
                if (getTripSheetPart().getPartdate() != null) {
                    dateSP.setValue(new java.util.Date(getTripSheetPart().getPartdate().getTime()));
                }
                if (getTripSheetPart().getFromsiteId() != null && getTripSheetPart().getFromsiteId() != 0) {
                    RecordEditPanel.addSiteItem(fromSiteCbModel, getTripSheetPart().getFromsiteId());
                    selectComboItem(fromSiteCB, getTripSheetPart().getFromsiteId());
                } else {
                    selectComboItem(fromSiteCB, 0);
                    fromPlaceField.setText(getTripSheetPart().getFromplace());
                }
                if (getTripSheetPart().getTositeId() != null && getTripSheetPart().getTositeId() != 0) {
                    RecordEditPanel.addSiteItem(toSiteCbModel, getTripSheetPart().getTositeId());
                    selectComboItem(toSiteCB, getTripSheetPart().getTositeId());
                } else {
                    selectComboItem(toSiteCB, 0);
                    toPlaceField.setText(getTripSheetPart().getToplace());
                }
                if (getTripSheetPart().getAssistantId() != null) {
                    selectComboItem(assistantCB, getTripSheetPart().getAssistantId());
                }
                isEmptyCB.setSelected(getTripSheetPart().getIsempty() != null && getTripSheetPart().getIsempty() == 1);
                if (getTripSheetPart().getKilimeters() != null) {
                    kilometersSP.setValue(getTripSheetPart().getKilimeters());
                }
                if (getTripSheetPart().getLoaded1Id() != null && getTripSheetPart().getLoaded1Id() != 0) {
                    selectComboItem(loaded1CB, getTripSheetPart().getLoaded1Id());
                } else {
                    selectComboItem(loaded1CB, 0);
                    loaded1Field.setText(getTripSheetPart().getLoaded1());
                }
                if (getTripSheetPart().getLoaded2Id() != null && getTripSheetPart().getLoaded2Id() != 0) {
                    selectComboItem(loaded2CB, getTripSheetPart().getLoaded2Id());
                } else {
                    selectComboItem(loaded2CB, 0);
                    loaded2Field.setText(getTripSheetPart().getLoaded2());
                }
                if (getTripSheetPart().getTimestart() != null) {
                    Timestamp dt = getTripSheetPart().getTimestart();
                    timeStartSP.setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
                if (getTripSheetPart().getTimeend() != null) {
                    Timestamp dt = getTripSheetPart().getTimeend();
                    timeEndSP.setValue(new java.util.Date(dt.getTime() - TimeZone.getDefault().getOffset(dt.getTime())));
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getTripSheetPart() == null) {
                tripSheetPart = new Xtripsheetpart(null);
                getTripSheetPart().setXtripsheetpartId(0);
                Xtripsheet xt = (Xtripsheet) getDbObject();
                getTripSheetPart().setXtripsheetId(xt.getXtripsheetId());
                isNew = true;
            }

            getTripSheetPart().setFromsiteId(getSelectedCbItem(fromSiteCB));
            if (fromPlaceField.getText().length() > 0) {
                getTripSheetPart().setFromplace(fromPlaceField.getText());
            }
            getTripSheetPart().setTositeId(getSelectedCbItem(toSiteCB));
            if (toPlaceField.getText().length() > 0) {
                getTripSheetPart().setToplace(toPlaceField.getText());
            }
            getTripSheetPart().setIsempty(isEmptyCB.isSelected() ? 1 : 0);
            getTripSheetPart().setKilimeters((Integer) kilometersSP.getValue());
            getTripSheetPart().setLoaded1Id(getSelectedCbItem(loaded1CB));
            if (loaded1Field.getText().length() > 0) {
                getTripSheetPart().setLoaded1(loaded1Field.getText());
            }
            getTripSheetPart().setLoaded2Id(getSelectedCbItem(loaded2CB));
            if (loaded2Field.getText().length() > 0) {
                getTripSheetPart().setLoaded2(loaded2Field.getText());
            }
            java.util.Date dt = (java.util.Date) dateSP.getValue();
            getTripSheetPart().setPartdate(new java.sql.Date(dt.getTime()));
            dt = (Date) timeStartSP.getValue();
            getTripSheetPart().setTimestart(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
            dt = (Date) timeEndSP.getValue();
            getTripSheetPart().setTimeend(new java.sql.Timestamp(dt.getTime() + TimeZone.getDefault().getOffset(dt.getTime())));
            getTripSheetPart().setAssistantId(getSelectedCbItem(assistantCB));

            return saveDbRecord(getTripSheetPart(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                tripSheetPart = (Xtripsheetpart) XlendWorks.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xPart
         */
        public Xtripsheetpart getTripSheetPart() {
            return tripSheetPart;
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
        for (ComboItem ci : XlendWorks.loadAllLowbeds()) {
            lowbedCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
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
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xtripsheetpart.class,
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
                if (d.getTripSheetPart() != null) {
                    XlendWorks.getExchanger().deleteObject(d.getTripSheetPart());
                }
            }
        }
        return ok;
    }
}
