package com.ymca.dao;

import java.util.List;

import com.ymca.model.ItemDetailDaysPricingRule;

public interface ItemDetailDaysPricingRuleDao extends GenericDao<ItemDetailDaysPricingRule, Long> {	
	
	List<ItemDetailDaysPricingRule> findByItemDetailId(Long id);
	List<ItemDetailDaysPricingRule> findByItemDetailIdAndPricingRule_Type(Long id, String type);
	List<ItemDetailDaysPricingRule> findByItemDetailIdAndItemDetailDaysAndPricingRule_Type(Long itemDetailid, Long itemDetailDaysid, String type);
	List<ItemDetailDaysPricingRule> findByItemDetailIdAndPricingRule_TypeAndPricingRule_ProductCategory(Long id, String type, String productCategory);
}
