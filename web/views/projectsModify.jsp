<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Modify Projects</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <form action="/projects/modify" method="post" style="padding-top: 5vh">
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
                int index = (Integer)session.getAttribute("index");
                Project project = projects.get(index);
                String temp = "required";
                if (!r.equalsIgnoreCase("view")) {
                    temp = "readonly";
                }
        %>
                <form action="/projects/modify" method="post" style="padding-top: 5vh">
                    <table>
                        <tr>
                            <th>ID</th>
                            <td><input type="text" name="id" value="<%=project.getId()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>Name</th>
                            <td><input type="text" name="pname" value="<%=project.getPname()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>About</th>
                            <td><input type="text" name="about" value="<%=project.getAbout()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>Assign to</th>
                            <td>
                                <%
                                    ArrayList<Integer> employeesid = project.getEmployeeids();
                                    ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
                                    if (!employees.isEmpty()) {
                                        for (Employee e : employees) {
                                            if (!e.getPosition().equalsIgnoreCase("admin")) {
                                                if(employeesid.contains(e.getId())){
                                                    out.println("<input type=\"checkbox\" name=\"empids\" value=\"" + e.getId() + "\" checked>" + e.getId() + " ");
                                                }else{
                                                    out.println("<input type=\"checkbox\" name=\"empids\" value=\"" + e.getId() + "\">" + e.getId() + " ");
                                                }
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
                                    <%
                                        String stat = project.getStatus();
                                        String inpro = "";
                                        String com = "";
                                        if(stat.equalsIgnoreCase("In Progress")){
                                            inpro = "selected";
                                        }else{
                                            com = "selected";
                                        }
                                    %>
                                    <option value="In Progress" <%=inpro%>>In Progress</option>
                                    <option value="Completed" <%=com%>>Completed</option>
                                </select>
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
