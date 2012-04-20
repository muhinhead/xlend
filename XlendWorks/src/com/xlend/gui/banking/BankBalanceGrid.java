package com.xlend.gui.banking;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbankbalance;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class BankBalanceGrid extends GeneralGridPanel {

    public BankBalanceGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BANKBALANCE, null, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Balance Record") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditBalanceDialog ed = new EditBalanceDialog("New Balance", null);
                    if (EditBalanceDialog.okPressed) {
                        Xbankbalance xbb = (Xbankbalance) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xbb.getXbankbalanceId());
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
        return new AbstractAction("Edit Balance Record") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xbankbalance xbb = (Xbankbalance) exchanger.loadDbObjectOnID(Xbankbalance.class, id);
                        new EditBalanceDialog("Edit Balance", xbb);
                        if (EditAccountDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id);
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
                try {
                    Xbankbalance xbb = (Xbankbalance) exchanger.loadDbObjectOnID(Xbankbalance.class, id);
                    if (xbb != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xbb);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
