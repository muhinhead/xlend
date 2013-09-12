package com.xlend.gui;

import com.xlend.gui.banking.CashDrawnGrid;
import static com.xlend.gui.GeneralFrame.errMessageBox;
import com.xlend.gui.banking.AccountsGrid;
import com.xlend.gui.banking.BankBalanceGrid;
import com.xlend.gui.banking.DieselPurchaseGrid;
import com.xlend.gui.banking.PettyInOutGrid;
import com.xlend.remote.IMessageSender;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author Nick Mukhin
 */
public class BankingFrame extends GeneralFrame {

    public static BankingFrame instance;
    private AccountsGrid bankAccountsPanel;
    private static String[] sheetList = new String[]{
        "Accounts", "Bank Balance", "Diesel Purchase","Petty In/Out","Cash Drawn"
    };
    private GeneralGridPanel accountsPanel;
    private GeneralGridPanel balancePanel;
    private GeneralGridPanel dieselPurchasePanel;
    private GeneralGridPanel pettyInOutPanel;
    private GeneralGridPanel cashDrawnPanel;

    public BankingFrame(IMessageSender exch) {
        super("Banking", exch);
        instance = this;
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
        if (XlendWorks.availableForCurrentUser(sheets()[3])) {
            bankTab.addTab(getPettyInOutPanel(), sheets()[3]);
        }
        if (XlendWorks.availableForCurrentUser(sheets()[4])) {
            bankTab.addTab(getCashDrawnPanel(), sheets()[4]);
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
    
    private JPanel getPettyInOutPanel() {
        if (pettyInOutPanel == null) {
            try {
                registerGrid(pettyInOutPanel = new PettyInOutGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return pettyInOutPanel;
    }

    private JPanel getCashDrawnPanel() {
        if (cashDrawnPanel == null) {
            try {
                registerGrid(cashDrawnPanel = new CashDrawnGrid(getExchanger()));
            } catch (RemoteException ex) {
                XlendWorks.log(ex);
                errMessageBox("Error:", ex.getMessage());
            }
        }
        return cashDrawnPanel;
    }
}
