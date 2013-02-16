package com.xlend.gui.parts;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.orm.Xbookouts;
import com.xlend.orm.Xparts;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
class PartsGrid extends GeneralGridPanel {

//    private int cartegoryID;
    public PartsGrid(IMessageSender exchanger, int category_id) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_XPARTS.replaceAll("#", "" + category_id), null, false);
//        EditXpartPanel.setCategoryID(//cartegoryID = 
//                category_id);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Part") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditXpartDialog ed = new EditXpartDialog("New Part", null);
                    if (EditXpartDialog.okPressed) {
                        Xparts xpart = (Xparts) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                xpart.getXpartsId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Part") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xparts xpart = (Xparts) exchanger.loadDbObjectOnID(Xparts.class, id);
                        new EditXpartDialog("Edit Part", xpart);
                        if (EditXpartDialog.okPressed) {
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
        EditXpartPanel.setCategoryID(//cartegoryID = 
                category_id);
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

    @Override
    protected JPanel getRightPanel(JPanel btnPanel) {
        JPanel rightPanel = super.getRightPanel(btnPanel);
        JPanel rightCenterPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightCenterPanel, BorderLayout.CENTER);
        JPanel inoutButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inoutButtonPanel.add(new JPanel());
        inoutButtonPanel.add(new JButton(getBookOutAction("Book Out")));
        inoutButtonPanel.add(new JButton(getAddStockAction("Add Stock")));
        rightCenterPanel.add(inoutButtonPanel, BorderLayout.NORTH);
        return rightPanel;
    }

    private AbstractAction getBookOutAction(final String label) {
        return new AbstractAction(label) {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditXbookOutPanel.partID = getSelectedID();
                if (EditXbookOutPanel.partID > 0) {
                    try {
                        Xparts part = (Xparts) DashBoard.getExchanger().loadDbObjectOnID(Xparts.class, EditXbookOutPanel.partID);
                        EditXbookOutDialog ed = new EditXbookOutDialog(label + " (part No_" + part.getPartnumber() + ")", null);
                        if (EditXbookOutDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                    EditXbookOutPanel.partID, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }

    private AbstractAction getAddStockAction(final String label) {
        return new AbstractAction(label) {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO:
                EditXaddStockPanel.partID = getSelectedID();
                if (EditXaddStockPanel.partID > 0) {
                    try {
                        Xparts part = (Xparts) DashBoard.getExchanger().loadDbObjectOnID(Xparts.class, EditXaddStockPanel.partID);
                        EditXaddStockDialog ed = new EditXaddStockDialog(label + " (part No_" + part.getPartnumber() + ")", null);
                        if (EditXaddStockDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(),
                                    EditXaddStockPanel.partID, getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            }
        };
    }
//    /**
//     * @return the cartegoryID
//     */
//    public int getCartegoryID() {
//        return cartegoryID;
//    }
}
