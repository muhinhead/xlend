package com.xlend.gui.contract;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.gui.work.OrdersGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField contractRefField;
    private JTextArea descriptionField;
    private JComboBox clientRefBox;
    private DefaultComboBoxModel cbModel;
    private JButton loadButton;
    private JScrollPane descrScroll;
    private Xclient xclint;
    private AbstractAction clientLookupAction;

    public EditContractPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Contract Ref:", "Client:", "Description:"
        };
        cbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            cbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            contractRefField = new JTextField(),
            clientRefBox = new JComboBox(cbModel),
            descrScroll = new JScrollPane(descriptionField = new JTextArea(5, 55),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
        };
        descriptionField.setWrapStyleWord(true);
        descriptionField.setLineWrap(true);
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new BorderLayout(5, 5));

        idField.setEnabled(false);

        JPanel upper = new JPanel(new BorderLayout(5, 5));
        JPanel uplabel = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(3, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);
        for (int i = 0; i < 3; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }
        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        for (int i = 0; i < 5; i++) {
            ided.add(new JPanel());
        }
        upedit.add(ided);
        upedit.add(edits[1]);
        upedit.add(comboPanelWithLookupBtn(clientRefBox, clientLookupAction = clientRefLookup()));

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[3], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setTopComponent(edits[3]);
        sp.setBottomComponent(getDetailsPanel());
        form.add(sp);

        alignPanelOnWidth(leftpanel, uplabel);

        add(form);

    }

    private void showClientLookup() {
        try {
            LookupDialog ld = new LookupDialog("Client Lookup", clientRefBox,
                    new ClientsGrid(DashBoard.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP, false),
                    new String[]{"clientcode", "companyname"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }

    @Override
    public void loadData() {
        Xcontract xcontract = (Xcontract) getDbObject();
        if (xcontract != null) {
            idField.setText(xcontract.getXcontractId().toString());
            contractRefField.setText(xcontract.getContractref());
            selectComboItem(clientRefBox, xcontract.getXclientId());
            descriptionField.setText(xcontract.getDescription());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xcontract xcontract = (Xcontract) getDbObject();
        boolean isNew = false;
        if (xcontract == null) {
            xcontract = new Xcontract(null);
            xcontract.setXcontractId(0);
            isNew = true;
        }
        xcontract.setContractref(contractRefField.getText());
        xcontract.setDescription(descriptionField.getText());
        ComboItem itm = (ComboItem) clientRefBox.getSelectedItem();
        if (itm.getValue().startsWith("--")) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    cbModel.addElement(ci);
                    clientRefBox.setSelectedItem(ci);
                }
            }
        } else {
            try {
                xcontract.setXclientId(itm.getId());
                xcontract.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(xcontract);
                setDbObject(saved);
                pagesdPanel.saveNewPages(((Xcontract) saved).getXcontractId());
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;

    }

    private JTabbedPane getDetailsPanel() {
        JTabbedPane tp = new JTabbedPane();
        OrdersGrid ordGrid = null;
        Xcontract xcontract = (Xcontract) getDbObject();
        int contract_id = xcontract == null ? 0 : xcontract.getXcontractId();
        try {
            pagesdPanel = new ContractPagesPanel(DashBoard.getExchanger(), contract_id);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        JScrollPane sp;
        tp.add(sp = new JScrollPane(pagesdPanel), "Attached documents");
        sp.setPreferredSize(new Dimension(descrScroll.getPreferredSize().width, 150));
        try {
            ordGrid = new OrdersGrid(DashBoard.getExchanger(),
                    Selects.SELECT_ORDERS4CONTRACTS.replace("#", "" + contract_id),false);
            tp.add(ordGrid, "Orders");
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        return tp;
    }

    private AbstractAction clientRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                showClientLookup();
            }
        };
    }

    void setXclient(Xclient xclient) {
        this.xclint = xclient;
        if (xclint != null) {
            clientLookupAction.setEnabled(false);
            selectComboItem(clientRefBox, xclient.getXclientId());
            clientRefBox.setEnabled(false);
        }
    }
}
