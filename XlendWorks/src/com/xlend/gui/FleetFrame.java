package com.xlend.gui;

import com.xlend.gui.fleet.MachineGrid;
import com.xlend.gui.fleet.TrackGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class FleetFrame extends GeneralFrame {

    private GeneralGridPanel machinesPanel;
    private GeneralGridPanel trackPanel;

    public FleetFrame(IMessageSender exch) {
        super("Fleet", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane fleetTab = new JTabbedPane();
        fleetTab.add(getMachinesPanel(), "Machine Files");
        fleetTab.add(getTrackPanel(), "Truck Files");
        fleetTab.add(new JPanel(), "Low-Beds");
        fleetTab.add(new JPanel(), "Pool Vehicles");
        fleetTab.add(new JPanel(), "Company Vehicles");
        return fleetTab;
    }

    private JPanel getMachinesPanel() {
        if (machinesPanel == null) {
            try {
                registerGrid(machinesPanel = new MachineGrid(exchanger));
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
                registerGrid(trackPanel = new TrackGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return trackPanel;
    }
}
