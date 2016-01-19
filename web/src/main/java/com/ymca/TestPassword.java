package com.ymca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.ymca.model.SystemProperty;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.util.Constants;

public class TestPassword {

	public static void main(String args[]){
		/*String rawPassword = "asdfg";
		String encryptedPassword = "$2a$10$jLch0QkRIgelB688IN.TleO2fi6SsmoaNH3S6lyFCPHbem5LXxuMC";
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		System.out.println(bCryptPasswordEncoder.matches(rawPassword, encryptedPassword));*/
		
		/*Date dateOfBirth = new Date(88, 11, 31);
		System.out.println(dateOfBirth);
		
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dateOfBirth);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}*/
		
		/*Calendar dob = Calendar.getInstance();  
		dob.setTime(new Date());
		//dob.add(Calendar.DAY_OF_YEAR, -1);
		
		int month =  dob.get(Calendar.MONTH);
		int date  = dob.get(Calendar.DAY_OF_MONTH);
		int year = dob.get(Calendar.YEAR);
		
		System.out.println(++month  + "/" + date +"/" + year);
		*/
		
		/*Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");
		System.out.println(cal.getTime());
		// Ouput "Wed Sep 26 14:23:28 EST 2012"

		String formatted = format1.format(cal.getTime());
		System.out.println(formatted);*/
		
		//Date curr = new Date();
		/*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dob = new Date();
		String abc = "2002-10-23";
		try {
			dob = sdf.parse(abc);
			System.out.println(isYouthByDob(dob));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dob);*/
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar startDateCal = Calendar.getInstance();
		startDateCal.set(Calendar.HOUR, 0);
		startDateCal.set(Calendar.MINUTE, 0);
		startDateCal.set(Calendar.SECOND, 0);
		startDateCal.set(Calendar.HOUR_OF_DAY, 0);	
		startDateCal.set(Calendar.MONTH,Calendar.JANUARY);		
		startDateCal.set(Calendar.DATE, 1);
	    //System.out.println(sdf.format(startDateCal.getTime()));   
	    // System.out.println(sdf.format(now.getTime()));
		//System.out.println(PaymentTypeEnum.CreditMemoWriteOff.getValue());
		
		String split = "asdf|sad|pqur";
		String[] splitArr = split.split("\\|");
		//System.out.println(splitArr[1]);
		
		String[] specialRequest = new String[3];
		
		specialRequest[0] = "1";
		//specialRequest[1] = "2";
		//specialRequest[2] = "3";
		System.out.println(specialRequest.length);
		if(specialRequest[1]!=null){
			System.out.println(specialRequest[1]);
		}
	}
	
	public static boolean isYouthByDob(Date dobDate){	
		boolean isYouth = false;
		Calendar dob = Calendar.getInstance();  
		dob.setTime(dobDate);  
		Calendar today = Calendar.getInstance();  
		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);  
		if (today.get(Calendar.MONTH) < dob.get(Calendar.MONTH)) {
		  age--;  
		} else if (today.get(Calendar.MONTH) == dob.get(Calendar.MONTH)
		    && today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
		  age--;  
		}	
		int youthAgeLowerLimit = 9;
		int youthAgeUpperLimit = 14;
		
		if(youthAgeLowerLimit < age && age < youthAgeUpperLimit){
			isYouth = true;
		}
		
		return isYouth;
	}
}
