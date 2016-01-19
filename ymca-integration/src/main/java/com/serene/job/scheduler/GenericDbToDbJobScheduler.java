/**
 * @author gpatwa
 * This class dynamically initialize the database to Sales Cloud Integration
 * The class will create new Spring Job and Spring Scheduler beans based on the Job Scheduler metadata 
 * The loadJob method is called to set job parameter
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
public class GenericDbToDbJobScheduler extends GenericJobScheduler implements InitializingBean {

	/**
	 * @throws ClassNotFoundException 
	 * @throws BeansException 	
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void loadJob() throws Exception {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
		job = customJobBuilderFactory.buildDbToDbJob(jobSchedulerMetadata, job);
		customJobBuilderFactory.loadScheduler(jobSchedulerMetadata, this);
	}
}