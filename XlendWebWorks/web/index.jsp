<%-- 
    Document   : index
    Created on : 18.08.2012, 14:39:17
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="java.awt.Image"%>
<%@page import="com.xlend.web.Util"%>
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
        <title>Xlend Web Works</title>
    </head>
    <body bgcolor="#666666">
        <% String sessRequested = request.getParameter("sessionGUUID") == null ? "" : request.getParameter("sessionGUUID");%>
        <% if (session.getAttribute("sessionGUUID") == null || !session.getAttribute("sessionGUUID").equals(sessRequested)) {%>
            <script lang="javascript">
                document.location.href = "login.jsp";
            </script>
        <% } else {%>
            <h3 align="center" >Xlend Works Dashboard</h3>
            <% ImagePanel backPanel = new ImagePanel(Util.loadImage("top.png", getServletContext()));%>
            <form >
                <table align="center" width="<%=backPanel.getWidth()%>" class="gray" border="0">
                    <tr>
                        <th valign="bottom">
                        <table align="center" class="gray" border="0">
                            <tr align="center">
                                <th colspan="5">
                                    <img cursor="default" src="images/top.png" alt="top" width="<%=backPanel.getWidth()%>" height="<%=backPanel.getHeight() + 10%>">
                                </th>
                            </tr>
                            <tr>
                                <% ImagePanel maxImg = new ImagePanel(Util.loadImage("Banking.png", getServletContext()));%>
                                <td align="center"  cursor="pointer">
                                    <% ImagePanel btnImg = new ImagePanel(Util.loadImage("Docs.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Docs.png" alt="Docs" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("HR.png", getServletContext()));%>
                                    <a href="./HR.jsp">
                                        <img src="images/HR.png" alt="HR" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Parts.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Parts.png" alt="Parts" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Sites.png", getServletContext()));%>
                                    <a href="./sites.jsp">
                                        <img src="images/Sites.png" alt="Sites" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Fleet.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Fleet.png" alt="Fleet" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                            </tr>
                            <tr align="center">
                                <td></td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Banking.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Banking.png" alt="Banking" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Reports.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Reports.png" alt="Reports" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td align="center">
                                    <% btnImg = new ImagePanel(Util.loadImage("Logistics.png", getServletContext()));%>
                                    <a href="javascript:alert('Not implemented yet')">
                                        <img src="images/Logistics.png" alt="Logistics" width="<%=maxImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                                <td valign="bottom" align="right">
                                    <% btnImg = new ImagePanel(Util.loadImage("logout.png", getServletContext()));%>
                                    <a href="./login.jsp">
                                        <img src="images/logout.png" alt="logout" width="<%=btnImg.getWidth()%>" height="<%=btnImg.getHeight() + 10%>">
                                    </a>
                                </td>
                            </tr>
                        </table>
                    </th>
                </tr>
            </table>
        </form>
        <% }%>
    </body>
</html>
