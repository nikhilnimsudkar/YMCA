package com.ymca.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Account;
import com.ymca.model.ItemDetail;
import com.ymca.model.Signup;
import com.ymca.model.User;

public interface SignupDao extends GenericDao<Signup, Long>  {
	
	List<Signup> getByCustomer(Account customer);
	
	List<Signup> getByCustomerAndType(Account customer, String type);
	
	List<Signup> findByContactNameAndTypeNotLike(String name,String type );
	
	Signup findByContactNameAndStatusAndTypeLike(String name,String status,String type);
	
	@Query("select s from Signup s where s.customer = ?1 and s.signupId=?2")
	Signup findByCustomerAndSignupId(Account customer, Long signupid);
	
	@Query("select s from Signup s inner join s.contact c inner join s.itemDetail i where c.partyId =?1 and i.id= ?2")
	List<Signup> getByContactAndItemDetails(Long contactId,Long itemDetailsId);
	
	List<Signup> getByCustomerAndContactAndStatusAndType(Account customer, User contact, String status,String type);
	List<Signup> getByCustomerAndStatusAndType(Account account, String status, String type);
	
	//Signup getByCustomerAndContactAndStatusAndType(long accId,long contactId,String status,String type);
	Signup findByCustomerAndContactAndStatusAndType(Account customer, User contact,String status,String type);
	@Query("select s, i from Signup s inner join s.itemDetail i where s.signupId=?1")
	Object getSignupProgramById(Long signupId);

	@Query("SELECT s FROM Signup s INNER JOIN s.paymentMethod pm WHERE s.customer = ?1 AND s.status = 'Active' AND pm.id = ?2")
	List<Signup> getByCustomerAndPaymentMethod(Account account, Long paymentId);
	
	List<Signup> findByContactAndTypeAndStatus(User contact, String type, String status);
	
	@Query("select MAX(waitlist) from Signup s where s.itemDetail=?1")
	Object getMaxWaitlistPriorityByItemDetail(ItemDetail itemDetail);
	
	List<Signup> getByItemDetailAndStatus(ItemDetail itemDetail, String status);
	List<Signup> getByItemDetailAndStatusOrderByWaitlistAsc(ItemDetail itemDetail, String status);
	
	//@Query("select s from Signup s where s.scId=?1")
	Signup getByScId(String scId);
	
	List<Signup> getByCustomerAndTypeAndStatusNot(Account customer, String type, String status);	
	List<Signup> getByCustomerAndTypeAndStatusAndPaymentMethodNotNull(Account customer, String type, String status); 
	
	List<Signup> findByContactNameAndCustomerAndContactAndTypeAndStatus(String name, Account customer, User contact, String type, String status);
	
	List<Signup> findByContactNameAndCustomerAndContactAndStatus(String name, Account customer, User contact, String status);	
	
	@Query("select s.status from Signup s where s.status in ('Active', 'Waitlisted') and s.type in ?1 and s.contact.contactId = ?2 and s.itemDetail.id = ?3 ")
	String checkActiveSignUp(List<String> type,Long contactId,Long itemDetailId);
	
	@Query("select s.status from Signup s where s.status in ('Active', 'Waitlisted') and s.contact.contactId = ?1 and s.itemDetail.id = ?2 ")
	String checkActiveSignUp(Long contactId,Long itemDetailId);
	
	Signup findFirst1ByOpptyId(String opptyId);
	
	Signup findFirst1ByContactIdAndItemDetailIdAndStatusOrderBySignupIdDesc(Long contactId,Long itemDetailId,String status);
	
	List<Signup> findByParentSignUpIdAndStatus(Long id, String status);
	
	@Query("SELECT s  from Signup s inner join s.customer c where c.accountId=?1 and s.type = 'DONATION'")
	List<Signup> getByCustomerIdAndTypeDonation(Long accountId);
	
	//@Query("SELECT s  from Signup s where s.type IN ('MEMBERSHIP','Guest Pass','Trial Pass') AND s.status = 'ACTIVE' and s.accountId = ?1 and s.contactId = ?2 limit 1")
	Signup getByCustomerAndContact(Long customer,Long contact);

	@Query("SELECT s  from Signup s where s.type IN ('MEMBERSHIP','Guest Pass','Trial Pass') AND s.status = 'Active'  and s.contactId = ?1")
	List<Signup> findByContact(Long contact,Pageable pageable);
	
	@Query("SELECT s  from Signup s where s.type IN ('MEMBERSHIP','Guest Pass','Trial Pass') AND s.status = 'Inactive'  and s.contactId = ?1")
	List<Signup> findByContactAndStatus(Long contact,Pageable pageable);
	
	@Query("SELECT s  from Signup s where s.type IN ('MEMBERSHIP','Guest Pass','Trial Pass')  and s.contactId = ?1")
	List<Signup> findByContactAndType(Long contact,Pageable pageable);

	List<Signup> findByTypeInAndContactId(List<String> types, Long contactId);
	
	@Query("SELECT s  from Signup s INNER JOIN s.contact c where s.type = 'Donations' and s.status = 'Active' and (s.employer = ?1 or s.companyName like ?2 or s.employer is null or s.companyName = '')")
	List<Signup> findByEmployerSignupData(Long employer,String companyName);
	
}
 