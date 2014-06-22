package com.xlend.rmi;

import com.xlend.gui.XlendWorks;
import com.xlend.remote.IMessageSender;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class ExchangeFactory {

    private static final int DB_VERSION_ID = 52;
    public static final String DB_VERSION = "0.52";
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",
        //        "alter table xemployee add notes text",
        //        "alter table xsupplier add is_fuel_suppllier bit default 0",
        // 50->51
        //        "create table xmachineorder"
        //        + "("
        //        + "    xmachineorder_id    int not null auto_increment,"
        //        + "    issue_date          date not null,"
        //        + "    require_date        date not null,"
        //        + "    xemployee_id        int not null,"
        //        + "    xsite_id            int not null,"
        //        + "    xclient_id          int,"
        //        + "    xorder_id           int,"
        //        + "    site_address        varchar(128),"
        //        + "    distance2site       int,"
        //        + "    foreman_req_plant   varchar(128),"
        //        + "    foreman_contact     varchar(128),"
        //        + "    constraint xmachineorder_pk primary key (xmachineorder_id),"
        //        + "    constraint xmachineorder_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id),"
        //        + "    constraint xmachineorder_xsite_fk foreign key (xsite_id) references xsite (xsite_id),"
        //        + "    constraint xmachineorder_xclient_fk foreign key (xclient_id) references xclient (xclient_id),"
        //        + "    constraint xmachineorder_xorder_fk foreign key (xorder_id) references xorder (xorder_id)"
        //        + ")",
        //        "create table xmachineorderitm"
        //        + "("
        //        + "    xmachineorderitm_id int not null auto_increment,"
        //        + "    xmachineorder_id    int not null,"
        //        + "    xmachine_id         int not null,"
        //        + "    xemployee_id        int not null,"
        //        + "    constraint xmachineorderitm_pk primary key (xmachineorderitm_id),"
        //        + "    constraint xmachineorderitm_xmachineorder_fk foreign key (xmachineorder_id) references xmachineorder (xmachineorder_id) on delete cascade,"
        //        + "    constraint xmachineorderitm_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),"
        //        + "    constraint xmachineorderitm_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id)"
        //        + ")"
        //51->52
        "alter table xsite add (x_map int, y_map int)",
        "alter table xemployee add (why_dismissed text)",
        "drop function to_char",
        "create function to_char(dt datetime, fmt varchar(32))\n"
        + "returns varchar(32) deterministic\n"
        + "begin\n"
        + "   declare fmt char(32) default fmt;\n"
        + "   set fmt = replace(fmt,'DD','%d');\n"
        + "   set fmt = replace(fmt,'MM','%m');\n"
        + "   set fmt = replace(fmt,'YYYY','%Y');\n"
        + "   set fmt = replace(fmt,'YY','%y');\n"
        + "   set fmt = replace(fmt,'HH24','%H');\n"
        + "   set fmt = replace(fmt,'MI','%i');\n"
        + "   set fmt = replace(fmt,'SS','%S');\n"
        + "   return DATE_FORMAT(dt, fmt);\n"
        + "end;"
    };

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
        if (dbParams.length < 4) {
            return null;
        }
        return createJDBCexchanger(dbParams[0], dbParams[1], dbParams[2], dbParams[3]);
    }

    public static IMessageSender createJDBCexchanger(String dbDriver, String connectString,
            String dbUser, String dbPassword) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (dbDriver == null || dbDriver.isEmpty() || connectString == null || connectString.isEmpty()
                || dbUser == null || dbUser.isEmpty() || dbPassword == null || dbPassword.isEmpty()) {
            throw new SQLException("Incomplete DB connection parameters");
        }
        XlendWorks.protocol = "jdbc";
        IMessageSender exchanger;
        DriverManager.registerDriver(
                (java.sql.Driver) Class.forName(dbDriver).newInstance());
        Connection connection = DriverManager.getConnection(connectString, dbUser, dbPassword);
        connection.setAutoCommit(true);
        sqlBatch(fixLocalDBsqls, connection, false);
        exchanger = new DbClientDataSender(connection);
        return exchanger;
    }

    public static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
                ps = connection.prepareStatement(sqls[i]);
                ps.execute();
                if (tolog) {
                    XlendWorks.log("STATEMENT [" + sqls[i].substring(0,
                            sqls[i].length() > 60 ? 60 : sqls[i].length()) + "]... processed");
                }
            } catch (SQLException e) {
                if (tolog) {
                    XlendWorks.log(e);
                }
            } finally {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
