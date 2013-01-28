package com.xlend.gui.site;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xppeissue;
import com.xlend.orm.Xppeissueitem;
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
public class PPEissuesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public PPEissuesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_PPEISSUES, maxWidths, false);
        setBorder(BorderFactory.createTitledBorder("Output"));
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditPPEissueDialog pd = new EditPPEissueDialog("Add Issue", null);
                    if (EditPPEissueDialog.okPressed) {
                        Xppeissue xi = (Xppeissue) pd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xi.getXppeissueId(), getPageSelector().getSelectedIndex());
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
                        Xppeissue xi = (Xppeissue) exchanger.loadDbObjectOnID(Xppeissue.class, id);
                        new EditPPEissueDialog("Edit Issue", xi);
                        if (EditPPEissueDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), 
                                    getSelect(), id, getPageSelector().getSelectedIndex());
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
                        Xppeissue xi = (Xppeissue) exchanger.loadDbObjectOnID(Xppeissue.class, id);
                        if (xi != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete the record (stocks will be reduced)?") == JOptionPane.YES_OPTION) {
                            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xppeissueitem.class,
                                    "xppeissue_id=" + xi.getXppeissueId(), "xppeissueitem_id");
                            for (DbObject rec : recs) {
                                exchanger.deleteObject(rec);
                            }
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
