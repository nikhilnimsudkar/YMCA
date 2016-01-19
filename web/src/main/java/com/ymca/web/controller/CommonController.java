package com.ymca.web.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ContactPromotionDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.ItemDetailDaysDao;
import com.ymca.dao.ItemDetailDaysPricingRuleDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.PromotionDao;
import com.ymca.dao.SetpUpFeeDao;
import com.ymca.dao.SignUpDaysDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.AccountFinancialAid;
import com.ymca.model.HealthHistory;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.ItemDetailDaysPricingRule;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.ItemDetailPromotion;
import com.ymca.model.Promotion;
import com.ymca.model.SetpUpFee;
import com.ymca.model.SignUpDays;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.CapacityManagementService;
import com.ymca.web.service.FeeCalculationService;
import com.ymca.web.service.FinancialAssistanceService;
import com.ymca.web.service.PaymentService;
import com.ymca.web.service.PromotionServiceImpl;
import com.ymca.web.util.Constants;
import com.ymca.web.util.MemberAge;

@Controller
public class CommonController extends BaseController {
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private PromotionDao promocodeDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private SignUpDaysDao signupDaysDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private SetpUpFeeDao setpUpFeeDao;
	
	@Resource
	private PaymentService paymentService;
	
	@Resource
	private JetPayPaymentDao jetPayPaymentDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	@Resource
	private ItemDetailDaysPricingRuleDao itemDetailDaysPricingRuleDao;
	
	@Resource
	private FinancialAssistanceService financialAssistanceService;
	
	@Resource
	private ItemDetailDaysDao itemDetailDaysDao;
	
	@Resource
	protected ContactPromotionDao contactPromotionDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	private CapacityManagementService capacityManagementService ;
	
	@Resource
	private PromotionServiceImpl promotionService;
	
	//@Resource(name="portalAuthenticationProvider")
	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private FeeCalculationService feeCalculationService;
	
	List<String> daysArr = Arrays.asList("Sun","Mon","Tues","Wed","Thurs","Fri","Sat");
	/**
	 * Dispatch to login pop-up
	 */
	@RequestMapping(value="/loginPopup", method=RequestMethod.GET)
    public String loginPopup(final HttpServletRequest request, final HttpServletResponse response) { 
		return "loginpop";
	}
	
	/**
	 * Quick sign up
	 */
	@RequestMapping(value="/quickSignIn", method=RequestMethod.POST)
    public String quickSignIn(final HttpServletRequest request, final HttpServletResponse response, Model model) {
		String view = "redirect:/login";
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String username = request.getParameter(Constants.USERNAME_LBL);
		String password = request.getParameter(Constants.PASSWORD_LBL);
		String dispatchTo = request.getParameter(Constants.PARAM_DISPATCH_TO);
		
		String urlPromoItemDetailId = request.getParameter("urlPromoItemDetailId");
		String urlPromoContactId = request.getParameter("urlPromoContactId");
		String urlPromoCode = request.getParameter("urlPromoCode");
		
		User user = null;
		if(username!=null && !"".equals(username.trim()) && password!=null && !"".equals(password)){
			Account account = accountDao.getByEmail(username);			
			user = getUserByAccount(account, user);
			if(user!=null && user.getPassword() !=null && bCryptPasswordEncoder.matches(password, user.getPassword())){
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());
				
				// generate session if one doesn't exist
		        request.getSession();
		        token.setDetails(new WebAuthenticationDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(token);
		        request.getSession().setAttribute("usInfo", user);	
		        request.getSession().setAttribute("accInfo", account);
		        
		        if(dispatchTo != null && !"".equals(dispatchTo)){
		        	if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.PROGRAM.toString())){
		        		view =  "redirect:/addprogramtocart";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.EVENT.toString())){
		        		view =  "redirect:/addEvent";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CHILDCARE.toString())){
		        		view =  "redirect:/addChildcareToCart";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.FACILITY.toString())){
		        		view =  "redirect:/requestBooking";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.DONATION.toString())){
		        		view =  "redirect:/donationHome";
		        	}
		        }else if(urlPromoItemDetailId != null && !"".equals(urlPromoItemDetailId)){
		        	
		    		/*Authentication a = SecurityContextHolder.getContext().getAuthentication();
		    		String userId = null;
		    		try{
		    			userId = ((UserDetails) a.getPrincipal()).getUsername();
		    		}catch(Exception e){
		    			log.error(" failed userId :   "+userId);
		    		}*/
		    		
		    		HashMap<String, Object> modelView = promotionService.getViewForPromoInURL(user.getEmailAddress(), urlPromoItemDetailId, null, urlPromoContactId, urlPromoCode, model);
		    		
		    		model = (Model)modelView.get("model");
		    		view = (String)modelView.get("view");
		        }
			}
		}
		return view;
	}
	
	@RequestMapping(value="/quickSignIn", method=RequestMethod.GET)
    public @ResponseBody String quickSignInGet(@RequestParam String username, @RequestParam String password, @RequestParam String urlPromoItemDetailId, 
    		@RequestParam String urlPromoContactId,@RequestParam String urlPromoCode) {
		String view = "redirect:/login";
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		//String username = request.getParameter(Constants.USERNAME_LBL);
		//String password = request.getParameter(Constants.PASSWORD_LBL);
		//String dispatchTo = request.getParameter(Constants.PARAM_DISPATCH_TO);
		
		//String urlPromoItemDetailId = request.getParameter("urlPromoItemDetailId");
		//String urlPromoContactId = request.getParameter("urlPromoContactId");
		//String urlPromoCode = request.getParameter("urlPromoCode");
		
		User user = null;
		if(username!=null && !"".equals(username.trim()) && password!=null && !"".equals(password)){
			Account account = accountDao.getByEmail(username);			
			user = getUserByAccount(account, user);
			if(user!=null && user.getPassword() !=null && bCryptPasswordEncoder.matches(password, user.getPassword())){
				UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, user.getPassword());
				
				// generate session if one doesn't exist
		        request.getSession();
		        token.setDetails(new WebAuthenticationDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(token);
		        request.getSession().setAttribute("usInfo", user);	
		        request.getSession().setAttribute("accInfo", account);
		        
		        //userDetailsService.loadUserByUsername(username);
		        
		        /*if(dispatchTo != null && !"".equals(dispatchTo)){
		        	if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.PROGRAM.toString())){
		        		view =  "redirect:/addprogramtocart";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.EVENT.toString())){
		        		view =  "redirect:/addEvent";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CHILDCARE.toString())){
		        		view =  "redirect:/addChildcareToCart";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.FACILITY.toString())){
		        		view =  "redirect:/requestBooking";
		        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.DONATION.toString())){
		        		view =  "redirect:/donationHome";
		        	}
		        }else*/ 
		        
		        if(urlPromoItemDetailId != null && !"".equals(urlPromoItemDetailId)){
		        	
		    		/*Authentication a = SecurityContextHolder.getContext().getAuthentication();
		    		String userId = null;
		    		try{
		    			userId = ((UserDetails) a.getPrincipal()).getUsername();
		    		}catch(Exception e){
		    			log.error(" failed userId :   "+userId);
		    		}*/
		    		
		    		//HashMap<String, Object> modelView = promotionService.getViewForPromoInURL(user.getEmailAddress(), urlPromoItemDetailId, null, urlPromoContactId, urlPromoCode, model);
		    		
		    		//model = (Model)modelView.get("model");
		    		//view = (String)modelView.get("view");
		        	view = "changeMembershipShowWizard";
		        }
			}
		}
		return view;
	}
	
	/**
	 * Get contacts as JSON
	 */
	@RequestMapping(value="/getContacts", method=RequestMethod.GET)
    public @ResponseBody String getContactS(final HttpServletRequest request, final HttpServletResponse response) { 
	 	JSONArray json = new JSONArray();
	
	 	Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
		
		// TODO Need better way of handling authentication for ajax request
/*
 		Account account = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
    	}
    	
    	if(account==null){
    		return json.toString();
    	}
*/
		MemberAge mAge = new MemberAge();
    	List<User> users = userDao.findByEndDateAndCustomerEmail(new Date(),userId);
    	
    	for(User u: users){
	    	JSONObject obj = new JSONObject();
	    	obj.put("id", u.getContactId());
			obj.put("contactId", u.getContactId());
			obj.put("fname", u.getPersonFirstName());
			obj.put("lname", u.getPersonLastName());
			obj.put("isMember", isMember(u.getContactId().toString()));
			obj.put("age", mAge.getAge(u.getDateOfBirth()));
			obj.put("gender", u.getGender());
			if (StringUtils.equals(u.getUsername(),userId)) {
				obj.put("isPrimary",true);	
			} else {
				obj.put("isPrimary", false);
			}
			json.add(obj);
    	}
    	
    	return json.toString();
	}
	
	@RequestMapping(value="/getPromodetails", method=RequestMethod.GET)
    public @ResponseBody String getPromodetails(final HttpServletRequest request, final HttpServletResponse response) { 
		JSONArray json = new JSONArray();
		
		String promocode = request.getParameter("promocode");
		
		if(!"".equals(promocode)){
			Promotion promodetails = promocodeDao.getPromoDiscountByPromocode(promocode);
			if(promodetails!=null){
				  JSONObject obj = new JSONObject();
				
				  Double discount = new Double(0);
				  if(promodetails.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_VALUE)){
					  discount = promodetails.getDiscountvalue();
				  }
				  
				  obj.put("valid", "true");
				  obj.put("promid", promodetails.getId());
				  obj.put("promname", promodetails.getPromoName());
				  obj.put("discounttype", promodetails.getDiscounttype());
				  obj.put("discountValue", discount);
				  obj.put("discountPercentage", promodetails.getDiscountpercentage());
				  obj.put("promcode", promocode);
				  
				  JSONArray json1 = new JSONArray();
				  for(ItemDetailPromotion ipd: promodetails.getItemPromoDetails()){
					  JSONObject obj1 = new JSONObject();
					 // obj1.put("itemPromoDetailsId", ipd.getPromotionId());
					  obj1.put("itemDetailsId", ipd.getItemDetail().getId());
					  json1.add(obj1);
	    		  }
				  
				  obj.put("itemDetails", json1.toString());
				  json.add(obj);
			}
		}
		
		return json.toString();
	}
	
	
	@RequestMapping(value="/program/checkProgramSignUp", method=RequestMethod.GET)
    public @ResponseBody String checkProgramSignUp(Long contactId,Long itemDetailId, String type, String category, String selectedDaysStr, String daysPreventDuplicateSignupStr, String selectedDaysOrDateStr) { 
		JSONObject obj = new JSONObject();
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		
		String status = null, duplicateDays = new String("");
		if(type != null && !type.equals("")){
			if(type.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CHILD_CARE) && (type.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CHILD_CARE) && !category.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_AFTER_SCHOOL))){
				if(selectedDaysStr != null && !selectedDaysStr.equals("") && daysPreventDuplicateSignupStr != null && !daysPreventDuplicateSignupStr.equals("") && selectedDaysOrDateStr!=null && !"".equals(selectedDaysOrDateStr)){
					selectedDaysStr = selectedDaysStr.replaceAll(";", ",");
					selectedDaysOrDateStr = selectedDaysOrDateStr.replaceAll(";", ",");
					String[] selectedDays = selectedDaysOrDateStr.split(",");
					String[] daysPreventDuplicateSignup = daysPreventDuplicateSignupStr.split(",");
					List<SignUpDays> signUpDays = signupDaysDao.checkActiveSignupDays(itemDetailId, contactId);
					
					if(selectedDays.length > 0 && daysPreventDuplicateSignup.length > 0){
						
						for (int i = 0; i < selectedDays.length; i++) {
								String selectedDayStr = selectedDays[i];
								if(selectedDaysOrDateStr != null){
									//int selectedDayInt = Integer.parseInt(selectedDayStr);
									//String tempDayStr = daysArr.get(selectedDayInt);
									String tempDayStr = selectedDayStr;
									boolean preventDuplicate = false;
									for (int j = 0; j < daysPreventDuplicateSignup.length; j++) {
										String temp = daysPreventDuplicateSignup[j];
										String tempArr[] = temp.split(":");
										if(tempArr != null && tempArr.length == 2){
											if(tempArr[0].equals(tempDayStr)){
												if(tempArr[1] != null && tempArr[1].equals("Yes")){
													preventDuplicate = true;  
													break;
												}
											}
										}
									}
									if(preventDuplicate){
										for (SignUpDays signUpDaysTemp : signUpDays) {
											String signupDaysorDate = signUpDaysTemp.getDay();
											if(type.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CHILD_CARE) && category.equalsIgnoreCase(Constants.PRODUCT_CATEGORY_INSERVICE)){
												signupDaysorDate = sdf.format(signUpDaysTemp.getSignupDate());
											}
											if(signupDaysorDate.equals(tempDayStr)){
												status = "true";
												duplicateDays = duplicateDays + "," + tempDayStr;
												break;
											}
										}
									}
								}
						}
					}
				}
				if(StringUtils.isNotBlank(duplicateDays)){
					obj.put("duplicateDays", duplicateDays.replaceFirst(",", ""));
				}
			}else{
				status = signupDao.checkActiveSignUp(contactId, itemDetailId);	
			}
		}
		
		if (StringUtils.isNotBlank(status)) {
			obj.put("resp", true);	
		} else {
			obj.put("resp", false);
		}
		return obj.toString();
	}
	
	
	@RequestMapping(value="/getSignupByType", method=RequestMethod.GET)
    public @ResponseBody String getSignupByType(final HttpServletRequest request, final HttpServletResponse response) { 
		 	
		String type = request.getParameter("type");
		JSONArray json = new JSONArray();
		
	 	Authentication authenticate = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) authenticate.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
    	
		Account account = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
    	}
    	
    	if(account==null){
    		return json.toString();
    	}
    	
    	//List<Signup> signups = signupDao.getByCustomerAndType(account, type);
    	List<Signup> signups = signupDao.getByCustomerAndTypeAndStatusNot(account, type, SignupStatusEnum.Inactive.toString());
    	
    	for(Signup s: signups){
    		
		    	JSONObject obj = new JSONObject();
		    	obj.put("id", s.getSignupId());
				obj.put("signupId", s.getSignupId());
				obj.put("customerId", s.getCustomer().getAccountId());
				//obj.put("itemDaysMapId", s.getItemDaysMapId());
				obj.put("prodId", s.getItemDetail().getId());
				obj.put("itemDetailsId", s.getItemDetail().getId());
				obj.put("contactId", s.getContact().getContactId());
				obj.put("locationId", s.getLocation().getId());
				json.add(obj);
    		
    	}
    	
    	System.out.print(json);
    	return json.toString();
	}
	
	@RequestMapping(value="/getallProducts", method=RequestMethod.GET)
    public @ResponseBody String getallProducts(String ids) { 	
		  DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		
		  String[] itemIds = ids.split(",");
		  List<Long> items = new ArrayList<Long>();
		  for (String i : itemIds) {
			  items.add(Long.parseLong(i));
		  }
		  List<ItemDetail> productLst =  itemDetailsDao.findByIdIn(items);
		  
		  List<Promotion> promo = new ArrayList<Promotion>();
		  promo = promocodeDao.getACPromoDiscountByItemDetailsId();
		  
		  JSONArray json = new JSONArray();
		  for(ItemDetail p: productLst){
		      if(p!=null){
		    	  	  List<ItemDetailDays> itemDays = p.getItemDays();

					  //double membertierPrice = 0D;
					//  double nonmembertierPrice = 0D;
		    	  	  List<ItemDetailPricingRule> pricingRuleLst = new ArrayList<ItemDetailPricingRule>();
		    	  	  if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(p.getType())){
		    	  		  pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_ProductCategory(p.getId(), Constants.Status_Active, Constants.Status_Active,Constants.PRODUCT_CATEGORY_CHILD_CARE);
		    	  	  }else{
		    	  		  pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(p.getId(), Constants.Status_Active,Constants.Status_Active);
		    	  	  }
					  JSONArray signuppriceArr = new JSONArray();
					  JSONArray registrationpriceArr = new JSONArray();
					  JSONArray depositpriceArr = new JSONArray();
					  JSONArray seetupfeepriceArr = new JSONArray();
					  
					  for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
						  /*
						  membertierPrice += Double.parseDouble(pricingRule.getPricingRule().getTierPrice());
						  if(pricingRule.getPricingRule()!=null && StringUtils.isNotBlank(pricingRule.getPricingRule().getNonmemberTierPrice()))
								  nonmembertierPrice += Double.parseDouble(pricingRule.getPricingRule().getNonmemberTierPrice());
								  */
						  double membertierPrice = 0D;
						  double nonmembertierPrice = 0D;
						  Double memberdiscount = new Double(0);
						  Double nonmemberdiscount = new Double(0);
						  Double joinFee = new Double(0);
						  
						  String lblPromoCode = "";
						  
						  if(pricingRule!=null && pricingRule.getPricingRule()!=null){
								JSONObject priceObj = new JSONObject();
								
								priceObj.put("id", pricingRule.getId());
								priceObj.put("type",pricingRule.getPricingRule().getType());
								priceObj.put("priceOption",pricingRule.getPricingRule().getPriceOption());
								priceObj.put("packageSize",pricingRule.getPricingRule().getPackageSize());
								
								if(pricingRule.getPricingRule().getTierPrice() != null)
									membertierPrice = pricingRule.getPricingRule().getTierPrice();
									
								if(pricingRule.getPricingRule().getNonmemberTierPrice() != null)
									nonmembertierPrice = pricingRule.getPricingRule().getNonmemberTierPrice(); 
								
								if(pricingRule.getPricingRule().getJoiningFee() != null)
									joinFee = pricingRule.getPricingRule().getJoiningFee();
								
								priceObj.put("memberPrice",membertierPrice);
								priceObj.put("nonmemberPrice",nonmembertierPrice);
								priceObj.put("joinFee",joinFee);
								
								if(promo.size()>0){
									for(Promotion pc: promo){
							    		  for(ItemDetailPromotion ipd: pc.getItemPromoDetails()){
							    			  if(ipd.getItemDetail().getId().equals(p.getId())){
							    				  lblPromoCode = pc.getPromoCode();
							    				  if(pc.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_VALUE)){
							    					  memberdiscount = pc.getDiscountvalue();
							    					  nonmemberdiscount = pc.getDiscountvalue();
							    				  }
							    				  else if(pc.getDiscounttype().equalsIgnoreCase(Constants.LABEL_ITEM_DISCOUNT_PERCENTAGE)){
							    					  BigDecimal memberdiscountAmount = pc.getDiscountpercentage().multiply(BigDecimal.valueOf(membertierPrice));
							    					  memberdiscountAmount = memberdiscountAmount.divide(new BigDecimal(100));
							    					  memberdiscount = memberdiscountAmount.doubleValue();
							    					  
							    					  BigDecimal nonmemberdiscountAmount = pc.getDiscountpercentage().multiply(BigDecimal.valueOf(nonmembertierPrice));
							    					  nonmemberdiscountAmount = nonmemberdiscountAmount.divide(new BigDecimal(100));
							    					  nonmemberdiscount = nonmemberdiscountAmount.doubleValue();
							    				  }
							    			  }
							    		  }
							    		  
							    	  }
								}
								
								priceObj.put("billingFrequency",pricingRule.getPricingRule().getBillingFrequency());
								priceObj.put("memberdiscount",memberdiscount);
								priceObj.put("nonmemberdiscount",nonmemberdiscount);
								priceObj.put("discountcode",lblPromoCode);
								priceObj.put("includedInMinimumPayment",pricingRule.getIncludedInMinimumPayment());
								priceObj.put("additiveOrSubtractive",pricingRule.getAdditiveOrSubtractive());
								priceObj.put("fromAge",pricingRule.getPricingRule().getFromAge());
								priceObj.put("toAge",pricingRule.getPricingRule().getToAge());
								
								if(Constants.SIGNUP.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
									signuppriceArr.add(priceObj);
								}
								else if(Constants.REGISTRATION.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
									registrationpriceArr.add(priceObj);
								}
								else if(Constants.DEPOSIT.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
									depositpriceArr.add(priceObj);
								}
								else if(Constants.SETUPFEE.equalsIgnoreCase(pricingRule.getPricingRule().getType())){
									seetupfeepriceArr.add(priceObj);
								}
							}
					  }
					  
					  if(signuppriceArr.size()==0){
						  JSONObject priceObj = new JSONObject();
						  priceObj.put("memberPrice",0);
						  priceObj.put("nonmemberPrice",0);
						  signuppriceArr.add(priceObj);
					  }
	    			  
			    	  JSONObject obj = new JSONObject();
			    	  obj.put("optyId", "");
			    	  obj.put("parentSignUpItem", "");
			    	  obj.put("id", p.getId());
			    	  obj.put("prodId", p.getId());
			    	  obj.put("itemDetailsId", p.getId());
			    	  obj.put("programType", p.getType());
			    	  obj.put("subType", p.getCategory());
				      obj.put("name", p.getRecordName());
			    	  //obj.put("memberprice", membertierPrice);
			    	  //obj.put("nonmemberprice", nonmembertierPrice);
				      obj.put("signuppriceArr", signuppriceArr);
				      obj.put("registrationpriceArr", registrationpriceArr);
				      obj.put("depositpriceArr", depositpriceArr);
				      obj.put("setupfeepriceArr", seetupfeepriceArr);
			    	  obj.put("description", p.getDescription());
				      obj.put("category", p.getCategory());
				     // obj.put("yAgentSpecialInstruction", p.getyAgentSpecialInstruction());
				      //obj.put("customerSpecialInstruction", p.getCustomerSpecialInstruction());
				      obj.put("customerSpecialInstruction", p.getSpecialInstruction());
				      
				      obj.put("capacity", convertNullToZero(p.getWebCapacity()));
				      if(StringUtils.isBlank(getAgentUidFromSession()) && p.getStopConfirmedSignUps() != null && p.getStopConfirmedSignUps().equalsIgnoreCase("Yes")){
				    	  obj.put("remainingCapacity", "0");
				      }else{
				    	  obj.put("remainingCapacity", convertNullToZero(p.getRemainingCapacity()));  
				      }
				      obj.put("automaticPaymentInd", p.getAutomaticPayment());
				      obj.put("paymentPlanInd", p.getPaymentPlan());
				      obj.put("allowToPickGrade", p.getAllowToPickGrade());
				      obj.put("allowToPickStartDate", p.getAllowToPickStartDate());
				      obj.put("minAge", p.getMinAge());
				      obj.put("maxAge", p.getMaxAge());
				      if(StringUtils.isNotBlank(p.getRequiresAuthorisedContact()) && p.getRequiresAuthorisedContact().equalsIgnoreCase("Yes")){
				    	  obj.put("requiresAuthorisedContact", true);  
				      }else{
				    	  obj.put("requiresAuthorisedContact", false);
				      }
				      if(StringUtils.isNotBlank(p.getRequiresEmergencyContact()) && p.getRequiresEmergencyContact().equalsIgnoreCase("Yes")){
				    	  obj.put("requiresEmergencyContact", true);
				      }else{
				    	  obj.put("requiresEmergencyContact", false);
				      }
				      obj.put("needTC", p.getNeedTC_c());
				      obj.put("preventDuplicateSignup", p.getPreventDuplicateSignup());
				      obj.put("allowMinPaymentBasedOn", p.getAllowMinimumPaymentBasedOn());
				      obj.put("dueDateOption", p.getDueDate());
				      obj.put("dueDateOffset", p.getDueDateOffset());
				      obj.put("billDateOption", p.getBillDate());
				      obj.put("billDateOffset", p.getBillDateOffset());
				      obj.put("gender", p.getGender());
				      obj.put("memberRegistrationStartDate", p.getMemberRegistrationStartDate().toString());
				      obj.put("memberRegistrationEndDate", p.getMemberRegistrationEndDate().toString());
				      obj.put("nonMemberRegistrationStartDate", p.getNonMemberRegistrationStartDate().toString());
				      obj.put("nonMemberRegistrationEndDate", p.getNonMemberRegistrationEndDate().toString());
				      obj.put("specialinstructions", p.getSpecialInstruction());
				      
				      String sessionName = "";
				      String instructorName = "";
				      String sessionDays = "";
				      String sessionDaysId = "";
				      String daysPreventDuplicateSignup = "";
				      List<String> daysArr = Arrays.asList("Sun","Mon","Tues","Wed","Thurs","Fri","Sat");
				      for(ItemDetailDays idys : itemDays){
				    	  //obj.put("itemDaysMapId", idys.getItemDaysId()+"_"+p.getItemDetails().getId());
					      //obj.put("item_Days", idys.getItemDaysId()+"_"+p.getItemDetails().getId());
				    	  sessionName = idys.getSessionName();
				    	  instructorName = idys.getInstructorName();
				    	  
				    	  if(idys!= null && idys.getDay()!=null){
					    	  Integer dayId = Integer.parseInt(idys.getDay());
					    	  if(dayId>=0 && dayId<=6){
					    		  sessionDaysId = sessionDaysId + "," + dayId;
					    		  sessionDays = sessionDays + "," + daysArr.get(dayId);
					    		  if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(p.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(p.getCategory())){
					    			  // for in service dup check is on date
					    			  daysPreventDuplicateSignup = daysPreventDuplicateSignup + "," + sdf.format(idys.getDate_c()) +":"+ idys.getPreventDuplicateSignup();
					    		  }
					    		  else{
					    			  daysPreventDuplicateSignup = daysPreventDuplicateSignup + "," + daysArr.get(dayId) +":"+ idys.getPreventDuplicateSignup();
					    		  }
					    	  }
				    	  }
				      }
				      obj.put("sessionName", sessionName);
				      obj.put("instructor", instructorName);
				      obj.put("dayId", sessionDaysId.replaceFirst(",", ""));
				      obj.put("days", sessionDays.replaceFirst(",", ""));
				      obj.put("daysPreventDuplicateSignup", daysPreventDuplicateSignup.replaceFirst(",", ""));

				      if (p.getStartTime() == null) obj.put("start_time", "");
				      else obj.put("start_time", p.getStartTime());
				      if (p.getEndTime() == null) obj.put("end_time", "");
				      else obj.put("end_time", p.getEndTime());
				      if (p.getStartDate() == null) obj.put("start_date", "");
				      else {
				    	  obj.put("start_date", p.getStartDate());
				    	  obj.put("start_date_text", sdf.format(p.getStartDate()));
				      }
				      if (p.getStartDate() == null) {
				    	  obj.put("end_date", "");
				    	  obj.put("end_date_str", "");
				      }
				      else {
				    	  obj.put("end_date", p.getEndDate());
				    	  obj.put("end_date_str", p.getEndDate().toString());
				      }

//					  Double amt = p.getItemDetails().getPrice() - discount;
				      obj.put("finalamount", new Double(0));
				      obj.put("WL_Text_For_CC", "");
				      obj.put("WLDays", "");
				      obj.put("hasWLDays", false);
				      json.add(obj);
		      }
		        
		  }
	      
	      //System.out.print(json);
	      return json.toString();
	}
	
	@RequestMapping(value="/getPricingruleByItemDetailsIdAndTier", method=RequestMethod.POST)
    public @ResponseBody String getPricingruleByItemDetailsIdAndTier(@RequestParam String itemDetailsId, @RequestParam String tier, final HttpServletRequest request, final HttpServletResponse response) { 	
		JSONArray json = new JSONArray();
			
		if(itemDetailsId!=null){
			List<ItemDetailPricingRule> lstPricingrule = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_TypeAndPricingRule_ProductCategory(Long.parseLong(itemDetailsId), Constants.Status_Active, Constants.Status_Active,Constants.SIGNUP,Constants.PRODUCT_CATEGORY_CHILD_CARE);
			
			if(lstPricingrule!=null && lstPricingrule.size()>0){
				for(ItemDetailPricingRule pricingrule : lstPricingrule){
					JSONObject obj = new JSONObject();
		  	  		obj.put("hourlyRate", pricingrule.getPricingRule().getHourlyRate());
		  	  		obj.put("fullDayHours", pricingrule.getPricingRule().getFullDayHours());
		  	  		obj.put("fullDayFactor", pricingrule.getPricingRule().getFullDayFactor());
		  	  		obj.put("days5Factor", pricingrule.getPricingRule().getDays5Factor());
		  	  		obj.put("days4Factor", pricingrule.getPricingRule().getDays4Factor());
		  	  		obj.put("days3Factor", pricingrule.getPricingRule().getDays3Factor());
		  	  		obj.put("days2Factor", pricingrule.getPricingRule().getDays2Factor());
		  	  		obj.put("day1Factor", pricingrule.getPricingRule().getDay1Factor());
		  	  		json.add(obj);
				}
			}
		}
  	  
		return json.toString();
	}
	
	@RequestMapping(value="/getLoggedInUserDetails", method=RequestMethod.GET)
    public @ResponseBody String getLoggedInUserDetails(final HttpServletRequest request, final HttpServletResponse response) {
		 JSONArray json = new JSONArray();
		 Authentication a = SecurityContextHolder.getContext().getAuthentication();
		 String userId = ((UserDetails) a.getPrincipal()).getUsername();	
		 User user = null;
		 Account account = null;
		 
		 if(userId != null && !"".equals(userId)){
			 JSONObject obj = new JSONObject();
			 account = accountDao.getByEmail(userId);
			 user = getUserByAccount(account, user);
			 obj.put("loggedInUserName",user.getPersonFirstName());
			 obj.put("loggedInUserContactId",user.getContactId());
	  	  	 json.add(obj);
		 }
		 return json.toString();
	}
	
	@RequestMapping(value="/getSetupFeeByItemDetailIdAndPartyId", method=RequestMethod.GET)
	private @ResponseBody String getSetupFeeByItemDetailIdAndPartyId(@RequestParam String itemDetailId, @RequestParam String partyId, final HttpServletRequest request, final HttpServletResponse response) {
		Double setupFee = feeCalculationService.getSetupFeeByItemDetailIdAndPartyId(itemDetailId, partyId);
		return setupFee.toString();
	}
	
	@RequestMapping(value="/getRegistrationFeeByItemDetailIdAndPartyId", method=RequestMethod.GET)
	private @ResponseBody String getRegistrationFeeByItemDetailIdAndPartyId(@RequestParam String itemDetailId, @RequestParam String partyId, final HttpServletRequest request, final HttpServletResponse response) {
		Double registrationFee = feeCalculationService.getRegistrationFeeByItemDetailIdAndPartyId(itemDetailId, partyId);
		return registrationFee.toString();
	}

	@RequestMapping(value="/getFA", method=RequestMethod.GET)
	private @ResponseBody String getFA(@RequestParam String itemDetailId, final HttpServletRequest request, final HttpServletResponse response) {
		JSONArray json = new JSONArray();
		
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
		
		Account account = null;
		if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
		}
		
		if(account==null){
			return json.toString();
		}
		
		ItemDetail i = itemDetailsDao.getById(Long.parseLong(itemDetailId));
		
		List<AccountFinancialAid> customerFaList = financialAssistanceService.computeFA(i,account.getAccountId());
		
		if(customerFaList!=null && customerFaList.size()>0){
			AccountFinancialAid fa = customerFaList.get(0);
			JSONObject obj = new JSONObject();
	    	obj.put("id", fa.getId());
			obj.put("FApercent", fa.getFaPerCent_c());
			obj.put("FAstartDate", fa.getFaStartDate_c());
			obj.put("FAendDate", fa.getFaEndDate_c());

			json.add(obj);
		}
		/* for testing
		if(json.size()==0){
			JSONObject obj = new JSONObject();
	    	obj.put("id", 5);
			obj.put("FApercent", 10);
			obj.put("FAstartDate", "");
			obj.put("FAendDate", "");
			
			json.add(obj);
		}*/
		
		return json.toString();
	}
	
	@RequestMapping(value = "/isHealthHistoryRequired", method = RequestMethod.GET)
	public @ResponseBody String isHealthHistoryRequired(@RequestParam(value = "itemDetailId") String itemDetailId) {
		JSONObject json = new JSONObject();
		try {
			if(itemDetailId != null){
				String[] itemDetailIds = itemDetailId.split(",");
				for(String item : itemDetailIds){
					ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(item));
					if(itemDetail != null && StringUtils.isNotBlank(itemDetail.getNeedsHealthHistory()) && itemDetail.getNeedsHealthHistory().equalsIgnoreCase("Yes")){
						json.put("RESULT", "YES");
						break;
					}else{
						json.put("RESULT", "NO");
					}
				}
			}
		} catch (Exception se) {
			//System.out.println("Error occoured");
			json.put("RESULT", "NO");
			log.error("Error validating isHealthHistoryRequired",se);
		}
		return json.toString();
	}
	
	@RequestMapping(value="/checkProgramRegistrationDate", method=RequestMethod.GET)
	private @ResponseBody boolean checkProgramRegistrationDate(@RequestParam String itemDetailId, @RequestParam String contactId, final HttpServletRequest request, final HttpServletResponse response) {
		boolean proceed = false;
		
		if(StringUtils.isNotBlank(itemDetailId) && StringUtils.isNotBlank(contactId)){
			try{
				User c = userDao.findOne(Long.parseLong(contactId));
				if(c!=null){
					List<Signup> signup = signupDao.findByContactAndTypeAndStatus(c,Constants.PRODUCT_CATEGORY_MEMBERSHIP,SignupStatusEnum.Active.toString());
					ItemDetail i = itemDetailsDao.getOne(Long.parseLong(itemDetailId));
					Date programRegStartDate;
					Date programRegEndDate;
					
					if(signup!=null && signup.size()>0){ // contact is active member
						programRegStartDate = i.getMemberRegistrationStartDate();
						programRegEndDate = i.getMemberRegistrationEndDate();
					}
					else{ // contact is not active member
						programRegStartDate = i.getNonMemberRegistrationStartDate();
						programRegEndDate = i.getNonMemberRegistrationEndDate();
					}
					
					java.util.Date currDate= new java.util.Date();
					long startdiff = currDate.getTime() - programRegStartDate.getTime();
					startdiff = startdiff / (24 * 60 * 60 * 1000);
					long enddiff = currDate.getTime() - programRegEndDate.getTime();
					enddiff = enddiff / (24 * 60 * 60 * 1000);
					
					//reg start date should be greater than curr date and reg end date should be less than curr date
					// stDt <= current <= endDt
					if(startdiff>=0 && enddiff<=0){
						proceed = true;
					}
				}
			}
			catch(Exception e){
				log.error("Error on checkProgramRegistrationDate: ",e);
			}
		}
		return proceed;
	}
	
	@RequestMapping(value="/checkAndUpdateCapacity", method=RequestMethod.GET)
	private @ResponseBody String checkAndUpdateCapacity(@RequestParam String itemIdsConfirmedAndWaitlistedItems, final HttpServletRequest request, final HttpServletResponse response) {
		boolean proceed = false;
		
		List<String> itemIdsAndremainingCapacity = new ArrayList<String>();
		
		if(itemIdsConfirmedAndWaitlistedItems!=null && StringUtils.isNotBlank(itemIdsConfirmedAndWaitlistedItems)){
			List<String> lstItemIdsConfirmedAndWaitlistedItems = Arrays.asList(itemIdsConfirmedAndWaitlistedItems.split(","));

			if(lstItemIdsConfirmedAndWaitlistedItems.size()>0){
	    		for(String itemIdConfirmedAndWaitlistedItems: lstItemIdsConfirmedAndWaitlistedItems){
	    			
	    			String itemDetailId = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(0);
	    			String confirmedItems = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(1);
	    			String waitlistedItems = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(2);
	    			String childcareItemsLst = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(3);
	    			
	    			ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
	    			
	    			 if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(itemDetail.getType()) && !Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equalsIgnoreCase(itemDetail.getCategory())){
	    				 // day wise capacity management for child care and in service
	    				List<String> childcareItems = Arrays.asList(childcareItemsLst.split("::"));
	    				try {
							if(childcareItems!=null && childcareItems.size()>0){
								for(String ccitem: childcareItems){
									String childcareItemDetailId = Arrays.asList(ccitem.split(" AND ")).get(0);
									//String selDays = Arrays.asList(ccitem.split(" AND ")).get(1);
									String selDaysId = Arrays.asList(ccitem.split(" AND ")).get(2);
									//String hasWLDays = Arrays.asList(ccitem.split(" AND ")).get(3);
									String WLDays = "";
									if(Arrays.asList(ccitem.split(" AND ")).size()==5 && !"".equals(Arrays.asList(ccitem.split(" AND ")).get(4).trim()))
										WLDays = Arrays.asList(ccitem.split(" AND ")).get(4);
									
									updateCapacityDecrementForDays(itemDetail, childcareItemDetailId, selDaysId, WLDays);
									
								}
							}
							
							proceed = true;
						} catch (Exception e) {
							e.printStackTrace();
							proceed = false;
						}
	    			}
	    			 else { // item wise capacity management
			    			Long remainingCapacity = 0L;			    			
			    			if(StringUtils.isBlank(getAgentUidFromSession())){
			    				remainingCapacity = convertNullToZero(itemDetail.getRemainingCapacity());
			    			}else{
			    				remainingCapacity = convertNullToZero(itemDetail.getActualRemainingCapacity());
			    			}
			    			if(itemDetail.getStopConfirmedSignUps() == null || itemDetail.getStopConfirmedSignUps().equalsIgnoreCase("No")){
				    			if(Long.parseLong(waitlistedItems)>0){
				    				if(remainingCapacity - Long.parseLong(confirmedItems)==0)
				    					proceed = true;
				    				else{ // if capacity is available still program is waitlisted, stop and update user
				    					String  itemIdAndremainingCapacity = itemDetailId + "__S__" + remainingCapacity;
				    					itemIdsAndremainingCapacity.add(itemIdAndremainingCapacity);
				    					proceed = false;
				    				}
				    			}
				    			else if(remainingCapacity-Long.parseLong(confirmedItems)>=0)
				    				proceed = true;
				    			else{ // if confirmed items have been waitlisted, stop and update user
				    				proceed = false;
				    				String  itemIdAndremainingCapacity = itemDetailId + "__S__" + remainingCapacity;
			    					itemIdsAndremainingCapacity.add(itemIdAndremainingCapacity);
				    			}
			    			}else{
			    				proceed = true;
			    			}
		    		} 
	    		}
	    		
	    		if(proceed){
	    			for(String itemIdConfirmedAndWaitlistedItems: lstItemIdsConfirmedAndWaitlistedItems){
	    				
	    				String itemDetailId = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(0);
		    			String confirmedItems = Arrays.asList(itemIdConfirmedAndWaitlistedItems.split("_")).get(1);
	    				
		    			ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
		    			if(Constants.PRODUCT_CATEGORY_PROGRAM.equalsIgnoreCase(itemDetail.getType()) || Constants.PRODUCT_CATEGORY_EVENT.equalsIgnoreCase(itemDetail.getType()) || Constants.CAMP_TYPE.equals(itemDetail.getType()) 
		    					|| (Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(itemDetail.getType()) && Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equals(itemDetail.getCategory())))
		    				baseService.updateCapacity(itemDetail,Integer.parseInt(confirmedItems));
		    		}
	    		}
			}
		}
		
		return proceed+"^"+StringUtils.join(itemIdsAndremainingCapacity, ',');
	}

	@RequestMapping(value="/UpdateCapacityAfterPaymentFail", method=RequestMethod.GET)
	private @ResponseBody void UpdateCapacityAfterPaymentFail(@RequestParam String itemDetailsId, @RequestParam String confirmedItems, @RequestParam String waitlistedItems, final HttpServletRequest request, final HttpServletResponse response) {
		if(itemDetailsId!=null && StringUtils.isNotBlank(itemDetailsId) && confirmedItems!=null && waitlistedItems!=null){
			List<String> lstItemDetailsId = Arrays.asList(itemDetailsId.split(","));
			if(lstItemDetailsId.size()>0){
				for(String itemDetailId: lstItemDetailsId){
					ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
					

					if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(itemDetail.getType()) && !Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equalsIgnoreCase(itemDetail.getCategory())){
						String childcareItemsLst = request.getParameter("itemIdsAndSelectedDays");
						List<String> childcareItems = Arrays.asList(childcareItemsLst.split("::"));
	    				try {
							if(childcareItems!=null && childcareItems.size()>0){
								for(String ccitem: childcareItems){
									//String childcareItemDetailId = Arrays.asList(ccitem.split(" AND ")).get(0);
									//String selDays = Arrays.asList(ccitem.split(" AND ")).get(0);
									String selDaysId = Arrays.asList(ccitem.split(" AND ")).get(1);
									//String hasWLDays = Arrays.asList(ccitem.split(" AND ")).get(2);
									String WLDays = Arrays.asList(ccitem.split(" AND ")).get(3);
									
									capacityManagementService.updatecapacityIncrementForDays(itemDetail,itemDetail.getId().toString(), selDaysId,WLDays);
								}
							}
	    				} catch (Exception e) {
							e.printStackTrace();
						}
							
					}
					else {
						Long remainingCapacity = convertNullToZero(itemDetail.getRemainingCapacity());
						Long actualRemainingCapacity = convertNullToZero(itemDetail.getActualRemainingCapacity());
						
						remainingCapacity = remainingCapacity+Long.parseLong(confirmedItems);
						actualRemainingCapacity = actualRemainingCapacity+Long.parseLong(confirmedItems);
						
						itemDetail.setRemainingCapacity(remainingCapacity);
						itemDetail.setActualRemainingCapacity(actualRemainingCapacity);
						baseService.populateAndSaveItemDetail(itemDetail);
					}
				}
			}
		}
	}
	
	@RequestMapping(value="/checkAgeRange", method=RequestMethod.GET)
	private @ResponseBody String checkAgeRange(@RequestParam String itemDetailIds, @RequestParam String contactIds, final HttpServletRequest request, final HttpServletResponse response) {
		Boolean validAge = false;
		List<String> invalidItemDetailContactArr = new ArrayList<String>();
		if(itemDetailIds!=null && contactIds!=null && StringUtils.isNotBlank(itemDetailIds) && StringUtils.isNotBlank(contactIds)){
			List<String> lstContactId = Arrays.asList(contactIds.split(","));
			
			List<String> lstItemDetailsId = Arrays.asList(itemDetailIds.split(","));
				
			if(lstContactId.size()>0 && lstItemDetailsId.size()>0){
				List<Long> lstContactIdasLong = new  ArrayList<Long>();
				for (String id : lstContactId) {
					lstContactIdasLong.add(Long.parseLong(id));
				}
				Integer maxContactAge = userDao.getMaxAgeForContacts(lstContactIdasLong);
				Boolean isFamilyCamp = false ;
				for(String contactId: lstContactId){
					User c = userDao.findOne(Long.parseLong(contactId));
					if(c!=null){
						MemberAge mAge = new MemberAge();
						Integer age = 0;
						if(c.getDateOfBirth()!=null){ // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
							age = mAge.getAge(c.getDateOfBirth()); //pass the DOB of user to the method and it returns int Age only in Years.
							
							for(String itemDetailId: lstItemDetailsId){
								ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
								if(itemDetail!=null){
									Integer minAge = itemDetail.getMinAge();
									Integer maxAge = itemDetail.getMaxAge();
									
									if (StringUtils.equalsIgnoreCase(itemDetail.getCategory(),Constants.CAMP_TYPE_FAMILY)){
										isFamilyCamp = true ;
									}
									
									if(minAge <= age && age <= maxAge){
										if(invalidItemDetailContactArr.size()==0)
											validAge = true;
									}
									else{
										validAge = false;
										String err = "Contact "+c.getFirstName() +" "+ c.getLastName()+" doesn't have valid age to signup for program "+itemDetail.getDescription();
										invalidItemDetailContactArr.add(err);
									}
								} else {
									validAge = false;
									String err = "item Detail Id "+itemDetailId+" does not exist";
									invalidItemDetailContactArr.add(err);
								}
							}
						} else {
							validAge = false;
							String err = "Contact "+c.getFirstName() +" "+ c.getLastName()+" does not have valid Date of Birth";
							invalidItemDetailContactArr.add(err);
						}
					} else {
						validAge = false;
						String err = "Contact Id "+contactId+" does not exist";
						invalidItemDetailContactArr.add(err);
					}
				}
				if (isFamilyCamp && maxContactAge < 18) {
					String err = "Please select one adult member for the family camp";
					invalidItemDetailContactArr.add(err);
				}
			}
		}
		return validAge+"_"+StringUtils.join(invalidItemDetailContactArr,"<br>");
	}
	
	@RequestMapping(value="/checkIfMember", method=RequestMethod.GET)
	private @ResponseBody Boolean checkIfMember(@RequestParam String contactId, final HttpServletRequest request, final HttpServletResponse response) {
		return isMember(contactId);
	}

	private Boolean isMember(String contactId) {
		Boolean isMember = false;
		if(contactId!=null && StringUtils.isNotBlank(contactId)){
			User contact = userDao.findOne(Long.parseLong(contactId));
			if(contact!=null){
				List<Signup> signup = signupDao.findByContactAndTypeAndStatus(contact,Constants.PRODUCT_CATEGORY_MEMBERSHIP,SignupStatusEnum.Active.toString());
				if(signup!=null && signup.size()>0){
					// contact is active member
					isMember = true;
				}
			}
		}
		return isMember;
	}
	
	@RequestMapping(value="/getContactHealthHistory", method=RequestMethod.GET)
    public @ResponseBody String  getContactHealthHistory(
    		@RequestParam String contacts,
    		final HttpServletRequest request, final HttpServletResponse response){
		String jsonStr = null;
		try{
			if(contacts != null && !contacts.equals("")){
				JSONArray resultList = new JSONArray();
			    HealthHistory hh = null;
			    String contactsStr[] = contacts.split(",");
			    if(contactsStr.length > 0){
				    Long[] contactsLong = new Long[contactsStr.length];
				    for(int i=0; i<contactsStr.length; i++) {
				    	contactsLong[i] = Long.parseLong(contactsStr[i]);
				    }
				    JSONObject jsonObj = null;
				    List<User> users = userDao.getUsersForContactIds(contactsLong);
				    
				    if(users != null && !users.isEmpty()){
				    	for (User tempUser : users) {
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
				        		jsonObj.put("insuranceCompany", "");
				        		jsonObj.put("instructions", "");
				        		jsonObj.put("listCurrentMedications", "");
				        		jsonObj.put("currentMedicationPurpose", "");
				        	}
				        	resultList.add(jsonObj);
						}
				    }
			    }
			    jsonStr = resultList.toString();
			}else{
				System.out.println(" No Contact To Get Contact History. ");
			}
		}catch(Exception e){
			log.error("Error  ",e);
		}
		return jsonStr;
	}
	
	@RequestMapping(value="/showEmergencyContact", method=RequestMethod.GET)
	private @ResponseBody Boolean showEmergencyContact(@RequestParam String itemDetailsId, final HttpServletRequest request, final HttpServletResponse response) {
		Boolean showEmergencyContact = false;
		if(itemDetailsId!=null && StringUtils.isNotBlank(itemDetailsId)){
			List<String> lstItemDetailsId = Arrays.asList(itemDetailsId.split(","));
			if(lstItemDetailsId.size()>0){
				for(String itemDetailId: lstItemDetailsId){
					ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
					
					if(StringUtils.isNotBlank(itemDetail.getRequiresEmergencyContact()) && itemDetail.getRequiresEmergencyContact().equalsIgnoreCase("Yes")){
						showEmergencyContact = true;
						break;
					}
					
				}
			}
		}
		return showEmergencyContact;
	}
	
	@RequestMapping(value="/getEmergencyContacts", method=RequestMethod.GET)
    public @ResponseBody String getEmergencyContacts(final HttpServletRequest request, final HttpServletResponse response) { 
	 	JSONArray json = new JSONArray();
	 	
	 	Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
		
		Account account = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
    	}
    	
    	if(account==null){
    		return json.toString();
    	}
    	
    	List<User> users = userDao.findByEndDateAndCustomerEmail(new Date(),userId);
    	
    	for(User u: users){
	    	JSONObject obj = new JSONObject();
	    	obj.put("id", u.getContactId());
			obj.put("contactId", u.getContactId());
			obj.put("fname", u.getPersonFirstName());
			obj.put("lname", u.getPersonLastName());
			obj.put("isMember", isMember(u.getContactId().toString()));
			obj.put("isAdult", isAdultMember(u));
			obj.put("age", getAge(u));
			if(u.getEmailAddress() != null && u.getEmailAddress().equals(userId)){
				obj.put("isLoggedInUser", 'Y');
			}
			json.add(obj);
    	}
    	
    	return json.toString();
	}
	
	@RequestMapping(value="/showAuthorisedContact", method=RequestMethod.GET)
	private @ResponseBody Boolean showAuthorisedContact(@RequestParam String itemDetailsId, final HttpServletRequest request, final HttpServletResponse response) {
		Boolean showAuthorisedContact = false;
		if(itemDetailsId!=null && StringUtils.isNotBlank(itemDetailsId)){
			List<String> lstItemDetailsId = Arrays.asList(itemDetailsId.split(","));
			if(lstItemDetailsId.size()>0){
				for(String itemDetailId: lstItemDetailsId){
					ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
					
					if(StringUtils.isNotBlank(itemDetail.getRequiresAuthorisedContact()) && itemDetail.getRequiresAuthorisedContact().equalsIgnoreCase("Yes")){
						showAuthorisedContact = true;
						break;
					}
					
				}
			}
		}
		return showAuthorisedContact;
	}
	
	@RequestMapping(value="/getAuthorisedContacts", method=RequestMethod.GET)
    public @ResponseBody String getAuthorisedContacts(final HttpServletRequest request, final HttpServletResponse response) { 
	 	JSONArray json = new JSONArray();
	 	
	 	Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
		
		Account account = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
    	}
    	
    	if(account==null){
    		return json.toString();
    	}
    	
    	List<User> users = userDao.findByEndDateAndCustomerEmail(new Date(),userId);
    	
    	
    	for(User u: users){
	    	JSONObject obj = new JSONObject();
	    	obj.put("id", u.getContactId());
			obj.put("contactId", u.getContactId());
			obj.put("fname", u.getPersonFirstName());
			obj.put("lname", u.getPersonLastName());
			obj.put("isMember", isMember(u.getContactId().toString()));
			obj.put("isAdult", isAdultMember(u));
			obj.put("age", getAge(u));
			json.add(obj);
    	}
    	
    	return json.toString();
	}

	private Boolean isAdultMember(User u) { // check for 18+ age 
		MemberAge mAge = new MemberAge();
		Boolean isAdult = false; 
		Integer age = 0;
		if(u.getDateOfBirth()!=null) // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
			age = mAge.getAge(u.getDateOfBirth()); //pass the DOB of user to the method and it returns int Age only in Years.
		
		if(age>=18)
			isAdult = true;
		return isAdult;
	}
	
	private Integer getAge(User u) { // get age of user
		MemberAge mAge = new MemberAge();
		Integer age = 0;
		if(u.getDateOfBirth()!=null) // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
			age = mAge.getAge(u.getDateOfBirth()); //pass the DOB of user to the method and it returns int Age only in Years.
		
		return age;
	}
	
	@RequestMapping(value="/showInserviceIndividualDays", method=RequestMethod.GET)
    public @ResponseBody String showInserviceIndividualDays(@RequestParam String itemDetailId, final HttpServletRequest request, final HttpServletResponse response) { 
	 	JSONArray json = new JSONArray();
	 	
	 	if(itemDetailId!=null && StringUtils.isNotBlank(itemDetailId)){
	 		ItemDetail itemDetail = itemDetailsDao.findOne(Long.parseLong(itemDetailId));
	 		List<ItemDetailDays> itemDays = itemDetail.getItemDays();
	 		
	 		List<ItemDetailDaysPricingRule> pricingRuleLst =  itemDetailDaysPricingRuleDao.findByItemDetailId(itemDetail.getId());
	 		
	 		if(itemDays.size()>0){
		    	for(ItemDetailDays itemDetailDays: itemDays){
		    		Boolean hasPastDates = false;
		    		if(itemDetailDays.getDate_c()!=null && itemDetailDays.getDate_c().before(new Date())){
		    			hasPastDates = true;
		    		}
		    		
		    		JSONObject obj = new JSONObject();
			    	obj.put("id", itemDetailDays.getId());
			    	obj.put("itemDetailId", itemDetailId);
			    	obj.put("dayId", Integer.parseInt(itemDetailDays.getDay()));
					obj.put("day", daysArr.get(Integer.parseInt(itemDetailDays.getDay())));
					obj.put("webcapacity", itemDetailDays.getWebCapacity());
					obj.put("remainingcapacity", itemDetailDays.getRemainingCapacity());
					obj.put("date_c", itemDetailDays.getDate_c());
					obj.put("hasPastDates", hasPastDates);
		    		
					Double membertierPrice = 0D;
					Double nonmembertierPrice = 0D;
					
					for(ItemDetailDaysPricingRule itemDetailDaysPricingRule: pricingRuleLst){
						if(itemDetailDaysPricingRule.getItemDetailDays().getId()!=null && itemDetailDays.getId().equals(itemDetailDaysPricingRule.getItemDetailDays().getId())){
							if(Constants.SIGNUP.equalsIgnoreCase(itemDetailDaysPricingRule.getPricingRule().getType())){
								if(itemDetailDaysPricingRule.getPricingRule().getTierPrice() != null)
									membertierPrice += itemDetailDaysPricingRule.getPricingRule().getTierPrice();
									
								if(itemDetailDaysPricingRule.getPricingRule().getNonmemberTierPrice() != null)
									nonmembertierPrice += itemDetailDaysPricingRule.getPricingRule().getNonmemberTierPrice();
							}
						}
		    		}
					
					obj.put("memberprice", membertierPrice);
					obj.put("nonmemberprice", nonmembertierPrice);
					
					json.add(obj);
		    		
		    	}
	 		}
	 	}
    	return json.toString();
	}
	
	@RequestMapping(value="/getSystemPropertiesByPicklistName", method=RequestMethod.GET)
    public @ResponseBody String getSystemPropertiesByPicklistName(@RequestParam String picklistName, final HttpServletRequest request, final HttpServletResponse response) {
		JSONArray json = new JSONArray();
		List<SystemProperty> props = systemPropertyDao.getByPicklistName(picklistName);
		for (SystemProperty systemProperty : props) {
			JSONObject obj = new JSONObject();	
			obj.put(systemProperty.getKeyName(), systemProperty.getKeyValue());
			json.add(obj);
		}
    	return json.toString();
	}
	
	@RequestMapping(value="/getPrimaryUserForAccount", method=RequestMethod.GET)
    public @ResponseBody String getPrimaryUserForAccount(final HttpServletRequest request, final HttpServletResponse response) {
		JSONObject json = new JSONObject();
    	
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			return json.toString();
		}
		
		Account account = null;
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
    	}
    	
		if(account != null && !account.getUser().isEmpty()){
			Set<User> users = account.getUser();
			for (User user : users) {
				if(user.isPrimary()){
					json.put("fname", user.getFirstName());
					json.put("lname", user.getLastName());
				}
			}
		}
		return json.toString();
	}
	
	@RequestMapping(value="/getPromotions", method=RequestMethod.GET)
    public @ResponseBody String getPromotions(@RequestParam String itemContactMapStr, @RequestParam String isAuto, final HttpServletRequest request, final HttpServletResponse response) {
		Account customer = null;
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			log.error(" failed userId :   "+userId);
			return "FAIL";
		}
		
    	if(userId != null && !"".equals(userId)){
    		customer = accountDao.getByEmail(userId);
    	}
		return promotionService.getPromotions(itemContactMapStr, customer, isAuto);
	}
	
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/getPromoMap", method=RequestMethod.GET)
    public @ResponseBody String getPromoMap(@RequestParam String itemId, @RequestParam String contactId, @RequestParam String isAuto, @RequestParam String isRecurring, @RequestParam String amountJSON, 
    		//@RequestParam String setupfee, @RequestParam String registrationPrice, @RequestParam String depositAmount, @RequestParam String joinFee, 
    		@RequestParam String selectedStartDate, @RequestParam String lstCartItem, @RequestParam String urlItemContactPromo,
    		final HttpServletRequest request, final HttpServletResponse response) throws ParseException {
		String promos = new String("");
		Account customer = null;
		String urlPromoCode = null, urlContactId = null;
		try{
			//log.info(" urlItemContactPromo  ::  "+urlItemContactPromo);
			if(urlItemContactPromo != null && !urlItemContactPromo.equals("")){
				String[] urlItemContactPromoA = urlItemContactPromo.split("_");
				if(urlItemContactPromoA.length == 3){
					String urlItemDetailId = urlItemContactPromoA[0];
					String urlContact = urlItemContactPromoA[1];
					String urlPromo = urlItemContactPromoA[2];
					
					if(itemId.equals(urlItemDetailId)){
						urlPromoCode = urlPromo;
						
						if(urlContact != null && urlContact.equals(contactId)){
							urlContactId = urlContact;
						}
					}
				}
			}
			
			
			List<Signup> cartSignups = null;
			if(lstCartItem != null && !lstCartItem.equals("")){
				List<String> cartItemsStr = Arrays.asList(lstCartItem.split("_AND_"));
				if(cartItemsStr.size()>0){
					Authentication a = SecurityContextHolder.getContext().getAuthentication();
					String userId = null;
					try{
						userId = ((UserDetails) a.getPrincipal()).getUsername();
					}catch(Exception e){
						log.error(" failed userId :   "+userId);
						return "FAIL";
					}
					
		        	if(userId != null && !"".equals(userId)){
		        		customer = accountDao.getByEmail(userId);
		        	}
					
					cartSignups = new ArrayList<Signup>();
		    		for(String cartItemStr: cartItemsStr){
		    			if(cartItemStr!=null){
		    				Signup signup = new Signup();
		    				JSONObject cartItemMap = JSONObject.fromObject(cartItemStr);
			    			Object itemDetailId = cartItemMap.get("itemDetailId");
			    			Object signupContactId = cartItemMap.get("contactId");
			    			Object itempriceOnSignup = cartItemMap.get("itempriceOnSignup");
			    			Object signupAmount = cartItemMap.get("signupAmount");
			    			Object setupFee = cartItemMap.get("setupFee");
			    			Object registrationFee = cartItemMap.get("registrationFee");
			    			Object depositAmount = cartItemMap.get("depositAmount");
			    			Object priceOption = cartItemMap.get("priceOption");
			    			Object billingOption = cartItemMap.get("billingOption");
			    			Object noOfTickets = cartItemMap.get("noOfTickets");
			    			Object specialrequest = cartItemMap.get("specialrequest");
			    			Object joinFee = cartItemMap.get("joinFee");
			    			Object signUpSelectedStartDate = cartItemMap.get("selectedStartDate");
			    			Object nextBillDateObj = cartItemMap.get("nextBillDate");
			    			
			    			ItemDetail itemdetail = itemDetailDao.findOne(Long.valueOf(itemDetailId.toString()));
			    			signup.setCustomer(customer);
			    			signup.setType(itemdetail.getType());
			    			signup.setItemDetail(itemdetail);
			    			signup.setContact(userDao.findOne(Long.valueOf(signupContactId.toString())));
			    			signup.setProgramStartDate(itemdetail.getStartDate());
			    			signup.setProgramEndDate(itemdetail.getEndDate());
			    			signup.setLocation(locationDao.getOne(itemdetail.getLocation().getId()));
			    			String signupstatus = SignupStatusEnum.Active.toString();
			    			if(Boolean.valueOf(cartItemMap.get("waitlist").toString()))
			    				signupstatus = SignupStatusEnum.Waitlisted.toString();
			    			signup.setStatus(signupstatus);
			    			signup.setWaitlist(0L);
			    			if(signUpSelectedStartDate != null && !signUpSelectedStartDate.toString().equals("")){
			    				signup.setSignupDate(new Date(signUpSelectedStartDate.toString()));
			    			}else{
			    				//signup.setSignupDate(new Date());
			    			}
			    			signup.setFinalAmount(itempriceOnSignup.toString());
			    			signup.setSignupPrice(Double.parseDouble(signupAmount.toString()));
			    			signup.setSetUpFee(Double.parseDouble(setupFee.toString()));
			    			signup.setRegistrationFee(Double.parseDouble(registrationFee.toString()));
			    			signup.setDeposit(Double.parseDouble(depositAmount.toString()));
			    			signup.setSignUpPricingOption(priceOption.toString());
			    			signup.setBillingOption(billingOption.toString());
			    			if(signup.getType().equals(ProductTypeEnum.EVENT.toString())){
			    				signup.setNoOfTickets(Integer.parseInt(noOfTickets.toString()));
			    			}
			    			signup.setJoinFee(Double.parseDouble(joinFee.toString()));
			    			signup.setSpecialRequest(specialrequest.toString());
			    			if(nextBillDateObj != null && !nextBillDateObj.equals("") && !nextBillDateObj.toString().equals("null"))
			    				signup.setNextBillDate(new Date(nextBillDateObj.toString()));
			    			
			    			cartSignups.add(signup);
		    			}
		    		}
		    		//log.info(" cartSignups.size  >>  "+cartSignups.size());
				}
			}
			
			/*if(contactId != null && contactId.contains(",")){
				String[] contacts = contactId.split(",");
				
				if(contacts.length > 0){
					for (String string : contacts) {
						promos = promotionService.getPromoMap(itemId, contactId, customer, isAuto, isRecurring, amountJSON, selectedStartDate, cartSignups, urlPromoCode, urlContactId);
					}
				}
				
				
			}else{
				*/
				promos = promotionService.getPromoMap(itemId, contactId, customer, isAuto, isRecurring, amountJSON, selectedStartDate, cartSignups, urlPromoCode, urlContactId);
				
			//}
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("Error: "+e.getMessage());
		}
		
		return promos;
	}
	
	@RequestMapping(value="/promoSignup", method=RequestMethod.GET)
    public String promoInURL(@RequestParam String i, @RequestParam(required = false) String c, @RequestParam(required = false) String pc, Model model) { 
		
		String view = "redirect:/login";
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			log.error(" failed userId :   "+userId);
		}
		
		HashMap<String, Object> modelView = promotionService.getViewForPromoInURL(userId, i, c, null, pc, model);
		
		model = (Model)modelView.get("model");
		view = (String)modelView.get("view");
		
		
		/*Long itemDetailId = -1l, contactPartyId = -1l;
		String promoCode = null;
		ItemDetail itemDetail = null;
		User contact = null;
		
		if(i!=null && !"".equals(i.trim())){
			itemDetailId = Long.parseLong(i);
			itemDetail = itemDetailsDao.getById(itemDetailId);
		}

		if(c!=null && !"".equals(c.trim())){
			contactPartyId = Long.parseLong(c);
			contact = userDao.findByPartyId(contactPartyId);
		}
			
		if(pc!=null && !"".equals(pc.trim())){
			promoCode = pc;
		}
			
		if(itemDetail != null && promoCode != null){
			if(itemDetail.getType() != null && !itemDetail.getType().equals("")){
				model.addAttribute("urlPromoItemDetailId", itemDetail.getId());
				if(contactPartyId > 0 && contact != null){
					model.addAttribute("urlPromoContactId", contact.getContactId());
				}
				model.addAttribute("urlPromoCode", promoCode);
				
				String dispatchTo = itemDetail.getType();
				if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.PROGRAM.toString())){
	        		return "forward:/program";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.EVENT.toString())){
	        		return "forward:/event";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CAMP.toString())){
	        		return "forward:/camp";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CHILDCARE.getValue())){
	        		return "forward:/childcare";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.MEMBERSHIP.getValue())){
	        		if(contactPartyId > 0 && contact != null){
	        			
	        	    	if(userId != null && !"".equals(userId)){
	        	    		
	        	    		return "forward:/changeMembershipShowWizard";
	        	    		
	        	    		//customer = accountDao.getByEmail(userId);
	        	    	}else{
	        	    		
	        	    		return "forward:/login";
	        	    		
	        	    	}
	        			
//	        			return "forward:/changeMembershipShowWizard";
	        		}else{
	        			return "forward:/becomeMember";
	        		}
	        	}
			}*/
		//}
		return view;
	}
	
	@RequestMapping(value="/isBypassMembershipRegistrationDateRange", method=RequestMethod.GET)
    public @ResponseBody String isBypassMembershipRegistrationDateRange(@RequestParam String itemId, @RequestParam String contactId) { 
		Account customer = null;
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			log.error(" failed userId :   "+userId);
			return "FAIL";
		}
		
    	if(userId != null && !"".equals(userId)){
    		customer = accountDao.getByEmail(userId);
    	}
    	
		JSONObject json = new JSONObject();
		boolean isAnyEarlyBirdPromo = false;
		//log.info("  itemId   "+itemId);
		//log.info("  contactId   "+contactId);
		if(itemId != null && !itemId.equals("")){
			
			ItemDetail itemDetail = itemDetailDao.findOne(Long.valueOf(itemId));
			User contact = null;
			if(contactId != null && !contactId.equals("") && Double.parseDouble(contactId) > 0)
				contact = userDao.findOne(Long.valueOf(contactId));
			List<Signup> cartSignups = new ArrayList<Signup>();
			List<ItemDetail> promos = promotionService.getApplicablePromos(itemDetail, contact, customer, cartSignups, null);
			
			if(promos != null && !promos.isEmpty()){
				for (ItemDetail promo : promos) {
					if(promo != null && promo.getCategory() != null && promo.getCategory().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_EARLY_BIRD)){
						isAnyEarlyBirdPromo = true;
					}
				}
			}
			
			if(isAnyEarlyBirdPromo){
				json.put("RESULT", "YES");	
			}else{
				json.put("RESULT", "NO");
			}
		}
		
		//log.info("  isAnyEarlyBirdPromo   "+isAnyEarlyBirdPromo);
		
		return json.toString();
	}
	
	@RequestMapping(value = "/isYAgent", method = RequestMethod.GET)
	public @ResponseBody String isYAgent() {
		String result = "false";
		try {
			String agentUId = getAgentUidFromSession();
			if(agentUId != null && !agentUId.equals(""))
				result = "true";
				
		} catch (Exception se) {
			log.error("Error getting isYAgent.",se);
		}
		return result;
	}
}
