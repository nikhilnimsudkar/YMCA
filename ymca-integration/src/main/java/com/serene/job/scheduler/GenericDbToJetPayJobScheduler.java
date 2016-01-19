package com.serene.job.scheduler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.model.JobSchedulerMetadata;

@Service
@Lazy(true)
@Scope("prototype")
public class GenericDbToJetPayJobScheduler extends GenericJobScheduler implements InitializingBean {
	
	/*
	 * (non-Javadoc)
	 * @see com.serene.job.scheduler.GenericJobScheduler#loadJob()
	 * @author: Lavy Toteza
	 */
	
	@SuppressWarnings("rawtypes")
	public void loadJob() throws Exception {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
		job = customJobBuilderFactory.buildDbToJetPayJob(jobSchedulerMetadata, job);
		customJobBuilderFactory.loadScheduler(jobSchedulerMetadata, this);
	}

}
