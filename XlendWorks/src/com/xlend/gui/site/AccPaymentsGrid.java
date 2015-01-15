/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xaccpayment;
import com.xlend.orm.Xsitediary;
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
public class AccPaymentsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public AccPaymentsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_ACCPAYMENTS, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Payment") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditAccPaymentDialog ed = new EditAccPaymentDialog("Add Payment", null);
                    if (EditAccPaymentDialog.okPressed) {
                        Xaccpayment xa = (Xaccpayment) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xa.getXaccpaymentId(),
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
                        Xaccpayment xa = (Xaccpayment) exchanger.loadDbObjectOnID(Xaccpayment.class, id);
                        new EditAccPaymentDialog("Edit Payment", xa);
                        if (EditAccPaymentDialog.okPressed) {
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
        return new AbstractAction("Delete Payment") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xaccpayment xa = (Xaccpayment) exchanger.loadDbObjectOnID(Xaccpayment.class, id);
                    if (xa != null && GeneralFrame.yesNo("Attention!", "Do you want to delete the payment?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xa);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), 
                                getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
