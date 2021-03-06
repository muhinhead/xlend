/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmoveableassets;
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
public class MoveableAssetsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public MoveableAssetsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_MOVEABLE_ASSETS, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditMoveableAssetDialog ed = new EditMoveableAssetDialog("Movable Asset", null);
                    if (EditMoveableAssetDialog.okPressed) {
                        Xmoveableassets xm = (Xmoveableassets) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xm.getXmoveableassetsId(),
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
        return new AbstractAction("Edit Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmoveableassets xm = (Xmoveableassets) exchanger.loadDbObjectOnID(Xmoveableassets.class, id);
                        EditMoveableAssetDialog ed = new EditMoveableAssetDialog("Movable Asset", xm);
                        if (EditMoveableAssetDialog.okPressed) {
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
        return new AbstractAction("Delete Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmoveableassets xm = (Xmoveableassets) exchanger.loadDbObjectOnID(Xmoveableassets.class, id);
                        if (xm != null && GeneralFrame.yesNo("Attention!", "Do you want to delete the record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xm);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                    getSelect(), null, getPageSelector().getSelectedIndex());
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
