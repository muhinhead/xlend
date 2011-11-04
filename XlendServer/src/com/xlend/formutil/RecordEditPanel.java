/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.formutil;

import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Nick Mukhin
 */
public abstract class RecordEditPanel extends JPanel {

    private DbObject dbObject;

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
        if (fld.getText().isEmpty()) {
            fld.requestFocus();
            throw new EmptyValueException("Enter value for " + name);
        }
        return fld.getText();
    }
}
