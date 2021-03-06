/**
 * @author gpatwa
 * This is the abstract fusion writer class. The write does basic write operation and calls callback writer if it is enable in the Job scheduler metadata
 * The Abstract Fusion writer does not check for any duplicate record check. 
 * In order to perform correct create and update operation fusion Id should be present in the payload
 *  
 */
package com.serene.job.writer.sc;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.serene.job.writer.FusionItemWriter;

@Service
@Lazy(false)
public class AccountFusionItemWriter extends FusionItemWriter implements ItemWriter<Map<String,Object>> {

	

	private static Logger log = LoggerFactory.getLogger(AccountFusionItemWriter.class); 
	
	@SuppressWarnings("unchecked")
	@Override
	public void write(List<? extends Map<String, Object>> items) throws Exception {
		
		for (Map<String, Object> item : items) {
			try {
				setOperationTypes("ALL","CRATE","UPDATE");
				List<String> operationTypes = getOperationTypes();
				Map<String,Object> payload = getPayload(item);
				Map<String,Object> response = fusionWebService.merge(payload, batchJobContext.getJobSchedulerMetadata().getToObjectName());
				
				updateResponse(item, operationTypes, response);
				
			} catch (Exception e) {
				log.error("Error while writing data ",e);
				// TODO dirty implement ideally it should throw an error and call the listener 
				logErrorInDb(item, e);

			}
		}
	}
}