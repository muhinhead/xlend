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
    constraint profile_pk primary key (profile_id)
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
    spouse_first_name varchar(32), 
    spouse_last_name  varchar(32), 
    spouse_birthday   date,
    spouse_email      varchar(80),
    source_type       varchar(10) check (source_type in('Phonebook','Referral','Location','Others')),
    source_descr      varchar(255),
    sales_potential   number(10),
    constraint clientprofile_pk primary key (profile_id),
    constraint clientprofile_profile_spers_fk foreign key (salesperson_id) references profile,
    constraint clientprofile_profile_fk foreign key (profile_id) references profile on delete cascade
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
       c.spouse_first_name,
       c.spouse_last_name,
       c.spouse_birthday,
       p.address1,
       p.address2,
       p.city,
       p.state,
       p.zip_code,
       p.phone as home_phone,
       p.cell_phone,
       p.email,
       c.spouse_email,
       c.source_type,
       c.source_descr,
       c.sales_potential
  from profile p, clientprofile c
 where c.profile_id = p.profile_id;
