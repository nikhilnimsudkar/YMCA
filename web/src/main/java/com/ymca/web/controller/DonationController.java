package com.ymca.web.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.DonationDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.OpportunityDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Activity;
import com.ymca.model.Address;
import com.ymca.model.Donation;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Location;
import com.ymca.model.Opportunity;
import com.ymca.model.OpportunityRevenue;
import com.ymca.model.Payer;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.PricingRule;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.enums.BillingOptionEnum;
import com.ymca.web.enums.OpportunityStatusEnum;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupFrequencyEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.model.ResponseObject;
import com.ymca.web.service.AccountContactService;
import com.ymca.web.service.PaymentService;
import com.ymca.web.service.SignUpService;
import com.ymca.web.util.Constants;

@Controller
//@RequestMapping("/donate*")
public class DonationController extends BaseController{
	
	private SecureRandom random = new SecureRandom();
	
	@Resource
	private DonationDao donationDao;
	
	//@Resource
	//private ProgramDao programDao;
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private AccountContactDao accountContactDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource 
	private SignupDao signupDao;
	
	@Resource
	private PaymentService paymentService;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private AccountContactService accountContactService;
	
	@Resource
	private OpportunityDao opportunityDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	@Resource
	private SignUpService signUpService;
	  
    @RequestMapping(value="/donation/createDonation",method = RequestMethod.GET)
    public ModelAndView createDonation(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	  	
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		Model model = new ExtendedModelMap();
		Account account = null;
		User user = new User();	
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);	
			user = getUserByAccount(account,user);			
			model.addAttribute("usInfo", user);
			model.addAttribute("donationInfo", new Donation()); 
		} else {
			model.addAttribute("errMsg",com.ymca.web.util.Constants.NO_RECORDS_FOUND);
		}
		return new ModelAndView("donationForm", model.asMap());
	}
	
	   
    @RequestMapping(value="/donation/createDonation",method = RequestMethod.POST)
    public @ResponseBody String createDonationSubmit(Donation donation, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	 
		try{
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String userId = ((UserDetails) a.getPrincipal()).getUsername();			
			Account account = null;
			User user = new User();	
			if (userId != null && !"".equals(userId)) {
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);	
				donation.setCustomer(account);
				donation.setContact(user);				
				if(donation.getDonationAmtStr()	!= null && !"".equals(donation.getDonationAmtStr().trim())){
					donation.setDonationAmount(Double.valueOf(donation.getDonationAmtStr()));
				}else{
					donation.setDonationAmount(0D);
				}
				donation.setDonationDate(new Date());
				donationDao.save(donation);
			} else {
				return "NOT_FOUND";
			}
			return "SUCCESS";
		}catch(Exception e){
			//System.out.println("Error occured while Creating Donation record in Database");
			log.error("Error in Create Donation ",e);
			return "FAIL";
		}
		
	}
	   
    @ModelAttribute
    @RequestMapping(value="/create",method = RequestMethod.POST)
    protected ResponseObject createDonation(Donation donation) throws Exception {
    	ResponseObject responseObj = new ResponseObject();
    	//responseObj.setStatus(YMCAConstants.STATUS_SUCCESS);
    	/*if(donation.getAccountId() != null){
    		try{
    		//	donationDao.saveDonationDetails(donation);
    		}catch(Exception e){
    			//responseObj.setStatus(YMCAConstants.STATUS_ERROR);
    		}
    	}*/
    	responseObj.setMessage("Successfully created donation details");
		return responseObj; 
    }
    
    @ModelAttribute
    @RequestMapping(value="/{donationId}",method = RequestMethod.GET)
    protected ResponseObject getDonationById(@PathVariable Long donationId) throws Exception {
    	Donation donation = donationDao.getOne(donationId);
    	ResponseObject responseObject = new ResponseObject();
    	responseObject.setData(donation);
    	return responseObject;
    }
    
    @ModelAttribute
    @RequestMapping(value="/",method = RequestMethod.PUT)
    protected ResponseObject updateDonationDetails(Donation donation) throws Exception {
    	ResponseObject responseObject = new ResponseObject();
    	if(donation.getDonationId() != null){
    		//donationDao.updateDonationDetails(donation);
    	}else{
        	responseObject.setMessage("Invalid donation Id.");
        	
        	
        	//responseObject.setStatus(YMCAConstants.STATUS_ERROR);
    	}
    	responseObject.setMessage("Successfully updated Donation details.");
		return responseObject; 
    }
    
    @ModelAttribute    
    @RequestMapping(value="/getDonationByCustomername/{customerName}",method = RequestMethod.GET)
    protected ModelAndView getDonationByCustomername(@PathVariable String customerName,@ModelAttribute("Donation")Donation donation) throws Exception {
    	//ResponseObject responseObject = new ResponseObject();
    	List<Donation> donationList = null;
    	if(customerName != null){
    		//donationList = getDonationService().getDonationByCustomername(customerName);
    		donationList=donationDao.getByCustomerName(customerName);
    	}
    	//responseObject.setData(donationList);
    	//return responseObject;
    	ModelAndView mav = new ModelAndView("read", "donationList",
    			donationList);
		return mav;
    }
    
    
    
    @ModelAttribute
    @RequestMapping(value="/getDonationByAccountId/{accountId}",method = RequestMethod.GET)
	public ResponseObject getDonationByAccountId(@PathVariable String accountId) throws Exception {
    	ResponseObject responseObject = new ResponseObject();
		List<Donation> donationList = null;
		if(accountId != null){
			//donationList = getDonationService().getDonationByAccountId(Long.valueOf(accountId));
			
		}
		responseObject.setData(donationList);
		return responseObject;
	} 
    
    @ModelAttribute    
    @RequestMapping(value="/getDonation",method = RequestMethod.GET)
    protected ModelAndView getDonationList(@ModelAttribute("Donation")Donation donation) throws Exception {
    	//ResponseObject responseObject = new ResponseObject();
    	List<Donation> donationList = null;
    	//if(customerName != null){
    		//donationList = getDonationService().getDonationByCustomername(customerName);
    		donationList=donationDao.findAll();
    				//}
    	//responseObject.setData(donationList);
    	//return responseObject;
    	ModelAndView mav = new ModelAndView("read", "donationList",donationList );
		return mav;
    }
    
    //@ModelAttribute    
    //@RequestMapping(value="/getProgram",method = RequestMethod.POST, final HttpServletRequest request, final HttpServletResponse response)
    //protected ModelAndView getProgramList(@ModelAttribute("Program")Program program) throws Exception {
/*    @ModelAttribute   
    @RequestMapping(value="/getProgram",method = RequestMethod.POST)
    public ModelAndView getProgramList(@ModelAttribute("Program")Program program, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	
    	Account account = accountDao.getByEmail(request.getParameter("Username"));
		User user =  null;
		Model model = new ExtendedModelMap();
		if(account !=null && account.getUser() != null){
			for (Iterator<User> it = account.getUser().iterator(); it.hasNext(); ) {
	    		user = it.next();
					        
		    }
			if(user != null && user.getUsername() !=null && user.getPassword() != null){
				if(user.getPassword().equals(request.getParameter("Password"))){	    	
			    	//ResponseObject responseObject = new ResponseObject();
			    	List<Program> programList = null;
			    	//if(customerName != null){
			    		//donationList = getDonationService().getDonationByCustomername(customerName);
			    	programList=programDao.findAll();
			    	
			    	List<Donation> donationList = null;
			    	//if(customerName != null){
			    		//donationList = getDonationService().getDonationByCustomername(customerName);
			    		donationList=donationDao.findAll();
			    	
			    	//ModelAndView mav = new ModelAndView("customer_home", "programList",programList );
			    	
			        try {
			            model.addAttribute("programList", programList);
			            model.addAttribute("donationList", donationList);
			            
			            HttpSession session = request.getSession();			            
			            session.setAttribute("accountInfo", account);
			            session.setAttribute("userInfo", user);
			            
			            //request.setAttribute("accountInfo", account);
			            //request.setAttribute("userInfo", user);
			        } catch (Exception se) {
			            //System.out.println("Error occoured while querying Product");
			            log.error("Error  ",e);
			        }
			        return new ModelAndView("customer_home", model.asMap());
				}else{
					model.addAttribute("errMsg", "Please enter the correct Email or password");
					return new ModelAndView("login", model.asMap());
				}
			}
		}
		//return mav;
		return null;
    }   */
    
    @ModelAttribute
    @RequestMapping(value="donationHome",method = RequestMethod.GET)
    protected ModelAndView donationHome() throws Exception {  
    	Model model = new ExtendedModelMap();
    	String userId = null;                 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();
        Account account =  null;
        //List<Signup> donationLst = new ArrayList<Signup>();
        List<Payment> paymentLst = new ArrayList<Payment>();
        if(userId != null && !"anonymousUser".equals(userId)){
			account = accountDao.getByEmail(userId);
			//donationLst =  signupDao.getByCustomerIdAndTypeDonation(account.getAccountId());				
			//paymentLst = paymentDao.getByCustomerIdAndTypeDonation(account.getAccountId());
			List<Payment> donationLst = paymentDao.getByCustomerIdAndTypeDonation(account.getAccountId());		
			List<Activity> activityLst =  activityDao.getByCustomerIdAndTypeDonation(account.getAccountId());
			Map<Long, Activity> activityMap = new HashMap<Long, Activity>();			
			for(Activity activity : activityLst){
				activityMap.put(activity.getSignup().getSignupId(), activity);
			}
			Map<Long, Signup> signupMap = new LinkedHashMap<Long, Signup>();
			//if(activityLst != null && !activityLst.isEmpty()){
				for(Payment payment : donationLst){					
					if(payment !=  null && payment.getSignup() != null && payment.getSignup().getSignupId() != null){
						if(activityMap.containsKey(payment.getSignup().getSignupId()) && !paymentLst.contains(payment)){
							payment.setIsCancelled(true);
						}
						if(!paymentLst.contains(payment)){
							paymentLst.add(payment);
						}
						if(!signupMap.containsKey(payment.getSignup().getSignupId())){
							if(activityMap.containsKey(payment.getSignup().getSignupId())){
								payment.getSignup().setIsCancelled(true);
							}
							if(payment.getSignup().getItemDetail() != null && payment.getSignup().getItemDetail().getEndDate() != null && payment.getSignup().getItemDetail().getCancellationCutOffPeriod() != null){
								Calendar endDateCal = Calendar.getInstance();
								endDateCal.setTime(payment.getSignup().getItemDetail().getEndDate());
								endDateCal.add(Calendar.DATE, -payment.getSignup().getItemDetail().getCancellationCutOffPeriod());
								Date today = new Date();
								if(today.before(endDateCal.getTime())){
									payment.getSignup().setIsCancelEnabled(true);
								}
							}
							signupMap.put(payment.getSignup().getSignupId(), payment.getSignup());
							
						}
					}					
				}
			List signupIdLst = new ArrayList(signupMap.values());
			model.addAttribute("donationSignupMap", signupIdLst);
			model.addAttribute("donationList", paymentLst);
        }
    	return new ModelAndView("donationHome", model.asMap());
    }
    
    @RequestMapping(value="annualDonationDonate",method = RequestMethod.GET)
    public ModelAndView annualDonationDonate(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	 
    	try{
	    	String userId = null;                 
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        userId = auth.getName();
			Model model = new ExtendedModelMap();
			Account account = null;
			User user = new User();
			List<PaymentMethod> paymentMethodList = null;
			
			if(userId != null && !"anonymousUser".equals(userId)){
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);				
				model.addAttribute("account", account);
				model.addAttribute("usInfo", user);
				model.addAttribute("donationInfo", new Donation()); 
				
				List<User> userLst =  new ArrayList<User>();
				List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
				for(AccountContact ac : accountContactLst){
					if(ac.getEndDate() == null){
						userLst.add(ac.getContact());
					}				
				}
				model.addAttribute("contactLst", userLst);
				paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
		        model.addAttribute("paymentInfoLst" , paymentMethodList);
				
				List<PaymentMethod> creditCardPaymentMethodLst = paymentMethodDao.getCreditCardInfoByAccountId(account.getAccountId());
				List<PaymentMethod> achPaymentMethodLst = paymentMethodDao.getACHInfoByAccountId(account.getAccountId());
				
				model.addAttribute("payMethodLstCredit", creditCardPaymentMethodLst);
				model.addAttribute("payMethodLstACH", achPaymentMethodLst); 
				
			} else {			
				
			}
			List<Account> accList = accountDao.getByCustomerType(Constants.ACCOUNT_ORGANIZATION);
			model.addAttribute("employerLst",accList);	
			
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
			Map<Long, Location> locationMap = new HashMap<Long, Location>();
			List<Location> distinctLocationLst = new ArrayList<Location>();
			try{			
				List<Object> locations  =  locationDao.findByItemDetailAndStatusOrderByRecordNameAsc(startDate, endDate);	
				List<Location> lstLocationData = new ArrayList<Location>();
				for (Object location : locations) {
					Object objArr1[] = (Object[]) location;
					Location locationData = new Location();
					if (objArr1 != null && objArr1.length > 0) {						
						if (objArr1[0] != null) {
							locationData.setId(Long.parseLong(objArr1[0].toString()));
						}
						if (objArr1[1] != null) {
							locationData.setRecordName(objArr1[1].toString());
						}
						if (objArr1[2] != null) {
							locationData.setItemId(Long.parseLong(objArr1[2].toString()));
						}
						lstLocationData.add(locationData);
					}
				}
				
				for(Location loc : lstLocationData){
					if(!locationMap.containsKey(loc.getId())){
						locationMap.put(loc.getId(), loc);						
					}
				}		
				distinctLocationLst = new ArrayList(locationMap.values());
			}catch(Exception e){
				e.printStackTrace();
				log.error("Error occured while fetching location data ",e);
			}
			//model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
			model.addAttribute("locations", distinctLocationLst);
				
			model.addAttribute("signup", new Signup());
			String opptyId = request.getParameter("opptyId");
			Opportunity oppty = null;
			if(StringUtils.isNotBlank(opptyId)){
				oppty = opportunityDao.getOne(Long.valueOf(opptyId));
				model.addAttribute("donationOppty", oppty);
				//model.addAttribute("campaignerLst", userDao.getByType(Constants.ANNUAL_DONATION_CAMPAIGNER));
			}
			if(oppty != null){
				List<OpportunityRevenue> oppRevenue = oppty.getOpptyRevenue();
				if(oppRevenue != null && !oppRevenue.isEmpty()){
					ItemDetail itemDetailAnnualCampaign = oppRevenue.get(0).getItemDetail();
					if(itemDetailAnnualCampaign != null){
						Calendar cal = Calendar.getInstance();
						cal.setTime(itemDetailAnnualCampaign.getStartDate());
						cal.add(Calendar.DATE, itemDetailAnnualCampaign.getDueDateOffset());
						itemDetailAnnualCampaign.setPlanEndDate(cal.getTime());
						model.addAttribute("annualDonationItemDetailObj", itemDetailAnnualCampaign);
						List<PricingRule> pricingRuleLst = new ArrayList<PricingRule>();
						List<ItemDetailPricingRule> itemDetailPricingRuleLst = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(itemDetailAnnualCampaign.getId(), Constants.Status_Active, Constants.Status_Active, Constants.SIGNUP);
						for(ItemDetailPricingRule pricingRule: itemDetailPricingRuleLst) {	    		 
				    		if(pricingRule!=null && pricingRule.getPricingRule()!=null){							
			    				if(pricingRule.getPricingRule().getType() != null && Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){    				    					
			    					/*if(pricingRule.getPricingRule().getPriceOption() != null && 
			    							(pricingRule.getPricingRule().getPriceOption().equals(Constants.DONATION_FREQUENCY_ONE_TIME) 
			    									|| pricingRule.getPricingRule().getPriceOption().equals(Constants.DONATION_FREQUENCY_MONTHLY))){
			    						pricingRuleLst.add(pricingRule.getPricingRule());		    						
			    					}*/	
			    					pricingRuleLst.add(pricingRule.getPricingRule());
			    				}	    				    							    				
				    		}
				    	}
						model.addAttribute("pricingRuleList", pricingRuleLst);
						if(itemDetailAnnualCampaign.getAutomaticPayment() != null && Constants.AUTOMATIC_PAYMENT_REQUIRED_NO.equals(itemDetailAnnualCampaign.getAutomaticPayment())){
							model.addAttribute("AutomaticPaymentRequired", Constants.AUTOMATIC_PAYMENT_REQUIRED_NO);
						}else{
							model.addAttribute("AutomaticPaymentRequired", Constants.AUTOMATIC_PAYMENT_REQUIRED_YES);
						}
					}else{
						model.addAttribute("AutomaticPaymentRequired", Constants.AUTOMATIC_PAYMENT_REQUIRED_YES);
					}
				}
			}
			return new ModelAndView("annualDonationForm", model.asMap());
    	}catch(Exception e){
    		log.error("Error occured", e);
    		return null;
    	}
	}
    
    @RequestMapping(value="annualDonationDonate",method = RequestMethod.POST)
    public ModelAndView annualDonationDonate(Signup signup, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	 
    	Model model = new ExtendedModelMap();
		try{
			String paymentOrderId = request.getParameter("paymentOrderId");
			String paymentMethodId = request.getParameter("paymentMethodId");			
			String itemDetailId = request.getParameter("itemDetailId");
			ItemDetail itemDetailObj = null;
			if(StringUtils.isNotBlank(itemDetailId)){
				itemDetailObj = itemDetailDao.getOne(Long.valueOf(itemDetailId));				
			}			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
			String userId = auth.getName();	
			Account account = new Account();
			User user = new User();				
			User savedUser =  new User();			
			
			PaymentMethod paymentMethod = null;		
			if(userId != null && !"anonymousUser".equals(userId)){
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);	
				signup.setCustomer(account);
				if(StringUtils.isNotBlank(paymentOrderId)){
					paymentMethod =  paymentMethodDao.getByOrderNumber(paymentOrderId);
					if(paymentMethod != null){
						paymentMethod.setCustomer(account);
						paymentMethodDao.save(paymentMethod);
					}					
				}
				if(StringUtils.isNotBlank(paymentMethodId)){
					paymentMethod =  paymentMethodDao.findOne(Long.valueOf(paymentMethodId));										
				}				
				signup.setEmployer(null);
				signup.setEmployee(null);			
				if(signup != null){					
					if(signup.getEmployee() != null){
						//signup.setEmployee(signup.getEmployee());
					}					
					if(signup.getInvoices() != null && !signup.getInvoices().isEmpty()){
						signup.setProgramStartDate(signup.getInvoices().get(0).getDueDate());
						signup.setProgramEndDate(signup.getInvoices().get(signup.getInvoices().size() - 1).getDueDate());
					}else{
						signup.setProgramStartDate(new Date());
					}
					
					if(signup.getInvoices() != null && !signup.getInvoices().isEmpty() && signup.getInvoices().size()>1){
						signup.setPaymentFrequency(PaymentTypeEnum.Recurring.getValue());
						signup.setProgramEndDate(signup.getInvoices().get(signup.getInvoices().size() - 1).getDueDate());
					}else{
						signup.setPaymentFrequency(PaymentTypeEnum.NonRecurring.getValue());
					}
					

					/*if(signup.getNextBillDate() != null && signup.getTodayKendoDate() != null && signup.getNextBillDate().equals(signup.getTodayKendoDate())){
						if(signup.getInvoices() != null && !signup.getInvoices().isEmpty()){
							signup.setProgramEndDate(signup.getInvoices().get(signup.getInvoices().size() - 1).getDueDate());						
						}
						Calendar nextBillingDate = Calendar.getInstance();	
						  if(StringUtils.isNotBlank(signup.getSignUpPricingOption())){
							if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Weekly.getValue())){								
								nextBillingDate.add(Calendar.DATE, 7);
								signup.setNextBillDate(nextBillingDate.getTime());
							}else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.SemiMonthly.getValue())){
								nextBillingDate.add(Calendar.DATE, 15);
								signup.setNextBillDate(nextBillingDate.getTime());
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Monthly.getValue())){
								nextBillingDate.add(Calendar.MONTH, 1);
								signup.setNextBillDate(nextBillingDate.getTime());
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Quarterly.getValue())){
								nextBillingDate.add(Calendar.MONTH, 3);
								signup.setNextBillDate(nextBillingDate.getTime());
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Annual.getValue())){
								nextBillingDate.add(Calendar.MONTH, 12);
								signup.setNextBillDate(nextBillingDate.getTime());
							}
						}
					}*/
					
					if(signup.getInvoices() != null && !signup.getInvoices().isEmpty() && signup.getInvoices().size() >=2){		
						if(itemDetailObj == null || itemDetailObj.getCategory() == null || !itemDetailObj.getCategory().equalsIgnoreCase(Constants.ANNUAL_CAMPAIGN_SUBCATAGORY)){
							signup.setNextBillDate(signup.getInvoices().get(1).getDueDate());
		    			}else{
		    				signup.setNextBillDate(null);
		    			}
					}else{
						signup.setNextBillDate(null);
					}
					
					if(itemDetailObj != null){
						signup.setItemDetail(itemDetailObj);
					}
					if(StringUtils.isNotBlank(signup.getRecognizeAs()) && isNumeric(signup.getRecognizeAs())){
						User u = userDao.getOne(Long.valueOf(signup.getRecognizeAs()));
						u.setDoNotEmail(signup.getContact().getDoNotEmail());
						savedUser = userDao.save(u);
						signup.setContact(u);
						signup.setRecognizeAs(u.getFullName());					
					}else{
						user.setDoNotEmail(signup.getContact().getDoNotEmail());
						savedUser = userDao.save(user);
						signup.setContact(user);
					}					
				}
				Calendar cal  =  Calendar.getInstance();
				signup.setSignupDate(cal.getTime());								
				signup.setSetUpFee(0D);
				signup.setDeposit(0D);
				signup.setRegistrationFee(0D);
				signup.setSignupPrice(0D);
				String donationPledgeTotalAmt = request.getParameter("donationPledgeTotalAmt");
				if(StringUtils.isNoneBlank(donationPledgeTotalAmt)){
					signup.setFinalAmount(donationPledgeTotalAmt);
					signup.setSignupPrice(Double.valueOf(signup.getFinalAmount()));
					signup.setTotalAmount(signup.getFinalAmount());
					signup.setPledgeAmount(Double.valueOf(signup.getFinalAmount()));					
				}
				if(StringUtils.isNoneBlank(signup.getSpecialRequest())){					
					String[] specialRequest = signup.getSpecialRequest().split(",");
					
					if(StringUtils.isNotBlank(specialRequest[0])){		
						String[] branchNameArr = specialRequest[0].split("_");
						if(StringUtils.isNotBlank(branchNameArr[0])){
							signup.setSpecialRequest(branchNameArr[0]);
						}
						if(signup.getSpecialRequest().equals(Constants.DONATION_OTHER) && specialRequest.length >= 2 && specialRequest[1] != null){
							signup.setSpecialRequest(specialRequest[1]);
						}	
					}
				}
				if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0") && StringUtils.isNoneBlank(signup.getCampaigner())){
					signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest() + ", YMCA campaigner : "+ signup.getCampaigner());									
				}else if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0")){
					signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest());	
				}else if(StringUtils.isNoneBlank(signup.getCampaigner())){
					signup.setSpecialRequest("YMCA campaigner : "+ signup.getCampaigner());
				}
				//picklist value in FCRM
				signup.setCampaigner(signup.getCampaigner());				
				signup.setType(ProductTypeEnum.DONATION.getValue());
				signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
				signup.setLastUpdated(Calendar.getInstance());
				signup.setStatus(SignupStatusEnum.Active.toString());
				if(StringUtils.isBlank(signup.getBillingOption())){
					signup.setBillingOption(BillingOptionEnum.Automatic.toString());	
				}
				if(StringUtils.isNotBlank(itemDetailObj.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetailObj.getStopConfirmedSignUps())){
					signup = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetailObj, signup, true);
				}
				Signup dbSignup = signupDao.save(signup);
				//dbSignup.setEmployeeSignUp(dbSignup.getSignupId());
				
				// check stop confirmed signup flag
				if(itemDetailObj != null && itemDetailObj.getAutomatedWaitlist() != null){
					if(itemDetailObj.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_No)){
						itemDetailDao.save(itemDetailObj);
					}
				}
				
				Payer payer = null;	            
            	JSONObject amountLst =  new JSONObject();
    			amountLst.put("itemprice", String.valueOf(dbSignup.getFinalAmount()));
    			amountLst.put("itempriceOnSignup", String.valueOf(dbSignup.getFinalAmount()));   
    			amountLst.put("itempriceOnPayer", String.valueOf(dbSignup.getFinalAmount()));       			
    			amountLst.put("isRecurring", "true");    			
    			//payment method remaining
    			String paymentMethodIdStr = "0";
    			if(paymentMethod != null && paymentMethod.getId() != null){
    				paymentMethodIdStr = paymentMethod.getId().toString();
    				payer = paymentService.savepayer(paymentMethodIdStr, account, amountLst, itemDetailObj, dbSignup, null); 
    			}else{
    				payer = paymentService.savepayer("0", account, amountLst, itemDetailObj, dbSignup, null); 
    			}    			
    			Double currentChargedAmt = 0D;
    			List<Invoice> invoiceLst = new ArrayList<Invoice>();
    			if(signup.getInvoices() != null && !signup.getInvoices().isEmpty()){
    				String downPayment = request.getParameter("downPayment");   
    				Double downPaymentAmount = 0D;
    				if(StringUtils.isNoneBlank(downPayment) && Double.parseDouble(downPayment) >0){
    					downPaymentAmount =  Double.parseDouble(downPayment); 
    				}
        			Double firstInstallmentAmt = 0D;        			
        			firstInstallmentAmt = signup.getInvoices().get(0).getAmount(); 
        			if(downPaymentAmount >0D){
        				if(signup.getTodayKendoDate() != null && signup.getInvoices().get(0).getDueDate().equals(signup.getTodayKendoDate())){
        					currentChargedAmt = downPaymentAmount + firstInstallmentAmt;
        					model.addAttribute("currentChargedAmt", currentChargedAmt);
        					signup.getInvoices().get(0).setAmount(currentChargedAmt);
    					}else{
    						currentChargedAmt = downPaymentAmount;
    						model.addAttribute("currentChargedAmt", currentChargedAmt);
    						paymentService.saveDownPaymentinvoice(account, currentChargedAmt, savedUser, dbSignup, payer);
    					}
        			}else{
        				if(signup.getTodayKendoDate() != null && signup.getInvoices().get(0).getDueDate().equals(signup.getTodayKendoDate())){        					
        	    			model.addAttribute("currentChargedAmt", firstInstallmentAmt);
    						currentChargedAmt = firstInstallmentAmt;
    					}else{
    						model.addAttribute("currentChargedAmt", 0D);
    						currentChargedAmt = 0D;
    					}        				        				
        			}
        					
					if(itemDetailObj != null && StringUtils.isNotBlank(itemDetailObj.getGenerateRecurringInvoicesAtTimeOfSignUp()) && itemDetailObj.getGenerateRecurringInvoicesAtTimeOfSignUp().equals("Yes")){
						invoiceLst = paymentService.saveMultipleInvoices(account, savedUser, dbSignup, payer, signup.getInvoices(), itemDetailObj);     
	    			}else{
	    				List<Invoice> invList = new ArrayList<Invoice>();
	    				if(signup.getInvoices() != null && !signup.getInvoices().isEmpty()){
	    					invList.add(signup.getInvoices().get(0));
	    				}	    				
	    				invoiceLst = paymentService.saveMultipleInvoices(account, savedUser, dbSignup, payer, invList, itemDetailObj);
	    			}	
				}   			
    			   			
    			//JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getPaymentId().toString(), "", account.getTransId(), acc,  savedUserLst.get(0), savedSignupList.get(0),payer,invoice);
    			Activity activity = activityDao.save(getActivityData(account, savedUser, dbSignup, com.ymca.web.util.Constants.NEW_DONATION_REQUEST));
    			//String paymentMode = "Cash";  
    			String paymentMode = request.getParameter("paymentInfoRadio");
    			if(StringUtils.isBlank(paymentMode)){
    				paymentMode = PaymentTypeEnum.New.name();
    			}else if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name()) 
    					&& !paymentMode.equals(PaymentTypeEnum.Cash.name()) && !paymentMode.equals(PaymentTypeEnum.Check.name())
    					&& !paymentMode.equals(PaymentTypeEnum.Stock.name()) && !paymentMode.equals(PaymentTypeEnum.New.name())){
    				paymentMode = PaymentTypeEnum.Debit.name();
    			}
    			if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name())){
    				Invoice invoice = null;
    				if(invoiceLst != null && !invoiceLst.isEmpty()){
    					invoice = invoiceLst.get(0);
    				}
    				if(paymentMode.equals(PaymentTypeEnum.New.name())){
    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", paymentOrderId, account, savedUser, dbSignup, payer, invoice, paymentMode, currentChargedAmt);
    				}else if(paymentMode.equals(PaymentTypeEnum.Debit.name())){
    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", "0", account, savedUser, dbSignup, payer, invoice, paymentMode, currentChargedAmt);
    				}else{
    					JetPayPayment jetpay = paymentService.savepayment("0", "0", "0", account, savedUser, dbSignup, payer, invoice, paymentMode, currentChargedAmt);
    				}    								
    			}
    			//dbSignup.setFinalAmount(String.valueOf(invoiceAmount));
    			model.addAttribute("signup", dbSignup);
    			
			} else {
				return new ModelAndView("annualDonationError");
			}
			String donationOpptyId = request.getParameter("donationOpptyId");
			if(StringUtils.isNotBlank(donationOpptyId)){
				Opportunity oppty = opportunityDao.getOne(Long.parseLong(donationOpptyId));
				oppty.setSalesStage(OpportunityStatusEnum.ClosedWon.getValue());
				opportunityDao.save(oppty);
			}
			
			return new ModelAndView("annualDonationSuccess",model.asMap());
		}catch(Exception e){
			//System.out.println("Error occured while Creating Donation record in Database");
			log.error("Error in Create Donation ",e);
			return new ModelAndView("annualDonationError");
		}
		
	}
    
    @RequestMapping(value="donationDonate",method = RequestMethod.GET)
    public ModelAndView registerAndDonate(final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	 
    	try{
	    	String userId = null;                 
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        userId = auth.getName();
			Model model = new ExtendedModelMap();
			Account account = null;
			User user = new User();
			List<PaymentMethod> paymentMethodList = null;
			
			if(userId != null && !"anonymousUser".equals(userId)){
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);				
				model.addAttribute("account", account);
				model.addAttribute("usInfo", user);
				model.addAttribute("donationInfo", new Donation()); 
				
				List<User> userLst =  new ArrayList<User>();
				List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
				for(AccountContact ac : accountContactLst){
					if(ac.getEndDate() == null){
						userLst.add(ac.getContact());
					}				
				}
				model.addAttribute("contactLst", userLst);
				paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
		        model.addAttribute("paymentInfoLst" , paymentMethodList);
				
				List<PaymentMethod> creditCardPaymentMethodLst = paymentMethodDao.getCreditCardInfoByAccountId(account.getAccountId());
				List<PaymentMethod> achPaymentMethodLst = paymentMethodDao.getACHInfoByAccountId(account.getAccountId());
				
				model.addAttribute("payMethodLstCredit", creditCardPaymentMethodLst);
				model.addAttribute("payMethodLstACH", achPaymentMethodLst); 
				
			} else {			
				
			}
			List<Account> accList = accountDao.getByCustomerType(Constants.ACCOUNT_ORGANIZATION);
			model.addAttribute("employerLst",accList);	
			model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));	
			/*List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndNeedTC_c(ProductTypeEnum.DONATION.getValue(), "Yes");
			if(donationTCLst != null && !donationTCLst.isEmpty()){
				model.addAttribute("donationTAndC", donationTCLst.get(0));
			}*/
			List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndCategoryAndStatusAndDate(ProductTypeEnum.DONATION.getValue(), Constants.ANNUAL_CAMPAIGN_SUBCATAGORY, Constants.Status_Active, new Date());
			if(donationTCLst != null && !donationTCLst.isEmpty()){
				model.addAttribute("donationItemDetailLst", donationTCLst);
			}
			model.addAttribute("signup", new Signup());
			String opptyId = request.getParameter("opptyId");
			if(StringUtils.isNotBlank(opptyId)){
				model.addAttribute("donationOppty", opportunityDao.getOne(Long.valueOf(opptyId)));
				//model.addAttribute("campaignerLst", userDao.getByType(Constants.ANNUAL_DONATION_CAMPAIGNER));
			}
			return new ModelAndView("donationForm", model.asMap());
    	}catch(Exception e){
    		log.error("Error occured", e);
    		return null;
    	}
	}
    	   
    @RequestMapping(value="donationDonate",method = RequestMethod.POST)
    public ModelAndView registerAndDonate(Signup signup, final HttpServletRequest request, final HttpServletResponse response)
            throws Exception {	 
    	Model model = new ExtendedModelMap();
		try{
			String paymentOrderId = request.getParameter("paymentOrderId");
			String paymentMethodId = request.getParameter("paymentMethodId");			
			ItemDetail itemDetail  = null;
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
			String userId = auth.getName();	
			Account account = new Account();
			User user = new User();				
			User savedUser =  new User();			
			if(userId == null || "anonymousUser".equals(userId)){
				Account dbSaveAccount =  new Account();
				String dobDate = request.getParameter("dobDate");
				String dobMonth = request.getParameter("dobMonth");
				String dobYear = request.getParameter("dobYear");
				String donerFirstName = request.getParameter("donerFirstName");
				String donerLastName = request.getParameter("donerLastName");
				String email = request.getParameter("email");
								
				Date dateOfBirthDate = new Date();
				if(!StringUtils.isBlank(dobDate) && !StringUtils.isBlank(dobMonth) && !StringUtils.isBlank(dobYear)){
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");			
					try {
						dateOfBirthDate = sdf.parse(dobMonth + "/" + dobDate + "/" + dobYear);
					} catch (ParseException e) {
						model.addAttribute("correctDob", "Please correct the Date of Birth and try again");
						log.error("Error parsing DOB - Donation >> ",e);
						return new ModelAndView("donationError", model.asMap());
					}
				}
				List<User> userLst = userDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(donerFirstName, donerLastName, email, dateOfBirthDate);
				 if(userLst != null && !userLst.isEmpty()){
					 user = userLst.get(0);
					 userId = user.getEmailAddress();
					 model.addAttribute("userDonateAsGuest", "No");
				 }else {
					dbSaveAccount = populateAccountFromRequest(request, dbSaveAccount, dateOfBirthDate);					
					dbSaveAccount = populateAccountData(dbSaveAccount);		
					/*
					 * To Generate the Password automatically - Start
					 */					
		 			// create a random token and save it in database with time stamp
		 			// create secure 16 digit token
		 			String token = new BigInteger(130, random).toString(32);
		 			
		 			// get current time stamp
		 			java.util.Date date= new java.util.Date();
		 			
		 			// save in database
		 			dbSaveAccount.setResetPassword(true);
		 			dbSaveAccount.setToken(token);
		 			dbSaveAccount.setSentdate(new Timestamp(date.getTime()));
					try {
					 	//this.getUserManager().saveUser(user);        	
					 	Account acc = accountDao.saveAndFlush(dbSaveAccount);
					 	//User dbUser = userDao.save(dbSaveAccount.getUser());
					 	for(User usr : acc.getUser()){
					 		AccountContact accContact = accountContactService.saveAccountContact(acc, usr);
					 		break;
					 	}
					 	userId = acc.getEmail();
					 	try  
					 	{  					 		
					 		if(acc!=null && acc.getUser() != null){				 			
					 				User contact =  null;	
					 				contact = getUserByAccount(acc, contact);
					 				
					 				// Interaction Integration block start 
					 				Activity interaction = new Activity();
					 				interaction.setType(Constants.REQUEST_RESET_PASSWORD);
					 				
					 				//String resetUrl = request.getRequestURL().toString().replace("/resetpassword", "/resetpasscode");
					 				String resetUrl = request.getRequestURL().toString().replace("/donationDonate", "/recovery/resetpasscode");
					 				String queryString = "?token="+token;
					 				interaction.setDescription(resetUrl+queryString);
					 				
					 				// get current time stamp
					 				interaction.setCreatedDate(new Timestamp(date.getTime()));
					 				interaction.setCustomer(acc);
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
					 	/*
						 * To Generate the Password automatically - End
						 */	
					 }catch(Exception e){
						 log.error("Error in Create Donation ",e);
						 return new ModelAndView("donationError");
					 }	
					model.addAttribute("userDonateAsGuest", "Yes");
					model.addAttribute("userDonateAsGuestEmail", userId);
				 }
				
				
			}else{
				model.addAttribute("userDonateAsGuest", "No");
			}
			PaymentMethod paymentMethod = null;		
			if(userId != null && !"anonymousUser".equals(userId)){
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);	
				signup.setCustomer(account);
				if(StringUtils.isNotBlank(paymentOrderId)){
					paymentMethod =  paymentMethodDao.getByOrderNumber(paymentOrderId);
					if(paymentMethod != null){
						paymentMethod.setCustomer(account);
						paymentMethodDao.save(paymentMethod);
					}					
				}
				if(StringUtils.isNotBlank(paymentMethodId)){
					paymentMethod =  paymentMethodDao.findOne(Long.valueOf(paymentMethodId));										
				}
				
				/*if(signup != null && signup.getEmployer() != null){
					signup.setEmployer(signup.getEmployer());
				}	
				if(signup != null && signup.getEmployee() != null){
					signup.setEmployee(signup.getEmployee());
				}*/
				if(StringUtils.isNotBlank(signup.getPledgeAmountFrequency()) && !signup.getPledgeAmountFrequency().equals("0")){
					signup.setPaymentFrequency(PaymentTypeEnum.Recurring.getValue());					
				}else{
					signup.setPaymentFrequency(PaymentTypeEnum.NonRecurring.getValue());
				}
				signup.setEmployer(null);
				signup.setEmployee(null);
				
				if(signup != null){					
					if(signup.getEmployee() != null){
						//signup.setEmployee(signup.getEmployee());
					}
					if(StringUtils.isNotBlank(signup.getPledgeAmountFrequency())){
						if(!signup.getPledgeAmountFrequency().equals("0")){
							if(!signup.getPledgeAmountFrequency().equals("Continuous")){
								Calendar endDateCal = Calendar.getInstance();
								endDateCal.add(Calendar.MONTH, Integer.valueOf(signup.getPledgeAmountFrequency()));								
								signup.setProgramEndDate(endDateCal.getTime());
							}
							Calendar nextBillingDate = Calendar.getInstance();
							nextBillingDate.add(Calendar.MONTH, 1);
							signup.setMembersshipFeeNextBillingDate(nextBillingDate.getTime());
						}else{
							signup.setProgramEndDate(new Date());
						}
						
					}	
					
					if(signup.getItemDetailId() != null){
						itemDetail = itemDetailDao.getOne(signup.getItemDetailId());
						signup.setItemDetail(itemDetail);
					}else {
						List<ItemDetail> donationItemDetail = itemDetailDao.findByCategory(ProductTypeEnum.DONATION.getValue());
						if(donationItemDetail != null && !donationItemDetail.isEmpty()){
							signup.setItemDetail(donationItemDetail.get(0));
						}
					}
					if(StringUtils.isNotBlank(signup.getRecognizeAs()) && isNumeric(signup.getRecognizeAs())){
						User u = userDao.getOne(Long.valueOf(signup.getRecognizeAs()));
						u.setDoNotEmail(signup.getContact().getDoNotEmail());
						savedUser = userDao.save(u);
						signup.setContact(u);
						signup.setRecognizeAs(u.getFullName());					
					}else{
						user.setDoNotEmail(signup.getContact().getDoNotEmail());
						savedUser = userDao.save(user);
						signup.setContact(user);
					}					
				}
				Calendar cal  =  Calendar.getInstance();
				signup.setSignupDate(cal.getTime());
				signup.setProgramStartDate(cal.getTime());				
				signup.setSetUpFee(0D);
				signup.setDeposit(0D);
				signup.setRegistrationFee(0D);
				signup.setSignupPrice(0D);
				Double paymentAmount = 0D;
				Double pledgeAmount = 0D;
				if(StringUtils.isNoneBlank(signup.getFinalAmount()) && StringUtils.isNotBlank(signup.getPledgeAmountFrequency())){
					paymentAmount = Double.valueOf(signup.getFinalAmount());
					try{
						pledgeAmount = paymentAmount * Double.valueOf(signup.getPledgeAmountFrequency());
					}catch(Exception e){
						log.error("Error occured while calculating pledge Amount ",e);
					}
					signup.setFinalAmount(String.valueOf(pledgeAmount));
					signup.setSignupPrice(pledgeAmount);
					signup.setTotalAmount(String.valueOf(pledgeAmount));
					signup.setPledgeAmount(pledgeAmount);
				}
				if(StringUtils.isNoneBlank(signup.getSpecialRequest())){					
					String[] specialRequest = signup.getSpecialRequest().split(",");
					if(StringUtils.isNotBlank(specialRequest[0]) && specialRequest[0].equals(Constants.DONATION_OTHER)){
						signup.setSpecialRequest(specialRequest[1]);
					}					
				}
				if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0") && StringUtils.isNoneBlank(signup.getCampaigner())){
					signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest() + ", YMCA campaigner : "+ signup.getCampaigner());									
				}else if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0")){
					signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest());	
				}else if(StringUtils.isNoneBlank(signup.getCampaigner())){
					signup.setSpecialRequest("YMCA campaigner : "+ signup.getCampaigner());
				}
				//picklist value in FCRM
				signup.setCampaigner(signup.getCampaigner());				
				signup.setType(ProductTypeEnum.DONATION.getValue());
				signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
				signup.setLastUpdated(Calendar.getInstance());
				signup.setStatus(SignupStatusEnum.Active.toString());
				signup.setBillingOption(BillingOptionEnum.Automatic.toString());
				if(StringUtils.isNotBlank(itemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(itemDetail.getStopConfirmedSignUps())){
					signup = signUpService.signupCapacityManagementForMembershipAndDonation(itemDetail, signup, true);
				}
				Signup dbSignup = signupDao.save(signup);
				//dbSignup.setEmployeeSignUp(dbSignup.getSignupId());
				
				// check stop confirmed signup flag
				ItemDetail itemDet =	signup.getItemDetail();
				if(itemDet != null && itemDet.getAutomatedWaitlist() != null){
					if(itemDet.getAutomatedWaitlist().equalsIgnoreCase(Constants.AutomatedWaitlist_No)){
						itemDetailDao.save(itemDet);
					}
				}
				
				Payer payer = null;	            
            	JSONObject amountLst =  new JSONObject();
    			amountLst.put("itemprice", String.valueOf(paymentAmount));
    			amountLst.put("itempriceOnSignup", String.valueOf(paymentAmount));
    			amountLst.put("itempriceOnPayer", String.valueOf(paymentAmount));
    			amountLst.put("isRecurring", "true");    			
    			//payment method remaining
    			String paymentMethodIdStr = "0";
    			if(paymentMethod != null && paymentMethod.getId() != null){
    				paymentMethodIdStr = paymentMethod.getId().toString();
    				payer = paymentService.savepayer(paymentMethodIdStr, account, amountLst, itemDetail, dbSignup, null); 
    			}else{
    				payer = paymentService.savepayer("0", account, amountLst, itemDetail, dbSignup, null); 
    			}
    			
	    		
    			Invoice invoice = null;
    			Date currentDate =  new Date();    			
    			JSONObject amountLst1 =  new JSONObject();
    			amountLst1.put("itemprice", String.valueOf(paymentAmount));
    			amountLst1.put("fa", "0");  
    			amountLst1.put("itempriceOnInvoice", String.valueOf(String.valueOf(paymentAmount)));
    			amountLst1.put("isRecurring", "true");    
    			
    			if(payer != null && payer.getEnddate() != null){
    				invoice = paymentService.saveinvoice(account, amountLst1, savedUser, dbSignup, payer);
    			}else if(payer.getEnddate() == null){
    				invoice = paymentService.saveinvoice(account, amountLst1, savedUser, dbSignup, payer);
    			}
    			//JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getPaymentId().toString(), "", account.getTransId(), acc,  savedUserLst.get(0), savedSignupList.get(0),payer,invoice);
    			Activity activity = activityDao.save(getActivityData(account, savedUser, dbSignup, com.ymca.web.util.Constants.NEW_DONATION_REQUEST));
    			//String paymentMode = "Cash";  
    			String paymentMode = request.getParameter("paymentInfoRadio");
    			if(StringUtils.isBlank(paymentMode)){
    				paymentMode = PaymentTypeEnum.New.name();
    			}else if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name()) 
    					&& !paymentMode.equals(PaymentTypeEnum.Cash.name()) && !paymentMode.equals(PaymentTypeEnum.Check.name())
    					&& !paymentMode.equals(PaymentTypeEnum.Stock.name()) && !paymentMode.equals(PaymentTypeEnum.New.name())){
    				paymentMode = PaymentTypeEnum.Debit.name();
    			}
    			if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name()) && invoice != null){
    				if(paymentMode.equals(PaymentTypeEnum.New.name())){
    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", paymentOrderId, account, savedUser, dbSignup, payer, invoice, paymentMode, invoice.getAmount());
    				}else if(paymentMode.equals(PaymentTypeEnum.Debit.name())){
    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", "0", account, savedUser, dbSignup, payer, invoice, paymentMode, invoice.getAmount());
    				}else{
    					JetPayPayment jetpay = paymentService.savepayment("0", "0", "0", account, savedUser, dbSignup, payer, invoice, paymentMode, invoice.getAmount());
    				}    								
    			}    			
    			model.addAttribute("signup", dbSignup);    			
			} else {
				return new ModelAndView("donationError");
			}
			
			return new ModelAndView("donationSuccess",model.asMap());
		}catch(Exception e){
			//System.out.println("Error occured while Creating Donation record in Database");
			log.error("Error in Create Donation ",e);
			return new ModelAndView("donationError");
		}
		
	}

    private Activity getActivityData(Account account, User contact, Signup signup, String donationDesc) {	
		Activity activity = new Activity();
		if(account!=null && contact!=null){	
			activity.setType(ProductTypeEnum.DONATION.getValue());
			activity.setDescription(donationDesc);
			// get current time stamp
			java.util.Date date= new java.util.Date();
	 		activity.setCreatedDate(new Timestamp(date.getTime()));
	 		activity.setCustomer(account);
	 		activity.setContact(contact);
	 		activity.setSignup(signup);
	 		activity.setShowOnPortal(true);	  	
	 		Calendar cal = Calendar.getInstance();
	 		List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndNeedTC_c(ProductTypeEnum.DONATION.getValue(), "Yes");
			if(donationTCLst != null && !donationTCLst.isEmpty()){
				activity.setAssignTo(donationTCLst.get(0).getDonationManager());
			}
	 		activity.setLastUpdated(cal);
    	}
		return activity;
	}

	public boolean isAdultByDob(Date dobDate){	
		boolean isAdult = false;
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
		
		int adultAgeLowerLimit = 18;		
		List<SystemProperty> adultAg = new ArrayList<SystemProperty>();			
		adultAg = systemPropertyDao.getPropertyByKeyName(Constants.MEMBERSHIP_ADULT_AGE_VALIDATION_LOWER_LIMIT);
		if(adultAg != null && !adultAg.isEmpty()){
			adultAgeLowerLimit = Integer.parseInt(adultAg.get(0).getKeyValue());				
		}

		//if(age > adultAgeLowerLimit && age < adultAgeUpperLimit){
		if(age > adultAgeLowerLimit){
			isAdult = true;
		}
		
		return isAdult;
	}
    
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	 @RequestMapping(value="cancelDonation",method = RequestMethod.POST)
	    public @ResponseBody String cancelDonation(@RequestParam(value="donationId") String donationId, final HttpServletRequest request, final HttpServletResponse response)
	            throws Exception {	  	
	    	String userId = null;                 
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        userId = auth.getName();			
			Account account = null;
			User user = new User();		
			Signup donationSignup =  null;
			
			if(userId != null && !"anonymousUser".equals(userId)){
				account = accountDao.getByEmail(userId);	
				user = getUserByAccount(account,user);			
				donationSignup = signupDao.getOne(Long.valueOf(donationId));
				donationSignup.setStatus(SignupStatusEnum.Inactive.toString());
				donationSignup.setProgramEndDate(new Date());
				donationSignup.setCancelDate(new Date());
				donationSignup.setMembersshipFeeNextBillingDate(null);
				Signup signupDB = signupDao.save(donationSignup);
				Activity activity = getActivityData(account, user, signupDB, com.ymca.web.util.Constants.CANCEL_DONATION_REQUEST);				
				activityDao.save(activity);
				
				/*
				 * 1. Find invoice tied up to the donation (Signup)
				 * 2. Get open balance on invoice 
				 * 3. Create payment record of type "CM/Waiver" with amount = open balance for each invoice
				 */
				List<Invoice> donationInvoiceLst = invoiceDao.getInvoiceListForDonationSignup(donationSignup.getSignupId());
				for (Invoice invoice : donationInvoiceLst) {					
					Double openBalance = paymentService.getOpenBalanceForInvoice(invoice);					
					if(openBalance > 0D){
						paymentService.createCMWaiverPayment(openBalance, donationSignup, invoice);
					}
				}				
				return "SUCCESS";
			} else {			
				return "FAIL";
			}	
		}
	 private Account populateAccountData(Account account){
		 User user = new User();    	
		 user = populateUserData(account);   
		 user.setEnabled(true);		 
		 account.setEmail(user.getPrimaryEmailAddress());
		 account.setCountry(Constants.COUNTRY_USA);
		 account.setName(user.getFullName() + " " + Constants.ACCOUNT_FAMILY);
		 account.setCustomerType(Constants.ACCOUNT_FAMILY);		 
		 Calendar cal =  Calendar.getInstance();
		 account.setLastUpdated(cal);	
		 user.setCustomer(account);
		 account.setUser(new HashSet<User>());
		 account.getUser().add(user);
		
		 return account;
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
	 
	 private Account populateAccountFromRequest(HttpServletRequest request, Account account, Date dateOfBirthDate){
		 	User guestUser =  new User();
		 	String billingAddressLine1 = request.getParameter("billingAddressLine1");
			String billingAddressLine2 = request.getParameter("billingAddressLine2");
			String billingCity = request.getParameter("billingCity");
			String billingZip = request.getParameter("billingZip");
			String billingState = request.getParameter("billingState");
			
			String dateOfBirth = request.getParameter("dateOfBirth");
			String email = request.getParameter("email");
			String gender = request.getParameter("gender");
			String preferredPhone = request.getParameter("preferredPhone");
			String donerFirstName = request.getParameter("donerFirstName");
			String donerLastName = request.getParameter("donerLastName");
			
			String tcDescription = "";
			String guestPassword = "";
			List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndNeedTC_c(ProductTypeEnum.DONATION.getValue(), "Yes");
			if(donationTCLst != null && !donationTCLst.isEmpty() && donationTCLst.get(0).getWaiversAndTC() != null){
				tcDescription = donationTCLst.get(0).getWaiversAndTC().getTcDescription();
			}
			List<SystemProperty> guestPasswordLst = new ArrayList<SystemProperty>();			
			guestPasswordLst = systemPropertyDao.getPropertyByKeyName(Constants.DONATION_AS_GUEST_USER_PASSWORD);
			if(guestPasswordLst != null && !guestPasswordLst.isEmpty()){
				guestPassword =  guestPasswordLst.get(0).getKeyValue();				
			}
			account.setAddressLine1(billingAddressLine1);
			account.setAddressLine2(billingAddressLine2);
			account.setCity(billingCity);
			account.setPostalCode(billingZip);
			account.setState(billingState);
			
			
			guestUser.setDateOfBirth(dateOfBirthDate);
			guestUser.setEmailAddress(email);
			guestUser.setGender(gender);
			guestUser.setFormattedPhoneNumber(preferredPhone);
			guestUser.setPassword(guestPassword);
			guestUser.setFirstName(donerFirstName);
			guestUser.setLastName(donerLastName);
			List<User> userLsts = new ArrayList<User>();
			userLsts.add(guestUser);
			account.setUserLst(userLsts);
			
			return account;
	 }
	 
	 @RequestMapping(value = "/fullDonationsList", method = RequestMethod.GET)
	public ModelAndView fullDonationsList() {
		Model model = new ExtendedModelMap();
		String userId = null;                 
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userId = auth.getName();			
		Account account = null;
		User user = new User();		
		if(userId != null && !"anonymousUser".equals(userId)){
			account = accountDao.getByEmail(userId);	
			user = getUserByAccount(account,user);	
			try{
				String accPartyId = null;
				if(account.getPartyId() != null){
					accPartyId = String.valueOf(account.getPartyId());
				}
				List<Opportunity> oppList = opportunityDao.getByTypeAndStage(ProductTypeEnum.DONATION.getValue(), accPartyId);
				//List<Opportunity> oppList = opportunityDao.getByTypeAndStage("Facility");
				model.addAttribute("donationOpptyLst", oppList);				
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
						
		} else {			
			return null;
		}	
		return new ModelAndView("fullDonationsList", model.asMap());
	}
	 
	 @RequestMapping(value="donationEmployerMatch",method = RequestMethod.GET)
	 public ModelAndView donationEmployerMatch(final HttpServletRequest request, final HttpServletResponse response) throws Exception {	 
	    	try{
		    	String userId = null;                 
		        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		        userId = auth.getName();
				Model model = new ExtendedModelMap();
				Account account = null;
				User user = new User();
				List<PaymentMethod> paymentMethodList = null;
				
				if(userId != null && !"anonymousUser".equals(userId)){
					account = accountDao.getByEmail(userId);	
					user = getUserByAccount(account,user);				
					model.addAttribute("account", account);
					model.addAttribute("usInfo", user);
					model.addAttribute("donationInfo", new Donation()); 
					
					List<User> userLst =  new ArrayList<User>();
					List<AccountContact> accountContactLst =  accountContactDao.getByCustomer(account);
					for(AccountContact ac : accountContactLst){
						if(ac.getEndDate() == null){
							userLst.add(ac.getContact());
						}				
					}
					model.addAttribute("contactLst", userLst);
					paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
			        model.addAttribute("paymentInfoLst" , paymentMethodList);
					
					List<PaymentMethod> creditCardPaymentMethodLst = paymentMethodDao.getCreditCardInfoByAccountId(account.getAccountId());
					List<PaymentMethod> achPaymentMethodLst = paymentMethodDao.getACHInfoByAccountId(account.getAccountId());
					
					model.addAttribute("payMethodLstCredit", creditCardPaymentMethodLst);
					model.addAttribute("payMethodLstACH", achPaymentMethodLst); 
					
				} else {			
					
				}
				List<Account> accList = accountDao.getByCustomerType(Constants.ACCOUNT_ORGANIZATION);
				model.addAttribute("employerLst",accList);	
				List<Signup> employeeSignupLst = signupDao.findByEmployerSignupData(account.getAccountId(), "");
				model.addAttribute("employeeSignupLst",employeeSignupLst);	
				model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));	
				/*List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndNeedTC_c(ProductTypeEnum.DONATION.getValue(), "Yes");
				if(donationTCLst != null && !donationTCLst.isEmpty()){
					model.addAttribute("donationTAndC", donationTCLst.get(0));
				}*/	
				List<ItemDetail> donationTCLst = itemDetailDao.getByTypeAndCategoryAndStatusAndDate(ProductTypeEnum.DONATION.getValue(), Constants.ANNUAL_CAMPAIGN_SUBCATAGORY, Constants.Status_Active, new Date());
				if(donationTCLst != null && !donationTCLst.isEmpty()){
					model.addAttribute("donationItemDetailLst", donationTCLst);
				}
				model.addAttribute("signup", new Signup());
				String opptyId = request.getParameter("opptyId");
				if(StringUtils.isNotBlank(opptyId)){
					model.addAttribute("donationOppty", opportunityDao.getOne(Long.valueOf(opptyId)));
					//model.addAttribute("campaignerLst", userDao.getByType(Constants.ANNUAL_DONATION_CAMPAIGNER));
				}
				
				List<PricingRule> pricingRuleLst = new ArrayList<PricingRule>();
				//To do get the item detail based on criteria
				List<ItemDetailPricingRule> itemDetailPricingRuleLst = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(300000010645002L, Constants.Status_Active, Constants.Status_Active, Constants.SIGNUP);
				for(ItemDetailPricingRule pricingRule: itemDetailPricingRuleLst) {	    		 
		    		if(pricingRule!=null && pricingRule.getPricingRule()!=null){							
	    				if(pricingRule.getPricingRule().getType() != null && Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){   					
	    					pricingRuleLst.add(pricingRule.getPricingRule());
	    				}	    				    							    				
		    		}
		    	}
				model.addAttribute("pricingRuleList", pricingRuleLst);
				
				return new ModelAndView("donationEmployerMatch", model.asMap());
	    	}catch(Exception e){
	    		log.error("Error occured", e);
	    		return null;
	    	}
		}
	 
	 @RequestMapping(value="donationEmployerMatch",method = RequestMethod.POST)
	 public ModelAndView donationEmployerMatchRegister(Signup signup, final HttpServletRequest request, final HttpServletResponse response) throws Exception {	 
	    	Model model = new ExtendedModelMap();
	    	ItemDetail donationItemDetail = null;
	    	if(signup != null && signup.getItemDetail() !=null){
	    		donationItemDetail = signup.getItemDetail();
	    	}else if(signup != null && signup.getItemDetailId() !=null){
	    		donationItemDetail = itemDetailDao.getOne(signup.getItemDetailId());
				signup.setItemDetail(donationItemDetail);
	    	}else{
	    		List<ItemDetail> donationItemDetailLst = itemDetailDao.findByCategory(ProductTypeEnum.DONATION.getValue());
	    		if(donationItemDetailLst != null && !donationItemDetailLst.isEmpty()){
	    			donationItemDetail = donationItemDetailLst.get(0);
	    		}
	    	}
			try{
				String paymentOrderId = request.getParameter("paymentOrderId");
				String paymentMethodId = request.getParameter("paymentMethodId");			
				ItemDetail itemDetail  = null;
				Authentication auth = SecurityContextHolder.getContext().getAuthentication();        
				String userId = auth.getName();	
				Account account = new Account();
				User user = new User();				
				User savedUser =  new User();			
				
				PaymentMethod paymentMethod = null;		
				if(userId != null && !"anonymousUser".equals(userId)){
					account = accountDao.getByEmail(userId);	
					user = getUserByAccount(account,user);	
					signup.setCustomer(account);
					signup.setContact(user);
					//Set Employer on Employee Signup record
					if(signup.getEmployeeSignUp() != null){
						Signup employeeSignup = signupDao.getOne(signup.getEmployeeSignUp());
						if(employeeSignup != null){
							employeeSignup.setEmployer(account.getAccountId());
							signupDao.save(employeeSignup);
						}
					}
					
					if(StringUtils.isNotBlank(paymentOrderId)){
						paymentMethod =  paymentMethodDao.getByOrderNumber(paymentOrderId);
						if(paymentMethod != null){
							paymentMethod.setCustomer(account);
							paymentMethodDao.save(paymentMethod);
						}					
					}
					if(StringUtils.isNotBlank(paymentMethodId)){
						paymentMethod =  paymentMethodDao.findOne(Long.valueOf(paymentMethodId));										
					}
					if(signup != null && StringUtils.isNotBlank(signup.getPledgeAmountFrequency()) && !signup.getPledgeAmountFrequency().equals(SignupFrequencyEnum.OneTime.getValue())){
						signup.setPaymentFrequency(PaymentTypeEnum.Recurring.getValue());
						Calendar programEndDate = Calendar.getInstance();						
						if(signup.getNextBillDate() != null && signup.getTodayKendoDate() != null && signup.getNextBillDate().equals(signup.getTodayKendoDate())){
							programEndDate.setTime(signup.getNextBillDate());
							if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Weekly.getValue())){								
								programEndDate.add(Calendar.DATE, 7*Integer.parseInt(signup.getPledgeAmountFrequency()));									
							}else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.SemiMonthly.getValue())){
								programEndDate.add(Calendar.DATE, 15*Integer.parseInt(signup.getPledgeAmountFrequency()));
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Monthly.getValue())){
								programEndDate.add(Calendar.MONTH, 1*Integer.parseInt(signup.getPledgeAmountFrequency()));
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Quarterly.getValue())){
								programEndDate.add(Calendar.MONTH, 3*Integer.parseInt(signup.getPledgeAmountFrequency()));
							}
							else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Annual.getValue())){
								programEndDate.add(Calendar.MONTH, 12*Integer.parseInt(signup.getPledgeAmountFrequency()));
							}
							signup.setProgramEndDate(programEndDate.getTime());
						}
					}else{
						signup.setPaymentFrequency(PaymentTypeEnum.NonRecurring.getValue());
						signup.setProgramEndDate(null);
					}			
					Date signupNextBillDate = signup.getNextBillDate();
					Date todayDate = signup.getTodayKendoDate();
					
					if(signup != null){						
						if(signup.getNextBillDate() != null && signup.getTodayKendoDate() != null && signup.getNextBillDate().equals(signup.getTodayKendoDate())){							
							Calendar nextBillingDate = Calendar.getInstance();							
							  if(StringUtils.isNotBlank(signup.getSignUpPricingOption()) && !signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.OneTime.getValue())){
								  nextBillingDate.setTime(signup.getNextBillDate());
								  if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Weekly.getValue())){								
										nextBillingDate.add(Calendar.DATE, 7);									
									}else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.SemiMonthly.getValue())){
										nextBillingDate.add(Calendar.DATE, 15);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Monthly.getValue())){
										nextBillingDate.add(Calendar.MONTH, 1);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Quarterly.getValue())){
										nextBillingDate.add(Calendar.MONTH, 3);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Annual.getValue())){
										nextBillingDate.add(Calendar.MONTH, 12);
									}
									signup.setNextBillDate(nextBillingDate.getTime());
							  }
							  
						}else{
							signup.setNextBillDate(signup.getNextBillDate());
						}
						if(StringUtils.isNotBlank(signup.getSignUpPricingOption()) && signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.OneTime.getValue())){
							signup.setNextBillDate(null);
						}					
						signup.setItemDetail(donationItemDetail);					
						if(StringUtils.isNotBlank(signup.getRecognizeAs()) && isNumeric(signup.getRecognizeAs())){
							User u = userDao.getOne(Long.valueOf(signup.getRecognizeAs()));
							u.setDoNotEmail(signup.getContact().getDoNotEmail());
							savedUser = userDao.save(u);							
							signup.setRecognizeAs(u.getFullName());					
						}else{
							user.setDoNotEmail(signup.getContact().getDoNotEmail());
							savedUser = userDao.save(user);
							signup.setContact(user);
						}					
					}
					Calendar cal  =  Calendar.getInstance();
					signup.setSignupDate(cal.getTime());
					signup.setProgramStartDate(cal.getTime());				
					signup.setSetUpFee(0D);
					signup.setDeposit(0D);
					signup.setRegistrationFee(0D);
					signup.setSignupPrice(0D);
					Double paymentAmount = 0D;
					Double pledgeAmount = 0D;
					if(StringUtils.isNoneBlank(signup.getFinalAmount()) && StringUtils.isNotBlank(signup.getPledgeAmountFrequency())){
						paymentAmount = Double.valueOf(signup.getFinalAmount());
						try{
							pledgeAmount = paymentAmount * Double.valueOf(signup.getPledgeAmountFrequency());
						}catch(Exception e){
							log.error("Error occured while calculating pledge Amount ",e);
						}
						signup.setFinalAmount(String.valueOf(pledgeAmount));
						signup.setSignupPrice(pledgeAmount);
						signup.setTotalAmount(String.valueOf(pledgeAmount));
						signup.setPledgeAmount(pledgeAmount);
					}
					/*if(StringUtils.isNoneBlank(signup.getSpecialRequest())){					
						String[] specialRequest = signup.getSpecialRequest().split(",");
						if(StringUtils.isNotBlank(specialRequest[0]) && specialRequest[0].equals(Constants.DONATION_OTHER)){
							signup.setSpecialRequest(specialRequest[1]);
						}					
					}
					if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0") && StringUtils.isNoneBlank(signup.getCampaigner())){
						signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest() + ", YMCA campaigner : "+ signup.getCampaigner());									
					}else if(StringUtils.isNoneBlank(signup.getSpecialRequest()) && !signup.getSpecialRequest().equals("0")){
						signup.setSpecialRequest("Apply my gift : " + signup.getSpecialRequest());	
					}else if(StringUtils.isNoneBlank(signup.getCampaigner())){
						signup.setSpecialRequest("YMCA campaigner : "+ signup.getCampaigner());
					}
					//picklist value in FCRM
					signup.setCampaigner(signup.getCampaigner());*/				
					signup.setType(ProductTypeEnum.DONATION.getValue());
					signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
					signup.setLastUpdated(Calendar.getInstance());
					signup.setStatus(SignupStatusEnum.Active.toString());
					signup.setBillingOption(BillingOptionEnum.Automatic.toString());
					if(StringUtils.isNotBlank(donationItemDetail.getStopConfirmedSignUps()) &&  "Yes".equals(donationItemDetail.getStopConfirmedSignUps())){
						signup = signUpService.signupCapacityManagementForMembershipAndDonation(donationItemDetail, signup, true);
					}
					Signup dbSignup = signupDao.save(signup);
					//dbSignup.setEmployeeSignUp(dbSignup.getSignupId());
					
					Payer payer = null;	            
	            	JSONObject amountLst =  new JSONObject();
	    			amountLst.put("itemprice", String.valueOf(paymentAmount));
	    			amountLst.put("itempriceOnSignup", String.valueOf(paymentAmount));
	    			amountLst.put("itempriceOnPayer", String.valueOf(paymentAmount));
	    			amountLst.put("isRecurring", "true");    			
	    			//payment method remaining
	    			String paymentMethodIdStr = "0";
	    			if(paymentMethod != null && paymentMethod.getId() != null){
	    				paymentMethodIdStr = paymentMethod.getId().toString();
	    				payer = paymentService.savepayer(paymentMethodIdStr, account, amountLst, itemDetail, dbSignup, null); 
	    			}else{
	    				payer = paymentService.savepayer("0", account, amountLst, itemDetail, dbSignup, null); 
	    			}
	    			
	    			if(signupNextBillDate != null && todayDate != null && signupNextBillDate.equals(todayDate)){		    													
		    			/*Invoice invoice = null;
		    			Date currentDate =  new Date();    			
		    			JSONObject amountLst1 =  new JSONObject();
		    			amountLst1.put("itemprice", String.valueOf(paymentAmount));
		    			amountLst1.put("fa", "0");  
		    			amountLst1.put("itempriceOnInvoice", String.valueOf(String.valueOf(paymentAmount)));
		    			amountLst1.put("isRecurring", "true");    
		    			
		    			if(payer != null && payer.getEnddate() != null){
		    				invoice = paymentService.saveinvoice(account, amountLst1, savedUser, dbSignup, payer);
		    			}else if(payer.getEnddate() == null){
		    				invoice = paymentService.saveinvoice(account, amountLst1, savedUser, dbSignup, payer);
		    			}*/
		    			//JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getPaymentId().toString(), "", account.getTransId(), acc,  savedUserLst.get(0), savedSignupList.get(0),payer,invoice);
		    			Activity activity = activityDao.save(getActivityData(account, savedUser, dbSignup, com.ymca.web.util.Constants.NEW_DONATION_REQUEST));
		    			//String paymentMode = "Cash";  
		    			String paymentMode = request.getParameter("paymentInfoRadio");
		    			if(StringUtils.isBlank(paymentMode)){
		    				paymentMode = PaymentTypeEnum.New.name();
		    			}else if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name()) 
		    					&& !paymentMode.equals(PaymentTypeEnum.Cash.name()) && !paymentMode.equals(PaymentTypeEnum.Check.name())
		    					&& !paymentMode.equals(PaymentTypeEnum.Stock.name()) && !paymentMode.equals(PaymentTypeEnum.New.name())){
		    				paymentMode = PaymentTypeEnum.Debit.name();
		    			}		    			
		    			List<Invoice> invoiceLst = new ArrayList<Invoice>();
		    			if(signup != null && StringUtils.isNotBlank(signup.getPledgeAmountFrequency()) && !signup.getPledgeAmountFrequency().equals(SignupFrequencyEnum.OneTime.getValue())){						
							Calendar invoiceDate = Calendar.getInstance();
							invoiceDate.setTime(signupNextBillDate);
							if(signupNextBillDate != null && todayDate != null && signupNextBillDate.equals(todayDate)){
								for(int i = 0; i< Integer.parseInt(signup.getPledgeAmountFrequency()); i++){
									Invoice inv =  new Invoice();
									inv.setBillDate(invoiceDate.getTime());
									inv.setDueDate((invoiceDate.getTime()));
									inv.setAmount(paymentAmount);
									if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Weekly.getValue())){								
										invoiceDate.add(Calendar.DATE, 7);									
									}else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.SemiMonthly.getValue())){
										invoiceDate.add(Calendar.DATE, 15);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Monthly.getValue())){
										invoiceDate.add(Calendar.MONTH, 1);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Quarterly.getValue())){
										invoiceDate.add(Calendar.MONTH, 3);
									}
									else if(signup.getSignUpPricingOption().equalsIgnoreCase(SignupFrequencyEnum.Annual.getValue())){
										invoiceDate.add(Calendar.MONTH, 12);
									}
									invoiceLst.add(inv);
								}
							}
						}else{
							Invoice inv =  new Invoice();
							inv.setBillDate(new Date());
							inv.setDueDate(new Date());
							inv.setAmount(paymentAmount);							
							invoiceLst.add(inv);
						}
		    			if(donationItemDetail != null){							
							if(donationItemDetail != null && StringUtils.isNotBlank(donationItemDetail.getGenerateRecurringInvoicesAtTimeOfSignUp()) && donationItemDetail.getGenerateRecurringInvoicesAtTimeOfSignUp().equals("Yes")){
								invoiceLst = paymentService.saveEmployerMatchInvoices(account, savedUser, dbSignup, payer, invoiceLst, donationItemDetail);     
							}else{
								List<Invoice> invList = new ArrayList<Invoice>();
								if(invoiceLst != null && !invoiceLst.isEmpty()){
									invList.add(invoiceLst.get(0));
								}	    				
								invoiceLst = paymentService.saveEmployerMatchInvoices(account, savedUser, dbSignup, payer, invList, donationItemDetail);
							}
						}
		    			if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name()) && invoiceLst != null && !invoiceLst.isEmpty()){
		    				if(paymentMode.equals(PaymentTypeEnum.New.name())){
		    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", paymentOrderId, account, savedUser, dbSignup, payer, invoiceLst.get(0), paymentMode, invoiceLst.get(0).getAmount());
		    				}else if(paymentMode.equals(PaymentTypeEnum.Debit.name())){
		    					JetPayPayment jetpay = paymentService.savepayment(paymentMethodIdStr, "0", "0", account, savedUser, dbSignup, payer, invoiceLst.get(0), paymentMode, invoiceLst.get(0).getAmount());
		    				}else{
		    					JetPayPayment jetpay = paymentService.savepayment("0", "0", "0", account, savedUser, dbSignup, payer, invoiceLst.get(0), paymentMode, invoiceLst.get(0).getAmount());
		    				}    								
		    			}
	    			//}
	    			model.addAttribute("signup", dbSignup);    			
				} else {
					//return new ModelAndView("donationError");
				}
				}
				return new ModelAndView("donationSuccess",model.asMap());
			}catch(Exception e){
				//System.out.println("Error occured while Creating Donation record in Database");
				log.error("Error in Create Donation ",e);
				return new ModelAndView("donationError");
			}
			
		}
	 
	 @RequestMapping(value="/viewUpdPM", method=RequestMethod.GET)
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
									invoiceObj.put("invoiceDate", sdf.format(invoice.getInvoiceDate()));
									invoiceObj.put("invoiceDueDate", sdf.format(invoice.getDueDate()));
									invoiceObj.put("invoiceBillDate", sdf.format(invoice.getBillDate()));
									invoiceObj.put("invoiceAmount", invoice.getAmount());
									
									PaymentMethod ipm = invoice.getPaymentMethod();
									
									if(ipm != null){
										invoiceObj.put("invoicePMId", ipm.getId());	
									}else if(invoice.getPaymentMode() != null){
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
				
			}
			
			return new ModelAndView("donationUpdatePaymentMethod", model.asMap());
	 }
}
