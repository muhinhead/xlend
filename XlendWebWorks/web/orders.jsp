<%-- 
    Document   : orders
    Created on : 09.02.2014, 14:53:42
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.orm.Xorder"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <% final String imgFile = "tabs_orders.png";%>
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage(imgFile, getServletContext()));%>
    <% DbObject[] quotation = Xorder.load(connection, null, null);%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Orders</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <div class="image">
            <div  id="my-div" style="position:absolute; left: 40px; top: 8px;">
                <a href="contracts.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 117px; top: 8px;">
                <a href="quotas.jsp" class="fill-div"></a>
            </div>

            <div  id="my-div" style="position:absolute; left: 347px; top: 8px;">
                <a href="clients.jsp" class="fill-div"></a>
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
        <h3>List of <%=quotation.length%> orders</h3>
        <form>
            <%=Util.showTable(Selects.SELECT_FROM_ORDERS, connection, null)%>
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
