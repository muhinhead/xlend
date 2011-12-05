package com.xlend.gui.order;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xorderitem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.util.SelectedDateSpinner;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class EditOrderItemPanel extends RecordEditPanel {

    private JTextField idField;
    private JTextField itemNumberField;
    private JTextArea descriptionField;
    private JTextField materialNumberField;
    private JComboBox machineTypeCB;
    private JSpinner requiredDateSpin;
    private JSpinner deliverDateSpin;
    private JSpinner quantitySpin;
    private JComboBox measureItemCB;
    private JSpinner priceOneSpin;
    private Xorder xorder;

    public EditOrderItemPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] labels = new String[]{
            "ID:",
            "Item Nr:", "Material Number:", "Machine Type:", "Description:",
            "Required:", "Delivery Date:", "Order Quantity:",
            "Measure Item", "Price per Unit:"
        };
        JComponent[] edits = new JComponent[]{
            idField = new JTextField(),
            itemNumberField = new JTextField(),
            materialNumberField = new JTextField(),
            machineTypeCB = new JComboBox(distinctMachineTypes()),
            new JScrollPane(descriptionField = new JTextArea(4, 20),
            JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
            requiredDateSpin = new SelectedDateSpinner(),
            deliverDateSpin = new SelectedDateSpinner(),
            quantitySpin = new JSpinner(new SpinnerNumberModel()),
            measureItemCB = new JComboBox(distinctMeasureItems()),
            priceOneSpin = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000000.00, 1.11))
        };
        
        machineTypeCB.setEditable(true);
        measureItemCB.setEditable(true);
        requiredDateSpin.setEditor(new JSpinner.DateEditor(requiredDateSpin, "dd/MM/yyyy"));
        deliverDateSpin.setEditor(new JSpinner.DateEditor(deliverDateSpin, "dd/MM/yyyy"));
        Util.addFocusSelectAllAction(requiredDateSpin);
        Util.addFocusSelectAllAction(deliverDateSpin);
        
        descriptionField.setWrapStyleWord(true);
        descriptionField.setLineWrap(true);
        organizePanels(labels, edits);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        setLayout(new BorderLayout());
        setLayout(new BorderLayout());
        JPanel form = new JPanel(new BorderLayout(5, 5));

        idField.setEnabled(false);

        JPanel upper = new JPanel(new BorderLayout(5, 5));
        JPanel uplabel = new JPanel(new GridLayout(4, 1, 5, 5));
        JPanel upedit = new JPanel(new GridLayout(4, 1, 5, 5));
        upper.add(uplabel, BorderLayout.WEST);
        upper.add(upedit, BorderLayout.CENTER);

        for (int i = 0; i < 4; i++) {
            uplabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
        }

        JPanel ided = new JPanel(new GridLayout(1, 3, 5, 5));
        ided.add(idField);
        ided.add(new JPanel());
        ided.add(new JPanel());

        upedit.add(ided);
        for (int i = 1; i < 4; i++) {
            upedit.add(edits[i]);
        }

        form.add(upper, BorderLayout.NORTH);

        JPanel leftpanel = new JPanel(new BorderLayout());
        leftpanel.add(new JLabel(labels[4], SwingConstants.RIGHT), BorderLayout.NORTH);

        form.add(leftpanel, BorderLayout.WEST);
        form.add(edits[4]);

        JPanel downper = new JPanel(new BorderLayout(5, 5));
        JPanel downlabel = new JPanel(new GridLayout(5, 1, 5, 5));
        JPanel downedit = new JPanel(new GridLayout(5, 1, 5, 5));
        downper.add(downlabel, BorderLayout.WEST);
        downper.add(downedit, BorderLayout.CENTER);

        for (int i = 5; i < labels.length; i++) {
            downlabel.add(new JLabel(labels[i], SwingConstants.RIGHT));
            downedit.add(edits[i]);
        }

        form.add(downper, BorderLayout.SOUTH);

        alignPanelOnWidth(uplabel, downlabel);
        alignPanelOnWidth(leftpanel, downlabel);

        add(form);
    }

    @Override
    public void loadData() {
        Xorderitem xorditem = (Xorderitem) getDbObject();
        if (xorditem != null) {
            idField.setText(xorditem.getXorderitemId().toString());
            itemNumberField.setText(xorditem.getItemnumber());
            descriptionField.setText(xorditem.getDescription());
            materialNumberField.setText(xorditem.getMaterialnumber());
            machineTypeCB.setSelectedItem(xorditem.getMachinetype());
            if (xorditem.getDeliveryreq() != null) {
                requiredDateSpin.setValue(new java.sql.Date(xorditem.getDeliveryreq().getTime()));
            }
            if (xorditem.getDelivery() != null) {
                deliverDateSpin.setValue(new java.sql.Date(xorditem.getDelivery().getTime()));
            }
            quantitySpin.setValue(xorditem.getQuantity());
            measureItemCB.setSelectedItem(xorditem.getMeasureitem());
            priceOneSpin.setValue(xorditem.getPriceperone());
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
            xorditem.setMaterialnumber(materialNumberField.getText());
            xorditem.setMachinetype((String) machineTypeCB.getSelectedItem());
            xorditem.setDescription(descriptionField.getText());
            java.util.Date dut = (java.util.Date) requiredDateSpin.getValue();
            xorditem.setDeliveryreq(dut == null ? null : new Date(dut.getTime()));
            dut = (java.util.Date) deliverDateSpin.getValue();
            xorditem.setDelivery(dut == null ? null : new Date(dut.getTime()));
            xorditem.setQuantity((Integer) quantitySpin.getValue());
            xorditem.setMeasureitem((String) measureItemCB.getSelectedItem());
            xorditem.setPriceperone((Double) priceOneSpin.getValue());

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

    private String[] distinctMachineTypes() {
        return Selects.getStringArray(Selects.DISTINCT_MACHINETYPES);
    }
    
    private String[] distinctMeasureItems() {
        return Selects.getStringArray(Selects.DISTINCT_MEASUREITEMS);
    }
}
