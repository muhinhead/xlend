<%-- 
    Document   : HR
    Created on : 21.08.2012, 13:19:48
    Author     : Nick Mukhin
--%>

<%@page import="java.io.File"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Employeeshort"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% Connection connection = DbConnection.getConnection();%>
    <% DbObject[] employees = Employeeshort.load(connection, "clock_num<>'000'", "clock_num");%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HR</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>
        <h3>List of <%=employees.length%> employees</h3>
        
        <form>
            <table class="gridtable">
                <tr><th>Id</th><th>Clock Number</th>
                    <th>First Name</th><th>Surname</th><th>Nick name</th><th>Work position</th>
                    <th>Last updated</th>
                    <th></th>
                </tr>
                <% for (DbObject row : employees) {%>
                <% Employeeshort emp = (Employeeshort) row;%>
                <tr class="<%=(request.getParameter("select")!=null && request.getParameter("select").equals(emp.getXemployeeId().toString())?"selected":"")%>" >
                    <td><%=emp.getXemployeeId()%><a name="xempl<%=emp.getXemployeeId()%>"></a></td>
                    <td><%=emp.getClockNum()%></td>
                    <td><%=emp.getFirstName()%></td>
                    <td><%=emp.getSurName()%></td>
                    <td><%=emp.getNickName()%></td>
                    <td><%=DbConnection.getPositionOnID(emp.getXpositionId(),connection)%></td>
                    <td><%=DbConnection.getStampOnID(emp.getXemployeeId(),"xemployee",connection)%></td>
                    <td><input type="button" id="details<%=emp.getXemployeeId()%>"
                               value="Details..." 
                               onclick="document.location.href = 'xemployee.jsp?id=<%=emp.getXemployeeId()%>'"/>
                    </td>
                </tr>
                <% }%>
            </table>        
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
