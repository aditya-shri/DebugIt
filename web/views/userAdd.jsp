<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Add User</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <div style="text-align: center; padding-top: 7vh; font-size: calc(0.5em + 2vw)">Add User</div>
        <form action="/user/add" method="post" style="padding-top: 5vh">
            <table style="">
                <tr>
                    <th>Name</th>
                    <td><input type="text" name="ename" required/>
                    </td>
                </tr>
                <tr>
                    <th><label for="it">Position</label></th>
                    <td>
                        <select id="it" name="position" style="width: 19.5vw; padding-left: 1vw">
                            <option value="Admin" selected>Admin</option>
                            <option value="Developer">Developer</option>
                            <option value="Tester">Tester</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Project id</th>
                    <td>
                        <%
                            ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
                            if (!projects.isEmpty()) {
                                for (Project p : projects) {
                                    out.println("<input type=\"checkbox\" name=\"projectids\" value=\"" + p.getId() + "\">" + p.getId() + " ");
                                }
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <th>Password</th>
                    <td><input style="width: 19.5vw;" type="password" name="pass" required/>
                    </td>
                </tr>
            </table>
            <br>
            <input type="submit" name="add" value="Add">
        </form>
        <%
            String res = (String) request.getAttribute("getOrPost");
            if (res.equalsIgnoreCase("post")) {
                String resp = (String) request.getAttribute("addResponse");
                if (resp.equalsIgnoreCase("yes")) {
        %>
        <p style="text-align: center; color: chartreuse;">User Added!</p>
        <%
        } else {
        %>
        <p style="text-align: center; color: crimson;">Addition Failed!</p>
        <%
                }
            }
        %>
    </div>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
