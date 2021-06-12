package com.adityashri.bit.controller;

import com.adityashri.bit.dao.BugsDAO;
import com.adityashri.bit.dao.EmployeesDAO;
import com.adityashri.bit.dao.ProjectDAO;
import com.adityashri.bit.model.Bugs;
import com.adityashri.bit.model.Employee;
import com.adityashri.bit.model.Project;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProjectsServlet", urlPatterns = "/projects/*")
public class ProjectsServlet extends HttpServlet {
    private String reqUri(HttpServletRequest request) {
        String temp = request.getPathInfo();
        temp = temp.replaceAll("/", "");
        return temp;
    }

    private void viewPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String value = (String) request.getParameter("selected");
        request.setAttribute("index", Integer.parseInt(request.getParameter("selected")) - 1);
        if (value.equalsIgnoreCase("-1")) {
            request.setAttribute("getOrPost", "get");
        } else {
            request.setAttribute("getOrPost", "post");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/projectsView.jsp");
        rd.forward(request, response);
    }

    private void modifyPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String) request.getParameter("projectSelect");
        if (sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index",Integer.parseInt(value) - 1);
            if (value.equalsIgnoreCase("-1")) {
                request.setAttribute("getOrPost", "get");
            } else {
                request.setAttribute("getOrPost", "post");
            }
            request.setAttribute("viewOrModify", "view");
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            String pname = request.getParameter("pname");
            String about = request.getParameter("about");
            String status = (String) request.getParameter("status");
            String[] temp = request.getParameterValues("empids");
            String empids = String.valueOf(((Employee)session.getAttribute("Employee")).getId());
            if (temp != null) {
                for (String s : temp) {
                    empids += " " + s;
                }
            }

            ArrayList<Project> oldprojects = (ArrayList<Project>)session.getAttribute("Projects");
            int index = (Integer)session.getAttribute("index");
            Project oldproject = new Project(oldprojects.get(index));

            Project p = new Project(id,pname,about,empids,status);

            boolean res = ProjectDAO.modifyProject(p);
            if (res) {
                Employee emp = (Employee) session.getAttribute("Employee");
                ArrayList<Project> projects = ProjectDAO.getAllProjects(emp.getProjectids());
                session.setAttribute("Projects",projects);

                String oldEmployees = oldproject.getEmployeeidsfordao();
                if(empids.length() != oldEmployees.length()){
                    String finalEmpids = empids;
                    new Thread(() -> {
                        EmployeesDAO.updateEmployees(id,oldproject.getEmployeeids(), finalEmpids, (ArrayList<Employee>) session.getAttribute("Employees"));
                        session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                    }).start();
                }
                System.out.println("In projectServlet : " + p.getStatus() + " " + oldproject.getStatus());
                if(!p.getStatus().equalsIgnoreCase(oldproject.getStatus())){
                    new Thread(() ->{
                        BugsDAO.updateAfterProjectStatusChange(p.getId(),p.getStatus());
                        ArrayList<Project> projs = (ArrayList<Project>) session.getAttribute("Projects");
                        ArrayList<Bugs> bugs = BugsDAO.getBugsForAllProjects(projs);
                        session.setAttribute("Bugs",bugs);
                    }).start();
                }
                request.setAttribute("modifyResponse", "yes");
            } else {
                request.setAttribute("modifyResponse", "no");
            }

            request.setAttribute("getOrPost", "post");
            request.setAttribute("viewOrModify", "modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/projectsModify.jsp");
        rd.forward(request, response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String) request.getParameter("projectSelect");
        if (sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index",Integer.parseInt(value) - 1);
            if (value.equalsIgnoreCase("-1")) {
                request.setAttribute("getOrPost", "get");
            } else {
                request.setAttribute("getOrPost", "post");
            }
            request.setAttribute("viewOrModify", "view");
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            String pname = request.getParameter("pname");
            String about = request.getParameter("about");
            String status = (String) request.getParameter("status");
            String empids = request.getParameter("empids").replaceAll(", "," ").trim();

            Project p = new Project(id,pname,about,empids,status);
            System.out.println("In project servlet : Project -> " + p);

            boolean res = ProjectDAO.deleteProject(p);
            if (res) {
                Employee emp = (Employee) session.getAttribute("Employee");
                ArrayList<Project> projects = ProjectDAO.getAllProjects(emp.getProjectids());
                session.setAttribute("Projects",projects);

                new Thread(() -> {
                        EmployeesDAO.updateEmployeesAfterProjectDelete(p);
                        session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                        BugsDAO.deleteAfterProjectdeletion(p);
                        ArrayList<Bugs> bugs = new ArrayList<>();
                        if(projects!=null) {
                            bugs = BugsDAO.getBugsForAllProjects(projects);
                        }
                        session.setAttribute("Bugs",bugs);
                }).start();

                request.setAttribute("modifyResponse", "yes");
            } else {
                request.setAttribute("modifyResponse", "no");
            }

            request.setAttribute("getOrPost", "post");
            request.setAttribute("viewOrModify", "modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/projectsDelete.jsp");
        rd.forward(request, response);
    }

    private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String pname = request.getParameter("pname");
        String about = request.getParameter("about");
        String status = (String) request.getParameter("status");
        String[] temp = request.getParameterValues("empids");
        String empids = String.valueOf(((Employee)session.getAttribute("Employee")).getId());
        if (temp != null) {
            for (String s : temp) {
                empids += " " + s;
            }
        }

        Project p = new Project(0,pname,about,empids,status);
        boolean res = ProjectDAO.addProject(p);
        int id = ProjectDAO.getProjectid(pname);
        if (res && id>0) {
            Employee emp = (Employee) session.getAttribute("Employee");
            emp.setProjectid(id);
            EmployeesDAO.modifyEmployee(emp);
            session.setAttribute("Employee",emp);
            ArrayList<Project> projects = ProjectDAO.getAllProjects(emp.getProjectids());
            session.setAttribute("Projects",projects);
            request.setAttribute("addResponse", "yes");

            if(empids.length()>1){
                empids = empids.substring(2);
                String finalEmpids = empids;
                new Thread(() -> {
                    EmployeesDAO.updateEmployees(id, finalEmpids, (ArrayList<Employee>) session.getAttribute("Employees"));
                    session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                }).start();
            }
        } else {
            request.setAttribute("addResponse", "no");
        }

        request.setAttribute("getOrPost", "post");
        RequestDispatcher rd = request.getRequestDispatcher("/views/projectsCreate.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = reqUri(request);
        switch (url) {
            case "modify":
                modifyPost(request, response);
                break;
            case "create":
                createPost(request, response);
                break;
            case "delete":
                deletePost(request, response);
                break;
            default:
                viewPost(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("getOrPost", "get");
        String url = "";
        switch (reqUri(request)) {
            case "modify":
                url = "/views/projectsModify.jsp";
                break;
            case "create":
                url = "/views/projectsCreate.jsp";
                break;
            case "delete":
                url = "/views/projectsDelete.jsp";
                break;
            default:
                url = "/views/projectsView.jsp";
                break;
        }
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
