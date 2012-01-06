package com.xlend.gui.hr;

import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.LookupDialog;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author Nick Mukhin
 */
public class EmployeeLookupAction extends AbstractAction {

    private JComboBox employeeCB;

    public EmployeeLookupAction(JComboBox cBox) {
        super("...");
        this.employeeCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            LookupDialog ld = new LookupDialog("Employee Lookup", employeeCB,
                    new EmployeesGrid(DashBoard.getExchanger(), Selects.EMPLOYEES, true),
                    new String[]{"sur_name", "clock_num"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
