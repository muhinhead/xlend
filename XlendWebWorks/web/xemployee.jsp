<%-- 
    Document   : xemployee
    Created on : 19.08.2012, 12:21:50
    Author     : nick
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="java.util.Vector"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="java.io.File"%>
<%@page import="com.xlend.orm.Xemployee"%>
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
        <title>Employee Card</title>
    </head>
    <body>
        <a href="HR.jsp?select=<%=request.getParameter("id")%>#xempl<%=request.getParameter("id")%>">Return to list</a>
        <h3>Employee Card</h3>
        <% String[] durations = new String[]{
                "Permanent", "1 month", "2 month", "3 month", "", "", "6 month",
                "", "", "", "", "", "1 year"
            };%>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xemployee emp = (Xemployee) new Xemployee(connection).loadOnId(id);%>
        <% String[] assigns = DbConnection.findCurrentAssignment(id);%>
        <% byte[] imageData = (byte[]) emp.getPhoto();%>
        <% String imgFileName = "Xemployee" + id + ".jpg";%>
        <%
            ServletContext servletContext = getServletContext();
            String contextPath = servletContext.getRealPath(File.separator);
        %>
        <% File fout = new File(contextPath + imgFileName);%>
        <% String fpath = fout.getAbsolutePath();%>
        <% if (!fout.exists()) DbConnection.writeFile(fout, imageData);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="8"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=emp.getXemployeeId()%></td>
                    <th>Clock Nr:</th>
                    <td><%=emp.getClockNum()%></td>
                    <th colspan="2"></th>
                    <th rowspan="11"><img src="<%=imgFileName%>" alt="Picture" width="450" height="342">
                        <br>
                        <%if (request.getParameter("mode") == null || !request.getParameter("mode").equals("withloans")) {%>
                            <input type="button" value="loans" 
                                   onclick="document.location.href='xemployee.jsp?id=<%=request.getParameter("id")%>&mode=withloans'"/>
                        <% } else {%>
                            <a href="xemployee.jsp?id=<%=request.getParameter("id")%>">Hide loans</a>
                        <% }%>
                        <%if (request.getParameter("mode") == null || !request.getParameter("mode").equals("withappleave")) {%>
                            <input type="button" value="apps for leave" 
                                   onclick="document.location.href='xemployee.jsp?id=<%=request.getParameter("id")%>&mode=withappleave'"/>
                        <% } else {%>
                            <a href="xemployee.jsp?id=<%=request.getParameter("id")%>">Hide apps for leave</a>
                        <% }%>
                        <%if (request.getParameter("mode") == null || !request.getParameter("mode").equals("withabsent")) {%>
                            <input type="button" value="absenteism" 
                                   onclick="document.location.href='xemployee.jsp?id=<%=request.getParameter("id")%>&mode=withabsent'"/>
                        <% } else {%>
                            <a href="xemployee.jsp?id=<%=request.getParameter("id")%>">Hide absenteeism list</a>
                        <% }%>
                    </th>
                </tr>
                <tr>
                    <th>Name:</th>
                    <td><%=emp.getFirstName()%></td>
                    <th>Surname:</th>
                    <td><%=emp.getSurName()%></td>
                    <th>Nickname:</th>
                    <td><%=emp.getNickName()%></td>
                </tr>
                <tr>
                    <th>SA ID or Passport Nr:</th>
                    <td><%=emp.getIdNum()%></td>
                    <th colspan="3">Tax Nr:</th>
                    <td><%=emp.getTaxnum()%></td>
                </tr>
                <tr>
                    <th>Phone Nr:</th>
                    <td><%=emp.getPhone0Num()%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Alter.phone 1:</th>
                    <td><%=emp.getPhone1Num()%></td>
                    <th>Relation to person:</th>
                    <td colspan="2"><%=emp.getRelation1()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Alter.phone 2:</th>
                    <td><%=emp.getPhone2Num()%></td>
                    <th>Relation to person:</th>
                    <td colspan="2"><%=emp.getRelation2()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Home Address:</th>
                    <td colspan="4"><%=emp.getAddress()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Work Position:</th>
                    <td colspan="4"><%=DbConnection.getPositionOnID(emp.getXpositionId(), connection)%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Contract diration:</th>
                    <td><%=durations[emp.getContractLen()]%></td>
                    <th>Start Date:</th>
                    <td><%=emp.getContractStart()%></td>
                    <th>End Date:</th>
                    <td><%=emp.getContractEnd() != null ? emp.getContractEnd() : ""%></td>
                </tr>
                <tr>
                    <th>Rate of Pay (R/hour):</th>
                    <td>
                    <% if (session.getAttribute("isSupervisor") != null && session.getAttribute("isSupervisor").equals("1")) {%>
                        <%= (emp.getRate() == null ? "" : emp.getRate().toString()) %>
                    <%} else {%>
                        ***
                    <%}%>
                    </td>
                    <th>Wage Category:</th>
                    <td><%=DbConnection.getWageCategoryOnID(emp.getWageCategory())%></td>
                    <th>Employment Start Date:</th>
                    <td><%=emp.getEmploymentStart() != null ? emp.getEmploymentStart() : ""%></td>
                </tr>
                <tr>
                    <th>Assigned to Site:</th>
                    <td><%=assigns != null ? assigns[0] : ""%></td>
                    <th>Machine:</th>
                    <td><%=assigns != null ? assigns[1] : ""%></td>
                    <th>
                    <%if (request.getParameter("mode") == null || !request.getParameter("mode").equals("withassignments")) {%>
                        <input type="button" value="Assignments..." 
                               onclick="document.location.href='xemployee.jsp?id=<%=request.getParameter("id")%>&mode=withassignments'"/>
                    <% } else {%>
                        <a href="xemployee.jsp?id=<%=request.getParameter("id")%>">Hide assignments</a>
                    <% }%>
                    </th>
                    <th>
                    <%if (request.getParameter("mode") == null || !request.getParameter("mode").equals("withtimesheets")) {%>
                        <input type="button" value="Time sheets..."
                               onclick="document.location.href='xemployee.jsp?id=<%=request.getParameter("id")%>&mode=withtimesheets'"/>
                    <% } else {%>
                        <a href="xemployee.jsp?id=<%=request.getParameter("id")%>">Hide timesheets</a>
                    <% }%>
                    </th>
                </tr>      
                <%if (request.getParameter("mode") != null && request.getParameter("mode").equals("withassignments")) {%>
                <tr>
                    <th>Assignments history:</th>
                    <td colspan="6">
                        <%=Util.showTable(Selects.SELECT_EMPLOYEE_ASSIGNMENTS.replace("#", emp.getXemployeeId().toString()))%>
                    </td>
                </tr>
                <% } else if (request.getParameter("mode") != null && request.getParameter("mode").equals("withtimesheets")) {%>
                <tr>
                    <th>Timesheets history:</th>
                    <td colspan="6">
                        <%=Util.showTable(Selects.SELECT_TIMESHEETS4EMPLOYEE.replace("#", emp.getXemployeeId().toString()))%>
                    </td>
                </tr>
                <% } else if (request.getParameter("mode") != null && request.getParameter("mode").equals("withloans")) {%>
                <tr>
                    <th>Loans history:</th>
                    <td colspan="6">
                        <%=Util.showTable(Selects.SELECT_LOANS4EMPLOYEE.replace("#", emp.getXemployeeId().toString()))%>
                    </td>
                </tr>
                <% } else if (request.getParameter("mode") != null && request.getParameter("mode").equals("withappleave")) {%>
                <tr>
                    <th>Apps for leave history:</th>
                    <td colspan="6">
                        <%=Util.showTable(Selects.SELECT_APP4LEAVE4EMPL.replace("#", emp.getXemployeeId().toString()))%>
                    </td>
                </tr>
                <% } else if (request.getParameter("mode") != null && request.getParameter("mode").equals("withabsent")) {%>
                <tr>
                    <th>Absenteeism list:</th>
                    <td colspan="6">
                        <%=Util.showTable(Selects.SELECT_ABSENTEISM4EMPLOYEE.replace("#", emp.getXemployeeId().toString()))%>
                    </td>
                </tr>
                <% } %>
                <tr>
                    <th colspan="7"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
