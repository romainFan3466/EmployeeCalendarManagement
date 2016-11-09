package com.main.filter;

import com.main.bean.ConnectionBean;


import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by romain on 05/10/16.
 */
public class RestrictionFilter implements Filter {
    private static String SIGNIN_PAGE = "/signin.xhtml";
    private static String HOME_PAGE = "/home.xhtml";

    @Inject
    private ConnectionBean connectionBean;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {


        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        //HttpSession session = request.getSession();

        String path = request.getRequestURI().substring(request.getContextPath().length());
        if(path.startsWith("/javax.faces.resource")){
            chain.doFilter(request, response);
            return;
        }

        boolean a = path.equals("/");
        boolean b = path.equals("/signin.xhtml");
        boolean c = path.equals("/home.xhtml");

        if(!path.equals("/signin.xhtml")){
            if(!this.connectionBean.isLoggedIn()) {
            response.sendRedirect(request.getContextPath() + SIGNIN_PAGE);
            }
            else {
                if(path.equals("/")){
                    response.sendRedirect(request.getContextPath() + HOME_PAGE);
                } else {chain.doFilter(request, response);}
            }
        }
        else {
        chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
