<%-- 
    Document   : suppliers
    Created on : 09.02.2014, 15:11:06
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Xsupplier"%>
<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.web.Util"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <% final String imgFile = "tab_suppliers.png";%>
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage(imgFile, getServletContext()));%>
    <% DbObject[] supplier = Xsupplier.load(connection, null, null);%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Suppliers</title>
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
            <div  id="my-div" style="position:absolute; left: 347px; top: 8px;">
                <a href="clients.jsp" class="fill-div"></a>
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
        <h3>List of <%=supplier.length%> supplisers</h3>
        <form>
            <%=Util.showTable(Selects.SELECT_FROM_SUPPLIERS, connection, null)%>
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
