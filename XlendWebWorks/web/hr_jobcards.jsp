<%-- 
    Document   : hr_jobcards
    Created on : 13.04.2013, 18:16:16
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.constants.Selects"%>
<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page import="java.sql.Connection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <% final String imgFile = "tab_jobcards.png";%>
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage(imgFile, getServletContext()));%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Loans</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <div class="image">
            <div  id="my-div" style="position:absolute; left: 25px; top: 8px;">
                <a href="HR.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 140px; top: 8px;">
                <a href="hr_timesheets.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 255px; top: 8px;">
                <a href="hr_salaries.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 370px; top: 8px;">
                <a href="hr_wages.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 485px; top: 8px;">
                <a href="hr_absenteism.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 600px; top: 8px;">
                <a href="hr_app4leave.jsp" class="fill-div"></a>
            </div>
            <div  id="my-div" style="position:absolute; left: 715px; top: 8px;">
                <a href="hr_loans.jsp" class="fill-div"></a>
            </div>
<!--            <div  id="my-div" style="position:absolute; left: 830px; top: 8px;">
                <a href="hr_jobcards.jsp" class="fill-div"></a>
            </div>-->

            <img src="images/<%=imgFile%>" alt="Tabs" width="<%=maxImg.getWidth()%>" height="<%=maxImg.getHeight()%>"/>
        </div>
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>
        <form>
            <% Util.TableCeil[] addCeils = new Util.TableCeil[]{
                    new Util.TableCeil("Last updated") {
                        @Override
                        public String getCeil(int id) {
                            return DbConnection.getStampOnID(id, "xjobcard", connection);
                        }
                    }, //                    new Util.TableCeil("") {
                //                        @Override
                //                        public String getCeil(int id) {
                //                            return "<input type=\"button\" value=\"Details...\" onclick=\"document.location.href='xincident.jsp?id=" + id + "'\"/>";
                //                        }
                //                    }
                };%>
            <%=Util.showTable(Selects.SELECT_FROM_JOBCARDS, connection, addCeils)%>
        </form>

    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
