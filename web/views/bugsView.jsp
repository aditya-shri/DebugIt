<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %><%--
  Created by IntelliJ IDEA.
  User: Aditya Shrivastava
  Date: 11-04-2021
  Time: 03:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - View User</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
    <form action="/bugs/view" method="post" style="padding-top: 5vh">
        <label for="ids">User id : </label>
        <select id="ids" name="selected">
            <option value="-1" selected>Select id</option>
            <%
                ArrayList<Bugs> bugs = (ArrayList<Bugs>) session.getAttribute("Bugs");
                int i = 1;
                if(!bugs.isEmpty()) {
                    for (Bugs b : bugs) {
                        out.println("<option value=\"" + i + "\">" + b.getId() + "</option>");
                        i++;
                    }
                }
            %>
        </select><br><br>
        <input type="submit" value="Load">
    </form>
    <br>
<%
    String res = (String)request.getAttribute("getOrPost");
    if(res.equalsIgnoreCase("post")){
        int index = (int)request.getAttribute("index");
        Bugs bug = bugs.get(index);
        %>
            <table style="">
                <tr>
                    <th>ID</th>
                    <td><%=bug.getId()%></td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><%=bug.getBname()%></td>
                </tr>
                <tr>
                    <th>About</th>
                    <td><%=bug.getAbout()%></td>
                </tr>
                <tr>
                    <th>Severity</th>
                    <td><%=bug.getSeverity()%></td>
                </tr>
                <tr>
                    <th>ProjectId</th>
                    <td><%=bug.getProjectid()%></td>
                </tr>
                <tr>
                    <th>Employee assigned to</th>
                    <td><%=bug.getEmpid()%></td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td><%=bug.getStatus()%></td>
                </tr>
            </table>
        <%
    }
%>
    </div>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
