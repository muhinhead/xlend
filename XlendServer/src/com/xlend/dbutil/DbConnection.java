package com.xlend.dbutil;

import com.xlend.XlendServer;
import com.xlend.rmi.RmiMessageSender;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    private static final int DB_VERSION_ID = 32;
    public static final String DB_VERSION = "0.32";
    private static boolean isFirstTime = true;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas_mysql.sql", ";");//"crebas_hsqldb.sql",";");
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",

        //28->29
//        "alter table xbreakdown drop xconsume_id",
//        "alter table xemployee drop clocksheets",
//        "alter table xtimesheet drop constraint xtimesheet_xorder_fk",
//        "alter table xtimesheet alter xorder_id int null",
//        "alter table xtimesheet add constraint xtimesheet_xorder_fk foreign key (xorder_id) references xorder",
//        "alter table xemployee drop bank_details",
//        "drop table xsitediarypart",
//        //"drop table dual",
//        "create table junk (j char(1), primary key (j))",
//        "insert into junk values('j')",
//        "insert into cbitems (name, id, val)"
//        + "  select 'parts_groups',1,'machines'"
//        + "    from junk"
//        + "   where not exists(select * from cbitems where name='parts_groups' and id=1)",
//        "insert into cbitems (name, id, val)"
//        + "  select 'parts_groups',2,'trucks'"
//        + "    from junk"
//        + "   where not exists(select * from cbitems where name='parts_groups' and id=2)",
//        "insert into cbitems (name, id, val)"
//        + "  select 'parts_groups',3,'vehicles'"
//        + "    from junk"
//        + "   where not exists(select * from cbitems where name='parts_groups' and id=3)",
//        "insert into cbitems (name, id, val)"
//        + "  select 'parts_groups',4,'misc'"
//        + "    from junk"
//        + "   where not exists(select * from cbitems where name='parts_groups' and id=4)",
//        "insert into cbitems (name, id, val)"
//        + "  select 'parts_groups',5,'liquids'"
//        + "    from junk"
//        + "   where not exists(select * from cbitems where name='parts_groups' and id=5)",
//        //        "drop table xparts",
//        //        "drop table xpartcategory",
//        "create table xpartcategory"
//        + "("
//        + "    xpartcategory_id int not null auto_increment,"
//        + "    group_id         int not null,"
//        + "    name             varchar(64) not null,"
//        + "    parent_id        int,"
//        + "    constraint xpartcategory_pk primary key (xpartcategory_id),"
//        + "    constraint xpartcategory_xpartcategory_fk foreign key (parent_id) references xpartcategory (xpartcategory_id) on delete cascade"
//        + ")",
//        "create unique index xpartcategory_uniq_idx on xpartcategory (group_id, name)",
//        "insert into xpartcategory (group_id, name) "
//        + "select id, concat(upper(substr(val,1,1)), substr(val,2), ' parts') "
//        + " from cbitems"
//        + " where name='parts_groups'"
//        + " and not exists(select * from xpartcategory"
//        + " where group_id=cbitems.id and name=concat(upper(substr(cbitems.val,1,1)), substr(cbitems.val,2)))",
//        //29->30
//        "create table xparts"
//        + "("
//        + "    xparts_id        int not null auto_increment,"
//        + "    serialnumber     varchar(32) null,"
//        + "    description      varchar(128) not null,"
//        + "    xmachtype_id     int not null,"
//        + "    xpartcategory_id int not null,"
//        + "    whatfor          varchar(128),"
//        + "    constraint xparts_pk primary key (xparts_id),"
//        + "    constraint xparts_xmachtype_id foreign key (xmachtype_id) references xmachtype (xmachtype_id),"
//        + "    constraint xparts_xpartcategory_fk foreign key (xpartcategory_id) references xpartcategory (xpartcategory_id)"
//        + ")",
        //30->31
//        "drop table xpartstocks",
//        "drop table xstocks",
//        "alter table xparts change column serialnumber partnumber varchar(32)",
//        "alter table xparts drop column whatfor",
//        "alter table xparts drop foreign key xparts_xmachtype_id",
//        "alter table xparts drop column xmachtype_id",
//        "alter table xparts add storename varchar(64)",
//        "alter table xparts add machinemodel varchar(64)",
//        "alter table xparts add quantity int not null default 0",
//        "alter table xparts add lastsupplier_id int",
//        "alter table xparts add prevsupplier_id int",
//        "alter table xparts add priceperunit decimal(10,2) not null default 0",
//        "alter table xparts add purchased date",
//        "alter table xparts add constraint xparts_xsupplier_fk foreign key (lastsupplier_id) references xsupplier (xsupplier_id)",
//        "alter table xparts add constraint xparts_xsupplier_fk2 foreign key (prevsupplier_id) references xsupplier (xsupplier_id)",
//        "alter table xparts add constraint valid_quantity check(quantity>=0)",
//        "create table xbookouts"
//        + "("
//        + "    xbookouts_id    int not null auto_increment,"
//        + "    xparts_id       int not null,"
//        + "    issue_date      date not null,"
//        + "    xsite_id        int not null,"
//        + "    xmachine_id     int not null,"
//        + "    issuedby_id     int not null,"
//        + "    issuedto_id     int not null,"
//        + "    quantity        int not null,"
//        + "    constraint xbookouts_pk primary key (xbookouts_id),"
//        + "    constraint xbookouts_xparts_fk foreign key (xparts_id) references xparts (xparts_id),"
//        + "    constraint xbookouts_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),"
//        + "    constraint xbookouts_xemployee_fk foreign key (issuedby_id) references xemployee (xemployee_id),"
//        + "    constraint xbookouts_xemployee_fk2 foreign key (issuedto_id) references xemployee (xemployee_id)"
//        + ")",
//        "create table xaddstocks"
//        + "("
//        + "    xaddstocks_id   int not null auto_increment,"
//        + "    xparts_id       int not null,"
//        + "    purchase_date   date not null,"
//        + "    enteredby_id    int not null,"
//        + "    xsupplier_id    int not null,"
//        + "    priceperunit    decimal(10,2) not null,"
//        + "    quantity        int not null,"
//        + "    constraint xaddstocks_pk primary key (xaddstocks_id),"
//        + "    constraint xaddstocks_xparts_fk foreign key (xparts_id) references xparts (xparts_id),"
//        + "    constraint xaddstocks_xemployee_fk foreign key (enteredby_id) references xemployee (xemployee_id),"
//        + "    constraint xaddstocks_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)"
//        + ")"
        //31->32
        "alter table picture add stamp timestamp",
        "alter table profile add stamp timestamp",
        "alter table userprofile add stamp timestamp",
        "alter table sheet add stamp timestamp",
        "alter table usersheet add stamp timestamp",
        "alter table reportgroup add stamp timestamp",
        "alter table clientprofile add stamp timestamp",
        "alter table xclient add stamp timestamp",
        "alter table xcontract add stamp timestamp",
        "alter table xcontractpage add stamp timestamp",
        "alter table xquotation add stamp timestamp",
        "alter table xquotationpage add stamp timestamp",
        "alter table xorder add stamp timestamp",
        "alter table xsite add stamp timestamp",
        "alter table xorderpage add stamp timestamp",
        "alter table xposition add stamp timestamp",
        "alter table xemployee add stamp timestamp",
        "alter table xtimesheet add stamp timestamp",
        "alter table xwage add stamp timestamp",
        "alter table xwagesum add stamp timestamp",
        "alter table xwagesumitem add stamp timestamp",
        "alter table xmachtype add stamp timestamp",
        "alter table xmachine add stamp timestamp",
        "alter table xlowbed add stamp timestamp",
        "alter table xtrip add stamp timestamp",
        "alter table xorderitem add stamp timestamp",
        "alter table xsupplier add stamp timestamp",
        "alter table xpaidmethod add stamp timestamp",
        "alter table xdieselpchs add stamp timestamp",
        "alter table xdieselcard add stamp timestamp",
        "alter table xconsume add stamp timestamp",
        "alter table xbreakdown add stamp timestamp",
        "alter table xbreakconsume add stamp timestamp",
        "alter table xfuel add stamp timestamp",
        "alter table xpayment add stamp timestamp",
        "alter table xabsenteeism add stamp timestamp",
        "alter table xissuing add stamp timestamp",
        "alter table xtripsheet add stamp timestamp",
        "alter table xtripsheetpart add stamp timestamp",
        "alter table xaccounts add stamp timestamp",
        "alter table xbankbalance add stamp timestamp",
        "alter table xbankbalancepart add stamp timestamp",
        "alter table xsitediary add stamp timestamp",
        "alter table xsitediaryitem add stamp timestamp",
        "alter table xappforleave add stamp timestamp",
        "alter table xloans add stamp timestamp",
        "alter table xincidents add stamp timestamp",
        "alter table xmachineincident add stamp timestamp",
        "alter table xemployeeincident add stamp timestamp",
        "alter table xsalarylist add stamp timestamp",
        "alter table xsalary add stamp timestamp",
        "alter table xopclocksheet add stamp timestamp",
        "alter table xjobcard add stamp timestamp",
        "alter table xhourcompare add stamp timestamp",
        "alter table xhourcompareday add stamp timestamp",
        "alter table cbitems add stamp timestamp",
        "alter table xmachrentalrate add stamp timestamp",
        "alter table xmachrentalrateitm add stamp timestamp",
        "alter table xtransscheduleitm add stamp timestamp",
        "alter table xopmachassing add stamp timestamp",
        "alter table xpartcategory add stamp timestamp",
        "alter table xparts add stamp timestamp",
        "alter table xbookouts add stamp timestamp",
        "alter table xaddstocks add stamp timestamp"
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
            RmiMessageSender.isMySQL = (connection.getClass().getCanonicalName().indexOf("mysql") > -1);
        } catch (Exception e) {
            XlendServer.log(e);
        }
        if (isFirstTime) {
            initLocalDB(connection);
//            if (props.getProperty("dbDriverName", "org.hsqldb.jdbcDriver").equals("org.hsqldb.jdbcDriver")) {
            fixLocalDB(connection);
//            }
            isFirstTime = false;
        }
        return checkVersion(connection);
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls, connection, false);
    }

    public static void fixLocalDB(Connection connection) {
        sqlBatch(fixLocalDBsqls, connection, props.getProperty("LogDbFixes", "false").equalsIgnoreCase("true"));
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
//        connection.commit();
        connection.close();
        connection = null;
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
}
