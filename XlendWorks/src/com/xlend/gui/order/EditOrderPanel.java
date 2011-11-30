/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.gui.work.ContractsGrid;
import com.xlend.gui.work.QuotationsGrid;
import com.xlend.gui.work.SitesGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xorder;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.sql.Date;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
public class EditOrderPanel extends RecordEditPanel {

    private static final String nocontract = "-- No contract yet --";
    private DefaultComboBoxModel clienCBModel;
    private DefaultComboBoxModel contractCBModel;
    private DefaultComboBoxModel rfqRefCBModel;
    private JTextField idField;
    protected JComboBox contractRefBox;
    protected JComboBox clientRefBox;
    protected JComboBox rfcRefBox;
    private JTextField vatNumber;
    private JTextField regNumber;
    private JTextField ordNumber;
    private JSpinner ordDate;
    private JTextField contactName;
    private JTextField contactPhone;
    private JTextField contactFax;
    private JTextField deliveryAddress;
    private JTextField invoiceAddress;
    private Xclient xclint;
    private AbstractAction clientLookupAction;

    public EditOrderPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Client:", "Contract Ref:", "RFQ:",
            "Vat Nr:",//"Registration No.:",
            "PO Number:", //"PO Date:", 
            "Contact Person:",
            "Contact Phone:", //"Contact Fax:",
            "Delivery address:", "Invoice Address:"
        };
        clienCBModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            clienCBModel.addElement(ci);
        }
        contractCBModel = new DefaultComboBoxModel();
        rfqRefCBModel = new DefaultComboBoxModel();

        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            clientRefBox = new JComboBox(clienCBModel),
            contractRefBox = new JComboBox(contractCBModel),
            rfcRefBox = new JComboBox(rfqRefCBModel),
            vatNumber = new JTextField(),
            regNumber = new JTextField(),
            ordNumber = new JTextField(),
            ordDate = new JSpinner(new SpinnerDateModel()),
            contactName = new JTextField(),
            contactPhone = new JTextField(),
            contactFax = new JTextField(),
            deliveryAddress = new JTextField(30),
            invoiceAddress = new JTextField(30)
        };
        clientRefBox.addActionListener(getClientRefChangedAction());
        ordDate.setEditor(new JSpinner.DateEditor(ordDate, "dd/MM/yyyy"));
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new BorderLayout(5, 5));

        idField.setEnabled(false);
        JPanel upper = new JPanel(new BorderLayout(5, 5));
        JPanel uplabel = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        for (int i = 0; i < labels.length; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        for (int i = 0; i < 5; i++) {
            ided.add(new JPanel());
        }
        upedit.add(ided);

        upedit.add(comboPanelWithLookupBtn(clientRefBox, clientLookupAction = clientRefLookup()));
        upedit.add(comboPanelWithLookupBtn(contractRefBox, contractRefLookup()));
        upedit.add(comboPanelWithLookupBtn(rfcRefBox, rfcRefRefLookup()));

        JPanel vatregPanel = new JPanel(new GridLayout(1, 3));
        vatregPanel.add(edits[4]);
        vatregPanel.add(new JLabel(" Registration Nr:", SwingConstants.RIGHT));
        vatregPanel.add(edits[5]);
        upedit.add(vatregPanel);

        JPanel poPanel = new JPanel(new GridLayout(1, 3));
        poPanel.add(edits[6]);
        poPanel.add(new JLabel(" PO Date:", SwingConstants.RIGHT));
        poPanel.add(edits[7]);
        upedit.add(poPanel);

        upedit.add(edits[8]);

        JPanel phoneFaxPanel = new JPanel(new GridLayout(1, 3));
        phoneFaxPanel.add(edits[9]);
        phoneFaxPanel.add(new JLabel(" Contact Fax:", SwingConstants.RIGHT));
        phoneFaxPanel.add(edits[10]);
        upedit.add(phoneFaxPanel);

        upedit.add(edits[11]);
        upedit.add(edits[12]);

        form.add(upper, BorderLayout.NORTH);
        form.add(getTabbedPanel(), BorderLayout.CENTER);
        add(form);
    }

    @Override
    public void loadData() {
        Xorder xorder = (Xorder) getDbObject();
        if (xorder != null) {
            idField.setText(xorder.getXorderId().toString());
            selectComboItem(clientRefBox, xorder.getXclientId());
            syncCombos();
            selectComboItem(contractRefBox, xorder.getXcontractId());
            selectComboItem(rfcRefBox, xorder.getXquotationId());
            vatNumber.setText(xorder.getVatnumber());
            regNumber.setText(xorder.getRegnumber());
            ordNumber.setText(xorder.getOrdernumber());
            Date sqlDt = xorder.getOrderdate();
            ordDate.setValue(new java.util.Date(sqlDt.getTime()));//?
            contactName.setText(xorder.getContactname());
            contactPhone.setText(xorder.getContactphone());
            contactFax.setText(xorder.getContactfax());
            deliveryAddress.setText(xorder.getDeliveryaddress());
            invoiceAddress.setText(xorder.getInvoiceaddress());

        }
    }

    @Override
    public boolean save() throws Exception {
        Xorder xorder = (Xorder) getDbObject();
        boolean isNew = false;
        if (xorder == null) {
            xorder = new Xorder(null);
            xorder.setXorderId(0);
            isNew = true;
        }
        ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
        if (itm.getValue().startsWith("--")) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    clienCBModel.addElement(ci);
                    clientRefBox.setSelectedItem(ci);
                }
            }
        } else {
            try {
                xorder.setXclientId(itm.getId());
                xorder.setNew(isNew);
                itm = (ComboItem) contractRefBox.getSelectedItem();
                xorder.setXcontractId(itm.getId() > 0 ? itm.getId() : null);
                itm = (ComboItem) rfcRefBox.getSelectedItem();
                xorder.setXquotationId(itm.getId() > 0 ? itm.getId() : null);
                xorder.setVatnumber(vatNumber.getText());
                xorder.setRegnumber(regNumber.getText());
                xorder.setOrdernumber(ordNumber.getText());
                java.util.Date dt = (java.util.Date) ordDate.getValue();
                xorder.setOrderdate(new Date(dt.getTime()));
                xorder.setContactname(contactName.getText());
                xorder.setContactfax(contactFax.getText());
                xorder.setDeliveryaddress(deliveryAddress.getText());
                xorder.setInvoiceaddress(invoiceAddress.getText());
                DbObject saved = DashBoard.getExchanger().saveDbObject(xorder);
                setDbObject(saved);
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    protected AbstractAction clientRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (clientRefBox.getSelectedItem() != null) {
                        LookupDialog ld = new LookupDialog("Client Lookup", clientRefBox,
                                new ClientsGrid(DashBoard.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP, false),
                                new String[]{"clientcode", "companyname"});
                    } else {
                        GeneralFrame.errMessageBox("Warning:", "Choose client first");
                    }
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    protected AbstractAction contractRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
                    String select = Selects.SELECT_CONTRACTS4LOOKUP.replace("#", "" + itm.getId());
                    if (contractRefBox.getSelectedItem() != null) {
                        LookupDialog ld = new LookupDialog("Contract Lookup", contractRefBox,
                                new ContractsGrid(DashBoard.getExchanger(), select),
                                new String[]{"contractref", "description"});
                    } else {
                        GeneralFrame.errMessageBox("Warning", "Choose client first");
                    }
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction rfcRefRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
                    String select = Selects.SELECT_QUOTATIONS4LOOKUP.replace("#", "" + itm.getId());
                    if (rfcRefBox.getSelectedItem() != null) {
                        LookupDialog ld = new LookupDialog("Quotation Lookup", rfcRefBox,
                                new QuotationsGrid(DashBoard.getExchanger(), select),
                                new String[]{"rfcnumber"});
                    } else {
                        GeneralFrame.errMessageBox("Warning", "Choose client first");
                    }
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private ActionListener getClientRefChangedAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncCombos();
            }
        };

    }

    protected void syncCombos() {
        ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
        contractCBModel.removeAllElements();
        for (ComboItem ci : XlendWorks.loadAllContracts(DashBoard.getExchanger(), itm.getId())) {
            contractCBModel.addElement(ci);
        }
        rfqRefCBModel.removeAllElements();
        for (ComboItem ci : XlendWorks.loadAllRFQs(DashBoard.getExchanger(), itm.getId())) {
            rfqRefCBModel.addElement(ci);
        }
    }

    private JTabbedPane getTabbedPanel() {
        JTabbedPane tp = new JTabbedPane();
        OrderItemsGrid ordItemGrid = null;
        OrderSitesGrid ordSitesGrid = null;
        Xorder xorder = (Xorder) getDbObject();
        int order_id = xorder == null ? 0 : xorder.getXorderId();
        try {
            ordItemGrid = new OrderItemsGrid(DashBoard.getExchanger(),
                    Selects.SELECTORDERITEMS.replace("#", "" + order_id));
            tp.add(ordItemGrid, "Order Items");
            pagesdPanel = new OrderPagesPanel(DashBoard.getExchanger(), order_id);
            JScrollPane sp;
            tp.add(sp = new JScrollPane(pagesdPanel), "Attached documents");
            ordSitesGrid = new OrderSitesGrid(DashBoard.getExchanger(),
                    Selects.SELECT_ORDERISITES.replace("#", "" + order_id));
            tp.add(ordSitesGrid, "Order Sites");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        Dimension pref = new Dimension(600, 200);
        ordItemGrid.setPreferredSize(pref);
        ordSitesGrid.setPreferredSize(pref);
        return tp;
    }

    public void setXclient(Xclient xclient) {
        this.xclint = xclient;
        if (xclint != null) {
            clientLookupAction.setEnabled(false);
            selectComboItem(clientRefBox, xclient.getXclientId());
            clientRefBox.setEnabled(false);
        }
    }
}
