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
import com.xlend.orm.Xpettyitem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class PettyInOutGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
//        maxWidths.put(1, 40);
    }

    public PettyInOutGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_PETTY, maxWidths, false);
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
                        XlendWorks.recalcAllCashBalances();
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
                            XlendWorks.recalcAllCashBalances();
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
                if (id > 0 && EditPettyPanel.haveAdminRights()) {
                    try {
                        Xpetty xt = (Xpetty) exchanger.loadDbObjectOnID(Xpetty.class, id);
                        if (xt != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            DbObject[] itms = exchanger.getDbObjects(Xpettyitem.class, "xpetty_id=" + id, null);
                            for (DbObject itm : itms) {
                                exchanger.deleteObject(itm);
                            }
                            exchanger.deleteObject(xt);
                            XlendWorks.recalcAllCashBalances();
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
