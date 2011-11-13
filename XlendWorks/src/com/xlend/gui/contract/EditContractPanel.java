package com.xlend.gui.contract;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.WorkFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField contractRefField;
    private JTextArea descriptionField;
    private JComboBox contractorBox;
    private JTabbedPane detailsTab;
    private JPanel imagePanel;
    private DefaultComboBoxModel cbModel;

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
            contractorBox = new JComboBox(cbModel),
            new JScrollPane(descriptionField = new JTextArea(5, 55),
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
        upedit.add(edits[2]);

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[3], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(edits[3]);

        form.add(detailsTab = getDetailsPanel(), BorderLayout.SOUTH);

        alignPanelOnWidth(leftpanel, uplabel);

        add(form);

    }

    @Override
    public void loadData() {
        Xcontract xcontract = (Xcontract) getDbObject();
        if (xcontract != null) {
            idField.setText(xcontract.getXcontractId().toString());
            contractRefField.setText(xcontract.getContractref());
            selectComboItem(contractorBox, xcontract.getXclientId());
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
        ComboItem itm = (ComboItem) contractorBox.getSelectedItem();
        if (itm.getId() == 0) { // add new client
            EditClientDialog ecd = new EditClientDialog("New Client", null);
            if (EditClientDialog.okPressed) {
                Xclient cl = (Xclient) ecd.getEditPanel().getDbObject();
                if (cl != null) {
                    ComboItem ci = new ComboItem(cl.getXclientId(), cl.getCompanyname());
                    cbModel.addElement(ci);
                    contractorBox.setSelectedItem(ci);
                }
            }
        } else {
            try {
                xcontract.setXclientId(itm.getId());
                xcontract.setNew(isNew);
                DbObject saved = DashBoard.getExchanger().saveDbObject(xcontract);
                setDbObject(saved);
                return true;
            } catch (Exception ex) {
                WorkFrame.errMessageBox("Error:", ex.getMessage());
            }
        }
        return false;

    }

    private JTabbedPane getDetailsPanel() {
        Xcontract xcontract = (Xcontract) getDbObject();
        JTabbedPane tp = new JTabbedPane();
        tp.add(WorkFrame.createGridPanel(DashBoard.getExchanger(),
                Selects.SELECT_FROM_CONTRACTITEMS.replace("#", xcontract == null
                ? "0" : xcontract.getXcontractId().toString()), 
                null, null, null, 
                null),
                "Contract Items");
        return tp;
    }
}
