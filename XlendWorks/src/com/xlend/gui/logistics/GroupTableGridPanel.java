package com.xlend.gui.logistics;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.Controller;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.orm.Xtransscheduleitm;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.*;
import java.awt.event.*;
import java.rmi.RemoteException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class GroupTableGridPanel extends JPanel {

    protected IMessageSender exchanger;
    private DbTableView tableView = null;
    private DbTableDocument tableDoc = null;
    private Controller controller = null;
    private JCheckBox dateCB;
    private JCheckBox dateMachineCB;
    private JCheckBox dateFromSiteCB;
    private JCheckBox dateToSiteCB;

    public GroupTableGridPanel(IMessageSender exchanger, String select) {
        super(new BorderLayout());
        this.exchanger = exchanger;
        tableView = new DbTableView();
        try {
            if (select == null) {
                Vector columnVector = new Vector();
                columnVector.add("Date");
                columnVector.add("Qty");
                tableDoc = new DbTableDocument("", new Object[]{columnVector, new Vector()});
            } else {
                tableDoc = new DbTableDocument("", exchanger.getTableBody(select));
            }
            controller = new Controller(tableDoc, tableView);
            add(createUpperPanel(), BorderLayout.NORTH);
            add(createCenterPanel(), BorderLayout.CENTER);
            add(createRightButtonPanel(new JComponent[]{
                        new JButton(getAddAction("New schedule line")),
                        new JButton(getEditAction("Edit schedule")),
                        new JButton(getDelAction("Delete schedule"))
                    }), BorderLayout.EAST);

            tableView.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        editSchedule("Edit schedule");
                    }
                }
            });
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }

    private AbstractAction getAddAction(final String title) {
        return new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSchedule(title);
            }
        };
    }

    private void addSchedule(String title) {
        try {
            EditTransscheduleitmDialog ed = new EditTransscheduleitmDialog(title, null);
            if (EditTransscheduleitmDialog.okPressed) {
                updateGrid();
            }
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }

    private AbstractAction getEditAction(final String title) {
        return new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                editSchedule(title);
            }
        };
    }

    private AbstractAction getDelAction(final String title) {
        return new AbstractAction(title) {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSchedule();
            }
        };
    }

    private void deleteSchedule() {
        int row = tableView.getSelectedRow();
        if (row >= 0 && row < tableView.getRowCount()) {
            try {
                DbObject[] recs = getRecs(row);
                if (GeneralFrame.yesNo("Attention!", "Do you want to delete schedule of "
                        + recs.length + " item"
                        + (recs.length > 1 ? "s" : "") + "?") == JOptionPane.YES_OPTION) {
                    for (DbObject rec : recs) {
                        exchanger.deleteObject(rec);
                    }
                    updateGrid();
                }
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
    }

    private DbObject[] getRecs(int row) throws RemoteException {
        Vector line = (Vector) tableView.getRowData().get(row);
        String dateReq = (String) line.get(0);
        DbObject[] recs = null;
        if (dateCB.isSelected()) {
            recs = exchanger.getDbObjects(Xtransscheduleitm.class,
                    "to_char(date_required,'DD/MM/YYYY')='" + dateReq + "'", null);
            EditTransscheduleitmPanel.resetFixedAttribute();
        } else if (dateMachineCB.isSelected()) {
            String machineName = (String) line.get(1);
            recs = exchanger.getDbObjects(Xtransscheduleitm.class,
                    "to_char(date_required,'DD/MM/YYYY')='" + dateReq + "' and machine_id="
                    + "(select max(xmachine_id) from xmachine where concat(classify,tmvnr)='" + machineName + "')", null);
//                    EditTransscheduleitmPanel.setFixedAttribute("machine_id");
        } else if (dateFromSiteCB.isSelected()) {
            String siteName = (String) line.get(1);
            recs = exchanger.getDbObjects(Xtransscheduleitm.class,
                    "to_char(date_required,'DD/MM/YYYY')='" + dateReq + "' and site_from_id="
                    + "(select max(xsite_id) from xsite where name='" + siteName + "')", null);
//                    EditTransscheduleitmPanel.setFixedAttribute("site_from_id");
        } else if (dateToSiteCB.isSelected()) {
            String siteName = (String) line.get(1);
            recs = exchanger.getDbObjects(Xtransscheduleitm.class,
                    "to_char(date_required,'DD/MM/YYYY')='" + dateReq + "' and site_to_id="
                    + "(select max(xsite_id) from xsite where name='" + siteName + "')", null);
//                    EditTransscheduleitmPanel.setFixedAttribute("site_to_id");
        }
        return recs;
    }

    private void editSchedule(String title) {
        int row = tableView.getSelectedRow();
        if (row >= 0 && row < tableView.getRowCount()) {
            try {
                DbObject[] recs = getRecs(row);
                if (recs != null) {
                    EditTransscheduleitmDialog ed = new EditTransscheduleitmDialog(title, recs);
                    if (EditTransscheduleitmDialog.okPressed) {
                        updateGrid();
                    }
                }
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
    }

    private JComponent createUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
        fillUpperPanel(upperPanel);
        return new JScrollPane(upperPanel);
    }

    /**
     * @return the tableView
     */
    public DbTableView getTableView() {
        return tableView;
    }

    /**
     * @return the tableDoc
     */
    public DbTableDocument getTableDoc() {
        return tableDoc;
    }

    /**
     * @return the controller
     */
    public Controller getController() {
        return controller;
    }

    protected void fillUpperPanel(JPanel upperPanel) {
        JLabel lbl;
        upperPanel.add(lbl = new JLabel(" Schedule on:", SwingConstants.RIGHT));
        lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
        lbl.setForeground(Color.blue);
        upperPanel.add(dateCB = new JCheckBox("Date Required"));
        upperPanel.add(dateMachineCB = new JCheckBox("Date & Machine"));
        upperPanel.add(dateFromSiteCB = new JCheckBox("Date & Source Site"));
        upperPanel.add(dateToSiteCB = new JCheckBox("Date & Target Site"));
        ButtonGroup grp = new ButtonGroup();
        grp.add(dateCB);
        grp.add(dateMachineCB);
        grp.add(dateFromSiteCB);
        grp.add(dateToSiteCB);
        dateCB.setSelected(true);
        ItemListener itmLsnr = getItemListener();
        for (JCheckBox cb : new JCheckBox[]{dateCB, dateFromSiteCB, dateToSiteCB, dateMachineCB}) {
            cb.addItemListener(getItemListener());
        }

    }

    private ItemListener getItemListener() {
        return new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                try {
                    updateGrid();
                } catch (RemoteException ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
        };
    }

    private void updateGrid() throws RemoteException {
        String newSelect = "";
        if (dateMachineCB.isSelected()) {
            newSelect = Selects.SELECT_TRANSSCHEDULE_BY_DATE_AND_MACHINE;
        } else if (dateFromSiteCB.isSelected()) {
            newSelect = Selects.SELECT_TRANSSCHEDULE_BY_DATE_AND_FROMSITE;
        } else if (dateToSiteCB.isSelected()) {
            newSelect = Selects.SELECT_TRANSSCHEDULE_BY_DATE_AND_TOSITE;
        } else {
            newSelect = Selects.SELECT_TRANSSCHEDULE_BY_DATE;
        }
        tableDoc.setBody(exchanger.getTableBody(newSelect));
        controller.updateExcept(null);
        tableView.invalidate();
    }

    private JComponent createCenterPanel() {
        return new JScrollPane(tableView);
    }

    private JComponent createRightButtonPanel(JComponent[] comps) {
        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new GridLayout(comps.length, 1, 5, 5));
        for (JComponent c : comps) {
            btnPanel.add(c);
        }
        JPanel shellPanel = new JPanel(new BorderLayout());
        shellPanel.add(btnPanel, BorderLayout.NORTH);
        return shellPanel;
    }
}
