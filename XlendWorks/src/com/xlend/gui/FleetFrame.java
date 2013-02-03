package com.xlend.gui;

import com.xlend.gui.fleet.*;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class FleetFrame extends GeneralFrame {

    /**
     * @param aSheetList the sheetList to set
     */
    public static void setSheetList(String[] aSheetList) {
        sheetList = aSheetList;
    }
    private GeneralGridPanel machinesPanel;
    private GeneralGridPanel trackPanel;
    private GeneralGridPanel lowbedsPanel;
    private GeneralGridPanel machineRentalRatesPanel;
    private GeneralGridPanel poolVehiclesPanel;
    private GeneralGridPanel companyVehiclesPanel;
    private GeneralGridPanel dieselCartsPanel;
    private GeneralGridPanel machineServicesPanel;
    private static String[] sheetList = new String[]{
        "Machine Files", "Truck Files", "Low-Beds", "Pool Vehicles", 
        "Company Vehicles", "Machine Rental Rates", "Diesel Carts", 
        "Service"
    };

    public FleetFrame(IMessageSender exch) {
        super("Fleet", exch);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    public static String[] sheets() {
        return sheetList;
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane fleetTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            fleetTab.addTab(getMachinesPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            fleetTab.addTab(getTrackPanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            fleetTab.addTab(getLowBedsPanel(), sheets()[2]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            fleetTab.addTab(getPoolVehiclesPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            fleetTab.addTab(getCompanyVehiclesPanel(), sheets()[4]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[5])) {
            fleetTab.addTab(getMachineRentalRates(), sheets()[5]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[6])) {
            fleetTab.addTab(getDieselCarts(), sheets()[6]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[7])) {
            fleetTab.addTab(getMachineServices(), sheets()[7]);
        }
        return fleetTab;
    }

    private JPanel getMachinesPanel() {
        if (machinesPanel == null) {
            try {
                registerGrid(machinesPanel = new MachineGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machinesPanel;
    }

    private JPanel getTrackPanel() {
        if (trackPanel == null) {
            try {
                registerGrid(trackPanel = new TrackGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return trackPanel;
    }

    private JPanel getLowBedsPanel() {
        if (lowbedsPanel == null) {
            try {
                registerGrid(lowbedsPanel = new LowBedGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return lowbedsPanel;
    }
    
    private JPanel getMachineRentalRates() {
        if (machineRentalRatesPanel == null) {
            try {
                registerGrid(machineRentalRatesPanel = new MachineRentalRatesGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machineRentalRatesPanel;
    }
    
    private JPanel getPoolVehiclesPanel() {
        if (poolVehiclesPanel == null) {
            try {
                registerGrid(poolVehiclesPanel = new PoolVehicleGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return poolVehiclesPanel;
    }

    private JPanel getCompanyVehiclesPanel() {
        if (companyVehiclesPanel == null) {
            try {
                registerGrid(companyVehiclesPanel = new CompanyVehicleGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return companyVehiclesPanel;
    }    

    private JPanel getMachineServices() {
        if(machineServicesPanel == null) {
            try {
                registerGrid(machineServicesPanel = new ServiceGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return machineServicesPanel;
    }

    private JComponent getDieselCarts() {
        if (dieselCartsPanel == null) {
            try {
                registerGrid(dieselCartsPanel = new DieselCartsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return dieselCartsPanel;
    }
}
