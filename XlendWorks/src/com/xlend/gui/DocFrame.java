package com.xlend.gui;

//import com.jidesoft.swing.JideTabbedPane;
import static com.xlend.gui.GeneralFrame.errMessageBox;
import com.xlend.gui.work.*;
import com.xlend.remote.IMessageSender;
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
    private GeneralGridPanel internalOrderPlacementsPanel = null;
    private static String[] sheetList = new String[]{
        "Contracts", "RFQ/Quotes", "Orders", "Clients", "Suppliers", "Payments", 
        "Hour Comparison", "Machine Order Placements"
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
        MyJideTabbedPane workTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            workTab.addTab(getContractsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            workTab.addTab(getQuotasPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            workTab.addTab(getOrdersPanel(), sheets()[2]);
        }
//        workTab.addTab(getSitesPanel(), "Sites");
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            workTab.addTab(getClientsPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            workTab.addTab(getSuppliersPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
            workTab.addTab(getPaymentsPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
            workTab.addTab(getHourComparePanel(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
            workTab.addTab(getInternalOrderPlacementsPanel(), sheets()[7]);
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

    private JPanel getClientsPanel() {
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

    private JPanel getOrdersPanel() {
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

    private JPanel getQuotasPanel() {
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
    
    private JPanel getInternalOrderPlacementsPanel() {
        if (internalOrderPlacementsPanel == null) {
            try {
                registerGrid(internalOrderPlacementsPanel = new InternalMachineOrderPlacementGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return internalOrderPlacementsPanel;
    }
}
