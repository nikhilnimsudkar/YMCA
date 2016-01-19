package com.ymca.dao;

import java.util.List;

import com.ymca.model.Signup;
import com.ymca.model.SignupPricingRule;


public interface SignupPricingRuleDao extends GenericDao<SignupPricingRule, Long> {	
	List<SignupPricingRule> findBySignup(Signup signup);
}
