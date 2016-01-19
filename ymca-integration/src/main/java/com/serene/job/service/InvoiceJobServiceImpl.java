/**
 * @author gpatwa
 * This is implementation class of InvoiceService
 */

package com.serene.job.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;

import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.util.JobUtils;
import com.ymca.model.Account;
import com.ymca.model.Invoice;
import com.ymca.model.Payer;
import com.ymca.model.Signup;
import com.ymca.model.User;

@Service
public class InvoiceJobServiceImpl implements InvoiceService {

	private static final Log log = LogFactory
			.getLog(InvoiceJobServiceImpl.class);

	@Resource
	private JobUtils jobUtils;
	private Calendar cal = Calendar.getInstance();
	
	@Override
	public Double computeFAAmount(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {
		Double fAamount = new Double(0);
		Date startDate = (Date) signUp.get("FAstartDate_c");
		Date endDate = (Date) signUp.get("FAendDate_c");
		if (signUp.get("type") != null && signUp.get("type").equals("Self")) {
			if ((startDate != null && startDate.before(new Date()))
					&& (endDate != null && endDate.after(new Date()))) {

				if(signUp.get("FAamount_c")!=null){
				//String s = (String) signUp.get("FAamount_c");
				
				fAamount = (Double)signUp.get("FAamount_c");
				}

			}

		}

		return fAamount;
	}

	@Override
	public Double computeInvoiceAmount(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {

		Double invoiceAmount = null;
		if (signUp.get("type") != null && signUp.get("type").equals("Self")) {
			Double amount = (double) 0;
			JdbcTemplate jdbcTemplate = jobUtils
					.getFromJdbcTemplate(jobSchedulerMetadata);

			if (signUp.get("hold_Start_Date") != null
					&& signUp.get("hold_End_Date") != null
					&& ((Date) signUp.get("hold_Start_Date"))
							.before(new Date())
					&& ((Date) signUp.get("hold_End_Date")).after(new Date())) {
				invoiceAmount = (Double) signUp.get("HoldFee_c");
			} else {
				
				invoiceAmount = (Double) signUp.get("amount") ;

				String sql = "select SUM(amount) from payer where signupId = ? AND type='3rd Party' AND(DATE(start_date) IS NULL OR DATE(start_date)>DATE(?) OR DATE(end_date)<DATE(?))";

				Date currentDate = new Date();

				amount = jdbcTemplate.queryForObject(sql, Double.class,
						signUp.get("signupId"), currentDate, currentDate);
				log.info("Sum of amounts -->> " + amount);
				if (amount != null)
					invoiceAmount = invoiceAmount + amount;
				log.info("invoiceAmount -->> " + invoiceAmount);
			}
			// Here put Recurring promo logic

			String sqlSignupPromotion = "select * from signup_promotion where signup_id = ?";

			List<Map<String, Object>> objs = jdbcTemplate.queryForList(
					sqlSignupPromotion, signUp.get("signupId"));
			int count = 0;
			String sqlItemDetail = "select COUNT(Id) from item_detail where Id = ? AND PromotionRuleType_c= ? AND RecurringPeriod_c > ?";
			Double promoAmount = (double) 0;
			Double sumOfMonthlyDiscountAmount = (double) 0;
			Double monthlyDiscountAmount = (double) 0;
			int i = 0;
			for (Map<String, Object> p : objs) {
				if (p != null && p.get("promotion_id") != null)
					count = jdbcTemplate.queryForObject(sqlItemDetail,
							Integer.class, p.get("promotion_id"), "Sign Up", 1);

				log.info("count of existing item_detail records-->> " + count
						+ " with promotion_id -->>  " + p.get("promotion_id"));
				if (count >= 1 && p.get("RemainingDiscountAmount_c") != null
						&& p.get("MonthlyDiscountAmount_c") != null) {
					log.info("RemainingDiscountAmount -->>  "
							+ (Double) p.get("RemainingDiscountAmount_c"));
					log.info("MonthlyDiscountAmount -->>  "
							+ (Double) p.get("MonthlyDiscountAmount_c"));
					promoAmount = (Double) p.get("RemainingDiscountAmount_c")
							- (Double) p.get("MonthlyDiscountAmount_c");
					if (promoAmount > 0) {
						String sql = "UPDATE signup_promotion SET RemainingDiscountAmount_c =? where id=?";

						i = jdbcTemplate.update(sql, promoAmount, p.get("id"));
						log.info("i-->> " + i);
						monthlyDiscountAmount = (Double) p
								.get("MonthlyDiscountAmount_c");
						sumOfMonthlyDiscountAmount += monthlyDiscountAmount;

					} else {
						String sql = "UPDATE signup_promotion SET RemainingDiscountAmount_c =? where id=?";

						i = jdbcTemplate.update(sql, 0, p.get("id"));
						log.info("i-->> " + i);

					}

				}
			}
			log.info("invoiceAmount -->>  " + invoiceAmount);
			log.info("sumOfMonthlyDiscountAmount -->>  "
					+ sumOfMonthlyDiscountAmount);
			if ((invoiceAmount - sumOfMonthlyDiscountAmount) > 0) {
				invoiceAmount = invoiceAmount - sumOfMonthlyDiscountAmount;
			} else {
				invoiceAmount = 0d;
			}
			log.info("At last invoiceAmount -->>  " + invoiceAmount);

		} else {
			invoiceAmount = (Double) signUp.get("amount");
			log.info("3rd Party invoiceAmount--->> " + invoiceAmount);
		}

		return invoiceAmount;
	}

	@Override
	public Double computePromoAmount(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isInvoiceRecordExist(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {

		Boolean b = false;
		Integer count = (int) 0;
	JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);

		String sql = "select COUNT(InvoiceNumber) from invoice where InvoiceNumber = ?";

		count = jdbcTemplate.queryForObject(sql, Integer.class,
				signUp.get("RecordName"));
		log.info("count of existing invoice records-->> " + count
				+ " with invoice number -->>  " + signUp.get("RecordName"));
		if (count >= 1) {
			b = true;
		}

		return b;
	}

	@Override
	public Date computeDueDate(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {
	
		Date computeDueDate = null;
		log.info("DueDate_c-->> " + signUp.get("DueDate_c"));
		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);
		if (signUp.get("DueDate_c") != null) {
			String sql = "select keyValue from system_property where keyName = ? AND picklistName= ?";
			
			String value = jdbcTemplate.queryForObject(sql, String.class,
					signUp.get("DueDate_c"),
					com.serene.job.common.Constants.PickListName_ITEM_DETAIL_DUE_DATE_C);
			log.info("value of system_property-->> " + value);
			Date dueDate = (Date) signUp.get(value);
		
			int dueDateOffset = 0;
			cal.setTime(dueDate);
			if (signUp.get("DueDateOffset_c") != null) {
				dueDateOffset = (int) signUp.get("DueDateOffset_c");
				}
			log.info("dueDateOffset-->> " + dueDateOffset);
			
			cal.add(Calendar.DAY_OF_YEAR, dueDateOffset);
			computeDueDate = cal.getTime();

			log.info("calculatedNextBillDate--->> " + cal.getTime());

		}

		return computeDueDate;
	}

	
	@Override
	public void updateSignupRecord(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata) {
		Date d = null;
	
		String signUpPricingOption = (String) signUp.get("signUpPricingOption");

		if ((signUp.get("NextBillDate_c") != null)) {
			d = ((Date) signUp.get("NextBillDate_c"));
			log.info("NextBillDate_c-->>> " + d);
			cal.setTime(d);

		}

		Date modifiedNextBillDate = modifyNextBillDate(cal,
				signUpPricingOption, jobSchedulerMetadata);

		if (signUp.get("program_end_date") == null) {
			Date calculatedNextBillDate = calculateNextBillDate(signUp, cal,
					signUpPricingOption, modifiedNextBillDate,
					jobSchedulerMetadata);
			updateSignupRecordWithJdbc(signUp, jobSchedulerMetadata,
					calculatedNextBillDate);
		} else if ((signUp.get("program_end_date") != null)
				&& (modifiedNextBillDate != null && (modifiedNextBillDate
						.before((Date) signUp.get("program_end_date")))))

		{
			Date calculatedNextBillDate = calculateNextBillDate(signUp, cal,
					signUpPricingOption, modifiedNextBillDate,
					jobSchedulerMetadata);
			updateSignupRecordWithJdbc(signUp, jobSchedulerMetadata,
					calculatedNextBillDate);
		} else {
			updateSignupRecordWithJdbc(signUp, jobSchedulerMetadata, null);
		}

	}

	private void updateSignupRecordWithJdbc(Map<String, Object> signUp,
			JobSchedulerMetadata jobSchedulerMetadata,
			Date calculatedNextBillDate) {
		int i = 0;

		JdbcTemplate jdbcTemplate = jobUtils

		.getFromJdbcTemplate(jobSchedulerMetadata);

		String sql = "UPDATE signup SET NextBillDate_c =? where signupId=?";

		i = jdbcTemplate.update(sql, calculatedNextBillDate,
				signUp.get("signupId"));
		log.info("i-->> " + i);
	}

	private Date modifyNextBillDate(Calendar cal, String signUpPricingOption,
			JobSchedulerMetadata jobSchedulerMetadata) {

		Date modifiedNextBillDate = null;

		JdbcTemplate jdbcTemplate = jobUtils
				.getFromJdbcTemplate(jobSchedulerMetadata);

		String sql = "select keyValue from system_property where keyName = ? AND picklistName= ?";
		log.info("keyName of system_property-->> " + signUpPricingOption);
		String value = jdbcTemplate
				.queryForObject(
						sql,
						String.class,
						signUpPricingOption,
						com.serene.job.common.Constants.PickListName_SIGNUP_PRICING_OPTION);
		log.info("value of system_property-->> " + value);
		Integer integer = new Integer(value);
		cal.add(Calendar.DAY_OF_YEAR, integer);
		modifiedNextBillDate = cal.getTime();
		log.info("modifiedNextBillDate--->> " + cal.getTime());

		return modifiedNextBillDate;
	}

	private Date calculateNextBillDate(Map<String, Object> signUp,
			Calendar cal, String signUpPricingOption,
			Date calculatedNextBillDate,
			JobSchedulerMetadata jobSchedulerMetadata) {

		int billDateOffset = 0;

		if (signUp.get("BillDateOffset_c") != null) {
			billDateOffset = (int) signUp.get("BillDateOffset_c");
		}
		log.info("billDateOffset-->> " + billDateOffset);

		cal.add(Calendar.DAY_OF_YEAR, -billDateOffset);
		calculatedNextBillDate = cal.getTime();

		log.info("calculatedNextBillDate before jdbc update---->> "
				+ calculatedNextBillDate);

		return calculatedNextBillDate;
	}
	
	@Override
	public Long saveinvoice(final Map<String, Object> data, JobSchedulerMetadata jobSchedulerMetadata) {
		Long invoiceId = 0L;
		final SimpleDateFormat sdf = 
			     new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			JdbcTemplate jdbcTemplate = jobUtils
					.getFromJdbcTemplate(jobSchedulerMetadata);
			
			int i = 0;
			final Calendar cal = Calendar.getInstance();
			final String sql = "INSERT INTO invoice ("
					+ "accountId, "
					+ "contact_id, "
					+ "DueDate, "
					+ "amount, "
					+ "signupId, "
					+ "FAamount_c, "
					+ "payerId, "
					+ "BillDate, "
					+ "InvoiceDate, "
					+ "InvoiceNumber, "
					+ "lastUpdated, "
					+ "paymentmethodId "
					+ " ) VALUES ( "
					+ " ?,?,?,?,?,?,?,?,?,?,?)";
			
			/*
			i = jdbcTemplate.update(sql, 
					data.get("accountId").toString(),
					data.get("contact_id").toString(),
					new Date(),
					data.get("amount").toString(),
					data.get("signupId").toString(),
					data.get("FAamount_c").toString(),
					data.get("payerId").toString(),
					new Date(),
					new Date(),
					generateInvoiceNumber(data),
					cal
					);*/
			
			KeyHolder keyHolder = new GeneratedKeyHolder();
			jdbcTemplate.update(
				    new PreparedStatementCreator() {
				        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				            PreparedStatement ps =
				                connection.prepareStatement(sql, new String[] {"InvoiceId"});
				            ps.setString(1, data.get("accountId").toString());
				            ps.setString(2, data.get("contact_id").toString());
				            ps.setString(3, sdf.format(new Date()));
				            ps.setDouble(4, Double.parseDouble(data.get("amount").toString()));
				            ps.setString(5, data.get("signupId").toString());
				            ps.setDouble(6, Double.parseDouble(data.get("FAamount_c").toString()));
				            ps.setString(7, data.get("payerId").toString());
				            ps.setString(8, sdf.format(new Date()));
				            ps.setString(9, sdf.format(new Date()));
				            ps.setString(10, generateInvoiceNumber(data));
				            ps.setString(11, sdf.format(cal.getTime()));
				            ps.setInt(12, Integer.parseInt(data.get("paymentmethod_id").toString()));
				            return ps;
				        }
				    },
				    keyHolder);
			
			invoiceId = Long.parseLong(keyHolder.getKey().toString());
		} catch (InvalidDataAccessApiUsageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in Save invoice",e);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in Save invoice",e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Error in Save invoice",e);
		}
		return invoiceId;
	}
	
	@Override
	public String generateInvoiceNumber(Map<String, Object> data){

		DateFormat sdf = new SimpleDateFormat("MMddYYYY");
		String invoiceNumber = data.get("accountId").toString() 
				+"-"+ data.get("signupId").toString() 
				+"-"+ data.get("payerId").toString() 
				+"-"+ sdf.format(new Date());
		
		return invoiceNumber;
	}

}