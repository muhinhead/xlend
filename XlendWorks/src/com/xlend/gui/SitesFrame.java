package com.xlend.gui;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.gui.site.BreakdownsGrid;
import com.xlend.gui.site.ConsumablesGrid;
import com.xlend.gui.site.DieselPurchaseGrid;
import com.xlend.gui.work.DieselCardsGrid;
import com.xlend.gui.work.SitesGrid;
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
    private static String[] sheetList = new String[]{
        "Sites", "Diesel Rurchases", "Diesel Issuing", "Consumables", "Breakdowns", "Fuel"
    };

    public SitesFrame(IMessageSender exch) {
        super("Sites", exch);
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
//        workTab.add(getContractsPanel(), "Contracts");
        workTab.add(getSitesPanel(), sheets()[0]);
        workTab.add(getDieselPrchsPanel(), sheets()[1]);
        workTab.add(getDieselCardsPanel(), sheets()[2]);
        workTab.add(getConsumablesPanel(), sheets()[3]);
        workTab.add(getBreakdownsPanel(), sheets()[4]);
        workTab.add(new JPanel(), sheets()[5]);
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
