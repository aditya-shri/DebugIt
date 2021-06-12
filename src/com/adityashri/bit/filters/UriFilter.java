package com.adityashri.bit.filters;

import com.adityashri.bit.constant.Constants;
import com.adityashri.bit.dao.EmployeesDAO;
import com.adityashri.bit.model.Employee;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

import static com.adityashri.bit.constant.Constants.checkUri;

@WebFilter(filterName = "UriFilter",urlPatterns = "/*")
public class UriFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        String uri = request.getRequestURI();
        if(uri.equalsIgnoreCase("/") ||
            uri.equalsIgnoreCase("/login") ||
            uri.equalsIgnoreCase("/error") ||
            uri.equalsIgnoreCase("/logout")){
            chain.doFilter(req,resp);
        }else{
            Employee emp = (Employee) EmployeesDAO.getEmp((String)session.getAttribute("ename"));
            session.setAttribute("Employee",emp);
            boolean res = Constants.checkUri(uri,emp.getPosition());

            if(res ||
                uri.equalsIgnoreCase("/home") ||
                uri.equalsIgnoreCase("/settings")){
                 chain.doFilter(req,resp);
            }else{
                response.sendRedirect("/error");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
