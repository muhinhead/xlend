package com.xlend.gui.contract;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xcontractpage;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractPagePanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField pageNumField;
    private JTextArea descriptionField;

    public EditContractPagePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{"Id:", "Ord.No:", "Description:"};
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            pageNumField = new JTextField(),
            descriptionField = new JTextArea(3, 30)
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
        upedit.add(pageNumField);

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[2], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(edits[2]);

        alignPanelOnWidth(leftpanel, uplabel);

        add(form);
    }

    @Override
    public void loadData() {
        Xcontractpage contrpage = (Xcontractpage) getDbObject();
        if (contrpage.getXcontractpageId() != null) {
            idField.setText(contrpage.getXcontractId().toString());
            pageNumField.setText(contrpage.getPagenum().toString());
            descriptionField.setText(contrpage.getDescription());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xcontractpage contrpage = (Xcontractpage) getDbObject();
        boolean isNew = false;
        if (contrpage.getXcontractpageId() == null) {
//            contrpage = new Xcontractpage(null);
            contrpage.setXcontractpageId(0);
            isNew = true;
        }
        contrpage.setPagenum(Integer.parseInt(pageNumField.getText()));
        contrpage.setDescription(descriptionField.getText());
        try {
            contrpage.setNew(isNew);
            DbObject saved = DashBoard.getExchanger().saveDbObject(contrpage);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
