/**
 * @author gpatwa
 * Generic DAO for JobFieldMappingDao object
 */
package com.serene.job.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.serene.job.model.FieldMapping;


@Repository
public interface JobFieldMappingDao  extends GenericDao<FieldMapping,Long> {    

	public List<FieldMapping> findByJobNameAndStatus(String jobName,Boolean status);
	
	public List<FieldMapping> findByJobNameAndCallbackUpdateAndStatus(String jobName,Boolean callBackUpdate,Boolean status);
	
	public List<FieldMapping> findByJobNameAndStatusAndOperationTypeIn(String jobName,Boolean status,List<String> operationType);
	
	public List<FieldMapping> findByJobNameAndStatusAndToFieldAndOperationTypeIn(String jobName,Boolean status,String fromField,List<String> operationType);
	
	public List<FieldMapping> findByJobNameAndStatusOrderByFieldOrderAsc(String jobName,Boolean status);
}