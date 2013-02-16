package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.contract.EditContractDialog;
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
public class ContractsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 150);
    }

    public ContractsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CONTRACTS, maxWidths, false);
    }

    public ContractsGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Contract") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditContractDialog ed = new EditContractDialog("New Contract", null);
                    if (EditContractDialog.okPressed) {
                        Xcontract xcontract = (Xcontract) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xcontract.getXcontractId(), getPageSelector().getSelectedIndex());
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
                        Xcontract xcontract = (Xcontract) exchanger.loadDbObjectOnID(Xcontract.class, id);
                        new EditContractDialog("Edit Contract", xcontract);
                        if (EditContractDialog.okPressed) {
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
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xcontract xcontract = (Xcontract) exchanger.loadDbObjectOnID(Xcontract.class, id);
                    if (xcontract != null && GeneralFrame.yesNo("Attention!", "Do you want to delete contract  ["
                            + xcontract.getContractref() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xcontract);
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
