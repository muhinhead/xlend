package com.xlend.gui.work;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.creditor.EditCreditorDialog;
import com.xlend.orm.Xcreditor;
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
public class CreditorsGrid extends GeneralGridPanel {
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
//        maxWidths.put(1, 300);
//        maxWidths.put(2, 500);
//        maxWidths.put(3, 200);
//        maxWidths.put(4, 200);
    }
    
    public CreditorsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CREDITORS, maxWidths, false);
    }

    public CreditorsGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, maxWidths, readonly);
    }

    @Override
    protected AbstractAction addAction() {
       return new AbstractAction("Add Creditor") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditCreditorDialog ed = new EditCreditorDialog("New Creditor", null);
                    if (EditCreditorDialog.okPressed) {
                        Xcreditor xcred = (Xcreditor) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xcred.getXcreditorId());
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
                        Xcreditor xcred = (Xcreditor) exchanger.loadDbObjectOnID(Xcreditor.class, id);
                        new EditCreditorDialog("Edit Creditor", xcred);
                        if (EditCreditorDialog.okPressed) {
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
                    Xcreditor xcred = (Xcreditor) exchanger.loadDbObjectOnID(Xcreditor.class, id);
                    if (xcred !=null && GeneralFrame.yesNo("Attention!", 
                            "Do you want to delete this entry?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xcred);
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
