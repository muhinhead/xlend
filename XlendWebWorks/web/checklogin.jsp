<%-- 
    Document   : checklogin
    Created on : 25.08.2012, 7:57:39
    Author     : nick
--%>
<%@page import="java.util.UUID"%>
<%@page import="com.xlend.orm.Userprofile"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Xemployee"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%!  DbObject[] obs;%>
<%   Connection connection = DbConnection.getConnection(); %>
<%   obs = Userprofile.load(connection, "login='"
            + request.getParameter("login") + "' and pwdmd5='"
            + request.getParameter("pwd") + "'", null);%>
    
    <head>
        <script lang="javascript">
            <!--
            function load(guuid) {
                document.location.href = "index.jsp?sessionGUUID="+guuid;
            }
            -->
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login checking</title>
    </head>
    <body
        <% if (obs.length > 0) {%>
        <%   session.setAttribute("sessionGUUID", UUID.randomUUID().toString()); %>
        <%   Userprofile curUser = (Userprofile)obs[0]; %>
        <%   session.setAttribute("currentLogin", curUser.getLogin()); %>
        <%   session.setAttribute("isSupervisor", curUser.getSupervisor().toString()); %>
             onload="load('<%=session.getAttribute("sessionGUUID")%>');"
        <% }%>
        >
        <% if (obs.length == 0) {%>
        <table border="0" align="center">
            <tr>
                <td valign="center" align="right"><h3>Wrong credentials, sorry</h3></td>
            </tr>
            <tr>
                <td>
                    <a href='login.jsp'>
                        Return to login page
                    </a>
                </td>
            </tr>
        </table>
        <% }%>
    </body>
    <%   DbConnection.closeConnection(connection); %>
</html>
