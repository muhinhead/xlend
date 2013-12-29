package com.xlend.rmi;

import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
                exchanger = createJDBCexchanger(dbDriver, connectString, dbUser, dbPassword);
                XlendWorks.protocol = "jdbc";
            } catch (Exception ex) {
                XlendWorks.log(ex);
            }
        }
        return exchanger;
    }

    public static IMessageSender createRMIexchanger(String address) throws NotBoundException, MalformedURLException, RemoteException {
        XlendWorks.protocol = "rmi";
        return (IMessageSender) Naming.lookup("rmi://" + address + "/XlendServer");
    }
    
    public static IMessageSender createJDBCexchanger(String[] dbParams) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if(dbParams.length<4) {
            return null;
        }
        return createJDBCexchanger(dbParams[0],dbParams[1],dbParams[2],dbParams[3]);
    }
    
    public static IMessageSender createJDBCexchanger(String dbDriver, String connectString, 
            String dbUser, String dbPassword) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (dbDriver==null || dbDriver.isEmpty() || connectString==null || connectString.isEmpty() || 
                dbUser==null || dbUser.isEmpty() || dbPassword==null || dbPassword.isEmpty()) {
            throw new SQLException("Incomplete DB connection parameters");
        }
        XlendWorks.protocol = "jdbc";
        IMessageSender exchanger;
        DriverManager.registerDriver(
                (java.sql.Driver) Class.forName(dbDriver).newInstance());
        Connection connection = DriverManager.getConnection(connectString, dbUser, dbPassword);
        connection.setAutoCommit(true);
        exchanger = new DbClientDataSender(connection);
        return exchanger;
    }
}
