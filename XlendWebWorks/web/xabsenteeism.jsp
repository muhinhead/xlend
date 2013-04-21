<%-- 
    Document   : xabsenteeism
    Created on : 19.04.2013, 14:32:14
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.orm.Xabsenteeism"%>
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
        <title>Absenteism</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="hr_absenteism.jsp?select=<%=request.getParameter("id")%>#xabsenteism<%=request.getParameter("id")%>">Return to list</a>
        <h3>Absenteism</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xabsenteeism absenteeism = (Xabsenteeism) new Xabsenteeism(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="5"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=absenteeism.getXabsenteeismId()%></td>
                    <th>Medical condition:</th>
                    <td><%=(absenteeism.getMedicalCond() != null && absenteeism.getMedicalCond() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Employee:</th>
                    <td><%=DbConnection.getEmoployeeOnID(absenteeism.getXemployeeId(), connection)%></td>
                    <th>Funeral:</th>
                    <td><%=(absenteeism.getFuneral() != null && absenteeism.getFuneral() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Date:</th>
                    <td><%=absenteeism.getAbsentdate()%></td>
                    <th>Family Problems:</th>
                    <td><%=(absenteeism.getFamilyProblem() != null && absenteeism.getFamilyProblem() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Site:</th>
                    <td><%=DbConnection.getSiteOnID(absenteeism.getXsiteId(), connection)%></td>
                    <th>In Jail:</th>
                    <td><%=(absenteeism.getInJail() != null && absenteeism.getInJail() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Machine:</th>
                    <td><%=DbConnection.getMachineOnID(absenteeism.getXmachineId() == null ? 0 : absenteeism.getXmachineId().intValue(), connection)%></td>
                    <th>PDP expired:</th>
                    <td><%=(absenteeism.getPdpExpired() != null && absenteeism.getPdpExpired() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Standing:</th>
                    <td><%=(absenteeism.getStanding() != null && absenteeism.getStanding() == 1 ? "Yes" : "No")%></td>
                    <th>License problem:</th>
                    <td><%=(absenteeism.getLicenseProblem() != null && absenteeism.getLicenseProblem() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Reported by:</th>
                    <td><%=DbConnection.getEmoployeeOnID(absenteeism.getReportedbyId(), connection)%></td>
                    <th>PPE & safety:</th>
                    <td><%=(absenteeism.getPpeSafety() != null && absenteeism.getPpeSafety() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Reported to:</th>
                    <td><%=DbConnection.getEmoployeeOnID(absenteeism.getReportedtoId(), connection)%></td>
                    <th>Wage dispute:</th>
                    <td><%=(absenteeism.getWageDispute() != null && absenteeism.getWageDispute() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Not communicated:</th>
                    <td><%=(absenteeism.getNotcommunicated() != null && absenteeism.getNotcommunicated() == 1 ? "Yes" : "No")%></td>
                    <th>Drunk on site:</th>
                    <td><%=(absenteeism.getWageDispute() != null && absenteeism.getWageDispute() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th>Permission granted by:</th>
                    <td><%=DbConnection.getEmoployeeOnID(absenteeism.getPermgranted(), connection)%></td>
                    <th>Work Accident:</th>
                    <td><%=(absenteeism.getWorkAccident() != null && absenteeism.getWorkAccident() == 1 ? "Yes" : "No")%></td>      
                    <th></th>                                      
                </tr>
                <tr>
                    <th rowspan="2" valign="top">Reason:</th>
                    <td rowspan="2" valign="top">
                        <%=absenteeism.getReason()%>
                    </td>
                    <th>Medical Certificate:</th>
                    <td><%=(absenteeism.getMedicalCert() != null && absenteeism.getMedicalCert() == 1 ? "Yes" : "No")%></td> 
                    <th rowspan="2"></th>
                </tr>
                <tr>
                    <th>Death Certificate:</th>
                    <td><%=(absenteeism.getDeathCert() != null && absenteeism.getDeathCert() == 1 ? "Yes" : "No")%></td>   
                </tr>
                <tr>
                    <th colspan="5"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
