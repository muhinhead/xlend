/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xlend.dbutil;

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
    private static Connection connection = null;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = new String[]{
        "create table clientgroup"
        + "("
        + "    clientgroup_id int not null identity,"
        + "    groupname      varchar(80) not null,"
        + "    constraint clientgroup_pk primary key (clientgroup_id)"
        + ");",
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
        + "    constraint profile_pk primary key (profile_id)"
        + ");",
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
        + ");",
        "create table clientprofile"
        + "("
        + "    profile_id        int not null,"
        + "    clientgroup_id    int not null,"
        + "    salesperson_id    int,"
        + "    birthday          date,"
        + "    spouse_first_name varchar(32), "
        + "    spouse_last_name  varchar(32), "
        + "    spouse_birthday   date,"
        + "    spouse_email      varchar(80),"
        + "    source_type       varchar(10),"
        + "    source_descr      varchar(255),"
        + "    sales_potential   integer,"
        + "    constraint clientprofile_pk primary key (profile_id),"
        + "    constraint clientprofile_clientgroup_fk foreign key (clientgroup_id) references clientgroup,"
        + "    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile,"
        + "    constraint clientprofile_profile_fk foreign key (profile_id) references profile on delete cascade"
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
        + "       u.manager    "
        + "  from profile p, userprofile u"
        + " where u.profile_id = p.profile_id;",
        "create  view v_clientprofile as "
        + "select p.profile_id,"
        + "       p.first_name,"
        + "       p.last_name,"
        + "       c.birthday,"
        + "       c.spouse_first_name,"
        + "       c.spouse_last_name,"
        + "       c.spouse_birthday,"
        + "       p.address1,"
        + "       p.address2,"
        + "       p.city,"
        + "       p.state,"
        + "       p.zip_code,"
        + "       p.phone as home_phone,"
        + "       p.cell_phone,"
        + "       p.email,"
        + "       c.spouse_email,"
        + "       c.source_type,"
        + "       c.source_descr,"
        + "       c.sales_potential,"
        + "       g.groupname as clientlist"
        + "  from profile p, clientprofile c, clientgroup g"
        + " where c.profile_id = p.profile_id"
        + "   and g.clientgroup_id = c.clientgroup_id;",
        "insert into clientgroup (clientgroup_id,groupname) values(1,'Main Group');",
        "insert into profile(profile_id,first_name,last_name,address1) values(1,'Admin','Adminson','not known');",
        "insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,0,1,'admin','admin' from profile where first_name='Admin';",
        "insert into profile(profile_id,first_name,last_name,address1) values(2,'Salesman','Sale','not known');",
        "insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,1,0,'sale','sale' from profile where first_name='Salesman';"
    };

    public static Connection getConnection() {
        if (null == connection) {
            try {
                Locale.setDefault(Locale.ENGLISH);
                DriverManager.registerDriver(
                        (java.sql.Driver) Class.forName(
                        props.getProperty("dbDriverName",
                        "org.hsqldb.jdbcDriver")).newInstance());
//                        "org.postgresql.Driver")).newInstance());
//                        "oracle.jdbc.driver.OracleDriver")).newInstance());
                connection = DriverManager.getConnection(
                        props.getProperty("dbConnection",
                        "jdbc:hsqldb:file://C:/Program Files/CMCServer/DB/LacusServer"),
//                        "jdbc:postgresql://localhost:5432/postgres"),
//                        "jdbc:oracle:thin:@localhost:1521:XE"),
//                        props.getProperty("dbUser", "lacus"),
//                        props.getProperty("dbPassword", "lacus"));
                        props.getProperty("dbUser", "sa"),
                        props.getProperty("dbPassword", ""));
                connection.setAutoCommit(true);
            } catch (Exception e) {
                DbUtil.log(e);
            }
            if (isFirstTime && props.getProperty("dbDriverName", "org.hsqldb.jdbcDriver").equals("org.hsqldb.jdbcDriver")) {
                initLocalDB(connection);
//                fixLocalDB(connection);
                isFirstTime = false;
            }
        }
        return connection;
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls);
    }

    private static void sqlBatch(String[] sqls) {
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

    public static void closeConnection() throws SQLException {
        connection.commit();
        connection.close();
        connection = null;
    }
}
