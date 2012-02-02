package com.xlend.gui.site;

import com.xlend.gui.GeneralGridPanel;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xfuel;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;

/**
 *
 * @author Nick Mukhin
 */
public class FuelGrid extends GeneralGridPanel {

 private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public FuelGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_FUELS, maxWidths, false);
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Record") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditFuelDialog ed = new EditFuelDialog("Add Record", null);
                    if (EditFuelDialog.okPressed) {
                        Xfuel xb = (Xfuel) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xb.getXfuelId());
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
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        return null;
    }
    
}
