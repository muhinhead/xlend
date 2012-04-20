package com.xlend.gui;

import com.xlend.gui.banking.AccountsGrid;
import com.xlend.gui.banking.BankBalanceGrid;
import com.xlend.gui.hr.EmployeesGrid;
import com.xlend.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class BankingFrame extends GeneralFrame {

    private AccountsGrid bankAccountsPanel;
    private static String[] sheetList = new String[]{
        "Accounts", "Bank Balance"
    };
    private AccountsGrid accountsPanel;
    private BankBalanceGrid balancePanel;
    
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
        JTabbedPane bankTab = new JTabbedPane();
        if (XlendWorks.availableForCurrentUsder(sheets()[0])) {
            bankTab.add(getAccountsPanel(), sheets()[0]);
        }
        if (XlendWorks.availableForCurrentUsder(sheets()[1])) {
            bankTab.add(getBalancePanel(), sheets()[1]);
        }
        return bankTab;
    }

    private Component getAccountsPanel() {
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

    private Component getBalancePanel() {
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
    
}
