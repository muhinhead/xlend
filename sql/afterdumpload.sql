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
delimiter |
create trigger tr_xemployee_beforeinsert
before insert on xemployee
for each row
begin
   set new.clock_numonly = extractnum(new.clock_num);
end;
|
delimiter |
create trigger tr_xemployee_beforeupdate
before update on xemployee
for each row
begin
   set new.clock_numonly = extractnum(new.clock_num);
end;
|
delimiter ;
