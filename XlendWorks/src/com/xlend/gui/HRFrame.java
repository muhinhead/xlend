package com.xlend.gui;

import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.gui.hr.TimeSheetsGrid;
import com.xlend.gui.hr.WagesGrid;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
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
    private WagesGrid wagesSummaryPanel;
    private static String[] sheetList = new String[] {
        "Employee Files","Time Sheets","Salaries","Wages","Diciplinary Actions","Rewards Program"};

    public HRFrame(IMessageSender exch) {
        super("HR", exch);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    public static String[] sheets() {
        return sheetList;
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane hrTab = new JTabbedPane();
        hrTab.add(getOperatorsPanel(), sheets()[0]);
        hrTab.add(getWeeklyWagesPanel(), sheets()[1]);
        hrTab.add(new JPanel(), sheets()[2]);
        hrTab.add(getWagesSummaryPanel(), sheets()[3]);
        hrTab.add(new JPanel(), sheets()[4]);
        hrTab.add(new JPanel(), sheets()[5]);
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

    private JPanel getWagesSummaryPanel() {
        if (wagesSummaryPanel == null) {
            try {
                registerGrid(wagesSummaryPanel = new WagesGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return wagesSummaryPanel;
    }
}
