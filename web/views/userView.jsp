<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
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
    <title><%=Constants.AppName()%> - View Users</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
    <form action="/user/view" method="post" style="padding-top: 5vh">
        <label for="ids">Employee id : </label>
        <select id="ids" name="selected">
            <option value="-1" selected>Select id</option>
            <%
                ArrayList<Employee> users = (ArrayList<Employee>) session.getAttribute("Employees");
                Employee emp = (Employee) session.getAttribute("Employee");
                if(!users.isEmpty()) {
                    int i=1;
                    for (Employee e : users) {
                        if(emp.getId() != e.getId()) {
                            out.println("<option value=\"" + i + "\">" + e.getId() + "</option>");
                        }
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
        Employee employee = users.get(index);
        %>
            <table style="">
                <tr>
                    <th>ID</th>
                    <td><%=employee.getId()%></td>
                </tr>
                <tr>
                    <th>Name</th>
                    <td><%=employee.getEname()%></td>
                </tr>
                <tr>
                    <th>Position</th>
                    <td><%=employee.getPosition()%></td>
                </tr>
                <tr>
                    <th>Projects assigned</th>
                    <td><%=employee.getProjectidsInStr().equalsIgnoreCase("")?"-":employee.getProjectidsInStr()%></td>
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
