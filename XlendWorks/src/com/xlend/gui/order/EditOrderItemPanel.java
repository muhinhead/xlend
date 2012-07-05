package com.xlend.gui.order;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xorderitem;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.SelectedNumberSpinner;
import com.xlend.util.Util;
import java.awt.GridLayout;
import java.sql.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class EditOrderItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField itemNumberField;
    private JComboBox machineTypeCB;
    private DefaultComboBoxModel machineTypeCbModel;
    private JSpinner requiredDateSpin;
    private JSpinner deliverDateSpin;
    private JSpinner quantitySpin;
    private JComboBox measureItemCB;
    private JSpinner priceOneSpin;
    private JSpinner totalValSpin;
    private Xorder xorder;

    public EditOrderItemPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        machineTypeCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadRootMachTypes(DashBoard.getExchanger(), "T','M")) {
            machineTypeCbModel.addElement(itm);
        }
        String[] titles = new String[]{
            "ID:",
            "Item Nr:", //"Material Number:", 
            "Machine Type:", //"Description:",
            "Required:", "Delivery Date:", "Order Quantity:",
            "Measure Item", "Price per Unit:", "Total Value for item:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            itemNumberField = new JTextField(),
            machineTypeCB = new JComboBox(machineTypeCbModel),
            requiredDateSpin = new SelectedDateSpinner(),
            deliverDateSpin = new SelectedDateSpinner(),
            quantitySpin = new JSpinner(new SpinnerNumberModel()),
            measureItemCB = new JComboBox(new String[]{"HRS", "RANDS"}),
            priceOneSpin = new SelectedNumberSpinner(0.0, 0.0, 1000000.00, 0.1),
            totalValSpin = new SelectedNumberSpinner(0.0, 0.0, 1000000.00, 0.1)
        };
        totalValSpin.setEnabled(false);
        requiredDateSpin.setEditor(new JSpinner.DateEditor(requiredDateSpin, "dd/MM/yyyy"));
        deliverDateSpin.setEditor(new JSpinner.DateEditor(deliverDateSpin, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(requiredDateSpin);
        Util.addFocusSelectAllAction(deliverDateSpin);
        organizePanels(titles, edits);

        priceOneSpin.addChangeListener(calcTotalValueAction());
        quantitySpin.addChangeListener(calcTotalValueAction());
    }

    protected void organizePanels(String[] titles, JComponent[] edits) {
        super.organizePanels(titles.length, edits.length);
        labels = createLabelsArray(titles);
        for (int i = 0; i < labels.length; i++) {
            lblPanel.add(labels[i]);
        }
        JPanel idPanel = new JPanel(new GridLayout(1, 3));
        idPanel.add(edits[0]);
        edits[0].setEnabled(false);
        idPanel.add(new JPanel());
        idPanel.add(new JPanel());
        editPanel.add(idPanel);
        for (int i = 1; i < edits.length; i++) {
            editPanel.add(edits[i]);
        }
    }

    @Override
    public void loadData() {
        Xorderitem xorditem = (Xorderitem) getDbObject();
        if (xorditem != null) {
            idField.setText(xorditem.getXorderitemId().toString());
            itemNumberField.setText(xorditem.getItemnumber());
            if (xorditem.getXmachtypeId() != null) {
                selectComboItem(machineTypeCB, xorditem.getXmachtypeId());
            }
            if (xorditem.getDeliveryreq() != null) {
                requiredDateSpin.setValue(new java.sql.Date(xorditem.getDeliveryreq().getTime()));
            }
            if (xorditem.getDelivery() != null) {
                deliverDateSpin.setValue(new java.sql.Date(xorditem.getDelivery().getTime()));
            }
            quantitySpin.setValue(xorditem.getQuantity());
            measureItemCB.setSelectedItem(xorditem.getMeasureitem());
            priceOneSpin.setValue(xorditem.getPriceperone());
            totalValSpin.setValue(xorditem.getTotalvalue());
        }
    }

    @Override
    public boolean save() throws Exception {
        Xorderitem xorditem = (Xorderitem) getDbObject();
        boolean isNew = false;
        if (xorditem == null) {
            xorditem = new Xorderitem(null);
            xorditem.setXorderitemId(0);
            xorditem.setXorderId(xorder.getXorderId());
            isNew = true;
        }
        try {
            xorditem.setXorderId(xorder.getXorderId());
            xorditem.setItemnumber(itemNumberField.getText());
//            xorditem.setMaterialnumber(materialNumberField.getText());
//            xorditem.setMachinetype((String) machineTypeCB.getSelectedItem());
            ComboItem ci = (ComboItem) machineTypeCB.getSelectedItem();
            xorditem.setXmachtypeId(ci == null ? null : ci.getId());
//            xorditem.setDescription(descriptionField.getText());
            java.util.Date dut = (java.util.Date) requiredDateSpin.getValue();
            xorditem.setDeliveryreq(dut == null ? null : new Date(dut.getTime()));
            dut = (java.util.Date) deliverDateSpin.getValue();
            xorditem.setDelivery(dut == null ? null : new Date(dut.getTime()));
            xorditem.setQuantity((Integer) quantitySpin.getValue());
            xorditem.setMeasureitem((String) measureItemCB.getSelectedItem());
            xorditem.setPriceperone((Double) priceOneSpin.getValue());
            xorditem.setTotalvalue((Double) totalValSpin.getValue());

            DbObject saved = DashBoard.getExchanger().saveDbObject(xorditem);
            setDbObject(saved);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return false;
    }

    protected void setXorder(Xorder xorder) {
        this.xorder = xorder;
    }

    private ChangeListener calcTotalValueAction() {
        return new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                if (priceOneSpin.getValue() != null && quantitySpin.getValue() != null) {
                    Integer qty = (Integer) quantitySpin.getValue();
                    Double prc = (Double) priceOneSpin.getValue();
                    totalValSpin.setValue(new Double(qty.intValue() * prc.doubleValue()));
                } else {
                    totalValSpin.setValue(0);
                }
            }
        };
    }
}
