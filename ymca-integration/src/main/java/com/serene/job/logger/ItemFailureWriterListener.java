/**
 * @author gpatwa
 *  Item failed writer called when is an error in Item writer
 */
package com.serene.job.logger;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class ItemFailureWriterListener implements ItemWriteListener<Map<String,Object>> ,StepExecutionListener {
	private Logger log = LoggerFactory.getLogger(ItemFailureWriterListener.class); 
	
	private StepExecution stepExecution ;
	
	@Resource
	private ApplicationContext applicationContext ; 
	
	@Override
	public void beforeWrite(List<? extends Map<String, Object>> items) {
		log.info(" data ",items);
	}

	@Override
	public void afterWrite(List<? extends Map<String, Object>> items) {
	}

	@Override
	public void onWriteError(Exception exception,List<? extends Map<String, Object>> items) {
		log.error("error ", exception.getMessage());
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
