package com.ymca.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Account;
import com.ymca.model.User;

public interface UserDao extends GenericDao<User, Long>  {
	User getByUsername(String username);
	List<User> getByFirstNameAndLastName(String firstName, String lastName);
	
	@Query("select u.password from User u where u.id = ?1")
	String getUserPassword(Long userId);
	
	@Query("select u from User u where u.contactId IN (?1) ")
	List<User> getUsersForContactIds(Long[] contactIds);
	
	User getByEmailAddress(String primaryEmailAddress);
	
	User getByContactId(Long contactId);
	
	@Query("select u from User u where u.emailAddress = ?1")
	List<User> getUserLstByPrimaryEmailAddress(String primaryEmailAddress);
	
	//List<User> getByCustomer_Email(String email);
	
	List<User> getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(String firstName, String lastName, String emailAddress, Date dateOfBirth);
	
	List<User> getByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, Date dateOfBirth);
	
	List<User> getByFirstNameAndLastNameAndDateOfBirthAndFormattedPhoneNumber(String firstName, String lastName, Date dateOfBirth, String formattedPhoneNumber);
	
	//List<User> findByPersonFirstNameOrPersonLastNameOrPrimaryEmailAddress(String fname,String lname,String email);
	List<User> findByFirstNameOrLastNameOrEmailAddress(String fname,String lname,String email);
	
	@Query("select u from User u where u.customer =?1 and u.isPrimary =?2")
	User getByPrimaryContact(Account account, boolean isPrimary);

	@Query("select ac.contact from AccountContact ac where (ac.endDate is null Or ac.endDate >  ?1) and ac.customer.email = ?2  and (ac.contact.partyStatus is null Or ac.contact.partyStatus = 'A')")
	List<User> findByEndDateAndCustomerEmail(Date endDate,String email);

	User findFirst1ByPartyId(Long partyId);
	
	@Query(value="SELECT max(TIMESTAMPDIFF(YEAR, c.DateOfBirth, CURDATE())) AS age FROM contact c  in c.contactId in (?1);",nativeQuery=true)
	Integer getMaxAgeForContacts(List<Long> ids);
	
	List<User> getByType(String type);

	User findByPartyId(Long partyId);
}

