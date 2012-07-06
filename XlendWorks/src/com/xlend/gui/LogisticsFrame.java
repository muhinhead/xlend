package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.gui.logistics.AllTripsGrid;
import com.xlend.gui.logistics.GroupTableGridPanel;
import com.xlend.gui.logistics.TripSheetGrid;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class LogisticsFrame extends GeneralFrame {

    private GeneralGridPanel tripSheetPanel = null;
    private GeneralGridPanel tripPanel = null;

    private static String[] sheetList = new String[]{
        "Trip Sheets", "Transport Schedule", "Trips"
    };

    public LogisticsFrame(IMessageSender exch) {
        super("Logistics", exch);
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
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            workTab.addTab(getTripSheetPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            workTab.addTab(getTransportSchedulePanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            workTab.addTab(getTripsPanel(), sheets()[2]);
        }
        return workTab;
    }

    private JPanel getTripSheetPanel() {
        if (tripSheetPanel == null) {
            try {
                registerGrid(tripSheetPanel = new TripSheetGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return tripSheetPanel;
    }
    
    private JPanel getTripsPanel() {
        if (tripPanel == null) {
            try {
                registerGrid(tripPanel = new AllTripsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return tripPanel;
    }

    private JComponent getTransportSchedulePanel() {
        return new GroupTableGridPanel(getExchanger(), Selects.SELECT_TRANSSCHEDULE_BY_DATE);
    }
}
