package com.xlend.fx.util;

import com.xlend.orm.dbobject.DbObject;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author Nick Mukhin
 */
public abstract class RecEditPanel extends BorderPane {

    private DbObject dbObject;
    protected Node[] edits;
    protected Label[] labels;
//    protected GridPane lblPanel;
    protected GridPane editPanel;
    protected BorderPane upPanel;
    protected final DbObject[] params;
    protected BorderPane rightUpperPanel;

    public RecEditPanel(DbObject dbObject) {
        super();
        this.dbObject = dbObject;
        this.params = null;
        fillContent();
        loadData();
    }

    protected static Label[] createLabelsArray(String[] titles) {
        Label[] lblArray = new Label[titles.length];
        int n = 0;
        for (String t : titles) {
            lblArray[n++] = new Label(t);
        }
        return lblArray;
    }

    protected void organizePanels(int editsLen) {
//        lblPanel = new GridPane();
        editPanel = new GridPane();
        upPanel = new BorderPane();
        setTop(upPanel);
//        upPanel.setLeft(lblPanel);
        upPanel.setCenter(editPanel);
        upPanel.setRight(rightUpperPanel = getRightUpperPanel());
    }

    protected static void organizePanels(GridPane gPanel, Label[] labels, Node[] edits) {
        for (int i = 0; i < edits.length && i < labels.length; i++) {
            gPanel.add(labels[i], 0, i);
            gPanel.add(edits[i], 1, i, 3, 1);
        }
    }
    
    protected void organizePanels(String[] titles, Node[] edits) {
        organizePanels(edits.length);
        labels = createLabelsArray(titles);
        organizePanels(editPanel,labels,edits);
    }

    private BorderPane getRightUpperPanel() {
        return new BorderPane();
    }

//    public static Node getGridPanel(Node component, int ceils) {
//        GridPane pane = new GridPane();
//        pane.add(component, 0, 0);
//        for (int i = 1; i < ceils; i++) {
//            pane.add(new Pane(), 1, 0);
//        }
//        return pane;
//    }
//
//    public static Node getGridPanel(Node[] components) {
//        GridPane pane = new GridPane();
//        int i = 0;
//        for (Node n : components) {
//            pane.add(n, i++, 0);
//        }
//        return pane;
//    }
//
//    public static Node getGridPanel(Node[] components, int ceils) {
//        GridPane pane = new GridPane();
//        for (int i = 0; i < ceils; i++) {
//            if (i < components.length) {
//                pane.add(components[i], i, 0);
//            } else {
//                pane.add(new Pane(), i, 0);
//            }
//        }
//        return pane;
//    }

    protected boolean saveDbRecord(DbObject dbOb, boolean isNew) {
        try {
            dbOb.setNew(isNew);
            setDbObject(Utils.getExchanger().saveDbObject(dbOb));
            return true;
        } catch (Exception ex) {
            Utils.logAndShowMessage(ex);
        }
        return false;
    }

    public DbObject getDbObject() {
        return dbObject;
    }

    public void setDbObject(DbObject dbObject) {
        this.dbObject = dbObject;
    }

    protected abstract void fillContent();

    public abstract void loadData();

    public abstract boolean save() throws Exception;
}
