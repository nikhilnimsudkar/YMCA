/**
 * @author gpatwa
 * This is Utility class used by processor or service
*/
package com.serene.job.util;

import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.serene.job.common.ResultsetToHashMap;
import com.serene.job.dao.IntermidiateTableDao;
import com.serene.job.model.IntermediateTable;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.ws.service.FusionWebService;

@Service
public class JobUtils {

	private static final Log log = LogFactory.getLog(JobUtils.class);

	@Resource(name="fusionWebServiceImpl")
	protected FusionWebService fusionWebService ;
	
	@Resource
	private IntermidiateTableDao intermidiateTableDao ; 
	
	@Resource
	public ApplicationContext applicationContext ;

	@Resource
	private ResultsetToHashMap resultsetToHashMap ; 
	
	
	
	@Transactional
	public JdbcTemplate getFromJdbcTemplate(JobSchedulerMetadata jobSchedulerMetadata){
		JdbcTemplate fromJdbcTemplate = new JdbcTemplate((DataSource) applicationContext.getBean(jobSchedulerMetadata.getFromDataSource())); 
		return fromJdbcTemplate ;
	}
	
	@Transactional
	public JdbcTemplate getToJdbcTemplate(JobSchedulerMetadata jobSchedulerMetadata){
		JdbcTemplate toJdbcTemplate = new JdbcTemplate((DataSource) applicationContext.getBean(jobSchedulerMetadata.getToDataSource()));
		return toJdbcTemplate ;
	}
	
	@Async
	@Transactional(value=TxType.REQUIRES_NEW)
	public void logItem(String jobName, String objectName, String objectIdField, Map<String, Object> item) {
		IntermediateTable intermediateTable = new IntermediateTable();
		intermediateTable.setJobName(jobName);
		intermediateTable.setObjectName(objectName);
		
		if (item.get(objectIdField) != null) {
			intermediateTable.setObjectId(item.get(objectIdField).toString());
		} else {
			// dummy value for id not found
			intermediateTable.setObjectId("-1000");
		}
		intermediateTable.setSyncState("");
		intermediateTable.setSyncStatus("COMPLETED");
		intermediateTable.setRetryCount(0);
		intermediateTable.setErrorMessage(null);
		intermediateTable.setData(item.toString());
		intermidiateTableDao.save(intermediateTable);
	}
	
	@Async
	@Transactional(value=TxType.REQUIRES_NEW)
	public void logItemStatus(Exception exception, String jobName, String objectName, String objectIdField,Map<String, Object> item) {
		intermidiateTableDao.updateItemErrorStatus("INTERMIDIATE",ExceptionUtils.getRootCauseMessage(exception),jobName,objectName,item.get(objectIdField).toString());
	}
}
