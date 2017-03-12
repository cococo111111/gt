package com.fu.database.dao;

import com.fu.database.entity.Customer;

import java.util.List;

/**
 * Created by manlm on 9/21/2016.
 */
public interface CustomerDao extends GenericDao<Customer, Long> {

    List<Customer> getAll();

    boolean isExist(String fbId);

    int insertPhoneByBotFbId(String botFbId, String phone);

    Customer getByPhone(String phone);

    Customer getByBotFBId(String botFbId);
}
