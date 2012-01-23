package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
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
    private JTextArea descriptionField;
    private JComboBox typeBox;
    private JRadioButton clientSuppliedDieselRB;
    private JRadioButton xlendSuppliedDieselRB;
    private DefaultComboBoxModel orderCBModel;
    private Xorder xorder;
    private AbstractAction ordLookupAction;
    private JScrollPane dsp;

    public EditSitePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Site Name:", "Purchase Order",
            "Client Supplies Diesel – Dry Rate:",
            "Xlend/T&F Supplies Diesel – Wet Rate:",
            "Type of Site:",
            "Description:"
        };
        orderCBModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllOrders(DashBoard.getExchanger())) {
            orderCBModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            siteNameField = new JTextField(),
            orderBox = new JComboBox(orderCBModel),
            clientSuppliedDieselRB = new JRadioButton(),
            xlendSuppliedDieselRB = new JRadioButton(),
            typeBox = new JComboBox(new String[]{"Work Site", "Additional"}),
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
        JPanel uplabel = new JPanel(new GridLayout(6, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(6, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        for (int i = 0; i < 6; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }

        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        ided.add(new JPanel());
        ided.add(new JPanel());
        upedit.add(ided);
        upedit.add(siteNameField);
        upedit.add(comboPanelWithLookupBtn(orderBox, ordLookupAction = orderLookup()));

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
        JTabbedPane tp = new JTabbedPane();
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
            descriptionField.setText(xsite.getDescription());
            typeBox.setSelectedIndex(xsite.getSitetype().equals("W") ? 0 : 1);
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
        xsite.setXorderId(itm.getId() > 0 ? itm.getId() : null);
        xsite.setDescription(descriptionField.getText());
        String tp = (String) typeBox.getSelectedItem();
        xsite.setSitetype(tp.substring(0, 1));
        xsite.setDieselsponsor(clientSuppliedDieselRB.isSelected() ? 1 : 0);

        if (itm.getValue().startsWith("--Add new order")) { // add new order
            EditOrderDialog eod = new EditOrderDialog("New Order", null);
            if (eod.okPressed) {
                Xorder xorder = (Xorder) eod.getEditPanel().getDbObject();
                if (xorder != null) {
                    itm = new ComboItem(xorder.getXorderId(), "Order Nr:" + xorder.getOrdernumber());
                    orderCBModel.addElement(itm);
                    orderBox.setSelectedItem(itm);
                }
            }
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

    private AbstractAction orderLookup() {
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
            ordLookupAction.setEnabled(false);
            selectComboItem(orderBox, xorder.getXorderId());
            orderBox.setEnabled(false);
        }
    }
}
