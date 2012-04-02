package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xsitediary;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class EditSiteDiaryPanel extends RecordEditPanel {

    private JTextField idField;
    private JSpinner dateSP;
    private DefaultComboBoxModel managerCbModel;
    private DefaultComboBoxModel siteCbModel;
    private JComboBox managerCB;
    private JComboBox siteCB;
    private JTextField siteForemanField;
    private JTextField siteNumberField;

    public EditSiteDiaryPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:",
            "Date:",
            "Site",
//            "Manager:",
            "Site Foreman:",
            "Foreman No:"
        };
        siteCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllSites(DashBoard.getExchanger())) {
            if (!ci.getValue().startsWith("--")) {
                siteCbModel.addElement(ci);
            }
        }
        managerCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XlendWorks.loadAllEmployees(DashBoard.getExchanger())) {
            managerCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[] {
            getGridPanel(idField = new JTextField(),7),
            getGridPanel(dateSP = new SelectedDateSpinner(),6),
            getGridPanel(new JComponent[] {
                comboPanelWithLookupBtn(siteCB = new JComboBox(siteCbModel), new SiteLookupAction(siteCB)),
                new JLabel("Manager:",SwingConstants.RIGHT),
                comboPanelWithLookupBtn(managerCB = new JComboBox(managerCbModel), new SiteLookupAction(managerCB)),
            }),
            getGridPanel(siteForemanField = new JTextField(),2),
            getGridPanel(siteNumberField = new JTextField(),2)
        };
        dateSP.setEditor(new JSpinner.DateEditor(dateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(dateSP);
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
    }

    @Override
    public void loadData() {
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean save() throws Exception {
        return false;
    }
}
