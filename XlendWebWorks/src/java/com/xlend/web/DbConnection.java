package com.xlend.web;

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
}
