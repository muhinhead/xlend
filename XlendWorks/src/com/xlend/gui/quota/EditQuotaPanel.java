/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.quota;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xquotation;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author nick
 */
class EditQuotaPanel extends EditPanelWithPhoto {

    private DefaultComboBoxModel cbModel;
    private JTextField idField;
    private JTextField rfcNumField;
    private JSpinner receivedSp;
    private JSpinner deadlineSp;
    private JRadioButton respYes;
    private JRadioButton respNo;
    private JSpinner responseDateSp;
    private JComboBox clientRefBox;
    private Xclient xclint;
    private AbstractAction clientLookupAction;
    private JComboBox userRespondedCB;
    private JComboBox respondedByCB;
    private JTextField respCommentsField;
    private AbstractAction showHideRFaction;
    private JTabbedPane tabbedPane;

    public EditQuotaPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        showHideRFaction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                showHideResponseFields();
            }
        };
        String[] titles = new String[]{
            "ID:", "RFQ Nr:",
            "RFQ Date Received:",
            "RFQ Deadline:",
            "Client:",
            "Responded:",
            "Response Date:",
            "User Responded:",
            "Response Sent by:",
            "Comments:"
        };
        labels = createLabelsArray(titles);
        cbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            cbModel.addElement(ci);
        }
        edits = new JComponent[]{
            idField = new JTextField(),
            rfcNumField = new JTextField(),
            receivedSp = new SelectedDateSpinner(),
            deadlineSp = new SelectedDateSpinner(),
            clientRefBox = new JComboBox(cbModel),
            respYes = new JRadioButton("Yes"),
            respNo = new JRadioButton("No", true),
            responseDateSp = new SelectedDateSpinner(),
            userRespondedCB = new JComboBox(XlendWorks.loadAllUsers(DashBoard.getExchanger())),
            respondedByCB = new JComboBox(new Object[]{"Email", "Fax", "Post", "Hand delivery"}),
            respCommentsField = new JTextField()
        };
        receivedSp.setEditor(new JSpinner.DateEditor(receivedSp, "dd/MM/yyyy"));
        deadlineSp.setEditor(new JSpinner.DateEditor(deadlineSp, "dd/MM/yyyy HH:mm"));
        responseDateSp.setEditor(new JSpinner.DateEditor(responseDateSp, "dd/MM/yyyy"));

        Util.addFocusSelectAllAction(receivedSp);
        Util.addFocusSelectAllAction(deadlineSp);
        Util.addFocusSelectAllAction(responseDateSp);

        idField.setEditable(false);
        organizePanels();
    }

    protected void organizePanels() {
        super.organizePanels(6, 6);
        for (int i = 0; i < 2; i++) {
            lblPanel.add(labels[i]);
            JPanel halfPanel = new JPanel(new GridLayout(1, 3));
            halfPanel.add(edits[i]);
            halfPanel.add(labels[i + 2]);
            halfPanel.add(edits[i + 2]);
            editPanel.add(halfPanel);
        }
        lblPanel.add(labels[4]);
        lblPanel.add(labels[5]);
        editPanel.add(comboPanelWithLookupBtn(clientRefBox, clientLookupAction = clientRefLookup()));
        JPanel respPanel = new JPanel(new GridLayout(1, 3));
        JPanel yesNoPanel = new JPanel(new GridLayout(1, 2));
        yesNoPanel.add(respNo);
        yesNoPanel.add(respYes);

        respYes.addActionListener(showHideRFaction);
        respNo.addActionListener(showHideRFaction);

        respPanel.add(yesNoPanel);
        respPanel.add(labels[6]);
        ButtonGroup group = new ButtonGroup();
        group.add(respNo);
        group.add(respYes);
        respPanel.add(responseDateSp);
        editPanel.add(respPanel);

        lblPanel.add(labels[7]);

        JPanel userRespPanel = new JPanel(new GridLayout(1, 3));
        userRespPanel.add(userRespondedCB);
        userRespPanel.add(labels[8]);
        userRespPanel.add(respondedByCB);
        editPanel.add(userRespPanel);

        lblPanel.add(labels[9]);
        editPanel.add(respCommentsField);

        try {
            add(getTabbedPanel(), BorderLayout.CENTER);
        } catch (RemoteException ex) {
            Logger.getLogger(EditQuotaPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected JComponent getRightUpperPanel() {
        return new JPanel();
    }

    private void showHideResponseFields() {
        boolean show = respYes.isSelected();
        responseDateSp.setVisible(show);
        userRespondedCB.setVisible(show);
        respondedByCB.setVisible(show);
        respCommentsField.setVisible(show);
        for (int i = 6; i < 10; i++) {
            labels[i].setVisible(show);
        }
//        if (tabbedPane != null && tabbedPane.getTabComponentAt(1) != null) {
//            tabbedPane.getTabComponentAt(1).setVisible(show);
//        }
    }

    @Override
    public void loadData() {
        Xquotation xq = (Xquotation) getDbObject();
        if (xq != null) {
            idField.setText(xq.getXquotationId().toString());
            rfcNumField.setText(xq.getRfcnumber());
            selectComboItem(clientRefBox, xq.getXclientId());
            if (xq.getReceived() != null) {
                receivedSp.setValue(new java.util.Date(xq.getReceived().getTime()));
            }
            if (xq.getDeadline() != null) {
                deadlineSp.setValue(new java.util.Date(xq.getDeadline().getTime()));
            }
            if (xq.getResponded() != null) {
                responseDateSp.setValue(new java.util.Date(xq.getResponded().getTime()));
            }
            if (xq.getResponderId() != null) {
                selectComboItem(userRespondedCB, xq.getResponderId());
            } else {
                Userprofile curuser = XlendWorks.getCurrentUser();
                selectComboItem(userRespondedCB, curuser.getProfileId());
            }
            if (xq.getResponsedby() != null) {
                respondedByCB.setSelectedItem(xq.getResponsedby());
            }
            if (xq.getResponsecmnt() != null) {
                respCommentsField.setText(xq.getResponsecmnt());
            }
            respYes.setSelected(xq.getResponded() != null);
            imageData = (byte[]) xq.getResponsedoc();
            setImage(imageData);
        }
        showHideResponseFields();
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
                xq.setRfcnumber(rfcNumField.getText());

                java.util.Date dt = (java.util.Date) receivedSp.getValue();
                xq.setReceived(dt != null ? new java.sql.Date(dt.getTime()) : null);

                dt = (java.util.Date) deadlineSp.getValue();
                xq.setDeadline(dt != null ? new java.sql.Date(dt.getTime()) : null);

                if (respYes.isSelected()) {
                    dt = (java.util.Date) responseDateSp.getValue();
                    xq.setResponded(dt != null ? new java.sql.Date(dt.getTime()) : null);

                    itm = (ComboItem) userRespondedCB.getSelectedItem();
                    xq.setResponderId(itm.getId());

                    xq.setResponsedby((String) respondedByCB.getSelectedItem());
                    xq.setResponsedoc(imageData);
                } else {
                    xq.setResponded(null);
                    xq.setResponderId(null);
                    xq.setResponsedby(null);
                    xq.setResponsedoc(null);
                }
                xq.setResponsecmnt(respCommentsField.getText());

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

    private JComponent getTabbedPanel() throws RemoteException {
        tabbedPane = new MyJideTabbedPane();

        try {
            Xquotation q = (Xquotation) getDbObject();
            int xquotation_id = (q == null ? 0 : q.getXquotationId());
            pagesdPanel = new QuotationPagePanel(DashBoard.getExchanger(), xquotation_id);
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
        }
        JScrollPane sp;
        tabbedPane.add(sp = new JScrollPane(pagesdPanel), "Attached documents");
        sp.setPreferredSize(new Dimension(500, 150));
        tabbedPane.add(getPicPanel(), "Response Document");

        return tabbedPane;
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
