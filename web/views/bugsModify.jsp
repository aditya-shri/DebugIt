<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Modify Bugs</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bug">
        <form action="/bugs/modify" method="post" style="padding-top: 5vh">
            <label for="ids">Bug id : </label>
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
            <input type="submit" name="bugSelect" value="Load">
        </form>
        <%
            String res = (String) request.getAttribute("getOrPost");
            if (res.equalsIgnoreCase("post")) {
                String r = (String) request.getAttribute("viewOrModify");
                int index = (int)session.getAttribute("index");
                Bugs bug = bugs.get(index);
                String temp = "required";
                if (!r.equalsIgnoreCase("view")) {
                    temp = "readonly";
                }
        %>
                <form action="/bugs/modify" method="post" style="padding-top: 5vh">
                    <table style="">
                        <tr>
                            <th>ID</th>
                            <td><input type="text" name="id" value="<%=bug.getId()%>" readonly/>
                            </td>
                        </tr>
                        <tr>
                            <th>Name</th>
                            <td><input type="text" name="bname" value="<%=bug.getBname()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>About</th>
                            <td><input type="text" name="about" value="<%=bug.getAbout()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="it">Severity</label></th>
                            <td>
                                <select id="it" name="severity" style="width: 19.5vw; padding-left: 1vw">
                                    <%
                                        ArrayList<String> bugsSeverity = Constants.getBugsSeverity();
                                        for(String item : bugsSeverity){
                                            if(item.equalsIgnoreCase(bug.getSeverity())){
                                                out.println("<option value=\""+item+"\" selected>"+item+"</option>");
                                            }else{
                                                out.println("<option value=\""+item+"\">"+item+"</option>");
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <th><label for="idss">Project id</label></th>
                            <td>
                                <select id="idss" name="projectid" style="width: 19.5vw; padding-left: 1vw">
                                    <%
                                        ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
                                        int j = 1;
                                        if(!projects.isEmpty()) {
                                            for (Project p : projects) {
                                                if(p.getId() == bug.getProjectid()){
                                                    out.println("<option value=\"" + p.getId() + "\" selected>" + p.getId() + "</option>");
                                                }else {
                                                    out.println("<option value=\"" + p.getId() + "\">" + p.getId() + "</option>");
                                                }
                                                j++;
                                            }
                                        }
                                    %>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <%
                                Employee emp = (Employee) session.getAttribute("Employee");
                                if(!emp.getPosition().equalsIgnoreCase("admin")){
                            %>
                                    <th>Employee assigned to</th>
                                    <td><input type="text" name="empid" value="<%=bug.getEmpid()%>" readonly/>
                                    </td>
                            <%
                                }else{
                            %>
                                    <th><label for="s">Employee assigned to</label></th>
                                    <td>
                                        <select id="s" name="empid" style="width: 19.5vw; padding-left: 1vw">
                                            <%
                                                ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
                                                int k = 1;
                                                if(!employees.isEmpty()) {
                                                    for (Employee e : employees) {
                                                        if(!e.getPosition().equalsIgnoreCase("admin")) {
                                                            if (e.getId() == bug.getEmpid()) {
                                                                out.println("<option value=\"" + e.getId() + "\" selected>" + e.getId() + "</option>");
                                                            } else {
                                                                out.println("<option value=\"" + e.getId() + "\">" + e.getId() + "</option>");
                                                            }
                                                            k++;
                                                        }
                                                    }
                                                }
                                            %>
                                        </select>
                                    </td>
                            <%
                                }
                            %>
                        </tr>
                        <tr>
                            <th><label for="st">Status</label></th>
                            <td>
                                <select id="st" name="status" style="width: 19.5vw; padding-left: 1vw">
                                    <%
                                        String stat = bug.getStatus();
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
                        <input type="submit" name="bugModify" value="Modify">
                </form>
        <%
                }else{
        %>
                </form>
        <%
                    String resp = (String) request.getAttribute("modifyResponse");
                    if(resp.equalsIgnoreCase("yes")){
        %>
                <p style="text-align: center; color: chartreuse;">Bug Updated!</p>
        <%
                    }else{
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
