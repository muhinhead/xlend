package com.xlend.gui;

import com.jidesoft.swing.JideTabbedPane;
import com.xlend.gui.work.*;
import com.xlend.remote.IMessageSender;
import com.xlend.util.MyJideTabbedPane;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class DocFrame extends GeneralFrame {

    private GeneralGridPanel contractsPanel = null;
    private GeneralGridPanel clientsPanel = null;
    private GeneralGridPanel ordersPanel = null;
    private GeneralGridPanel quotasPanel = null;
    private GeneralGridPanel suppliersPanel = null;
    private GeneralGridPanel paymentsPanel = null;
    private GeneralGridPanel hourComparePanel = null;
    private static String[] sheetList = new String[]{
        "Contracts", "RFQ/Quotes", "Orders", "Clients", "Suppliers", "Payments", "Hour Comparison"
    };

    public DocFrame(IMessageSender exch) {
        super("Documents", exch);
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
        JTabbedPane workTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            workTab.add(getContractsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[1])) {
            workTab.add(getQuotasPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[2])) {
            workTab.add(getOrdersPanel(), sheets()[2]);
        }
//        workTab.add(getSitesPanel(), "Sites");
        if (XlendWorks.availableForCurrentUsder(sheets()[3])) {
            workTab.add(getClientsPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[4])) {
            workTab.add(getSuppliersPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[5])) {
            workTab.add(getPaymentsPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[6])) {
            workTab.add(getHourComparePanel(), sheets()[6]);
        }
//        workTab.setShowTabButtons(true);
//        workTab.setBoldActiveTab(true);
//        workTab.setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
//        workTab.setTabShape(JideTabbedPane.SHAPE_EXCEL);
        return workTab;
    }

    private JPanel getContractsPanel() {
        if (contractsPanel == null) {
            try {
                registerGrid(contractsPanel = new ContractsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return contractsPanel;
    }

    private Component getClientsPanel() {
        if (clientsPanel == null) {
            try {
                registerGrid(clientsPanel = new ClientsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return clientsPanel;
    }

    private Component getOrdersPanel() {
        if (ordersPanel == null) {
            try {
                registerGrid(ordersPanel = new OrdersGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return ordersPanel;
    }

    private Component getQuotasPanel() {
        if (quotasPanel == null) {
            try {
                registerGrid(quotasPanel = new QuotationsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return quotasPanel;
    }

    private JPanel getSuppliersPanel() {
        if (suppliersPanel == null) {
            try {
                registerGrid(suppliersPanel = new SuppliersGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return suppliersPanel;
    }

    private JPanel getPaymentsPanel() {
        if (paymentsPanel == null) {
            try {
                registerGrid(paymentsPanel = new PaymentsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return paymentsPanel;
    }

    private JPanel getHourComparePanel() {
        if (hourComparePanel == null) {
            try {
                registerGrid(hourComparePanel = new HourCompareGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return hourComparePanel;
    }
}
