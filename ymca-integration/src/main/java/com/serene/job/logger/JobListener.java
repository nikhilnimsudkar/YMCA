/**
 * @author gpatwa
 * This class is used to JobContext data before the job start and clean up job context data after job is completed
 */
package com.serene.job.logger;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(final JobExecution jobExecution) {
        // do some work with the jobExecution
    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        // do some work with the jobExecution
    }
}