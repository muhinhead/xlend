package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.BreakdownsGrid;
import com.xlend.gui.site.ConsumablesGrid;
import com.xlend.gui.site.DieselPurchaseGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class SitesFrame extends GeneralFrame {

    private GeneralGridPanel sitesPanel = null;
    private GeneralGridPanel disprchsPanel = null;
    private GeneralGridPanel dieselIsissPanel = null;
    private GeneralGridPanel consumablesPanel = null;
    private GeneralGridPanel breakdownsPanel;

    public SitesFrame(IMessageSender exch) {
        super("Sites", exch);
    }

    @Override
    protected JTabbedPane getMainPanel() {
        JTabbedPane workTab = new JTabbedPane();
//        workTab.add(getContractsPanel(), "Contracts");
        workTab.add(getSitesPanel(), "Sites");
        workTab.add(getDieselPrchsPanel(), "Diesel Rurchases");
        workTab.add(getDieselCardsPanel(), "Diesel Issuing");
        workTab.add(getConsumablesPanel(), "Consumables");
        workTab.add(getBreakdownsPanel(), "Breakdowns");
        workTab.add(new JPanel(), "Fuel");
        return workTab;
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

    private JPanel getDieselPrchsPanel() {
        if (disprchsPanel == null) {
            try {
                registerGrid(disprchsPanel = new DieselPurchaseGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return disprchsPanel;
    }
    
    private JPanel getDieselCardsPanel() {
        if (dieselIsissPanel == null) {
            try {
                registerGrid(dieselIsissPanel = new DieselCardsGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return dieselIsissPanel;
    }
    
    private JPanel getConsumablesPanel() {
        if (consumablesPanel == null) {
            try {
                registerGrid(consumablesPanel = new ConsumablesGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return consumablesPanel;
    }
    
    private JPanel getBreakdownsPanel() {
        if (breakdownsPanel == null) {
            try {
                registerGrid(breakdownsPanel = new BreakdownsGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return breakdownsPanel;
    }
    
}
