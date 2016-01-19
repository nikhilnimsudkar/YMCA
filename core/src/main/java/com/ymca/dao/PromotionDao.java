package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Promotion;

public interface PromotionDao extends GenericDao<Promotion, Long> {
	@Query("select p from Promotion p where p.status='ACTIVE' and p.autoApply=1 and DATE(p.startdate)<=DATE(now()) and DATE(p.enddate)>=DATE(now())")
	List<Promotion> getACPromoDiscountByItemDetailsId();
	
	@Query("select p from Promotion p inner join p.itemPromoDetails ipd where p.status='ACTIVE' and p.autoApply=0 and DATE(p.startdate)<=DATE(now()) and DATE(p.enddate)>=DATE(now()) and p.promoCode=?1")
	Promotion getPromoDiscountByPromocode(String promocode);
	
	//@Query("select p from ItemDetail ipd inner join ipd.promocode p inner join ipd.itemDetail id where id.id=?1 and p.status='ACTIVE' and p.autoApply=1 and DATE(p.startdate)<=DATE(now()) and DATE(p.enddate)>=DATE(now())")
	//Promotion getDefaultPromocode(Long itemdetailsId);
}
