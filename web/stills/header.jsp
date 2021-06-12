<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.constant.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Aditya Shrivastava
  Date: 08-04-2021
  Time: 08:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        <%@include file="../css/styles.css" %>
    </style>
</head>
<body>
<div class="topnav">
    <a class="home" href="/home"><%= ((Employee) session.getAttribute("Employee")).getEname()%></a>
    <span><%=Constants.AppName()%></span>
    <a class="logout" href="/logout">Logout</a>
</div>
</body>
</html>
