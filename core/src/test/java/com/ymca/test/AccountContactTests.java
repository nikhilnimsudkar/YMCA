package com.ymca.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.CoreConfig;
import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.OpportunityDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.UserDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Activity;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.Location;
import com.ymca.model.Opportunity;
import com.ymca.model.Payment;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.model.WaiversAndTC;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreConfig.class)
public class AccountContactTests {


	@Resource
	UserDao userDao ;

	@Resource
	AccountDao accountDao;
	
	@Resource
	AccountContactDao accountContactDao;

	@Resource
	WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	SignupDao signupDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private OpportunityDao opportunityDao;
	
	@Resource
	private ItemDetailDao itemDetailDao;
	
	@Resource
	private LocationDao locationDao;	
	
	@Test
	public void saveAccountContactObj() {	
		try{
			
			Account account = accountDao.findByAccountId(239L);
			User contact =  userDao.getByContactId(64L);
			AccountContact accountContact = new AccountContact();			
			accountContact.setCustomer(account);
			accountContact.setContact(contact);	
			//accountContact.setCreationDate(new Date());
			accountContact.setLastUpdated(Calendar.getInstance());
			accountContact.setStartDate(new Date());			
			//AccountContact accContactSave = accountContactDao.save(accountContact);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getContactByFNameLname() {	
		try{		
			Date dob =  new Date(80, 00, 01);		
			System.out.println(dob);
			
			List<Object[]> accLst = accountDao.getByFirstNameAndLastNameAndDateOfBirth("Govind", "Patwa",  dob);
			//List<Account> contactLst =  accountDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth("Govind", "Patwa", "1gpatwa@serenecorp.com", dob);
			//List<User> contactLst =  userDao.getByFirstNameAndLastNameAndDateOfBirth("Govind", "Patwa", dob);
			
			System.out.println(accLst);
			//accountContactDao.save(null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getAccountContactByContactId() {	
		try{		
			User contact =  userDao.getByContactId(64L);
			Account account = accountDao.findByAccountId(238L);
			//List<AccountContact> accountContact =  accountContactDao.getByContact(contact);
			
			List<User> contactLst =  new ArrayList<User>();
			List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
			for(AccountContact ac : accountContactLst){
				contactLst.add(ac.getContact());
			}
			//contactLst.get(0).getContactId();
			//System.out.println(accountContactLst);
			//accountContactDao.save(null);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getTAndCbyType() {	
		try{		
			List<WaiversAndTC> w = waiversAndTCDao.getTcByTypeAndDate("Common");
			System.out.println("Test");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByFirstNameAndLastNameAndAddressLine1AndPostalCodeAndDOB() {	
		try{	
			Date dateOfBirth  = new Date(70, 02, 02);
			List<Account> w = accountDao.getByFirstNameAndLastNameAndAddressLine1AndPostalCodeAndDOB("User", "Testing1", dateOfBirth, "324 bldv", "3243424");
			System.out.println("Test");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByPricingRuleByItemDetail() {	
		try{	
			Date dateOfBirth  = new Date(70, 02, 02);
			List<Account> w = accountDao.getByFirstNameAndLastNameAndAddressLine1AndPostalCodeAndDOB("User", "Testing1", dateOfBirth, "324 bldv", "3243424");
			System.out.println("Test");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByUserByAccountAndFName() {	
		try{	
			Date dateOfBirth  = new Date(90, 10, 11);
			System.out.println("Test");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth() {
		//11/11/1977
		Date dateOfBirth  = new Date(77, 10, 11);
		List<Object[]> accLst =  accountDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth("TwoAdult111", "TwoAdult111l", "TwoAdult111@gmail.com",  dateOfBirth);
		if(accLst != null && !accLst.isEmpty()){
			for(Object accountObj : accLst){
    			Object accountArr[] = (Object[]) accountObj;	
    			if(accountArr != null && accountArr.length >0){
					if(accountArr[0] != null && accountArr[1] != null){    						
						Account account = (Account) accountArr[0];
						System.out.println(account.getAccountId());
						User user = (User) accountArr[1];
						System.out.println(user.getContactId());
					}
    			}
			}
		}
		
		System.out.println("Test");
	}
	
	@Test
	public void getSignupByPaymentMethod(){
		//Account account = accountDao.getOne(238L);
		List<Account> accList = accountDao.getByCustomerType("Organization");
		//List<Signup> signupLst = signupDao.getByCustomerAndPaymentMethodNotNull(account);
		System.out.println(accList);
	}
	
	@Test
	public void getByCustomerIdAndTypeDonation(){
		List<Payment> paymentLst = paymentDao.getByCustomerIdAndTypeDonation(1L);		
		List<Activity> activityLst =  activityDao.getByCustomerIdAndTypeDonation(1L);
		List<Payment> donationLst =  new ArrayList<Payment>();
		for(Payment payment : paymentLst){			for(Activity activity : activityLst){
				if(payment !=  null && activity != null && payment.getSignup() != null && activity.getSignup() != null && payment.getSignup().getSignupId() != null && activity.getSignup().getSignupId() != null){
					if(!payment.getSignup().getSignupId().equals(activity.getSignup().getSignupId())){
						donationLst.add(payment);
					}
				}
			}
		}
		
		//List<Signup> signupLst = signupDao.getByCustomerAndPaymentMethodNotNull(account);
		//System.out.println(signupLst);
	}
	
	@Test
	public void getOppDonation(){
		//Account account = accountDao.getOne(238L);
		//List<Opportunity> oppList = opportunityDao.getByTypeAndStage("DONATION");
		//List<Signup> signupLst = signupDao.getByCustomerAndPaymentMethodNotNull(account);
		//System.out.println(oppList);
		String opptyId = "23";
		try{
			List<ItemDetail> opp = itemDetailDao.findBySubCategory("Annual Campaign");
			System.out.println(opp);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	@Test
	public void getItemDetail(){		
		
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.HOUR, 0);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.HOUR_OF_DAY, 0);	
		startDateCal.set(Calendar.MONTH,Calendar.JANUARY);		
		startDateCal.set(Calendar.DATE, 1);		
		Date startDate = startDateCal.getTime();
		
		Calendar endDateCal = Calendar.getInstance();
		endDateCal.set(Calendar.HOUR, 0);
		endDateCal.set(Calendar.MINUTE, 0);
		endDateCal.set(Calendar.SECOND, 0);
		endDateCal.set(Calendar.HOUR_OF_DAY, 0);	
		endDateCal.set(Calendar.MONTH,Calendar.DECEMBER);		
		endDateCal.set(Calendar.DATE, 31);
		Date endDate = endDateCal.getTime();
		
		try{			
			List<Object> locations =  locationDao.findByItemDetailAndStatusOrderByRecordNameAsc(startDate, endDate);
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getEmployerSignup(){
		
		
		try{
			List<Signup> opp = signupDao.findByEmployerSignupData(null, "abc");
			for(Signup s : opp){
				System.out.println(s.getSignupId());
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
}
