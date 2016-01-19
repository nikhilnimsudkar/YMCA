package com.serene.job.service;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.common.Constants;
import com.ymca.model.JetPayPayment;

@Service
public class ConfirmWaitlistedSignupServiceImpl implements ConfirmWaitlistedSignupService {
	
	private static final Log log = LogFactory.getLog(ConfirmWaitlistedSignupServiceImpl.class);
	
	@Resource
	private PaymentJobService paymentJobService ;
	@Resource
	private CommonService commonService ;
	@Resource
	private InvoiceService invoiceService ;
	
	@Override
	public Map<String,Object> confirmWaitlistedSignup(Map<String,Object> data, BatchJobContext batchJobContext) {
		
		/* param data requires fields:
		 * no_of_tickets, remaining_capacity_c, final_amount, payment_mode, paymentmethod_id, signupId, accountId, contact_id, amount, payerId
		 * full_name, token_number, check_number
		 * FAamount_c, 
		 */

		try {
			
			Object noOfTix = data.get("no_of_tickets");
			Object remainingCapacity = data.get("remaining_capacity_c");
			Object payAmountObj = data.get("final_amount");
			
			Integer noOfTickets = 1;
			if(noOfTix !=null){
				noOfTickets = Integer.parseInt(noOfTix.toString());
			}
			
			if( payAmountObj!=null && remainingCapacity!=null 
					&& noOfTickets <= Integer.parseInt(remainingCapacity.toString()) ){
				
				Object paymentMode = data.get("payment_mode");
				Object paymentMethod = data.get("paymentmethod_id");
				if(paymentMethod!=null && paymentMode!=null && !"".equals(paymentMode)){
					
					String payAmount = payAmountObj.toString();
					JetPayPayment jp = null;
					
					log.debug("DEBUG - paymentMode: " +paymentMode + "payAmount: " + payAmount + " data: "+data);
					
					jp = processJetpayPayment(data, batchJobContext, paymentMode, payAmount, jp);
					
					if(jp==null){
						data.put("SignupStatus", "Inactive");
					} else {
						if(jp.getActCode().equals("000")){ //transaction was success
							
							paymentSuccess(data, batchJobContext, payAmountObj);
							
						} else { // transaction was failure
							
							paymentFailed(data, batchJobContext, jp);
						}
					}
				}
				
				Calendar cal = Calendar.getInstance();
				data.put("lastUpdatedDate", cal);
			} else {
				return null;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in Confirm Waitlisted Signup Service ",e);
			return null;
		}
		
		return data;
	}

	private void paymentFailed(Map<String, Object> data,
			BatchJobContext batchJobContext, JetPayPayment jp) {
		data.put("SignupStatus", "Waitlisted");
		String errMsg = jp.getResponseText();
		if(errMsg.length()>255){
			errMsg = errMsg.substring(0,255);
		}
		log.error("Payment is Denied Reason - " + jp.getResponseText());
		// create activity
		commonService.saveActivity(data, batchJobContext.getJobSchedulerMetadata(),errMsg);
	}

	private void paymentSuccess(Map<String, Object> data,
			BatchJobContext batchJobContext, Object payAmountObj) {
		// capacity management
		String signupStatus = commonService.signupCapacityManagement(data, batchJobContext.getJobSchedulerMetadata());
		data.put("SignupStatus", signupStatus);
		log.debug("DEBUG - SignupStatus "+signupStatus);
		
		// process invoice
		Long invoiceId = invoiceService.saveinvoice(data, batchJobContext.getJobSchedulerMetadata());
		log.debug("DEBUG - Invoice created with invoiceId "+invoiceId);
		
		// process payment
		paymentJobService.savePayment(
				data, 
				batchJobContext.getJobSchedulerMetadata(), 
				Double.parseDouble(payAmountObj.toString()), 
				invoiceId,
				"Payment", Constants.PAYMENT_STATUS_SUCCESS);
		
		// process fa amount
		Object fa = data.get("FAamount_c");
		if(fa!=null && Double.parseDouble(fa.toString())>0){
			log.error("DEBUG - FAamount_c "+Double.parseDouble(fa.toString()));
			String paymentType = "Credit Memo/FA Waiver";
			paymentJobService.savePayment(
					data, 
					batchJobContext.getJobSchedulerMetadata(), 
					Double.parseDouble(payAmountObj.toString()), 
					invoiceId,
					paymentType, Constants.PAYMENT_STATUS_SUCCESS);
		}
	}

	private JetPayPayment processJetpayPayment(Map<String, Object> data,
			BatchJobContext batchJobContext, Object paymentMode,
			String payAmount, JetPayPayment jp)
			throws UnsupportedEncodingException, FactoryConfigurationError,
			Exception {
		if(Double.parseDouble(payAmount)>0){
			if("ACH".equalsIgnoreCase(paymentMode.toString())){
				// process ACH payment
				jp = paymentJobService.processJetpayACHPayment(data, payAmount, batchJobContext.getJobSchedulerMetadata());
			}
			else if("CREDIT".equalsIgnoreCase(paymentMode.toString())){
				// process CC payment
				jp = paymentJobService.processCCjetPayTransaction(data,payAmount, batchJobContext.getJobSchedulerMetadata());
			}
			log.debug("DEBUG - payment was success "+jp.getTransId());
		}
		return jp;
	}
}
