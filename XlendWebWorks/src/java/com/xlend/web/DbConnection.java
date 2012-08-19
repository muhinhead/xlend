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
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    protected static Properties props = new Properties();

    public static Connection getConnection() {
        Connection connection = null;
        try {
            DriverManager.registerDriver((java.sql.Driver) Class.forName(
                    props.getProperty("dbDriverName", "com.mysql.jdbc.Driver")).newInstance());
            connection = DriverManager.getConnection(props.getProperty("dbConnection",
                    "jdbc:mysql://localhost/xlend?autoReconnect=true"),
                    props.getProperty("dbUser", "jaco"),
                    props.getProperty("dbPassword", "jaco84oliver"));
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return connection;
    }

    public static void setProps(Properties props) {
        DbConnection.props = props;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
            connection = null;
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

    public static String getPositionOnID(int xposition_id) {
        String position = "unknown";
        try {
            Xposition pos = (Xposition) new Xposition(getConnection()).loadOnId(xposition_id);
            position = pos.getPos();
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return position;
    }

    public static String getWageCategoryOnID(int wageCatID) {
        String category = "unknown";
        try {
            DbObject[] obs = Cbitems.load(getConnection(), "name='wage_category' and id=" + wageCatID, null);
            if (obs.length > 0) {
                Cbitems cb = (Cbitems) obs[0];
                category = cb.getVal();
            }
        } catch (Exception ex) {
            Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return category;
    }

    public static String[] findCurrentAssignment(int employee_id) {
        try {
            Connection connection = getConnection();
            DbObject[] obs = Xopmachassing.load(connection, "xemployee_id=" 
                    + employee_id + " and date_end is null", null);
            if (obs.length > 0) {
                String[] ans = new String[2];
                Xopmachassing assign = (Xopmachassing) obs[0];
                Xsite xsite = //(Xsite) exchanger.loadDbObjectOnID(Xsite.class, assign.getXsiteId());
                        (Xsite) new Xsite(getConnection()).loadOnId(assign.getXsiteId());
                Xmachine xmachine = //(Xmachine) exchanger.loadDbObjectOnID(Xmachine.class, assign.getXmachineId());
                        (Xmachine) new Xmachine(getConnection()).loadOnId(assign.getXmachineId());
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
