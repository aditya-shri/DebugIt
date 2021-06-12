<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.constant.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Aditya Shrivastava
  Date: 11-04-2021
  Time: 03:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Settings</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <h2 style="text-align: center;padding-top: 10vh;">Update Password</h2>
    <br>
    <form action="/settings" method="post" class="fm">
        UserName : <input class="in" name="userName" type="text" value="<%=session.getAttribute("ename")%>" readonly><br><br>
        Old Password : <input class="in" name="oldPassword" type="password" required><br><br>
        New Password : <input class="in" name="newPassword" type="password"required><br><br>
        <br><input class="in" type="submit" name="Update">
    </form>
    <br>
    <br>
    <%
        String getOrPost = (String)request.getAttribute("getOrPost");
        if(getOrPost.equalsIgnoreCase("post")) {
            boolean res = (boolean)request.getAttribute("updateResult");
            if (res) {
                out.println("<p style=\"color: chartreuse; text-align: center;\">Password updated!</p>");
            } else {
                out.println("<p style=\"color: crimson; text-align: center;\">Error occurred! Try Again!</p>");
            }
        }
    %>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
