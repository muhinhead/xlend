<%-- 
    Document   : xtimesheet
    Created on : 14.04.2013, 10:15:32
    Author     : Nick Mukhin
--%>

<%@page import="java.io.File"%>
<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xtimesheet"%>
<%@page import="com.xlend.orm.Xwage"%>
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
        <title>Timesheet</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="hr_timesheets.jsp?select=<%=request.getParameter("id")%>#xtimesheet<%=request.getParameter("id")%>">Return to list</a>
        <h3>Timesheet</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xtimesheet timesheet = (Xtimesheet) new Xtimesheet(connection).loadOnId(id);%>
        <% byte[] imageData = (byte[]) timesheet.getImage();%>
        <% String imgFileName = "Xtimesheet" + id + ".jpg";%>
        <%
            ServletContext servletContext = getServletContext();
            String contextPath = servletContext.getRealPath(File.separator);
        %>
        <% File fout = new File(contextPath + imgFileName);%>
        <% String fpath = fout.getAbsolutePath();%>
        <% if (!fout.exists()) {
                DbConnection.writeFile(fout, imageData);
            }%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="6"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=timesheet.getXtimesheetId()%></td>
                    <th colspan="3"></th>
                    <th rowspan="8"><img src="<%=imgFileName%>" alt="Picture" width="450" height="342"/></th>
                </tr>
                <tr>
                    <th>Operator:</th>
                    <td colspan="4"><%=DbConnection.getEmoployeeOnID(timesheet.getXemployeeId(), connection)%></td>
                </tr>
                <tr>
                    <th>Machine:</th>
                    <td colspan="4"><%=DbConnection.getMachineOnID(timesheet.getXmachineId(), connection)%></td>
                </tr>
                <tr>
                    <th>Week ending:</th>
                    <td><%=timesheet.getWeekend()%></td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th>Site:</th>
                    <td colspan="4"><%=DbConnection.getSiteOnID(timesheet.getXsiteId(), connection)%></td>
                </tr>
                <tr>
                    <th>Order No:</th>
                    <td colspan="4"><%=DbConnection.getOrderNumberOnID(timesheet.getXorderId(), connection).trim()%></td>
                </tr>
                <tr>
                    <th>Clock Sheet:</th>
                    <td><%=(timesheet.getClocksheet() != null && timesheet.getClocksheet() == 1 ? "Yes" : "No")%></td>
                    <th colspan="3"></th>
                </tr>
                <tr>
                    <th colspan="5"></th>
                </tr>
                <tr>
                    <th valign="top">Dayly time sheet:</th>
                    <td colspan="5"> 
                        <%=Util.showTable("select "
                                + "case dayofweek(day) when 1 then 'Sunday' when 2 then 'Monday' when 3 then 'Tuesday' "
                                + "when 4 then 'Wednesday' when 5 then 'Thursday' when 6 then 'Friday' when 7 then 'Saturday' end \"Day\","
                                + "normaltime \"Normal Time\",overtime \"Overtime\",doubletime \"Double Time\",stoppeddetails \"Details\","
                                + "tsnum \"Timesheet No\" from xwage where xtimesheet_id=" + timesheet.getXtimesheetId(), connection)%>
                    </td>
                </tr>                <tr>
                    <th colspan="6"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
