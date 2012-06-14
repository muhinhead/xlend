package com.xlend.gui.admin;

import com.jidesoft.swing.JideTabbedPane;
import com.xlend.constants.Selects;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.MyJideTabbedPane;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.Controller;
import com.xlend.mvc.dbtable.DbTableGridPanel;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class AdminFrame extends GeneralFrame {

    private GeneralGridPanel usersPanel;
    private MachineTypeGrid machineTypesPanel;
    private MachineTypeGrid machineSubTypesPanel;
    private JideTabbedPane dictionaryPanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane admTab = new MyJideTabbedPane();
        admTab.add(getUsersPanel(), getSheetList()[0]);
        admTab.add(getDictionariesPanel(), getSheetList()[1]);
        return admTab;
    }

    private JPanel getUsersPanel() {
        if (usersPanel == null) {
            try {
                registerGrid(usersPanel = new UsersGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return usersPanel;
    }

    @Override
    protected String[] getSheetList() {
        return new String[]{"Users", "Dictionaries"};
    }

    private JComponent getDictionariesPanel() {
        if (dictionaryPanel == null) {
            try {
                registerGrid(machineSubTypesPanel = new MachineTypeGrid(getExchanger(), Selects.SELECT_MACHSUBTYPES, null));
                Controller detailController = machineSubTypesPanel.getController();
                XlendMasterTableView masterView = new XlendMasterTableView(getExchanger(), detailController, "parenttype_id", 0);
                registerGrid(machineTypesPanel = new MachineTypeGrid(getExchanger(), masterView));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
            dictionaryPanel = new JideTabbedPane();
            dictionaryPanel.setShowTabButtons(true);
            dictionaryPanel.setBoldActiveTab(true);
            dictionaryPanel.setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
            dictionaryPanel.setTabShape(JideTabbedPane.SHAPE_BOX);

            JSplitPane sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
            JPanel topPanel = new JPanel(new BorderLayout());
            JPanel bottomPanel = new JPanel(new BorderLayout());
            topPanel.add(machineTypesPanel);
            bottomPanel.add(machineSubTypesPanel);
            sp.setTopComponent(topPanel);
            sp.setBottomComponent(bottomPanel);
            sp.setDividerLocation(400);
            topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Machine Types"));
            bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Machine Subtypes"));
            dictionaryPanel.addTab("Machine Types", sp);
            final DbTableView tv = (DbTableView) machineTypesPanel.getController().getViews().get(0);
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    tv.gotoRow(0);
                }
            });
        }
        return dictionaryPanel;
    }
}
