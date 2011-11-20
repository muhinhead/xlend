package com.xlend.gui;

import com.xlend.orm.dbobject.ComboItem;
import com.xlend.remote.IMessageSender;
//import com.xlend.util.ComboItem;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Nick Mukhin
 */
public class LookupDialog extends PopupDialog {

    private static Integer choosedID;
    private JButton okBtn, cancelBtn;
    private AbstractAction okAction, cancelAction;
    private GeneralGridPanel grid;
    private JComboBox comboBox;

    public LookupDialog(String title, JComboBox cb, GeneralGridPanel grid) {
        super(null, title, new Object[]{cb, grid});
    }

    protected void fillContent() {
        super.fillContent();
        Object[] params = (Object[]) getObject();
        comboBox = (JComboBox) params[0];
        choosedID = ((ComboItem) comboBox.getSelectedItem()).getId();
        grid = (GeneralGridPanel) params[1];
        grid.selectRowOnId(choosedID);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);

        okBtn = new JButton(okAction = selectionAction());
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
    }

    private AbstractAction selectionAction() {
        return new AbstractAction("Ok") {

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
        okBtn.removeActionListener(okAction);
        okBtn = null;
        cancelBtn.removeActionListener(cancelAction);
        cancelBtn = null;
    }
}
