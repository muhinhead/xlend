/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xaccounts;
import com.xlend.orm.Xpetty;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class PettyInOutGrid extends GeneralGridPanel {

    public PettyInOutGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_PETTY, null, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Petty Cash") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    EditPettyDialog ed = new EditPettyDialog("New Petty Cash", null);
                    if (EditPettyDialog.okPressed) {
                        Xpetty xp = (Xpetty) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xp.getXpettyId(), getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }  
        };
    }

    @Override
    protected AbstractAction editAction() {
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        return null;
    }
}
