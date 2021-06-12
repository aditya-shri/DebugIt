<%@ page import="com.adityashri.bit.model.Bugs" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Project" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=Constants.AppName()%> - Delete Bugs</title>
</head>
<body>
<jsp:include page="../stills/header.jsp"/>
<jsp:include page="../stills/menu.jsp"/>
<div class="main">
    <div class="bugdelete">
        <form action="/bugs/delete" method="post" style="padding-top: 5vh">
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
                String temp = "readonly";
                if (r.equalsIgnoreCase("view")) {
                    int index = (int)session.getAttribute("index");
                    Bugs bug = bugs.get(index);
        %>
                <form action="/bugs/delete" method="post" style="padding-top: 5vh">
                    <table style="">
                        <tr>
                            <th>ID</th>
                            <td><input type="text" name="id" value="<%=bug.getId()%>" <%=temp%>/>
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
                            <th>Severity</th>
                            <td><input type="text" name="severity" value="<%=bug.getSeverity()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>Project ID</th>
                            <td><input type="text" name="projectid" value="<%=bug.getProjectid()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>Assigned to</th>
                            <td><input type="text" name="empid" value="<%=bug.getEmpid()%>" <%=temp%>/>
                            </td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td><input type="text" name="status" value="<%=bug.getStatus()%>" <%=temp%>/>
                            </td>
                        </tr>
                    </table>
                        <br>
                        <input type="submit" name="bugMDelete" value="Delete">
                </form>
        <%
                }else{
                    String resp = (String) request.getAttribute("modifyResponse");
                    if(resp.equalsIgnoreCase("yes")){
        %>
                <p style="text-align: center; color: chartreuse;">Bug Deleted!</p>
        <%
                    }else{
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
