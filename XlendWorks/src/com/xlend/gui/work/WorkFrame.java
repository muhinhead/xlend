package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class WorkFrame extends GeneralFrame {

    private GeneralGridPanel contractsPanel = null;
    private GeneralGridPanel sitesPanel = null;
    private GeneralGridPanel clientsPanel = null;
    private GeneralGridPanel ordersPanel = null;
    private GeneralGridPanel quotasPanel;

    public WorkFrame(IMessageSender exch) {
        super("Work", exch);
    }

    @Override
    protected JTabbedPane getMainPanel() {
        JTabbedPane workTab = new JTabbedPane();
        workTab.add(getContractsPanel(), "Contracts");
        workTab.add(getQuotasPanel(), "RFQ/Quotes");
        workTab.add(getOrdersPanel(), "Orders");
        workTab.add(getSitesPanel(), "Sites");
        workTab.add(getClientsPanel(), "Clients");
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

    private JPanel getSitesPanel() {
        if (sitesPanel == null) {
            try {
                registerGrid(sitesPanel = new SitesGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return sitesPanel;
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
}
