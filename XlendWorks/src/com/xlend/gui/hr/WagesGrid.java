/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xtimesheet;
import com.xlend.orm.Xwage;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
public class WagesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xtimesheet xtimesheet = null;
    private static final String whereId = "xtimesheet_id = #";

    static {
        maxWidths.put(0, 40);
    }

    public WagesGrid(IMessageSender exchanger, String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
        int p = Selects.SELECT_WAGE4TIMESHEET.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_WAGE4TIMESHEET.substring(0, p))) {
            xtimesheet = (Xtimesheet) exchanger.loadDbObjectOnID(
                    Xtimesheet.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Day time sheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                EditWageDayDialog ed;
                try {
                    EditWageDayDialog.xtimesheet = getXtimesheet();
                    if (getXtimesheet() != null) {
                        ed = new EditWageDayDialog("Day time sheet", null);
                        if (EditWageDayDialog.okPressed) {
                            Xwage xwage = (Xwage) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xwage.getXwageId());
                        }
                    } else {
                        GeneralFrame.infoMessageBox("Attention!", "Save order please before adding items");
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
         return new AbstractAction("Edit Day time sheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xwage itm = (Xwage) exchanger.loadDbObjectOnID(Xwage.class, id);
                        EditWageDayDialog.xtimesheet = getXtimesheet();
                        new EditWageDayDialog("Edit Day time sheet", itm);
                        if (EditWageDayDialog.okPressed) {
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
        return new AbstractAction("Delete Item") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xwage itm = (Xwage) exchanger.loadDbObjectOnID(Xwage.class, id);
                    if (itm != null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete day time sheet?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(itm);
                        GeneralFrame.updateGrid(exchanger, getTableView(),
                                getTableDoc(), getSelect(), null);
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    /**
     * @return the xtimesheet
     */
    public Xtimesheet getXtimesheet() {
        return xtimesheet;
    }
}
