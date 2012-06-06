package com.xlend.gui;

import com.xlend.gui.site.*;
import com.xlend.gui.work.DieselCardsGrid;
import com.xlend.gui.work.SitesGrid;
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
public class SitesFrame extends GeneralFrame {

    private GeneralGridPanel sitesPanel = null;
    private GeneralGridPanel disprchsPanel = null;
    private GeneralGridPanel dieselIsissPanel = null;
    private GeneralGridPanel consumablesPanel = null;
    private GeneralGridPanel breakdownsPanel;
    private GeneralGridPanel fuelPanel;
    private GeneralGridPanel issuePanel;
    private GeneralGridPanel siteDiaryPanel;
    private GeneralGridPanel incidentsPanel;
    private GeneralGridPanel operatorClockSheetPanel;
    
    private static String[] sheetList = new String[]{
        "Sites", "Diesel Rurchases", "Diesel Issuing", "Consumables", "Breakdowns", 
        "Fuel", "Issuing", "Site Diary", "Incidents", "Operator Clock Sheet"
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
        JTabbedPane workTab = new MyJideTabbedPane();
//        workTab.add(getContractsPanel(), "Contracts");
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            workTab.add(getSitesPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            workTab.add(getDieselPrchsPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            workTab.add(getDieselCardsPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            workTab.add(getConsumablesPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            workTab.add(getBreakdownsPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
            workTab.add(getFuelPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
            workTab.add(getIssuesPanel(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
            workTab.add(getSiteDiaryPanel(), sheets()[7]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[8])) {
            workTab.add(getIncidentsPanel(), sheets()[8]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[9])) {
            workTab.add(getOperatorClockSheetPanel(), sheets()[9]);
        }
        return workTab;
    }

    private JPanel getSitesPanel() {
        if (sitesPanel == null) {
            try {
                registerGrid(sitesPanel = new SitesGrid(getExchanger()));
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
                registerGrid(disprchsPanel = new DieselPurchaseGrid(getExchanger()));
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
                registerGrid(dieselIsissPanel = new DieselCardsGrid(getExchanger()));
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
                registerGrid(consumablesPanel = new ConsumablesGrid(getExchanger()));
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
                registerGrid(breakdownsPanel = new BreakdownsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return breakdownsPanel;
    }

    private JPanel getFuelPanel() {
        if (fuelPanel == null) {
            try {
                registerGrid(fuelPanel = new FuelGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return fuelPanel;
    }

    private JPanel getIssuesPanel() {
        if (issuePanel == null) {
            try {
                registerGrid(issuePanel = new IssueGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return issuePanel;
    }

    private JPanel getSiteDiaryPanel() {
        if (siteDiaryPanel == null) {
            try {
                registerGrid(siteDiaryPanel = new SiteDiaryGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return siteDiaryPanel;
    }

    private Component getIncidentsPanel() {
        if (incidentsPanel == null) {
            try {
                registerGrid(incidentsPanel = new IncidentsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return incidentsPanel;
    }

    private Component getOperatorClockSheetPanel() {
        if (operatorClockSheetPanel == null) {
            try {
                registerGrid(operatorClockSheetPanel = new OperatorClockSheetGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return operatorClockSheetPanel;
    }
}
