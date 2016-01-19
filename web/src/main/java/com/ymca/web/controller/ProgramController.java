package com.ymca.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.HealthHistoryDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.ItemDetailDaysDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.PromotionDao;
import com.ymca.dao.SetpUpFeeDao;
import com.ymca.dao.SignUpDaysDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.TandCDao;
import com.ymca.dao.UserDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.Account;
import com.ymca.model.HealthHistory;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Location;
import com.ymca.model.Payer;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.model.WaiversAndTC;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.CapacityManagementService;
import com.ymca.web.service.FinancialAssistanceService;
import com.ymca.web.service.PaymentService;
import com.ymca.web.service.SignUpService;
import com.ymca.web.util.Constants;

@Controller
public class ProgramController extends BaseController {

	@PersistenceContext
	public EntityManager em; 
	
	//@Resource(name="portalAuthenticationProvider")
	@Resource
    private UserDetailsService userService;
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private PromotionDao promocodeDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	@Resource
	private JetPayPaymentDao jetPayPaymentDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private ItemDetailDaysDao itemDaysDao;
	
	@Resource
	private ActivityDao interactionDao;
	
	@Resource
	private TandCDao tandCDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	
	@Autowired
    protected AuthenticationManager authenticationManager;
	
	@Resource
	private PaymentService paymentService;
	
	@Resource
	private FinancialAssistanceService financialAssistanceService;
	
	@Resource
	private SetpUpFeeDao setpUpFeeDao;
	
	@Resource
	private PayerDao payerDao;
	
	@Resource
	private HealthHistoryDao healthHistoryDao;
	
	@Resource
	private SignUpDaysDao signUpDaysDao;
	
	@Resource
	private WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	private CapacityManagementService capacityManagementService ;

	@Resource
	private SignUpService signUpService ;
	
	List<String> daysArr = Arrays.asList("Sun","Mon","Tues","Wed","Thurs","Fri","Sat");
	
	@RequestMapping(value="/addprogramtocart", method=RequestMethod.GET)
    public ModelAndView addprogramtocart(final HttpServletRequest request, final HttpServletResponse response, Model model) { 
//		Model model = new ExtendedModelMap();
		
		/*
		HashMap programsLbl = new HashMap<String, String>();
		for(SystemProperty sysprop : systemPropertyDao.getPropertyByPageName(Constants.PROGRAM_PAGENAME_LABEL)){
			programsLbl.put(sysprop.getKeyName(), sysprop.getKeyValue());
		}*/
		
		model.addAttribute("productcategories", itemDetailsDao.getDistinctTypeForCategory(ProductTypeEnum.PROGRAM.toString()));
		//model.addAttribute("programlabels", programsLbl);
        model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
        
        // check if user is logged in
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			//model.addAttribute("errMsg", "Your session is expired");
			//return new ModelAndView("login", model.asMap());
			////System.out.println(e);
		}
		
		Account account = null;
    	User user =  null;	
    	List<PaymentMethod> paymentMethodList = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			request.setAttribute("userId", userId);				
			user = getUserByAccount(account, user);
			paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
    	}
    	
    	if(account != null){	    		    	    	
	        model.addAttribute("accInfo", account);
	        model.addAttribute("usInfo", user);	
	        
	        int userCount = account.getUser().size();
	        List<User> userS = new ArrayList<User>();
	        int countmembers = 0;
	        if(userCount>1){
		        for(User u: account.getUser()){
		        	if(user.getPartyId() != u.getPartyId() && u.isActive()){
		        		countmembers = countmembers + 1;
		        		userS.add(u);
		        	}
		        }
			}
	        model.addAttribute("userCount", countmembers);
	        model.addAttribute("userS", userS);
	        model.addAttribute("AlluserS", account.getUser());
	        
	        model.addAttribute("paymentInfoLst" , paymentMethodList);	     
	        model.addAttribute("userCount", countmembers);
	        model.addAttribute("gottocontact", "true");
	        
	        model.addAttribute("selectedLocation", user.getScLocation());
    	}
    	else {
			//model.addAttribute("errMsg", "Please Login");
			//return new ModelAndView("login", model.asMap());
    		return new ModelAndView("loginpop", model.asMap());
    		
		}	
		return new ModelAndView("searchHome", model.asMap());
	}
	
	@RequestMapping(value="/programRegistration", method=RequestMethod.GET)
    public ModelAndView programRegistration(final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		
		model.addAttribute("productcategories", itemDetailsDao.getDistinctTypeForCategory(ProductTypeEnum.PROGRAM.toString()));
        //model.addAttribute("locations", locationDao.findAll());
		model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
        
        // check if user is logged in
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			//model.addAttribute("errMsg", "Your session is expired");
			//return new ModelAndView("login", model.asMap());
		}
		
		Account account = null;
    	User user =  null;	
    	List<PaymentMethod> paymentMethodList = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			request.setAttribute("userId", userId);				
			user = getUserByAccount(account, user);
			//paymentList = account.getPaymentMethod();
			paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
    	}
    	
    	if(account != null){	    		    	    	
	        model.addAttribute("accInfo", account);
	        model.addAttribute("usInfo", user);	
	        
	        int userCount = account.getUser().size();
	        List<User> userS = new ArrayList<User>();
	        int countmembers = 0;
	        if(userCount>1){
		        for(User u: account.getUser()){
		        	if(user.getPartyId() != u.getPartyId() && u.isActive()){
		        		countmembers = countmembers + 1;
		        		userS.add(u);
		        	}
		        }
			}
	        
	        JSONArray resultList = new JSONArray();
	        HealthHistory hh = user.getHealthHistory();
	        JSONObject jsonObj = null;
	        List<User> accountContactList = userDao.findByEndDateAndCustomerEmail(new Date(),userId);
	        if(accountContactList != null && !accountContactList.isEmpty()){
	        	for (User tempUser : accountContactList) {
	        		hh = tempUser.getHealthHistory();
	        		jsonObj = new JSONObject();
	        		jsonObj.put("firstName", tempUser.getFirstName());
            		jsonObj.put("lastName", tempUser.getLastName());
            		jsonObj.put("contactId", tempUser.getContactId());
	            	if(hh != null){
	            		jsonObj.put("healthHistoryId", hh.getId());
	            		jsonObj.put("insuranceCompany", hh.getInsuranceCompany_c());
	            		jsonObj.put("instructions", hh.getInstructions_c());
	            		jsonObj.put("listCurrentMedications", hh.getListCurrentMedications_c());
	            		jsonObj.put("currentMedicationPurpose", hh.getCurrentMedicationPurpose_c());
	            	}else{
	            		jsonObj.put("healthHistoryId", "0");
	            	}
	            	resultList.add(jsonObj);
				}
	        }
			
        	model.addAttribute("contactHealthHistoryList", resultList);
	        model.addAttribute("userCount", countmembers);
	        model.addAttribute("userS", userS);
	        model.addAttribute("AlluserS", account.getUser());
	        model.addAttribute("paymentInfoLst" , paymentMethodList);	     
	        model.addAttribute("gottocontact", "false");
	        model.addAttribute("selectedLocation", user.getScLocation());
	        
    	}/*
    	else {
			model.addAttribute("errMsg", "Please Login");
			return new ModelAndView("login", model.asMap());
			
		}	*/
    	
    	    	
    	//FA option2 Start
    	/*String lstCartItem = request.getParameter("cartItems");
    	if (lstCartItem != null) {
    		List<String> lstStrCartItem = Arrays.asList(lstCartItem.split(","));
        	List<AccountFinancialAid> customerFaList = null;
        	if (lstStrCartItem != null) {
        		if(lstStrCartItem.size()>0){
            		for(String lstcartItems: lstStrCartItem){
            			List<String> cartItems = Arrays.asList(lstcartItems.split("__")); 
            			String itemDetailId = cartItems.get(0);
            			if (itemDetailId != null) {
            				ItemDetails itemDetails = itemDetailsDao.getById(Long.parseLong(itemDetailId));;
                			if (itemDetails != null) {
            					customerFaList = financialAssistanceService.computeFA(itemDetails,account.getAccountId());
            				}
            			}            			
            		}
            	}
        	}    		
    	}		*/
    	//FA option2 End
    	//FA data stub Start
    	/*AccountFinancialAid accountFinancialAid = new AccountFinancialAid();
    	accountFinancialAid.setAccount_c("test");
    	model.addAttribute("accountFinancialAid", accountFinancialAid);*/
    	/*//System.out.println("FA service start.");
    	Calendar cal = Calendar.getInstance();
    	cal.set(2015, Calendar.DECEMBER, 21); //Year, month and day of month
    	Date date = cal.getTime();
    	Long customerId = new Long("2");
    	ItemDetail itemDetails = new ItemDetail();
    	itemDetails.setFAEligible("Yes");
    	itemDetails.setStartDate(date);
    	itemDetails.setType("CHILD CARE");
    	List<AccountFinancialAid> customerFaList = financialAssistanceService.computeFA(itemDetails,customerId);
    	//System.out.println("customerFaList size:" + customerFaList.size());
    	////System.out.println("accountFinancialAid1:" + customerFaList.get(0).toString());
    	//model.addAttribute("accountFinancialAid1", customerFaList.get(0));    	
    	if(customerFaList!=null && customerFaList.size()>0 && customerFaList.get(0)!=null)
    		model.addAttribute("accountFinancialAid", customerFaList.get(0));
    	else
    		model.addAttribute("accountFinancialAid", new AccountFinancialAid());
    	//FA data stub End
*/		return new ModelAndView("programRegistration", model.asMap());
    }
	
	@RequestMapping(value="/getProgramDetails", method=RequestMethod.GET)
    public @ResponseBody String  getProgramDetails(@RequestParam String strLocation, @RequestParam String category, @RequestParam String productname, final HttpServletRequest request, final HttpServletResponse response) { 	
		
		Integer minAge, maxAge;
		
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		DateFormat inputsdf = new SimpleDateFormat("M/d/yyyy");  
		DateFormat inputmonthsdf = new SimpleDateFormat("MMM yyyy");  
		
		String minAgeStr = request.getParameter("age_min");
		String maxAgeStr = request.getParameter("age_max");
		
		String datestart = request.getParameter("datestart");
		String dateend = request.getParameter("dateend");
		
		//Location loc= new Location();
		Long locId = Long.parseLong(strLocation.toString());
		//loc.setId(locId);
		Location loc= locationDao.findOne(locId);
		
		Date startDate;
        Date endDate;
		
		try {
			startDate = sdf.parse(sdf.format(inputsdf.parse(datestart)));
	        endDate = sdf.parse(sdf.format(inputsdf.parse(dateend)));
	        
	        if(minAgeStr != null && !"".equals(minAgeStr))
	        	minAge = Integer.parseInt(minAgeStr);
	        else
	        	minAge = null;
	        
	        if(maxAgeStr != null && !"".equals(maxAgeStr))
	        	maxAge = Integer.parseInt(maxAgeStr);
	        else
	        	maxAge = null;
	        
		} catch (ParseException e) {
			log.error("Error  ",e);
			return null;
		} catch (Exception e1) {
			log.error("Error  ",e1);
			return null;
		}
		try {
			/*
			List<Object[]> itemDetailsSession = null;
			if("".equals(productname.trim())){
				itemDetailsSession = productDao.getMembershipProgramSession(startDate,endDate,category,locations);
			} else {
				itemDetailsSession = productDao.getMembershipProgramSession(startDate,endDate,category,productname,locations);
			}*/
			
			List<Object[]> itemDetailsObj = getMembershipProgramSession(category,productname, loc, startDate, endDate, minAge, maxAge);
			
			if(itemDetailsObj.size()==0){
				return null;
			}

			JSONArray json = new JSONArray();
			for(Object obj: itemDetailsObj){
				ItemDetail i = (ItemDetail) obj;	
				
				//double membertierPrice = 0D;
				//double nonmembertierPrice = 0D;
				  
				List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(i.getId(), Constants.Status_Active, Constants.Status_Active);
				JSONArray priceArr = new JSONArray();
				
				for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
					if(pricingRule!=null && pricingRule.getPricingRule()!=null){
						JSONObject priceObj = new JSONObject();
						
						priceObj.put("type",pricingRule.getPricingRule().getType());
						priceObj.put("priceoption",pricingRule.getPricingRule().getPriceOption());
						
						if(pricingRule.getPricingRule().getTierPrice() != null)
							priceObj.put("memberPrice",0);
						else
							priceObj.put("memberPrice",pricingRule.getPricingRule().getTierPrice());
							
						if(pricingRule.getPricingRule().getNonmemberTierPrice() != null)
							priceObj.put("nonmemberPrice",0);
						
						else
							priceObj.put("nonmemberPrice",pricingRule.getPricingRule().getNonmemberTierPrice());
						
						/*
						if(Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
							
						}
						else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
							
						}
						else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
							
						}
						else if(Constants.SETUPFEE.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
							
						}*/
						priceArr.add(priceObj);
					}
				}
				
				
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("id", i.getId());
				jsonObj.put("productId", i.getId());
				jsonObj.put("startDate", i.getStartDate());
				jsonObj.put("endDate", i.getEndDate());
				jsonObj.put("startTime", i.getStartTime());
				jsonObj.put("endTime", i.getEndTime());
				jsonObj.put("capacity", i.getWebCapacity());
				jsonObj.put("remainingCapacity", convertNullToZero(i.getRemainingCapacity()));
				if(StringUtils.isBlank(getAgentUidFromSession())){
					jsonObj.put("actualRemainingCapacity", 0L);
				}else{
					jsonObj.put("actualRemainingCapacity", convertNullToZero(i.getActualRemainingCapacity()));
				}
				jsonObj.put("gender", i.getGender());
				jsonObj.put("minAge", i.getMinAge());
				jsonObj.put("maxAge", i.getMaxAge());
				jsonObj.put("priceArr", priceArr);
				jsonObj.put("productName", i.getRecordName());
				jsonObj.put("productDesc", i.getDescription());
				jsonObj.put("productCategory", i.getType());
				jsonObj.put("productType", i.getCategory());
				jsonObj.put("branchName", i.getLocation().getRecordName());
				jsonObj.put("tier", i.getLocation().getTier());
				
				String sessionName = "";
			    String instructorName = "";
			    String sessionDays = "";
			    for(ItemDetailDays idys : i.getItemDays()){
			    	sessionName = idys.getSessionName();
			    	instructorName = idys.getInstructorName();
			    	sessionDays = sessionDays + "," + idys.getId();
			    }
			    
				jsonObj.put("sessionName", sessionName);
				jsonObj.put("instructorName", instructorName);
				jsonObj.put("dayId", sessionDays);
				
				json.add(jsonObj);
			}
			
			return json.toString();
		} catch (Exception e1) {
			log.error("Error  ",e1);
			e1.printStackTrace();
			return null;
		}
		
	}

	private List<Object[]> getMembershipProgramSession(String category,
			String productname, Location location, 
			Date startDate, Date endDate, 
			Integer minAge, Integer maxAge) {
		
		List<Location> bayareaLocations = new ArrayList<Location>();
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
		//Root<ItemDays> ids = query.from(ItemDays.class);
		//Join<ItemDays, ItemDetails> i = ids.join("itemDetails",JoinType.INNER);
		Root<ItemDetail> i = query.from(ItemDetail.class);
		Join<ItemDetail, Location> l = i.join("location",JoinType.INNER);

		ParameterExpression<String> prodType = builder.parameter(String.class);
		ParameterExpression<Date> sd = builder.parameter(Date.class);
		ParameterExpression<Date> ed = builder.parameter(Date.class);
		ParameterExpression<String> cat = builder.parameter(String.class);
		ParameterExpression<String> prodName = builder.parameter(String.class);
		ParameterExpression<String> prodDescription = builder.parameter(String.class);
		ParameterExpression<Location> loc = builder.parameter(Location.class);
		ParameterExpression<Integer> minAgeParam = builder.parameter(Integer.class);
		ParameterExpression<Integer> maxAgeParam = builder.parameter(Integer.class);
		
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(
				builder.and(
					builder.equal(i.get("subType"), prodType),
					builder.greaterThanOrEqualTo(i.<Date>get("startDate"), sd),
					builder.lessThanOrEqualTo(i.<Date>get("startDate"), ed),
					builder.equal(i.get("type"), cat)
				)
		);
		
		if(minAge != null && !"".equals(minAge) && maxAge != null && !"".equals(maxAge)){
			predicates.add(
				builder.or(
					builder.or(
						builder.between(minAgeParam, i.<Integer>get("minAge"), i.<Integer>get("maxAge")),
						builder.between(maxAgeParam, i.<Integer>get("minAge"), i.<Integer>get("maxAge"))
					),
					
					builder.and(
						builder.between(i.<Integer>get("minAge"), minAgeParam, maxAgeParam),
						builder.between(i.<Integer>get("maxAge"), minAgeParam, maxAgeParam)
					)
				)
			);
		}
		
		if(!"".equals(productname.trim())){
			predicates.add(builder.or(
					builder.like(i.<String>get("recordName"), prodName),
					builder.like(i.<String>get("description"), prodDescription)
				));
		}
		
		String allBranchLocation = Constants.LOCATION_ALL_BRANCH;
		String bayAreaLocation = Constants.LOCATION_BAYAREA;
		if(systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH).size()>0){
			allBranchLocation = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_ALL_BRANCH).get(0).getKeyValue().toString();
		}
		if(systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_BAYAREA).size()>0){
			bayAreaLocation = systemPropertyDao.getPropertyByKeyName(Constants.LOCATION_BAYAREA).get(0).getKeyValue().toString();
		}
		if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(allBranchLocation)){}
		else if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(bayAreaLocation)){
			 bayareaLocations = locationDao.getLocationsByArea(Constants.LOCATION_BAYAREA);
			 Collection<Location> lstlocations = bayareaLocations;
			 //Expression<Collection<Locations>> lstLocations = lstlocations;
			 Expression<Location> lstloc = i.get("location");
			 predicates.add(lstloc.in(lstlocations));
			 
			 
		}
		else {
			predicates.add(builder.and(builder.equal(i.<Location>get("location"), loc)));
		}
		query.multiselect(i);
		/*
		query.multiselect("instructorName","sessionName",i.get("starttime"),i.get("endtime"),"itemDaysId",i.get("capacity"),"capacity"
				,i.get("price"),i.get("price"),i.get("startdate"),i.get("enddate")
				,p.get("productId"),p.get("productName"),p.get("description")
				,l.get("branchName"),l.get("tier"),i.get("id")); */
		query.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<Object[]> typequery = em.createQuery(query);
		typequery.setParameter(prodType, ProductTypeEnum.PROGRAM.toString().toUpperCase());
		typequery.setParameter(sd, startDate);
		typequery.setParameter(ed, endDate);
		typequery.setParameter(cat, category);
		if(!"".equals(productname.trim())){
			typequery.setParameter(prodName, "%"+productname.trim()+"%");
			typequery.setParameter(prodDescription, "%"+productname.trim()+"%");
		}
		
		if(minAge != null && !"".equals(minAge) && maxAge != null && !"".equals(maxAge)){
			typequery.setParameter(minAgeParam, minAge);
			typequery.setParameter(maxAgeParam, maxAge);
		}
		
		if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(allBranchLocation)){}
		else if(location.getRecordName()!=null && location.getRecordName().equalsIgnoreCase(bayAreaLocation)){
			/*String locat = "3,4";
			typequery.setParameter(lstloc, locat);*/
		}
		else {
			typequery.setParameter(loc, location);
		}
		
		List<Object[]> lstObj = typequery.getResultList();
		return lstObj;
	}
	
	@RequestMapping(value="/signupProgram", method=RequestMethod.POST)
	//@Transactional(value=TxType.REQUIRES_NEW)
    public @ResponseBody String  signupProgram() {
		String tansId = "";
		List<String> cartItemTypeOnCart = new ArrayList<String>();
		Boolean mixedCart = false;
		Boolean isSetupFeeInserted = false;
		Boolean isRegistrationFeeInserted = false;
		String paymentId = request.getParameter("paymentId");
		String jp_request_hash = request.getParameter("jp_request_hash");
		String orderNumber = request.getParameter("orderNumber");
		String paymentMode = request.getParameter("paymentMode");
		String bankAccountNumber = request.getParameter("bankAccountNumber");
		String bankRoutingNumber = request.getParameter("bankRoutingNumber");
		String checkNumber = request.getParameter("checkNumber");
		String accountName = request.getParameter("accountName");	
		
		String lstCartItem = request.getParameter("cartItems");
		List<String> cartItemsStr = Arrays.asList(lstCartItem.split("_AND_"));
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			log.error(" failed userId :   "+userId);
			return "FAIL";
		}
    	
    	try{
    		Account customer = null;
        	if(userId != null && !"".equals(userId)){
        		customer = accountDao.getByEmail(userId);		
        		customer.setCheckNumber(checkNumber);
        		customer.setPaymentDesc(bankAccountNumber+","+bankRoutingNumber+","+checkNumber+","+accountName);
        	}
        	String portalLastModifiedBy = getPortalLastModifiedBy();
        	List<Signup> signups = new ArrayList<Signup>();
	    	if(cartItemsStr.size()>0){
	    		for(String cartItemStr: cartItemsStr){
	    			if(cartItemStr!=null){
	    				JSONObject cartItemMap = JSONObject.fromObject(cartItemStr);
		    			Object itemDetailId = cartItemMap.get("itemDetailId");
		    			Object contactId = cartItemMap.get("contactId");
		    			Object type = cartItemMap.get("programType");
		    			Object category = cartItemMap.get("category");
		    			Object waitlist = cartItemMap.get("waitlist");
		    			Object setupFee = cartItemMap.get("setupFee");
		    			Object parentSignUpItem = cartItemMap.get("parentSignUpItem");
		    			//Object activities = cartItemMap.get("activities");
		    			Object hasWLDaysObj = cartItemMap.get("hasWLDays");
		    			Object WLDaysObj = cartItemMap.get("WLDays");
		    			Object selectedDaysObj = cartItemMap.get("selectedDaysId");
		    			//Object signupAmountObj = cartItemMap.get("signupAmount");
		    			Object registrationFee = cartItemMap.get("registrationFee");
		    			Object selectedStartDate = cartItemMap.get("selectedStartDate");
		    			//Object itemprice = cartItemMap.get("itemprice");
		    			Object promosObj = cartItemMap.get("promos");
		    			Object invoicesObj = cartItemMap.get("invoices");
		    			Object fa = cartItemMap.get("fa");
		    			JSONArray promosArr = null, invoicesArr = null;
		    			if(promosObj != null){
		    				promosArr = JSONArray.fromObject(promosObj.toString());
		    			}
		    			
		    			if(invoicesObj != null){
		    				invoicesArr = JSONArray.fromObject(invoicesObj.toString());
		    			}
		    			
		    			
		    			ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId.toString()));
		    			User u =  userDao.getOne(Long.parseLong(contactId.toString()));
		    			
		    			String programType = type.toString();
		    			//String programCategory = category.toString();
		    			cartItemTypeOnCart.add(programType);
		    			if(Constants.FACILITY_TYPE.equalsIgnoreCase(programType) || Constants.CAMP_TYPE.equalsIgnoreCase(programType) || Constants.PRODUCT_CATEGORY_PROGRAM.equalsIgnoreCase(programType) || Constants.PRODUCT_CATEGORY_EVENT.equalsIgnoreCase(programType) || (Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(programType) && (category != null && Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equalsIgnoreCase(category.toString()))) ){
		    				
		    				if(Boolean.valueOf(waitlist.toString())){
			    				capacityManagementService.checkStopConfirmedSignups(itemDetail, null);
			    			}
		    				
		    				// get portal last modified by 
		    				Signup signupprogram = paymentService.savesignup(paymentId, customer, cartItemMap, itemDetail, u, portalLastModifiedBy);
							signups.add(signupprogram);
							
							paymentService.saveSignupPromos(signupprogram, promosArr);
							
							// save parent Id 
							signupprogram.setParentUnqiueId((String) parentSignUpItem);
							
							paymentService.saveSignupAssociatedOtherContacts(signupprogram, cartItemMap);
							Payer payer = paymentService.savepayer(paymentId, customer, cartItemMap, itemDetail, signupprogram, paymentMode);
			  
			    			// capacity management block
			    			if(Boolean.valueOf(waitlist.toString())){ // call only if program is waitlisted
			    				capacityManagementService.signupCapacityManagement(itemDetail, signupprogram, false);
			    			
			    			}
			    			signUpService.saveActivity(customer, u, signupprogram);
			    			
			    			Date currDate = new Date();
			    			if(!Boolean.valueOf(waitlist.toString()) && (payer.getEnddate()==null || "".equals(payer.getEnddate()) || currDate.getTime()<=payer.getEnddate().getTime())){
			    				// call only for confirmed items
				    			List<Invoice> invoices = paymentService.saveCartInvoice(customer, u, signupprogram, payer, invoicesArr, fa);
				    			if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name())){
				    				paymentService.saveCartPayment(paymentId, jp_request_hash, orderNumber, customer, u, signupprogram, payer, invoices, paymentMode, fa);
				    			}
				    			Double setupAmount = Double.parseDouble(setupFee.toString());
				    			//System.out.println("Set up amount: "+setupAmount);
				    			if(!isSetupFeeInserted && setupAmount.doubleValue()>0){
				    				signUpService.saveSetupFee(setupAmount, u);
				    				isSetupFeeInserted = true;
				    			}
				    			
				    			Double registrationAmount = Double.parseDouble(registrationFee.toString());
				    			//System.out.println("Set up amount: "+setupAmount);
				    			if(!isRegistrationFeeInserted && registrationAmount.doubleValue()>0){
				    				signUpService.saveRegistrationFee(registrationAmount, u, selectedStartDate);
				    				isRegistrationFeeInserted = true;
				    			}
				    			
				    			/*if(jetpay!=null)
				    				tansId = jetpay.getTransId();*/
			    			}
		    			}
		    			else if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(programType)){
		    				// day wise management for child care and in service
		    				String hasWLDays = hasWLDaysObj.toString();
		    				String selectedDays = selectedDaysObj.toString().trim();
		    				String WLDays = WLDaysObj.toString().trim();
		    				List<String> lstSelectedDays = Arrays.asList(selectedDays.split(";"));
		    				
		    				List<String> lstWLDays = new ArrayList<String>();
		    				if(WLDays!=null && !"".equals(WLDays.trim()))
		    					lstWLDays = Arrays.asList(WLDays.split(";"));
		    				
		    				if(lstSelectedDays.size()>lstWLDays.size()){ // save confirmed signup because selected days > WL days
		    					Signup signupprogram = paymentService.savesignup(paymentId, customer, cartItemMap, itemDetail, u,portalLastModifiedBy, SignupStatusEnum.Active.toString());
		    					
		    					paymentService.saveSignupPromos(signupprogram, promosArr);
		    					
		    					// save signup days
		    					for(int k=0; k<lstSelectedDays.size();k++){
		    						boolean insertDay = true;
		    						if(lstWLDays.size()>0){
			    						for(String wlDays: lstWLDays){
			    							Integer WLdayId = daysArr.indexOf(wlDays);
			    							if(lstSelectedDays.get(k).equalsIgnoreCase(WLdayId.toString())){
			    								insertDay = false;
			    								break;
			    							}
			    						}
		    						}
		    						
		    						if(insertDay){
		    							signUpService.saveSignupDays(itemDetail,signupprogram, lstSelectedDays.get(k));
		    						}
		    					}
		    					Payer payer = paymentService.savepayer(paymentId, customer, cartItemMap, itemDetail, signupprogram, paymentMode);
		    					
		    					Date currDate = new Date();
		    					if(payer.getEnddate()==null || "".equals(payer.getEnddate()) || currDate.getTime()<=payer.getEnddate().getTime()){
				    				
					    			List<Invoice> invoices = paymentService.saveCartInvoice(customer, u, signupprogram, payer, invoicesArr, fa);
					    			if(StringUtils.isNotBlank(paymentMode) && !paymentMode.equals(PaymentTypeEnum.None.name())){
					    				paymentService.saveCartPayment(paymentId, jp_request_hash, orderNumber, customer, u, signupprogram, payer, invoices, paymentMode, fa);
					    			}
					    			
					    			Double setupAmount = Double.parseDouble(setupFee.toString());
					    			if(!isSetupFeeInserted && setupAmount.doubleValue()>0){
					    				signUpService.saveSetupFee(setupAmount, u);
					    				isSetupFeeInserted = true;
					    			}
					    			
					    			Double registrationAmount = Double.parseDouble(registrationFee.toString());
					    			if(!isRegistrationFeeInserted && registrationAmount.doubleValue()>0){
					    				signUpService.saveRegistrationFee(registrationAmount, u, selectedStartDate);
					    				isRegistrationFeeInserted = true;
					    			}
					    			
					    			/*if(jetpay!=null)
					    				tansId = jetpay.getTransId();*/
				    			}
		    				}
		    				
		    				if(Boolean.parseBoolean(hasWLDays)){ // save waitlisted signup
		    					Signup WLsignupprogram = paymentService.savesignup(paymentId, customer, cartItemMap, itemDetail, u,portalLastModifiedBy, SignupStatusEnum.Waitlisted.toString());
		    					// save WL signup days
		    					if(lstWLDays.size()>0){
		    						for(int k=0; k<lstWLDays.size();k++){
		    							Integer selDaysId;
		    							try {
		    								selDaysId = Integer.parseInt(lstWLDays.get(k));
		    							}
		    							catch(Exception e){
		    								selDaysId = daysArr.indexOf(lstWLDays.get(k));
		    							}
		    							ItemDetailDays itemDetailDays = itemDetailDaysDao.getByItemDetailAndDay(itemDetail,selDaysId.toString());
		    							
		    			    			capacityManagementService.checkStopConfirmedSignups(itemDetail, itemDetailDays);
		    							
		    							signUpService.saveSignupDays(itemDetail, WLsignupprogram, lstWLDays.get(k));
		    						}
		    					}
		    					Payer payer = paymentService.savepayer(paymentId, customer, cartItemMap, itemDetail, WLsignupprogram, paymentMode);
		    				}
		    			}
		    			if(Constants.FACILITY_TYPE.equalsIgnoreCase(programType) || Constants.CAMP_TYPE.equalsIgnoreCase(programType) || Constants.PRODUCT_CATEGORY_PROGRAM.equalsIgnoreCase(programType) || Constants.PRODUCT_CATEGORY_EVENT.equalsIgnoreCase(programType)){
		    				mixedCart = true;
	    				}
		    		}
	    		}
	    		
    			// associated parent sign up;
	    		for(Signup s : signups){
	    			signUpService.assignParentSignUp(s.getParentUnqiueId(), s);
	    		}	
 
	    		if(!"".equals(orderNumber) && StringUtils.isNotBlank(paymentMode) && !paymentMode.equalsIgnoreCase(PaymentTypeEnum.None.name()) && !paymentMode.equalsIgnoreCase(PaymentTypeEnum.Cash.name()) && !paymentMode.equalsIgnoreCase(PaymentTypeEnum.Check.name())){
	    			JetPayPayment jetPayPayment = jetPayPaymentDao.getByOrderNumber(orderNumber);
	    			if(jetPayPayment.getActCode() != null && Constants.PAYMENT_ACTION_CODE_SUCCESS.equals(jetPayPayment.getActCode())){
	    				tansId = jetPayPayment.getOrderNumber();
	    			}else{
	    				log.error("  failed orderNumber :   "+orderNumber);
	    				return "FAIL";
	    			}
	    		}
	    	}else{
	    		log.error("  failed  ");
	    		return "FAIL";
	    	}
    	}
    	catch(Exception e){
    		log.error("  failed exception  :   ");
    		log.error("Error  ",e);
    		e.printStackTrace();
    		////System.out.println(e);
    		return "FAIL";
    	}
		
		return "SUCCESS__"+tansId+"__"+mixedCart;
	}

	@RequestMapping(value="/pastDuePaymentProcess", method=RequestMethod.POST)
    public @ResponseBody String  pastDuePaymentProcess(Account account, final HttpServletRequest request, final HttpServletResponse response) { 	
		//System.out.println("pastDuePaymentProcess --> ");
		String tansId = "";
		String paymentId = request.getParameter("paymentId");
		String jp_request_hash = request.getParameter("jp_request_hash");
		String orderNumber = request.getParameter("orderNumber");
		String paymentMode = request.getParameter("paymentMode");
		String tag=request.getParameter("tag");
		String bankAccountNumber = request.getParameter("bankAccountNumber");
		String bankRoutingNumber = request.getParameter("bankRoutingNumber");
		String checkNumber = request.getParameter("checkNumber");
		String accountName = request.getParameter("accountName");
		String invoiceArr = request.getParameter("invoiceArr");
		System.out.println(invoiceArr);
		
		JetPayPayment jetpay=null;
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		User contact=null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return "FAIL";
		}
		Account customer = null;
		try{
    		
        	if(userId != null && !"".equals(userId)){
        		customer = accountDao.getByEmail(userId);	
        		customer.setCheckNumber(checkNumber);
        		customer.setPaymentDesc(bankAccountNumber+","+bankRoutingNumber+","+checkNumber+","+accountName);
        	}
    		
        	if(customer != null){	    		    	    	
    	      //  model.addAttribute("accInfo", account);
    	        //model.addAttribute("usInfo", user);	
    	        
    	      //  int userCount = customer.getUser().size();
    	        //List<User> userS = new ArrayList<User>();
    	        //int countmembers = 0;
    	      //  if(userCount>1){
    		        for(User u: customer.getUser()){
    		        	if(u.isPrimary()){
    		        		contact=u;
    		        		break;
    		        	}
    		        }
    			}
		}catch(Exception e){
			return "FAIL";
		}
		
		if(account != null && StringUtils.isNotBlank(tag) && tag.equals("SELECTEDCURRENTDUE")){		
			//Selected Current Due Invoice(s)			
			List<Invoice> invoiceLst = account.getInvoice();
			/*for(Invoice inv : invoiceLst){
				Invoice  invoice = invoiceDao.getOne(inv.getInvoiceId());						
				double openBalance = getOpenBalanceForInvoice(invoice);					
				if (openBalance > 0) {
					invoice.setBalance(openBalance);
					selectedCurrentDueInvoiceList.add(invoice);
				}	
			}*/
			if(invoiceLst != null && !invoiceLst.isEmpty()){
				jetpay = paymentService.savePastDuePayment(paymentId.toString(), jp_request_hash, orderNumber, customer,contact, paymentMode,invoiceLst);
		        if(jetpay!=null)
		    	tansId = jetpay.getTransId();
		        return "SUCCESS__"+tansId;
			}else{
				return "FAIL";
			}	    	        	
		}else {
	//	//System.out.println("pastDuePaymentProcess --> "+paymentId+ "orderNumber "+orderNumber);
		//String lstCartItem = request.getParameter("cartItems");
		//List<String> lstStrCartItem = Arrays.asList(lstCartItem.split(","));
		
		
    	try{
	    	    	
        	
    
			 ////////////////////////////////////////////////////
			 
			 
			 
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
								
				 List<Invoice> requiredInvoiceList = new ArrayList<Invoice>();
				
				
				invoiceList = invoiceDao.getInvoiceByCustomer(
						customer.getAccountId(), startDate, formattedEndDate);
				if(invoiceList != null && invoiceList.size() > 0){
				for (Invoice invoice : invoiceList) {
					
					double openBalance = getOpenBalanceForInvoice(invoice);
					//System.out.println(" openBalance ::  "+openBalance);
					if(openBalance > 0){
						
					
						invoice.setBalance(openBalance);
						requiredInvoiceList.add(invoice);
					
					}
				
				}
				}
			
				
				 List<Invoice> requiredPastInvoiceList = new ArrayList<Invoice>();
				pastInvoiceList = invoiceDao.getInvoicePastDueByCustomer(
						customer.getAccountId(), startDate);
				if(pastInvoiceList != null && pastInvoiceList.size() > 0){
				for (Invoice invoice : pastInvoiceList) {
					double openBalance = getOpenBalanceForInvoice(invoice);
					//System.out.println(" openBalance ::  "+openBalance);
					if(openBalance > 0){
						
						//pastDueAmount += openBalance;
						invoice.setBalance(openBalance);
						requiredPastInvoiceList.add(invoice);
						//Signup s=invoice.getSignup();
						//Account a1=s.getCustomer();
					}
					
				}
			 
			 
				}
			 
        	//System.out.println("pastDuePaymentProcess --.....> "+paymentId+ "orderNumber "+orderNumber);
        	
        	//////
        	
        	
        	/*String paymentMode1 = "Cash";
			if(StringUtils.isNotBlank(paymentMode1)){
				JetPayPayment jetpay1 = paymentService.savepayment("0", "0", "0", account, savedUser, dbSignup, payer, invoice, paymentMode, invoice.getAmount());				
			} */ 
        	
        	
        	/////
        	
        	
        	if(tag.equals("CURRENTPASTDUE"))
        	jetpay = paymentService.savePastDuePayment(paymentId.toString(), jp_request_hash, orderNumber, customer,contact, paymentMode,requiredPastInvoiceList,requiredInvoiceList);
        	else	
        	 jetpay = paymentService.savePastDuePayment(paymentId.toString(), jp_request_hash, orderNumber, customer,contact, paymentMode,requiredPastInvoiceList);
	    			if(jetpay!=null)
	    				tansId = jetpay.getTransId();
	    		//}
	    	//}
    	}
    	catch(Exception e){
    		
    		return "FAIL";
    	}
		
		return "SUCCESS__"+tansId;
		}		
	}
	
	@RequestMapping(value="/getProductsbyCategory", method=RequestMethod.GET)
    public @ResponseBody List<ItemDetail>  getProductsbyCategory(final HttpServletRequest request, final HttpServletResponse response) { 		
    	try{
    		String strCategory = request.getParameter("category");
    		List<ItemDetail> productLst =  itemDetailsDao.findDistinctItemDetailByType(strCategory);
    		return productLst; 
    		
    	}catch(Exception e){    		
    		      		
    	}
		return null;    	
    }
	
	@RequestMapping(value="/changeProgram", method=RequestMethod.GET)
    public ModelAndView changeProgramWizard(final HttpServletRequest request, final HttpServletResponse response) { 
		String signupId = request.getParameter("sId");
		Model model = new ExtendedModelMap();
		
		if(!"".equals(signupId)){
			try {
				/*
				Object programObj[] = (Object[]) signupDao.getSignupProgramById(Long.parseLong(signupId));
				Signup signup = (Signup) programObj[0];
		    	ItemDetail program = (ItemDetail) programObj[1];
		    	
		    	DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		    	Date nowdate = new Date();
		    	String cancelDt = sdf.format(nowdate);
		    	
		    	
		    	boolean isFutureCancelDate = capacityManagementService.isFutureCancelDate(cancelDt);
		    	signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
		    	signUpService.updateCancelInfo(cancelDt, isFutureCancelDate, signup);
		    	
				 capacityManagementService.updateCapacityCancelledSignupDays(signup, program,"");
				 
				response.sendRedirect(request.getContextPath()+"/Childcare");
				*/
				
				
				Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
				String userId = null;
				try{
					userId =authenticate.getName();
				}catch(Exception e){
					log.error("Error  ",e);
				}
				
				model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
				
				List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
				if(terms != null && !terms.isEmpty()){
		        	model.addAttribute("terms", terms.get(0).getTcDescription());
		        }
				
				Account account = null;
		    	User user =  null;	
		    	List<PaymentMethod> paymentMethodList = null;
		    	if(userId != null && !"".equals(userId)){
			    	account = accountDao.getByEmail(userId);
					if(account != null){
						user = getUserByAccount(account, user);
						paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
						model.addAttribute("paymentInfoLst" , paymentMethodList);
						model.addAttribute("accInfo" , account);
					}
		    	}
		    	model.addAttribute("signupId", signupId);
		        
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ModelAndView("viewChildcare", model.asMap());
	}
	
	@RequestMapping(value="/confirmProgram", method=RequestMethod.GET)
    public @ResponseBody String confirmProgramWizard(final HttpServletRequest request, final HttpServletResponse response) { 
		String signupId = request.getParameter("sId");
		Model model = new ExtendedModelMap();
		JSONObject json = new JSONObject();
		if(!"".equals(signupId)){
			try {
				/*
				Object programObj[] = (Object[]) signupDao.getSignupProgramById(Long.parseLong(signupId));
				Signup signup = (Signup) programObj[0];
		    	ItemDetail program = (ItemDetail) programObj[1];
		    	
		    	DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		    	Date nowdate = new Date();
		    	String cancelDt = sdf.format(nowdate);
		    	
		    	
		    	boolean isFutureCancelDate = capacityManagementService.isFutureCancelDate(cancelDt);
		    	signup.setPortalLastModifiedBy(getPortalLastModifiedBy());
		    	signUpService.updateCancelInfo(cancelDt, isFutureCancelDate, signup);
		    	
				 capacityManagementService.updateCapacityCancelledSignupDays(signup, program,"");
				 
				response.sendRedirect(request.getContextPath()+"/Childcare");
				*/
				
				Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
				String userId = null;
				try{
					userId =authenticate.getName();
				}catch(Exception e){
					log.error("Error  ",e);
				}
				Signup signup = signupDao.getOne(Long.parseLong(signupId));
				ItemDetail itemDetail = itemDetailDao.getById(signup.getItemDetailId());
				capacityManagementService.signupConfirmCapacityManagement(itemDetail, signup, true, true);
				
				json.put("RESULT", "YES");
				
				/*model.addAttribute("locations", locationDao.findByStatusOrderByRecordNameAsc("Active"));
				
				List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
				if(terms != null && !terms.isEmpty()){
		        	model.addAttribute("terms", terms.get(0).getTcDescription());
		        }
				
				Account account = null;
		    	User user =  null;	
		    	List<PaymentMethod> paymentMethodList = null;
		    	if(userId != null && !"".equals(userId)){
			    	account = accountDao.getByEmail(userId);
					if(account != null){
						user = getUserByAccount(account, user);
						paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(account.getAccountId());
						model.addAttribute("paymentInfoLst" , paymentMethodList);
						model.addAttribute("accInfo" , account);
					}
		    	}*/
		    	model.addAttribute("signupId", signupId);
		        
			} catch (Exception e) {
				log.error("Error : "+e.getMessage());
				json.put("RESULT", "NO");
			}
		}
		return json.toString();
	}
	
	/* NOT IN USE
	private PaymentMethod populatePaymentMethodData(Account account, String orderNumber) {
	    	JetPayPayment jetPayPayment = null;
			if(!"".equals(orderNumber)){
				//jetPayPayment = jetPayPaymentDao.getByJpReturnHash(jp_request_hash);
				//jetPayPayment = jetPayPaymentDao.getByOrderNumber("254657884038");
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
	    	paymentMethod.setCustomer(account);
	    	
			return paymentMethod;
		}
	 */
	
	@RequestMapping(value="/saveContactHistory", method=RequestMethod.POST)
	private @ResponseBody String saveContactHistory(final HttpServletRequest request, final HttpServletResponse response) {
		String resp = null;
		try{
			String[] contacts = request.getParameterValues("contactId");
			if(contacts != null && contacts.length > 0){
				HealthHistory healthHistory = null;
				Long contactId = 0l, healthHistoryId = 0l;
				for (String strContactId : contacts) {
					contactId = Long.parseLong(strContactId);
					healthHistoryId = Long.parseLong(request.getParameter("healthHistoryId_"+contactId));
					if(healthHistoryId > 0){
						healthHistory = healthHistoryDao.getOne(healthHistoryId);
					}else{
						healthHistory = new HealthHistory();
					}
					healthHistory.setInsuranceCompany_c(request.getParameter("InsuranceCompany_"+contactId));
					healthHistory.setInstructions_c(request.getParameter("Instructions_"+contactId));
					healthHistory.setListCurrentMedications_c(request.getParameter("listCurrentMedications_"+contactId));
					healthHistory.setCurrentMedicationPurpose_c(request.getParameter("currentMedicationPurpose_"+contactId));
					healthHistory.setLastUpdated(Calendar.getInstance());
					healthHistoryDao.save(healthHistory);
					if(healthHistoryId == 0){
						User contact = userDao.findOne(contactId);
						contact.setHealthHistory(healthHistory);
						contact.setLastUpdated(Calendar.getInstance());
						userDao.save(contact);
					}
				}
			}
			resp = "SUCCESS";
		}catch(Exception e){
			log.error("Error  ",e);
			resp = "FAIL";
		}
		return resp;
	}
	
	
	
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