package com.serene.job.processor;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.serene.job.service.PaymentJobService;

@Service
@ComponentScan("com.ymca.core.service")
public class JetpayPaymentProcessor extends GenericItemProcessor implements ItemProcessor<Object,Object>{
	
	private static final Log log = LogFactory.getLog(JetpayPaymentProcessor.class);

	private Integer sno = 1;
	
	@Resource
	private PaymentJobService paymentJobService ;
	
	@SuppressWarnings("unchecked")
	@Override
	public Object process(Object item) throws Exception {
		//Integer sno = 1;
		Map<String,Object> data = (Map<String, Object>) item;
		
		try {
			if(data.get("PaymentMode")!=null && data.get("amount")!=null){
				String UNUSED = "";
				Integer TRANSACTION_SEQUENCE_NUMBER = 0;
				Double TRANSACTION_AMOUNT = Double.parseDouble(data.get("amount").toString()) * 100;
				
				if(data.get("PaymentMode").toString().equalsIgnoreCase("ACH")){
					sno++;
					String TRANSACTION_TYPE = "DB"; // Debit
					Object ABA_NUMBER = data.get("bank_routing_number");
					Object ACCOUNT_NUMBER_OR_TOKEN = data.get("token_number");
					Object CHECK_NUMBER = data.get("check_number");
					Object ACCOUNT_TYPE = data.get("BankAccountType_c");
					Object ACCOUNT_HOLDER_NAME = data.get("full_name");
					String ACTION_CODE = "";
					String MERCHANT_ASSIGNED_REFERENCE_NUMBER = data.get("InvoiceId")+"_"+data.get("paymentId");
					
					data.put("RECORD_TYPE", "CH");
					data.put("SEQ_NO", sno.toString());
					data.put("TRANSACTION_SEQUENCE_NUMBER", TRANSACTION_SEQUENCE_NUMBER);
					data.put("TRANSACTION_TYPE", TRANSACTION_TYPE);
					
					data.put("ABA_NUMBER", convertNullToBlank(ABA_NUMBER)); 
					data.put("ACCOUNT_NUMBER_OR_TOKEN", convertNullToBlank(ACCOUNT_NUMBER_OR_TOKEN));
					data.put("CHECK_NUMBER", convertNullToBlank(CHECK_NUMBER));
					
					String acct_Type = convertNullToBlank(ACCOUNT_TYPE);
					if(!"".equals(acct_Type) && acct_Type.length()>1)
						acct_Type = acct_Type.substring(0,1);
					
					data.put("ACCOUNT_TYPE", acct_Type);
					data.put("TRANSACTION_AMOUNT", convertNullToBlank(TRANSACTION_AMOUNT.intValue()));
					data.put("ACCOUNT_HOLDER_NAME", convertNullToBlank(ACCOUNT_HOLDER_NAME));
					data.put("ACTION_CODE", convertNullToBlank(ACTION_CODE));
					data.put("UNUSED", convertNullToBlank(UNUSED));
					data.put("MERCHANT_ASSIGNED_REFERENCE_NUMBER", convertNullToBlank(MERCHANT_ASSIGNED_REFERENCE_NUMBER));
					
				} else {
					sno++;
					String TRANSACTION_TYPE = "AC"; // Auth and Capture
					Object PRIMARY_ACCOUNT_NUMBER_OR_TOKEN = data.get("token_number");
					Object expMonth = data.get("expiration_month");
					Object expYearObj = data.get("expiration_year");
					String expYear = "";
					if(expYearObj!=null)
						expYear = expYearObj.toString().substring(2,4);
					String EXPIRATION_DATE = expYear+expMonth;
					String AUTHORIZATION_CODE = "";
					String RESPONSE_CODE = "";
					String REFERNCE_NUMBER = data.get("InvoiceId").toString()+"_"+data.get("paymentId").toString();
					
					data.put("RECORD_TYPE", "DR");
					data.put("SEQ_NO", sno.toString());
					data.put("TRANSACTION_SEQUENCE_NUMBER", TRANSACTION_SEQUENCE_NUMBER);
					data.put("TRANSACTION_TYPE", TRANSACTION_TYPE);
					
					data.put("UNUSED1", convertNullToBlank(UNUSED));
					data.put("UNUSED2", convertNullToBlank(UNUSED));
					data.put("PRIMARY_ACCOUNT_NUMBER_OR_TOKEN", convertNullToBlank(PRIMARY_ACCOUNT_NUMBER_OR_TOKEN));
					data.put("EXPIRATION_DATE", convertNullToBlank(EXPIRATION_DATE));
					data.put("TRANSACTION_AMOUNT1", convertNullToBlank(TRANSACTION_AMOUNT.intValue()));
					data.put("UNUSED3", convertNullToBlank(UNUSED));
					data.put("UNUSED4", convertNullToBlank(UNUSED));
					data.put("UNUSED5", convertNullToBlank(UNUSED));
					data.put("AUTHORIZATION_CODE", convertNullToBlank(AUTHORIZATION_CODE));
					data.put("RESPONSE_CODE", convertNullToBlank(RESPONSE_CODE));
					data.put("UNUSED6", convertNullToBlank(UNUSED));
					data.put("UNUSED7", convertNullToBlank(UNUSED));
					data.put("REFERNCE_NUMBER", convertNullToBlank(REFERNCE_NUMBER));
					data.put("UNUSED8", convertNullToBlank(UNUSED));
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in payment processor ",e);
		}
		
		if(data.size()==0)
			return null;
		
		// update payment status to "sent to Jetpay"
		paymentJobService.updatePaymentStatus(data,batchJobContext.getJobSchedulerMetadata(),"Sent to Jetpay");
		
		data.remove("PaymentMode");
		data.remove("paymentId");
		
		return data;
	}
	
	private String convertNullToBlank(Object obj) {
		String str = "";
		if (obj==null){
			str = "";
		}
		else if (obj.toString().trim().equals("")){
			str = "";
		} 
		else{
			str = obj.toString();
		}
		return str;
	}
	
	// not in use - remove
	/*private void createJetPayFile(Map<String, Object> data, String FILE_NAME, String FILE_PATH) throws IOException {
		
		String UNUSED = "";
		Integer sno = 1;
		String MERCHANT_ID = "";
		String MERCHANT_NAME = "";
		String currentDate = DATE_FORMAT.format(new Date());
		String currentTime = TIME_FORMAT.format(new Date());
		
		FileWriter fileWriter = null;
		// write a file
		fileWriter = new FileWriter(FILE_PATH);
		
		// file header
		fileWriter.append("FH");
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(sno.toString());
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(MERCHANT_ID);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(currentDate);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(currentTime);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(MERCHANT_NAME);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(FILE_NAME);
		
		fileWriter.append(NEW_LINE_OPERATOR);
		
		if(data.get("PaymentMode_c").toString().equalsIgnoreCase("ACH")){
			sno++;
			String TRANSACTION_SEQUENCE_NUMBER = "0";
			String TRANSACTION_TYPE = "DB";
			String ABA_NUMBER = "";
			String ACCOUNT_NUMBER_OR_TOKEN = "";
			String CHECK_NUMBER = "";
			String ACCOUNT_TYPE = "";
			String TRANSACTION_AMOUNT = "";
			String ACCOUNT_HOLDER_NAME = "";
			String ACTION_CODE = "";
			
			String MERCHANT_ASSIGNED_REFERENCE_NUMBER = "";
			
			fileWriter.append("CH");
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(sno.toString());
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_SEQUENCE_NUMBER);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_TYPE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(ABA_NUMBER);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(ACCOUNT_NUMBER_OR_TOKEN);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(CHECK_NUMBER);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(ACCOUNT_TYPE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_AMOUNT);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(ACCOUNT_HOLDER_NAME);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(ACTION_CODE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(MERCHANT_ASSIGNED_REFERENCE_NUMBER);
		}
		else{
			sno++;
			String TRANSACTION_SEQUENCE_NUMBER = "0";
			String TRANSACTION_TYPE = "DB";
			String PRIMARY_ACCOUNT_NUMBER_OR_TOKEN = "";
			String EXPIRATION_DATE = "";
			String TRANSACTION_AMOUNT = "";
			String AUTHORIZATION_CODE = "";
			String RESPONSE_CODE = "";
			String REFERNCE_NUMBER = "";
			
			fileWriter.append("DR");
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(sno.toString());
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_SEQUENCE_NUMBER);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_TYPE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(PRIMARY_ACCOUNT_NUMBER_OR_TOKEN);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(EXPIRATION_DATE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(TRANSACTION_AMOUNT);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(AUTHORIZATION_CODE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(RESPONSE_CODE);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(REFERNCE_NUMBER);
			fileWriter.append(FILE_COLUMN_SEPERATOR);
			fileWriter.append(UNUSED);
			
		}
		
		fileWriter.append(NEW_LINE_OPERATOR);
		
		sno++;
		String NET_AMOUNT = "";
		String DETAIL_COUNT = "";
		String CREDIT_AMOUNT = "";
		String CREDIT_COUNT = "";
		String DEBIT_AMOUNT = "";
		String DEBIT_COUNT = "";
		String APROVED_AMOUNT = "";
		String APROVED_COUNT = "";
		String REJECTED_AMOUNT = "";
		String REJECTED_COUNT = "";
		String DECLINED_AMOUNT = "";
		String DECLINED_COUNT = "";
		String CAPTURED_AMOUNT = "";
		String CAPTURED_COUNT = "";
		
		// file trailer
		fileWriter.append("FT");
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(sno.toString());
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(NET_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(DETAIL_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(CREDIT_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(CREDIT_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(DEBIT_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(DEBIT_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(APROVED_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(APROVED_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(REJECTED_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(REJECTED_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(DECLINED_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(DECLINED_COUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(CAPTURED_AMOUNT);
		fileWriter.append(FILE_COLUMN_SEPERATOR);
		fileWriter.append(CAPTURED_COUNT);
		
		fileWriter.flush();
		fileWriter.close();
	}*/
}
