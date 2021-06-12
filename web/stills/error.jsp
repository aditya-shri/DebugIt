<%@ page import="com.adityashri.bit.constant.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Aditya Shrivastava
  Date: 11-04-2021
  Time: 04:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Error</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <h2 style="text-align: center">Error !</h2>
    <p style="text-align: center">Page Not Found!</p>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
