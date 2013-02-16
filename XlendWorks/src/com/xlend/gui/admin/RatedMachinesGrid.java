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
public class RatedMachinesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 40);
    }

    public RatedMachinesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_RATEDMACHINES, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Rated Machine") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditRatedMachineDialog ed = new EditRatedMachineDialog("New Rated Machine", null);
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
        return new AbstractAction("Edit Rated Machine") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Cbitems itm = (Cbitems) exchanger.loadDbObjectOnID(Cbitems.class, id);
                        new EditRatedMachineDialog("Edit Rated Machine", itm);
                        if (EditRatedMachineDialog.okPressed) {
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
        return new AbstractAction("Delete Rate Machine") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Cbitems itm = (Cbitems) exchanger.loadDbObjectOnID(Cbitems.class, id);
                        if (itm != null) {
                            if (GeneralFrame.yesNo("Attention!",
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
