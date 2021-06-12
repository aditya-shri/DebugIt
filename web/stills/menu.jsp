<%@ page import="com.adityashri.bit.constant.Constants" %>
<%@ page import="com.adityashri.bit.model.Employee" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<div class="sidenav">
    <a href="/home">Home</a>
    <%=Constants.getMenuItems(((Employee)session.getAttribute("Employee")).getPosition())%>
    <script>
        var dropdown = document.getElementsByClassName("dropdown-btn");
        var i;
        for (i = 0; i < dropdown.length; i++) {
            dropdown[i].addEventListener("click", function () {
                this.classList.toggle("active");
                var dropdownContent = this.nextElementSibling;
                if (dropdownContent.style.display === "block") {
                    dropdownContent.style.display = "none";
                } else {
                    dropdownContent.style.display = "block";
                }
                for (var j = 0; j < dropdown.length; j++) {
                    if (this!==dropdown[j] && dropdown[j].classList.contains("active")) {
                        dropdown[j].classList.toggle("active");
                        var dropdownContent2 = dropdown[j].nextElementSibling;
                        if (dropdownContent2.style.display === "block") {
                            dropdownContent2.style.display = "none";
                        } else {
                            dropdownContent2.style.display = "block";
                        }
                    }
                }
            });
        }
    </script>
</div>
<div class="settingnav">
    <a href="/settings">Settings</a>
</div>
</body>
</html>
