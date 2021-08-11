package com.fjminred.crm.web.filter;

import com.fjminred.crm.settings.domian.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String path = request.getServletPath();
        if ("/login.jsp".equals(path) || "/settings/user/login.do".equals(path)){
            filterChain.doFilter(req,resp);
        }else{
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");

            if (user != null){
                filterChain.doFilter(req,resp);
            }else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        }


    }

    @Override
    public void destroy() {

    }
}
