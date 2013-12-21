package com.xlend.rmi;

import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.rmi.Naming;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class ExchangeFactory {

    public static IMessageSender getExchanger(String connectString, Properties props) {
        IMessageSender exchanger = null;
        if (connectString.startsWith("rmi:")) {
            try {
                exchanger = (IMessageSender) Naming.lookup(connectString);
                XlendWorks.protocol = "rmi";
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
        } else if (connectString.startsWith("jdbc:")) {
            String dbUser = props.getProperty("dbUser", "root");
            String dbPassword = props.getProperty("dbPasword", "root");
            String dbDriver = props.getProperty("dbDriverName", "com.mysql.jdbc.Driver");
            try {
                DriverManager.registerDriver(
                        (java.sql.Driver) Class.forName(dbDriver).newInstance());
                Connection connection = DriverManager.getConnection(connectString, dbUser, dbPassword);
                connection.setAutoCommit(true);
                exchanger = new DbClientDataSender(connection);
                XlendWorks.protocol = "jdbc";
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
        }
        return exchanger;
    }
}
