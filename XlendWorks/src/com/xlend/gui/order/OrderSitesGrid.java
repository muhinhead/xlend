package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.EditSiteDialog;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xsite;
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
public class OrderSitesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xorder xorder = null;
    private static final String whereId = "xorder_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public OrderSitesGrid(IMessageSender exchanger, String slct) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECT_ORDERISITES.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_ORDERISITES.substring(0, p))) {
            xorder = (Xorder) exchanger.loadDbObjectOnID(
                    Xorder.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditSiteDialog.xorder = getXorder();
                    EditSiteDialog esd = new EditSiteDialog("Add Site", null);
                    if (esd.okPressed) {
                        Xsite xsite = (Xsite) esd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xsite.getXsiteId());
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
        return new AbstractAction("Edit Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xsite itm = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                        EditSiteDialog.xorder = getXorder();
                        EditSiteDialog esd = new EditSiteDialog("Edit Site", itm);
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
        return new AbstractAction("Delete Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xsite itm = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                    if (itm != null && GeneralFrame.yesNo("Attention!", "Do you want to delete site ["
                            + itm.getName() + "]?") == JOptionPane.YES_OPTION) {
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
    public Xorder getXorder() {
        return xorder;
    }
}
