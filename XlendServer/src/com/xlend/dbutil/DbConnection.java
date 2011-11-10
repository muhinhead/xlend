/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

import com.xlend.XlendServer;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    private static boolean isFirstTime = true;
//    private static Connection connection = null;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas_hsqldb.sql");
    private static String[] fixLocalDBsqls = new String[]{ //TODO: put here database fixes
    };

    public static Connection getConnection() {
//        if (null == connection) {
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
//        }
        return connection;
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls, connection);
    }

    public static void fixLocalDB(Connection connection) {
        sqlBatch(fixLocalDBsqls, connection);
    }

    private static void sqlBatch(String[] sqls, Connection connection) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
                ps = connection.prepareStatement(sqls[i]);
                ps.execute();
            } catch (SQLException e) {
//                e.printStackTrace();
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
