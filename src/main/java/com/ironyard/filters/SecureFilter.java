package com.ironyard.filters;

import com.ironyard.data.CarUser;
import com.ironyard.security.TokenMaster;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by jasonskipper on 2/7/17.
 */
@WebFilter(filterName = "AuthFilter")
public class SecureFilter implements Filter {
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = ((HttpServletRequest) request);
        HttpServletResponse res = ((HttpServletResponse) response);
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "Authorization, x-access-token, Content-Type, Accept, x-requested-with, Cache-Control");
        // check for key based authentication
        boolean authorized = false;
        Long userId = null;
        String authToken = req.getHeader("x-access-token");
        TokenMaster tm = new TokenMaster();
        if(authToken!=null) {
            userId = tm.validateAndGetUserId(authToken);
        }
        if(authToken != null && userId != null){
            // send request on its way like normal
            req.setAttribute("authToken", authToken);
            req.setAttribute("userId", userId);
            filterChain.doFilter(req, res);
        }else{
            //res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            res.getWriter().write("Sorry, you are unauthorized to view this page.");
        }
    }


    public void init(FilterConfig config) throws ServletException {

    }

}