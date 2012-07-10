package com.xlend.gui.assign;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.SiteLookupAction;
import com.xlend.orm.Xemployee;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;

/**
 *
 * @author nick
 */
class ChooseDepotForOperatorDialog extends PopupDialog {

    private DefaultComboBoxModel siteCbModel;
    private JButton okButton;
    private AbstractAction okAction;
    private JComboBox siteCB;
    private static int depot_id;

    public ChooseDepotForOperatorDialog(Xemployee prevOperator) {
        super(null, "Choose depot for " + prevOperator.getFirstName() + " "
                + prevOperator.getSurName() + " (" + prevOperator.getClockNum() + ")", prevOperator);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        depot_id = 0;
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadSites(DashBoard.getExchanger(), "sitetype='D'")) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
                if (getDepot_id() == 0) {
                    depot_id = ci.getId();
                }
            }
        }
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel upperMainPanel = new JPanel(new BorderLayout());
        mainPanel.add(upperMainPanel, BorderLayout.NORTH);
        upperMainPanel.add(new JLabel("   Depot:"), BorderLayout.WEST);
        upperMainPanel.add(comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel),
                new SiteLookupAction(siteCB, Selects.SELECT_DEPOTS4LOOKUP)));
        add(mainPanel);
        JPanel btnPanel = new JPanel(new FlowLayout());
        btnPanel.add(okButton = new JButton(okAction = new AbstractAction(" Ok ") {

            @Override
            public void actionPerformed(ActionEvent e) {
                ComboItem ci = (ComboItem) siteCB.getSelectedItem();
                if (ci != null) {
                    depot_id = ci.getId();
                } else {
                    depot_id = 0;
                }
                dispose();
            }
        }));
        add(btnPanel, BorderLayout.SOUTH);
    }

    private JPanel comboPanelWithLookupBtn(JComboBox cb, AbstractAction lookupButtonAction) {
        JPanel comboBoxPanel = new JPanel(new BorderLayout());
        comboBoxPanel.add(cb);
        if (lookupButtonAction != null) {
            comboBoxPanel.add(new JButton(lookupButtonAction), BorderLayout.EAST);
        }
        return comboBoxPanel;
    }

    @Override
    public void freeResources() {
        okButton.removeActionListener(okAction);
        okAction = null;
    }

    /**
     * @return the depot_id
     */
    public static int getDepot_id() {
        return depot_id;
    }
}
