package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.ItemDetailPromotion;

public interface ItemDetailPromotionDao extends GenericDao<ItemDetailPromotion, Long> {

	@Query("select ipd  from ItemDetailPromotion ipd inner join ipd.promotion p inner join ipd.itemDetail id where id.id=?1 and p.status='ACTIVE' and p.autoApply=1 and DATE(p.startdate)<=DATE(now()) and DATE(p.enddate)>=DATE(now())")
	List<ItemDetailPromotion> getAutoApplyPromoDiscount(Long itemDetailsId);
}
