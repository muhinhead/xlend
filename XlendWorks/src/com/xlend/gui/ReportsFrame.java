package com.xlend.gui;

import com.xlend.gui.reports.CashTurnoverReport;
import com.xlend.gui.reports.PettyReport;
import com.xlend.gui.reports.EmployeeReportPanel;
import com.xlend.gui.reports.GeneralReportPanel;
import com.xlend.gui.reports.IncidentsReport;
import com.xlend.gui.reports.LoansReport;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.gui.reports.SuppliersCreditorsReportPanel;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class ReportsFrame extends GeneralFrame {

//    private JComponent selectedTab;
    private static String[] sheetList = new String[]{
        "Creditor Age Analysis", "Employee Summary",
        "Loans Report", "Incidents Report",
        "Assignments Report",
        "Petty Report","Cash Turnover"
    };
    private SuppliersCreditorsReportPanel suppliersCreditorsPanel;
    private EmployeeReportPanel employeePanel;
    private LoansReport loansPanel;
    private AssignmentsReport assignmentsPanel;
    private MyJideTabbedPane reportsTab;
    private IncidentsReport incidentsPanel;
    private PettyReport pettyPanel;
    private CashTurnoverReport cashTurnPanel;

    public ReportsFrame(IMessageSender exch) {
        super("Reports", exch);
        getSearchButton().setVisible(false);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    public static String[] sheets() {
        return sheetList;
    }

    @Override
    protected JTabbedPane getMainPanel() {
//        new ReportsMenuDialog();
        int n = -1;
        reportsTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getSuppliersCreditorsPanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getEmployeePanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getLoansPanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getIncidentsPanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getAssignmentsPanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getPettyPanel(), sheets()[n]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[++n]) && ReportsMenuDialog.isCheckedReport(sheets()[n])) {
            reportsTab.addTab(getCashTurnPanel(), sheets()[n]);
        }

        return reportsTab;
    }

    private JPanel getSuppliersCreditorsPanel() {
        if (suppliersCreditorsPanel == null) {
            registerGrid(suppliersCreditorsPanel = new SuppliersCreditorsReportPanel(getExchanger()));
        }
        return suppliersCreditorsPanel;
    }

    private JPanel getEmployeePanel() {
        if (employeePanel == null) {
            registerGrid(employeePanel = new EmployeeReportPanel(getExchanger()));
        }
        return employeePanel;
    }

    private JComponent getLoansPanel() {
        if (loansPanel == null) {
            registerGrid(loansPanel = new LoansReport(getExchanger()));
        }
        return loansPanel;
    }

    private JComponent getAssignmentsPanel() {
        if (assignmentsPanel == null) {
            registerGrid(assignmentsPanel = new AssignmentsReport(getExchanger()));
        }
        return assignmentsPanel;
    }

    private JComponent getIncidentsPanel() {
        if (incidentsPanel == null) {
            registerGrid(incidentsPanel = new IncidentsReport(getExchanger()));
        }
        return incidentsPanel;
    }

    private JComponent getPettyPanel() {
       if (pettyPanel == null) {
            registerGrid(pettyPanel = new PettyReport(getExchanger()));
        }
        return pettyPanel;
    }
    
    private JComponent getCashTurnPanel() {
       if (cashTurnPanel == null) {
            registerGrid(cashTurnPanel = new CashTurnoverReport(getExchanger()));
        }
        return cashTurnPanel;
    }
    
    @Override
    protected ActionListener getPrintAction() {
        return new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Component selectedTab = reportsTab.getSelectedComponent();
                if (selectedTab != null && selectedTab instanceof GeneralReportPanel) {
                    try {
                        GeneralReportPanel repPanel = (GeneralReportPanel) selectedTab;
                        repPanel.getEditorPanel().print();
                    } catch (PrinterException ex) {
                        XlendWorks.logAndShowMessage(ex);
                    }
                }
            }
        };
    }
}
