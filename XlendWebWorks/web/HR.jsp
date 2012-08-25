<%-- 
    Document   : HR
    Created on : 21.08.2012, 13:19:48
    Author     : nick
--%>

<%@page import="java.io.File"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Xemployee"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% Connection connection = DbConnection.getConnection();%>
    <% DbObject[] employees = Xemployee.load(connection, "clock_num<>'000'", "clock_num");%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xlend Web Works</title>
    </head>
    <body>
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <h3>List of <%=employees.length%> employees</h3>
        
        <form>
            <table class="gridtable">
                <tr><th>Id</th><th>Clock Number</th>
                    <th>First Name</th><th>Surname</th><th>Nick name</th><th>Work position</th>
                    <th>Last updated</th>
                    <th></th>
                </tr>
                <% for (DbObject row : employees) {%>
                <% Xemployee emp = (Xemployee) row;%>
                <tr>
                    <td><%=emp.getXemployeeId()%></td>
                    <td><%=emp.getClockNum()%></td>
                    <td><%=emp.getFirstName()%></td>
                    <td><%=emp.getSurName()%></td>
                    <td><%=emp.getNickName()%></td>
                    <td><%=DbConnection.getPositionOnID(emp.getXpositionId(),connection)%></td>
                    <td><%=DbConnection.getStampOnID(emp.getXemployeeId(),connection)%></td>
                    <td><input type="button" value="Details..." 
                               onclick="document.location.href = 'xemployee.jsp?id=<%=emp.getXemployeeId()%>'"/></td>
                </tr>
                <% }%>
            </table>        
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
