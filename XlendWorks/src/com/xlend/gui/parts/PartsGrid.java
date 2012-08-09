package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.orm.Xparts;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
class PartsGrid extends GeneralGridPanel {

    private int cartegoryID;

    public PartsGrid(IMessageSender exchanger, int category_id) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_XPARTS.replaceAll("#", "" + category_id), null, false);
//        EditXpartPanel.categoryID = cartegoryID = category_id;
    }

    @Override
    protected AbstractAction addAction() {
//        return new AbstractAction("Add Part") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    EditXpartDialog ed = new EditXpartDialog("New Part", null);
//                    if (EditXpartDialog.okPressed) {
//                        Xparts xpart = (Xparts) ed.getEditPanel().getDbObject();
//                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
//                                xpart.getXpartsId(), getPageSelector().getSelectedIndex());
//                    }
//                } catch (RemoteException ex) {
//                    XlendWorks.log(ex);
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
        return null;
    }

    @Override
    protected AbstractAction editAction() {
//        return new AbstractAction("Edit Part") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int id = getSelectedID();
//                if (id > 0) {
//                    try {
//                        Xparts xpart = (Xparts) exchanger.loadDbObjectOnID(Xparts.class, id);
//                        new EditXpartDialog("Edit Part", xpart);
//                        if (EditXpartDialog.okPressed) {
//                            GeneralFrame.updateGrid(exchanger, getTableView(),
//                                    getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
//                        }
//                    } catch (RemoteException ex) {
//                        XlendWorks.log(ex);
//                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                    }
//                }
//            }
//        };
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Part") {

            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();

                if (id > 0) {
                    try {
                        Xparts xpart = (Xparts) exchanger.loadDbObjectOnID(Xparts.class, id);
                        if (xpart != null && GeneralFrame.yesNo("Attention!", "Do you want to delete part ["
                                + xpart.getPartnumber() + "]?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(xpart);
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

    public void selectCategoryID(int category_id) {
//        EditXpartPanel.categoryID = cartegoryID = category_id;
        String newSelect = Selects.SELECT_FROM_XPARTS.replaceAll("#", "" + category_id);
        DbTableDocument td = (DbTableDocument) getController().getDocument();
        setSelect(newSelect);
        td.setSelectStatement(newSelect);
        try {
            int id = getSelectedID();
            td.setBody(exchanger.getTableBody(newSelect, 0, PAGESIZE));
            updatePageCounter(newSelect);
            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
            XlendWorks.log(ex);
        }
    }

    /**
     * @return the cartegoryID
     */
    public int getCartegoryID() {
        return cartegoryID;
    }
}
