       
CREAte table dbversion
(
    dbversion_id    int not null auto_increment,
    version_id      int not null,
    version         varchar(12),
    constraint dbversion_pk primary key (dbversion_id)
);

create table picture
(
    picture_id   int not null auto_increment,
    picture      mediumblob,
    stamp timestamp,
    constraint picture_pk primary key (picture_id)
);


create table profile
(
    profile_id   int not null auto_increment,
    first_name   varchar(32) not null,
    last_name    varchar(32) not null,
    address1     varchar(80) not null,
    address2     varchar(80),
    city         varchar(48),
    state        varchar(12),
    zip_code     varchar(12),
    phone        varchar(12),
    cell_phone   varchar(12),
    email        varchar(80),
    picture_id   int,
    stamp timestamp,
    constraint profile_pk primary key (profile_id),
    constraint profile_picture_fk foreign key (picture_id) references picture (picture_id)
);

create table userprofile
(
    profile_id   int not null,
    fax          varchar(12),
    webaddress   varchar(80),
    office_hours varchar(48),
    salesperson  smallint,
    manager      smallint,
    supervisor   smallint,
    login        varchar(16),
    pwdmd5       varchar(32),
    stamp timestamp,
    constraint userprofile_pk primary key (profile_id),
    constraint userprofile_profile_fk foreign key (profile_id) references profile (profile_id) on delete cascade
);

create table sheet
(
    sheet_id  int not null auto_increment,
    sheetname varchar(32) not null,
    classname varchar(64),
    parent_id int,
    stamp timestamp,
    constraint sheet_pk primary key (sheet_id)
);

create table usersheet
(
    usersheet_id int not null auto_increment,
    profile_id   int not null,
    sheet_id     int not null,
    stamp timestamp,
    constraint usersheet_pk primary key (usersheet_id),
    constraint usersheet_sheet_fk foreign key (sheet_id) references sheet (sheet_id) on delete cascade,
    constraint usersheet_user_fk foreign key (profile_id) references userprofile (profile_id) on delete cascade
);

create table reportgroup
(
    reportgroup_id int not null auto_increment,
    sheetgroup_id  int not null,
    sheet_id       int not null,
    stamp timestamp,
    constraint reportgroup_pk primary key (reportgroup_id),
    constraint reportgroup_sheet_fk foreign key (sheetgroup_id) references sheet (sheet_id) on delete cascade,
    constraint reportgroup_sheet_fk2 foreign key (sheet_id) references sheet (sheet_id) on delete cascade
);

create table clientprofile
(
    profile_id        int not null,
    salesperson_id    int,
    birthday          date,
    source_type       varchar(10), #--check (source_type in('Phonebook','Referral','Location','Others')),
    source_descr      varchar(255),
    sales_potential   int,
    stamp timestamp,
    constraint clientprofile_pk primary key (profile_id),
    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile (profile_id),
    constraint clientprofile_profile_fk foreign key (profile_id) references profile (profile_id) on delete cascade
);

create table xclient
(
    xclient_id      int not null auto_increment,
    clientcode      varchar(16) not null,
    companyname     varchar(255) not null,
    contactname     varchar(255),
    phonenumber     varchar(12),
    vatnumber       varchar(32),
    address         varchar(255),
    description     varchar(255),
    stamp timestamp,
    constraint xclient_pk primary key (xclient_id)
);

create table xcontract
(
    xcontract_id   int not null auto_increment,
    contractref    varchar(32) not null,
    description    varchar(255),
    xclient_id     int not null,
    stamp timestamp,
    constraint xcontract_pk primary key (xcontract_id),
    constraint xcontract_xclient_fk foreign key (xclient_id) references xclient (xclient_id)
);

create table xcontractpage #--implements IPage
(
    xcontractpage_id   int not null auto_increment,
    xcontract_id       int not null,
    pagenum            int,
    description        varchar(64),
    fileextension      varchar(8),
    pagescan           mediumblob,
    stamp timestamp,
    constraint xcontractpage_pk primary key (xcontractpage_id),
    constraint xcontractpage_xcontract_fk foreign key (xcontract_id) references xcontract (xcontract_id) on delete cascade
);

create table xquotation
(
    xquotation_id      int not null auto_increment,
    xclient_id         int not null,
    rfcnumber          varchar(32) not null,
    received           date,
    deadline           date,
    responded          date,
    responsedby        varchar(12),
    responsecmnt       varchar(128),
    responder_id       int,
    responsedoc        mediumblob,
    stamp timestamp,
    constraint xquotation_pk primary key (xquotation_id),
    constraint xquotation_xclient_fk foreign key (xclient_id) references xclient (xclient_id),
    constraint xquotation_profile_fk foreign key (responder_id) references userprofile (profile_id)
);

create table xquotationpage #--implements IPage
(
    xquotationpage_id  int not null auto_increment,
    xquotation_id      int not null,
    pagenum            int,
    description        varchar(64),
    fileextension      varchar(8),
    pagescan           blob,
    stamp timestamp,
    constraint xquotationpage_pk primary key (xquotationpage_id),
    constraint xquotationpage_xquotation_fk foreign key (xquotation_id) references xquotation (xquotation_id) on delete cascade
);

create table xorder
(
    xorder_id          int not null auto_increment,
    xclient_id         int not null,
    xcontract_id       int,
    xquotation_id      int,
    ordernumber        varchar(32) not null,
    orderdate          date not null,
    contactname        varchar(255),
    contactphone       varchar(32),
    contactfax         varchar(32),
    deliveryaddress    varchar(255) not null,
    invoiceaddress     varchar(255) not null,
    stamp timestamp,
    constraint xorder_pk primary key (xorder_id),
    constraint xorder_xclient_fk foreign key (xclient_id) references xclient (xclient_id),
    constraint xorder_xquotation_fk  foreign key (xquotation_id) references xquotation (xquotation_id),
    constraint xorder_xcontract foreign key (xcontract_id) references xcontract (xcontract_id)
);

create table xsite
(
    xsite_id        int not null auto_increment,
    name            varchar(32) not null,
    description     varchar(255),
    dieselsponsor   smallint default 0,
    sitetype        char(1),
    xorder_id       int,
    xorder2_id      int,
    xorder3_id      int,
    is_active       bit,
    stamp timestamp,
    constraint xsite_id primary key (xsite_id),
    constraint xsite_xorder_fk foreign key (xorder_id) references xorder (xorder_id),
    constraint xsite_xorder_fk2 foreign key (xorder2_id) references xorder (xorder_id),
    constraint xsite_xorder_fk3 foreign key (xorder3_id) references xorder (xorder_id)
);

create table xorderpage #--implements IPage
(
    xorderpage_id     int not null auto_increment,
    xorder_id         int not null,
    pagenum           int,
    description       varchar(64),
    fileextension      varchar(8),
    pagescan          blob,
    stamp timestamp,
    constraint xorderpage_pk primary key (xorderpage_id),
    constraint xorderpage_xorder_fk foreign key (xorder_id) references xorder (xorder_id) on delete cascade
);

create table xposition
(
    xposition_id    int not null auto_increment,
    pos             varchar(64) not null,
    stamp timestamp,
    constraint xposition_pk primary key (xposition_id)
);

create table xemployee
(
    xemployee_id    int not null auto_increment,
    clock_num       varchar(8)  not null,
    first_name      varchar(32) not null,
    sur_name        varchar(32) not null,
    nick_name       varchar(32) not null,
    id_num          varchar(32) not null,
    phone0_num      varchar(16) not null,
    phone1_num      varchar(16),
    phone2_num      varchar(16),
    relation1       varchar(32),
    relation2       varchar(32),
    address         varchar(256) not null,
    contract_len    int          not null, #--1/3/6/12/0
    contract_start  date         not null,
    contract_end    date,
    rate            double not null,
    xposition_id    int,
    taxnum          varchar(32),
    photo           mediumblob,
    photo2          mediumblob,
    photo3          mediumblob,
    deceased        bit,
    dismissed       bit,
    absconded       bit,
    resigned        bit,
    deceased_date   date,
    dismissed_date  date,
    absconded_date  date,
    resigned_date   date,
    wage_category   int,
    bank_name       varchar(128),
    bank_account    varchar(32),
    branch_code_name varchar(32),
    bank_name2       varchar(128),
    bank_account2    varchar(32),
    branch_code_name2 varchar(32),
    bank_name3        varchar(128),
    bank_account3     varchar(32),
    branch_code_name3 varchar(32),
    employment_start date,
    management      bit default 0,
    stamp timestamp,
    constraint xemployee_pk primary key (xemployee_id),
    constraint xemployee_xposition_fk foreign key (xposition_id) references xposition (xposition_id)
);

create table xmachtype
(
    xmachtype_id     int not null auto_increment,
    machtype         varchar(32) not null,
    parenttype_id    int,
    classify         char(1),
    stamp timestamp,
    constraint xmachtype_pk primary key (xmachtype_id)
    #--,constraint xmachtype_xmachtype_fk foreign key (parenttype_id) references xmachtype
);
create unique index xmachtype_parenttype_idx on xmachtype(machtype,parenttype_id);


create table xmachine
(
    xmachine_id     int not null auto_increment,
    tmvnr           varchar(3) not null,
    xmachtype_id    int not null,
    xmachtype2_id   int,
    reg_nr          varchar(32),
    expdate         date,
    vehicleid_nr    varchar(32),
    engine_nr       varchar(32),
    chassis_nr      varchar(32),
    classify        char(1),
    insurance_nr    varchar(32),
    insurance_tp    varchar(128),
    insurance_amt   decimal(10,2),
    deposit_amt     decimal(10,2),
    contract_fee    int,
    monthly_pay     int,
    paystart        date,
    payend          date,
    photo           mediumblob,
    stamp timestamp,
    constraint xmachine_pk primary key (xmachine_id),
    constraint xmachine_xmachtype_fk foreign key (xmachtype_id) references xmachtype (xmachtype_id)
);

create table xtimesheet
(
    xtimesheet_id int not null auto_increment,
    xemployee_id  int not null,
    xorder_id     int null,
    xsite_id      int not null,
    weekend       date,
    clocksheet    smallint default 1,
    image         blob,
    xmachine_id   int,
    stamp         timestamp,
    constraint xtimesheet_pk primary key (xtimesheet_id),
    constraint xtimesheet_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id) on delete cascade,
    constraint xtimesheet_xorder_fk foreign key (xorder_id) references xorder (xorder_id),
    constraint xtimesheet_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xtimesheet_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

create table xwage
(
    xwage_id       int not null auto_increment,
    xtimesheet_id  int not null,
    day            date,
    normaltime     double,
    overtime       double,
    doubletime     double,
    stoppeddetails varchar(128),
    tsnum          int,
    stamp          timestamp,
    constraint xwage_pk primary key (xwage_id),
    constraint xwage_xtimesheet_fk foreign key (xtimesheet_id) references xtimesheet (xtimesheet_id) on delete cascade
);

create table xwagesum
(
    xwagesum_id int not null auto_increment,
    weekend     date,
    stamp timestamp,
    constraint xwagesum_pk primary key (xwagesum_id)
);

create table xwagesumitem
(
    xwagesumitem_id int not null auto_increment,
    xwagesum_id     int,
    xemployee_id    int not null,
    xtimesheet_id   int,
    weeklywage      int,
    normaltime      double,
    overtime        double,
    doubletime      double,
    stamp timestamp,
    constraint xwagesumitem_pk primary key (xwagesumitem_id),
    constraint xwagesumitem_xwagesum_fk foreign key (xwagesum_id) references xwagesum (xwagesum_id) on delete cascade,
    constraint xwagesumitem_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id) on delete cascade,
    constraint xwagesumitem_xtimesheet_fk foreign key (xtimesheet_id) references xtimesheet (xtimesheet_id)
);

create table xlowbed
(
    xlowbed_id      int not null auto_increment,
    xmachine_id     int not null,
    driver_id       int,
    assistant_id    int,
    stamp timestamp,
    constraint xlowbed_pk primary key (xlowbed_id),
    constraint xlowbed_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xlowbed_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id),
    constraint xlowbed_xemployee_fk2 foreign key (assistant_id) references xemployee (xemployee_id)
);

create table xtrip
(
    xtrip_id      int not null auto_increment,
    xlowbed_id    int not null,
    trip_date     date not null,
    fromsite_id   int not null,
    insite_id     int,
    tosite_id     int,
    othersite     varchar(128),
    driver_id     int,
    assistant_id  int,
    trip_type     int not null,
    distance_empty  int,
    distance_loaded int,
    machine_id      int,
    withmachine_id  int,
    is_copmplete    bit,
    operator_id     int,
    stamp timestamp,
    constraint xtrip_pk primary key (xtrip_id),
    constraint xtrip_xlowbed_fk foreign key (xlowbed_id) references xlowbed (xlowbed_id) on delete cascade,
    constraint xtrip_xsite_fk foreign key (fromsite_id) references xsite (xsite_id),
    constraint xtrip_xsite_fk2 foreign key (tosite_id) references xsite (xsite_id),
    constraint xtrip_xsite_fk3 foreign key (insite_id) references xsite (xsite_id),
    constraint xtrip_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id),
    constraint xtrip_xemployee_fk2 foreign key (assistant_id) references xemployee (xemployee_id),
    constraint xtrip_xemployee_fk3 foreign key (operator_id) references xemployee (xemployee_id),
    constraint xtrip_xmachine_fk foreign key (machine_id) references xmachine (xmachine_id),
    constraint xtrip_xmachine_fk2 foreign key (withmachine_id) references xmachine (xmachine_id)
);

create table xorderitem
(
    xorderitem_id     int not null auto_increment,
    xorder_id         int not null,
    itemnumber        varchar(16) not null,
    deliveryreq       date,
    delivery          date,
    quantity          int not null,
    measureitem       varchar(16),
    priceperone       decimal(10,2) not null,
    xmachtype_id      int,
    totalvalue        decimal(10,2),
    stamp             timestamp,
    description       varchar(255),
    constraint xorderitem_pk primary key (xorderitem_id),
    constraint xoreritem_xorder_fk foreign key (xorder_id) references xorder (xorder_id) on delete cascade,
    constraint xorderitem_xmachtype_fk foreign key (xmachtype_id) references xmachtype (xmachtype_id)
);

create table xsupplier
(
    xsupplier_id     int not null auto_increment,
    companyname      varchar(64)  not null,
    contactperson    varchar(64),
    productdesc      varchar(512) not null,
    phone            varchar(64),
    fax              varchar(32),
    cell             varchar(64),
    email            varchar(128),
    vatnr            varchar(32),
    company_regnr    varchar(32),
    address          varchar(255),
    banking          varchar(255),
    credit_limit     int,
    stamp timestamp,
    constraint xsupplier_pk primary key (xsupplier_id)
);

create table xpaidmethod
(
    xpaidmethod_id   int not null auto_increment,
    method           varchar(32) not null,
    stamp timestamp,
    constraint xpaidmethod_pk primary key (xpaidmethod_id)
);

--create table xdieselpchs
--(
--    xdieselpchs_id   int not null auto_increment,
--    xsupplier_id     int not null,
--    purchased        date not null,
--    authorizer_id    int,
--    amount_liters    int not null,
--    amount_rands     decimal(10,2) not null,
--    paidby_id        int,
--    xpaidmethod_id   int not null,
--    stamp timestamp,
--    constraint xdieselpchs_pk primary key (xdieselpchs_id),
--    constraint xdieselpchs_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id),
--    constraint xdieselpchs_xemployee_fk foreign key (authorizer_id) references xemployee (xemployee_id),
--    constraint xdieselpchs_xemployee_fk2 foreign key (paidby_id) references xemployee (xemployee_id),
--    constraint xdieselpchs_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod (xpaidmethod_id)
--);

--create table xdieselcard
--(
--    xdieselcard_id    int not null auto_increment,
--    carddate          date not null,
--    xmachine_id       int not null,
--    operator_id       int not null,
--    xsite_id          int not null,
--    amount_liters     int,
--    personiss_id      int,
--    stamp timestamp,
--    constraint xdieselcard_pk primary key (xdieselcard_id),
--    constraint xdieselcard_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
--    constraint xdieselcard_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id),
--    constraint xdieselcard_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
--    constraint xdieselcard_xemployee_fk2 foreign key (personiss_id) references xemployee (xemployee_id)
--);

create table xconsume
(
    xconsume_id       int not null auto_increment,
    xsupplier_id      int not null,
    xmachine_id       int not null,
    requester_id      int not null,
    invoicedate       date,
    invoicenumber     varchar(16),
    authorizer_id     int,
    collector_id      int,
    description       varchar(255),
    partnumber        varchar(64),
    amount_liters     int,
    amount_rands      decimal(10,2),
    payer_id          int,
    xpaidmethod_id    int not null,
    chequenumber      varchar(32),
    accnum            varchar(16),
    xsite_id          int,
    stamp timestamp,
    constraint xconsume_pk primary key (xconsume_id),
    constraint xconsume_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id),
    constraint xconsume_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xconsume_xemployee_fk foreign key (requester_id) references xemployee (xemployee_id),
    constraint xconsume_xemployee_fk2 foreign key (authorizer_id) references xemployee (xemployee_id),
    constraint xconsume_xemployee_fk3 foreign key (collector_id) references xemployee (xemployee_id),
    constraint xconsume_xemployee_fk4 foreign key (payer_id) references xemployee (xemployee_id),
    constraint xconsume_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod (xpaidmethod_id),
    constraint xconsume_xsite_fk foreign key (xsite_id) references xsite (xsite_id)
);

create table xbreakdown
(
    xbreakdown_id     int not null auto_increment,
    breakdowndate     date,
    xmachine_id       int not null,
    xsite_id          int not null,
    reportedto_id     int,
    reportedby_id     int,
    attendedby_id     int,
    vehicle_id        int,
    repairdate        date,
    repaired          smallint,
    operatorfault     smallint,
    operator_id       int,
    km2site1way       int,
    hoursonjob        int,
    timeleft          int,
    timeback          int,
    stayedover        smallint,
    accomprice        int,
    invoicenumber     varchar(16),
    amount            decimal(10,2),
    stamp timestamp,
    constraint xbreakdown_pk primary key (xbreakdown_id),
    constraint xbreakdown_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xbreakdown_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xbreakdown_xemployee_fk foreign key (reportedto_id) references xemployee (xemployee_id),
    constraint xbreakdown_xemployee_fk2 foreign key (reportedby_id) references xemployee (xemployee_id),
    constraint xbreakdown_xemployee_fk3 foreign key (attendedby_id) references xemployee (xemployee_id),
    constraint xbreakdown_xmachine_fk2 foreign key (vehicle_id) references xmachine (xmachine_id),
    constraint xbreakdown_xemployee_fk4 foreign key (operator_id) references xemployee (xemployee_id)
);

create table xbreakconsume
(
    xbreakconsume_id  int not null auto_increment,
    xconsume_id       int not null,
    xbreakdown_id     int not null,
    stamp timestamp,
    constraint xbreakconsume_pk primary key (xbreakconsume_id),
    constraint xbreakconsume_xconsume_fk foreign key (xconsume_id) references xconsume (xconsume_id),
    constraint xbreakconsume_xbreakdown_fk foreign key (xbreakdown_id) references xbreakdown (xbreakdown_id) on delete cascade
);

create table xfuel
(
    xfuel_id     int not null auto_increment,
    fdate        date,
    ammount      decimal(10,2),
    liters       int,
    xsite_id     int,
    issuedby_id  int,
    issuedto_id  int,
    xsupplier_id int,
    iscache      bit,
    stamp timestamp,
    constraint xfuel_id primary key (xfuel_id),
    constraint xfuel_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xfuel_xemployee_fk foreign key (issuedby_id) references xemployee (xemployee_id),
    constraint xfuel_xemployee_fk2 foreign key (issuedto_id) references xemployee (xemployee_id),
    constraint xfuel_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)
);

create table xpayment
(
    xpayment_id  int not null auto_increment,
    xsupplier_id int not null,
    paydate      date not null,
    ammount      decimal(10,2) not null,
    paidfrom     int not null,
    paydby_id    int,
    stamp timestamp,
    constraint xpayment_pk primary key (xpayment_id),
    constraint xpayment_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id),
    constraint xpayment_xemployee_fk foreign key (paydby_id) references xemployee (xemployee_id)
);

create table xabsenteeism
(
    xabsenteeism_id int not null auto_increment,
    xemployee_id    int not null,
    absentdate      date not null,
    xsite_id        int not null,
    xmachine_id     int,
    standing        bit,
    reportedby_id   int,
    reportedto_id   int,
    notcommunicated bit,
    permgranted     bit,
    grantedby_id    int,
    reason          varchar(1024),
    medical_cond    bit,
    funeral         bit,
    family_problem  bit,
    in_jail         bit,
    pdp_expired     bit,
    license_problem bit,
    ppe_safety      bit,
    wage_dispute    bit,
    drunk_on_site   bit,
    work_accident   bit,
    no_reason       bit,
    medical_cert    bit,
    death_cert      bit,
    stamp timestamp,
    constraint xabsenteeism_pk primary key (xabsenteeism_id),
    constraint xabsenteeism_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id) on delete cascade,
    constraint xabsenteeism_xsite_fk foreign key (xsite_id) references xsite (xsite_id) on delete cascade,
    constraint xabsenteeism_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xabsenteeism_xemployee_fk2 foreign key (reportedby_id) references xemployee (xemployee_id) on delete cascade,
    constraint xabsenteeism_xemployee_fk3 foreign key (reportedto_id) references xemployee (xemployee_id) on delete cascade,
    constraint xabsenteeism_xemployee_fk4 foreign key (grantedby_id) references xemployee (xemployee_id) on delete cascade
);

--create table xissuing
--(
--    xissuing_id int not null auto_increment,
--    issueddate  date not null,
--    operator_id int not null,
--    xmachine_id int not null,
--    xsite_id    int not null,
--    time1_start  time,
--    time1_end    time,
--    time2_start  time,
--    time2_end    time,
--    time3_start  time,
--    time3_end    time,
--    time4_start  time,
--    time4_end    time,
--    time5_start  time,
--    time5_end    time,
--    time6_start  time,
--    time6_end    time,
--    time7_start  time,
--    time7_end    time,
--    hours1_start int,
--    hours1_end   int,
--    hours2_start int,
--    hours2_end   int,
--    hours3_start int,
--    hours3_end   int,
--    hours4_start int,
--    hours4_end   int,
--    hours5_start int,
--    hours5_end   int,
--    hours6_start int,
--    hours6_end   int,
--    hours7_start int,
--    hours7_end   int,
--    liters1_start int,
--    liters1_end   int,
--    liters2_start int,
--    liters2_end   int,
--    liters3_start int,
--    liters3_end   int,
--    liters4_start int,
--    liters4_end   int,
--    liters5_start int,
--    liters5_end   int,
--    liters6_start int,
--    liters6_end   int,
--    liters7_start int,
--    liters7_end   int,
--    issuedby1_start int,
--    issuedby1_end   int,
--    issuedby2_start int,
--    issuedby2_end   int,
--    issuedby3_start int,
--    issuedby3_end   int,
--    issuedby4_start int,
--    issuedby4_end   int,
--    issuedby5_start int,
--    issuedby5_end   int,
--    issuedby6_start int,
--    issuedby6_end   int,
--    issuedby7_start int,
--    issuedby7_end   int,
--    stamp timestamp,
--    constraint xissuing_pk primary key (xissuing_id),
--    constraint xissuing_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id),
--    constraint xissuing_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
--    constraint xissuing_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
--    constraint xissuing_xemployee_fk1s foreign key (issuedby1_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk1e foreign key (issuedby1_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk2s foreign key (issuedby2_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk2e foreign key (issuedby2_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk3s foreign key (issuedby3_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk3e foreign key (issuedby3_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk4s foreign key (issuedby4_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk4e foreign key (issuedby4_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk5s foreign key (issuedby5_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk5e foreign key (issuedby5_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk6s foreign key (issuedby6_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk6e foreign key (issuedby6_end) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk7s foreign key (issuedby7_start) references xemployee (xemployee_id),
--    constraint xissuing_xemployee_fk7e foreign key (issuedby7_end) references xemployee (xemployee_id)
--);

create table xtripsheet
(
    xtripsheet_id int not null auto_increment,
    tripdate      date not null,
    xlowbed_id    int not null,
    driver_id     int not null,
    authorized_id int not null,
    stamp timestamp,
    constraint xtripsheet_pk primary key (xtripsheet_id),
    constraint xtripsheet_xlowbed_fk foreign key (xlowbed_id) references xlowbed (xlowbed_id),
    constraint xtripsheet_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id),
    constraint xtripsheet_xemployee_fk2 foreign key (authorized_id) references xemployee (xemployee_id)
);

create table xtripsheetpart
(
    xtripsheetpart_id int not null auto_increment,
    xtripsheet_id     int not null,
    partdate          date not null,
    fromsite_id       int,
    tosite_id         int,
    fromplace         varchar(64),
    toplace           varchar(64),
    loaded1_id        int,
    loaded2_id        int,
    loaded1           varchar(64),
    loaded2           varchar(64),
    isempty           bit,
    timestart         time,
    timeend           time,
    kilimeters        int,
    assistant_id      int,
    stamp timestamp,
    constraint xtripsheetpart_fk primary key (xtripsheetpart_id),
    constraint xtripsheetpart_xtripsheet_fk foreign key (xtripsheet_id) references xtripsheet (xtripsheet_id) on delete cascade,
    constraint xtripsheetpart_xsite_fk foreign key (fromsite_id) references xsite (xsite_id),
    constraint xtripsheetpart_xsite_fk2 foreign key (tosite_id) references xsite (xsite_id),
    constraint xtripsheetpart_xmachine_fk foreign key (loaded1_id) references xmachine (xmachine_id),
    constraint xtripsheetpart_xmachine_fk2 foreign key (loaded2_id) references xmachine (xmachine_id),
    constraint xtripsheetpart_xemployee_fk foreign key (assistant_id) references xemployee (xemployee_id)
);

create table xaccounts
(
    xaccount_id       int not null auto_increment,
    accname           varchar(32) not null,
    accnumber         varchar(12) not null,
    bank              varchar(32) not null,
    branch            varchar(32) not null,
    stamp timestamp,
    constraint xaccount_pk primary key (xaccount_id)
);

create table xbankbalance
(
    xbankbalance_id  int not null auto_increment,
    balancedate      timestamp not null,
    totalvalue       decimal(12,2),
    stamp timestamp,
    constraint xbankbalance_pk primary key (xbankbalance_id)
);

create table xbankbalancepart
(
   xbankbalancepart_id int not null auto_increment,
   xbankbalance_id     int not null,
   xaccount_id         int not null,
   total               decimal(10,2),
   stamp timestamp,
   constraint xbankbalancepart_pk primary key (xbankbalancepart_id),
   constraint xbankbalancepart_xbankbalance_fk foreign key (xbankbalance_id) references xbankbalance (xbankbalance_id) on delete cascade,
   constraint xbankbalancepart_xaccounts_fk foreign key (xaccount_id) references xaccounts (xaccount_id)
);

create table xsitediary
(
    xsitediary_id    int not null auto_increment,
    diarydate        date not null,
    weekend          date not null,
    xsite_id         int not null,
    manager_id       int not null,
    site_foreman     varchar(32),
    site_number      varchar(32),
    stamp timestamp,
    constraint xsitediary_pk primary key (xsitediary_id),
    constraint xsitediary_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xsitediary_xemployee_fk foreign key (manager_id) references xemployee (xemployee_id)
);

create table xsitediaryitem
(
    xsitediaryitem_id  int not null auto_increment,
    xsitediary_id      int not null,
    xmachine_id        int not null,
    operator_id        int not null,
    day1value          varchar(6),
    day2value          varchar(6),
    day3value          varchar(6),
    day4value          varchar(6),
    day5value          varchar(6),
    day6value          varchar(6),
    day7value          varchar(6),
    stamp timestamp,
    constraint xsitediaryitem_pk primary key (xsitediaryitem_id),
    constraint xsitediaryitem_xsitediary_fk foreign key (xsitediary_id) references xsitediary (xsitediary_id) on delete cascade,
    constraint xsitediaryitem_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xsitediaryitem_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id)
);

create table xappforleave
(
    xappforleave_id  int not null auto_increment,
    appdate          date not null,
    applicant_id     int not null,
    is_sickleave     bit,
    is_annualleave   bit,
    is_specialleave  bit,
    is_unpaidleave   bit,
    fromdate         date,
    todate           date,
    days             int,
    is_signed        bit,
    is_approved      bit,
    approvedby_id    int,
    remarks          varchar(1024),
    stamp timestamp,
    constraint xappforleave_pk primary key (xappforleave_id),
    constraint xappforleave_xemployee_fk foreign key (applicant_id) references xemployee (xemployee_id),
    constraint xappforleave_xemployee_fk2 foreign key (approvedby_id) references xemployee (xemployee_id)
);

create table xloans
(
    xloans_id        int not null auto_increment,
    loandate         date not null,
    is_loan          bit,
    is_advance       bit,
    is_transport     bit,
    requestedby_id   int not null,
    authorizedby_id  int,
    issueddate       date,
    deductstartdate  date,
    amount           decimal(10,2) not null,
    deduction        decimal(10,2) not null,
    deducttime       int,
    is_signed        bit,
    stamp timestamp,
    constraint xloans_pk primary key (xloans_id),
    constraint xloans_xemployee_fk foreign key (requestedby_id) references xemployee (xemployee_id),
    constraint xloans_xemployee_fk2 foreign key (authorizedby_id) references xemployee (xemployee_id)
);

create table xincidents
(
    xincidents_id    int not null auto_increment,
    reportdate       date not null,
    incidentdate     date not null,
    xsite_id         int,
    blobmachines    varchar(512),
    blobpeople      varchar(512),
    location         varchar(255),
    description      varchar(1024) not null,
    damages          varchar(1024) not null,
    estimated_cost   int not null,
    reportedby_id    int,
    reportedto_id    int,
    is_signed        bit,
    is_verified      bit,
    verifiedby_id    int,
    lost_income      int,
    stamp timestamp,
    constraint xincidents_pk primary key (xincidents_id),
    constraint xincidents_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xincidents_xemployee_fk foreign key (reportedby_id) references xemployee (xemployee_id),
    constraint xincidents_xemployee_fk2 foreign key (reportedto_id) references xemployee (xemployee_id),
    constraint xincidents_xemployee_fk3 foreign key (verifiedby_id) references xemployee (xemployee_id)
);

create table xmachineincident
(
    xmachineincident_id int not null auto_increment,
    xincidents_id    int not null,
    xmachine_id      int not null,
    stamp timestamp,
    constraint xmachineincident_pk primary key (xmachineincident_id),
    constraint xmachineincident_xincidents_fk foreign key (xincidents_id) references xincidents (xincidents_id) on delete cascade,
    constraint xmachineincident_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id) on delete cascade
);

create table xemployeeincident
(
    xemployeeincident_id int not null auto_increment,
    xincidents_id        int not null,
    xemployee_id         int not null,
    stamp timestamp,
    constraint xemployeeincident_pk primary key (xemployeeincident_id),
    constraint xemployeeincident_xincidents_fk foreign key (xincidents_id) references xincidents (xincidents_id) on delete cascade,
    constraint xemployeeincident_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id) on delete cascade
);

create table xsalarylist
(
    xsalarylist_id int not null auto_increment,
    payday         date not null,
    stamp timestamp,
    constraint xsalarylist_pk primary key (xsalarylist_id)
);

create table xsalary
(
    xsalary_id     int not null auto_increment,
    xsalarylist_id int not null,
    xemployee_id   int not null,
    amount         decimal(10,2) not null,
    stamp timestamp,
    constraint xsalarylist_pk primary key (xsalary_id),
    constraint xsalary_xsalarylist_fk foreign key (xsalarylist_id) references xsalarylist (xsalarylist_id) on delete cascade,
    constraint xsalary_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id)
);

create table xopclocksheet
(
    xopclocksheet_id int not null auto_increment,
    xemployee_id     int not null,
    xsite_id         int not null,
    sheet_date       date,
    xmachine_id      int not null,
    km_start1        int,
    km_stop1         int,
    work_from1       time,
    work_to1         time,
    stand_from1      time,
    stand_to1        time,
    reason1          varchar(255),
    km_start2        int,
    km_stop2         int,
    work_from2       time,
    work_to2         time,
    stand_from2      time,
    stand_to2        time,
    reason2          varchar(255),
    km_start3        int,
    km_stop3         int,
    work_from3       time,
    work_to3         time,
    stand_from3      time,
    stand_to3        time,
    reason3          varchar(255),
    km_start4        int,
    km_stop4         int,
    work_from4       time,
    work_to4         time,
    stand_from4      time,
    stand_to4        time,
    reason4          varchar(255),
    km_start5        int,
    km_stop5         int,
    work_from5       time,
    work_to5         time,
    stand_from5      time,
    stand_to5        time,
    reason5          varchar(255),
    km_start6        int,
    km_stop6         int,
    work_from6       time,
    work_to6         time,
    stand_from6      time,
    stand_to6        time,
    reason6          varchar(255),
    km_start7        int,
    km_stop7         int,
    work_from7       time,
    work_to7         time,
    stand_from7      time,
    stand_to7        time,
    reason7          varchar(255),
    stamp timestamp,
    constraint xopclocksheet_pk primary key (xopclocksheet_id),
    constraint xopclocksheet_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id),
    constraint xopclocksheet_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xopclocksheet_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

create table xjobcard
(
    xjobcard_id      int not null auto_increment,
    xemployee_id     int not null,
    carddate         date not null,
    week_ending      date not null,
    job1_day1        varchar(255),
    job2_day1        varchar(255),
    job3_day1        varchar(255),
    job4_day1        varchar(255),
    job5_day1        varchar(255),
    machine_id1_day1 int,
    machine_id2_day1 int,
    machine_id3_day1 int,
    machine_id4_day1 int,
    machine_id5_day1 int,
    job1_day2        varchar(255),
    job2_day2        varchar(255),
    job3_day2        varchar(255),
    job4_day2        varchar(255),
    job5_day2        varchar(255),
    machine_id1_day2 int,
    machine_id2_day2 int,
    machine_id3_day2 int,
    machine_id4_day2 int,
    machine_id5_day2 int,
    job1_day3        varchar(255),
    job2_day3        varchar(255),
    job3_day3        varchar(255),
    job4_day3        varchar(255),
    job5_day3        varchar(255),
    machine_id1_day3 int,
    machine_id2_day3 int,
    machine_id3_day3 int,
    machine_id4_day3 int,
    machine_id5_day3 int,
    job1_day4        varchar(255),
    job2_day4        varchar(255),
    job3_day4        varchar(255),
    job4_day4        varchar(255),
    job5_day4        varchar(255),
    machine_id1_day4 int,
    machine_id2_day4 int,
    machine_id3_day4 int,
    machine_id4_day4 int,
    machine_id5_day4 int,
    job1_day5        varchar(255),
    job2_day5        varchar(255),
    job3_day5        varchar(255),
    job4_day5        varchar(255),
    job5_day5        varchar(255),
    machine_id1_day5 int,
    machine_id2_day5 int,
    machine_id3_day5 int,
    machine_id4_day5 int,
    machine_id5_day5 int,
    job1_day6        varchar(255),
    job2_day6        varchar(255),
    job3_day6        varchar(255),
    job4_day6        varchar(255),
    job5_day6        varchar(255),
    machine_id1_day6 int,
    machine_id2_day6 int,
    machine_id3_day6 int,
    machine_id4_day6 int,
    machine_id5_day6 int,
    job1_day7        varchar(255),
    job2_day7        varchar(255),
    job3_day7        varchar(255),
    job4_day7        varchar(255),
    job5_day7        varchar(255),
    machine_id1_day7 int,
    machine_id2_day7 int,
    machine_id3_day7 int,
    machine_id4_day7 int,
    machine_id5_day7 int,
    vehicle_id1_day1 int,
    vehicle_id2_day1 int,
    vehicle_id3_day1 int,
    vehicle_id4_day1 int,
    vehicle_id5_day1 int,
    vehicle_id1_day2 int,
    vehicle_id2_day2 int,
    vehicle_id3_day2 int,
    vehicle_id4_day2 int,
    vehicle_id5_day2 int,
    vehicle_id1_day3 int,
    vehicle_id2_day3 int,
    vehicle_id3_day3 int,
    vehicle_id4_day3 int,
    vehicle_id5_day3 int,
    vehicle_id1_day4 int,
    vehicle_id2_day4 int,
    vehicle_id3_day4 int,
    vehicle_id4_day4 int,
    vehicle_id5_day4 int,
    vehicle_id1_day5 int,
    vehicle_id2_day5 int,
    vehicle_id3_day5 int,
    vehicle_id4_day5 int,
    vehicle_id5_day5 int,
    vehicle_id1_day6 int,
    vehicle_id2_day6 int,
    vehicle_id3_day6 int,
    vehicle_id4_day6 int,
    vehicle_id5_day6 int,
    vehicle_id1_day7 int,
    vehicle_id2_day7 int,
    vehicle_id3_day7 int,
    vehicle_id4_day7 int,
    vehicle_id5_day7 int,
    stamp timestamp,
    constraint xjobcard_pk primary key (xjobcard_id),
    constraint xjobcard_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id),
    constraint xjobcard_xmachine_fk11 foreign key (machine_id1_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk21 foreign key (machine_id2_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk31 foreign key (machine_id3_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk41 foreign key (machine_id4_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk51 foreign key (machine_id5_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk12 foreign key (machine_id1_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk22 foreign key (machine_id2_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk32 foreign key (machine_id3_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk42 foreign key (machine_id4_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk52 foreign key (machine_id5_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk13 foreign key (machine_id1_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk23 foreign key (machine_id2_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk33 foreign key (machine_id3_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk43 foreign key (machine_id4_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk53 foreign key (machine_id5_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk14 foreign key (machine_id1_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk24 foreign key (machine_id2_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk34 foreign key (machine_id3_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk44 foreign key (machine_id4_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk54 foreign key (machine_id5_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk15 foreign key (machine_id1_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk25 foreign key (machine_id2_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk35 foreign key (machine_id3_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk45 foreign key (machine_id4_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk55 foreign key (machine_id5_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk16 foreign key (machine_id1_day6) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk26 foreign key (machine_id2_day6) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk36 foreign key (machine_id3_day6) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk46 foreign key (machine_id4_day6) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk56 foreign key (machine_id5_day6) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk17 foreign key (machine_id1_day7) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk27 foreign key (machine_id2_day7) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk37 foreign key (machine_id3_day7) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk47 foreign key (machine_id4_day7) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk57 foreign key (machine_id5_day7) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk011 foreign key (vehicle_id1_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk021 foreign key (vehicle_id2_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk031 foreign key (vehicle_id3_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk041 foreign key (vehicle_id4_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk051 foreign key (vehicle_id5_day1) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk012 foreign key (vehicle_id1_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk022 foreign key (vehicle_id2_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk032 foreign key (vehicle_id3_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk042 foreign key (vehicle_id4_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk052 foreign key (vehicle_id5_day2) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk013 foreign key (vehicle_id1_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk023 foreign key (vehicle_id2_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk033 foreign key (vehicle_id3_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk043 foreign key (vehicle_id4_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk053 foreign key (vehicle_id5_day3) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk014 foreign key (vehicle_id1_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk024 foreign key (vehicle_id2_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk034 foreign key (vehicle_id3_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk044 foreign key (vehicle_id4_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk054 foreign key (vehicle_id5_day4) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk015 foreign key (vehicle_id1_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk025 foreign key (vehicle_id2_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk035 foreign key (vehicle_id3_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk045 foreign key (vehicle_id4_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk055 foreign key (vehicle_id5_day5) references xmachine (xmachine_id),
    constraint xjobcard_xmachine_fk016 foreign key (vehicle_id1_day6) references xmachine (xmachine_id)
#--    ,constraint xjobcard_xmachine_fk026 foreign key (vehicle_id2_day6) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk036 foreign key (vehicle_id3_day6) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk046 foreign key (vehicle_id4_day6) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk056 foreign key (vehicle_id5_day6) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk017 foreign key (vehicle_id1_day7) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk027 foreign key (vehicle_id2_day7) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk037 foreign key (vehicle_id3_day7) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk047 foreign key (vehicle_id4_day7) references xmachine (xmachine_id),
#--    constraint xjobcard_xmachine_fk057 foreign key (vehicle_id5_day7) references xmachine (xmachine_id)    
);

create table xhourcompare
(
    xhourcompare_id int not null auto_increment,
    month_year      date,
    xsite_id        int not null,
    operator_id     int not null,
    xmachine_id     int not null,
    stamp timestamp,
    constraint xhourcompare_pk primary key (xhourcompare_id),
    constraint xhourcompare_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xhourcompare_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id),
    constraint xhourcompare_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

create table xhourcompareday
(
    xhourcompareday_id int not null auto_increment,
    xhourcompare_id    int not null,
    day_no             int not null,
    ocs                double,
    hm                 double,
    dr                 double,
    std                double,
    tsh                double,
    tsn                double,
    invn               double,
    stamp timestamp,
    constraint xhourcompareday_pk primary key (xhourcompareday_id),
    constraint xhourcompareday_xhourcompare_fk foreign key (xhourcompare_id) references xhourcompare (xhourcompare_id)
);


#---------------------------------------------------------------------------------------
create table cbitems
(
  cbitem_id     int not null auto_increment,
  name          varchar(32) not null,
  id            int not null,
  val           varchar(64),
  stamp timestamp,
  constraint cbitems_pk primary key (cbitem_id)
);

create unique index cbitems_uniq_idx on cbitems (name, id);


create table xmachrentalrate
(
    xmachrentalrate_id int not null auto_increment,
    actual_date        date,
    diesel_price       decimal(6,2),
    factor             decimal(6,2),
    stamp timestamp,
    constraint xmachinerentalrate_pk primary key (xmachrentalrate_id)
);

create table xmachrentalrateitm
(
    xmachrentalrateitm_id  int not null auto_increment,
    xmachrentalrate_id     int not null,
    cbitem_id              int not null,
    litres_hour            decimal(8,2) not null,
    dry                    decimal(8,2) not null,
    real_wet               decimal(8,2) not null,
    good_wet               decimal(8,2) not null,
    stamp timestamp,
    constraint xmachrentalrateitm_pk primary key (xmachrentalrateitm_id),
    constraint xmachrentalrateitm_xmachrentalrate_fk foreign key (xmachrentalrate_id) references xmachrentalrate (xmachrentalrate_id) on delete cascade,
    constraint xmachrentalrateitm_cbitem_fk foreign key (cbitem_id) references cbitems (cbitem_id)
);

create table xtransscheduleitm
(
    xtransscheduleitm_id   int not null auto_increment,
    machine_id             int not null,
    site_from_id           int not null,
    site_to_id             int not null,
    date_required          date,
    date_move              date,
    lowbed_id              int,
    operator_id            int,
    is_completed           bit,
    stamp timestamp,
    constraint xtransscheduleitm_pk primary key (xtransscheduleitm_id),
    constraint xtransscheduleitm_xmachine_fk foreign key (machine_id) references xmachine (xmachine_id),
    constraint xtransscheduleitm_xsite_fk foreign key (site_from_id) references xsite (xsite_id),
    constraint xtransscheduleitm_xsite_fk2 foreign key (site_to_id) references xsite (xsite_id),
    constraint xtransscheduleitm_xmachine_fk2 foreign key (lowbed_id) references xlowbed (xlowbed_id),
    constraint xtransscheduleitm_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id)
);

create table xopmachassing
(
    xopmachassing_id int not null auto_increment,
    xsite_id         int not null,
    xemployee_id     int,
    xmachine_id      int,
    date_start       date not null,
    date_end         date,
    stamp timestamp,
    constraint xopmachassing_pk primary key (xopmachassing_id),
    constraint xopmachassing_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xopmachassing_xemployee_fk foreign key (xemployee_id) references xemployee (xemployee_id) on delete cascade,
    constraint xopmachassing_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

create unique index xopmachassing_uniq_idx on xopmachassing (date_start, xsite_id, xemployee_id, xmachine_id);

drop table junk;
create table junk (j char(1), primary key (j));
insert into junk values('j');

insert into cbitems (name, id, val)
  select 'parts_groups',1,'machines'
    from junk
   where not exists(select * from cbitems where name='parts_groups' and id=1);
   
insert into cbitems (name, id, val)
  select 'parts_groups',2,'trucks'
    from junk
   where not exists(select * from cbitems where name='parts_groups' and id=2);

insert into cbitems (name, id, val)
  select 'parts_groups',3,'vehicles'
    from junk
   where not exists(select * from cbitems where name='parts_groups' and id=3);

insert into cbitems (name, id, val)
  select 'parts_groups',4,'misc'
    from junk
   where not exists(select * from cbitems where name='parts_groups' and id=4);

insert into cbitems (name, id, val)
  select 'parts_groups',5,'liquids'
    from junk
   where not exists(select * from cbitems where name='parts_groups' and id=5);

create table xpartcategory
(
    xpartcategory_id int not null auto_increment,
    group_id         int not null,
    name             varchar(64) not null,
    parent_id        int,
    stamp timestamp,
    constraint xpartcategory_pk primary key (xpartcategory_id),
    constraint xpartcategory_xpartcategory_fk foreign key (parent_id) references xpartcategory (xpartcategory_id)
);

create unique index xpartcategory_uniq_idx on xpartcategory (group_id, name);

insert into xpartcategory (group_id, name)
select id, upper(substr(val,1,1))+substr(val,2)+" parts" from cbitems where name='parts_groups';

create table xparts
(
    xparts_id       int not null auto_increment,
    partnumber      varchar(32) not null,
    description     varchar(128) not null,
    storename       varchar(64) not null,
    machinemodel    varchar(64),
    quantity        decimal(10,2) not null,
    lastsupplier_id int,
    prevsupplier_id int,
    priceperunit    decimal(10,2) not null,
    purchased       date,
    xpartcategory_id int not null,
    stamp timestamp,
    constraint xparts_pk primary key (xparts_id),
    constraint xparts_xsupplier_fk foreign key (lastsupplier_id) references xsupplier (xsupplier_id),
    constraint xparts_xsupplier_fk2 foreign key (prevsupplier_id) references xsupplier (xsupplier_id),
    constraint xparts_xpartcategory_fk foreign key (xpartcategory_id) references xpartcategory (xpartcategory_id),
    constraint valid_quantity check (quantity>=0)
);

create table xbookouts
(
    xbookouts_id    int not null auto_increment,
    xparts_id       int not null,
    issue_date      date not null,
    xsite_id        int not null,
    xmachine_id     int not null,
    issuedby_id     int not null,
    issuedto_id     int not null,
    quantity        decimal(10,2) not null,
    stamp           timestamp,
    constraint xbookouts_pk primary key (xbookouts_id),
    constraint xbookouts_xparts_fk foreign key (xparts_id) references xparts (xparts_id) on delete cascade,
    constraint xbookouts_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xbookouts_xemployee_fk foreign key (issuedby_id) references xemployee (xemployee_id),
    constraint xbookouts_xemployee_fk2 foreign key (issuedto_id) references xemployee (xemployee_id)
);

create table xaddstocks
(
    xaddstocks_id   int not null auto_increment,
    xparts_id       int not null,
    purchase_date   date not null,
    enteredby_id    int not null,
    xsupplier_id    int not null,
    priceperunit    decimal(10,2) not null,
    quantity        decimal(10,2) not null,
    stamp           timestamp,
    constraint xaddstocks_pk primary key (xaddstocks_id),
    constraint xaddstocks_xparts_fk foreign key (xparts_id) references xparts (xparts_id) on delete cascade,
    constraint xaddstocks_xemployee_fk foreign key (enteredby_id) references xemployee (xemployee_id),
    constraint xaddstocks_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)
);

create table xppetype
(
    xppetype_id    int not null auto_increment,
    xppetype       varchar(64) not null,
    stocklevel     int not null default 0,
    stamp          timestamp,
    constraint xppetype_pk primary key (xppetype_id)
);

create table xppebuy
(
    xppebuy_id      int not null auto_increment,
    buydate         date not null,
    boughtby_id     int not null,
    xsupplier_id    int not null,
    authorizedby_id int not null,
    stamp          timestamp,
    constraint xppebuy_pk primary key (xppebuy_id),
    constraint xppebuy_xemployee_fk foreign key (boughtby_id) references xemployee (xemployee_id),
    constraint xppebuy_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id),
    constraint xppebuy_xemployee_fk2 foreign key (authorizedby_id) references xemployee (xemployee_id)
);

create table xppebuyitem
(
    xppebuyitem_id  int not null auto_increment,
    xppebuy_id      int not null,
    xppetype_id     int not null,
    quantity        int not null,
    priceperunit    decimal (8,2),
    stamp           timestamp,
    constraint xppebuyitem_pk primary key (xppebuyitem_id),
    constraint xppebuyitem_xppetype_fk foreign key (xppetype_id) references xppetype (xppetype_id),
    constraint xppebuyitem_xppeby_fk foreign key (xppebuy_id) references xppebuy (xppebuy_id) on delete cascade
);

create table xppeissue
(
    xppeissue_id    int not null auto_increment,
    issuedate       date not null,
    issuedby_id     int not null,
    issuedto_id     int not null,
    authorizedby_id int not null,
    stamp          timestamp,
    constraint xppeissue_pk primary key (xppeissue_id),
    constraint xppeissue_xemployee_fk foreign key (issuedby_id) references xemployee (xemployee_id),
    constraint xppeissue_xemployee_fk2 foreign key (issuedto_id) references xemployee (xemployee_id),
    constraint xppeissue_xemployee_fk3 foreign key (authorizedby_id) references xemployee (xemployee_id)
);

create table xppeissueitem
(
    xppeissueitem_id int not null auto_increment,
    xppeissue_id     int not null,
    xppetype_id      int not null,
    quantity         int not null,
    stamp            timestamp,
    constraint xppeissueitem_pk primary key (xppeissueitem_id),
    constraint xppeissueitem_xppetype_fk foreign key (xppetype_id) references xppetype (xppetype_id),
    constraint xppeissueitem_xppeissue_fk foreign key (xppeissue_id) references xppeissue (xppeissue_id) on delete cascade
);

create table xmachservice
(
    xmachservice_id  int not null auto_increment,
    servicedate      date not null,
    entrydate        date not null,
    servicedby_id    int not null,
    assistedby_id    int not null,
    xmachine_id      int not null,
    engine_oil       varchar(128),
    hydraulic_oil    varchar(128),
    brake_fluid      varchar(128),
    transmission_oil varchar(128),
    gearbox_oil      varchar(128),
    anti_freeze      varchar(128),
    diff_checked1    varchar(128),
    diff_checked2    varchar(128),
    diff_checked3    varchar(128),
    stamp            timestamp,
    constraint xmachservice_pk primary key (xmachservice_id),
    constraint xmachservice_xemployee_fk foreign key (servicedby_id) references xemployee (xemployee_id),
    constraint xmachservice_xemployee_fk2 foreign key (assistedby_id) references xemployee (xemployee_id),
    constraint xmachservice_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

create table xdieselcart
(
    xdieselcart_id   int not null auto_increment,
    fleet_nr         int not null,
    reg_nr           varchar(32),
    expdate          date,
    litres           int not null,
    chassis_nr       varchar(32),
    assigned_id      int,
    photo            mediumblob,
    stamp            timestamp,
    constraint xdieselcart_pk primary key (xdieselcart_id),
    constraint xdieselcart_xmachine_fk foreign key (assigned_id) references xmachine (xmachine_id)
);

create table xdieselpurchase
(
    xdieselpurchase_id int not null auto_increment,
    xsupplier_id       int not null,
    purchase_date      date not null,
    litres             decimal(6,2) not null, 
    rand_factor        decimal(5,2) not null,
    stamp              timestamp,
    constraint xdieselpurchase_pk primary key (xdieselpurchase_id),
    constraint xdieselpurchase_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)
);

create table xdieselcartissue
(
    xdieselcartissue_id int not null auto_increment,
    issue_date          date not null,
    driver_id           int not null,
    xdieselcart_id      int not null,
    liters              decimal(6,2) not null,
    xsupplier_id        int not null,
    stamp               timestamp,
    constraint xdieselcartissue_pk primary key (xdieselcartissue_id),
    constraint xdieselcartissue_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id),
    constraint xdieselcartissue_xdieselcart_fk foreign key (xdieselcart_id) references xdieselcart (xdieselcart_id),
    constraint xdieselcartissue_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)
);

create table xdiesel2plant
(
    xdiesel2plant_id  int not null auto_increment,
    xdieselcart_id    int not null,
    last_date         date,
    driver_id         int not null,
    stamp             timestamp,
    constraint xdiesel2plant_pk primary key (xdiesel2plant_id),
    constraint xdiesel2plant_xdieselcart_fk foreign key (xdieselcart_id) references xdieselcart (xdieselcart_id),
    constraint xdiesel2plant_xemployee_fk foreign key (driver_id) references xemployee (xemployee_id)
);

create table xdiesel2plantitem
(
    xdiesel2plantitem_id int not null auto_increment,
    xdiesel2plant_id     int not null,
    add_date             date not null,
    xmachine_id          int not null,
    xsite_id             int not null,
    operator_id          int not null,
    hour_meter           decimal(9,1) not null,
    issuedby_id          int not null,
    liters               decimal(6,2) not null,
    stamp                timestamp,
    constraint xdiesel2plantitem_pk primary key (xdiesel2plantitem_id),
    constraint xdiesel2plantitem_xdiesel2plant_fk foreign key (xdiesel2plant_id) references xdiesel2plant (xdiesel2plant_id),
    constraint xdiesel2plantitem_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id),
    constraint xdiesel2plantitem_xsite_fk foreign key (xsite_id) references xsite (xsite_id),
    constraint xdiesel2plantitem_xemployee_fk foreign key (operator_id) references xemployee (xemployee_id),
    constraint xdiesel2plantitem_xemployee_fk2 foreign key (issuedby_id) references xemployee (xemployee_id)
);

create table xbattery
(
    xbattery_id   int not null auto_increment,
    battery_code  varchar(32) not null,
    vat_excl_unit decimal (6,2),
    battery_id    varchar(32),
    stamp         timestamp,
    constraint xbattery_pk primary key (xbattery_id)
);

create table xbatterypurchase
(
    xbatterypurchase_id int not null auto_increment,
    purchase_date       date not null,
    entry_date          date not null,
    purchased_by        int not null,
    xsupplier_id        int not null,
    invoice_vat_incl    decimal (9,2) not null,
    invoice_vat_excl    decimal (9,2) not null,
    stamp               timestamp,
    constraint xbatterypurchase_pk primary key (xbatterypurchase_id),
    constraint xbatterypurchase_xsupplier_fk foreign key (xsupplier_id) references xsupplier (xsupplier_id)
);

create table xbateryissue
(
    xbateryissue_id     int not null auto_increment,
    xbattery_id         int not null,
    issue_date          date not null,
    entry_date          date not null,
    issued_by           int not null,
    issued_to           int not null,
    xmachine_id         int not null,
    stamp               timestamp,
    constraint xbateryissue_pk primary key (xbateryissue_id),
    constraint xbateryissue_xemployee_fk foreign key (issued_by) references xemployee (xemployee_id),
    constraint xbateryissue_xemployee_fk2 foreign key (issued_to) references xemployee (xemployee_id),
    constraint xbateryissue_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id)
);

#----------------- auxiliary tables -------------------

create or replace view v_userprofile as
select p.profile_id,
       p.first_name,
       p.last_name,
       p.address1,
       p.address2,
       p.city,
       p.state,
       p.zip_code,
       p.phone as office_phone,
       p.cell_phone,
       u.fax,
       p.email,
       u.webaddress,
       u.office_hours,
       u.salesperson,
       u.manager
  from profile p, userprofile u
 where u.profile_id = p.profile_id;

create view v_clientprofile as
select p.profile_id,
       p.first_name,
       p.last_name,
       c.birthday,
       p.address1,
       p.address2,
       p.city,
       p.state,
       p.zip_code,
       p.phone as home_phone,
       p.cell_phone,
       p.email,
       c.source_type,
       c.source_descr,
       c.sales_potential
  from profile p, clientprofile c
 where c.profile_id = p.profile_id;

delimiter |
create function to_char(dt datetime, fmt varchar(32))
returns varchar(32) deterministic
begin
   declare fmt char(32) default fmt;
   set fmt = replace(fmt,'DD','%e');
   set fmt = replace(fmt,'MM','%m');
   set fmt = replace(fmt,'YYYY','%Y');
   set fmt = replace(fmt,'YY','%y');
   set fmt = replace(fmt,'HH24','%H');
   set fmt = replace(fmt,'MI','%i');
   set fmt = replace(fmt,'SS','%S');
   return DATE_FORMAT(dt, fmt);
end;
|
delimiter |
create function casewhen(cnd tinyint(1),yesans varchar(32),noans varchar(32))
returns varchar(32) deterministic
begin
return ifnull(if(cnd,yesans,noans),'');
end;
|

delimiter |
create function extractnum(sval varchar(32))
returns integer deterministic
begin
   declare sub varchar(32) default '';
   declare i tinyint default 0;
   LP: loop
        set i = i + 1;
        if i > length(sval) then
            leave LP;
        end if;
        if instr('0123456789',substr(sval,i,1))>0 then
            set sub = concat(sub,substr(sval,i,1));
        end if;
   end loop LP;
   return convert(sub, unsigned);
end;
|
delimiter |
create function extractchars(sval varchar(32))
returns char(32) deterministic
begin
   declare sub varchar(32) default '';
   declare i tinyint default 0;
   LP: loop
        set i = i + 1;
        if i > length(sval) then
            leave LP;
        end if;
        if instr('0123456789',substr(sval,i,1))=0 then
            set sub = concat(sub,substr(sval,i,1));
        end if;
   end loop LP;
   return sub ;
end;
|
delimiter ;
