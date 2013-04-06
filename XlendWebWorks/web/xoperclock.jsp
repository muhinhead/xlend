<%-- 
    Document   : xoperclock
    Created on : 05.04.2013, 19:25:44
    Author     : Nick Mukhin
--%>

<%@page import="java.sql.Timestamp"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xopclocksheet"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Operator Clock Sheet</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="sites_operclock.jsp?select=<%=request.getParameter("id")%>#xoperclock<%=request.getParameter("id")%>">Return to list!</a>
        <h3>Operator Clock Sheet</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% String[] days = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturay"}; %>
        <% String[] dayLabels = new String[7]; %>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xopclocksheet clockSheet = (Xopclocksheet) new Xopclocksheet(connection).loadOnId(id);
        Double[] kmStarts = new Double[] {
            clockSheet.getKmStart1(),clockSheet.getKmStart2(),clockSheet.getKmStart3(),clockSheet.getKmStart4(),
            clockSheet.getKmStart5(),clockSheet.getKmStart6(),clockSheet.getKmStart7()
        }; 
        Double[] kmStops = new Double[] {
            clockSheet.getKmStop1(),clockSheet.getKmStop2(),clockSheet.getKmStop3(),clockSheet.getKmStop4(),
            clockSheet.getKmStop5(),clockSheet.getKmStop6(),clockSheet.getKmStop7()
        };
        Timestamp[] workTos = new Timestamp[] {
            clockSheet.getWorkTo1(),clockSheet.getWorkTo2(),clockSheet.getWorkTo3(),clockSheet.getWorkTo4(),
            clockSheet.getWorkTo5(),clockSheet.getWorkTo6(),clockSheet.getWorkTo7()
        };
        Timestamp[] workFroms = new Timestamp[] {
            clockSheet.getWorkFrom1(),clockSheet.getWorkFrom2(),clockSheet.getWorkFrom3(),clockSheet.getWorkFrom4(),
            clockSheet.getWorkFrom5(),clockSheet.getWorkFrom6(),clockSheet.getWorkFrom7()
        };
        Timestamp[] standFroms = new Timestamp[] {
            clockSheet.getStandFrom1(),clockSheet.getStandFrom2(),clockSheet.getStandFrom3(),clockSheet.getStandFrom4(),
            clockSheet.getStandFrom5(),clockSheet.getStandFrom6(),clockSheet.getStandFrom7()
        };
        Timestamp[] standTos = new Timestamp[] {
            clockSheet.getStandTo1(),clockSheet.getStandTo2(),clockSheet.getStandTo3(),clockSheet.getStandTo4(),
            clockSheet.getStandTo5(),clockSheet.getStandTo6(),clockSheet.getStandTo7()
        };
        String[] reasons = new String[] {
            clockSheet.getReason1(),clockSheet.getReason2(),clockSheet.getReason3(),clockSheet.getReason4(),
            clockSheet.getReason5(),clockSheet.getReason6(),clockSheet.getReason7()
        };
        java.util.Date weekend = clockSheet.getSheetDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(weekend);
        java.util.Date dates[] = new java.util.Date[7];
        dates[6] = calendar.getTime();
        int d;
        for (d = 5; d >= 0; d--) {
            calendar.add(Calendar.DATE, -1);
            dates[d] = calendar.getTime();
        }
        
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < 7; i++) {
            d = dates[i].getDay();
            dayLabels[i] = days[d] + "," + df.format(dates[i]);
        }
        %>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="9"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=clockSheet.getXopclocksheetId()%></td>
                    <th colspan="8"></th>
                </tr>
                <tr>
                    <th>Operator:</th>
                    <td colspan="3">
                        <%=DbConnection.getEmoployeeOnID(clockSheet.getXemployeeId(), connection)%>
                    </td>
                    <th colspan="2">Site:</th>
                    <td colspan="2">
                        <%=DbConnection.getSiteOnID(clockSheet.getXsiteId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th>Week ending:</th>
                    <td>
                        <%=clockSheet.getSheetDate()%>
                    </td>
                    <th colspan="4">Machine:</th>
                    <td colspan="2">
                        <%=DbConnection.getMachineOnID(clockSheet.getXmachineId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th></th>
                    <th colspan="2">Hours/Kilometers</th>
                    <th colspan="2">Workings</th>
                    <th colspan="2">Standing</th>
                    <th colspan="2"></th>
                </tr>
                <tr>
                    <th colspan="2">Start</th>
                    <th>Stop</th>
                    <th>From</th>
                    <th>To</th>
                    <th>From</th>
                    <th>To</th>
                    <th>Reason</th>
                    <th></th>
                </tr>
                <% for (int i=0; i<7; i++) {%>
                    <tr>
                        <th><%=dayLabels[i]%></th>
                        <td><%=kmStarts[i]%></td>
                        <td><%=kmStops[i]%></td>
                        <td><%=workFroms[i].toString().substring(11)%></td>
                        <td><%=workTos[i].toString().substring(11)%></td>
                        <td><%=standFroms[i].toString().substring(11)%></td>
                        <td><%=standTos[i].toString().substring(11)%></td>
                        <td><%=reasons[i]%></td>
                        <th></th>
                    </tr>
                <%}%>
                <tr>
                    <th colspan="9"></th>
                </tr>
            </table>
        </form>
    </body>
</html>
