package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbMasterTableView;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xmachtype;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class MachineTypeGrid extends GeneralGridPanel {

    public MachineTypeGrid(IMessageSender exchanger, String select, DbMasterTableView masterView) throws RemoteException {
        super(exchanger, select, null, false, masterView);
    }

    public MachineTypeGrid(IMessageSender exchanger, DbMasterTableView masterView) throws RemoteException {
        this(exchanger, Selects.SELECT_MACHTYPES, masterView);
    }

    private String setParentID() throws NumberFormatException {
        DbTableDocument td = (DbTableDocument) getController().getDocument();
        int p = td.getSelectStatement().indexOf("where parenttype_id=");
        if (p > 0) {
            EditMachineTypePanel.parentID = new Integer(
                    td.getSelectStatement().substring(p + 20,
                    p + 20 + td.getSelectStatement().substring(p + 20).indexOf(" ")));
        } else {
            EditMachineTypePanel.parentID = null;
        }
        return td.getSelectStatement();
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction(isExternalView ? "Add Type" : "Add Subtype") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String select = setParentID();
                    EditMachineTypeDialog ed = new EditMachineTypeDialog("New Machine " + (isExternalView ? "Type" : "Subtype"), null);
                    if (EditMachineTypeDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, null);
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
        return new AbstractAction("Edit Machine " + (isExternalView ? "Type" : "Subtype")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachtype mt = (Xmachtype) exchanger.loadDbObjectOnID(Xmachtype.class, id);
                        String select = setParentID();
                        EditMachineTypeDialog ed = new EditMachineTypeDialog("Edit Machine " + (isExternalView ? "Type" : "Subtype"), mt);
                        if (EditMachineTypeDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, id);
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
        return new AbstractAction("Delete Machine " + (isExternalView ? "Type" : "Subtype")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachtype mt = (Xmachtype) exchanger.loadDbObjectOnID(Xmachtype.class, id);
                        String select = setParentID();
                        if (mt != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this type?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(mt);
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, null);
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