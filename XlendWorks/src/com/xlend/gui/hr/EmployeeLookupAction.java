package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import com.xlend.gui.XlendWorks;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class EmployeeLookupAction extends AbstractAction {

    protected JComboBox employeeCB;
    protected String whereCond;

    public EmployeeLookupAction(JComboBox cBox) {
        this(cBox, Selects.activeEmployeeCondition);
    }

    public EmployeeLookupAction(JComboBox cBox, String whereCond) {
        super("...");
        this.employeeCB = cBox;
        this.whereCond = whereCond;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String select = Selects.SELECT_EMPLOYEE4LOOKUP;//SELECT_FROM_EMPLOYEE;
            if (whereCond != null) {
                select = select.replace("order by", (select.indexOf("where") > 0 ? " and " : " where ")
                        + whereCond + " order by");
            }
            LookupDialog ld = new LookupDialog("Employee Lookup", employeeCB,
                    new EmployeesGrid(XlendWorks.getExchanger(), select, true),
                    new String[]{"id_num", "first_name", "sur_name", "clock_num"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
