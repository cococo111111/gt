package com.fu.api.service.impl;

import com.fu.api.model.Account;
import com.fu.api.service.AuthenticationService;
import com.fu.common.util.IdUtil;
import com.fu.database.dao.EmployeeDao;
import com.fu.database.entity.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by manlm on 11/11/2016.
 */
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private static final Logger LOG = Logger.getLogger(AuthenticationServiceImpl.class);

    private final EmployeeDao employeeDao;

    @Autowired
    public AuthenticationServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public String login(Account account) {
        LOG.info("[login] Start");
        String token = "";
        Employee employee = employeeDao.getByUsername(account.getUsername());
        if (employee != null) {
            if (employee.getPassword().equals(account.getPassword()) && employee.getRole() == 2) {
                token = IdUtil.generateId();
            }
        }
        LOG.info("[login] End");
        return token;
    }
}
