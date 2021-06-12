package com.adityashri.bit.controller;

import com.adityashri.bit.dao.BugsDAO;
import com.adityashri.bit.model.Bugs;
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

@WebServlet(name = "BugsServlet",urlPatterns = "/bugs/*")
public class BugsServlet extends HttpServlet {
    private String reqUri(HttpServletRequest request){
        String temp = request.getPathInfo();
        temp = temp.replaceAll("/","");
        return temp;
    }

    private void viewPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String value = (String)request.getParameter("selected");
        request.setAttribute("index",Integer.parseInt(request.getParameter("selected"))-1);
        if(value.equalsIgnoreCase("-1")){
            request.setAttribute("getOrPost","get");
        }else{
            request.setAttribute("getOrPost","post");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/bugsView.jsp");
        rd.forward(request,response);
    }

    private void modifyPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String)request.getParameter("bugSelect");
        if(sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index",Integer.parseInt(request.getParameter("selected"))-1);
            if(value.equalsIgnoreCase("-1")){
                request.setAttribute("getOrPost","get");
            }else{
                request.setAttribute("getOrPost","post");
            }
            request.setAttribute("viewOrModify","view");
        }else{
            int projectid = Integer.parseInt((String)request.getParameter("projectid"));
            int empid = Integer.parseInt((String) request.getParameter("empid"));
            int id = Integer.parseInt(request.getParameter("id"));
            String bname = request.getParameter("bname");
            String about = request.getParameter("about");
            String severity = request.getParameter("severity");
            String status = (String)request.getParameter("status");
            Bugs bug = new Bugs(id,bname,about,severity,projectid,empid,status);
            boolean res = BugsDAO.modifyBug(bug);
            if(res){
                ArrayList<Bugs> bugs = BugsDAO.getBugsForAllProjects((ArrayList<Project>) session.getAttribute("Projects"));
                session.setAttribute("Bugs",bugs);
                request.setAttribute("modifyResponse","yes");
            }else{
                request.setAttribute("modifyResponse","no");
            }
            request.setAttribute("getOrPost","post");
            request.setAttribute("viewOrModify","modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/bugsModify.jsp");
        rd.forward(request,response);
    }

    private void deletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String sub = (String)request.getParameter("bugSelect");
        if(sub != null) {
            String value = (String) request.getParameter("selected");
            session.setAttribute("index",Integer.parseInt(request.getParameter("selected"))-1);
            if(value.equalsIgnoreCase("-1")){
                request.setAttribute("getOrPost","get");
            }else{
                request.setAttribute("getOrPost","post");
            }
            request.setAttribute("viewOrModify","view");
        }else{
            int projectid = Integer.parseInt((String)request.getParameter("projectid"));
            int empid = Integer.parseInt((String) request.getParameter("empid"));
            int id = Integer.parseInt(request.getParameter("id"));
            String bname = request.getParameter("bname");
            String about = request.getParameter("about");
            String severity = request.getParameter("severity");
            String status = (String)request.getParameter("status");
            Bugs bug = new Bugs(id,bname,about,severity,projectid,empid,status);
            boolean res = BugsDAO.deleteBug(bug);
            if(res){
                ArrayList<Bugs> bugs = BugsDAO.getBugsForAllProjects((ArrayList<Project>) session.getAttribute("Projects"));
                session.setAttribute("Bugs",bugs);
                request.setAttribute("modifyResponse","yes");
            }else{
                request.setAttribute("modifyResponse","no");
            }
            request.setAttribute("getOrPost","post");
            request.setAttribute("viewOrModify","modify");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/bugsDelete.jsp");
        rd.forward(request,response);
    }

    private void addPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int projectid = Integer.parseInt(request.getParameter("projectid"));
        int empid = Integer.parseInt(request.getParameter("empid"));
        String status = (String)request.getParameter("status");
        if (projectid != -1 && empid != -1 && !status.equalsIgnoreCase("-1")) {
            String bname = request.getParameter("bname");
            String about = request.getParameter("about");
            String severity = request.getParameter("severity");
            int id = BugsDAO.getBugsCount();

            Bugs bug = new Bugs(id, bname, about, severity, projectid, empid, status);
            boolean res = BugsDAO.addBug(bug);
            if (res) {
                ArrayList<Bugs> bugs = BugsDAO.getBugsForAllProjects((ArrayList<Project>) session.getAttribute("Projects"));
                session.setAttribute("Bugs", bugs);
                request.setAttribute("addResponse", "yes");
            } else {
                request.setAttribute("addResponse", "no");
            }
            request.setAttribute("getOrPost", "post");
        }else{
            request.setAttribute("getOrPost", "post");
            request.setAttribute("addResponse", "no");
        }
        RequestDispatcher rd = request.getRequestDispatcher("/views/bugsAdd.jsp");
        rd.forward(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String url = reqUri(request);
        switch(url){
            case "modify":
                modifyPost(request,response);
                break;
            case "add":
                addPost(request,response);
            case "delete":
                deletePost(request,response);
            default:
                viewPost(request,response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("getOrPost","get");
        String url = "";
        switch(reqUri(request)){
            case "modify":
                url = "/views/bugsModify.jsp";
                break;
            case "add":
                url = "/views/bugsAdd.jsp";
                break;
            case "delete":
                url = "/views/bugsDelete.jsp";
                break;
            default:
                url = "/views/bugsView.jsp";
                break;
        }
        RequestDispatcher rd = request.getRequestDispatcher(url);
        rd.forward(request,response);
    }
}
