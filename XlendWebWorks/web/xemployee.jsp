<%-- 
    Document   : xemployee
    Created on : 19.08.2012, 12:21:50
    Author     : nick
--%>

<%@page import="java.io.File"%>
<%@page import="com.xlend.orm.Xemployee"%>
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
        <title>Employee Card</title>
    </head>
    <body>
        <h3>Employee Card</h3>
        <% String[] durations = new String[]{
            "Permanent","1 month","2 month","3 month","","","6 month",
            "","","","","","1 year"
        };%>
        <% Connection connection = DbConnection.getConnection();%>
        <% int id = Integer.parseInt(request.getParameter("id")); %>
        <% Xemployee emp = (Xemployee) new Xemployee(connection).loadOnId(id);%>
        <% String[] assigns = DbConnection.findCurrentAssignment(id);%>
        <% byte[] imageData = (byte[]) emp.getPhoto();%>
        <% String imgFileName = "Xemployee"+id+".jpg";%>
        <% 
            ServletContext servletContext = getServletContext();
	    String contextPath = servletContext.getRealPath(File.separator);        
        %>
        <% File fout = new File(contextPath+imgFileName);%>
        <% String fpath = fout.getAbsolutePath();%>
        <% DbConnection.writeFile(fout, imageData);%>
        <form>
            <table class="formtable">
                <tr>
                    <th colspan="8"></th>
                </tr>
                <tr>
                    <th>ID:</th>
                    <td><%=emp.getXemployeeId()%></td>
                    <th>Clock Nr:</th>
                    <td><%=emp.getClockNum()%></td>
                    <th colspan="2"></th>
                    <th rowspan="11"><img src="<%=imgFileName%>" alt="Here should be a picture" width="450" height="342"></th>
                </tr>
                <tr>
                    <th>Name:</th>
                    <td><%=emp.getFirstName()%></td>
                    <th>Surname:</th>
                    <td><%=emp.getSurName()%></td>
                    <th>Nickname:</th>
                    <td><%=emp.getNickName()%></td>
                </tr>
                <tr>
                    <th>SA ID or Passport Nr:</th>
                    <td><%=emp.getIdNum()%></td>
                    <th colspan="3">Tax Nr:</th>
                    <td><%=emp.getTaxnum()%></td>
                </tr>
                <tr>
                    <th>Phone Nr:</th>
                    <td><%=emp.getPhone0Num()%></td>
                    <th colspan="4"></th>
                </tr>
                <tr>
                    <th>Alter.phone 1:</th>
                    <td><%=emp.getPhone1Num()%></td>
                    <th>Relation to person:</th>
                    <td colspan="2"><%=emp.getRelation1()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Alter.phone 2:</th>
                    <td><%=emp.getPhone2Num()%></td>
                    <th>Relation to person:</th>
                    <td colspan="2"><%=emp.getRelation2()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Home Address:</th>
                    <td colspan="4"><%=emp.getAddress()%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Work Position:</th>
                    <td colspan="4"><%=DbConnection.getPositionOnID(emp.getXpositionId())%></td>
                    <th></th>
                </tr>
                <tr>
                    <th>Contract diration:</th>
                    <td><%=durations[emp.getContractLen()]%></td>
                    <th>Start Date:</th>
                    <td><%=emp.getContractStart()%></td>
                    <th>End Date:</th>
                    <% if(emp.getContractEnd()!=null) {%>
                        <td><%=emp.getContractEnd()%></td>
                    <% } else {%>
                        <th></th>
                    <% } %>
                </tr>
                <tr>
                    <th>Rate of Pay (R/hour):</th>
                    <td><%="**.*"%></td>
                    <th>Wage Category:</th>
                    <td><%=DbConnection.getWageCategoryOnID(emp.getWageCategory())%></td>
                    <th>Employment Start Date:</th>
                    <td><%=emp.getEmploymentStart()%></td>
                </tr>
                <tr>
                    <th>Assigned to Site:</th>
                    <td><%=assigns!=null?assigns[0]:""%></td>
                    <th>Machine:</th>
                    <td><%=assigns!=null?assigns[1]:""%></td>
                    <th><input type="button" value="Assignments..." onclick="alert('Not implemented yet')"></th>
                    <th></th>
                </tr>      
                <tr>
                    <th colspan="8"></th>
                </tr>
            </table>
        </form>
        <%DbConnection.closeConnection(connection);%>
        <% //fout.delete(); %>
        <a href="javascript:history.go(-1)">Return to list</a>
    </body>
</html>
