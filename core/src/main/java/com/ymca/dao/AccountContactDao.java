package com.ymca.dao;

import java.util.List;

import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.User;

public interface AccountContactDao  extends GenericDao<AccountContact, Long>  {
	List<AccountContact> getByCustomer(Account customer);
	List<AccountContact> getByContact(User contact);	
	List<AccountContact> getByCustomerAndContact(Account customer, User contact);	
	AccountContact getByAccountContactId(Long accountContactId);
	AccountContact getByStatus(String Status);
	
}