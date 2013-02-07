package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselcart;
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
public class DieselCartsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public DieselCartsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESELCARTS, maxWidths, false);
    }
    
    public DieselCartsGrid(IMessageSender exchanger, 
            String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Cart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditDieselCartDialog ed = new EditDieselCartDialog("Add Diesel Cart",null);
                    if (EditDieselCartDialog.okPressed) {
                        Xdieselcart xc = (Xdieselcart) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xc.getXdieselcartId(), 
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
        return new AbstractAction("Edit Cart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xdieselcart xc = (Xdieselcart) exchanger.loadDbObjectOnID(Xdieselcart.class, id);
                    new EditDieselCartDialog("Edit Diesel Cart", xc);
                    if (EditDieselCartDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Delete Diesel Cart") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xdieselcart xc = (Xdieselcart) exchanger.loadDbObjectOnID(Xdieselcart.class, id);
                    if (xc != null && GeneralFrame.yesNo("Attention!", "Do you want to delete record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xc);
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
