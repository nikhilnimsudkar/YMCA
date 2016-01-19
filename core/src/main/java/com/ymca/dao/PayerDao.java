package com.ymca.dao;

import java.util.List;

import com.ymca.model.Payer;
import com.ymca.model.Signup;

public interface PayerDao extends GenericDao<Payer, Long> {
	List<Payer> findBySignupAndType(Signup signup, String type);
	
	//@Query("select p from payer p where p.scId=?1")
	Payer findByScId(String scId);
	List<Payer> findBySignup(Signup signup);
}
