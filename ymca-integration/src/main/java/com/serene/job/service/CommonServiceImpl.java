package com.serene.job.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.util.JobUtils;

@Service
public class CommonServiceImpl implements CommonService {
	
	@Resource
	private JobUtils jobUtils;
	
	@Override
	public synchronized String signupCapacityManagement(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata) {
		String signupStatus = "Active";
		Integer noofTickets = 1;
		if(data.get("no_of_tickets")!=null){
			noofTickets = Integer.parseInt(data.get("no_of_tickets").toString());
		}
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		updateCapacity(data,noofTickets, jobSchedulerMetadata);
		
		int i = 0;
		Calendar cal = Calendar.getInstance();
	    String sql = "UPDATE signup SET "
	    		+ "status =?, lastUpdated=?"
	    		+ "where signupId=?";

		i = jdbcTemplate.update(sql, 
				signupStatus,
				cal, 
				data.get("signupId").toString()
				);
		
		return signupStatus;
	}
	
	public synchronized void updateCapacity(Map<String, Object> data, Integer noOfTickets, JobSchedulerMetadata jobSchedulerMetadata) {

			Long remainingCapacity = convertNullToZero(data.get("remaining_capacity_c"));
			Long actualRemainingCapacity = convertNullToZero(data.get("actualRemainingCapacity_c"));
			Long waitListCounter =convertNullToZero(data.get("WaitlistCounter_c"));
			
			if(remainingCapacity.compareTo(0L)<=0 || remainingCapacity < noOfTickets){
				//remaining capacity is less than 0
				waitListCounter = waitListCounter+1;
			}
			/* allow remaining capacity to be negative number 
			else{*/
				// remaining capacity is greater than 0
				remainingCapacity = remainingCapacity-noOfTickets;
				//actualRemainingCapacity = actualRemainingCapacity-noOfTickets;
			//}
			if(actualRemainingCapacity.compareTo(0L)<=0 || actualRemainingCapacity < noOfTickets){
				//remaining capacity is less than 0
				waitListCounter = waitListCounter+1;
			}
			/* allow remaining capacity to be negative number 
			else{ */
				// remaining capacity is greater than 0
				//remainingCapacity = remainingCapacity-noOfTickets;
				actualRemainingCapacity = actualRemainingCapacity-noOfTickets;
			//}
			
			int i = 0;
			
			Calendar cal = Calendar.getInstance();
		    JdbcTemplate jdbcTemplate = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata);
		    
		    String sql = "UPDATE item_detail SET "
		    		+ "RemainingCapacity_c =?, actualRemainingCapacity_c=?, WaitlistCounter_c=?, lastUpdated=?"
		    		+ "where Id=?";

			i = jdbcTemplate.update(sql, 
					remainingCapacity,
					actualRemainingCapacity,
					waitListCounter,
					cal, 
					data.get("Id").toString()
					);
		
	}
	
	@Override
	public void saveActivity(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata, String description) {
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		int i = 0;
		Calendar cal = Calendar.getInstance();
	    String sql = "INSERT INTO activity ("
	    		+ "show_on_portal, "
	    		+ "accountId, "
	    		+ "contact_id, "
	    		+ "type, "
	    		+ "signupId, "
	    		+ "description, "
	    		+ "facilityLoationCheckedInTo, "
	    		+ "lastUpdated "
	    		+ " ) VALUES ( "
	    		+ " ?,?,?,?,?,?,?,?)";

		i = jdbcTemplate.update(sql, 
				0,
				data.get("accountId").toString(),
				data.get("contact_id").toString(),
				"Payment Denied",
				data.get("signupId").toString(),
				description,
				0,
				cal
				);
	}
	
	public Long convertNullToZero(Object obj){
		Long val = 0L;
		if(obj!=null){
			 val = Long.parseLong(obj.toString());
		}
		
		return val;
	}
}
