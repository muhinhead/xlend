<%-- 
    Document   : sites
    Created on : 01.09.2012, 11:44:47
    Author     : nick
--%>

<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xsite"%>
<%@page import="com.xlend.orm.Xsite"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sites</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>
        <h3>List of sites</h3>
        <% ImagePanel maxImg = new ImagePanel(Util.loadImage("tab_sites.png", getServletContext()));%>
        <form>
            <img source="tab_sites.png" alt="Tabs" width="<%=maxImg.getWidth()%>" height="<%=maxImg.getHeight()%>" />
            <% Util.TableCeil[] addCeils = new Util.TableCeil[]{
                    new Util.TableCeil("Last updated") {
                        @Override
                        public String getCeil(int id) {
                            return DbConnection.getStampOnID(id,"xsite",connection);
                        }
                    },
                    new Util.TableCeil("") {
                        @Override
                        public String getCeil(int id) {
                            return "<input type=\"button\" value=\"Details...\" onclick=\"document.location.href='xsite.jsp?id=" + id + "'\"/>";
                        }
                    }
                };%>
            <%=Util.showTable(Selects.SELECT_FROM_SITES_WEB, connection, addCeils)%>
        </form>
        <%DbConnection.closeConnection(connection);%>
    </body>
</html>
