package com.serene.job.service;

import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.serene.job.common.Constants;
import com.serene.job.model.JobSchedulerMetadata;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Payment;
import com.ymca.model.Signup;
import com.serene.job.util.JobUtils;
import com.serene.job.util.PaymentTypeEnum;

@Service
public class PaymentJobServiceImpl implements PaymentJobService {
	
	private static final Log log = LogFactory.getLog(PaymentJobServiceImpl.class);
	
	@Resource
	private JobUtils jobUtils;
	
	@Override
	public Double computePaymentAmount(Map<String, Object> invoice,
			JobSchedulerMetadata jobSchedulerMetadata) {
		// TODO Auto-generated method stub
		Double openBalanceOnInvoice = 0D, trueInvoiceValue = 0D, sumOfDebitPayment = 0D, sumOfCreditMemoFAWaiver = 0D, sumOfCreditMemoWaiver = 0D, sumOfPayment = 0D;
		Double sumOfCreditMemoWriteOff = 0D, sumOfCreditMemoRefund = 0D, sumOfNSF = 0D, sumOfChargeBack = 0D,sumOfCreditMemoADJ = 0D;
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		String sql = "select p.amount, p.type from payment p inner join invoice i on p.InvoiceId = i.InvoiceId where i.InvoiceId=? AND p.status='Success'";
		List<Map<String,Object>> objs = jdbcTemplate.queryForList(sql,invoice.get("InvoiceId").toString());
		for (Map obj : objs) {
			
			switch (PaymentTypeEnum.getEnumByString(obj.get("type").toString())) {
				
				case CreditMemoFAWaiver:
					sumOfCreditMemoFAWaiver += Double.parseDouble(obj.get("amount").toString());
					break;
				case CreditMemoWaiver:
					sumOfCreditMemoWaiver += Double.parseDouble(obj.get("amount").toString());
					break;
				case CreditMemoRefund:
					sumOfCreditMemoRefund += Double.parseDouble(obj.get("amount").toString());
					break;
				case CreditMemoWriteOff:
					sumOfCreditMemoWriteOff += Double.parseDouble(obj.get("amount").toString());
					break;
				case Debit:
					sumOfDebitPayment += Double.parseDouble(obj.get("amount").toString());
					break;
				case NSF:
					sumOfNSF += Double.parseDouble(obj.get("amount").toString());
					break;
				case ChargeBack:
					sumOfChargeBack += Double.parseDouble(obj.get("amount").toString());
					break;
				case CreditMemoADJ:
				    sumOfCreditMemoADJ += Double.parseDouble(obj.get("amount").toString());
				case Payment:
					sumOfPayment += Double.parseDouble(obj.get("amount").toString());
				default:
					break;
			} 
		}	
		trueInvoiceValue = (Double.parseDouble(invoice.get("amount").toString()) + sumOfCreditMemoADJ) - (sumOfCreditMemoWaiver + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff);		
		//(Inv Amt + CM/FA waiver + CM/Write Off + CM / Waiver + CM / Adj) – (Payment (previously DEBIT) + NSF + Charge Back + CM / Refund)
		openBalanceOnInvoice = (Double.parseDouble(invoice.get("amount").toString()) + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff + sumOfCreditMemoWaiver + sumOfCreditMemoADJ )
								- (sumOfNSF + sumOfChargeBack + sumOfCreditMemoRefund + sumOfPayment);
		
		return openBalanceOnInvoice;
	
	}
	
	@Override
	public Boolean checkDupeByInvoiceIdAndDueDateAndSource(Map<String, Object> invoice, JobSchedulerMetadata jobSchedulerMetadata) {
		Boolean isDupe = false;
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		//Calendar cal = Calendar.getInstance();
		//cal.setTime(new Date(invoice.get("DueDate").toString()));
		String sql = "select p.InvoiceId from payment p where p.InvoiceId=? AND p.dueDate=? AND p.Source_c=? and type='Payment'";
		
		try{
			String value = jdbcTemplate.queryForObject(sql, String.class,
					invoice.get("InvoiceId"),
					invoice.get("DueDate"),
					Constants.PAYMENT_BATCH_SOURCE);
			System.out.println("dupe found with id-->> " + value);
			if(value!=null)
				isDupe = true;
		}
		catch(Exception e){
			System.out.println("Exception-->> " + e);
		}
		
		return isDupe;
	}
	
	@Override
	public void updatePaymentStatus(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata, String status) {
		//Boolean isUpdated = false;
		
		int i = 0;
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		try{
			 String sql = "UPDATE payment SET "
			    		+ "JetPayStatus_c =?"
			    		+ "where paymentId=?";

				i = jdbcTemplate.update(sql, 
						status,
						data.get("paymentId").toString()
						);
		}
		catch(Exception e){
			System.out.println("Exception-->> " + e);
		}
		
		//return isUpdated;
	}
	
	@Override
	public void updatePaymentStatusAndErrorMessage(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata, String status, String errorMessage) {
		//Boolean isUpdated = false;
		
		int i = 0;
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		try{
			 String sql = "UPDATE payment SET "
			    		+ "JetPayStatus_c =?, ReasonforFailureInJetPay=?"
			    		+ "where paymentId=?";

				i = jdbcTemplate.update(sql, 
						status,
						errorMessage,
						data.get("paymentId").toString()
						);
		}
		catch(Exception e){
			System.out.println("Exception-->> " + e);
		}
		
		//return isUpdated;
	}
	
	@Override
	public JetPayPayment processJetpayACHPayment(Map<String, Object> data,
			String payAmount, JobSchedulerMetadata jobSchedulerMetadata) throws UnsupportedEncodingException,
			FactoryConfigurationError {
		HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);    

		int amtToJetPay = (int) (Float.parseFloat(payAmount) * 100);
		
		StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
		inputXMLStrBuffer.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");
		inputXMLStrBuffer.append("<TransactionType>CHECK</TransactionType>");
		inputXMLStrBuffer.append("<TerminalID>"+ Constants.JETPAY_TERMINAL_ID +"</TerminalID>");
		inputXMLStrBuffer.append("<Origin> INTERNET </Origin>");
		inputXMLStrBuffer.append("<TotalAmount> "+ amtToJetPay +" </TotalAmount>");
		//inputXMLStrBuffer.append("<FeeAmount>100</FeeAmount>");
		inputXMLStrBuffer.append("<CardName> " + data.get("full_name").toString() +" </CardName>");
		inputXMLStrBuffer.append("<Token> " +data.get("token_number").toString()+ "</Token>");
		inputXMLStrBuffer.append("<ACH>");
		inputXMLStrBuffer.append("<CheckNumber> "+ data.get("check_number").toString() +" </CheckNumber>");
		inputXMLStrBuffer.append("</ACH>");
		inputXMLStrBuffer.append("</JetPay>");
		post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
		post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");

		// Get HTTP client
		HttpClient httpclient = HttpClients.createDefault();

		// Execute request
		JetPayPayment jetPayPayment =  new JetPayPayment();
		try {

			HttpResponse httpResponse =  httpclient.execute(post);
			
			// Display status code
			//System.out.println("Response status code: " + result);

			// Display response
			//System.out.println("Response body: ");
			//System.out.println(post.getResponseBodyAsString());
			
			String xml = EntityUtils.toString(httpResponse.getEntity());           
			InputSource source = new InputSource(new StringReader(xml));

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(source);

			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();

			jetPayPayment.setTransId(xpath.evaluate("/JetPayResponse/TransactionID", document));
			jetPayPayment.setOrderNumber(xpath.evaluate("/JetPayResponse/TransactionID", document));
		    jetPayPayment.setActCode(xpath.evaluate("/JetPayResponse/ActionCode", document));
		    jetPayPayment.setResponseText(xpath.evaluate("/JetPayResponse/ResponseText", document));
		    jetPayPayment.setAmount(amtToJetPay);
		    //jetPayPaymentDao.save(jetPayPayment);
		    
		    int i = 0;
		    JdbcTemplate jdbcTemplate = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata);
		    
		    String sql = "INSERT INTO payment_audit_log (transId,orderNumber,actCode,responseText,amount) VALUES (?,?,?,?,?)";

			i = jdbcTemplate.update(sql, 
					xpath.evaluate("/JetPayResponse/TransactionID", document),
					xpath.evaluate("/JetPayResponse/TransactionID", document),
					xpath.evaluate("/JetPayResponse/ActionCode", document),
					xpath.evaluate("/JetPayResponse/ResponseText", document),
					amtToJetPay);
		    
		} catch(Exception e){
			log.error("Errror while processing ACH JeyPay ",e);
			
		} finally {
			post.releaseConnection();
		}
		
		return jetPayPayment;
	}
	
	@Override
	public JetPayPayment processCCjetPayTransaction(Map<String, Object> data, String totalAmount, JobSchedulerMetadata jobSchedulerMetadata) throws Exception {
		int amtToJetPay = (int) (Float.parseFloat(totalAmount) * 100);
		// Prepare HTTP post
	    HttpPost post = new HttpPost(Constants.JETPAY_XML_SCHEMA_URL);	    
	    StringBuffer inputXMLStrBuffer = new StringBuffer("<JetPay>");
	    inputXMLStrBuffer.append("<TransactionID>"+RandomStringUtils.randomAlphanumeric(18)+"</TransactionID>");	
	    inputXMLStrBuffer.append("<TransactionType>SALE</TransactionType>");       
	    inputXMLStrBuffer.append("<Token>"+data.get("token_number").toString()+"</Token>");
	    inputXMLStrBuffer.append("<TotalAmount>"+amtToJetPay+"</TotalAmount>");
	    inputXMLStrBuffer.append("<TerminalID>"+ Constants.JETPAY_TERMINAL_ID +"</TerminalID>");
	    inputXMLStrBuffer.append("</JetPay>");	    
	    post.setEntity(new StringEntity(inputXMLStrBuffer.toString()));
	    post.addHeader("Content-type", "text/xml; charset=ISO-8859-1");
	    // Get HTTP client
	    HttpClient httpclient = HttpClients.createDefault();
	    // Execute request
	    JetPayPayment jetPayPayment =  new JetPayPayment();
	    try {
	    	HttpResponse httpResponse =  httpclient.execute(post);
	        String xml = EntityUtils.toString(httpResponse.getEntity());  
	        if(xml != null){
	        	
	            InputSource source = new InputSource(new StringReader(xml));
	
	            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	            DocumentBuilder db = dbf.newDocumentBuilder();
	            Document document = db.parse(source);
	
	            XPathFactory xpathFactory = XPathFactory.newInstance();
	            XPath xpath = xpathFactory.newXPath();  
	            
	            jetPayPayment.setTransId(xpath.evaluate("/JetPayResponse/TransactionID", document));
	            jetPayPayment.setOrderNumber(xpath.evaluate("/JetPayResponse/TransactionID", document));
	            jetPayPayment.setActCode(xpath.evaluate("/JetPayResponse/ActionCode", document));
	            jetPayPayment.setResponseText(xpath.evaluate("/JetPayResponse/ResponseText", document));
	            jetPayPayment.setAmount(amtToJetPay);
	            //jetPayPaymentDao.save(jetPayPayment);
            
	            int i = 0;
			    JdbcTemplate jdbcTemplate = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata);
			    
			    String sql = "INSERT INTO payment_audit_log (transId,orderNumber,actCode,responseText,amount) VALUES (?,?,?,?,?)";

				i = jdbcTemplate.update(sql, 
						xpath.evaluate("/JetPayResponse/TransactionID", document),
						xpath.evaluate("/JetPayResponse/TransactionID", document),
						xpath.evaluate("/JetPayResponse/ActionCode", document),
						xpath.evaluate("/JetPayResponse/ResponseText", document),
						amtToJetPay);
	        }
	    } catch(Exception e){
	    	log.error("Error in processCCjetPayTransaction ",e);
	    	
	    } finally {
	        post.releaseConnection();
	    }
		return jetPayPayment;
	}
	
	
	@Override
	public void savePayment(Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata, Double amount, Long invoiceId, String paymentType, String paymentStatus) {
		
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		
		int i = 0;
		Calendar cal = Calendar.getInstance();
	    String sql = "INSERT INTO payment ("
	    		+ "paymentMode, "
	    		+ "accountId, "
	    		+ "contact_id, "
	    		+ "type, "
	    		+ "amount, "
	    		+ "signupId, "
	    		+ "InvoiceId, "
	    		+ "payerId, "
	    		+ "paymentDate, "
	    		+ "paymentMethod, "
	    		+ "Source_c, "
	    		+ "lastUpdated, "
	    		+ "JetPayStatus_c"
	    		+ " ) VALUES ( "
	    		+ " ?,?,?,?,?,?,?,?,?,?,?,?,?)";

		i = jdbcTemplate.update(sql, 
				data.get("payment_mode").toString(),
				data.get("accountId").toString(),
				data.get("contact_id").toString(),
				paymentType,
				amount,
				data.get("signupId").toString(),
				invoiceId,
				data.get("payerId").toString(),
				new Date(),
				data.get("paymentmethod_id").toString(),
				Constants.PAYMENT_BATCH_SOURCE,
				cal,
				paymentStatus
				);
	}
}
