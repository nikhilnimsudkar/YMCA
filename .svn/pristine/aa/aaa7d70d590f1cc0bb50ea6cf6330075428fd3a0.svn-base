package com.ymca.web.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.SetpUpFeeDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.UserDao;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.SetpUpFee;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.util.Constants;

@Service 
public class FeeCalculationService {
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private SetpUpFeeDao setpUpFeeDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	public Double getSetupFeeByItemDetailIdAndPartyId(String itemDetailId, String partyId){
		Double setupFee = 0D;
		
		if(itemDetailId!=null && StringUtils.isNotBlank(itemDetailId) && partyId!=null && StringUtils.isNotBlank(partyId)){
			ItemDetail itemDetail = itemDetailsDao.getById(Long.parseLong(itemDetailId));
			
			if(itemDetail!=null && StringUtils.isNotBlank(itemDetail.getRequiresSetUpFee()) && itemDetail.getRequiresSetUpFee().equalsIgnoreCase("Yes")){
				User contact = userDao.getOne(Long.parseLong(partyId));
				if(contact!=null){
					
					List<Signup> signup = signupDao.findByContactAndTypeAndStatus(contact,Constants.PRODUCT_CATEGORY_MEMBERSHIP,SignupStatusEnum.Active.toString());
					//if(contact.isActive()){
					if(signup!=null && signup.size()>0){
						// do nothing
					}
					else{
						List<SetpUpFee> setpUpFees = setpUpFeeDao.findByContactIfActiveSetupFee(contact,itemDetail.getStartDate(),Constants.SETUPFEE);
						if(setpUpFees!=null && setpUpFees.size()>0){
							// do nothing
						}
						else{
							// calculate setup fee
							List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(itemDetail.getId(), Constants.Status_Active, Constants.Status_Active,Constants.SETUPFEE);
							
							if(pricingRuleLst!=null && pricingRuleLst.size()>0){
								for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
									if(pricingRule!=null && pricingRule.getPricingRule()!=null){
										
										if(isMember(contact.getContactId().toString())){
											if(pricingRule.getPricingRule().getTierPrice() != null)
												setupFee += pricingRule.getPricingRule().getTierPrice();
										} else {
											if(pricingRule.getPricingRule().getNonmemberTierPrice() != null)
												setupFee += pricingRule.getPricingRule().getNonmemberTierPrice();
										}
									}
								}
								SetpUpFee s = new SetpUpFee();
								s.setContact(contact);
								s.setSetUpFee(setupFee);
								s.setSetUpFeeStartDate(new Date());
								
								Calendar cal = Calendar.getInstance();    		
					    		cal.add(Calendar.YEAR, 1); // to get previous year add -1
							    Date nextYear = cal.getTime();
								s.setSetUpFeeEndDate(nextYear);
								
								s.setType(Constants.SETUPFEE);
								
								//setpUpFeeDao.save(s);
							}
						}
					}
				}
			}
		}
		return setupFee;
	}
	
	public Double getRegistrationFeeByItemDetailIdAndPartyId(String itemDetailId, String partyId) {
		Double registrationFee = 0D;
		
		if(itemDetailId!=null && StringUtils.isNotBlank(itemDetailId) && partyId!=null && StringUtils.isNotBlank(partyId)){
			ItemDetail itemDetail = itemDetailsDao.getById(Long.parseLong(itemDetailId));
			
			if(itemDetail!=null && itemDetail.isRequiresRegistrationFee()!=null && itemDetail.isRequiresRegistrationFee()){
				User contact = userDao.getOne(Long.parseLong(partyId));
				if(contact!=null){
					
						List<SetpUpFee> registrationFees = setpUpFeeDao.findByContactIfActiveRegistrationFee(contact,itemDetail.getStartDate(),itemDetail.getEndDate(),Constants.REGISTRATION);
						if(registrationFees!=null && registrationFees.size()>0){
							// do nothing
						}
						else{
							// calculate setup fee
							List<ItemDetailPricingRule> pricingRuleLst =  itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(itemDetail.getId(), Constants.Status_Active, Constants.Status_Active, Constants.REGISTRATION);
							
							if(pricingRuleLst!=null && pricingRuleLst.size()>0){
								for(ItemDetailPricingRule pricingRule: pricingRuleLst) {
									if(pricingRule!=null && pricingRule.getPricingRule()!=null){
										
										if(isMember(contact.getContactId().toString())){
											if(pricingRule.getPricingRule().getTierPrice() != null)
												registrationFee += pricingRule.getPricingRule().getTierPrice();
										} else {
											if(pricingRule.getPricingRule().getNonmemberTierPrice() != null)
												registrationFee += pricingRule.getPricingRule().getNonmemberTierPrice();
										}
									}
								}
								SetpUpFee s = new SetpUpFee();
								s.setContact(contact);
								s.setSetUpFee(registrationFee);
								s.setSetUpFeeStartDate(new Date());
								s.setSetUpFeeEndDate(new Date());
								
								s.setType(Constants.REGISTRATION);
								//setpUpFeeDao.save(s);
							}
						}
				}
			}
		}
		return registrationFee;
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
}
