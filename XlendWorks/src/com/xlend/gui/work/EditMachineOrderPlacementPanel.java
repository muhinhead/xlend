package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import static com.xlend.gui.RecordEditPanel.getBorderPanel;
import static com.xlend.gui.RecordEditPanel.getGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.MachineLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xbattery;
import com.xlend.orm.Xmachineorder;
import com.xlend.orm.Xmachineorderitm;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditMachineOrderPlacementPanel extends RecordEditPanel {

    private JTextField idField;
    private DefaultComboBoxModel personCbModel;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox personCB;
    private EmployeeLookupAction personLookupAction;
    private JComboBox siteCB;
    private SiteLookupAction siteLookupAction;
    private JTextField siteAddressField;
    private SelectedDateSpinner issueDateSP;
    private DefaultComboBoxModel clientCbModel;
    private JComboBox clientCB;
    private ClientLookupAction clientLookupAction;
    private SelectedNumberSpinner distanceSiteCP;
    private SelectedDateSpinner requiredDateSP;
    private DefaultComboBoxModel orderCbModel;
    private JComboBox orderCB;
    private OrdersLookupAction orderLookupAction;
    private JTextField frmRequestingPlanField;
    private JTextField frmContactDetailsField;
    private JPanel downGridPanel;
    private JComponent hdrPanel;
    private JCheckBox markAllCB;
    private ArrayList<ItemPanel> childRows;
    private ArrayList<ItemPanel> toDelete;

    private class ItemPanel extends JPanel {

        private Xmachineorderitm item;
        private JCheckBox markCB;
        private JComboBox machineCB;
        private JLabel machTypeField;
        private JComboBox operatorCB;

        ItemPanel(Xmachineorderitm itm) {
            super(new BorderLayout());
            this.item = itm;
            add(markCB = new JCheckBox(), BorderLayout.WEST);
            add(getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(machineCB = new JComboBox(XlendWorks.loadAllMachines()), new MachineLookupAction(machineCB, null)),
                machTypeField = new JLabel(""),
                comboPanelWithLookupBtn(operatorCB = new JComboBox(XlendWorks.loadAllEmployees("1=1")), new EmployeeLookupAction(operatorCB))
            }));
            machTypeField.setBorder(BorderFactory.createEtchedBorder());
            machineCB.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent ae) {
//                     Integer machineID = getSelectedCbItem(machineCB);
                    machTypeField.setText(XlendWorks.getMachineType1(getSelectedCbItem(machineCB)));
                }
            });
            load();
        }

        private void load() {
            if (getItem() != null) {
                selectComboItem(machineCB, item.getXmachineId());
                machTypeField.setText(XlendWorks.getMachineType1(item.getXmachineId()));
                selectComboItem(operatorCB, item.getXemployeeId());
            }
        }

        public boolean save() throws Exception {
            boolean isNew = false;
            if (item == null) {
                isNew = true;
                item = new Xmachineorderitm(null);
                item.setXmachineorderitmId(0);
            }
            if (getDbObject() != null && getDbObject() instanceof Xmachineorder) {
                Xmachineorder mo = (Xmachineorder) getDbObject();
                item.setXmachineorderId(mo.getXmachineorderId());
            }
            Integer operatorID = getSelectedCbItem(operatorCB);
            Integer machineID = getSelectedCbItem(machineCB);
            item.setXemployeeId(operatorID);
            item.setXmachineId(machineID);

            return saveDbRecord(item, isNew);
        }

        private boolean saveDbRecord(DbObject dbOb, boolean isNew) {
            try {
                dbOb.setNew(isNew);
                item = (Xmachineorderitm) XlendWorks.getExchanger().saveDbObject(dbOb);
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
        public Xmachineorderitm getItem() {
            return item;
        }
    }

    public EditMachineOrderPlacementPanel(Xmachineorder xmachineorder) {
        super(xmachineorder);
    }

    @Override
    protected void fillContent() {
        childRows = new ArrayList<ItemPanel>();
        toDelete = new ArrayList<ItemPanel>();
        String titles[] = new String[]{
            "ID:",
            "Person Requesting Plant:", //"Site:", "Site Address:"
            "Date of Issue:", // "Client:", "Distance to Site:"
            "Date Required:", // "Order Number:", "Distance to Site:"
            "", // "", "Foreman Contact Details:"
            ""
        };
        personCbModel = new DefaultComboBoxModel(XlendWorks.loadAllEmployees().toArray());
        siteCbModel = new DefaultComboBoxModel(XlendWorks.loadSites("1=1"));
        clientCbModel = new DefaultComboBoxModel(XlendWorks.loadAllClients());
        orderCbModel = new DefaultComboBoxModel(XlendWorks.loadAllOrdersWOadd());
        JLabel clientLbl;
        JLabel orderNoLbl;
        JLabel siteLbl;
        JLabel frmRequestingPlanLbl;
        JLabel frmContactDetLbl;
        JLabel distanceToSiteLbl;
//        JLabel siteAddressLbl;
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField(8)}),
            getGridPanel(new JComponent[]{
                comboPanelWithLookupBtn(personCB = new JComboBox(personCbModel), personLookupAction = new EmployeeLookupAction(personCB)),
                getBorderPanel(new JComponent[]{
                    siteLbl = new JLabel("Site:", SwingConstants.RIGHT),
                    comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), siteLookupAction = new SiteLookupAction(siteCB))
                }),
                getBorderPanel(new JComponent[]{
                    new JLabel("Site Address:", SwingConstants.RIGHT),
                    siteAddressField = new JTextField()
                })
            }),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{issueDateSP = new SelectedDateSpinner()}),
                getBorderPanel(new JComponent[]{
                    clientLbl = new JLabel("Client:", SwingConstants.RIGHT),
                    comboPanelWithLookupBtn(clientCB = new JComboBox(clientCbModel), clientLookupAction = new ClientLookupAction(clientCB))
                }),
                getBorderPanel(new JComponent[]{
                    new JPanel(),
                    distanceToSiteLbl = new JLabel("Distance to Site:", SwingConstants.RIGHT),
                    distanceSiteCP = new SelectedNumberSpinner(0, 0, 100000, 1)
                })
            }),
            getGridPanel(new JComponent[]{
                getBorderPanel(new JComponent[]{requiredDateSP = new SelectedDateSpinner()}),
                getBorderPanel(new JComponent[]{
                    orderNoLbl = new JLabel("Order No:", SwingConstants.RIGHT),
                    comboPanelWithLookupBtn(orderCB = new JComboBox(orderCbModel), orderLookupAction = new OrdersLookupAction(orderCB))
                }),
                getBorderPanel(new JComponent[]{
                    frmRequestingPlanLbl = new JLabel("Foreman Requesting Plant:", SwingConstants.RIGHT),
                    frmRequestingPlanField = new JTextField()
                })
            }),
            getGridPanel(new JComponent[]{
                new JPanel(), new JPanel(),
                getBorderPanel(new JComponent[]{
                    frmContactDetLbl = new JLabel("Foreman Contract Details:", SwingConstants.RIGHT),
                    frmContactDetailsField = new JTextField()
                })
            }),
            getBorderPanel(new JComponent[]{new JPanel(), new JPanel(), createItmBtnPanel()})
        };
        issueDateSP.setEditor(new JSpinner.DateEditor(issueDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(issueDateSP);
        requiredDateSP.setEditor(new JSpinner.DateEditor(requiredDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(requiredDateSP);

        clientLbl.setPreferredSize(orderNoLbl.getPreferredSize());
        siteLbl.setPreferredSize(orderNoLbl.getPreferredSize());

        distanceToSiteLbl.setPreferredSize(frmRequestingPlanLbl.getPreferredSize());
        frmContactDetLbl.setPreferredSize(frmRequestingPlanLbl.getPreferredSize());
        organizePanels(titles, edits, null);

        JPanel downShellPanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane;
        downGridPanel = new JPanel();
        downShellPanel.add(downGridPanel, BorderLayout.NORTH);
        downGridPanel.setLayout(new BoxLayout(downGridPanel, BoxLayout.Y_AXIS));
        JLabel h1,h2,h3;
        hdrPanel = getBorderPanel(new JComponent[]{
            markAllCB = new JCheckBox(),
            getGridPanel(new JComponent[]{
                h1 = new JLabel("Plant to Site", SwingConstants.CENTER),
                h2 = new JLabel("Type of Plant", SwingConstants.CENTER),
                h3 = new JLabel("Operator", SwingConstants.CENTER)
            })
        });
        h1.setFont(h1.getFont().deriveFont(Font.BOLD, 12));
        h2.setFont(h2.getFont().deriveFont(Font.BOLD, 12));
        h3.setFont(h3.getFont().deriveFont(Font.BOLD, 12));
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
        scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Items Bought"));
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        scrollPane.setPreferredSize(new Dimension(d.width * 3 / 5, 400));
        //add(getDownPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void loadData() {
        Xmachineorder mo = (Xmachineorder) getDbObject();
        if (mo != null) {
            idField.setText(mo.getXmachineorderId().toString());
            selectComboItem(personCB, mo.getXemployeeId());
            selectComboItem(siteCB, mo.getXsiteId());
            selectComboItem(clientCB, mo.getXclientId());
            selectComboItem(orderCB, mo.getXorderId());
            siteAddressField.setText(mo.getSiteAddress());
            if (mo.getIssueDate() != null) {
                issueDateSP.setValue(new java.util.Date(mo.getIssueDate().getTime()));
            }
            if (mo.getRequireDate() != null) {
                requiredDateSP.setValue(new java.util.Date(mo.getRequireDate().getTime()));
            }
            if (mo.getDistance2site() != null) {
                distanceSiteCP.setValue(mo.getDistance2site());
            }
            frmRequestingPlanField.setText(mo.getForemanReqPlant());
            frmContactDetailsField.setText(mo.getForemanContact());
            try {
                DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xmachineorderitm.class,
                        "xmachineorder_id=" + mo.getXmachineorderId(), "xmachineorderitm_id");
                for (DbObject rec : recs) {
                    Xmachineorderitm itm = (Xmachineorderitm) rec;
                    childRows.add(new ItemPanel(itm));
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
        boolean isNew = false;
        Xmachineorder mo = (Xmachineorder) getDbObject();
        if (mo == null) {
            mo = new Xmachineorder(null);
            mo.setXmachineorderId(0);
            isNew = true;
        }
        java.util.Date dt = (java.util.Date) issueDateSP.getValue();
        if (dt != null) {
            mo.setIssueDate(new java.sql.Date(dt.getTime()));
        }
        dt = (java.util.Date) requiredDateSP.getValue();
        if (dt != null) {
            mo.setRequireDate(new java.sql.Date(dt.getTime()));
        }
        mo.setXemployeeId(getSelectedCbItem(personCB));
        mo.setXsiteId(getSelectedCbItem(siteCB));
        mo.setXclientId(getSelectedCbItem(clientCB));
        mo.setXorderId(getSelectedCbItem(orderCB));
        mo.setSiteAddress(siteAddressField.getText());
        mo.setForemanContact(frmContactDetailsField.getText());
        mo.setForemanReqPlant(frmRequestingPlanField.getText());
        mo.setDistance2site((Integer) distanceSiteCP.getValue());

        boolean ok = saveDbRecord(mo, isNew);
        if (ok) {
            for (ItemPanel p : childRows) {
                if (!p.save()) {
                    return false;
                }
            }
            for (ItemPanel d : toDelete) {
                if (d.getItem() != null) {
                    XlendWorks.getExchanger().deleteObject(d.getItem());
                }
            }
        }
        return ok;
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
    }

    private JPanel createItmBtnPanel() {
        JPanel itemBtnPanel = new JPanel(new GridLayout(1, 2));
        JButton addBtn;
        itemBtnPanel.add(addBtn = new JButton(new AbstractAction("+") {
            @Override
            public void actionPerformed(ActionEvent e) {
                childRows.add(new ItemPanel(null));
                redrawRows();
            }
        }));
        addBtn.setToolTipText("Add Item");
        JButton delBtn;
        itemBtnPanel.add(delBtn = new JButton(new AbstractAction("x") {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isMarked = false;
                for (ItemPanel p : childRows) {
                    if (p.isMarked()) {
                        toDelete.add(p);
                        isMarked = true;
                    }
                }
                if (toDelete.size() > 0) {
                    for (ItemPanel p : toDelete) {
                        childRows.remove(p);
                    }
                    redrawRows();
                } else if (!isMarked) {
                    JOptionPane.showMessageDialog(null, "Check line(s) to delete first",
                            "Attention!", JOptionPane.WARNING_MESSAGE);
                }
            }
        }));
        delBtn.setToolTipText("Delete Item(s)");
        return itemBtnPanel;
    }

//    private JComponent getDownPanel() {
//        JComponent comp = getGridPanel(new JComponent[]{
//            new JPanel(),
//            new JLabel("Invoice Vat.Incl.Total (R):", SwingConstants.RIGHT),
//            vatInclTotalLBL = new JLabel("", SwingConstants.RIGHT),
//            new JLabel("Invoice Vat.Excl.Total (R):", SwingConstants.RIGHT),
//            vatExclTotalLBL = new JLabel("", SwingConstants.RIGHT),
//            new JPanel()
//        });
//        vatInclTotalLBL.setBorder(BorderFactory.createLoweredBevelBorder());
//        vatExclTotalLBL.setBorder(BorderFactory.createLoweredBevelBorder());
//        return comp;
//    }
    @Override
    public void freeResources() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
