package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdiesel2plant;
import com.xlend.orm.Xdieselcartissue;
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
public class DieselToPlantGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public DieselToPlantGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_DIESEL2PLANT, maxWidths, false);
    }

    public DieselToPlantGrid(IMessageSender exchanger, int xdieselcart_id) throws RemoteException {
        super(exchanger, Selects.SELECT_DIESEL2PLANT.replace("#", "" + xdieselcart_id), maxWidths, true);
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditDieselToPlantDialog pd = new EditDieselToPlantDialog("Add Diesel to Plant", null);
                    if (EditDieselToPlantDialog.okPressed) {
                        Xdiesel2plant xdp = (Xdiesel2plant) pd.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xdp.getXdiesel2plantId(), getPageSelector().getSelectedIndex());
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
                        Xdiesel2plant xdp = (Xdiesel2plant) exchanger.loadDbObjectOnID(Xdiesel2plant.class, id);
                        new EditDieselToPlantDialog("Edit Issue", xdp);
                        if (EditDieselToPlantDialog.okPressed) {
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
        return new AbstractAction("Delete Issue") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xdiesel2plant xdp = (Xdiesel2plant) exchanger.loadDbObjectOnID(Xdiesel2plant.class, id);
                        if (xdp != null && GeneralFrame.yesNo("Attention!",
                                "Do you want to delete the record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xdp);
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
