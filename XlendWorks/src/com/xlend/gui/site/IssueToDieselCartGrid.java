package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselcartissue;
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
public class IssueToDieselCartGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public IssueToDieselCartGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESELCARTISSUES, maxWidths, false);
    }

    public IssueToDieselCartGrid(IMessageSender exchanger, int xdieselcart_id) throws RemoteException {
        super(exchanger, Selects.SELECT_DIESELCARTISSUES.replace("#", "" + xdieselcart_id), maxWidths, true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditIssueToDieselDialog pd = new EditIssueToDieselDialog("Add Issue", null);
                    if (EditIssueToDieselDialog.okPressed) {
                        Xdieselcartissue xi = (Xdieselcartissue) pd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xi.getXdieselcartissueId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xdieselcartissue xi = (Xdieselcartissue) exchanger.loadDbObjectOnID(Xdieselcartissue.class, id);
                        new EditIssueToDieselDialog("Edit Issue", xi);
                        if (EditIssueToDieselDialog.okPressed) {
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
        return new AbstractAction("Delete Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xdieselcartissue xi = (Xdieselcartissue) exchanger.loadDbObjectOnID(Xdieselcartissue.class, id);
                        if (xi != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete the record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xi);
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
