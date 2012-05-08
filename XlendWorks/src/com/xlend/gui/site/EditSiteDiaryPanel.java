package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.Xsitediary;
import com.xlend.orm.Xsitediarypart;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.PopupDialog;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDiaryPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private DefaultComboBoxModel managerCbModel;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox managerCB;
    private JComboBox siteCB;
    private JTextField siteForemanField;
    private JTextField siteNumberField;
    private static String[] hdrs = new String[]{
        "Date", "Truck/Machine", "Hrs.worked", "Hrs.standing", "Operator", "Comments"
    };
    private JPanel downGridPanel;
    private ArrayList<RowPanel> childRows;
    private ArrayList<RowPanel> toDelete;
    private JScrollPane scrollPane;
    private JPanel hdrPanel;
    private JCheckBox selectAllCB;

    private class RowPanel extends JPanel {

        private DefaultComboBoxModel machineCbModel, operatorCbModel;
        private Xsitediarypart xpart;
        private JCheckBox markCB;
        private SelectedDateSpinner partDateSP;
        private JComboBox operatorCB;
        private JComboBox machineCB;
        private SelectedNumberSpinner hoursWorkedSP;
        private SelectedNumberSpinner hoursStandingSP;
        private JTextArea commentsField;
        private final JButton editBtn;

        RowPanel(Xsitediarypart xpart) {
            super(new GridLayout(1, 7, 5, 5));
            this.xpart = xpart;
            operatorCbModel = new DefaultComboBoxModel();
            machineCbModel = new DefaultComboBoxModel();
            for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
                operatorCbModel.addElement(ci);
            }
            for (ComboItem ci : XlendWorks.loadAllMachines(DashBoard.getExchanger())) {
                machineCbModel.addElement(ci);
            }
            markCB = new JCheckBox();
            partDateSP = new SelectedDateSpinner();
            partDateSP.setEditor(new JSpinner.DateEditor(partDateSP, "dd/MM/yyyy"));
            operatorCB = new JComboBox(operatorCbModel);
            machineCB = new JComboBox(machineCbModel);
            hoursWorkedSP = new SelectedNumberSpinner(0, 0, 24, 1);
            hoursStandingSP = new SelectedNumberSpinner(0, 0, 24, 1);
            commentsField = new JTextArea(10, 50);
            commentsField.setWrapStyleWord(true);
            commentsField.setLineWrap(true);

            add(markCB);
            add(getBorderPanel(new JComponent[]{partDateSP}));
            add(comboPanelWithLookupBtn(machineCB, new MachineLookupAction(machineCB, null)));
            add(hoursWorkedSP);
            add(hoursStandingSP);
            add(comboPanelWithLookupBtn(operatorCB, new EmployeeLookupAction(operatorCB)));
            add(editBtn = new JButton(new AbstractAction("...") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    editComment();
                }
            }));

            for (JComponent c : new JComponent[]{partDateSP, machineCB, hoursWorkedSP, hoursStandingSP, operatorCB, editBtn}) {
                c.setPreferredSize(new Dimension(partDateSP.getPreferredSize().width, c.getPreferredSize().height));
            }

            load();
        }

        private void editComment() {
            new PopupDialogImpl("Comments", commentsField);
        }

        public void mark(boolean m) {
            markCB.setSelected(m);
        }

        public boolean isMarked() {
            return markCB.isSelected();
        }

        private void load() {
            if (getXpart() != null) {
                if (getXpart().getComments() != null) {
                    commentsField.setText(getXpart().getComments());
                }
                if (getXpart().getPartdate() != null) {
                    partDateSP.setValue(new java.util.Date(getXpart().getPartdate().getTime()));
                }
                if (getXpart().getHrsStanding() != null) {
                    hoursStandingSP.setValue(getXpart().getHrsStanding());
                }
                if (getXpart().getHrsWorked() != null) {
                    hoursWorkedSP.setValue(getXpart().getHrsWorked());
                }
                if (getXpart().getOperatorId() != null) {
                    selectComboItem(operatorCB, getXpart().getOperatorId());
                }
                if (getXpart().getXmachineId() != null) {
                    selectComboItem(machineCB, getXpart().getXmachineId());
                }
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (getXpart() == null) {
                xpart = new Xsitediarypart(null);
                xpart.setXsitediarypartId(0);
                Xsitediary xdiary = (Xsitediary) getDbObject();
                xpart.setXsitediaryId(xdiary.getXsitediaryId());
                isNew = true;
            }
            xpart.setComments(commentsField.getText());
            java.util.Date dt = (java.util.Date) partDateSP.getValue();
            xpart.setPartdate(new java.sql.Date(dt.getTime()));
            xpart.setHrsStanding((Integer) hoursStandingSP.getValue());
            xpart.setHrsWorked((Integer) hoursWorkedSP.getValue());
            xpart.setOperatorId(getSelectedCbItem(operatorCB));
            xpart.setXmachineId(getSelectedCbItem(machineCB));
            return saveDbRecord(getXpart(), isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                xpart = (Xsitediarypart) DashBoard.getExchanger().saveDbObject(dbOb);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
            return false;
        }

        /**
         * @return the xpart
         */
        public Xsitediarypart getXpart() {
            return xpart;
        }

        private class PopupDialogImpl extends PopupDialog {

            public PopupDialogImpl(String comments, JTextArea commentsField) {
                super(null, comments, commentsField);
            }

            @Override
            protected void fillContent() {
                super.fillContent();
                JTextArea ta = (JTextArea) getObject();
                JScrollPane sp;
                getContentPane().add(sp = new JScrollPane(ta,
                        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
                ta.setWrapStyleWord(true);
                ta.setLineWrap(true);
                getContentPane().add(new JPanel(), BorderLayout.WEST);
                getContentPane().add(new JPanel(), BorderLayout.EAST);
                JPanel buttonPanel = new JPanel();
                buttonPanel.add(new JButton(new AbstractAction("Close") {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                }));
                getContentPane().add(buttonPanel, BorderLayout.SOUTH);
            }

            @Override
            public void freeResources() {
//                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
    }

    public EditSiteDiaryPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<RowPanel>();
        toDelete = new ArrayList<RowPanel>();
        String titles[] = new String[]{
            "ID:",
            "Date:",//   "Site",
            "Manager:",
            ""//Site Foreman:",
            //""//"Foreman No:"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        managerCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            managerCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 7),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{dateSP = new SelectedDateSpinner()}),
                new JLabel("Site:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB))
            }),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(managerCB = new JComboBox(managerCbModel), new SiteLookupAction(managerCB)),
                new JLabel("Site Foreman:", SwingConstants.RIGHT), siteForemanField = new JTextField()}),
            getGridPanel(new JComponent[]{getBorderPanel(new JComponent[]{new JButton(getAddLineAction()),
                    new JPanel(), new JButton(getDeleteLineAction())}),
                new JLabel("Foreman No:", SwingConstants.RIGHT), siteNumberField = new JTextField()})
        };

        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        hdrPanel = new JPanel(new GridLayout(1, 7));
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
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Records"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(2 * d.width / 3, 400));
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
        Xsitediary xd = (Xsitediary) getDbObject();
        if (xd != null) {
            idField.setText(xd.getXsitediaryId().toString());
            if (xd.getDiarydate() != null) {
                dateSP.setValue(new java.util.Date(xd.getDiarydate().getTime()));
            }
            if (xd.getManagerId() != null) {
                selectComboItem(managerCB, xd.getManagerId());
            }
            if (xd.getSiteForeman() != null) {
                siteForemanField.setText(xd.getSiteForeman());
            }
            if (xd.getSiteNumber() != null) {
                siteNumberField.setText(xd.getSiteNumber());
            }
            if (xd.getXsiteId() != null) {
                selectComboItem(siteCB, xd.getXsiteId());
            }
            try {
                DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xsitediarypart.class,
                        "xsitediary_id=" + xd.getXsitediaryId(), "xsitediarypart_id");
                for (DbObject rec : recs) {
                    childRows.add(new RowPanel((Xsitediarypart) rec));
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
        Xsitediary xd = (Xsitediary) getDbObject();
        boolean isNew = false;
        if (xd == null) {
            xd = new Xsitediary(null);
            xd.setXsitediaryId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) dateSP.getValue();
        if (dt != null) {
            xd.setDiarydate(new java.sql.Date(dt.getTime()));
        }
        xd.setManagerId(getSelectedCbItem(managerCB));
        xd.setSiteForeman(siteForemanField.getText());
        xd.setSiteNumber(siteNumberField.getText());
        xd.setXsiteId(getSelectedCbItem(siteCB));
        boolean ok = saveDbRecord(xd, isNew);
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
