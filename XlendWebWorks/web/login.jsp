<%-- 
    Document   : login
    Created on : 21.08.2012, 12:36:57
    Author     : Nick Mukhin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            <!--
            @import url("gridtablestyle.css");
            -->
        </style>
        <script lang="javascript">
            <!--
            function enter(loginvalue, passwordvalue) {
                if (loginvalue=='') {
                    alert("Enter login please");
                } else if (passwordvalue=='') {
                    alert("Enter password please");
                } else {
                    document.location.href = "checklogin.jsp?login="+loginvalue
                        +"&pwd="+passwordvalue;
                }
            }
            -->
        </script>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xlend Works Login</title>
    </head>
    <body background="images/metallic-gears-background-.jpg"
        <% if (request.getParameter("loginfield") != null) {%>
        onload="enter('<%=request.getParameter("loginfield")%>','<%=request.getParameter("passwordfield")%>');"
        <% }%>
        >
        <h3 align="center">Xlend Works Login</h3>
        <form>
            <table border="0" align="center">
                <tr>
                    <th valign="center" align="right">
                        <table class="formtable">
                            <tr>
                                <th>Login:</th>
                                <td><input id="loginfield" name="loginfield" value="<%=(request.getParameter("loginfield")==null?"":request.getParameter("loginfield"))%>"/>
                                </td>
                            </tr>
                            <tr>
                                <th>Password:</th>
                                <td><input id="passwordfield" name="passwordfield" type="password"/></td>
                            </tr>
                            <tr>
                                <th align="center" colspan="2">
                                    <input type="submit" value="Login"/>
                                </th>
                            </tr>
                        </table>
                    </th>
                </tr>
            </table>
        </form>
    </body>
</html>
