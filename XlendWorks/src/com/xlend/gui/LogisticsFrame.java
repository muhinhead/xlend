package com.xlend.gui;

import com.xlend.gui.logistics.TripSheetGrid;
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
public class LogisticsFrame extends GeneralFrame {

    private GeneralGridPanel tripSheetPanel = null;

    private static String[] sheetList = new String[]{
        "Treep Sheets"
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
        JTabbedPane workTab = new MyJideTabbedPane();
//        workTab.add(getContractsPanel(), "Contracts");
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            workTab.add(getTreepSheetPanel(), sheets()[0]);
        }
        return workTab;
    }

    private JPanel getTreepSheetPanel() {
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
}
