package com.xlend.gui.hr;

import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class OperatorLookupAction extends EmployeeLookupAction {

    public OperatorLookupAction(JComboBox cBox, String whereCond) {
        super(cBox, adjustWhereCond(whereCond));
    }
    
    public OperatorLookupAction(JComboBox cBox) {
        this(cBox, null);
    }

    private static String adjustWhereCond(String whereCond) {
        return (whereCond == null ? "" : whereCond + " and ") + "wage_category<>1";
    }
}
