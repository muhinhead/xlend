package com.xlend.constants;

/**
 *
 * @author Nick Mukhin
 */
public class Selects {

    public static final String SELECT_FROM_USERS =
            "Select profile_id \"Id\","
            + "first_name \"First Name\",last_name \"Last Name\","
            + "city  \"City\", state  \"Province\", email \"E-mail\" "
            + " from v_userprofile";
    public static final String SELECT_FROM_SITES =
            "Select xsite_id \"Id\", name \"Site Name\", description \"Description\", "
            + "CASEWHEN(dieselsponsor,'Yes','No') \"Diesel Sponsored\", "
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\","
            + "CASEWHEN(is_active,'Yes','No') \"Active\" "
            + "from xsite order by is_active desc,upper(name)";
    public static final String SELECT_FROM_SITES_WEB =
            "Select xsite_id \"Id\", concat(name,'<a name=\"xsite',xsite_id,'\"></a>') \"Site Name\", description \"Description\", "
            + "CASEWHEN(dieselsponsor,'Yes','No') \"Diesel Sponsored\", "
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\","
            + "CASEWHEN(is_active,'Yes','No') \"Active\" "
            + "from xsite order by is_active desc,upper(name)";
    public static final String SELECT_ORDERISITES =
            "Select xsite_id \"Id\", name \"Site Name\", description \"Description\", "
            + "CASEWHEN(dieselsponsor,'Yes','No') \"Diesel Sponsored\", "
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\", "
            + "CASEWHEN(is_active,'Yes','No') \"Active\" "
            + "from xsite where xorder_id = # or xorder2_id = # or xorder3_id = #";
    public static final String SELECT_FROM_CLIENTS = "Select xclient_id \"Id\","
            + "clientcode \"Client Code\", companyname \"Company name\", "
            + "contactname \"Contact Name\", phonenumber \"Tel Nr.\", vatnumber \"Vat Nr.\" "
            + "from xclient order by upper(clientcode)";
    public static final String SELECT_CLIENTS4LOOKUP = "Select xclient_id \"Id\","
            + "clientcode \"Client Code\", companyname \"Company name\", contactname \"Contact person\" "
            + "from xclient order by upper(clientcode)";
    public static final String SELECT_FROM_CONTRACTS =
            "Select xcontract_id \"Id\", "
            + "contractref \"Ref.Nr\", xcontract.description \"Description\", xclient.companyname \"Company\" "
            + "from xcontract, xclient where xclient.xclient_id=xcontract.xclient_id";
    public static final String SELECT_CONTRACTS4CLIENT =
            "Select xcontract_id \"Id\", "
            + "contractref \"Ref.Nr\", description \"Description\" "
            + "from xcontract where xclient_id = #";
    public static final String SELECT_CONTRACTS4LOOKUP =
            "Select xcontract_id \"Id\", "
            + "contractref \"Ref.Nr\", description \"Description\" "
            + "from xcontract where xclient_id = #";
    public static final String SELECT_FROM_CONTRACTPAGE =
            "Select xcontractpage_id \"Id\", pagenum \"Ind.No\", description \"Notes\" "
            + "from xcontractpage where xcontract_id = # order by pagenum";
    public static final String SELECT_FROM_ORDERS =
            "Select xorder_id \"Id\", xclient.companyname \"Company\", "
            //            + "vatnumber \"Vat.number\", "
            //            + "regnumber \"Registration No\", "
            + "ordernumber \"Order No\", orderdate \"Order date\" "
            + "from xorder, xclient where xclient.xclient_id=xorder.xclient_id";
    public static final String SELECT_ORDERS4LOOKUP =
            "Select xorder_id \"Id\", xclient.companyname \"Company\", "
            //            + "vatnumber \"Vat.number\", "
            //            + "regnumber \"Registration No\", "
            + "ordernumber \"Order No\", orderdate \"Order Date\" "
            + "from xorder, xclient where xclient.xclient_id=xorder.xclient_id";
    public static final String SELECT_ORDERS4CONTRACTS =
            "Select xorder_id \"Id\", "
            //            + "vatnumber \"Vat.number\", "
            //            + "regnumber \"Registration No\", "
            + "ordernumber \"Order No\", orderdate \"Order date\" "
            + "from xorder where xcontract_id = #";
    public static final String SELECT_ORDERS4CLIENT =
            "Select xorder_id \"Id\", "
            //            + "vatnumber \"Vat.number\", "
            //            + "regnumber \"Registration No\", "
            + "ordernumber \"Order No\", orderdate \"Order date\" "
            + "from xorder where xclient_id = #";
    public static final String SELECT_FROM_QUOTATIONS =
            "Select xquotation_id \"Id\", xclient.companyname \"Company\", "
            + "rfcnumber \"RFC Nr\" "
            + "from xquotation, xclient where xclient.xclient_id=xquotation.xclient_id";
    public static final String SELECT_QUOTATIONS4CLIENTS =
            "Select xquotation_id \"Id\", rfcnumber \"RFC Nr\" "
            + "from xquotation where xclient_id = #";
    public static final String SELECT_QUOTATIONS4LOOKUP =
            "Select xquotation_id \"Id\", "
            + "rfcnumber \"RFC Nr\" "
            + "from xquotation where xclient_id = #";
    public static final String SELECTORDERITEMS =
            "Select xorderitem_id \"Id\", itemnumber \"Item\", "
            //            + "materialnumber \"Material Nr.\", "
            + "(select machtype from xmachtype where xmachtype_id=xorderitem.xmachtype_id) \"Machine Type\","
            + "deliveryreq \"Required\" "
            + "from xorderitem where xorderitem.xorder_id = #";
//    public static final String DISTINCT_MACHINETYPES =
//            "Select distinct machinetype from xorderitem";
    public static final String DISTINCT_INSURANCETYPES =
            "Select distinct insurance_tp from xmachine";
//    public static final String DISTINCT_MEASUREITEMS =
//            "Select distinct measureitem from xorderitem";
    public static final String SELECT_EMPLOYEE4LOOKUP =
            "Select xemployee_id \"Id\",clock_num \"Clock Nr\","
            + "concat(first_name,' ',sur_name,if(length(ifnull(nick_name,''))=0,'',concat(' (',nick_name,')'))) \"Name\", "
            + "val \"Wage Cat.\" "
            //            + ",(select s.name from xsite s,xopmachassing a "
            //            + " where a.xemployee_id=xemployee.xemployee_id and s.xsite_id=a.xsite_id and date_end is null"
            //            + " and xopmachassing_id=(select max(xopmachassing_id) from xopmachassing "
            //            + " where xemployee_id=xemployee.xemployee_id and xsite_id=a.xsite_id and date_end is null)) \"on site\" "
            //            + ",(select concat(m.classify,m.tmvnr) from xmachine m,xopmachassing a where a.xemployee_id=xemployee.xemployee_id and m.xmachine_id=a.xmachine_id and date_end is null "
            //            + "and xopmachassing_id=(select max(xopmachassing_id) from xopmachassing where xemployee_id=xemployee.xemployee_id"
            //            + " and xmachine_id=m.xmachine_id and date_end is null)) \"on machine\" "
            + "from xemployee left join cbitems on cbitems.name='wage_category' and cbitems.id=xemployee.wage_category "
            + "where "
//            + "clock_num!='000' and "
            + "ifnull(deceased,0)=0 and ifnull(dismissed,0)=0 and ifnull(absconded,0)=0 and ifnull(resigned,0)=0 "
            + "order by clock_numonly,clock_num";
    public static final String SELECT_FROM_EMPLOYEE =
            "Select xemployee_id \"Id\",clock_num \"Clock Nr\","
            + "id_num \"ID Number\",first_name \"First Name\", "
            + "sur_name \"Surname\", phone0_num \"Phone Nr\", val \"Wage Cat.\" "
            + ",(select max(s.name) from xsite s,xopmachassing a "
            + " where a.xemployee_id=xemployee.xemployee_id and s.xsite_id=a.xsite_id and date_end is null"
            + " and xopmachassing_id=(select max(xopmachassing_id) from xopmachassing "
            + " where xemployee_id=xemployee.xemployee_id and xsite_id=a.xsite_id and date_end is null)) \"Now on site\" "
            //            + ",(select max(concat(m.classify,m.tmvnr)) from xmachine m,xopmachassing a where a.xemployee_id=xemployee.xemployee_id and m.xmachine_id=a.xmachine_id and date_end is null "
            //            + "and xopmachassing_id=(select max(xopmachassing_id) from xopmachassing where xemployee_id=xemployee.xemployee_id"
            //            + " and xmachine_id=m.xmachine_id and date_end is null)) \"Now on machine\" "
            + "from xemployee left join cbitems on cbitems.name='wage_category' and cbitems.id=xemployee.wage_category "
            + "where xemployee_id>0 "
            + "order by clock_numonly,clock_num";
    public static final String SELECT_FROM_SALEMPLOYEE_EXCLUDING =
            "Select xemployee_id \"Id\",clock_num \"Clock Nr\","
            + "id_num \"ID Number\",first_name \"First Name\", "
            + "sur_name \"Surname\", phone0_num \"Phone Nr\" from xemployee "
            + "where ifnull(wage_category,1)=1 and xemployee_id not in(#) order by clock_num";
    public static final String SELECT_FROM_TIMESHEET =
            "Select t.xtimesheet_id \"Id\", to_char(t.weekend,'DD/MM/YYYY') \"Week Ending\", "
            + "e.clock_num \"Clock Nr\", e.first_name \"First Name\", "
            + "e.sur_name \"Surname\", s.name \"Site\", o.ordernumber \"Order Nr\" "
            + "from xtimesheet t,xemployee e, xsite s, xorder o "
            + "where t.xemployee_id=e.xemployee_id "
            + "and t.xsite_id=s.xsite_id and t.xorder_id=o.xorder_id order by xtimesheet_id desc";
    public static final String SELECT_TIMESHEETS4EMPLOYEE =
            "Select t.xtimesheet_id \"Id\", to_char(t.weekend,'DD/MM/YYYY') \"Week Ending\", "
            + "s.name \"Site\", "
            + "(select max(ordernumber) from xorder where xorder_id=t.xorder_id) \"Order Nr\" "
            + "from xtimesheet t, xsite s "
            + "where t.xsite_id=s.xsite_id and t.xemployee_id = #";
    public static final String SELECT_WAGE4TIMESHEET =
            "Select xwage_id \"Id\", day \"Day\", normaltime \"Normal Time\", "
            + "overtime \"Over Time\", doubletime \"Double Time\", stoppeddetails \"Details\" "
            + "from xwage where xtimesheet_id = #";
    public static final String SELECT_ALLSITES4LOOKUP =
            "Select xsite_id \"Id\", name \"Site Name\","
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\", CASEWHEN(is_active,'Yes','No') \"Active\" "
            + "from xsite order by sitetype,upper(name)";
    public static final String SELECT_SITES4LOOKUP =
            "Select xsite_id \"Id\", name \"Site Name\","
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\", CASEWHEN(is_active,'Yes','No') \"Active\" "
            + "from xsite where is_active=1 order by sitetype,upper(name)";
    public static final String SELECT_DEPOTS4LOOKUP =
            "Select xsite_id \"Id\", name \"Site Name\", "
            + "(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types') \"Type of Site\" "
            + "from xsite where is_active=1 and sitetype='D' order by upper(name)";
//    public static final String SELECT_FROM_LOWBEDS =
//            "Select xlowbed_id \"IId\", "
//            + "concat(m.classify,m.tmvnr) \"TMVnr\", "
//            + "(select t1.machtype from xmachtype t1 where t1.xmachtype_id=m.xmachtype_id) \"Machine Type\", "
////            + "(select reg_nr from xmachine where xmachine.xmachine_id=xlowbed.xmachine_id) \"Reg.Nr\","
////            + "t1.machtype \"Machine Type\", "
//            + "m.reg_nr \"Reg.Nr\", "
//            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xlowbed.driver_id) \"Driver\", "
//            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xlowbed.assistant_id) \"Assistant\" "
//            + "from xlowbed,xmachine m where m.xmachine_id=xlowbed.xmachine_id";
    public static final String SELECT_FROM_LOWBEDS =
            "Select xlowbed_id \"Id\","
            + "concat(xmachine.classify,xmachine.tmvnr) \"TMVnr\","
            + "(select machtype from xmachtype where xmachtype_id=xmachine.xmachtype_id) \"Machine Type\","
            + "xmachine.reg_nr \"Reg.Nr\","
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xlowbed.driver_id) \"Driver\","
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xlowbed.assistant_id) \"Assistant\" "
            + "from xlowbed, xmachine "
            + "where xmachine.xmachine_id=xlowbed.xmachine_id";
    public static final String SELECT_ALL_LOWBEDS =
            "select l.xlowbed_id \"Id\",concat(m.classify,m.tmvnr) \"Machine\", "
            + "concat(d.clock_num,' ',d.first_name) \"Driver\", concat(a.clock_num,' ',a.first_name) \"Assistant\" "
            + "from xlowbed l, xmachine m, xemployee d, xemployee a where l.xmachine_id=m.xmachine_id "
            + "and l.driver_id=d.xemployee_id and l.assistant_id=a.xemployee_id";
    public static final String SELECT_FROM_MACHINE =
            "Select xmachine_id \"IId\", concat(m.classify,m.tmvnr) \"TMVnr\", "
            + "(select machtype from xmachtype where xmachtype_id=m.xmachtype_id) \"Machine Type\", reg_nr \"Reg.Nr\", "
            + "expdate \"License Exp.Date\", CASEWHEN(expdate is null,'',CASEWHEN(expdate<now(),'Expired','Normal')) \"License Status\" "
            + "from xmachine m  "
            + "where m.classify='M' order by m.classify,cast(m.tmvnr as decimal)";
    public static final String SELECTMACHINESONSITE =
            "Select xmachineonsate_id \"Id\", "
            + "concat(xmachine.classify,tmvnr) \"Fleet Nr\", "
            + "(select machtype from xmachtype where xmachtype_id=xmachine.xmachtype_id) \"Machine\", "
            + "(select machtype from xmachtype where xmachtype2_id=xmachine.xmachtype_id) \"Type\", xmachine.reg_nr \"Reg.Nr\", "
            + "concat(xemployee.clock_num,' ',substr(xemployee.first_name,1,1),'.',xemployee.sur_name) \"Operator\", "
            + "estdate \"Est.Date\", deestdate \"De-Est.Date\" "
            + "from xmachineonsite,xmachine,xemployee "
            + "where xmachine.xmachine_id=xmachineonsite.xmachine_id "
            + "and xemployee.xemployee_id=xmachineonsite.xemployee_id "
            + "and xsite_id = #";
    public static final String MACHINETVMS =
            "Select xmachine_id,concat(classify,tmvnr) from xmachine where not classify is null "
            + "order by classify,cast(tmvnr as decimal)";
//    public static final String notAssignedMachinesCondition = "xmachine_id not in (select xmachine_id from xopmachassing where date_end is null "
//            + " and xsite_id not in(select xsite_id from xsite where sitetype='D'))";
//    public static final String notAssignedOperatorCondition = "xemployee_id not in (select xemployee_id from xopmachassing where date_end is null "
//            + " and xsite_id not in(select xsite_id from xsite where sitetype='D'))";
//    public static final String NOT_ASSIGNED_MACHINES =
//            "Select xmachine_id,concat(classify,tmvnr) from xmachine where not classify is null "
//            + "and " + notAssignedMachinesCondition + " order by classify,cast(tmvnr as decimal)";
    public static final String ALLMACHINETVMS =
            "Select xmachine_id,concat(classify,tmvnr) from xmachine order by classify,cast(tmvnr as decimal)";
    public static final String FREEMACHINETVMS =
            "Select xmachine_id,concat(classify,tmvnr) \"TMVNR\" from xmachine "
            + "where not classify is null "
            + "and xmachine_id not in (select xmachine_id from xmachineonsite "
            + "where deestdate is null or deestdate > CURDATE()) order by classify,cast(tmvnr as decimal)";
    public static final String EMPLOYEES =
            "Select xemployee_id, concat(clock_num,' ',first_name,' ',sur_name) \"Operator\" "
            + "from xemployee order by sur_name";
    public static final String FREEEMPLOYEES =
            "Select xemployee_id, concat(clock_num,' ',first_name) \"Operator\" "
            + "from xemployee where xemployee_id not in (select xemployee_id from xmachineonsite "
            + "where deestdate is null or deestdate > CURDATE()) order by clock_num";
    public static final String SELECT_MASCHINES4LOOKUP =
            "Select xmachine_id \"Id\", concat(m.classify,tmvnr) \"Fleet Nr\", reg_nr \"Reg.Nr\", "
            + "(select max(machtype) from xmachtype where xmachtype_id=m.xmachtype_id) \"Machine\","
            + "(select max(s.name) from xsite s,xopmachassing a where s.xsite_id=a.xsite_id and a.xmachine_id=m.xmachine_id and date_end is null and "
            + "xopmachassing_id=(select max(xopmachassing_id) from xopmachassing where xsite_id=a.xsite_id and xmachine_id=m.xmachine_id and date_end is null)) \"On site\" "
            + "from xmachine m where not classify is null ";
    public static final String DIESELCARTS = "select xdieselcart_id, fleet_nr from xdieselcart order by fleet_nr";
    public static final String SUPPLIERS = "Select xsupplier_id \"Id\",substr(companyname,1,20) \"Company Name\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_FROM_STORES =
            "select xstocks_id, name from xstocks";
    public static final String SELECT_FROM_SUPPLIERS =
            "Select xsupplier_id \"Id\",companyname \"Company Name\", vatnr \"Vat Nr\", "
            + "company_regnr \"Reg.Nr\", contactperson \"Contact Person\", "
            + "phone \"Tel Nr\", fax \"Fax Nr\", cell \"Cell Nr\", email \"Email\", credit_limit \"Credit Limit\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_SUPPLIERS4LOOKUP =
            "Select xsupplier_id \"Id\",companyname \"Company Name\", vatnr \"Vat Nr\", "
            + "company_regnr \"Reg.Nr\", credit_limit \"Cred.Limit\", contactperson \"Contact Person\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_DIESELCARTS4LOOKUP =
            "select xdieselcart_id\"Id\",fleet_nr \"Fleet Nr\", reg_nr \"Reg Nr\", "
            + "chassis_nr \"Chassis Nr\" from xdieselcart order by fleet_nr";
    public static final String SELECT_FROM_DIESELPURCHASES =
            "select xdieselpurchase_id \"Id\", "
            + "(select companyname from xsupplier where xsupplier_id=xdieselpurchase.xsupplier_id) \"Supplier\", "
            + "to_char(purchase_date,'DD/MM/YYYY') \"Date of Purchase\", litres \"Litres\", "
            + "ROUND(rand_factor,2) \"Rand Factor\", ROUND(litres*rand_factor,2) \"Total R\" "
            + " from xdieselpurchase order by purchase_date desc";
//    public static final String SELECT_FROM_DIESELPCHS =
//            "Select xdieselpchs_id \"Id\", companyname \"Supplier\", purchased \"Date\", "
//            + "amount_liters \"Amount(liters)\", amount_rands \"Amount(Rands)\" "
//            + "from xdieselpchs,xsupplier where xsupplier.xsupplier_id=xdieselpchs.xsupplier_id";
//    public static final String SELECT_FROM_DIESELCARD =
//            "Select xdieselcard_id \"Id\", carddate \"Date\", "
//            + "concat(classify,tmvnr) \"Machine\", "
//            + "concat(substr(first_name,1,1),'.',sur_name,' (',clock_num,')') \"Operator\", "
//            + "name \"Site\", amount_liters \"Liters\" from xdieselcard,xmachine,xemployee,xsite "
//            + "where xdieselcard.xmachine_id=xmachine.xmachine_id and xdieselcard.xsite_id=xsite.xsite_id "
//            + "and xdieselcard.operator_id=xemployee.xemployee_id";
    public static final String SELECT_FROM_DIESELCARTS =
            "select xdieselcart_id \"Id\", fleet_nr \"Fleet Nr\", reg_nr \"Reg Nr\", litres \"Litres\","
            + "CASEWHEN(expdate is null,'',CASEWHEN(expdate<now(),'Expired','Normal')) \"License Status\"  from xdieselcart";
    public static final String SELECT_FROM_DIESELCARTISSUES =
            "select xdieselcartissue_id \"Id\", to_char(issue_date,'DD/MM/YYYY') \"Date of Issue\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=driver_id) \"Driver\","
            + "(select fleet_nr from xdieselcart where xdieselcart_id=xdieselcartissue.xdieselcart_id) \"Fleet Nr of Diesel Cart\","
            + "liters \"Liters\","
            + "(select companyname from xsupplier where xsupplier_id=xdieselcartissue.xsupplier_id) \"From Supplier\" "
            + " from xdieselcartissue order by issue_date desc";
    public static final String SELECT_DIESELCARTISSUES =
            "select xdieselcartissue_id \"Id\", to_char(issue_date,'DD/MM/YYYY') \"Date\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=driver_id) \"Driver\","
            + "liters \"Liters\","
            + "(select companyname from xsupplier where xsupplier_id=xdieselcartissue.xsupplier_id) \"From Supplier\" "
            + " from xdieselcartissue where xdieselcart_id=# order by issue_date desc";
    public static final String SELECT_FROM_DIESEL2PLANT =
            "select xdiesel2plant_id \"Id\",to_char(last_date,'DD/MM/YYYY') \"Date\", "
            + "(select fleet_nr from xdieselcart where xdieselcart_id=xdiesel2plant.xdieselcart_id) \"Fleet Nr of Diesel Cart\","
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=driver_id) \"Driver\" "
            + "from xdiesel2plant order by last_date desc";
    public static final String SELECT_DIESEL2PLANT = 
            "select i.xdiesel2plantitem_id \"Id\", to_char(i.add_date,'DD/MM/YYYY') \"Date\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=driver_id) \"Driver\", "
            + "(select name from xsite where xsite_id=i.xsite_id) \"Site\","
            + "(select concat(classify,tmvnr) from xmachine where xmachine_id=i.xmachine_id) \"Machine\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=i.operator_id) \"Operator\", "
            + "i.liters \"Liters\" "
            + "from xdiesel2plantitem i, xdiesel2plant dp where dp.xdiesel2plant_id=i.xdiesel2plant_id "
            + "and dp.xdieselcart_id=# order by add_date desc";
    public static final String PAYMETHODS =
            "Select xpaidmethod_id, method from xpaidmethod order by xpaidmethod_id";
    public static final String SELECT_FROM_CONSUMABLES =
            "Select xconsume_id \"Id\", sup.companyname \"Supplier\", "
            + "concat(mac.classify,mac.tmvnr) \"Machine\", concat(substr(req.first_name,1,1),"
            + "'.',req.sur_name,' (',req.clock_num,')') \"Requested by\", "
            + "con.invoicedate \"Inv.Date\", "
            + "con.amount_rands \"Vat Incl Amt (R)\", "
            + "con.description \"Description\" "
            + "from xconsume con, xsupplier sup, xmachine mac, xemployee req "
            + "where con.xsupplier_id=sup.xsupplier_id and con.xmachine_id=mac.xmachine_id "
            + "and con.requester_id=req.xemployee_id";
    public static final String SELECT_SUPPLIERS_CONSUMABLES =
            "Select xconsume_id \"Id\", "
            + "concat(mac.classify,mac.tmvnr) \"Machine\", concat(substr(req.first_name,1,1),"
            + "'.',req.sur_name,' (',req.clock_num,')') \"Requested by\", "
            + "con.invoicedate \"Inv.Date\", con.invoicenumber \"Inv.Nr\", con.amount_rands \"Amount(R)\" "
            + "from xconsume con, xmachine mac, xemployee req "
            + "where con.xsupplier_id=# and con.xmachine_id=mac.xmachine_id and con.xpaidmethod_id=4 "
            + "and con.requester_id=req.xemployee_id";
    public static final String SELECT_FROM_CONSUMABLES4MACHINE =
            "Select xconsume_id \"Id\", sup.companyname \"Supplier\", "
            + "concat(mac.classify,mac.tmvnr) \"Machine\", concat(substr(req.first_name,1,1),"
            + "'.',req.sur_name,' (',req.clock_num,')') \"Requested by\", "
            + "con.invoicedate \"Inv.Date\", con.invoicenumber \"Inv.Nr\", con.partnumber \"Part.Nr\" "
            + "from xconsume con, xsupplier sup, xmachine mac, xemployee req "
            + "where con.xsupplier_id=sup.xsupplier_id and con.xmachine_id=mac.xmachine_id "
            + "and con.requester_id=req.xemployee_id and mac.xmachine_id=#";
    public static final String SELECT_CONSUMABLES4BREAKDOWN =
            "Select xconsume_id, concat('Invoice Nr:',invoicenumber), partnumber from xconsume where xmachine_id = #";
    public static final String SELECT_CONSUMABLES4LOOKUP =
            "Select xconsume_id, invoicenumber from xconsume where xsupplier_id = #";
    public static final String SELECT_FROM_BREAKDOWNS =
            "Select xbreakdown_id \"Id\", concat(mac.classify,mac.tmvnr) \"Machine\", s.name \"Site\", bd.repairdate \"Repair Date\" "
            + "from xbreakdown bd, xmachine mac, xsite s where bd.xmachine_id=mac.xmachine_id and s.xsite_id=bd.xsite_id";
    public static final String SELECT_FROM_WAGES =
            "Select xwagesum.xwagesum_id \"List Id\",to_char(weekend,'DD/MM/YYYY') \"Week ending\", "
            //            + "xemployee.clock_num \"Clock Nr.\","
            //            + " concat(substr(xemployee.first_name,1,1),'.',xemployee.sur_name,' (',xemployee.clock_num,')') \"Name\","
            //            + "weeklywage \"Weekly Wage\", "
            + "sum(normaltime) \"Total Hours\",sum(overtime) \"Total Overtime\", sum(doubletime) \"Total Doubletime\","
            + "sum(weeklywage) \"Total Weekly Wage\" "
            + "from xwagesum,xwagesumitem "//,xemployee "
            + "where xwagesumitem.xwagesum_id=xwagesum.xwagesum_id group by xwagesum.xwagesum_id,weekend";//and xwagesumitem.xemployee_id=xemployee.xemployee_id";
    public static final String NOTFIXED_TIMESHEETDATES =
            "Select distinct weekend from xtimesheet where weekend not in (select weekend from xwagesum)";
    public static final String SELECT_FROM_PAYMENTS =
            "Select xpayment_id \"Id\", companyname \"Supplier\", to_char(paydate,'DD/MM/YYYY') \"Pay Date\", round(ammount,2) \"Amount\", val \"Paid From\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=paydby_id) \"Paid By\" "
            + "from xpayment, xsupplier, cbitems "
            + "where xsupplier.xsupplier_id=xpayment.xsupplier_id and cbitems.id=xpayment.paidfrom "
            + "order by paydate desc";
    public static final String SELECT_SUPPLIERS_PAYMENTS =
            "Select xpayment_id \"Id\", to_char(paydate,'DD/MM/YYYY') \"Pay Date\", round(ammount,2) \"Amount\", val \"Paid From\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=paydby_id) \"Paid By\" "
            + "from xpayment, cbitems "
            + "where cbitems.id=xpayment.paidfrom and xsupplier_id=# "
            + "order by paydate desc";
    public static final String SELECT_FROM_CREDITORS =
            "Select xcreditor_id \"Id\", companyname \"Supplier\", accnum \"Account Nr.\", "
            + "(select invoicenumber from xconsume where xconsume_id=xc.xconsume_id) \"Invoice Nr.\", round(invoiceammount,2) \"Invoice Ammt.\", "
            + "paid \"Paid\", "
            + "cbitems.val \"Paid from\", "
            + "round((select ifnull(sum(amount_rands),0) from xconsume where xsupplier_id=xc.xsupplier_id and xpaidmethod_id=4)+"
            //(select ifnull(sum(invoiceammount),0) from xcreditor where xsupplier_id=xc.xsupplier_id and (paid is null or not paid))+"
            + "(select ifnull(sum(ammount),0) from xfuel where xsupplier_id=xc.xsupplier_id  and (iscache is null or not iscache))"
            + "-(select ifnull(sum(ammount),0) from xpayment where xsupplier_id=xc.xsupplier_id),2) "
            + " \"Outstanding Ammt.\" "
            + "from xcreditor xc, xsupplier, cbitems "
            + "where xsupplier.xsupplier_id=xc.xsupplier_id and cbitems.name='paidfrom' and cbitems.id=ifnull(paidfrom,0)";
    public static final String SELECT_FROM_FUELS =
            "Select xfuel_id \"Id\", ROUND(ammount,2) \"Amount\", "
            + "(Select name from xsite where xsite_id=xfuel.xsite_id) \"Site\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xfuel.issuedby_id) \"Issued By\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xfuel.issuedto_id) \"Issued To\", "
            + "(Select companyname from xsupplier where xsupplier_id=xfuel.xsupplier_id) \"Supplier\", iscache \"Cache\" "
            + "from xfuel";
    public static final String SELECT_SUPPLIERS_FUELS =
            "Select xfuel_id \"Id\", ROUND(ammount,2) \"Amount\", "
            + "(Select name from xsite where xsite_id=xfuel.xsite_id) \"Site\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xfuel.issuedby_id) \"Issued By\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xfuel.issuedto_id) \"Issued To\" "
            + "from xfuel where xsupplier_id=#";
    public static final String SELECT_TRIPS =
            "Select xtrip_id \"Id\", trip_date \"Date\" ,"
            + "(Select name from xsite where xsite_id=xtrip.fromsite_id) \"From Site\", "
            + "(Select name from xsite where xsite_id=xtrip.tosite_id) \"To Site\" "
            + "from xtrip where xlowbed_id=# order by trip_date desc";
    public static final String SELECT_ALL_TRIPS =
            "Select t.xtrip_id \"Id\", to_char(t.trip_date,'DD/MM/YYYY') \"Date\", "
            + "concat(m.classify,m.tmvnr) \"Machine\", "
            + "concat(d.clock_num,' ',d.first_name) \"Driver\", "
            + "concat(a.clock_num,' ',a.first_name) \"Assistant\", "
            + "s1.name \"From Site\", s2.name \"To Site\" "
            + "from xtrip t, xlowbed l, xmachine m, xemployee d, xemployee a, xsite s1, xsite s2 "
            + "where t.xlowbed_id=l.xlowbed_id and m.xmachine_id=l.xmachine_id "
            + "and d.xemployee_id=l.driver_id and a.xemployee_id=l.assistant_id and s1.xsite_id=t.fromsite_id and s2.xsite_id=t.tosite_id";
//            "Select xtrip_id \"Id\", trip_date \"Date\" ,"
//            + "(Select concat(m.classify,m.tmvnr) from xmachine m,xlowbed l where l.xmachine_id=m.xmachine_id and l.xlowbed_id=xtrip.xlowbed_id) \"Machine\", "
//            + "(Select concat(e.clock_num,' ',e.first_name) from xemployee e, where xemployee_id=) \"Driver\", "
//            + "(Select name from xsite where xsite_id=xtrip.fromsite_id) \"From Site\", "
//            + "(Select name from xsite where xsite_id=xtrip.tosite_id) \"To Site\" "
//            + "from xtrip where xlowbed_id=# order by trip_date desc";
    public static final String SELECT_FROM_ABSENTEISM =
            "Select xabsenteeism_id \"Id\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xabsenteeism.xemployee_id) \"Employee\", "
            + "to_char(absentdate,'DD/MM/YYYY') \"Date\", "
            + "(Select name from xsite where xsite_id=xabsenteeism.xsite_id) \"Site\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xabsenteeism.xmachine_id) \"Machine\" "
            + "from xabsenteeism";
    public static final String SELECT_ABSENTEISM4EMPLOYEE =
            "Select xabsenteeism_id \"Id\", "
            + "absentdate \"Date\", "
            + "(Select name from xsite where xsite_id=xabsenteeism.xsite_id) \"Site\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xabsenteeism.xmachine_id) \"Machine\" "
            + "from xabsenteeism where xemployee_id=#";
    public static final String SELECT_FROM_ISSUING =
            "Select xissuing_id \"Id\", issueddate \"Date\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xissuing.operator_id) \"Operator\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xissuing.xmachine_id) \"Machine\" "
            + " from xissuing";
    public static final String SELECT_FROM_TRIPSHEET =
            "Select xtripsheet_id \"Id\", tripdate \"Trip Date\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xtripsheet.driver_id) \"Driver\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xtripsheet.authorized_id) \"Authorized By\" "
            + "from xtripsheet";
    public static final String SELECT_FROM_ACCOUNTS =
            "Select xaccount_id \"Id\", accname \"Name\", accnumber \"Number\", bank \"Bank\", branch \"Branch\" "
            + "from xaccounts";
    public static final String SELECT_FROM_BANKBALANCE =
            "Select xbankbalance_id \"Id\", to_char(balancedate,'DD/MM/YYYY HH24:MI') \"Date/time\", totalvalue "
            + "from xbankbalance order by balancedate";
    public static final String SELECT_FROM_SITE_DIARY =
            "Select xsitediary_id \"Id\", to_char(diarydate,'DD/MM/YYYY') \"Date\", "
            + "(Select name from xsite where xsite_id=xsitediary.xsite_id) \"Site\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xsitediary.manager_id) \"Manager\" "
            + "from xsitediary";
    public static final String SELECT_FROM_APP4LEAVE =
            "Select xappforleave_id \"Id\", to_char(appdate,'DD/MM/YYYY') \"Date\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xappforleave.applicant_id) \"Applicant\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xappforleave.approvedby_id) \"Approved\","
            + "fromdate \"From\", todate \"To\" "
            + "from xappforleave";
    public static final String SELECT_APP4LEAVE4EMPL =
            "Select xappforleave_id \"Id\", to_char(appdate,'DD/MM/YYYY') \"Date\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xappforleave.approvedby_id) \"Approved\", "
            + "fromdate \"From\", todate \"To\" "
            + "from xappforleave where applicant_id=#";
    public static final String SELECT_FROM_LOANS =
            "Select xloans_id \"Id\", to_char(loandate,'DD/MM/YYYY') \"Date\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xloans.requestedby_id) \"Requested by\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xloans.authorizedby_id) \"Authorized by\", "
            + "to_char(issueddate,'DD/MM/YYYY') \"Date issued\", amount \"Amount\" from xloans";
    public static final String SELECT_LOANS4EMPLOYEE =
            "Select xloans_id \"Id\", to_char(loandate,'DD/MM/YYYY') \"Date\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xloans.authorizedby_id) \"Authorized by\", "
            + "to_char(issueddate,'DD/MM/YYYY') \"Date issued\", amount \"Amount\" "
            + "from xloans where requestedby_id=# order by issueddate";
    public static final String SELECT_FROM_INCIDENTS =
            "Select xincidents_id \"Id\", to_char(reportdate,'DD/MM/YYYY') \"Date\", incidentdate \"Date of incident\", "
            + "(Select name from xsite where xsite_id=xincidents.xsite_id) \"Site\", "
            + "estimated_cost \"Estimated cost\", lost_income \"Lost income\" "
            + "from xincidents where requestedby_id=# order by incidentdate";
    public static final String SELECT_FROM_SALARYLISTS =
            "Select xsalarylist_id \"Id\", to_char(payday,'DD/MM/YYYY') \"Date\", "
            + "(Select sum(amount) from xsalary where xsalarylist_id=xsalarylist.xsalarylist_id) \"Total amount\" "
            + "from xsalarylist order by payday desc";
    public static final String SELECT_FROM_OPCLOCKSHEET =
            "Select xopclocksheet_id \"Id\", to_char(sheet_date,'MM/YYYY') \"Month\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xopclocksheet.xemployee_id) \"Operator\", "
            + "(Select name from xsite where xsite_id=xopclocksheet.xsite_id) \"Site\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xopclocksheet.xmachine_id) \"Machine/Truck\" "
            + "from xopclocksheet";
    public static final String SELECT_FROM_JOBCARDS =
            "Select xjobcard_id \"Id\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xjobcard.xemployee_id) \"Name\", "
            + "week_ending \"Week Ending\" from xjobcard";
    public static final String SELECT_FROM_HOURCOMPARE =
            "Select xhourcompare_id \"Id\", MONTHNAME(month_year) \"Month\", YEAR(month_year) \"Year\", "
            + "(Select name from xsite where xsite_id=xhourcompare.xsite_id) \"Site\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xhourcompare.operator_id) \"Operator\", "
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xhourcompare.xmachine_id) \"Machine\" "
            + "from xhourcompare";
    public static final String SELECT_BREAKDOWNCONSUMES =
            "Select xbreakconsume.xbreakconsume_id \"Id\", xsupplier.companyname \"Supplier\", "
            + " xconsume.invoicedate \"Inv.Date\", xconsume.invoicenumber \"Inv.Nr\", amount_liters \"Amt.liters\",amount_rands \"Amt.R\" "
            + " from xbreakconsume,xconsume,xsupplier where xconsume.xconsume_id=xbreakconsume.xconsume_id "
            + " and xsupplier.xsupplier_id=xconsume.xsupplier_id and xbreakconsume.xbreakdown_id = #";
    public static final String SELECT_MACHTYPES =
            "Select xmachtype_id \"Id\", machtype \"Type Name\", classify \"Classify\""
            + " from xmachtype where parenttype_id is null ";
    public static final String SELECT_MACHSUBTYPES =
            "Select xmachtype_id \"Id\", machtype \"Type Name\", classify \"Classify\""
            + " from xmachtype where not parenttype_id is null";
    public static final String SELECT_FROM_PAIDMETHODS =
            "Select xpaidmethod_id \"Id\", method \"Method\" from xpaidmethod order by xpaidmethod_id";
    public static final String SELECT_FROM_PAYFROM =
            "select cbitem_id \"Id\", id \"Code\", val \"Pay From\" from cbitems where name='paidfrom'";
    public static final String SELECT_FROM_WAGECATEGORY =
            "select cbitem_id \"Id\", id \"Code\", val \"Wage Category\" from cbitems where name='wage_category'";
    public static final String SELECT_FROM_SITETYPES =
            "select cbitem_id \"Id\", substr(val,1,1) \"Code\", val \"Site Type\" from cbitems where name='site_types'";
    public static final String SELECT_FROM_RATEDMACHINES =
            "select cbitem_id \"Id\", id \"Code\", val \"Machine\" from cbitems where name='rated_machines'";
    public static final String SELECT_FROM_MACHINERANTALRATE =
            "select xmachrentalrate_id \"Id\", actual_date \"Date\", ROUND(diesel_price,2) \"Diesel Price\", ROUND(factor,2) \"Factor\" from xmachrentalrate";
    public static final String SELECT_TRANSSCHEDULE_BY_DATE =
            "select to_char(date_required,'DD/MM/YYYY') \"Date\", count(*) \"Qty\" from xtransscheduleitm group by date_required";
    public static final String SELECT_FROM_XPPETYPE =
            "select xppetype_id \"Id\", xppetype \"PPE type\" from xppetype";
    public static final String GET_TRANSSCHEDULE_BY_DATE =
            "select to_char(date_required,'DD/MM/YYYY'), count(*) from xtransscheduleitm group by date_required";
    public static final String SELECT_TRANSSCHEDULE_BY_DATE_AND_MACHINE =
            "select to_char(i.date_required,'DD/MM/YYYY') \"Date\", concat(m.classify,m.tmvnr) \"Machine\", count(*) \"Qty\" "
            + "from xtransscheduleitm i,xmachine m where m.xmachine_id=i.machine_id group by \"Date\",i.machine_id";
    public static final String SELECT_TRANSSCHEDULE_BY_DATE_AND_FROMSITE =
            "select to_char(i.date_required,'DD/MM/YYYY') \"Date\", s.name \"Site (source)\", count(*) \"Qty\" "
            + "from xtransscheduleitm i,xsite s where i.site_from_id=s.xsite_id group by i.date_required,s.name";
    public static final String SELECT_TRANSSCHEDULE_BY_DATE_AND_TOSITE =
            "select to_char(i.date_required,'DD/MM/YYYY') \"Date\", s.name \"Site (target)\", count(*) \"Qty\" "
            + "from xtransscheduleitm i,xsite s where i.site_to_id=s.xsite_id group by i.date_required,s.name";
    public static final String SELECT_EMPLOYEE_ASSIGNMENTS =
            "select xopmachassing_id \"Id\", to_char(date_start,'DD/MM/YYYY') \"from\", to_char(date_end,'DD/MM/YYYY') \"to\","
            + "(Select concat(name,' (',(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types'),')') "
            + "   from xsite where xsite_id=xopmachassing.xsite_id) \"Site\","
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xopmachassing.xmachine_id) \"Machine\" "
            + "from xopmachassing where xemployee_id=# order by xopmachassing_id desc";
    public static final String SELECT_MACHINE_ASSIGNMENTS =
            "select xopmachassing_id \"Id\", to_char(date_start,'DD/MM/YYYY') \"from\", to_char(date_end,'DD/MM/YYYY') \"to\","
            + "(Select concat(name,'(',(select min(val) from cbitems where substr(val,1,1)=sitetype and name='site_types'),')') from xsite where xsite_id=xopmachassing.xsite_id) \"Site\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xopmachassing.xemployee_id) \"Operator\" "
            + "from xopmachassing where xmachine_id=# order by xopmachassing_id desc";
    public static final String SELECT_SITE_ASSIGNMENTS =
            "select xopmachassing_id \"Id\", to_char(date_start,'DD/MM/YYYY') \"from\", "
            //            + "to_char(date_end,'DD/MM/YYYY') \"to\","
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xopmachassing.xmachine_id) \"Machine\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xopmachassing.xemployee_id) \"Operator\" "
            + "from xopmachassing where xsite_id=# and date_end is null order by xopmachassing_id desc";
    public static final String activeEmployeeCondition = //"clock_num!='000' and " +
            " ifnull(deceased,0)=0 and ifnull(dismissed,0)=0 and ifnull(absconded,0)=0 and ifnull(resigned,0)=0";
    public static final String SELECT_FROM_XPARTS =
            "select xparts_id \"Id\", partnumber \"Part No\", description \"Part Description\", storename \"Store\","
            + "machinemodel \"Machine Model\", quantity \"Quantity\","
            + "(select companyname from xsupplier where xsupplier_id=lastsupplier_id) \"Last Supplier\","
            + "(select companyname from xsupplier where xsupplier_id=prevsupplier_id) \"Previous Supplier\","
            + "priceperunit \"Last Price Per Unit\", purchased \"Purchase Date\" "
            + " from xparts where xpartcategory_id=#";
    public static final String SELECT_ALL_STOCKS =
            "select xstocks_id \"Id\", name \"Name\", "
            + "(select name from xsite where xsite_id=xstocks.xsite_id) \"Site\" "
            + "description \"Description\" from xstocks";
    public static final String SELECT_PART_STOCKS =
            "select xpartstocks.xpartstocks_id \"Id\", companyname \"Supplier\", xstocks.name \"Location\", rest \"Rest\" "
            + " from xpartstocks,xstocks,xsupplier"
            + " where xpartstocks.xstocks_id=xstocks.xstocks_id and xpartstocks.xsupplier_id=xsupplier.xsupplier_id"
            + " and xpartstocks.xparts_id=#";
    public static final String SELECT_DISTINCT_STORES =
            "select distinct storename from xparts order by storename";
    public static final String SELECT_DISTINCT_MACHINEMODELS =
            "select distinct machinemodel from xparts order by machinemodel";
    public static final String SELECT_DISTINCT_BATTCODES = 
            "select distinct battery_code from xbattery order by battery_code";
    public static final String SELECT_DISTINCT_AVAILABLE_BATTERIES = 
            "select distinct battery_code from xbattery where xbateryissue_id is null order by battery_code";
    public static final String SELECT_OLDEST_AVAILABLE_BATTERIES =
            "select min(xbattery_id), concat(battery_code,' (',count(*),' items)') "
            + "from xbattery where xbateryissue_id is null group by battery_code";
    public static final String SELECT_FROM_ADDSTOCKS =
            "Select xaddstocks_id \"Id\", to_char(purchase_date,'DD/MM/YYYY') \"Purchase Date\", "
            + "quantity \"Quantity\", priceperunit \"Price per Unit\", "
            + "(Select companyname from xsupplier where xsupplier_id=xaddstocks.xsupplier_id) \"Supplier\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xaddstocks.enteredby_id) \"Entered By\" "
            + "from xaddstocks where xparts_id=# order by purchase_date desc";
    public static final String SELECT_FROM_BOOKOUTS =
            "Select xbookouts_id \"Id\",to_char(issue_date,'DD/MM/YYYY') \"Date of Issue\","
            + "quantity \"Quantity\", "
            + "(Select name from xsite where xsite_id=xbookouts.xsite_id) \"Depot/Site\","
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xbookouts.xmachine_id) \"For Machine\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xbookouts.issuedby_id) \"Issued By\","
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xbookouts.issuedto_id) \"Issued To\" "
            + "from xbookouts where xparts_id=# order by issue_date desc";
    public static final String SELECT_LOANLIST =
            "Select clock_num,first_name,sur_name,to_char(issueddate,'DD/MM/YYYY'),concat('R',ROUND(amount,2)) "
            + " from xemployee e, xloans l where l.requestedby_id=e.xemployee_id order by clock_num,issueddate";
    public static final String SELECT_INCIDENTSREPORT =
            "select ifnull(e.first_name,'-unknown-'), ifnull(e.clock_num,'-'), to_char(i.incidentdate,'DD/MM/YYYY'),"
            + "i.estimated_cost,"
            + "concat(m.classify,m.tmvnr,ifnull(concat(' (',t.machtype,')'),'')),"
            + "i.description "
            + "from xincidents i "
            + "left join xemployeeincident ei on ei.xincidents_id=i.xincidents_id "
            + "left join xemployee e on e.xemployee_id=ei.xemployee_id "
            + "left join xmachineincident mi on mi.xincidents_id=i.xincidents_id "
            + "left join xmachine m on m.xmachine_id=mi.xmachine_id "
            + "left join xmachtype t on t.xmachtype_id=m.xmachtype_id "
            + "order by e.clock_num, i.incidentdate";
    public static final String SELECT_ASSIGNMENTSREPORT =
            "select s.name, "
            + "concat(ifnull(o1.ordernumber,''),ifnull(concat(', ',o2.ordernumber),''),ifnull(concat(', ',o3.ordernumber),'')),"
            + "concat(ifnull(c1.companyname,''),ifnull(concat(', ',c2.companyname),''),ifnull(concat(', ',c3.companyname),'')),"
            + "ifnull(concat(m.classify,m.tmvnr,ifnull(concat(' (',t.machtype,')'),'')),'- w/o -'),"
            + "ifnull(e.clock_num,'- w/o -'),e.first_name,e.sur_name,"
            + "to_char(a.date_start,'DD/MM/YYYY'), to_char(a.date_end,'DD/MM/YYYY') "
            + "from xopmachassing a "
            + "left join xemployee e on e.xemployee_id=a.xemployee_id "
            + "left join xmachine m on m.xmachine_id=a.xmachine_id "
            + "left join xmachtype t on t.xmachtype_id=m.xmachtype_id "
            + "left join xsite s on s.xsite_id=a.xsite_id "
            + "left join xorder o1 on o1.xorder_id=s.xorder_id "
            + "left join xorder o2 on o2.xorder_id=s.xorder2_id "
            + "left join xorder o3 on o3.xorder_id=s.xorder3_id "
            + "left join xclient c1 on c1.xclient_id=o1.xclient_id "
            + "left join xclient c2 on c2.xclient_id=o2.xclient_id "
            + "left join xclient c3 on c3.xclient_id=o3.xclient_id "
            + "where a.date_end is null and not ifnull(a.xemployee_id,a.xmachine_id) is null "
            + "order by s.name";
    public static final String SELECT_FROM_PPEBUYS =
            "Select xppebuy_id \"Id\", to_char(buydate,'DD/MM/YYYY') \"Purchase Date\",  "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppebuy.boughtby_id) \"Bought By\", "
            + "(Select companyname from xsupplier where xsupplier_id=xppebuy.xsupplier_id) \"Supplier\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppebuy.authorizedby_id) \"Authorized By\" "
            + "from xppebuy order by buydate desc";
    public static final String SELECT_FROM_PPEISSUES =
            "select xppeissue_id \"Id\", to_char(issuedate,'DD/MM/YYYY') \"Issue Date\",  "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppeissue.issuedby_id) \"Issued By\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppeissue.issuedto_id) \"Issued To\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppeissue.authorizedby_id) \"Authorized By\" "
            + "from xppeissue order by issuedate desc";
    public static final String SELECT_FROM_PPEISSUES4EMPLOYEE =
            "select xppeissue_id \"Id\", to_char(issuedate,'DD/MM/YYYY') \"Issue Date\",  "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppeissue.issuedby_id) \"Issued By\", "
            + "(Select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xppeissue.authorizedby_id) \"Authorized By\" "
            + "from xppeissue where issuedto_id=# order by issuedate desc";
    public static final String SELECT_FROM_PPEISSUEITEMS =
            "select i.xppeissueitem_id \"Id\", t.xppetype \"PPE\", i.quantity \"Quantity\" "
            + "from xppeissueitem i,xppetype t where i.xppetype_id=t.xppetype_id order by t.xppetype";
    public static final String SELECT_FROM_MACHSERVICE =
            "select xmachservice_id \"Id\", to_char(servicedate,'DD/MM/YYYY') \"Service Date\", "
            + "to_char(entrydate,'DD/MM/YYYY') \"Entry Date\", "
            + "(select max(concat(classify,tmvnr)) from xmachine where xmachine_id=xmachservice.xmachine_id) \"Machine\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xmachservice.servicedby_id) \"Serviced By\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xmachservice.servicedby_id) \"Assisted By\" "
            + "from xmachservice order by servicedate desc";

    public static final String SELECT_FROM_BATTPURCHASES = 
            "Select xbatterypurchase_id \"Id\", to_char(purchase_date,'DD/MM/YYYY') \"Date of Purchase\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xbatterypurchase.purchased_by) \"Purchased By\","
            + "(select companyname from xsupplier where xsupplier_id=xbatterypurchase.xsupplier_id) \"Supplier\", "
            + "invoice_vat_incl \"Invoice Vat.Incl.R\", invoice_vat_excl \"Invoice Vat.Excl.R\" "
            + "from xbatterypurchase order by purchase_date desc";

    public static final String SELECT_FROM_BATTISSUES = 
            "select xbateryissue_id \"Id\", to_char(issue_date,'DD/MM/YYYY') \"Date of Issue\", "
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xbateryissue.issued_by) \"Issued By\","
            + "(select concat(clock_num,' ',first_name) from xemployee where xemployee_id=xbateryissue.issued_to) \"Issued To\","
            + "(Select concat(classify,tmvnr) from xmachine where xmachine_id=xbateryissue.xmachine_id) \"For Machine\" "
            + "from xbateryissue order by issue_date desc";
    
    public static String selectActiveEmployees() {
        return Selects.SELECT_FROM_EMPLOYEE.replace("where",
                "where "+
//                + "clock_num!='000' and " +
                "ifnull(deceased,0)=0 and ifnull(dismissed,0)=0 and ifnull(absconded,0)=0 and ifnull(resigned,0)=0 and ");
    }
//    public static final String[] getStringArray(String select) {
//        try {
//            Vector[] body = DashBoard.getExchanger().getTableBody(select);
//            Vector lines = body[1];
//            String[] answer = new String[lines.size()];
//            int n = 0;
//            for (Object o : lines) {
//                Vector itm = (Vector) o;
//                answer[n++] = itm.get(0).toString();
//            }
//            return answer;
//        } catch (RemoteException ex) {
//            ex.printStackTrace();
//            GeneralFrame.errMessageBox("Error:", ex.getMessage());
//        }
//        return null;
//    }
}
