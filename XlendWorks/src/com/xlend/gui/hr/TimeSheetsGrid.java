package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.employee.EditTimeSheetDialog;
import com.xlend.gui.work.ClientsGrid;
import com.xlend.orm.Xemployee;
import com.xlend.orm.Xtimesheet;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class TimeSheetsGrid extends GeneralGridPanel {

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();
    private Xemployee xemployee = null;
    private static final String whereId = "xemployee_id = #";
    private boolean inEmloyee = false;
    private static boolean isInner = false;

    static {
        maxWidths.put(0, 40);
        maxWidths.put(1, 250);
        maxWidths.put(3, 400);
    }

    public TimeSheetsGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, Selects.SELECT_FROM_TIMESHEET, maxWidths, false);
    }

    public TimeSheetsGrid(IMessageSender exchanger, String slct, boolean readOnly) throws RemoteException {
        super(exchanger, slct, maxWidths, readOnly);
        int p = Selects.SELECT_TIMESHEETS4EMPLOYEE.indexOf(whereId);
        if (getSelect().startsWith(Selects.SELECT_TIMESHEETS4EMPLOYEE.substring(0, p))) {
            xemployee = (Xemployee) exchanger.loadDbObjectOnID(
                    Xemployee.class, Integer.parseInt(getSelect().substring(p + whereId.length() - 1)));
            inEmloyee = true;
        }
    }

    private AbstractAction getShowDuplicatesAction() {
        return new AbstractAction("Show duplicates") {
            @Override
            public void actionPerformed(ActionEvent e) {
                getParent().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                try {
                    isInner = true;
                    LookupDialog ld = new LookupDialog("Duplicated timesheets", null,
                            new TimeSheetsGrid(DashBoard.getExchanger(), Selects.SELECT_TIMESHEETSDUPLICATED, false),
                            null);
                } catch (RemoteException ex) {
                    isInner = false;
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
                getParent().setCursor(Cursor.getDefaultCursor());
            }
        };
    }

    @Override
    protected JPanel getRightPanel(JPanel btnPanel) {
        if (!isInner) {
            btnPanel.setLayout(new GridLayout(4, 1, 5, 5));
            btnPanel.add(new JButton(getShowDuplicatesAction()));
        }
        return super.getRightPanel(btnPanel);
    }

//    @Override
//    protected void activatePopup(AbstractAction addAction,
//            final AbstractAction editAction,
//            AbstractAction delAction) {
//        super.activatePopup(addAction, editAction, delAction);
//        popMenu.add(new AbstractAction("Show duplicates") {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    LookupDialog ld = new LookupDialog("Duplicated timesheets", null,
//                            new TimeSheetsGrid(DashBoard.getExchanger(), Selects.SELECT_TIMESHEETSDUPLICATED, false),
//                            null);
//                } catch (RemoteException ex) {
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        });
//    }
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("New Timesheet") {
            @Override
            public void actionPerformed(ActionEvent e) {
                EditTimeSheetDialog ed;
                try {
                    if (getXemployee() != null) {
                        EditTimeSheetDialog.xemployee = getXemployee();
                        ed = new EditTimeSheetDialog("New Timesheet", null);
                        if (EditTimeSheetDialog.okPressed) {
                            Xtimesheet ts = (Xtimesheet) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger,
                                    getTableView(), getTableDoc(), getSelect(), ts.getXtimesheetId(), getPageSelector().getSelectedIndex());
                        }
                    } else if (inEmloyee) {
                        GeneralFrame.infoMessageBox("Attention!", "Save contract please before adding orders");
                    } else {
                        ed = new EditTimeSheetDialog("New Timesheet", null);
                        if (EditTimeSheetDialog.okPressed) {
                            Xtimesheet ts = (Xtimesheet) ed.getEditPanel().getDbObject();
                            GeneralFrame.updateGrid(exchanger,
                                    getTableView(), getTableDoc(), getSelect(), ts.getXtimesheetId(), getPageSelector().getSelectedIndex());
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
        return new AbstractAction("Edit Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                if (id > 0) {
                    try {
                        Xtimesheet ts = (Xtimesheet) exchanger.loadDbObjectOnID(Xtimesheet.class, id);
                        if (getXemployee() != null) {
                            EditTimeSheetDialog.xemployee = getXemployee();
                            EditTimeSheetDialog od = new EditTimeSheetDialog("Edit time sheet for employee", ts);
                            if (EditTimeSheetDialog.okPressed) {
                                GeneralFrame.updateGrid(exchanger,
                                        getTableView(), getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
                            }
                        } else {
                            new EditTimeSheetDialog("Edit Timesheet", ts);
                            if (EditTimeSheetDialog.okPressed) {
                                GeneralFrame.updateGrid(exchanger, getTableView(),
                                        getTableDoc(), getSelect(), id, getPageSelector().getSelectedIndex());
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

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete Entry") {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = getSelectedID();
                try {
                    Xtimesheet ts = (Xtimesheet) exchanger.loadDbObjectOnID(Xtimesheet.class, id);
                    if (ts != null && GeneralFrame.yesNo("Attention!",
                            "Do you want to delete timesheet?") == JOptionPane.YES_OPTION) {
                        exchanger.deleteObject(ts);
                        GeneralFrame.updateGrid(exchanger, getTableView(), getTableDoc(), getSelect(), null, getPageSelector().getSelectedIndex());
                    }
                } catch (RemoteException ex) {
                    XlendWorks.log(ex);
                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
                }
            }
        };
    }

    /**
     * @return the xemployee
     */
    public Xemployee getXemployee() {
        return xemployee;
    }
}
