package com.ymca.dao;

import java.util.List;

import com.ymca.model.ItemDetailAssociatedItemDetail;

public interface ItemDetailAssociatedItemDetailDao extends GenericDao<ItemDetailAssociatedItemDetail, Long>  {
	
	List<ItemDetailAssociatedItemDetail> findAssociatedItemDetailTypeByItemDetailIdInAndTypeInAndStatusOrderByActivityPriorityAsc(List<Long> itemDetailId, List<String> type, String status);
}
