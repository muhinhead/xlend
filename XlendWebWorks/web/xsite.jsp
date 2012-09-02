<%-- 
    Document   : xsite
    Created on : 01.09.2012, 12:57:53
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xsite"%>
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
        <title>Site card</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="sites.jsp?select=<%=request.getParameter("id")%>#xsite<%=request.getParameter("id")%>">Return to list</a>
        <h3>Site Card</h3>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id"));%>
        <% Xsite site = (Xsite) new Xsite(connection).loadOnId(id);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="6"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=site.getXsiteId()%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Site Name:</th>
                    <td><%=site.getName()%></td>
                    <th>Active:</th>
                    <td><%=(site.getIsActive()!=null && site.getIsActive()==1)?"Yes":"No"%></td>
                    <th colspan="2"></th>
                </tr>
                <tr>
                    <th>Site Type:</th>
                    <td><%=DbConnection.getSiteType(site.getSitetype(),connection)%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Purchase order:</th>
                    <td><%=DbConnection.getOrderOnId(site.getXorderId()==null?0:site.getXorderId(), connection)%></td>
                    <th>order 2:</th>
                    <td><%=DbConnection.getOrderOnId(site.getXorder2Id()==null?0:site.getXorder2Id(), connection)%></td>
                    <th>order 3:</th>
                    <td><%=DbConnection.getOrderOnId(site.getXorder3Id()==null?0:site.getXorder3Id(), connection)%></td>
                </tr>
                <tr>
                    <th>Client Supplies Diesel – Dry Rate:</th>
                    <td align="center"><%=(site.getDieselsponsor() != null && site.getDieselsponsor() == 1)?"&#9679;":""%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Xlend/T&F Supplies Diesel – Wet Rate:</th>
                    <td align="center"><%=(site.getDieselsponsor() == null || site.getDieselsponsor() == 0)?"&#9679;":""%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Description:</th>
                    <td colspan="5"><%=site.getDescription()%></td>
                </tr>
                <tr>
                    <th valign="top">Current assignments:</th>
                    <td colspan="5">
                        <%=Util.showTable(Selects.SELECT_SITE_ASSIGNMENTS.replace("#", site.getXsiteId().toString()),connection)%>
                    </td>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
