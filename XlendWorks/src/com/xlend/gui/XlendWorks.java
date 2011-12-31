package com.xlend.gui;

import com.xlend.constants.Selects;
import com.xlend.orm.Profile;
import com.xlend.orm.Userprofile;
import com.xlend.orm.Xclient;
import com.xlend.orm.Xcontract;
import com.xlend.orm.Xemployee;
//import com.xlend.orm.Xlicensestat;
import com.xlend.orm.Xmachtype;
import com.xlend.orm.Xorder;
import com.xlend.orm.Xposition;
import com.xlend.orm.Xquotation;
import com.xlend.orm.Xsite;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.Image;
import java.awt.Window;
import java.io.File;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Vector;
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

    public static final String version = "0.7";
    private static Userprofile currentUser;
    private static Logger logger = null;
    private static FileHandler fh;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String serverIP = DashBoard.readProperty("ServerAddress", "localhost");
        IMessageSender exchanger;
        while (true) {
            try {
                exchanger = (IMessageSender) Naming.lookup("rmi://" + serverIP + "/XlendServer");
                if (login(exchanger)) {

//                    DbObject[] xtypes = exchanger.getDbObjects(Xmachtype.class, null, "xmachtype_id");
//                    for (DbObject o : xtypes) {
//                        Xmachtype mt = (Xmachtype) o;
//                        System.out.println(""+mt.getXmachtypeId()+"|"+mt.getMachtype()+"|"+mt.getParenttypeId()+"|"+mt.getClassify());
//                    }

                    new DashBoard(exchanger);
                    break;
                } else {
                    System.exit(1);
                }
            } catch (Exception ex) {
                logAndShowMessage(ex);
                if ((serverIP = serverSetup("Check server settings")) == null) {
                    System.exit(1);
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
        return currentUser.getLogin().equalsIgnoreCase("admin");
    }

    public static void logAndShowMessage(Throwable ne) {
        GeneralFrame.errMessageBox("Error:", ne.getMessage());
        log(ne);
    }

    public static boolean login(IMessageSender exchanger) {
        try {
//            JComboBox loginField = new JComboBox(loadAllLogins(exchanger));
//            loginField.setEditable(true);
//            JPasswordField pwdField = new JPasswordField(20);
            new LoginImagedDialog(exchanger);//new Object[]{loginField, pwdField, exchanger});
            return LoginImagedDialog.isOkPressed();
        } catch (Throwable ee) {
            GeneralFrame.errMessageBox("Error:", "Server failure\nCheck your logs please");
            log(ee);
        }
        return false;
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

    public static ComboItem[] loadAllContracts(IMessageSender exchanger, int client_id) {
        try {
            DbObject[] contracts = exchanger.getDbObjects(Xcontract.class, "xclient_id=" + client_id, "contractref");
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

    public static ComboItem[] loadAllEmployees(IMessageSender exchanger) {
        try {
            DbObject[] employees = exchanger.getDbObjects(Xemployee.class, null, "sur_name");
            ComboItem[] itms = new ComboItem[employees.length + 1];
            int i = 0;
            for (DbObject o : employees) {
                Xemployee emp = (Xemployee) o;
                itms[i++] = new ComboItem(emp.getXemployeeId(), emp.getClockNum()
                        + " (" + emp.getFirstName().substring(0, 1) + "." + emp.getSurName() + ")");
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllRFQs(IMessageSender exchanger, int client_id) {
        try {
            DbObject[] rfqs = exchanger.getDbObjects(Xquotation.class, "xclient_id=" + client_id, "rfcnumber");
            ComboItem[] itms = new ComboItem[rfqs.length + 1];
            itms[0] = new ComboItem(0, "--No requests for quotation yet--");
            int i = 1;
            for (DbObject o : rfqs) {
                Xquotation xquotation = (Xquotation) o;
                itms[i++] = new ComboItem(xquotation.getXquotationId(), xquotation.getRfcnumber());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllOrderSites(IMessageSender exchanger, int order_id) {
        try {
            DbObject[] rfqs = exchanger.getDbObjects(Xsite.class,
                    "xorder_id=" + order_id, "name");
            ComboItem[] itms = new ComboItem[rfqs.length + 1];
            itms[0] = new ComboItem(0, "--No requests for clients yet--");
            int i = 1;
            for (DbObject o : rfqs) {
                Xsite xsite = (Xsite) o;
                itms[i++] = new ComboItem(xsite.getXsiteId(), xsite.getName());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllSites(IMessageSender exchanger) {
        try {
            DbObject[] orders = exchanger.getDbObjects(Xsite.class, null, "name");
            ComboItem[] itms = new ComboItem[orders.length + 1];
            itms[0] = new ComboItem(0, "--Add new site --");
            int i = 1;
            for (DbObject o : orders) {
                Xsite xsite = (Xsite) o;
                itms[i++] = new ComboItem(xsite.getXsiteId(), xsite.getName());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllOrders(IMessageSender exchanger) {
        try {
            DbObject[] orders = exchanger.getDbObjects(Xorder.class, null, "ordernumber");
            ComboItem[] itms = new ComboItem[orders.length + 1];
            itms[0] = new ComboItem(0, "--Add new order --");
            int i = 1;
            for (DbObject o : orders) {
                Xorder xorder = (Xorder) o;
                itms[i++] = new ComboItem(xorder.getXorderId(), "Order Nr:" + xorder.getOrdernumber());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadRootMachTypes(IMessageSender exchanger, String classify) {
        try {
            DbObject[] clients = exchanger.getDbObjects(Xmachtype.class, "parenttype_id is null and classify in ('" + classify + "')", "xmachtype_id");
            ComboItem[] itms = new ComboItem[clients.length];
            int i = 0;
            for (DbObject o : clients) {
                Xmachtype tp = (Xmachtype) o;
                itms[i++] = new ComboItem(tp.getXmachtypeId(), tp.getMachtype());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllPositions(IMessageSender exchanger) {
        try {
            DbObject[] clients = exchanger.getDbObjects(Xposition.class, null, "pos");
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

    public static ComboItem[] loadAllUsers(IMessageSender exchanger) {
        try {
            DbObject[] users = exchanger.getDbObjects(Profile.class, "profile_id in (select profile_id from userprofile)", "last_name");
            ComboItem[] itms = new ComboItem[users.length + 1];
            //itms[0] = new ComboItem(0, "--Add new client--");
            int i = 0;
            for (DbObject o : users) {
                Profile user = (Profile) o;
                itms[i++] = new ComboItem(user.getProfileId(),
                        user.getFirstName().substring(0, 1) + "." + user.getLastName());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadAllClients(IMessageSender exchanger) {
        try {
            DbObject[] clients = exchanger.getDbObjects(Xclient.class, null, "companyname");
            ComboItem[] itms = new ComboItem[clients.length + 1];
            itms[0] = new ComboItem(0, "--Add new client--");
            int i = 1;
            for (DbObject o : clients) {
                Xclient xclient = (Xclient) o;
                itms[i++] = new ComboItem(xclient.getXclientId(), xclient.getCompanyname());
            }
            return itms;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static String[] loadAllLogins(IMessageSender exchanger) {
        try {
            DbObject[] users = exchanger.getDbObjects(Userprofile.class, null, "login");
            String[] logins = new String[users.length + 1];
            logins[0] = "";
            int i = 1;
            for (DbObject o : users) {
                Userprofile up = (Userprofile) o;
                logins[i++] = up.getLogin();
            }
            return logins;
        } catch (RemoteException ex) {
            log(ex);
        }
        return null;
    }

    public static ComboItem[] loadMachines(IMessageSender exchanger, boolean freeOnly) {
        try {
            Vector[] tab = exchanger.getTableBody(freeOnly ? Selects.FREEMACHINETVMS : Selects.MACHINETVMS);
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

    private static ComboItem[] loadOnSelect(IMessageSender exchanger, String select) {
        try {
            Vector[] tab = exchanger.getTableBody(select);
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
    
    public static ComboItem[] loadEmployees(IMessageSender exchanger, boolean freeOnly) {
        return loadOnSelect(exchanger, freeOnly ? Selects.FREEEMPLOYEES : Selects.EMPLOYEES);
    }

    public static ComboItem[] loadAllSuppliers(IMessageSender exchanger) {
        return loadOnSelect(exchanger, Selects.SUPPLIERS);
    }
    
    public static ComboItem[] loadPayMethods(IMessageSender exchanger) {
        return loadOnSelect(exchanger, Selects.PAYMETHODS);
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

    public static int getOrderIdOnSiteId(IMessageSender exchanger, int site_id) {
        try {
            Xsite xsite = (Xsite) exchanger.loadDbObjectOnID(Xsite.class, site_id);
            if (xsite != null) {
                return xsite.getXorderId();
            }
        } catch (RemoteException ex) {
            log(ex);
        }
        return 0;
    }

}
