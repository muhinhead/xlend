package com.xlend.gui;

import com.xlend.gui.reports.GeneralReportPanel;
import com.xlend.gui.reports.SuppliersCreditorsReportPanel;
import com.xlend.remote.IMessageSender;
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
    private GeneralReportPanel suppliersCreditorsPanel;
    
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
        JTabbedPane reportsTab = new JTabbedPane();
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            reportsTab.add(getSuppliersCreditorsPanel(), sheets()[0]);
        }
        return reportsTab;
    }

    private JPanel getSuppliersCreditorsPanel() {
        if (suppliersCreditorsPanel == null) {
            registerGrid(suppliersCreditorsPanel = new SuppliersCreditorsReportPanel(getExchanger()));
//            try {
//                registerGrid(disprchsPanel = new DieselPurchaseGrid(exchanger));
//            } catch (RemoteException ex) {
//                XlendWorks.log(ex);
//                errMessageBox("Error:", ex.getMessage());
//            }
        }
        return suppliersCreditorsPanel;
    }

    
}
