/**
 * @author gpatwa
 * Generic SC to DB scheduler
 */
package com.serene.job.scheduler;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.model.JobSchedulerMetadata;

@Service
@Lazy(true)
@Scope("prototype")
public class GenericSCToDbJobScheduler extends GenericJobScheduler implements InitializingBean  {
	
	
	/**
	 * @throws ClassNotFoundException 
	 * @throws BeansException 	
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void loadJob() throws Exception {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
		job = customJobBuilderFactory.buildScToScbJob(jobSchedulerMetadata, job);
		customJobBuilderFactory.loadScheduler(jobSchedulerMetadata, this);
	}
}