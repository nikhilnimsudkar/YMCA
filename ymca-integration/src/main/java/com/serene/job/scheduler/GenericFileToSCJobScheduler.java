/**
 * @author gpatwa
 * Generic File to SC sync job template
 */
package com.serene.job.scheduler;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;

import com.serene.job.model.JobSchedulerMetadata;

public class GenericFileToSCJobScheduler extends GenericJobScheduler implements InitializingBean {

	private Logger log = LoggerFactory.getLogger(GenericSCToDbJobScheduler.class);
	
	@PostConstruct
	public void loadJob() {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
		ItemReader fileItemReader =  (ItemReader) applicationContext.getBean("fileItemReader");

		fusionItemWriter.setOperation("Merge");
		
		Step step = stepBuilderFactory.get("step")
		.chunk(jobSchedulerMetadata.getCommitSize())
		.reader((ItemReader) fileItemReader)
		.writer((ItemWriter) fusionItemWriter)
		.listener(itemListener)
		.build();
		
		job = jobBuilderFactory.get(jobSchedulerMetadata.getJobName())
        .incrementer(new RunIdIncrementer())
        .flow(step)
        .end()
        .build();
	}
	
}