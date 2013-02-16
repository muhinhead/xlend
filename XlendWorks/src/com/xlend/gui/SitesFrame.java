package com.xlend.gui;

import com.xlend.gui.site.IssueToDieselCartGrid;
import com.xlend.gui.site.*;
import com.xlend.gui.work.SitesGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

/**
 *
 * @author Nick Mukhin
 */
public class SitesFrame extends GeneralFrame {

    private GeneralGridPanel sitesPanel = null;
//    private GeneralGridPanel disprchsPanel = null;
//    private GeneralGridPanel dieselIsissPanel = null;
    private GeneralGridPanel consumablesPanel = null;
    private GeneralGridPanel breakdownsPanel;
    private GeneralGridPanel fuelPanel;
//    private GeneralGridPanel issuePanel;
    private GeneralGridPanel siteDiaryPanel;
    private GeneralGridPanel incidentsPanel;
    private GeneralGridPanel operatorClockSheetPanel;
    private GeneralGridPanel ppeBuysPanel;
    private GeneralGridPanel ppeIssuesPanel;
    private GeneralGridPanel issueToDieselCartPanel;
    private GeneralGridPanel dieselToPlantPanel;
    private static String[] sheetList = new String[]{
        "Sites",
        //        "Diesel Purchases",//"Diesel Rurchases", 
        //        "Yard Diesel",//Diesel Issuing", 
        "Consumables",
        "Breakdowns",
        "Petrol Issued",//Fuel", 
        //"Site Diesel",//Issuing", 
        "Site Diary",
        "Incidents",
        "Operator Clock Sheet",
        "PPE and Safety",
        "Issue to Diesel Cart",
        "Diesel to Plant"
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
        MyJideTabbedPane workTab = new MyJideTabbedPane();
//        workTab.addTab(getContractsPanel(), "Contracts");
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            workTab.addTab(getSitesPanel(), sheets()[0]);
        }
//        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
//            workTab.addTab(getDieselPrchsPanel(), sheets()[1]);
//        }
//        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
//            workTab.addTab(getDieselCardsPanel(), sheets()[2]);
//        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            workTab.addTab(getConsumablesPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            workTab.addTab(getBreakdownsPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            workTab.addTab(getFuelPanel(), sheets()[3]);
        }
//        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
//            workTab.addTab(getIssuesPanel(), sheets()[6]);
//        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            workTab.addTab(getSiteDiaryPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
            workTab.addTab(getIncidentsPanel(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
            workTab.addTab(getOperatorClockSheetPanel(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
            workTab.addTab(getPPEandSafetyPanel(), sheets()[7]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[8])) {
            workTab.addTab(getIssueToDieselCartPanel(), sheets()[8]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[9])) {
            workTab.addTab(getDieselToPlantPanel(), sheets()[9]);
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

//    private JPanel getDieselPrchsPanel() {
//        if (disprchsPanel == null) {
//            try {
//                registerGrid(disprchsPanel = new DieselPurchaseGrid(getExchanger()));
//            } catch (RemoteException ex) {
//                XlendWorks.log(ex);
//                errMessageBox("Error:", ex.getMessage());
//            }
//        }
//        return disprchsPanel;
//    }
//
//    private JPanel getDieselCardsPanel() {
//        if (dieselIsissPanel == null) {
//            try {
//                registerGrid(dieselIsissPanel = new DieselCardsGrid(getExchanger()));
//            } catch (RemoteException ex) {
//                XlendWorks.log(ex);
//                errMessageBox("Error:", ex.getMessage());
//            }
//        }
//        return dieselIsissPanel;
//    }
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

//    private JPanel getIssuesPanel() {
//        if (issuePanel == null) {
//            try {
//                registerGrid(issuePanel = new IssueGrid(getExchanger()));
//            } catch (RemoteException ex) {
//                XlendWorks.log(ex);
//                errMessageBox("Error:", ex.getMessage());
//            }
//        }
//        return issuePanel;
//    }
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

    private JPanel getIncidentsPanel() {
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

    private JPanel getOperatorClockSheetPanel() {
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

    private JComponent getIssueToDieselCartPanel() {
        if (issueToDieselCartPanel == null) {
            try {
                registerGrid(issueToDieselCartPanel = new IssueToDieselCartGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return issueToDieselCartPanel;
    }

    private JComponent getDieselToPlantPanel() {
        if (dieselToPlantPanel == null) {
            try {
                registerGrid(dieselToPlantPanel = new DieselToPlantGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return dieselToPlantPanel;
    }

    private JComponent getPPEandSafetyPanel() {
        JSplitPane sp = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        sp.setDividerLocation(250);
        try {
            sp.add(ppeBuysPanel = new PPEbuysGrid(getExchanger()));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
            errMessageBox("Error:", ex.getMessage());
            sp.add(new JLabel(ex.getMessage(), SwingConstants.CENTER));
        }

        try {
            sp.add(ppeIssuesPanel = new PPEissuesGrid(getExchanger()));
        } catch (RemoteException ex) {
            XlendWorks.log(ex);
            errMessageBox("Error:", ex.getMessage());
            sp.add(new JLabel(ex.getMessage(), SwingConstants.CENTER));
        }
        return sp;
    }
}
