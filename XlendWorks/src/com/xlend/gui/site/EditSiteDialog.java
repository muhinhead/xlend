package com.xlend.gui.site;

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
public class EditSiteDialog extends PopupDialog {

    public static boolean okPressed;
    private Xsite xsite = null;
    private JButton saveButton;
    private AbstractAction saveAction;
    private JButton cancelButton;
    private AbstractAction cancelAction;
    private EditSitePanel siteEditPanel;

    public EditSiteDialog(Frame owner, String title, Object obj) {
        super(owner, title, obj);
    }

    @Override
    protected void fillContent() {
        XlendWorks.setWindowIcon(this, "Xcost.png");
        super.fillContent();
        EditSiteDialog.okPressed = false;
        if (getObject() != null) {
            xsite = (Xsite) getObject();
        }

        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton = new JButton(saveAction = new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (siteEditPanel.save()) {
                        xsite = (Xsite) siteEditPanel.getDbObject();
                        okPressed = true;
                        dispose();
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    WorkFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        }));
        btnPanel.add(cancelButton = new JButton(cancelAction = new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));

        siteEditPanel = new EditSitePanel(xsite);
        getContentPane().add(new JPanel(), BorderLayout.WEST);
        getContentPane().add(new JPanel(), BorderLayout.EAST);
        getContentPane().add(siteEditPanel, BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        getContentPane().add(aroundButton, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(saveButton);

    }

    @Override
    public void freeResources() {
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
    }
}
