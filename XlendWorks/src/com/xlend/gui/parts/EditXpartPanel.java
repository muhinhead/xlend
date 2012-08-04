package com.xlend.gui.parts;

import com.xlend.gui.DashBoard;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xparts;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditXpartPanel extends RecordEditPanel {
    
    public static int categoryID;
    private static EditXpartPanel instance;

    /**
     * @return the instance
     */
    public static EditXpartPanel getInstance() {
        return instance;
    }
    
    private JTextField idField;
    private JTextField partNoField;
    private JTextField descriptionField;
    private JComboBox machineTypeCB;
    private DefaultComboBoxModel machineTypeCbModel;
    private JTextField destinationField;
    private PartStocksGrid partStockGrid;

    public EditXpartPanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        instance = this;
        String titles[] = new String[]{
            "ID:",
            "Part No_:",
            "Description:",
            "Machine type:",
            "Destination:"
        };
        machineTypeCbModel = new DefaultComboBoxModel();
        for (ComboItem itm : XlendWorks.loadRootMachTypes(DashBoard.getExchanger(), "M','T','P','L','V")) {
            machineTypeCbModel.addElement(itm);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            getGridPanel(partNoField = new JTextField(), 3),
            descriptionField = new JTextField(),
            getGridPanel(machineTypeCB = new JComboBox(machineTypeCbModel), 2),
            destinationField = new JTextField()
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        JPanel downPanel = new JPanel(new BorderLayout());
        downPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Stocks"));
        try {
            downPanel.add(partStockGrid = new PartStocksGrid(DashBoard.getExchanger(), -1));
        } catch (RemoteException ex) {
            XlendWorks.logAndShowMessage(ex);
        }
        add(downPanel, BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        Xparts xpart = (Xparts) getDbObject();
        if (xpart != null) {
            idField.setText(xpart.getXpartsId().toString());
            partNoField.setText(xpart.getPartnumber());
            descriptionField.setText(xpart.getDescription());
            selectComboItem(machineTypeCB, xpart.getXmachtypeId());
            destinationField.setText(xpart.getWhatfor());
            partStockGrid.selectPartID(xpart.getXpartsId().intValue());
        }
    }

    @Override
    public boolean save() throws Exception {
        boolean isNew = false;
        Xparts xpart = (Xparts) getDbObject();
        if (xpart == null) {
            isNew = true;
            xpart= new Xparts(null);
            xpart.setXpartsId(0);
            xpart.setXpartcategoryId(categoryID);
        }
        xpart.setPartnumber(partNoField.getText());
        xpart.setDescription(descriptionField.getText());
        xpart.setWhatfor(destinationField.getText());
        xpart.setXmachtypeId(getSelectedCbItem(machineTypeCB));
        return saveDbRecord(xpart, isNew);
    }
}
