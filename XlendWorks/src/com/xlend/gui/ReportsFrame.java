package com.xlend.gui;

import com.xlend.gui.reports.EmployeeReportPanel;
import com.xlend.gui.reports.SuppliersCreditorsReportPanel;
import com.xlend.remote.IMessageSender;
import com.xlend.util.MyJideTabbedPane;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author Nick Mukhin
 */
public class ReportsFrame extends GeneralFrame {

    private JComponent selectedTab;
    private static String[] sheetList = new String[]{
        "Supplier and Creditor", "Employee"
    };
    private SuppliersCreditorsReportPanel suppliersCreditorsPanel;
    private EmployeeReportPanel employeePanel;
    private JTabbedPane reportsTab;

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
        reportsTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            reportsTab.add(getSuppliersCreditorsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            reportsTab.add(getEmployeePanel(), sheets()[1]);
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

    private Component getEmployeePanel() {
        if (employeePanel == null) {
            registerGrid(employeePanel = new EmployeeReportPanel(getExchanger()));
        }
        return employeePanel;
    }

    @Override
    protected ActionListener getPrintAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedTab instanceof SuppliersCreditorsReportPanel) {
//                    if (suppliersCreditorsPanel.getEditorPanel() != null) {
                        try {
                            suppliersCreditorsPanel.getEditorPanel().print();
                        } catch (PrinterException ex) {
                            XlendWorks.log(ex);
                        }
//                    }
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
