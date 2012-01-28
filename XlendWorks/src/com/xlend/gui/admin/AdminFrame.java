package com.xlend.gui.admin;

import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class AdminFrame extends GeneralFrame {

    private GeneralGridPanel usersPanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        JTabbedPane admTab = new JTabbedPane();
        admTab.add(getUsersPanel(), getSheetList()[0]);
        return admTab;
    }

    private JPanel getUsersPanel() {
        if (usersPanel == null) {
            try {
                registerGrid(usersPanel = new UsersGrid(exchanger));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return usersPanel;
    }

    @Override
    protected String[] getSheetList() {
        return new String[] {"Users"};
    }
}
