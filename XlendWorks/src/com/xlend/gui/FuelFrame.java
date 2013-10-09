package com.xlend.gui;

import com.xlend.gui.site.DieselToPlantGrid;
import com.xlend.gui.site.FuelGrid;
import com.xlend.gui.site.IssueToDieselCartGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class FuelFrame extends GeneralFrame {

    private static String[] sheetList = new String[]{
        "Petrol Issued",//Fuel", 
        "Issue to Diesel Cart",
        "Diesel to Plant"
    };

    /**
     * @param aSheetList the sheetList to set
     */
    public static void setSheetList(String[] aSheetList) {
        sheetList = aSheetList;
    }
    private FuelGrid fuelPanel;
    private IssueToDieselCartGrid issueToDieselCartPanel;
    private DieselToPlantGrid dieselToPlantPanel;

    public FuelFrame(IMessageSender exch) {
        super("Fuel", exch);
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
        MyJideTabbedPane fuelTab = new MyJideTabbedPane();
//        int n = 0;
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            fuelTab.addTab(getFuelPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            fuelTab.addTab(getIssueToDieselCartPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            fuelTab.addTab(getDieselToPlantPanel(), sheets()[2]);
        }
        return fuelTab;
    }

    private JComponent getFuelPanel() {
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
}
