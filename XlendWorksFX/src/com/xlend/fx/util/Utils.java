package com.xlend.fx.util;

import com.xlend.orm.Userprofile;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.remote.IMessageSender;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.Preferences;
import javafx.scene.control.Dialogs;
import javafx.stage.Stage;

/**
 *
 * @author Nick Mukhin
 */
public class Utils {

    private static Stage mainStage = null;
    private static Properties props;
    private static String PROPERTYFILENAME = "XlendWorksFX";
    public final static String LASTLOGIN = "LastLogin";//    public final static String PWDMD5 = "PWDMD5";
    private static Userprofile currentUser;
    private static Logger logger;
    private static FileHandler fh;
    private static IMessageSender exchanger;

    public static void setExchanger(IMessageSender iExchanger) {
        exchanger = iExchanger;
    }
    
    public static IMessageSender getExchanger() {
        return exchanger;
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

    public static void logAndShowMessage(Throwable ne) {
        Dialogs.showErrorDialog(mainStage, ne.getMessage(), "Error:", "");
        log(ne);
    }

    public static String readProperty(String key, String deflt) {
        if (null == props) {
            props = new Properties();
            try {
                File propFile = new File(PROPERTYFILENAME);
                if (!propFile.exists() || propFile.length() == 0) {
                    String curPath = propFile.getAbsolutePath();
                    curPath = curPath.substring(0,
                            curPath.indexOf(PROPERTYFILENAME)).replace('\\', '/');
                    props.setProperty("user", "admin");
                    props.setProperty("userPassword", "admin");
                    propFile.createNewFile();
                } else {
                    props.load(new FileInputStream(propFile));
                }
            } catch (IOException e) {
                e.printStackTrace();
                return deflt;
            }
        }
        return props.getProperty(key, deflt);
    }

    public static void saveProperties() {
        try {
            if (props != null) {
                props.store(new FileOutputStream(PROPERTYFILENAME),
                        "-----------------------");
            }
        } catch (IOException e) {
            //e.printStackTrace();
            logAndShowMessage(e);
        }
    }

    public static void saveProps() {
        if (props != null) {
            if (getCurrentUser() != null) {
                props.setProperty(LASTLOGIN, getCurrentUser().getLogin());
            }
            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
        }
        Preferences userPref = Preferences.userRoot();
        saveProperties();
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
    
    public static boolean matchVersions(String version) {
        try {
            String servVersion;
            servVersion = Utils.getExchanger().getServerVersion();
            boolean match = removeTail(servVersion).equals(removeTail(version));
            if (!match) {
                Dialogs.showErrorDialog(mainStage, "Client's software version (" + version 
                        + ") doesn't match server (" + servVersion + ")", 
                        "Error:", "Version dismatch");
            }
            return match;
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return false;
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
    
    
    public static Properties getProperties() {
        return props;
    }

    private static Userprofile getCurrentUser() {
        return currentUser;
    }

    /**
     * @param aCurrentUser the currentUser to set
     */
    public static void setCurrentUser(Userprofile aCurrentUser) {
        currentUser = aCurrentUser;
    }
    
    public static Vector[] getBodyOnSelect(String select) {
        try {
            return getExchanger().getTableBody(select);
        } catch (RemoteException ex) {
            logAndShowMessage(ex);
        }
        return null;
    }

    /**
     * @return the mainStage
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * @param aMainStage the mainStage to set
     */
    public static void setMainStage(Stage aMainStage) {
        mainStage = aMainStage;
    }
    
}
