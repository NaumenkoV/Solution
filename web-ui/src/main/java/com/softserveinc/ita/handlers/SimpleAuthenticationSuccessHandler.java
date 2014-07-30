package com.softserveinc.ita.handlers;

import com.softserveinc.ita.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.getContext();
        Object principalObj = context.getAuthentication().getPrincipal();
        String principal = ((org.springframework.security.core.userdetails.User) principalObj).getUsername();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", principal);

        User user = null;
        try{
            user = (User) restTemplate.getForObject("http://localhost:8080/users/param?email="+principal, User.class);
            if(user==null){
                throw new UsernameNotFoundException("User with such username (email) not found.");
            }
            // Create the User Cookie
            Cookie cookie = new Cookie("userId",user.getId());

            request.getSession().setAttribute("user", user);
            response.addCookie(cookie);
            response.sendRedirect("/");
        }
        catch (Exception ex){
            response.sendRedirect("/login?error");
        }

    }
}