/**
 * @author gpatwa
 */
package com.serene.job.writer;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.jxpath.JXPathContext;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.model.ChildFieldMapping;
import com.serene.job.model.ChildObject;
import com.serene.job.model.FieldMapping;

@Service
@Scope("prototype")
public class DbCallbackWriter {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private BatchJobContext batchJobContext ;
	
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate.setDataSource(dataSource);
	}

	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateResponse(Map<String,Object> requestData,Map<String,Object> responseData,List<String> operationTypes) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder("Update ");
		sqlBuilder.append(batchJobContext.getJobSchedulerMetadata().getFromObjectName()).append(" Set ");
		JXPathContext fieldMappingsContext = JXPathContext.newContext(responseData);
		fieldMappingsContext.setLenient(true);
		
		List<FieldMapping> fieldMappings = batchJobContext.getCallBackfieldMappings(); 
		for(FieldMapping mapping:  fieldMappings) {
			//if (mapping.getCallbackUpdate()) {
				sqlBuilder.append(" ").append(mapping.getFromField()).append(" = ").append(fieldMappingsContext.getValue(mapping.getToField())).append(",");
			//}
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","));
		sqlBuilder.append(" where ").append(batchJobContext.getJobSchedulerMetadata().getFromObjectIdField()).append(" = ").append(requestData.get(batchJobContext.getJobSchedulerMetadata().getFromObjectIdField()));
		jdbcTemplate.update(sqlBuilder.toString());
	}
	
	@Transactional(value=TxType.REQUIRES_NEW)
	public void updateChildResponse(ChildObject childObject,Map<String,Object> requestData,Map<String,Object> responseData,List<String> operationTypes) throws Exception {
		StringBuilder sqlBuilder = new StringBuilder("Update ");
		sqlBuilder.append(childObject.getFromObjectName()).append(" Set ");
		JXPathContext fieldMappingsContext = JXPathContext.newContext(responseData);
		fieldMappingsContext.setLenient(true);
		
		List<ChildFieldMapping> fieldMappings =  batchJobContext.getCallBackChildFieldMappings().get(childObject.getObjectName());
		for(ChildFieldMapping mapping:  fieldMappings) {
			//if (mapping.getCallbackUpdate()) {
				sqlBuilder.append(" ").append(mapping.getFromField()).append(" = ").append(fieldMappingsContext.getValue(mapping.getToField())).append(",");
			//}
		}
		sqlBuilder.deleteCharAt(sqlBuilder.lastIndexOf(","));
		sqlBuilder.append(" where ").append(childObject.getFromObjectIdField()).append(" = ").append(requestData.get(childObject.getFromObjectIdField()));
		jdbcTemplate.update(sqlBuilder.toString());
	}

}
