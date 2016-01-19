package com.ymca.web.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.ymca.dao.SignupDao;
import com.ymca.model.Activity;
import com.ymca.model.Signup;
import com.ymca.web.util.Constants;

@Component 
public class CheckInCheckoutService extends BaseService {
	
	private Logger log = LoggerFactory.getLogger(CheckInCheckoutService.class);
	
	@Resource
	private SignupDao signupDao ;
	
	public String isValidCheckin(Long contactId,Long checkinLocationId) {
		
		Pageable pageable = new PageRequest(0, 1);
		List<Signup> signups =  signupDao.findByContact(contactId,pageable);
		//List<Signup> signuplst=signupDao.findByTypeInAndContactId(types ,user.getContactId());
		
		Collections.sort(signups, new Comparator<Signup>() {
		    public int compare(Signup m1, Signup m2) {
		        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
		    }
		});
	  for(Signup a:signups){
		 System.out.println(a.getLastUpdated());
	  }
		String message = "";
		if (!signups.isEmpty()) {
			Signup signup = signups.get(0);
		    long locID=signup.getLocation().getId();
			//if(signup.getLocation().getId() !=null  && locID==checkinLocationId){
			if (signup.getLocation() != null && (locID == checkinLocationId || (signup.getLocation().getRecordName().equals(Constants.LOCATION_ALL_BRANCH) || signup.getLocation().getRecordName().equals(Constants.LOCATION_BAYAREA) ))) {
				message = "GRANTED";
			} else {
				message = "GRANTED -- Alert! Member location is different from facility location ";
			}
			logCheckinActivity(signup,checkinLocationId,"Member Checkin",null);
			return message ;
		}
		return "DENY" ;
	}

	@Async
	@Transactional
	public void logCheckinActivity(Signup signup,Long checkinLocationId, String description,String sexOffReason) {
    	// cal1_EndDate = Calendar.getInstance();
    	//cal1_EndDate.setTime(signup.getProgramEndDate());
    	Calendar cal2_CurrentDate = Calendar.getInstance();
    	//if(cal1_EndDate.after(cal2_CurrentDate)) {
	    	Activity activity=new Activity();
	    	activity.setCreatedDate(new Date());
	    	activity.setCustomer(signup.getCustomer());
	    	activity.setContact(signup.getContact());
	    	activity.setSignup(signup);
	    	//activity.setDescription("Member Checkin");
	    	activity.setDescription(description);
	    	activity.setLastUpdated(cal2_CurrentDate);
	    	activity.setType("CHECKIN");
	    	activity.setUnauthorizedAccessReason(sexOffReason);
	    	activity.setFacilityLoationCheckedInTo(checkinLocationId);
	    	activityDao.save(activity);
    	//}
	}
	
	/*@Async
	@Transactional
	public void logCheckinActivitySexOff(Signup signup,Long checkinLocationId, String description,String sexOffReason ) {
    	// cal1_EndDate = Calendar.getInstance();
    	//cal1_EndDate.setTime(signup.getProgramEndDate());
    	Calendar cal2_CurrentDate = Calendar.getInstance();
    	//if(cal1_EndDate.after(cal2_CurrentDate)) {
	    	Activity activity=new Activity();
	    	activity.setCreatedDate(new Date());
	    	activity.setCustomer(signup.getCustomer());
	    	activity.setContact(signup.getContact());
	    	activity.setSignup(signup);
	    	//activity.setDescription("Member Checkin");
	    	activity.setDescription(description);
	    	activity.setLastUpdated(cal2_CurrentDate);
	    	activity.setType("CHECKIN");
	    	activity.setUnauthorizedAccessReason(sexOffReason);
	    	activity.setFacilityLoationCheckedInTo(checkinLocationId);
	    	activityDao.saveAndFlush(activity);
    	//}
	}*/
}
