package com.xlend.gui.reports;

import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Sheet;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.PopupDialog;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class ReportsMenuDialog extends PopupDialog {

    private JButton okButton;
    private JButton cancelButton;
    private AbstractAction okAction, cancelAction;
    private HashMap<JToggleButton, JCheckBox[]> checkBoxMap;
    private ArrayList<JToggleButton> btns;
    private JPanel mainPanel;
    public static boolean okPressed;
    private static HashMap<String, JCheckBox> reportsChecked;

    static {
        okPressed = false;
        reportsChecked = new HashMap<String, JCheckBox>();
    }

    public ReportsMenuDialog() {
        super(DashBoard.ourInstance, "Choose reports to show", null);
    }

    public static boolean isCheckedReport(String repName) {
        JCheckBox cb = reportsChecked.get(repName);
        boolean ok = false;
        if (cb != null) {
            ok = cb.isSelected();
        }
        return ok;
    }

    @Override
    protected void fillContent() {
        okPressed = false;
        super.fillContent();
        checkBoxMap = new HashMap<JToggleButton, JCheckBox[]>();
        btns = new ArrayList<JToggleButton>();
        initialFillContent();
    }

    private void initialFillContent() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        //reportsChecked.clear();

        DbObject[] repGrpSheets = XlendWorks.loadReportGrpSheets(DashBoard.getExchanger());
        for (DbObject rec : repGrpSheets) {
            Sheet sheet = (Sheet) rec;
            JToggleButton grpButton = new JToggleButton(toggleButtonAction(sheet.getSheetname()));
            grpButton.setFont(grpButton.getFont().deriveFont(Font.BOLD, 14));
            JPanel linePanel = new JPanel(new GridLayout(1, 1));
            linePanel.add(grpButton);
            mainPanel.add(linePanel);
            ComboItem[] reportSheets = XlendWorks.loadReportGroup(DashBoard.getExchanger(), sheet.getSheetId());
            JCheckBox[] childCbs = new JCheckBox[reportSheets.length];
            for (int i = 0; i < reportSheets.length; i++) {
                if (reportsChecked.get(reportSheets[i].getValue()) != null) {
                    childCbs[i] = reportsChecked.get(reportSheets[i].getValue());
                } else {
                    childCbs[i] = new JCheckBox(reportSheets[i].getValue());
                    reportsChecked.put(reportSheets[i].getValue(), childCbs[i]);
                }
                childCbs[i].setEnabled(reportSheets[i].getId() > 0);
            }
            btns.add(grpButton);
            checkBoxMap.put(grpButton, childCbs);
        }
        mainPanel.setPreferredSize(new Dimension(300, mainPanel.getPreferredSize().height));

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.add(okButton = new JButton(okAction = new AbstractAction("Ok") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = true;
                dispose();
            }
        }));
        btnPanel.add(cancelButton = new JButton(cancelAction = new AbstractAction("Cancel") {

            @Override
            public void actionPerformed(ActionEvent e) {
                okPressed = false;
                dispose();
            }
        }));
        getRootPane().setDefaultButton(okButton);

        getContentPane().add(new JPanel(), BorderLayout.WEST);
        getContentPane().add(new JPanel(), BorderLayout.EAST);
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    private AbstractAction toggleButtonAction(String sheetName) {
        return new AbstractAction(sheetName) {

            @Override
            public void actionPerformed(ActionEvent e) {
                JToggleButton repGrdpBtn = (JToggleButton) e.getSource();
                if (repGrdpBtn.isSelected()) {
                    repaintMainPanel(repGrdpBtn);
                } else {
                    repaintMainPanel(null);
                }
            }
        };
    }

    private void repaintMainPanel(JToggleButton repGrdpBtn) {
        mainPanel.removeAll();
        getContentPane().remove(mainPanel);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        for (JToggleButton tb : btns) {
            JPanel linePanel = new JPanel(new GridLayout(1, 1));
            linePanel.add(tb);
            mainPanel.add(linePanel);
            if (tb != repGrdpBtn) {
                if (tb.isSelected()) {
                    tb.setSelected(false);
                }
            } else {
                JCheckBox[] cbs = checkBoxMap.get(tb);
                for (JCheckBox cb : cbs) {
                    linePanel = new JPanel(new BorderLayout());
                    linePanel.add(new JPanel(), BorderLayout.WEST);
                    linePanel.add(cb);
                    mainPanel.add(linePanel);
                }
            }
        }
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        pack();
    }

    @Override
    public void freeResources() {
        okButton.removeActionListener(okAction);
        okAction = null;
        cancelButton.removeActionListener(cancelAction);
        cancelAction = null;
    }
}
