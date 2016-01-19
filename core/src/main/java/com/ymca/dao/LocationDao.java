package com.ymca.dao;

import java.util.Date;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.Query;

import com.ymca.model.Location;

public interface LocationDao  extends GenericDao<Location, Long>{
	List<Location> getLocationsByArea(String area_c);	
	
	@Query("select l from Location l where l.id=?1")	
	Location getLocationsByLocationId(Long locationId);
	
	List<Location> findByBranch(String branchName);
	List<Location> findByRecordName(String recordName);
	
	@Cacheable("ymca-core")
	List<Location> findAll();
	
	List<Location> findByStatusOrderByRecordNameAsc(String status);
	
	@Cacheable("ymca-core")
	@Query("select l from Location l order by l.recordName asc")
	List<Location> findAllLocation();
	 
	@Query("select l.id, l.recordName, i.id from ItemDetail i inner join i.location l where DATE(i.startDate) >= DATE(?1) and DATE(i.endDate) <= DATE(?2) and l.status = 'Active' and i.type = 'Donation' and i.category = 'Annual Campaign' order by l.recordName asc")
	//and l.status = 'Active' and i.type = 'Donation' and i.category = 'Annual Campaign' order by l.recordName asc 
	List<Object> findByItemDetailAndStatusOrderByRecordNameAsc(Date startDate, Date endDate);
}