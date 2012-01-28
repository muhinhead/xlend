package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbreakdown;
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
public class BreakdownsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public BreakdownsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BREAKDOWNS, maxWidths, false);
    }

    public BreakdownsGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, maxWidths, readonly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Breakdown") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditBreakdownDialog ed = new EditBreakdownDialog("Add breakdown", null);
                    if (EditBreakdownDialog.okPressed) {
                        Xbreakdown xb = (Xbreakdown) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xb.getXbreakdownId());
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
                        Xbreakdown xb = (Xbreakdown) exchanger.loadDbObjectOnID(Xbreakdown.class, id);
                        new EditBreakdownDialog("Edit breakdown", xb);
                        if (EditBreakdownDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id);
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
                    Xbreakdown xb = (Xbreakdown) exchanger.loadDbObjectOnID(Xbreakdown.class, id);
                    if (xb !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete breakdown?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xb);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

}
