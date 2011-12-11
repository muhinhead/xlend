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
            + "materialnumber \"Material Nr.\", machinetype \"Machine Type\","
            + "deliveryreq \"Required\" "
            + "from xorderitem where xorder_id = #";
    public static final String DISTINCT_MACHINETYPES =
            "Select distinct machinetype from xorderitem";
    public static final String DISTINCT_MEASUREITEMS =
            "Select distinct measureitem from xorderitem";
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
            "Select xmachine_id \"Id\", tmvnr, descr \"Description\", "
            + "t.machtype \"Machine Type\", classify \"Classify\", reg_nr \"Reg.Nr\", "
            + "licensedate \"License Date\", licstatus \"License Status\" "
            + "from xmachine m, xmachtype t, xlicensestat ls "
            + "where m.xmachtype_id=t.xmachtype_id "
            + "and m.xlicensestat_id=ls.xlicensestat_id";

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
