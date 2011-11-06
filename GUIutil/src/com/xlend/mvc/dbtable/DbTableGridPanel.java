package com.xlend.mvc.dbtable;

import com.xlend.mvc.Controller;
//import com.iid.util.PopupListener;
import com.xlend.util.PopupListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public class DbTableGridPanel extends JPanel {

    private DbTableViewMarked tableView;
    private DbTableDocument tableDoc;
    private JScrollPane sp;
    private Controller controller;

    public DbTableGridPanel(
            AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction,
            DbTableDocument tableDoc, int[] widths, DbTableViewMarked tv) {
        super(new BorderLayout());
        tableView = (tv == null ? new DbTableViewMarked(true) : tv);
        this.tableDoc = tableDoc;
        if (widths != null) {
            for (int i = 0; i < widths.length; i++) {
                getTableView().setMaxColWidth(i, widths[i]);
            }
        }
        init(addAction, editAction, delAction);
    }

    public DbTableGridPanel(DbTableDocument tableDoc, int[] widths, DbTableViewMarked tv) {
        this(null, null, null, tableDoc, widths, tv);
    }

    private void init(AbstractAction addAction, final AbstractAction editAction, AbstractAction delAction) {
        if (tableView.isWithCopyMark()) {
            Object[] content = (Object[]) getTableDoc().getBody();
            Vector headers = (Vector) content[0];
            headers.add(0, "");
            Vector lines = (Vector) content[1];
            for (Object o : lines) {
                Vector line = (Vector) o;
                line.add(0, new Boolean(false));
            }
        }
        controller = new Controller(getTableDoc(), getTableView());
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel leftPanel = new JPanel(new BorderLayout());
        if (addAction != null || delAction != null || editAction != null) {
            btnPanel.add(new JPanel());
        }
        if (addAction != null) {
            btnPanel.add(new JButton(addAction));
        }
        if (editAction != null) {
            btnPanel.add(new JButton(editAction));
        }
        if (delAction != null) {
            btnPanel.add(new JButton(delAction));
        }
        leftPanel.add(btnPanel, BorderLayout.NORTH);
        add(sp = new JScrollPane(getTableView()), BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        tableView.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && editAction != null) {
                    editAction.actionPerformed(null);
                }
            }
        });
        activatePopup(addAction, editAction, delAction);
    }

    public DbTableGridPanel(
            AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction,
            Vector[] tableBody) {
        super(new BorderLayout());
        tableView = new DbTableViewMarked(true);
//        tableView.setMaxColWidth(0, 40);
        tableDoc = new DbTableDocument(toString(), tableBody);
        init(addAction, editAction, delAction);
    }

    public void activatePopup(AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction) {
        if (addAction != null || delAction != null || editAction != null) {
            JPopupMenu pop = new JPopupMenu();

            if (addAction != null) {
                pop.add(addAction);
            }
            if (editAction != null) {
                pop.add(editAction);
            }
            if (delAction != null) {
                pop.add(delAction);
            }
            tableView.addMouseListener(new PopupListener(pop));
            sp.addMouseListener(new PopupListener(pop));
        }
    }

    public int getSelectedID() {
        int row = tableView.getSelectedRow();
        if (row >= 0) {//&& row < tableView.getSelectedRow()) {
            Vector line = (Vector) tableView.getRowData().get(row);
            return Integer.parseInt((String) line.get(0));
        }
        return 0;
    }

    /**
     * @return the tableView
     */
    public DbTableViewMarked getTableView() {
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
}
