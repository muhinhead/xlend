package com.xlend.dbutil;

import com.xlend.XlendServer;
import com.xlend.orm.dbobject.DbObject;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Set;
import java.util.TimerTask;

/**
 *
 * @author Nick Mukhin
 */
public class SyncPushTimer extends TimerTask {

    private static Connection targetConnection = null;

    public static Connection getRemoteDBconnection() {
        if (targetConnection == null) {
            try {
                Locale.setDefault(Locale.ENGLISH);
                DriverManager.registerDriver((java.sql.Driver) Class.forName(
                        DbConnection.getProps().getProperty("remoteDriver",
                        "com.mysql.jdbc.Driver")).newInstance());
                targetConnection = DriverManager.getConnection(
                        DbConnection.getProps().getProperty("remoteConnection",
                        "jdbc:mysql://ec2-23-22-145-131.compute-1.amazonaws.com/xlend"),
                        DbConnection.getProps().getProperty("remoteLogin",
                        "jaco"), 
                        DbConnection.getProps().getProperty("remotePassword",
                        "jaco84oliver"));
                targetConnection.setAutoCommit(true);
            } catch (Exception ex) {
                XlendServer.log(ex);
            }
        }
        return targetConnection;
    }

    private class UpdateLog {

        String classname;
        int operation;
        int id;

        UpdateLog(String classname, int operation, int id) {
            this.classname = classname;
            this.operation = operation;
            this.id = id;
        }
    }
    private static Hashtable<Integer, UpdateLog> updated = new Hashtable<Integer, UpdateLog>();

    public static synchronized Hashtable<Integer, UpdateLog> getUpdated() {
        return updated;
    }

    @Override
    public void run() {
        //XlendServer.log("==== SyncPushTimer runs ====");
        sync();
    }

    private void sync() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String stmt = "SELECT updatelog_id, classname, operation, id "
                + "FROM updatelog WHERE synchronized is null ORDER BY updatelog_id";
        try {
            ps = DbConnection.getLogDBconnection().prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                getUpdated().put(rs.getInt(1), new UpdateLog(rs.getString(2), rs.getInt(3), rs.getInt(4)));
            }
        } catch (Exception ex) {
            XlendServer.log(ex);
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
    }

    private static void setSyncMark(Integer ulID) {
        PreparedStatement ps = null;
        try {
            ps = DbConnection.getLogDBconnection().prepareStatement(
                    "update updatelog set synchronized = now() where updatelog_id=" + ulID);
            ps.execute();
            getUpdated().remove(ulID);
        } catch (SQLException ex) {
            XlendServer.log(ex);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException se2) {
            }
        }
    }

    public static void syncRemoteDB() throws Exception {
        Object[] keySet = getUpdated().keySet().toArray().clone();
        if (keySet.length > 0) {
            XlendServer.log("==== SyncPushTimer.syncRemoteDB() runs ====");
            for (Object ob : keySet) {
                Integer ulID = (Integer) ob;
                UpdateLog ul = getUpdated().get(ulID);
                Class dbobClass = Class.forName(ul.classname);
                Constructor constructor = dbobClass.getConstructor(Connection.class);
                DbObject dbob = (DbObject) constructor.newInstance(DbConnection.getConnection());
                if (ul.operation != -1) {
                    dbob = dbob.loadOnId(ul.id);
                } else {
                    dbob.setPK_ID(ul.id);
                }
                if (dbob != null) {
                    dbob.setConnection(getRemoteDBconnection());
                    switch (ul.operation) {
                        case 1:
                            dbob.setNew(true);
                            dbob.save();
                            XlendServer.log(ul.classname + " id=" + ul.id + " inserted");
                            break;
                        case 0:
                            dbob.setNew(false);
                            dbob.save();
                            XlendServer.log(ul.classname + " id=" + ul.id + " updated");
                            break;
                        case -1:
                            dbob.delete();
                            XlendServer.log(ul.classname + " id=" + ul.id + " deleted");
                            break;
                    }
                } else {
                    XlendServer.log(ul.classname + " id=" + ul.id + " not found");
                }
                setSyncMark(ulID);
            }
        }
    }
}
