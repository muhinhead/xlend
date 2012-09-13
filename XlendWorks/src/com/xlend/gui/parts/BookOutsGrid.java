package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.XbookoutsWithTrigger;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class BookOutsGrid extends MovePartsGrid {

//    private static EditXpartPanel xpartsPanel;
    public BookOutsGrid(IMessageSender exchanger, int part_id, EditXpartPanel xpartsPane) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_BOOKOUTS.replaceAll("#", "" + (EditXbookOutPanel.partID = part_id)),
                null, xpartsPane);
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit Record") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        XbookoutsWithTrigger bout =
                                (XbookoutsWithTrigger) exchanger.loadDbObjectOnID(XbookoutsWithTrigger.class, id);
                        new EditXbookOutDialog("Edit Book Out", bout);
                        if (EditXbookOutDialog.okPressed) {
                            updateXpartsPanel(EditXbookOutPanel.partID);
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
        if (!XlendWorks.isCurrentAdmin()) {
            return null;
        } else {
            return new AbstractAction("Delete record") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = getSelectedID();
                    if (id > 0) {
                        try {
                            XbookoutsWithTrigger bout =
                                    (XbookoutsWithTrigger) exchanger.loadDbObjectOnID(XbookoutsWithTrigger.class, id);
                            if (bout != null && GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record? (Parts will be returned to store)") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(bout);
                                updateXpartsPanel(EditXbookOutPanel.partID);
                                GeneralFrame.updateGrid(exchanger, getTableView(),
                                        getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
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
}
