package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.EditSiteDialog;
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
public class SitesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 300);
        maxWidths.put(2, 500);
        maxWidths.put(3, 200);
        maxWidths.put(4, 200);
    }

    public SitesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_SITES, maxWidths);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Site") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new EditSiteDialog("New Site", null);
                    if (EditSiteDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
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
                        Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                        new EditSiteDialog("Edit Site", xsite);
                        if (EditSiteDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect());
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
                    Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, id);
                    if (GeneralFrame.yesNo("Attention!", "Do you want to delete site ["
                            + xsite.getName() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xsite);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                Selects.SELECT_FROM_SITES);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }
}
