package com.ymca.web.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.ymca.Constants;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.SignupDao;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.Payment;
import com.ymca.model.Signup;

@Service
public class ChildCareService {
	
	/**
	 * Created by Lavy Toteza
	 * lstoteza@serenecorp.com
	 * 10072015
	 */
	
	@Resource
	private SignupDao signupDao ;
	
	@Resource
	private ItemDetailDao itemDetailsDao ;
	
	@Resource
	private PaymentDao paymentDao ;
	
	@Resource
	private InvoiceService invoiceService ;
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	public synchronized void changeChildCareService(List<Long> oldSignUpsId, List<String> newSignupData) {
		// this service is for change child care process
		/*
		 * Params: 
		 * old sign up Id to cancel - can be 1 or 2
		 * new sign up data object - we are looking for only childcare signup data
		 * 
		 * from new signup we will need bill date and amount
		 * bill date will always be same for all new childcare signup data
		 * amount is taken as itempriceOnInvoice
		 */
		
		Boolean allowSignupCancellation = false; // allow cancellation only if there is no past due for the old sign ups - true means allow :: false means not allow
		if(oldSignUpsId!=null && oldSignUpsId.size()>0){
			for(Long oldSignUpId: oldSignUpsId){
				if(isSignupCancellationAllowed(oldSignUpId)){
					allowSignupCancellation = true;
				} else {
					allowSignupCancellation = false; // isSignupCancellationAllowed returned false, so there is past due invoice for one of the sign up.. dont allow cancellation
					return;
				}
			}
		}

		if( allowSignupCancellation ){
			// get new child care signup data in signup object 
			//Signup newSignup = populateSignupData(newSignupData);
			
			// get bill date of new sign up
			// bill date will always be same for all new childcare signup data
			Date billDate = getBillDateForSignup(newSignupData.get(0));
			
			//get the signup amount for program of new signup
			double signupAmount = calculateSignupAmount(newSignupData);
			
			double currentMonthPaymentAmount = 0;
			double futureMonthPaymentAmount = 0;
			double futureMonthBalance = 0;
			for(Long oldSignUpId: oldSignUpsId){
				
				// get old sign up object
				Signup oldSignup = signupDao.findOne(oldSignUpId);
				
				// get all invoice where invoice bill date >= input bill date and invoice bill date < (input bill date + 1 month)for the old signup
				List<Invoice> currentMonthInvoicesList = invoiceService.getAllInvoiceByBilldate(oldSignup,billDate);
				if(currentMonthInvoicesList != null && currentMonthInvoicesList.size() > 0){
					for (Invoice invoice : currentMonthInvoicesList) {
						// calculate payment made for this invoice
						currentMonthPaymentAmount += calculatePaymentByInvoice(invoice);
					}
				}
				
				// get all invoice where invoice bill date >= (input bill date + 1 month) and invoice bill date < (input bill date + 2 month)for the old signup
				Date nextMonthBillDate = getNextMonthDate(billDate);
				List<Invoice> futureMonthInvoicesList = invoiceService.getAllInvoiceByBilldate(oldSignup,nextMonthBillDate);
				if(futureMonthInvoicesList != null && futureMonthInvoicesList.size() > 0){
					for (Invoice invoice : futureMonthInvoicesList) {
						// calculate payment made for this invoice
						futureMonthPaymentAmount += calculatePaymentByInvoice(invoice);
						futureMonthBalance += invoiceService.getOpenBalanceForInvoice(invoice);
					}
				}
			}
			
			if(signupAmount>=currentMonthPaymentAmount){
				// upgrade - do nothing
				// pass the difference amount to current month invoice for new sign up
				
			}
			else{
				// downgrade - create refund
			}
			
			if(futureMonthPaymentAmount>0){
				// create refund activity
			}
			
			if(futureMonthBalance>0){
				// create payment record of type cm/write off with amount = balance
			}
		}
	}
	
	private Date getNextMonthDate(Date date) {
		Date nextMonthDate = null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH)+1));
		nextMonthDate = cal.getTime();
		return nextMonthDate;
	}
	
	private double calculateSignupAmount(List<String> newSignupData) {
		double signupAmount = 0;
		for(String signup: newSignupData ){
			JSONObject newSignupDataObj = JSONObject.fromObject(signup);
			String strSignupAmount = newSignupDataObj.get("itempriceOnInvoice").toString();
			signupAmount = Double.parseDouble(strSignupAmount);
		}
		
		return signupAmount;
	}

	private Date getBillDateForSignup(String newSignupData) {
		// compute bill date by newSignupData
		JSONObject newSignupDataObj = JSONObject.fromObject(newSignupData);
		
		Date billDate = new Date();
		Object billDateObj = newSignupDataObj.get("billDate");
		if(billDateObj != null && !billDateObj.equals("") && !billDateObj.toString().equals("null")){
			billDate = new Date(billDateObj.toString());
		}
		return billDate;
	}

	private double calculatePaymentByInvoice(Invoice invoice) {
		// calculate payment made for the invoice
		double paymentAmount = 0;
		List<Payment> allPaymentsForInvoice = paymentDao.findByInvoiceAndStatusAndType(invoice,com.ymca.web.util.Constants.SUCCESS,"Payment");
		if(allPaymentsForInvoice != null && allPaymentsForInvoice.size() > 0){
			for(Payment payment:allPaymentsForInvoice){
				if(payment.getAmount()>0){
					paymentAmount += payment.getAmount();
				}
			}
		}
		return paymentAmount;
	}

	private Boolean isSignupCancellationAllowed(Long oldSignUpId) {
		//check if sign up to cancel has due amount, if so don't allow to cancel
		
		Boolean allowSignupToCancel = true;
		if(oldSignUpId!=null){
			Signup signupToCancel = signupDao.findOne(oldSignUpId);
			
			Double pastDueAmount = invoiceService.getPastDueAmountBySignup(signupToCancel);
			if(pastDueAmount.doubleValue()>0){
				allowSignupToCancel = false;
			}
		}
		return allowSignupToCancel;
	}
	
	private Signup populateSignupData(JSONObject newSignupData) {
		Signup newSignup = new Signup();
		
		Long itemDetailId = Long.parseLong(newSignupData.get("itemDetailId").toString());
		newSignup.setItemDetailId(itemDetailId);
		
		ItemDetail itemDetail = itemDetailsDao.findOne(itemDetailId);
		newSignup.setItemDetail(itemDetail);
		
		// Signup date will be selected start date from signup process
		try {
			Date newSignupDate = sdf.parse(newSignupData.get("selectedStartDate").toString());
			newSignup.setSignupDate(newSignupDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return newSignup;
	}
}
