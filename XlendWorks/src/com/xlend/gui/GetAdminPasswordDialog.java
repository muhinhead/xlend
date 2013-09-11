package com.xlend.gui;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 *
 * @author Nick Mukhin
 */
public class GetAdminPasswordDialog extends PopupDialog {

    private JButton okBtn;
    private JButton cancelBtn;
    private AbstractAction okAction;
    private AbstractAction cancelAction;

    public GetAdminPasswordDialog(JPasswordField pwdFld) {
        super(null, "Enter admin's password", pwdFld);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
//            add(getBorderPanel(new JComponent[]{new JPanel(),JComponent) getObject(),new JPanel()});
        add(RecordEditPanel.getBorderPanel(new JComponent[]{
            new JPanel(), (JComponent) getObject(), new JPanel()
        }));
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(okBtn = new JButton(okAction = new AbstractAction("OK") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
            }
        }));
        btnPanel.add(cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JPasswordField pwdFld = (JPasswordField) getObject();
                pwdFld.setText("");
                dispose();
            }
        }));
        add(btnPanel, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(okBtn);
    }

    @Override
    public void freeResources() {
        okBtn.setAction(null);
        cancelBtn.setAction(null);
        okAction = null;
        cancelAction = null;
    }
}
