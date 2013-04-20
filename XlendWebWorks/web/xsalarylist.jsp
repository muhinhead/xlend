<%-- 
    Document   : xsalarylist
    Created on : 16.04.2013, 18:11:49
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.orm.Xsalarylist"%>
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
        <title>Salary List</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="hr_salaries.jsp?select=<%=request.getParameter("id")%>#xsalarylist<%=request.getParameter("id")%>">Return to list</a>
        <h3>Salary List</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xsalarylist salaryList = (Xsalarylist) new Xsalarylist(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="5"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=salaryList.getXsalarylistId()%></td>
                    <th>Date:</th>
                    <td><%=salaryList.getPayday()%></td>      
                    <th></th>
                </tr>              
                <tr>
                    <th vallign="top">Records:</th>
                    <td colspan="3">
                        <%=Util.showTable(
                                "select concat(clock_num,' ',first_name) \"Employee\", amount \"Amount\" "
                                + "from xsalary s,xemployee e where e.xemployee_id=s.xemployee_id and s.xsalarylist_id="
                                + salaryList.getXsalarylistId(), connection)%>
                    </td>
                    <th></th>
                </tr>
                <tr>
                    <th colspan="5"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
