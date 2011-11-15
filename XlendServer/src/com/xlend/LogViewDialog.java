package com.xlend;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Nick Mukhin
 */
public class LogViewDialog extends PopupDialog {

    private AbstractAction closeAction;
    private JButton closeBtn;
    private JScrollPane sp;

    public LogViewDialog() {
        super(null, "Xcost Server Log", null);
        XlendServer.setWindowIcon(this, "Xcost.png");
    }

    protected void fillContent() {
        super.fillContent();
        JTextArea logViewArea = new JTextArea();

        JPanel btnPanel = new JPanel();
        btnPanel.add(closeBtn = new JButton(closeAction = new AbstractAction("Close") {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        getContentPane().add(sp = new JScrollPane(logViewArea));
        sp.setPreferredSize(new Dimension(400,200));
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }
}
