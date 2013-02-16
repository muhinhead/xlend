package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbankbalance;
import com.xlend.orm.Xbankbalancepart;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditBalancePanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private JSpinner valueField;
    private static String[] hdrs = new String[]{
        "Account", "Balance"
    };
    private JPanel downGridPanel;
    private ArrayList<RowPanel> childRows;
    private ArrayList<RowPanel> toDelete;
    private JScrollPane scrollPane;
    private JPanel hdrPanel;
    private JCheckBox selectAllCB;

    private class RowPanel extends JPanel {

        private DefaultComboBoxModel accountCbModel;
        private JSpinner totalSP;
        private JCheckBox markCB;
        private JComboBox accountCB;
        private Xbankbalancepart xpart;

        RowPanel(Xbankbalancepart xpart) {
            //super(new GridLayout(1, 3, 5, 5));
            super(new BorderLayout(5, 5));
            this.xpart = xpart;
            accountCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllAccounts(DashBoard.getExchanger())) {
                accountCbModel.addElement(ci);
            }
            add(markCB = new JCheckBox(), BorderLayout.WEST);
            add(comboPanelWithLookupBtn(accountCB = new JComboBox(accountCbModel), new AccountLookupAction(accountCB)));
            add(totalSP = new SelectedNumberSpinner(0.0, 0.0, 999999999.99, .01), BorderLayout.EAST);
            totalSP.addChangeListener(changeAccTotalListener());
            load();
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (getXpart() != null) {
                if (getXpart().getXaccountId() != null) {
                    selectComboItem(accountCB, getXpart().getXaccountId());
                }
                if (getXpart().getTotal() != null) {
                    totalSP.setValue(getXpart().getTotal());
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getXpart() == null) {
                xpart = new Xbankbalancepart(null);
                getXpart().setXbankbalancepartId(0);
                Xbankbalance xb = (Xbankbalance) getDbObject();
                getXpart().setXbankbalanceId(xb.getXbankbalanceId());
                isNew = true;
            }
            getXpart().setXaccountId(getSelectedCbItem(accountCB));
            getXpart().setTotal((Double) totalSP.getValue());
            return saveDbRecord(getXpart(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xpart = (Xbankbalancepart) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        private ChangeListener changeAccTotalListener() {
            return new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    recalc();
                }
            };
        }

        /**
         * @return the xpart
         */
        public Xbankbalancepart getXpart() {
            return xpart;
        }
    }

    public EditBalancePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date & Time:",
            "Total Available:"
        };
        childRows = new ArrayList<RowPanel>();
        toDelete = new ArrayList<RowPanel>();
        JComponent[] edits = new JComponent[]{
            getGridPanel(new JComponent[]{idField = new JTextField(), new JPanel(), new JButton(getAddLineAction())}),
            getGridPanel(new JComponent[]{dateSP = new SelectedDateSpinner(), new JPanel(), new JButton(getDeleteLineAction())}),
            getGridPanel(valueField = new SelectedNumberSpinner(0.0, 0.0, 999999999, 0.01), 3)
        };
        valueField.setFont(valueField.getFont().deriveFont(Font.BOLD, 14));
        valueField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy HH:mm"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 3));
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
            hdrPanel.add(new JLabel(h, h.equals("Balance") ? SwingConstants.RIGHT : SwingConstants.LEFT));
        }
        downGridPanel.add(hdrPanel);
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Records"));
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, 300));
    }

    @Override
    public void loadData() {
        Xbankbalance xb = (Xbankbalance) getDbObject();
        if (xb != null) {
            idField.setText(xb.getXbankbalanceId().toString());
            if (xb.getBalancedate() != null) {
                dateSP.setValue(new java.util.Date(xb.getBalancedate().getTime()));
            }
            if (xb.getTotalvalue() != null) {
                valueField.setValue(xb.getTotalvalue());
            }

            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xbankbalancepart.class,
                        "xbankbalance_id=" + xb.getXbankbalanceId(), "xbankbalancepart_id");
                for (DbObject rec : recs) {
                    childRows.add(new RowPanel((Xbankbalancepart) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }

        }
    }

    private AbstractAction getAddLineAction() {
        return new AbstractAction("Add record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new RowPanel(null));
                redrawRows();
            }
        };
    }

    private AbstractAction getDeleteLineAction() {
        return new AbstractAction("Delete record(s)") {
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

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (RowPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
        recalc();
    }

    private void recalc() {
        Double sum = 0.0;
        for (RowPanel rp : childRows) {
            if (rp.totalSP.getValue() != null) {
                sum += (Double) rp.totalSP.getValue();
            }
        }
        valueField.setValue(sum);
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xbankbalance xb = (Xbankbalance) getDbObject();
        if (xb == null) {
            xb = new Xbankbalance(null);
            xb.setXbankbalanceId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xb.setBalancedate(new java.sql.Timestamp(dt.getTime()));
        }
        xb.setTotalvalue((Double) valueField.getValue());
        boolean ok = saveDbRecord(xb, isNew);
        if (ok) {
            for (RowPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (RowPanel d : toDelete) {
                if (d.getXpart() != null) {
                    DashBoard.getExchanger().deleteObject(d.getXpart());
                }
            }
        }
        return ok;
    }
}
