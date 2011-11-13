package com.xlend.gui;

import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public abstract class RecordEditPanel extends JPanel {

    private DbObject dbObject;
    protected JPanel lblPanel;
    protected JPanel editPanel;
    protected JPanel upPanel;

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        lblPanel = new JPanel(new GridLayout(labels.length, 1, 5, 5));
        editPanel = new JPanel(new GridLayout(edits.length, 1, 5, 5));
        upPanel = new JPanel(new BorderLayout());
        add(upPanel, BorderLayout.NORTH);
        upPanel.add(lblPanel, BorderLayout.WEST);
        upPanel.add(editPanel, BorderLayout.CENTER);
        upPanel.add(new JPanel(), BorderLayout.EAST);
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

    protected static void alignPanelOnWidth(JPanel one, JPanel two) {
        Dimension a = one.getPreferredSize();
        Dimension b = two.getPreferredSize();
        int width = a.width > b.width ? a.width : b.width;
        one.setPreferredSize(new Dimension(width,a.height));
        two.setPreferredSize(new Dimension(width,b.height));
    }
}
