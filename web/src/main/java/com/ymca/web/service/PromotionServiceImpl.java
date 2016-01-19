package com.ymca.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ContactPromotionDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.ContactPromotion;
import com.ymca.model.ItemDetail;
import com.ymca.model.Signup;
import com.ymca.model.SignupPromotion;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.util.Constants;

@Service
public class PromotionServiceImpl implements PromotionService {
	
	private Logger log = LoggerFactory.getLogger(PromotionServiceImpl.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Resource
	private ItemDetailDao itemDetailDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ContactPromotionDao contactPromotionDao;
	
	@Resource
	private SystemPropertyDao systemPropertyDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private PromoExpressionEvaluator promoExpressionEvaluator;
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Override
	public String getPromotions(String itemContactMapStr, Account customer, String isAuto){
		
		JSONObject json = new JSONObject();
		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
		if(itemContactMapStr != null && !itemContactMapStr.equals("")){
			String[] itemContactMap = itemContactMapStr.split(",");
			if(itemContactMap != null && itemContactMap.length > 0){

				for (String itemContactStr : itemContactMap) {
					String[] itemContact = itemContactStr.split("_");
					if(itemContact.length == 2){
						String itemDetailId = itemContact[0];
						String contactId = itemContact[1];
						//log.info(" itemContactStr ::   "+itemContactStr);
						
						ItemDetail itemDetail = itemDetailDao.findOne(Long.valueOf(itemDetailId));
						User contact = userDao.findOne(Long.valueOf(contactId));
						//List<ItemDetail> promos = itemDetailDao.getPromotions(itemDetail, contact);
						
						//if(promos != null && promos.isEmpty()){
						List<ItemDetail> promos = getApplicablePromos(itemDetail, contact, customer, null, null);
						//}
						
						JSONArray jsonA = new JSONArray();
						if(promos != null && !promos.isEmpty()){
							for (ItemDetail promo : promos) {
								
								JSONObject promoObj = new JSONObject();
								promoObj.put("PromoId", promo.getId());
								promoObj.put("PromoCode", promo.getPromoCode());
								promoObj.put("AutoPromo", promo.getAutoPromotion());
								promoObj.put("PromoRuleType", promo.getPromotionRuleType());
								promoObj.put("PromoType", promo.getPromotionType());
								promoObj.put("PromoDiscountValue", promo.getPromotionDiscountValue());
								promoObj.put("RecurringPeriod", promo.getRecurringPeriod());
								//log.info("  StartDate str ::  "+ft.format(promo.getStartDate()));
								//log.info("  EndDate str ::  "+ft.format(promo.getEndDate()));
								promoObj.put("StartDate", ft.format(promo.getStartDate()));
								promoObj.put("EndDate", ft.format(promo.getEndDate()));
									
								if(isAuto != null && (isAuto.equals("true") || isAuto.equals("All")) && promo.getAutoPromotion().equals("Yes")){
									// get auto promos
									
									/*log.info(" getPromoCode :::  "+promo.getPromoCode());
									log.info(" getAutoPromotion :::  "+promo.getAutoPromotion());
									log.info(" getPromotionRuleType :::  "+promo.getPromotionRuleType());
									log.info(" getPromotionType :::  "+promo.getPromotionType());
									log.info(" getPromotionDiscountValue :::  "+promo.getPromotionDiscountValue());
									log.info(" getRecurringPeriod :::  "+promo.getRecurringPeriod());
									log.info(" getStartDate :::  "+promo.getStartDate());
									log.info(" getEndDate :::  "+promo.getEndDate());*/
									
									jsonA.add(promoObj);
								}
								if(isAuto != null && (isAuto.equals("true") || isAuto.equals("All")) && promo.getAutoPromotion().equals("No")){
									// get manual promos
									
									/*log.info(" getPromoCode :::  "+promo.getPromoCode());
									log.info(" getAutoPromotion :::  "+promo.getAutoPromotion());
									log.info(" getPromotionRuleType :::  "+promo.getPromotionRuleType());
									log.info(" getPromotionType :::  "+promo.getPromotionType());
									log.info(" getPromotionDiscountValue :::  "+promo.getPromotionDiscountValue());
									log.info(" getRecurringPeriod :::  "+promo.getRecurringPeriod());
									log.info(" getStartDate :::  "+promo.getStartDate());
									log.info(" getEndDate :::  "+promo.getEndDate());*/
									
									jsonA.add(promoObj);
								}
							}
						}
						
						json.put(itemContactStr, jsonA);
					}
				}
			}
		}
    	return json.toString();
	}
	
	@Override
	public String getPromoMap(String itemDetailId, String contactId, Account customer, String isAuto, String isRecurring, String amountJSON, String selectedStartDate, List<Signup> cartSignups, String urlPromoCode, String urlContactId) throws ParseException  {
		
		JSONObject json = new JSONObject();
		SimpleDateFormat ft = new SimpleDateFormat("MM/dd/yyyy");
		JSONObject amounts = JSONObject.fromObject(amountJSON);
		String signupPrice = (String) amounts.get("signupPrice");
		String setupfee = (String) amounts.get("setupFee");
		String registrationPrice = (String) amounts.get("registrationFee");
		String depositAmount = (String) amounts.get("depositAmount");
		String joinFee = (String) amounts.get("joinFee");
		Date signUpDate = new Date();
		if(selectedStartDate != null && !selectedStartDate.equals("")){
			signUpDate = ft.parse(selectedStartDate);
		}
		//log.info(" itemDetailId :: "+itemDetailId+", contactId :: "+contactId+", isAuto :: "+isAuto+", signupPrice :: "+signupPrice+", setupfee :: "+setupfee+", registrationPrice :: "+registrationPrice+", depositAmount :: "+depositAmount+", joinFee :: "+joinFee+", selectedStartDate :: "+selectedStartDate+", signUpDate :: "+signUpDate.toString());
		
		ItemDetail itemDetail = itemDetailDao.findOne(Long.valueOf(itemDetailId));
		
		List<ItemDetail> promos = new ArrayList<ItemDetail>();
		if(contactId != null && contactId.contains(",")){
			String[] contacts = contactId.split(",");
			if(contacts.length > 0){
				for (String constr : contacts) {
					
					User contact = null;
					if(constr != null && !constr.equals("")){
						Long conL = 0l;
						try{
							conL = Long.valueOf(constr);
							contact = userDao.findOne(conL);
							Account cust = accountDao.findByAccountId(contact.getAccountId());
							contact.setCustomer(cust);
						}catch(Exception e){
							e.printStackTrace();
						}
					}
					
					List<ItemDetail> promosTemp = getApplicablePromos(itemDetail, contact, customer, cartSignups, urlPromoCode);
					if(promosTemp != null && promosTemp.size() > 0){
						for (ItemDetail p : promosTemp) {
							
							boolean isAlreadyExist = false;
							for(ItemDetail p1 : promos){
								if(p.getId() == p1.getId()){
									isAlreadyExist = true;
								}
							}
							if(!isAlreadyExist){
								promos.add(p);
							}
						}
					}
				}
			}
		}else{
			User contact = null;
			Account cust = null;
			if(contactId != null && !contactId.equals("")){
				contact = userDao.findOne(Long.valueOf(contactId));
				if(contact != null){
					cust = accountDao.findByAccountId(contact.getAccountId());
					contact.setCustomer(cust);
				}
			}
			promos = getApplicablePromos(itemDetail, contact, cust, cartSignups, urlPromoCode);
		}
		
		JSONArray jsonA = new JSONArray(); 
		if(promos != null && !promos.isEmpty()){
			for (ItemDetail promo : promos) {
				
				JSONObject promoObj = new JSONObject();
				promoObj.put("PromoId", promo.getId());
				promoObj.put("PromoCode", promo.getPromoCode());
				promoObj.put("Description", promo.getDescription());
				promoObj.put("RecordName", promo.getRecordName());
				promoObj.put("AutoPromo", promo.getAutoPromotion());
				promoObj.put("PromoRuleType", promo.getPromotionRuleType());
				promoObj.put("PromoType", promo.getPromotionType());
				promoObj.put("PromoDiscountValue", promo.getPromotionDiscountValue());
				promoObj.put("RecurringPeriod", promo.getRecurringPeriod());
				promoObj.put("Category", promo.getCategory());
				//promoObj.put("StartDate", promo.getStartDate());
				//promoObj.put("EndDate", promo.getEndDate());
				
				double discountValue = 0;
				int noOfDiscountMonths = 0;
				
				if(promo.getPromotionRuleType() != null){
					if(promo.getPromotionRuleType().equals(Constants.RuleType_SignUp)){
						if(signupPrice != null && !signupPrice.equals("") && Double.parseDouble(signupPrice) > 0){
							discountValue = computePromoDiscount(promo, signupPrice);
						}
					}else if(promo.getPromotionRuleType().equals(Constants.RuleType_Registration)){
						if(registrationPrice != null && !registrationPrice.equals("") && Double.parseDouble(registrationPrice) > 0){
							discountValue = computePromoDiscount(promo, registrationPrice);
						}
					}else if(promo.getPromotionRuleType().equals(Constants.RuleType_SetUpFee)){
						if(setupfee != null && !setupfee.equals("") && Double.parseDouble(setupfee) > 0){
							discountValue = computePromoDiscount(promo, setupfee);
						}
					}else if(promo.getPromotionRuleType().equals(Constants.RuleType_Deposit)){
						if(depositAmount != null && !depositAmount.equals("") && Double.parseDouble(depositAmount) > 0){
							discountValue = computePromoDiscount(promo, depositAmount);
						}
					}else if(promo.getPromotionRuleType().equals(Constants.RuleType_JoinFee)){
						if(joinFee != null && !joinFee.equals("") && Double.parseDouble(joinFee) > 0){
							discountValue = computePromoDiscount(promo, joinFee);
						}
					}
					
					/*if(promo.getRecurringPeriod() == 1){
						// one time promo
						noOfDiscountMonths = 0;
					}else */ 
					if(promo.getRecurringPeriod() > 0 && (isRecurring != null && isRecurring.equals("true"))){
						// recurring promo
						noOfDiscountMonths = promo.getRecurringPeriod();
					}else if(isRecurring != null && !isRecurring.equals("true")){
						// Auto Promotion is not applied
						discountValue = 0;
						noOfDiscountMonths = 0;
					}
				}
				promoObj.put("discountValue", discountValue);
				promoObj.put("noOfDiscountMonths", noOfDiscountMonths);
				promoObj.put("monthlyDiscountAmount", computeMonthlyDiscountAmount(discountValue, noOfDiscountMonths));
				
				boolean isSignupDateInRange = false;
				if(promo.getStartDate() != null){
					if(promo.getEndDate() != null){
						isSignupDateInRange = (signUpDate.after(promo.getStartDate()) && signUpDate.before(promo.getEndDate())) ? true : false;
					}else if(promo.getEndDate() == null){
						isSignupDateInRange = (signUpDate.after(promo.getStartDate())) ? true : false;
					}
				}
				if(discountValue > 0 && isAuto != null && isAuto.equals("true") && promo.getAutoPromotion().equals("Yes") && isSignupDateInRange){
					// get auto promos
					//log.info(" getPromoCode :::  "+promo.getPromoCode()+", getAutoPromotion :::  "+promo.getAutoPromotion()+", getPromotionRuleType :::  "+promo.getPromotionRuleType()+", getPromotionType :::  "+promo.getPromotionType()+", getPromotionDiscountValue :::  "+promo.getPromotionDiscountValue()+", getRecurringPeriod :::  "+promo.getRecurringPeriod()+", getStartDate :::  "+promo.getStartDate()+", getEndDate :::  "+promo.getEndDate());
					jsonA.add(promoObj);
				}
				if(discountValue > 0 && (isAuto != null && isAuto.equals("false") || (urlPromoCode != null && urlPromoCode.equalsIgnoreCase(promo.getPromoCode()))) && promo.getAutoPromotion().equals("No") && isSignupDateInRange){
					// get manual promos
					//log.info(" getPromoCode :::  "+promo.getPromoCode()+", getAutoPromotion :::  "+promo.getAutoPromotion()+", getPromotionRuleType :::  "+promo.getPromotionRuleType()+", getPromotionType :::  "+promo.getPromotionType()+", getPromotionDiscountValue :::  "+promo.getPromotionDiscountValue()+", getRecurringPeriod :::  "+promo.getRecurringPeriod()+", getStartDate :::  "+promo.getStartDate()+", getEndDate :::  "+promo.getEndDate());
					jsonA.add(promoObj);
				}
			}
			json.put("promos", jsonA);
		}
    	return json.toString();
	}
	
	public List<ItemDetail> getApplicablePromos(ItemDetail itemDetail, User contact, Account customer, List<Signup> cartSignups, String urlPromoCode){
		//log.info(" ItemDetailId   "+itemDetail.getId()+"   Contact   "+contact.getContactId()+"		urlPromoCode   "+urlPromoCode);
		List<ItemDetail> promos = new ArrayList<ItemDetail>();
		List<ItemDetail> allPromos = itemDetailDao.getAllPromotions(itemDetail);
		List<SystemProperty> promoExpressionProperties = systemPropertyDao.findByPicklistNameAndFieldStatus(Constants.PickListName_PROMO_EXPRESSION, true);
		
		for (ItemDetail promo : allPromos) {
			boolean isAddPromo = false, isValidPromotionExpression = false;
			if(promo.getPromoApplicableToAllContacts() != null && promo.getPromoApplicableToAllContacts().equalsIgnoreCase("No") &&  contact != null){
				ContactPromotion contactPromotion = contactPromotionDao.findByItemDetailAndContactAndStatus(promo, contact, Constants.ACTIVE);
				if(contactPromotion != null){
					isAddPromo = true;
				}
			}else if(promo.getPromoApplicableToAllContacts() != null && promo.getPromoApplicableToAllContacts().equalsIgnoreCase("Yes")){
				isAddPromo = true;
			}
			
			if(isAddPromo){
				// evaluate promotion expression
				if(promo.getPromotionExpression() != null && !promo.getPromotionExpression().equals("")){
					String promotionExpressionStr = promo.getPromotionExpression();
					String promotionExpressionOperator = promo.getPromotionExpressionOperator();
					String[] promotionExpressionArray = promotionExpressionStr.split(",");
					if(promotionExpressionArray != null && promotionExpressionArray.length > 0){
						
						boolean[] promotionExpressionResultArray = new boolean[promotionExpressionArray.length];
						for (int i = 0; i < promotionExpressionArray.length; i++) {
							String promotionExpressionCode = promotionExpressionArray[i];
							if(promotionExpressionCode != null && !promotionExpressionCode.equals("")){
								String promotionExpression = null;
								Signup signup = new Signup();
								signup.setItemDetail(itemDetail);
								if(contact != null){
									signup.setContact(contact);
								}
								signup.setCustomer(customer);
								if(promoExpressionProperties != null && promoExpressionProperties.size() > 0){
									for (SystemProperty systemProperty : promoExpressionProperties) {
										if(systemProperty.getKeyName().equals(promotionExpressionCode.trim())){
											promotionExpression = systemProperty.getKeyValue();
										}
									}
								}
								promotionExpressionResultArray[i] = evaluatePromotionExpression(promotionExpression, signup, cartSignups);
							}else{
								promotionExpressionResultArray[i] = false;
							}
						}
						
						if(promotionExpressionArray.length == promotionExpressionResultArray.length){
							if(promotionExpressionArray.length > 1 && promotionExpressionOperator != null && !promotionExpressionOperator.equals("")){
								if(promotionExpressionOperator.equalsIgnoreCase(Constants.PromotionExpressionOperator_AND)){
									isValidPromotionExpression = true;
									for (int i = 0; i < promotionExpressionResultArray.length; i++) {
										isValidPromotionExpression = isValidPromotionExpression && promotionExpressionResultArray[i];
									}
								} else if(promotionExpressionOperator.equalsIgnoreCase(Constants.PromotionExpressionOperator_OR)){
									for (int i = 0; i < promotionExpressionResultArray.length; i++) {
										isValidPromotionExpression = isValidPromotionExpression || promotionExpressionResultArray[i];
									}
								}
							}else if(promotionExpressionArray.length == 1){
								isValidPromotionExpression = promotionExpressionResultArray[0];
							}
						}
					}
				}else{
					isValidPromotionExpression = true;
				}
			}
			
			if(isAddPromo && isValidPromotionExpression){
				//log.info("  Add Promo ");
				promos.add(promo);
			}
		}
		return promos;
	}
	
	private Double computePromoDiscount(ItemDetail promo, String price){
		double discountValue = 0;
		if(promo.getPromotionType().equals("$")){
			discountValue = Double.parseDouble(promo.getPromotionDiscountValue());
		}else if(promo.getPromotionType().equals("%")){
			discountValue = (Double.parseDouble(price) * Double.parseDouble(promo.getPromotionDiscountValue())) / 100;
		}
		return discountValue;
	}
	
	/*@SuppressWarnings("rawtypes")
	public boolean evaluatePromotionExpression(String sqlExpression, Signup signup, List<Signup> cartSignups){
		
			signup.setProgramStartDate(new Date());
	
			Signup signup1 = signupDao.save(signup);
			
			log.info("  Signup1  "+signup1.getSignupId());
			
			
			
			boolean result = false;
			if(sqlExpression != null && !sqlExpression.equals("")){
				Set<String> keywords = getExpressionKeywords(sqlExpression);
				if(keywords != null && keywords.size() > 0){
					ItemDetail itemDetail = signup.getItemDetail();
					User contact = signup.getContact();
					//Account account = contact.getCustomer();
					//Map<String, Object> values = getPromotionExpressionValues(signup);
					for (String keyword : keywords) {
						log.info("  keyword  :: "+keyword);
						String[] str = keyword.split("\\.");
						
						if(str.length == 2){
							log.info("  str  :: "+str);
							String table = str[0];
							String column = str[1];
							log.info("  table  ::  "+table+"   column  ::  "+column);
							Object value = null;
							switch (table) {
							case "ItemDetail":
								value = getFieldValue(itemDetail, column);
								break;
							case "Signup":
								value = getFieldValue(signup, column);
								break;
							case "User":
								value = getFieldValue(contact, column);
								break;
							default:
								break;
							}
							
							if(value != null && !value.toString().equals("")){
								sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
							}
							
						}else if(str.length == 3){
							log.info("  str  :: "+str);
							String table1 = str[0];
							String table2 = str[1];
							String column = str[2];
							log.info("  table1   ::  "+table1+"   table2   ::  "+table2+"   column  ::  "+column);
							Object table2Obj = null, value = null;
							switch (table1) {
							case "ItemDetail":
								table2Obj = getFieldValue(itemDetail, table2);
								break;
							case "Signup":
								table2Obj = getFieldValue(signup, table2);
								break;
							case "User":
								table2Obj = getFieldValue(contact, table2);
							default:
								break;
							}
							
							value = getFieldValue(table2Obj, table2);
							
							if(value != null && !value.toString().equals("")){
								sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
							}
						}
						*/
						/*Object value = null;
						switch (keyword) {
						
						case Constants.PromotionExpressionKeyword_Signup_ContactId:
						case Constants.PromotionExpressionKeyword_Signup_ItemDetailId:
							
							value = values.get(keyword);
							if(value != null && !value.toString().equals("")){
								sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
							}
							break;
								
						default:
							break;
						}*/
					/*}
				}
			}
			
			log.info("  sqlExpression  ::  "+sqlExpression);
			
			if(sqlExpression != null && !sqlExpression.equals("")){
				Set<String> keywords = getExpressionKeywords(sqlExpression.toString());
				if(keywords != null && keywords.size() > 0){
					// do nothing
					log.error("Unable to evaluate promotion expression - "+sqlExpression);
				}else{
					// execute query
					List resultList = entityManager.createNativeQuery(sqlExpression.toString()).getResultList();
					if(resultList != null && resultList.size() > 0){
						result = true;
						log.info("  Result found :: "+resultList.size());
					}else{
						// do nothing
						log.info("  Result not found");
					}
				}
			}
		
		return result;
	}*/
	
	
	/*private Object getFieldValue(Object obj, String fieldName){
		Object value = null;
		Class c = obj.getClass();
		try {
			Field field = c.getDeclaredField(fieldName);
			value = field.get(obj);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			log.error("Unable to evaluate promotion expression for the keyward - "+c.getName()+"."+fieldName);
			log.error("Exception : "+e.getMessage());
		}
		return value;
	}
	
	private static final Pattern TAG_REGEX = Pattern.compile("<(.+?)>");

	private static Set<String> getExpressionKeywords(final String str) {
	    final Set<String> tagValues = new HashSet<String>();
	    final Matcher matcher = TAG_REGEX.matcher(str);
	    while (matcher.find()) {
	        tagValues.add(matcher.group(1));
	    }
	    return tagValues;
	}*/
	
	@Override
	public boolean evaluatePromotionExpression(String sqlExpression, Signup signup, List<Signup> cartSignups){
		try {
			
			promoExpressionEvaluator.evaluatePromotionExpressionTransactional(sqlExpression, signup, cartSignups);
			
		} catch (Exception e) {
			log.error(" RuntimeException For Rollback :::  "+e.getMessage());
			//e.printStackTrace();
		}
		
		//log.info("   promoExpressionEvaluator.expressionResult  >>>  "+promoExpressionEvaluator.expressionResult);
		//log.info("   promoExpressionEvaluator.expressionResult 11 >>>  "+promoExpressionEvaluator.isExpressionResult());
		
		return promoExpressionEvaluator.isExpressionResult();
	}
	
	@Override
	public Double computeMonthlyDiscountAmount(Double discountAmount, int recurringPeriod){
		Double monthlyDiscountAmount = 0d;
		if(recurringPeriod > 0){
			monthlyDiscountAmount = discountAmount / recurringPeriod;
		}
		return monthlyDiscountAmount;
	}
	
	@Override
	public Double computeRemainingDiscountAmount(SignupPromotion signupPromotion, Double discountAmount, Double actualDiscountAmt){
		Double remainingDiscountAmount = 0d, oldRemainingDiscountAmount = 0d, monthlyDiscountAmount = 0d;
		if(signupPromotion.getRemainingDiscountAmount() != null){
			oldRemainingDiscountAmount = signupPromotion.getRemainingDiscountAmount();
		}
		
		if(signupPromotion.getMonthlyDiscountAmount() != null){
			monthlyDiscountAmount = signupPromotion.getMonthlyDiscountAmount();
		}
		
		if(oldRemainingDiscountAmount - monthlyDiscountAmount > 0){
			remainingDiscountAmount =  oldRemainingDiscountAmount - monthlyDiscountAmount;
		}else{
			remainingDiscountAmount =  discountAmount - actualDiscountAmt;
		}
		return remainingDiscountAmount;
	}
	
	@Override
	public HashMap<String, Object> getViewForPromoInURL(String userId, String i, String urlPromoContactPartyId, String contactId, String pc, Model model){
		HashMap<String, Object> result = new HashMap<String, Object>();
		//Model model  = new ExtendedModelMap();
		String view = null;
		Long itemDetailId = -1l, contactPartyId = -1l;
		String promoCode = "";
		ItemDetail itemDetail = null;
		User contact = null;
		
		if(i!=null && !"".equals(i.trim())){
			itemDetailId = Long.parseLong(i);
			itemDetail = itemDetailsDao.getById(itemDetailId);
		}

		if(urlPromoContactPartyId!=null && !"".equals(urlPromoContactPartyId.trim())){
			contactPartyId = Long.parseLong(urlPromoContactPartyId);
			contact = userDao.findByPartyId(contactPartyId);
		}else if(contactId != null){
			contact = userDao.findOne(Long.parseLong(contactId));
		}
			
		if(pc!=null && !"".equals(pc.trim())){
			promoCode = pc;
		}
			
		if(itemDetail != null){
			if(itemDetail.getType() != null && !itemDetail.getType().equals("")){
				model.addAttribute("urlPromoItemDetailId", itemDetail.getId());
				if(contactPartyId > 0 && contact != null){
					model.addAttribute("urlPromoContactId", contact.getContactId());
				}
				model.addAttribute("urlPromoCode", promoCode);
				
				String dispatchTo = itemDetail.getType();
				if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.PROGRAM.toString())){
	        		view = "forward:/program";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.EVENT.toString())){
	        		view = "forward:/event";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CAMP.toString())){
	        		view = "forward:/camp";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.CHILDCARE.getValue())){
	        		view = "forward:/childcare";
	        	}else if(dispatchTo.equalsIgnoreCase(ProductTypeEnum.MEMBERSHIP.getValue())){
	        		if(contact != null){
	        			
/*	        			Account customer = null;
	        			Authentication a = SecurityContextHolder.getContext().getAuthentication();
	        			String userId = null;
	        			try{
	        				userId = ((UserDetails) a.getPrincipal()).getUsername();
	        			}catch(Exception e){
	        				log.error(" failed userId :   "+userId);
	        				//return "FAIL";
	        			}
	        			*/
	        	    	if(userId != null && !"".equals(userId)){
	        	    		
	        	    		view = "forward:/changeMembershipShowWizard";
	        	    		
	        	    		//customer = accountDao.getByEmail(userId);
	        	    	}else{
	        	    		
	        	    		view = "forward:/login";
	        	    		
	        	    	}
	        			
//	        			return "forward:/changeMembershipShowWizard";
	        		}else{
	        			view = "forward:/becomeMember";
	        		}
	        	}
				result.put("model", model);
				result.put("view", view);
			}
		}
		return result;
	}
	
	/*private Object getKeywardValue(String keyword){
		Object value = null;
		log.info("  keyword  :: "+keyword);
		String[] str = keyword.split("\\.");
		
		if(str.length == 2){
			
			log.info("  str  :: "+str);
			String tableName = str[0];
			String columnName = str[1];
			log.info("  tableName  :: "+tableName+"   columnName  ::  "+columnName);
			
			
//			String value = BeanUtils.getMappedProperty(itemDetail, columnName);
			
			switch (tableName) {
			case "ItemDetail":
				
				try {
				
					String value = BeanUtils.getMappedProperty(itemDetail, columnName);
					
					log.info("  value  :: "+value);
				
					if(value != null && !value.toString().equals("")){
						sqlExpression = sqlExpression.replaceAll("<"+keyword+">", value.toString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;

			case "Signup":
				
				try {
				
					String value = BeanUtils.getMappedProperty(signup, columnName);
					
					log.info("  value  :: "+value);
				
					if(value != null && !value.toString().equals("")){
						sqlExpression = sqlExpression.replaceAll("<"+key+">", value.toString());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				
				break;
				
			default:
				break;
			}
			
		}else if(str.length == 3){
			
		}
		
		return value;
	}*/
	
	/*private Map<String, Object> getPromotionExpressionValues(Signup signup){
	
	Map<String, Object> valueMap = new HashMap<String, Object>();
	if(signup != null){
		//Account account = signup.getCustomer();
		User signUpContact = signup.getContact();
		ItemDetail signUpItemDetail = signup.getItemDetail();
		
		if(signUpContact != null){
			// Signing up contact id
			valueMap.put(Constants.PromotionExpressionKeyword_Signup_ContactId, signUpContact.getContactId());
		}
		
		if(signUpItemDetail != null){
			// Signing up item detail id
			valueMap.put(Constants.PromotionExpressionKeyword_Signup_ItemDetailId, signUpItemDetail.getId());
		}
	}
	return valueMap;
}*/
	
}
