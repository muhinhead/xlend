<%-- 
    Document   : xwagesum
    Created on : 17.04.2013, 13:19:31
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.orm.Xwagesum"%>
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
        <title>Wage List</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="hr_wages.jsp?select=<%=request.getParameter("id")%>#xwagesum<%=request.getParameter("id")%>">Return to list</a>
        <h3>Wage List</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xwagesum wagesum = (Xwagesum) new Xwagesum(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="6"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=wagesum.getXwagesumId()%></td>
                    <th>Date:</th>
                    <td><%=wagesum.getWeekend()%></td>      
                    <th></th>                    
                    <th></th>                    
                </tr>
                <tr>
                    <th valign="top">Records:</th>
                    <td colspan="4">
                        <%=Util.showTable(
                                "select e.clock_num \"Clock Nr.\",concat(substr(e.first_name,1,1),' ',e.sur_name) \"Name\","
                                + "weeklywage \"Weekly Wage\",normaltime \"Hours\", overtime, doubletime "
                                + " from xemployee e, xwagesumitem i"
                                + " where i.xemployee_id=e.xemployee_id and xwagesum_id="
                                + wagesum.getXwagesumId()
                                + " and e.wage_category in (2,3) and "
                                + "upper(e.clock_num) not like 'S%' and "
                                + "ifnull(e.deceased,0)=0 and ifnull(e.dismissed,0)=0 and ifnull(e.absconded,0)=0 and ifnull(e.resigned,0)=0"
                                + " order by e.clock_numonly,e.clock_num", connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th colspan="6"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>         
    </body>
</html>
