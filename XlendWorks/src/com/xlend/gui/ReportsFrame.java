package com.xlend.gui;

import com.xlend.gui.reports.SuppliersCreditorsReportPanel;
import com.xlend.remote.IMessageSender;
import com.xlend.util.MyJideTabbedPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class ReportsFrame extends GeneralFrame {

    private static String[] sheetList = new String[]{
        "Supplier and Creditor"
    };
    private SuppliersCreditorsReportPanel suppliersCreditorsPanel;
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
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            reportsTab.add(getSuppliersCreditorsPanel(), sheets()[0]);
        }
        return reportsTab;
    }

    private JPanel getSuppliersCreditorsPanel() {
        if (suppliersCreditorsPanel == null) {
            registerGrid(suppliersCreditorsPanel = new SuppliersCreditorsReportPanel(getExchanger()));
        }
        return suppliersCreditorsPanel;
    }

    @Override
    protected ActionListener getPrintAction() {
        return new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (suppliersCreditorsPanel.getEditorPanel() != null) {
                    try {
                        suppliersCreditorsPanel.getEditorPanel().print();
                    } catch (PrinterException ex) {
                        XlendWorks.log(ex);
                    }
                }
            }
        };
    }
}
