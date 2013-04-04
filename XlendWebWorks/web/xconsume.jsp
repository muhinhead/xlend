<%-- 
    Document   : xconsume
    Created on : 04.04.2013, 8:53:00
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xconsume"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Consumable Card</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="sites_consumable.jsp?select=<%=request.getParameter("id")%>#xconsume<%=request.getParameter("id")%>">Return to list</a>
        <h3>Consumable card</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% final double TAX_PROC = 1.14;%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xconsume consume = (Xconsume) new Xconsume(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="5"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=consume.getXconsumeId()%></td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Supplier:</th>
                    <td colspan="3">
                        <%=DbConnection.getSupplierOnID(consume.getXsupplierId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Machine/Truck/Other/</th>
                    <td>
                        <%=DbConnection.getMachineOnID(consume.getXmachineId(), connection)%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Site:</th>
                    <td colspan="3">
                        <%=DbConnection.getSiteOnID(consume.getXsiteId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Requested by:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(consume.getRequesterId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Invoice Date:</th>
                    <td>
                        <%=consume.getInvoicedate()%>
                    </td>
                    <th>Invoice Number:</th>
                    <td>
                        <%=consume.getInvoicenumber()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Authorized by:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(consume.getAuthorizerId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Collected by:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(consume.getCollectorId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td colspan="3">
                        <%=consume.getDescription()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Account Nr/Reference:</th>
                    <td >
                        <%=consume.getAccnum()%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Part Nr:</th>
                    <td colspan="3">
                        <%=consume.getPartnumber()%>
                    </td>
                    <th ></th>
                </tr>
                <tr>
                    <th>Amount(liters):</th>
                    <td >
                        <%=(consume.getAmountLiters()==null?"0":consume.getAmountLiters())%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Vat Incl.Amount(R):</th>
                    <td >
                        <%=consume.getAmountRands()%>
                    </td>
                    <th>Vat Excl.Amount(R):</th>
                    <td >
                        <%=String.format("%.2f",consume.getAmountRands()/TAX_PROC).replace(",",".")%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Paid by(employee):</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(consume.getPayerId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Paid by(method):</th>
                    <td colspan="3">
                        <%=DbConnection.getPaydMethodOnID(consume.getXpaidmethodId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th colspan="5"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
