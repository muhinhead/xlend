package com.xlend.gui.contract;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;

/**
 *
 * @author Nick Mukhin
 */
public class EditContractDialog extends EditRecordDialog {

    public static boolean okPressed;
    public static Xclient xclient;

    public EditContractDialog(String title, Object obj) {
        super(title, obj);
    }

    private void setXclient() {
        EditContractPanel editPanel = (EditContractPanel) getEditPanel();
        editPanel.setXclient(xclient);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditContractPanel((Xcontract) getObject()));
        setXclient();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        EditContractDialog.xclient = null;
    }
}
