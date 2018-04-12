package com.dkey.boost.filter;

import com.dkey.boost.auth.AuthBean;
import com.dkey.boost.auth.PersonBean;
import org.apache.commons.lang3.StringUtils;

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
        String resource = httpRequest.getRequestURI();

        if(!personBean.isAuthenticated()){
            personBean.setInitialRequestURI(resource);
            httpResponse.sendRedirect(httpRequest.getContextPath()+"/login.xhtml");
            return;
        }

        if(!authBean.isGranted(personBean.getLogin(),resource)){
            httpResponse.sendRedirect("error.xhtml");
            return;
        }
        if(StringUtils.isNotEmpty(personBean.getInitialRequestURI())) {
            httpResponse.sendRedirect(personBean.getInitialRequestURI());
            personBean.setInitialRequestURI("");
            return;
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
