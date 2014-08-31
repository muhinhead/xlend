/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.fx.util;

import javafx.scene.control.Dialogs;
import javafx.scene.control.Dialogs.DialogOptions;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author Nick Mukhin
 */
public class FXpopupDialog {

    public static Dialogs.DialogResponse show(Stage stage, Pane pane, String title, String header, 
            Dialogs.DialogOptions options, Callback callback) 
    {
        return Dialogs.showCustomDialog(stage, pane, header, title, options, callback);
    }
    
    private final Stage stage;
    private final String title;
    private final String header;
    private final Callback callback;
    
    public FXpopupDialog(Stage stage, String title, String header, Callback callback) {
        this.stage = stage;
        this.title = title;
        this.header = header;
        this.callback = callback;
    }
    
    public Dialogs.DialogResponse show(Pane pane) {
        return Dialogs.showCustomDialog(stage, pane, header, title, 
                Dialogs.DialogOptions.OK_CANCEL, callback);
    }
    public Dialogs.DialogResponse show(Pane pane, Dialogs.DialogOptions opts) {
        return Dialogs.showCustomDialog(stage, pane, header, title, 
                opts, callback);
    }
    public Dialogs.DialogResponse show(Pane pane, String header, String title) {
        return Dialogs.showCustomDialog(stage, pane, header, title, 
                Dialogs.DialogOptions.OK_CANCEL, callback);
    }
    public Dialogs.DialogResponse show(Pane pane, String header, String title, Dialogs.DialogOptions options) {
        return Dialogs.showCustomDialog(stage, pane, header, title, 
                options, callback);
    }
}
