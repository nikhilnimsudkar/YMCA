package com.serene.job;

import static org.junit.Assert.*;

import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.serene.job.common.BatchJobContext;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.processor.InvoiceItemProcessor;
import com.serene.job.scheduler.AbstractJobScheduler;
import com.serene.job.scheduler.InitializeJobFromDbMetadata;
import com.serene.job.util.JobUtils;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.model.Invoice;
import com.ymca.model.User;
//import com.ymca.model.Invoice;
import com.ymca.model.PaymentMethod;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JobBootLoader.class)
public class InvoiceProcessorTestCase {

	private static Logger log = LoggerFactory.getLogger(InvoiceProcessorTestCase.class); 
	
	@Resource 
	protected BatchJobContext batchJobContext ;
	
	@Resource
	private InvoiceItemProcessor invoiceItemProcessor ;
	
	@Resource
	private InitializeJobFromDbMetadata initializeJobFromDbMetadata ;
	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao ; 
	
	@Resource
	private JobUtils jobUtils ; 
	
	@Resource
	protected ApplicationContext applicationContext;
	
	@Resource
	PaymentMethodDao paymentMethodDao;
	
	@Test
	public void getAll() {
		List<PaymentMethod> paymentList = paymentMethodDao.findAll();

	}
		
	@Test
	@Transactional
	public void processInvoice() throws Exception {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.getOne("DB_TO_DB");
		String sql = " select * from signup where signupId = 10395 limit 1";
		List<Map<String, Object>> objs = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).queryForList(sql); 
		Object item = invoiceItemProcessor.process(objs.get(0));
		log.debug(" item " + item);
	}
	
	
	private Map<String, Object> retrieveSignup(String usecaseName) {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		String signupSql = "select * from signup where contact_name=?";
		Map<String, Object> signupMap = jobUtils.getFromJdbcTemplate(
				jobSchedulerMetadata).queryForMap(signupSql, usecaseName);
		return signupMap;
	}

	private List<Map<String, Object>> retrievePayers(Object signupId) {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		String payerSql = "select * from payer where signupId=?";
		List<Map<String, Object>> payerRecords = jobUtils.getFromJdbcTemplate(
				jobSchedulerMetadata).queryForList(payerSql, signupId);
		return payerRecords;

	}

	private List<Map<String, Object>> retrieveInvoice(Object payerId) {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		String invoiceSql = "select * from invoice where payerId=?";
		List<Map<String, Object>> invoiceRecords = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata).queryForList(
						invoiceSql, payerId);
		return invoiceRecords;
	}

	private Date getNextBillDate(Map<String, Object> signupMap) {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Calendar clNextBillDate = Calendar.getInstance();
		clNextBillDate.setTime((Date) signupMap.get("NextBillDate_c"));

		String sql = "select keyValue from system_property where keyName = ? AND picklistName= ?";
		log.info("keyName of system_property-->> "
				+ (String) signupMap.get("signUpPricingOption"));
		String value = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(
						sql,
						String.class,
						(String) signupMap.get("signUpPricingOption"),
						com.serene.job.common.Constants.PickListName_SIGNUP_PRICING_OPTION);
		Integer integer = new Integer(value);
		clNextBillDate.add(Calendar.DAY_OF_YEAR, -integer);
		log.info("modifiedNextBillDate--->> " + clNextBillDate.getTime());
		return clNextBillDate.getTime();

	}

	private String makeInvoiceNumber(Object accountId, Object signupId,
			Object payerId, Date nextBillDate) {

		String invoiceNumber = ((Long) accountId).toString() + "-"
				+ ((Long) signupId).toString() + "-"
				+ ((Long) payerId).toString() + "-"
				+ new SimpleDateFormat("MMddyyyy").format(nextBillDate);

		return invoiceNumber;

	}

	private Double addExpiredPayerAmount(Object signupId) {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Double amount = new Double(0);

		String sql = "select SUM(amount) from payer where signupId = ? AND type='3rd Party' AND(DATE(start_date) IS NULL OR DATE(start_date)>DATE(?) OR DATE(end_date)<DATE(?))";

		Date currentDate = new Date();

		amount = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(sql, Double.class, signupId, currentDate,
						currentDate);
		log.info("Sum of amounts -->> " + amount);
		return amount;

	}

	Double computePromotionAmount(Object signupId){
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Double amount = new Double(0);

		String sql = "select RemainingDiscountAmount_c,MonthlyDiscountAmount_c from signup_promotion where signup_id = ?";

		Map<String,Object> signup_promotionMap  = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForMap(sql, signupId);
		Double RemainingDiscountAmount_c=(Double)signup_promotionMap.get("RemainingDiscountAmount_c");
		Double MonthlyDiscountAmount_c=(Double)signup_promotionMap.get("MonthlyDiscountAmount_c");
		amount=RemainingDiscountAmount_c-MonthlyDiscountAmount_c;
		log.info("Subtraction amounts -->> " + amount);
		return amount;
		
	}
	
	Double getMonthlyDiscountAmount_c(Object signupId){
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Double amount = new Double(0);

		String sql = "select MonthlyDiscountAmount_c from signup_promotion where signup_id = ?";

		
		Map<String,Object> signup_promotionMap  = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForMap(sql, signupId);
		
		Double MonthlyDiscountAmount_c=(Double)signup_promotionMap.get("MonthlyDiscountAmount_c");
		amount=MonthlyDiscountAmount_c;
		log.info("MonthlyDiscountAmount_c amounts -->> " + amount);
		return amount;
		
	}
	
	private Date currentDate() {
		Calendar cl = Calendar.getInstance();
		cl.set(Calendar.HOUR_OF_DAY, 0);
		cl.set(Calendar.MINUTE, 0);
		cl.set(Calendar.SECOND, 0);
		cl.set(Calendar.MILLISECOND, 0);

		Date invoiceDate = cl.getTime();
		return invoiceDate;
	}

	private void deleteInvoiceAndPayer(Object payerId) {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		String deleteInvoiceSql = "delete from invoice where payerId=?";
		int rowInvoice = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.update(deleteInvoiceSql, payerId);

		String deletePayerSql = "delete from Payer where id=?";
		int rowPayer = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.update(deletePayerSql, payerId);

	}

	private void deleteSignupAndItemDetail(Object signupId) {
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		///
         String signupSql = "select item_detail_id from signup where signupId=?";
		
		int itemDetailId = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(signupSql, Integer.class,
						signupId);
		///
		
		///
		
		String deleteSignup_PromotionSql = "delete from signup_promotion where signup_id=? and promotion_id=?";
		int rowSignup_Promotion = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.update(deleteSignup_PromotionSql, signupId,itemDetailId);
		///
		String deleteSignupSql = "delete from signup where signupId=?";
		int rowSignup = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.update(deleteSignupSql, signupId);
		
		
		String deleteItemDetailSql = "delete from item_detail where Id=?";
		int rowItemDetail = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.update(deleteItemDetailSql, itemDetailId);


	}

	
	@Test
	@Transactional
	public void testInvoice_UC3() throws Exception {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Map<String, Object> signupMap = retrieveSignup("TEST_USE_CASE_3");
		List<Map<String, Object>> payerRecords = retrievePayers(signupMap
				.get("signupId"));

		for (Map<String, Object> payer : payerRecords) {
			if (payer.get("type") != null
					&& (((String) payer.get("type")).equals("Self"))) {

				List<Map<String, Object>> invoiceRecords = retrieveInvoice(payer
						.get("id"));
				Date nextBillDate = getNextBillDate(signupMap);
				assertEquals(1, invoiceRecords.size());
				for (Map<String, Object> invoice : invoiceRecords) {
					assertNotNull(invoice.get("InvoiceNumber"));

					String invoiceNumber = makeInvoiceNumber(
							signupMap.get("accountId"),
							signupMap.get("signupId"), payer.get("id"),
							nextBillDate);
					assertEquals(invoiceNumber,
							(String) invoice.get("InvoiceNumber"));

					Double amount = addExpiredPayerAmount(signupMap
						.get("signupId"));
					Double invoiceAmount = (Double) payer.get("amount");
				if (amount != null)
						invoiceAmount = invoiceAmount + amount;
				//Double promotionAmount = computePromotionAmount(signupMap
					//	.get("signupId"));
				
				invoiceAmount=invoiceAmount-getMonthlyDiscountAmount_c(signupMap
						.get("signupId"));
					log.info("invoiceAmount -->> " + invoiceAmount);
					assertEquals(invoiceAmount, (Double)invoice.get("amount"));

					// assertEquals(new java.sql.Date( new Date().getTime()),
					// (java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(currentDate(),
							(java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(nextBillDate, (Date) invoice.get("BillDate"));
					assertEquals(nextBillDate, (Date) invoice.get("DueDate"));
					assertEquals(new Double(0), (Double)invoice.get("FAamount_c"));

					 deleteInvoiceAndPayer(payer.get("id"));
				}
			}else
			{
				List<Map<String, Object>> invoiceRecords = retrieveInvoice(payer
						.get("id"));
				Date nextBillDate = getNextBillDate(signupMap);
				assertEquals(1, invoiceRecords.size());
				for (Map<String, Object> invoice : invoiceRecords) {
					assertNotNull(invoice.get("InvoiceNumber"));

					String invoiceNumber = makeInvoiceNumber(
							signupMap.get("accountId"),
							signupMap.get("signupId"), payer.get("id"),
							nextBillDate);
					assertEquals(invoiceNumber,
							(String) invoice.get("InvoiceNumber"));

					
					Double invoiceAmount = (Double) payer.get("amount");
					log.info("invoiceAmount -->> " + invoiceAmount);
					assertEquals(invoiceAmount, (Double) invoice.get("amount"));

					assertEquals(currentDate(),
							(java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(nextBillDate, (Date) invoice.get("BillDate"));
					assertEquals(nextBillDate, (Date) invoice.get("DueDate"));
					assertEquals(new Double(0), (Double)invoice.get("FAamount_c"));

				
				deleteInvoiceAndPayer(payer.get("id"));
				}
			}
		}

		deleteSignupAndItemDetail(signupMap.get("signupId"));
	}

	
	
   @Test(expected = AssertionError.class)
	@Transactional
	public void testInvoice_UC2() throws Exception {

		
		Map<String, Object> signupMap = retrieveSignup("TEST_USE_CASE_2");
		List<Map<String, Object>> payerRecords = retrievePayers(signupMap
				.get("signupId"));

		for (Map<String, Object> payer : payerRecords) {

			List<Map<String, Object>> invoiceRecords = retrieveInvoice(payer
					.get("id"));
			assertEquals(1, invoiceRecords.size());

		}
	}
	
		

	@Test
	@Transactional
	public void testInvoice() throws Exception {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Map<String, Object> signupMap = retrieveSignup("TEST_USE_CASE_1");
		List<Map<String, Object>> payerRecords = retrievePayers(signupMap
				.get("signupId"));

		for (Map<String, Object> payer : payerRecords) {
			if (payer.get("type") != null
					&& (((String) payer.get("type")).equals("Self"))) {

				List<Map<String, Object>> invoiceRecords = retrieveInvoice(payer
						.get("id"));
				Date nextBillDate = getNextBillDate(signupMap);
				assertEquals(1, invoiceRecords.size());
				for (Map<String, Object> invoice : invoiceRecords) {
					assertNotNull(invoice.get("InvoiceNumber"));

					String invoiceNumber = makeInvoiceNumber(
							signupMap.get("accountId"),
							signupMap.get("signupId"), payer.get("id"),
							nextBillDate);
					assertEquals(invoiceNumber,
							(String) invoice.get("InvoiceNumber"));

					Double amount = addExpiredPayerAmount(signupMap
							.get("signupId"));
					Double invoiceAmount = (Double) payer.get("amount");
					if (amount != null)
						invoiceAmount = invoiceAmount + amount;
					log.info("invoiceAmount -->> " + invoiceAmount);
					assertEquals(invoiceAmount, (Double) invoice.get("amount"));

					// assertEquals(new java.sql.Date( new Date().getTime()),
					// (java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(currentDate(),
							(java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(nextBillDate, (Date) invoice.get("BillDate"));
					assertEquals(nextBillDate, (Date) invoice.get("DueDate"));
					assertEquals(new Double(0), (Double)invoice.get("FAamount_c"));

					 deleteInvoiceAndPayer(payer.get("id"));
				}
			}else
			{
				List<Map<String, Object>> invoiceRecords = retrieveInvoice(payer
						.get("id"));
				Date nextBillDate = getNextBillDate(signupMap);
				assertEquals(1, invoiceRecords.size());
				for (Map<String, Object> invoice : invoiceRecords) {
					assertNotNull(invoice.get("InvoiceNumber"));

					String invoiceNumber = makeInvoiceNumber(
							signupMap.get("accountId"),
							signupMap.get("signupId"), payer.get("id"),
							nextBillDate);
					assertEquals(invoiceNumber,
							(String) invoice.get("InvoiceNumber"));

					
					Double invoiceAmount = (Double) payer.get("amount");
					log.info("invoiceAmount -->> " + invoiceAmount);
					assertEquals(invoiceAmount, (Double) invoice.get("amount"));

					assertEquals(currentDate(),
							(java.sql.Date) invoice.get("InvoiceDate"));

					assertEquals(nextBillDate, (Date) invoice.get("BillDate"));
					assertEquals(nextBillDate, (Date) invoice.get("DueDate"));
					assertEquals(new Double(0), (Double)invoice.get("FAamount_c"));


				
				deleteInvoiceAndPayer(payer.get("id"));
				}
			}
		}

		deleteSignupAndItemDetail(signupMap.get("signupId"));
	}

	@Test
	@SuppressWarnings("unchecked")
	public void runJobByName() throws Exception{
		JobSchedulerMetadata j = jobSchedulerMetadataDao.findOne("DB_TO_DB_INVOICE_GENRATION") ;
		
		AbstractJobScheduler jobScheduler =  (AbstractJobScheduler) applicationContext.getBean(InitializeJobFromDbMetadata.interfaceTypes.get(j.getInterfaceType()));
		jobScheduler.setJobName(j.getJobName());
		jobScheduler.loadJob();
		jobScheduler.run();

	}
	
	
	@Test
	@Transactional
	public void saveSignupPayerUC3() throws Exception {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, 2);
		Date next2Days = cl.getTime();
		Calendar cl2 = Calendar.getInstance();
		cl2.add(Calendar.DAY_OF_YEAR, 1);
		Date next1Day = cl2.getTime();
		Calendar cl3 = Calendar.getInstance();
		cl3.add(Calendar.DAY_OF_YEAR, -30);
		Date past30Days = cl3.getTime();

		int numberOfItemDetail = saveItemDetailRecord(3,"TEST_USE_CASE_3", 2,
				"Sign Up", jobSchedulerMetadata, 1);
		String itemDetailIdSql = "select Id from item_detail where RecordName=?";
		int itemDetailId = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(itemDetailIdSql, Integer.class,
						"TEST_USE_CASE_3");

		int numberOfSingup = saveSignupRecord(next2Days, "TEST_USE_CASE_3",
				next2Days, next1Day, next2Days, "Weekly", "ACTIVE",
				itemDetailId, 0d, next2Days, next1Day, 8.0, past30Days,
				jobSchedulerMetadata);
		assertEquals(1, numberOfSingup);

		String sql = "select signupId from signup where contact_name=?";
		int signupId = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(sql, Integer.class, "TEST_USE_CASE_3");

		int numberOfPayer = savePayerRecord(1450, 450, next2Days, past30Days,
				"Self", signupId, jobSchedulerMetadata, 1);
		assertEquals(1, numberOfPayer);
		
		
		int numberOfSignup_Promotion = saveSignup_PromotionRecord(50, 100, itemDetailId, signupId,jobSchedulerMetadata);
		assertEquals(1, numberOfSignup_Promotion);
		

	}
	
	
	
	@Test
	@Transactional
	public void saveSignupPayerUC2() throws Exception {
		
		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao.getOne("DB_TO_DB_INVOICE_GENRATION");
		Calendar cl=Calendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, 2);
		Date next2Days=cl.getTime();
		Calendar cl2=Calendar.getInstance();
		cl2.add(Calendar.DAY_OF_YEAR, 1);
		Date next1Day=cl2.getTime();
		Calendar cl3=Calendar.getInstance();
		cl3.add(Calendar.DAY_OF_YEAR, -30);
		Date past30Days=cl3.getTime();
		
		int numberOfItemDetail= saveItemDetailRecord(2,"TEST_USE_CASE_2",2,"SignUp",jobSchedulerMetadata,1);
		 String itemDetailIdSql="select Id from item_detail where RecordName=?";
		 int itemDetailId=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).queryForObject(itemDetailIdSql, Integer.class, "TEST_USE_CASE_2");
		 
	int numberOfSingup	=saveSignupRecord(next2Days,"TEST_USE_CASE_2",next2Days,next1Day,next2Days,"Weekly","NONACTIVE",itemDetailId,0d,next2Days,next1Day,8.0,past30Days,jobSchedulerMetadata);
		assertEquals(1,numberOfSingup);
		
		 String sql="select signupId from signup where contact_name=?";
		 int signupId=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).queryForObject(sql, Integer.class, "TEST_USE_CASE_2");
		
		
	int numberOfPayer	=	savePayerRecord(1450,450,next2Days,past30Days,"Self",signupId,jobSchedulerMetadata,2);
	    assertEquals(2,numberOfPayer);
	
	}
	
	
	@Test
	@Transactional
	public void saveSignupPayer() throws Exception {

		JobSchedulerMetadata jobSchedulerMetadata = jobSchedulerMetadataDao
				.getOne("DB_TO_DB_INVOICE_GENRATION");
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DAY_OF_YEAR, 2);
		Date next2Days = cl.getTime();
		Calendar cl2 = Calendar.getInstance();
		cl2.add(Calendar.DAY_OF_YEAR, 1);
		Date next1Day = cl2.getTime();
		Calendar cl3 = Calendar.getInstance();
		cl3.add(Calendar.DAY_OF_YEAR, -30);
		Date past30Days = cl3.getTime();

		int numberOfItemDetail = saveItemDetailRecord(1,"TEST_USE_CASE_1", 2,
				"Sign Up", jobSchedulerMetadata, 1);
		String itemDetailIdSql = "select Id from item_detail where RecordName=?";
		int itemDetailId = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(itemDetailIdSql, Integer.class,
						"TEST_USE_CASE_1");

		int numberOfSingup = saveSignupRecord(next2Days, "TEST_USE_CASE_1",
				next2Days, next1Day, next2Days, "Weekly", "ACTIVE",
				itemDetailId, 0d, next2Days, next1Day, 8.0, past30Days,
				jobSchedulerMetadata);
		assertEquals(1, numberOfSingup);

		String sql = "select signupId from signup where contact_name=?";
		int signupId = jobUtils.getFromJdbcTemplate(jobSchedulerMetadata)
				.queryForObject(sql, Integer.class, "TEST_USE_CASE_1");

		int numberOfPayer = savePayerRecord(1450, 450, next2Days, past30Days,
				"Self", signupId, jobSchedulerMetadata, 2);
		assertEquals(2, numberOfPayer);

	}

	
	private  int saveSignup_PromotionRecord(double MonthlyDiscountAmount_c,double RemainingDiscountAmount_c,int promotion_id,int signup_id,JobSchedulerMetadata jobSchedulerMetadata) {
		 
		
	    Object[] params = new Object[] {MonthlyDiscountAmount_c,RemainingDiscountAmount_c, promotion_id, signup_id};
	 
	    int[] types = new int[] { Types.DOUBLE,Types.DOUBLE, Types.BIGINT,Types.BIGINT};
	 
	
	  	   int row=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).update(insertSignup_PromotionSql, params, types);
	       return row;
	   }
  
private static final String insertSignup_PromotionSql =
"insert  into `signup_promotion`(`DiscountAmount_c`,`MonthlyDiscountAmount_c`,`OverrideBy_c`,`OverrideDate_c`,`OverrideReason_c`,`PromoCode_c`,          `RemainingDiscountAmount_c`,`promotion_id`,`signup_id`)"+
                        "values ( 132.5,             ?,                        NULL,          NULL,            NULL,              'NEWMEM-WV-MEMFEE-001', ?,                          ?,             ?)";
	
	
	
private  int savePayerRecord(int paymentId,double amount,Date end_date,Date start_date,String type,int signupId,JobSchedulerMetadata jobSchedulerMetadata,int numberOfPayers) {
			 
		   Object[] params = new Object[] { paymentId, amount,end_date, start_date,type,signupId};
		   int[] types = new int[] { Types.BIGINT, Types.DOUBLE, Types.DATE,
				Types.DATE, Types.VARCHAR,Types.BIGINT};
		 
		      int i=0;
		      while(i<numberOfPayers){
		    	
		  	 int row=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).update(insertPayerSql, params, types);
		     
		      params[0]=paymentId+1;
		      params[4]="3rd Party";
		      i++;
		      }
		      return i;
		   }
	
private static final String insertPayerSql = "insert into `payer` (`paymentId`, `amount`, `end_date`, `start_date`, `type`, `accountId`, `paymentmethodId`, `signupId`, `paymentMode`, `sc_id`, `lastUpdated`)"+
	  "values(?,?,?,?,?,'1',NULL,?,NULL,'1','2015-03-01 02:10:23')";
	  
	  
	  
private  int saveSignupRecord(Date cancel_date,String contact_name,Date hold_End_Date,Date hold_Start_Date,Date program_end_date,String signUpPricingOption,String status,int item_detail_id,Double FAamount_c,Date FAendDate_c, Date FAstartDate_c, Double HoldFee_c, Date NextBillDate_c,JobSchedulerMetadata jobSchedulerMetadata) {
		 
		
		    Object[] params = new Object[] { cancel_date,contact_name, hold_End_Date, hold_Start_Date,program_end_date,signUpPricingOption,status,item_detail_id,FAamount_c,FAendDate_c,FAstartDate_c,HoldFee_c,NextBillDate_c };
		 
		    int[] types = new int[] { Types.DATE,Types.VARCHAR, Types.DATE,
				Types.DATE, Types.DATE, Types.VARCHAR, Types.VARCHAR,
				Types.BIGINT,Types.DOUBLE, Types.DATE, Types.DATE, Types.DOUBLE,
				Types.DATE };
		 
		
		  	   int row=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).update(insertSql, params, types);
		       return row;
		   }
	  
private static final String insertSql =

	" insert into `signup` ( `sc_id`, `cancel_date`, `contact_name`, `Deposit`, `DiscountAmount_c`, `final_amount`, `hold_End_Date`, `hold_Start_Date`, `itemDaysMapId`, `membership_fee_next_billing__date`, `oppty_id`, `payType`, `program_end_date`, `program_start_date`, `RegistrationFee`, `SetUpFee`, `signUpName`, `signUpPricingOption`, `sign_up_product_type`, `signup_date`, `status`, `totalAmount`, `type`, `waitlist`, `contact_id`, `accountId`, `item_detail_id`, `location`, `paymentmethod_id`, `discountamount`, `FAamount_c`, `FAendDate_c`, `FApercentage_c`, `FAstartDate_c`, `signupPrice`, `PaymentFrequency`, `lastUpdated`, `noofTickets`, `HoldFee_c`, `JoinFee_c`, `specialRequest_c`, `PortalLastModifiedBy_c`, `BillingOption_c`, `GradeLevel_c`, `SelectedStartDate_c`, `parent_signup_id`, `Campaigner_c`, `CompanyAddress_c`, `CompanyName_c`, `CompanyPhone_c`, `Employee_c`, `EmployeeEmailId_c`, `EmployeeSignUp_c`, `Employer_c`, `RecognizeAs_c`, `NextBillDate_c`, `PledgeAmount_c`, `PledgeAmountFrequency_c`, `Activity1`, `Activity10`, `Activity2`, `Activity3`, `Activity4`, `Activity5`, `Activity6`, `Activity7`, `Activity8`, `Activity9`, `Priority1`, `Priority10`, `Priority2`, `Priority3`, `Priority4`, `Priority5`, `Priority6`, `Priority7`, `Priority8`, `Priority9`, `InHonorOf_c`, `OtherCampaigner_c`, `MembershipAgeCategory_c`)" +
	               " values('300000003533224',  ?,           ?,       '75',     NULL,               '75',           ?,               ?,                 NULL,           '2015-03-23',                         NULL,       NULL,      ?,                  '2015-02-23',         '0',               '75',       NULL,         ?,                     'All Branches',         '2015-02-23',  ?,        NULL,          'MEMBERSHIP',NULL, '23',          '203',        ?,                NULL,       '26',               NULL,             ?,            ?,             NULL,             ?,              '0',            NULL,              '2015-03-09 06:36:05',NULL,    ?,           NULL,        NULL,               NULL,                     NULL,              NULL,           NULL,                  NULL,               NULL,           NULL,               NULL,            NULL,             NULL,         NULL,                NULL,               NULL,         NULL,            ?,                NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);";



private  int saveItemDetailRecord(int iD,String RecordName,int RecurringPeriod_c,String PromotionRuleType_c,JobSchedulerMetadata jobSchedulerMetadata,int numberOfItems) {
	 
	   Object[] params = new Object[] {iD,RecordName, RecurringPeriod_c,PromotionRuleType_c};
	   int[] types = new int[] {Types.BIGINT, Types.VARCHAR,Types.INTEGER,Types.VARCHAR};
	 
	      int i=0;
	      while(i<numberOfItems){
	    	
	  	 int row=   jobUtils.getFromJdbcTemplate(jobSchedulerMetadata).update(insertItemDatialSql, params, types);
	     
	    
	      i++;
	      }
	      return i;
	   }



private static final String insertItemDatialSql=
"insert  into `item_detail`(`Id`,`RecordName`, `ActualCapacity_c`,`AutomatedWaitlist_c`,`AutomaticPayment_c`,`BillingRules_c`,`CancellationCutOffPeriod_c`,`ClassID_c`,`Description_c`,`Direction_c`,`DonationManager_c`,`DueDateOffset_c`,`Duration_c`,`EndDate_c`,`EndTime_c`,`FAEligible_c`,`FacilityManager_c`,`FinancialAid_c`,`Gender_c`,`locationId`,`LodgingType_c`,`MaxAdultsPerMembership_c`,`MaxAge_c`,`MaxHoldPeriod_c`,`MemberRegistrationEndDate_c`,`MemberRegistrationStartDate_c`,`MinAge_c`,`MinHoldPeriod_c`,`NonMemberRegistrationEndDate_c`,`NonMemberRegistrationStartDate_c`,`noofdaysinschoolyear`,`noofmonthsinschoolyear`,`PackageSize_c`,`PaymentPlan_c`,`PreRequisities_c`,`price`,`ProgramDirector_c`,`RecurringPeriod_c`,`registrationPrice`,`RegistrationStartDate`,`RemainingCapacity_c`,`SchoolDaysPerYear_c`,`StartDate_c`,`StartTime_c`,`Status_c`,`SubType_c`,`TransportationLocation_c`,`Type_c`,`WaitlistCounter_c`,`WebCapacity_c`,`waivers_and_tc_id_FK`,`RequiresSetUpFee`,`dueDate`,`DueDate_c`,`actualRemainingCapacity_c`,`RequiresHealthHistory`,`lastUpdated`,`subCategory_c`,`Category_c`,`RequiresEmergencyContact_c`,`RequiresAuthorisedContact_c`,`FutureCancellationAllowed_c`,`allowToPickGrade_c`,`allowToPickStartDate_c`,`NeedTC_c`,`AllowMinimumPaymentBasedOn_c`,`BillDate_c`,`BillDateOffset_c`,`PreventDuplicateSignup_c`,`AssignTo_c`,`RequiresRegistrationFee`,`AutoPromotion_c`,`PromoApplicableToAllContacts_c`,`PromoCode_c`,`PromotionDiscountValue_c`,`PromotionRuleType_c`,`PromotionType_c`,`PromotionExpression_c`,`PromotionExpressionOperator_c`,`GenerateRecurringInvoicesAtTimeOfSignUp_c`)"+ 
                   "values ( ?,    ?,             NULL,               '',                  'No',                NULL,            0,                           NULL,       NULL,           NULL,         NULL,               0,                NULL,        '2015-06-19','17:00:00',NULL,          NULL,               NULL,            'M, F',    2899,        NULL,           NULL,                      18,        NULL,             '2015-06-12',                '2015-03-12',                    2,         NULL,            '2015-06-07',                    '2015-03-12',                       NULL,                  NULL,                    NULL,           'No',           NULL,              NULL,   NULL,               ?,                  NULL,               NULL,                   NULL,                 NULL,                '2015-04-15', '15:00:00',   'ACTIVE',   NULL,       NULL,                     'Camp',   NULL,               NULL,           NULL,                  NULL,              NULL,     'Bill Date',NULL,                       NULL,                   NULL,        'Activity',     'Residence Camp Activity',0,               0,                            NULL,                         NULL,               'No',                    'No',       NULL,                          NULL,        NULL,              NULL,                      NULL,       '',                       NULL,             NULL,                            NULL,         NULL,                      ?,                    NULL,             NULL,                   NULL,                           NULL)";
}
