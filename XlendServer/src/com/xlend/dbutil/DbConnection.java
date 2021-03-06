package com.xlend.dbutil;

import com.xlend.XlendServer;
import com.xlend.orm.Xopmachassing;
import com.xlend.orm.dbobject.DbObject;
import com.xlend.orm.dbobject.ForeignKeyViolationException;
import com.xlend.rmi.RmiMessageSender;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    private static Thread pingThread;

    private static class ConnectionWithFlag {

        boolean isBusy = false;
        Connection connection = null;

        ConnectionWithFlag(Connection c) {
            connection = c;
            isBusy = true;
        }

        private void freeConnection() {
            isBusy = false;
        }
    }
//    private static Connection logDBconnection = null;
    private static final int DB_VERSION_ID = 55;
    public static final String DB_VERSION = "0.55";
    private static boolean isFirstTime = true;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas_mysql.sql", ";");//"crebas_hsqldb.sql",";");
    private static ArrayList<ConnectionWithFlag> connections = new ArrayList<ConnectionWithFlag>();
    private static String[] createLogDBsqls = new String[]{
        "create cached table updatelog "
        + "("
        + "    updatelog_id int generated by default as identity (start with 1),"
        + "    classname    varchar(32) not null,"
        + "    operation    int not null," //-1 - delete, 0 - update, 1 - insert
        + "    id           int not null,"
        + "    synchronized datetime,"
        + "    constraint updatelog_pk primary key(updatelog_id)"
        + ")"
    };
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",
        //        // 48->49
        //        "alter table xcashdrawn modify cur_date datetime",
        //        "alter table xpetty modify issue_date datetime",
        //        "alter table xpetty modify receipt_date datetime",
        //        "alter table xpetty modify amount decimal(10,2)",
        //        "alter table xpetty modify change_amt decimal(10,2)",
        //        "alter table xpettyitem modify amount decimal(10,2)",
        // 49->50
        //        "alter table xemployee add notes text",
        //        "alter table xsupplier add is_fuel_suppllier bit default 0",
        //        // 50->51
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
        //        + ")",
        //51->52
        //        "alter table xsite add (x_map int, y_map int)",
        //        "alter table xemployee add (why_dismissed text)",
        //        //52->53
        //        "alter table xmachine add is_lowbed bit default 0",
        //        "update xmachine set is_lowbed=1 where xmachine_id in (select xmachine_id from xlowbed)",
        //        "alter table xtrip drop foreign key xtrip_xlowbed_fk",
        //        "update xtrip set xlowbed_id=(select xmachine_id from xlowbed where xlowbed_id=xtrip.xlowbed_id)",
        //        "alter table xtrip add constraint xtrip_xmachine_fk1 foreign key (xlowbed_id) references xmachine (xmachine_id)",
        //        "alter table xtripsheet drop foreign key xtripsheet_xlowbed_fk",
        //        "update xtripsheet set xlowbed_id=(select xmachine_id from xlowbed where xlowbed_id=xtripsheet.xlowbed_id)",
        //        "alter table xtripsheet add constraint xtripsheet_xmachine_fk foreign key (xlowbed_id) references xmachine (xmachine_id)",
        //        "alter table xtransscheduleitm drop foreign key xtransscheduleitm_xmachine_fk2",
        //        "update xtransscheduleitm set lowbed_id=(select xmachine_id from xlowbed where xlowbed_id=xtransscheduleitm.lowbed_id)",
        //        "alter table xtransscheduleitm add constraint xtransscheduleitm_xmachine_fk2 foreign key (lowbed_id) references xmachine (xmachine_id)",
        //        "drop table xlowbed",
        //        //53->54
        //        "create table xessential "
        //        + "( "
        //        + "    xessential_id int not null auto_increment,"
        //        + "    issue_date date not null,"
        //        + "    return_date date null,"
        //        + "    redress_date date null,"
        //        + "    redress_amt decimal(10,2),"
        //        + "    essential varchar(255) not null," // spare wheel / jack / wheel spanner
        //        + "    driver_id int not null,"
        //        + "    issued_by int not null,"
        //        + "    received_by int,"
        //        + "    stamp timestamp,"
        //        + "    constraint xessential_pk primary key (xessential_id),"
        //        + "    constraint xessential_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id),"
        //        + "    constraint xessential_xemployee_fk1 foreign key (issued_by) references xemployee (xemployee_id),"
        //        + "    constraint xessential_xemployee_fk2 foreign key (received_by) references xemployee (xemployee_id)"
        //        + ")",
        //54->55
        "alter table xemployee add overall_size tinyint",
        "alter table xemployee add shoe_size tinyint",
        "alter table xemployee add medical_expires date",
        "create table xaccpayment "
        + "("
        + "    xaccpayment_id int not null auto_increment,"
        + "    xemployee_id   int not null,"
        + "    amount         decimal(8,2) not null,"
        + "    xsite_id       int not null,"
        + "    date1          date not null,"
        + "    date2          date not null,"
        + "    constraint xaccpayment_pk primary key (xaccpayment_id),"
        + "    constraint xaccpayment_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id),"
        + "    constraint xaccpayment_xsite_fk foreign key (xsite_id) references xsite (xsite_id)"
        + ")",
        "create table xmoveableassets "
        + "("
        + "    xmoveableassets_id int not null auto_increment,"
        + "    asset_name varchar(64) not null,"
        + "    booked_to  int not null,"
        + "    xsite_id       int not null,"
        + "    booked_out date not null,"
        + "    deadline      date not null,"
        + "    returned      date,"
        + "    constraint xmoveableassets_pk primary key (xmoveableassets_id),"
        + "    constraint xmoveableassets_xemployee_fk foreign key (booked_to) references xemployee (xemployee_id),"
        + "    constraint xmoveableassets_xsite_fk foreign key (xsite_id) references xsite (xsite_id)"
        + ")",
        "create table xemployeepenalty"
        + "("
        + "    xemployeepenalty_id  int not null auto_increment,"
        + "    points tinyint not null,"
        + "    xemployee_id int not null,"
        + "    year smallint not null,"
        + "    xincidents_id int,"
        + "    decreased_at date not null,"
        + "    notes varchar(512),"
        + "    constraint xemployeepenalty_pk primary key (xemployeepenalty_id),"
        + "    constraint xemployeepenalty_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id),"
        + "    constraint xemployeepenalty_xincidents_fk foreign key (xincidents_id) references xincidents (xincidents_id) on delete cascade"
        + ")"
    };

//    public synchronized static Connection getLogDBconnection() {
//        if (logDBconnection == null) {
//            try {
//                Locale.setDefault(Locale.ENGLISH);
//                DriverManager.registerDriver((java.sql.Driver) Class.forName("org.hsqldb.jdbcDriver").newInstance());
//                logDBconnection = DriverManager.getConnection("jdbc:hsqldb:file://" + getCurDir() + "/LogDB/XlendServer", "sa", "");
//                logDBconnection.setAutoCommit(true);
//                sqlBatch(createLogDBsqls, logDBconnection, true);
//            } catch (Exception ex) {
//                XlendServer.log(ex);
//            }
//        }
//        return logDBconnection;
//    }
    public static String getLogin() {
        return props.getProperty("dbUser", "jaco");
    }

    public static String getPassword() {
        return props.getProperty("dbPassword", "jaco84oliver");
    }

    public static String getBackupCommand() {
        return props.getProperty("dbDump", "mysqldump");
    }

    public static String getFtpURL() {
        return props.getProperty("ftpURL", "162.209.108.207");
        //"ec2-23-22-145-131.compute-1.amazonaws.com");
    }

    public static String getFtpPath() {
        return props.getProperty("ftpPath", "/");//"/root/backups/");
    }

    public static String getFtpLogin() {
        return props.getProperty("ftpLogin", "jake");//"jaco");
    }

    public static String getFtpPassword() {
        return props.getProperty("ftpLogin", "840905");//jaco84oliver");
    }

    public static boolean needBackup() {
        return props.getProperty("doBackup", "yes").equals("yes");
    }

    private static void runPingService() throws RemoteException {
        pingThread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (XlendServer.isCycle()) {
                    for (ConnectionWithFlag con : connections) {
                        if (!con.isBusy) {
                            ping(con);
                        }
                    }
                    try {
                        sleep(1000 * 60 * 30);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        };
        pingThread.start();
    }

    private static void ping(ConnectionWithFlag con) {
        con.isBusy = true;
        sqlBatch(new String[]{"select 1"}, con.connection, false);
        con.isBusy = false;
    }

    public static Connection getConnection() throws RemoteException {
        for (ConnectionWithFlag con : connections) {
            if (!con.isBusy) {
                con.isBusy = true;
//                System.out.println("!! connection FOUND");
                return con.connection;
            }
        }
        Connection connection = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(
                    (java.sql.Driver) Class.forName(
                            props.getProperty("dbDriverName",
                                    "com.mysql.jdbc.Driver")).newInstance());
            connection = DriverManager.getConnection(
                    props.getProperty("dbConnection",
                            "jdbc:mysql://localhost/xlend?characterEncoding=UTF8"),
                    //"jdbc:hsqldb:file://" + getCurDir() + "/DB/XlendServer"),
                    getLogin(), getPassword());
            connection.setAutoCommit(true);
            RmiMessageSender.isMySQL = (connection.getClass().getCanonicalName().indexOf("mysql") > -1);
        } catch (Exception e) {
            XlendServer.log(e);
        }
        if (isFirstTime) {
            initLocalDB(connection);
//            if (props.getProperty("dbDriverName", "org.hsqldb.jdbcDriver").equals("org.hsqldb.jdbcDriver")) {
            fixLocalDB(connection);
//            }
            runPingService();
            isFirstTime = false;
        }
        if (connection != null) {
            connections.add(new ConnectionWithFlag(connection));
//            System.out.println("!! connection CREATED");
            return checkVersion(connection);
        } else {
            return null;
        }
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls, connection, false);
    }

    public static void fixLocalDB(Connection connection) {
        sqlBatch(fixLocalDBsqls, connection, props.getProperty("LogDbFixes", "false").equalsIgnoreCase("true"));
        fixWrongAssignments(connection);
    }

    public static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
//                System.out.println(""+i+") "+sqls[i]);
                ps = connection.prepareStatement(sqls[i]);
                ps.execute();
                if (tolog) {
                    XlendServer.log("STATEMENT [" + sqls[i].substring(0,
                            sqls[i].length() > 60 ? 60 : sqls[i].length()) + "]... processed");
                }
            } catch (SQLException e) {
                if (tolog) {
                    XlendServer.log(e);
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

    public static Properties getProps() {
        return props;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        for (ConnectionWithFlag cf : connections) {
            if (cf.connection == connection) {
                cf.isBusy = false;
//                System.out.println("!! connection FREED");
                return;
            }
        }
//        connection.close();
        connection = null;
    }

    public static void closeAllConnections() throws SQLException {
        for (ConnectionWithFlag cf : connections) {
            cf.connection.close();
//            System.out.println("!! connection CLOSED");
            cf.connection = null;
        }
        connections.clear();
    }

    public static String getCurDir() {
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

//    public static void shutDownDatabase() {
//        Connection connection = null;
//        try {
//            connection = getConnection();
//            sqlBatch(new String[]{"sрutdown"}, connection, true);
//        } catch (RemoteException ex) {
//            XlendServer.log(ex);
//        } finally {
//            try {
//                closeConnection(connection);
//            } catch (SQLException ex1) {
//            }
//        }
//    }
    public static String[] loadDDLscript(String fname, String splitter) {
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
                ans = contents.toString().split(splitter);
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

    private static ArrayList<Integer> getAnomalies(Connection connection, String stmt) {
        PreparedStatement ps = null;
        ResultSet rs = null;
//        System.out.println("!![" + stmt + "]");
        ArrayList<Integer> anomalies = new ArrayList<Integer>();
        try {
            ps = connection.prepareStatement(stmt);
            rs = ps.executeQuery();
            while (rs.next()) {
                anomalies.add(rs.getInt(1));
            }
        } catch (SQLException ex) {
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
        return anomalies;
    }

    private static void fixWrongAssignments(Connection connection) {
        XlendServer.log("Checking assignments...");
        String stmt = "select xemployee_id from xopmachassing "
                + "where date_end is null and not xemployee_id is null group by xemployee_id having count(*)>1";
        fixOperatorsAssignments(connection, getAnomalies(connection, stmt));
        stmt = "select xmachine_id from xopmachassing "
                + "where date_end is null and not xmachine_id is null group by xmachine_id having count(*)>1";
        fixMachineAssignments(connection, getAnomalies(connection, stmt));
        stmt = "select xemployee_id from xopmachassing o "
                + "where not exists(select * from xopmachassing "
                + " where xemployee_id=o.xemployee_id and date_end is null)"
                + "and not xemployee_id is null order by xemployee_id,xopmachassing_id";
        addCurrentAssignment(connection, getAnomalies(connection, stmt), "xemployee_id");
        stmt = "select xmachine_id from xopmachassing o "
                + "where not exists(select * from xopmachassing "
                + " where xmachine_id=o.xmachine_id and date_end is null)"
                + "and not xmachine_id is null order by xmachine_id,xopmachassing_id";
        addCurrentAssignment(connection, getAnomalies(connection, stmt), "xmachine_id");
    }

    private static void fixOperatorsAssignments(Connection connection, ArrayList<Integer> operatorAnomalies) {
        fixAssignment(connection, operatorAnomalies, "xemployee_id");
    }

    private static void fixMachineAssignments(Connection connection, ArrayList<Integer> machineAnomalies) {
        fixAssignment(connection, machineAnomalies, "xmachine_id");
    }

    private static void fixAssignment(Connection connection, ArrayList<Integer> anomalies, String fld) {
        for (Integer itemID : anomalies) {
            try {
                Xopmachassing cur = null;
                Xopmachassing next = null;
                DbObject[] assigns = Xopmachassing.load(connection, fld + "=" + itemID, "xopmachassing_id");
                for (int i = 0; i < assigns.length; i++) {
                    cur = (Xopmachassing) assigns[i];
                    if (cur.getDateEnd() == null && i < assigns.length - 1) {
                        next = (Xopmachassing) assigns[i + 1];
                        if (cur.getXmachineId() == 0) {
                            cur.setXmachineId(null);
                        }
                        if (cur.getXemployeeId() == 0) {
                            cur.setXemployeeId(null);
                        }
                        cur.setDateEnd(next.getDateStart());
                        cur.save();
                    }
                }
                if (cur.getDateEnd() != null) {
                    addAssignment(cur, fld);
                }
            } catch (Exception ex) {
                XlendServer.log(ex);
            }
        }
    }

    private static void addCurrentAssignment(Connection connection, ArrayList<Integer> anomalies, String fld) {
        for (Integer itemID : anomalies) {
            try {
                DbObject[] assigns = Xopmachassing.load(connection, fld + "=" + itemID, "xopmachassing_id desc");
                Xopmachassing cur = (Xopmachassing) assigns[0];
                addAssignment(cur, fld);
            } catch (Exception ex) {
                XlendServer.log(ex);
            }
        }
    }

    private static void addAssignment(Xopmachassing cur, String fld) throws SQLException, ForeignKeyViolationException {
        Date dt = cur.getDateEnd();
        cur.setXopmachassingId(0);
        cur.setNew(true);
        cur.setDateStart(dt);
        cur.setDateEnd(null);
        if (fld.equals("xemployee_id")) {
            cur.setXmachineId(null);
        } else if (fld.equals("xmachine_id")) {
            cur.setXemployeeId(null);
        }
        cur.save();
    }
}
