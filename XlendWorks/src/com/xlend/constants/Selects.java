package com.xlend.constants;

import com.xlend.gui.DashBoard;
import com.xlend.gui.GeneralFrame;
import java.rmi.RemoteException;
import java.util.Vector;

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
            + "CASEWHEN(sitetype='W','Work Site',CASEWHEN(sitetype='A','Additional','unknown')) \"Type of Site\" "
            + "from xsite order by upper(name)";
    public static final String SELECT_ORDERISITES = 
            "Select xsite_id \"Id\", name \"Site Name\", description \"Description\", "
            + "CASEWHEN(dieselsponsor,'Yes','No') \"Diesel Sponsored\", "
            + "CASEWHEN(sitetype='W','Work Site',CASEWHEN(sitetype='A','Additional','unknown')) \"Type of Site\" "
            + "from xsite where xorder_id = #";
    public static final String SELECT_FROM_CLIENTS = "Select xclient_id \"Id\","
            + "clientcode \"Client Code\", companyname \"Company name\", "
            + "contactname \"Contact Name\", phonenumber \"Tel Nr.\", vatnumber \"Vat Nr.\" "
            + "from xclient order by upper(clientcode)";
    public static final String SELECT_CLIENTS4LOOKUP = "Select xclient_id \"Id\","
            + "clientcode \"Client Code\", companyname \"Company name\" "
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
    public static final String SELECT_FROM_EMPLOYEE = 
            "Select xemployee_id \"Id\",clock_num \"Clock Nr\","
            + "id_num \"ID Number\",first_name \"First Name\", "
            + "sur_name \"Surename\", phone0_num \"Phone Nr\" from xemployee";
    public static final String SELECT_FROM_TIMESHEET = 
            "Select t.xtimesheet_id \"Id\", t.weekend \"Week Ending\", "
            + "e.clock_num \"Clock Nr\", e.first_name \"First Name\", "
            + "e.sur_name \"Surename\", s.name \"Site\", o.ordernumber \"Order Nr\" "
            + "from xtimesheet t,xemployee e, xsite s, xorder o "
            + "where t.xemployee_id=e.xemployee_id "
            + "and t.xsite_id=s.xsite_id and t.xorder_id=o.xorder_id";
    public static final String SELECT_TIMESHEETS4EMPLOYEE =
            "Select t.xtimesheet_id \"Id\", t.weekend \"Week Ending\", "
            + "s.name \"Site\", o.ordernumber \"Order Nr\" "
            + "from xtimesheet t, xsite s, xorder o "
            + "where t.xsite_id=s.xsite_id and t.xorder_id=o.xorder_id and t.xemployee_id = #";
    public static final String SELECT_WAGE4TIMESHEET =
            "Select xwage_id \"Id\", day \"Day\", normaltime \"Normal Time\", "
            + "overtime \"Over Time\", doubletime \"Double Time\", stoppeddetails \"Details\" "
            + "from xwage where xtimesheet_id = #";
    public static final String SELECT_SITES4LOOKUP = 
            "Select xsite_id \"Id\", name \"Site Name\","
            + "CASEWHEN(sitetype='W','Work Site',CASEWHEN(sitetype='A','Additional','unknown')) \"Type of Site\" "
            + "from xsite order by upper(name)";
    public static final String SELECT_FROM_MACHINE =
            "Select xmachine_id \"Id\", tmvnr \"TMVnr\", "
            + "t1.machtype \"Machine Type\", reg_nr \"Reg.Nr\", "
            + "expdate \"License Exp.Date\", CASEWHEN(expdate is null,'',CASEWHEN(expdate<now(),'Expired','Normal')) \"License Status\" "
            + "from xmachine m, xmachtype t1 "
            + "where m.xmachtype_id=t1.xmachtype_id and t1.classify='M'";
    public static final String SELECTMACHINESONSITE =
            "Select xmachineonsate_id \"Id\", "
            + "classify+tmvnr \"Fleet Nr\", "
            + "(select machtype from xmachtype where xmachtype_id=xmachine.xmachtype_id) \"Machine\", "
            + "(select machtype from xmachtype where xmachtype2_id=xmachine.xmachtype_id) \"Type\", xmachine.reg_nr \"Reg.Nr\", "
            + "substr(xemployee.first_name,0,1)+'.'+xemployee.sur_name \"Operator\", "
            + "estdate \"Est.Date\", deestdate \"De-Est.Date\" "
            + "from xmachineonsite,xmachine,xemployee "
            + "where xmachine.xmachine_id=xmachineonsite.xmachine_id "
            + "and xemployee.xemployee_id=xmachineonsite.xemployee_id "
            + "and xsite_id = #";
    public static final String MACHINETVMS = 
            "Select xmachine_id,classify+tmvnr from xmachine where classify in ('M','T') order by classify+tmvnr";
    public static final String ALLMACHINETVMS = 
            "Select xmachine_id,classify+tmvnr from xmachine order by classify+tmvnr";
    public static final String FREEMACHINETVMS = 
            "Select xmachine_id,classify+tmvnr \"TMVNR\" from xmachine "
            + "where classify in ('M','T') "
            + "and xmachine_id not in (select xmachine_id from xmachineonsite "
            + "where deestdate is null or deestdate > CURDATE()) order by classify+tmvnr";
    public static final String EMPLOYEES = 
            "Select xemployee_id, substr(first_name,0,1)+'.'+sur_name+' ('+clock_num+')' \"Operator\" "
            + "from xemployee order by sur_name";
    public static final String FREEEMPLOYEES = 
            "Select xemployee_id, substr(first_name,0,1)+'.'+sur_name+' ('+clock_num+')' \"Operator\" "
            + "from xemployee where xemployee_id not in (select xemployee_id from xmachineonsite "
            + "where deestdate is null or deestdate > CURDATE()) order by sur_name";
    public static final String SELECT_MASCHINES4LOOKUP =
            "Select xmachine_id \"Id\", classify+tmvnr \"Fleet Nr\", reg_nr \"Reg.Nr\", "
            + "t1.machtype \"Machine\",t2.machtype \"Type\" "
            + "from xmachine m, xmachtype t1, xmachtype t2 "
            + "where m.xmachtype_id=t1.xmachtype_id "
            + "and m.xmachtype2_id=t2.xmachtype_id ";
    public static final String SUPPLIERS = "Select xsupplier_id \"Id\",companyname \"Company Name\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_FROM_SUPPLIERS =
            "Select xsupplier_id \"Id\",companyname \"Company Name\", vatnr \"Vat Nr\", "
            + "company_regnr \"Reg.Nr\", contactperson \"Contact Person\", "
            + "phone \"Tel Nr\", fax \"Fax Nr\", cell \"Cell Nr\", email \"Email\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_SUPPLIERS4LOOKUP =
            "Select xsupplier_id \"Id\",companyname \"Company Name\", vatnr \"Vat Nr\", "
            + "company_regnr \"Reg.Nr\", contactperson \"Contact Person\" "
            + "from xsupplier order by companyname";
    public static final String SELECT_FROM_DIESELPCHS = 
            "Select xdieselpchs_id \"Id\", companyname \"Supplier\", purchased \"Date\", "
            + "amount_liters \"Amount(liters)\", amount_rands \"Amount(Rands)\" "
            + "from xdieselpchs,xsupplier where xsupplier.xsupplier_id=xdieselpchs.xsupplier_id";
    public static final String SELECT_FROM_DIESELCARD =
            "Select xdieselcard_id \"Id\", carddate \"Date\", "
            + "classify+tmvnr \"Machine\", "
            + "substr(first_name,0,1)+'.'+sur_name+' ('+clock_num+')' \"Operator\", "
            + "name \"Site\", amount_liters \"Liters\" from xdieselcard,xmachine,xemployee,xsite "
            + "where xdieselcard.xmachine_id=xmachine.xmachine_id and xdieselcard.xsite_id=xsite.xsite_id "
            + "and xdieselcard.operator_id=xemployee.xemployee_id";
    public static final String PAYMETHODS = 
            "Select xpaidmethod_id, method from xpaidmethod order by xpaidmethod_id";
    public static final String SELECT_FROM_CONSUMABLES = 
            "Select xconsume_id \"Id\", sup.companyname \"Supplier\", "
            + "mac.classify+mac.tmvnr \"Machine\", substr(req.first_name,0,1)+"
            + "'.'+req.sur_name+' ('+req.clock_num+')' \"Requested by\", "
            + "con.invoicedate \"Inv.Date\", con.invoicenumber \"Inv.Nr\", con.partnumber \"Part.Nr\" "
            + "from xconsume con, xsupplier sup, xmachine mac, xemployee req "
            + "where con.xsupplier_id=sup.xsupplier_id and con.xmachine_id=mac.xmachine_id "
            + "and con.requester_id=req.xemployee_id";      
    public static final String SELECT_CONSUMABLES4BREAKDOWN =
            "Select xconsume_id, 'Invoice Nr:'+invoicenumber, partnumber from xconsume where xmachine_id = #";
    public static final String SELECT_FROM_BREAKDOWNS = 
            "Select xbreakdown_id \"Id\", mac.classify+mac.tmvnr \"Machine\", s.name \"Site\", bd.repairdate \"Repair Date\" "
            + "from xbreakdown bd, xmachine mac, xsite s where bd.xmachine_id=mac.xmachine_id and s.xsite_id=bd.xsite_id";
    public static final String SELECT_FROM_WAGES =
            "Select xwagesum.xwagesum_id \"List Id\",weekend \"Week edning\", xemployee.clock_num \"Clock Nr.\","
            + " substr(xemployee.first_name,0,1)+'.'+xemployee.sur_name+' ('+xemployee.clock_num+')' \"Name\","
            + "weeklywage \"Weekly Wage\", normaltime \"Hours\",overtime \"Overtime\", doubletime \"Doubletime\" "
            + "from xwagesum,xwagesumitem,xemployee "
            + "where xwagesumitem.xwagesum_id=xwagesum.xwagesum_id and xemployee.xemployee_id=xemployee.xemployee_id";
    
    public static String[] getStringArray(String select) {
        try {
            Vector[] body = DashBoard.getExchanger().getTableBody(select);
            Vector lines = body[1];
            String[] answer = new String[lines.size()];
            int n = 0;
            for (Object o : lines) {
                Vector itm = (Vector) o;
                answer[n++] = itm.get(0).toString();
            }
            return answer;
        } catch (RemoteException ex) {
            ex.printStackTrace();
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
        return null;
    }
}
