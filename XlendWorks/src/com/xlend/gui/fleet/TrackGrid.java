package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xmachine;
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
public class TrackGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public TrackGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_MACHINE.replace(
                "classify='M'", "classify='T'"), maxWidths, false);
    }

    public TrackGrid(IMessageSender exchanger, String select,
            boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
    }

    private String whatIsThis() {
        return (getSelect().indexOf("is_lowbed=1") > 0) ? "Low Bed" : "Truck";
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New " + whatIsThis()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditTrackPanel.setLowbed(getSelect().indexOf("is_lowbed=1") > 0);
                    EditTrackDialog ed = new EditTrackDialog("New " + whatIsThis(), null);
                    if (EditTrackDialog.okPressed) {
                        Xmachine machine = (Xmachine) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), machine.getXmachineId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit " + whatIsThis()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachine machine = (Xmachine) exchanger.loadDbObjectOnID(Xmachine.class, id);
                        EditTrackPanel.setLowbed(getSelect().indexOf("is_lowbed=1") > 0);
                        new EditTrackDialog("Edit " + whatIsThis(), machine);
                        if (EditTrackDialog.okPressed) {
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
        return new AbstractAction("Delete " + whatIsThis()) {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xmachine machine = (Xmachine) exchanger.loadDbObjectOnID(Xmachine.class, id);
                    if (machine != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete " + whatIsThis() + " [Reg.Nr "
                            + machine.getRegNr() + "]?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(machine);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
}
