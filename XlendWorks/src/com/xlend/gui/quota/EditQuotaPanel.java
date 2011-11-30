/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.quota;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.PagesPanel;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xquotation;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
class EditQuotaPanel extends RecordEditPanel {

    private DefaultComboBoxModel cbModel;
    private JTextField idField;
    private JTextField rfcNumField;
    private JComboBox clientRefBox;
    private Xclient xclint;
    private AbstractAction clientLookupAction;

    public EditQuotaPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:", "RFC Nr:", "Client:"
        };
        cbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            cbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            rfcNumField = new JTextField(),
            clientRefBox = new JComboBox(cbModel)
        };
        idField.setEditable(false);
        organizePanels(labels, edits);
    }

    @Override
    protected void organizePanels(String[] labels, JComponent[] edits) {
        super.organizePanels(labels, edits);
        for (String lbl : labels) {
            lblPanel.add(new JLabel(lbl, SwingConstants.RIGHT));
        }
        for (int i = 0; i < 2; i++) {
            JPanel halfPanel = new JPanel(new GridLayout(1, 3));
            halfPanel.add(edits[i]);
            halfPanel.add(new JPanel());
            halfPanel.add(new JPanel());
            editPanel.add(halfPanel);
        }
        editPanel.add(comboPanelWithLookupBtn(clientRefBox, clientLookupAction = clientRefLookup()));
        try {
            add(getTabbedPanel(), BorderLayout.CENTER);
        } catch (RemoteException ex) {
            Logger.getLogger(EditQuotaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void loadData() {
        Xquotation xq = (Xquotation) getDbObject();
        if (xq != null) {
            idField.setText(xq.getXquotationId().toString());
            rfcNumField.setText(xq.getRfcnumber());
            selectComboItem(clientRefBox, xq.getXclientId());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xquotation xq = (Xquotation) getDbObject();
        boolean isNew = false;
        if (xq == null) {
            xq = new Xquotation(null);
            xq.setXquotationId(0);
            isNew = true;
        }
        xq.setRfcnumber(rfcNumField.getText());
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
                xq.setXclientId(itm.getId());
                xq.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(xq);
                setDbObject(saved);
                pagesdPanel.saveNewPages(((Xquotation) saved).getXquotationId());
                return true;
            } catch (Exception ex) {
                GeneralFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;
    }

    private AbstractAction clientRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Client Lookup", clientRefBox,
                            new ClientsGrid(DashBoard.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP, false),
                            new String[]{"clientcode", "companyname"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private Component getTabbedPanel() throws RemoteException {
        JTabbedPane tp = new JTabbedPane();

        try {
            Xquotation q = (Xquotation) getDbObject();
            int xquotation_id = (q == null ? 0 : q.getXquotationId());
            pagesdPanel = new QuotationPagePanel(DashBoard.getExchanger(), xquotation_id);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        JScrollPane sp;
        tp.add(sp = new JScrollPane(pagesdPanel), "Attached documents");
        sp.setPreferredSize(new Dimension(500, 150));

        return tp;
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
