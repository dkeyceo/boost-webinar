package com.dkey.boost.filter;

import com.dkey.boost.auth.AuthBean;
import com.dkey.boost.auth.Person;
import com.dkey.boost.auth.PersonBean;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = "/secured/*")
public class AuthFilter implements Filter {
    @EJB
    private AuthBean authBean;
    @Inject
    private PersonBean personBean;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if(!personBean.isAuthenticated()){
            httpResponse.sendRedirect("login.xhtml");
            return;
        }
        String resource = httpRequest.getRequestURI();
        if(!authBean.isGranted(personBean.getLogin(),resource)){
            httpResponse.sendRedirect("denied.xhtml");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
