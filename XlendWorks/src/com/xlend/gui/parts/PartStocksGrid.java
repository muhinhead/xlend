package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.orm.Xparts;
import com.xlend.orm.Xpartstocks;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class PartStocksGrid extends GeneralGridPanel {

    private int xpartID;

    public PartStocksGrid(IMessageSender exchanger, int xpart_id) throws RemoteException {
        super(exchanger, Selects.SELECT_PART_STOCKS.replaceAll("#", "" + xpart_id), null, false);
        EditPartStockPanel.xpartID = xpartID = xpart_id;
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Stock") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (xpartID <= 0) {
                        if (GeneralFrame.yesNo("Attention!", "Do you want to save first the part record?") == JOptionPane.YES_OPTION) {
                            if (EditXpartPanel.getInstance()!=null) {
                                try {
                                    EditXpartPanel.getInstance().save();
                                    EditXpartPanel.getInstance().loadData();
                                    Xparts xpart = (Xparts) EditXpartPanel.getInstance().getDbObject();
                                    selectPartID(xpart.getXpartsId());
                                } catch (Exception ex) {
                                    XlendWorks.logAndShowMessage(ex);
                                }
                            }
                        }
                    }
                    if (xpartID > 0) {
                        EditPartStockDialog ed = new EditPartStockDialog("Add Stock", null);
                        if (EditPartStockDialog.okPressed) {
                            Xpartstocks partstocks = (Xpartstocks) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                    partstocks.getXpartsId(), getPageSelector().getSelectedIndex());
                        }
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
        return new AbstractAction("Edit Reserve") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpartstocks partstocks = (Xpartstocks) exchanger.loadDbObjectOnID(Xpartstocks.class, id);
                        new EditPartStockDialog("Edit Reserve", partstocks);
                        if (EditPartStockDialog.okPressed) {
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
        return new AbstractAction("Delete Reserve") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpartstocks partstocks = (Xpartstocks) exchanger.loadDbObjectOnID(Xpartstocks.class, id);
                        if (partstocks != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(partstocks);
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

    public void selectPartID(int xpart_id) {
        EditPartStockPanel.xpartID = xpartID = xpart_id;
        String newSelect = Selects.SELECT_PART_STOCKS.replaceAll("#", "" + xpart_id);
        DbTableDocument td = (DbTableDocument) getController().getDocument();
        setSelect(newSelect);
        td.setSelectStatement(newSelect);
        try {
            int id = getSelectedID();
            td.setBody(exchanger.getTableBody(newSelect, 0, PAGESIZE));
            updatePageCounter(newSelect);
            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
            XlendWorks.log(ex);
        }
    }

    /**
     * @return the xpartID
     */
    public int getXpartID() {
        return xpartID;
    }
}
