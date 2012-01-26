package com.xlend.gui;

import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Nick Mukhin
 */
public abstract class RecordEditPanel extends JPanel {

    protected static JLabel[] createLabelsArray(String[] titles) {
        JLabel[] lblArray = new JLabel[titles.length];
        int n = 0;
        for (String t : titles) {
            lblArray[n++] = new JLabel(t, SwingConstants.RIGHT);
        }
        return lblArray;
    }
    private DbObject dbObject;
    protected JPanel lblPanel;
    protected JPanel editPanel;
    protected JPanel upPanel;
    protected PagesPanel pagesdPanel;
    protected JComponent[] edits;
    protected JLabel[] labels;
//    protected ArrayList<JComponent[]> componentRows = new ArrayList<JComponent[]>();

    protected void organizePanels(int labelLength, int editsLen) {
        setLayout(new BorderLayout());
        lblPanel = new JPanel(new GridLayout(labelLength, 1, 5, 5));
        editPanel = new JPanel(new GridLayout(editsLen, 1, 5, 5));
        upPanel = new JPanel(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        upPanel.add(lblPanel, BorderLayout.WEST);
        upPanel.add(editPanel, BorderLayout.CENTER);
        upPanel.add(getRightUpperPanel(), BorderLayout.EAST);
    }

    protected void organizePanels(String[] titles, JComponent[] edits, HashSet<JComponent> except) {
        int diff = (except == null ? 0 : except.size());
        organizePanels(titles.length - diff, edits.length - diff);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length; i++) {
            if (except == null || !except.contains(edits[i])) {
                lblPanel.add(labels[i]);
            }
        }
        for (int i = 0; i < edits.length; i++) {
            if (except == null || !except.contains(edits[i])) {
                editPanel.add(edits[i]);
            }
        }
    }

    protected JPanel comboPanelWithLookupBtn(JComboBox cb, AbstractAction lookupButtonAction) {
        JPanel comboBoxPanel = new JPanel(new BorderLayout());
        comboBoxPanel.add(cb);
        if (lookupButtonAction != null) {
            comboBoxPanel.add(new JButton(lookupButtonAction), BorderLayout.EAST);
        }
        return comboBoxPanel;
    }

    protected JComponent getRightUpperPanel() {
        return new JPanel();
    }

    protected static class EmptyValueException extends Exception {

        public EmptyValueException(String msg) {
            super(msg);
        }
    }

    public RecordEditPanel(DbObject dbObject) {
        super(new BorderLayout());
        this.dbObject = dbObject;
        fillContent();
        loadData();
    }

    protected abstract void fillContent();

    public abstract void loadData();

    public abstract boolean save() throws Exception;

    /**
     * @return the dbObject
     */
    public DbObject getDbObject() {
        return dbObject;
    }

    /**
     * @param dbObject the dbObject to set
     */
    public void setDbObject(DbObject dbObject) {
        this.dbObject = dbObject;
    }

    protected String notEmpty(JTextComponent fld, String name) throws Exception {
        if (fld.getText().trim().length() == 0) {
            fld.requestFocus();
            throw new EmptyValueException("Enter value for " + name);
        }
        return fld.getText();
    }

    protected static void alignPanelOnWidth(JComponent one, JComponent two) {
        Dimension a = one.getPreferredSize();
        Dimension b = two.getPreferredSize();
        int width = a.width > b.width ? a.width : b.width;
        one.setPreferredSize(new Dimension(width, a.height));
        two.setPreferredSize(new Dimension(width, b.height));
    }

    protected static void selectComboItem(JComboBox cb, Integer id) {
        for (int i = 0; id != null && i < cb.getItemCount(); i++) {
            ComboItem itm = (ComboItem) cb.getItemAt(i);
            if (itm.getId() == id) {
                cb.setSelectedIndex(i);
                return;
            }
        }
    }

    protected static MaskFormatter createFormatter(String s) {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter(s);
        } catch (ParseException ex) {
            XlendWorks.log(ex);
        }
        return formatter;
    }

    protected JComponent getGridPanel(JComponent comp, int ceils) {
        JPanel ans = new JPanel(new GridLayout(1, ceils));
        ans.add(comp);
        for (int i = 1; i < ceils; i++) {
            ans.add(new JPanel());
        }
        return ans;
    }

    protected JComponent getGridPanel(JComponent[] comps) {
        JPanel ans = new JPanel(new GridLayout(1, comps.length));
        for (int i = 0; i < comps.length; i++) {
            ans.add(comps[i]);
        }
        return ans;
    }

    protected JComponent getGridPanel(JComponent[] comps, int ceils) {
        ceils = (comps.length > ceils ? comps.length : ceils);
        JPanel ans = new JPanel(new GridLayout(1, ceils));
        for (int i = 0; i < ceils; i++) {
            if (i < comps.length) {
                ans.add(comps[i]);
            } else {
                ans.add(new JPanel());
            }
        }
        return ans;
    }

    protected Integer getSelectedCbItem(JComboBox cb) {
        ComboItem ci = (ComboItem) cb.getSelectedItem();
        return ci == null ? null : ci.getId();
    }

    protected boolean saveDbRecord(DbObject dbOb, boolean isNew) {
        try {
            dbOb.setNew(isNew);
            setDbObject(DashBoard.getExchanger().saveDbObject(dbOb));
            return true;
        } catch (Exception ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }
}
