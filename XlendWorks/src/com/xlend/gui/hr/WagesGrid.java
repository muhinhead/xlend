package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xwagesum;
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
public class WagesGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public WagesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_WAGES, maxWidths, false);
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Wage List") {

           @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditWagesDialog ed = new EditWagesDialog("New Wage List", null);
                    if (EditWagesDialog.okPressed) {
                        Xwagesum xs = (Xwagesum) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), 
                                getSelect(), xs.getXwagesumId(),getPageSelector().getSelectedIndex());
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
                        Xwagesum xs = (Xwagesum) exchanger.loadDbObjectOnID(Xwagesum.class, id);
                        new EditWagesDialog("Edit Wage List", xs);
                        if (EditWagesDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id,getPageSelector().getSelectedIndex());
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
                    Xwagesum xs = (Xwagesum) exchanger.loadDbObjectOnID(Xwagesum.class, id);
                    if (xs !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete this entry?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xs);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null,getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

}
