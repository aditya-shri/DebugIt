package com.adityashri.bit.controller;

import com.adityashri.bit.dao.EmployeesDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "SettingsServlet", urlPatterns = "/settings")
public class SettingsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        request.setAttribute("getOrPost","post");
        String userName = (String)session.getAttribute("ename");
        String password = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");
        boolean res = EmployeesDAO.updatePassword(userName,password,newPassword);
        request.setAttribute("updateResult",res);
        RequestDispatcher rd = request.getRequestDispatcher("/views/settings.jsp");
        rd.forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("getOrPost","get");
        RequestDispatcher rd = request.getRequestDispatcher("/views/settings.jsp");
        rd.forward(request,response);
    }
}
