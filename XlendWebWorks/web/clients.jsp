<%-- 
    Document   : clients
    Created on : 09.02.2014, 15:00:04
    Author     : Администратор
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.orm.Xclient"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <% final String imgFile = "tabs_clients.png";%>
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage(imgFile, getServletContext()));%>
    <% DbObject[] clients = Xclient.load(connection, null, null);%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clients</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <div class="image">
            <div  id="my-div" style="position:absolute; left: 40px; top: 8px;">
                <a href="contracts.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 117px; top: 8px;">
                <a href="quotas.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 232px; top: 8px;">
                <a href="orders.jsp" class="fill-div"></a>
            </div>

            <div  id="my-div" style="position:absolute; left: 462px; top: 8px;">
                <a href="suppliers.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 577px; top: 8px;">
                <a href="payments.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 710px; top: 8px;">
                <a href="hourcomparisons.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 880px; top: 8px;">
                <a href="machorderplace.jsp" class="fill-div"></a>
            </div>
            <img src="images/<%=imgFile%>" alt="Tabs" width="<%=maxImg.getWidth()%>" height="<%=maxImg.getHeight()%>"/>
        </div>
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>
        <h3>List of <%=clients.length%> clients</h3>
        <form>
            <%=Util.showTable(Selects.SELECT_FROM_CLIENTS, connection, null)%>
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
