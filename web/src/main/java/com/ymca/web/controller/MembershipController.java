package com.ymca.web.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.ItemDetailPromotionDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.OpportunityDao;
import com.ymca.dao.OpportunityRevenueDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.PromotionDao;
import com.ymca.dao.RoleDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Activity;
import com.ymca.model.Address;
import com.ymca.model.ContactWaiversAndTC;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.ItemDetailPromotion;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Location;
import com.ymca.model.Opportunity;
import com.ymca.model.OpportunityRevenue;
import com.ymca.model.Payer;
import com.ymca.model.PaymentMethod;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.model.WaiversAndTC;
import com.ymca.web.enums.MembershipAgeCategoryEnum;
import com.ymca.web.enums.PaymentMethodTypeEnum;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.PortalStatusEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.AccountContactService;
import com.ymca.web.service.PaymentService;
import com.ymca.web.service.SignUpService;
import com.ymca.web.util.Constants;
import com.ymca.web.util.MemberAge;


@Controller
public class MembershipController extends BaseController {

	private SecureRandom random = new SecureRandom();
	/*
	@Resource
	private ProductDao productDao;
	*/
	@Resource
	private RoleDao roleDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ActivityDao interactionDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private WaiversAndTCDao  waiversAndTCDao;
	
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	@Resource
	private PromotionDao promocodeDao;
	
	@Resource
	private ItemDetailPromotionDao itemPromoDetailsDao;
	
	@Resource
	private ItemDetailDao itemDetailDao;
	
	@Resource
	private PaymentService paymentService;	
	
	@Resource
	private JetPayPaymentDao jetPayPaymentDao;	
	
	@Resource
	private SystemPropertyDao  systemPropertyDao;
	
	@Resource
	private AccountContactService accountContactService;
	
	@Resource
	private AccountContactDao accountContactDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	@Resource
	private OpportunityDao opportunityDao; 
	
	@Resource
	private OpportunityRevenueDao opportunityRevenueDao;
	
	@Resource
	private AccountContactService accContactService;
	
	@Resource
	private SignUpService signUpService;
	
	public MembershipController(){
		
	}
	
	@ModelAttribute
    @RequestMapping(value= "ymca/memberships/view_membership", method = RequestMethod.GET)
    public String showMembershipInfo() {
			
		
		return "home";
		
	}
	
	//ymca/memberships/signup
	@ModelAttribute
    @RequestMapping(value= "signup", method = RequestMethod.GET)
    public ModelAndView showForm() {
		
		List<ItemDetail> products = itemDetailDao.findAll();
		//List<WaiversAndTC> waiversAndTCLst = waiversAndTCDao.getTcByType(Constants.WAIVERS_TC_TYPE_COMMON);
		List<WaiversAndTC> waiversAndTCLst = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
    	/*ObjectMapper mapper = new ObjectMapper();
        try {        
    		//System.out.println(mapper.writeValueAsString(products));     
    	} catch (Exception e) {     
    	}*/
    	
        Model model = new ExtendedModelMap();
        try {
            model.addAttribute("products", products);
            if(waiversAndTCLst != null && !waiversAndTCLst.isEmpty()){
            	model.addAttribute("waiversAndTC", waiversAndTCLst.get(0));
            }
            model.addAttribute("account", new Account());           
            List<SystemProperty> adultAgeLowerLst = new ArrayList<SystemProperty>();			
			adultAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
			if(adultAgeLowerLst != null && !adultAgeLowerLst.isEmpty()){
				model.addAttribute("adultAgeValidationLowerLimit", adultAgeLowerLst.get(0).getKeyValue());				
			}
			
			List<SystemProperty> adultAgeUpperLst = new ArrayList<SystemProperty>();			
			adultAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_UPPER_LIMIT);
			if(adultAgeUpperLst != null && !adultAgeUpperLst.isEmpty()){
				model.addAttribute("adultAgeValidationUpperLimit", adultAgeUpperLst.get(0).getKeyValue());				
			}
			List<SystemProperty> ethnicityLst = systemPropertyDao.getByPicklistName(Constants.EthnicityPicklistName);
	        if(ethnicityLst != null && !ethnicityLst.isEmpty()){
	        	model.addAttribute("ethnicityLst", ethnicityLst);
	        }
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
        	log.error("Error in show form",se);
        }
        String dispatchTo = request.getParameter(Constants.PARAM_DISPATCH_TO);
        model.addAttribute("dispatchTo", dispatchTo);	        
        return new ModelAndView("membership_signup", model.asMap());
    	/*//products = productDao.getAllProducts();
        ModelAndView model = new ModelAndView( "membership_signup");
		model.addObject("products",products);
		model.addObject("account", new Account());
		return model;*/
    	/*Model model = new ExtendedModelMap();
        try {
            model.addAttribute("products", products);
            model.addAttribute("account", new Account());
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
            log.error("Error",e);
        }*/
        
        //return "membership_signup";
        
        /*ResponseObject responseObj = new ResponseObject();
        responseObj.setStatus(Constants.STATUS_SUCCESS);       
        responseObj.setMessage("Successfully created donation details");
        responseObj.setData(products);
        //responseObj.setData(new Account());
        return responseObj; */
	}
    
    @RequestMapping(value="register", method = RequestMethod.POST)
    public ModelAndView onSubmit(Account account, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {    	
    	User user = new User();    	
    	user = populateUserData(account);   	
    	//populateContactAndCustomerWaiversAndTC(user, account);
    	//populateMembershipData(account, user);
    	
    	account.setUser(new HashSet<User>());
    	account.getUser().add(user);
    	account.setEmail(user.getPrimaryEmailAddress());
    	account.setCountry(Constants.COUNTRY_USA);
    	account.setName(user.getFullName() + " " + Constants.ACCOUNT_FAMILY);
    	account.setCustomerType(Constants.ACCOUNT_FAMILY);
        user.setEnabled(true);
        Calendar cal =  Calendar.getInstance();
        account.setLastUpdated(cal);
        // Set the default user role on this new user
        //user.addRole(roleDao.getRoleByName(Constants.USER_ROLE));
        
        // unencrypted users password to log in user automatically
        final String password = user.getPassword();

        try {
            //this.getUserManager().saveUser(user);        	
            Account acc = accountDao.saveAndFlush(account);
            for(User usr : acc.getUser()){
            	AccountContact accContact = accountContactService.saveAccountContact(acc, usr);
            	break;
            }
            
            
            /*Interaction interaction = getInteractionData(account, user, Constants.NEW_MEMBERSHIP_REQUEST);
            Interaction interactionSave = interactionDao.saveAndFlush(interaction);
            User contact =  null;	
			contact = getUserByAccount(acc, contact);
            try{
    			Long fcrmCustId = 0L;
        		Long fcrmContactId = 0L;
        		if(account != null && account.getFcrmCustId() != null && !"".equals(account.getFcrmCustId())){
        			fcrmCustId = Long.valueOf(account.getFcrmCustId());
        		}
        		if(contact != null && contact.getFcrmContactId() != null && !"".equals(contact.getFcrmContactId())){
        			fcrmContactId = Long.valueOf(contact.getFcrmContactId());
        		}
        		fcrmCustId = 300000002351031L;
        		fcrmContactId = 300000001957013L;        		
    			CreateInteraction.createInteraction(Constants.NEW_MEMBERSHIP_REQUEST, Constants.NEW_MEMBERSHIP_REQUEST_INTERACTION_DESCTIPTION, fcrmCustId, fcrmContactId, new Date());
    		
            }catch(Exception e){
    		}*/
            
            Model model = new ExtendedModelMap();
    		if(acc != null){	    		    	    	
    	        model.addAttribute("account", account);     
    	        int userCount = account.getUser().size();
    	        List<User> userS = new ArrayList<User>();
    	        if(userCount>1){
    		        for(User u: account.getUser()){
    		        	if(!user.getUsername().equalsIgnoreCase(u.getUsername())){
    		        		userS.add(u);
    		        	}
    		        }
    			}    	        
    	        model.addAttribute("userS", acc.getUserLst());
    			
    		}
    		if(account != null && !StringUtils.isBlank(getAgentUidFromSession())){
    			try  
			 	{  					 		
			 		if(account!=null ){		
				 			/*
							 * To Generate the Password automatically - Start
							 */					
				 			// create a random token and save it in database with time stamp
				 			// create secure 16 digit token
				 			String token = new BigInteger(130, random).toString(32);
				 			
				 			// get current time stamp
				 			java.util.Date date= new java.util.Date();
				 			
				 			// save in database
				 			acc.setResetPassword(true);
				 			acc.setToken(token);
				 			acc.setSentdate(new Timestamp(date.getTime()));
				 			Account accDb = accountDao.save(acc);
			 				User contact =  null;	
			 				contact = getUserByAccount(accDb, contact);
			 				
			 				// Interaction Integration block start 
			 				Activity interaction = new Activity();
			 				interaction.setType(Constants.REQUEST_RESET_PASSWORD);
			 				
			 				//String resetUrl = request.getRequestURL().toString().replace("/resetpassword", "/resetpasscode");
			 				String resetUrl = request.getRequestURL().toString().replace("/register", "/recovery/resetpasscode");
			 				String queryString = "?token="+token;
			 				interaction.setDescription(resetUrl+queryString);
			 				
			 				// get current time stamp
			 				interaction.setCreatedDate(new Timestamp(date.getTime()));
			 				interaction.setCustomer(accDb);
			 				interaction.setContact(contact);
			 				interaction.setShowOnPortal(true);
			 				interaction.setLastUpdated(Calendar.getInstance());
			 				activityDao.save(interaction);			
			 				model.addAttribute("EmailSentSuccess", Constants.FORGET_PASSWORD_SUCCESS); 
			 			} else {
			 				model.addAttribute("EmailSentError",Constants.FORGET_PASSWORD_ERROR); 
			 			}
			 	} catch (Exception ex) {  
			 		log.error("Error occured while creating Activity for reset Password Activity ",ex);
			 		model.addAttribute("EmailSentError",Constants.FORGET_PASSWORD_ERROR);  
			 	}
    		}
    		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmailAddress(), user.getPassword());		
    		// generate session if one doesn't exist
            request.getSession();
            token.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(token);
            request.getSession().setAttribute("usInfo", user);	
            request.getSession().setAttribute("accInfo", account);
            
    		String dispatchTo = request.getParameter(Constants.PARAM_DISPATCH_TO);    		        
            if(StringUtils.isNotBlank(dispatchTo) && dispatchTo.equalsIgnoreCase(ProductTypeEnum.DONATION.toString())){            	
            	return new ModelAndView("redirect:/donationHome");		        	
            }else {
            	return new ModelAndView("membershipConfirmation", model.asMap());
            }
    		
            /*List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(1);  	          
 	       // All users are granted with ROLE_USER access
 	 	   // Therefore this user gets a ROLE_USER by default
 	 	   authList.add(new GrantedAuthorityImpl("USER"));            
           UserDetails userDetails = new org.springframework.security.core.userdetails.User(  
            		user.getUsername().trim(), user.getPassword() , true,  
                    true, true, true, authList);
           Authentication token = new UsernamePasswordAuthenticationToken(user.getEmail().trim(), user.getPassword());	        
	       SecurityContextHolder.getContext().setAuthentication(token);          
           //System.out.println(acc.getName());
           return "redirect:view_membership";*/
           
            
        } catch (Exception ade) {
        	Model model = new ExtendedModelMap();
        	model.addAttribute("errMsg", "Error Occured in Registration Process");
			return new ModelAndView("login", model.asMap());
        }
		//return "home";
    }
    
    @ModelAttribute
    @RequestMapping(value= "becomeMember", method = RequestMethod.GET)
    public ModelAndView becomeMemberForm() {	
    	
        Model model = new ExtendedModelMap();
        try {
        	List<SystemProperty> kidsAgeLst = new ArrayList<SystemProperty>();			
        	kidsAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_KIDS_AGE_VALIDATION);
			if(kidsAgeLst != null && !kidsAgeLst.isEmpty()){
				model.addAttribute("kidsAgeValidation", kidsAgeLst.get(0).getKeyValue());				
			}	
			List<SystemProperty> adultAgeLowerLst = new ArrayList<SystemProperty>();			
			adultAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
			if(adultAgeLowerLst != null && !adultAgeLowerLst.isEmpty()){
				model.addAttribute("adultAgeValidationLowerLimit", adultAgeLowerLst.get(0).getKeyValue());				
			}

			List<SystemProperty> adultAgeUpperLst = new ArrayList<SystemProperty>();			
			adultAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_UPPER_LIMIT);
			if(adultAgeUpperLst != null && !adultAgeUpperLst.isEmpty()){
				model.addAttribute("adultAgeValidationUpperLimit", adultAgeUpperLst.get(0).getKeyValue());				
			}	
			List<SystemProperty> youthAgeLowerLst = new ArrayList<SystemProperty>();			
			youthAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_LOWER_LIMIT);
			if(youthAgeLowerLst != null && !youthAgeLowerLst.isEmpty()){
				model.addAttribute("youthAgeValidationLowerLimit", youthAgeLowerLst.get(0).getKeyValue());				
			}	
			List<SystemProperty> youthAgeUpperLst = new ArrayList<SystemProperty>();			
			youthAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_UPPER_LIMIT);
			if(youthAgeUpperLst != null && !youthAgeUpperLst.isEmpty()){
				model.addAttribute("youthAgeValidationUpperLimit", youthAgeUpperLst.get(0).getKeyValue());
			}	
			List<SystemProperty> seniorAgeLst = new ArrayList<SystemProperty>();			
			seniorAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_SENIOR_AGE_VALIDATION);
			if(seniorAgeLst != null && !seniorAgeLst.isEmpty()){
				model.addAttribute("seniorAgeValidationLimit", seniorAgeLst.get(0).getKeyValue());
			}
            model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
            model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea(Constants.LOCATION_BAYAREA));
            model.addAttribute("account", new Account());
            
            String userId = null;                 
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            userId = auth.getName();
    			
        	Account accountVO = null;
        	User user =  null;	
        	List<PaymentMethod> paymentMethodList = null;
        	if(userId != null && !"anonymousUser".equals(userId)){
        		accountVO = accountDao.getByEmail(userId);
        		model.addAttribute("account", accountVO);
        		if(accountVO != null && !accountVO.getSignup().isEmpty()){
        			if(accountVO.getSignup().get(0).getProgramEndDate() != null){
        				Date programEndDate = accountVO.getSignup().get(0).getProgramEndDate();
            		    Date todayDate = new Date();
            		    Calendar with = Calendar.getInstance();
            		    with.setTime(programEndDate);
            		    Calendar to = Calendar.getInstance();
            		    to.setTime(todayDate);
            		    to.set(Calendar.YEAR, with.get(Calendar.YEAR));
            		    int withDAY = with.get(Calendar.DAY_OF_YEAR);
            		    int toDAY = to.get(Calendar.DAY_OF_YEAR);

            		    int diffDay =  toDAY  - withDAY;
            		    if(diffDay < 30){
            		    	List<Signup> activateSignupLst =  signupDao.getByCustomerAndStatusAndType(accountVO, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
                            if(activateSignupLst != null && !activateSignupLst.isEmpty()){
                                if(activateSignupLst != null && !activateSignupLst.isEmpty()){
                                 model.addAttribute("existingMemberJoinFee", activateSignupLst.get(0).getJoinFee());
                                }
                            }
            		    }else{
            		    	model.addAttribute("existingMemberJoinFee", "0");
            		    }
        			}       			
        			
        		}
        		
        		User primaryContact = null;
        		primaryContact = getUserByAccount(accountVO, primaryContact);
        		model.addAttribute("primaryUser", primaryContact);
        		List<User> userLst =  new ArrayList<User>();
    			List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(accountVO);
    			for(AccountContact ac : accountContactLst){
    				if(ac.getEndDate() == null && ac.getContact() != null){
    					userLst.add(ac.getContact());
    				}				
    			}
    			if(userLst != null && !userLst.isEmpty()){
    				List<User> kidsInfo = new ArrayList<User>();
    				List<User> adultUserLstInfo = new ArrayList<User>();
    				List<User> youthUserLstInfo = new ArrayList<User>();
    				boolean isSecUser = false;
    				//boolean isThirdUser = false;
    				for(User usr : userLst){	    
    					if(isAdultByDob(usr.getDateOfBirth())){
    						if(!isSecUser){
    							model.addAttribute("secUser", usr);
    							isSecUser = true;
    						}else{
    							model.addAttribute("thirdUser", usr);
    						}
    						adultUserLstInfo.add(usr);
		        		}
    					if(isYouthByDob(usr.getDateOfBirth())){
		        			youthUserLstInfo.add(usr);
		        		}
    					if(isChildByDob(usr.getDateOfBirth())){
		        			kidsInfo.add(usr);
		        		}   							
    				}
    				model.addAttribute("userLstInfo", adultUserLstInfo);
    				model.addAttribute("kidsInfo", kidsInfo);
    				model.addAttribute("youthUserLstInfo", youthUserLstInfo); 
    			}
    			
    			paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(accountVO.getAccountId());
    			
        	}else{
        		model.addAttribute("account", new Account());
        		model.addAttribute("existingMemberJoinFee", "0");
        	}
        	model.addAttribute("paymentInfoLst" , paymentMethodList);
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
        	log.error("Error while querying product",se);
        }
        return new ModelAndView("becomeMemberForm", model.asMap());    	
	}
    
   
    
  /* // @ModelAttribute
    @RequestMapping(value= "trailPassReg", method = RequestMethod.GET)
    public String trailPassRegShowForm() {	
    	//System.out.println("in trail pass method");
        Model model = new ExtendedModelMap();
        try {            
            model.addAttribute("locations", locationDao.findAll());
            model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea("Bay Area"));
            model.addAttribute("account", new Account());
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
            log.error("Error in show form",se);
        }
        return "trialPassReges";    	
	}*/
    
    /*@RequestMapping(value="getProductDetailsByLocation", method=RequestMethod.GET)
    public @ResponseBody String  getProductDetailsByLocation(@RequestParam(value="locationId") Long locationId, final HttpServletRequest request, final HttpServletResponse response) { 		
    	try{
    		List<Location> allBranchLocationIdLst = locationDao.findByBranch(Constants.LOCATION_ALL_BRANCH);
    		List<Location> bayAreaBranchIdLst = locationDao.findByBranch(Constants.LOCATION_BAYAREA);
    		
    		HashMap<String, Integer> allBranchMap =  new HashMap<String, Integer>();
    		HashMap<String, Integer> bayAreaBranchMap =  new HashMap<String, Integer>();
    		
    		if(allBranchLocationIdLst != null && !allBranchLocationIdLst.isEmpty()){
    			List<Object[]> allBranchItemDetails =  itemDetailDao.getMembershipProgramByLocation(allBranchLocationIdLst.get(0).getId());
    			for(Object product : allBranchItemDetails){
        			Object objArr1[] = (Object[]) product;	
        			if(objArr1 != null && objArr1.length >0){
    					if(objArr1[6] != null && objArr1[7] != null){    						
    						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndPricingRule_Tier(Long.parseLong(objArr1[7].toString()), objArr1[6].toString());						
    						if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
    							allBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getPricingRule().getTierPrice()));
    						}
    					}
        			}
    			}
    		}
    		if(bayAreaBranchIdLst != null && !bayAreaBranchIdLst.isEmpty()){
    			List<Object[]> bayAreaBranchItemDetails =  itemDetailDao.getMembershipProgramByLocation(bayAreaBranchIdLst.get(0).getId());
    			for(Object product : bayAreaBranchItemDetails){
        			Object objArr1[] = (Object[]) product;	
        			if(objArr1 != null && objArr1.length >0){
    					if(objArr1[6] != null && objArr1[7] != null){    						
    						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndPricingRule_Tier(Long.parseLong(objArr1[7].toString()), objArr1[6].toString());						
    						if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
    							bayAreaBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getPricingRule().getTierPrice()));
    						}
    					}
        			}
    			}
    		}   		
    		
    		
    		List<Object[]> itemDetails =  itemDetailDao.getMembershipProgramByLocation(locationId);
    		JSONArray json = new JSONArray();    		
    		for(Object product : itemDetails){
    			Object objArr1[] = (Object[]) product;	
    			if(objArr1 != null && objArr1.length >0){
    				JSONObject obj = new JSONObject();
    				if(objArr1[0] != null){
    					obj.put("id", objArr1[0].toString());
    				}
    				if(objArr1[1] != null){
    					obj.put("productName", objArr1[1].toString());
    				}
					if(objArr1[2] != null){
						obj.put("productDescription", objArr1[2].toString());			
					}
					if(objArr1[3] != null){
						obj.put("productType", objArr1[3].toString());
					}
					if(objArr1[4] != null){
						obj.put("productDuration", objArr1[4].toString());
					}	
					
					if(objArr1[6] != null && objArr1[7] != null){
						//Locations location = (Locations) objArr1[6];
						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndPricingRule_Tier(Long.parseLong(objArr1[7].toString()), objArr1[6].toString());
						
						double price = 0D;			
						double joiningPrice = 0D;
						double tierPrice = 0D;
						double allAreaPrice = 0D;
						double bayPrice = 0D;
						StringBuffer sb = new StringBuffer();
						for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
							tierPrice += Double.parseDouble(pricingRule.getPricingRule().getTierPrice());
							joiningPrice = Double.parseDouble(pricingRule.getPricingRule().getJoiningFee());
							price += tierPrice + joiningPrice;
							allAreaPrice += Double.parseDouble(pricingRule.getPricingRule().getAllBranchPrice());
							bayPrice += Double.parseDouble(pricingRule.getPricingRule().getBayAreaPrice());
							sb.append(pricingRule.getPricingRule().getRuleName()+":"+pricingRule.getPricingRule().getTierPrice()+";;");
						}
						Double discount = new Double(0);
						if(objArr1[7] != null){							
							try { 
								List<ItemDetailPromotion> ipdLst =  itemPromoDetailsDao.getAutoApplyPromoDiscount(Long.parseLong(objArr1[7].toString()));
								for(ItemDetailPromotion ipd : ipdLst){
									Promotion promo = ipd.getPromotion();								
									if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_VALUE)){
										discount = promo.getDiscountvalue();
										  
									}
									else if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_PERCENTAGE)){
										BigDecimal memberdiscountAmount = promo.getDiscountpercentage().multiply(BigDecimal.valueOf(tierPrice));
										memberdiscountAmount = memberdiscountAmount.divide(new BigDecimal(100));
										discount = memberdiscountAmount.doubleValue();									
									}
								}
							}catch (Exception e) {
								// TODO Auto-generated catch block
							}
						}
						obj.put("itemDetailsId", objArr1[7].toString());
						obj.put("productPrice", price);						
						obj.put("pricingJsonArray", sb.toString());	
						obj.put("joiningPrice", joiningPrice);	
						obj.put("tierPrice", tierPrice);	
						obj.put("allAreaPrice", allAreaPrice);
						obj.put("bayAreaPrice", bayPrice);	
						if(objArr1[1] != null && allBranchMap.get(objArr1[1].toString()) != null){
							obj.put("allAreaPrice", allBranchMap.get(objArr1[1].toString()));
						}else{
							obj.put("allAreaPrice", null);
						}
						if(objArr1[1] != null && bayAreaBranchMap.get(objArr1[1].toString()) != null){
							obj.put("bayAreaPrice", bayAreaBranchMap.get(objArr1[1].toString()));
						}else{
							obj.put("bayAreaPrice", null);
						}
						obj.put("autoPromoDiscount", discount);						
					}
					if(objArr1[5] != null){
						obj.put("tandc", objArr1[5].toString());
					}
					json.add(obj);
    			}  			
    		}   		
    		//System.out.println(json.toString());
    		return json.toString(); 	    	  					
    	}catch(Exception e){    		
    		//System.out.println("Error occoured while querying Product");
            log.error("Error in show form",se);        		
    	}
		return null;    	
    }*/
    
    @RequestMapping(value="getProductDetailsByLocation", method=RequestMethod.GET)
    public @ResponseBody String  getProductDetailsByLocation(@RequestParam(value="locationId") Long locationId, final HttpServletRequest request, final HttpServletResponse response) { 		
    	try{  
    		List<SystemProperty> allAreaBranchName = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH);			
			List<SystemProperty> bayAreaBranchName = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_BAYAREA);
			
			HashMap<String, JSONArray> allBranchMap =  new HashMap<String, JSONArray>();
    		HashMap<String, JSONArray> bayAreaBranchMap =  new HashMap<String, JSONArray>();
			if(allAreaBranchName != null && !allAreaBranchName.isEmpty() && bayAreaBranchName != null && !bayAreaBranchName.isEmpty()){			
				List<Location> allBranchLocationIdLst = locationDao.findByRecordName(allAreaBranchName.get(0).getKeyValue());
	    		List<Location> bayAreaBranchIdLst = locationDao.findByRecordName(bayAreaBranchName.get(0).getKeyValue());
	    		
	    		if(allBranchLocationIdLst != null && !allBranchLocationIdLst.isEmpty()){
	    			List<Object[]> allBranchItemDetails =  itemDetailDao.getMembershipProgramByLocation(allBranchLocationIdLst.get(0).getId());
	    			for(Object product : allBranchItemDetails){
	    				JSONArray allBranchArr = new JSONArray();
	        			Object objArr1[] = (Object[]) product;	
	        			if(objArr1 != null && objArr1.length >0){
	    					if(objArr1[7] != null){    						
	    						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(Long.parseLong(objArr1[7].toString()), Constants.Status_Active, Constants.Status_Active);						
	    						/*if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
	    							allBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getPricingRule().getTierPrice()));
	    						}*/
	    						double signupMonthlyTierPrice = 0D;
	    						double signupMonthlyJoinPrice = 0D;
	    						double signupAnnualTierPrice = 0D;
	    						double signupAnnualJoinPrice = 0D;
	    						double registrationPrice = 0D;
	    						double depositPrice = 0D;
	    						String signupMonthlyBillingFrequency = new String();
	    						String signupAnnualBillingFrequency = new String();
	    						String registrationBillingFrequency = new String();
	    						String depositBillingFrequency = new String();
	    						for(ItemDetailPricingRule pricingRule: pricingRuleLst) {	    		 
	    				    		if(pricingRule!=null && pricingRule.getPricingRule()!=null){							
    				    				if(pricingRule.getPricingRule().getType() != null && Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){    				    					
    				    					if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_MONTHLY)){
    				    						signupMonthlyTierPrice = signupMonthlyTierPrice + pricingRule.getPricingRule().getTierPrice();
    				    						signupMonthlyJoinPrice = signupMonthlyJoinPrice + pricingRule.getPricingRule().getJoiningFee();
    				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
    				    							signupMonthlyBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
    				    						//signUpPrice.put("Monthly", pricingRule.getPricingRule().getTierPrice());
    				    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());    				    						
    				    					}else if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_ANNUAL)){
    				    						//signUpPrice.put("Annual", pricingRule.getPricingRule().getTierPrice());
    				    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());	
    				    						signupAnnualTierPrice = signupAnnualTierPrice + pricingRule.getPricingRule().getTierPrice();
    				    						signupAnnualJoinPrice = signupAnnualJoinPrice + pricingRule.getPricingRule().getJoiningFee();
    				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
    				    							signupAnnualBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();    				    			
    				    					}  				    						    				    					
    				    				}else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
				    						
					    					//registrationpriceArr.add(priceObj);
					    					//obj.put("registrationPrice", pricingRule.getPricingRule().getTierPrice());
				    						registrationPrice = registrationPrice + pricingRule.getPricingRule().getTierPrice();
				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
				    							registrationBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
					    				}
					    				else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
					    					//depositpriceArr.add(priceObj);
					    					//obj.put("depositPrice", pricingRule.getPricingRule().getTierPrice());
					    					depositPrice = depositPrice + pricingRule.getPricingRule().getTierPrice();
					    					if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
					    						depositBillingFrequency = pricingRule.getPricingRule().getBillingFrequency(); 
					    				}      				    							    				
	    				    		}
	    				    	}
	    						/*JSONObject signUpPrice = new JSONObject();
	    						signUpPrice.put("Monthly", signupMonthlyTierPrice);
	    						signUpPrice.put("joiningPrice", signupMonthlyJoinPrice);
	    						signUpPrice.put("Annual", signupAnnualTierPrice);
	    						signUpPrice.put("joiningPrice", signupAnnualJoinPrice);
	    						allBranchArr.add(signUpPrice);
	    						allBranchMap.put(objArr1[7].toString(), allBranchArr);*/
	    						
	    						JSONObject signUpPriceMonthly = new JSONObject();
		    					signUpPriceMonthly.put("Monthly", signupMonthlyTierPrice);
		    					signUpPriceMonthly.put("joiningPrice", signupMonthlyJoinPrice);		    						
		    					signUpPriceMonthly.put("billingFrequency", signupMonthlyBillingFrequency);
		    					JSONObject signUpPriceAnnual = new JSONObject();
	    						signUpPriceAnnual.put("Annual", signupAnnualTierPrice);
	    						signUpPriceAnnual.put("joiningPrice", signupAnnualJoinPrice);	
	    						signUpPriceAnnual.put("billingFrequency", signupAnnualBillingFrequency);
	    						JSONObject registrationPriceJson = new JSONObject();
	    						registrationPriceJson.put("registrationPrice", registrationPrice);
	    						registrationPriceJson.put("billingFrequency", registrationBillingFrequency);
	    						JSONObject depositPriceJson = new JSONObject();	  
	    						depositPriceJson.put("depositPrice", depositPrice);
	    						depositPriceJson.put("billingFrequency", depositBillingFrequency);
	    						JSONObject allBranchItemDetailId = new JSONObject();
	    						allBranchItemDetailId.put("allBranchItemDetailId", objArr1[7].toString());
	    						allBranchArr.add(signUpPriceMonthly);
	    						allBranchArr.add(signUpPriceAnnual);
	    						allBranchArr.add(registrationPriceJson);
	    						allBranchArr.add(depositPriceJson);
	    						allBranchArr.add(allBranchItemDetailId);
	    						allBranchMap.put(objArr1[1].toString(), allBranchArr);
	    					}
	        			}
	    			}
	    		}
	    		if(bayAreaBranchIdLst != null && !bayAreaBranchIdLst.isEmpty()){
	    			List<Object[]> bayAreaBranchItemDetails =  itemDetailDao.getMembershipProgramByLocation(bayAreaBranchIdLst.get(0).getId());
	    			for(Object product : bayAreaBranchItemDetails){
	    				JSONArray bayAreaArr = new JSONArray();
	        			Object objArr1[] = (Object[]) product;	
	        			if(objArr1 != null && objArr1.length >0){
	    					if(objArr1[7] != null){    						
	    						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(Long.parseLong(objArr1[7].toString()), Constants.Status_Active, Constants.Status_Active);						
	    						/*if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
	    							bayAreaBranchMap.put(objArr1[1].toString(), Integer.parseInt(pricingRuleLst.get(0).getPricingRule().getTierPrice()));
	    						}*/
	    						double signupMonthlyTierPrice = 0D;
	    						double signupMonthlyJoinPrice = 0D;
	    						double signupAnnualTierPrice = 0D;
	    						double signupAnnualJoinPrice = 0D;
	    						double registrationPrice = 0D;
	    						double depositPrice = 0D;
	    						String signupMonthlyBillingFrequency = new String();
	    						String signupAnnualBillingFrequency = new String();
	    						String registrationBillingFrequency = new String();
	    						String depositBillingFrequency = new String();
	    						for(ItemDetailPricingRule pricingRule: pricingRuleLst) {	    		 
	    				    		if(pricingRule!=null && pricingRule.getPricingRule()!=null){							
    				    				if(pricingRule.getPricingRule().getType() != null && Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
    				    					//JSONObject signUpPrice = new JSONObject();
    				    					if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_MONTHLY)){
    				    						signupMonthlyTierPrice = signupMonthlyTierPrice + pricingRule.getPricingRule().getTierPrice();
    				    						signupMonthlyJoinPrice = signupMonthlyJoinPrice + pricingRule.getPricingRule().getJoiningFee();
    				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
    				    							signupMonthlyBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
    				    						//signUpPrice.put("Monthly", pricingRule.getPricingRule().getTierPrice());
    				    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());    				    						
    				    					}else if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_ANNUAL)){
    				    						//signUpPrice.put("Annual", pricingRule.getPricingRule().getTierPrice());
    				    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());
    				    						signupAnnualTierPrice = signupAnnualTierPrice + pricingRule.getPricingRule().getTierPrice();
    				    						signupAnnualJoinPrice = signupAnnualJoinPrice + pricingRule.getPricingRule().getJoiningFee();
    				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
    				    							signupAnnualBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
    				    					}
    				    					//bayAreaArr.add(signUpPrice);	    				    					
    				    				}else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
					    					//registrationpriceArr.add(priceObj);
					    					//obj.put("registrationPrice", pricingRule.getPricingRule().getTierPrice());
				    						registrationPrice = registrationPrice + pricingRule.getPricingRule().getTierPrice();
				    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
				    							registrationBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
					    				}
					    				else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
					    					//depositpriceArr.add(priceObj);
					    					//obj.put("depositPrice", pricingRule.getPricingRule().getTierPrice());
					    					depositPrice = depositPrice + pricingRule.getPricingRule().getTierPrice();
					    					if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
					    						depositBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
					    				}    				    							    				
	    				    		}
	    				    	}
	    						JSONObject signUpPriceMonthly = new JSONObject();
		    					signUpPriceMonthly.put("Monthly", signupMonthlyTierPrice);
		    					signUpPriceMonthly.put("joiningPrice", signupMonthlyJoinPrice);	
		    					signUpPriceMonthly.put("billingFrequency", signupMonthlyBillingFrequency);
	    						JSONObject signUpPriceAnnual = new JSONObject();
	    						signUpPriceAnnual.put("Annual", signupAnnualTierPrice);
	    						signUpPriceAnnual.put("joiningPrice", signupAnnualJoinPrice);		
	    						signUpPriceAnnual.put("billingFrequency", signupAnnualBillingFrequency);
	    						
	    						JSONObject registrationPriceJson = new JSONObject();
	    						registrationPriceJson.put("registrationPrice", registrationPrice);
	    						registrationPriceJson.put("billingFrequency", registrationBillingFrequency);
	    						JSONObject depositPriceJson = new JSONObject();	  
	    						depositPriceJson.put("depositPrice", depositPrice);
	    						depositPriceJson.put("billingFrequency", depositBillingFrequency);
	    						JSONObject bayAreaItemDetailId = new JSONObject();
	    						bayAreaItemDetailId.put("bayAreaItemDetailId", objArr1[7].toString());
	    						
	    						bayAreaArr.add(signUpPriceMonthly);
	    						bayAreaArr.add(signUpPriceAnnual);    						
	    						bayAreaArr.add(registrationPriceJson);
	    						bayAreaArr.add(depositPriceJson);
	    						bayAreaArr.add(bayAreaItemDetailId);
	    						bayAreaBranchMap.put(objArr1[1].toString(), bayAreaArr);
		    					/*
	    						JSONObject signUpPrice = new JSONObject();
	    						signUpPrice.put("Monthly", signupMonthlyTierPrice);
	    						signUpPrice.put("joiningPrice", signupMonthlyJoinPrice);
	    						signUpPrice.put("Annual", signupAnnualTierPrice);
	    						signUpPrice.put("joiningPrice", signupAnnualJoinPrice);
	    						bayAreaArr.add(signUpPrice);	    						
	    						bayAreaBranchMap.put(objArr1[7].toString(), bayAreaArr);*/
	    					}
	        			}
	    			}
	    		}  	
			}
    		
    		List<Object[]> itemDetails =  itemDetailDao.getMembershipProgramByLocation(locationId);
    		List<WaiversAndTC> waiversAndTC = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
    		JSONArray json = new JSONArray();  
   		
    		for(Object product : itemDetails){
    			Object objArr1[] = (Object[]) product;	
    			if(objArr1 != null && objArr1.length >0){
    				JSONObject obj = new JSONObject();
    				if(objArr1[0] != null){
    					obj.put("id", objArr1[0].toString());
    					System.out.println("\nItemDetailId ::  "+objArr1[0].toString());
    				}
    				if(objArr1[1] != null){
    					obj.put("productName", objArr1[1].toString());
    				}
					if(objArr1[2] != null){
						obj.put("productDescription", objArr1[2].toString());			
					}
					if(objArr1[3] != null){
						obj.put("productType", objArr1[3].toString());
					}
					if(objArr1[4] != null){
						obj.put("productDuration", objArr1[4].toString());
					}
					if(objArr1[8] != null){
						obj.put("billDateOption", objArr1[8].toString());
					}
					if(objArr1[9] != null){
						obj.put("billDateOffset", objArr1[9].toString());
					}
					if(objArr1[10] != null){
						obj.put("dueDateOption", objArr1[10].toString());
					}
					if(objArr1[11] != null){
						obj.put("dueDateOffset", objArr1[11].toString());
					}
					JSONArray signuppriceArr = new JSONArray();
					if(objArr1[0] != null){
						//System.out.println("   objArr1[7].toString() --> "+objArr1[7].toString()+" objArr1[1].toString() >>  "+objArr1[1].toString());
						List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(Long.parseLong(objArr1[0].toString()), Constants.Status_Active, Constants.Status_Active);
			    		
			    		//JSONArray registrationpriceArr = new JSONArray();
			    		//JSONArray depositpriceArr = new JSONArray();
						double signupMonthlyTierPrice = 0D;
						double signupMonthlyJoinPrice = 0D;
						double signupAnnualTierPrice = 0D;
						double signupAnnualJoinPrice = 0D;
						double registrationPrice = 0D;
						double depositPrice = 0D;
						String signupMonthlyBillingFrequency = new String();
						String signupAnnualBillingFrequency = new String();
						String registrationBillingFrequency = new String();
						String depositBillingFrequency = new String();
			    		for(ItemDetailPricingRule pricingRule: pricingRuleLst) {	    		 
			    		  if(pricingRule!=null && pricingRule.getPricingRule()!=null){							
			    				if(Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
			    					//JSONObject signUpPrice = new JSONObject();
			    					if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals("Monthly")){
			    						//signUpPrice.put("Monthly", pricingRule.getPricingRule().getTierPrice());
			    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());		
			    						//obj.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());	
			    						//obj.put("tierPrice", pricingRule.getPricingRule().getTierPrice());
			    						signupMonthlyTierPrice = signupMonthlyTierPrice + pricingRule.getPricingRule().getTierPrice();
			    						signupMonthlyJoinPrice = signupMonthlyJoinPrice + pricingRule.getPricingRule().getJoiningFee();
			    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
			    							signupMonthlyBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
			    					}else if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals("Annual")){
			    						//signUpPrice.put("Annual", pricingRule.getPricingRule().getTierPrice());
			    						//signUpPrice.put("joiningPrice", pricingRule.getPricingRule().getJoiningFee());	
			    						signupAnnualTierPrice = signupAnnualTierPrice + pricingRule.getPricingRule().getTierPrice();
			    						signupAnnualJoinPrice = signupAnnualJoinPrice + pricingRule.getPricingRule().getJoiningFee();
			    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
			    							signupAnnualBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
			    					}
			    								    					
			    				}else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
			    					//registrationpriceArr.add(priceObj);
			    					//obj.put("registrationPrice", pricingRule.getPricingRule().getTierPrice());
		    						registrationPrice = registrationPrice + pricingRule.getPricingRule().getTierPrice();
		    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
		    							registrationBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
			    				}
			    				else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
			    					//depositpriceArr.add(priceObj);
			    					//obj.put("depositPrice", pricingRule.getPricingRule().getTierPrice());
			    					depositPrice = depositPrice + pricingRule.getPricingRule().getTierPrice();
			    					if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
			    						depositBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
			    				}			    							    				
			    			}
			    		}
			    		JSONObject signUpPriceMonthly = new JSONObject();
    					signUpPriceMonthly.put("Monthly", signupMonthlyTierPrice);
    					signUpPriceMonthly.put("joiningPrice", signupMonthlyJoinPrice);
    					signUpPriceMonthly.put("billingFrequency", signupMonthlyBillingFrequency);
						JSONObject signUpPriceAnnual = new JSONObject();
						signUpPriceAnnual.put("Annual", signupAnnualTierPrice);
						signUpPriceAnnual.put("joiningPrice", signupAnnualJoinPrice);	
						signUpPriceAnnual.put("billingFrequency", signupAnnualBillingFrequency);
						
						JSONObject registrationPriceJson = new JSONObject();
    					registrationPriceJson.put("registrationPrice", registrationPrice);
    					registrationPriceJson.put("billingFrequency", registrationBillingFrequency);
    					JSONObject depositPriceJson = new JSONObject();	  
    					depositPriceJson.put("depositPrice", depositPrice);
    					depositPriceJson.put("billingFrequency", depositBillingFrequency);
    					
    					signuppriceArr.add(signUpPriceMonthly);
    					signuppriceArr.add(signUpPriceAnnual);				
    					signuppriceArr.add(registrationPriceJson);
    					signuppriceArr.add(depositPriceJson);
			    		
					}
					Double discount = new Double(0);
					if(objArr1[7] != null){							
						try { 
							List<ItemDetailPromotion> ipdLst =  itemPromoDetailsDao.getAutoApplyPromoDiscount(Long.parseLong(objArr1[7].toString()));
							for(ItemDetailPromotion ipd : ipdLst){
								/*Promotion promo = ipd.getPromotion();								
								if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_VALUE)){
									discount = promo.getDiscountvalue();
									  
								}
								else if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_PERCENTAGE)){
									BigDecimal memberdiscountAmount = promo.getDiscountpercentage().multiply(BigDecimal.valueOf(Long.valueOf(obj.get("tierPrice").toString())));
									memberdiscountAmount = memberdiscountAmount.divide(new BigDecimal(100));
									discount = memberdiscountAmount.doubleValue();									
								}*/
							}
						}catch (Exception e) {
							// TODO Auto-generated catch block
							log.error("Error in get Product by location ",e);
						}
					}
					System.out.println("  This branch ("+objArr1[7].toString()+") ::  "+signuppriceArr.toString());
					obj.put("itemDetailsId", objArr1[7].toString());
					obj.put("signupPrice", signuppriceArr);					
					obj.put("autoPromoDiscount", discount);	
						
					/*if(objArr1[1] != null && allBranchMap.get(objArr1[1].toString()) != null){
						obj.put("allAreaPrice", allBranchMap.get(objArr1[1].toString()));
					}else{
						obj.put("allAreaPrice", null);
					}
					if(objArr1[1] != null && bayAreaBranchMap.get(objArr1[1].toString()) != null){
						obj.put("bayAreaPrice", bayAreaBranchMap.get(objArr1[1].toString()));
					}else{
						obj.put("bayAreaPrice", null);
					}*/
					
					if(objArr1[1] != null && allBranchMap.get(objArr1[1].toString()) != null){
						System.out.println("  All Area ("+objArr1[7].toString()+") ::  "+allBranchMap.get(objArr1[1].toString()));
						obj.put("allAreaPrice", allBranchMap.get(objArr1[1].toString()));
					}else{
						obj.put("allAreaPrice", null);
					}
					if(objArr1[1] != null && bayAreaBranchMap.get(objArr1[1].toString()) != null){
						System.out.println("  Bay Area ("+objArr1[7].toString()+") ::  "+bayAreaBranchMap.get(objArr1[1].toString()));
						obj.put("bayAreaPrice", bayAreaBranchMap.get(objArr1[1].toString()));
					}else{
						obj.put("bayAreaPrice", null);
					}
				
					if(objArr1[5] != null && waiversAndTC != null && !waiversAndTC.isEmpty()){
						obj.put("tandc", waiversAndTC.get(0).getTcDescription());
					}
					json.add(obj);
				}
			
    		}  	
    		return json.toString(); 					
    	}catch(Exception e){    		
    		//System.out.println("Error occoured while querying Product");
    		log.error("Error in get Product by location ",e);      		
    	}
		return null;    	
    }
    
    @SuppressWarnings("unused")
	@RequestMapping(value="becomeMemberRegister", method = RequestMethod.POST)
    public ModelAndView becomeMemberRegister(Account account, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {    
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	String opptyId = request.getParameter("opptyId");  
    	String finalAmount =  request.getParameter("finalAmount");
    	String discountAmount =  request.getParameter("discountAmount");
    	String faAmount =  request.getParameter("faAmtHiddenInput");
    	String signupPrice =  request.getParameter("signupPrice");
    	String setUpFee =  request.getParameter("setUpFee");
    	String registrationFee =  request.getParameter("registrationFee");
    	String deposit =  request.getParameter("deposit");
    	String faPercentage =  request.getParameter("faPercentage");
    	String faStartDate =  request.getParameter("faStartDate");
    	String faEndDate =  request.getParameter("faEndDate");
    	String joinFeeAmt =  request.getParameter("joinFeeAmt");       	
    	String membershipStartDateStr =  request.getParameter("membershipStartDate");
    	String promotionMapStr =  request.getParameter("promotionMapInput");
    	
    	String signUpPromoDiscount =  request.getParameter("signUpPromoDiscountHiddenInput");
    	String otherPromoDiscount =  request.getParameter("otherPromoDiscountHiddenInput");
    	String sumOfAdditives =  request.getParameter("sumOfAdditivesHiddenInput");
    	String isRecurring =  request.getParameter("isRecurringHiddenInput");
    	String priceOption =  request.getParameter("paymentFrequency");
    	
    	String billDate =  request.getParameter("billDate");
    	String dueDate =  request.getParameter("dueDate");
    	String nextBillDate =  request.getParameter("nextBillDate");
    	String invoiceDate =  request.getParameter("invoiceDate");
    	String billDateOnInvoice =  request.getParameter("billDateOnInvoice");
    	String dueDateOnInvoice =  request.getParameter("dueDateOnInvoice");
    	
    	JSONArray promosJson = null;
    	double totalDiscount = 0d, totalMonthlyDiscountAmount = 0d;
		if(promotionMapStr != null && !promotionMapStr.equals("")){
			promosJson = JSONArray.fromObject(promotionMapStr);
			
			if(promosJson != null && promosJson.size() > 0){
				for (Object promoObj : promosJson) {
					if(promoObj != null && !promoObj.toString().equals("") && !promoObj.toString().equals("undefined")){
						JSONObject promoJson = JSONObject.fromObject(promoObj.toString());
						totalDiscount += Double.parseDouble(promoJson.get("discountValue").toString());
						totalMonthlyDiscountAmount += Double.parseDouble(promoJson.get("monthlyDiscountAmount").toString());
					}
				}
			}
		}
		
		double signupAmount = computeAmount("forSignup", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
		double payerAmount = computeAmount("forPayer", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
		double invoiceAmount = computeAmount("forInvoice", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
		double paymentAmount = computeAmount("forPayment", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
		
		log.info(" finalAmount "+finalAmount+", signupPrice "+signupPrice+", registrationFee "+registrationFee+", deposit "+deposit+" joinFeeAmt "+joinFeeAmt);
		log.info(" promotionMapStr "+promotionMapStr+", signUpPromoDiscount "+signUpPromoDiscount+", otherPromoDiscount "+otherPromoDiscount+", sumOfAdditives "+sumOfAdditives+" isRecurring "+isRecurring);
		log.info(" billDate "+billDate+", dueDate "+dueDate+", nextBillDate "+nextBillDate+", invoiceDate "+invoiceDate+" billDateOnInvoice "+billDateOnInvoice+", dueDateOnInvoice "+dueDateOnInvoice);
		
    	Date membershipStartDate = new Date();
    	if(!StringUtils.isBlank(membershipStartDateStr)){
	    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");			
			try {
				membershipStartDate = sdf.parse(membershipStartDateStr);
			} catch (ParseException e) {
				log.error("Error becomeMemberRegister",e);
			}
    	}
    	
    	User user = new User();     	
    	//User accUser = new User();  
    	//accUser = account.getUserLst().get(0);
    	StringBuffer draftCycle = null;
    	String draftCycleStr = null;
    	Signup signup = new Signup();
    	boolean isYouthUserAdult = false;
    	Model model = new ExtendedModelMap();
    	ItemDetail itemDetail  =  itemDetailDao.getById(Long.parseLong(account.getProductId()));
    	if(isRecurring != null && isRecurring.equals("true") && priceOption != null && itemDetail.getDueDateOffset() != null){
    		draftCycle = new StringBuffer();
    		draftCycle.append(priceOption).append(",").append(itemDetail.getDueDateOffset());
    		draftCycleStr = draftCycle.toString();
    	}
    	String userId = null;
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	userId = auth.getName();
    	if(userId == null || "anonymousUser".equals(userId)){ 
    		List<User> userLst = new ArrayList<User>();
        	userLst = populateUserLstData(account);
			if(userLst != null && !userLst.isEmpty()){
				User primaryUser = null;
				if(userLst.size() > 1 && isYouthMemberAdult(itemDetail, userLst.get(1))){
					isYouthUserAdult =  true;
					primaryUser = userLst.get(1);
					userLst = new ArrayList<User>();
					userLst.add(primaryUser);					
				}else{
					isYouthUserAdult = false;
					primaryUser = userLst.get(0);					
				}
				if(account != null && account.getLoggedInUserEmailId().equals("") && StringUtils.isBlank(getAgentUidFromSession())){
					userLst.get(0).setPassword(bCryptPasswordEncoder.encode(userLst.get(0).getPassword()));
					primaryUser.setPassword(userLst.get(0).getPassword());
				}
				userLst.get(0).setPrimary(true);
				userLst.get(0).setMember(true);
				
		    	//populateContactAndCustomerWaiversAndTC(primaryUser, account);  	    	  
		    	//userLst.add(user);
		    	account.setUser(new HashSet<User>());
		    	
		    	List<User> kidsInfo = new ArrayList<User>();
		    	for(User usr : userLst){	    		
		    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
		    			model.addAttribute("secUser", usr);
		    		}
		    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
		    			model.addAttribute("thirdUser", usr);
		    		}
		    		if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
		    			kidsInfo.add(usr);
		    		}
		    		   		
		    	}    	    	   	
		    	account.setEmail(primaryUser.getPrimaryEmailAddress());
		    	account.setName(primaryUser.getFullName());  	
		    	Calendar cal = Calendar.getInstance();
		    	account.setLastUpdated(cal);
		    	account.setCountry(Constants.COUNTRY_USA);
		    	account.setName(primaryUser.getFullName() + " " + Constants.ACCOUNT_FAMILY);
		    	account.setCustomerType(Constants.ACCOUNT_FAMILY);
		        user.setEnabled(true);        
		    	  
		        try {              	
		            Account acc = accountDao.saveAndFlush(account);  
		            List<User> savedUserLst = new ArrayList<User>();
		            for(User usr : userLst){
		            	usr.setCustomer(acc);
		            	User dbUser = userDao.save(usr);
		            	AccountContact accContact = accountContactService.saveAccountContact(acc, dbUser);
		            	savedUserLst.add(dbUser);
		            }
		           
		            Date nextBillDateObj =  null;
		            if(nextBillDate != null){
		            	try{
		            		nextBillDateObj = new Date(nextBillDate);
		            	}catch(Exception e1){
		            		//e1.printStackTrace();
		            	}
		            }
		            JetPayPayment jetPayPayment = new JetPayPayment();
		            PaymentMethod paymentMethod  =  populatePaymentMethodData(acc, account.getTransId());
		            paymentMethod = paymentMethodDao.save(paymentMethod);		            
		            List<Signup> signupList =   populateSignupDataLst(account, savedUserLst, itemDetail,  opptyId, paymentMethod, String.valueOf(paymentAmount), discountAmount, faAmount, signupPrice, setUpFee, registrationFee,  deposit,
		       			 faPercentage, faStartDate, faEndDate, joinFeeAmt, membershipStartDate, nextBillDateObj, draftCycleStr);
		            List<Signup> savedSignupList = new ArrayList<Signup>();
		            int count = 0;
		            Signup parentSignupObj = null;
		            for(Signup signupSave : signupList){
		            	signupSave.setDiscountAmount(String.valueOf(totalDiscount));
		            	if(itemDetail != null && itemDetail.getRecordName() != null 
		            			&& Constants.MEMBERSHIP_PRODUCT_YOUTH.equals(itemDetail.getRecordName())){
		            		if(count != 0 || isYouthUserAdult){
		            			signupSave.setPaymentMethod(paymentMethod);
		            			signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());		            			
		            			if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
        							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
        						}
		            			parentSignupObj = signupDao.save(signupSave);
		            			savedSignupList.add(parentSignupObj);
		            		}
						}else{
							signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());
							signupSave.setParentSignUp(parentSignupObj);
							if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
    							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
    						}
							Signup signup2 = signupDao.save(signupSave); 
							savedSignupList.add(signup2);
						}
		            	count++;
		            }         
		            
		            User userTemp = new User();
		            User userDb = getUserByAccount(acc, user);
		            Payer payer = null;
		            if(savedSignupList !=  null && !savedSignupList.isEmpty() && savedSignupList.get(0) != null ){
		            	
		            	paymentService.saveSignupPromos(savedSignupList.get(0), promosJson);
		            	
		            	/*List<String> amountLst =  new ArrayList<String>();
		    			amountLst.add("");
		    			amountLst.add("");
		    			amountLst.add(String.valueOf(savedSignupList.get(0).getSignupPrice()));*/
		            	JSONObject amountLst =  new JSONObject();
		    			//amountLst.put("itemprice", String.valueOf(savedSignupList.get(0).getSignupPrice()));		    			
		    			//amountLst.put("itemprice", String.valueOf(savedSignupList.get(0).getSignupPrice()));
		    			//amountLst.put("itempriceOnSignup", String.valueOf(savedSignupList.get(0).getSignupPrice()));
		    			amountLst.put("itempriceOnPayer", String.valueOf(payerAmount));
		    			amountLst.put("isRecurring", "true");
		    			payer = paymentService.savepayer(paymentMethod.getId().toString(), account, amountLst, itemDetail, savedSignupList.get(0), null); 
		    			
		            }
	    			Invoice invoice = null;
	    			Date currentDate =  new Date();
	    			/*List<String> amountLst1 =  new ArrayList<String>();
	    			amountLst1.add("");
	    			amountLst1.add("");
	    			amountLst1.add("");
	    			amountLst1.add("");
	    			amountLst1.add(account.getProductPrice());*/
	    			JSONObject amountLst1 =  new JSONObject();
	    			//amountLst1.put("itemprice", account.getProductPrice());	    			
	    			amountLst1.put("itemprice", account.getProductPrice());
	    			amountLst1.put("fa", "0");  
	    			amountLst1.put("itempriceOnInvoice", String.valueOf(invoiceAmount));
	    			amountLst1.put("isRecurring", "true");    
	    			if(payer != null && payer.getEnddate() != null && currentDate.before(payer.getEnddate())){
	    				invoice = paymentService.saveinvoice(acc, amountLst1, savedUserLst.get(0), savedSignupList.get(0), payer);
	    			}else if(payer.getEnddate() == null){
	    				invoice = paymentService.saveinvoice(acc, amountLst1, savedUserLst.get(0), savedSignupList.get(0), payer);
	    			}
	    			JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), acc,  savedUserLst.get(0), savedSignupList.get(0),payer,invoice, paymentAmount);
	    			// added on 24072015 - if FA amount on invoice > 0, create payment record of type "Credit Memo/FA Waiver" and amount will be FA_AMOUNT
	    			Object fa = amountLst1.get("fa");
	    			if(fa!=null && Double.parseDouble(fa.toString())>0){
	    				String paymentMode = PaymentTypeEnum.Debit.toString();
    					String paymentType = PaymentTypeEnum.CreditMemoFAWaiver.toString();
    					paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), acc,  savedUserLst.get(0), savedSignupList.get(0),payer,invoice,paymentMode,Double.parseDouble(fa.toString()),paymentType);
    				}
	    			//end 
	    			
	    			Activity activity = interactionDao.save(getActivityData(account, savedUserLst.get(0), savedSignupList.get(0)));
	    			
	    			//Interaction interaction = interactionDao.save(populateInteractionData(signupList.get(0), account, userDb));
	    			
	    			if(!StringUtils.isBlank(userLst.get(0).getReferrerEmail())){
						List<User> referrerContactLst = userDao.getUserLstByPrimaryEmailAddress(userLst.get(0).getReferrerEmail());
						if(referrerContactLst != null && !referrerContactLst.isEmpty()){
							List<User> registredContactLst = userDao.getUserLstByPrimaryEmailAddress(userLst.get(0).getPrimaryEmailAddress());
							if(registredContactLst != null && !registredContactLst.isEmpty()){
								registredContactLst.get(0).setReferrerContactId(referrerContactLst.get(0).getContactId());
								Calendar lastUpdCal = Calendar.getInstance();
								registredContactLst.get(0).setLastUpdated(lastUpdCal);
								//registredContactLst.get(0).setRelationships(Constants.REFEREE);
								referrerContactLst.get(0).setReferrerContactId(registredContactLst.get(0).getContactId());
								referrerContactLst.get(0).setLastUpdated(lastUpdCal);
								//referrerContactLst.get(0).setRelationships(Constants.REFERRER);
								userDao.save(referrerContactLst.get(0));
								userDao.save(registredContactLst.get(0));
							}						
						}
					}
		    		if(acc != null){	    		    	    	
		    	        model.addAttribute("account", account);    	            	        
		    	        model.addAttribute("primaryUser", primaryUser);
		    	        model.addAttribute("kidsInfo", kidsInfo);    	    	        
		    	        model.addAttribute("signup", savedSignupList.get(0));
		    	        model.addAttribute("product", itemDetail);
		    	        model.addAttribute("paymentTransId", account.getTransId());
		    	        model.addAttribute("prodJoinFee", account.getJoiningFee());
		    	        model.addAttribute("productPrice", account.getProductPrice());
		    	        if(account.getJoiningFee() != null && account.getProductPrice() != null){
		    	        	double prodJoinFee = Double.parseDouble(account.getJoiningFee());
		    	        	double prodPrice = Double.parseDouble(account.getProductPrice());
		    	        	double totalPrice = prodJoinFee+ prodPrice;
		    	        	model.addAttribute("productTotalPrice", totalPrice);
		    	        }
		    		}
		    		if(StringUtils.isBlank(getAgentUidFromSession())){
			    		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(primaryUser.getEmailAddress(), primaryUser.getPassword());		
		        		// generate session if one doesn't exist
		                request.getSession();
		                token.setDetails(new WebAuthenticationDetails(request));
		                SecurityContextHolder.getContext().setAuthentication(token);		                
		    		}
		    		if(account != null && account.getSendEmail() != null && account.getSendEmail()){
		    			try  
					 	{  					 		
					 		if(acc!=null && acc.getUser() != null){		
						 			/*
									 * To Generate the Password automatically - Start
									 */					
						 			// create a random token and save it in database with time stamp
						 			// create secure 16 digit token
						 			String token = new BigInteger(130, random).toString(32);
						 			
						 			// get current time stamp
						 			java.util.Date date= new java.util.Date();
						 			
						 			// save in database
						 			acc.setResetPassword(true);
						 			acc.setToken(token);
						 			acc.setSentdate(new Timestamp(date.getTime()));
						 			Account accDb = accountDao.save(acc);
					 				User contact =  null;	
					 				contact = getUserByAccount(accDb, contact);
					 				
					 				// Interaction Integration block start 
					 				Activity interaction = new Activity();
					 				interaction.setType(Constants.REQUEST_RESET_PASSWORD);
					 				
					 				//String resetUrl = request.getRequestURL().toString().replace("/resetpassword", "/resetpasscode");
					 				String resetUrl = request.getRequestURL().toString().replace("/becomeMember", "/recovery/resetpasscode");
					 				String queryString = "?token="+token;
					 				interaction.setDescription(resetUrl+queryString);
					 				
					 				// get current time stamp
					 				interaction.setCreatedDate(new Timestamp(date.getTime()));
					 				interaction.setCustomer(accDb);
					 				interaction.setContact(contact);
					 				interaction.setShowOnPortal(true);
					 				interaction.setLastUpdated(Calendar.getInstance());
					 				activityDao.save(interaction);			
					 				model.addAttribute("EmailSentSuccess", Constants.FORGET_PASSWORD_SUCCESS); 
					 			} else {
					 				model.addAttribute("EmailSentError",Constants.FORGET_PASSWORD_ERROR); 
					 			}
					 	} catch (Exception ex) {  
					 		log.error("Error occured while creating Activity for reset Password Activity ",ex);
					 		model.addAttribute("EmailSentError",Constants.FORGET_PASSWORD_ERROR);  
					 	}
		    		}
		    		request.getSession().setAttribute("usInfo", primaryUser);	
	                request.getSession().setAttribute("accInfo", account);		
	                model.addAttribute("isYouthUserAdult", isYouthUserAdult);	                
		    		return new ModelAndView("becomeMemberconfirmation", model.asMap());
		            
		        } catch (Exception ade) {
		        	log.error("Error  ",ade);
		        	model.addAttribute("errMsg", "Error Occured in Registration Process");
					return new ModelAndView("login", model.asMap());
		        }
			}else{
				return new ModelAndView("login", model.asMap());
			}
    	}else{
    		//Process to save the new User(s) as member and process the membership of the added users.  
    		try {
	    		List<User> userLst = new ArrayList<User>();
	        	userLst = populateUserLstData(account);	   
	        	
				if(userLst.size() > 1 && isYouthMemberAdult(itemDetail, userLst.get(1))){
					isYouthUserAdult =  true;
					User primaryUser = null;
					primaryUser = userLst.get(1);
					userLst = new ArrayList<User>();
					userLst.add(primaryUser);					
				}else{
					isYouthUserAdult = false;					
				}
	    		Account accountVO = accountDao.getByEmail(userId);    		
	    		User primaryContact = null;
	    		primaryContact = getUserByAccount(accountVO, primaryContact);
	    		List<User> saveUserLst = new ArrayList<User>();
	    		List<User> savedUserLst = new ArrayList<User>();
	    		List<User> membershipUserLst = new ArrayList<User>();	
	    		
				for(User u : userLst){
					List<User> dbUsrLst = null;
    				dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirth(u.getFirstName(), u.getLastName(), u.getDateOfBirth());
    				/*if(isAdultByDob(u.getDateOfBirth())){
    					dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirthAndFormattedPhoneNumber(u.getFirstName(), u.getLastName(), u.getDateOfBirth(), u.getFormattedPhoneNumber());
    				}else{
    					dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirth(u.getFirstName(), u.getLastName(), u.getDateOfBirth());
    				}*/
    				
    				if(dbUsrLst != null && !dbUsrLst.isEmpty()){
    					dbUsrLst.get(0).setMembershipAgeCategory(u.getMembershipAgeCategory());
    					membershipUserLst.add(dbUsrLst.get(0));
    				}else{
    					saveUserLst.add(u);
    				}				
				}
	    		if(saveUserLst != null && !saveUserLst.isEmpty()){		        		            
		            for(User usr : saveUserLst){
		            	usr.setCustomer(accountVO);
		            	User dbUser = userDao.save(usr);
		            	AccountContact accContact = accountContactService.saveAccountContact(accountVO, dbUser);
		            	savedUserLst.add(dbUser);
		            }		        	
	    		}	
	    		membershipUserLst.addAll(savedUserLst);
	    		account.setAccountId(accountVO.getAccountId());
	    		model.addAttribute("account", accountVO);
	            JetPayPayment jetPayPayment = new JetPayPayment();
	            PaymentMethod paymentMethod  =  populatePaymentMethodData(account, account.getTransId());
	            paymentMethod = paymentMethodDao.save(paymentMethod);
	            //signup = populateSignupData(account, primaryUser, itemDetails,signup,  opptyId, paymentMethod);
	            //List<Signup> signupList =  populateSignupDataLst(account, membershipUserLst, itemDetail,  opptyId, paymentMethod);
	            List<Signup> inactivateSignupLst =  signupDao.getByCustomerAndStatusAndType(account, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());        		
        		for(Signup sign : inactivateSignupLst){
        			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
        			sign.setPaymentMethod(null);
        			Calendar calendar = Calendar.getInstance(); // this would default to now
        			if(sign.getMembersshipFeeNextBillingDate() != null){
        				calendar.setTime(sign.getMembersshipFeeNextBillingDate());
        			}else {
        				calendar.setTime(new Date());
        			}       			
        			calendar.add(Calendar.DAY_OF_MONTH, -1);        			
        			sign.setProgramEndDate(calendar.getTime());
        			sign.setStatus(SignupStatusEnum.Inactive.toString());
        			sign.setPortalLastModifiedBy(getPortalLastModifiedBy());
        			signupDao.save(sign);
        		}	        		
        		Date nextBillDateObj =  null;
	            if(nextBillDate != null){
	            	try{
	            		nextBillDateObj = new Date(nextBillDate);
	            	}catch(Exception e1){
	            		//e1.printStackTrace();
	            	}
	            }
	            
	            List<Signup> signupList =   populateSignupDataLst(account, membershipUserLst, itemDetail,  opptyId, paymentMethod, String.valueOf(signupAmount), discountAmount, faAmount, signupPrice, setUpFee, registrationFee,  deposit,
		       			 faPercentage, faStartDate, faEndDate, joinFeeAmt, membershipStartDate, nextBillDateObj, draftCycle.toString());
	            
	            List<Signup> savedSignupList = new ArrayList<Signup>();
	            int count = 0;
	            Signup parentSignupObj = null;
	            for(Signup signupSave : signupList){
	            	if(itemDetail != null && itemDetail.getRecordName() != null && Constants.MEMBERSHIP_PRODUCT_YOUTH.equals(itemDetail.getRecordName())){
	            		if(count != 0 || isYouthUserAdult){
	            			signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());
	            			if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
    							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
    						}
	            			parentSignupObj = signupDao.save(signupSave);
	            			savedSignupList.add(parentSignupObj);
	            		}
					}else{
						signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());
						signupSave.setParentSignUp(parentSignupObj);
						if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
						}
						Signup signup2 = signupDao.save(signupSave); 
						savedSignupList.add(signup2);
					}
	            	count++;
	            }
	            /*for(Signup signupSave : signupList){
	            	Signup signup2 = signupDao.save(signupSave);  
	            }*/         
			            
	            paymentService.saveSignupPromos(savedSignupList.get(0), promosJson);
	            
	            User userTemp = new User();           
				/*List<String> amountLst =  new ArrayList<String>();
				amountLst.add("");
				amountLst.add("");
				amountLst.add(finalAmount);*/
	            JSONObject amountLst = new JSONObject();
	            //amountLst.put("itemprice", finalAmount);	            
	            amountLst.put("itemprice", String.valueOf(savedSignupList.get(0).getSignupPrice()));
	            amountLst.put("itempriceOnPayer", String.valueOf(payerAmount));
	            amountLst.put("isRecurring", "true");
				Payer payer = paymentService.savepayer(paymentMethod.getId().toString(), accountVO, amountLst, itemDetail, savedSignupList.get(0), null); 
				Invoice invoice = null;
				Date currentDate =  new Date();
				JSONObject amountLst1 =  new JSONObject();
    			//amountLst1.put("itemprice", account.getProductPrice());	    			
    			amountLst1.put("itemprice", account.getProductPrice());
    			amountLst1.put("fa", "0");  
    			amountLst1.put("itempriceOnInvoice", String.valueOf(invoiceAmount));
    			amountLst1.put("isRecurring", "true");    
				if(payer != null && payer.getEnddate() != null && currentDate.before(payer.getEnddate())){
					invoice = paymentService.saveinvoice(accountVO, amountLst1, primaryContact, savedSignupList.get(0), payer);
				}else if(payer.getEnddate() == null){
					invoice = paymentService.saveinvoice(accountVO, amountLst1, primaryContact, savedSignupList.get(0), payer);
				}
				JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), accountVO,  primaryContact, savedSignupList.get(0),payer,invoice, paymentAmount);			   	
				// added on 24072015 - if FA amount on invoice > 0, create payment record of type "Credit Memo/FA Waiver" and amount will be FA_AMOUNT
    			Object fa = amountLst1.get("fa");
    			if(fa!=null && Double.parseDouble(fa.toString())>0){
    				String paymentMode = PaymentTypeEnum.Debit.toString();
					String paymentType = PaymentTypeEnum.CreditMemoFAWaiver.toString();
					paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), accountVO,  primaryContact, savedSignupList.get(0),payer,invoice,paymentMode,Double.parseDouble(fa.toString()),paymentType);
				}
    			//end 
				
				Activity activity = getActivityData(accountVO, membershipUserLst.get(0), savedSignupList.get(0));
				interactionDao.save(activity);
				//Interaction interaction = interactionDao.save(populateInteractionData(savedSignupList.get(0), account, userDb));
		        model.addAttribute("account", account);    	            	        
		        model.addAttribute("primaryUser", primaryContact);
		          	    	        
		        model.addAttribute("signup", savedSignupList.get(0));
		        model.addAttribute("product", itemDetail);
		        model.addAttribute("paymentTransId", account.getTransId());
		        model.addAttribute("prodJoinFee", account.getJoiningFee());
		        model.addAttribute("productPrice", account.getProductPrice());		        
		        /*List<User> kidsInfo = new ArrayList<User>();
		    	for(User usr : userLst){	    		
		    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
		    			model.addAttribute("secUser", usr);
		    		}
		    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
		    			model.addAttribute("thirdUser", usr);
		    		}
		    		if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
		    			kidsInfo.add(usr);
		    		}
		    		   		
		    	}*/  
		        List<User> kidsInfo = new ArrayList<User>();
				List<User> adultUserLstInfo = new ArrayList<User>();
				boolean isSecUser = false;
				boolean isPrimaryUser = false;
				//boolean isThirdUser = false;
				/*for(User usr : userLst){	    
					if(isAdultByDob(usr.getDateOfBirth()) || isYouthByDob(usr.getDateOfBirth())){						
						if(!isPrimaryUser){
							model.addAttribute("primaryUser", usr);
							isPrimaryUser = true;
						}else if(!isSecUser){
							model.addAttribute("secUser", usr);
							isSecUser = true;
						}else{
							model.addAttribute("thirdUser", usr);
						}
						adultUserLstInfo.add(usr);
	        		}else{
	        			kidsInfo.add(usr);
	        		}   							
				}
				model.addAttribute("kidsInfo", kidsInfo); */
				
				for(Signup sign : savedSignupList){
    				if(sign != null && sign.getContact() != null && StringUtils.isNotBlank(sign.getMembershipAgeCategory())){
    					if(sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Adult.toString()) || sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Youth.toString())){
    						if(sign.getPaymentMethod() != null){
        						model.addAttribute("primaryUser", sign.getContact());
        						isPrimaryUser = true;
        					}else if(!isSecUser){
        						model.addAttribute("secUser", sign.getContact());
        						isSecUser = true;
        					}else{
        						model.addAttribute("thirdUser", sign.getContact());
        					}
        					adultUserLstInfo.add(sign.getContact());
    					}else if(sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Kid.toString()) ){
        					kidsInfo.add(sign.getContact());
        				}else{
        					kidsInfo.add(sign.getContact());
        				} 
    				}
    			}
    			model.addAttribute("kidsInfo", kidsInfo);
    			model.addAttribute("isYouthUserAdult", isYouthUserAdult);
		    	return new ModelAndView("becomeMemberconfirmation", model.asMap());
    		}catch(Exception e){
    			log.error("Error  ",e);    	        	
	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
				return new ModelAndView("login", model.asMap());
    		}
    	}
		            
		        /*} catch (Exception ade) {
		        	log.error("Error  ",e);    	        	
		        	model.addAttribute("errMsg", "Error Occured in Registration Process");
					return new ModelAndView("login", model.asMap());
		        }
			}else{
				return new ModelAndView("login", model.asMap());
			}
    	}*/
		
    	/*if(account != null && !account.getLoggedInUserEmailId().equals("") ){
    			Account isAccountExist = accountDao.getByEmail(account.getLoggedInUserEmailId());    		
	    		if(isAccountExist != null){
	    			List<User> userLstForm = new ArrayList<User>();
	    			User userDb = getUserByAccount(isAccountExist, user);
	    			signup = populateSignupData(isAccountExist, userDb, itemDetails,signup,  opptyId, null);  
	    			try {   
	    				userLst = populateExistingSignedUpUserLstData(account, isAccountExist); 
	    				userLstForm = populateUsrLstFromUsrSet(account);
	        			Model model = new ExtendedModelMap();
	        			if(userLst != null && !userLst.isEmpty()){
	    	    			//User primaryUser = userLst.get(0);
	    	    	    	populateContactAndCustomerWaiversAndTC(userDb, account);  	    	  
	    	    	    	//userLst.add(user);
	    	    	    	account.setUser(new HashSet<User>());
	    	    	    	
	    	    	    	List<User> kidsInfo = new ArrayList<User>();
	    	    	    	for(User usr : userLst){
	    	    	    		account.getUser().add(usr); 	    	    	    		   		
	    	    	    	} 
	    	    	    	for(User usr : userLstForm){
	    	    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
	    	    	    			model.addAttribute("secUser", usr);
	    	    	    		}
	    	    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
	    	    	    			model.addAttribute("thirdUser", usr);
	    	    	    		}
	    	    	    		if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
	    	    	    			kidsInfo.add(usr);
	    	    	    		}
	    	    	    	}
	    	    	    	account.setEmail(userDb.getPrimaryEmailAddress());
	    	    	    	account.setName(userDb.getFullName());  	    	
	    	    	    	  
	    	    	        try {              	
	    	    	            Account acc = accountDao.saveAndFlush(account);    	              
	    	    	            JetPayPayment jetPayPayment = new JetPayPayment();
	    	    	            PaymentMethod paymentMethod  =  populatePaymentMethodData(acc, account.getTransId());
	    	    	            paymentMethod = paymentMethodDao.save(paymentMethod);
	    	    	            //signup = populateSignupData(account, primaryUser, itemDetails,signup,  opptyId, paymentMethod);
	    	    	            List<Signup> signupList =  populateSignupDataLst(account, userLst, itemDetails,  opptyId, paymentMethod);
	    	    	            for(Signup signupSave : signupList){
	    	    	            	Signup signup2 = signupDao.save(signupSave);  
	    	    	            }         
	    	    	            
	    	    	            User userTemp = new User();	    	    	            
	    		    			List<String> amountLst =  new ArrayList<String>();
	    		    			amountLst.add("");
	    		    			amountLst.add("");
	    		    			amountLst.add(account.getProductPrice());
	    		    			JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getPaymentId().toString(), "", account.getTransId(), acc, amountLst, userDb, signupList.get(0), ProductTypeEnum.Membership,null);
	    		    			
	    	    	            
	    	    	    		if(acc != null){	    		    	    	
	    	    	    	        model.addAttribute("account", account);    	            	        
	    	    	    	        model.addAttribute("primaryUser", userDb);
	    	    	    	        model.addAttribute("kidsInfo", kidsInfo);    	    	        
	    	    	    	        model.addAttribute("signup", signupList.get(0));
	    	    	    	        model.addAttribute("product", itemDetails);
	    	    	    	        model.addAttribute("paymentTransId", account.getTransId());
	    	    	    	        model.addAttribute("prodJoinFee", account.getJoiningFee());
	    	    	    	        model.addAttribute("productPrice", account.getProductPrice());
	    	    	    	        if(account.getJoiningFee() != null && account.getProductPrice() != null){
	    	    	    	        	double prodJoinFee = Double.parseDouble(account.getJoiningFee());
	    	    	    	        	double prodPrice = Double.parseDouble(account.getProductPrice());
	    	    	    	        	double totalPrice = prodJoinFee+ prodPrice;
	    	    	    	        	model.addAttribute("productTotalPrice", totalPrice);
	    	    	    	        }
	    	    	    		}
	    	    	        
	    	    	    		return new ModelAndView("becomeMemberconfirmation", model.asMap());
	    	    	            
	    	    	        } catch (Exception ade) {
	    	    	        	log.error("Error  ",ade);      	
	    	    	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
	    	    				return new ModelAndView("login", model.asMap());
	    	    	        }
	        			}else{
	        				return new ModelAndView("login", model.asMap());
	        			}
	    	            
	    	        } catch (Exception ade) {
	    	        	log.error("Error  ",e);
	    	        	Model model = new ExtendedModelMap();
	    	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
	    				return new ModelAndView("login", model.asMap());
	    	        }
	    		}else{
	    			Model model = new ExtendedModelMap();
    	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
	    			return new ModelAndView("login", model.asMap());
	    		}
    	}else{*/
    			
    		//}   	
    }
    
    /*private Interaction populateInteractionData(Signup signup, Account account,	User userDb) {
    	Interaction interaction = new Interaction();
		interaction.setSignup(signup);
		interaction.setCustomer(signup.getCustomer());
		interaction.setType(Constants.NEW_MEMBERSHIP_REQUEST);
		interaction.setContact(signup.getContact());
		interaction.setCreatedDate(new Date());
		interaction.setDescription(Constants.NEW_MEMBERSHIP_REQUEST);	
		return interaction;
	}*/

	/*private boolean isContactSavedInDb(User contact, User u) {
		if(!StringUtils.isBlank(contact.getFirstName()) && !StringUtils.isBlank(u.getFirstName()) && !StringUtils.isBlank(contact.getLastName()) && !StringUtils.isBlank(u.getLastName()) && !StringUtils.isBlank(contact.getFormattedPhoneNumber()) && !StringUtils.isBlank(u.getFormattedPhoneNumber()) && contact.getDateOfBirth() != null && u.getDateOfBirth() != null){
			if(contact.getFirstName().equalsIgnoreCase(u.getFirstName()) && contact.getLastName().equalsIgnoreCase(u.getLastName()) && contact.getFormattedPhoneNumber().equals(u.getFormattedPhoneNumber()) && contact.getDateOfBirth().equals(u.getDateOfBirth())){
				return true;
			}
		}else{
			return false;
		}		
		return false;
	}*/

	private boolean isYouthMemberAdult(ItemDetail itemDetail, User user) {
		if(itemDetail != null && user != null && StringUtils.isNotBlank(itemDetail.getRecordName()) 
				&& itemDetail.getRecordName().equals(MembershipAgeCategoryEnum.Youth.toString()) 
				&& isAdultByDob(user.getDateOfBirth())){
			return true;
		}else{
			return false;
		}		
	}

	private PaymentMethod populatePaymentMethodData(Account account, String orderNumber) {
    	JetPayPayment jetPayPayment = null;
		if(!"".equals(orderNumber)){
			//jetPayPayment = jetPayPaymentDao.getByJpReturnHash(jp_request_hash);
			//jetPayPayment = jetPayPaymentDao.getByOrderNumber("968787959410");
			jetPayPayment = jetPayPaymentDao.getByOrderNumber(orderNumber);
		}
    	PaymentMethod paymentMethod = new PaymentMethod();
    	paymentMethod.setPaymentMethodType(PaymentMethodTypeEnum.CREDIT.toString());
    	paymentMethod.setPortalStatus(PortalStatusEnum.ACTIVE.toString());
    	paymentMethod.setTransId(jetPayPayment.getTransId());
    	paymentMethod.setCardNumber(Constants.CARD_MASKED_FIRST_DIGITS+jetPayPayment.getCardNum());
    	paymentMethod.setCardType(jetPayPayment.getCard());    	
    	paymentMethod.setFullName(jetPayPayment.getName());
    	paymentMethod.setTokenNumber(jetPayPayment.getCcToken());
    	paymentMethod.setOldToken(jetPayPayment.getOldToken());
    	paymentMethod.setOrderNumber(jetPayPayment.getOrderNumber());
    	paymentMethod.setBillingAddressLine1(jetPayPayment.getBillingAddress1());
    	paymentMethod.setBillingAddressLine2(jetPayPayment.getBillingAddress2());
    	paymentMethod.setBillingCity(jetPayPayment.getBillingCity());
    	paymentMethod.setBillingState(jetPayPayment.getBillingState());
    	paymentMethod.setBillingZip(jetPayPayment.getBillingZip());
    	
    	paymentMethod.setBillingCountry(jetPayPayment.getBillingCountry());    	
    	paymentMethod.setExpirationMonth(account.getExpirationMonth());
    	paymentMethod.setExpirationYear(account.getExpirationYear());
    	paymentMethod.setNickName(account.getNickName());
    	paymentMethod.setCustomer(accountDao.findByAccountId(account.getAccountId()));
    	Calendar cal = Calendar.getInstance();
    	paymentMethod.setLastUpdated(cal);
    	
		return paymentMethod;
	}

	
    
    /*private Signup populateSignupData(Account account, User contact, ItemDetail item, Signup signup, String opptyId, PaymentMethod paymentMethod) {
    	if(account.getSignup() != null && !account.getSignup().isEmpty()){
    		signup = account.getSignup().get(0);
    	}    	
    	signup.setContact(contact);
    	signup.setCustomer(account);
    	signup.setItemDetail(item);
    	signup.setPaymentMethod(paymentMethod);
    	signup.setContactName(contact.getFullName());
    	signup.setType(ProductTypeEnum.MEMBERSHIP.toString());
    	signup.setStatus(SignupStatusEnum.Active.toString());
    	signup.setOpptyId(opptyId);
    	signup.setSignUpProductType(account.getSignUpProductType());
    	signup.setSignupDate(new Date());   
    	if(account.getMembershipFrequency() != null && "Annual".equals(account.getMembershipFrequency() )){
    		Calendar cal = Calendar.getInstance();    		
    		cal.add(Calendar.YEAR, 1); // to get previous year add -1
    		signup.setProgramEndDate(cal.getTime());
    	}
    	signup.setProgramStartDate(new Date());
    	if(account.getProductPrice() != null){
    		signup.setFinalAmount(account.getProductPrice());
    	}else{
    		signup.setFinalAmount("0");
    	}
    	if(account.getMembershipFrequency() != null && "Monthly".equals(account.getMembershipFrequency() )){
    		Calendar cal = Calendar.getInstance();    		
    		cal.add(Calendar.MONTH, 1); // to get previous year add -1
    		signup.setMembersshipFeeNextBillingDate(cal.getTime());    		
    	}
    	
    	
    	return signup;		
	}*/

	private User populateUserData(Account account) { 
    	User user = new User();
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();	
    	user = account.getUserLst().get(0);
    	if(account != null  && StringUtils.isBlank(getAgentUidFromSession())){
    		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    	}
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
		//user.setEthnicity(account.getEthnicity());  
	    user.setEmployer(account.getEmployer());
    	//user.setDateOfBirth(new Date());	
		Address address =  new Address();
		address.setPrimaryAddressLine1(account.getAddressLine1());
		address.setPrimaryAddressLine2(account.getAddressLine2());
		address.setPrimaryAddressCity(account.getCity());
		address.setPrimaryAddressProvince(account.getState());
		address.setPrimaryAddressPostalCode(account.getPostalCode());			
		address.setPrimaryAddressCountry(Constants.COUNTRY_USA);	
		user.setAddress(address);
    	//System.out.println("new user data populated");
		if(user.getGender().equals("Male")){
			user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
		}else{
			user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
		}
	    
    	return user;
	}
	
	private List<User> populateUserLstData(Account account) { 
		List<User> userLst =  new ArrayList<User>();
		userLst = account.getUserLst();		
		for(User usr : userLst){
			usr.setEnabled(true);	
			usr.setAccountExpired(false);
			usr.setAccountLocked(false);
			usr.setCredentialsExpired(false);
			usr.setEnabled(true);
			usr.setActive(true);
			usr.setPrimary(false); 
			usr.setMember(true);
			usr.setfNameAndLName(usr.getFullName());
			Location location = new Location();
			if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
				location = locationDao.getOne(Long.parseLong(account.getLocationId()));
			}
			/*location.setLocationId(Long.parseLong(account.getLocationId()));*/
			usr.setLocation(location);
			if(StringUtils.isNotBlank(usr.getGender()) && usr.getGender().equals("Male")){
				usr.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
			}else{
				usr.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
			}
	    	//user.setDateOfBirth(new Date());	
			Address address =  new Address();
			address.setPrimaryAddressLine1(account.getAddressLine1());
			address.setPrimaryAddressLine2(account.getAddressLine2());
			address.setPrimaryAddressCity(account.getCity());
			address.setPrimaryAddressProvince(account.getState());
			address.setPrimaryAddressPostalCode(account.getPostalCode());			
			address.setPrimaryAddressCountry(Constants.COUNTRY_USA);			
			Calendar cal = Calendar.getInstance();
			usr.setLastUpdated(cal);
			usr.setAddress(address);
		}		
    	return userLst;
	}
	
	/*private List<User> populateExistingSignedUpUserLstData(Account account, Account accountFromDb) { 
		List<User> userLst =  new ArrayList<User>();
		List<User> userLstFiltered =  new ArrayList<User>();
		userLst = account.getUserLst();
		for(User usr : userLst){
			if((usr.getIsAdult().equals("true") && usr.getPrimaryEmailAddress() != null)){				
				List<User> userExist = userDao.getUserLstByPrimaryEmailAddress(usr.getPrimaryEmailAddress());
				if(userExist != null && userExist.size() == 0){
					usr.setEnabled(true);	
					usr.setAccountExpired(false);
					usr.setAccountLocked(false);
					usr.setCredentialsExpired(false);
					usr.setEnabled(true);
					usr.setActive(true);
					usr.setPrimary(false); 
					usr.setMember(true);
					Location location = new Location();
					if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
						location = locationDao.getOne(Long.parseLong(account.getLocationId()));
					}
					usr.setLocation(location);
			    	//user.setDateOfBirth(new Date());	
					Address address =  new Address();
					address.setPrimaryAddressLine1(account.getAddressLine1());
					address.setPrimaryAddressLine2(account.getAddressLine2());
					address.setPrimaryAddressCity(account.getCity());
					address.setPrimaryAddressProvince(account.getState());
					address.setPrimaryAddressPostalCode(account.getPostalCode());			
					address.setPrimaryAddressCountry("USA");
					usr.setAddress(address);
					userLstFiltered.add(usr);
				}
			}else{
				List<User> userExist = userDao.getByFirstNameAndLastName(usr.getPersonFirstName(), usr.getPersonLastName());
				if(userExist != null  && userExist.size() == 0){
					usr.setEnabled(true);	
					usr.setAccountExpired(false);
					usr.setAccountLocked(false);
					usr.setCredentialsExpired(false);
					usr.setEnabled(true);
					usr.setActive(true);
					usr.setPrimary(false); 
					usr.setMember(true);
					Location location = new Location();
					if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
						location = locationDao.getOne(Long.parseLong(account.getLocationId()));
					}
					usr.setLocation(location);
			    	//user.setDateOfBirth(new Date());	
					Address address =  new Address();
					address.setPrimaryAddressLine1(account.getAddressLine1());
					address.setPrimaryAddressLine2(account.getAddressLine2());
					address.setPrimaryAddressCity(account.getCity());
					address.setPrimaryAddressProvince(account.getState());
					address.setPrimaryAddressPostalCode(account.getPostalCode());			
					address.setPrimaryAddressCountry("USA");
					usr.setAddress(address);
					userLstFiltered.add(usr);
				}
			}
		}			
    	return userLstFiltered;
	}
	
	private List<User> populateUserLstRegister(Account account) { 
		List<User> userLst =  new ArrayList<User>();
		//userLst = account.getUser();
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		for(User usr : account.getUser()){
			usr.setEnabled(true);	
			usr.setAccountExpired(false);
			usr.setAccountLocked(false);
			usr.setCredentialsExpired(false);
			usr.setEnabled(true);
			usr.setActive(true);
			usr.setPrimary(false); 
			usr.setMember(false);
			Location location = new Location();
			if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
				location = locationDao.getOne(Long.parseLong(account.getLocationId()));
			}
			location.setLocationId(Long.parseLong(account.getLocationId()));
			usr.setLocation(location);
	    	//user.setDateOfBirth(new Date());
			userLst.add(usr);
		
		}
		if(userLst != null && !userLst.isEmpty()){
			userLst.get(0).setPassword(bCryptPasswordEncoder.encode(userLst.get(0).getPassword()));
			userLst.get(0).setPrimary(true);
			userLst.get(0).setMember(true);
			
		}
    	return userLst;
	}
	
	private List<User> populateUsrLstFromUsrSet(Account account) { 
		List<User> userLst =  new ArrayList<User>();		
		for(User usr : account.getUser()){			
			userLst.add(usr);
		
		}	
    	return userLst;
	}*/

	/*private void populateMembershipData(Account account, User user) {
		Membership membership = new Membership();
		membership = account.getMembership();
		if(account.getUserLst() != null && !account.getUserLst().isEmpty()){
			membership.setContactName(account.getUserLst().get(0).getFullName());
		}
		membership.setMembershipBeginDate(new Date());
		membership.setMembershipEndDate(new Date());
		account.setMembership(membership);
	}*/
	
	private Activity getActivityData(Account account, User contact, Signup signup) {	
		Activity activity = new Activity();
		if(account!=null && contact!=null){	
			activity.setType(com.ymca.web.util.Constants.NEW_MEMBERSHIP_REQUEST);
			activity.setDescription(com.ymca.web.util.Constants.NEW_MEMBERSHIP_REQUEST);
			// get current time stamp
			java.util.Date date= new java.util.Date();
	 		activity.setCreatedDate(new Timestamp(date.getTime()));
	 		activity.setCustomer(account);
	 		activity.setContact(contact);
	 		activity.setSignup(signup);
	 		activity.setShowOnPortal(true);	  	
	 		Calendar cal = Calendar.getInstance();
	 		activity.setLastUpdated(cal);
    	}
		return activity;
	}

	private void populateContactAndCustomerWaiversAndTC(User user, Account account) {
		ContactWaiversAndTC tAncC = new ContactWaiversAndTC();
		if(account.getUserLst() != null && !account.getUserLst().isEmpty()){
			tAncC.setRecordName(account.getUserLst().get(0).getFullName());
		}
		tAncC.setTcDescription(account.getTcDescription());
		tAncC.setContact(user);
		tAncC.setCustomer(account);
		//tAncC.setContactPartyId(443L);
		//tAncC.setCustomerPartyId(665L);		
		user.setContactWaiversAndTC(new HashSet<ContactWaiversAndTC>());	
		user.getContactWaiversAndTC().add(tAncC);		
		////System.out.println("TNC set nw user");
	}
	@RequestMapping(value= "view_membership", method = RequestMethod.GET)
    public ModelAndView showMembershipInfo(final HttpServletRequest request, final HttpServletResponse response) {
		//HttpSession session = request.getSession();
		Model model = new ExtendedModelMap();
		try{			
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String userId = null;
			//Interaction interaction=null;
			try{
				userId = ((UserDetails) a.getPrincipal()).getUsername();
			}catch(Exception e){
				model.addAttribute("errMsg", "Your session is expired");
				return new ModelAndView("login", model.asMap());
			}
			
	    	Account account = null;
	    	User user =  null;	
	    	if(userId != null && !"".equals(userId)){
		    	account = accountDao.getByEmail(userId);
				request.setAttribute("userId", userId);				
				user = getUserByAccount(account, user);
				if(user.getDateOfBirth() !=  null){
					MemberAge age =  new MemberAge();
					user.setAge(age.getAge(user.getDateOfBirth()));
				}				
	    	}
	    	
	    	Signup primarySignup = null;
	    	Map<String, String> signupIdMap =  new HashMap<String, String>();
	    	for(Signup signup : account.getSignup()){
	    		if(signup != null && signup.getContact() != null && signup.getContact().getContactId() != null){
	    			signupIdMap.put(String.valueOf(signup.getContact().getContactId()), String.valueOf(signup.getStatus()));
	    			if(signup.getPaymentMethod() !=  null){
	    				primarySignup = signup;
	    			}
	    		}	    		
	    	}
			
	    	List<Signup> signupLst = signupDao.getByCustomerAndTypeAndStatusAndPaymentMethodNotNull(account, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
	    	
			if(account != null){	    		    	    	
		        model.addAttribute("accInfo", account);
		        model.addAttribute("usInfo", user);		
		        if(signupLst != null && !signupLst.isEmpty()){
		        	model.addAttribute("primarySignupRec", signupLst.get(0));
		        }
		        
		        
		        //int userCount = account.getUser().size();
		        List<User> userS = new ArrayList<User>();	        
		        //List<User> userLst = userDao.getByCustomer(account);
		        
		        List<User> userLst =  new ArrayList<User>();
				List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
				for(AccountContact ac : accountContactLst){
					if(ac.getEndDate() == null){
						userLst.add(ac.getContact());
					}				
				}
		        int userCount = userLst.size();
		        int countmembers = 0;
		        if(userCount>1){
			        for(User u: userLst){
			        	if(user.getContactId() != u.getContactId()){
			        		if(signupIdMap.containsKey(String.valueOf(u.getContactId())) && signupIdMap.get(String.valueOf(u.getContactId())).toString().equals(SignupStatusEnum.Active.toString())){
			        			u.setSignupId(signupIdMap.get(String.valueOf(u.getContactId())));
			        		}
				        	
				        	if(isChildByDob(u.getDateOfBirth())){
				        		countmembers = countmembers + 1;			        		
			        			u.setIsKid("true");
			        			userS.add(u);
				        	}else {
				        		countmembers = countmembers + 1;
				        		userS.add(u);
				        	}
				        	MemberAge mAge = new MemberAge();
							Integer contactAge  = mAge.getAge(u.getDateOfBirth());
							List<SystemProperty> contactRemoveAge = new ArrayList<SystemProperty>();			
					    	contactRemoveAge = systemPropertyDao.getPropertyByKeyName(Constants.KIDS_REMOVE_AGE_VALIDATION);
							if(contactRemoveAge != null && !contactRemoveAge.isEmpty()){
								Integer removeAge = 18;
								removeAge = Integer.parseInt(contactRemoveAge.get(0).getKeyValue());
								if(contactAge >= removeAge){
									u.setIsAdult("true");
								}else{
									u.setIsAdult("false");
								}
							}								
			        	}
			        	//if(!user.getUsername().equalsIgnoreCase(u.getUsername()) && u.isActive()){
			        	/*if(user != null && u != null && user.getEmailAddress() != null && u.getEmailAddress() != null 
			        			&& !user.getEmailAddress().equalsIgnoreCase(u.getEmailAddress()) && u.isActive()){
			        		
			        		countmembers = countmembers + 1;
			        		if(signupIdMap.containsKey(String.valueOf(u.getContactId())) && signupIdMap.get(String.valueOf(u.getContactId())).toString().equals(SignupStatusEnum.ACTIVE.toString())){
			        			u.setSignupId(signupIdMap.get(String.valueOf(u.getContactId())));
			        		}			        			        		
			        		userS.add(u);
			        		
			        	}else if(user != null && u != null && user.getEmailAddress() != null && u.getEmailAddress() != null && user.getEmailAddress().equalsIgnoreCase(u.getEmailAddress())){
			        		//Dont add the primary user to the user List
			        	}else{			        
			        		if(isChildByDob(u.getDateOfBirth())){			        			
			        			countmembers = countmembers + 1;
			        			if(signupIdMap.containsKey(String.valueOf(u.getContactId())) && signupIdMap.get(String.valueOf(u.getContactId())).toString().equals(SignupStatusEnum.ACTIVE.toString())){
				        			u.setSignupId(signupIdMap.get(String.valueOf(u.getContactId())));
				        		}
			        			u.setIsKid("true");
			        			userS.add(u);
			        		}else{
			        			userS.add(u);
			        		}
			        		
			        	}*/
			        }
				}
		        
		        
		        
		        
		        model.addAttribute("userCount", countmembers);
		        model.addAttribute("userS", userS);
		        model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
		        model.addAttribute("volunteerActivity", getVolunteerActivity());
		        List<WaiversAndTC> waiversAndTCLst = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		        if(waiversAndTCLst != null && !waiversAndTCLst.isEmpty()){
		        	model.addAttribute("waiversTAndC", waiversAndTCLst.get(0));
		        }	
		       /* String cancelledMember = "false";
		        if(account.getInteractions()!=null && account.getInteractions().size()>0){
			        for(Interaction interaction: account.getInteractions()){
			        	if(interaction.getType().equalsIgnoreCase(com.ymca.web.util.Constants.INTERACTION_TYPE.Request_Membership_Cancellation.toString())){
			        		cancelledMember = "true";
			        		break;
			        	}
			        }
		        }
		       */
		       // model.addAttribute("cancelled_Member", cancelledMember);
		        model.addAttribute("account", new Account());
		        model.addAttribute("msg1", "You will recieve an Email for further instrutions.");
		        model.addAttribute("msg2", "You have already requested for FA..");
		        List<SystemProperty> ethnicityLst = systemPropertyDao.getByPicklistName(Constants.EthnicityPicklistName);
		        if(ethnicityLst != null && !ethnicityLst.isEmpty()){
		        	model.addAttribute("ethnicityLst", ethnicityLst);
		        }
				return new ModelAndView("myProfileView", model.asMap());
			}else {
				model.addAttribute("errMsg", "Please Login");
				return new ModelAndView("login", model.asMap());
				
			}	
		}catch(Exception e){
			model.addAttribute("errMsg", "Error Occured"+e);
			log.error("Error  ",e);
			return new ModelAndView("login", model.asMap());
		}
	
	}
	
	@RequestMapping(value="update_membership", method = RequestMethod.POST)
    public @ResponseBody String updateMembership(Account account, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();	
    	Account accountVO = null;
    	User user =  null;
    	User primaryUser = null;
    	Signup primarySignup = null; 
    	
    	if(StringUtils.isBlank(account.getUsrId().toString())){
    		return null;
    	}
    	
    	if(userId != null && !"".equals(userId)){
    		accountVO = accountDao.getByEmail(userId);
    		primaryUser = getUserByAccount(accountVO, primaryUser);
    		for(Signup signup : accountVO.getSignup()){
    			if(signup != null && signup.getContact() != null && signup.getContact().getContactId() != null){    			
    				if(signup.getPaymentMethod() !=  null){
    					primarySignup = signup;
    				}
    			}	    		
    		}
    		List<User> userLst =  new ArrayList<User>();
    		List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(accountVO);
			for(AccountContact ac : accountContactLst){
				userLst.add(ac.getContact());
			}
			request.setAttribute("userId", userId);				
			//user = getUserByAccount(accountVO, user);
			for(User u: userLst){
				 if(u.getContactId()!=null && account.getUsrId().toString().trim().equalsIgnoreCase(u.getContactId().toString())){
					 user = u;
					 break;
				 }
			 }
    	}
    	boolean isEmailUpdated =  false;
    	if(account != null && accountVO != null && StringUtils.isNotBlank(account.getEmail()) && accountVO.getEmail() != null ){    		
			try{
				 User tempUser =  userDao.getByEmailAddress(account.getEmail());
				 if(tempUser != null && primaryUser.getContactId().toString().equals(account.getUsrId().toString().trim()) 
						 && StringUtils.isNotBlank(primaryUser.getEmailAddress()) && StringUtils.isNotBlank(account.getEmail()) && !account.getEmail().equals(primaryUser.getEmailAddress())){    					 
     	    		 return "PRIMARY_EMAIL_EXISTS";
				 }else if(tempUser != null && !primaryUser.getContactId().toString().equals(account.getUsrId().toString().trim())){  				 
     	    		 return "SECONDARY_EMAIL_EXISTS";
				 }    				 
			 }catch(Exception e){    				 
 	    		 return "ERROR";
			 }   
			if(!account.getEmail().trim().equals(accountVO.getEmail())){
    			isEmailUpdated = true;
    		}else{
    			isEmailUpdated = false;
    		}
    	}
    	if(user==null){
    		return null;
    	}else if(account.getEmail() != null && account.getEmail().trim().equals("") && primaryUser.getContactId().toString().equals(account.getUsrId().toString().trim())){
    		return "PRIMARY-USER-EMAIL-UPTATE-ERROR";
    	}else {
	    	populateAccountUserData(account, accountVO, user);	
	    	
	    	try{
	    		Account updAccount =  accountDao.save(accountVO);
	    		if(primarySignup != null && StringUtils.isNotBlank(account.getAddToMembership()) && "Yes".equals(account.getAddToMembership())){
	    			List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(user.getFullName(), accountVO, user, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
	    			if(signupLst == null || signupLst.isEmpty()){
	    				Signup saveSignup = populateSignupAddChild(user, primarySignup);
	        			signupDao.save(saveSignup);
	    			} else{
	    				signupLst.get(0).setStatus(SignupStatusEnum.Active.toString());
	    				signupDao.save(signupLst.get(0));
	    			}
	    		}else if(StringUtils.isNotBlank(account.getAddToMembership()) && "No".equals(account.getAddToMembership())){
	    			List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(user.getFullName(), accountVO, user, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());	    			
	    			if(signupLst != null && !signupLst.isEmpty()){
	    				for(Signup signup : signupLst){
	    					signup.setStatus(SignupStatusEnum.Inactive.toString());
		    				signupDao.save(signup);
	    				}	    				
	    			}
	    		}
	    		if(isEmailUpdated){
	    			return "LOGIN";
	    		}else{
	    			return "SUCCESS";
	    		}   		
	    	}catch(Exception e){    		
	    		return "ERROR";
	    	}
    	}
    }
	 
	 @RequestMapping(value= "view_member", method = RequestMethod.GET)
	 public @ResponseBody User viewMember(final HttpServletRequest request, final HttpServletResponse response) {
		 String member_id = request.getParameter("member_id");
		 if(member_id==null || "".equals(member_id)){
			 return null;
		 }
		 
		 Authentication a = SecurityContextHolder.getContext().getAuthentication();
		 String userId = ((UserDetails) a.getPrincipal()).getUsername();	
		 User user = null;
		 Account account = null;
		 
		 if(userId != null && !"".equals(userId)){
			 account = accountDao.getByEmail(userId);	
			 
			 List<User> userLst =  new ArrayList<User>();
			List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
			for(AccountContact ac : accountContactLst){
				userLst.add(ac.getContact());
			}
			 for(User u: userLst){
				 if(u.getContactId()!=null && member_id.trim().equalsIgnoreCase(u.getContactId().toString())){
					 user = u;
					 break;
				 }
			 }
			 
			 if(user!=null){
				 User retUser = new User();
				 retUser.setFirstName(user.getFirstName());
				 retUser.setLastName(user.getLastName());
				 retUser.setFormattedPhoneNumber(user.getFormattedPhoneNumber());
				 retUser.setEmailAddress(user.getEmailAddress());
				 retUser.setDateOfBirth(user.getDateOfBirth());
				 if(user.getDateOfBirth() != null){
					 retUser.setDateOfBirthStr(user.getDateOfBirth().toString());
				 }				 
				 //List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndStatus(user.getFullName(), account, user, SignupStatusEnum.Active.toString());
				 List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(user.getFullName(), account, user, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
				 account = new Account();
				 if(signupLst != null && !signupLst.isEmpty()){				 	
				 	account.setAddToMembership("Yes");
				 }else{
					account.setAddToMembership("No");
				 }
				 retUser.setCustomer(account);
				 if(user.getDateOfBirth() !=  null){
						MemberAge age =  new MemberAge();
						retUser.setAge(age.getAge(user.getDateOfBirth()));
				 }
				 retUser.setEmployer(user.getEmployer());
				 retUser.setGender(user.getGender());
				 retUser.setEthnicity(user.getEthnicity());
				 return retUser;
			 }
		 }
		 return null;
	 }
	 
	 
	@RequestMapping(value = "enrolled_programs", method = RequestMethod.GET)
	public @ResponseBody
	List<Signup> enrolledProgram(final HttpServletRequest request,
			final HttpServletResponse response) {
		String member_id = request.getParameter("member_id");
		if (member_id == null || "".equals(member_id)) {
			return null;
		}
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		User user = null;
		Account account = null;
		List<Signup> signup = null;

		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			for (User u : account.getUser()) {
				if (u.getPartyId() != null
						&& member_id.trim().equalsIgnoreCase(
								u.getPartyId().toString())) {
					user = u;
					signup = signupDao.findByContactNameAndTypeNotLike(user
							.getPartyId().toString(), "Memberships");

					break;
				}
			}

			if (signup != null) {

				return signup;
			}
		}

		return null;
	}
	
	 @RequestMapping(value= "remove_member", method = RequestMethod.GET)
	 public @ResponseBody String removeMember(final HttpServletRequest request, final HttpServletResponse response) {
		 String member_id = request.getParameter("member_id");
		 if(member_id==null || "".equals(member_id)){
			 return null;
		 }
		 
		 Authentication a = SecurityContextHolder.getContext().getAuthentication();
		 String userId = ((UserDetails) a.getPrincipal()).getUsername();	
		 User user = null;
		 Account account = null;
		 
		 if(userId != null && !"".equals(userId)){
			 account = accountDao.getByEmail(userId);	
			 if(account != null){
				 //List<User> userLst = userDao.getByCustomer(account);
				 List<User> userLst =  new ArrayList<User>();
				List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
				for(AccountContact ac : accountContactLst){
					userLst.add(ac.getContact());
				}
				 for(User u: userLst){
					 if(member_id != null && u.getContactId()!=null && member_id.trim().equalsIgnoreCase(u.getContactId().toString())){
						 user = u;
						 break;
					 }
				 }
				 
				 /*if(user!=null){
					 user.setActive(false);
					 userDao.save(user);
					 return "SUCCESS";
				 }*/
				 
				 List<AccountContact> updateAccountContactLst =  accountContactDao.getByCustomerAndContact(account, user);
				 for(AccountContact ac : updateAccountContactLst){
					 ac.setEndDate(new Date());
					 ac.setLastUpdated(Calendar.getInstance());
					 try{
						 accountContactDao.save(ac);
						 return "SUCCESS";
					 }catch(Exception e){
						 return null;
					 }
				 }
				 
				 
			 }else{
				 return null;
			 }
    	 }
		 return null;
	 }
	 @RequestMapping(value= "requestcancellation", method = RequestMethod.GET)
	 public ModelAndView requestCancellation(final HttpServletRequest request, final HttpServletResponse response) {
		 Model model = new ExtendedModelMap();
		 model.addAttribute("activity", new Activity());
		 List<WaiversAndTC> waiversAndTCLst = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		 if(waiversAndTCLst != null && !waiversAndTCLst.isEmpty()){
			 model.addAttribute("waiversTAndC", waiversAndTCLst.get(0));
		 }	
		 return new ModelAndView("requestcancellation", model.asMap());
	 }
	 
	@RequestMapping(value="requestcancellation", method = RequestMethod.POST)
	public ModelAndView requestCancellation1(@RequestParam(value="reason1") String reason1, @RequestParam(value="reason2") String reason2, 
			@RequestParam(value="reason3") String reason3, @RequestParam(value="reason4") String reason4, @RequestParam(value="reason5") String reason5, 
			@RequestParam(value="reason6") String reason6, @RequestParam(value="reasontext") String reasontext, @RequestParam(value="howtoimprove") String howtoimprove, 
			@RequestParam(value="otherjoined") String otherjoined, @RequestParam(value="otherreason") String otherreason, @RequestParam(value="otherreason1") String otherreason1,
			@RequestParam(value="whydissatisfied") String whydissatisfied, final HttpServletRequest request, final HttpServletResponse response) {	 
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		
		Model model = new ExtendedModelMap();
		//String reasontext = request.getParameter("reason");
		
		Account account = null;
    	User contact =  null;	
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);		
	    	contact = getUserByAccount(account, contact);
    	}    	
    	Activity activity = new Activity();
		if(account!=null && contact!=null && account.getSignup() != null){	
			activity.setType(com.ymca.web.util.Constants.Request_For_Cancellation);
			activity.setDescription(com.ymca.web.util.Constants.Request_For_Cancellation);
			// get current time stamp
			java.util.Date date= new java.util.Date();
	 		activity.setCreatedDate(new Timestamp(date.getTime()));
	 		activity.setCustomer(account);
	 		activity.setContact(contact);
	 		activity.setSignup(account.getSignup().get(0));
	 		activity.setShowOnPortal(false);	
	 	
	 		activity.setReason1(reason1);
	 		activity.setReason2(reason2);
	 		activity.setReason3(reason3);
	 		activity.setReason4(reason4);
	 		activity.setReason5(reason5);
	 		activity.setReason6(reason6);
	 		
	 		activity.setHowtoimprove(howtoimprove);
	 		activity.setOtherjoined(otherjoined);
	 		activity.setOtherreason(otherreason);
	 		activity.setOtherreason1(otherreason1);
	 		
	 		activity.setWhydissatisfied(whydissatisfied);
	 		activity.setLastUpdated(Calendar.getInstance());
	 		Activity activitySave =  interactionDao.save(activity);
	 		
	 		for(Signup signup : account.getSignup()){
	    		if(signup != null && signup.getMembersshipFeeNextBillingDate() != null){
	    			Calendar dob = Calendar.getInstance();  
	    			dob.setTime(signup.getMembersshipFeeNextBillingDate());
	    			dob.add(Calendar.DAY_OF_YEAR, -1);
	    			signup.setProgramEndDate(dob.getTime());
	    			signup.setLastUpdated(Calendar.getInstance());
	    			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
	    			signupDao.save(signup);    			
	    		}else{
	    			Calendar dob = Calendar.getInstance();    			
	    			signup.setProgramEndDate(dob.getTime());
	    			signup.setLastUpdated(Calendar.getInstance());
	    			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
	    			signupDao.save(signup);
	    		}
	    	}
	 		
	 		model.addAttribute("successMsg", com.ymca.web.util.Constants.REQUEST_MEMBERSHIP_SUCCESS);
    	}
		else{
    		model.addAttribute("errMsg", com.ymca.web.util.Constants.INTERNAL_ERROR);
    	}   	
    	return new ModelAndView("requestcancellation", model.asMap());
	 }
	
	 @RequestMapping(value= "receivecancellation", method = RequestMethod.GET)
	 public ModelAndView receiveCancellation(final HttpServletRequest request, final HttpServletResponse response) {
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();		
		Account account = null;		
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
		}
		String success = request.getParameter("success");
		 String err = request.getParameter("err");
		 Model model = new ExtendedModelMap();
		 
		 if(success!=null && success.equals("1")){			
			 if(account != null && account.getSignup() != null && !account.getSignup().isEmpty()){
				 Calendar dob = Calendar.getInstance();  
				dob.setTime(account.getSignup().get(0).getProgramEndDate());
				int month =  dob.get(Calendar.MONTH);
				int date  = dob.get(Calendar.DAY_OF_MONTH);
				int year = dob.get(Calendar.YEAR);
				String formattedCancelledSignupDate = ++month  + "/" + date +"/" + year;
				
				 model.addAttribute("successMsg", "Your membership will be cancelled on " + formattedCancelledSignupDate +". You can use your membership until then.");		
		     }
			 
		 }
		 if(err!=null && err.equals("1")){
			 model.addAttribute("errMsg", com.ymca.web.util.Constants.INTERNAL_ERROR);
		 }
		 return new ModelAndView("receivecancellation", model.asMap());
	 }
	 
	private void populateAccountUserData(Account account, Account accountVO, User user) {
	
		accountVO.setAddressLine1(account.getAddressLine1());
		accountVO.setAddressLine2(account.getAddressLine2());
		accountVO.setCity(account.getCity());
		accountVO.setState(account.getState());
		//accountVO.setCountry(account.getCountry());
		if(user != null && user.isPrimary()){
			accountVO.setEmail(account.getEmail());
		}		
		accountVO.setLastUpdated(Calendar.getInstance());
		
		//user.setUsername(account.getUsername());
    	//user.setPassword(account.getPassword());     
    	user.setFirstName(account.getFirstName());
    	user.setLastName(account.getLastName());
    	user.setEmailAddress(account.getEmail());    	
    	user.setFormattedPhoneNumber(account.getPhoneNumber());
    	user.setDateOfBirth(account.getDateOfBirth());
    	user.setIsAdult(account.getIsAdult());
    	//user.setId(account.getUsrId());
    	user.setEnabled(true);	
    	user.setLastUpdated(Calendar.getInstance());
    	user.setEmployer(account.getEmployer());
    	user.setGender(account.getGender());    	
    	user.setEthnicity(account.getEthnicity());  
	    
    	//user.setFormattedPhoneNumber(account.getWorkNumber());
    	//user.setContactPointPurpose(account.getHomeNumber());
    	
    	// TODO fixed this
    	//accountVO.getMembership().setContactName(user.getFullName());
    	//accountVO.getMembership().setMembershipBeginDate(new Date());
    	//accountVO.getMembership().setMembershipEndDate(new Date());
		
		/*for (Iterator<ContactWaiversAndTC> it = user.getContactWaiversAndTC().iterator(); it.hasNext(); ) {
			ContactWaiversAndTC tAncC = it.next();
			tAncC.setRecordName(user.getFullName());		
	    }*/
	}	 
	
	@RequestMapping(value= "addMember", method = RequestMethod.GET)
	public ModelAndView addMember(final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		//Interaction interaction=null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			model.addAttribute("errMsg", "Your session is expired");
			return new ModelAndView("login", model.asMap());
		}
		
    	Account account = null;
    	User user =  null;	
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);			
    	}    	
    	Signup primarySignup = null;    	
    	for(Signup signup : account.getSignup()){
    		if(signup != null && signup.getContact() != null && signup.getContact().getContactId() != null){    			
    			if(signup.getPaymentMethod() !=  null){
    				primarySignup = signup;
    			}
    		}	    		
    	}    	
    	List<Signup> signupLst = signupDao.getByCustomerAndTypeAndStatusAndPaymentMethodNotNull(account, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());			
        if(signupLst != null && !signupLst.isEmpty()){
        	model.addAttribute("primarySignupRec", signupLst.get(0));
        }
    	//model.addAttribute("primarySignupRec", primarySignup); 
		model.addAttribute("account", new Account()); 
		List<SystemProperty> ethnicityLst = systemPropertyDao.getByPicklistName(Constants.EthnicityPicklistName);
        if(ethnicityLst != null && !ethnicityLst.isEmpty()){
        	model.addAttribute("ethnicityLst", ethnicityLst);
        }

        List<SystemProperty> kidsAgeLst = new ArrayList<SystemProperty>();			
    	kidsAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_KIDS_AGE_VALIDATION);
		if(kidsAgeLst != null && !kidsAgeLst.isEmpty()){
			model.addAttribute("kidsAgeValidation", kidsAgeLst.get(0).getKeyValue());				
		}	
		List<SystemProperty> adultAgeLowerLst = new ArrayList<SystemProperty>();			
		adultAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
		if(adultAgeLowerLst != null && !adultAgeLowerLst.isEmpty()){
			model.addAttribute("adultAgeValidationLowerLimit", adultAgeLowerLst.get(0).getKeyValue());				
		}
		
		return new ModelAndView("addMember", model.asMap());
		//return "addMember";
	}
	
	@SuppressWarnings("unused")
	@RequestMapping(value= "addMember", method = RequestMethod.POST)
	public @ResponseBody String addMember1(Account account, final HttpServletRequest request, final HttpServletResponse response) {
		JSONArray json = new JSONArray();
		JSONObject obj = new JSONObject();		
		boolean isContactExists = false;
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();	
    	Account accountVO = null;
    	
    	List<Object[]> accLst = null;
		List<User> contactLst =  new ArrayList<User>();
    	/*if(account != null && !StringUtils.isBlank(account.getFirstName()) && !StringUtils.isBlank(account.getLastName()) && !StringUtils.isBlank(account.getEmail()) && account.getDateOfBirth() != null){
    		contactLst =  userDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(account.getFirstName(), account.getLastName(), account.getEmail(), account.getDateOfBirth());
    	}else{
    		contactLst =  userDao.getByFirstNameAndLastNameAndDateOfBirth(account.getFirstName(), account.getLastName(), account.getDateOfBirth());
    	}*/		
    	if(account != null && !StringUtils.isBlank(account.getFirstName()) && !StringUtils.isBlank(account.getLastName()) && !StringUtils.isBlank(account.getEmail()) && account.getDateOfBirth() != null){
    		accLst =  accountDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(account.getFirstName(), account.getLastName(), account.getEmail(), account.getDateOfBirth());
    	}else{
    		accLst =  accountDao.getByFirstNameAndLastNameAndDateOfBirth(account.getFirstName(), account.getLastName(), account.getDateOfBirth());
    	}
    	Account accountDB = null;
    	User userDb = null;    	
    	if(accLst != null && !accLst.isEmpty()){
    		accountDB = (Account) accLst.get(0)[0];						
    		userDb =  (User) accLst.get(0)[1];
			/*for(Object accountObj : accLst){
    			Object accountArr[] = (Object[]) accountObj;	
    			if(accountArr != null && accountArr.length >0){
					if(accountArr[0] != null && accountArr[1] != null){    						
						accountDB = (Account) accountArr[0];						
						userDb = (User) accountArr[1];						
					}
    			}
			}*/
		}    	
    	//Set<User> usersLst = new HashSet<User>();
    	if(userId != null && !"".equals(userId)){
    		accountVO = accountDao.getByEmail(userId); 
    		Signup primarySignup = null;    	
        	for(Signup signup : accountVO.getSignup()){
        		if(signup != null && signup.getContact() != null && signup.getContact().getContactId() != null){    			
        			if(signup.getPaymentMethod() !=  null){
        				primarySignup = signup;
        			}
        		}	    		
        	}
    		if(accLst != null && accountDB != null && userDb != null){    			    				
    				if(accountVO != null && accountVO.getAccountId().equals(accountDB.getAccountId())){
    					obj.put("Duplicate", "Duplicate");
						json.add(obj);
						isContactExists = true;
						return json.toString();
    				}else{
    					List<AccountContact> accountContactLst = new ArrayList<AccountContact>();
    					accountContactLst = accountContactDao.getByCustomerAndContact(accountVO, userDb);
    					if(accountContactLst != null && !accountContactLst.isEmpty()){
    						obj.put("Duplicate", "Duplicate");
    						json.add(obj);
    						isContactExists = true;
    						return json.toString();
    					}else {
    						AccountContact accContact = accountContactService.saveAccountContact(accountVO, userDb);  
    						if(primarySignup != null && StringUtils.isNotBlank(account.getAddToMembership()) && "Yes".equals(account.getAddToMembership())){
    							List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(userDb.getFullName(), accountVO, userDb, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
    			    			if(signupLst == null || signupLst.isEmpty()){
    			    				Signup saveSignup = populateSignupAddChild(userDb, primarySignup);
        							signupDao.save(saveSignup);
    			    			} 
    							
    						}else if(StringUtils.isNotBlank(account.getAddToMembership()) && "No".equals(account.getAddToMembership())){
    							List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(userDb.getFullName(), accountVO, userDb, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
    							if(signupLst != null && !signupLst.isEmpty()){
    								for(Signup signup : signupLst){
    									signup.setStatus(SignupStatusEnum.Inactive.toString());
    									signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
    									signup.setLastUpdated(Calendar.getInstance());
    									signupDao.save(signup);
    								}
    							}
    						}
        					obj.put("Success", "Success");
        					json.add(obj);
        					return json.toString();
    					}    					
    				}
    			
    		}else{    			
    	    	try{
    	    		if(accountVO!=null){
    	    			User user = new User();
    	    			user.setCustomer(accountVO);
    	    			user.setDateOfBirth(account.getDateOfBirth());
    	    	    	user.setEnabled(true);	
    	    	    	user.setAccountExpired(false);
    	    	    	user.setAccountLocked(false);
    	    	    	user.setCredentialsExpired(false);
    	    			user.setActive(true);    			
    	    			user.setUsername(account.getEmail());    			
    	     	    	user.setFirstName(account.getFirstName());
    	    			user.setLastName(account.getLastName());
    	    			user.setfNameAndLName(user.getFullName());     	    	
    	     	    	if(account != null && account.getGender() != null && account.getGender().equals("Male")){
    	     	    		user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
    	    			}else{
    	    				user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
    	    			}
    	     	    	user.setEmailAddress(account.getEmail());
    	     	    	user.setFormattedPhoneNumber(account.getPhoneNumber());
    	     	    	user.setGender(account.getGender());     	    	    	
    	     	    	user.setEnabled(true);	     	    	
    	     	    	user.setContactPointPurpose(account.getHomeNumber());     
    	     	    	
    	     	    	user.setEthnicity(account.getEthnicity());  
    	     	    	user.setEmployer(account.getEmployer());   
    	     	    	user.setIsAdult(account.getIsAdult());
    	     	    	
    	     	    	Calendar cal = Calendar.getInstance();
    	     	    	user.setLastUpdated(cal);
    	     	    	
    	     	    	Address address =  new Address();
    	    			address.setPrimaryAddressLine1(accountVO.getAddressLine1());
    	    			address.setPrimaryAddressLine2(accountVO.getAddressLine2());
    	    			address.setPrimaryAddressCity(accountVO.getCity());
    	    			address.setPrimaryAddressProvince(accountVO.getState());
    	    			address.setPrimaryAddressPostalCode(accountVO.getPostalCode());			
    	    			address.setPrimaryAddressCountry(Constants.COUNTRY_USA);
    	    			user.setAddress(address);
    	     	    	
    	     	    	user = userDao.save(user);
    	     	    	AccountContact accContact = accountContactService.saveAccountContact(accountVO, user);
    	     	    	
    	     	    	if(primarySignup != null && StringUtils.isNotBlank(account.getAddToMembership()) && "Yes".equals(account.getAddToMembership())){
    	     	    		List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(user.getFullName(), accountVO, user, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
			    			if(signupLst == null || signupLst.isEmpty()){
			    				Signup saveSignup = populateSignupAddChild(user, primarySignup);
    							signupDao.save(saveSignup);
			    			} 
						}else if(StringUtils.isNotBlank(account.getAddToMembership()) && "No".equals(account.getAddToMembership())){
							List<Signup> signupLst = signupDao.findByContactNameAndCustomerAndContactAndTypeAndStatus(user.getFullName(), accountVO, user, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
							if(signupLst != null && !signupLst.isEmpty()){
								for(Signup signup : signupLst){
									signup.setStatus(SignupStatusEnum.Inactive.toString());
									signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
									signup.setLastUpdated(Calendar.getInstance());
									signupDao.save(signup);
								}
							}
						}
    	     	    	//user.setPrimaryFormattedPhoneNumber(account.getPhoneNumber());
    	     	    	//user.setPassword("password");
    	     			//user.setPersonFirstName(account.getFirstName());
    	     	    	//user.setPersonLastName(account.getLastName());  
    	     	    	//user.setProfileImage("resources/img/portal_Images/defaultpic.jpg");
    	     	    	//user.setPrimaryEmailAddress(account.getEmail());       	
    	     	    	
    	     	    	
    	     	    	//usersLst.add(user);
    	    	    	//populateContactAndCustomerWaiversAndTC(user, accountVO);
    	    	    	//populateMembershipData(accountVO, user);
    	    	    	
    	    	    	//accountVO.setUser(usersLst);
    	    	    	//accountVO.getUser().add(user);
    	    	
    		    		//Account updAccount =  accountDao.save(accountVO);
    		    		////System.out.println("  user "+updAccount.getUser());
    		    		
    	     	    	obj.put("Success", "Success");
    					json.add(obj);
    					return json.toString();
    	    		}
    	    		
    	    	}catch(Exception e){
    	    		log.error("Error  ",e);
    	    		return null;
    	    	}  
    		}
	        
    	}
		return null;
	}
	
	private Signup populateSignupAddChild(User userDb, Signup primarySignup) {
		Signup signup = new Signup();		
		signup.setContactName(userDb.getFullName());
		signup.setSignupDate(new Date());
		signup.setContact(userDb);
		//signup.setPaymentMethod(null);
		signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
		signup.setCustomer(primarySignup.getCustomer());
		signup.setItemDetail(primarySignup.getItemDetail());
		signup.setType(primarySignup.getType());
		signup.setStatus(primarySignup.getStatus());
		signup.setOpptyId(primarySignup.getOpptyId());
		signup.setSignUpProductType(primarySignup.getSignUpProductType());
		signup.setProgramEndDate(primarySignup.getProgramEndDate());
		signup.setPaymentFrequency(primarySignup.getPaymentFrequency());
		signup.setSignUpPricingOption(primarySignup.getSignUpPricingOption());
		signup.setProgramStartDate(primarySignup.getProgramStartDate());
		signup.setMembersshipFeeNextBillingDate(primarySignup.getMembersshipFeeNextBillingDate());
		signup.setPaymentFrequency(primarySignup.getPaymentFrequency());
		signup.setSignUpPricingOption(primarySignup.getSignUpPricingOption());
		signup.setLocation(primarySignup.getLocation());		
		signup.setFinalAmount(primarySignup.getFinalAmount());
		signup.setDiscountAmount(primarySignup.getDiscountAmount());
		signup.setFAamount(primarySignup.getFAamount());
		signup.setSignupPrice(primarySignup.getSignupPrice());
		signup.setSetUpFee(primarySignup.getSetUpFee());
		signup.setRegistrationFee(primarySignup.getRegistrationFee());
		signup.setDeposit(primarySignup.getDeposit());
		signup.setJoinFee(primarySignup.getJoinFee());
		signup.setFApercentage(primarySignup.getFApercentage());
		signup.setLastUpdated(Calendar.getInstance());
		
		return signup;
	}

	@RequestMapping(value= "holdMembership", method = RequestMethod.GET)
	 public String holdMembership(final HttpServletRequest request, final HttpServletResponse response) {		
		 return "holdMembership";
	 }
	
	@RequestMapping(value= "isPrimaryUserEmail", method = RequestMethod.GET)
	 public @ResponseBody String isPrimaryUserEmail(final HttpServletRequest request, final HttpServletResponse response) {		
		Enumeration<String> parameterNames = request.getParameterNames();
		String email = null;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if(!StringUtils.isBlank(paramName) && paramName.contains("email")){
				String[] paramValues = request.getParameterValues(paramName);				
				for (int i = 0; i < paramValues.length; i++) {
					email = paramValues[i];			
				}
			}
		}		
		if(email==null || "".equals(email)){
			 return "true";
		 }else{
			 try{
				 User user =  userDao.getByEmailAddress(email);
				 if(user != null){
					 if(!user.isPrimary()){
						 return "true";
					 }else{
						 return "false";
					 }
					 
				 }else{
					 return "true";
				 }
				 
			 }catch(Exception e){
				 return null;
			 }			
		 }
			
		 
		 
	 }
	
	@RequestMapping(value= "isEmailExists", method = RequestMethod.GET)
	 public @ResponseBody String isEmailExists(final HttpServletRequest request, final HttpServletResponse response) {		
		Enumeration<String> parameterNames = request.getParameterNames();
		String email = null;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if(!StringUtils.isBlank(paramName) && paramName.contains("email")){
				String[] paramValues = request.getParameterValues(paramName);				
				for (int i = 0; i < paramValues.length; i++) {
					email = paramValues[i];			
				}
			}
		}		
		if(email==null || "".equals(email)){
			 return "true";
		 }else{
			 try{
				 User user =  userDao.getByEmailAddress(email);
				 if(user != null){
					 return "false";
				 }else{
					 return "true";
				 }
				 
			 }catch(Exception e){
				 return null;
			 }			
		 }
			
		 
		 
	 }
	
	@RequestMapping(value= "isEmailExistsWithUserData", method = RequestMethod.GET)
	 public @ResponseBody String isEmailExistsWithUserData(@RequestParam("donerFirstName") String donerFirstName, @RequestParam("donerLastName") String donerLastName, 
			 @RequestParam("dobMonth") String dobMonth, @RequestParam("dobDate") String dobDate, @RequestParam("dobYear") String dobYear, final HttpServletRequest request, final HttpServletResponse response) {		
		Enumeration<String> parameterNames = request.getParameterNames();
		String email = null;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if(!StringUtils.isBlank(paramName) && paramName.contains("email")){
				String[] paramValues = request.getParameterValues(paramName);				
				for (int i = 0; i < paramValues.length; i++) {
					email = paramValues[i];			
				}
			}
		}	
		 if(email==null || "".equals(email)){
			 return "true";
		 }else{
			 try{
				 Date dobForm =  null;
				 //User user =  null;				 
				 User user =  userDao.getByEmailAddress(email);
				 if(user != null){	
					 if(StringUtils.isNotBlank(donerFirstName) && StringUtils.isNotBlank(donerLastName) && StringUtils.isNotBlank(dobMonth) 
							 && StringUtils.isNotBlank(dobDate) && StringUtils.isNotBlank(dobYear)){
						 
						 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");					 
						 try { 
							 dobForm =sdf.parse(dobMonth + "/" + dobDate + "/" + dobYear);
						 }catch(Exception e){
							 e.printStackTrace();
						 }
						 
						 //List<User> userLst = userDao.getByFirstNameAndLastNameAndDateOfBirth(donerFirstName, donerLastName, dobForm);
						 List<User> userLst = userDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(donerFirstName, donerLastName, email, dobForm);
						 if(userLst != null && !userLst.isEmpty()){
							 user = userLst.get(0);
							 return "true";
						 }else {
							 return "false";
						 }
					 }else{
						 return "false";
					 }
					 
					 /*if(StringUtils.isNotBlank(user.getFirstName()) && StringUtils.isNotBlank(user.getLastName()) && user.getDateOfBirth() != null){
						 
						 SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						 String dob =  null;
						 try { 
							dob =sdf.format(user.getDateOfBirth());
						 }catch(Exception e){
							 e.printStackTrace();
						 }
						 return "false_" + user.getFirstName() + "_" + user.getLastName() + "_" + dob;
					 }else {
						 return "false"; 
					 }*/					 
				 }else{
					 return "true";
				 }
				 
			 }catch(Exception e){
				 return null;
			 }			
		 }
			
		 
		 
	 }
	
	@RequestMapping(value="/memberAuth", method=RequestMethod.POST)
    public @ResponseBody String auth(final HttpServletRequest request, final HttpServletResponse response) { 
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		User user = null;

		if(username!=null && !"".equals(username.trim()) && password!=null && !"".equals(password)){
			try{
				Account account = accountDao.getByEmail(username);			
				user = getUserByAccount(account, user);
			}catch(Exception e){
				log.error("Error  ",e);
			}
			if(user!=null && user.getPassword() !=null && bCryptPasswordEncoder.matches(password, user.getPassword())){			
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());
				//request.getSession();
		        token.setDetails(new WebAuthenticationDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(token);		        
				return "Success";
			}			
		}		
		return "Failure";
		
	}
	
	@RequestMapping(value="/getAccountDetailsByEmail", method=RequestMethod.POST)
    public @ResponseBody List<Account> getAccountDetailsByEmail(@RequestParam("emailId") String email, final HttpServletRequest request, final HttpServletResponse response) { 
		if(email != null && !"".equals(email.trim())){	
			List<Account> acccList = new ArrayList<Account>();
			Account accountData = new Account();			
			Account account =  accountDao.getByEmail(email);
			accountData.setAddressLine1(account.getAddressLine1());
			accountData.setAddressLine2(account.getAddressLine2());
			accountData.setCity(account.getCity());
			accountData.setState(account.getState());
			accountData.setPostalCode(account.getPostalCode());
			//accountData.setUser(account.getUser());
			List<User> userLst =  new ArrayList<User>();
			for(User user : account.getUser()){
				User userData =  new User();
				userData.setPersonFirstName(user.getPersonFirstName());
				userData.setPersonLastName(user.getPersonLastName());
				userData.setPrimaryEmailAddress(user.getPrimaryEmailAddress());
				userData.setPrimaryFormattedPhoneNumber(user.getPrimaryFormattedPhoneNumber());
				userData.setDateOfBirth(user.getDateOfBirth());
				userData.setActive(user.isActive());
				userData.setEnabled(user.isEnabled());
				userData.setAccountExpired(user.isAccountExpired());
				userData.setPrimary(user.isPrimary());
				userData.setMember(user.isMember());
				userData.setGender(user.getGender());
				userLst.add(userData);
			}
			accountData.setUserLst(userLst);			
			acccList.add(accountData);		
			return acccList;
			//return accountDao.getAccountByEmail(email);
		}		
		return null;		
	}	
	
	
	@RequestMapping(value= "ReqforFA", method = RequestMethod.POST)
	public @ResponseBody String requestForFA1( final HttpServletRequest request, final HttpServletResponse response)
	{
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		System.out.println(userId);
    	Account account= null;
    	//Set<User> usersLst = new HashSet<User>();
    	Activity activity=null;
    	account=accountDao.getByEmail(userId);
     //  //System.out.println(account.getEmail()+"---"+account.getName()+"----"+account.getAccountId());
       	try{
       	//	//System.out.println("in try");
       	List<Activity> faList=interactionDao.findByTypeAndCustomer("REQUEST FOR FA",account );	
    	activity=faList.get(0);
		////System.out.println("interaction is not null");
		System.out.println(activity.getType()+"---"+ activity.getDescription());
    	
    	
    		
    					return "DUPE";
       	}
    		catch(NullPointerException ex){
    			
    			//System.out.println("in catch "+ex);
    			Activity activity2=new Activity();
    			Account acc=accountDao.getByEmail(account.getEmail());
    
    			if(activity2.getCustomer()==null)
    			{
    			//System.out.println("in if");
    			activity2.setCustomer(acc);
    			activity2.setShowOnPortal(true);
        		activity2.setType("REQUEST FOR FA");
        		activity2.setDescription("Financial Assistance..");
        		Calendar LastUpdated = Calendar.getInstance();
        		activity2.setLastUpdated(LastUpdated);
        		User user=userDao.getByEmailAddress(acc.getEmail());
        		activity2.setContact(user);
        		//Signup signup=signupDao.
    			interactionDao.save(activity2);
    			
    		//	//System.out.println(interaction2.getCustomer().getAccountId());
    			}
    			return "SUCCESS";
    		}
    
	}
	
	/*@ModelAttribute
    @RequestMapping(value= "changeMembershipShowWizard", method = RequestMethod.GET)
    public ModelAndView changeMembershipWizard() {	
    	
        Model model = new ExtendedModelMap();               
    	
        try {            
        	Authentication a = SecurityContextHolder.getContext().getAuthentication();
        	String userId = null;
        	User contact =  null;
        	Account account = null;
        	List<User> userLst = null;
        	try{
        		userId = ((UserDetails) a.getPrincipal()).getUsername();        		
        	}catch(Exception e){
        		//System.out.println("Your session is expired");	
        		log.error("Error  ",e);
        	}        	       	
        	if(userId != null && !"".equals(userId)){
        		account = accountDao.getByEmail(userId);
        		userLst = account.getUserLst();		
        		List<User> kidsInfo = new ArrayList<User>();
        		for(User usr : account.getUser()){
        			account.getUser().add(usr); 
        			if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
        				model.addAttribute("secUser", usr);
        			}
        			if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
        				model.addAttribute("thirdUser", usr);
        			}
        			if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
        				kidsInfo.add(usr);
        			}        					
        		}
        		contact = getUserByAccount(account, contact);
        		List<Signup> signupLst = signupDao.getByCustomerAndContactAndStatusAndType(account, contact, PortalStatusEnum.ACTIVE.toString(), ProductTypeEnum.MEMBERSHIP.toString());
        		if(signupLst != null && !signupLst.isEmpty()){
        			model.addAttribute("signUpProduct", signupLst.get(0));
        		}else{
        			List<Signup> signupLstNew = signupDao.getByCustomerAndContactAndStatusAndType(account, contact, PortalStatusEnum.New.toString(), ProductTypeEnum.MEMBERSHIP.toString());
        			if(signupLstNew != null && !signupLstNew.isEmpty()){
            			model.addAttribute("signUpProduct", signupLstNew.get(0));
            		}else{
            			signupLstNew = null;
            		}        			
        		}
        		List<PaymentMethod> creditCardPaymentMethodLst = paymentMethodDao.getCreditCardInfoByAccountId(account.getAccountId());
        		List<PaymentMethod> achPaymentMethodLst = paymentMethodDao.getACHInfoByAccountId(account.getAccountId());
        		
        		model.addAttribute("kidsInfo", kidsInfo);
        		model.addAttribute("locations", locationDao.findAll());
        		model.addAttribute("payMethodLstCredit", creditCardPaymentMethodLst);
        		model.addAttribute("payMethodLstACH", achPaymentMethodLst);        		
                model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea(Constants.LOCATION_BAYAREA));
                model.addAttribute("account", account);
                model.addAttribute("primaryUser", contact);
        	} 
            
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
            log.error("Error  ",e);
        }
        return new ModelAndView("changeMembership", model.asMap());    	
	}*/

	@ModelAttribute
    @RequestMapping(value= "changeMembershipShowWizard", method = RequestMethod.GET)
    public ModelAndView changeMembershipWizard(final HttpServletRequest request, final HttpServletResponse response, 
    		@RequestParam(required = false) String urlPromoItemDetailId,@RequestParam(required = false) String urlPromoContactPartyId,
    		@RequestParam(required = false) String urlPromoContactId,@RequestParam(required = false) String urlPromoCode) {	
    	
        Model model = new ExtendedModelMap();               
    	
        try {
        	Long itemDetailId = 0l;
        	String promoCode = null;
    		ItemDetail itemDetail = null;
    		User contact = null;
    		
    		if(urlPromoItemDetailId!=null && !"".equals(urlPromoItemDetailId.trim())){
    			itemDetailId = Long.parseLong(urlPromoItemDetailId);
    			itemDetail = itemDetailsDao.getById(itemDetailId);
    		}

    		if(urlPromoContactId!=null && !"".equals(urlPromoContactId.trim())){
    			contact = userDao.findOne(Long.parseLong(urlPromoContactId));
    		}
    			
    		if(urlPromoCode!=null && !"".equals(urlPromoCode.trim())){
    			promoCode = urlPromoCode;
    		}
    			
    		if(itemDetail != null && promoCode != null){
    			if(itemDetail.getType() != null && !itemDetail.getType().equals("") && itemDetail.getType().equalsIgnoreCase(ProductTypeEnum.MEMBERSHIP.getValue())){
    				model.addAttribute("urlPromoItemDetailId", itemDetail.getId());
    				if(contact != null){
    					model.addAttribute("urlPromoContactId", contact.getContactId());
    				}
    				model.addAttribute("urlPromoCode", promoCode);
    			}
    		}
        	
        	Authentication a = SecurityContextHolder.getContext().getAuthentication();
        	String userId = null;
        	//User contact =  null;
        	Account account = null;
        	List<User> userLst = new ArrayList<User>();
        	List<PaymentMethod> paymentMethodList = null;
        	/*try{
        		userId = ((UserDetails) a.getPrincipal()).getUsername();        		
        	}catch(Exception e){
        		//System.out.println("Your session is expired");	
        		log.error("Error  ",e);
        	} */
        	try{
    			userId = a.getName();
    		}catch(Exception e){
    			log.error("Error  ",e);
    		}
        	if(userId != null && !"".equals(userId)){
        		List<SystemProperty> kidsAgeLst = new ArrayList<SystemProperty>();			
            	kidsAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_KIDS_AGE_VALIDATION);
    			if(kidsAgeLst != null && !kidsAgeLst.isEmpty()){
    				model.addAttribute("kidsAgeValidation", kidsAgeLst.get(0).getKeyValue());				
    			}	
    			List<SystemProperty> adultAgeLowerLst = new ArrayList<SystemProperty>();			
    			adultAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
    			if(adultAgeLowerLst != null && !adultAgeLowerLst.isEmpty()){
    				model.addAttribute("adultAgeValidationLowerLimit", adultAgeLowerLst.get(0).getKeyValue());				
    			}

    			List<SystemProperty> adultAgeUpperLst = new ArrayList<SystemProperty>();			
    			adultAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_UPPER_LIMIT);
    			if(adultAgeUpperLst != null && !adultAgeUpperLst.isEmpty()){
    				model.addAttribute("adultAgeValidationUpperLimit", adultAgeUpperLst.get(0).getKeyValue());				
    			}
    			List<SystemProperty> youthAgeLowerLst = new ArrayList<SystemProperty>();			
    			youthAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_LOWER_LIMIT);
    			if(youthAgeLowerLst != null && !youthAgeLowerLst.isEmpty()){
    				model.addAttribute("youthAgeValidationLowerLimit", youthAgeLowerLst.get(0).getKeyValue());				
    			}	
    			List<SystemProperty> youthAgeUpperLst = new ArrayList<SystemProperty>();			
    			youthAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_UPPER_LIMIT);
    			if(youthAgeUpperLst != null && !youthAgeUpperLst.isEmpty()){
    				model.addAttribute("youthAgeValidationUpperLimit", youthAgeUpperLst.get(0).getKeyValue());
    			}
    			List<SystemProperty> seniorAgeLst = new ArrayList<SystemProperty>();			
    			seniorAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_SENIOR_AGE_VALIDATION);
    			if(seniorAgeLst != null && !seniorAgeLst.isEmpty()){
    				model.addAttribute("seniorAgeValidationLimit", seniorAgeLst.get(0).getKeyValue());
    			}
    			
    			model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
        		model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea(Constants.LOCATION_BAYAREA));	
        		account = accountDao.getByEmail(userId);
        		
        		account = (Account)account;
        		
        		//account = accountDao.findByEmail(userId);
        		if(account != null && account.getUserLst() != null && !account.getUserLst().isEmpty()){
        			// Do nothing
        		}else if(account != null){
        			List<User> ul = new ArrayList<User>();
        			ul.add(new User());
        			account.setUserLst(ul);
        		}
        		
        		model.addAttribute("account", account);
        		
        		paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
        		model.addAttribute("paymentInfoLst" , paymentMethodList);
        		
        		User primaryContact = null;
        		primaryContact = getUserByAccount(account, primaryContact);
        		model.addAttribute("primaryUser", primaryContact);        		
        		
    			List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
    			for(AccountContact ac : accountContactLst){
    				if(ac.getEndDate() == null && ac.getContact() != null){
    					userLst.add(ac.getContact());
    				}				
    			}
    			if(userLst != null && !userLst.isEmpty()){
    				List<User> kidsInfo = new ArrayList<User>();
    				List<User> adultUserLstInfo = new ArrayList<User>();
    				List<User> youthUserLstInfo = new ArrayList<User>();
    				boolean isSecUser = false;
    				//boolean isThirdUser = false;
    				List<User> selectKidsInfo = new ArrayList<User>();
    				
    				for(User usr : userLst){   					        		
    					List<Signup> activeSignupLst =  signupDao.getByCustomerAndContactAndStatusAndType(account,usr, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
    					if(activeSignupLst != null && !activeSignupLst.isEmpty()){   
    						if(!primaryContact.getContactId().equals(usr.getContactId())){
    							if(isAdultByDob(usr.getDateOfBirth())){
    	    						if(!isSecUser){
    	    							model.addAttribute("secUser", usr);
    	    							isSecUser = true;
    	    						}else{
    	    							model.addAttribute("thirdUser", usr);
    	    						}	    						
    			        		}else if(isYouthByDob(usr.getDateOfBirth())){
    			        			//Youth Select
    			        		}else{
    			        			kidsInfo.add(usr);
    			        		}
    						}	    					  
    					}     					
    					if(isAdultByDob(usr.getDateOfBirth())){    						
    						adultUserLstInfo.add(usr);
		        		}
    					if(isYouthByDob(usr.getDateOfBirth())){
		        			youthUserLstInfo.add(usr);
    					}
    					if(isChildByDob(usr.getDateOfBirth())){
		        			selectKidsInfo.add(usr);
		        		} 
    				}
    				model.addAttribute("userLstInfo", adultUserLstInfo);
    				model.addAttribute("kidsInfo", kidsInfo);
    				model.addAttribute("selectKidsInfo", selectKidsInfo);
    				model.addAttribute("youthUserLstInfo", youthUserLstInfo);    				
    			}
        		
    			/*if(userLst != null && !userLst.isEmpty()){    				
    				for(User usr : userLst){
    					List<Signup> signupLst = signupDao.getByCustomerAndContactAndStatusAndType(account, usr, PortalStatusEnum.ACTIVE.toString(), ProductTypeEnum.MEMBERSHIP.toString());
    	        		if(signupLst != null && !signupLst.isEmpty()){
    	        			if(signupLst.get(0).getPaymentMethod() != null){
    	        				model.addAttribute("signUpProduct", signupLst.get(0));
    	        				break;
    	        			}    	        			
    	        		}
    				}
    			} */   			
    			List<Signup> signupLst = signupDao.getByCustomerAndTypeAndStatusAndPaymentMethodNotNull(account, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
    			if(signupLst != null && !signupLst.isEmpty()){
        			if(signupLst.get(0).getPaymentMethod() != null){
        				model.addAttribute("signUpProduct", signupLst.get(0));        				
        			}    	        			
        		}
    			List<Signup> activateSignupLst =  signupDao.getByCustomerAndStatusAndType(account, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
                if(activateSignupLst != null && !activateSignupLst.isEmpty()){
                    if(activateSignupLst != null && !activateSignupLst.isEmpty()){
                     model.addAttribute("existingMemberJoinFee", activateSignupLst.get(0).getJoinFee());
                    }
                }
        		List<PaymentMethod> creditCardPaymentMethodLst = paymentMethodDao.getCreditCardInfoByAccountId(account.getAccountId());
        		List<PaymentMethod> achPaymentMethodLst = paymentMethodDao.getACHInfoByAccountId(account.getAccountId());
        		
        		model.addAttribute("payMethodLstCredit", creditCardPaymentMethodLst);
        		model.addAttribute("payMethodLstACH", achPaymentMethodLst);                    
                model.addAttribute("primaryUser", primaryContact);
        	} 
            
        } catch (Exception se) {
            //System.out.println("Error occoured while querying Product");
        	log.error("Error  ",se);
        }
        return new ModelAndView("changeMembership", model.asMap());    	
	}
	  /*@RequestMapping(value= "changeMembershipRegister", method = RequestMethod.POST)
	    public ModelAndView changeMembershipRegister(Account accountForm, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response) {    	
	        Model model = new ExtendedModelMap();	    	
	        try {            
	        	Authentication a = SecurityContextHolder.getContext().getAuthentication();
	        	String userId = null;
	        	User contact =  null;
	        	Account account = null;
	        	List<User> userLst = new ArrayList<User>();
	        	try{
	        		userId = ((UserDetails) a.getPrincipal()).getUsername();        		
	        	}catch(Exception e){
	        		//System.out.println("Your session is expired");	
	        		log.error("Error  ",e);
	        	}        	       	
	        	if(userId != null && !"".equals(userId)){	
	        		account = accountDao.getByEmail(userId);
	            	User user = new User();     	
	            	User accUser = new User();  
	            	accUser = getUserByAccount(account, accUser);
	            	Signup signup = new Signup();
	            	ItemDetail itemDetail  =  itemDetailDao.getById(Long.parseLong(accountForm.getItemDetailsId())); 
///userLst = populateUsrLstFromUsrSet(accountForm);
	            	userLst = populateUsrLstFromUsrSet(account);
	            	if(userLst != null && !userLst.isEmpty()){
	            		User primaryUser = null;
	            		for(User usr : userLst){			
	            			if(usr != null && usr.isPrimary()){
	            				primaryUser = usr;
	            				break;
	            			}
	            			
	            		}
	            		///populateContactAndCustomerWaiversAndTC(primaryUser, accountForm);
		    			//User primaryUser = userLst.get(0);
		    	    	//populateContactAndCustomerWaiversAndTC(primaryUser, account);  	    	  
		    	    	//userLst.add(user);
		    	    	account.setUser(new HashSet<User>());	    	    	
		    	    	List<User> kidsInfo = new ArrayList<User>();
		    	    	for(User usr : userLst){
		    	    		account.getUser().add(usr); 
		    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
		    	    			model.addAttribute("secUser", usr);
		    	    		}
		    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
		    	    			model.addAttribute("thirdUser", usr);
		    	    		}
		    	    		if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
		    	    			kidsInfo.add(usr);
		    	    		}
		    	    		   		
		    	    	}    	    	   	
		    	    	account.setEmail(primaryUser.getPrimaryEmailAddress());
		    	    	account.setName(primaryUser.getFullName());    
		    	        
		    	    	
		    	    	Account acc = accountDao.saveAndFlush(accountForm);  
	    	            if(accountForm.getSavePayment() != null && accountForm.getSavePayment().equals("true")){
	    	            	JetPayPayment jetPayPayment = new JetPayPayment();
		    	            PaymentMethod paymentMethod  =  populatePaymentMethodData(accountForm, accountForm.getTransId());
		    	            paymentMethod = paymentMethodDao.save(paymentMethod);
		    	            signup = populateSignupData(accountForm, primaryUser, itemDetails,signup,  "", paymentMethod); 
		    	            model.addAttribute("paymentTransId", paymentMethod.getTransId());
	    	            }else{
	    	            	signup = populateSignupData(accountForm, primaryUser, itemDetails,signup,  "", null); 
	    	            	model.addAttribute("paymentTransId", "0");
	    	            }
	    	            
		    	    	
	    	            Account acc = accountDao.saveAndFlush(accountForm);    	              
	    	            JetPayPayment jetPayPayment = new JetPayPayment();
	    	            PaymentMethod paymentMethod  =  populatePaymentMethodData(accountForm, accountForm.getTransId());
	    	            paymentMethod.setLastUpdated(Calendar.getInstance());
	    	            paymentMethod = paymentMethodDao.save(paymentMethod);
	    	            signup = populateSignupData(account, primaryUser, itemDetail,signup,  "", paymentMethod); 
	    	            signup.setLastUpdated(Calendar.getInstance());
	    	            Signup signup2 = signupDao.save(signup);  
	    	            
	    	    		if(account != null){	    		    	    	
	    	    	        model.addAttribute("account", account);    	            	        
	    	    	        model.addAttribute("primaryUser", primaryUser);
	    	    	        model.addAttribute("kidsInfo", kidsInfo);    	    	        
	    	    	        model.addAttribute("signup", signup2);
	    	    	        model.addAttribute("product", itemDetail);
	    	    	        model.addAttribute("paymentTransId", paymentMethod.getTransId());
	    	    	        model.addAttribute("prodJoinFee", accountForm.getJoiningFee());
	    	    	        model.addAttribute("productPrice", accountForm.getProductPrice());
	    	    	        model.addAttribute("productTotalPrice", signup2.getTotalAmount());
	    	    	        if(account.getJoiningFee() != null && account.getProductPrice() != null){
	    	    	        	double prodJoinFee = Double.parseDouble(account.getJoiningFee());
	    	    	        	double prodPrice = Double.parseDouble(account.getProductPrice());
	    	    	        	double totalPrice = prodJoinFee+ prodPrice;
	    	    	        	model.addAttribute("productTotalPrice", totalPrice);
	    	    	        }
	    	    		}
		        		
		        		List<Signup> signupLst =  signupDao.getByCustomer(account);
		        		List<Signup> signupLstNew = new ArrayList<Signup>();
		        		
		        		for(Signup sign : signupLst){
		        			Signup signupNew = populateNewSignupData(sign);
		        			signupLstNew.add(signupNew);
		        			sign.setStatus(PortalStatusEnum.INACTIVE.toString());
		        			signupDao.save(sign);
		        		}
		        		for(Signup sign : signupLstNew){		        			
		        			signupDao.save(sign);
		        		}
		        	}
	        	}
	        }catch(Exception e){
	        	log.error("Error  ",e);
	        }
	        return new ModelAndView("changeMembershipConfirmation", model.asMap());
	        
    	   
	  }*/
	
	@SuppressWarnings("unused")
	@RequestMapping(value= "changeMembershipRegister", method = RequestMethod.POST)
    public ModelAndView changeMembershipRegister(Account account, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response) {
    	
        Model model = new ExtendedModelMap();	    	
        try {
        	//BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        	String opptyId = request.getParameter("opptyId");  
        	String finalAmount =  request.getParameter("finalAmount");
        	String discountAmount =  request.getParameter("discountAmount");
        	String faAmount =  request.getParameter("faAmtHiddenInput");
        	String signupPrice =  request.getParameter("signupPrice");
        	String setUpFee =  request.getParameter("setUpFee");
        	String registrationFee =  request.getParameter("registrationFee");
        	String deposit =  request.getParameter("deposit");
        	String faPercentage =  request.getParameter("faPercentage");
        	String faStartDate =  request.getParameter("faStartDate");
        	String faEndDate =  request.getParameter("faEndDate");
        	String joinFeeAmt =  request.getParameter("joinFeeAmt");       	
        	String membershipStartDateStr =  request.getParameter("membershipStartDate");
        	String promotionMapStr =  request.getParameter("promotionMapInput");
        	
        	String signUpPromoDiscount =  request.getParameter("signUpPromoDiscountHiddenInput");
        	String otherPromoDiscount =  request.getParameter("otherPromoDiscountHiddenInput");
        	String sumOfAdditives =  request.getParameter("sumOfAdditivesHiddenInput");
        	String isRecurring =  request.getParameter("isRecurringHiddenInput");
        	String priceOption =  request.getParameter("paymentFrequency");
        	
        	String billDate =  request.getParameter("billDate");
        	String dueDate =  request.getParameter("dueDate");
        	String nextBillDate =  request.getParameter("nextBillDate");
        	String invoiceDate =  request.getParameter("invoiceDate");
        	String billDateOnInvoice =  request.getParameter("billDateOnInvoice");
        	String dueDateOnInvoice =  request.getParameter("dueDateOnInvoice");
        	
        	Date membershipStartDate = new Date();
        	if(!StringUtils.isBlank(membershipStartDateStr)){
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");			
        		try {
        			membershipStartDate = sdf.parse(membershipStartDateStr);
        		} catch (ParseException e) {
        			log.error("Error  ",e);
        		}
        	}
        	
        	JSONArray promosJson = null;
        	double totalDiscount = 0d, totalMonthlyDiscountAmount = 0d;
    		if(promotionMapStr != null && !promotionMapStr.equals("")){
    			promosJson = JSONArray.fromObject(promotionMapStr);
    			
    			if(promosJson != null && promosJson.size() > 0){
    				for (Object promoObj : promosJson) {
    					if(promoObj != null && !promoObj.toString().equals("") && !promoObj.toString().equals("undefined")){
    						JSONObject promoJson = JSONObject.fromObject(promoObj.toString());
    						totalDiscount += Double.parseDouble(promoJson.get("discountValue").toString());
    						totalMonthlyDiscountAmount += Double.parseDouble(promoJson.get("monthlyDiscountAmount").toString());
    					}
    				}
    			}
    		}
        	
        	double signupAmount = computeAmount("forSignup", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
    		double payerAmount = computeAmount("forPayer", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
    		double invoiceAmount = computeAmount("forInvoice", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
    		double paymentAmount = computeAmount("forPayment", isRecurring, signupPrice, sumOfAdditives, signUpPromoDiscount, otherPromoDiscount, faAmount, totalMonthlyDiscountAmount, finalAmount);
    		
    		log.info(" finalAmount "+finalAmount+", signupPrice "+signupPrice+", registrationFee "+registrationFee+", deposit "+deposit+" joinFeeAmt "+joinFeeAmt);
    		log.info(" promotionMapStr "+promotionMapStr+", signUpPromoDiscount "+signUpPromoDiscount+", otherPromoDiscount "+otherPromoDiscount+", sumOfAdditives "+sumOfAdditives+" isRecurring "+isRecurring);
    		log.info(" billDate "+billDate+", dueDate "+dueDate+", nextBillDate "+nextBillDate+", invoiceDate "+invoiceDate+" billDateOnInvoice "+billDateOnInvoice+", dueDateOnInvoice "+dueDateOnInvoice);
    		
    		
        	//User user = new User();     	
        	//User accUser = new User();  
        	//accUser = account.getUserLst().get(0);
    		StringBuffer draftCycle = null;
    		String draftCycleStr = null;
        	Signup signup = new Signup(); 
			boolean isYouthUserAdult = false;			
        	ItemDetail itemDetail  =  itemDetailDao.getById(Long.parseLong(account.getProductId()));
        	if(isRecurring != null && isRecurring.equals("true") && priceOption != null && itemDetail.getDueDateOffset() != null){
        		draftCycle = new StringBuffer();
        		draftCycle.append(priceOption).append(",").append(itemDetail.getDueDateOffset());
        		draftCycleStr = draftCycle.toString();
        	}
        	String userId = null;                 
        	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        	userId = auth.getName();

        	if(userId != null && !"anonymousUser".equals(userId)){
        		try {
        			List<User> userLst = new ArrayList<User>();
        			userLst = populateUserLstData(account);	        		
        			Account accountVO = accountDao.getByEmail(userId);
        			model.addAttribute("account", accountVO);
        			User primaryContact = null;
        			primaryContact = getUserByAccount(accountVO, primaryContact);	    		  
        			List<User> saveUserLst = new ArrayList<User>();
        			List<User> savedUserLst = new ArrayList<User>();
        			List<User> membershipUserLst = new ArrayList<User>();

					User primaryUser = null;
					if(userLst.size() > 1 && isYouthMemberAdult(itemDetail, userLst.get(1))){
						isYouthUserAdult =  true;
						primaryUser = userLst.get(1);
						userLst = new ArrayList<User>();
						userLst.add(primaryUser);					
					}else{
						isYouthUserAdult = false;					
					}					
        			for(User u : userLst){
        				List<User> dbUsrLst = null;
        				dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirth(u.getFirstName(), u.getLastName(), u.getDateOfBirth());
        				/*if(isAdultByDob(u.getDateOfBirth())){
        					dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirthAndFormattedPhoneNumber(u.getFirstName(), u.getLastName(), u.getDateOfBirth(), u.getFormattedPhoneNumber());
        				}else{
        					dbUsrLst = userDao.getByFirstNameAndLastNameAndDateOfBirth(u.getFirstName(), u.getLastName(), u.getDateOfBirth());
        				}*/
        				
        				if(dbUsrLst != null && !dbUsrLst.isEmpty()){
        					dbUsrLst.get(0).setMembershipAgeCategory(u.getMembershipAgeCategory());
        					membershipUserLst.add(dbUsrLst.get(0));
        				}else{
        					saveUserLst.add(u);
        				}					
        			}
        			
        			if(saveUserLst != null && !saveUserLst.isEmpty()){		        		            
        				for(User usr : saveUserLst){
        					usr.setCustomer(accountVO);
        					User dbUser = userDao.save(usr);
        					AccountContact accContact = accountContactService.saveAccountContact(accountVO, dbUser);
        					savedUserLst.add(dbUser);
        				}		        	
        			}	
        			membershipUserLst.addAll(savedUserLst);
        			account.setAccountId(accountVO.getAccountId());
        			//JetPayPayment jetPayPayment = new JetPayPayment();
        			//PaymentMethod paymentMethod  =  populatePaymentMethodData(account, account.getTransId());
        			//paymentMethod = paymentMethodDao.save(paymentMethod);
        			//signup = populateSignupData(account, primaryUser, itemDetails,signup,  opptyId, paymentMethod);
        			//List<Signup> signupList =  populateSignupDataLst(account, membershipUserLst, itemDetail,  opptyId, paymentMethod);
        			List<Signup> inactivateSignupLst =  signupDao.getByCustomerAndStatusAndType(account, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
        			PaymentMethod paymentMethod = null;
        			if(inactivateSignupLst != null && !inactivateSignupLst.isEmpty()){
        				
        				for(Signup sign : inactivateSignupLst){	  
        					if(sign.getPaymentMethod() != null){
        						paymentMethod = sign.getPaymentMethod();
        					}
        					sign.setPaymentMethod(null);
        					Calendar calendar = Calendar.getInstance(); // this would default to now
        					if(sign != null && sign.getMembersshipFeeNextBillingDate() != null){
        						calendar.setTime(sign.getMembersshipFeeNextBillingDate());
        					}else{
        						calendar.setTime(calendar.getTime());
        					}                			
                			calendar.add(Calendar.DAY_OF_MONTH, -1);        			
                			sign.setProgramEndDate(calendar.getTime());
        					sign.setStatus(SignupStatusEnum.Inactive.toString());
                			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
        					signupDao.save(sign);
        				}	
        			}
        	        				
        			Date nextBillDateObj =  null;
    	            if(nextBillDate != null){
    	            	try{
    	            		nextBillDateObj = new Date(nextBillDate);
    	            	}catch(Exception e1){
    	            		//e1.printStackTrace();
    	            	}
    	            }
        			List<Signup> signupList =   populateSignupDataLst(account, membershipUserLst, itemDetail,  opptyId, paymentMethod, String.valueOf(paymentAmount), discountAmount, faAmount, signupPrice, setUpFee, registrationFee,  deposit,
        					 faPercentage, faStartDate, faEndDate, joinFeeAmt, membershipStartDate, nextBillDateObj, draftCycleStr);
					
        			List<Signup> savedSignupList = new ArrayList<Signup>();
        			int count = 0;
        			Signup parentSignupObj= null;
        			for(Signup signupSave : signupList){
        				if(itemDetail != null && itemDetail.getRecordName() != null && Constants.MEMBERSHIP_PRODUCT_YOUTH.equals(itemDetail.getRecordName())){
        					if(count != 0 || isYouthUserAdult){
        						signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());
        						signupSave.setPaymentMethod(paymentMethod);
        						if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
        							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
        						}        								
        						parentSignupObj = signupDao.save(signupSave);
        						savedSignupList.add(parentSignupObj);
        					}
        				}else{
        					signupSave.setPortalLastModifiedBy(getPortalLastModifiedBy());
        					signupSave.setParentSignUp(parentSignupObj);
        					if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
    							signupSave = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signupSave, true);
    						}		
        					Signup signup2 = signupDao.save(signupSave); 
        					savedSignupList.add(signup2);
        				}
        				count++;
        			}
        			
        			paymentService.saveSignupPromos(savedSignupList.get(0), promosJson);
        			
        			/*for(Signup signupSave : signupList){
        				Signup signup2 = signupDao.save(signupSave);  
        			}*/         
        					
        			//User userTemp = new User();           
        			/*List<String> amountLst =  new ArrayList<String>();
        			amountLst.add("");
        			amountLst.add("");
        			amountLst.add(finalAmount);*/
        			JSONObject amountLst = new JSONObject();
    	            //amountLst.put("itemprice", finalAmount);    	            
    	            amountLst.put("itemprice", finalAmount);
    	            amountLst.put("itempriceOnPayer", payerAmount);
    	            amountLst.put("isRecurring", "true");
    	            
        			Payer payer = paymentService.savepayer(paymentMethod.getId().toString(), accountVO, amountLst, itemDetail, savedSignupList.get(0), null); 
        			Invoice invoice = null;
        			Date currentDate =  new Date();
        			/*List<String> amountLst1 =  new ArrayList<String>();
        			amountLst1.add("");
        			amountLst1.add("");
        			amountLst1.add("");
        			amountLst1.add("");
        			amountLst1.add(finalAmount);*/
        			JSONObject amountLst1 = new JSONObject();
        			amountLst1.put("itemprice", finalAmount);
        			amountLst1.put("fa", "0");  
        			amountLst1.put("itempriceOnInvoice", invoiceAmount);
        			amountLst1.put("isRecurring", "true"); 
        			
        			if(payer != null && payer.getEnddate() != null && currentDate.before(payer.getEnddate())){
        				invoice = paymentService.saveinvoice(accountVO, amountLst1, primaryContact, savedSignupList.get(0), payer);
        			}else if(payer.getEnddate() == null){
        				invoice = paymentService.saveinvoice(accountVO, amountLst1, primaryContact, savedSignupList.get(0), payer);
        			}
        			JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), accountVO,  primaryContact, savedSignupList.get(0),payer,invoice, paymentAmount);			   	
        			// added on 24072015 - if FA amount on invoice > 0, create payment record of type "Credit Memo/FA Waiver" and amount will be FA_AMOUNT
        			Object fa = amountLst1.get("fa");
        			if(fa!=null && Double.parseDouble(fa.toString())>0){
        				String paymentMode = PaymentTypeEnum.Debit.toString();
    					String paymentType = PaymentTypeEnum.CreditMemoFAWaiver.toString();
    					paymentService.savepayment(paymentMethod.getId().toString(), "", account.getTransId(), accountVO,  primaryContact, savedSignupList.get(0),payer,invoice,paymentMode,Double.parseDouble(fa.toString()),paymentType);
    				}
        			//end 
        			
        			Activity activity = getActivityData(accountVO, membershipUserLst.get(0), savedSignupList.get(0));
        			interactionDao.save(activity);
        			//Interaction interaction = interactionDao.save(populateInteractionData(savedSignupList.get(0), account, userDb));
        			model.addAttribute("account", account);    	            	        
        			model.addAttribute("primaryUser", membershipUserLst);
        								
        			Signup s1 = savedSignupList.get(0);
        			if(s1.getFinalAmount() != null && Double.valueOf(s1.getFinalAmount()) <= 0){
        				s1.setFinalAmount(finalAmount);
        			}
        			
        			model.addAttribute("signup", savedSignupList.get(0));
        			model.addAttribute("product", itemDetail);
        			model.addAttribute("paymentTransId", account.getTransId());
        			model.addAttribute("prodJoinFee", account.getJoiningFee());
        			model.addAttribute("productPrice", account.getProductPrice());		        
        			/*List<User> kidsInfo = new ArrayList<User>();
        			for(User usr : userLst){	    		
        				if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
        					model.addAttribute("secUser", usr);
        				}
        				if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
        					model.addAttribute("thirdUser", usr);
        				}
        				if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
        					kidsInfo.add(usr);
        				}
        						
        			}*/  
        			List<User> kidsInfo = new ArrayList<User>();
        			List<User> adultUserLstInfo = new ArrayList<User>();
        			boolean isSecUser = false;
        			boolean isPrimaryUser = false;
        			//boolean isThirdUser = false;
        			/*for(User usr : userLst){	    
        				if(isAdultByDob(usr.getDateOfBirth()) || isYouthByDob(usr.getDateOfBirth())){						
        					if(!isPrimaryUser){
        						model.addAttribute("primaryUser", usr);
        						isPrimaryUser = true;
        					}else if(!isSecUser){
        						model.addAttribute("secUser", usr);
        						isSecUser = true;
        					}else{
        						model.addAttribute("thirdUser", usr);
        					}
        					adultUserLstInfo.add(usr);
        				}else{
        					kidsInfo.add(usr);
        				}   							
        			}
        			model.addAttribute("kidsInfo", kidsInfo); */
        			
        			for(Signup sign : savedSignupList){
        				if(sign != null && sign.getContact() != null && StringUtils.isNotBlank(sign.getMembershipAgeCategory())){
        					if(sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Adult.toString()) || sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Youth.toString())){
        						if(sign.getPaymentMethod() != null){
            						model.addAttribute("primaryUser", sign.getContact());
            						isPrimaryUser = true;
            					}else if(!isSecUser){
            						model.addAttribute("secUser", sign.getContact());
            						isSecUser = true;
            					}else{
            						model.addAttribute("thirdUser", sign.getContact());
            					}
            					adultUserLstInfo.add(sign.getContact());
        					}else if(sign.getMembershipAgeCategory().equals(MembershipAgeCategoryEnum.Kid.toString()) ){
            					kidsInfo.add(sign.getContact());
            				}else{
            					kidsInfo.add(sign.getContact());
            				} 
        				}
        			}
        			model.addAttribute("kidsInfo", kidsInfo);
        			model.addAttribute("isYouthUserAdult", isYouthUserAdult);
        			return new ModelAndView("changeMembershipConfirmation", model.asMap());
        		}catch(Exception e){
        			log.error("Error  ",e);    	        	
        			model.addAttribute("errMsg", "Error Occured in Registration Process");
        			return new ModelAndView("login", model.asMap());
        		}
        	}
        }catch(Exception e){
        	log.error("Error  ",e);
        }
        return new ModelAndView("changeMembershipConfirmation", model.asMap());
	}

	/*private Signup populateNewSignupData(Signup signup) {
		Signup newSignup =  new Signup();
		newSignup.setContactName(signup.getContactName());
		newSignup.setFinalAmount(signup.getFinalAmount());
		newSignup.setLocation(signup.getLocation());
		newSignup.setMembersshipFeeNextBillingDate(signup.getMembersshipFeeNextBillingDate());
		newSignup.setProgramStartDate(signup.getProgramStartDate());
		newSignup.setProgramEndDate(signup.getProgramEndDate());
		newSignup.setSignupDate(signup.getSignupDate());
		newSignup.setStatus(SignupStatusEnum.Active.toString());
		newSignup.setType(signup.getType());
		newSignup.setContact(signup.getContact());
		newSignup.setCustomer(signup.getCustomer());
		newSignup.setItemDetail(signup.getItemDetail());
		newSignup.setPaymentMethod(signup.getPaymentMethod());
		newSignup.setSignUpProductType(signup.getSignUpProductType());
		newSignup.setOpptyId(signup.getOpptyId());
		newSignup.setMembersshipFeeNextBillingDate(signup.getMembersshipFeeNextBillingDate());
    	
    	return newSignup;
		
	}*/
	
	 private List<Signup> populateSignupDataLst(Account account, List<User> userSet, ItemDetail item,  String opptyId, PaymentMethod paymentMethod,
			 String finalAmount, String discountAmount, String faAmount, String signupPrice, String setUpFee, String registrationFee,  String deposit,
			 String faPercentage, String faStartDate, String faEndDate, String joinFeeAmt, Date membershipStartDate, Date nextBillDate, String draftCycle) {
		 List<Signup> signupLst =  new ArrayList<Signup>();
		 for(User user : userSet){
			 Signup signup =  new Signup();
			 signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
	    	if(account.getSignup() != null && !account.getSignup().isEmpty()){
	    		signup = account.getSignup().get(0);
	    	}    	
	    	signup.setContact(user);
	    	signup.setCustomer(accountDao.findByAccountId(account.getAccountId()));
	    	signup.setItemDetail(item);
	    	
	    	signup.setContactName(user.getFullName());
	    	signup.setType(ProductTypeEnum.MEMBERSHIP.toString());
	    	signup.setStatus(SignupStatusEnum.Active.toString());
	    	signup.setOpptyId(opptyId);
	    	signup.setSignUpProductType(account.getSignUpProductType());
	    	signup.setSignupDate(membershipStartDate);   
	    	signup.setNextBillDate(nextBillDate);
	    	if(draftCycle != null){
	    		signup.setDraftCycle(draftCycle);
	    	}
	    	if(account.getMembershipFrequency() != null && Constants.MEMBERSHIP_FREQUENCY_ANNUAL.equals(account.getMembershipFrequency() )){
	    		Calendar cal = Calendar.getInstance();    
	    		cal.setTime(membershipStartDate);
	    		cal.add(Calendar.YEAR, 1); 
	    		signup.setProgramEndDate(cal.getTime());
	    		signup.setPaymentFrequency(Constants.MEMBERSHIP_FREQUENCY_ANNUAL);
	    		signup.setSignUpPricingOption(Constants.MEMBERSHIP_FREQUENCY_ANNUAL);
	    	}
	    	signup.setProgramStartDate(new Date());	    	
	    	if(account.getMembershipFrequency() != null && Constants.MEMBERSHIP_FREQUENCY_MONTHLY.equals(account.getMembershipFrequency() )){
	    		Calendar cal = Calendar.getInstance();    	
	    		cal.setTime(membershipStartDate);
	    		cal.add(Calendar.MONTH, 1); // to get previous year add -1
	    		signup.setMembersshipFeeNextBillingDate(cal.getTime());   
	    		signup.setPaymentFrequency(Constants.MEMBERSHIP_FREQUENCY_MONTHLY);
	    		signup.setSignUpPricingOption(Constants.MEMBERSHIP_FREQUENCY_MONTHLY);
	    	}
	    	Location location = new Location();
			if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
				location = locationDao.getOne(Long.parseLong(account.getLocationId()));
			}
	    	signup.setLocation(location);	    	
	    	
			// final amount
			signup.setFinalAmount(finalAmount);
			// discount amount
			signup.setDiscountAmount(discountAmount);
			if(!StringUtils.isBlank(faAmount)){
				signup.setFAamount(Double.parseDouble(faAmount));
			}else{
				signup.setFAamount(0D);
			}
			if(!StringUtils.isBlank(signupPrice)){
				signup.setSignupPrice(Double.parseDouble(signupPrice));
			}else{
				signup.setSignupPrice(0D);
			}
			if(!StringUtils.isBlank(setUpFee)){
				signup.setSetUpFee(Double.parseDouble(setUpFee));
			}else{
				signup.setSetUpFee(0D);
			}
			if(!StringUtils.isBlank(registrationFee)){
				signup.setRegistrationFee(Double.parseDouble(registrationFee));
			}else{
				signup.setRegistrationFee(0D);
			}
			if(!StringUtils.isBlank(deposit)){
				signup.setDeposit(Double.parseDouble(deposit));
			}else{
				signup.setDeposit(0D);
			}
			if(!StringUtils.isBlank(joinFeeAmt)){
				signup.setJoinFee(Double.parseDouble(joinFeeAmt));
			}else{
				signup.setJoinFee(0D);
			}
			
			
			signup.setFApercentage(faPercentage);
			signup.setMembershipAgeCategory(user.getMembershipAgeCategory());
			//signup.setFAstartDate(faStartDate);
			//signup.setFAendDate(faEndDate);
			Calendar cal = Calendar.getInstance();	
			signup.setLastUpdated(cal);			
	    	signupLst.add(signup);
	    	
	    	// check stop confirmed signup flag
	    	if(item != null && item.getAutomatedWaitlist() != null){
	    		if(item.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_No)){
	    			itemDetailDao.save(item);
	    		}
	    	}
		}
    	if(userSet != null && !userSet.isEmpty() && signupLst != null && !signupLst.isEmpty()){
    		signupLst.get(0).setPaymentMethod(paymentMethod);				
		}	    	
	    return signupLst;		
	}
	 
	 @RequestMapping(value="holdMembershipInfo", method = RequestMethod.POST)
	 public @ResponseBody String holdMembershipInfo(@RequestParam(value="startMonth") String startMonth, @RequestParam(value="numberOfMonths") String numberOfMonths, final HttpServletRequest request, final HttpServletResponse response) {
		 JSONArray json = new JSONArray();
		 JSONObject obj = new JSONObject();
		 Date currentDate =  new Date();
		 Double holdFee = 0D;
		 
		 if(startMonth != null && numberOfMonths != null){
		 	SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
						
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String userId = ((UserDetails) a.getPrincipal()).getUsername();			
			
			Account account = null;	    	
	    	if(userId != null && !"".equals(userId)){
		    	account = accountDao.getByEmail(userId);    	
	    	}   
	    	
	    	if(account != null && account.getSignup() != null && !account.getSignup().isEmpty()){
	    		if(account.getSignup().get(0).getItemDetail() != null && account.getSignup().get(0).getItemDetail().getId() != null){
	    			List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(account.getSignup().get(0).getItemDetail().getId(), Constants.Status_Active, Constants.Status_Active);						
					 if(pricingRuleLst != null && !pricingRuleLst.isEmpty()){
						 for(ItemDetailPricingRule itemDetailPricingRule : pricingRuleLst){
							 if(itemDetailPricingRule.getPricingRule() != null && itemDetailPricingRule.getPricingRule().getType() != null && Constants.SIGNUP_PRICING_RULE_HOLD.equals(itemDetailPricingRule.getPricingRule().getType())){
								 holdFee += Double.valueOf(itemDetailPricingRule.getPricingRule().getTierPrice());
							 }
						 }
						 
					 }
	    		}
	    		
	    	}
	    	
		 		
	 		for(Signup signup : account.getSignup()){
	    		if(signup != null && signup.getMembersshipFeeNextBillingDate() != null ){
	    			try{
	    				if(signup.getHoldStartDate() != null & signup.getHoldEndDate() != null){
	    					Calendar holdStartDateCal = Calendar.getInstance();  
			    			holdStartDateCal.setTime(signup.getHoldStartDate());
			    			int holdStartDateYear = holdStartDateCal.get(Calendar.YEAR);
			    					    			
			    			Calendar holdEndDateCal = Calendar.getInstance();  
			    			holdEndDateCal.setTime(signup.getHoldEndDate());
			    			int holdEndDateYear = holdEndDateCal.get(Calendar.YEAR);
			    			
			    			Calendar currentDateCal = Calendar.getInstance();  
			    			int currentYear = currentDateCal.get(Calendar.YEAR);
			    			if(holdStartDateYear == currentYear || holdEndDateYear == currentYear){
			    				obj.put("Error", "You have already used your hold for current year. You can not add hold for current year");
			    			}else if(signup.getHoldStartDate().before(currentDate) && signup.getHoldEndDate().before(currentDate)){
			    				
			    				int selectedMonth = Integer.parseInt(startMonth);
			    				selectedMonth = selectedMonth - 1;	    				
			    				
				    			Calendar holdStartDateCalUpdated = Calendar.getInstance();  
				    			holdStartDateCalUpdated.setTime(signup.getMembersshipFeeNextBillingDate());
				    			
				    			Calendar holdEndDateCalUpdated = Calendar.getInstance();  
				    			holdEndDateCalUpdated.setTime(signup.getMembersshipFeeNextBillingDate());
				    			
				    			int signupBillingMonth = holdStartDateCalUpdated.get(Calendar.MONTH);
				    			int noOfMonthsToAdd = (selectedMonth - signupBillingMonth);
				    			
				    			if(noOfMonthsToAdd >0 ){
				    				holdStartDateCalUpdated.add(Calendar.MONTH, noOfMonthsToAdd);
				    				holdEndDateCalUpdated.add(Calendar.MONTH, noOfMonthsToAdd + Integer.parseInt(numberOfMonths));
				    				signup.setHoldStartDate(holdStartDateCalUpdated.getTime());
				    				signup.setHoldEndDate(holdEndDateCalUpdated.getTime());
				    			}else{
				    				holdEndDateCalUpdated.add(Calendar.MONTH, Integer.parseInt(numberOfMonths));
				    				signup.setHoldStartDate(holdStartDateCalUpdated.getTime());
				    				signup.setHoldEndDate(holdEndDateCalUpdated.getTime());
				    			}   	
				    			signup.setHoldFee(holdFee);
				    			signup.setLastUpdated(Calendar.getInstance());
				    			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
				    			signupDao.save(signup); 	
				    			
				    			obj.put("holdStartDate", format1.format(holdStartDateCalUpdated.getTime()));
				    			obj.put("holdEndDate", format1.format(holdEndDateCalUpdated.getTime()));
				    			obj.put("holdFee", holdFee);
			    			}
	    				}else{	    				
		    				int selectedMonth = Integer.parseInt(startMonth);
		    				selectedMonth = selectedMonth - 1;	    				
		    				
			    			Calendar holdStartDateCalUpdated = Calendar.getInstance();  
			    			holdStartDateCalUpdated.setTime(signup.getMembersshipFeeNextBillingDate());
			    			
			    			Calendar holdEndDateCalUpdated = Calendar.getInstance();  
			    			holdEndDateCalUpdated.setTime(signup.getMembersshipFeeNextBillingDate());
			    			
			    			int signupBillingMonth = holdStartDateCalUpdated.get(Calendar.MONTH);
			    			int noOfMonthsToAdd = (selectedMonth - signupBillingMonth);
			    			
			    			if(noOfMonthsToAdd >0 ){
			    				holdStartDateCalUpdated.add(Calendar.MONTH, noOfMonthsToAdd);
			    				holdEndDateCalUpdated.add(Calendar.MONTH, noOfMonthsToAdd + Integer.parseInt(numberOfMonths));
			    				signup.setHoldStartDate(holdStartDateCalUpdated.getTime());
			    				signup.setHoldEndDate(holdEndDateCalUpdated.getTime());
			    			}else{
			    				holdEndDateCalUpdated.add(Calendar.MONTH, Integer.parseInt(numberOfMonths));
			    				signup.setHoldStartDate(holdStartDateCalUpdated.getTime());
			    				signup.setHoldEndDate(holdEndDateCalUpdated.getTime());
			    			}   		
			    			signup.setHoldFee(holdFee);
			    			signup.setLastUpdated(Calendar.getInstance());
			    			signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
			    			signupDao.save(signup); 	
			    			
			    			obj.put("holdStartDate", format1.format(holdStartDateCalUpdated.getTime()));
			    			obj.put("holdEndDate", format1.format(holdEndDateCalUpdated.getTime()));
			    			obj.put("holdFee", holdFee);
		    			}
		    			
	    			}catch(Exception e){
	    				obj.put("Error", "There was some error. Please try again later");
	    				
	    			}			
	    		}    		
	    	}
	 		json.add(obj);
			return json.toString();
		 
		 }else{
			 obj.put("Error", "There was some error. Please try again later");
			 json.add(obj);		
			 return json.toString();
		 }
	 }
	 
	 public boolean isAdultByDob(Date dobDate){			 
			boolean isAdult = false;
			if(dobDate != null){
				Calendar dob = Calendar.getInstance();  
				dob.setTime(dobDate);  
				Calendar today = Calendar.getInstance();  
				int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
				if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;  
				} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;  
				}		
				
				int adultAgeLowerLimit = 23;
				int adultAgeUpperLimit = 64;
				
				List<SystemProperty> adultAgeLowerLst = new ArrayList<SystemProperty>();			
				adultAgeLowerLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
				if(adultAgeLowerLst != null && !adultAgeLowerLst.isEmpty()){
					adultAgeLowerLimit = Integer.parseInt(adultAgeLowerLst.get(0).getKeyValue());				
				}
	
				List<SystemProperty> adultAgeUpperLst = new ArrayList<SystemProperty>();			
				adultAgeUpperLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_UPPER_LIMIT);
				if(adultAgeUpperLst != null && !adultAgeUpperLst.isEmpty()){
					adultAgeUpperLimit = Integer.parseInt(adultAgeUpperLst.get(0).getKeyValue());				
				}
				//if(age > adultAgeLowerLimit && age < adultAgeUpperLimit){
				if(age > adultAgeLowerLimit){
					isAdult = true;
				}
			}else{
				isAdult = false;
			}
			return isAdult;
		}	
		
		public boolean isChildByDob(Date dobDate){	
			boolean isChild = false;
			if(dobDate != null){
				Calendar dob = Calendar.getInstance();  
				dob.setTime(dobDate);  
				Calendar today = Calendar.getInstance();  
				int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
				if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;  
				} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;  
				}		
				int adultAgeLimit = 12;
				List<SystemProperty> childAgeLst = new ArrayList<SystemProperty>();			
				childAgeLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_KIDS_AGE_VALIDATION);
				if(childAgeLst != null && !childAgeLst.isEmpty()){
					adultAgeLimit = Integer.parseInt(childAgeLst.get(0).getKeyValue());				
				}
				if(age < adultAgeLimit){
					isChild = true;
				}
			}else{
				isChild = false;
			}
			
			return isChild;
		}		
		
		public boolean isYouthByDob(Date dobDate){	
			boolean isYouth = false;
			if(dobDate != null){
				Calendar dob = Calendar.getInstance();  
				dob.setTime(dobDate);  
				Calendar today = Calendar.getInstance();  
				int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
				if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
				  age--;  
				} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
				    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
				  age--;  
				}	
				int youthAgeLowerLimit = 9;
				int youthAgeUpperLimit = 14;
				List<SystemProperty> youthAgeLowerLimitLst = new ArrayList<SystemProperty>();		
				youthAgeLowerLimitLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_LOWER_LIMIT);
				if(youthAgeLowerLimitLst != null && !youthAgeLowerLimitLst.isEmpty()){
					youthAgeLowerLimit = Integer.parseInt(youthAgeLowerLimitLst.get(0).getKeyValue());				
				}
				List<SystemProperty> youthAgeUpperLimitLst = new ArrayList<SystemProperty>();		
				youthAgeUpperLimitLst = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_YOUTH_AGE_VALIDATION_UPPER_LIMIT);
				if(youthAgeUpperLimitLst != null && !youthAgeUpperLimitLst.isEmpty()){
					youthAgeUpperLimit = Integer.parseInt(youthAgeUpperLimitLst.get(0).getKeyValue());				
				}
				
				if(youthAgeLowerLimit < age && age < youthAgeUpperLimit){
					isYouth = true;
				}
			}else{
				isYouth = false;
			}
			return isYouth;
		}
		
		
		
 //Trial pass product selection page
	    
	    @ModelAttribute
	    @RequestMapping(value= "trailPassReg", method = RequestMethod.GET)
	    public ModelAndView trailPassRegShowForm() {	
	    	
	        Model model = new ExtendedModelMap();
	        try {            
	            model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
	            model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea("Bay Area"));
	            model.addAttribute("account", new Account());
	        } catch (Exception se) {
	            System.out.println("Error occoured while querying Product");
	            log.error("Error  ",se);
	        }
	        return new ModelAndView("trialPassReges", model.asMap());    	
		}
	    
	  // return product according to location.
	    @RequestMapping(value="getTrailProductDetailsByLocation", method=RequestMethod.GET)
	    public @ResponseBody String  getTrailPassProductDetailsByLocation(@RequestParam(value="locationId") Long locationId, final HttpServletRequest request, final HttpServletResponse response) { 		
	    	try{
	    		//List<ItemDetail> productDetails =  itemDetailDao.findByType_AndSubType_AndlocationId("Trial Pass", "Trail Pass",locationId);
	    		//List<ItemDetail> productDetails =  itemDetailDao.findByTypeAndSubTypeAndLocation("Trial Pass", "Trail Pass",locationId);
	    		List<Object[]> productDetails =  itemDetailDao.getTrialPassProgramByLocation(locationId);
	    		
	    		
	    		
	    		JSONArray json = new JSONArray();    		
	    		for(Object product : productDetails){
	    			Object objArr1[] = (Object[]) product;	
	    			if(objArr1 != null && objArr1.length >0){
	    				JSONObject obj = new JSONObject();
	    				if(objArr1[0] != null){
	    					obj.put("id", objArr1[0].toString());
	    				}
	    				if(objArr1[1] != null){
	    					obj.put("productName", objArr1[1].toString());
	    				}
						if(objArr1[2] != null){
							obj.put("productDescription", objArr1[2].toString());			
						}
						if(objArr1[3] != null){
							obj.put("productType", objArr1[3].toString());
						}
						/*if(objArr1[4] != null){
							obj.put("productDuration", objArr1[4].toString());
						}*/	
						
						/*if(objArr1[6] != null ){
							//Locations location = (Locations) objArr1[6];
							List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Tier(Long.parseLong(objArr1[7].toString()), Constants.Status_Active, Constants.Status_Active, objArr1[6].toString());
							
							double price = 0D;			
							double joiningPrice = 0D;
							double tierPrice = 0D;
							double allAreaPrice = 0D;
							double bayPrice = 0D;
							StringBuffer sb = new StringBuffer();
							for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
								tierPrice += pricingRule.getPricingRule().getTierPrice();
								joiningPrice = pricingRule.getPricingRule().getJoiningFee();
								price += tierPrice + joiningPrice;
								allAreaPrice = allAreaPrice + pricingRule.getPricingRule().getAllBranchPrice();
								bayPrice += pricingRule.getPricingRule().getBayAreaPrice();
								sb.append(pricingRule.getPricingRule().getRuleName()+":"+pricingRule.getPricingRule().getTierPrice()+";;");
							}
							Double discount = new Double(0);
							if(objArr1[7] != null){							
								try { 
									List<ItemDetailPromotion> ipdLst =  itemPromoDetailsDao.getAutoApplyPromoDiscount(Long.parseLong(objArr1[7].toString()));
									for(ItemDetailPromotion ipd : ipdLst){
										Promotion promo = ipd.getPromotion();								
										if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_VALUE)){
											discount = promo.getDiscountvalue();
											  
										}
										else if(promo.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_PERCENTAGE)){
											BigDecimal memberdiscountAmount = promo.getDiscountpercentage().multiply(BigDecimal.valueOf(tierPrice));
											memberdiscountAmount = memberdiscountAmount.divide(new BigDecimal(100));
											discount = memberdiscountAmount.doubleValue();									
										}
									}
								}catch (Exception e) {
									// TODO Auto-generated catch block
									log.error("Error  ",e);
								}
							}
							obj.put("itemDetailsId", objArr1[7].toString());
							obj.put("productPrice", price);						
							obj.put("pricingJsonArray", sb.toString());	
							obj.put("joiningPrice", joiningPrice);	
							obj.put("tierPrice", tierPrice);	
							obj.put("allAreaPrice", allAreaPrice);
							obj.put("bayAreaPrice", bayPrice);	
							if(objArr1[1] != null && allBranchMap.get(objArr1[1].toString()) != null){
								obj.put("allAreaPrice", allBranchMap.get(objArr1[1].toString()));
							}else{
								obj.put("allAreaPrice", null);
							}
							if(objArr1[1] != null && bayAreaBranchMap.get(objArr1[1].toString()) != null){
								obj.put("bayAreaPrice", bayAreaBranchMap.get(objArr1[1].toString()));
							}else{
								obj.put("bayAreaPrice", null);
							}
							obj.put("autoPromoDiscount", discount);						
						}*/
						if(objArr1[4] != null){
							obj.put("tandc", objArr1[5].toString());
						}
						obj.put("productPrice", 0.0);						
						obj.put("pricingJsonArray", 0.0);	
						obj.put("joiningPrice", 0.0);	
						obj.put("tierPrice", 0.0);	
						obj.put("allAreaPrice", 0.0);
						obj.put("bayAreaPrice", 0.0);	
						json.add(obj);
	    			}  			
	    		System.out.println(json.toString());
	    		return json.toString(); 
	    	
	    		} 
	    	}
	    	
	    		
	    
	    	catch(Exception e){    		
	    		System.out.println("Error occoured while querying Product");
	            log.error("Error  ",e);        		
	    	}
			return null;    	
	    }
	    
	    
	    //on Trial Pass Register .
	    @RequestMapping(value="/becomeTPMemberRegister", method = RequestMethod.POST)
	    public ModelAndView onSubmitTrialPassRegisterform(Account account, final BindingResult errors, final HttpServletRequest request, final HttpServletResponse response)
	            throws Exception {        	
	    	String opptyId = request.getParameter("opptyId"); 
	    	 
	    	
	   // System.out.println(account.getProductId()+"--"+ account.getItemDetailsId());
	    	//System.out.println(account.getEmail()+"---- "+account.getFirstName()+"---- "+account.getAddressLine1()+"---- "+account.getProductId());
	    	List<User> userLst = new ArrayList<User>();
	    	User user = new User();     	
	    	User accUser = new User(); 
	    	AccountContact accountContact=new AccountContact();
	    	Opportunity opportunity=new Opportunity();
	    	OpportunityRevenue opportunityRevenue=new OpportunityRevenue();
	    	accUser = account.getUserLst().get(0);
	    	//System.out.println(accUser.getPersonFirstName()+"---- "+accUser.getPrimaryFormattedPhoneNumber()+"---- "+accUser.getPrimaryEmailAddress());
	    	Signup signup = new Signup();
	    	ItemDetail itemDetail  =  itemDetailDao.getById(Long.parseLong(account.getProductId())); 
	    	//System.out.println(itemDetail.getDescription());
	    	  	
	    //	System.out.println(accUser.getPersonFirstName());
	    		Account isAccountExist = accountDao.getByEmail(accUser.getPrimaryEmailAddress());    		
	    		if(isAccountExist != null){
	    			//try{
	    			Calendar LastUpdated = Calendar.getInstance();
			    	isAccountExist.setLastUpdated(LastUpdated);
	    			User userDb = getUserByAccount(isAccountExist, user);
	    			//System.out.println(userDb.getEmailAddress());
	    			//System.out.println(userDb.getCustomer().getAccountId());
	    			//System.out.println(userDb.getContactId());
	    			Signup signupTPchk=signupDao.findByCustomerAndContactAndStatusAndType(isAccountExist,userDb,SignupStatusEnum.Active.toString(), Constants.TRIAL_PASS);
	    			//List<Signup> signuplist=signupDao.getByCustomerAndContactAndStatusAndType(isAccountExist,userDb, "ACTIVE", "MEMBERSHIP");
	    			//Signup signup=signuplist.get(0);
	    			//System.out.println(signupTPchk.getContactName());
	    			//System.out.println(signupTPchk.getProgramStartDate());
	    		//	System.out.println(signupTPchk.getProgramEndDate());
	    			
	    			Calendar cal1_EndDate = Calendar.getInstance();
	    	    	Calendar cal2_CurrentDate = Calendar.getInstance();
	    	    	//Date d1=new Date();
	    	    	//d1=signup.getProgramEndDate();
	    	    //	cal1_EndDate.setTime(d1);
	    	    	cal1_EndDate.setTime(signupTPchk.getProgramEndDate());
	       	    //	System.out.println(cal1_EndDate);
	    	    	
	    	    	cal2_CurrentDate.add(Calendar.YEAR, -1);
	    	    	
	    	    	//System.out.println(cal2_CurrentDate);
	    	    	
	    	    	
	    	    	/*if(cal1_EndDate.after(cal2_CurrentDate)){
	    	    		System.out.println("Date1 is after Date2");
	    	    		
	    	    		System.out.println("u have aleredy used ur trial pass in this year");
	    	    	}*/
	    	    //	Model model = new ExtendedModelMap();
	    	    	//end date=03/03/13  and current date - 12 mnth= 03/03/14 (curr daate= 03/03/15)
	    	    //	if(cal1_EndDate.before(cal2_CurrentDate) && cal1_EndDate.equals(cal2_CurrentDate)){
	    	    	if(cal1_EndDate.before(cal2_CurrentDate) || cal1_EndDate.equals(cal2_CurrentDate)){
	    	    		//System.out.println("Date1 is before Date2");
	    	    		//System.out.println( "trial pass expired so u can reg for it " );
	    	    		
	    	    		
	    	    	//}

	    	    		Model model = new ExtendedModelMap();		
	    			signup = populateTrialPassSignupData(isAccountExist, userDb, itemDetail,signup,  opptyId, null);  
	    			try {   
	    				System.out.println("if  existing user dn in try blk");
	    	            Account acc = accountDao.saveAndFlush(isAccountExist);
	    	            User user2 = userDao.saveAndFlush(userDb);
	    	            Signup signup2 = signupDao.save(signup);            
	    	          //  Model model = new ExtendedModelMap();
	    	    		if(acc != null){	    		    	    	
	    	    	        model.addAttribute("account", isAccountExist);    	            	        
	    	    	        model.addAttribute("primaryUser", userDb);
	    	    	        model.addAttribute("signup", signup2);
	    	    	        model.addAttribute("product", itemDetail);
	    	    	      
	    	    	        
	    	    		}
	    	    		
	    	            
	    	        } catch (Exception ade) {
	    	        	log.error("Error  ",ade);
	    	        	//Model model = new ExtendedModelMap();
	    	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
	    				return new ModelAndView("login", model.asMap());
	    	        }
	    			return new ModelAndView("trial_pass_confirmationSumary", model.asMap());
	    	    	}
	    	    	
	    	    	else{
	    	    		Model model = new ExtendedModelMap();
	    	    		//String msg="You have already used your trial pass...!!";
	    	    		model.addAttribute("alreadyUSedTP", "You have already used your trial pass...!!");
	    	    		return new ModelAndView("trialPassErr", model.asMap());
	    	    		
	    	    	}
	    	    	//return new ModelAndView("trial_pass_confirmationSumary", model.asMap());
	    			//}
	    		/*catch (Exception ade) {
	    	        	adlog.error("Error  ",e);
	    	        	Model model = new ExtendedModelMap();
	    	        	model.addAttribute("errMsg", "You have already used ur Trial Pass.");
	    				return new ModelAndView("login", model.asMap());
	    			}*/
	    
	    		}else{
	    			userLst = populatTrialPasseUserLstData(account); 
	    			User primaryUser = userLst.get(0);
	    	    	populateContactAndCustomerWaiversAndTC(primaryUser, account);   	    		    	
	    	    	signup = populateTrialPassSignupData(account, primaryUser, itemDetail,signup,  opptyId, null); 
	    	    	
	    	    	//userLst.add(user);
	    	    	account.setUser(new HashSet<User>());
	    	    	
	    	    	Model model = new ExtendedModelMap();
	    	    	List<User> kidsInfo = new ArrayList<User>();
	    	    	for(User usr : userLst){
	    	    		account.getUser().add(usr); 
	    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "1".equals(usr.getUsrSequence())){
	    	    			model.addAttribute("secUser", usr);
	    	    		}
	    	    		if(usr.getIsAdult() != null && "true".equals(usr.getIsAdult()) && usr.getUsrSequence() != null && "2".equals(usr.getUsrSequence())){
	    	    			model.addAttribute("thirdUser", usr);
	    	    		}
	    	    		if(usr.getIsAdult() != null && "false".equals(usr.getIsAdult())){
	    	    			kidsInfo.add(usr);
	    	    		}
	    	    		   		
	    	    	}    	    	   	
	    	    	account.setEmail(primaryUser.getPrimaryEmailAddress());
	    	    	account.setName(primaryUser.getFullName());
	    	    	Calendar LastUpdated = Calendar.getInstance();
			    	account.setLastUpdated(LastUpdated);
	    	    	  
	    	        try {              	
	    	            Account acc = accountDao.saveAndFlush(account);
	    	            Signup signup2 = signupDao.save(signup); 
	    	            System.out.println(signup2.getSignupId());
	    	            opportunity=populateOpprtunityforTrialPAss(account,primaryUser,signup2);
	        	    	//opportunityRevenue=populateOppRevenueforTrialPass(opportunity, itemDetail);
	        	    	accountContact=accountContactService.saveAccountContact(account, primaryUser);
	    	            
	    	    		if(acc != null){	    		    	    	
	    	    	        model.addAttribute("account", account);    	            	        
	    	    	        model.addAttribute("primaryUser", primaryUser);
	    	    	        model.addAttribute("kidsInfo", kidsInfo);    	    	        
	    	    	        model.addAttribute("signup", signup2);
	    	    	        model.addAttribute("product", itemDetail);
	    	    	       /* model.addAttribute("paymentTransId", account.getTransId());
	    	    	        model.addAttribute("prodJoinFee", account.getJoiningFee());
	    	    	        model.addAttribute("productPrice", account.getProductPrice());
	    	    	        if(account.getJoiningFee() != null && account.getProductPrice() != null){
	    	    	        	double prodJoinFee = Double.parseDouble(account.getJoiningFee());
	    	    	        	double prodPrice = Double.parseDouble(account.getProductPrice());
	    	    	        	double totalPrice = prodJoinFee+ prodPrice;
	    	    	        	model.addAttribute("productTotalPrice", totalPrice);
	    	    	        }*/
	    	    		}
	    	    		//System.out.println("user rreg");
	    	    		return new ModelAndView("trial_pass_confirmationSumary", model.asMap());
	    	            
	    	        } catch (Exception ade) {
	    	        	log.error("Error  ",ade);
	    	        	Model model1 = new ExtendedModelMap();
	    	        	model.addAttribute("errMsg", "Error Occured in Registration Process");
	    				return new ModelAndView("login", model1.asMap());
	    	        }
	    		}   	
	    	    	
	    }
	    
	    
	    
	    private List<User> populatTrialPasseUserLstData(Account account) { 
			List<User> userLst =  new ArrayList<User>();
			userLst = account.getUserLst();
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			for(User usr : userLst){
				usr.setEnabled(true);	
				usr.setAccountExpired(false);
				usr.setAccountLocked(false);
				usr.setCredentialsExpired(false);
				usr.setEnabled(true);
				usr.setActive(true);
				usr.setPrimary(true); 
				usr.setMember(true);
				Calendar LastUpdated = Calendar.getInstance();
		    	usr.setLastUpdated(LastUpdated);
		    	if(account != null  && StringUtils.isBlank(getAgentUidFromSession())){
		    		usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()));
		    	}
		    	
				//usr.setPassword(bCryptPasswordEncoder.encode(userLst.get(0).getPassword()));
				usr.setfNameAndLName(usr.getFullName());
				Location location = new Location();
				if(account.getLocationId() != null && !"".equals(account.getLocationId().trim())){
					location = locationDao.getOne(Long.parseLong(account.getLocationId()));
				}
				/*location.setLocationId(Long.parseLong(account.getLocationId()));*/
				usr.setLocation(location);
				if(usr.getGender().equals("Male")){
					usr.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
				}else{
					usr.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
				}
		    	//user.setDateOfBirth(new Date());	
				Address address =  new Address();
				address.setPrimaryAddressLine1(account.getAddressLine1());
				address.setPrimaryAddressLine2(account.getAddressLine2());
				address.setPrimaryAddressCity(account.getCity());
				address.setPrimaryAddressProvince(account.getState());
				address.setPrimaryAddressPostalCode(account.getPostalCode());			
				address.setPrimaryAddressCountry(Constants.COUNTRY_USA);
				usr.setAddress(address);
			}
			/*if(userLst != null && !userLst.isEmpty()){
				if(account != null && account.getLoggedInUserEmailId().equals("") ){
					userLst.get(0).setPassword(bCryptPasswordEncoder.encode(userLst.get(0).getPassword()));
				}
				userLst.get(0).setPrimary(true);
				userLst.get(0).setMember(true);
				
			}*/
	    	return userLst;
		}
		
	    
	    private Signup populateTrialPassSignupData(Account account, User contact, ItemDetail item, Signup signup, String opptyId, PaymentMethod paymentMethod) {
			// TODO Auto-generated method stub   
	    	List<SystemProperty> trailPassExpiryLimit =systemPropertyDao.getPropertyByKeyName(Constants.TRIAL_PASS_EXPIRY_LIMIT);
			  Integer trialPassLimit=Integer.parseInt(trailPassExpiryLimit.get(0).getKeyValue());
			  System.out.println("trial pass validity"+trialPassLimit );
	    	
	    	if(account.getSignup() != null && !account.getSignup().isEmpty()){
	    		signup = account.getSignup().get(0);
	    	}    	
	    	signup.setContact(contact);
	    	signup.setCustomer(account);
	    	signup.setItemDetail(item);
	    	signup.setPaymentMethod(paymentMethod);
	    	signup.setContactName(contact.getFullName());
	    	signup.setType(Constants.TRIAL_PASS);
	    	signup.setStatus(SignupStatusEnum.Active.toString());
	    	signup.setOpptyId(opptyId);
	    	signup.setSignUpProductType(account.getSignUpProductType());
	    	signup.setSignupDate(new Date());  
	    	signup.setSetUpFee(0.00);
	    	Calendar LastUpdated = Calendar.getInstance();
	    	signup.setLastUpdated(LastUpdated);
	    	/*if(account.getMembershipFrequency() != null && "Annual".equals(account.getMembershipFrequency() )){
	    		Calendar cal = Calendar.getInstance();    		
	    		cal.add(Calendar.YEAR, 1); // to get previous year add -1
	    		signup.setProgramEndDate(cal.getTime());
	    	}*/
	    	Calendar cal = Calendar.getInstance();    		
			//cal.add(Calendar.WEEK_OF_YEAR,Integer.parseInt(item.getDuration())); // to get previous year add -1
	    	cal.add(Calendar.YEAR, trialPassLimit);
	    	Date endDateAfter1year=cal.getTime();
			signup.setProgramEndDate(endDateAfter1year);
			signup.setCancelDate(endDateAfter1year);
	    	signup.setProgramStartDate(new Date());
	    	if(account.getProductPrice() != null){
	    		signup.setFinalAmount(account.getProductPrice());
	    	}else{
	    		signup.setFinalAmount("0");
	    	}
	    	if(account.getMembershipFrequency() != null && "Monthly".equals(account.getMembershipFrequency() )){
	    		Calendar cal2 = Calendar.getInstance();    		
	    		cal2.add(Calendar.MONTH, 1); // to get previous year add -1
	    		signup.setMembersshipFeeNextBillingDate(cal.getTime());    		
	    	}
	    	
	    	
	    	return signup;		
		}

	    
	    private Opportunity populateOpprtunityforTrialPAss(Account account,User primaryUser,Signup signup) {
			// TODO Auto-generated method stub
	    	Opportunity opportunity=new Opportunity();
	    	if(account!=null && primaryUser!=null){
	    		opportunity.setContact_c(primaryUser.getContactId().toString());
	    		opportunity.setCustomerAccountId_c(account.getAccountId().toString());
	    		opportunity.setName(account.getName());
	    		opportunity.setSignup(signup);
	    		opportunity.setType(Constants.TRIAL_PASS);
	    		Calendar LastUpdated = Calendar.getInstance();
		    	opportunity.setLastUpdated(LastUpdated);
	    		
	    	
	    	}
	    	Opportunity opportunity2=opportunityDao.save(opportunity);
			return opportunity2;
		}
	    
	    
	    private OpportunityRevenue populateOppRevenueforTrialPass(Opportunity opportunity,ItemDetail itemDetail){
	    	OpportunityRevenue opportunityRevenue=new OpportunityRevenue();
	    	if(opportunity!=null && itemDetail!=null){
	    		
	    		opportunityRevenue.setItemDetail(itemDetail);
	    		opportunityRevenue.setOpportunity(opportunity);
	    		Calendar LastUpdated = Calendar.getInstance();
		    	opportunityRevenue.setLastUpdated(LastUpdated);
	    	}
	    	OpportunityRevenue revenue=opportunityRevenueDao.save(opportunityRevenue);
	    	return revenue;
	    }
	    
	    @RequestMapping(value = "getActiveSignupDataByContact", method = RequestMethod.GET)
		public @ResponseBody String getActiveSignupDataByContact(final HttpServletRequest request,	final HttpServletResponse response) {
			String contact_id = request.getParameter("contact_id");
			if (contact_id == null || "".equals(contact_id)) {
				return null;
			}
			Authentication a = SecurityContextHolder.getContext()
					.getAuthentication();
			String userId = ((UserDetails) a.getPrincipal()).getUsername();			
			Account account = null;
			User contact = null;			
			if (userId != null && !"".equals(userId)) {
				account = accountDao.getByEmail(userId);
				contact = userDao.getByContactId(Long.parseLong(contact_id));
				List<Signup> activeSignupLst =  signupDao.getByCustomerAndContactAndStatusAndType(account,contact, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
				if (activeSignupLst != null && !activeSignupLst.isEmpty()) {
					return activeSignupLst.get(0).getMembershipAgeCategory();					
				}else{
					return null;
				}
			}

			return null;
		}
	
	    @RequestMapping(value="/getMembershipProductDetail", method=RequestMethod.GET)
	    public @ResponseBody String getMembershipProductDetail(@RequestParam String itemDetailId, final HttpServletRequest request, final HttpServletResponse response) {
			 //JSONArray json = new JSONArray();
	    	JSONObject obj = new JSONObject();
	    	
			 if(itemDetailId != null && Long.parseLong(itemDetailId) > 0){
				 
				 ItemDetail itemDetail  =  itemDetailDao.getById(Long.parseLong(itemDetailId));
				 
				 List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(Long.parseLong(itemDetailId), Constants.Status_Active, Constants.Status_Active);
				 
				 double signupMonthlyTierPrice = 0, signupMonthlyJoinPrice = 0, signupAnnualTierPrice = 0, signupAnnualJoinPrice = 0, registrationPrice = 0, depositPrice = 0;
				 String signupMonthlyBillingFrequency = null, signupAnnualBillingFrequency = null, registrationBillingFrequency = null, depositBillingFrequency = null;
				 for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
			    		if(pricingRule!=null && pricingRule.getPricingRule()!=null){
		    				if(pricingRule.getPricingRule().getType() != null && Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){    				    					
		    					if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_MONTHLY)){
		    						signupMonthlyTierPrice = signupMonthlyTierPrice + pricingRule.getPricingRule().getTierPrice();
		    						signupMonthlyJoinPrice = signupMonthlyJoinPrice + pricingRule.getPricingRule().getJoiningFee();
		    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
		    							signupMonthlyBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
		    					}else if(pricingRule.getPricingRule() != null && pricingRule.getPricingRule().getPriceOption() != null && pricingRule.getPricingRule().getPriceOption().equals(Constants.MEMBERSHIP_FREQUENCY_ANNUAL)){
		    						signupAnnualTierPrice = signupAnnualTierPrice + pricingRule.getPricingRule().getTierPrice();
		    						signupAnnualJoinPrice = signupAnnualJoinPrice + pricingRule.getPricingRule().getJoiningFee();
		    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
		    							signupAnnualBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();    				    			
		    					}
		    				}else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
	    						registrationPrice = registrationPrice + pricingRule.getPricingRule().getTierPrice();
	    						if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
	    							registrationBillingFrequency = pricingRule.getPricingRule().getBillingFrequency();
		    				}
		    				else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
		    					depositPrice = depositPrice + pricingRule.getPricingRule().getTierPrice();
		    					if(pricingRule.getPricingRule().getBillingFrequency() != null && pricingRule.getPricingRule().getBillingFrequency().equals(Constants.BillingFrequency_Recurring))
		    						depositBillingFrequency = pricingRule.getPricingRule().getBillingFrequency(); 
		    				}      				    							    				
			    		}
			    	}
				 
				JSONArray signuppriceArr = new JSONArray();
				 
				JSONObject signUpPriceMonthly = new JSONObject();
				signUpPriceMonthly.put("Monthly", signupMonthlyTierPrice);
				signUpPriceMonthly.put("joiningPrice", signupMonthlyJoinPrice);
				signUpPriceMonthly.put("billingFrequency", signupMonthlyBillingFrequency);
				JSONObject signUpPriceAnnual = new JSONObject();
				signUpPriceAnnual.put("Annual", signupAnnualTierPrice);
				signUpPriceAnnual.put("joiningPrice", signupAnnualJoinPrice);	
				signUpPriceAnnual.put("billingFrequency", signupAnnualBillingFrequency);
				
				JSONObject registrationPriceJson = new JSONObject();
				registrationPriceJson.put("registrationPrice", registrationPrice);
				registrationPriceJson.put("billingFrequency", registrationBillingFrequency);
				JSONObject depositPriceJson = new JSONObject();	  
				depositPriceJson.put("depositPrice", depositPrice);
				depositPriceJson.put("billingFrequency", depositBillingFrequency);
				
				signuppriceArr.add(signUpPriceMonthly);
				signuppriceArr.add(signUpPriceAnnual);				
				signuppriceArr.add(registrationPriceJson);
				signuppriceArr.add(depositPriceJson);
				 
				 List<WaiversAndTC> waiversAndTC = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
				 
				 
				 System.out.println(" itemDetail.getRecordName()   "+itemDetail.getRecordName());
				 System.out.println(" itemDetail.getType()   "+itemDetail.getType());

				 
				 obj.put("RecordName",itemDetail.getRecordName());
				 obj.put("Description",itemDetail.getDescription());
				 obj.put("billDateOption",itemDetail.getBillDate());
				 obj.put("billDateOffset",itemDetail.getBillDateOffset());
				 obj.put("dueDateOption",itemDetail.getDueDate());
				 obj.put("dueDateOffset",itemDetail.getDueDateOffset());
				 obj.put("signupMonthlyTierPrice",signupMonthlyTierPrice);
				 obj.put("signupMonthlyJoinPrice",signupMonthlyJoinPrice);
				 obj.put("signupMonthlyBillingFrequency",signupMonthlyBillingFrequency);
				 obj.put("signupAnnualTierPrice",signupAnnualTierPrice);
				 obj.put("signupAnnualJoinPrice",signupAnnualJoinPrice);
				 obj.put("signupAnnualBillingFrequency",signupAnnualBillingFrequency);
				 obj.put("registrationPrice",registrationPrice);
				 obj.put("registrationBillingFrequency",registrationBillingFrequency);
				 obj.put("depositPrice",depositPrice);
				 obj.put("depositBillingFrequency",depositBillingFrequency);
				 obj.put("tAndC",waiversAndTC.get(0).getTcDescription());
				 obj.put("ProductType",itemDetail.getType());
				 obj.put("signupPrice", signuppriceArr);
				 Location loc = itemDetail.getLocation();
				 obj.put("location_RecordName",loc.getRecordName());
				 obj.put("productLocationId",itemDetail.getLocationId());
				 System.out.println("  loc.getRecordName "+loc.getRecordName());
				 
				 //json.add(obj);
			 }
			 return obj.toString();
		}

	    public double computeAmount(String forField, String isRecurring, String signupPrice, String sumOfAdditives, String signUpPromoDiscount, String otherPromoDiscount, String faAmount, double totalMonthlyDiscountAmount, String finalAmount){
	    	double amount = 0, sumOfAddi = 0, signupDiscount = 0, otherDiscount = 0, faAmt = 0, signupPric = 0;
	    	
	    	if(sumOfAdditives != null && !sumOfAdditives.equals("")){
	    		sumOfAddi = Double.parseDouble(sumOfAdditives);
    		}
	    	if(signUpPromoDiscount != null && !signUpPromoDiscount.equals("")){
	    		signupDiscount = Double.parseDouble(signUpPromoDiscount);
    		}
	    	if(otherPromoDiscount != null && !otherPromoDiscount.equals("")){
	    		otherDiscount = Double.parseDouble(otherPromoDiscount);
    		}
	    	if(faAmount != null && !faAmount.equals("")){
	    		faAmt = Double.parseDouble(faAmount);
    		}
	    	if(signupPrice != null && !signupPrice.equals("")){
	    		signupPric = Double.parseDouble(signupPrice);
    		}
	    	
	    	if(forField != null){
	    		if(forField.equalsIgnoreCase("forSignup")){
	    			if(isRecurring != null && isRecurring.equals("true")){
	    				amount = signupPric;
	    	    	}else{
	    	    		amount = sumOfAddi - (signupDiscount + otherDiscount) - faAmt;
	    	    	}
	    		}else if(forField.equalsIgnoreCase("forPayer")){
	    			if(isRecurring != null && isRecurring.equals("true")){
	    				amount = signupPric; 
	    	    	}else{
	    	    		amount = sumOfAddi - (signupDiscount + otherDiscount) - faAmt;
	    	    	}
	    		}else if(forField.equalsIgnoreCase("forInvoice")){
	    			if(isRecurring != null && isRecurring.equals("true")){
	    				amount = sumOfAddi - totalMonthlyDiscountAmount; 
	    	    	}else{
	    	    		amount = sumOfAddi - (signupDiscount + otherDiscount);
	    	    	}
	    		}else if(forField.equalsIgnoreCase("forPayment")){
	    			if(finalAmount != null){
	    	    		amount = Double.parseDouble(finalAmount);
	        		}
	    		}
	    	}
	    	
	    	return amount;
	    }
	    
	    @RequestMapping(value="/viewUpdMembershipPM", method=RequestMethod.GET)
		 public ModelAndView viewUpdatePM(final HttpServletRequest request, final HttpServletResponse response) {
				Model model = new ExtendedModelMap();
				JSONArray payerArr = new JSONArray();
				List<PaymentMethod> paymentMethodList = null;
				try{
					String signupId = request.getParameter("sId");
					
					if(!"".equals(signupId)){
						
				    	Signup signup = signupDao.getOne(Long.parseLong(signupId));
					
						List<Payer> lstPayer = payerDao.findBySignup(signup);
						/*Payer p =  new Payer();
						p.setId(1l);
						
						List<Invoice> invoices = new ArrayList<Invoice>();
						Invoice i = new Invoice();
						i.setInvoiceId(101l);
						invoices.add(i);
						
						p.setInvoices(invoices);
						lstPayer.add(p);*/
						
						if(lstPayer.size()>0){
							
							SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
						    
							for(Payer payer: lstPayer){
								JSONObject payObj = new JSONObject();
								payObj.put("payerId", payer.getId());
								payObj.put("payerAmount", payer.getAmount());
								payObj.put("payerType", payer.getType());
								
								PaymentMethod pm = payer.getPaymentMethod();
								if(pm != null){
									payObj.put("payerPMId", pm.getId());
								}else if(payer.getPaymentMode() != null){
									payObj.put("payerPM", payer.getPaymentMode());
								}
								
								List<Invoice> invoices = invoiceDao.findByPayer(payer);
								
								JSONArray invoiceArr = new JSONArray();
								
								if(invoices != null && !invoices.isEmpty()){
									
									for (Invoice invoice : invoices) {
										JSONObject invoiceObj = new JSONObject();
										
										invoiceObj.put("invoiceId", invoice.getInvoiceId());
										if(invoice.getInvoiceDate() != null)
										invoiceObj.put("invoiceDate", sdf.format(invoice.getInvoiceDate()));
										if(invoice.getDueDate() != null)
										invoiceObj.put("invoiceDueDate", sdf.format(invoice.getDueDate()));
										if(invoice.getBillDate() != null)
										invoiceObj.put("invoiceBillDate", sdf.format(invoice.getBillDate()));
										invoiceObj.put("invoiceAmount", invoice.getAmount());
										
										PaymentMethod ipm = invoice.getPaymentMethod();
										
										if(ipm != null){
											invoiceObj.put("invoicePMId", ipm.getId());	
										}else if(invoice.getPaymentMethod() != null){
											invoiceObj.put("invoicePM", invoice.getPaymentMode());
										}
										invoiceArr.add(invoiceObj);
									}
								}
								
								payObj.put("invoices", invoiceArr);
								if(StringUtils.isBlank(getAgentUidFromSession()) && payer.getType() != null && payer.getType().equalsIgnoreCase(Constants.SELF)){
									payerArr.add(payObj);
								}
								if(!StringUtils.isBlank(getAgentUidFromSession())){
									payerArr.add(payObj);
								}
							}
						}
					
						if(signup.getCustomer() != null){
							paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(signup.getCustomer().getAccountId());
						}
					}
					
					model.addAttribute("paymentInfoLst" , paymentMethodList);
					model.addAttribute("payerList", payerArr);
					model.addAttribute("signupId", signupId);
					
				}catch(Exception e){
					e.printStackTrace();
				}
				
				return new ModelAndView("membershipHomePaymentMethod", model.asMap());
		 }
	    
}

