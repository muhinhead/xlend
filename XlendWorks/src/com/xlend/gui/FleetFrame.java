package com.xlend.gui;

import com.xlend.remote.IMessageSender;
import java.util.Properties;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class FleetFrame extends WorkFrame {
    public FleetFrame(IMessageSender exch) {
        super("Fleet", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane fleetTab = new JTabbedPane();
        fleetTab.add(new JPanel(), "Machine Files");
        fleetTab.add(new JPanel(), "Truck Files");
        fleetTab.add(new JPanel(), "Low-Beds");
        fleetTab.add(new JPanel(), "Pool Vehicles");
        fleetTab.add(new JPanel(), "Company Vehicles");
        return fleetTab;
    }

}
