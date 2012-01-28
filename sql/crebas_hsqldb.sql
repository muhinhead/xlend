CREAte cached table dbversion
(
    dbversion_id    int generated by default as identity (start with 1),
    version_id      int not null,
    version         varchar(12),
    constraint dbversion_pk primary key (dbversion_id)
);

insert into dbversion values(1,14,'0.14');


create cached table picture
(
    picture_id   int generated by default as identity (start with 1),
    picture      other,
    constraint picture_pk primary key (picture_id)
);

create cached table profile 
(
    profile_id   int generated by default as identity (start with 1),
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
    constraint profile_pk primary key (profile_id),
    constraint profile_picture_fk foreign key (picture_id) references picture
);

create cached table userprofile
(
    profile_id   int not null,
    fax          varchar(12),
    webaddress   varchar(80),
    office_hours varchar(48),
    salesperson  smallint,
    manager      smallint,
    login        varchar(16),
    pwdmd5       varchar(32),
    constraint userprofile_pk primary key (profile_id),
    constraint userprofile_profile_fk foreign key (profile_id) references profile on delete cascade
);

create cached table sheet
(
    sheet_id  int generated by default as identity (start with 1),
    sheetname varchar(32) not null,
    classname varchar(64),
    parent_id int,
    constraint sheet_pk primary key (sheet_id)
);

insert into sheet (sheetname,classname) values('DOCS','com.xlend.gui.work.DocFrame');
insert into sheet (sheetname,classname) values('SITES','com.xlend.gui.work.SitesFrame');
insert into sheet (sheetname,classname) values('REPORTS',null);
insert into sheet (sheetname,classname) values('HR','com.xlend.gui.HRFrame');
insert into sheet (sheetname,classname) values('FLEET','package com.xlend.gui.FleetFrame');

create cached table usersheet
(
    usersheet_id int generated by default as identity (start with 1),
    profile_id   int not null,
    sheet_id     int not null,
    constraint usersheet_pk primary key (usersheet_id),
    constraint usersheet_sheet_fk foreign key (sheet_id) references sheet on delete cascade,
    constraint usersheet_user_fk foreign key (profile_id) references userprofile on delete cascade
);

create cached table clientprofile
(
    profile_id        int not null,
    salesperson_id    int,
    birthday          date,
    source_type       varchar(10), --check (source_type in('Phonebook','Referral','Location','Others')),
    source_descr      varchar(255),
    sales_potential   int,
    constraint clientprofile_pk primary key (profile_id),
    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile,
    constraint clientprofile_profile_fk foreign key (profile_id) references profile on delete cascade
);

create cached table xclient
(
    xclient_id      int generated by default as identity (start with 1),
    clientcode      varchar(16) not null,
    companyname     varchar(255) not null,
    contactname     varchar(255),
    phonenumber     varchar(12),
    vatnumber       varchar(32),
    address         varchar(255),
    description     varchar(255),
    constraint xclient_pk primary key (xclient_id)
);

create cached table xcontract
(
    xcontract_id   int generated by default as identity (start with 1),
    contractref    varchar(32) not null,
    description    varchar(255),
    xclient_id     int not null,
    constraint xcontract_pk primary key (xcontract_id),
    constraint xcontract_xclient_fk foreign key (xclient_id) references xclient
);

create cached table xcontractpage --implements IPage
(
    xcontractpage_id   int generated by default as identity (start with 1),
    xcontract_id       int not null,
    pagenum            int,
    description        varchar(64),
    fileextension      varchar(8),
    pagescan           other, 
    constraint xcontractpage_pk primary key (xcontractpage_id),
    constraint xcontractpage_xcontract_fk foreign key (xcontract_id) references xcontract on delete cascade
);
 
create cached table xquotation
(
    xquotation_id      int generated by default as identity (start with 1),
    xclient_id         int not null,
    rfcnumber          varchar(32) not null,
    received           date,
    deadline           date,
    responded          date,
    responsedby        varchar(12),
    responsecmnt       varchar(128),
    responder_id       int,
    responsedoc        other,
    constraint xquotation_pk primary key (xquotation_id),
    constraint xquotation_xclient_fk foreign key (xclient_id) references xclient,
    constraint xquotation_profile_fk foreign key (responder_id) references userprofile
);   
 
create cached table xquotationpage --implements IPage
(
    xquotationpage_id  int generated by default as identity (start with 1),
    xquotation_id      int not null,
    pagenum            int,
    description        varchar(64),
    fileextension      varchar(8),
    pagescan           other, 
    constraint xquotationpage_pk primary key (xquotationpage_id),
    constraint xquotationpage_xquotation_fk foreign key (xquotation_id) references xquotation on delete cascade
);

create cached table xorder
(
    xorder_id          int generated by default as identity (start with 1),
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
    constraint xorder_pk primary key (xorder_id),
    constraint xorder_xclient_fk foreign key (xclient_id) references xclient,
    constraint xorder_xquotation_fk  foreign key (xquotation_id) references xquotation,
    constraint xorder_xcontract foreign key (xcontract_id) references xcontract
);                            

create cached table xsite
(
    xsite_id        int generated by default as identity (start with 1),
    name            varchar(32) not null,
    description     varchar(255),
    dieselsponsor   smallint default 0,
    sitetype        char(1),
    xorder_id       int,
    constraint xsite_id primary key (xsite_id),
    constraint xsite_xorder_fk foreign key (xorder_id) references xorder on delete cascade
);

create cached table xorderpage --implements IPage
(
    xorderpage_id     int generated by default as identity (start with 1),
    xorder_id         int not null,
    pagenum           int,
    description       varchar(64),
    fileextension      varchar(8),
    pagescan          other,
    constraint xorderpage_pk primary key (xorderpage_id),
    constraint xorderpage_xorder_fk foreign key (xorder_id) references xorder on delete cascade 
);

create cached table xposition
(
    xposition_id    int generated by default as identity (start with 1),
    pos             varchar(64) not null,
    constraint xposition_pk primary key (xposition_id)
);

create cached table xemployee
(
    xemployee_id    int generated by default as identity (start with 1),
    clock_num       varchar(8)  not null,
    first_name      varchar(16) not null,
    sur_name        varchar(16) not null,
    nick_name       varchar(16) not null,
    id_num          varchar(16) not null,
    phone0_num      varchar(16) not null,
    phone1_num      varchar(16),
    phone2_num      varchar(16),
    relation1       varchar(16), 
    relation2       varchar(16), 
    address         varchar(128) not null, 
    contract_len    int          not null, --1/3/6/12/0
    contract_start  date         not null,
    contract_end    date,
    rate            int not null,
    xposition_id    int,
    taxnum          varchar(32),
    photo           other,
    photo2          other,
    deceased        bit,
    dismissed       bit,
    absconded       bit,
    constraint xemployee_pk primary key (xemployee_id),
    constraint xemployee_xposition_fk foreign key (xposition_id) references xposition
);

create cached table xtimesheet
(
    xtimesheet_id int generated by default as identity (start with 1),
    xemployee_id  int not null,
    xorder_id     int not null,
    xsite_id      int not null,
    weekend       date,
    clocksheet    smallint default 1,
    image         other,
    constraint xtimesheet_pk primary key (xtimesheet_id),
    constraint xtimesheet_xemployee_fk foreign key (xemployee_id) references xemployee on delete cascade,
    constraint xtimesheet_xorder_fk foreign key (xorder_id) references xorder,
    constraint xtimesheet_xsite_fk foreign key (xsite_id) references xsite
);

create cached table xwage
(
    xwage_id       int generated by default as identity (start with 1),
    xtimesheet_id  int not null,
    day            date,
    normaltime     double,
    overtime       double,
    doubletime     double,
    deduction      double,
    stoppeddetails varchar(128),
    constraint xwage_pk primary key (xwage_id),
    constraint xwage_xtimesheet_fk foreign key (xtimesheet_id) references xtimesheet on delete cascade
);

create cached table xwagesum
(
    xwagesum_id int generated by default as identity (start with 1),
    weekend     date 
    constraint xwagesum_pk primary key (xwagesum_id)
);

create cached table xwagesumitem
(
    xwagesumitem_id int generated by default as identity (start with 1),
    xwagesum_id     int,
    xemployee_id    int not null,
    xtimesheet_id   int,
    weeklywage      int,
    normaltime      double,
    overtime        double,
    doubletime      double,
    constraint xwagesumitem_pk primary key (xwagesumitem_id),
    constraint xwagesumitem_xwagesum_fk foreign key (xwagesum_id) references xwagesum on delete cascade,
    constraint xwagesumitem_xemployee_fk foreign key (xemployee_id) references xemployee on delete cascade,
    constraint xwagesumitem_xtimesheet_fk foreign key (xtimesheet_id) references xtimesheet
);

create cached table xmachtype
(
    xmachtype_id     int generated by default as identity (start with 1),
    machtype         varchar(32) not null,
    parenttype_id    int,
    classify         char(1),
    constraint xmachtype_pk primary key (xmachtype_id),
    --constraint xmachtype_xmachtype_fk foreign key (parenttype_id) references xmachtype
);

insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(1,'Buldozer',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(2,'TLB',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(3,'Roller',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(4,'Excavator',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(5,'Bobcat',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(6,'Grader',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(7,'Other',null,'M');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(8,'6M TIPPER',null,'T');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(9,'10M TIPPER',null,'T');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(10,'WATERCARTS',null,'T');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(11,'TATA NOVUS 5542',null,'L');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(12,'FORD LOUISVILLE',null,'L');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(13,'INTERNATIONAL S-LINE',null,'L');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(14,'SINGLE AXLE MERCEDES',null,'L');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(15,'VOLVO F12',null,'L');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(16,'PICK-UP',null,'P');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(17,'BREAK-DOWN',null,'P');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(18,'PANELVAN',null,'P');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(19,'BUS',null,'P');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(20,'MINI BUS',null,'P');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(21,'LAND ROVER',null,'V');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(22,'SEDAN',null,'V');
insert into xmachtype (xmachtype_id,machtype,parenttype_id,classify) values(23,'BIKE',null,'V');
insert into xmachtype (machtype,parenttype_id) values('D31', 1);
insert into xmachtype (machtype,parenttype_id) values('D41', 1);
insert into xmachtype (machtype,parenttype_id) values('D4H', 1);
insert into xmachtype (machtype,parenttype_id) values('D65', 1);
insert into xmachtype (machtype,parenttype_id) values('D6H', 1);
insert into xmachtype (machtype,parenttype_id) values('D6R', 1);
insert into xmachtype (machtype,parenttype_id) values('428B 4X4',2);
insert into xmachtype (machtype,parenttype_id) values('428B 4X2',2);
insert into xmachtype (machtype,parenttype_id) values('428C 4X4',2);
insert into xmachtype (machtype,parenttype_id) values('428C 4X2',2);
insert into xmachtype (machtype,parenttype_id) values('428E 4X4',2);
insert into xmachtype (machtype,parenttype_id) values('428E HAMMER',2);
insert into xmachtype (machtype,parenttype_id) values('428E 4X2',2);
insert into xmachtype (machtype,parenttype_id) values('422E 4X4',2);
insert into xmachtype (machtype,parenttype_id) values('422E 4X2',2);
insert into xmachtype (machtype,parenttype_id) values('434E 4X4',2);
insert into xmachtype (machtype,parenttype_id) values('434E HAMMER',2);
insert into xmachtype (machtype,parenttype_id) values('PADFOOT ROLLER',3);
insert into xmachtype (machtype,parenttype_id) values('SMOOTH DRUM ROLLER',3);
insert into xmachtype (machtype,parenttype_id) values('2.5TON ROLLER',3);
insert into xmachtype (machtype,parenttype_id) values('PNEUMATIC ROLLER',3);
insert into xmachtype (machtype,parenttype_id) values('320 CAT',4);
insert into xmachtype (machtype,parenttype_id) values('322 CAT',4);
insert into xmachtype (machtype,parenttype_id) values('322 CAT HAMMER',4);
insert into xmachtype (machtype,parenttype_id) values('325 CAT',4);
insert into xmachtype (machtype,parenttype_id) values('330 CAT',4);
insert into xmachtype (machtype,parenttype_id) values('PC200',4);
insert into xmachtype (machtype,parenttype_id) values('PC300',4);
insert into xmachtype (machtype,parenttype_id) values('216B',5);
insert into xmachtype (machtype,parenttype_id) values('EXCAVATING ATTACHMENT',5);
insert into xmachtype (machtype,parenttype_id) values('BROOM ATTACHMENT',5);
insert into xmachtype (machtype,parenttype_id) values('HAMMER ATTACHMENT',5);
insert into xmachtype (machtype,parenttype_id) values('140H',6);
insert into xmachtype (machtype,parenttype_id) values('140G',6);
insert into xmachtype (machtype,parenttype_id) values('TRACTOR WITH SLASHER',7);
insert into xmachtype (machtype,parenttype_id) values('TELESCOPIC HANDLER',7);
insert into xmachtype (machtype,parenttype_id) values('BELL 225A LOGGER',7);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES 1414',8);
insert into xmachtype (machtype,parenttype_id) values('TOYOTA 6000',8);
insert into xmachtype (machtype,parenttype_id) values('TATA LPT',8);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES ATEGO',8);
insert into xmachtype (machtype,parenttype_id) values('OTHER',8);
insert into xmachtype (machtype,parenttype_id) values('TATA NOVUS 3434',9);
insert into xmachtype (machtype,parenttype_id) values('MITCHUBISHI FUSO',9);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES ACTROS',9);
insert into xmachtype (machtype,parenttype_id) values('SAMAG',9);
insert into xmachtype (machtype,parenttype_id) values('BLUNT NOSE MERCEDES',9);
insert into xmachtype (machtype,parenttype_id) values('NISSAN CW46',9);
insert into xmachtype (machtype,parenttype_id) values('OTHER',9);
insert into xmachtype (machtype,parenttype_id) values('6000L',6);
insert into xmachtype (machtype,parenttype_id) values('12000L',6);
insert into xmachtype (machtype,parenttype_id) values('HILUX',16);
insert into xmachtype (machtype,parenttype_id) values('HILUX HIGH RIDER',16);
insert into xmachtype (machtype,parenttype_id) values('NISSAN 1400',16);
insert into xmachtype (machtype,parenttype_id) values('OTHER',16);
insert into xmachtype (machtype,parenttype_id) values('TOYOTA DYNA',17);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES VITO',18);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES SPRINTER',18);
insert into xmachtype (machtype,parenttype_id) values('9 SEATER SPRINTER',19);
insert into xmachtype (machtype,parenttype_id) values('12 SEATER SPRINTER',19);
insert into xmachtype (machtype,parenttype_id) values('VOLKSWAGEN MICROBUS',20);
insert into xmachtype (machtype,parenttype_id) values('KIA PREGIO',20);
insert into xmachtype (machtype,parenttype_id) values('DEFENDER 100',21);
insert into xmachtype (machtype,parenttype_id) values('DEFENDER 110',21);
insert into xmachtype (machtype,parenttype_id) values('DISCOVERY 2',21);
insert into xmachtype (machtype,parenttype_id) values('BMW 318',22);
insert into xmachtype (machtype,parenttype_id) values('BMW 328',22);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES C220',22);
insert into xmachtype (machtype,parenttype_id) values('MERCEDES C280',22);
insert into xmachtype (machtype,parenttype_id) values('AUDI',22);
insert into xmachtype (machtype,parenttype_id) values('HONDA',23);
insert into xmachtype (machtype,parenttype_id) values('BMW',23);

insert into xmachtype(machtype,parenttype_id,classify) values('FrontEnd Loader',null,'M');
insert into xmachtype(machtype,parenttype_id) select 'Kamatsu FEL',xmachtype_id from xmachtype where machtype='FrontEnd Loader';
insert into xmachtype(machtype,parenttype_id) select 'Case TLB',xmachtype_id from xmachtype where machtype='TLB';
                        

create cached table xmachine
(
    xmachine_id     int generated by default as identity (start with 1),
    tmvnr           varchar(3) not null,
    xmachtype_id    int not null,
    xmachtype2_id   int,
    reg_nr          varchar(16),
    expdate         date,
    vehicleid_nr    varchar(16),
    engine_nr       varchar(16),
    chassis_nr      varchar(32),
    classify        char(1),
    insurance_nr    varchar(16),
    insurance_tp    varchar(16),
    insurance_amt   int,
    deposit_amt     int,
    contract_fee    int,
    monthly_pay     int,
    paystart        date,
    payend          date,
    photo           other,
    constraint xmachine_pk primary key (xmachine_id),
    constraint xmachine_xmachtype_fk foreign key (xmachtype_id) references xmachtype
);

create cached table xmachineonsite
(
    xmachineonsate_id int generated by default as identity (start with 1),
    xsite_id          int not null,
    xmachine_id       int not null,
    xemployee_id      int,
    estdate           date not null,
    deestdate         date,
    constraint xmachineonsite_pk primary key (xmachineonsate_id),
    constraint xmachineonsite_xmachine_fk foreign key (xmachine_id) references xmachine on delete cascade,
    constraint xmachineonsite_xsite_fk foreign key (xsite_id) references xsite,
    constraint xmachineonsite_xemployee_fk foreign key (xemployee_id) references xemployee
);

create cached table xorderitem
(
    xorderitem_id     int generated by default as identity (start with 1),
    xorder_id         int not null,
    itemnumber        varchar(16) not null,
    deliveryreq       date,
    delivery          date,
    quantity          int not null,
    measureitem       varchar(16),
    priceperone       decimal(10,2) not null,    
    xmachtype_id      int,
    totalvalue        decimal(10,2),
    constraint xorderitem_pk primary key (xorderitem_id),
    constraint xoreritem_xorder_fk foreign key (xorder_id) references xorder on delete cascade,
    constraint xorderitem_xmachtype_fk foreign key (xmachtype_id) references xmachtype
);

create cached table xsupplier
(
    xsupplier_id     int generated by default as identity (start with 1),
    companyname      varchar(64)  not null,
    contactperson    varchar(64),
    productdesc      varchar(128) not null,
    phone            varchar(64),
    fax              varchar(32),
    cell             varchar(64),
    email            varchar(128),
    vatnr            varchar(32),
    company_regnr    varchar(32),
    address          varchar(255),
    banking          varchar(255),
    constraint xsupplier_pk primary key (xsupplier_id)
);

create cached table xpaidmethod
(
    xpaidmethod_id   int generated by default as identity (start with 1),
    method           varchar(32) not null,
    constraint xpaidmethod_pk primary key (xpaidmethod_id)
);

insert into xpaidmethod values (1, 'Cash Purchase');
insert into xpaidmethod values (2, 'Cheque');
insert into xpaidmethod values (3, 'Electronic Bank Transfer');
insert into xpaidmethod values (4, 'On Account (not Paid)');
insert into xpaidmethod values (5, 'On Account (Paid)');

create cached table xdieselpchs
(
    xdieselpchs_id   int generated by default as identity (start with 1),
    xsupplier_id     int not null, 
    purchased        date not null,
    authorizer_id    int,
    amount_liters    int not null,
    amount_rands     int not null,
    paidby_id        int,
    xpaidmethod_id   int not null,  
    constraint xdieselpchs_pk primary key (xdieselpchs_id),
    constraint xdieselpchs_xsupplier_fk foreign key (xsupplier_id) references xsupplier,
    constraint xdieselpchs_xemployee_fk foreign key (authorizer_id) references xemployee,
    constraint xdieselpchs_xemployee_fk2 foreign key (paidby_id) references xemployee,
    constraint xdieselpchs_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod
);

create cached table xdieselcard
(
    xdieselcard_id    int generated by default as identity (start with 1),
    carddate          date not null,
    xmachine_id       int not null,
    operator_id       int not null,
    xsite_id          int not null,
    amount_liters     int,
    personiss_id      int,
    constraint xdieselcard_pk primary key (xdieselcard_id),
    constraint xdieselcard_xmachine_fk foreign key (xmachine_id) references xmachine,
    constraint xdieselcard_xemployee_fk foreign key (operator_id) references xemployee,
    constraint xdieselcard_xsite_fk foreign key (xsite_id) references xsite,
    constraint xdieselcard_xemployee_fk2 foreign key (personiss_id) references xemployee
);

create cached table xconsume
(
    xconsume_id       int generated by default as identity (start with 1),
    xsupplier_id      int not null,
    xmachine_id       int not null,
    requester_id      int not null,
    invoicedate       date,
    invoicenumber     varchar(16),
    authorizer_id     int,
    collector_id      int,
    description       varchar(255),
    partnumber        varchar(32),
    amount_liters     int,
    amount_rands      int,
    payer_id          int,
    xpaidmethod_id    int not null,  
    chequenumber      varchar(32),
    constraint xconsume_pk primary key (xconsume_id),
    constraint xconsume_xsupplier_fk foreign key (xsupplier_id) references xsupplier,
    constraint xconsume_xmachine_fk foreign key (xmachine_id) references xmachine,
    constraint xconsume_xemployee_fk foreign key (requester_id) references xemployee,
    constraint xconsume_xemployee_fk2 foreign key (authorizer_id) references xemployee,
    constraint xconsume_xemployee_fk3 foreign key (collector_id) references xemployee,
    constraint xconsume_xemployee_fk4 foreign key (payer_id) references xemployee,
    constraint xconsume_xpaidmethod_fk foreign key (xpaidmethod_id) references xpaidmethod
);

create cached table xcreditor
(
    xcreditor_id     int generated by default as identity (start with 1),
    xsupplier_id     int not null,
    xconsume_id      int,
    accnum           varchar(16),
    invoiceammount   decimal(10,2),
    paid             bit,
    purchased        date,
    paidfrom         int,
    constraint xcreditors_pk primary key (xcreditor_id),
    constraint xcreditor_xsupplier_fk foreign key (xsupplier_id) references xsupplier,
    constraint xcreditor_xconsume_fk foreign key (xconsume_id) references xconsume
);

create cached table xbreakdown
(
    xbreakdown_id     int generated by default as identity (start with 1),
    breakdowndate     date,
    xmachine_id       int not null,
    xsite_id          int not null,
    reportedto_id     int,
    reportedby_id     int,
    attendedby_id     int,
    vehicle_id        int,
    repairdate        date,
    repaired          smallint,
    description       varchar(255),
    operatorfault     smallint,
    operator_id       int,
    xconsume_id       int,
    km2site1way       int,
    hoursonjob        int,
    timeleft          int,
    timeback          int,
    stayedover        smallint,
    accomprice        int,
    constraint xbreakdown_pk primary key (xbreakdown_id),
    constraint xbreakdown_xmachine_fk foreign key (xmachine_id) references xmachine,
    constraint xbreakdown_xsite_fk foreign key (xsite_id) references xsite,
    constraint xbreakdown_xemployee_fk foreign key (reportedto_id) references xemployee,
    constraint xbreakdown_xemployee_fk2 foreign key (reportedby_id) references xemployee,
    constraint xbreakdown_xemployee_fk3 foreign key (attendedby_id) references xemployee,
    constraint xbreakdown_xmachine_fk2 foreign key (vehicle_id) references xmachine,
    constraint xbreakdown_xemployee_fk4 foreign key (operator_id) references xemployee,
    constraint xbreakdown_xconsume_fk foreign key (xconsume_id) references xconsume
);

create table cbitems
(
  cbitem_id     int generated by default as identity (start with 1),
  name          varchar(12) not null, 
  id            int not null, 
  val           varchar(32),
  constraint cbitems_pk primary key (cbitem_id)
);

insert into cbitems values('paidfrom',0,'--Unknown--');
insert into cbitems values('paidfrom',1,'TO MACHINE AND TRUCK');
insert into cbitems values('paidfrom',2,'TO TRUCK SALES');
insert into cbitems values('paidfrom',3,'T&F CONSTRUCTION');
insert into cbitems values('paidfrom',4,'XLEND PLANT');
insert into cbitems values('paidfrom',5,'EACSA');

----------------- auxiliary tables -------------------

create view v_userprofile as 
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

create or replace view v_clientprofile as
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

insert into profile(profile_id,first_name,last_name,address1) values(1,'Admin','Adminson','not known');
insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,0,1,'admin','admin' from profile where first_name='Admin';

insert into profile(profile_id,first_name,last_name,address1) values(2,'Salesman','Sale','not known');
insert into userprofile(profile_id,salesperson,manager,login,pwdmd5) select profile_id,1,0,'sale','sale' from profile where first_name='Salesman';
