/**
 * @author gpatwa
 */
package com.serene.job.writer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.common.ResultsetToHashMap;
import com.serene.job.model.ChildObject;
import com.serene.job.model.FieldMapping;
import com.serene.ws.service.FusionWebService;

@Service
@Scope("prototype")
public class FusionCallbackWriter {
	

	private static Logger log = LoggerFactory.getLogger(FusionCallbackWriter.class);

	@Resource
	private JdbcTemplate jdbcTemplate;
	

	@Resource 
	private FusionWebService fusionWebService ;
	
	@Resource
	private BatchJobContext batchJobContext ;  
	
	@Resource
	private ResultsetToHashMap resultsetToHashMap ; 
	
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	public void updateResponse(List<Map<String,Object>> requests,Map<String,Object> responseData,List<String> operationTypes) throws Exception {
		for (Map<String,Object> request : requests) {
			try {
				updateResponse(request,responseData,operationTypes) ;
			} catch(Exception ex) {
				log.error(" Error while updating the response back ",ex);
			}
		}
	}

	/**
	 * Current limitation, the to field id should be same as column name in the table 
	 * @param requestData
	 * @param responseData
	 * @param operationTypes
	 * @throws Exception
	 */
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateResponse(Map<String,Object> requestData,Map<String,Object> responseData,List<String> operationTypes) throws Exception {
		List<FieldMapping> fieldMappings =  batchJobContext.getCallBackfieldMappings();
		String sql = "select * from " + batchJobContext.getJobSchedulerMetadata().getToObjectName() + " where "  + batchJobContext.getJobSchedulerMetadata().getFromObjectIdField() + " =  " + requestData.get(batchJobContext.getJobSchedulerMetadata().getFromObjectIdField()) +   " limit 1";
		List<Map<String,Object>> respone =  jdbcTemplate.query(sql, resultsetToHashMap);
		if (!respone.isEmpty()) {
			log.error(" Unable to find response for id "  + batchJobContext.getJobSchedulerMetadata().getFromObjectIdField() + " value " + requestData.get(batchJobContext.getJobSchedulerMetadata().getFromObjectIdField()));
			return ;
		}
		Map<String,Object> out = new HashMap<String, Object>();
		for(FieldMapping mapping:  fieldMappings) {
			if (mapping.getCallbackUpdate()) {
				out.put(mapping.getFromField(), respone.get(0).get(mapping.getToField()));
			}
		}
		fusionWebService.update(out, batchJobContext.getJobSchedulerMetadata().getFromObjectName());
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateChildResponse(ChildObject childObject,Map<String,Object> requestData,Map<String,Object> responseData,List<String> operationTypes) throws Exception {
		// TODO need to implements for child objects too 
	
	}
}
