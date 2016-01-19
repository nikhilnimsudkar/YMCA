/**
 * @author gpatwa
 * Generic DAO for JobSchedulerMetadataDao object
 */
package com.serene.job.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.serene.job.model.JobSchedulerMetadata;


@Repository
public interface JobSchedulerMetadataDao  extends GenericDao<JobSchedulerMetadata,String> {    

	public List<JobSchedulerMetadata> findByOrderByJobGroupAscSequenceAsc(); 
	
	public List<JobSchedulerMetadata> findByJobName(String jobName);
	
}