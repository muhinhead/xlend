package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xappforleave;
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
public class Application4LeaveGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public Application4LeaveGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_APP4LEAVE, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New App for Leave") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditApp4LeaveDialog ed = new EditApp4LeaveDialog("New App for Leave", null);
                    if (EditApp4LeaveDialog.okPressed) {
                        Xappforleave xl = (Xappforleave) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(),
                                getSelect(), xl.getXappforleaveId(), getPageSelector().getSelectedIndex());
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
                        Xappforleave xl = (Xappforleave) exchanger.loadDbObjectOnID(Xappforleave.class, id);
                        new EditApp4LeaveDialog("Edit App for Leave", xl);
                        if (EditApp4LeaveDialog.okPressed) {
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
                    Xappforleave xl = (Xappforleave) exchanger.loadDbObjectOnID(Xappforleave.class, id);
                    if (xl != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xl);
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
