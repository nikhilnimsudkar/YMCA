/**
 * @author gpatwa
 * Generic job scheduler template
 */
package com.serene.job.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.common.Constants;
import com.serene.job.model.JobSchedulerMetadata;

@Service
@Lazy(true)
@Scope("prototype")
public class GenericJobScheduler extends AbstractJobScheduler implements InitializingBean {
	private Logger log =LoggerFactory.getLogger(GenericJobScheduler.class); 
	
	@Override
	public void run() {
		try {
			JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
			JobParametersBuilder parametersBuilder = new JobParametersBuilder();
			parametersBuilder.addLong("time",System.currentTimeMillis());
			parametersBuilder.addString(Constants.OBJECT_NAME,jobSchedulerMetadata.getFromObjectName());
			parametersBuilder.addString(Constants.OBJECT_ID_FIELD,jobSchedulerMetadata.getFromObjectIdField());
			
			JobExecution execution = jobLauncher.run(job, parametersBuilder.toJobParameters());
			
			log.info("Exit Status : " + execution.getStatus());
		} catch (Exception e) {
			log.error("Error while running the job " + jobName, e);
		}
	}
	

	/**
	 * @throws ClassNotFoundException 
	 * @throws BeansException 	
	 * 
	 */
	@SuppressWarnings("rawtypes")
	public void loadJob() throws Exception {

	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//Assert.notNull(jobSchedulerMetadata.getJobName(), "A job name is required");
	}	
}