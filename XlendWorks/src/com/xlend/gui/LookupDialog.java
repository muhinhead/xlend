package com.xlend.gui;

import com.xlend.orm.dbobject.ComboItem;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class LookupDialog extends PopupDialog {

    private static Integer choosedID;
    private JButton okBtn, cancelBtn;
    private AbstractAction okAction, cancelAction;
    private GeneralGridPanel grid;
    private String[] filteredColumns;
    private JComboBox comboBox;
    private JTextField filterField;
    private String originalSelect;

    public LookupDialog(String title, JComboBox cb, GeneralGridPanel grid, String[] filteredColumns) {
        super(null, title, new Object[]{cb, grid, filteredColumns});
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        XlendWorks.setWindowIcon(this, "Xcost.png");
        Object[] params = (Object[]) getObject();
        comboBox = (JComboBox) params[0];
        if (comboBox.getSelectedItem() != null) {
            choosedID = ((ComboItem) comboBox.getSelectedItem()).getId();
            grid = (GeneralGridPanel) params[1];
            grid.selectRowOnId(choosedID);
            this.filteredColumns = (String[]) params[2];
            originalSelect = grid.getSelect();

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

            JPanel centerPanel = new JPanel(new BorderLayout());

            JPanel upCenterPanel = new JPanel(new FlowLayout());
            upCenterPanel.add(new JLabel("Filter:"));
            upCenterPanel.add(filterField = new JTextField(40));
            centerPanel.add(upCenterPanel, BorderLayout.NORTH);
            centerPanel.add(new JScrollPane(grid), BorderLayout.CENTER);
            getContentPane().add(centerPanel, BorderLayout.CENTER);

            filterField.addKeyListener(new KeyAdapter() {

                @Override
                public void keyReleased(KeyEvent e) {
                    String select = originalSelect;
                    int w = findNotInParents(select.toLowerCase()," where");//select.toLowerCase().lastIndexOf(" where");
                    int o = findNotInParents(select.toLowerCase()," order by");
                    o = o < w ? -1 : o;
                    StringBuilder addWhereCond = new StringBuilder();
                    for (String col : filteredColumns) {
                        addWhereCond.append(addWhereCond.length() > 0 ? " or " : "(").append("upper(").append(col).append(") like '%").append(filterField.getText().toUpperCase()).append("%'");
                    }
                    if (addWhereCond.length() > 0) {
                        addWhereCond.append(")");
                    }

                    if (w < 0 && o < 0) {
                        select += (" where " + addWhereCond.toString());
                    } else if (w < 0 && o > 0) {
                        select = select.substring(0, o) + " where " + addWhereCond.toString() + select.substring(o);
                    } else {
                        select = select.substring(0, w + 7) + addWhereCond.toString() + " aNd " + select.substring(w + 7);//select.substring(o);
                    }
                    grid.setSelect(select);
                    try {
                        GeneralFrame.updateGrid(DashBoard.getExchanger(),
                                grid.getTableView(), grid.getTableDoc(), grid.getSelect(), null);
                    } catch (RemoteException ex) {
                        XlendWorks.log(ex);
                    }
                }
            });

            okBtn = new JButton(okAction = selectionAction("Pick up"));
            getRootPane().setDefaultButton(okBtn);
            cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel") {

                @Override
                public void actionPerformed(ActionEvent e) {
                    choosedID = null;
                    dispose();
                }
            });
            buttonPanel.add(okBtn);
            buttonPanel.add(cancelBtn);
            getContentPane().add(buttonPanel, BorderLayout.SOUTH);

            grid.getTableView().removeMouseListener(grid.getDoubleClickAdapter());
            grid.getTableView().addMouseListener(new MouseAdapter() {

                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        okAction.actionPerformed(null);
                    }
                }
            });
        } else {
            GeneralFrame.errMessageBox("Sorry!", "Empty combobox, couldn't build lookup dialog");
            dispose();
        }
    }

    private static int findNotInParents(String source, String search) {
        int level = 0;
        for (int i = 0; i < source.length(); i++) {
            String sub = source.substring(i);
            if (sub.startsWith("(")) {
                level++;
            } else if (sub.startsWith(")")) {
                level--;
            } else if (sub.startsWith(search) && level == 0) {
                return i;
            }
        }
        return -1;
    }

    private AbstractAction selectionAction(String title) {
        return new AbstractAction(title) {

            @Override
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                choosedID = grid.getSelectedID();
                for (int i = 0; i < comboBox.getItemCount(); i++) {
                    ComboItem citm = (ComboItem) comboBox.getItemAt(i);
                    if (citm.getId() == choosedID) {
                        comboBox.setSelectedIndex(i);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ComboItem newItem;
                    DefaultComboBoxModel cbModel = (DefaultComboBoxModel) comboBox.getModel();
                    cbModel.addElement(newItem = new ComboItem(choosedID, grid.getSelectedRowCeil(1)));
                    comboBox.setSelectedItem(newItem);
                }
                dispose();
            }
        };
    }

    /**
     * @return the choosed
     */
    public static Integer getChoosed() {
        return choosedID;
    }

    @Override
    public void freeResources() {
        if (okBtn != null && okAction != null) {
            okBtn.removeActionListener(okAction);
        }
        okAction = null;
        if (cancelBtn != null && cancelAction != null) {
            cancelBtn.removeActionListener(cancelAction);
        }
        cancelAction = null;
    }
}
