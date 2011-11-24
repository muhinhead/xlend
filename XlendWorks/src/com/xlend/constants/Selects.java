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
            + "CASEWHEN(sitetype='W','Work Site',CASEWHEN(sitetype='A','Additional','unknown')) \"Type of Site\" "
            + "from xsite order by upper(name)";
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
    public static final String SELECT_CONTRACTS4LOOKUP = 
            "Select xcontract_id \"Id\", "
            + "contractref \"Ref.Nr\", description \"Description\" "
            + "from xcontract where xclient_id = #";

    
    public static final String SELECT_FROM_CONTRACTPAGE = 
            "Select xcontractpage_id \"Id\", pagenum \"Ind.No\", description \"Notes\" "
            + "from xcontractpage where xcontract_id = # order by pagenum";
    
    public static final String SELECT_FROM_ORDERS = 
            "Select xorder_id \"Id\", xclient.companyname \"Company\", vatnumber \"Vat.number\", "
            + "regnumber \"Registration No\", ordernumber \"Order No\", orderdate \"Order date\" "
            + "from xorder, xclient where xclient.xclient_id=xorder.xclient_id";
    public static final String SELECT_ORDERS4CONTRACTS = 
            "Select xorder_id \"Id\", vatnumber \"Vat.number\", "
            + "regnumber \"Registration No\", ordernumber \"Order No\", orderdate \"Order date\" "
            + "from xorder where xcontract_id = #";
    
    public static final String SELECT_FROM_QUOTATIONS =
            "Select xquotation_id \"Id\", xclient.companyname \"Company\", "
            + "rfcnumber \"RFC Nr\" "
            + "from xquotation, xclient where xclient.xclient_id=xquotation.xclient_id";
    public static final String SELECT_QUOTATIONS4LOOKUP =
            "Select xquotation_id \"Id\", "
            + "rfcnumber \"RFC Nr\" "
            + "from xquotation where xclient_id = #";
    
    public static final String SELECTORDERITEMS = 
            "Select xorderitem_id \"Id\", itemnumber \"Item\", "
            + "materialnumber \"Material Nr.\", machinetype \"Machine Type\","
            + "deliveryreq \"Required\" "
            + "from xorderitem where xorder_id = #";

}
