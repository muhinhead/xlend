package com.xlend.gui;

import com.xlend.gui.hr.*;
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
    private WagesGrid wagesSummaryPanel;
    private AbsenteismGrid absenteismPanel;
    private Application4LeaveGrid app4leavePanel;
    private static String[] sheetList = new String[]{
        "Employee Files", "Time Sheets", "Salaries", "Wages", 
        "Diciplinary Actions", "Rewards Program",
        "Absenteism", "Application for Leave"
    };

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
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            hrTab.add(getOperatorsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[1])) {
            hrTab.add(getWeeklyWagesPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[2])) {
            hrTab.add(new JPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[3])) {
            hrTab.add(getWagesSummaryPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[4])) {
            hrTab.add(new JPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[5])) {
            hrTab.add(new JPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[6])) {
            hrTab.add(getAbsenteismPanel(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[7])) {
            hrTab.add(getApp4LeavePanel(), sheets()[7]);
        }
        return hrTab;
    }

    private JPanel getOperatorsPanel() {
        if (operatorsPanel == null) {
            try {
                registerGrid(operatorsPanel = new EmployeesGrid(getExchanger()));
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
                registerGrid(weeklyWagesPanel = new TimeSheetsGrid(getExchanger()));
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
                registerGrid(wagesSummaryPanel = new WagesGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return wagesSummaryPanel;
    }
    
    private JPanel getAbsenteismPanel() {
        if (absenteismPanel == null) {
            try {
                registerGrid(absenteismPanel = new AbsenteismGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return absenteismPanel;
    }

    private JPanel getApp4LeavePanel() {
        if (app4leavePanel == null) {
            try {
                registerGrid(app4leavePanel = new Application4LeaveGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return app4leavePanel;
    }
}
