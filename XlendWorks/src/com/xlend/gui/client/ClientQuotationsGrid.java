package com.xlend.gui.client;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.quota.EditQuotaDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xquotation;
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
public class ClientQuotationsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xclient xclient = null;
    private static final String whereId = "xclient_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public ClientQuotationsGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECT_QUOTATIONS4CLIENTS.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_QUOTATIONS4CLIENTS.substring(0, p))) {
            xclient = (Xclient) exchanger.loadDbObjectOnID(
                    Xclient.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    /**
     * @return the xorder
     */
    public Xclient getXclient() {
        return xclient;
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add RFQ") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditQuotaDialog.xclient = getXclient();
                    EditQuotaDialog esd = new EditQuotaDialog("Add RFQ", null);
                    if (esd.okPressed) {
                        Xquotation xq = (Xquotation) esd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xq.getXquotationId());
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
                        Xquotation itm = (Xquotation) exchanger.loadDbObjectOnID(Xquotation.class, id);
                        EditQuotaDialog.xclient = getXclient();
                        EditQuotaDialog esd = new EditQuotaDialog("Edit RFQ", itm);
                        if (esd.okPressed) {
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
                        Xquotation itm = (Xquotation) exchanger.loadDbObjectOnID(Xquotation.class, id);
                    if (itm != null && GeneralFrame.yesNo("Attention!", "Do you want to delete contract [RefNr_"
                            + itm.getRfcnumber() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(itm);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
