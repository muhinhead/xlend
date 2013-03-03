package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbatterypurchase;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class BatteryPurchaseGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public BatteryPurchaseGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BATTPURCHASES, maxWidths, false);
        setBorder(BorderFactory.createTitledBorder("Input"));
    }
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditBatteryPurchaseDialog ed = new EditBatteryPurchaseDialog("New Purchase", null);
                    if (EditBatteryPurchaseDialog.okPressed) {
                        Xbatterypurchase bp = (Xbatterypurchase) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), bp.getXbatterypurchaseId(), 
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
        return new AbstractAction("Edit Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xbatterypurchase bp = (Xbatterypurchase) exchanger.loadDbObjectOnID(Xbatterypurchase.class, id);
                    new EditBatteryPurchaseDialog("Edit Purchase", bp);
                    if (EditBatteryPurchaseDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xbatterypurchase bp = (Xbatterypurchase) exchanger.loadDbObjectOnID(Xbatterypurchase.class, id);
                    if (bp != null && GeneralFrame.yesNo("Attention!", "Do you want to delete purchase record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(bp);
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
