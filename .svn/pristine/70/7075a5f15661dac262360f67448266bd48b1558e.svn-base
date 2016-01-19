/**
 * @author gpatwa
 * Generic item listener triggered when ITem reader and item writer is called 
 * This class is used for logging purpose in the various steps 
 */
package com.serene.job.logger;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.serene.job.common.BatchJobContext;
import com.serene.job.dao.IntermidiateTableDao;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.util.JobUtils;

@Component
@JobScope
public class ItemListener implements  ItemWriteListener<Map<String,Object>>,ItemReadListener<Map<String,Object>>,  StepExecutionListener  {

	private Logger log = LoggerFactory.getLogger(ItemFailureWriterListener.class);
	private StepExecution stepExecution ;
	
	@Resource
	private IntermidiateTableDao intermidiateTableDao ; 

	@Resource 
	private JobUtils jobUtils ;  
	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao ;
	

	@Resource
	private ApplicationContext applicationContext;

	@Resource
	private BatchJobContext batchJobContext ;  
	
	
	@Override
	public void beforeRead() {
		
	}

	@Override
	@Transactional(value=TxType.REQUIRES_NEW)
	public void afterRead(Map<String, Object> item) {
		String jobName = batchJobContext.getJobSchedulerMetadata().getJobName();
		JobSchedulerMetadata jobSchedulerMetadata =  jobSchedulerMetadataDao.findOne(jobName);
		Object lastUpdatedTime = item.get(jobSchedulerMetadata.getInterfaceLastUpdatedField());
		if (lastUpdatedTime != null) jobSchedulerMetadata.setInterfaceLastPoolTime(lastUpdatedTime.toString());
		else jobSchedulerMetadata.setInterfaceLastPoolTime(null);
		jobSchedulerMetadataDao.save(jobSchedulerMetadata);
	}
	
	@Override
	public void onReadError(Exception ex) {
		log.error("Error while reading item",ex);
	}
	
	@Override
	public void beforeWrite(List<? extends Map<String, Object>> items) {
		String jobName = batchJobContext.getJobSchedulerMetadata().getJobName();
		JobSchedulerMetadata jobSchedulerMetadata =  jobSchedulerMetadataDao.findOne(jobName);
		String objectName = jobSchedulerMetadata.getFromObjectName();
		String objectIdField = jobSchedulerMetadata.getFromObjectIdField();
		for (Map<String,Object> item : items) {
			try {
				jobUtils.logItem(jobName, objectName, objectIdField, item);
			} catch (Exception e) {
				log.error("Error while loggging item in the intermediate table",e);
			}
		}
	}

	@Override
	public void afterWrite(List<? extends Map<String, Object>> items) {
	
	}

	@Override
	@Transactional(value=TxType.REQUIRES_NEW)
	public void onWriteError(Exception exception,List<? extends Map<String, Object>> items) {
		String jobName = batchJobContext.getJobSchedulerMetadata().getJobName();
		JobSchedulerMetadata jobSchedulerMetadata =  jobSchedulerMetadataDao.findOne(jobName);
		String objectName = jobSchedulerMetadata.getFromObjectName();
		String objectIdField = jobSchedulerMetadata.getFromObjectIdField();
		for (Map<String,Object> item : items) {
			try {
				jobUtils.logItemStatus(exception, jobName, objectName, objectIdField, item);
			} catch (Exception e) {
				log.error("Error while loggging item error status in the intermediate table",e);
			}
		}
		log.error("Error while write operation in the writer ", exception);
	}

	@Override
	public void beforeStep(StepExecution stepExecution) {
		this.stepExecution =stepExecution;
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
