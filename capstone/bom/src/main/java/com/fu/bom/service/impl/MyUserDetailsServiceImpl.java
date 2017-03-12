package com.fu.bom.service.impl;

import com.fu.bom.service.MyUserDetailService;
import com.fu.database.dao.EmployeeDao;
import com.fu.database.dao.RoleDao;
import com.fu.database.entity.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by manlm on 7/24/2016.
 */
@Service
public class MyUserDetailsServiceImpl implements MyUserDetailService {

    private static final Logger LOG = Logger.getLogger(MyUserDetailsServiceImpl.class);

    private final EmployeeDao employeeDao;

    private final RoleDao roleDao;

    /**
     * Constructor
     *
     * @param employeeDao
     * @param roleDao
     */
    @Autowired
    public MyUserDetailsServiceImpl(EmployeeDao employeeDao, RoleDao roleDao) {
        this.employeeDao = employeeDao;
        this.roleDao = roleDao;
    }

    @Override
    public UserDetails loadUserByUsername(final String username) {
        LOG.info("[loadUserByUsername] Start: username = " + username);
        Employee employee = employeeDao.getByUsername(username);

        if (employee == null) {
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        List<GrantedAuthority> authorities = buildUserAuthority(employee.getRole());

        LOG.info("[loadUserByUsername] End");
        return buildUserForAuthentication(employee, authorities);
    }

    private User buildUserForAuthentication(Employee employee,
                                            List<GrantedAuthority> authorities) {
        LOG.info("[buildUserForAuthentication] Start: username = " + employee.getUsername());
        LOG.info("[buildUserForAuthentication] End");
        return new User(employee.getUsername(),
                employee.getPassword(), true,
                true, true, true, authorities);
    }

    List<GrantedAuthority> buildUserAuthority(int userRoles) {
        LOG.info("[buildUserAuthority] Start: userRoles = " + userRoles);
        Set<GrantedAuthority> setAuths = new HashSet<>();

        // Build user's authorities
        String role = roleDao.getById(userRoles).getName();
        setAuths.add(new SimpleGrantedAuthority(role));

        List<GrantedAuthority> result = new ArrayList<>(setAuths);

        LOG.info("[buildUserAuthority] End");
        return result;
    }
}
