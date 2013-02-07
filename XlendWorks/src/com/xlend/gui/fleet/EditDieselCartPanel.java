package com.xlend.gui.fleet;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.site.IssueToDieselCartGrid;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xdieselcart;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
class EditDieselCartPanel extends AbstractMechDevicePanel {

    private DefaultComboBoxModel assignedVehicleCbModel;
    private SelectedNumberSpinner fleetNrSP;
    private JComboBox assignedVehicleCB;
    private JComboBox litresCB;
    private JLabel expLicDateLBL;
    private JLabel licStatusLBL;

    public EditDieselCartPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String titles[] = new String[]{
            "ID:", //"Fleet Number (D):", "Reg.Nr:"
            "Licensed:", //"Lic.Exp.Date:", "Lic.Status:"
            "",// "Litres:", "Chassis Nr:"
            "" //"Assigned to Vehicle:"
        };
//        litresCbModel = new DefaultComboBoxModel(new Object[]{"500", "1000", "5000"});
        assignedVehicleCbModel = new DefaultComboBoxModel();
        String selectVehicles = Selects.SELECT_FROM_MACHINE.replace("m.classify='M'", "m.classify='P' or m.classify='V'");
        for (ComboItem ci : XlendWorks.loadMachines(DashBoard.getExchanger(), selectVehicles)) {
            assignedVehicleCbModel.addElement(ci);
        }
        JComponent edits[] = new JComponent[]{
            getGridPanel(new JComponent[]{
                idField = new JTextField(),
                new JLabel("Fleet Number (D):", SwingConstants.RIGHT),
                getBorderPanel(new JComponent[]{fleetNrSP = new SelectedNumberSpinner(0, 0, 99999999, 1)}),
                new JLabel("Reg Nr:", SwingConstants.RIGHT),
                regNrField = new JTextField()
            }),
            getGridPanel(new JComponent[]{
                licensedChB = new JCheckBox("", true),
                expLicDateLBL = new JLabel("Lic.Exp.Date:", SwingConstants.RIGHT),
                expDateSP = new SelectedDateSpinner(),
                licStatusLBL = new JLabel("Lic.Status:", SwingConstants.RIGHT),
                licenseStatusLBL = new JLabel("Current")
            }),
            getGridPanel(new JComponent[]{
                new JPanel(),
                new JLabel("Litres:", SwingConstants.RIGHT),
                litresCB = new JComboBox(new Object[]{"500", "1000", "5000"}),
                new JLabel("Chassis Nr:", SwingConstants.RIGHT),
                chassisNrField = new JTextField()
            }),
            getGridPanel(new JComponent[]{
                new JPanel(),
                new JLabel("Assigned to Vehicle:", SwingConstants.RIGHT),
                comboPanelWithLookupBtn(assignedVehicleCB = new JComboBox(assignedVehicleCbModel),
                new MachineLookupAction(assignedVehicleCB, "(m.classify='P' or m.classify='V')")),
                new JPanel(),
                new JPanel()
            })
        };

        licenseExpSetup();
        expDateSP.setEditor(new JSpinner.DateEditor(expDateSP, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(expDateSP);
        idField.setEnabled(false);
        litresCB.setEditable(true);

        organizePanels(titles, edits, null);
        add(getTabbedPanel(), BorderLayout.CENTER);
    }

    protected void adjustLicenseFierlds() {
        super.adjustLicenseFierlds();
        expLicDateLBL.setVisible(licensed);
        licStatusLBL.setVisible(licensed);
    }

    protected JComponent getTabbedPanel() {
        JComponent pane = super.getTabbedPanel();
        Xdieselcart xc = (Xdieselcart) getDbObject();
        if (xc != null) {
            try {
                pane.add("Input",new IssueToDieselCartGrid(DashBoard.getExchanger(), xc.getXdieselcartId()));
                pane.add("Output",new JPanel());
            } catch (RemoteException ex) {
                XlendWorks.logAndShowMessage(ex);
            }
        }
        return pane;
    }

    @Override
    public void loadData() {
        Xdieselcart xc = (Xdieselcart) getDbObject();
        if (xc != null) {
            idField.setText(xc.getXdieselcartId().toString());
            if (xc.getExpdate() != null) {
                expDateSP.setValue(new java.util.Date(xc.getExpdate().getTime()));
                licensedChB.setSelected(true);
            }
            fleetNrSP.setValue(xc.getFleetNr());
            regNrField.setText(xc.getRegNr());
            licensedChB.setSelected(licensed = (xc.getExpdate() != null));
            chassisNrField.setText(xc.getChassisNr());
            litresCB.getEditor().setItem(xc.getLitres().toString());
            selectComboItem(assignedVehicleCB, xc.getAssignedId());
            imageData = (byte[]) xc.getPhoto();
            setImage(imageData);
            adjustLicenseFierlds();
        }
    }

    @Override
    public boolean save() throws Exception {
        Date dt;
        Xdieselcart xc = (Xdieselcart) getDbObject();
        boolean isNew = false;
        if (xc == null) {
            xc = new Xdieselcart(null);
            xc.setXdieselcartId(0);
            isNew = true;
        }
        if (!licensed) {
            xc.setExpdate(null);
        } else {
            dt = (Date) expDateSP.getValue();
            xc.setExpdate(new java.sql.Date(dt.getTime()));
        }
        xc.setFleetNr((Integer) fleetNrSP.getValue());
        xc.setAssignedId(getSelectedCbItem(assignedVehicleCB));
        xc.setRegNr(regNrField.getText());
        xc.setChassisNr(chassisNrField.getText());
        xc.setLitres(new Integer((String) litresCB.getSelectedItem()));
        xc.setPhoto(imageData);
        return saveDbRecord(xc, isNew);
    }
}
