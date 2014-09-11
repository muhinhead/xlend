package com.xlend.gui.hr;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.ScaledButton;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xessential;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.PopupDialog;
import com.xlend.util.SelectedNumberSpinner;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 *
 * @author Nick
 */
class EssentialsPanel extends RecordEditPanel {

    private class GetPaymentAmtDialog extends PopupDialog {

        private JButton okBtn;
        private JButton cancelBtn;
        private AbstractAction okAction;
        private AbstractAction cancelAction;

        public GetPaymentAmtDialog(SelectedNumberSpinner amtSpinner) {
            super(null, "Enter amount redressed", amtSpinner);
        }

        @Override
        protected void fillContent() {
            super.fillContent();
            SelectedNumberSpinner amtSpinner = (SelectedNumberSpinner) getObject();
            add(RecordEditPanel.getBorderPanel(new JComponent[]{
                new JLabel("    Enter amount:"), amtSpinner, new JPanel()
            }));
            amtSpinner.setPreferredSize(new Dimension(50, amtSpinner.getPreferredSize().height));
            JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            btnPanel.add(okBtn = new JButton(okAction = new AbstractAction("OK") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dispose();
                }
            }));
            btnPanel.add(cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    SelectedNumberSpinner amtSpinner = (SelectedNumberSpinner) getObject();
                    amtSpinner.setValue(new Double(0.0));
                    dispose();
                }
            }));
            add(btnPanel, BorderLayout.SOUTH);
            getRootPane().setDefaultButton(okBtn);
        }

        @Override
        public void freeResources() {
            okBtn.setAction(null);
            cancelBtn.setAction(null);
            okAction = null;
            cancelAction = null;
        }
    }

    private static Integer driverID = null;

    /**
     * @return the driverID
     */
    public static Integer getDriverID() {
        return driverID;
    }

    /**
     * @param aDriverID the driverID to set
     */
    public static void setDriverID(Integer aDriverID) {
        driverID = aDriverID;
    }

    private DefaultComboBoxModel driverCbModel;
    private ScaledButton driverBtn;
    private JComboBox driverCB;
    private EssentialsGrid essGrid;
    private DbTableView table;
    private ScaledButton b1;
    private ScaledButton b2;
    private ScaledButton b3;
    private List<DbObject> addedRows = new ArrayList<DbObject>();
    private ScaledButton b4;
    private ScaledButton b5;

    public EssentialsPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        driverCbModel = new DefaultComboBoxModel();
        driverCbModel.addElement(new ComboItem(0, "--select driver--"));
        for (ComboItem ci : XlendWorks.loadAllEmployees()) {
            driverCbModel.addElement(ci);
        }
        Dimension d = new Dimension(100, 70);
        setLayout(new BorderLayout());
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel upperFlowPanel = new JPanel(new FlowLayout());
        upperFlowPanel.add(driverBtn = new ScaledButton("user.png", "", false, d));
        upperFlowPanel.add(driverCB = new JComboBox(driverCbModel));
        driverBtn.setToolTipText("Select driver here");
        driverBtn.addActionListener(new EmployeeLookupAction(driverCB, null));
        leftPanel.add(upperFlowPanel, BorderLayout.NORTH);
        JPanel rightPanel = new JPanel(new FlowLayout());//new GridLayout(4, 1, 10, 10));
        d = new Dimension(30, 30);
        add(leftPanel, BorderLayout.WEST);
        upperFlowPanel.add(rightPanel);

        if (getDbObject() == null) {
            rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Issue"));
            rightPanel.add(b1 = new ScaledButton("jack.jpg", null, false, d));
            rightPanel.add(b2 = new ScaledButton("spare_wheel.jpg", null, false, d));
            rightPanel.add(b3 = new ScaledButton("wheel_spanner.jpg", null, false, d));
            b1.setEnabled(false);
            b2.setEnabled(false);
            b3.setEnabled(false);

            b1.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addEssential("Jack");
                }
            });
            b2.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addEssential("Spare Wheel");
                }
            });
            b3.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addEssential("Wheel Spanner");
                }
            });
        } else {
            rightPanel.add(b4 = new ScaledButton("return.png", "Return", false, d));
            rightPanel.add(b5 = new ScaledButton("pay.png", "Pay", false, d));
            rightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Return"));
            b4.addActionListener(returnEssential());
            b5.addActionListener(redressEssential());
            b4.setEnabled(false);
            b5.setEnabled(false);
        }
        driverCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer empID = getSelectedCbItem(driverCB);
                if (empID != null && empID.intValue() > 0) {
                    try {
                        Xemployee emp = (Xemployee) XlendWorks.getExchanger().loadDbObjectOnID(
                                Xemployee.class, empID.intValue());
                        byte[] imageData = (byte[]) emp.getPhoto();
                        String lbl = "";//emp.getClockNum() + " " + emp.getFirstName();
                        if (imageData != null) {
                            driverBtn.setImage(imageData, lbl);
                        } else {
                            driverBtn.setImage("user.png", lbl);
                        }
                        essGrid.reloadForDriver(emp.getXemployeeId().intValue());
                        updateList(null);
                        if (b1 != null) {
                            b1.setEnabled(true);
                            b2.setEnabled(true);
                            b3.setEnabled(true);
                        } else if (b4 != null) {
                            boolean isThere = (essGrid.getTableView().getRowCount()>0);
                            b4.setEnabled(isThere);
                            b5.setEnabled(isThere);
                        }

                    } catch (RemoteException re) {
                        XlendWorks.logAndShowMessage(re);
                    }
                } else {
                    if (b1 != null) {
                        b1.setEnabled(false);
                        b2.setEnabled(false);
                        b3.setEnabled(false);
                    } else if (b4 != null) {
                        b4.setEnabled(false);
                        b5.setEnabled(false);
                    }
                    driverBtn.setImage("user.png", "");
                }
            }
        });
        try {
            add(essGrid = new EssentialsGrid(XlendWorks.getExchanger(), 0), BorderLayout.SOUTH);
        } catch (RemoteException re) {
            XlendWorks.logAndShowMessage(re);
        }
        essGrid.setPreferredSize(new Dimension(700, 300));

    }

    private AbstractAction returnEssential() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = essGrid.getSelectedID();
                if (id > 0) {
                    try {
                        Xessential xe = (Xessential) XlendWorks.getExchanger()
                                .loadDbObjectOnID(Xessential.class, id);
                        if (xe != null) {
                            java.sql.Date dt = new java.sql.Date(new java.util.Date().getTime());
                            xe.setReturnDate(dt);
                            xe.setReceivedBy(XlendWorks.getCurrentUser().getPK_ID());
                            if ((xe = (Xessential) XlendWorks.getExchanger().saveDbObject(xe)) != null) {
                                updateList(xe.getPK_ID());
                            }
                        }
                    } catch (Exception ex) {
                        XlendWorks.log(ex);
                    }
                }
            }
        };
    }

    private AbstractAction redressEssential() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = essGrid.getSelectedID();
                if (id > 0) {
                    try {
                        SelectedNumberSpinner sp = new SelectedNumberSpinner(0.0, 0.0, Double.MAX_VALUE, 1.0);
                        new GetPaymentAmtDialog(sp);
                        Double payment = (Double) sp.getValue();
                        if (payment >= 0.01) {
                            Xessential xe = (Xessential) XlendWorks.getExchanger()
                                    .loadDbObjectOnID(Xessential.class, id);
                            if (xe != null) {
                                java.sql.Date dt = new java.sql.Date(new java.util.Date().getTime());
                                xe.setRedressDate(dt);
                                xe.setRedressAmt(payment);
                                xe.setReceivedBy(XlendWorks.getCurrentUser().getPK_ID());
                                if ((xe = (Xessential) XlendWorks.getExchanger().saveDbObject(xe)) != null) {
                                    updateList(xe.getPK_ID());
                                }
                            }
                        }
                    } catch (Exception ex) {
                        XlendWorks.log(ex);
                    }
                }
            }
        };
    }

    private void addEssential(String ess) {
        try {
            Xessential xe = new Xessential(null);
            xe.setNew(true);
            xe.setXessentialId(0);
            xe.setEssential(ess);
            xe.setDriverId(getSelectedCbItem(driverCB));
            xe.setIssueDate(new java.sql.Date(new java.util.Date().getTime()));
            xe.setIssuedBy(XlendWorks.getCurrentUser().getPK_ID());
            if ((xe = (Xessential) XlendWorks.getExchanger().saveDbObject(xe)) != null) {
                updateList(xe.getPK_ID());
                addedRows.add(xe);
            } else {
            }
        } catch (Exception ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }

    private void updateList(Integer id) {
        try {
            GeneralFrame.updateGrid(XlendWorks.getExchanger(), essGrid.getTableView(),
                    essGrid.getTableDoc(), essGrid.getSelect(), id, essGrid.getPageSelector().getSelectedIndex());
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
    }

    @Override
    public void loadData() {
        //TODO:
        if (driverID != null) {
            selectComboItem(driverCB, driverID);
        }
    }

    @Override
    public boolean save() throws Exception {
        EssentialsPanel.setDriverID(null);
        return true;
    }

    @Override
    public void freeResources() {

    }

    void removeAddedRows() {
        for (DbObject db : addedRows) {
            try {
                XlendWorks.getExchanger().deleteObject(db);
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
    }

}
