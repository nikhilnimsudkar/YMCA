package com.ymca.dao;



//import antlr.collections.List;

//import java.util.List;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Account;
import com.ymca.model.Activity;
import com.ymca.model.Signup;
import com.ymca.model.User;

public interface ActivityDao extends GenericDao<Activity, Long>{
	
	
	//Activity findByTypeAndCustomer(String type,Account customer);
	List<Activity> findBySignupAndType(Signup signup, String type);
	
	@Query("SELECT a  from Activity a inner join a.signup s inner join a.customer c  where c.accountId=?1 and s.type = 'Donations' and a.description = 'CANCEL DONATION'")
	List<Activity> getByCustomerIdAndTypeDonation(Long accountId);
	
	List<Activity> findByTypeAndCustomerAndContact(String type,Account customer,User user);
	
	List<Activity> findByCustomerAndContactAndCreatedDateBetween(Account customer,User user,Date startDate,Date endDate);
	
	Long countByTypeAndCustomerAndContact(String type,Account customer,User user);
	
	
	List<Activity> findByTypeAndCustomer(String type,Account customer);
	
	
	@Query("SELECT a  from Activity a  where a.customer=?1 and a.type = 'KIDS CLUB' and a.checkinDatetime is NULL and a.checkoutDatetime is NULL")
	List<Activity> findBydCustomer(Account customer);
	
	@Query("SELECT a  from Activity a  where a.type = 'KIDS CLUB' and a.checkinDatetime is NULL and a.checkoutDatetime is NULL")
	List<Activity> findByType();
	
	@Query("SELECT a  from Activity a  where a.type = 'KIDS CLUB' and a.facilityLoationCheckedInTo=?1 and a.checkinDatetime is NULL and a.checkoutDatetime is NULL")
	List<Activity> findByTypeAndFacilityLoationCheckedInTo(long locationId);
}
