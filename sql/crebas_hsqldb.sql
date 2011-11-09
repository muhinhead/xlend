create table xclient
(
    xclient_id      int not null identity,
    clientcode      varchar(16) not null,
    companyname     varchar(255) not null,
    contactname	    varchar(255),
    phonenumber	    varchar(12),
    vatnumber       varchar(32),
    address	        varchar(255),
    constraint xclient_pk primary key (xclient_id)
);


create table picture
(
    picture_id   int not null identity,
    picture      other,
    constraint picture_pk primary key (picture_id)
);

create table profile 
(
    profile_id   int not null identity,
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
    constraint profile_picture_fk foreign key (picture_id) references picture on delete cascade
);

create table userprofile
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

create table clientprofile
(
    profile_id        int not null,
    salesperson_id    int,
    birthday          date,
    source_type       varchar(10) --check (source_type in('Phonebook','Referral','Location','Others')),
    source_descr      varchar(255),
    sales_potential   int,
    constraint clientprofile_pk primary key (profile_id),
    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile,
    constraint clientprofile_profile_fk foreign key (profile_id) references profile on delete cascade
);

-- Buldozer operator
create table boperator
(
    profile_id        int not null,
    clocksheets       smallint default 0,
    clocknumber       varchar(32),
    contractnumber    varchar(32),
    loan_amount       int,
    loan_balance      int,
    loan_repayment    int,
    rate              smallint,
    mon_normaltime    smallint default 8,
    mon_overtime      smallint default 0,
    mon_doubletime    smallint default 0,
    tue_normaltime    smallint default 8,
    tue_overtime      smallint default 0,
    tue_doubletime    smallint default 0,
    wen_normaltime    smallint default 8,
    wen_overtime      smallint default 0,
    wen_doubletime    smallint default 0,
    thu_normaltime    smallint default 8,
    thu_overtime      smallint default 0,
    thu_doubletime    smallint default 0,
    fri_normaltime    smallint default 8,
    fri_overtime      smallint default 0,
    fri_doubletime    smallint default 0,
    sat_normaltime    smallint default 8,
    sat_overtime      smallint default 0,
    sat_doubletime    smallint default 0,
    sun_normaltime    smallint default 8,
    sun_overtime      smallint default 0,
    sun_doubletime    smallint default 0,
    tot_norm_hours    smallint default 40,
    tot_overtime      smallint default 0,
    tot_doubletime    smallint default 0,
    contract_term     smallint default 12, -- 1|3|6|12|0 --
    contract_start    date,
    contract_end      date,
    constraint boperator_pk primary key (profile_id),
    constraint boperator_profile_fk foreign key (profile_id) references profile,
);

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
