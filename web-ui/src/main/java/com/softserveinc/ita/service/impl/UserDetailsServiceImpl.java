package com.softserveinc.ita.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

public class UserDetailsServiceImpl implements UserDetailsService {

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", username);

        com.softserveinc.ita.entity.User user = null;
        try{
            user = restTemplate.getForObject("http://localhost:8080/users/param?email="+username, com.softserveinc.ita.entity.User.class);
        }
        catch (Exception ex){
            throw new UsernameNotFoundException("User with such username (email) not found.");
        }
            return new User(
                    username,
                    user.getPassword(),
                    enabled,
                    accountNonExpired,
                    credentialsNonExpired,
                    accountNonLocked,
                    getAuthorities(1));
    }

    /**
     * Retrieves a collection of {@link org.springframework.security.core.GrantedAuthority} based on a numerical role
     * @param role the numerical role
     * @return a collection of {@link org.springframework.security.core.GrantedAuthority
     */
    public Collection<? extends GrantedAuthority> getAuthorities(Integer role) {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles(role));
        return authList;
    }
    /**
     * Wraps {@link String} roles to {@link org.springframework.security.core.authority.SimpleGrantedAuthority} objects
     * @param roles {@link String} of roles
     * @return list of granted authorities
     */
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
    /**
     * Converts a numerical role to an equivalent list of roles
     * @param role the numerical role
     * @return list of roles as as a list of {@link String}
     */
    public List<String> getRoles(Integer role) {
        List<String> roles = new ArrayList<String>();

        if (role.intValue() == 1) {
            roles.add("ROLE_USER");
            roles.add("ROLE_ADMIN");

        } else if (role.intValue() == 2) {
            roles.add("ROLE_USER");
        }

        return roles;
    }
}
