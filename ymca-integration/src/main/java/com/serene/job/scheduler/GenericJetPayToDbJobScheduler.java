package com.serene.job.scheduler;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.model.JobSchedulerMetadata;

@Service
@Lazy(true)
@Scope("prototype")
public class GenericJetPayToDbJobScheduler extends GenericJobScheduler implements InitializingBean  {
	/*
	 * @author: Lavy Toteza
	 */
	
	@SuppressWarnings("rawtypes")
	public void loadJob() throws Exception {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
		job = customJobBuilderFactory.buildJetPayToDbJob(jobSchedulerMetadata, job);
		customJobBuilderFactory.loadScheduler(jobSchedulerMetadata, this);
	}
}
