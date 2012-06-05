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
        EditBreakConsumePanel editPanel = (EditBreakConsumePanel) getEditPanel();
        editPanel.setXmachineID(getXmachineID());
        editPanel.setXbreakdownID(getXbreakdownID());
    }
    
    /**
     * @param aXmachineID the xmachineID to set
     */
    public static void setXmachineID(Integer aXmachineID) {
        xmachineID = aXmachineID;
    }

    
    @Override
    protected void fillContent() {
        super.fillContent(new EditBreakConsumePanel((Xbreakconsume) getObject()));
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
