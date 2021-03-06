/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.quota.EditQuotaDialog;
import com.xlend.orm.Xquotation;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class QuotationsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public QuotationsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_QUOTATIONS, maxWidths, false);
    }

    public QuotationsGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Quotation") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditQuotaDialog ed = new EditQuotaDialog("New Quotation", null);
                    if (EditQuotaDialog.okPressed) {
                        Xquotation xq = (Xquotation) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xq.getXquotationId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xquotation xq = (Xquotation) exchanger.loadDbObjectOnID(Xquotation.class, id);
                        new EditQuotaDialog("Edit Quotation", xq);
                        if (EditQuotaDialog.okPressed) {
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
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xquotation xq = (Xquotation) exchanger.loadDbObjectOnID(Xquotation.class, id);
                    if (xq != null && GeneralFrame.yesNo("Attention!", "Do you want to delete contract  ["
                            + xq.getRfcnumber() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xq);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
