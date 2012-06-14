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
    private PaidMethodsGrid paidMathodsPanel;
    private PayFromGrid payFromPanel;
    private WageCategoryGrid wageCategoryPanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane admTab = new MyJideTabbedPane();
        admTab.addTab(getUsersPanel(), getSheetList()[0]);
        admTab.addTab(getDictionariesPanel(), getSheetList()[1]);
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
                machineSubTypesPanel = new MachineTypeGrid(getExchanger(), Selects.SELECT_MACHSUBTYPES, null);
                Controller detailController = machineSubTypesPanel.getController();
                XlendMasterTableView masterView = new XlendMasterTableView(getExchanger(), detailController, "parenttype_id", 0);
                registerGrid(machineTypesPanel = new MachineTypeGrid(getExchanger(), masterView));
                registerGrid(paidMathodsPanel = new PaidMethodsGrid(getExchanger()));
                registerGrid(payFromPanel = new PayFromGrid(getExchanger()));
                registerGrid(wageCategoryPanel = new WageCategoryGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
            dictionaryPanel = new JideTabbedPane();
            dictionaryPanel.setShowTabButtons(true);
            dictionaryPanel.setBoldActiveTab(true);
            dictionaryPanel.setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
            dictionaryPanel.setTabShape(JideTabbedPane.SHAPE_BOX);

            dictionaryPanel.addTab("Machine Types", getMachineTypeSplitPanel());//machitypeSplitPanel);
            dictionaryPanel.addTab("Pay Methods", paidMathodsPanel);
            dictionaryPanel.addTab("Pay From", payFromPanel);
            dictionaryPanel.addTab("Wage Category", wageCategoryPanel);

            final DbTableView tv = (DbTableView) machineTypesPanel.getController().getViews().get(0);
            SwingUtilities.invokeLater(new Runnable() {

                public void run() {
                    tv.gotoRow(0);
                }
            });
        }
        return dictionaryPanel;
    }

    private JSplitPane getMachineTypeSplitPanel() {
        JSplitPane machitypeSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        topPanel.add(machineTypesPanel);
        bottomPanel.add(machineSubTypesPanel);
        machitypeSplitPanel.setTopComponent(topPanel);
        machitypeSplitPanel.setBottomComponent(bottomPanel);
        machitypeSplitPanel.setDividerLocation(400);
        topPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Machine Types"));
        bottomPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Machine Subtypes"));
        return machitypeSplitPanel;
    }
}
