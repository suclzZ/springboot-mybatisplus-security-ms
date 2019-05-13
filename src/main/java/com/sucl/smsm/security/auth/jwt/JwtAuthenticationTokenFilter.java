package com.sucl.smsm.security.auth.jwt;

import com.sucl.smsm.security.auth.PreAbstractAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT Token过滤器
 * request head : JWT-TOKEN: JWT <token>
 *
 * @author hackyo
 * Created on 2017/12/8 9:28.
 */
public class JwtAuthenticationTokenFilter extends PreAbstractAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private JwtTokenHelper jwtTokenHelper;

    public JwtAuthenticationTokenFilter(UserDetailsService userDetailsService, JwtTokenHelper jwtTokenHelper) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader("JWT-TOKEN");
        String tokenHead = "JWT ";
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            String authToken = authHeader.substring(tokenHead.length());
            String username = jwtTokenHelper.getUsernameFromToken(authToken);
            if (username != null && authenticationIsRequired(username)) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if (jwtTokenHelper.validateToken(authToken, userDetails)) {//校验userDetails的一致性,这里是认证的核心校验，后面的只是将用户的信息作为媒介
                    //构造参数无认证意义 只是保证了上下文中的一致性
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean authenticationIsRequired(String username) {
        Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();

        if (existingAuth == null || !existingAuth.isAuthenticated()) {
            return true;
        }

        if (existingAuth instanceof UsernamePasswordAuthenticationToken&& !existingAuth.getName().equals(username)) {
            return true;
        }

        if (existingAuth instanceof AnonymousAuthenticationToken) {
            return true;
        }

        return false;
    }

}
