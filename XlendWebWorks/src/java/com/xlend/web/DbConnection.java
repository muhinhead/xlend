package com.xlend.web;

import com.xlend.orm.*;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    protected static Properties props = new Properties();
    private static Connection cnct = null;
    private static long timestamp = Calendar.getInstance().getTimeInMillis();

    public static Connection getConnection() {
        if (cnct == null || Calendar.getInstance().getTimeInMillis() - timestamp > 1000 * 1800) {
            Connection connection = null;
            try {
                if (cnct != null) {
                    closeConnection(cnct);
                }
                DriverManager.registerDriver((java.sql.Driver) Class.forName(
                        props.getProperty("dbDriverName", "com.mysql.jdbc.Driver")).newInstance());
                connection = DriverManager.getConnection(props.getProperty("dbConnection",
                        "jdbc:mysql://localhost/xlend?autoReconnect=true"),
                        props.getProperty("dbUser", "jaco"),
                        props.getProperty("dbPassword", "jaco84oliver"));
            } catch (Exception ex) {
                Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
            cnct = connection;
        }
        if (cnct != null) {
            timestamp = Calendar.getInstance().getTimeInMillis();
        }
        return cnct;
    }

    public static void setProps(Properties props) {
        DbConnection.props = props;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (cnct != null && Calendar.getInstance().getTimeInMillis() - timestamp > 1000 * 1800) {
            cnct.close();
            cnct = null;
        }
    }

    public static String encodeHTML(String s) {
        StringBuffer out = new StringBuffer();
        if (s == null) {
            return "null";
        }
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c > 127 || c == '"' || c == '<' || c == '>') {
                out.append("&#" + (int) c + ";");
            } else {
                out.append(c);
            }
        }
        return out.toString();
    }

    public static String getPositionOnID(int xposition_id, Connection connection) {
        String position = "unknown";
        try {
            Xposition pos = (Xposition) new Xposition(connection).loadOnId(xposition_id);
            position = pos.getPos();
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }

    public static String getSiteType(String shortSiteType, Connection connection) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement("select min(val) from cbitems where substr(val,1,1)='"
                    + shortSiteType + "' and name='site_types'");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                    }
                }
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }
        return "?";
    }

    private static String getStringOnId(String select, int id, Connection connection) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(select + id);
            rs = ps.executeQuery();
            if (rs.next()) {
                try {
                    return rs.getObject(1).toString();
                } catch (Exception ex) {
                    return "";
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                    }
                }
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }
        return "?";
    }

    public static String getStampOnID(int id, String table, Connection connection) {
        return getStringOnId("Select date(stamp) from " + table + " where " + table + "_id=", id, connection);
    }

//    public static String getStampOnID(int id, Connection connection) {
//        return getStringOnId("Select date(stamp) from xemployee where xemployee_id=", id, connection);
//    }
    public static String getOrderOnId(int order_id, Connection connection) {
        if (order_id <= 0) {
            return "none";
        } else {
            return getStringOnId("Select concat('Order No_',ordernumber) from xorder where xorder_id=", order_id, connection);
        }
    }

    public static String getWageCategoryOnID(int wageCatID, Connection connection) {
        String category = "unknown";
        try {
            DbObject[] obs = Cbitems.load(connection, "name='wage_category' and id=" + wageCatID, null);
            if (obs.length > 0) {
                Cbitems cb = (Cbitems) obs[0];
                category = cb.getVal();
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public static String[] findCurrentAssignment(int employee_id, Connection connection) {
        try {
            DbObject[] obs = Xopmachassing.load(connection, "xemployee_id="
                    + employee_id + " and date_end is null", null);
            if (obs.length > 0) {
                String[] ans = new String[2];
                Xopmachassing assign = (Xopmachassing) obs[0];
                Xsite xsite = (Xsite) new Xsite(getConnection()).loadOnId(assign.getXsiteId());
                Xmachine xmachine = (Xmachine) new Xmachine(getConnection()).loadOnId(assign.getXmachineId());
                ans[0] = xsite == null ? "unassigned" : xsite.getName();
                ans[1] = xmachine == null ? "unassigned" : (xmachine.getClassify() + xmachine.getTmvnr());
                return ans;
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeFile(File file, byte[] imageData) {
        try {
            FileOutputStream fout = new FileOutputStream(file);
            fout.write(imageData);
            fout.close();
        } catch (Exception e) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
