package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xorderitem;
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
class OrderItemsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xorder xorder = null;
    private static final String whereId = "xorder_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public OrderItemsGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECTORDERITEMS.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECTORDERITEMS.substring(0, p))) {
            xorder = (Xorder) exchanger.loadDbObjectOnID(
                    Xorder.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Item") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EditOrderItemDialog ed;
                try {
                    EditOrderItemDialog.xorder = getXorder();
                    if (getXorder() != null) {
                        ed = new EditOrderItemDialog("New Order Item", null);
                        if (EditOrderItemDialog.okPressed) {
                            Xorderitem xitm = (Xorderitem) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xitm.getXorderitemId(),getPageSelector().getSelectedIndex());
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "Save order please before adding items");
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
                        Xorderitem itm = (Xorderitem) exchanger.loadDbObjectOnID(Xorderitem.class, id);
                        EditOrderItemDialog.xorder = getXorder();
                        new EditOrderItemDialog("Edit Order Item", itm);
                        if (EditOrderItemDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id,getPageSelector().getSelectedIndex());
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
                    Xorderitem itm = (Xorderitem) exchanger.loadDbObjectOnID(Xorderitem.class, id);
                    if (itm != null && GeneralFrame.yesNo("Attention!", "Do you want to delete order item["
                            + itm.getItemnumber() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(itm);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null,getPageSelector().getSelectedIndex());
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
    public Xorder getXorder() {
        return xorder;
    }
}
