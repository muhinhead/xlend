package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xmachtype;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class MachineTypeGrid extends GeneralGridPanel {

    public static Xmachtype papaType;

    public MachineTypeGrid(IMessageSender exchanger, String select, DbTableView masterView, boolean readOnly) throws RemoteException {
        super(exchanger, select, null, readOnly, masterView);
//        setIsMultilineSelection(true);
    }

    public MachineTypeGrid(IMessageSender exchanger, DbTableView masterView) throws RemoteException {
        this(exchanger, Selects.SELECT_MACHTYPES, masterView, false);
    }

    private String setParentID() throws NumberFormatException {
        papaType = null;
        DbTableDocument td = (DbTableDocument) getController().getDocument();
        int p = td.getSelectStatement().indexOf("where parenttype_id=");
        if (p > 0) {
            EditMachineTypePanel.parentID = new Integer(
                    td.getSelectStatement().substring(p + 20,
                    p + 20 + td.getSelectStatement().substring(p + 20).indexOf(" ")));
            try {
                papaType = (Xmachtype) exchanger.loadDbObjectOnID(Xmachtype.class, EditMachineTypePanel.parentID);
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        } else {
            EditMachineTypePanel.parentID = null;
        }
        return td.getSelectStatement();
    }

    private static String subTypeHeader() {
        return "Subtype" + (papaType == null ? "" : " of type " + papaType.getMachtype());
    }

    @Override
    protected AbstractAction addAction() {
//        if (!isExternalView) {
//            return null;
//        }
        return new AbstractAction(isExternalView ? "Add Type" : "Add Subtype") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String select = setParentID();
                    EditMachineTypeDialog ed = new EditMachineTypeDialog("New " + (isExternalView ? "Machine Type" : subTypeHeader()), null);
                    if (EditMachineTypeDialog.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, null,
                                getPageSelector().getSelectedIndex());
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
//        if (!isExternalView) {
//            return null;
//        }
        return new AbstractAction("Edit " + (isExternalView ? "Machine Type" : "Subtype")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xmachtype mt = (Xmachtype) exchanger.loadDbObjectOnID(Xmachtype.class, id);
                        String select = setParentID();
                        EditMachineTypeDialog ed = new EditMachineTypeDialog("Edit " + (isExternalView ? "Machine Type" : subTypeHeader()), mt);
                        if (EditMachineTypeDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, id,
                                    getPageSelector().getSelectedIndex());
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
//        if (!isExternalView) {
//            return null;
//        }
        return new AbstractAction("Delete Machine " + (isExternalView ? "Type(s)" : "Subtype(s)")) {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    if (GeneralFrame.yesNo("Attention!", "Do you want to delete selected type?") == JOptionPane.YES_OPTION) {
                        try {
                            String select = setParentID();
                            Xmachtype mt = (Xmachtype) exchanger.loadDbObjectOnID(Xmachtype.class, id);
                            exchanger.deleteObject(mt);
                            
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), select, null,
                                    getPageSelector().getSelectedIndex());
                        } catch (RemoteException ex) {
                            XlendWorks.log(ex);
                            GeneralFrame.errMessageBox("Error:", ex.getMessage());
                        }
                    }
                } else {
                    GeneralFrame.infoMessageBox("Attention!", "Mark rows to delete please");
                }
            }
        };
    }
}
