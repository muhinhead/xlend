package com.xlend.gui;

import com.jidesoft.plaf.LookAndFeelFactory;
import com.xlend.constants.Selects;
import com.xlend.orm.*;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import com.xlend.rmi.ExchangeFactory;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Nick Mukhin
 */
public class XlendWorks {

    private static IMessageSender exchanger;

    /**
     * @return the exchanger
     */
    public static IMessageSender getExchanger() {
        return exchanger;
    }

    /**
     * @param aExchanger the exchanger to set
     */
    public static void setExchanger(IMessageSender aExchanger) throws Exception {
        if (aExchanger == null) {
            throw new Exception("Data exchanger creation fails");
        }
        exchanger = aExchanger;
    }

    public static Double getUnallocatedTotalPettyForSite(Integer siteID, java.util.Date dt1, java.util.Date dt2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        ComboItem[] itm = loadOnSelect(
                "select 0, sum(i.amount) "
                + "from xpetty p, xpettyitem i left outer join xmachine m on i.xmachine_id=m.xmachine_id "
                + "where i.xpetty_id=p.xpetty_id "
                + " and receipt_date between '" + fmt.format(dt1) + "' and '" + fmt.format(dt2) + "' "
                + " and i.xsite_id=" + siteID + " and (m.xmachine_id is null or classify not in ('T','M'))");
        try {
            if (itm.length > 0) {
                return new Double(itm[0].getValue());
            }
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    public static Double getTotalPettyForSite(Integer siteID, java.util.Date dt1, java.util.Date dt2) {
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        ComboItem[] itm = loadOnSelect(
                "select 0, sum(i.amount) "
                + "from xpetty p, xpettyitem i where i.xpetty_id=p.xpetty_id "
                + " and receipt_date between '" + fmt.format(dt1) + "' and '" + fmt.format(dt2) + "' "
                + " and i.xsite_id=" + siteID);
        try {
            if (itm.length > 0) {
                return new Double(itm[0].getValue());
            }
        } catch (NumberFormatException nfe) {
        }
        return null;
    }

    public static class XDate extends java.sql.Date {

        public XDate(long t) {
            super(t);
        }

        @Override
        public String toString() {
            if (getTime() == 0) {
                return "--Unknown--";
            }
            String s = super.toString();
            return s.substring(8) + "/" + s.substring(5, 7) + "/" + s.substring(0, 4);
        }
    };
    public static final String version = "0.83.D";
    public static String protocol = "unknown";
    private static Userprofile currentUser;
    private static Logger logger = null;
    private static FileHandler fh;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            String current = new java.io.File(".").getCanonicalPath();
//            String osName = System.getProperty("os.name");
//            String osVersion = System.getProperty("os.version");
//    if("Windows 7".equals(osName))
//            System.out.println("OS:["+osName+"]");
//            System.out.println("Vr:["+osVersion+"]");

            System.out.println("Current dir:" + current);
            String currentDir = System.getProperty("user.dir");
            System.out.println("Current dir using System:" + currentDir);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        LookAndFeelFactory.installDefaultLookAndFeelAndExtension();
        String serverIP = DashBoard.readProperty("ServerAddress", "localhost");
        while (true) {
            try {
                IMessageSender exc = ExchangeFactory.getExchanger("rmi://" + serverIP + "/XlendServer", DashBoard.getProperties());
//                setExchanger((IMessageSender) Naming.lookup("rmi://" + serverIP + "/XlendServer"));
                if (exc == null) {
                    exc = ExchangeFactory.getExchanger(DashBoard.readProperty("JDBCconnection", "jdbc:mysql://localhost/xlend"),
                            DashBoard.getProperties());
                }
                setExchanger(exc);
                if (matchVersions() && login()) {
                    new DashBoard(getExchanger());
                    break;
                } else {
                    System.exit(1);
                }
            } catch (Exception ex) {
                logAndShowMessage(ex);
                if ((serverIP = serverSetup("Check server settings")) == null) {
                    System.exit(1);
                } else {
                    DashBoard.saveProps();
                }
            }
        }
    }

    public static void log(String msg) {
        log(msg, null);
    }

    public static void log(Throwable th) {
        log(null, th);
    }

    private static void log(String msg, Throwable th) {
        if (logger == null) {
            try {
                logger = Logger.getLogger("XlendWorks");
                fh = new FileHandler("%h/XlendWorks.log", 1048576, 10, true);
                logger.addHandler(fh);
                logger.setLevel(Level.ALL);
                SimpleFormatter formatter = new SimpleFormatter();
                fh.setFormatter(formatter);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        logger.log(Level.SEVERE, msg, th);
    }

    /**
     * @return the currentUser
     */
    public static Userprofile getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(Userprofile aCurrentUser) {
        currentUser = aCurrentUser;
    }

    public static String getCurrentUserLogin() {
        if (currentUser != null) {
            return currentUser.getLogin();
        }
        return "";
    }

    public static boolean isCurrentAdmin() {
        return currentUser != null && currentUser.getLogin().equalsIgnoreCase("admin");
    }

    public static void logAndShowMessage(Throwable ne) {
        GeneralFrame.errMessageBox("Error:", ne.getMessage());
        log(ne);
    }

    public static boolean login() {
        try {
            new LoginImagedDialog(getExchanger());//new Object[]{loginField, pwdField, exchanger});
            return LoginImagedDialog.isOkPressed();
        } catch (Throwable ee) {
            GeneralFrame.errMessageBox("Error:", "Server failure\nCheck your logs please");
            log(ee);
        }
        return false;
    }

    public static boolean checkAdminPassword(String pwd) throws RemoteException {
        DbObject[] users = getExchanger().getDbObjects(Userprofile.class,
                "login='admin' and pwdmd5='" + pwd + "'", null);
        return users.length > 0;
    }

    public static String serverSetup(String title) {
        String address = DashBoard.readProperty("ServerAddress", "localhost");
        String[] vals = address.split(":");
        JTextField imageDirField = new JTextField(DashBoard.getProperties().getProperty("imagedir"));
        JTextField addressField = new JTextField(16);
        addressField.setText(vals[0]);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(
                vals.length > 1 ? new Integer(vals[1]) : 1099, 0, 65536, 1));
        JComponent[] edits = new JComponent[]{imageDirField, addressField, portSpinner};
        new ConfigEditor(title, edits);
        if (addressField.getText().trim().length() > 0) {
            String addr = addressField.getText() + ":" + portSpinner.getValue();
            DashBoard.getProperties().setProperty("ServerAddress", addr);
            DashBoard.getProperties().setProperty("imagedir", imageDirField.getText());
            return addr;
        } else {
            return null;
        }
    }

    public static ComboItem[] loadAllContracts(int client_id) {
        try {
            DbObject[] contracts = getExchanger().getDbObjects(Xcontract.class, "xclient_id=" + client_id, "contractref");
            ComboItem[] itms = new ComboItem[contracts.length + 1];
            itms[0] = new ComboItem(0, "--No contract yet--");
            int i = 1;
            for (DbObject o : contracts) {
                Xcontract xcontract = (Xcontract) o;
                itms[i++] = new ComboItem(xcontract.getXcontractId(), xcontract.getContractref());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static List<ComboItem> loadEmployeeList(String field) {
        //        ArrayList<ComboItem> list = new ArrayList<ComboItem>();
        //        try {
        //            DbObject[] employees = exchanger.getDbObjects(Xemployee.class, Selects.activeEmployeeCondition, "clock_num");
        //            for (DbObject e : employees) {
        //                Xemployee emp = (Xemployee) e;
        //                list.add(new ComboItem(emp.getXemployeeId(), field.equals("name") ? emp.getFirstName() : emp.getClockNum()));
        //            }
        //        } catch (RemoteException ex) {
        //            log(ex);
        //        }
        //        return list;
        ComboItem[] arr = loadOnSelect("select xemployee_id,"
                + (field.equals("name") ? "first_name" : "clock_num")
                + " from xemployee where " + Selects.activeEmployeeCondition);
        ArrayList<ComboItem> list = new ArrayList<ComboItem>();
        for (ComboItem ci : arr) {
            list.add(ci);
        }
        return list;
    }
    private static ArrayList<ComboItem> employeesCache = new ArrayList<ComboItem>();

    public static void refreshEmployeeCache() {
        employeesCache.clear();
        loadEmployees();
    }

    public static ArrayList<ComboItem> loadAllEmployees() {
        if (employeesCache.isEmpty()) {
            ComboItem[] emps = loadAllEmployees(Selects.activeEmployeeCondition);
            for (ComboItem ci : emps) {
                employeesCache.add(ci);
            }
        }
        return employeesCache;
    }

    public static ComboItem[] loadAllEmployees(String whereCond) {
        return loadOnSelect("select xemployee_id,concat(clock_num,' ',first_name) from xemployee order by clock_numonly");
    }

    public static ComboItem[] loadAllXpettyCategories() {
        return loadOnSelect("select xpettycategory_id,concat(lpad(xpettycategory_id,2,'0'),' ',category_name) from xpettycategory");
    }

    public static ComboItem[] loadAllRFQs(int client_id) {
//        try {
//            DbObject[] rfqs = exchanger.getDbObjects(Xquotation.class, "xclient_id=" + client_id, "rfcnumber");
//            ComboItem[] itms = new ComboItem[rfqs.length + 1];
//            itms[0] = new ComboItem(0, "--No requests for quotation yet--");
//            int i = 1;
//            for (DbObject o : rfqs) {
//                Xquotation xquotation = (Xquotation) o;
//                itms[i++] = new ComboItem(xquotation.getXquotationId(), xquotation.getRfcnumber());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select xquotation_id,rfcnumber from xquotation where xclient_id=" + client_id + " order by rfcnumber");
    }

    public static ComboItem[] loadAllOrderSites(int order_id) {
//        try {
//            DbObject[] rfqs = exchanger.getDbObjects(Xsite.class,
//                    "xorder_id=" + order_id, "name");
//            ComboItem[] itms = new ComboItem[rfqs.length + 1];
//            itms[0] = new ComboItem(0, "--No requests for clients yet--");
//            int i = 1;
//            for (DbObject o : rfqs) {
//                Xsite xsite = (Xsite) o;
//                itms[i++] = new ComboItem(xsite.getXsiteId(), xsite.getName());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select xsite_id,name from xsite where xorder_id=" + order_id + " order by name");
    }

    public static ComboItem[] loadSites(String whereCond) {
//        try {
//            DbObject[] orders = exchanger.getDbObjects(Xsite.class, whereCond, "name");
//            ComboItem[] itms = new ComboItem[orders.length + 1];
//            itms[0] = new ComboItem(0, "--Add new site --");
//            int i = 1;
//            for (DbObject o : orders) {
//                Xsite xsite = (Xsite) o;
//                itms[i++] = new ComboItem(xsite.getXsiteId(), xsite.getName());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select xsite_id,name from xsite where " + whereCond + " order by name");
    }

    public static ComboItem loadSite(Integer siteID) {
        //        if (siteID != null && siteID.intValue() > 0) {
        //            try {
        //                Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, siteID);
        //                return new ComboItem(xsite.getXsiteId(), xsite.getName());
        //            } catch (RemoteException ex) {
        //                log(ex);
        //            }
        //        }
        //        return null;
        ComboItem citms[] = loadOnSelect("select xsite_id,name from xsite where xsite_id=" + siteID);
        if (citms != null && citms.length > 0) {
            return citms[0];
        }
        return null;
    }

    public static ComboItem[] loadActiveSites() {
        return loadSites("is_active=1");
    }

    public static ComboItem[] loadAllOrders() {
//        try {
//            DbObject[] orders = exchanger.getDbObjects(Xorder.class, null, "ordernumber");
//            ComboItem[] itms = new ComboItem[orders.length + 2];
//            itms[0] = new ComboItem(-1, "--ORDER NOT RECEIVED--");
//            itms[1] = new ComboItem(0, "--Add new order--");
//            int i = 2;
//            for (DbObject o : orders) {
//                Xorder xorder = (Xorder) o;
//                itms[i++] = new ComboItem(xorder.getXorderId(), "Order Nr:" + xorder.getOrdernumber());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select -1 as xorder_id,'--ORDER NOT RECEIVED--' as ordernumber "
                + "union select 0,'--Add new order--' "
                + "union select xorder_id,concat('Order Nr:',ordernumber) from xorder order by ordernumber");
    }

    public static ComboItem[] loadRootMachTypes(String classify) {
//        try {
//            DbObject[] clients = exchanger.getDbObjects(Xmachtype.class, "parenttype_id is null and classify in ('" + classify + "')", "xmachtype_id");
//            ComboItem[] itms = new ComboItem[clients.length];
//            int i = 0;
//            for (DbObject o : clients) {
//                Xmachtype tp = (Xmachtype) o;
//                itms[i++] = new ComboItem(tp.getXmachtypeId(), tp.getMachtype());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select xmachtype_id,machtype "
                + "from xmachtype "
                + "where parenttype_id is null "
                + "and classify in ('" + classify + "') order by xmachtype_id");
    }

    public static ComboItem[] loadAllPositions() {
        try {
            DbObject[] clients = getExchanger().getDbObjects(Xposition.class, null, "pos");
            ComboItem[] itms = new ComboItem[clients.length + 1];
            itms[0] = new ComboItem(0, "--Add new position--");
            int i = 1;
            for (DbObject o : clients) {
                Xposition pos = (Xposition) o;
                itms[i++] = new ComboItem(pos.getXpositionId(), pos.getPos());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllUsers() {
//        try {
//            DbObject[] users = exchanger.getDbObjects(Profile.class, "profile_id in (select profile_id from userprofile)", "last_name");
//            ComboItem[] itms = new ComboItem[users.length + 1];
//            //itms[0] = new ComboItem(0, "--Add new client--");
//            int i = 0;
//            for (DbObject o : users) {
//                Profile user = (Profile) o;
//                itms[i++] = new ComboItem(user.getProfileId(),
//                        user.getFirstName().substring(0, 1) + "." + user.getLastName());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select profile_id, concat(substr(first_name,1,1),'.',last_name) from profile order by last_name");
    }

    public static ComboItem[] loadAllClients() {
//        try {
//            DbObject[] clients = exchanger.getDbObjects(Xclient.class, null, "companyname");
//            ComboItem[] itms = new ComboItem[clients.length + 1];
//            itms[0] = new ComboItem(0, "--Add new client--");
//            int i = 1;
//            for (DbObject o : clients) {
//                Xclient xclient = (Xclient) o;
//                itms[i++] = new ComboItem(xclient.getXclientId(), xclient.getCompanyname());
//            }
//            return itms;
//        } catch (RemoteException ex) {
//            log(ex);
//        }
//        return null;
        return loadOnSelect("select xclient_id,companyname from xclient order by companyname");
    }

    public static List loadAllLogins() {
        try {
            DbObject[] users = getExchanger().getDbObjects(Userprofile.class, null, "login");
            ArrayList logins = new ArrayList();
            logins.add("");
            int i = 1;
            for (DbObject o : users) {
                Userprofile up = (Userprofile) o;
                logins.add(up.getLogin());
            }
            return logins;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadMachines() {
        return loadMachines(Selects.MACHINETVMS);
    }

    public static ComboItem[] loadMachines(String select) {
        try {
            Vector[] tab = getExchanger().getTableBody(select);
            Vector rows = tab[1];
            ComboItem[] ans = new ComboItem[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                int id = Integer.parseInt(line.get(0).toString());
                String tmvnr = line.get(1).toString();
                ans[i] = new ComboItem(id, tmvnr);
            }
            return ans;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllMachines() {
        try {
            Vector[] tab = getExchanger().getTableBody(Selects.ALLMACHINETVMS);
            Vector rows = tab[1];
            ComboItem[] ans = new ComboItem[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                int id = Integer.parseInt(line.get(0).toString());
                String tmvnr = line.get(1).toString();
                ans[i] = new ComboItem(id, tmvnr);
            }
            return ans;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    private static String[] loadOnSelect(String select, int fldNum, String initValue) {
        try {
            Vector[] tab = getExchanger().getTableBody(select);
            Vector rows = tab[1];
            String[] ans = new String[rows.size() + (initValue != null ? 1 : 0)];
            int n = 0;
            if (initValue != null) {
                ans[n++] = initValue;
            }
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                ans[n++] = line.get(fldNum).toString();
            }
            return ans;
        } catch (RemoteException ex) {
            log(ex);
        }
        return new String[]{""};
    }

    private static String[] loadStringsOnSelect(String select) {
        try {
            Vector[] tab = getExchanger().getTableBody(select);
            Vector rows = tab[1];
            String[] ans = new String[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                ans[i] = line.get(0).toString();
            }
            return ans;
        } catch (RemoteException ex) {
            log(ex);
        }
        return new String[]{""};
    }

    private static ComboItem[] loadOnSelect(String select) {
        try {
            Vector[] tab = getExchanger().getTableBody(select);
            Vector rows = tab[1];
            ComboItem[] ans = new ComboItem[rows.size()];
            for (int i = 0; i < rows.size(); i++) {
                Vector line = (Vector) rows.get(i);
                int id = Integer.parseInt(line.get(0).toString());
                String tmvnr = line.get(1).toString();
                ans[i] = new ComboItem(id, tmvnr);
            }
            return ans;
        } catch (RemoteException ex) {
            log(ex);
        }
        return new ComboItem[]{new ComboItem(0, "")};
    }

    public static ComboItem[] loadEmployees() { //, boolean freeOnly) {
//        String select = freeOnly ? Selects.FREEEMPLOYEES : Selects.EMPLOYEES;
        return loadOnSelect(Selects.EMPLOYEES);
    }

    public static ComboItem[] loadAllDieselCarts() {
        return loadOnSelect(Selects.DIESELCARTS);
    }

    public static ComboItem[] loadAllSuppliers() {
        return loadOnSelect(Selects.SUPPLIERS);
    }

    public static ComboItem[] loadPayMethods() {
        return loadOnSelect(Selects.PAYMETHODS);
    }

    public static ComboItem[] loadAllPPEtypes() {
        return loadOnSelect(Selects.SELECT_FROM_XPPETYPE);
    }

    public static double getBalance4newXpetty() {
        return getBalance4newXpetty(null);
    }

    public static void recalcAllCashBalances() {
        try {
            double inBalance;
            DbObject[] obs = getExchanger().getDbObjects(Xcashdrawn.class, null, null);
            for (DbObject o : obs) {
                Xcashdrawn cd = (Xcashdrawn) o;
                java.util.Date dt = new java.util.Date(cd.getCurDate().getTime());
                inBalance = getBalance4newXpetty(dt);
                if (inBalance != cd.getBalance() - cd.getCashDrawn() - cd.getAddMonies()) {
                    cd.setBalance(inBalance + cd.getCashDrawn() + cd.getAddMonies());
                    getExchanger().saveDbObject(cd);
                }
            }
            obs = getExchanger().getDbObjects(Xpetty.class, null, null);
            for (DbObject o : obs) {
                Xpetty xp = (Xpetty) o;
                java.util.Date dt = new java.util.Date(xp.getIssueDate().getTime());
                inBalance = getBalance4newXpetty(dt);
                if (inBalance != xp.getBalance() + xp.getAmount() - xp.getChangeAmt()) {
                    xp.setBalance(inBalance - xp.getAmount() + xp.getChangeAmt());
                    getExchanger().saveDbObject(xp);
                }
            }
        } catch (Exception ex) {
            log(ex);
        }
    }

    public static double getBalance4newXpetty(java.util.Date dt) {
        if (dt == null) {
            return 0.0;
        }
        double drawn = 0.0;
        double amt = 0.0;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sdt = (dt == null ? null : df.format(dt));
        ComboItem[] itms = loadOnSelect(
                "Select 0,ifnull(sum(cash_drawn + add_monies),0.0) "
                + " from xcashdrawn "
                + (dt != null ? "where cur_date<'" + sdt + "'" : ""));
        if (itms.length > 0) {
            drawn = Double.parseDouble(itms[0].getValue());
        }
        itms = loadOnSelect(
                "Select 0,ifnull(sum(amount),0.0)-ifnull(sum(change_amt),0.0) "
                + " from xpetty "
                + (dt != null ? "where issue_date<'" + sdt + "'" : ""));
        if (itms.length > 0) {
            amt = Double.parseDouble(itms[0].getValue());
        }
        return drawn - amt;
    }

    public static ComboItem loadEmployeeExcept(String excepts) {
        ComboItem[] clist = loadOnSelect(Selects.SELECT_FROM_SALEMPLOYEE_EXCLUDING.replace("#", excepts));
        return (clist != null && clist.length > 0 ? clist[0] : null);
    }

    public static Image loadImage(String iconName, Window w) {
        Image im = null;
        File f = new File("images/" + iconName);
        if (f.exists()) {
            try {
                ImageIcon ic = new javax.swing.ImageIcon("images/" + iconName, "");
                im = ic.getImage();
            } catch (Exception ex) {
                log(ex);
            }
        } else {
            try {
                im = ImageIO.read(w.getClass().getResourceAsStream("/" + iconName));
            } catch (Exception ie) {
                log(ie);
            }
        }
        return im;
    }

    public static void setWindowIcon(Window w, String iconName) {
        w.setIconImage(loadImage(iconName, w));
    }

    public static Integer getOrderIdOnSiteId(int site_id) {
        try {
            Xsite xsite = (Xsite) getExchanger().loadDbObjectOnID(Xsite.class, site_id);
            if (xsite != null) {
                return xsite.getXorderId();
            }
        } catch (RemoteException ex) {
            log(ex);
        }
        return 0;
    }

    public static ComboItem[] loadConsumeInvNumbersOnSupplier(int id) {
        return loadOnSelect(Selects.SELECT_CONSUMABLES4LOOKUP.replace("#", "" + id));
    }

    public static ComboItem[] loadConsumesOnMachine(int id) {
        return loadOnSelect(Selects.SELECT_CONSUMABLES4BREAKDOWN.replace("#", "" + id));
    }

    public static Object[] getNotFixedTimeSheetDates() {
        HashSet hs = new HashSet();
        try {
            hs.add(new XDate(0));
            DbObject[] recs = getExchanger().getDbObjects(Xtimesheet.class,
                    "weekend not in (select weekend from xwagesum)", "weekend");
            for (DbObject rec : recs) {
                Xtimesheet ts = (Xtimesheet) rec;
                if (!hs.contains(ts.getWeekend())) {
                    hs.add(new XDate(ts.getWeekend().getTime()));
                }
            }
        } catch (RemoteException ex) {
            log(ex);
        }
        return hs.toArray();
    }

    public static Object[] getTimeSheetData(Date xd, int xemployee_Id) {
        Object[] ans = new Object[]{new Integer(0), new Double(0.0), new Double(0.0), new Double(0.0)};
        try {
            java.sql.Date dt = new java.sql.Date(xd.getTime());
            DbObject[] itms = getExchanger().getDbObjects(Xwage.class, "xtimesheet_id="
                    + "(select xtimesheet_id from xtimesheet where xemployee_id="
                    + xemployee_Id + " and weekend='" + dt.toString() + "')", null);
            double normalTimeSum = 0.0;
            double overTimeSum = 0.0;
            double doubleTimeSum = 0.0;
            for (DbObject itm : itms) {
                Xwage wg = (Xwage) itm;
                ans[0] = wg.getXtimesheetId();
                normalTimeSum += wg.getNormaltime();
                overTimeSum += wg.getOvertime();
                doubleTimeSum += wg.getDoubletime();
            }
            ans[1] = new Double(normalTimeSum);
            ans[2] = new Double(overTimeSum);
            ans[3] = new Double(doubleTimeSum);
        } catch (RemoteException ex) {
            log(ex);
        }
        return ans;
    }

    public static double calcOutstandingAmtSumBetweenDays(int xsupplier_id, int lastday1, int lastday2) {
        double sum = 0.0;
        Calendar started = Calendar.getInstance();
        started.set(Calendar.HOUR_OF_DAY, 0);
        started.set(Calendar.MINUTE, 0);
        started.set(Calendar.SECOND, 0);
        started.set(Calendar.MILLISECOND, 0);
        long d;
        try {
            DbObject[] recs = getExchanger().getDbObjects(Xconsume.class,
                    "xsupplier_id=" + xsupplier_id + " and xpaidmethod_id=4", null);
            for (DbObject rec : recs) {
                Xconsume c = (Xconsume) rec;
                d = (started.getTime().getTime() - c.getInvoicedate().getTime()) / (24 * 3600 * 1000);
                if ((lastday1 == 0 || d < lastday1) && d >= lastday2) {
                    sum += c.getAmountRands();
                }
            }
            recs = getExchanger().getDbObjects(Xfuel.class, "xsupplier_id="
                    + xsupplier_id + " and (iscache is null or not iscache)", null);
            for (DbObject rec : recs) {
                Xfuel f = (Xfuel) rec;
                d = (started.getTime().getTime() - f.getFdate().getTime()) / (24 * 3600 * 1000);
                if ((lastday1 == 0 || d < lastday1) && d >= lastday2) {
                    sum += f.getAmmount();
                }
            }
        } catch (RemoteException ex) {
            log(ex);
        }
        return sum;
    }

    public static double calcOutstandingAmtSum(int xsupplier_id) {
        double sum = 0.0;
        try {
            DbObject[] recs = getExchanger().getDbObjects(Xconsume.class,
                    "xsupplier_id=" + xsupplier_id + " and xpaidmethod_id=4", null);
            for (DbObject rec : recs) {
                Xconsume c = (Xconsume) rec;
                sum += c.getAmountRands();
            }
            recs = getExchanger().getDbObjects(Xfuel.class, "xsupplier_id="
                    + xsupplier_id + " and (iscache is null or not iscache)", null);
            for (DbObject rec : recs) {
                Xfuel f = (Xfuel) rec;
                sum += f.getAmmount();
            }
            recs = getExchanger().getDbObjects(Xpayment.class, "xsupplier_id=" + xsupplier_id, null);
            for (DbObject rec : recs) {
                Xpayment xp = (Xpayment) rec;
                sum -= xp.getAmmount();
            }
        } catch (RemoteException ex) {
            log(ex);
        }
        return sum;
    }

    public static ComboItem[] loadSiteTypes() {
        return loadOnSelect("select id,val from cbitems where name='site_types'");
    }

    public static ComboItem[] loadWageCategories() {
        return loadOnSelect("select id,val from cbitems where name='wage_category'");
    }

    public static ComboItem[] loadPaidFromCodes() {
        return loadOnSelect("select id,val from cbitems where name='paidfrom'");
    }

    public static ComboItem[] loadRootSheets() {
        return loadOnSelect("select sheet_id,sheetname from sheet where parent_id is null");
    }

    public static ComboItem[] loadSubSheets(int parent_id) {
        return loadOnSelect("select sheet_id,sheetname from sheet where parent_id=" + parent_id);
    }

    public static ComboItem[] loadDistinctAccNames() {
        return loadOnSelect("select distinct 0,accname from xaccounts");
    }

    public static ComboItem[] loadReportGroup(Integer sheetId) {
        return loadOnSelect(
                "select (select coalesce(max(sign(usersheet_id)),-1) from usersheet "
                + "where sheet_id=sheet.sheet_id and profile_id="
                + XlendWorks.currentUser.getProfileId() + "), sheet.sheetname from sheet, reportgroup "
                + "where reportgroup.sheet_id=sheet.sheet_id and sheetgroup_id=" + sheetId);
    }

    public static ComboItem[] loadAllAccounts() {
        return loadOnSelect("select xaccount_id,accname from xaccounts");
    }

    public static ComboItem[] loadAllTracks() {
        return loadOnSelect("Select xmachine_id, concat(m.classify,m.tmvnr) "
                + "from xmachine m left join xmachtype t1 "
                + "on m.xmachtype_id=t1.xmachtype_id");// and t1.classify='T'");
    }

    public static ComboItem[] loadRatedMachines() {
        return loadOnSelect("select xmachtype_id,machtype from xmachtype where is_rated=1 order by machtype");
//                "Select cbitem_id, val "
//                + "from cbitems where name='rated_machines' order by id");
    }

    public static boolean existsSiteOfType(String siteType) {
        try {
            Vector[] tab = getExchanger().getTableBody(
                    "select xsite_id from xsite where substring(sitetype,1,1)='" + siteType.substring(0, 1) + "'");
            Vector rows = tab[1];
            return rows.size() > 0;
        } catch (RemoteException ex) {
            log(ex);
        }
        return true;
    }

    public static boolean isXpettyCategoryUsed(Integer xpettyCategoryID) {
        try {
            Vector[] tab = getExchanger().getTableBody("select xpettyitem_id from xpettyitem where xpettycategory_id=" + xpettyCategoryID);
            Vector rows = tab[1];
            return rows.size() > 0;
        } catch (RemoteException ex) {
            log(ex);
        }
        return true;
    }

    public static boolean isXPPEtypeUsed(Integer xppetypeID) {
        try {
            Vector[] tab = getExchanger().getTableBody("select xppebuyitem_id from xppebuyitem where xppetype_id="
                    + xppetypeID + " union select xppeissueitem_id from xppeissueitem where xppetype_id=" + xppetypeID);
            Vector rows = tab[1];
            return rows.size() > 0;
        } catch (RemoteException ex) {
            log(ex);
        }
        return true;
    }

    public static boolean existsEmployeeWithWageCategory(Integer wageCatID) {
        try {
            Vector[] tab = getExchanger().getTableBody(
                    "select xemployee_id from xemployee where xemployee_id="
                    + "(select min(xemployee_id) from xemployee where wage_category=" + wageCatID + ") and wage_category=" + wageCatID);
            Vector rows = tab[1];
            return rows.size() > 0;
        } catch (RemoteException ex) {
            log(ex);
        }
        return true;
    }

    public static ComboItem[] loadAllLowbeds() {
        return loadOnSelect(
                "select l.xlowbed_id,concat('Machine:',m.classify,m.tmvnr,"
                + "'; Driver:',d.clock_num,' ',d.first_name,'; Assistant:',a.clock_num,' ',a.first_name) "
                + "from xlowbed l, xmachine m, xemployee d, xemployee a where l.xmachine_id=m.xmachine_id "
                + "and l.driver_id=d.xemployee_id and l.assistant_id=a.xemployee_id");
    }

    public static ComboItem[] loadConsumesForMachine(Integer xmachineID) {
        return loadOnSelect("select xconsume_id, invoicenumber from xconsume where xmachine_id=" + xmachineID);
    }

    public static boolean availableForCurrentUser(String sheetName) {
        try {
            String s;
            DbObject[] recs = getExchanger().getDbObjects(Usersheet.class, s = "profile_id=" + getCurrentUser().getProfileId()
                    + " and sheet_id in (select sheet_id from sheet where sheetname like binary '" + sheetName + "')", null);
//            System.out.println("!!SELECT FROM USERSHEET WHERE "+s);
            return recs.length > 0;
        } catch (RemoteException ex) {
            log(ex);
        }
        return false;
    }

//    public static Xtripestablish getTripEstablish(Xtrip xtr) throws RemoteException {
//        Xtripestablish xtre = null;
//        if (xtr != null) {
//            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripestablish.class,
//                    "xtrip_id=" + xtr.getXtripId() 
////                    + " and ifnull(distance_loaded,0)=0"
//                    , "xtripestablish_id");
//            if (recs.length > 0) {
//                xtre = (Xtripestablish) recs[0];
//            }
//        }
//        return xtre;
//    }
//    public static Xtripestablish getTripDeEstablish(Xtrip xtr) throws RemoteException {
//        Xtripestablish xtre = null;
//        if (xtr != null) {
//            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripestablish.class,
//                    "xtrip_id=" + xtr.getXtripId() 
////                    + " and ifnull(distance_loaded,0)<>0"
//                    , "xtripestablish_id");
//            if (recs.length > 0) {
//                xtre = (Xtripestablish) recs[0];
//            }
//        }
//        return xtre;
//    }
//    public static Xtripmoving getTripMove(Xtrip xtr) throws RemoteException {
//        Xtripmoving xtrm = null;
//        if (xtr != null) {
//            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripmoving.class,
//                    "xtrip_id=" + xtr.getXtripId(), "xtripmoving_id");
//            if (recs.length > 0) {
//                xtrm = (Xtripmoving) recs[0];
//            }
//        }
//        return xtrm;
//    }
//    public static Xtripexchange getTripExchange(Xtrip xtr) throws RemoteException {
//        Xtripexchange xtre = null;
//        if (xtr != null) {
//            DbObject[] recs = DashBoard.getExchanger().getDbObjects(Xtripexchange.class,
//                    "xtrip_id=" + xtr.getXtripId(), "xtripexchange_id");
//            if (recs.length > 0) {
//                xtre = (Xtripexchange) recs[0];
//            }
//        }
//        return xtre;
//    }
    public static ComboItem[] loadSiteDiaryHrsWorked(java.util.Date dt,
            Integer siteID, Integer operatorID, Integer machineID) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String select = "select DAYOFMONTH(partdate)-1, hrs_worked from xsitediarypart "
                + " where operator_id=" + operatorID + " and xmachine_id=" + machineID
                + " and year(partdate)=" + year + " and month(partdate)=" + month
                + " and xsitediary_id in (select xsitediary_id from xsitediary where xsite_id=" + siteID + ")";
        ComboItem[] itms = loadOnSelect(select);
        return itms;
    }

    public static ComboItem[] loadTimeSheetHrsWorked(java.util.Date dt,
            Integer siteID, Integer operatorID) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        String select = "select DAYOFMONTH(xw.day)-1, normaltime+overtime+doubletime "
                + " from xtimesheet ts, xwage xw where xw.xtimesheet_id=ts.xtimesheet_id "
                + " and year(xw.day)=" + year + " and month(xw.day)=" + month + " and ts.xsite_id=" + siteID
                + " and ts.xemployee_id=" + operatorID;
        ComboItem[] itms = loadOnSelect(select);
        return itms;
    }

    private static double timeDiff(Timestamp t1, Timestamp t2) {
        double diff = 0.0;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(t1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(t2);
        c1.set(Calendar.YEAR, 0);
        c1.set(Calendar.MONTH, 0);
        c1.set(Calendar.DAY_OF_MONTH, 1);
        c1.set(Calendar.SECOND, 0);
        c2.set(Calendar.YEAR, 0);
        c2.set(Calendar.MONTH, 0);
        c2.set(Calendar.DAY_OF_MONTH, 1);
        c2.set(Calendar.SECOND, 0);
        diff = (c2.getTime().getTime() - c1.getTime().getTime()) / 3600000.0;
        return diff;
    }

    public static ComboItem[][] loadOperatorHourMeterAndOcs(java.util.Date dt,
            Integer siteID, Integer operatorID, Integer machineID) {
        ComboItem[][] ciArr = new ComboItem[2][];
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int year1 = cal.get(Calendar.YEAR);
        int month1 = cal.get(Calendar.MONTH) + 1;

        cal.add(Calendar.MONTH, 1);
        int year2 = cal.get(Calendar.YEAR);
        int month2 = cal.get(Calendar.MONTH) + 1;

        String sd1 = String.format("%04d-%02d-01", year1, month1);
        String sd2 = String.format("%04d-%02d-06", year2, month2);

        try {
            ComboItem[] hrItms = new ComboItem[daysInMonth];
            ComboItem[] wrkItms = new ComboItem[daysInMonth];
            for (int i = 0; i < daysInMonth; i++) {
                hrItms[i] = new ComboItem(i, "0.0");
                wrkItms[i] = new ComboItem(i, "0.0");
            }
            String whereCond = "xsite_id=" + siteID + " and xemployee_id=" + operatorID + " and xmachine_id=" + machineID
                    + " and sheet_date between '" + sd1 + "' and '" + sd2 + "'";
            DbObject[] recs = getExchanger().getDbObjects(
                    Xopclocksheet.class, whereCond, "sheet_date");
            Calendar calendar = Calendar.getInstance();
            for (DbObject rec : recs) {
                Xopclocksheet ocs = (Xopclocksheet) rec;

                Timestamp wf1 = ocs.getWorkFrom1();
                Timestamp wt1 = ocs.getWorkTo1();
                double diff1 = timeDiff(wf1, wt1);

                java.util.Date sheet_date = new java.util.Date(ocs.getSheetDate().getTime());
                calendar.setTime(sheet_date);
                int dayShift = calendar.get(Calendar.DAY_OF_MONTH) - 1;
                for (int i = 0; i < 7; i++, dayShift--) {
                    if (calendar.get(Calendar.YEAR) == year1 && calendar.get(Calendar.MONTH) + 1 == month1) {
                        double deltaHR = 0.0;
                        double deltaWRK = 0.0;
                        switch (i) {
                            case 0:
                                deltaHR = (ocs.getKmStop7() - ocs.getKmStart7());
                                deltaWRK = (timeDiff(ocs.getWorkFrom7(), ocs.getWorkTo7()) - timeDiff(ocs.getStandFrom7(), ocs.getStandTo7()));
                                break;
                            case 1:
                                deltaHR = (ocs.getKmStop6() - ocs.getKmStart6());
                                deltaWRK = (timeDiff(ocs.getWorkFrom6(), ocs.getWorkTo6()) - timeDiff(ocs.getStandFrom6(), ocs.getStandTo6()));
                                break;
                            case 2:
                                deltaHR = (ocs.getKmStop5() - ocs.getKmStart5());
                                deltaWRK = (timeDiff(ocs.getWorkFrom5(), ocs.getWorkTo5()) - timeDiff(ocs.getStandFrom5(), ocs.getStandTo5()));
                                break;
                            case 3:
                                deltaHR = (ocs.getKmStop4() - ocs.getKmStart4());
                                deltaWRK = (timeDiff(ocs.getWorkFrom4(), ocs.getWorkTo4()) - timeDiff(ocs.getStandFrom4(), ocs.getStandTo4()));
                                break;
                            case 4:
                                deltaHR = (ocs.getKmStop3() - ocs.getKmStart3());
                                deltaWRK = (timeDiff(ocs.getWorkFrom3(), ocs.getWorkTo3()) - timeDiff(ocs.getStandFrom3(), ocs.getStandTo3()));
                                break;
                            case 5:
                                deltaHR = (ocs.getKmStop2() - ocs.getKmStart2());
                                deltaWRK = (timeDiff(ocs.getWorkFrom2(), ocs.getWorkTo2()) - timeDiff(ocs.getStandFrom2(), ocs.getStandTo2()));
                                break;
                            case 6:
                                deltaHR = (ocs.getKmStop1() - ocs.getKmStart1());
                                deltaWRK = (timeDiff(ocs.getWorkFrom1(), ocs.getWorkTo1()) - timeDiff(ocs.getStandFrom1(), ocs.getStandTo1()));
                                break;
                        }
                        hrItms[dayShift].setValue("" + (Double.parseDouble(hrItms[dayShift].getValue()) + deltaHR));
                        wrkItms[dayShift].setValue("" + (Double.parseDouble(wrkItms[dayShift].getValue()) + deltaWRK));
                        calendar.add(Calendar.DAY_OF_MONTH, -1);
                    }
                }
            }
            ciArr[0] = hrItms;
            ciArr[1] = wrkItms;
            return ciArr;
        } catch (Exception ex) {
            log(ex);
        }
        return null;
    }

    public static int getWageCategory(Integer xemployeeID) {
        try {
            Xemployee emp = (Xemployee) getExchanger().loadDbObjectOnID(Xemployee.class, xemployeeID);
            return emp == null ? 0 : (emp.getWageCategory() == null ? 1 : emp.getWageCategory());
        } catch (RemoteException ex) {
            log(ex);
        }
        return 0;
    }

    public static DbObject[] loadReportGrpSheets() {
        try {
            return getExchanger().getDbObjects(Sheet.class,
                    "parent_id is null and sheetname<>'REPORTS' and sheet_id in(select sheet_id from usersheet where profile_id="
                    + currentUser.getProfileId() + ")", null);
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static Xopmachassing findCurrentAssignment(int machine_id, int operator_id) {
        Xopmachassing curAss = null;
        try {
            StringBuffer whereCond = new StringBuffer("");
            if (machine_id != 0) {
                whereCond.append("xmachine_id=" + machine_id).append(" and ");
            }
            if (operator_id != 0) {
                whereCond.append("xemployee_id=" + operator_id).append(" and ");
            }
            whereCond.append("date_end is null");
            DbObject[] obs = getExchanger().getDbObjects(Xopmachassing.class, whereCond.toString(), null);
            if (obs.length > 0) {
                curAss = (Xopmachassing) obs[0];
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return curAss;
    }

    public static String[] findCurrentAssignment(Xemployee xemployee) {
        if (xemployee != null) {
            try {
                DbObject[] obs = getExchanger().getDbObjects(Xopmachassing.class,
                        "xemployee_id=" + xemployee.getXemployeeId() + " and date_end is null", null);
                if (obs.length > 0) {
                    String[] ans = new String[2];
                    Xopmachassing assign = (Xopmachassing) obs[0];
                    Xsite xsite = (Xsite) getExchanger().loadDbObjectOnID(Xsite.class, assign.getXsiteId());
                    Xmachine xmachine = (Xmachine) getExchanger().loadDbObjectOnID(Xmachine.class, assign.getXmachineId());
                    ans[0] = xsite == null ? "unassigned" : xsite.getName();
                    ans[1] = xmachine == null ? "unassigned" : (xmachine.getClassify() + xmachine.getTmvnr());
                    return ans;
                }
            } catch (RemoteException ex) {
                logAndShowMessage(ex);
            }
        }
        return null;
    }

    public static String[] findLastService(Xmachine xmachine) {
        if (xmachine != null) {
            try {
                DbObject[] obs = getExchanger().getDbObjects(Xmachservice.class,
                        "xmachservice_id=(select max(xmachservice_id) from xmachservice where xmachine_id=" + xmachine.getXmachineId() + ")", null);
                if (obs.length > 0) {
                    Xmachservice ms = (Xmachservice) obs[0];
                    String[] ans = new String[2];
                    XDate xdt = new XDate(ms.getServicedate().getTime());
                    ans[0] = xdt.toString();
                    Xemployee serviceMan = (Xemployee) getExchanger().loadDbObjectOnID(Xemployee.class, ms.getServicedbyId());
                    ans[1] = serviceMan.getClockNum() + " " + serviceMan.getFirstName();
                    return ans;
                }
            } catch (RemoteException ex) {
                logAndShowMessage(ex);
            }
        }
        return null;
    }

    public static String[] findCurrentAssignment(Xmachine xmachine) {
        if (xmachine != null) {
            try {
                DbObject[] obs = getExchanger().getDbObjects(Xopmachassing.class,
                        "xmachine_id=" + xmachine.getXmachineId() + " and date_end is null", null);
                if (obs.length > 0) {
                    String[] ans = new String[2];
                    Xopmachassing assign = (Xopmachassing) obs[0];
                    Xsite xsite = (Xsite) getExchanger().loadDbObjectOnID(Xsite.class, assign.getXsiteId());
                    Xemployee xemployee = (Xemployee) getExchanger().loadDbObjectOnID(Xemployee.class, assign.getXemployeeId());
                    ans[0] = xsite == null ? "unassigned" : xsite.getName();
                    ans[1] = xemployee == null ? "unassigned" : (xemployee.getFirstName()
                            + " " + xemployee.getSurName()
                            + " (" + xemployee.getClockNum() + ")");
                    return ans;
                }
            } catch (RemoteException ex) {
                logAndShowMessage(ex);
            }
        }
        return null;
    }

    private static String removeTail(String s) {
        int p = s.lastIndexOf(".");
        if (p > 0 && s.length() > p + 1) {
            if ("0123456789".indexOf(s.substring(p + 1, p + 2)) < 0) {
                return s.substring(0, p);
            }
        }
        return s;
    }

    private static boolean matchVersions() {
        try {
            String servVersion = getExchanger().getServerVersion();
            boolean match = removeTail(servVersion).equals(removeTail(version));
            if (!match) {
                GeneralFrame.errMessageBox("Error:", "Client's software version (" + version + ") doesn't match server (" + servVersion + ")");
            }
            return match;
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return false;
    }

    public static String getEmployeeClockNumOnID(Integer id) {
        try {
            Xemployee emp = (Xemployee) getExchanger().loadDbObjectOnID(Xemployee.class, id);
            if (emp != null) {
                return emp.getClockNum();
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return null;
    }

    public static Integer getEmployeeOnClockNum(String clock_num) {
        try {
            DbObject[] obs = getExchanger().getDbObjects(Xemployee.class, "clock_num='" + clock_num + "'", null);
            if (obs.length > 0) {
                Xemployee emp = (Xemployee) obs[0];
                return emp.getXemployeeId();
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return null;
    }

    public static boolean isActiveSite(Integer xsiteID) {
        try {
            Xsite s = (Xsite) getExchanger().loadDbObjectOnID(Xsite.class, xsiteID);
            if (s != null) {
                return (s.getIsActive() != null && s.getIsActive().intValue() == 1);
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return true;
    }

    public static void activateSite(Integer xsiteID) {
        try {
            Xsite s = (Xsite) getExchanger().loadDbObjectOnID(Xsite.class, xsiteID);
            if (s != null) {
                try {
                    s.setIsActive(1);
                    getExchanger().saveDbObject(s);
                } catch (Exception ex) {
                    logAndShowMessage(ex);
                }
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
    }

    public static String getMachineType1(Integer machineID) {
        try {
            Xmachine m = (Xmachine) getExchanger().loadDbObjectOnID(Xmachine.class, machineID);
            if (m != null) {
                Xmachtype mt = (Xmachtype) getExchanger().loadDbObjectOnID(Xmachtype.class, m.getXmachtypeId());
                return mt != null ? mt.getMachtype() : "";
            }
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return "";
    }

    public static ComboItem[] loadAllWarehouses() {
        return loadOnSelect(Selects.SELECT_FROM_STORES);
    }

    public static String[] loadDistinctStoreNames() {
        return loadStringsOnSelect(Selects.SELECT_DISTINCT_STORES);
    }

    public static String[] loadDistinctMachineModels() {
        return loadStringsOnSelect(Selects.SELECT_DISTINCT_MACHINEMODELS);
    }

    public static String[] loadDistinctBatteryCodes() {
        return loadStringsOnSelect(Selects.SELECT_DISTINCT_BATTCODES);
    }

    public static ComboItem[] loadOldestAvailableBateries() {
        return loadOnSelect(Selects.SELECT_OLDEST_AVAILABLE_BATTERIES);
    }
//    public static String[] loadAvailableBatteryCodes() {
//        return loadStringsOnSelect(exchanger, Selects.SELECT_DISTINCT_AVAILABLE_BATTERIES);
//    }

    public static String getStockLevels(Integer xppetypeID) {
        if (xppetypeID != null && xppetypeID.intValue() > 0) {
            ComboItem[] itms = loadOnSelect("select " + xppetypeID
                    + ", (select ifnull(sum(quantity),0) from xppebuyitem where xppetype_id=" + xppetypeID + ") - "
                    + "(select ifnull(sum(quantity),0) from xppeissueitem where xppetype_id=" + xppetypeID + ")");
            return itms[0].getValue();
        } else {
            return "0";
        }
    }

    public static String getLastPPEprice(Integer xppetypeID) {
        if (xppetypeID != null && xppetypeID.intValue() > 0) {
            ComboItem[] itms = loadOnSelect("select "
                    + xppetypeID + ", ifnull(priceperunit,0.0) from xppebuyitem where xppebuyitem_id="
                    + "(select max(xppebuyitem_id) from xppebuyitem where xppetype_id=" + xppetypeID + ")");
            if (itms.length > 0) {
                return itms[0].getValue();
            }
        }
        return "0";
    }

    public static String calcDieselBalanceAtCart(Integer dieselCartId, java.util.Date dt) {
        if (dieselCartId != null && dieselCartId.intValue() > 0) {
            String sDate = new SimpleDateFormat("yyyy-MM-dd").format(dt);
            ComboItem[] itms = loadOnSelect("select " + dieselCartId + ","
                    + "(select ifnull(sum(liters),0) from xdieselcartissue where xdieselcart_id=" + dieselCartId + " and issue_date<='" + sDate + "') - "
                    + "(select ifnull(sum(liters),0) from xdiesel2plantitem i, xdiesel2plant p "
                    + "where i.xdiesel2plant_id=p.xdiesel2plant_id and "
                    + "p.xdieselcart_id=" + dieselCartId + " and i.add_date<='" + sDate + "')");
            if (itms.length > 0) {
                return itms[0].getValue();
            }
        }
        return "0";
    }

    public static Double calcDieselBalanceAtSupplier(Integer xsupplierId, java.util.Date dt) {
        if (xsupplierId != null && xsupplierId.intValue() > 0) {
            try {
                Double litres = 0.0;
                DbObject[] obs = getExchanger().getDbObjects(Xdieselpurchase.class, "xsupplier_id=" + xsupplierId, "purchase_date");
                for (DbObject rec : obs) {
                    Xdieselpurchase xp = (Xdieselpurchase) rec;
                    if (xp.getPurchaseDate().getTime() <= dt.getTime()) {
                        litres += xp.getLitres();
                    } else {
                        break;
                    }
                }
                obs = getExchanger().getDbObjects(Xdieselcartissue.class, "xsupplier_id=" + xsupplierId, "issue_date");
                for (DbObject rec : obs) {
                    Xdieselcartissue xi = (Xdieselcartissue) rec;
                    if (xi.getIssueDate().getTime() <= dt.getTime()) {
                        litres -= xi.getLiters();
                    } else {
                        break;
                    }
                }
                return litres;
            } catch (RemoteException ex) {
                logAndShowMessage(ex);
            }
        }
        return 0.0;
    }

    public static Double calcMoneyForDieselBalanceAtSupplier(Integer xsupplierId, java.util.Date dt) {
        if (xsupplierId != null && xsupplierId.intValue() > 0) {
            try {
                Double balance = 0.0;
                DbObject[] obs = getExchanger().getDbObjects(Xdieselpurchase.class, "xsupplier_id=" + xsupplierId, "purchase_date");
                for (DbObject rec : obs) {
                    Xdieselpurchase xp = (Xdieselpurchase) rec;
                    if (dt == null || xp.getPurchaseDate().getTime() <= dt.getTime()) {
                        balance += (xp.getPaid() - xp.getLitres() * xp.getRandFactor());
                    } else {
                        break;
                    }
                }
                return balance;
            } catch (RemoteException ex) {
                logAndShowMessage(ex);
            }
        }
        return 0.0;
    }

    public static String getPrevHourMeter(Integer xdiesel2plantitemID, Integer machineID) {
        if (machineID != null) {
            ComboItem[] itms = loadOnSelect(
                    "select " + machineID + ", hour_meter "
                    + "from xdiesel2plantitem where xdiesel2plantitem_id="
                    + "(select max(xdiesel2plantitem_id) from xdiesel2plantitem where xmachine_id="
                    + machineID + (xdiesel2plantitemID == null ? "" : " and xdiesel2plantitem_id<" + xdiesel2plantitemID) + ")");
            if (itms.length > 0) {
                return itms[0].getValue();
            }
        }
        return "";
    }
}
