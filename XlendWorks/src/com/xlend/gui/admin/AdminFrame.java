package com.xlend.gui.admin;

import com.jidesoft.swing.JideTabbedPane;
import com.xlend.constants.Selects;
import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import com.xlend.gui.GeneralGridPanel;
import com.xlend.gui.MyJideTabbedPane;
import com.xlend.gui.XlendWorks;
import com.xlend.mvc.Controller;
import com.xlend.mvc.dbtable.DbTableView;
import com.xlend.remote.IMessageSender;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private JPanel dataControlPanel;
    private PaidMethodsGrid paidMathodsPanel;
    private PayFromGrid payFromPanel;
    private WageCategoryGrid wageCategoryPanel;
    private RatedMachinesGrid ratedMachinesPanel;
    private SiteTypeGrid siteTypePanel;
    private PPEtypeGrid ppeTypePanel;

    public AdminFrame(IMessageSender exch) {
        super("Admin Console", exch);
    }

    protected JTabbedPane getMainPanel() {
        MyJideTabbedPane admTab = new MyJideTabbedPane();
        admTab.addTab(getUsersPanel(), getSheetList()[0]);
        admTab.addTab(getDictionariesPanel(), getSheetList()[1]);
        admTab.addTab(getDataControl(), getSheetList()[2]);
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
        return new String[]{"Users", "Dictionaries", "Data control"};
    }

    private JComponent getDictionariesPanel() {
        if (dictionaryPanel == null) {
            try {
                machineSubTypesPanel = new MachineTypeGrid(getExchanger(), Selects.SELECT_MACHSUBTYPES, null, true);
                Controller detailController = machineSubTypesPanel.getController();
                XlendMasterTableView masterView = new XlendMasterTableView(getExchanger(), detailController, "parenttype_id", 0);
                registerGrid(machineTypesPanel = new MachineTypeGrid(getExchanger(), masterView));
                registerGrid(paidMathodsPanel = new PaidMethodsGrid(getExchanger()));
                registerGrid(payFromPanel = new PayFromGrid(getExchanger()));
                registerGrid(wageCategoryPanel = new WageCategoryGrid(getExchanger()));
                registerGrid(ratedMachinesPanel = new RatedMachinesGrid(getExchanger()));
                registerGrid(siteTypePanel = new SiteTypeGrid(getExchanger()));
                registerGrid(ppeTypePanel = new PPEtypeGrid(getExchanger()));
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
            dictionaryPanel.addTab("Rated Machines", ratedMachinesPanel);
            dictionaryPanel.addTab("Site Types", siteTypePanel);
            dictionaryPanel.addTab("PPE Types", ppeTypePanel);

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

    private JPanel getDataControl() {
        if (dataControlPanel == null) {
            dataControlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            dataControlPanel.add(new JButton(new AbstractAction("Clear Assignments List") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (GeneralFrame.yesNo("ATTENTION!!!", "The Operator/Machine assignments list will be completely cleared!\n"
                            + "It is an irreversible operation!\nAre you sure?") == JOptionPane.YES_OPTION) {
                        try {
                            int count = DashBoard.getExchanger().getCount("select * from xopmachassing");
                            DashBoard.getExchanger().truncateTable("xopmachassing");
                            GeneralFrame.infoMessageBox("Ok", "" + count + " rows deleted");
                        } catch (RemoteException ex) {
                            XlendWorks.logAndShowMessage(ex);
                        }
                    }
                }
            }));
        }
        return dataControlPanel;
    }
}
