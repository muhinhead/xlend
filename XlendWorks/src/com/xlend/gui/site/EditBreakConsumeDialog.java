package com.xlend.gui.site;

import com.xlend.gui.EditRecordDialog;
import com.xlend.orm.Xbreakconsume;

/**
 *
 * @author Nick Mukhin
 */
class EditBreakConsumeDialog extends EditRecordDialog {

    public static boolean okPressed;
    private static Integer xmachineID;
    private static Integer xbreakdownID;
    private static EditBreakConsumePanel editPanel;

    /**
     * @return the xconsumeID
     */
    public static Integer getXbreakdownID() {
        return xbreakdownID;
    }

    /**
     * @param aXconsumeID the xconsumeID to set
     */
    public static void setXbreakdownID(Integer breakdownID) {
        xbreakdownID = breakdownID;
    }

    public EditBreakConsumeDialog(String title, Object obj) {
        super(title, obj);
    }

    /**
     * @return the xmachineID
     */
    public static Integer getXmachineID() {
        return xmachineID;
    }

    private void setDependentIDs() {
        editPanel.setXbreakdownID(getXbreakdownID());
    }

    /**
     * @param aXmachineID the xmachineID to set
     */
    public static void setXmachineID(Integer aXmachineID) {
        xmachineID = aXmachineID;
        if (editPanel != null) {
            editPanel.reloadMachineComboBox();
        }
    }

    @Override
    protected void fillContent() {
        super.fillContent(editPanel = new EditBreakConsumePanel((Xbreakconsume) getObject()));
        setDependentIDs();
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }

    @Override
    public void dispose() {
        super.dispose();
        setXmachineID(null);
        setXbreakdownID(null);
    }
}
