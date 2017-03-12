package com.fu.bom.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by manlm on 8/6/2016.
 */
public interface MyUserDetailService extends UserDetailsService {

    @Override
    UserDetails loadUserByUsername(final String username);
}
