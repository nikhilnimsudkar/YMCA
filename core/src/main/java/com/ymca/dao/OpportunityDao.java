package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Opportunity;

public interface OpportunityDao extends GenericDao<Opportunity,Long>{
	
	@Query("select o from Opportunity o where o.type = ?1 and o.customerAccountId_c = ?2 and o.salesStage <> 'Closed - Won' and o.salesStage <> 'Closed - Lost'")	
	List<Opportunity> getByTypeAndStage(String type, String customerAccountId_c);	
}
