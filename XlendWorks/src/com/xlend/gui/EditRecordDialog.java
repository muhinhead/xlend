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

    protected JButton applyButton;
    private AbstractAction applyAction;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog(String title, Object obj) {
        super(null, title, obj);
    }

    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
        setOkPressed(false);
        XlendWorks.setWindowIcon(this, "Xcost.png");
        this.editPanel = editPanel;;
        JPanel btnPanel = new JPanel();
        btnPanel.add(applyButton = new JButton(applyAction = getApplyAction()));
        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));

        applyButton.setToolTipText("Apply changes to database");
        saveButton.setToolTipText("Save changes and close dialog");
        cancelButton.setToolTipText("Discard changes and clode dialog");
        
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
        applyButton.removeActionListener(applyAction);
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
        applyAction = null;
    }

    protected AbstractAction getApplyAction() {
        return new AbstractAction("Apply") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        getEditPanel().loadData();
//                        setOkPressed(true);
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    protected AbstractAction getSaveAction() {
        return new AbstractAction("Save") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        setOkPressed(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    protected void setOkPressed(boolean b) {
    }

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
