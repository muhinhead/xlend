package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsalarylist;
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
public class SalaryListsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public SalaryListsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_SALARYLISTS, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Salary List") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditSalaryListDialog ed = new EditSalaryListDialog("Add Salary List", null);
                    if (EditSalaryListDialog.okPressed) {
                        Xsalarylist xl = (Xsalarylist) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger,
                                getTableView(), getTableDoc(), getSelect(), xl.getXsalarylistId(), getPageSelector().getSelectedIndex());
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
                        Xsalarylist xl = (Xsalarylist) exchanger.loadDbObjectOnID(Xsalarylist.class, id);
                        new EditSalaryListDialog("Edit Salary List", xl);
                        if (EditSalaryListDialog.okPressed) {
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
                    Xsalarylist xl = (Xsalarylist) exchanger.loadDbObjectOnID(Xsalarylist.class, id);
                    if (xl != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this list?") == JOptionPane.YES_OPTION) {
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
