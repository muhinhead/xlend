/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.gui.XlendWorks;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class PenaltyListDialog extends PopupDialog {

    public PenaltyListDialog(Frame owner, Integer xemployeeID) {
        super(owner, "Penalties", xemployeeID);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        Integer xemployeeID = (Integer) getObject();
        try {
            getContentPane().add(new PenaltiesGrid(XlendWorks.getExchanger(), xemployeeID), BorderLayout.CENTER);
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
        JPanel downPanel = new JPanel();
        downPanel.add(new JButton(new AbstractAction("Ok") {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }));
        getContentPane().add(downPanel, BorderLayout.SOUTH);
    }

}
