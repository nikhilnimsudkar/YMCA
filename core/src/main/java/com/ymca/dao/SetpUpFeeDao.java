package com.ymca.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.SetpUpFee;
import com.ymca.model.User;

public interface SetpUpFeeDao extends GenericDao<SetpUpFee,Long>{
	
	@Query("select s from SetpUpFee s where s.contact =?1 and DATE(s.setUpFeeEndDate)>=DATE(?2) and s.type=?3")
	List<SetpUpFee> findByContactIfActiveSetupFee(User user, Date itemStartDate, String type );
	
	@Query("select s from SetpUpFee s where s.contact =?1 and (DATE(s.setUpFeeStartDate) between DATE(?2) AND DATE(?3)) and (DATE(s.setUpFeeEndDate) between DATE(?2) AND DATE(?3)) and s.type=?4")
	List<SetpUpFee> findByContactIfActiveRegistrationFee(User user, Date itemStartDate, Date itemEndDate, String type );
}
