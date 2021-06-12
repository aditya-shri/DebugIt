package com.adityashri.bit.controller;

import com.adityashri.bit.dao.EmployeesDAO;
import com.adityashri.bit.dao.ProjectDAO;
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

@WebServlet(name = "UserServlet", urlPatterns = "/user/*")
public class UserServlet extends HttpServlet {
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
        RequestDispatcher rd = request.getRequestDispatcher("/views/userView.jsp");
        rd.forward(request, response);
    }

    private void modifyPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String) request.getParameter("userSelect");
        if (sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index", Integer.parseInt(request.getParameter("selected")) - 1);
            if (value.equalsIgnoreCase("-1")) {
                request.setAttribute("getOrPost", "get");
            } else {
                request.setAttribute("getOrPost", "post");
            }
            request.setAttribute("viewOrModify", "view");
        } else {
            String[] temp = request.getParameterValues("projectids");
            String projectid = "0";
            if (temp != null) {
                for (String s : temp) {
                    projectid += " " + s;
                }
            }
            int id = Integer.parseInt((String) request.getParameter("id"));
            String ename = request.getParameter("ename");
            String position = request.getParameter("position");
            String pass = EmployeesDAO.getPass(ename).getPass();

            ArrayList<Employee> employees = (ArrayList<Employee>) session.getAttribute("Employees");
            ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
            Employee employee = (Employee)session.getAttribute("Employee");
            int index = (Integer)session.getAttribute("index");
            Employee oldEmployee = new Employee(employees.get(index));
            ArrayList<Integer> oldProjectids = oldEmployee.getProjectids();

            Employee emp = new Employee(id, ename, position, projectid, pass);

            System.out.println("In userServlet : old Employee -> " + oldEmployee);
            System.out.println("In userServlet : new Employee -> " + emp);

            boolean res = EmployeesDAO.modifyEmployee(emp);
            if (res) {
                if(!projectid.equalsIgnoreCase(oldEmployee.getProjectidsfordao())){
                    new Thread(()->{
                        ProjectDAO.updateProjects(oldEmployee.getId(),emp,projects,oldProjectids);
                        ArrayList<Project> proj = ProjectDAO.getAllProjects(employee.getProjectids());
                        session.setAttribute("Projects", proj);
                        session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                    }).start();
                }
                session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                request.setAttribute("modifyResponse", "yes");
            } else {
                request.setAttribute("modifyResponse", "no");
            }

            request.setAttribute("getOrPost", "post");
            request.setAttribute("viewOrModify", "modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/userModify.jsp");
        rd.forward(request, response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String) request.getParameter("userSelect");
        if (sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index", Integer.parseInt(request.getParameter("selected")) - 1);
            if (value.equalsIgnoreCase("-1")) {
                request.setAttribute("getOrPost", "get");
            } else {
                request.setAttribute("getOrPost", "post");
            }
            request.setAttribute("viewOrModify", "view");
        } else {
            String temp = request.getParameter("projectids");
            String projectid = "0";
            if(temp!=null && !temp.equalsIgnoreCase("")){
                temp = temp.replaceAll(", "," ");
                projectid+= " " + temp;
            }
            int id = Integer.parseInt((String) request.getParameter("id"));
            String ename = request.getParameter("ename");
            String position = request.getParameter("position");
            String pass = EmployeesDAO.getPass(ename).getPass();

            Employee emp = new Employee(id, ename, position, projectid, pass);
            Employee employee = (Employee) session.getAttribute("Employee");

            boolean res = EmployeesDAO.deleteEmployee(emp);
            if (res) {
                if(!projectid.equalsIgnoreCase("0")){
                    new Thread(()->{
                        ProjectDAO.updateProjectsAfterUserDeletion(emp);
                        ArrayList<Project> proj = ProjectDAO.getAllProjects(employee.getProjectids());
                        session.setAttribute("Projects", proj);
                        session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                    }).start();
                }else{
                    session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
                }
                request.setAttribute("modifyResponse", "yes");
            } else {
                request.setAttribute("modifyResponse", "no");
            }

            request.setAttribute("getOrPost", "post");
            request.setAttribute("viewOrModify", "modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/userDelete.jsp");
        rd.forward(request, response);
    }

    private void addPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ArrayList<Project> projects = (ArrayList<Project>) session.getAttribute("Projects");
        Employee employee = (Employee) session.getAttribute("Employee");
        String[] temp = request.getParameterValues("projectids");
        String projectid = "0";
        if (temp != null) {
            for (String s : temp) {
                projectid += " " + s;
            }
        }
        String ename = request.getParameter("ename");
        String position = request.getParameter("position");
        String password = request.getParameter("pass");

        Employee emp = new Employee(0,ename,position,projectid,password);
        boolean res = EmployeesDAO.addEmp(emp);

        if (res) {
            if(!projectid.equalsIgnoreCase("0")) {
                int id = EmployeesDAO.getEmployeeId(ename, password);
                new Thread(() -> {
                    ProjectDAO.updateProjects(id, emp, projects);
                    ArrayList<Project> proj = ProjectDAO.getAllProjects(employee.getProjectids());
                    session.setAttribute("Projects", proj);
                }).start();
            }
            session.setAttribute("Employees", EmployeesDAO.getAllEmployees());
            request.setAttribute("addResponse", "yes");
        } else {
            request.setAttribute("addResponse", "no");
        }
        request.setAttribute("getOrPost", "post");

        RequestDispatcher rd = request.getRequestDispatcher("/views/userAdd.jsp");
        rd.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = reqUri(request);
        switch (url) {
            case "modify":
                modifyPost(request, response);
                break;
            case "add":
                addPost(request, response);
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
                url = "/views/userModify.jsp";
                break;
            case "add":
                url = "/views/userAdd.jsp";
                break;
            case "delete":
                url = "/views/userDelete.jsp";
                break;
            default:
                url = "/views/userView.jsp";
                break;
        }
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request, response);
    }
}
