alter table xtimesheet modify xsite_id int null;
alter table xtimesheet add xmachine_id int null;
alter table xtimesheet add constraint xtimesheet_xmachine_fk foreign key (xmachine_id) references xmachine (xmachine_id);
alter table xwage drop deduction;
drop function extractnum;
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
delimiter ;
drop function extractchars;
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
   return sub;
end;
|
delimiter ;

alter table xjobcard add vehicle_id1_day1 int;
alter table xjobcard add vehicle_id2_day1 int;
alter table xjobcard add vehicle_id3_day1 int;
alter table xjobcard add vehicle_id4_day1 int;
alter table xjobcard add vehicle_id5_day1 int;
alter table xjobcard add vehicle_id1_day2 int;
alter table xjobcard add vehicle_id2_day2 int;
alter table xjobcard add vehicle_id3_day2 int;
alter table xjobcard add vehicle_id4_day2 int;
alter table xjobcard add vehicle_id5_day2 int;
alter table xjobcard add vehicle_id1_day3 int;
alter table xjobcard add vehicle_id2_day3 int;
alter table xjobcard add vehicle_id3_day3 int;
alter table xjobcard add vehicle_id4_day3 int;
alter table xjobcard add vehicle_id5_day3 int;
alter table xjobcard add vehicle_id1_day4 int;
alter table xjobcard add vehicle_id2_day4 int;
alter table xjobcard add vehicle_id3_day4 int;
alter table xjobcard add vehicle_id4_day4 int;
alter table xjobcard add vehicle_id5_day4 int;
alter table xjobcard add vehicle_id1_day5 int;
alter table xjobcard add vehicle_id2_day5 int;
alter table xjobcard add vehicle_id3_day5 int;
alter table xjobcard add vehicle_id4_day5 int;
alter table xjobcard add vehicle_id5_day5 int;
alter table xjobcard add vehicle_id1_day6 int;
alter table xjobcard add vehicle_id2_day6 int;
alter table xjobcard add vehicle_id3_day6 int;
alter table xjobcard add vehicle_id4_day6 int;
alter table xjobcard add vehicle_id5_day6 int;
alter table xjobcard add vehicle_id1_day7 int;
alter table xjobcard add vehicle_id2_day7 int;
alter table xjobcard add vehicle_id3_day7 int;
alter table xjobcard add vehicle_id4_day7 int;
alter table xjobcard add vehicle_id5_day7 int;

alter table xjobcard add constraint xjobcard_xmachine_fk011 foreign key (vehicle_id1_day1) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk021 foreign key (vehicle_id2_day1) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk031 foreign key (vehicle_id3_day1) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk041 foreign key (vehicle_id4_day1) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk051 foreign key (vehicle_id5_day1) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk012 foreign key (vehicle_id1_day2) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk022 foreign key (vehicle_id2_day2) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk032 foreign key (vehicle_id3_day2) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk042 foreign key (vehicle_id4_day2) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk052 foreign key (vehicle_id5_day2) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk013 foreign key (vehicle_id1_day3) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk023 foreign key (vehicle_id2_day3) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk033 foreign key (vehicle_id3_day3) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk043 foreign key (vehicle_id4_day3) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk053 foreign key (vehicle_id5_day3) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk014 foreign key (vehicle_id1_day4) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk024 foreign key (vehicle_id2_day4) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk034 foreign key (vehicle_id3_day4) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk044 foreign key (vehicle_id4_day4) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk054 foreign key (vehicle_id5_day4) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk015 foreign key (vehicle_id1_day5) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk025 foreign key (vehicle_id2_day5) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk035 foreign key (vehicle_id3_day5) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk045 foreign key (vehicle_id4_day5) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk055 foreign key (vehicle_id5_day5) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk016 foreign key (vehicle_id1_day6) references xmachine (xmachine_id);
alter table xjobcard add constraint xjobcard_xmachine_fk026 foreign key (vehicle_id2_day6) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk036 foreign key (vehicle_id3_day6) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk046 foreign key (vehicle_id4_day6) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk056 foreign key (vehicle_id5_day6) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk017 foreign key (vehicle_id1_day7) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk027 foreign key (vehicle_id2_day7) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk037 foreign key (vehicle_id3_day7) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk047 foreign key (vehicle_id4_day7) references xmachine (xmachine_id);
#--alter table xjobcard add constraint xjobcard_xmachine_fk057 foreign key (vehicle_id5_day7) references xmachine (xmachine_id);


update dbversion set version_id = 33, version='0.33';
