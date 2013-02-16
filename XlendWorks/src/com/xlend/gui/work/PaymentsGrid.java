package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.creditor.EditPaymentDialog;
import com.xlend.orm.Xpayment;
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
public class PaymentsGrid extends GeneralGridPanel {

    public PaymentsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_PAYMENTS, getMaxWidths(new int[]{40}), false);
    }

    public PaymentsGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, getMaxWidths(new int[]{40, 70, 70, 100, 100}), true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Payment") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditPaymentDialog ed = new EditPaymentDialog("New Payment", null);
                    if (EditPaymentDialog.okPressed) {
                        Xpayment xpay = (Xpayment) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xpay.getXpaymentId(), getPageSelector().getSelectedIndex());
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
                        Xpayment xpay = (Xpayment) exchanger.loadDbObjectOnID(Xpayment.class, id);
                        new EditPaymentDialog("Edit Payment", xpay);
                        if (EditPaymentDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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
                    Xpayment xpay = (Xpayment) exchanger.loadDbObjectOnID(Xpayment.class, id);
                    if (xpay != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this payment?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xpay);
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
