/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.orm.Xorder;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
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
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
class EditOrderPanel extends RecordEditPanel {

    private DefaultComboBoxModel clienCBModel;
    private DefaultComboBoxModel contractCBModel;
    private DefaultComboBoxModel rfcRefCBModel;
    private JTextField idField;
    private JComboBox contractRefBox;
    private JComboBox rfcRefBox;
    private JComboBox clientRefBox;
    private JTextField vatNumber;
    private JTextField regNumber;
    private JTextField ordNumber;
    private JSpinner ordDate;
    private JTextField contactNameFiels;
    private JTextField contactPhoneFiels;
    private JTextField contactFaxFiels;
    private JTextField deliveryAddress;
    private JTextField invoiceAddress;

    public EditOrderPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Client:", "Contract Ref:", "RFC:",
            "Vat Nr:",//"Registration No.:",
            "PO Number:", //"PO Date:", 
            "Contact Person:", "Contact Phone:", "Contact Fax:",
            "Delivery address:", "Invoice Address:"
        };
        clienCBModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllClients(DashBoard.getExchanger())) {
            clienCBModel.addElement(ci);
        }
        contractCBModel = new DefaultComboBoxModel();
        rfcRefCBModel = new DefaultComboBoxModel();
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            clientRefBox = new JComboBox(clienCBModel),
            contractRefBox = new JComboBox(contractCBModel),
            rfcRefBox = new JComboBox(rfcRefCBModel),
            vatNumber = new JTextField(),
            regNumber = new JTextField(),
            ordNumber = new JTextField(),
            ordDate = new JSpinner(new SpinnerDateModel()),
            contactNameFiels = new JTextField(),
            contactPhoneFiels = new JTextField(),
            contactFaxFiels = new JTextField(),
            deliveryAddress = new JTextField(30),
            invoiceAddress = new JTextField(30)
        };
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

        upedit.add(comboPanelWithLookupBtn(clientRefBox, clientRefLookup()));
        upedit.add(comboPanelWithLookupBtn(contractRefBox, contractRefLookup()));
        upedit.add(comboPanelWithLookupBtn(rfcRefBox, rfcRefRefLookup()));
        
        JPanel vatregPanel = new JPanel(new GridLayout(1, 3));
        vatregPanel.add(edits[4]);
        vatregPanel.add(new JLabel(" Registration Nr:",SwingConstants.RIGHT));
        vatregPanel.add(edits[5]);
        upedit.add(vatregPanel);
        
        JPanel poPanel = new JPanel(new GridLayout(1, 3));
        poPanel.add(edits[6]);
        poPanel.add(new JLabel(" PO Date:",SwingConstants.RIGHT));
        poPanel.add(edits[7]);
        upedit.add(poPanel);
        
        for (int i = 8; i < edits.length; i++) {
            upedit.add(edits[i]);
        }

        form.add(upper, BorderLayout.NORTH);
        add(form);
    }

    @Override
    public void loadData() {
        //throw new UnsupportedOperationException("Not supported yet.");
        //TODO: loadData()
    }

    @Override
    public boolean save() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet.");
        //TODO: saveData()
        return false;
    }

    private AbstractAction clientRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LookupDialog ld = new LookupDialog("Client Lookup", clientRefBox,
                            new ClientsGrid(DashBoard.getExchanger(), Selects.SELECT_CLIENTS4LOOKUP),
                            new String[]{"clientcode", "companyname"});
                } catch (RemoteException ex) {
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    private AbstractAction contractRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }

    private AbstractAction rfcRefRefLookup() {
        return new AbstractAction("...") {

            @Override
            public void actionPerformed(ActionEvent e) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
    }
}
