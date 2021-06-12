<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.dao.EmployeesDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Delete User</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bugdelete">
        <form action="/user/delete" method="post" style="padding-top: 5vh">
            <label for="ids">Employee id : </label>
            <select id="ids" name="selected">
                <option value="-1" selected>Select id</option>
                <%
                    ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
                    Employee emp = (Employee) session.getAttribute("Employee");
                    int i = 1;
                    if (!employees.isEmpty()) {
                        for (Employee e : employees) {
                            if (!e.getPosition().equalsIgnoreCase("admin")) {
                                out.println("<option value=\"" + i + "\">" + e.getId() + "</option>");
                            }
                            i++;
                        }
                    }
                %>
            </select><br><br>
            <input type="submit" name="userSelect" value="Load">
        </form>
        <%
            String res = (String) request.getAttribute("getOrPost");
            if (res.equalsIgnoreCase("post")) {
                String r = (String) request.getAttribute("viewOrModify");
                String temp = "readonly";
                if (r.equalsIgnoreCase("view")) {
                    int index = (Integer)session.getAttribute("index");
                    Employee employee = employees.get(index);
        %>
        <form action="/user/delete" method="post" style="padding-top: 5vh">
            <table style="">
                <tr>
                    <th>ID</th>
                    <td><input type="text" name="id" value="<%=employee.getId()%>" <%=temp%>/>
                    </td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><input type="text" name="ename" value="<%=employee.getEname()%>" <%=temp%>/>
                    </td>
                </tr>
                <tr>
                    <th>Position</th>
                    <td><input type="text" name="position" value="<%=employee.getPosition()%>" <%=temp%>/>
                    </td>
                </tr>
                    <th>Projects assigned</th>
                    <td><input type="text" name="projectids" value="<%=employee.getProjectidsInStr()%>" <%=temp%>/>
                    </td>
                </tr>
            </table>
            <br>
            <input type="submit" name="userDelete" value="Delete">
        </form>
        <%
        } else {
            String resp = (String) request.getAttribute("modifyResponse");
            if (resp.equalsIgnoreCase("yes")) {
        %>
        <p style="text-align: center; color: chartreuse;">User Deleted!</p>
        <%
        } else {
        %>
        <p style="text-align: center; color: crimson;">Deletion failed!</p>
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
