package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.order.EditOrderDialog;
import com.xlend.gui.work.OrdersGrid;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditSitePanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField siteNameField;
    private JComboBox orderBox;
    private JComboBox order2Box;
    private JComboBox order3Box;
    private JTextArea descriptionField;
    private JComboBox typeBox;
    private JRadioButton clientSuppliedDieselRB;
    private JRadioButton xlendSuppliedDieselRB;
    private DefaultComboBoxModel orderCBModel;
    private DefaultComboBoxModel order2CBModel;
    private DefaultComboBoxModel order3CBModel;
    private Xorder xorder;
    private AbstractAction ordLookupAction;
    private AbstractAction ordLookupAction2;
    private AbstractAction ordLookupAction3;
    private JScrollPane dsp;
    private DefaultComboBoxModel typeCBmodel;

    public EditSitePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Site Name:", "Purchase Order:",
            "Order 2:", "Order 3:",
            "Client Supplies Diesel – Dry Rate:",
            "Xlend/T&F Supplies Diesel – Wet Rate:",
            "Type of Site:",
            "Description:"
        };
        orderCBModel = new DefaultComboBoxModel();
        order2CBModel = new DefaultComboBoxModel();
        order3CBModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllOrders(DashBoard.getExchanger())) {
            orderCBModel.addElement(ci);
            order2CBModel.addElement(ci);
            order3CBModel.addElement(ci);
        }
        typeCBmodel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadSiteTypes(DashBoard.getExchanger())) {
            typeCBmodel.addElement(ci.getValue());
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            siteNameField = new JTextField(),
            orderBox = new JComboBox(orderCBModel),
            order2Box = new JComboBox(order2CBModel),
            order3Box = new JComboBox(order3CBModel),
            clientSuppliedDieselRB = new JRadioButton(),
            xlendSuppliedDieselRB = new JRadioButton(),
            typeBox = new JComboBox(typeCBmodel),//new String[]{"Work Site", "Additional", "Depot"}),
            dsp = new JScrollPane(descriptionField = new JTextArea(6, 30),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        };
        ButtonGroup group = new ButtonGroup();
        group.add(clientSuppliedDieselRB);
        group.add(xlendSuppliedDieselRB);

        descriptionField.setWrapStyleWord(true);
        descriptionField.setLineWrap(true);
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new BorderLayout(5, 5));

        idField.setEnabled(false);

        JPanel upper = new JPanel(new BorderLayout(5, 5));
        JPanel uplabel = new JPanel(new GridLayout(8, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(8, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        for (int i = 0; i < 8; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }

        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        ided.add(new JPanel());
        ided.add(new JPanel());
        upedit.add(ided);
        upedit.add(siteNameField);
        upedit.add(comboPanelWithLookupBtn(orderBox, ordLookupAction = orderLookup(orderBox)));
        upedit.add(comboPanelWithLookupBtn(order2Box, ordLookupAction2 = orderLookup(order2Box)));
        upedit.add(comboPanelWithLookupBtn(order3Box, ordLookupAction3 = orderLookup(order3Box)));

        upedit.add(clientSuppliedDieselRB);
        upedit.add(xlendSuppliedDieselRB);
        upedit.add(typeBox);

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[6], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(dsp);
        form.add(getTabbedPanel(), BorderLayout.SOUTH);

        add(form);
    }

    private JComponent getTabbedPanel() {
        JTabbedPane tp = new MyJideTabbedPane();
        MachinesOnSitesGrid msg = null;
        Xsite xsite = (Xsite) getDbObject();
        int xsite_id = xsite != null ? xsite.getXsiteId() : 0;
        try {
            msg = new MachinesOnSitesGrid(DashBoard.getExchanger(),
                    Selects.SELECTMACHINESONSITE.replace("#", "" + xsite_id));
            Dimension pref = new Dimension(800, 200);
            msg.setPreferredSize(pref);
            tp.add(msg, "Machines/Trucks on site");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return tp;
    }

    @Override
    public void loadData() {
        Xsite xsite = (Xsite) getDbObject();
        if (xsite != null) {
            idField.setText(xsite.getXsiteId().toString());
            siteNameField.setText(xsite.getName());
            selectComboItem(orderBox, xsite.getXorderId());
            selectComboItem(order2Box, xsite.getXorder2Id() == null ? -1 : xsite.getXorder2Id());
            selectComboItem(order3Box, xsite.getXorder3Id() == null ? -1 : xsite.getXorder3Id());
            descriptionField.setText(xsite.getDescription());
            for (int i=0; i<typeCBmodel.getSize(); i++) {
                String itm = typeCBmodel.getElementAt(i).toString();
                if (itm.startsWith(xsite.getSitetype())) {
                    typeBox.setSelectedIndex(i);
                }
            }
            clientSuppliedDieselRB.setSelected(xsite.getDieselsponsor() != null && xsite.getDieselsponsor() == 1);
            xlendSuppliedDieselRB.setSelected(xsite.getDieselsponsor() == null || xsite.getDieselsponsor() == 0);
        }
    }

    @Override
    public boolean save() throws Exception {
        Xsite xsite = (Xsite) getDbObject();
        boolean isNew = false;
        if (xsite == null) {
            xsite = new Xsite(null);
            xsite.setXsiteId(0);
            isNew = true;
        }
        xsite.setName(siteNameField.getText());
        ComboItem itm = (ComboItem) orderBox.getSelectedItem();
        ComboItem itm2 = (ComboItem) order2Box.getSelectedItem();
        ComboItem itm3 = (ComboItem) order3Box.getSelectedItem();

        xsite.setXorderId(itm.getId() > 0 ? itm.getId() : null);
        xsite.setXorder2Id(itm2.getId() > 0 ? itm2.getId() : null);
        xsite.setXorder3Id(itm3.getId() > 0 ? itm3.getId() : null);
        xsite.setDescription(descriptionField.getText());
        String tp = typeBox.getSelectedItem().toString();
        xsite.setSitetype(tp.substring(0, 1));
        xsite.setDieselsponsor(clientSuppliedDieselRB.isSelected() ? 1 : 0);

        if (itm.getValue().startsWith("--Add new order")) { // add new order
            itm = updateOrderComboBox(orderBox, orderCBModel);
        } else if (itm2.getValue().startsWith("--Add new order")) { // add new order
            itm = updateOrderComboBox(order2Box, order2CBModel);
        } else if (itm3.getValue().startsWith("--Add new order")) { // add new order
            itm = updateOrderComboBox(order3Box, order3CBModel);
        } else {
            try {
                xsite.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(xsite);
                setDbObject(saved);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    private ComboItem updateOrderComboBox(JComboBox ordBox, DefaultComboBoxModel ordCBModel) {
        ComboItem itm = null;
        EditOrderDialog eod = new EditOrderDialog("New Order", null);
        if (eod.okPressed) {
            Xorder xorder = (Xorder) eod.getEditPanel().getDbObject();
            if (xorder != null) {
                itm = new ComboItem(xorder.getXorderId(), "Order Nr:" + xorder.getOrdernumber());
                orderCBModel.addElement(itm);
                order2CBModel.addElement(itm);
                order3CBModel.addElement(itm);
                ordBox.setSelectedItem(itm);
            }
        }
        return itm;
    }

    private AbstractAction orderLookup(final JComboBox orderBox) {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Order Lookup", orderBox,
                            new OrdersGrid(DashBoard.getExchanger(), Selects.SELECT_ORDERS4LOOKUP, false),
                            new String[]{"companyname", //"vatnumber", "regnumber", 
                                "ordernumber"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    protected void setXorder(Xorder xorder) {
        this.xorder = xorder;
        if (xorder != null) {
            Xsite xsite = (Xsite) getDbObject();
            if (xsite == null || xsite.getXorderId().intValue() == xorder.getXorderId().intValue()) {
                ordLookupAction.setEnabled(false);
                selectComboItem(orderBox, xorder.getXorderId());
                orderBox.setEnabled(false);
            } else if (xsite.getXorder2Id().intValue() == xorder.getXorderId().intValue()) {
                ordLookupAction2.setEnabled(false);
                selectComboItem(order2Box, xorder.getXorderId());
                order2Box.setEnabled(false);
            } else if (xsite.getXorder3Id().intValue() == xorder.getXorderId().intValue()) {
                ordLookupAction3.setEnabled(false);
                selectComboItem(order3Box, xorder.getXorderId());
                order3Box.setEnabled(false);
            }
        }
    }
}