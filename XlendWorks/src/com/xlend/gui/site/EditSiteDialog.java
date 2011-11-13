package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.gui.WorkFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsite;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDialog extends EditRecordDialog {

    public static boolean okPressed;

    public EditSiteDialog(String title, Object obj) {
        super(title, obj);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditSitePanel((Xsite) getObject()));
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
                    WorkFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
