package com.xlend.gui.admin;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xpettycategory;
//import com.xlend.orm.Xppetype;
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
public class PettyCategoryGrid extends GeneralGridPanel {

private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    
    public PettyCategoryGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_XPETTYCATEGORY, maxWidths, false);
    }
    
    @Override
    protected AbstractAction addAction() {
       return new AbstractAction("Add category") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditPettyCategoryDialog ed = new EditPettyCategoryDialog("New petty category", null);
                    if (ed.okPressed) {
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), 
                                getSelect(), null, getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit category") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpettycategory xc = (Xpettycategory) exchanger.loadDbObjectOnID(Xpettycategory.class, id);
                        new EditPettyCategoryDialog("Edit petty category", xc);
                        if (EditPettyCategoryDialog.okPressed) {
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
        return new AbstractAction("Delete Category") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xpettycategory xc = (Xpettycategory) exchanger.loadDbObjectOnID(Xpettycategory.class, id);
                        if (xc != null) {
                            if (XlendWorks.isXpettyCategoryUsed(id)) {
                                GeneralFrame.errMessageBox("Error:",
                                        "There are some petty of this category, it couldn't be removed!");
                            } else if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(xc);
                                GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null,
                                        getPageSelector().getSelectedIndex());
                            }
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
