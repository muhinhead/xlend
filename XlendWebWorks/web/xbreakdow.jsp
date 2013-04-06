<%-- 
    Document   : xbreakdow
    Created on : 04.04.2013, 13:25:29
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xbreakdown"%>
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
        <title>Breakdown Card</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="sites_breakdowns.jsp?select=<%=request.getParameter("id")%>#xbreakdown<%=request.getParameter("id")%>">Return to list</a>
        <h3>Breakdown card</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xbreakdown breakdown = (Xbreakdown) new Xbreakdown(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="5"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=breakdown.getXbreakdownId()%></td>
                    <th>Breakdown Date:</th>
                    <td><%=breakdown.getBreakdowndate()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Machine/Truck/Other:</th>
                    <td>
                        <%=DbConnection.getMachineOnID(breakdown.getXmachineId(), connection)%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Site:</th>
                    <td colspan="3">
                        <%=DbConnection.getSiteOnID(breakdown.getXsiteId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Reported to:</th>
                    <td colspan>
                        <%=DbConnection.getEmoployeeOnID(breakdown.getReportedtoId(), connection)%>
                    </td>
                    <th>Reported by:</th>
                    <td colspan>
                        <%=DbConnection.getEmoployeeOnID(breakdown.getReportedbyId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Attended by:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(breakdown.getAttendedbyId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>With Vehicle:</th>
                    <td>
                        <%=DbConnection.getMachineOnID(breakdown.getVehicleId(), connection)%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Repair Date:</th>
                    <td>
                        <%=breakdown.getRepairdate()%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Problem Repaired?:</th>
                    <td colspan>
                        <%=(breakdown.getRepaired()!=null && breakdown.getRepaired()==1)?"Yes":"No"%>
                    </td>
                    <th>Operator at fault?:</th>
                    <td colspan>
                        <%=(breakdown.getOperatorfault()!=null && breakdown.getOperatorfault()==1)?"Yes":"No"%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Operator:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(breakdown.getOperatorId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Kilometers to site (one way):</th>
                    <td colspan>
                        <%=breakdown.getKm2site1way()%>
                    </td>
                    <th>Hours on job:</th>
                    <td colspan>
                        <%=breakdown.getHoursonjob()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Time left:</th>
                    <td colspan>
                        <%=breakdown.getTimeleft()==null?"":breakdown.getTimeleft().toString()%>
                    </td>
                    <th>Time back:</th>
                    <td colspan>
                        <%=breakdown.getTimeback()==null?"":breakdown.getTimeback().toString()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Stayed Over?:</th>
                    <td>
                        <%=(breakdown.getStayedover()!=null && breakdown.getStayedover()==1)?"Yes":"No"%>
                    </td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Accomodation Price:</th>
                    <td colspan>
                        <%=breakdown.getAccomprice()==null?0:breakdown.getAccomprice()%>
                    </td>
                    <th>Amount:</th>
                    <td colspan>
                        <%=String.format("%.2f", breakdown.getAmount()).replace(",",".")%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Purchases:</th>
                    <td colspan="4">
                        <%=Util.showTable(Selects.SELECT_BREAKDOWNCONSUMES.replaceAll("#", "" + breakdown.getXbreakdownId()),connection)%>
                    </td>
                </tr>
            </table>
        </form>

    </body>
</html>
