package com.ymca.dao;

import com.ymca.model.PricingRule;


public interface PricingRuleDao extends GenericDao<PricingRule, Long> {	
	
	//List<PricingRule> findByItemDetailIdAndTier(Long id, String tier);  
    
    //@Query("select pr from SignupPricingRule spr inner join spr.pricingRule pr where spr.signup = ?1 and pr.type = ?2")
    //List<PricingRule> findBySignupAndType(Signup signup, String type);
    
}