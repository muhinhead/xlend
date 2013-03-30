<%-- 
    Document   : sites
    Created on : 01.09.2012, 11:44:47
    Author     : Nick Mukhin
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
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage("tab_sites.png", getServletContext()));%>
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

        <div class="image">
            <div  id="my-div" style="position:absolute; left: 118px; top: 8px;">
                <a href="sites_consumable.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 235px; top: 8px;">
                <a href="sites_breakdowns.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 350px; top: 8px;">
                <a href="sites_sitediary.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 465px; top: 8px;">
                <a href="sites_incidents.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 580px; top: 8px;">
                <a href="sites_operclock.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 695px; top: 8px;">
                <a href="sites_ppe.jsp" class="fill-div"></a>
            </div>
            <div id="my-div" style="position:absolute; left: 810px; top: 8px;">
                <a href="sites_issuetodc.jsp" class="fill-div"></a>
            </div>

            <img src="images/tab_sites.png" alt="Tabs" width="<%=maxImg.getWidth()%>" height="<%=maxImg.getHeight()%>"/>
        </div>

        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>

        <form>

            <% Util.TableCeil[] addCeils = new Util.TableCeil[]{
                    new Util.TableCeil("Last updated") {
                        @Override
                        public String getCeil(int id) {
                            return DbConnection.getStampOnID(id, "xsite", connection);
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
