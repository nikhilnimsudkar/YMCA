package com.ymca.web.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ymca.dao.AccountFinancialAidDao;
import com.ymca.model.AccountFinancialAid;
import com.ymca.model.ItemDetail;
import com.ymca.web.util.Constants;

@Service 
public class FinancialAssistanceService {
	
	@Resource
	private AccountFinancialAidDao customerFADao;
	
	public List<AccountFinancialAid> computeFA(ItemDetail itemDetails, Long customerId) {
		
		List<AccountFinancialAid> customerFAList = new ArrayList<AccountFinancialAid>();
		String category = itemDetails.getType();
		Date programStartDate = itemDetails.getStartDate();
		String faEligible = itemDetails.getFAEligible();
		//System.out.println("customerId:" + customerId);
		//System.out.println("category:" + category);
		//System.out.println("faEligible:" + faEligible);
		Date sysDate = new Date();
		
		//Check the FA eligibility flag
		if (Constants.YES.equalsIgnoreCase(faEligible)) {
			//System.out.println("faEligible:" + faEligible);
			//For the category and customer ID get records from Customer FA table.
			List<AccountFinancialAid> customerFAResponse = customerFADao.getBySalesAccountIdAndProductCategory(customerId, category);
			if (customerFAResponse != null) {
				//System.out.println("customerFAResponse is not null.");
				for (int i=0; i<customerFAResponse.size(); i++) {
					AccountFinancialAid customerFA = customerFAResponse.get(i);
					if (customerFA != null) {
						//System.out.println("customerFA is not null.");
						//System.out.println("customerFA ID:" + customerFA.getId());
						Date faStartDate = customerFA.getFaStartDate_c();
						Date faEndDate = customerFA.getFaEndDate_c();
						boolean isFA = false;
						//FOr PC=Membership Sign up date should be between FA start and end date
						if (Constants.PRODUCT_CATEGORY_MEMBERSHIP.equalsIgnoreCase(category)) {
							if (sysDate.after(faStartDate) && sysDate.before(faEndDate)) {
								isFA = true;
							}
							
						} else if (Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(category) ||
								Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equalsIgnoreCase(category) ||
								Constants.PRODUCT_CATEGORY_DAY_CAMP.equalsIgnoreCase(category) ||
								Constants.PRODUCT_CATEGORY_RESIDENT_CAMP.equalsIgnoreCase(category) ||
								Constants.PRODUCT_CATEGORY_PROGRAM.equalsIgnoreCase(category)
								) {
							/* For PC =Child Care/After School/Day Camp/Resident Camp/Program the 
							   program start date on the sign up is within the FA eligible start and end date
							    	*/
							if (programStartDate.after(faStartDate) && programStartDate.before(faEndDate)) {
								isFA = true;
							}
							
						}
						//System.out.println("isFA:" + isFA);
						if (isFA) {
							customerFAList.add(customerFA);
						}
					}
				}
			}
		}		
		//System.out.println("customerFAList size:" + customerFAList.size());
		return customerFAList;
	}
	
}
