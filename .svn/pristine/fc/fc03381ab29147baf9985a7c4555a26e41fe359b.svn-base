/**
 * @author gpatwa
 * Custom JDBC item reader
 */
package com.serene.job.reader;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.common.Constants;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.JobSchedulerMetadata;

@Service
@Lazy(true)
@Scope("prototype")
public class CustomJdbcItemReader<T> extends JdbcCursorItemReader<T> implements StepExecutionListener {

	@Resource
	private BatchJobContext batchJobContext ;     
	
	@Resource
	private JobSchedulerMetadataDao  jobSchedulerMetadataDao ;
	
	private String jobName ;
	
	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	@Override
	@Transactional
	public void beforeStep(StepExecution stepExecution) {
        String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
        JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.findOne(jobName);
//        JobSchedulerMetadata jobSchedulerMetadata =  batchJobContext.getJobSchedulerMetadata();
        String sql = jobSchedulerMetadata.getFromQuery();
       	if (StringUtils.contains(sql,Constants.LAST_UPDATED_DATE)) {
			String startTime = jobSchedulerMetadata.getInterfaceLastPoolTime();
			startTime = null ;
			if (StringUtils.isNoneBlank(startTime)) {
				sql = sql.replaceAll(Constants.LAST_UPDATED_DATE, startTime);
			} else {
				sql = sql.replaceAll(Constants.LAST_UPDATED_DATE, "1800-01-11 12:08:01.0");
			}
			//if (StringUtils.isNoneBlank(startTime))  
			setSql(sql);
			setFetchSize(Constants.DB_FETCH_SIZE);
			// setSql("SELECT  c.PartyId,IF(c.PartyId is null,"PORTAL_CONTACT",null) SourceSystem,IF(c.PartyId is null,contactId,null) SourceSystemReferenceValue,contactId,a.PartyId AccountPartyId,FirstName,LastName,EmailAddress,DateOfBirth,concat(c.FirstName,' ',c.LastName) ContactName,c.AddressLine1,c.AddressLine2,c.City,c.State,c.PostalCode,c.Country,c.lastUpdated,c.FormattedPhoneNumber from contact c,account a  where a.accountId = c.accountId and a.PartyId is not null and (c.lastUpdated > "#lastUpdatedDate" OR (c.lastUpdated > NOW() - INTERVAL 20 MINUTE and c.PartyId is null) ) order by c.lastUpdated asc ;");
       	}
	}

	@Override
	public ExitStatus afterStep(StepExecution stepExecution) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//super.afterPropertiesSet();
		//Assert.notNull(sql, "The SQL query must be provided");
		//Assert.notNull(rowMapper, "RowMapper must be provided");
	}
	
}
