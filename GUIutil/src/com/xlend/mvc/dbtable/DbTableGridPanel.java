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

    private DbTableView tableView;
    private DbTableDocument tableDoc;
    private final JScrollPane sp;
    private final AbstractAction addAction;
    private final AbstractAction editAction;
    private final AbstractAction delAction;
    private final JButton addButton;
    private final JButton editButton;
    private final JButton delButton;

    public DbTableGridPanel(
            AbstractAction addAction,
            final AbstractAction editAction,
            AbstractAction delAction,
            Vector[] tableBody) {
        super(new BorderLayout());
        this.addAction = addAction;
        this.editAction = editAction;
        this.delAction = delAction;
        tableView = new DbTableView();
        tableView.setMaxColWidth(0, 40);
        tableDoc = new DbTableDocument(toString(), tableBody);
        new Controller(getTableDoc(), getTableView());
        JPanel btnPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel leftPanel = new JPanel(new BorderLayout());
//        btnPanel.add(new JPanel());
        btnPanel.add(addButton = new JButton(addAction));
        btnPanel.add(editButton = new JButton(editAction));
        btnPanel.add(delButton = new JButton(delAction));
        leftPanel.add(btnPanel, BorderLayout.NORTH);
        add(sp = new JScrollPane(getTableView()), BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);
        tableView.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
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
}
