/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

import com.xlend.XlendServer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    private static final int DB_VERSION_ID = 2;
    private static final String DB_VERSION = "0.02";
    private static boolean isFirstTime = true;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas_hsqldb.sql");
    private static String[] fixLocalDBsqls = new String[] {
        "create table dbversion ("
        + "  dbversion_id    int not null identity,"
        + "  version_id      int not null,"
        + "  version         varchar(12),"
        + "constraint dbversion_pk primary key (dbversion_id))",
        "insert into dbversion values(1,0,'0.0')",
        "create table xsite ("  
        + "    xsite_id        int not null identity,"
        + "    name            varchar(32) not null,"
        + "    description     varchar(255),"
        + "    dieselsponsor   smallint default 0,"
        + "    sitetype        char(1),"
        + "    constraint xsite_id primary key (xsite_id))",
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'"
    };

    public static Connection getConnection() throws RemoteException {
        Connection connection = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(
                    (java.sql.Driver) Class.forName(
                    props.getProperty("dbDriverName",
                    "org.hsqldb.jdbcDriver")).newInstance());
            connection = DriverManager.getConnection(
                    props.getProperty("dbConnection",
                    "jdbc:hsqldb:file://" + getCurDir() + "/DB/XlendServer"),
                    props.getProperty("dbUser", "sa"),
                    props.getProperty("dbPassword", ""));
            connection.setAutoCommit(true);
        } catch (Exception e) {
            DbUtil.log(e);
        }
        if (isFirstTime && props.getProperty("dbDriverName", "org.hsqldb.jdbcDriver").equals("org.hsqldb.jdbcDriver")) {
            initLocalDB(connection);
            fixLocalDB(connection);
            isFirstTime = false;
        }
        return checkVersion(connection);
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls, connection, false);
    }

    public static void fixLocalDB(Connection connection) {
        sqlBatch(fixLocalDBsqls, connection, true);
    }

    private static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
                ps = connection.prepareStatement(sqls[i]);
                ps.execute();
            } catch (SQLException e) {
                if (tolog) {
                    e.printStackTrace();
                }
            } finally {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
        }
    }

    public static void setProps(Properties props) {
        DbConnection.props = props;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        connection.commit();
        connection.close();
        connection = null;
    }

    private static String getCurDir() {
        File curdir = new File("./");
        return curdir.getAbsolutePath();
    }

    private static Connection checkVersion(Connection connection) throws RemoteException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String err;
        String stmt = "SELECT version_id,version FROM dbversion WHERE dbversion_id=1";
        int curversion_id = 0;
        String curversion = "0.0";
        try {
            ps = connection.prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                curversion_id = rs.getInt(1);
                curversion = rs.getString(2);
            }
            if (DB_VERSION_ID > curversion_id || !curversion.equals(DB_VERSION)) {
                err = "Invalid database version! " + "expected:" + DB_VERSION + "(" + DB_VERSION_ID + ") "
                        + "found:" + curversion + "(" + curversion_id + ")";
                XlendServer.log(err);
                throw new RemoteException(err);
            }
        } catch (SQLException ex) {
            XlendServer.log(ex);
            try {
                closeConnection(connection);
            } catch (SQLException ex1) {
            }
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
        return connection;
    }

    private static class BufferedReader {

        public BufferedReader() {
        }
    }

    private static String[] loadDDLscript(String fname) {
        String[] ans = new String[0];
        File sqlFile = new File(fname);
        boolean toclean = true;
        if (!sqlFile.exists()) {
            fname = "../sql/" + fname;
            sqlFile = new File(fname);
            toclean = false;
        }
        if (sqlFile.exists()) {
            StringBuffer contents = new StringBuffer();
            java.io.BufferedReader reader = null;
            try {
                reader = new java.io.BufferedReader(new FileReader(sqlFile));
                String line = null;
                int lineNum = 0;
                while ((line = reader.readLine()) != null) {
                    int cut = line.indexOf("--");
                    if (cut >= 0) {
                        line = line.substring(0, cut);
                    }
                    contents.append(line).append(System.getProperty("line.separator"));
                }
                ans = contents.toString().split(";");
            } catch (Exception e) {
                XlendServer.log(e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ie) {
                }
            }
            if (toclean) {
                sqlFile.delete();
            }
        } else {
            XlendServer.log("File " + fname + " not found");
        }
        return ans;
    }
}
