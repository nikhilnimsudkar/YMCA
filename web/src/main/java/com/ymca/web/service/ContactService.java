package com.ymca.web.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Activity;
import com.ymca.model.Address;
import com.ymca.model.User;

@Service 
public class ContactService {
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private AccountContactService accountContactService;
		
	@PersistenceContext
	public EntityManager em;
	
	public List<Object[]> getAccountDetails(String firstName, String lastName, String emailAddress ,String PhoneNum, String cname) {
		
		List<Object[]> customerList = null;		
		customerList = this.createQuery(firstName, lastName, emailAddress,PhoneNum,cname);
		return customerList;
	}
	
	public List<Object[]> createQuery(String fName, String lName, String email,String phno, String cname) {
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);		
		Root<AccountContact> i = query.from(AccountContact.class);
		
		Join<AccountContact,User> p = i.join("contact",JoinType.INNER);
		Join<AccountContact,Account> ac = i.join("customer",JoinType.INNER);
		
		ParameterExpression<String> firstName = builder.parameter(String.class);
		ParameterExpression<String> lastName = builder.parameter(String.class);
		ParameterExpression<String> emailAddress = builder.parameter(String.class);
		ParameterExpression<String> phone = builder.parameter(String.class);
		ParameterExpression<String> gender = builder.parameter(String.class);
		ParameterExpression<String> membershipId = builder.parameter(String.class);
		ParameterExpression<Date> dateOfBirth = builder.parameter(Date.class);
		ParameterExpression<String> customerName = builder.parameter(String.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		
		if(!"".equals(fName)){
			predicates.add(builder.and(builder.like(p.<String>get("firstName"), firstName)));
		}
		if(!"".equals(lName)){
			predicates.add(builder.and(builder.like(p.<String>get("lastName"), lastName)));
		}
		if(!"".equals(email)){
			predicates.add(builder.and(builder.like(p.<String>get("emailAddress"), emailAddress)));
		}
		if(!"".equals(phno)){
			predicates.add(builder.and(builder.like(p.<String>get("formattedPhoneNumber"), phone)));
		}
		if(cname!=null && !"".equals(cname)){
			predicates.add(builder.and(builder.like(ac.<String>get("name"), customerName)));
		}		
		/*if(!"".equals(gen)){
			predicates.add(builder.and(builder.like(p.<String>get("gender"), gender)));
		}
		if(!"".equals(mid)){
			predicates.add(builder.and(builder.like(p.<String>get("facilityAccessNumber"), membershipId)));
		}
		if(dob != null){
			predicates.add(builder.and(builder.equal(p.get("dateOfBirth"), dateOfBirth)));
		}*/
		
		query.multiselect(i);		
		query.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<Object[]> typequery = em.createQuery(query);
		if(!"".equals(fName.trim())){
			typequery.setParameter(firstName,"%"+ fName + "%");
		}
		if(!"".equals(lName.trim())){
			typequery.setParameter(lastName, "%"+lName+"%");
		}
		if(!"".equals(email.trim())){
			typequery.setParameter(emailAddress, "%"+email+"%");
		}
		if(!"".equals(phno.trim())){
			typequery.setParameter(phone, phno);
		}
		if(cname!=null && !"".equals(cname.trim())){
			typequery.setParameter(customerName, cname);
		}
		/*if(!"".equals(gen.trim())){
			typequery.setParameter(gender, gen);
		}
		if(!"".equals(mid.trim())){
			typequery.setParameter(membershipId, mid);
		}
		if(dob != null){
			typequery.setParameter(dateOfBirth, dob);
		}*/
		
		// join account and contact

		//a.fetch("name");
		List<Object[]> lstObj = typequery.getResultList();
		return lstObj;
	}
	
	public User getPrimaryUserByCustomerId(Long customerId) {		
		User user = null;
		Account account = accountDao.findByAccountId(customerId);
		if (account != null) {
			user = userDao.getByPrimaryContact(account, true);		
		}		
		return user;		
	}
	
	public Activity saveActivity(Account customer, User contact, String description, String type){
		Activity activity = new Activity();			
		activity.setDescription(description);
		activity.setType(type);
		activity.setCustomer(customer);
		activity.setContact(contact);
		Activity activitySave = activityDao.save(activity);
		return activitySave;
	}
	
	
	
	public void establishContact (Account account) {
		
		User user = populateUserData(account); 
		account.setUser(new HashSet<User>());
    	account.getUser().add(user);
    	account.setEmail(user.getPrimaryEmailAddress());
    	//account.setCountry(Constants.COUNTRY_USA);
    	//account.setName(user.getFullName() + " " + Constants.ACCOUNT_FAMILY);
    	//account.setCustomerType(Constants.ACCOUNT_FAMILY);
        user.setEnabled(true);
        Calendar cal =  Calendar.getInstance();
        account.setLastUpdated(cal);
        
        try {       	
            Account acc = accountDao.saveAndFlush(account);
            for(User usr : acc.getUser()){
            	AccountContact accContact = accountContactService.saveAccountContact(acc, usr);
            	break;
            }
        } catch (Exception ade) {
        	
        }
        String desc = getFacilityDesc(account);
		saveActivity(account, user, desc, "Facility Booking Request");
	}
	
	private User populateUserData(Account account) { 
    	User user = new User();
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();	
    	user = account.getUserLst().get(0);
    	user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	//user.setDateOfBirth(new Date());    	
    	user.setAccountExpired(false);
    	user.setAccountLocked(false);
    	user.setCredentialsExpired(false);
    	user.setEnabled(true);
    	user.setPrimary(true); 
    	user.setActive(true);
    	user.setPrimary(true);
    	user.setfNameAndLName(user.getFullName());
    	Calendar cal = Calendar.getInstance();
		user.setLastUpdated(cal);		
    	//user.setDateOfBirth(new Date());	
		Address address =  new Address();
		address.setPrimaryAddressLine1(account.getAddressLine1());
		address.setPrimaryAddressLine2(account.getAddressLine2());
		address.setPrimaryAddressCity(account.getCity());
		address.setPrimaryAddressProvince(account.getState());
		address.setPrimaryAddressPostalCode(account.getPostalCode());			
		address.setPrimaryAddressCountry("USA");	
		user.setAddress(address);
    	//System.out.println("new user data populated");
		if(user.getGender().equals("Male")){
			//user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
		}else{
			//user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
		}
		User userSave = userDao.save(user);	

    	return userSave;
	}
	
	public String getFacilityDesc (Account account) {
		StringBuffer desc = new StringBuffer();
		if (account.getPurpose() != null) {
			desc.append("Purpose:");
			desc.append(account.getPurpose());
			desc.append(";");
		}
		if (account.getSize() != null) {
			desc.append("Size:");
			desc.append(account.getSize());
			desc.append(";");
		}
		if (account.getOther() != null) {
			desc.append("Other:");
			desc.append(account.getOther());
			desc.append(";");
		}
		if (account.getDateTime() != null) {
			desc.append("DateTime:");
			desc.append(account.getDateTime());
			desc.append(";");
		}
		return desc.toString();
		
	}
	
}
