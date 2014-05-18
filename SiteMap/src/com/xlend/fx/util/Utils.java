package com.xlend.fx.util;

import com.xlend.SiteMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.prefs.Preferences;
import javafx.stage.Stage;
import javafx.scene.control.Dialogs;

/**
 *
 * @author Nick Mukhin
 */
public class Utils {

    private static Stage mainStage = null;
    private static Properties props;
    private static String PROPERTYFILENAME = "SiteMap.config";
    private static Logger logger;
    private static FileHandler fh;

    public static void log(String msg) {
        log(msg, null);
    }

    public static void log(Throwable th) {
        log(null, th);
    }

    private static void log(String msg, Throwable th) {
        if (logger == null) {
            try {
                logger = Logger.getLogger("SiteMap");
                fh = new FileHandler("%h/SiteMap.log", 1048576, 10, true);
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
        Dialogs.showErrorDialog(mainStage, ne.getMessage(), "Error:", "v."+SiteMap.VERSION);
        log(ne);
    }

    public static void notImplementedYet() {
        Dialogs.showErrorDialog(mainStage, "Not implemented yet", "Error:", "Sorry!");
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
                    props.setProperty("dbDriverName", "com.mysql.jdbc.Driver");
                    props.setProperty("dbConnection", "jdbc:mysql://localhost/xlend?characterEncoding=UTF8");
                    props.setProperty("dbUser", "jaco");
                    props.setProperty("dbPassword", "jaco84oliver");
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
            logAndShowMessage(e);
        }
    }
//    public static void saveProps() {
//        if (props != null) {
//            if (getCurrentUser() != null) {
//                props.setProperty(LASTLOGIN, getCurrentUser().getLogin());
//            }
//            props.setProperty("ServerAddress", props.getProperty("ServerAddress", "localhost:1099"));
//        }
//        Preferences userPref = Preferences.userRoot();
//        saveProperties();
//    }
//
//    private static String removeTail(String s) {
//        int p = s.lastIndexOf(".");
//        if (p > 0 && s.length() > p + 1) {
//            if ("0123456789".indexOf(s.substring(p + 1, p + 2)) < 0) {
//                return s.substring(0, p);
//            }
//        }
//        return s;
//    }    
    
    public static int getCount(Connection connection, String select) throws SQLException {
        StringBuffer slct;
        int count = 0;
        int p = select.toLowerCase().lastIndexOf("order by");
        slct = new StringBuffer("select count(*) from (" + select.substring(0, p > 0 ? p : select.length()) + ") intab");
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(slct.toString());
            rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException se2) {
                }
            }
        }
        return count;
    }
    
    public static Vector[] getTableBody(Connection connection, String select) throws SQLException {
        return getTableBody(connection, select, 0, 0);
    }
    
    public static Vector[] getTableBody(Connection connection, String select, int page, int pagesize) throws SQLException {
        Vector headers = getColNames(connection, select);
        int startrow = 0, endrow = 0;
        if (page > 0 || pagesize > 0) {
            startrow = page * pagesize + 1; //int page starts from 0, int startrow starts from 1
            endrow = (page + 1) * pagesize; //last row of page
        }
        return new Vector[]{headers, getRows(connection, select, headers.size(), startrow, endrow)};
    }

    private static Vector getRows(Connection connection, String select, int cols, int startrow, int endrow) throws SQLException {
        Vector rows = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String pagedSelect;
            if (select.toUpperCase().indexOf(" LIMIT ") > 0 || (startrow == 0 && endrow == 0)) {
                pagedSelect = select;
            } else {
                pagedSelect = select.replaceFirst("select", "SELECT").replaceAll("Select", "SELECT");
                    pagedSelect += (" LIMIT " + (startrow - 1) + "," + (endrow - startrow + 1));
            }
            Vector line;
            ps = connection.prepareStatement(pagedSelect);
            rs = ps.executeQuery();
            while (rs.next()) {
                line = new Vector();
                for (int c = 0; c < cols; c++) {
                    String ceil = rs.getString(c + 1);
                    ceil = ceil == null ? "" : ceil;
                    line.add(ceil);
                }
                rows.add(line);
            }
            return rows;
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException se2) {
                }
            }
        }
    }
    
    public static Vector getColNames(Connection connection, String select) throws SQLException {
        String original = null;
        Vector colNames = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            int i;
            int bracesLevel = 0;
            StringBuffer sb = null;
            for (i = 0; i < select.length(); i++) {
                char c = select.charAt(i);
                if (c == '(') {
                    bracesLevel++;
                } else if (c == ')') {
                    bracesLevel--;
                } else if (bracesLevel == 0 && select.substring(i).toUpperCase().startsWith("WHERE ")) {
                    if (sb == null) {
                        original = select;
                        sb = new StringBuffer(select);
                    }
                    sb.insert(i + 6, "1=0 AND ");
                    break;
                }
            }
            if (sb != null) {
                select = sb.toString();
            }
            ps = connection.prepareStatement(select);
            rs = ps.executeQuery();
            ResultSetMetaData md = rs.getMetaData();
            for (i = 0; i < md.getColumnCount(); i++) {
                colNames.add(md.getColumnLabel(i + 1));
            }
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException se1) {
            } finally {
                try {
                    if (ps != null) {
                        ps.close();
                    }
                } catch (SQLException se2) {
                }
            }
        }
        return colNames;
    }

}
