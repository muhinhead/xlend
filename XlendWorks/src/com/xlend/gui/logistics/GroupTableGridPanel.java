package com.xlend.gui.logistics;

import com.xlend.constants.Selects;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.Controller;
import com.xlend.mvc.dbtable.DbTableDocument;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.remote.IMessageSender;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.util.Vector;
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
                        new JButton("Edit schedule")
                    }), BorderLayout.EAST);
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
    }

    private AbstractAction getAddAction(final String title) {
        return new AbstractAction(title) {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EditTransscheduleitmDialog ed = new EditTransscheduleitmDialog(title, null);
                    if (EditTransscheduleitmDialog.okPressed) {
                        updateGrid();
                    }
                } catch (RemoteException ex) {
                    XlendWorks.logAndShowMessage(ex);
                }
            }
        };

    }

    private JComponent createUpperPanel() {
        JPanel upperPanel = new JPanel();
        upperPanel.setLayout(new BoxLayout(upperPanel, BoxLayout.X_AXIS));
        fiilUpperPanel(upperPanel);
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

    protected void fiilUpperPanel(JPanel upperPanel) {
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
