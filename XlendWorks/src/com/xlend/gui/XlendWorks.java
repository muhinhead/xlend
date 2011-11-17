package com.xlend.gui;

import com.xlend.orm.Userprofile;
import com.xlend.orm.Xclient;
import com.xlend.orm.dbobject.ComboItem;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.awt.Image;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Nick Mukhin
 */
public class XlendWorks {

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
                    new DashBoard(exchanger);
                    break;
                } else {
                    System.exit(1);
                }
            } catch (Exception ex) {
                //ex.printStackTrace();
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
            JComboBox loginField = new JComboBox(loadAllLogins(exchanger));
            loginField.setEditable(true);
            JPasswordField pwdField = new JPasswordField(20);
            new LoginDialog(new Object[]{loginField, pwdField, exchanger});
            return LoginDialog.isOkPressed();
        } catch (Throwable ee) {
            GeneralFrame.errMessageBox("Error:", "Server failure\nCheck your logs please");
            log(ee);
        }
        return false;
    }

    public static String serverSetup(String title) {
        String address = DashBoard.readProperty("ServerAddress", "localhost");
        String[] vals = address.split(":");
        JTextField addressField = new JTextField(16);
        addressField.setText(vals[0]);
        JSpinner portSpinner = new JSpinner(new SpinnerNumberModel(
                vals.length > 0 ? new Integer(vals[1]) : 1099, 0, 65536, 1));
        JComponent[] edits = new JComponent[]{addressField, portSpinner};
        new ConfigEditor(title, edits);
        if (addressField.getText().trim().length() > 0) {
            return addressField.getText() + ":" + portSpinner.getValue();
        } else {
            return null;
        }
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
}
