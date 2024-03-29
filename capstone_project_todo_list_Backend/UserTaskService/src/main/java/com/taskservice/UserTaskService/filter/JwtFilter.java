package com.taskservice.UserTaskService.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtFilter extends GenericFilter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        String authHeader=request.getHeader("Authorization");
        if(authHeader==null||!authHeader.startsWith("Bearer"))
        {
            throw new ServletException("Invalid Token");
        }
        else{
            String token = authHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey("privateKey").parseClaimsJws(token).getBody();
            request.setAttribute("Claims", claims);
            filterChain.doFilter(request, response);
        }
    }
}
