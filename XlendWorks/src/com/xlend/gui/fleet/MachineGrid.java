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
public class MachineGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public MachineGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_MACHINE, maxWidths, false);
    }

    public MachineGrid(IMessageSender exchanger, String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Machine") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditMachineDialog ed = new EditMachineDialog("New Machine", null);
                    if (EditMachineDialog.okPressed) {
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
        return new AbstractAction("Edit Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachine machine = (Xmachine) exchanger.loadDbObjectOnID(Xmachine.class, id);
                        new EditMachineDialog("Edit Machine", machine);
                        if (EditMachineDialog.okPressed) {
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
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xmachine machine = (Xmachine) exchanger.loadDbObjectOnID(Xmachine.class, id);
                    if (machine != null && GeneralFrame.yesNo("Attention!", "Do you want to delete machine  [Reg.Nr "
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
