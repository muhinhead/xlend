package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class ClercLookupAction extends EmployeeLookupAction {

    public ClercLookupAction(JComboBox cBox, String whereCond) {
        super(cBox, adjustWhereCond(whereCond));
    }

    public ClercLookupAction(JComboBox cBox) {
        this(cBox, Selects.activeEmployeeCondition);
    }

    private static String adjustWhereCond(String whereCond) {
        return (whereCond == null ? "" : whereCond + " and ") //+ "management=1 and "
                + "wage_category=1";
    }
}
