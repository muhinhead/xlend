package com.xlend.gui;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public abstract class EditRecordDialog extends PopupDialog {

    private JButton saveButton;
    private AbstractAction saveAction;
    private JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog(Frame owner, String title, Object obj) {
        super(owner, title, obj);
    }

    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
        XlendWorks.setWindowIcon(this, "Xcost.png");
        this.editPanel = editPanel;;
        JPanel btnPanel = new JPanel();
        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));
        
        getContentPane().add(new JPanel(), BorderLayout.WEST);
        getContentPane().add(new JPanel(), BorderLayout.EAST);
        getContentPane().add(editPanel, BorderLayout.CENTER);

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

    protected abstract AbstractAction getSaveAction();

    protected AbstractAction getCancelAction() {
        return new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
    }

    /**
     * @return the editPanel
     */
    public RecordEditPanel getEditPanel() {
        return editPanel;
    }
}
