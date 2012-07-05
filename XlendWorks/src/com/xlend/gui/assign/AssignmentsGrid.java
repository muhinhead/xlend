package com.xlend.gui.assign;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xhourcompare;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.Xsite;
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
public class AssignmentsGrid extends GeneralGridPanel {

    private static final String MARK = "from xopmachassing where xsite_id=";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public AssignmentsGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        if (getSelect().indexOf("from xopmachassing where xsite_id=") > 0) {
            return new AbstractAction("Add new assignment") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int p = getSelect().indexOf(MARK);
                    int p1 = getSelect().indexOf(" ", p + MARK.length());
                    try {
                        EditSiteAssignmentPanel.xsiteID = Integer.parseInt(getSelect().substring(p + MARK.length(), p1));
                        EditSiteAssignmentDialog esad = new EditSiteAssignmentDialog("Add assignment", null);
                        if (esad.okPressed) {
                            Xopmachassing assign = (Xopmachassing) esad.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), assign.getXopmachassingId());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            };
        } else {
            return null;
        }
    }

    @Override
    protected AbstractAction editAction() {
        return null;
    }

    @Override
    protected AbstractAction delAction() {
        if (getSelect().indexOf("from xopmachassing where xsite_id=") < 0) {
            return new AbstractAction("Delete record") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    int id = getSelectedID();
                    try {
                        Xopmachassing assign = (Xopmachassing) exchanger.loadDbObjectOnID(Xopmachassing.class, id);
                        if (assign != null) {
                            if (assign.getDateEnd() == null) {
                                GeneralFrame.errMessageBox("Attention!", "Current assignment couldn't be deleted. Just reassign it");
                            } else if (GeneralFrame.yesNo("Attention!",
                                    "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                                exchanger.deleteObject(assign);
                                GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null);
                            }
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    }
                }
            };
        } else {
            return null;
        }
    }
}
