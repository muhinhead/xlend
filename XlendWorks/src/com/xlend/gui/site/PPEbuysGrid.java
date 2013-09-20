package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xppebuy;
import com.xlend.orm.Xppebuyitem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class PPEbuysGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public PPEbuysGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_PPEBUYS, maxWidths, false);
        setBorder(BorderFactory.createTitledBorder("Input"));
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditPPEbuyDialog pd = new EditPPEbuyDialog("Add Stock", null);
                    if (EditPPEbuyDialog.okPressed) {
                        Xppebuy xb = (Xppebuy) pd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xb.getXppebuyId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xppebuy xb = (Xppebuy) exchanger.loadDbObjectOnID(Xppebuy.class, id);
                        new EditPPEbuyDialog("Edit Purchase", xb);
                        if (EditPPEbuyDialog.okPressed) {
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
        return new AbstractAction("Delete Purchase") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xppebuy xb = (Xppebuy) exchanger.loadDbObjectOnID(Xppebuy.class, id);
                        if (xb != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete the record (stocks will be reduced)?") == JOptionPane.YES_OPTION) {
                            DbObject[] recs = XlendWorks.getExchanger().getDbObjects(Xppebuyitem.class,
                                    "xppebuy_id=" + xb.getXppebuyId(), "xppebuyitem_id");
                            for (DbObject rec : recs) {
                                exchanger.deleteObject(rec);
                            }
                            exchanger.deleteObject(xb);
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
