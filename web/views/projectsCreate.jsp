<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Create Project</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <div style="text-align: center; padding-top: 7vh; font-size: calc(0.5em + 2vw)">Create Project</div>
        <form action="/projects/create" method="post" style="padding-top: 5vh">
            <table style="">
                <tr>
                    <th>Name</th>
                    <td><input type="text" name="pname" required/>
                    </td>
                </tr>
                <tr>
                    <th>About</th>
                    <td><input type="text" name="about" required/>
                    </td>
                </tr>
                <tr>
                    <th>Assign to</th>
                    <td>
                        <%
                            ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
                            Employee employee = (Employee) session.getAttribute("Employee");
                            if (!employees.isEmpty()) {
                                for (Employee e : employees) {
                                    if (!e.getPosition().equalsIgnoreCase("admin")) {
                                        out.println("<input type=\"checkbox\" name=\"empids\" value=\"" + e.getId() + "\">" + e.getId() + " ");
                                    }
                                }
                            }
                        %>
                    </td>
                </tr>
                <tr>
                    <th><label for="st">Status</label></th>
                    <td>
                        <select id="st" name="status" style="width: 19.5vw; padding-left: 1vw">
                            <option value="In Progress">In Progress</option>
                            <option value="Completed">Completed</option>
                        </select>
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
        <p style="text-align: center; color: chartreuse;">Project Created!</p>
        <%
        } else {
        %>
        <p style="text-align: center; color: crimson;">Creation Failed!</p>
        <%
                }
            }
        %>
    </div>
</div>
<jsp:include page="../stills/footer.jsp"/>
</body>
</html>
