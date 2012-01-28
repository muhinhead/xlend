package com.xlend.gui.client;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.contract.EditContractDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
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
public class ClientContractsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xclient xclient = null;
    private static final String whereId = "xclient_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public ClientContractsGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECT_CONTRACTS4CLIENT.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_CONTRACTS4CLIENT.substring(0, p))) {
            xclient = (Xclient) exchanger.loadDbObjectOnID(
                    Xclient.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Contract") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditContractDialog.xclient = getXclient();
                    EditContractDialog esd = new EditContractDialog("Add Contract", null);
                    if (esd.okPressed) {
                        Xcontract xcontract = (Xcontract) esd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xcontract.getXcontractId());
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
                        Xcontract itm = (Xcontract) exchanger.loadDbObjectOnID(Xcontract.class, id);
                        EditContractDialog.xclient = getXclient();
                        EditContractDialog esd = new EditContractDialog("Edit Contract", itm);
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
                    Xcontract itm = (Xcontract) exchanger.loadDbObjectOnID(Xcontract.class, id);
                    if (itm != null && GeneralFrame.yesNo("Attention!", "Do you want to delete contract ["
                            + itm.getContractref() + "]?") == JOptionPane.YES_OPTION) {
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

    /**
     * @return the xorder
     */
    public Xclient getXclient() {
        return xclient;
    }
}
