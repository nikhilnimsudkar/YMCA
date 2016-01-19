package com.ymca.web.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.DonationDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.OpportunityDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.PromotionDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.Activity;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.Membership;
import com.ymca.model.Opportunity;
import com.ymca.model.Payer;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.Promotion;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.model.WaiversAndTC;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.model.OtherPrograms;
import com.ymca.web.util.Constants;
import com.ymca.web.util.propReaderForCurrentMemShip;

@Controller
public class HomePage extends BaseController {

	@Resource
	private PromotionDao 	promocodeDao; 
	
	@Resource
	private ItemDetailDao 	itemDetailsDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	
	@Resource
	private DonationDao donationDao;

	@Resource
	private AccountDao accountDao;

	
	@Resource
	private SignupDao signupDao;
	
	/*@Resource
	private ProductDao productDao;
	*/
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private OpportunityDao opportunityDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	private String agentUid ;
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	//@Transactional(value=TxType.REQUIRES_NEW)
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
	try{
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		
		Account account = null;
		Membership memberShip=null;
		User user = null;
		List<Signup> programList = null;
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			List<Signup> signupLst = signupDao.getByCustomerAndTypeAndStatusAndPaymentMethodNotNull(account, ProductTypeEnum.MEMBERSHIP.toString(), SignupStatusEnum.Active.toString());
			if(signupLst != null && !signupLst.isEmpty()){
				model.addAttribute("signup", signupLst.get(0));
			}
			request.setAttribute("userId", userId);
			/*if (account != null && account.getUser() != null) {
				User tempUser = null;
				for (Iterator<User> it = account.getUser().iterator(); it
						.hasNext();) {
					tempUser = it.next();
					if(tempUser.isPrimary()){
						user = tempUser;
					}

				}
			}*/
			user = getUserByAccount(account, user);

			if(user!=null)
			{
				 programList  = signupDao.findByContactNameAndTypeNotLike(user.getContactId().toString(), ProductTypeEnum.MEMBERSHIP.toString());
			}

			
			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			if (userCount > 1) {
				for (User u : account.getUser()) {
					if (user != null && !user.getEmailAddress().equalsIgnoreCase(u.getEmailAddress())) {
						userS.add(u);
					}
				}
			}

			model.addAttribute("userS", userS);

//			List<Program> programList = programDao.findAll();
			if(programList != null && !programList.isEmpty()){
				Collections.sort(programList, Signup.DATE_ORDER_DEC_COMPARATOR);
			}			
		
			model.addAttribute("programList", programList);

			HttpSession session = request.getSession();
			setAgentUidInSession();
            session.setAttribute("usInfo", user);
           
            session.setAttribute("programList", programList);
			
			//List<Donation> donationList = donationDao.findAll();

			//Collections.sort(donationList, Donation.DATE_ORDER_DEC_COMPARATOR);
			//model.addAttribute("donationList", donationList);
			// session.setAttribute("donationList", donationList);
			
			 //memberShip= account.getMembership();

			//List<Signup> donationLst =  signupDao.getByCustomerIdAndTypeDonation(account.getAccountId());
            //model.addAttribute("donationList", donationLst);
            /*List<Payment> paymentLst = new ArrayList<Payment>();
            List<Payment> donationLst = paymentDao.getByCustomerIdAndTypeDonation(account.getAccountId());		
			List<Activity> activityLst =  activityDao.getByCustomerIdAndTypeDonation(account.getAccountId());
			Map<Long, Activity> activityMap = new HashMap<Long, Activity>();			
			for(Activity activity : activityLst){
				activityMap.put(activity.getSignup().getSignupId(), activity);
			}
			if(activityLst != null && !activityLst.isEmpty()){
				for(Payment payment : donationLst){					
					if(payment !=  null && payment.getSignup() != null && payment.getSignup().getSignupId() != null){
						if(!activityMap.containsKey(payment.getSignup().getSignupId()) && !paymentLst.contains(payment)){
							paymentLst.add(payment);
						}
					}					
				}
			}else{
				paymentLst.addAll(donationLst);
			}
			model.addAttribute("donationList", paymentLst);*/
			
            List<Payment> paymentLst = new ArrayList<Payment>();
			List<Payment> donationLst = paymentDao.getByCustomerIdAndTypeDonation(account.getAccountId());		
			List<Activity> activityLst =  activityDao.getByCustomerIdAndTypeDonation(account.getAccountId());
			Map<Long, Activity> activityMap = new HashMap<Long, Activity>();			
			for(Activity activity : activityLst){
				activityMap.put(activity.getSignup().getSignupId(), activity);
			}
			Map<Long, Signup> signupMap = new LinkedHashMap<Long, Signup>();
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
						signupMap.put(payment.getSignup().getSignupId(), payment.getSignup());
						
					}
				}					
			}
			List signupIdLst = new ArrayList(signupMap.values());
			model.addAttribute("donationSignupMap", signupIdLst);
			model.addAttribute("donationList", paymentLst);
			 
			 model.addAttribute("memberShip", memberShip);
			 session.setAttribute("memberShip", memberShip);
			 
			 model.addAttribute("usInfo", user);
			 
			 
			 
			 
			// -----------------------------------
				List<Signup> signupList2=null;
				ItemDetail prod=null;
				List<OtherPrograms> OtherProgramsList = new ArrayList<OtherPrograms>();
				Promotion promocode=null;
				if (account != null && account.getUser() != null) {
					/*for (Iterator<User> it = account.getUser().iterator(); it
							.hasNext();) {
						user = it.next();*/
						
						////System.out.println("prod getLocation  before query-- "+user.getLocation().getId());
						Date startDate1 = new Date();
						//System.out.println(" startDate1-- "+startDate1);
						//prod=	itemDetailsDao.getByLocationsAndRegistrationStartDate(user.getLocation().getLocationId(),startDate1,user.getAreaOfInterest());
					
					
						if(prod!=null && prod.getId()!=null){
						//signupList2=signupDao.getByContactAndItem(user.getPartyId(),prod.getProductId());
						}
						if(signupList2!=null && signupList2.size()==0)
						{
							//if(prod.getItemDetails()!=null && prod.getItemDetails().getId()!=null)
							//{
								
							//promocode=promocodeDao.getDefaultPromocode(prod.getItemDetails().getId());
							//}
							OtherPrograms otherPrograms = new OtherPrograms();
							//otherPrograms.setName("Fany");
							//otherPrograms.setPrograms("Zumba Class");
							otherPrograms.setName(user.getPersonFirstName());
							otherPrograms.setPrograms(prod.getRecordName());
							if(promocode!=null && promocode.getPromoCode()!=null)
							otherPrograms.setDefaultPromoCode(promocode.getPromoCode());
							otherPrograms.setText("SIGN UP");
							/*OtherPrograms otherPrograms2 = new OtherPrograms();
							otherPrograms2.setName("Fany");
							otherPrograms2.setPrograms("Spin Class");
							otherPrograms2.setText("SIGN UP");
							*/OtherProgramsList.add(otherPrograms);
							//OtherProgramsList.add(otherPrograms2);
							model.addAttribute("otherPrograms", OtherProgramsList);
						}
					
				}

				
				
				//------------------------------------
			 
			 
			 //----------------------------------
			 
			 
			// Invoice start

			// CurrentDue
			Date startDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_YEAR, -14);
			Date formattedStartDate = cal.getTime();

			Date endDate = new Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(endDate);
			cal1.add(Calendar.DAY_OF_YEAR, 14);
			Date formattedEndDate = cal1.getTime();

			List<Invoice> invoiceList = null;
			List<Invoice> pastInvoiceList = null;
			//List<Payment> paymentList = null;
			
			 List<Invoice> requiredInvoiceList = new ArrayList<Invoice>();
			//double invoiceAmountTotal = 0;
			//double paymentAmountTotal = 0;
			double dueAmount = 0;
			invoiceList = invoiceDao.getInvoiceByCustomer(
					account.getAccountId(), startDate, formattedEndDate);
			if(invoiceList != null && invoiceList.size() > 0){
			for (Invoice invoice : invoiceList) {
				
				double openBalance = getOpenBalanceForInvoice(invoice);
				//System.out.println(" openBalance ::  "+openBalance);
				if(openBalance > 0){
					
					dueAmount += openBalance;
					invoice.setBalance(openBalance);
					requiredInvoiceList.add(invoice);
					//Signup s=invoice.getSignup();
					//Account a1=s.getCustomer();
				}
			/*	double invoiceAmount = 0;
				double paymentAmount = 0;
				invoiceAmount = invoice.getAmount();

				paymentList = paymentDao.getPaymentAmount(invoice
						.getInvoiceId());

				for (Payment payment : paymentList) {

					paymentAmount += payment.getAmount();

				}
				if(invoiceAmount>paymentAmount)
				{
					invoiceAmountTotal+=invoiceAmount;
					paymentAmountTotal+=paymentAmount;
				}
				//System.out.println("invoiceAmount--  " + invoiceAmount);
				//System.out.println("paymentAmount--  " + paymentAmount);*/
			}
			}
		//	double dueAmount = invoiceAmountTotal - paymentAmountTotal;
			//System.out.println("dueAmount--  " + dueAmount);
			// session.setAttribute("invoiceList", invoiceList);
			// session.setAttribute("requiredInvoiceList", requiredInvoiceList); 
			// Double d = new Double(dueAmount);
			if (dueAmount > 0){
				// session.setAttribute("dueAmount", dueAmount);
				model.addAttribute("dueAmount", dueAmount);
				
			}
			else{
				// session.setAttribute("dueAmount", 0);
				model.addAttribute("dueAmount", 0);
				
			}
			// Past Due
			
			//double invoicePastAmountTotal=0;
			//double paymenPastAmountTotal=0;
			double pastDueAmount = 0;
			 List<Invoice> requiredPastInvoiceList = new ArrayList<Invoice>();
			pastInvoiceList = invoiceDao.getInvoicePastDueByCustomer(
					account.getAccountId(), startDate);
			if(pastInvoiceList != null && pastInvoiceList.size() > 0){
			for (Invoice invoice : pastInvoiceList) {
				double openBalance = getOpenBalanceForInvoice(invoice);
				//System.out.println(" openBalance ::  "+openBalance);
				if(openBalance > 0){
					
					pastDueAmount += openBalance;
					invoice.setBalance(openBalance);
					requiredPastInvoiceList.add(invoice);
					//Signup s=invoice.getSignup();
					//Account a1=s.getCustomer();
				}
				
			}
			/*	double invoicePastAmount = 0;
				double paymentPastAmount = 0;
				invoicePastAmount = invoice.getAmount();

				paymentList = paymentDao.getPaymentAmount(invoice
						.getInvoiceId());

				for (Payment payment : paymentList) {

					paymentPastAmount += payment.getAmount();

				}
				//System.out.println("invoicePastAmount--  " + invoicePastAmount);
				//System.out.println("paymentPastAmount--  " + paymentPastAmount);
				if(invoicePastAmount>paymentPastAmount)
				{
					invoicePastAmountTotal+=invoicePastAmount;
					paymenPastAmountTotal+=paymentPastAmount;
				}*/
			}
			
			//double pastDueAmount = invoicePastAmountTotal - paymenPastAmountTotal;
			//System.out.println("pastDueAmount--  " + pastDueAmount);
			// session.setAttribute("pastInvoiceList", pastInvoiceList);
			//session.setAttribute("requiredPastInvoiceList", requiredPastInvoiceList); 
			if (pastDueAmount > 0){
				// session.setAttribute("pastDueAmount", pastDueAmount);
				model.addAttribute("pastDueAmount", pastDueAmount);
			}
			else{
				// session.setAttribute("pastDueAmount", 0);
				model.addAttribute("pastDueAmount", 0);
			}
			
			//Future Due
			Calendar startDateCal = Calendar.getInstance();
			startDateCal.set(Calendar.HOUR, 0);
			startDateCal.set(Calendar.MINUTE, 0);
			startDateCal.set(Calendar.SECOND, 0);
			startDateCal.set(Calendar.HOUR_OF_DAY, 0);	
			startDateCal.add(Calendar.DAY_OF_YEAR, 14);		
			
			Calendar endDateCal = Calendar.getInstance();
			endDateCal.set(Calendar.HOUR, 0);
			endDateCal.set(Calendar.MINUTE, 0);
			endDateCal.set(Calendar.SECOND, 0);
			endDateCal.set(Calendar.HOUR_OF_DAY, 0);	
			endDateCal.set(Calendar.MONTH,Calendar.DECEMBER);		
			endDateCal.set(Calendar.DATE, 31);
			endDateCal.set(Calendar.YEAR, 2200);
			Date futureDueEndDate = endDateCal.getTime();
			List<Invoice> futureDueInvoiceList = null;			
			double futureDueAmount = 0;
			futureDueInvoiceList = invoiceDao.getInvoiceByCustomer(account.getAccountId(), startDateCal.getTime(), futureDueEndDate);
			if (futureDueInvoiceList != null && futureDueInvoiceList.size() > 0) {
				for (Invoice invoice : futureDueInvoiceList) {
					double openBalance = getOpenBalanceForInvoice(invoice);					
					if (openBalance > 0) {
						futureDueAmount += openBalance;						
					}
				}
			}
			
			if (futureDueAmount > 0){				
				model.addAttribute("futureDueAmount", futureDueAmount);
			}
			else{
				
				model.addAttribute("futureDueAmount", 0);
			}

		//	double currentPastDueAmount = 0;
			//currentPastDueAmount=pastDueAmount+dueAmount;
			// session.setAttribute("currentPastDueAmount",currentPastDueAmount);
			//model.addAttribute("currentPastDueAmount",currentPastDueAmount);
			// Invoice end
			try{
				String accPartyId = null;
				if(account.getPartyId() != null){
					accPartyId = String.valueOf(account.getPartyId());
				}
				List<Opportunity> oppList = opportunityDao.getByTypeAndStage(ProductTypeEnum.DONATION.getValue(), accPartyId);
				//List<Opportunity> oppList = opportunityDao.getByTypeAndStage("Facility");
				List<Opportunity> annualDonationOppLst =  new ArrayList<Opportunity>();
				for(Opportunity opp :  oppList){
					if(opp.getOpptyRevenue() != null && !opp.getOpptyRevenue().isEmpty()){
						if(opp.getOpptyRevenue().get(0).getItemDetail() != null && opp.getOpptyRevenue().get(0).getItemDetail().getStartDate() != null && opp.getOpptyRevenue().get(0).getItemDetail().getEndDate() != null){
							Date todayDate = new Date();
							if(opp.getOpptyRevenue().get(0).getItemDetail().getStartDate().before(todayDate) &&  todayDate.before(opp.getOpptyRevenue().get(0).getItemDetail().getEndDate())){
								annualDonationOppLst.add(opp);
							}
						}
					}
				}
				model.addAttribute("donationOpptyLst", annualDonationOppLst);
			}catch(Exception e){
				log.error("Error Occured ",e);
			}
			return new ModelAndView("customer_home", model.asMap());
		} else {
			model.addAttribute("errMsg", "Please Login");
			return new ModelAndView("login", model.asMap());

		}
	}catch(Exception e){
		log.error("Error in home page",e);
		model.addAttribute("errMsg", "Please Login");
		return new ModelAndView("login", model.asMap());
	}
	}

	@RequestMapping(value = "/currentmembership", method = RequestMethod.GET)
	 public ModelAndView currentMembershipView() 
	 {

		JSONArray paymentDetailsArr = new JSONArray(); 
		
	  Authentication a = SecurityContextHolder.getContext()
	    .getAuthentication();
	  String userId = ((UserDetails) a.getPrincipal()).getUsername();
	  //System.out.println(userId);
	  Model model = new ExtendedModelMap();

	  Account account = null;
	  User user = null;
	  Membership member = null;
	  Signup signup=null;
	  List<Signup> signupList=null;
	  List<Signup> signupFamilyMemberList=null;
	  
	  
	  List<PaymentMethod> paymentList = null;
	  List<ItemDetail> products = null;
	  
	   if (userId != null && !"".equals(userId))
	   {
	   
	   account = accountDao.getByEmail(userId);
	   
	   
	   user = getUserByAccount(account, user);
	   
	   //member = account.getMembership();
	   
	   
	   paymentList = account.getPaymentMethod();
	  }
	   
	   try{
	   //signupList=signupDao.getByCustomerAndTypeLike(account.getAccountId(), "Membership");
	   signupList=signupDao.getByCustomerAndType(account,"Membership" );
	   for(Signup si:signupList ){
	      
	      if(si.getPaymentMethod()!=null){
	       signup=si;
	       break;
	      }
	      }
	   
	   }catch(Exception e){
	    //System.out.println("no acc");
		   log.error("Error in view current memmbership",e);
	   }

		if (account != null) {
			int userCount = account.getUser().size();
			List<User> userS = new ArrayList<User>();
			/*int countmembers = 0;
			if (userCount > 1) {
				for (User u : account.getUser()) {
					if (user.getPrimaryEmailAddress() != null && u.getPrimaryEmailAddress() != null	&& !user.getPrimaryEmailAddress().equalsIgnoreCase(u.getPrimaryEmailAddress())) {
						countmembers = countmembers + 1;
						userS.add(u);
					}
				}
			}*/
			List<Signup> signupLst = signupDao.getByCustomerAndStatusAndType(account, SignupStatusEnum.Active.toString(), ProductTypeEnum.MEMBERSHIP.toString());
			int countmembers = 0;
			for(Signup s : signupLst){
				if(user.getContactId() != s.getContact().getContactId()){
					countmembers = countmembers + 1;
					userS.add(s.getContact());
				}				
			}

		if (signup != null) {	       
			
			boolean isDraftCycle = false;
			if(signup.getDraftCycle() != null){
				String[] dratfCycleA = signup.getDraftCycle().split(",");
				if(dratfCycleA.length == 2){
					signup.setDraftCycleNumber(dratfCycleA[1]);
				}
			}
			
		
		Payer selfPayer =  null;
		if(signup != null){
			List<Payer> payerL = payerDao.findBySignupAndType(signup, Constants.SELF);
			if(payerL != null && !payerL.isEmpty()){
				selfPayer = payerL.get(0);
			}
		}
		
		
		// get payment details associated with sign up
		List<Payment> lstPaymentDetails = paymentDao.findBySignupAndType(signup,Constants.PAYMENT_TYPE_Payment);
		if(lstPaymentDetails.size()>0){
			
			//String ccNum = "";
			for(Payment payment: lstPaymentDetails){
				PaymentMethod paymentMethod = null;
				String paymentMethodIdStr = payment.getPaymentMethod();
				try{
					if(paymentMethodIdStr != null){
						paymentMethod = paymentMethodDao.findById(Long.parseLong(paymentMethodIdStr));
					}
				}catch(Exception e11){
					log.error("Exception : "+e11.getStackTrace());
				}
				JSONObject payObj = new JSONObject();
				payObj.put("transactionId", payment.getPaymentNumber());
				if (paymentMethod != null) {
					payObj.put("ccnumber", paymentMethod.getCardNumber());
				}
				payObj.put("amount", payment.getAmount());
				payObj.put("paymentDate", payment.getPaymentDate());
				
				paymentDetailsArr.add(payObj);
			}
			
		}
		
		
  		  model.addAttribute("paymentDetailsArr", paymentDetailsArr);
	      model.addAttribute("signup",signup);
	      model.addAttribute("selfPayer",selfPayer);
	      HashMap<String,String> getPropr=new HashMap<String,String>();
	      getPropr=propReaderForCurrentMemShip.loadPropInfo();
	   
	      model.addAttribute("labelVal", getPropr);
	      //model.addAttribute("userCount", countmembers);
	      model.addAttribute("userCount", countmembers);
	      model.addAttribute("userS", userS);
	      model.addAttribute("usInfo", user);
	      model.addAttribute("member", member);
	      model.addAttribute("paymentInfoLst" , paymentList);     
	      model.addAttribute("volunteerActivity", getVolunteerActivity());
	      model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
	      List<WaiversAndTC> waiversAndTCLst = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		  if(waiversAndTCLst != null && !waiversAndTCLst.isEmpty()){
			model.addAttribute("waiversTAndC", waiversAndTCLst.get(0));
		  }		
			
	   
	    }
	    else{
			model.addAttribute("userS", userS);
			model.addAttribute("usInfo", user);
			model.addAttribute("member", member);
	      //System.out.println("No Active MembersHip now");
	      model.addAttribute("NoMembersHip", " WELCOME BACK ");
	     }
	    
	   }
	   else {
	    model.addAttribute("errMsg",com.ymca.web.util.Constants.NO_RECORDS_FOUND);
	      
	   }
	   
	    return new ModelAndView("currentmembership", model.asMap());
	  
	 }

	/*@RequestMapping(value = "/currentmembership", method = RequestMethod.GET)
	public ModelAndView currentMembershipView() 
	{

		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();
		//System.out.println(userId);
		Model model = new ExtendedModelMap();

		Account account = null;
		User user = null;
		Membership member = null;
		Signup signup=null;
		
		
		List<PaymentMethod> paymentList = null;
		
		
			if (userId != null && !"".equals(userId))
			{
			
			account = accountDao.getByEmail(userId);
			
			
			user = getUserByAccount(account, user);
			
			member = account.getMembership();
			
			
			paymentList = account.getPaymentMethod();
		}
			
			signup=signupDao.findByContactNameAndStatusAndTypeLike(member.getContactName(), "Active", "Memberships");
			if(account != null)
			{	
				int userCount = account.getUser().size();
				List<User> userS = new ArrayList<User>();
				int countmembers = 0;
				if (userCount > 1) 
				{
					for (User u : account.getUser()) 
					{
						if (!user.getPrimaryEmailAddress().equalsIgnoreCase(u.getPrimaryEmailAddress())) 
						{
						countmembers = countmembers + 1;
						userS.add(u);
						}
					}
				}
				
				if(signup!=null)
				{
							
						model.addAttribute("signup",signup);
				
						//List<Product> products = productDao.findAll();


						HashMap<String,String> getPropr=new HashMap<String,String>();
						getPropr=propReaderForCurrentMemShip.loadPropInfo();
			
						model.addAttribute("labelVal", getPropr);
						model.addAttribute("userCount", countmembers);

						model.addAttribute("userS", userS);
						model.addAttribute("usInfo", user);
						model.addAttribute("member", member);
						model.addAttribute("paymentInfoLst" , paymentList);	    
						//model.addAttribute("products", products);
						model.addAttribute("locations", locationDao.findAll());
			
				}
				else{
				
						//System.out.println("No Active MembersHip now");
						model.addAttribute("NoMembersHip", " WELCOME BACK ");
					}
				
			}
			else {
				model.addAttribute("errMsg",com.ymca.web.util.Constants.NO_RECORDS_FOUND);
						
			}
			
				return new ModelAndView("currentmembership", model.asMap());
		
	}*/
	
	
	@RequestMapping(value = "/fullUpcomingPrograms", method = RequestMethod.GET)
	public ModelAndView fullUpcomingPrograms() {

		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();

		//Model model = new ExtendedModelMap();

				return new ModelAndView("fullUpcomingPrograms");

	}
	
	/*@RequestMapping(value = "/fullDonationsList", method = RequestMethod.GET)
	public ModelAndView fullDonationsList() {

		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = ((UserDetails) a.getPrincipal()).getUsername();

			return new ModelAndView("fullDonationsList");

	}*/
	private double getOpenBalanceForInvoice(Invoice invoice){
		double openBalanceOnInvoice = 0, trueInvoiceValue = 0, sumOfDebitPayment = 0, sumOfCreditMemoFAWaiver = 0, sumOfCreditMemoWaiver = 0;
		double sumOfCreditMemoWriteOff = 0, sumOfCreditMemoRefund = 0, sumOfNSF = 0, sumOfChargeBack = 0,sumOfCreditMemoADJ = 0 ;
		//System.out.println(" invoice id  : "+invoice.getInvoiceId()+"  invoice amount  :"+invoice.getAmount() );
		List<Payment> payments = paymentDao.getPaymentListForInvoice(invoice.getInvoiceId());
		for (Payment payment : payments) {
			//System.out.println(" Type : "+payment.getType()+ " TypeEnum : "+PaymentTypeEnum.getEnumByString(payment.getType())+" Amount : "+payment.getAmount());
			switch (PaymentTypeEnum.getEnumByString(payment.getType())) {
				
				case CreditMemoFAWaiver:
					sumOfCreditMemoFAWaiver += payment.getAmount();
					break;
				case CreditMemoWaiver:
					sumOfCreditMemoWaiver += payment.getAmount();
					break;
				case CreditMemoRefund:
					sumOfCreditMemoRefund += payment.getAmount();
					break;
				case CreditMemoWriteOff:
					sumOfCreditMemoWriteOff += payment.getAmount();
					break;
				case Debit:
					sumOfDebitPayment += payment.getAmount();
					break;
				case NSF:
					sumOfNSF += payment.getAmount();
					break;
				case ChargeBack:
					sumOfChargeBack += payment.getAmount();
					break;
				case CreditMemoADJ:
				    sumOfCreditMemoADJ+= payment.getAmount();
				case Payment:
				    //sumOfCreditMemoADJ+= payment.getAmount();   
				default:
					break;
			} 
		}
		
		//System.out.println(" sumOfCreditMemoFAWaiver  "+sumOfCreditMemoFAWaiver);
		//System.out.println(" sumOfCreditMemoWaiver  "+sumOfCreditMemoWaiver);
		//System.out.println(" sumOfCreditMemoRefund  "+sumOfCreditMemoRefund);
		//System.out.println(" sumOfCreditMemoWriteOff  "+sumOfCreditMemoWriteOff);
		//System.out.println(" sumOfDebitPayment  "+sumOfDebitPayment);
		//System.out.println(" sumOfNSF  "+sumOfNSF);
		//System.out.println(" sumOfChargeBack  "+sumOfChargeBack);
		
		trueInvoiceValue = (invoice.getAmount()+sumOfCreditMemoADJ) - (sumOfCreditMemoWaiver + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff);
		//System.out.println(" trueInvoiceValue "+trueInvoiceValue);
		
		openBalanceOnInvoice = trueInvoiceValue - (sumOfDebitPayment + sumOfCreditMemoRefund) + ((sumOfNSF > 0 ? sumOfNSF : sumOfChargeBack));
		//System.out.println(" openBalanceOnInvoice "+openBalanceOnInvoice);
		
		return openBalanceOnInvoice;
	}
}