package com.ymca.dao;

import com.ymca.model.ContactPromotion;
import com.ymca.model.ItemDetail;
import com.ymca.model.User;

public interface ContactPromotionDao extends GenericDao<ContactPromotion, Long>  {
	
	ContactPromotion findByItemDetailAndContactAndStatus(ItemDetail itemDetail, User contact, String status);
	
}
