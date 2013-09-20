package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.orm.Xsalary;
import com.xlend.orm.Xsalarylist;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditSalaryListPanel extends RecordEditPanel {

    private JTextField idField;
    private SelectedDateSpinner dateSP;
    private JPanel downGridPanel;
    private ArrayList<SalaryPanel> childRows;
    private ArrayList<SalaryPanel> toDelete;
    private static String[] hdrs = new String[]{
        "Employee No", "Amt.after deduct."
    };
    private JPanel hdrPanel;
    private JCheckBox selectAllCB;
    private JScrollPane scrollPane;

    private class SalaryPanel extends JPanel {

        private DefaultComboBoxModel employeeCbModel;
        private Xsalary xsalary;
        private JComboBox employeeCB;
        private SelectedNumberSpinner amountSP;
        private JCheckBox markCB;
//        private JPanel hdrPanel;

        public SalaryPanel(Xsalary xsalary) {
            super(new GridLayout(1, 2, 5, 5));
            this.xsalary = xsalary;
            employeeCbModel = new DefaultComboBoxModel();
            markCB = new JCheckBox();
            for (ComboItem ci : XlendWorks.loadAllEmployees("management=1 and coalesce(wage_category,1)=1")) {
                employeeCbModel.addElement(ci);
            }
            employeeCB = new JComboBox(employeeCbModel);
            add(getBorderPanel(new JComponent[]{markCB, employeeCB}));//comboPanelWithLookupBtn(employeeCB, new SpecEmployeeLookupAction(employeeCB))}));
            add(getBorderPanel(new JComponent[]{new JPanel(), amountSP = new SelectedNumberSpinner(0.0, 0.0, 9999999.0, 0.01)}));
            load();
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (xsalary != null) {
                if (xsalary.getXemployeeId() != null) {
                    selectComboItem(employeeCB, xsalary.getXemployeeId());
                } else {
                    ComboItem citm = XlendWorks.loadEmployeeExcept(getAddedList());
                    selectComboItem(employeeCB, citm.getId());
                }
                if (xsalary.getAmount() != null) {
                    amountSP.setValue(xsalary.getAmount());
                }
            } else {
                ComboItem citm = XlendWorks.loadEmployeeExcept(getAddedList());
                if (citm != null) {
                    xsalary = new Xsalary(null, 0, 0, citm.getId(), 0.0);
                    selectComboItem(employeeCB, citm.getId());
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (xsalary == null || xsalary.getXsalaryId() == 0) {
                isNew = true;
                xsalary = new Xsalary(null);
                xsalary.setXsalaryId(0);
                Xsalarylist xsalarylist = (Xsalarylist) getDbObject();
                xsalary.setXsalarylistId(xsalarylist.getXsalarylistId());
            }
            xsalary.setAmount((Double) amountSP.getValue());
            xsalary.setXemployeeId(getSelectedCbItem(employeeCB));
            return saveDbRecord(xsalary, isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xsalary = (Xsalary) XlendWorks.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        public Integer getSelectedEmployeeID() {
            ComboItem ci = (ComboItem) employeeCB.getSelectedItem();
            if (ci != null) {
                return ci.getId();
            }
            return null;
        }

        public Double getAmountValue() {
            return (Double) amountSP.getValue();
        }

        public void setAmountValue(Double val) {
            amountSP.setValue(val);
        }

        /**
         * @return the xsalary
         */
        public Xsalary getXsalary() {
            return xsalary;
        }
    }

    public EditSalaryListPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {

        childRows = new ArrayList<SalaryPanel>();
        toDelete = new ArrayList<SalaryPanel>();
        String[] titles = new String[]{
            "ID:", "Date:"
        };
        JComponent edits[] = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            getGridPanel(new JComponent[]{
                dateSP = new SelectedDateSpinner(),
                new JPanel(),
                new JButton(getAddLineAction()),
                new JButton(getAddAllLineAction()),
                new JButton(getDeleteLineAction())
            })
        };
//        totalAmtLbl.setBorder(BorderFactory.createEtchedBorder());

        idField.setEnabled(false);
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        organizePanels(titles, edits, null);
        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 2));
        hdrPanel.add(getBorderPanel(new JComponent[]{selectAllCB = new JCheckBox(), new JLabel(hdrs[0], SwingConstants.CENTER)}));
        hdrPanel.add(new JLabel(hdrs[1], SwingConstants.CENTER));
        selectAllCB.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (SalaryPanel p : childRows) {
                    p.mark(selectAllCB.isSelected());
                }
            }
        });
        downGridPanel.add(hdrPanel);
        add(scrollPane = new JScrollPane(downShellPanel), BorderLayout.CENTER);
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Records"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width / 3, 400));
    }

    private String getAddedList() {
        StringBuffer sb = new StringBuffer("-1");
        for (SalaryPanel p : childRows) {
            if (p.getXsalary() != null) {
                sb.append("," + p.getXsalary().getXemployeeId().toString());
            }
        }
        return sb.toString();
    }

    private void redrawRows() {
        downGridPanel.setVisible(false);
        downGridPanel.removeAll();
        downGridPanel.add(hdrPanel);
        for (SalaryPanel p : childRows) {
            downGridPanel.add(p);
        }
        downGridPanel.repaint();
        downGridPanel.setVisible(true);
    }

    private AbstractAction getAddLineAction() {
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new SalaryPanel(null));
                redrawRows();
            }
        };
    }

    private AbstractAction getAddAllLineAction() {
        return new AbstractAction("Add all") {
            @Override
            public void actionPerformed(ActionEvent e) {
                Hashtable<Integer, Double> amtSet = new Hashtable<Integer, Double>();
                for (SalaryPanel p : childRows) {
                    Integer employeeID = getSelectedCbItem(p.employeeCB);
                    if (employeeID != null) {
                        amtSet.put(employeeID, p.getAmountValue());
                    }
                }

                childRows.clear();
                ComboItem[] emlist = XlendWorks.loadAllEmployees("management=1 and coalesce(wage_category,1)=1");
                for (ComboItem ci : emlist) {
                    SalaryPanel p = new SalaryPanel(null);
                    Integer id = p.getSelectedEmployeeID();
                    Double v = amtSet.get(id);
                    if (v != null) {
                        p.setAmountValue(v);
                    }
                    childRows.add(p);
                }
                redrawRows();
            }
        };
    }

    private AbstractAction getDeleteLineAction() {
        return new AbstractAction("Remove") {
            @Override
            public void actionPerformed(ActionEvent e) {

                for (SalaryPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                    }
                }
                if (toDelete.size() > 0) {
                    for (SalaryPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else {
                    JOptionPane.showMessageDialog(null, "Check employee to remove",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        };
    }

    @Override
    public void loadData() {
        HRFrame.instance.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        Xsalarylist xl = (Xsalarylist) getDbObject();
        if (xl != null) {
            idField.setText(xl.getXsalarylistId().toString());
            if (xl.getPayday() != null) {
                dateSP.setValue(new java.util.Date(xl.getPayday().getTime()));
            }
            try {
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xsalary.class, "xsalarylist_id=" + xl.getXsalarylistId(), "xsalary_id");
                for (DbObject rec : recs) {
                    childRows.add(new SalaryPanel((Xsalary) rec));
                }
                redrawRows();
            } catch (RemoteException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                XlendWorks.log(ex);
            }
        }
        HRFrame.instance.setCursor(Cursor.getDefaultCursor());
    }

    @Override
    public boolean save() throws Exception {
        Xsalarylist xl = (Xsalarylist) getDbObject();
        boolean isNew = false;
        if (xl == null) {
            xl = new Xsalarylist(null);
            xl.setXsalarylistId(0);
        }
        isNew = xl.getXsalarylistId() == 0;
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xl.setPayday(new java.sql.Date(dt.getTime()));
        }
        boolean ok = saveDbRecord(xl, isNew);
        if (ok) {
            for (SalaryPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (SalaryPanel p : toDelete) {
                if (p.getXsalary() != null) {
                    XlendWorks.getExchanger().deleteObject(p.getXsalary());
                }
            }
        }
        return ok;
    }
}
