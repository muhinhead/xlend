package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Cbitems;
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
public class SiteTypeGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 40);
    }

    public SiteTypeGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_SITETYPES, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Site Type") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditSiteTypeDialog ed = new EditSiteTypeDialog("New Site Type", null);
                    if (ed.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Site Type") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Cbitems itm = (Cbitems) exchanger.loadDbObjectOnID(Cbitems.class, id);
                        new EditSiteTypeDialog("Edit Site Type", itm);
                        if (EditSiteTypeDialog.okPressed) {
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
        return new AbstractAction("Delete Site Type") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Cbitems itm = (Cbitems) exchanger.loadDbObjectOnID(Cbitems.class, id);
                        if (itm != null) {
                            if (XlendWorks.existsSiteOfType(exchanger, itm.getVal())) {
                                GeneralFrame.errMessageBox("Error:", 
                                        "There are some sites of this type, it couldn't be removed!");
                            } else if (GeneralFrame.yesNo("Attention!", 
                                    "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(itm);
                                GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
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
}
