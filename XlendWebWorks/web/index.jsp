<%-- 
    Document   : index
    Created on : 18.08.2012, 14:39:17
    Author     : nick
--%>

<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Xemployee"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.web.DbConnection"%>
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
        <title>Xlend Web Works</title>
    </head>
    <body>
        <h3>List of employees</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <form>
            <table class="gridtable"><tr><th>Id</th><th>Clock Number</th><th>First Name</th><th>Surname</th><th>Nick name</th></tr>
                <% for (DbObject row : Xemployee.load(connection, null, "clock_num")) {%>
                <% Xemployee emp = (Xemployee) row;%>
                <tr>
                    <td><%=emp.getXemployeeId()%></td>
                    <td><%=emp.getClockNum()%></td>
                    <td><%=emp.getFirstName()%></td>
                    <td><%=emp.getSurName()%></td>
                    <td><%=emp.getNickName()%></td>
                </tr>
                <% }%>
            </table>        
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
