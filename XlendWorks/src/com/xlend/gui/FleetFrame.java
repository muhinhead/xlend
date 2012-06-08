package com.xlend.gui;

import com.xlend.gui.fleet.LowBedGrid;
import com.xlend.gui.fleet.MachineGrid;
import com.xlend.gui.fleet.TrackGrid;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import java.util.Properties;
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
    private static String[] sheetList = new String[]{
        "Machine Files", "Truck Files", "Low-Beds", "Pool Vehicles", "Company Vehicles"};

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
            fleetTab.addTab(new JPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            fleetTab.addTab(new JPanel(), sheets()[4]);
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
}
