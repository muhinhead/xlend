package com.xlend.gui;

import com.xlend.gui.work.ClientsGrid;
import com.xlend.gui.work.ContractsGrid;
import com.xlend.gui.work.CreditorsGrid;
import com.xlend.gui.work.OrdersGrid;
import com.xlend.gui.work.QuotationsGrid;
import com.xlend.gui.work.SuppliersGrid;
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
    private GeneralGridPanel creditorsPanel = null;
    private static String[] sheetList = new String[]{
        "Contracts", "RFQ/Quotes", "Orders", "Clients", "Suppliers", "Credotors"
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
        JTabbedPane workTab = new JTabbedPane();
        workTab.add(getContractsPanel(), sheets()[0]);
        workTab.add(getQuotasPanel(), sheets()[1]);
        workTab.add(getOrdersPanel(), sheets()[2]);
//        workTab.add(getSitesPanel(), "Sites");
        workTab.add(getClientsPanel(), sheets()[3]);
        workTab.add(getSuppliersPanel(), sheets()[4]);
        workTab.add(getCreditorsPanel(), sheets()[5]);
        return workTab;
    }

    private JPanel getContractsPanel() {
        if (contractsPanel == null) {
            try {
                registerGrid(contractsPanel = new ContractsGrid(exchanger));
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
                registerGrid(clientsPanel = new ClientsGrid(exchanger));
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
                registerGrid(ordersPanel = new OrdersGrid(exchanger));
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
                registerGrid(quotasPanel = new QuotationsGrid(exchanger));
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
                registerGrid(suppliersPanel = new SuppliersGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return suppliersPanel;
    }

    private JPanel getCreditorsPanel() {
        if (creditorsPanel == null) {
            try {
                registerGrid(creditorsPanel = new CreditorsGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return creditorsPanel;
    }
}
