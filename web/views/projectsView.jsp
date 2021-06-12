<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.model.Project" %>
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
    <title><%=Constants.AppName()%> - View Projects</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
    <form action="/projects/view" method="post" style="padding-top: 5vh">
        <label for="ids">Project id : </label>
        <select id="ids" name="selected">
            <option value="-1" selected>Select id</option>
            <%
                ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
                if(!projects.isEmpty()) {
                    int i=1;
                    for (Project p : projects) {
                        out.println("<option value=\"" + i + "\">" + p.getId() + "</option>");
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
        int index = (Integer)request.getAttribute("index");
        Project project = projects.get(index);
        %>
            <table>
                <tr>
                    <th>ID</th>
                    <td><%=project.getId()%></td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><%=project.getPname()%></td>
                </tr>
                <tr>
                    <th>About</th>
                    <td><%=project.getAbout()%></td>
                </tr>
                <tr>
                    <th>Employees assigned to</th>
                    <td><%=project.getEmployeeidsInStr().equalsIgnoreCase("")?"-":project.getEmployeeidsInStr()%></td>
                </tr>
                <tr>
                    <th>Status</th>
                    <td><%=project.getStatus()%></td>
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
