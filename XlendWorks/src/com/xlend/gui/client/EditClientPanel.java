package com.xlend.gui.client;

import com.xlend.constants.Selects;
import com.xlend.gui.*;
import com.xlend.orm.Xclient;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditClientPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField clientCodeField;
    private JTextField companyNameField;
    private JTextField contactNameField;
    private JTextArea descriptionField;
    private JTextField telNoField;
    private JTextField vatNoField;
    private JTextField addressField;
    private GeneralGridPanel clientsGrid;
    private GeneralGridPanel ordersGrid;
    private GeneralGridPanel rfqsGrid;

    public EditClientPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Client Code:", "Company Name:", "Contact Name:",
            "Client Description:", "Tel Nr:",
            "Vat Nr:", "Address:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            clientCodeField = new JTextField(),
            companyNameField = new JTextField(),
            contactNameField = new JTextField(),
            new JScrollPane(descriptionField = new JTextArea(6, 30),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
            telNoField = new JTextField(),
            vatNoField = new JTextField(),
            addressField = new JTextField()
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
        JPanel uplabel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(4, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        uplabel.add(new JLabel(labels[0], SwingConstants.RIGHT));
        uplabel.add(new JLabel(labels[1], SwingConstants.RIGHT));
        uplabel.add(new JLabel(labels[2], SwingConstants.RIGHT));
        uplabel.add(new JLabel(labels[3], SwingConstants.RIGHT));

        JPanel ided = new JPanel(new GridLayout(1, 5, 5, 5));
        ided.add(idField);
        for (int i = 0; i < 4; i++) {
            ided.add(new JPanel());
        }
        upedit.add(ided);

        for (int i = 1; i <= 3; i++) {
            upedit.add(edits[i]);
        }

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[4], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(edits[4]);

        JPanel downper = new JPanel(new BorderLayout(5, 5));
        JPanel downlabel = new JPanel(new GridLayout(3, 1, 5, 5));
        JPanel downedit = new JPanel(new GridLayout(3, 1, 5, 5));
        downper.add(downlabel, BorderLayout.WEST);
        downper.add(downedit, BorderLayout.CENTER);

        for (int i = 5; i <= 7; i++) {
            downlabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
            downedit.add(edits[i]);
        }

        form.add(downper, BorderLayout.SOUTH);

        alignPanelOnWidth(leftpanel, downlabel);
        alignPanelOnWidth(uplabel, downlabel);

        add(form, BorderLayout.NORTH);
        add(getTabbedPanel());
    }

    private JComponent getTabbedPanel() {
        JTabbedPane tp = new MyJideTabbedPane();
        try {
            Xclient xclient = (Xclient) getDbObject();
            int client_id = xclient == null ? 0 : xclient.getXclientId();
            tp.add(clientsGrid = new ClientContractsGrid(XlendWorks.getExchanger(),
                    Selects.SELECT_CONTRACTS4CLIENT.replace("#", "" + client_id)), "Contracts");
            tp.add(rfqsGrid = new ClientQuotationsGrid(XlendWorks.getExchanger(),
                    Selects.SELECT_QUOTATIONS4CLIENTS.replace("#", "" + client_id)), "RFQs");
            tp.add(ordersGrid = new ClientOrdersGrid(XlendWorks.getExchanger(),
                    Selects.SELECT_ORDERS4CLIENT.replace("#", "" + client_id)), "Orders");
            Dimension prefDimension = new Dimension(500, 200);
            clientsGrid.setPreferredSize(prefDimension);
            ordersGrid.setPreferredSize(prefDimension);
            rfqsGrid.setPreferredSize(prefDimension);
        } catch (RemoteException ex) {
            Logger.getLogger(EditClientPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tp;
    }

    @Override
    public void loadData() {
        Xclient xclient = (Xclient) getDbObject();
        if (xclient != null) {
            idField.setText(xclient.getXclientId().toString());
            clientCodeField.setText(xclient.getClientcode());
            companyNameField.setText(xclient.getCompanyname());
            contactNameField.setText(xclient.getContactname());
            descriptionField.setText(xclient.getDescription());
            telNoField.setText(xclient.getPhonenumber());
            vatNoField.setText(xclient.getVatnumber());
            addressField.setText(xclient.getAddress());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xclient xclient = (Xclient) getDbObject();
        boolean isNew = false;
        if (xclient == null) {
            xclient = new Xclient(null);
            xclient.setXclientId(0);
            isNew = true;
        }
        xclient.setClientcode(clientCodeField.getText());
        xclient.setCompanyname(companyNameField.getText());
        xclient.setContactname(contactNameField.getText());
        xclient.setDescription(descriptionField.getText());
        xclient.setPhonenumber(telNoField.getText());
        xclient.setVatnumber(vatNoField.getName());
        xclient.setAddress(addressField.getText());
        try {
            xclient.setNew(isNew);
            DbObject saved = XlendWorks.getExchanger().saveDbObject(xclient);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
