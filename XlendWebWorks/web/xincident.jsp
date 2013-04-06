<%-- 
    Document   : xincident
    Created on : 04.04.2013, 18:43:24
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xincidents"%>
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
        <title>Incident Card</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="sites_incidents.jsp?select=<%=request.getParameter("id")%>#xincident<%=request.getParameter("id")%>">Return to list</a>
        <h3>Incident Card</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xincidents incident = (Xincidents) new Xincidents(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="7"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=incident.getXincidentsId()%></td>
                    <th>Date:</th>
                    <td><%=incident.getReportdate()%></td>
                    <th>Date:</th>
                    <td><%=incident.getIncidentdate()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Machine(s) involved:</th>
                    <td colspan="2">
                        <%=Util.showTable(Selects.SELECT_INCIDENT_MACHINES.replace("#",incident.getXincidentsId().toString()), connection, null)%>
                    </td>
                    <th>Person(s) involved:</th>
                    <td colspan="2">
                        <%=Util.showTable(Selects.SELECT_INCIDENT_PERSONS.replace("#",incident.getXincidentsId().toString()), connection, null)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Other machines:</th>
                    <td colspan="2"><%=incident.getBlobmachines()%></td>
                    <th>Other persones:</th>
                    <td colspan="2"><%=incident.getBlobpeople()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Site:</th>
                    <td colspan="2">
                        <%=DbConnection.getSiteOnID(incident.getXsiteId(), connection)%>
                    </td>
                    <th>Location:</th>
                    <td colspan="2">
                        <%=incident.getLocation()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td colspan="5">
                        <%=incident.getDescription()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Damages:</th>
                    <td colspan="5">
                        <%=incident.getDamages()%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Reported by:</th>
                    <td colspan="2">
                        <%=DbConnection.getEmoployeeOnID(incident.getReportedbyId(), connection)%>
                    </td>
                    <th>Reported to:</th>
                    <td colspan="2">
                        <%=DbConnection.getEmoployeeOnID(incident.getReportedtoId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Signed:</th>
                    <td><%=(incident.getIsSigned() != null && incident.getIsSigned() == 1 ? "Yes" : "No")%></td>
                    <th colspan="2">Verifyed:</th>
                    <td><%=(incident.getIsVerified() != null && incident.getIsVerified() == 1 ? "Yes" : "No")%></td>
                    <th colspan="2"></th>
                </tr>
                <tr>
                    <th>Estimated lost:</th>
                    <td><%=incident.getEstimatedCost() == null ? 0 : incident.getEstimatedCost()%></td>
                    <th colspan="2">Lost income:</th>
                    <td><%=incident.getLostIncome() == null ? 0 : incident.getLostIncome()%></td>
                    <th colspan="2"></th>
                </tr>
                <tr>
                    <th colspan="7"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
