package com.ymca.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.ymca.model.SystemProperty;

public interface SystemPropertyDao extends GenericDao<SystemProperty, Long>  {	
	
	@Query("select s from SystemProperty s")
	List<SystemProperty> getAllPicklistValues();	
	
	@Query("select s from SystemProperty s where s.picklistName=?1 and s.fieldStatus= true")
	List<SystemProperty> getByPicklistName(String picklistName);
	
	List<SystemProperty> getPropertyByKeyName(String keyName);
	
	List<SystemProperty> getPropertyByPageName(String pageName);
	
	List<SystemProperty> findByKeyNameIn(List<String> keyNameList);
	
	List<SystemProperty> findByPicklistNameAndFieldStatus(String picklistName, boolean fieldStatus);
	
	SystemProperty findPropertyByKeyName(String keyName);
}
