<%@ page import="com.adityashri.bit.constant.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Aditya Shrivastava
  Date: 08-04-2021
  Time: 04:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title><%=Constants.AppName()%></title>
    <style>
      <%@include file="css/styles.css" %>
    </style>
  </head>
  <body>
  <jsp:include page="stills/Loginheader.jsp" />
  <h1 style="text-align: center; padding-top: 18vh">Login</h1>
  <form action="/login" method="post">
    <div class="login">
    <table class="info">
      <tr>
        <th>UserName</th>
        <td>:</td>
        <td><input name="userName" type="text"></td>
      </tr>
      <tr>
        <th>Password</th>
        <td>:</td>
        <td><input name="password" type="password"></td>
      </tr>
    </table>
    </div>
    <br>
    <input style="width: 10vw" type="submit" name="Login">
  </form>
  <jsp:include page="stills/footer.jsp"/>
  </body>
</html>
