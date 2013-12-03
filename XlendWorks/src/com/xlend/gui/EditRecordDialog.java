package com.xlend.gui;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public abstract class EditRecordDialog extends PopupDialog {

//    protected JButton applyButton;
//    private AbstractAction applyAction;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog(String title, Object obj) {
        super(null, title, obj);
        setResizable(true);
//        setUndecorated(false);
    }

    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
//        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setOkPressed(false);
        XlendWorks.setWindowIcon(this, "Xcost.png");
        this.editPanel = editPanel;
        this.editPanel.setOwnerDialog(this);
        JPanel btnPanel = new JPanel();
//        btnPanel.add(applyButton = new JButton(applyAction = getApplyAction()));
        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));

//        applyButton.setToolTipText("Apply changes to database");
        saveButton.setToolTipText("Save changes and close dialog");
        cancelButton.setToolTipText("Discard changes and close dialog");

        JPanel innerPanel = new JPanel(new BorderLayout());

        innerPanel.add(new JPanel(), BorderLayout.WEST);
        innerPanel.add(new JPanel(), BorderLayout.EAST);
        innerPanel.add(editPanel, BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        innerPanel.add(aroundButton, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(innerPanel), BorderLayout.CENTER);
        getRootPane().setDefaultButton(saveButton);
    }

    @Override
    public void freeResources() {
//        applyButton.removeActionListener(applyAction);
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
//        applyAction = null;
        super.freeResources();
    }

//    protected AbstractAction getApplyAction() {
//        return new AbstractAction("Apply") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (getEditPanel().save()) {
//                        getEditPanel().loadData();
//                    }
//                } catch (Exception ex) {
//                    XlendWorks.log(ex);
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
//    }
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
