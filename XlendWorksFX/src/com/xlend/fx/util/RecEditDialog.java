package com.xlend.fx.util;

import com.xlend.orm.dbobject.DbObject;
import javafx.scene.control.Dialogs;
import javafx.util.Callback;

/**
 *
 * @author Nick Mukhin
 */
public abstract class RecEditDialog extends FXpopupDialog {
    private RecEditPanel panel;
    
    public RecEditDialog(String title, String header, Callback callback) {
        super(Utils.getMainStage(),title,header,callback);
    }
   
    public abstract void fillContent(DbObject obj);
    
    public void fillContent(RecEditPanel panel) {
        this.panel = panel;
    }
    
    public Dialogs.DialogResponse show() {  //should return OK if data saved, CANCEL if Cancel pressed, 
                                            //YES in case of exception or Save() failed
        Dialogs.DialogResponse result = super.show(panel);
        if (result==Dialogs.DialogResponse.OK) {
            try {
                if (!panel.save()) {
                    result = Dialogs.DialogResponse.YES; //something went wrong!
                }
            } catch (Exception ex) {
                Utils.logAndShowMessage(ex);
                result = Dialogs.DialogResponse.YES;
            }
        }
        return result;
    }
}
