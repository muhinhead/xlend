package com.xlend.gui;

import com.xlend.gui.banking.AccountsGrid;
import com.xlend.gui.banking.BankBalanceGrid;
import com.xlend.gui.banking.DieselPurchaseGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class BankingFrame extends GeneralFrame {

    private AccountsGrid bankAccountsPanel;
    private static String[] sheetList = new String[]{
        "Accounts", "Bank Balance", "Diesel Purchase"
    };
    private GeneralGridPanel accountsPanel;
    private GeneralGridPanel balancePanel;
    private GeneralGridPanel dieselPurchasePanel;
    
    public BankingFrame(IMessageSender exch) {
        super("Banking", exch);
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
        MyJideTabbedPane bankTab = new MyJideTabbedPane();
        if (XlendWorks.availableForCurrentUser(sheets()[0])) {
            bankTab.addTab(getAccountsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[1])) {
            bankTab.addTab(getBalancePanel(), sheets()[1]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[2])) {
            bankTab.addTab(getDieselPurchasePanel(), sheets()[2]);
        }
        return bankTab;
    }

    private JPanel getAccountsPanel() {
        if (accountsPanel == null) {
            try {
                registerGrid(accountsPanel = new AccountsGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return accountsPanel;
    }

    private JPanel getBalancePanel() {
         if (balancePanel == null) {
            try {
                registerGrid(balancePanel = new BankBalanceGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return balancePanel;
    }
    
    private JPanel getDieselPurchasePanel() {
        if (dieselPurchasePanel == null) {
            try {
                registerGrid(dieselPurchasePanel = new DieselPurchaseGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return dieselPurchasePanel;
    }
}
