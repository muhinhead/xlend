package com.xlend.gui;

import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.gui.hr.TimeSheetsGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class HRFrame extends GeneralFrame {

    private EmployeesGrid operatorsPanel;
    private TimeSheetsGrid weeklyWagesPanel;

    public HRFrame(IMessageSender exch) {
        super("HR", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane hrTab = new JTabbedPane();
        hrTab.add(getOperatorsPanel(), "Employee Files");
        hrTab.add(getWeeklyWagesPanel(), "Time Sheets");
        hrTab.add(new JPanel(), "Salaries");
        hrTab.add(new JPanel(), "Diciplinary Actions");
        hrTab.add(new JPanel(), "Rewards Program");
        return hrTab;
    }

    private JPanel getOperatorsPanel() {
        if (operatorsPanel == null) {
            try {
                registerGrid(operatorsPanel = new EmployeesGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return operatorsPanel;
    }

    private JPanel getWeeklyWagesPanel() {
        if (weeklyWagesPanel == null) {
            try {
                registerGrid(weeklyWagesPanel = new TimeSheetsGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return weeklyWagesPanel;
    }
}
