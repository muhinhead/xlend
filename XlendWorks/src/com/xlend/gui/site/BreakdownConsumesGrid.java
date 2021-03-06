package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbreakconsume;
import com.xlend.orm.Xbreakdown;
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
public class BreakdownConsumesGrid extends GeneralGridPanel {

//    private static ArrayList<Xbreakconsume> newPurchases;
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private static final String whereId = "xbreakconsume.xbreakdown_id = #";
    private static Integer xbreakdownID = null;
    private static Integer xmachineID;
    private final EditBreakdownPanel parentPanel;

    static {
        maxWidths.put(0, 40);
    }

    /**
     * @return the xmachineID
     */
    public static Integer getXmachineID() {
        return xmachineID;
    }

    /**
     * @param aXmachineID the xmachineID to set
     */
    public static void setXmachineID(Integer aXmachineID) {
        xmachineID = aXmachineID;
    }

    public BreakdownConsumesGrid(IMessageSender exchanger, String slct, Integer xmachID, EditBreakdownPanel parentPanel) throws RemoteException {
        super(exchanger, slct, maxWidths, false);
        int p = Selects.SELECT_BREAKDOWNCONSUMES.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_BREAKDOWNCONSUMES.substring(0, p))) {
            xbreakdownID = new Integer(Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
        }
        this.xmachineID = xmachID;
        this.parentPanel = parentPanel;
    }

    @Override
    protected AbstractAction addAction() {
        //TODO
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (xbreakdownID == 0 && GeneralFrame.yesNo("Attention!",
                            "Do you want to save parent record first?") == JOptionPane.YES_OPTION) {
                        try {
                            if (parentPanel.save()) {
                                parentPanel.loadData();
                                Xbreakdown xb = (Xbreakdown) parentPanel.getDbObject();
                                xbreakdownID = xb.getXbreakdownId();
                            }
                        } catch (Exception ex) {
                            XlendWorks.logAndShowMessage(ex);
                        }
                    }

                    if (xbreakdownID != 0) {
                        EditBreakConsumeDialog.setXmachineID(xmachineID);
                        EditBreakConsumeDialog.setXbreakdownID(xbreakdownID);
                        EditBreakConsumeDialog bcd = new EditBreakConsumeDialog("Add Purchase", null);
                        if (bcd.okPressed) {
                            Xbreakconsume breakconsume = (Xbreakconsume) bcd.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect().replace("= 0", "= " + xbreakdownID),
                                    breakconsume.getXbreakconsumeId(), getPageSelector().getSelectedIndex());
                        }
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
        //TODO
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xbreakconsume xbc = (Xbreakconsume) exchanger.loadDbObjectOnID(Xbreakconsume.class, id);
                        EditBreakConsumeDialog.setXmachineID(xmachineID);
                        EditBreakConsumeDialog.setXbreakdownID(xbreakdownID);
                        new EditBreakConsumeDialog("Edit Purchase", xbc);
                        if (EditBreakConsumeDialog.okPressed) {
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
        //TODO
        return new AbstractAction("Del") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xbreakconsume xbc = (Xbreakconsume) exchanger.loadDbObjectOnID(Xbreakconsume.class, id);
                    if (xbc != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xbc);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }
//    /**
//     * @return the newPurchases
//     */
//    public static ArrayList<Xbreakconsume> getNewPurchases() {
//        return newPurchases;
//    }
}
