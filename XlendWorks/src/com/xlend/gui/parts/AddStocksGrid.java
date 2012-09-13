package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.XaddstocksWithTrigger;
import com.xlend.orm.Xparts;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class AddStocksGrid extends MovePartsGrid {
    
    public AddStocksGrid(IMessageSender exchanger, int part_id, EditXpartPanel xpartsPane) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ADDSTOCKS.replaceAll("#", "" + (EditXaddStockPanel.partID = part_id)),
                null, xpartsPane);
    }
    
    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        XaddstocksWithTrigger addstock =
                                (XaddstocksWithTrigger) exchanger.loadDbObjectOnID(XaddstocksWithTrigger.class, id);
                        new EditXaddStockDialog("Edit Add Stock", addstock);
                        if (EditXaddStockDialog.okPressed) {
                            updateXpartsPanel(EditXaddStockPanel.partID);
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
        if (!XlendWorks.isCurrentAdmin()) {
            return null;
        } else {
            return new AbstractAction("Delete record") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = getSelectedID();
                    if (id > 0) {
                        try {
                            XaddstocksWithTrigger addstock =
                                    (XaddstocksWithTrigger) exchanger.loadDbObjectOnID(XaddstocksWithTrigger.class, id);
                            if (addstock != null && GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record? (Parts will be removed to store)") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(addstock);
                                updateXpartsPanel(EditXbookOutPanel.partID);
                                GeneralFrame.updateGrid(exchanger, getTableView(),
                                        getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
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
}
