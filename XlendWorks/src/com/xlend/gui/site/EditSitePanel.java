package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.GeneralFrame;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
    private JTextArea descriptionField;
    private JComboBox typeBox;
    private JCheckBox dieselSponsoredCB;

    public EditSitePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Site Name:", "Description:", "Diesel Sponsored:", "Type of Site:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            siteNameField = new JTextField(),
            new JScrollPane(descriptionField = new JTextArea(6, 30),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
            dieselSponsoredCB = new JCheckBox(),
            typeBox = new JComboBox(new String[]{"Work Site", "Additional"})
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
        JPanel uplabel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(2, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        uplabel.add(new JLabel(labels[0], SwingConstants.RIGHT));
        uplabel.add(new JLabel(labels[1], SwingConstants.RIGHT));

        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        ided.add(new JPanel());
        ided.add(new JPanel());
        upedit.add(ided);
        upedit.add(siteNameField);

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[2], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(edits[2]);

        JPanel downper = new JPanel(new BorderLayout(5, 5));
        JPanel downlabel = new JPanel(new GridLayout(2, 1, 5, 5));
        JPanel downedit = new JPanel(new GridLayout(2, 1, 5, 5));
        downper.add(downlabel, BorderLayout.WEST);
        downper.add(downedit, BorderLayout.CENTER);

        downlabel.add(new JLabel(labels[3], SwingConstants.RIGHT));
        downlabel.add(new JLabel(labels[4], SwingConstants.RIGHT));

        downedit.add(dieselSponsoredCB);
        downedit.add(typeBox);

        form.add(downper, BorderLayout.SOUTH);

        alignPanelOnWidth(uplabel, downlabel);
        alignPanelOnWidth(leftpanel, downlabel);

        add(form);
    }

    @Override
    public void loadData() {
        Xsite xsite = (Xsite) getDbObject();
        if (xsite != null) {
            idField.setText(xsite.getXsiteId().toString());
            siteNameField.setText(xsite.getName());
            descriptionField.setText(xsite.getDescription());
            typeBox.setSelectedIndex(xsite.getSitetype().equals("W") ? 0 : 1);
            dieselSponsoredCB.setSelected(xsite.getDieselsponsor() != null && xsite.getDieselsponsor() == 1);
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
        xsite.setDescription(descriptionField.getText());
        String tp = (String) typeBox.getSelectedItem();
        xsite.setSitetype(tp.substring(0, 1));
        xsite.setDieselsponsor(dieselSponsoredCB.isSelected() ? 1 : 0);
        try {
            xsite.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(xsite);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
