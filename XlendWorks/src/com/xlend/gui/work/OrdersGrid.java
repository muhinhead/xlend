package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.contract.EditContractOrderDialog;
import com.xlend.gui.order.EditOrderDialog;
import com.xlend.orm.Xcontract;
import com.xlend.orm.Xorder;
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
public class OrdersGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xcontract xcontract = null;
    private static final String whereId = "xcontract_id = #";
    private boolean inContract = false;

    static {
        maxWidths.put(0, 40);
    }

    public OrdersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ORDERS, maxWidths, false);
    }

    public OrdersGrid(IMessageSender exchanger, String slct, boolean readOnly) throws RemoteException {
        super(exchanger, slct, maxWidths, readOnly);
        int p = Selects.SELECT_ORDERS4CONTRACTS.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_ORDERS4CONTRACTS.substring(0,p))) {
            xcontract = (Xcontract) exchanger.loadDbObjectOnID(
                    Xcontract.class, Integer.parseInt(getSelect().substring(p + whereId.length()-1)));
            inContract = true;
        }
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Order") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditOrderDialog ed;
                    if (getXcontract() != null) {
                        EditContractOrderDialog.xcontract = getXcontract();
                        ed = new EditContractOrderDialog("New Order on contract", null);
                        if (EditContractOrderDialog.okPressed) {
                            Xorder xorder = (Xorder) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger,
                                    getTableView(), getTableDoc(), getSelect(), xorder.getXorderId());
                        }
                    } else if (inContract) {
                        GeneralFrame.infoMessageBox("Attention!", "Save contract please before adding orders");
                    } else {
                        ed = new EditOrderDialog("New Order", null);
                        if (EditOrderDialog.okPressed) {
                            Xorder xorder = (Xorder) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger,
                                    getTableView(), getTableDoc(), getSelect(), xorder.getXorderId());
                        }
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
        return new AbstractAction("Edit Order") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xorder xorder = (Xorder) exchanger.loadDbObjectOnID(Xorder.class, id);
                        if (getXcontract() != null) {
                            EditContractOrderDialog.xcontract = getXcontract();
                            EditContractOrderDialog od = new EditContractOrderDialog("Edit Order on contract", xorder);
                            if (EditContractOrderDialog.okPressed) {
                                GeneralFrame.updateGrid(exchanger,
                                        getTableView(), getTableDoc(), getSelect(), id);
                            }
                        } else {
                            new EditOrderDialog("Edit Order", xorder);
                            if (EditOrderDialog.okPressed) {
                                GeneralFrame.updateGrid(exchanger, getTableView(),
                                        getTableDoc(), getSelect(),id);
                            }
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
        return new AbstractAction("Delete Order") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xorder xorder = (Xorder) exchanger.loadDbObjectOnID(Xorder.class, id);
                    if (xorder!=null && GeneralFrame.yesNo("Attention!", "Do you want to delete order [Nr "
                            + xorder.getOrdernumber() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xorder);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    /**
     * @return the xcontract
     */
    public Xcontract getXcontract() {
        return xcontract;
    }
}
