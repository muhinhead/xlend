package com.xlend.gui.site;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SwingUtilities;

/**
 *
 * @author Nick Mukhin
 */
public class DeEstablishMachineDate extends PopupDialog {

    public static boolean okPressed = false;
    private JButton okBtn;
    private JButton cancelBtn;
    private AbstractAction okAction;
    private AbstractAction cancelAction;

    public DeEstablishMachineDate(String title, JSpinner dtSp) {
        super(null, title, dtSp);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        okPressed = false;
        JSpinner dateSp = (JSpinner) getObject();
        JLabel lbl = new JLabel("  De-establishment date:", SwingUtilities.RIGHT);
        JPanel centerJPanel = new JPanel(new BorderLayout(10, 10));
        centerJPanel.add(lbl, BorderLayout.WEST);
        JPanel datePanel = new JPanel(new BorderLayout());
        datePanel.add(dateSp, BorderLayout.WEST);
        centerJPanel.add(datePanel, BorderLayout.CENTER);
        centerJPanel.add(new JPanel(), BorderLayout.EAST);
        JPanel btnPanel = new JPanel(new FlowLayout());
//        JPanel rghtBtnPanel = new JPanel(new FlowLayout());
//        btnPanel.add(rghtBtnPanel, BorderLayout.EAST);
        getContentPane().add(centerJPanel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
        okBtn = new JButton(okAction = new AbstractAction("Ok") {
            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = true;
                dispose();
            }
        });
        cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        });
        btnPanel.add(okBtn);
        btnPanel.add(cancelBtn);
        getRootPane().setDefaultButton(okBtn);
        setResizable(false);
    }

    @Override
    public void freeResources() {
        okBtn.removeActionListener(okAction);
        okAction = null;
        cancelBtn.removeActionListener(cancelAction);
        cancelAction = null;
    }
}
