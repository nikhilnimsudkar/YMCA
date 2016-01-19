package com.serene.job.processor;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.serene.job.dao.IntermidiateTableDao;
import com.serene.job.model.IntermediateTable;
import com.serene.job.model.IntermediateTablePrimaryKey;
import com.serene.job.service.PaymentJobService;

@Service
@ComponentScan("com.ymca.core.service")
public class JetpayResponseProcessor extends GenericItemProcessor implements ItemProcessor<Object,Object>{
	
	private static final Log log = LogFactory.getLog(JetpayResponseProcessor.class);
	
	@Resource
	private PaymentJobService paymentJobService ;
	
	@Resource
	private IntermidiateTableDao intermidiateTableDao ; 
	
	@SuppressWarnings("unchecked")
	@Override
	public Object process(Object item) throws Exception {

		Map<String,Object> data = (Map<String, Object>) item;
		
		try {
			boolean paymentSuccess = false;
			String paymentStatus = "";
			
			Object auth_code = data.get("AUTHORIZATION_CODE"); // Blank for failure for credit card // for ACH, we will always send ACH
			Object response_code = data.get("RESPONSE_CODE"); // 0 for success
			
			if(auth_code!=null && !" ".equals(auth_code)){
				//success
				paymentSuccess = true; 
			}
			
			if(paymentSuccess && response_code!=null && "0".equals(response_code)){
				// success
			} else {
				paymentSuccess = false;
			}
			
			if(!paymentSuccess){ // fail
				// update payment status to Failed
				String errMsg = "Payment Failed: Response code: "+response_code.toString();
				data.put("ReasonforFailureInJetPay", errMsg);
				try{
					paymentStatus = "FAILURE";
					// data should have paymentId
					//paymentJobService.updatePaymentStatusAndErrorMessage(data,batchJobContext.getJobSchedulerMetadata(),paymentStatus,errMsg);
					// send email
				} catch (Exception e){
					log.error("Exception - Error while updating payment record Fail:- ",e);
					updateIntermediateTable(data,errMsg);
				}
			}
			
			if(paymentSuccess){ // success
				// update payment status to Success
				try{
					paymentStatus = "SUCCESS";
					// data should have paymentId
					//paymentJobService.updatePaymentStatus(data,batchJobContext.getJobSchedulerMetadata(),paymentStatus);
					// send email
				} catch (Exception e){
					String errMsg = "Error while updating payment record Success:- ";
					log.error("Exception - "+errMsg ,e);
					updateIntermediateTable(data,errMsg + e.getMessage());
				}
				
				// update signup nextbilldate
				// 20151104 - this is handle by invoice batch job by Atul
			}
			
			// update token
			
			data.put("paymentStatus", paymentStatus);
			
		} catch(Exception e){
			log.error("Exception - Error on JetpayResponseProcessor while processing data ",e);
			updateIntermediateTable(data, "Error on JetpayResponseProcessor while processing data" + e.getMessage());
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
