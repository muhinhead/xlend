/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.employee;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xemployeepenalty;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class PenaltiesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private final Xemployee emp;

    public PenaltiesGrid(IMessageSender exchanger, Integer xemployeeID) throws RemoteException {
        super(exchanger, Selects.SELECT_PENALTIES.replace("#", xemployeeID.toString()), maxWidths, false);
        EditPenaltyDialog.xemployeeID = getXemployeeID();
        emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, EditPenaltyDialog.xemployeeID);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditPenaltyDialog ed = new EditPenaltyDialog("Add penalty of " + emp.getClockNum(), null);
                    if (EditPenaltyDialog.okPressed) {
                        Xemployeepenalty xp = (Xemployeepenalty) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xp.getXemployeepenaltyId(),
                                getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xemployee emp = (Xemployee) exchanger.loadDbObjectOnID(Xemployee.class, EditPenaltyDialog.xemployeeID);
                        Xemployeepenalty xp = (Xemployeepenalty) exchanger.loadDbObjectOnID(Xemployeepenalty.class, id);
                        EditPenaltyDialog ed = new EditPenaltyDialog("Edit penalty of " + emp.getClockNum(), xp);
                        if (EditPenaltyDialog.okPressed) {
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
        return new AbstractAction("Delete record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xemployeepenalty xp = (Xemployeepenalty) exchanger.loadDbObjectOnID(Xemployeepenalty.class, id);
                        if (xp != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xp);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private Integer getXemployeeID() {
        String where = "from xemployeepenalty where xemployee_id=";
        int p = getSelect().indexOf(where);
        if (p > 0) {
            String sID = getSelect().substring(p + where.length());
            sID = sID.substring(0, sID.indexOf(" ")).trim();
            return new Integer(sID);
        } else {
            return null;
        }
    }
}
