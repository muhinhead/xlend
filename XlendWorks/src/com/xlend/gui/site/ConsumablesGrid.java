package com.xlend.gui.site;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xconsume;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class ConsumablesGrid extends GeneralGridPanel {

//    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
//
//    static {
//        maxWidths.put(0, 40);
//    }
    public ConsumablesGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_CONSUMABLES, getMaxWidths(new int[]{40}), false);
    }

    public ConsumablesGrid(IMessageSender exchanger, String select, boolean readonly) throws RemoteException {
        super(exchanger, select, getMaxWidths(new int[]{40, 100, 100}), readonly);
    }

    @Override
    protected void init(AbstractAction[] acts, String select, Vector[] tableBody, HashMap<Integer, Integer> maxWidths, DbTableView tabView) {
        super.init(acts, select, tableBody, maxWidths, tabView);
//        alignRight(0);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add Consumable") {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditConsumableDialog ed = new EditConsumableDialog("Add Consumable", null);
                    if (EditConsumableDialog.okPressed) {
                        Xconsume xcns = (Xconsume) ed.getEditPanel().getDbObject();
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xcns.getXconsumeId(), getPageSelector().getSelectedIndex());
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
                        Xconsume xcns = (Xconsume) exchanger.loadDbObjectOnID(Xconsume.class, id);
                        new EditConsumableDialog("Edit Consumable", xcns);
                        if (EditConsumableDialog.okPressed) {
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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
                    Xconsume xcns = (Xconsume) exchanger.loadDbObjectOnID(Xconsume.class, id);
                    if (xcns != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete consumable?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(xcns);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }

            }
        };
    }

    @Override
    protected JPanel getRightPanel(JPanel btnPanel) {
        JPanel rightPanel = super.getRightPanel(btnPanel);
        JPanel rightCenterPanel = new JPanel(new BorderLayout());
        rightPanel.add(rightCenterPanel, BorderLayout.CENTER);
        JPanel inoutButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        inoutButtonPanel.add(new JPanel());
        JButton dupBtn;
        inoutButtonPanel.add(dupBtn = new JButton(getDuplicateAction("Duplicate")));
        rightCenterPanel.add(inoutButtonPanel, BorderLayout.NORTH);
        dupBtn.setToolTipText("Duplicate selected row");
        return rightPanel;
    }

    private AbstractAction getDuplicateAction(final String label) {
        return new AbstractAction(label) {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (GeneralFrame.yesNo("Attention!", "Please confirm duplication of item") == JOptionPane.YES_OPTION) {
                    try {
                        int id = getSelectedID();
                        EditConsumablePanel.sampleRecord = (Xconsume) exchanger.loadDbObjectOnID(Xconsume.class, id);
                        EditConsumableDialog ed = new EditConsumableDialog("Duplicate Consumable", null);
                        if (EditConsumableDialog.okPressed) {
                            Xconsume xcns = (Xconsume) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), xcns.getXconsumeId(), getPageSelector().getSelectedIndex());
                        }
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                        GeneralFrame.errMessageBox("Error:", ex.getMessage());
                    } finally {
                        EditConsumablePanel.sampleRecord = null;
                    }
                }
            }
        };
    }
}
