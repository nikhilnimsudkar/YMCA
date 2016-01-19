package com.ymca.test.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.CoreConfig;
import com.ymca.dao.AccountDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.Address;
import com.ymca.model.ContactWaiversAndTC;
import com.ymca.model.Location;
import com.ymca.model.Membership;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.util.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreConfig.class)
public class AccountControllerTest {

	@Resource
	AccountDao accountDao;
	
	@Resource
	UserDao userDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	LocationDao locationDao;
	
	@Resource
	ItemDetailDao itemDetailDao;

	@Test
	public void getAll() {
		Account userLst = accountDao.getByName("Test");
		////System.out.println("User List Size " + userLst.getAddressLine1() + "  " + userLst.getCity());
	}
	
	@Test
	public void getAccountByEmail() {
		/*Account account = accountDao.getByEmail("nnimsudkar@serenecorp.co");
		//System.out.println(account.getAddressLine1());
		User user =  null;
		for (Iterator<User> it = account.getUser().iterator(); it.hasNext(); ) {
    		user = it.next();
				        
	    }	
		//System.out.println("User List Size " + account.getAddressLine1() + "  " + account.getEmail());*/
	}
	
	@Test
	public void save() {
		try{
		Membership membership = new Membership();
    	membership.setContactName("Joseph");
    	membership.setContactPartyId("123");
    	membership.setCustomerPartyId("123");
    	//membership.setMembershipBeginDate("Date");
    	//membership.setMembershipEndDate("Date");
    	membership.setMembershipNumber("123");
    	membership.setPricingRuleId(Long.parseLong("123445522222"));
    	membership.setProductId(Long.parseLong("12213123222222"));
    	//membership.setMembershipPrice(new Double(30));
    	
        Account account = new Account();
    	//account.setAccountId(1L);
    	account.setName("jalexander");
    	account.setAddressLine1("887 bldv");
    	account.setAddressLine2("23 Apartment");
    	account.setCity("San Jose");
    	account.setState("CA");
    	account.setCountry("77833");
    	
    	account.setUser(new HashSet<User>());    	
    	//account.setMembership(membership);
    	account.setEmail("jalexander@gmail.com");
    	
    	Address address = new Address();
        address.setPrimaryAddressLine1("887 bldv");
        address.setPrimaryAddressCity("San Jose");
        address.setPrimaryAddressProvince("CA");
        address.setPrimaryAddressCountry("77833");
        address.setPrimaryAddressPostalCode("77833");
        
        

		Set<User> userLst =  new HashSet<User>();
        User user = new User();
        user.setAddress(address);
        user.setContactWaiversAndTC(new HashSet<ContactWaiversAndTC>());         	

        user.setUsername("jalexander");
        user.setPassword("jalexander");
        user.setConfirmPassword("jalexander");
        user.setPersonFirstName("Joseph");
        user.setPersonLastName("Alexander");
        user.setPrimaryEmailAddress("jalexander@gmail.com");
        user.setPrimaryURL("http://jalexander.com");
        user.setPasswordHint("jalexander");
        
        user.setDateOfBirth(new Date());
        user.setRelationships("Account - User [OneToMany]");
        user.setAreaOfInterest("AreaOfInterest");
        user.setProfileImage("resources/img/portal_Images/joseph.jpg");
        userLst.add(user);
    	
    	
    	ContactWaiversAndTC contactWaiversAndTC = new ContactWaiversAndTC();
        contactWaiversAndTC.setContact(user);
        contactWaiversAndTC.setCustomer(account);
		//contactWaiversAndTC.setContactPartyId(3L);
		//contactWaiversAndTC.setCustomerPartyId(3L);
		contactWaiversAndTC.setRecordName("Joseph Alexander");
		contactWaiversAndTC.setTcDescription("Terms Desc");
		
		ContactWaiversAndTC contactWaiversAndTC1 = new ContactWaiversAndTC();
		contactWaiversAndTC1.setContact(user);
		contactWaiversAndTC1.setCustomer(account);
		//contactWaiversAndTC1.setContactPartyId(4L);
		//contactWaiversAndTC1.setCustomerPartyId(4L);
		contactWaiversAndTC1.setRecordName("Joseph Alexander 1");
		contactWaiversAndTC1.setTcDescription("Terms Desc");
    	
		user.getContactWaiversAndTC().add(contactWaiversAndTC);
	    user.getContactWaiversAndTC().add(contactWaiversAndTC1);  
	    
	    User refContact =  userDao.getByEmailAddress("asdfdasf@dsafdaf");
	    //user.setReferrerContactId(refContact.getContactId());
	    
		account.getUser().add(user);
		Account acc = accountDao.save(account);	
		refContact.setReferrerContactId(user.getContactId());
		userDao.save(refContact);
		
		//saveUsrObj = refContact;
		
    	System.out.println(acc.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
    	
	}
	
	
	@Test
	public void matchPassword() {
		String rawPassword = "asdfg";
		String encryptedPassword = "$2a$10$woM9oUm/zwYFrtRDw1u6MexChNrtwxZs6xishyGKMQPExSd1hWaNy";
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		//System.out.println(bCryptPasswordEncoder.matches(rawPassword, encryptedPassword));
	}
	
	@Test
	public void allAreaBranchName() {
		//List<SystemProperty> allAreaBranchName = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH);
		List<Object[]> abc = itemDetailDao.getMembershipProgramByLocation(1L);
		System.out.println(abc);
	}
	
	
}
