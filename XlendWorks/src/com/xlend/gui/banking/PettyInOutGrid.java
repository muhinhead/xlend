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
import javax.swing.JOptionPane;

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
        return new AbstractAction("Edit Petty Cash") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpetty xt = (Xpetty) exchanger.loadDbObjectOnID(Xpetty.class, id);
                        new EditPettyDialog("Edit Petty Cash", xt);
                        if (EditPettyDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Petty Cash") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpetty xt = (Xpetty) exchanger.loadDbObjectOnID(Xpetty.class, id);
                        if (xt != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xt);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null,
                                    getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }
}
