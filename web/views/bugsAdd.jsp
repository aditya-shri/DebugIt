<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Add Bug</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <div style="text-align: center; padding-top: 7vh; font-size: calc(0.5em + 2vw)">Add Bug</div>
        <form action="/bugs/add" method="post" style="padding-top: 5vh">
            <table style="">
                <tr>
                    <th>Name</th>
                    <td><input type="text" name="bname" required/>
                    </td>
                </tr>
                <tr>
                    <th>About</th>
                    <td><input type="text" name="about" required/>
                    </td>
                </tr>
                <tr>
                    <th><label for="it">Severity</label></th>
                    <td>
                        <select id="it" name="severity" style="width: 19.5vw; padding-left: 1vw">
                            <option value="Low" selected>Low</option>
                            <option value="Medium">Medium</option>
                            <option value="High">High</option>
                            <option value="Deadly">Deadly</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label for="ids">Project id</label></th>
                    <td>
                        <select id="ids" name="projectid" style="width: 19.5vw; padding-left: 1vw">
                            <option value="-1" selected>Select id</option>
                            <%
                                ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
                                if(!projects.isEmpty()) {
                                    for (Project p : projects) {
                                        out.println("<option value=\"" + p.getId() + "\">" + p.getId() + "</option>");
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label for="s">Employee assigned to</label></th>
                    <td>
                        <select id="s" name="empid" style="width: 19.5vw; padding-left: 1vw">
                            <option value="-1" selected>Select id</option>
                            <%
                                ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
                                if(!employees.isEmpty()) {
                                    for (Employee e : employees) {
                                        if(!e.getPosition().equalsIgnoreCase("admin") && !e.getPosition().equalsIgnoreCase("tester")) {
                                            out.println("<option value=\"" + e.getId() + "\">" + e.getId() + "</option>");
                                        }
                                    }
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th><label for="st">Status</label></th>
                    <td>
                        <select id="st" name="status" style="width: 19.5vw; padding-left: 1vw">
                            <option value="-1" selected>Select status</option>
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
                if(resp.equalsIgnoreCase("yes")){
        %>
                    <p style="text-align: center; color: chartreuse;">Bug Added!</p>
        <%
                }else{
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
