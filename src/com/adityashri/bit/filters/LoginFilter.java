package com.adityashri.bit.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession(false);

        boolean loggedIn = session != null && session.getAttribute("ename") != null;
        boolean logginUrl = request.getRequestURI().equals(request.getContextPath() + "/login");
        boolean baseUrl = request.getRequestURI().equals(request.getContextPath() + "/");

        if (loggedIn && (logginUrl || baseUrl)) {
            response.sendRedirect(request.getContextPath() + "/home");
        } else if (loggedIn || logginUrl) {
            chain.doFilter(req, resp);
        } else if (baseUrl) {
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }

    @Override
    public void destroy() {
    }
}
