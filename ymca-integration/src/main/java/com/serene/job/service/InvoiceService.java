/**
 * @author gpatwa
 * This interface define high level Invoice calculation method. The known implementation of this class is InvoiceServiceImpl 
*/

package com.serene.job.service;

import java.util.Date;
import java.util.Map;

import com.serene.job.model.JobSchedulerMetadata;
import com.ymca.model.Invoice;

public interface InvoiceService {

	public Double computeFAAmount(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);
	
	public Double computeInvoiceAmount(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);
	
	public Double computePromoAmount(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);
	
	public Boolean isInvoiceRecordExist(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);
	
	public void updateSignupRecord(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);
	
	public Date computeDueDate(Map<String, Object> signUp,JobSchedulerMetadata jobSchedulerMetadata);

	public Long saveinvoice(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata);
	public String generateInvoiceNumber(Map<String, Object> data);
	
}
