package com.fu.bom.service.impl;

import com.fu.bom.service.AdminAccountService;
import com.fu.bom.utils.Constant;
import com.fu.bom.utils.PasswordGenerater;
import com.fu.common.util.MD5Util;
import com.fu.database.dao.EmployeeDao;
import com.fu.database.entity.Employee;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by manlm on 9/10/2016.
 */
@Service
public class AdminAccountServiceImpl implements AdminAccountService {

    private static final Logger LOG = Logger.getLogger(AdminAccountServiceImpl.class);

    private final EmployeeDao employeeDao;

    @Autowired
    public AdminAccountServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAll() {
        LOG.info("[getAll] Start");
        LOG.info("[getAll] End");
        return employeeDao.getAll();
    }

    @Override
    public Employee getAccountByUsername(String username) {
        LOG.info("[getAccountByUsername] Start");
        LOG.info("[getAccountByUsername] End");
        return employeeDao.getByUsername(username);
    }

    @Override
    public int updateAccount(String username, String firstName, String lastName, String phone) {
        LOG.info("[updateAccount] Start");
        LOG.info("[updateAccount] End");
        return employeeDao.updateAccount(username, firstName, lastName, phone);
    }


    @Override
    public boolean addAccount(String username, String firstName, String lastName, String phone, String srole) {
        LOG.info("[addAccount] Start: username = " + username);
        String email = generateEmail(firstName, lastName);
        String password = PasswordGenerater.random();
        String pass = MD5Util.stringToMD5(password);
        int role;
        if ("admin".equals(srole)) {
            role = Constant.ACCOUNT_ROLE_ID.ADMIN.getValue();
        } else {
            role = Constant.ACCOUNT_ROLE_ID.STAFF.getValue();
        }
        if (employeeDao.getByUsername(username) != null) {
            LOG.info("[addAccount] End");
            return false;
        }
        Employee acc = new Employee(username, pass, firstName, lastName, email, phone, role, Constant.ACCOUNT_STATUS_ID.ACTIVE.getValue());
        employeeDao.insert(acc);
        LOG.info("[addAccount] End");
        return true;
    }

    @Override
    public boolean changeStatus(String username) {
        LOG.info("[changeStatus] Start: username = " + username);
        Employee employee = employeeDao.getByUsername(username);
        if (employee.getStatus() == Constant.ACCOUNT_STATUS_ID.ACTIVE.getValue()) {
            employee.setStatus(Constant.ACCOUNT_STATUS_ID.DEACTIVATE.getValue());
            employeeDao.update(employee);
        } else {
            employee.setStatus(Constant.ACCOUNT_STATUS_ID.ACTIVE.getValue());
            employeeDao.update(employee);
        }
        LOG.info("[changeStatus] End");
        return true;
    }

    private String generateEmail(String firstName, String lastName) {
        LOG.info("[generateEmail] Start");
        int flag = 0;
        String email;
        while (true) {
            if (flag == 0) {
                email = firstName + "." + lastName + "@ismb.com";
            } else {
                email = firstName + "." + lastName + flag + "@ismb.com";
            }
            if (employeeDao.checkEmailExist(email)) {
                flag++;
            } else {
                break;
            }
        }
        LOG.info("[generateEmail] End");
        return email;
    }
}
