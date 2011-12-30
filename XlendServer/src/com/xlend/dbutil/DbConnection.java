package com.xlend.dbutil;

import com.xlend.XlendServer;
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

    private static final int DB_VERSION_ID = 11;
    public static final String DB_VERSION = "0.11";
    private static boolean isFirstTime = true;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas_hsqldb.sql");
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",
        //        //version 0.2->0.3
        //        "alter table xclient add description varchar(255)",
        //        "create cached table xcontract"
        //        + "("
        //        + "    xcontract_id   int generated by default as identity (start with 1),"
        //        + "    contractref    varchar(32) not null,"
        //        + "    description    varchar(255),"
        //        + "    xclient_id     int not null,"
        //        + "    picture_id     int,"
        //        + "    constraint xcontract_pk primary key (xcontract_id),"
        //        + "    constraint xcontract_xclient_fk foreign key (xclient_id) references xclient,"
        //        + "    constraint xcontact_picture_fk foreign key (picture_id) references picture"
        //        + ")",
        //        //version 0.3->0.4
        //        "alter table xcontract drop constraint xcontact_picture_fk",
        //        "alter table xcontract drop picture_id",
        //        "create cached table xcontractpage"
        //        + "("
        //        + "    xcontractpage_id   int generated by default as identity (start with 1),"
        //        + "    xcontract_id       int not null,"
        //        + "    pagenum            int,"
        //        + "    description        varchar(64),"
        //        + "    pagescan           other,"
        //        + "    constraint xcontractpage_pk primary key (xcontractpage_id),"
        //        + "    constraint xcontractpage_xcontract_fk foreign key (xcontract_id) references xcontract on delete cascade"
        //        + ")",
        //        //version 0.4->0.5
        //        "create cached table xquotation ("
        //        + "    xquotation_id      int generated by default as identity (start with 1),"
        //        + "    xclient_id         int not null,"
        //        + "    rfcnumber          varchar(32) not null,"
        //        + "    constraint xquotation_pk primary key (xquotation_id),"
        //        + "    constraint xquotation_xclient_fk foreign key (xclient_id) references xclient"
        //        + ")",
        //        "create cached table xquotationpage ("
        //        + "    xquotationpage_id  int generated by default as identity (start with 1),"
        //        + "    xquotation_id      int not null,"
        //        + "    pagenum            int,"
        //        + "    description        varchar(64),"
        //        + "    pagescan           other,"
        //        + "    constraint xquotationpage_pk primary key (xquotationpage_id),"
        //        + "    constraint xquotationpage_xquotation_fk foreign key (xquotation_id) references xquotation on delete cascade"
        //        + ")",
        //        "create cached table xorder ("
        //        + "    xorder_id          int generated by default as identity (start with 1),"
        //        + "    xclient_id         int not null,"
        //        + "    xcontract_id       int,"
        //        + "    xquotation_id      int,"
        //        + "    vatnumber          varchar(32) not null,"
        //        + "    regnumber          varchar(32) not null,"
        //        + "    ordernumber        varchar(32) not null,"
        //        + "    orderdate          date not null,"
        //        + "    contactname        varchar(255),"
        //        + "    contactphone       varchar(32),"
        //        + "    contactfax         varchar(32),"
        //        + "    deliveryaddress    varchar(255) not null,"
        //        + "    invoiceaddress     varchar(255) not null,"
        //        + "    constraint xorder_pk primary key (xorder_id),"
        //        + "    constraint xorder_xclient_fk foreign key (xclient_id) references xclient,"
        //        + "    constraint xorder_xquotation_fk  foreign key (xquotation_id) references xquotation,"
        //        + "    constraint xorder_xcontract foreign key (xcontract_id) references xcontract"
        //        + ")",
        //        "create cached table xorderitem ("
        //        + "    xorderitem_id     int generated by default as identity (start with 1),"
        //        + "    xorder_id         int not null,"
        //        + "    itemnumber        varchar(16) not null,"
        //        + "    materialnumber    varchar(16),"
        //        + "    description       varchar(255) not null,"
        //        + "    quantity          int not null,"
        //        + "    measureitem       varchar(8),"
        //        + "    priceperone       decimal(10,2) not null,"
        //        + "    constraint xorderitem_pk primary key (xorderitem_id),"
        //        + "    constraint xoreritem_xorder_fk foreign key (xorder_id) references xorder on delete cascade"
        //        + ")",
        //        "create cached table xorderpage ("
        //        + "     xorderpage_id     int generated by default as identity (start with 1),"
        //        + "     xorder_id         int not null,"
        //        + "     pagenum           int,"
        //        + "     description       varchar(64),"
        //        + "     pagescan          other,"
        //        + "     constraint xorderpage_pk primary key (xorderpage_id),"
        //        + "     constraint xorderpage_xorder_fk foreign key (xorder_id) references xorder on delete cascade"
        //        + ")",
        //        //version 0.5->0.6
        //        "alter table xorderitem add machinetype varchar(64)",
        //        "alter table xorderitem add deliveryreq date",
        //        "alter table xorderitem add delivery date",
        //        //version 0.6->0.7
        //        "alter table xsite add xorder_id int",
        //        "alter table xsite add constraint xsite_xorder_fk foreign key (xorder_id) references xorder on delete cascade",
        //        "alter table xcontractpage add fileextension varchar(8)",
        //        "alter table xorderpage add fileextension varchar(8)",
        //        "alter table xquotationpage add fileextension varchar(8)",
        //        //version 0.7->0.8
        //        "create cached table xposition"
        //        + "("
        //        + "    xposition_id    int generated by default as identity (start with 1),"
        //        + "    pos        varchar(64) not null,"
        //        + "    constraint xposition_pk primary key (xposition_id)"
        //        + ")",
        //        "create cached table xemployee"
        //        + "("
        //        + "    xemployee_id    int generated by default as identity (start with 1),"
        //        + "    clock_num       varchar(8)  not null,"
        //        + "    first_name      varchar(16) not null,"
        //        + "    sur_name        varchar(16) not null,"
        //        + "    nick_name       varchar(16) not null,"
        //        + "    id_num          varchar(16) not null,"
        //        + "    clocksheets     smallint default 1,"
        //        + "    phone0_num      varchar(16) not null,"
        //        + "    phone1_num      varchar(16) not null,"
        //        + "    phone2_num      varchar(16) not null,"
        //        + "    relation1       varchar(16),"
        //        + "    relation2       varchar(16),"
        //        + "    address         varchar(128),"
        //        + "    contract_len    int not null,"
        //        + "    contract_start  date not null,"
        //        + "    contract_end    date,"
        //        + "    rate            int not null,"
        //        + "    xposition_id    int,"
        //        + "    photo          other,"
        //        + "    constraint xemployee_pk primary key (xemployee_id),"
        //        + "    constraint xemployeer_xposition_fk foreign key (xposition_id) references xposition"
        //        + ")",
        //        "create cached table xtimesheet"
        //        + "("
        //        + "    xtimesheet_id int generated by default as identity (start with 1),"
        //        + "    xemployee_id  int not null,"
        //        + "    xclient_id    int not null,"
        //        + "    xsite_id      int not null,"
        //        + "    weekend       date,"
        //        + "    clocksheet    smallint default 1,"
        //        + "    image         other,"
        //        + "    constraint xtimesheet_pk primary key (xtimesheet_id),"
        //        + "    constraint xtimesheet_xemployee_fk foreign key (xemployee_id) references xemployee on delete cascade,"
        //        + "    constraint xtimesheet_xclient_fk foreign key (xclient_id) references xclient,"
        //        + "    constraint xtimesheet_xsite_fk foreign key (xsite_id) references xsite"
        //        + ")",
        //        "create cached table xwage"
        //        + "("
        //        + "    xwage_id       int generated by default as identity (start with 1),"
        //        + "    xtimesheet_id  int not null,"
        //        + "    day            date,"
        //        + "    normaltime     double,"
        //        + "    overtime       double,"
        //        + "    doubletime     double,"
        //        + "    stoppeddetails varchar(128),"
        //        + "    constraint xwage_pk primary key (xwage_id),"
        //        + "    constraint xwage_xtimesheet_fk foreign key (xtimesheet_id) references xtimesheet on delete cascade"
        //        + ")",
        //        //version 0.8->0.9
        //        "alter table xquotation add received date",
        //        "alter table xquotation add deadline date",
        //        "alter table xquotation add responded date",
        //        "alter table xquotation add responsedby varchar(12)",
        //        "alter table xquotation add responsecmnt varchar(128)",
        //        "alter table xquotation add responder_id  int",
        //        "alter table xquotation add responsedoc other",
        //        "alter table xquotation add constraint xquotation_profile_fk foreign key (responder_id) references userprofile",
        //        "alter table xorder drop vatnumber",
        //        "alter table xorder drop regnumber",
        //        "alter table xtimesheet drop constraint xtimesheet_xclient_fk",
        //        "alter table xtimesheet drop xclient_id",
        //        "alter table xtimesheet add xorder_id int",
        //        "alter table xtimesheet add constraint xtimesheet_xorder_fk foreign key (xorder_id) references xorder",
        //        "alter table xwage add deduction double",
        //        //version 09->10
        "alter table xorderitem drop constraint XORDERITEM_XMACHTYPE_FK",
        "alter table xmachtype drop constraint xmachtype_xmachtype_fk",
        "alter table xmachine drop constraint xmachine_xmachtype_fk",
        "alter table xmachine drop constraint xmachine_xmachtype_fk2",
        //        "delete from xmachtype",
        //        "drop table xlicensestat",
        //        "drop table xmachine",
        "drop table xmachtype",
        "create cached table xmachtype"
        + "("
        + "    xmachtype_id     int generated by default as identity (start with 1),"
        + "    machtype         varchar(32) not null,"
        + "    parenttype_id    int,"
        + "    classify         char(1),"
        + "    constraint xmachtype_pk primary key (xmachtype_id),"
        + "    constraint xmachtype_xmachtype_fk foreign key (parenttype_id) references xmachtype"
        + ")",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(1,'Buldozer',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(2,'TLB',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(3,'Roller',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(4,'Excavator',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(5,'Bobcat',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(6,'Grader',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(7,'Other',null,'M')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(8,'6M TIPPER',null,'T')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(9,'10M TIPPER',null,'T')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(10,'WATERCARTS',null,'T')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(11,'TATA NOVUS 5542',null,'L')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(12,'FORD LOUISVILLE',null,'L')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(13,'INTERNATIONAL S-LINE',null,'L')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(14,'SINGLE AXLE MERCEDES',null,'L')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(15,'VOLVO F12',null,'L')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(16,'PICK-UP',null,'P')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(17,'BREAK-DOWN',null,'P')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(18,'PANELVAN',null,'P')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(19,'BUS',null,'P')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(20,'MINI BUS',null,'P')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(21,'LAND ROVER',null,'V')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(22,'SEDAN',null,'V')",
        "insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(23,'BIKE',null,'V')",
        "insert into xmachtype (machtype,parenttype_id) values('D31', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('D41', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('D4H', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('D65', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('D6H', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('D6R', 1)",
        "insert into xmachtype (machtype,parenttype_id) values('428B 4X4',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428B 4X2',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428C 4X4',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428C 4X2',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428E 4X4',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428E HAMMER',2)",
        "insert into xmachtype (machtype,parenttype_id) values('428E 4X2',2)",
        "insert into xmachtype (machtype,parenttype_id) values('422E 4X4',2)",
        "insert into xmachtype (machtype,parenttype_id) values('422E 4X2',2)",
        "insert into xmachtype (machtype,parenttype_id) values('434E 4X4',2)",
        "insert into xmachtype (machtype,parenttype_id) values('434E HAMMER',2)",
        "insert into xmachtype (machtype,parenttype_id) values('PADFOOT ROLLER',3)",
        "insert into xmachtype (machtype,parenttype_id) values('SMOOTH DRUM ROLLER',3)",
        "insert into xmachtype (machtype,parenttype_id) values('2.5TON ROLLER',3)",
        "insert into xmachtype (machtype,parenttype_id) values('PNEUMATIC ROLLER',3)",
        "insert into xmachtype (machtype,parenttype_id) values('320 CAT',4)",
        "insert into xmachtype (machtype,parenttype_id) values('322 CAT',4)",
        "insert into xmachtype (machtype,parenttype_id) values('322 CAT HAMMER',4)",
        "insert into xmachtype (machtype,parenttype_id) values('325 CAT',4)",
        "insert into xmachtype (machtype,parenttype_id) values('330 CAT',4)",
        "insert into xmachtype (machtype,parenttype_id) values('PC200',4)",
        "insert into xmachtype (machtype,parenttype_id) values('PC300',4)",
        "insert into xmachtype (machtype,parenttype_id) values('216B',5)",
        "insert into xmachtype (machtype,parenttype_id) values('EXCAVATING ATTACHMENT',5)",
        "insert into xmachtype (machtype,parenttype_id) values('BROOM ATTACHMENT',5)",
        "insert into xmachtype (machtype,parenttype_id) values('HAMMER ATTACHMENT',5)",
        "insert into xmachtype (machtype,parenttype_id) values('140H',6)",
        "insert into xmachtype (machtype,parenttype_id) values('140G',6)",
        "insert into xmachtype (machtype,parenttype_id) values('TRACTOR WITH SLASHER',7)",
        "insert into xmachtype (machtype,parenttype_id) values('TELESCOPIC HANDLER',7)",
        "insert into xmachtype (machtype,parenttype_id) values('BELL 225A LOGGER',7)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES 1414',8)",
        "insert into xmachtype (machtype,parenttype_id) values('TOYOTA 6000',8)",
        "insert into xmachtype (machtype,parenttype_id) values('TATA LPT',8)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES ATEGO',8)",
        "insert into xmachtype (machtype,parenttype_id) values('OTHER',8)",
        "insert into xmachtype (machtype,parenttype_id) values('TATA NOVUS 3434',9)",
        "insert into xmachtype (machtype,parenttype_id) values('MITCHUBISHI FUSO',9)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES ACTROS',9)",
        "insert into xmachtype (machtype,parenttype_id) values('SAMAG',9)",
        "insert into xmachtype (machtype,parenttype_id) values('BLUNT NOSE MERCEDES',9)",
        "insert into xmachtype (machtype,parenttype_id) values('NISSAN CW46',9)",
        "insert into xmachtype (machtype,parenttype_id) values('OTHER',9)",
        "insert into xmachtype (machtype,parenttype_id) values('6000L',10)",
        "insert into xmachtype (machtype,parenttype_id) values('12000L',10)",
        "insert into xmachtype (machtype,parenttype_id) values('HILUX',16)",
        "insert into xmachtype (machtype,parenttype_id) values('HILUX HIGH RIDER',16)",
        "insert into xmachtype (machtype,parenttype_id) values('NISSAN 1400',16)",
        "insert into xmachtype (machtype,parenttype_id) values('OTHER',16)",
        "insert into xmachtype (machtype,parenttype_id) values('TOYOTA DYNA',17)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES VITO',18)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES SPRINTER',18)",
        "insert into xmachtype (machtype,parenttype_id) values('9 SEATER SPRINTER',19)",
        "insert into xmachtype (machtype,parenttype_id) values('12 SEATER SPRINTER',19)",
        "insert into xmachtype (machtype,parenttype_id) values('VOLKSWAGEN MICROBUS',20)",
        "insert into xmachtype (machtype,parenttype_id) values('KIA PREGIO',20)",
        "insert into xmachtype (machtype,parenttype_id) values('DEFENDER 100',21)",
        "insert into xmachtype (machtype,parenttype_id) values('DEFENDER 110',21)",
        "insert into xmachtype (machtype,parenttype_id) values('DISCOVERY 2',21)",
        "insert into xmachtype (machtype,parenttype_id) values('BMW 318',22)",
        "insert into xmachtype (machtype,parenttype_id) values('BMW 328',22)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES C220',22)",
        "insert into xmachtype (machtype,parenttype_id) values('MERCEDES C280',22)",
        "insert into xmachtype (machtype,parenttype_id) values('AUDI',22)",
        "insert into xmachtype (machtype,parenttype_id) values('HONDA',23)",
        "insert into xmachtype (machtype,parenttype_id) values('BMW',23)",
        "alter table xorderitem add constraint XORDERITEM_XMACHTYPE_FK foreign key (xmachtype_id) references xmachtype",
        "alter table xmachtype add constraint xmachtype_xmachtype_fk foreign key (parenttype_id) references xmachtype",
        //        "create cached table xmachine"
        //        + "("
        //        + "    xmachine_id     int generated by default as identity (start with 1),"
        //        + "    tmvnr           varchar(3) not null,"
        //        + "    xmachtype_id    int not null,"
        //        + "    xmachtype2_id   int,"
        //        + "    reg_nr          varchar(16),"
        //        + "    expdate         date,"
        //        + "    vehicleid_nr    varchar(16),"
        //        + "    engine_nr       varchar(16),"
        //        + "    chassis_nr      varchar(32),"
        //        + "    classify        char(1),"
        //        + "    insurance_nr    varchar(16),"
        //        + "    insurance_tp    varchar(16),"
        //        + "    insurance_amt   int,"
        //        + "    deposit_amt     int,"
        //        + "    contract_fee    int,"
        //        + "    monthly_pay     int,"
        //        + "    paystart        date,"
        //        + "    payend          date,"
        //        + "    photo           other,"
        //        + "    constraint xmachine_pk primary key (xmachine_id),"
        //        + "    constraint xmachine_xmachtype_fk foreign key (xmachtype_id) references xmachtype,"
        //        + "    constraint xmachine_xmachtype_fk2 foreign key (xmachtype2_id) references xmachtype"
        //        + ")",
        "alter table xmachine add constraint xmachine_xmachtype_fk foreign key (xmachtype_id) references xmachtype",
        "alter table xmachine add constraint xmachine_xmachtype_fk2 foreign key (xmachtype2_id) references xmachtype",
        //version 09->10
        "alter table xorderitem drop materialnumber",
        "alter table xorderitem drop description",
        "alter table xorderitem drop machinetype",
        "alter table xorderitem add xmachtype_id int",
        "alter table xorderitem add totalvalue decimal(10,2)",
        "alter table xorderitem add constraint xorderitem_xmachtype_fk foreign key (xmachtype_id) references xmachtype",
        "drop table xmachineonsite ",
        "create table xmachineonsite "
        + "("
        + "    xmachineonsate_id int generated by default as identity (start with 1),"
        + "    xsite_id          int not null,"
        + "    xmachine_id       int not null,"
        + "    xemployee_id      int,"
        + "    estdate           date not null,"
        + "    deestdate         date,"
        + "    constraint xmachineonsite_pk primary key (xmachineonsate_id),"
        + "    constraint xmachineonsite_xmachine_fk foreign key (xmachine_id) references xmachine on delete cascade,"
        + "    constraint xmachineonsite_xsite_fk foreign key (xsite_id) references xsite,"
        + "    constraint xmachineonsite_xemployee_fk foreign key (xemployee_id) references xemployee "
        + ")",
        "create cached table xsupplier"
        + "("
        + "    xsupplier_id     int generated by default as identity (start with 1),"
        + "    companyname      varchar(64)  not null,"
        + "    contactperson    varchar(64),"
        + "    productdesc      varchar(128) not null,"
        + "    phone            varchar(64),"
        + "    fax              varchar(32),"
        + "    cell             varchar(64),"
        + "    email            varchar(128),"
        + "    vatnr            varchar(32),"
        + "    company_regnr    varchar(32),"
        + "    address          varchar(255),"
        + "    constraint xsupplier_pk primary key (xsupplier_id)"
        + ")",
        "create cached table xpaidmethod"
        + "("
        + "    xpaidmethod_id   int generated by default as identity (start with 1),"
        + "    method           varchar(32) not null,"
        + "    constraint xpaidmethod_pk primary key (xpaidmethod_id)"
        + ")",
        "insert into xpaidmethod values (1, 'Cash Purchase')",
        "insert into xpaidmethod values (2, 'Cheque')",
        "insert into xpaidmethod values (3, 'Electronic Bank Transfer')",
        "insert into xpaidmethod values (4, 'On Account (not Paid)')",
        "insert into xpaidmethod values (5, 'On Account (Paid)')",
        "create cached table xdieselpchs"
        + "("
        + "    xdieselpchs_id   int generated by default as identity (start with 1),"
        + "    xsupplier_id     int not null,"
        + "    purchased        date not null,"
        + "    authorizer_id    int,"
        + "    amount_liters    int not null,"
        + "    amount_rands     int not null,"
        + "    paidby_id        int,"
        + "    xpaidmethod_id   int not null,"
        + "    constraint xdieselpchs_pk primary key (xdieselpchs_id),"
        + "    constraint xdieselpchs_xsupplier_fk foreign key (xsupplier_id) references xsupplier,"
        + "    constraint xdieselpchs_xemployee_fk foreign key (authorizer_id) references xemployee,"
        + "    constraint xdieselpchs_xemployee_fk2 foreign key (paidby_id) references xemployee,"
        + "    constraint xdieselpchs_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod"
        + ")",
        "create cached table xdieselcard"
        + "("
        + "    xdieselcard_id    int generated by default as identity (start with 1),"
        + "    carddate          date not null,"
        + "    xmachine_id       int not null,"
        + "    operator_id       int not null,"
        + "    xsite_id          int not null,"
        + "    amount_liters     int,"
        + "    personiss_id      int,"
        + "    constraint xdieselcard_pk primary key (xdieselcard_id),"
        + "    constraint xdieselcard_xmachine_fk foreign key (xmachine_id) references xmachine,"
        + "    constraint xdieselcard_xemployee_fk foreign key (operator_id) references xemployee,"
        + "    constraint xdieselcard_xsite_fk foreign key (xsite_id) references xsite,"
        + "    constraint xdieselcard_xemployee_fk2 foreign key (personiss_id) references xemployee"
        + ")",
        "create cached table xconsume"
        + "("
        + "    xconsume_id       int generated by default as identity (start with 1),"
        + "    xsupplier_id      int not null,"
        + "    xmachine_id       int not null,"
        + "    requester_id      int not null,"
        + "    invoicedate       date,"
        + "    authorizer_id     int,"
        + "    collector_id      int,"
        + "    description       varchar(255),"
        + "    partnumber        varchar(32),"
        + "    amount_liters     int,"
        + "    amount_rands      int,"
        + "    payer_id          int,"
        + "    xpaidmethod_id    int not null,"
        + "    chequenumber      varchar(32),"
        + "    constraint xconsume_pk primary key (xconsume_id),"
        + "    constraint xconsume_xsupplier_fk foreign key (xsupplier_id) references xsupplier,"
        + "    constraint xconsume_xmachine_fk foreign key (xmachine_id) references xmachine,"
        + "    constraint xconsume_xemployee_fk foreign key (requester_id) references xemployee,"
        + "    constraint xconsume_xemployee_fk2 foreign key (authorizer_id) references xemployee,"
        + "    constraint xconsume_xemployee_fk3 foreign key (collector_id) references xemployee, "
        + "    constraint xconsume_xemployee_fk4 foreign key (payer_id) references xemployee,"
        + "    constraint xconsume_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod"
        + ")"
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
        } catch (Exception e) {
            XlendServer.log(e);
        }
        if (isFirstTime && props.getProperty("dbDriverName", "org.hsqldb.jdbcDriver").equals("org.hsqldb.jdbcDriver")) {
            initLocalDB(connection);
            fixLocalDB(connection);
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

    private static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
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

    public static void closeConnection(Connection connection) throws SQLException {
        connection.commit();
        connection.close();
        connection = null;
    }

    private static String getCurDir() {
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
