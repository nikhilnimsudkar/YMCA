/**
 * @author gpatwa
 * This class is use to share the objects throughout the job.  The objects is is garbage collected once the job is completed
 */
package com.serene.job.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.serene.job.dao.ChildFieldMappingDao;
import com.serene.job.dao.ChildObjectDao;
import com.serene.job.dao.JobFieldMappingDao;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.ChildFieldMapping;
import com.serene.job.model.ChildObject;
import com.serene.job.model.FieldMapping;
import com.serene.job.model.JobSchedulerMetadata;

@Service
@JobScope
@Lazy(true)
public class BatchJobContext implements JobExecutionListener  {

	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao ;
	
	@Resource
	private ChildFieldMappingDao childFieldMappingDao ;
	
	@Resource
	private JobFieldMappingDao jobFieldMappingDao ; 
	
	@Resource 
	private ChildObjectDao childObjectDao;
	
	private JobSchedulerMetadata jobSchedulerMetadata ;
	
	private List<FieldMapping> fieldMappings ;
	
	private Map<String,ChildObject> childObjs ;
	
	private Map<String,List<ChildFieldMapping>> childFieldMappings  ;
	
	private  List<ChildObject> childs  ;

	private List<FieldMapping> callBackfieldMappings ;
	
	private Map<String,List<ChildFieldMapping>> callBackChildFieldMappings ;
	
	public JobSchedulerMetadata getJobSchedulerMetadata() {
		return jobSchedulerMetadata;
	}
	public void setJobSchedulerMetadata(JobSchedulerMetadata jobSchedulerMetadata) {
		this.jobSchedulerMetadata = jobSchedulerMetadata;
	}
	public List<FieldMapping> getFieldMappings() {
		return fieldMappings;
	}
	public void setFieldMappings(List<FieldMapping> fieldMappings) {
		this.fieldMappings = fieldMappings;
	}
	public Map<String, List<ChildFieldMapping>> getChildFieldMappings() {
		return childFieldMappings;
	}
	public void setChildFieldMappings(Map<String, List<ChildFieldMapping>> childFieldMappings) {
		this.childFieldMappings = childFieldMappings;
	}
	
	public Map<String, ChildObject> getChildObjs() {
		return childObjs;
	}
	public void setChildObjs(Map<String, ChildObject> childObjs) {
		this.childObjs = childObjs;
	}
	
	public List<ChildObject> getChilds() {
		return childs;
	}
	public void setChilds(List<ChildObject> childs) {
		this.childs = childs;
	}
	
	public List<FieldMapping> getCallBackfieldMappings() {
		return callBackfieldMappings;
	}
	public void setCallBackfieldMappings(List<FieldMapping> callBackfieldMappings) {
		this.callBackfieldMappings = callBackfieldMappings;
	}
	
	public Map<String,List<ChildFieldMapping>> getCallBackChildFieldMappings() {
		return callBackChildFieldMappings;
	}
	public void setCallBackChildFieldMappings(Map<String,List<ChildFieldMapping>> callBackChildFieldMappings) {
		this.callBackChildFieldMappings = callBackChildFieldMappings;
	}
	@Override
	@Transactional
	public void beforeJob(JobExecution jobExecution) {
		jobSchedulerMetadata = jobSchedulerMetadataDao.getOne(jobExecution.getJobInstance().getJobName());
		fieldMappings = jobFieldMappingDao.findByJobNameAndStatus(jobSchedulerMetadata.getJobName(), true);

		//
		callBackfieldMappings = jobFieldMappingDao.findByJobNameAndCallbackUpdateAndStatus(jobSchedulerMetadata.getJobName(), true,true);
		childs  = childObjectDao.findByJobNameAndActive(jobSchedulerMetadata.getJobName(), true);
		if (!childs.isEmpty()) {
			childFieldMappings = new HashMap<String,List<ChildFieldMapping>>();
			callBackChildFieldMappings = new HashMap<String,List<ChildFieldMapping>>();
			childObjs  = new HashMap<String,ChildObject>();
			for (ChildObject childObject :childs ) {
				List<ChildFieldMapping> fieldMappings = childFieldMappingDao.findByJobNameAndObjectNameAndStatus(jobSchedulerMetadata.getJobName(),childObject.getObjectName(), true);
				childFieldMappings.put(childObject.getObjectName(), fieldMappings);
				childObjs.put(childObject.getObjectName(), childObject);
				callBackChildFieldMappings.put(childObject.getObjectName(),childFieldMappingDao.findByJobNameAndObjectNameAndCallbackUpdateAndStatus(jobSchedulerMetadata.getJobName(),childObject.getObjectName(),true, true));
			}
		}
	}
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		
	}
}
