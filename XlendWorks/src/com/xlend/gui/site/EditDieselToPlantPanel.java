package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.fleet.DieselCartLookupAction;
import com.xlend.gui.hr.EmployeeLookupAction;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditDieselToPlantPanel extends RecordEditPanel {

    private DefaultComboBoxModel driverCbModel;
    private DefaultComboBoxModel dieselCartCbModel;
    private JComboBox driverCB;
    private JComboBox dieselCartCB;
    private JTextField idField;
    private SelectedDateSpinner dateStartSP;
    private JLabel balanceInCartLBL;

    public EditDieselToPlantPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Diesel Cart:", // "Balance Available in Diesel Cart:"
            "Driver:"
        };
        driverCbModel = new DefaultComboBoxModel();
        dieselCartCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            driverCbModel.addElement(ci);
        }
        for (ComboItem ci : XlendWorks.loadAllDieselCarts(DashBoard.getExchanger())) {
            dieselCartCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getBorderPanel(new JComponent[]{idField = new JTextField()}),
            getBorderPanel(new JComponent[]{dateStartSP = new SelectedDateSpinner()}),
            getBorderPanel(new JComponent[]{
                comboPanelWithLookupBtn(dieselCartCB = new JComboBox(dieselCartCbModel), new DieselCartLookupAction(dieselCartCB)),
                new JLabel("    Balance Available in Diesel Cart:", SwingConstants.RIGHT),
                balanceInCartLBL = new JLabel("0", SwingConstants.RIGHT)
            }),
            getBorderPanel(new JComponent[]{comboPanelWithLookupBtn(
                driverCB = new JComboBox(driverCbModel), new EmployeeLookupAction(driverCB))
            })
        };
        idField.setEnabled(false);
        idField.setPreferredSize(dateStartSP.getPreferredSize());
        dateStartSP.setEditor(new JSpinner.DateEditor(dateStartSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateStartSP);
        balanceInCartLBL.setPreferredSize(dateStartSP.getPreferredSize());
        balanceInCartLBL.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
        //
    }

    @Override
    public boolean save() throws Exception {
        return true;
    }
}
