/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.fx.util;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Dialogs;

/**
 *
 * @author Nick Mukhin
 */
public abstract class DbTablePanel extends TableGridPanel {

    public DbTablePanel(String select) {
        super(Utils.getBodyOnSelect(select));
        setActions(getAddAction(), getEditAction(), getDelAction());
    }

    private void delRow() {
        Integer id = null;
        ObservableList<String> row = (ObservableList<String>) getTableView().getSelectionModel().getSelectedItem();
        if (row != null && Dialogs.showConfirmDialog(Utils.getMainStage(),
                "Do you want to delete the row?", "Confirm please", "Attention!",
                Dialogs.DialogOptions.YES_NO) == Dialogs.DialogResponse.YES) {
            id = new Integer(row.get(0));
            if (id != null && id.intValue() > 0) {
                if (deleteDbObject(id)) {
                    getTableView().getItems().remove(row);
                }
            }
        }
    }

    protected abstract boolean deleteDbObject(Integer id);

    protected abstract EventHandler<ActionEvent> getAddAction();

    protected abstract EventHandler<ActionEvent> getEditAction();

    protected EventHandler<ActionEvent> getDelAction() {
        return new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                delRow();
            }
        };
    }
}
