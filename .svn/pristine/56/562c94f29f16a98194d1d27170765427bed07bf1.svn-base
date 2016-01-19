/**
 * @author gpatwa
 * Custom Item reader for the fusion web service
 *  
 */
package com.serene.job.reader;

import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.common.BaseService;
import com.serene.job.common.BatchJobContext;
import com.serene.job.common.Constants;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.ws.exception.FQLException;
import com.serene.ws.exception.WebServiceException;

@Service
@Lazy(true)
@Scope("prototype")
public class FusionItemReader extends BaseService implements ItemReader<Map<String,Object>> ,StepExecutionListener {

	private Logger log = LoggerFactory.getLogger(FusionItemReader.class);
	private String fql ; 
	private Integer offset = 0 ;
	
	private Queue<Map<String,Object>> items ;
	
	@Resource
	private BatchJobContext  batchJobContext ;
	
	@Override
	public Map<String,Object> read() throws Exception {
		if (items.isEmpty()) {
			validateInput();
			fetchData();
		}
		return items.poll();
	}

	private void validateInput() throws Exception {
		if (StringUtils.isBlank(fql)) {
			throw new Exception(" Fql cannot be blank");
		}
	}

	// TODO remove try catch and throw error so that readOnError method is invoked
	private void fetchData() throws WebServiceException, FQLException {
		String appendPaging = " limit  " + Constants.FUSION_FETCH_SIZE + " offset " +  offset ;
		
		try {
			Map<String, Object> newItems =  fusionWebService.query(fql + appendPaging);
			if (!newItems.isEmpty()) {
				Object response =  newItems.get("result");
				if (response instanceof AbstractMap) {
					Object data = ((Map<String, Object>) response).get("Value");
					if (data == null) {
						items.add((Map<String, Object>) response);
					} else if (data instanceof AbstractMap) {
						items.add((Map<String, Object>) data);
					} else if (((Map<String, Object>) response).get("Value")  instanceof AbstractList){
						List<Map<String,Object>> payload = (List<Map<String,Object>> )data;
						for (Map<String, Object> map : payload) {
							if (map.get("Value") instanceof AbstractList) {
								List<Map<String, Object>> values = (List) map.get("Value");
								for (Map<String, Object> value : values) {
									items.add(value);	
								}
							} else {
								items.add(map);
							}
						}
					}
				} else if (response  instanceof AbstractList){
					List<Map<String,Object>> payload = (List<Map<String,Object>> )response;
					for (Map<String, Object> map : payload) {
						if (map.get("Value") instanceof AbstractList) {
							List<Map<String, Object>> values = (List) map.get("Value");
							for (Map<String, Object> value : values) {
								items.add(value);	
							}
						} else {
							items.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error while reading item",e);
		}
		offset = offset+ Constants.FUSION_FETCH_SIZE;
	}

	public void setFql(String fql) {
		this.fql = fql;
	}

	@Override
	@Transactional
	public void beforeStep(StepExecution stepExecution) {
        JobSchedulerMetadata jobSchedulerMetadata = batchJobContext.getJobSchedulerMetadata();
        fql = jobSchedulerMetadata.getFromQuery();
       	if (StringUtils.contains(fql,Constants.LAST_UPDATED_DATE)) {
			String startTime = jobSchedulerMetadata.getInterfaceLastPoolTime();
			//startTime = null ;
			if (StringUtils.isNoneBlank(startTime)) {
				fql = fql.replaceAll(Constants.LAST_UPDATED_DATE, startTime);
			} else {
				fql = fql.replaceAll(Constants.LAST_UPDATED_DATE, "1800-01-11 12:08:01.0");
			}
       	}
       	offset = 0 ;
       	items = new LinkedBlockingQueue<Map<String,Object>>();
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// cleanup resource
		items.clear();
		return null;
	}
}