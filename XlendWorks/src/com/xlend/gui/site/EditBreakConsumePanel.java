package com.xlend.gui.site;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.RecordEditPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.orm.Xbreakconsume;
import com.xlend.orm.Xconsume;
import com.xlend.orm.Xsupplier;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
class EditBreakConsumePanel extends RecordEditPanel {

//    private Integer xmachineID;
    private Integer xbreakdownID;
    private DefaultComboBoxModel breakConsumeCbModel;
    private JComboBox breakConsumeCB;
    private JLabel supplierLB, invDateLB, amtLitersLB, amtRandsLB;
    private JTextField idField;

    public EditBreakConsumePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected void fillContent() {
        String[] titles = new String[]{
            "ID:",
            "Supplier:",
            "Inv.Nr:",
            "Inv.Date:",
            "Amt.liters:",
            "Amt.R:"
        };
        breakConsumeCbModel = new DefaultComboBoxModel();
        int n = 0;
        for (ComboItem ci : XlendWorks.loadConsumesForMachine(DashBoard.getExchanger(), EditBreakConsumeDialog.getXmachineID())) {
            breakConsumeCbModel.addElement(ci);
            n++;
        }
        if (n == 0) {
            GeneralFrame.errMessageBox("Warning:", "There is no purchases for this machine found");
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            supplierLB = new JLabel(""),
            comboPanelWithLookupBtn(breakConsumeCB = new JComboBox(breakConsumeCbModel),
            new Consume4MachineLookupAction(breakConsumeCB)),
            getGridPanel(invDateLB = new JLabel("", SwingConstants.LEFT), 4),
            getGridPanel(amtLitersLB = new JLabel("", SwingConstants.RIGHT), 4),
            getGridPanel(amtRandsLB = new JLabel("", SwingConstants.RIGHT), 4)
        };
        breakConsumeCB.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                syncLabels();
            }
        });
        supplierLB.setBorder(BorderFactory.createEtchedBorder());
        invDateLB.setBorder(BorderFactory.createEtchedBorder());
        amtLitersLB.setBorder(BorderFactory.createEtchedBorder());
        amtRandsLB.setBorder(BorderFactory.createEtchedBorder());
        idField.setEnabled(false);
        organizePanels(titles, edits, null);
        setPreferredSize(new Dimension(400, getPreferredSize().height));
    }

    @Override
    public void loadData() {
//        for (ComboItem ci : XlendWorks.loadConsumesForMachine(DashBoard.getExchanger(), EditBreakConsumeDialog.getXmachineID())) {
//            breakConsumeCbModel.addElement(ci);
//        }
        Xbreakconsume xbc = (Xbreakconsume) getDbObject();
        if (xbc != null) {
            selectComboItem(breakConsumeCB, xbc.getXconsumeId());
            idField.setText(xbc.getXbreakconsumeId().toString());
            syncLabels();
        }
    }

    @Override
    public boolean save() throws Exception {
        Xbreakconsume xbc = (Xbreakconsume) getDbObject();
        boolean isNew = false;
        if (xbc == null) {
            xbc = new Xbreakconsume(null);
            xbc.setXbreakconsumeId(0);
            isNew = true;
        }
        xbc.setXconsumeId(getSelectedCbItem(breakConsumeCB));
        xbc.setXbreakdownId(xbreakdownID);
//        if (xbreakdownID == 0) {
//            BreakdownConsumesGrid.getNewPurchases().add(xbc);
//            return true;
//        } else {
        return saveDbRecord(xbc, isNew);
//        }
    }

//    void setXmachineID(Integer machineID) {
//        xmachineID = machineID;
//        for (ComboItem ci : XlendWorks.loadConsumesForMachine(DashBoard.getExchanger(), xmachineID)) {
//            breakConsumeCbModel.addElement(ci);
//        }
//        breakConsumeCB.setModel(breakConsumeCbModel);
//    }
    void setXbreakdownID(Integer breakdownID) {
        xbreakdownID = breakdownID;
    }

    private void syncLabels() {
        Integer consumeID = getSelectedCbItem(breakConsumeCB);
        if (consumeID != null && consumeID > 0) {
            try {
                Xconsume rec = (Xconsume) DashBoard.getExchanger().loadDbObjectOnID(Xconsume.class, consumeID);
                if (rec.getInvoicedate() != null) {
                    invDateLB.setText(rec.getInvoicedate().toString());
                } else {
                    invDateLB.setText("");
                }
                if (rec.getAmountLiters() != null) {
                    amtLitersLB.setText(rec.getAmountLiters().toString());
                } else {
                    amtLitersLB.setText("");
                }
                if (rec.getAmountRands() != null) {
                    amtRandsLB.setText(rec.getAmountRands().toString());
                } else {
                    amtRandsLB.setText("");
                }
                Xsupplier sup = (Xsupplier) DashBoard.getExchanger().loadDbObjectOnID(Xsupplier.class, rec.getXsupplierId());
                if (sup != null) {
                    supplierLB.setText(sup.getCompanyname());
                } else {
                    supplierLB.setText("");
                }
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
            }
        }
    }
}
