package com.dewpoint.rts.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dewpoint.rts.dao.UserDao;
import com.dewpoint.rts.errorconfig.ApiOperationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userName);
        List<com.dewpoint.rts.model.User> users = this.userDao.findByNamedQueryAndNamedParams("User.findSpecific", params);
        if(users == null || users.isEmpty() || users.size() == 0) {
            throw new ApiOperationException("Unable to authenticate. Supply correct user information");
        }

        GrantedAuthority authority = new SimpleGrantedAuthority(users.get(0).getRole());
        UserDetails userDetails = (UserDetails)new User(users.get(0).getUserId(), users.get(0).getPassword(), Arrays.asList(authority));
        return userDetails;
    }
}