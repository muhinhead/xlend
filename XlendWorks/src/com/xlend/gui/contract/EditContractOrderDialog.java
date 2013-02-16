package com.xlend.gui.contract;

import com.xlend.gui.order.EditOrderDialog;
import com.xlend.orm.Xcontract;
import com.xlend.orm.Xorder;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractOrderDialog extends EditOrderDialog {

    public static boolean okPressed;
    public static Xcontract xcontract;

    public EditContractOrderDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXcontract() {
        EditContractOrderPanel editPanel = (EditContractOrderPanel) getEditPanel();
        editPanel.setXcontract(xcontract);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditContractOrderPanel((Xorder) getObject()));
        setXcontract();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditContractOrderDialog.xcontract = null;
    }
}
