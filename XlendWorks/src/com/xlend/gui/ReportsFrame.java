package com.xlend.gui;

import com.xlend.gui.reports.EmployeeReportPanel;
import com.xlend.gui.reports.LoansReport;
import com.xlend.gui.reports.ReportsMenuDialog;
import com.xlend.gui.reports.SuppliersCreditorsReportPanel;
import com.xlend.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class ReportsFrame extends GeneralFrame {

    private JComponent selectedTab;
    private static String[] sheetList = new String[]{
        "Creditor Age Analysis", "Employee Summary", 
        "Loans Report", "Incidents Report"
    };
    private SuppliersCreditorsReportPanel suppliersCreditorsPanel;
    private EmployeeReportPanel employeePanel;
    private LoansReport loansPanel;
    private MyJideTabbedPane reportsTab;

    public ReportsFrame(IMessageSender exch) {
        super("Reports", exch);
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

        reportsTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0]) && ReportsMenuDialog.isCheckedReport(sheets()[0])) {
            reportsTab.addTab(getSuppliersCreditorsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1]) && ReportsMenuDialog.isCheckedReport(sheets()[1])) {
            reportsTab.addTab(getEmployeePanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2]) && ReportsMenuDialog.isCheckedReport(sheets()[2])) {
            reportsTab.addTab(getLoansPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3]) && ReportsMenuDialog.isCheckedReport(sheets()[3])) {
            //reportsTab.addTab(getLoansPanel(), sheets()[3]);
            GeneralFrame.errMessageBox("!!!", "HERE!");
        }
        reportsTab.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                selectedTab = (JComponent) reportsTab.getSelectedComponent();
            }
        });
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
    
    @Override
    protected ActionListener getPrintAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedTab instanceof SuppliersCreditorsReportPanel) {
                    try {
                        suppliersCreditorsPanel.getEditorPanel().print();
                    } catch (PrinterException ex) {
                        XlendWorks.log(ex);
                    }
                } else if (selectedTab instanceof EmployeeReportPanel) {
                    try {
                        employeePanel.getEditorPanel().print();
                    } catch (PrinterException ex) {
                        XlendWorks.log(ex);
                    }
                }
            }
        };
    }

}
