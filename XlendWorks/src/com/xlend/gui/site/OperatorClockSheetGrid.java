package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xopclocksheet;
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
public class OperatorClockSheetGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public OperatorClockSheetGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_OPCLOCKSHEET, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Clock Sheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditOperatorClockSheetDialog ed = new EditOperatorClockSheetDialog("Add Clock Sheet", null);
                    if (EditOperatorClockSheetDialog.okPressed) {
                        Xopclocksheet xl = (Xopclocksheet) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xl.getXopclocksheetId());
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
        return new AbstractAction("Edit Clock Sheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xopclocksheet xi = (Xopclocksheet) exchanger.loadDbObjectOnID(Xopclocksheet.class, id);
                        new EditOperatorClockSheetDialog("Edit Clock Sheet", xi);
                        if (EditOperatorClockSheetDialog.okPressed) {
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
        return new AbstractAction("Delete Clock Sheet") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xopclocksheet xi = (Xopclocksheet) exchanger.loadDbObjectOnID(Xopclocksheet.class, id);
                    if (xi != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xi);
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
