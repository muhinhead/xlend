package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.client.EditClientDialog;
import com.xlend.orm.Xclient;
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
public class ClientsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 100);
        maxWidths.put(4, 200);
        maxWidths.put(5, 200);
    }

    public ClientsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CLIENTS, maxWidths, false);
    }

    public ClientsGrid(IMessageSender exchanger, String slct, boolean readOnly) throws RemoteException {
        super(exchanger, slct, maxWidths, readOnly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Client") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditClientDialog ed = new EditClientDialog("New Client", null);
                    if (EditClientDialog.okPressed) {
                        Xclient xclient = (Xclient) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xclient.getXclientId(),getPageSelector().getSelectedIndex());
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
                        Xclient xclient = (Xclient) exchanger.loadDbObjectOnID(Xclient.class, id);
                        new EditClientDialog("Edit Client", xclient);
                        if (EditClientDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id,getPageSelector().getSelectedIndex());
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
                    Xclient xclient = (Xclient) exchanger.loadDbObjectOnID(Xclient.class, id);
                    if (xclient != null && GeneralFrame.yesNo("Attention!", "Do you want to delete client ["
                            + xclient.getCompanyname() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xclient);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null,getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
