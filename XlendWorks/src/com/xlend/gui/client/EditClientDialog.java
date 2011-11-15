package com.xlend.gui.client;

import com.xlend.gui.EditRecordDialog;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.EditSitePanel;
import com.xlend.orm.Xclient;
import com.xlend.util.PopupDialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;

/**
 *
 * @author Nick Mukhin
 */
public class EditClientDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditClientDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditClientPanel((Xclient) getObject()));
    }

    @Override
    protected AbstractAction getSaveAction() {
        return new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        okPressed = true;
                        dispose();
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
