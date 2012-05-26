package com.xlend.gui;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class RefGridDialog extends PopupDialog {

    private JButton closeBtn;
    private AbstractAction closeAction;

    public RefGridDialog(Frame owner, String title, GeneralGridPanel grid) {
        super(owner, title, grid);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        JPanel downBtnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        downBtnPanel.add(closeBtn = new JButton(closeAction = new AbstractAction("Close") {

            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        add(downBtnPanel, BorderLayout.SOUTH);
        add(new JPanel(), BorderLayout.WEST);
        add((GeneralGridPanel) getObject());
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }
}
