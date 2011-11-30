package com.xlend.mvc.dbtable;

import com.xlend.mvc.Controller;
//import com.iid.util.PopupListener;
import com.xlend.util.PopupListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
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

    private DbTableView tableView = null;
    private DbTableDocument tableDoc = null;
    private JScrollPane sp = null;
    private AbstractAction addAction = null;
    private AbstractAction editAction = null;
    private AbstractAction delAction = null;
    private JButton addButton = null;
    private JButton editButton = null;
    private JButton delButton = null;
    private MouseAdapter doubleClickAdapter;

    public DbTableGridPanel(
            AbstractAction addAction,
            AbstractAction editAction,
            AbstractAction delAction,
            Vector[] tableBody) {
        this(addAction, editAction, delAction, tableBody, null);
    }

    public DbTableGridPanel() {
        super(new BorderLayout());
    }

    public DbTableGridPanel(
            AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction,
            Vector[] tableBody, HashMap<Integer, Integer> maxWidths) {
        this();
        init(addAction, editAction, delAction, tableBody, maxWidths);
    }

    protected void init(AbstractAction add, AbstractAction edit,
            AbstractAction del, Vector[] tableBody, HashMap<Integer, Integer> maxWidths) {
        this.setAddAction(add);
        this.setEditAction(edit);
        this.setDelAction(del);
        tableView = new DbTableView();
        if (maxWidths != null) {
            tableView.setMaxColWidths(maxWidths);
        } else {
            tableView.setMaxColWidth(0, 40);
        }
        tableDoc = new DbTableDocument(toString(), tableBody);
        new Controller(getTableDoc(), getTableView());
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel leftPanel = new JPanel(new BorderLayout());
        if (addAction != null) {
            btnPanel.add(addButton = new JButton(addAction));
        }
        if (editAction != null) {
            btnPanel.add(editButton = new JButton(editAction));
        }
        if (delAction != null) {
            btnPanel.add(delButton = new JButton(delAction));
        }
        leftPanel.add(btnPanel, BorderLayout.NORTH);
        add(sp = new JScrollPane(getTableView()), BorderLayout.CENTER);
        add(leftPanel, BorderLayout.EAST);
        tableView.addMouseListener(doubleClickAdapter = new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && editAction != null) {
                    editAction.actionPerformed(null);
                }
            }
        });
        activatePopup(addAction, editAction, delAction);
    }

    public void activatePopup(AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction) {
        JPopupMenu pop = new JPopupMenu();

        pop.add(addAction);
        pop.add(editAction);
        pop.add(delAction);
        tableView.addMouseListener(new PopupListener(pop));
        sp.addMouseListener(new PopupListener(pop));
    }

    public void selectRowOnId(int id) {
        selectRowOnId(tableView, id);
    }
    
    public static void selectRowOnId(DbTableView view, int id) {
        for (int row = 0; row < view.getRowData().size(); row++) {
            Vector line = (Vector) view.getRowData().get(row);
            try {
                if (Integer.parseInt(line.get(0).toString()) == id) {
                    view.setSelectedRow(row);
                    break;
                }
            } catch (NumberFormatException ne) {
            }
        }
    }
    
//    public int getRowOnId(int id) {
//        for (int r=0; r<tableView.getRowCount(); r++) {
//            Vector line = (Vector) tableView.getRowData().get(r);
//            Integer iid = Integer.parseInt((String) line.get(0));
//            if (iid.intValue()==id)
//                return r;
//        }
//        return -1;
//    }

    public int getSelectedID() {
        int row = tableView.getSelectedRow();
        if (row >= 0 && row < tableView.getRowCount()) {//&& row < tableView.getSelectedRow()) {
            Vector line = (Vector) tableView.getRowData().get(row);
            return Integer.parseInt((String) line.get(0));
        }
        return 0;
    }
    
    public String getSelectedRowCeil(int col) {
        int row = tableView.getSelectedRow();
        if (row >= 0) {//&& row < tableView.getSelectedRow()) {
            Vector line = (Vector) tableView.getRowData().get(row);
            return (String) line.get(col);
        }
        return "";
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
     * @return the addAction
     */
    public AbstractAction getAddAction() {
        return addAction;
    }

    /**
     * @return the editAction
     */
    public AbstractAction getEditAction() {
        return editAction;
    }

    /**
     * @return the delAction
     */
    public AbstractAction getDelAction() {
        return delAction;
    }

    /**
     * @return the addButton
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     * @return the editButton
     */
    public JButton getEditButton() {
        return editButton;
    }

    /**
     * @return the delButton
     */
    public JButton getDelButton() {
        return delButton;
    }

    /**
     * @return the doubleClickAdapter
     */
    public MouseAdapter getDoubleClickAdapter() {
        return doubleClickAdapter;
    }

    /**
     * @param addAction the addAction to set
     */
    public void setAddAction(AbstractAction addAction) {
        this.addAction = addAction;
    }

    /**
     * @param editAction the editAction to set
     */
    public void setEditAction(AbstractAction editAction) {
        this.editAction = editAction;
    }

    /**
     * @param delAction the delAction to set
     */
    public void setDelAction(AbstractAction delAction) {
        this.delAction = delAction;
    }
}
