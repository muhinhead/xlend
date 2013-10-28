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
    private static final int DB_VERSION_ID = 49;
    public static final String DB_VERSION = "0.49";
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
        //        "alter table picture add stamp timestamp",
        //        "alter table profile add stamp timestamp",
        //        "alter table userprofile add stamp timestamp",
        //        "alter table sheet add stamp timestamp",
        //        "alter table usersheet add stamp timestamp",
        //        "alter table reportgroup add stamp timestamp",
        //        "alter table clientprofile add stamp timestamp",
        //        "alter table xclient add stamp timestamp",
        //        "alter table xcontract add stamp timestamp",
        //        "alter table xcontractpage add stamp timestamp",
        //        "alter table xquotation add stamp timestamp",
        //        "alter table xquotationpage add stamp timestamp",
        //        "alter table xorder add stamp timestamp",
        //        "alter table xsite add stamp timestamp",
        //        "alter table xorderpage add stamp timestamp",
        //        "alter table xposition add stamp timestamp",
        //        "alter table xemployee add stamp timestamp",
        //        "alter table xtimesheet add stamp timestamp",
        //        "alter table xwage add stamp timestamp",
        //        "alter table xwagesum add stamp timestamp",
        //        "alter table xwagesumitem add stamp timestamp",
        //        "alter table xmachtype add stamp timestamp",
        //        "alter table xmachine add stamp timestamp",
        //        "alter table xlowbed add stamp timestamp",
        //        "alter table xtrip add stamp timestamp",
        //        "alter table xorderitem add stamp timestamp",
        //        "alter table xsupplier add stamp timestamp",
        //        "alter table xpaidmethod add stamp timestamp",
        //        "alter table xdieselpchs add stamp timestamp",
        //        "alter table xdieselcard add stamp timestamp",
        //        "alter table xconsume add stamp timestamp",
        //        "alter table xbreakdown add stamp timestamp",
        //        "alter table xbreakconsume add stamp timestamp",
        //        "alter table xfuel add stamp timestamp",
        //        "alter table xpayment add stamp timestamp",
        //        "alter table xabsenteeism add stamp timestamp",
        //        "alter table xissuing add stamp timestamp",
        //        "alter table xtripsheet add stamp timestamp",
        //        "alter table xtripsheetpart add stamp timestamp",
        //        "alter table xaccounts add stamp timestamp",
        //        "alter table xbankbalance add stamp timestamp",
        //        "alter table xbankbalancepart add stamp timestamp",
        //        "alter table xsitediary add stamp timestamp",
        //        "alter table xsitediaryitem add stamp timestamp",
        //        "alter table xappforleave add stamp timestamp",
        //        "alter table xloans add stamp timestamp",
        //        "alter table xincidents add stamp timestamp",
        //        "alter table xmachineincident add stamp timestamp",
        //        "alter table xemployeeincident add stamp timestamp",
        //        "alter table xsalarylist add stamp timestamp",
        //        "alter table xsalary add stamp timestamp",
        //        "alter table xopclocksheet add stamp timestamp",
        //        "alter table xjobcard add stamp timestamp",
        //        "alter table xhourcompare add stamp timestamp",
        //        "alter table xhourcompareday add stamp timestamp",
        //        "alter table cbitems add stamp timestamp",
        //        "alter table xmachrentalrate add stamp timestamp",
        //        "alter table xmachrentalrateitm add stamp timestamp",
        //        "alter table xtransscheduleitm add stamp timestamp",
        //        "alter table xopmachassing add stamp timestamp",
        //        "alter table xpartcategory add stamp timestamp",
        //        "alter table xparts add stamp timestamp",
        //        "alter table xbookouts add stamp timestamp",
        //        "alter table xaddstocks add stamp timestamp",
        //        "alter table xtrip modify tosite_id int null",
        //        "alter table xtrip add othersite varchar(128)",
        //        "alter table xtripsheet drop foreign key xtripsheet_xmachine_fk",
        //        "alter table xtripsheet add constraint xtripsheet_xlowbed_fk foreign key (xlowbed_id) references xlowbed (xlowbed_id)",
        //        "update xlowbed set xmachine_id=185 where xmachine_id=179",
        //        "update xlowbed set xmachine_id=186 where xmachine_id=178",
        //        "update xlowbed set xmachine_id=187 where xmachine_id=181",
        //        "update xlowbed set xmachine_id=196 where xmachine_id=182",
        //        "update xconsume set xmachine_id=185 where xmachine_id=179",
        //        "update xconsume set xmachine_id=186 where xmachine_id=178",
        //        "update xconsume set xmachine_id=187 where xmachine_id=181",
        //        "update xconsume set xmachine_id=196 where xmachine_id=182",
        //        "update xmachineincident set xmachine_id=185 where xmachine_id=179",
        //        "update xmachineincident set xmachine_id=186 where xmachine_id=178",
        //        "update xmachineincident set xmachine_id=187 where xmachine_id=181",
        //        "update xmachineincident set xmachine_id=196 where xmachine_id=182",
        //        "update xopmachassing set xmachine_id=185 where xmachine_id=179",
        //        "update xopmachassing set xmachine_id=186 where xmachine_id=178",
        //        "update xopmachassing set xmachine_id=187 where xmachine_id=181",
        //        "update xopmachassing set xmachine_id=196 where xmachine_id=182",
        //        "update xmachine set xmachtype_id=7 where xmachtype_id=3826",
        //        "delete from xmachine where xmachtype_id not in (select xmachtype_id from xmachtype)",
        //32->33    
        //        "alter table xtimesheet modify xsite_id int null",
        //        "alter table xtimesheet add xmachine_id int null",
        //        "alter table xtimesheet add constraint xtimesheet_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)",
        //        "alter table xwage drop deduction",
        //        "drop function extractnum",
        //        "create function extractnum(sval varchar(32)) "
        //        + "returns integer deterministic "
        //        + "begin "
        //        + "   declare sub varchar(32) default '';"
        //        + "   declare i tinyint default 0;"
        //        + "   LP: loop"
        //        + "        set i = i + 1;"
        //        + "        if i > length(sval) then"
        //        + "            leave LP;"
        //        + "        end if;"
        //        + "        if instr('0123456789',substr(sval,i,1))>0 then"
        //        + "            set sub = concat(sub,substr(sval,i,1));"
        //        + "        end if;"
        //        + "   end loop LP;"
        //        + "   return convert(sub, unsigned);"
        //        + "end;",
        //        "drop function extractchars",
        //        "create function extractchars(sval varchar(32)) "
        //        + "returns char(32) deterministic "
        //        + "begin "
        //        + "   declare sub varchar(32) default ''; "
        //        + "   declare i tinyint default 0; "
        //        + "   LP: loop "
        //        + "        set i = i + 1; "
        //        + "        if i > length(sval) then "
        //        + "            leave LP; "
        //        + "        end if; "
        //        + "        if instr('0123456789',substr(sval,i,1))=0 then "
        //        + "            set sub = concat(sub,substr(sval,i,1)); "
        //        + "        end if; "
        //        + "   end loop LP; "
        //        + "   return sub; "
        //        + "end; ",

        //        //41->42
        //        "alter table xmachtype add is_rated bit",
        //        "update xmachtype set is_rated=(parenttype_id is null)",
        //        "alter table xmachrentalrateitm drop foreign key xmachrentalrateitm_cbitem_fk",
        //        "alter table xmachrentalrateitm drop cbitem_id",
        //        "alter table xmachrentalrateitm add xmachtype_id int",
        //        "alter table xmachrentalrateitm add "
        //        + "constraint xmachrentalrateitm_xmachtype_fk foreign key (xmachtype_id) "
        //        + "references xmachtype(xmachtype_id) on delete cascade",
        //        "alter table xmachine add fueltype int default 1",
        //        //42->43
        //        "create table xpettycategory"
        //        + "("
        //        + "    xpettycategory_id   int not null auto_increment,"
        //        + "    category_name       varchar(64) not null,"
        //        + "    stamp               timestamp,"
        //        + "    constraint xpettycategory_pk primary key (xpettycategory_id)"
        //        + ")",
        //        "create table xpetty"
        //        + "("
        //        + "    xpetty_id           int not null auto_increment,"
        //        + "    issue_date          date not null,"
        //        + "    xemployee_in_id     int not null,"
        //        + "    xmachine_id         int not null,"
        //        + "    xsite_id            int not null,"
        //        + "    amount              decimal(6,2) not null,"
        //        + "    change_amt          decimal(6,2) not null,"
        //        + "    is_loan             bit default 0,"
        //        + "    is_petty            bit default 0,"
        //        + "    is_allowance        bit default 0,"
        //        + "    notes               text,"
        //        + "    receipt_date        date,"
        //        + "    xemployee_out_id    int,"
        //        + "    stamp               timestamp,"
        //        + "    constraint xpetty_pk primary key (xpetty_id),"
        //        + "    constraint xpetty_xemployee_fk foreign key (xemployee_in_id) references xemployee (xemployee_id),"
        //        + "    constraint xpetty_xemployee_fk2 foreign key (xemployee_out_id) references xemployee (xemployee_id),"
        //        + "    constraint xpetty_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),"
        //        + "    constraint xpetty_xsite_fk foreign key (xsite_id) references xsite (xsite_id)"
        //        + ")",
        //        "create table xpettyitem"
        //        + "("
        //        + "    xpettyitem_id       int not null auto_increment,"
        //        + "    xpetty_id           int not null,"
        //        + "    xpettycategory_id   int not null,"
        //        + "    amount              decimal(6,2) not null,"
        //        + "    stamp               timestamp,"
        //        + "    constraint xpettyitem_pk primary key (xpettyitem_id),"
        //        + "    constraint xpettyitem_xpetty_fk foreign key (xpetty_id) references xpetty (xpetty_id) on delete cascade,"
        //        + "    constraint xpettyitem_xpettycategory_fk foreign key (xpettycategory_id) references xpettycategory (xpettycategory_id)"
        //        + ")",
        //        // 43-> 44
        //        "alter table xpetty modify xmachine_id int null",
        //        "alter table xpetty add change_amt decimal(6,2) not null default 0",
        //        "alter table xpetty add balance decimal(6,2) not null default 0",
        //        "create table xcashdrawn"
        //        +"("
        //        +"     xcashdrawn_id       int not null auto_increment,"
        //        +"     cur_date            date not null,"
        //        +"     cash_drawn          decimal(6,2) not null,"
        //        +"     add_monies          decimal(6,2) not null default 0.0,"
        //        +"     notes               text,"
        //        +"     stamp               timestamp,"
        //        +"     constraint xcashdrawn_pk primary key (xcashdrawn_id)"
        //        + ")",
        //        "alter table xpetty drop balance",
        //        "alter table xpettyitem drop foreign key xpettyitem_xpetty_fk",
        //        "alter table xpettyitem add constraint xpettyitem_xpetty_fk foreign key (xpetty_id) references xpetty (xpetty_id) on delete cascade",
        // 44-> 45
//        "alter table xpetty drop foreign key xpetty_xmachine_fk",
//        "alter table xpetty drop foreign key xpetty_xsite_fk",
//        "alter table xpetty drop xmachine_id",
//        "alter table xpetty drop xsite_id",
//        "alter table xpettyitem add xmachine_id int null",
//        "alter table xpettyitem add xsite_id int null",
//        "alter table xpettyitem add constraint xpettyitem_xsite_fk foreign key (xsite_id) references xsite (xsite_id)",
//        "alter table xpettyitem add constraint xpettyitem_xmachine_fk foreign key (xmachine_id) references xsite (xmachine_id)",
//        "insert into sheet(sheetname,parent_id) select 'Petty Report',sheet_id from sheet where sheetname='REPORTS' "
//        + "and not exists(select sheet_id from sheet where sheetname='Petty Report')",
//        "insert into reportgroup(sheetgroup_id,sheet_id) "
//        + "       select s.sheet_id,ss.sheet_id "
//        + "         from sheet s,sheet ss "
//        + "   where s.sheetname='BANKING' "
//        + "     and ss.sheetname='Petty Report' "
//        + "     and not exists(select * from reportgroup where sheet_id=(select sheet_id from sheet where sheetname='Petty Report'))"
        // 45-> 46
        "alter table xpetty add balance decimal(11,2)",
        "alter table xcashdrawn add balance decimal(11,2)",
        "alter table xcashdrawn modify cash_drawn decimal(10,2)",
        "alter table xcashdrawn modify add_monies decimal(10,2)",
        "insert into sheet(sheetname,parent_id) select 'Cash Turnover',sheet_id from sheet where sheetname='REPORTS' "
        + "and not exists(select sheet_id from sheet where sheetname='Cash Turnover')",
        "insert into reportgroup(sheetgroup_id,sheet_id) "
        + "       select s.sheet_id,ss.sheet_id "
        + "         from sheet s,sheet ss "
        + "   where s.sheetname='BANKING' "
        + "     and ss.sheetname='Cash Turnover' "
        + "     and not exists(select * from reportgroup where sheet_id=(select sheet_id from sheet where sheetname='Cash Turnover'))",
        // 48->49
        "alter table xcashdrawn modify cur_date datetime",
        "alter table xpetty modify issue_date datetime",
        "alter table xpetty modify receipt_date datetime",
        "alter table xpetty modify amount decimal(10,2)",
        "alter table xpetty modify change_amt decimal(10,2)",
        "alter table xpettyitem modify amount decimal(10,2)",
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
        return props.getProperty("ftpURL", "ec2-23-22-145-131.compute-1.amazonaws.com");
    }

    public static String getFtpPath() {
        return props.getProperty("ftpPath", "/root/backups/");
    }

    public static String getFtpLogin() {
        return props.getProperty("ftpLogin", "jaco");
    }

    public static String getFtpPassword() {
        return props.getProperty("ftpLogin", "jaco84oliver");
    }

    public static boolean needBackup() {
        return props.getProperty("doBackup","yes").equals("yes");
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
