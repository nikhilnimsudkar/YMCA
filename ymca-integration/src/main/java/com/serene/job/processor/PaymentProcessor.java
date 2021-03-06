package com.serene.job.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.serene.job.common.Constants;
import com.serene.job.dao.IntermidiateTableDao;
import com.serene.job.model.IntermediateTable;
import com.serene.job.model.IntermediateTablePrimaryKey;
import com.serene.job.service.PaymentJobService;
import com.serene.job.util.PaymentTypeEnum;

@Service
@ComponentScan("com.ymca.core.service")
public class PaymentProcessor extends GenericItemProcessor implements ItemProcessor<Object,Object>{
	
	private static final Log log = LogFactory.getLog(PaymentProcessor.class);
	
	SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
	
	@Resource
	private PaymentJobService paymentJobService ;
	
	@Resource
	private IntermidiateTableDao intermidiateTableDao ; 
	
	@SuppressWarnings("unchecked")
	@Override
	public Object process(Object item) throws Exception {
		
		Map<String,Object> data = (Map<String, Object>) item;
		
		if(data.get("InvoiceId")==null)
			return null;
		
		String jetPayStatus = null;
		String financialStatus = null;
		Object paymentModeObj = data.get("paymentMode");
		if(paymentModeObj!=null){
			String paymentMode = paymentModeObj.toString();
			if(paymentMode.equals(Constants.PAYMENT_MODE_CREDIT_SLASH_DEBIT) || paymentMode.equals(Constants.PAYMENT_MODE_ACH)){
				jetPayStatus = Constants.PAYMENT_STATUS_IN_PROCESS;
			} else{
				financialStatus = Constants.PAYMENT_FINANCIAL_STATUS_PICK;
			}
		}
		data.put("source", Constants.PAYMENT_BATCH_SOURCE);
		data.put("paymentStatus", jetPayStatus);
		data.put("financialStatus", financialStatus);
		data.put("paymentType", PaymentTypeEnum.Payment.value);
		
		Boolean isDupe = paymentJobService.checkDupeByInvoiceIdAndDueDateAndSource(data,batchJobContext.getJobSchedulerMetadata());
		if(isDupe){
			updateIntermediateTable(data,"Dupe payment record for invoiceId and DueDate and source");
			return null;
		}
		
		String strInvoiceNumber = "";
		Object invoiceNumber = data.get("InvoiceNumber");
		if(invoiceNumber!=null){
			strInvoiceNumber = invoiceNumber.toString();
		}
		String paymentDate = sdf.format(new Date());
		String paymentNumber = strInvoiceNumber + paymentDate;
		data.put("paymentNumber", paymentNumber);
		
		Map<String,Object> childData = new HashMap<String,Object>();
		if(data.get("FAamount_c")!=null && (Double)data.get("FAamount_c")>0){
			
			
			/* payment amount should be same as invoice amount, when sending to jetpay it will be (payment amount - FA if any)
			 * 
			// if FA amount > 0, then payment amount should be invoice amount - FA amount
			Double invoiceAmount = Double.parseDouble(data.get("amount").toString());
			Double faAmount = Double.parseDouble(data.get("FAamount_c").toString());
			Double paymentAmt = invoiceAmount - faAmount;
			data.put("amount", paymentAmt);
			*/
			
			/* insert FA amount with query in processor itself due to open balance calculation */
			//	check if FA amount exists for invoice, if NOT then insert FA amount 
			
			data.put("payment_mode", data.get("paymentMode"));
			data.put("paymentmethod_id", data.get("paymentmethodId"));
			String paymentType = "Credit Memo/FA Waiver";
			paymentJobService.savePayment(
					data, 
					batchJobContext.getJobSchedulerMetadata(), 
					Double.parseDouble(data.get("FAamount_c").toString()), 
					Long.parseLong(data.get("InvoiceId").toString()),
					paymentType, Constants.PAYMENT_STATUS_SUCCESS);
			
			/* 
			 * 
			childData.put("FAamount", data.get("FAamount_c"));
			childData.put("paymentType", PaymentTypeEnum.CreditMemoFAWaiver.value);
			childData.put("paymentStatus", jetPayStatus);
			childData.put("financialStatus", financialStatus);
			childData.put("InvoiceId", data.get("InvoiceId"));
			childData.put("source", Constants.PAYMENT_BATCH_SOURCE);
			childData.put("paymentNumber", paymentNumber);
			childData.put("DueDate", data.get("DueDate"));
			childData.put("paymentmethodId", data.get("paymentmethodId"));
			childData.put("paymentMode", data.get("paymentMode"));
			childData.put("contact_id", data.get("contact_id"));
			childData.put("accountId", data.get("accountId"));
			childData.put("payerId", data.get("payerId"));
			childData.put("signupId", data.get("signupId"));
			*/
		} else {
			childData = null;
			updateIntermediateTable(data,"FA amount is 0");
		}
		
		childData = null; /* insert FA amount SQL query in processor itself due to open balance calculation for payment */
		data.put("createCreditMemoFAWaiverPayment", childData);
		
		Double paymentAmount = paymentJobService.computePaymentAmount(data,batchJobContext.getJobSchedulerMetadata());
		if (paymentAmount<=0){
			updateIntermediateTable(data,"Open balance for payment is 0");
			return null;
		}
		
		return data;
	}

	private void updateIntermediateTable(Map<String, Object> data, String err) {
		try {
			IntermediateTablePrimaryKey key = new IntermediateTablePrimaryKey();
			key.setJobName(batchJobContext.getJobSchedulerMetadata().getJobName());
			key.setObjectName(batchJobContext.getJobSchedulerMetadata().getFromObjectName());
			key.setObjectId(String.valueOf((Long) data.get(batchJobContext.getJobSchedulerMetadata().getFromObjectIdField())));
			IntermediateTable intermediateTable = intermidiateTableDao.getOne(key);
			
			intermediateTable.setSyncState("INTERMIDIATE");
			intermediateTable.setSyncStatus("ERROR");
			intermediateTable.setRetryCount(intermediateTable.getRetryCount()+1);
			intermediateTable.setErrorMessage(err);
			intermidiateTableDao.save(intermediateTable);
		} catch (Exception e1) {
			log.error("Error while writing error log ",e1);
		}
	}
}
