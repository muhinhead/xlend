CREAte cached table dbversion
(
    dbversion_id    int generated by default as identity (start with 1),
    version_id      int not null,
    version         varchar(12),
    constraint dbversion_pk primary key (dbversion_id)
);

insert into dbversion values(1,6,'0.06');


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
    contactname	    varchar(255),
    phonenumber	    varchar(12),
    vatnumber       varchar(32),
    address	    varchar(255),
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

create cached table xorderitem
(
    xorderitem_id     int generated by default as identity (start with 1),
    xorder_id         int not null,
    itemnumber        varchar(16) not null,
    materialnumber    varchar(16),
    machinetype       varchar(64),
    deliveryreq       date,
    delivery          date,
    description       varchar(255) not null,
    quantity          int not null,
    measureitem       varchar(16),
    priceperone       decimal(10,2) not null,    
    constraint xorderitem_pk primary key (xorderitem_id),
    constraint xoreritem_xorder_fk foreign key (xorder_id) references xorder on delete cascade
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
    constraint xposition_pk primary key (xposition_id),
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
    photo           other,
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

create cached table xmachtype
(
    xmachtype_id     int generated by default as identity (start with 1),
    machtype         varchar(32) not null,
    parenttype_id    int,
    constraint xmachtype_pk primary key (xmachtype_id),
 --   constraint xmachtype_xmachtype_fk foreign key (parenttype_id) references xmachtype
);

insert into xmachtype values( 1,'Dozer',null);
insert into xmachtype values( 2,'TLB',null);
insert into xmachtype values( 3,'Roller',null);
insert into xmachtype values( 4,'Excavator',null);
insert into xmachtype values( 5,'Front End Loader',null);
insert into xmachtype values( 6,'Tractor',null);
insert into xmachtype values( 7,'Bobcat',null);
insert into xmachtype values( 8,'Telehandler',null);
insert into xmachtype values( 9,'D3', 1);
insert into xmachtype values(10,'D4', 1);
insert into xmachtype values(11,'D6', 1);
insert into xmachtype values(12,'D6R',1);
insert into xmachtype values(13,'428C',2); 
insert into xmachtype values(14,'428E',2);
insert into xmachtype values(15,'422C',2); 
insert into xmachtype values(16,'438C',2);
insert into xmachtype values(17,'PC300',4); 
insert into xmachtype values(18,'CAT420',4);
insert into xmachtype values(19,'PC220',4);

create cached table xmachine
(
    xmachine_id     int generated by default as identity (start with 1),
    tmvnr           varchar(3) not null,
    xmachtype_id    int not null,
    xmachtype2_id    int,
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
    constraint xmachine_xmachtype_fk foreign key (xmachtype_id) references xmachtype,
    constraint xmachine_xmachtype_fk2 foreign key (xmachtype2_id) references xmachtype
);


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
