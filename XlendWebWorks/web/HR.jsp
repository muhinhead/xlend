<%-- 
    Document   : HR
    Created on : 21.08.2012, 13:19:48
    Author     : Nick Mukhin
--%>

<%@page import="com.xlend.util.ImagePanel"%>
<%@page import="com.xlend.web.Util"%>
<%@page import="java.io.File"%>
<%@page import="com.xlend.orm.dbobject.DbObject"%>
<%@page import="com.xlend.orm.Employeeshort"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.xlend.web.DbConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <% final Connection connection = DbConnection.getConnection();%>
    <% final String imgFile = "tab_employee.png";%>
    <% ImagePanel maxImg = new ImagePanel(Util.loadImage(imgFile, getServletContext()));%>
    <% DbObject[] employees = Employeeshort.load(connection, "clock_num<>'000'", "clock_num");%>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HR</title>
    </head>
    <body background="images/metallic-gears-background-.jpg">
        <div class="image">
<!--            <div  id="my-div" style="position:absolute; left: 25px; top: 8px;">
                <a href="HR.jsp" class="fill-div"></a>
            </div>-->
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
            <div  id="my-div" style="position:absolute; left: 830px; top: 8px;">
                <a href="hr_jobcards.jsp" class="fill-div"></a>
            </div>
            
            <img src="images/<%=imgFile%>" alt="Tabs" width="<%=maxImg.getWidth()%>" height="<%=maxImg.getHeight()%>"/>
        </div>
        <a href="./index.jsp?sessionGUUID=<%=session.getAttribute("sessionGUUID")%>">Return to dashboard</a>
        <script type="text/javascript" language="JavaScript" src="find2.js">
        </script>
        <h3>List of <%=employees.length%> employees</h3>
        
        <form>
            <table class="gridtable">
                <tr><th>Id</th><th>Clock Number</th>
                    <th>First Name</th><th>Surname</th><th>Nick name</th><th>Work position</th>
                    <th>Last updated</th>
                    <th></th>
                </tr>
                <% for (DbObject row : employees) {%>
                <% Employeeshort emp = (Employeeshort) row;%>
                <tr class="<%=(request.getParameter("select")!=null && request.getParameter("select").equals(emp.getXemployeeId().toString())?"selected":"")%>" >
                    <td><%=emp.getXemployeeId()%><a name="xempl<%=emp.getXemployeeId()%>"></a></td>
                    <td><%=emp.getClockNum()%></td>
                    <td><%=emp.getFirstName()%></td>
                    <td><%=emp.getSurName()%></td>
                    <td><%=emp.getNickName()%></td>
                    <td><%=DbConnection.getPositionOnID(emp.getXpositionId(),connection)%></td>
                    <td><%=DbConnection.getStampOnID(emp.getXemployeeId(),"xemployee",connection)%></td>
                    <td><input type="button" id="details<%=emp.getXemployeeId()%>"
                               value="Details..." 
                               onclick="document.location.href = 'xemployee.jsp?id=<%=emp.getXemployeeId()%>'"/>
                    </td>
                </tr>
                <% }%>
            </table>        
        </form>
    </body>
    <%DbConnection.closeConnection(connection);%>
</html>
