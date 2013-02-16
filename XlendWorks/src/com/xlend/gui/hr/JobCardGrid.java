package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xjobcard;
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
public class JobCardGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public JobCardGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_JOBCARDS, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Job Card") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditJobCardDialog ed = new EditJobCardDialog("Add Job Card", null);
                    if (EditJobCardDialog.okPressed) {
                        Xjobcard xj = (Xjobcard) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xj.getXjobcardId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Job Card") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xjobcard xi = (Xjobcard) exchanger.loadDbObjectOnID(Xjobcard.class, id);
                        new EditJobCardDialog("Edit Job Card", xi);
                        if (EditJobCardDialog.okPressed) {
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
        return new AbstractAction("Delete Clock Sheet") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xjobcard xi = (Xjobcard) exchanger.loadDbObjectOnID(Xjobcard.class, id);
                    if (xi != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this job card?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xi);
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
