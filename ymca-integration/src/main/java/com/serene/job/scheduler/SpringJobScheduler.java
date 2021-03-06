/**
 * @author gpatwa
 */
package com.serene.job.scheduler;

import java.util.concurrent.ScheduledFuture;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.JobSchedulerMetadata;


@Service
@Lazy(false)
@Scope("prototype")
public class SpringJobScheduler {

	private static Logger log = LoggerFactory.getLogger(SpringJobScheduler.class);
	
	@Resource
	protected ApplicationContext applicationContext;
	
/*	@Resource 
	private ThreadPoolTaskScheduler threadPoolTaskScheduler ;*/
	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao ;

	//@Resource
	//private ThreadPoolTaskExecutor threadPoolTaskExecutor ;
	
	public void initializeJob(final AbstractJobScheduler jobScheduler){
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobScheduler.getJobName());
		ThreadPoolTaskScheduler threadPoolTaskScheduler = (ThreadPoolTaskScheduler) applicationContext.getBean(jobSchedulerMetadata.getJobGroup());
		threadPoolTaskScheduler.setThreadGroupName(jobSchedulerMetadata.getJobGroup());
		threadPoolTaskScheduler.setThreadNamePrefix(jobSchedulerMetadata.getJobName()+"-");
		threadPoolTaskScheduler.setThreadNamePrefix(jobSchedulerMetadata.getJobGroup()+"-");
		threadPoolTaskScheduler.setPoolSize(1);
		CronTrigger trigger = new CronTrigger(jobSchedulerMetadata.getCronExpression());
		JobThread task = new JobThread(jobScheduler);
		task.setName(jobSchedulerMetadata.getJobName());
		/*	    Runnable task = new Runnable() {
	        public void run() {
	        	jobScheduler.runJob();
	        }
	     };*/
		ScheduledFuture scedulefuture=  threadPoolTaskScheduler.schedule(task, trigger);
		// threadPoolTaskScheduler.initialize();
	}
}

class JobThread extends Thread {
	
	private static Logger log = LoggerFactory.getLogger(SpringJobScheduler.class);
	
	private AbstractJobScheduler jobScheduler ;
	
	public JobThread(String groupName,String threadName){
		
	}
	
	public JobThread(AbstractJobScheduler jobScheduler2) {
		super(jobScheduler2.getJobName());
		this.jobScheduler = jobScheduler2;
	}

	@Override
	public void run()  {
		try {
			//jobScheduler.runJob();
		} catch (Exception e) {
			log.error(" Error while running the job " + jobScheduler.getJobName(),e);
		}
	}
}  