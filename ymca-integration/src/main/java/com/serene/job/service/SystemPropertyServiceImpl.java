package com.serene.job.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.util.JobUtils;
import com.serene.ws.service.impl.GenericWebServiceImpl;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.model.SystemProperty;

@Service
public class SystemPropertyServiceImpl implements SystemPropertyService {
	
	private static final Log log = LogFactory.getLog(SystemPropertyServiceImpl.class);
	
	@Resource(name="genericWebServiceImpl")
	GenericWebServiceImpl genericWebService;
	
	@Resource
	private JobUtils jobUtils;
	
	@Resource 
	protected BatchJobContext batchJobContext ;
	
	@Override
	public String getPropertyByKeyName(String keyName) {
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(batchJobContext.getJobSchedulerMetadata());
		
		String value = "";
		if(!"".equals(keyName)){
			String sql = "select keyValue from system_property where keyName = ?";
			
			value = jdbcTemplate.queryForObject(sql, String.class, keyName);
		}
		return value;
	}
}
