package com.xlend.gui;

import com.xlend.gui.hr.*;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class HRFrame extends GeneralFrame {

    public static HRFrame instance;
    private GeneralGridPanel operatorsPanel;
    private GeneralGridPanel weeklyWagesPanel;
    private GeneralGridPanel wagesSummaryPanel;
    private GeneralGridPanel absenteismPanel;
    private GeneralGridPanel app4leavePanel;
    private GeneralGridPanel loansPanel;
    private GeneralGridPanel salaryListPanel;
    private GeneralGridPanel jobCardListrPanel;
    private static String[] sheetList = new String[]{
        "Employee Files", "Time Sheets", "Salaries", "Wages",
        "Diciplinary Actions", "Rewards Program",
        "Absenteism", "Application for Leave", "Loans", "Job Cards"
    };

    public HRFrame(IMessageSender exch) {
        super("HR", exch);
        instance = this;
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    public static String[] sheets() {
        return sheetList;
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane hrTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            hrTab.addTab(getOperatorsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            hrTab.addTab(getWeeklyWagesPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            hrTab.addTab(getSalaryListPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            hrTab.addTab(getWagesSummaryPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            hrTab.addTab(new JPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
            hrTab.addTab(new JPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
            hrTab.addTab(getAbsenteismPanel(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
            hrTab.addTab(getApp4LeavePanel(), sheets()[7]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[8])) {
            hrTab.addTab(getLoansPanel(), sheets()[8]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[9])) {
            hrTab.addTab(getОobCardListrPanel(), sheets()[9]);
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

    private JPanel getLoansPanel() {
        if (loansPanel == null) {
            try {
                registerGrid(loansPanel = new LoansGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return loansPanel;
    }

    private JPanel getSalaryListPanel() {
        if (salaryListPanel == null) {
            try {
                registerGrid(salaryListPanel = new SalaryListsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return salaryListPanel;
    }

    private JPanel getОobCardListrPanel() {
        if (jobCardListrPanel == null) {
            try {
                registerGrid(jobCardListrPanel = new JobCardGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return jobCardListrPanel;
    }
}
