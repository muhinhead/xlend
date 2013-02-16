/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.parts;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xparts;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author nick
 */
public abstract class MovePartsGrid extends GeneralGridPanel {

    protected static EditXpartPanel xpartsPanel;

    public MovePartsGrid(IMessageSender exchanger, String select, HashMap<Integer, Integer> maxWidths, EditXpartPanel xpartsPane) throws RemoteException {
        super(exchanger, select, maxWidths, (xpartsPanel = xpartsPane) == null);
    }

    @Override
    protected AbstractAction addAction() {
        return null;
    }

    protected void updateXpartsPanel(Integer partID) {
        try {
            Xparts part = (Xparts) exchanger.loadDbObjectOnID(Xparts.class, partID);
            xpartsPanel.setDbObject(part);
            xpartsPanel.loadData();
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
