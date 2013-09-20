/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.admin.PettyCategoryGrid;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class PettyCatergoryLookupAction extends AbstractAction {

    private final JComboBox pettyCategoryCB;

    public PettyCatergoryLookupAction(JComboBox pettyCategoryCB) {
        super("...");
        this.pettyCategoryCB = pettyCategoryCB;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Petty Category Lookup", pettyCategoryCB,
                    new PettyCategoryGrid(XlendWorks.getExchanger()), new String[]{"category_name"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
