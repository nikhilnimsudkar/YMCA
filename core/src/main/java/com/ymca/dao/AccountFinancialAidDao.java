package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.AccountFinancialAid;

public interface AccountFinancialAidDao extends GenericDao<AccountFinancialAid, String>  {
	
		
	@Query("select c from AccountFinancialAid c where c.account_id =?1 and c.productCategory_c =?2")
	List<AccountFinancialAid> getBySalesAccountIdAndProductCategory(Long customerId,String category);
	
	
}
