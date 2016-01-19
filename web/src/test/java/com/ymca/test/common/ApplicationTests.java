//package com.ymca.test.common;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.SpringApplicationConfiguration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import com.ymca.CoreConfig;
//import com.ymca.dao.AccountDao;
//import com.ymca.dao.ItemDetailsDao;
//import com.ymca.dao.LocationDao;
//import com.ymca.dao.PricingRuleDao;
//import com.ymca.dao.SystemPropertyDao;
//import com.ymca.dao.UserDao;
//import com.ymca.model.Account;
//import com.ymca.model.Address;
//import com.ymca.model.Locations;
//import com.ymca.model.PricingRule;
//import com.ymca.model.SystemProperty;
//import com.ymca.model.User;
//import com.ymca.web.util.Constants;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = CoreConfig.class)
//public class ApplicationTests {
//
//
//	@Resource
//	UserDao userDao ;
//
//	@Resource
//	LocationDao locationDao; 
//	
//	@Resource
//	ItemDetailsDao itemDetailsDao; 
//	
//	@Resource
//	PricingRuleDao pricingRuleDao; 
//	
//	@Resource
//	AccountDao accountDao; 
//	
//	@Resource
//	private SystemPropertyDao  systemPropertyDao;
//	
//	@Test
//	public void getAll() {
//		//List<User> userLst = userDao.findAll();
//		
//		//System.out.println("User List Size " + userLst.size() + "  " + userLst.get(0).getFirstName());
//	} 
//	
//	@Test
//	public void getLocationByLocationName() {
//		List<Locations> allBranchLocationIdLst = locationDao.getLocationsByBranchName(Constants.LOCATION_ALL_BRANCH);
//		List<Locations> bayAreaBranchIdLst = locationDao.getLocationsByBranchName(Constants.LOCATION_BAYAREA);
//		
//		HashMap<String, Integer> allBranchMap =  new HashMap<String, Integer>();
//		HashMap<String, Integer> bayAreaBranchMap =  new HashMap<String, Integer>();
//		
//		if(allBranchLocationIdLst != null && !allBranchLocationIdLst.isEmpty()){
//			List<Object[]> allBranchItemDetails =  itemDetailsDao.getMembershipProgramByLocation(allBranchLocationIdLst.get(0).getLocationId());
//			for(Object product : allBranchItemDetails){
//    			Object objArr1[] = (Object[]) product;	
//    			if(objArr1 != null && objArr1.length >0){
//					if(objArr1[6] != null && objArr1[7] != null){    						
//						List<PricingRule> pricingRuleLst =  pricingRuleDao.findByitemDetailsIdAndTier(Long.parseLong(objArr1[7].toString()), objArr1[6].toString());						
//						if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
//							allBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getTierPrice()));
//						}
//					}
//    			}
//			}
//		}
//		if(bayAreaBranchIdLst != null && !bayAreaBranchIdLst.isEmpty()){
//			List<Object[]> bayAreaBranchItemDetails =  itemDetailsDao.getMembershipProgramByLocation(bayAreaBranchIdLst.get(0).getLocationId());
//			for(Object product : bayAreaBranchItemDetails){
//    			Object objArr1[] = (Object[]) product;	
//    			if(objArr1 != null && objArr1.length >0){
//					if(objArr1[6] != null && objArr1[7] != null){    						
//						List<PricingRule> pricingRuleLst =  pricingRuleDao.findByitemDetailsIdAndTier(Long.parseLong(objArr1[7].toString()), objArr1[6].toString());						
//						if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
//							bayAreaBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getTierPrice()));
//						}
//					}
//    			}
//			}
//		}
//		
//		System.out.println(allBranchMap.get("safdTwo Adults w/ Kids"));
//		System.out.println(bayAreaBranchMap.get("Adult"));
//	}
//	
//	@Test
//	public void inserAddressData() {	
//		try{
//			List<User> usr = userDao.getByPersonFirstNameAndPersonLastName("kid2", "kid2 loo");
//			//User userExist = userDao.getByPrimaryEmailAddress("sbaghchi@gmail.com");
//	    	
//			System.out.println(usr);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//	@Test
//	public void getAdultsChildsYouthAge() {	
//		try{
//			List<SystemProperty> systemPropertyLst = new ArrayList<SystemProperty>();			
//			systemPropertyLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_UPPER_LIMIT);
//			if(systemPropertyLst != null && !systemPropertyLst.isEmpty()){
//				System.out.println(systemPropertyLst.get(0).getKeyValue());
//			}				
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//}
