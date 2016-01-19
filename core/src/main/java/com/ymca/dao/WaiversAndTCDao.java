package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.WaiversAndTC;

public interface WaiversAndTCDao extends GenericDao<WaiversAndTC, Long>  {
	List<WaiversAndTC> getTcByType(String type);	
	
	@Query("select w from WaiversAndTC w where w.type=?1 and DATE(w.startDate)<=DATE(now()) and DATE(w.endDate)>=DATE(now())")
	List<WaiversAndTC> getTcByTypeAndDate(String type);
}