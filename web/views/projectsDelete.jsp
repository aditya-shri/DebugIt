<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - delete Projects</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <form action="/projects/delete" method="post" style="padding-top: 5vh">
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
            <input type="submit" name="projectSelect" value="Load">
        </form>
        <%
            String res = (String) request.getAttribute("getOrPost");
            if (res.equalsIgnoreCase("post")) {
                String r = (String) request.getAttribute("viewOrModify");
                if (r.equalsIgnoreCase("view")) {
                    int index = (Integer)session.getAttribute("index");
                    Project project = projects.get(index);
        %>
                <form action="/projects/delete" method="post" style="padding-top: 5vh">
                    <table>
                        <tr>
                            <th>ID</th>
                            <td><input type="text" name="id" value="<%=project.getId()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>Name</th>
                            <td><input type="text" name="pname" value="<%=project.getPname()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>About</th>
                            <td><input type="text" name="about" value="<%=project.getAbout()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>Assigned to</th>
                            <td><input type="text" name="empids" value="<%=project.getEmployeeidsInStr()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td><input type="text" name="status" value="<%=project.getStatus()%>" readonly/>
                            </td>
                        </tr>
                    </table>
                        <br>
                        <input type="submit" name="projectDelete" value="delete">
                    </form>
                <%
                } else {
                    String resp = (String) request.getAttribute("modifyResponse");
                    if (resp.equalsIgnoreCase("yes")) {
                %>
                        <p style="text-align: center; color: chartreuse;">User Updated!</p>
                <%
                    } else {
                %>
                        <p style="text-align: center; color: crimson;">Update failed!</p>
                <%
                    }
                }
            }
        %>
    </div>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
