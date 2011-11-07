/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    private static String[] createLocalDBsqls = new String[]{
        "create table profile "
        + "("
        + "    profile_id   int not null identity,"
        + "    first_name   varchar(32) not null,"
        + "    last_name    varchar(32) not null,"
        + "    address1     varchar(80) not null,"
        + "    address2     varchar(80),"
        + "    city         varchar(48),"
        + "    state        varchar(12),"
        + "    zip_code     varchar(12),"
        + "    phone        varchar(12),"
        + "    cell_phone   varchar(12),"
        + "    email        varchar(80),"
        + "    photo        object,"
        + "    constraint profile_pk primary key (profile_id)"
        + ")",
        "create table userprofile"
        + "("
        + "    profile_id   int not null,"
        + "    fax          varchar(12),"
        + "    webaddress   varchar(80),"
        + "    office_hours varchar(48),"
        + "    salesperson  smallint,"
        + "    manager      smallint,"
        + "    login        varchar(16),"
        + "    pwdmd5       varchar(32),"
        + "    constraint userprofile_pk primary key (profile_id),"
        + "    constraint userprofile_profile_fk foreign key (profile_id) references profile on delete cascade"
        + ")",
        "create table clientprofile"
        + "("
        + "    profile_id        int not null,"
        + "    salesperson_id    int,"
        + "    birthday          date,"
        + "    source_type       varchar(10),"
        + "    source_descr      varchar(255),"
        + "    sales_potential   int,"
        + "    constraint clientprofile_pk primary key (profile_id),"
        + "    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile,"
        + "    constraint clientprofile_profile_fk foreign key (profile_id) references profile on delete cascade"
        + ")",
        "create table boperator"
        + "("
        + "    profile_id        int not null,"
        + "    clocksheets       smallint default 0,"
        + "    clocknumber       varchar(32),"
        + "    contractnumber    varchar(32),"
        + "    loan_amount       int,"
        + "    loan_balance      int,"
        + "    loan_repayment    int,"
        + "    rate              smallint,"
        + "    mon_normaltime    smallint default 8,"
        + "    mon_overtime      smallint default 0,"
        + "    mon_doubletime    smallint default 0,"
        + "    tue_normaltime    smallint default 8,"
        + "    tue_overtime      smallint default 0,"
        + "    tue_doubletime    smallint default 0,"
        + "    wen_normaltime    smallint default 8,"
        + "    wen_overtime      smallint default 0,"
        + "    wen_doubletime    smallint default 0,"
        + "    thu_normaltime    smallint default 8,"
        + "    thu_overtime      smallint default 0,"
        + "    thu_doubletime    smallint default 0,"
        + "    fri_normaltime    smallint default 8,"
        + "    fri_overtime      smallint default 0,"
        + "    fri_doubletime    smallint default 0,"
        + "    sat_normaltime    smallint default 8,"
        + "    sat_overtime      smallint default 0,"
        + "    sat_doubletime    smallint default 0,"
        + "    sun_normaltime    smallint default 8,"
        + "    sun_overtime      smallint default 0,"
        + "    sun_doubletime    smallint default 0,"
        + "    tot_norm_hours    smallint default 40,"
        + "    tot_overtime      smallint default 0,"
        + "    tot_doubletime    smallint default 0,"
        + "    contract_term     smallint default 12," // could be 1|3|6|12|0
        + "    contract_start    date,"
        + "    contract_end      date,"
        + "    constraint boperator_pk primary key (profile_id),"
        + "    constraint boperator_profile_spers_fk foreign key (salesperson_id) references profile,"
        + ");",
        "create  view v_userprofile as "
        + "select p.profile_id,"
        + "       p.first_name,"
        + "       p.last_name,"
        + "       p.address1,"
        + "       p.address2,"
        + "       p.city,"
        + "       p.state,"
        + "       p.zip_code,"
        + "       p.phone as office_phone,"
        + "       p.cell_phone,"
        + "       u.fax,"
        + "       p.email,"
        + "       u.webaddress,"
        + "       u.office_hours,"
        + "       u.salesperson,"
        + "       u.manager"
        + "  from profile p, userprofile u"
        + " where u.profile_id = p.profile_id;",
        "create view v_clientprofile as "
        + "       select p.profile_id,"
        + "       p.first_name,"
        + "       p.last_name,"
        + "       c.birthday,"
        + "       p.address1,"
        + "       p.address2,"
        + "       p.city,"
        + "       p.state,"
        + "       p.zip_code,"
        + "       p.phone as home_phone,"
        + "       p.cell_phone,"
        + "       p.email,"
        + "       c.source_type,"
        + "       c.source_descr,"
        + "       c.sales_potential"
        + "  from profile p, clientprofile c"
        + " where c.profile_id = p.profile_id",
        "insert into profile(profile_id,first_name,last_name,address1) values(1,'Admin','Adminson','not known');",
        "insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,0,1,'admin','admin' from profile where first_name='Admin';",
        "insert into profile(profile_id,first_name,last_name,address1) values(2,'Salesman','Sale','not known');",
        "insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,1,0,'sale','sale' from profile where first_name='Salesman';"
    };
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
}
