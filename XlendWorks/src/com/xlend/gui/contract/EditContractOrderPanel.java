package com.xlend.gui.contract;

import com.xlend.gui.order.EditOrderPanel;
import com.xlend.orm.Xcontract;
import com.xlend.orm.Xorder;
import com.xlend.orm.dbobject.DbObject;
import javax.swing.AbstractAction;
import javax.swing.JComponent;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractOrderPanel extends EditOrderPanel {

    private Xcontract xcontract;

    public EditContractOrderPanel(DbObject dbObject) {
        super(dbObject);
    }

    protected void organizePanels(String[] labels, JComponent[] edits) {
        super.organizePanels(labels, edits);
        contractRefBox.setEnabled(false);
        clientRefBox.setEnabled(false);
    }

    protected AbstractAction clientRefLookup() {
        return null;
    }

    protected AbstractAction contractRefLookup() {
        return null;
    }

    /**
     * @return the parent
     */
    public Xcontract getXcontract() {
        return xcontract;
    }

    /**
     * @param parent the parent to set
     */
    public void setXcontract(Xcontract xcontract) {
        this.xcontract = xcontract;
        if (xcontract != null) {
            selectComboItem(clientRefBox, xcontract.getXclientId());
            syncCombos();
            selectComboItem(contractRefBox, xcontract.getXcontractId());
            Xorder xorder = (Xorder) getDbObject();
            if (xorder != null) {
                selectComboItem(rfcRefBox, xorder.getXquotationId());
            }
        }
    }
}
