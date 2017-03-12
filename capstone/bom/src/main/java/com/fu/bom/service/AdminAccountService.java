package com.fu.bom.service;

import com.fu.database.entity.Employee;

import java.util.List;

/**
 * Created by manlm on 9/10/2016.
 */
public interface AdminAccountService {

    List<Employee> getAll();

    Employee getAccountByUsername(String username);

    int updateAccount(String username, String firstName, String lastName, String phone);

    boolean addAccount(String username, String firstName, String lastName,String phone, String role);

    boolean changeStatus(String username);
}
