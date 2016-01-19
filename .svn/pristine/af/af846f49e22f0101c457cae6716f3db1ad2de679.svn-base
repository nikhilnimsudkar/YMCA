package com.ymca.dao;

import java.util.List;

import com.ymca.model.ItemDetailPricingRule;


public interface ItemDetailPricingRuleDao extends GenericDao<ItemDetailPricingRule, Long> {	
	List<ItemDetailPricingRule> findByItemDetailIdAndStatusAndPricingRule_StatusOrderByPricingRule_ToAgeAsc(Long id, String status, String pricingRuleStatus);
	List<ItemDetailPricingRule> findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Tier(Long id, String status, String pricingRuleStatus, String tier);
	List<ItemDetailPricingRule> findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(Long id, String status, String pricingRuleStatus, String type);
	List<ItemDetailPricingRule> findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_TypeAndPricingRule_ProductCategory(Long id, String status, String pricingRuleStatus, String type, String productCategory);
	List<ItemDetailPricingRule> findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_ProductCategory(Long id, String status, String pricingRuleStatus, String productCategory);
}
