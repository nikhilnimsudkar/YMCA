package com.ymca.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.CoreConfig;
import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.PricingRuleDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.ItemDetail;
import com.ymca.model.Location;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CoreConfig.class)
public class ApplicationTests {

	private Logger log = LoggerFactory.getLogger(ApplicationTests.class);

	@Resource
	UserDao userDao ;

	@Resource
	AccountDao accountDao;
	
	@Resource
	PaymentDao paymentDao;
	
/*	@Resource
	ProductDao productDao;
*/
	@Resource
	WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	SystemPropertyDao systemPropertyDao;
	
	@Resource
	PaymentMethodDao paymentMethodDao;
	
	@Resource
	LocationDao locationDao;
	
	@Resource
	PricingRuleDao pricingRuleDao;
	

	@Resource
	SignupDao signupDao;
	
	@Resource
	private AccountContactDao accountContactDao ; 
	
	@Test
	public void getAll() {
		//userDao.findAll();
		//List<Payment> paymentLst = paymentDao.findAll();
		//System.out.println(paymentLst);
		//2014-08-18 00:00:00
		Date startDate = new Date();		
		Calendar cal =  Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_YEAR, -3);
		Date formattedStartDate = cal.getTime();
		System.out.println(formattedStartDate);
		
		Date endDate = new Date();
		Calendar cal1 =  Calendar.getInstance();
		cal1.setTime(endDate);
		cal1.add(Calendar.DAY_OF_YEAR, 3);
		
		Date formattedEndDate = cal1.getTime();
		System.out.println(formattedEndDate);
		//2014-08-18 00:00:00.0
		//customer
		
		/*List<Object> paymentLst = paymentDao.getPaymentHistory("","th", 20L, formattedStartDate,formattedEndDate);
		for(Object payment : paymentLst){
			Object objArr1[] = (Object[]) payment;			
			for(Object obj : objArr1){
				System.out.println(obj);
			}	
		}*/
		
	} 
	
	@Test
	public void getUserByEmail() {
		//Product p =  productDao.getItemDetailsByProductId(1L);
		///System.out.println(p);
		//Locations location = new Locations();
		//location.setLocationId(1L);
		ItemDetail itemDetails = new ItemDetail();
		itemDetails.setId(1L);
		//List<PricingRule> productDetails =  pricingRuleDao.findByitemDetailsIdAndTier(1L, "1");
		//System.out.println(productDetails);

		/*List<ItemDetails> productDetails =  productDao.getMembershipProgramByLocation(1L);
		//JSONArray json = new JSONArray();    		
		for(Object product : productDetails){
			Object objArr1[] = (Object[]) product;	
			if(objArr1 != null && objArr1.length >0){
				//JSONObject obj = new JSONObject();
				if(objArr1[0] != null){
					//obj.put("id", objArr1[0].toString());
				}
				if(objArr1[1] != null){
					//obj.put("productName", objArr1[1].toString());
				}
				if(objArr1[2] != null){
					//obj.put("productDescription", objArr1[2].toString());			
				}
				if(objArr1[3] != null){
					//obj.put("productType", objArr1[3].toString());
				}
				if(objArr1[4] != null){
					//obj.put("productDuration", objArr1[4].toString());
				}		
				double price = 0D;
				if(objArr1[6] != null){
					Locations location = (Locations) objArr1[6];
					List<PricingRule> pricingRuleLst =  location.getPricingRule();
					List<PricingRule> pricingLst = new ArrayList<PricingRule>();
					for(PricingRule pricingRule: pricingRuleLst) {
						if(location.getTier() != null && pricingRule.getTier() != null && location.getTier().equals(pricingRule.getTier())){
							pricingLst.add(pricingRule);
						}
					}
					
					for(PricingRule pricingRule: pricingLst) {
						if(pricingRule.getTierPrice() != null){
							price += Double.parseDouble(pricingRule.getTierPrice());
						}
					}
					System.out.println(price);
					//obj.put("productPrice", price);
				}
				if(objArr1[5] != null){
					//obj.put("tandc", objArr1[5].toString());
				}
				//json.add(obj);
			}  			
		} */  		
			    	  					
	
		//User user =  userDao.getByEmail("nnimsudkar@serenecorp.com1");
		//System.out.println(user);
		
		//List<Account> account = accountDao.getAccountByEmail("nnimsudkar@serenecorp.com");
		//System.out.println(account);
	} 
	
	@Test
	public void getPaymentMethod() {
		
		//List<PaymentMethod> paymentList = paymentMethodDao.getCreditCardInfoByAccountId(20L);
		//System.out.println(paymentList);
		List<Location> bayAreaLocatios = locationDao.getLocationsByArea("Bay Area");
		//System.out.println(bayAreaLocatios);
	}
	
	@Test
	public void getPicklistValuesByPicklistName() {
		//List<SystemProperty> systemPropLst = systemPropertyDao.getByFieldStatus(true);
		List<SystemProperty> systemPropLst = systemPropertyDao.getByPicklistName("PAYMENT_METHOD");
		System.out.println(systemPropLst);
	}
	
	@Test
	public void getAllPicklistValues() {		
		List<SystemProperty> systemPropLst = systemPropertyDao.getAllPicklistValues();
		System.out.println(systemPropLst);
	}

	@Test
	public void getPropertyValueByKeyName() {		
		List<SystemProperty> systemPropLst = systemPropertyDao.getPropertyByKeyName("ADD_BANK_INFO");
		System.out.println(systemPropLst);
	}
	
	@Test
	public void getPropertyValueByKeyName33() {	
		
		Account account = accountDao.getByEmail("nnimsudkar@serenecorp.com");
		//account.setAccountId(20L);
		User user = userDao.getByEmailAddress("nnimsudkar@serenecorp.com");
		//user.setId(14L);
		account.setUser(new HashSet<User>());
    	account.getUser().add(user);
		Signup signup = new Signup();
		
    	signup.setContact(user);
    	signup.setCustomer(account);
    	
    	signup.setContactName("Test");
    	signup.setType("Membership");
    	signup.setStatus("New");
    	signup.setOpptyId("Test opp");
    	signup.setSignupDate(new Date());
    	signup.setProgramStartDate(new Date());
		try{
			//accountDao.save(account);
		//signupDao.save(signup);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

	@Test
	public void getAllLocation() {
		
		List<Location> bayAreaLocatios = locationDao.findAll();
		//System.out.println(bayAreaLocatios);
	}
	
	@Test
	@Transactional
	public void findActiveContact(){
		List<User> users = userDao.findByEndDateAndCustomerEmail(new Date(),"gpatwa@serenecorp.com");
		log.info("Total Users " + users.size());
		for(User u : users) {
			log.info(u.toString());
		}
	}
	
	@Test
	@Transactional
	public void checkActiveSignUp(){
		//String status= signupDao.checkActiveSignUp("PROGRAM",11L,23L);
		//log.info(" sign up " + status);
	}
}
