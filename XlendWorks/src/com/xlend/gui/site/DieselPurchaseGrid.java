package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselpchs;
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
public class DieselPurchaseGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public DieselPurchaseGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESELPCHS, maxWidths, false);
    }

    public DieselPurchaseGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, maxWidths, readonly);
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Diesel Purchase") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditDieselPurchaseDialog ed = new EditDieselPurchaseDialog("Add Purchase", null);
                    if (EditDieselPurchaseDialog.okPressed) {
                        Xdieselpchs xpr = (Xdieselpchs) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xpr.getXdieselpchsId());
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
                        Xdieselpchs xpr = (Xdieselpchs) exchanger.loadDbObjectOnID(Xdieselpchs.class, id);
                        new EditDieselPurchaseDialog("Edit Diesel Purchase", xpr);
                        if (EditDieselPurchaseDialog.okPressed) {
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
        return new AbstractAction("Delete Entry") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xdieselpchs xpr = (Xdieselpchs) exchanger.loadDbObjectOnID(Xdieselpchs.class, id);
                    if (xpr !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete diesel purchase?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xpr);
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
