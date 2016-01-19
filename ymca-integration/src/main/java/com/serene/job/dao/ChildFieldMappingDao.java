/**
 * @author gpatwa
 * Generic DAO for childFieldMapping object
 */
package com.serene.job.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.serene.job.model.ChildFieldMapping;


@Repository
public interface ChildFieldMappingDao  extends GenericDao<ChildFieldMapping,Long> {    

	
	public List<ChildFieldMapping> findByJobNameAndObjectNameAndStatus(String jobName,String objectName,Boolean status);
	
	public List<ChildFieldMapping> findByJobNameAndObjectNameAndCallbackUpdateAndStatus(String jobName,String objectName,Boolean callbackUpdate,Boolean status);
	
	public List<ChildFieldMapping> findByJobNameAndStatus(String jobName,Boolean status);
	
	public List<ChildFieldMapping> findByJobNameAndStatusAndOperationTypeIn(String jobName,Boolean status,List<String> operationType);
	
	public List<ChildFieldMapping> findByJobNameAndStatusAndToFieldAndOperationTypeIn(String jobName,Boolean status,String fromField,List<String> operationType);
}