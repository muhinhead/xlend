package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.gui.site.EditIssuingDialog;
import com.xlend.orm.Xissuing;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class IssueGrid extends GeneralGridPanel {

    public IssueGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_ISSUING, getMaxWidths(new int[]{40,120}), false);
    }

    public IssueGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, getMaxWidths(new int[]{40,120}), true);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Record") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditIssuingDialog ed = new EditIssuingDialog("Add Record", null);
                    if (EditIssuingDialog.okPressed) {
                        Xissuing xi = (Xissuing) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xi.getXissuingId());
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
                        Xissuing xi = (Xissuing) exchanger.loadDbObjectOnID(Xissuing.class, id);
                        new EditIssuingDialog("Edit Issuing Record", xi);
                        if (EditIssuingDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(),
                                    getTableDoc(), getSelect(), id);
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
                    Xissuing xi = (Xissuing) exchanger.loadDbObjectOnID(Xissuing.class, id);
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
