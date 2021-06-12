<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.dao.EmployeesDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Modify User</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <form action="/user/modify" method="post" style="padding-top: 5vh">
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
                int index = (Integer)session.getAttribute("index");
                Employee employee = employees.get(index);
                String temp = "required";
                if (!r.equalsIgnoreCase("view")) {
                    temp = "readonly";
                }
        %>
        <form action="/user/modify" method="post" style="padding-top: 5vh">
            <table style="">
                <tr>
                    <th>ID</th>
                    <td><input type="text" name="id" value="<%=employee.getId()%>" readonly/>
                    </td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><input type="text" name="ename" value="<%=employee.getEname()%>" <%=temp%>/>
                    </td>
                </tr>
                <tr>
                    <th><label for="q">Position</label></th>
                    <td>
                        <select id="q" name="position" style="width: 19.5vw; padding-left: 1vw">
                            <%
                                ArrayList<String> positions = Constants.getEmpoyeePosition();
                                for (String item : positions) {
                                    if (item.equalsIgnoreCase(employee.getPosition())) {
                                        out.println("<option value=\"" + item + "\" selected>" + item + "</option>");
                                    } else {
                                        out.println("<option value=\"" + item + "\">" + item + "</option>");
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>Project id</th>
                    <td>
                        <%
                            ArrayList<Project> projects = (ArrayList<Project>)session.getAttribute("Projects");
                            ArrayList<Integer> alreadyIn = employee.getProjectids();
                            int j = 1;
                            if (!projects.isEmpty()) {
                                for (Project p : projects) {
                                    if (alreadyIn.contains(p.getId())) {
                                        out.println("<input type=\"checkbox\" name=\"projectids\" value=\"" + p.getId() + "\" checked >" + p.getId() + " ");
                                    } else {
                                        out.println("<input type=\"checkbox\" name=\"projectids\" value=\"" + p.getId() + "\">" + p.getId() + " ");
                                    }
                                    j++;
                                }
                            }
                        %>
                    </td>
                </tr>
            </table>
            <%
                if (r.equalsIgnoreCase("view")) {
            %>
            <br>
            <input type="submit" name="userModify" value="Modify">
        </form>
        <%
        } else {
        %>
        </form>
        <%
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
