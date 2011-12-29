package com.xlend.gui.work;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class SitesFrame extends GeneralFrame {

//    private GeneralGridPanel contractsPanel = null;
    private GeneralGridPanel sitesPanel = null;
    
     public SitesFrame(IMessageSender exch) {
        super("Sites", exch);
    }

    @Override
    protected JTabbedPane getMainPanel() {
        JTabbedPane workTab = new JTabbedPane();
//        workTab.add(getContractsPanel(), "Contracts");
        workTab.add(getSitesPanel(), "Sites");
        workTab.add(new JPanel(), "Fuel");
        return workTab;
    }

//    private JPanel getContractsPanel() {
//        if (contractsPanel == null) {
//            try {
//                registerGrid(contractsPanel = new ContractsGrid(exchanger));
//            } catch (RemoteException ex) {
//                XlendWorks.log(ex);
//                errMessageBox("Error:", ex.getMessage());
//            }
//        }
//        return contractsPanel;
//    }

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
}
