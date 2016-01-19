package com.ymca.web.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ymca.dao.AssociatedOtherContactsDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SignUpPromotionDao;
import com.ymca.dao.SignupDao;
import com.ymca.model.Account;
import com.ymca.model.AssociatedOtherContacts;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Payer;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.Signup;
import com.ymca.model.SignupPromotion;
import com.ymca.model.User;
import com.ymca.web.enums.BillingOptionEnum;
import com.ymca.web.enums.PaymentModeEnum;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.util.Constants;
import com.ymca.web.util.StringUtil;

@Service 
public class PaymentService  {
	
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private JetPayPaymentDao jetPayPaymentDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private PayerDao payerDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private ItemDetailDao itemDetailDao;
	
	@Resource
	private AssociatedOtherContactsDao associatedOtherContactsDao;
	
	@Resource
	private SignUpPromotionDao signUpPromotionDao;
	
	@Resource
	private InvoiceService invoiceService;
	
	@Resource
	private PromotionService promotionService;
	
	private Logger log = LoggerFactory.getLogger(PaymentService.class); 

	@SuppressWarnings("deprecation")
	public Signup savesignup(String paymentId, Account customer, JSONObject cartItemMap, ItemDetail itemDetail, User u, String portalLastModifiedBy, String signupstatus) {

		Object itempriceOnSignup = cartItemMap.get("itempriceOnSignup");
		//Object discount = cartItemMap.get("discount");
		Object fa = cartItemMap.get("fa");
		//Object FAobj = cartItemMap.get("FAobj");
		Object signupAmount = cartItemMap.get("signupAmount");
		Object setupFee = cartItemMap.get("setupFee");
		Object registrationFee = cartItemMap.get("registrationFee");
		Object depositAmount = cartItemMap.get("depositAmount");
		Object priceOption = cartItemMap.get("priceOption");
		Object FApercent = cartItemMap.get("FApercent");
		Object FAstartDate = cartItemMap.get("FAstartDate");
		Object FAendDate = cartItemMap.get("FAendDate");
		Object billingOption = cartItemMap.get("billingOption");
		Object gradeLevel = cartItemMap.get("gradeLevel");
		Object noOfTickets = cartItemMap.get("noOfTickets");
		Object specialrequest = cartItemMap.get("specialrequest");
		Object joinFee = cartItemMap.get("joinFee");
		Object selectedStartDate = cartItemMap.get("selectedStartDate");
		Object nextBillDateObj = cartItemMap.get("nextBillDate");
		Object activities = cartItemMap.get("activities");
		Object promosObj = cartItemMap.get("promos");
		Object isRecurring = cartItemMap.get("isRecurring");
		JSONArray promosJson = null;
		if(promosObj != null){
			promosJson = JSONArray.fromObject(promosObj.toString());
		}
		Double totalDiscount = computeTotalDiscount(promosJson);
		
		StringBuffer draftCycle = null;
		if(isRecurring != null && Boolean.parseBoolean(isRecurring.toString())){
			if(priceOption != null && itemDetail.getDueDateOffset() != null){
				draftCycle = new StringBuffer();
				draftCycle.append(priceOption);
				draftCycle.append(","+itemDetail.getDueDateOffset().toString());
			}
		}
		
		Signup signup = new Signup();
		signup.setCustomer(customer);
		signup.setPortalLastModifiedBy(portalLastModifiedBy);
		//signup.setItemDaysMapId(ids.getItemDaysId()+"_"+ids.getItemDetails().getId());
		//signup.setItem(ids.getProduct());
		signup.setItemDetail(itemDetail);
		signup.setSignUpName(itemDetail.getRecordName());
		signup.setProgramStartDate(itemDetail.getStartDate());
		signup.setProgramEndDate(itemDetail.getEndDate());
		
		Long  loc_id = itemDetail.getLocation().getId();
		signup.setLocation(locationDao.getOne(loc_id));
		
		signup.setContact(u);
		signup.setContactName(u.getFullName());
		/*
		if(Boolean.valueOf(cartItems.get(14)))
			signup.setStatus(Constants.WAITLISTED);
		else
			signup.setStatus(Constants.CONFIRMED);*/
		signup.setStatus(signupstatus);
		
		signup.setWaitlist(0L);
		if(selectedStartDate != null && !selectedStartDate.toString().equals("")){
			signup.setSignupDate(new Date(selectedStartDate.toString()));
		}else{
			signup.setSignupDate(new Date());
		}
		
		signup.setType(itemDetail.getType());
		
		if(Long.parseLong(paymentId)>0){
			List<PaymentMethod> lstPaymentMethod = paymentMethodDao.getPaymentMethodByPaymentId(Long.parseLong(paymentId));
			PaymentMethod paymentMethod = lstPaymentMethod.get(0);
			signup.setPaymentMethod(paymentMethod);
		}
		
		signup.setFinalAmount(itempriceOnSignup.toString());
		signup.setDiscountAmount(totalDiscount.toString());
		signup.setFAamount(Double.parseDouble(fa.toString()));
		signup.setSignupPrice(Double.parseDouble(signupAmount.toString()));
		signup.setSetUpFee(Double.parseDouble(setupFee.toString()));
		signup.setRegistrationFee(Double.parseDouble(registrationFee.toString()));
		signup.setDeposit(Double.parseDouble(depositAmount.toString()));
		signup.setSignUpPricingOption(priceOption.toString());
		signup.setFApercentage(FApercent.toString());
		signup.setBillingOption(billingOption.toString());
		signup.setGradeLevel(gradeLevel.toString());
		if(draftCycle != null){
			signup.setDraftCycle(draftCycle.toString());
		}
		try {
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
			DateFormat inputsdf = new SimpleDateFormat("MM/dd/yyyy"); 
			if(FAstartDate != null && !"".equals(FAstartDate.toString()) && !" ".equals(FAstartDate.toString())){
				signup.setFAstartDate(sdf.parse(sdf.format(inputsdf.parse(FAstartDate.toString()))));
			}

			if(FAendDate != null && !"".equals(FAendDate.toString()) && !" ".equals(FAendDate.toString())){
				signup.setFAendDate(sdf.parse(sdf.format(inputsdf.parse(FAendDate.toString()))));
			}
		} catch (ParseException e) {
			log.error("Error  ",e);
		}
		
		if(signup.getType().equals(ProductTypeEnum.EVENT.toString())){
			signup.setNoOfTickets(Integer.parseInt(noOfTickets.toString()));
		}
		signup.setJoinFee(Double.parseDouble(joinFee.toString()));
		signup.setSpecialRequest(specialrequest.toString());
		Calendar cal = Calendar.getInstance();
		signup.setLastUpdated(cal);
		
		System.out.println("  nextBillDateObj.toString() "+nextBillDateObj.toString());
		
		if(nextBillDateObj != null && !nextBillDateObj.equals("") && !nextBillDateObj.toString().equals("null"))
			signup.setNextBillDate(new Date(nextBillDateObj.toString()));

		// save activity if present
		
		String itemActivities = activities.toString();
		if (StringUtils.isNoneBlank(itemActivities)) {
			int activityNumber = 0;
			String[] contactActivities = itemActivities.split("#");
			for (String contactActivity : contactActivities) {
				
				String[] contact = contactActivity.split("\\$");
			
				if(contact[3] != null && !contact[3].equals("null")){
					//Long activityItemDetailId = Long.parseLong(contact[2]);
					Integer activityPriority = Integer.parseInt(contact[3]);
					String activityName = contact[4];
					
					activityNumber = activityNumber + 1;
					
					switch (activityNumber) {
					case 1:
						signup.setActivity1(activityName);
						signup.setPriority1(activityPriority);
						break;
					case 2:
						signup.setActivity2(activityName);
						signup.setPriority2(activityPriority);
						break;
					case 3:
						signup.setActivity3(activityName);
						signup.setPriority3(activityPriority);
						break;
					case 4:
						signup.setActivity4(activityName);
						signup.setPriority4(activityPriority);
						break;
					case 5:
						signup.setActivity5(activityName);
						signup.setPriority5(activityPriority);
						break;
					case 6:
						signup.setActivity6(activityName);
						signup.setPriority6(activityPriority);
						break;
					case 7:
						signup.setActivity7(activityName);
						signup.setPriority7(activityPriority);
						break;
					case 8:
						signup.setActivity8(activityName);
						signup.setPriority8(activityPriority);
						break;
					case 9:
						signup.setActivity9(activityName);
						signup.setPriority9(activityPriority);
						break;
					case 10:
						signup.setActivity10(activityName);
						signup.setPriority10(activityPriority);
						break;
					default:
						break;
					}
				}
			}
		}
		
		/*try {
			String itemActivities = activities.toString();
			if (StringUtils.isNoneBlank(itemActivities)) {
				String[] contactActivities = itemActivities.split("#");
				for (String contactActivity : contactActivities) {
					String[] contact = contactActivity.split("\\$");
					SignupAssociatedItemDetail activity = new SignupAssociatedItemDetail() ;
					activity.setSignup(signupprogram);
					ItemDetail actItemDetail = itemDetailDao.findOne(Long.parseLong(contact[2]));
					activity.setItemDetail(actItemDetail);
					activity.setActivityPriority(contact[3]);
					signUpAssociatedItemDetailDao.save(activity);	
				}
			}
		} catch (Exception e) {
			log.error("Error while saving the activities for signup",e);
		}*/
		
		Signup signupprogram = new Signup();
		signupprogram = signupDao.save(signup);
		return signupprogram;
	}
	public Signup savesignup(String paymentId, Account customer, JSONObject cartItemMap, ItemDetail ids, User u, String portalLastModifiedBy) {
		String signupstatus = SignupStatusEnum.Active.toString();
		if(Boolean.valueOf(cartItemMap.get("waitlist").toString()))
			signupstatus = SignupStatusEnum.Waitlisted.toString();
		
		return savesignup(paymentId, customer, cartItemMap, ids, u, portalLastModifiedBy,signupstatus);
	}
	
	public Payer savepayer(String paymentId, Account customer, JSONObject cartItemMap, ItemDetail ids, Signup signup, String paymentMode) {
		
		Object billingOption = cartItemMap.get("billingOptwwion");
		//Object itempriceOnSignup = cartItemMap.get("itempriceOnSignup");
		Object itempriceOnPayer = cartItemMap.get("itempriceOnPayer");
		//Object isRecurringObj = cartItemMap.get("isRecurring");
		
		if(billingOption != null && billingOption.toString().equalsIgnoreCase(BillingOptionEnum.Manual.toString())){
			paymentMode = PaymentModeEnum.Cash.toString();
		}
		
		Payer payer = new Payer();
		payer.setCustomer(customer);
		payer.setStartdate(signup.getProgramStartDate());
		payer.setEnddate(signup.getProgramEndDate());
		/*if(Constants.CHILDCARE_TYPE.equals(type.toString())){
			payer.setAmount(Double.parseDouble(itempriceOnSignup.toString()));
		}else{*/
			/*if(signup != null && signup.getType() != null && signup.getType().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_MEMBERSHIP)){
				if(customer != null && customer.getMembershipFrequency() != null && Constants.MEMBERSHIP_FREQUENCY_ANNUAL.equals(customer.getMembershipFrequency() )){
					payer.setAmount(Double.valueOf(signup.getFinalAmount()));
				} else if(customer != null && customer.getMembershipFrequency() != null && Constants.MEMBERSHIP_FREQUENCY_MONTHLY.equals(customer.getMembershipFrequency() ) && StringUtils.isNotBlank(signup.getFinalAmount())){
					payer.setAmount(signup.getSignupPrice());
				}
			}else {*/
				payer.setAmount(Double.parseDouble(itempriceOnPayer.toString()));
			//}
		//}
		payer.setType(Constants.SELF);
		payer.setSignup(signup);
		
		Calendar cal = Calendar.getInstance();
		payer.setLastUpdated(cal);
		
		if(Long.parseLong(paymentId)>0){
			List<PaymentMethod> lstPaymentMethod = paymentMethodDao.getPaymentMethodByPaymentId(Long.parseLong(paymentId));
			PaymentMethod paymentMethod = lstPaymentMethod.get(0);
			payer.setPaymentMethod(paymentMethod);
		}
		
		if(payer.getPaymentMethod() != null){
			payer.setPaymentMode(payer.getPaymentMethod().getPaymentMethodType());
		}else{
			payer.setPaymentMode(paymentMode);
		}
		
		Payer p = new Payer();
		p = payerDao.save(payer);
		return p;
	}
	
	public List<Invoice> saveCartInvoice(Account customer, User u, Signup signup, Payer payer, JSONArray invoiceArr, Object fa) {
		List<Invoice> invoices = new ArrayList<Invoice>();
		if(invoiceArr != null && invoiceArr.size() > 0){
			for (Object invoiceObj : invoiceArr) {
				if(invoiceObj != null && !invoiceObj.toString().equals("") && !invoiceObj.toString().equals("undefined")){
					JSONObject invoiceJson = JSONObject.fromObject(invoiceObj.toString());
					
					JSONObject invoiceDetail = new JSONObject();
					invoiceDetail.put("itempriceOnInvoice", invoiceJson.get("invoiceAmt"));
					invoiceDetail.put("discountAmount", invoiceJson.get("invoiceDiscountAmt"));
					invoiceDetail.put("dueDateOnInvoice", invoiceJson.get("dueDate"));
					invoiceDetail.put("billDateOnInvoice", invoiceJson.get("billDate"));
					
					if(fa!=null && Double.parseDouble(fa.toString())>0){
						invoiceDetail.put("fa", fa.toString());
					}
					
					Invoice i = saveinvoice(customer, invoiceDetail, u, signup, payer);
					
					invoices.add(i);
				}
			}
		}
		return invoices;
	}
	
	public Invoice saveCartPayment(String paymentId, String jp_request_hash, String orderNumber,
			Account customer, User u, Signup signupprogram, Payer payer, List<Invoice> invoices, 
			String paymentMode, Object fa) {
		Invoice i = null;
		if(invoices != null && invoices.size() > 0){
			for (Invoice invoice : invoices) {
				if(invoice != null){
					savepayment(paymentId, jp_request_hash, orderNumber, customer, u, signupprogram, payer, invoice, paymentMode, invoice.getAmount());
				}
			}
			// added on 24072015 - if FA amount on invoice > 0, create payment record of type "Credit Memo/FA Waiver" and amount will be FA_AMOUNT
			if(fa!=null && Double.parseDouble(fa.toString())>0){
				String paymentType = PaymentTypeEnum.CreditMemoFAWaiver.getValue();
				savepayment(paymentId, jp_request_hash, orderNumber, customer, u, signupprogram, payer, invoices.get(0), paymentMode, Double.parseDouble(fa.toString()),paymentType);
			}
		}
		return i;
	}
	
	@SuppressWarnings("deprecation")
	public Invoice saveinvoice(Account customer, JSONObject invoiceDetail, User u, Signup signup, Payer payer) {
		
		Object itempriceOnInvoice = invoiceDetail.get("itempriceOnInvoice");
		//Object discountAmount = invoiceDetail.get("discountAmount");
		Object fa = invoiceDetail.get("fa");
		Object dueDateObj = invoiceDetail.get("dueDateOnInvoice");
		Object billDateObj = invoiceDetail.get("billDateOnInvoice");
		
		Invoice invoice = new Invoice();
		invoice.setCustomer(customer);
		invoice.setContact(u);
		//invoice.setOrderDate(new Date());
		/*Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		if(signup.getItemDetail().getDueDateOffset() != null){
			cal.add(Calendar.DATE, signup.getItemDetail().getDueDateOffset()); // add offset
		}*/
		
		invoice.setDraftDate(invoiceService.getInvoiceDraftDate(signup.getDraftCycle(), invoice.getDueDate()));
		
		invoice.setAmount(StringUtil.round(Double.valueOf(itempriceOnInvoice.toString()), 2));
		/*if(discountAmount != null && discountAmount.toString() != null && !discountAmount.equals("")){
			invoice.setDiscountAmount(StringUtil.round(Double.valueOf(discountAmount.toString()), 2));
		}*/
		invoice.setSignup(signup);
		if(fa != null && fa.toString() != null && !fa.toString().equals("")){
			invoice.setFAamount(Double.valueOf(fa.toString()));
		}
		invoice.setPayer(payer);
		if(billDateObj != null && !billDateObj.equals("") && !billDateObj.toString().equals("null"))
			invoice.setBillDate(new Date(billDateObj.toString()));
		else
			invoice.setBillDate(new Date());
		if(dueDateObj != null && !dueDateObj.equals("") && !dueDateObj.toString().equals("null"))
			invoice.setDueDate(new Date(dueDateObj.toString()));
		else
			invoice.setDueDate(new Date());
		invoice.setInvoiceDate(new Date());
		Calendar lastUpdCal = Calendar.getInstance();
		invoice.setLastUpdated(lastUpdCal);
		invoice.setInvoiceNumber(invoiceService.generateInvoiceNumber(customer.getAccountId(), signup.getSignupId(), payer.getId(), invoice.getBillDate()));
		invoice.setPaymentMethod(payer.getPaymentMethod());
		if(payer.getPaymentMethod() != null){
			invoice.setPaymentMode(payer.getPaymentMethod().getPaymentMethodType());
		}else if(payer.getPaymentMethod() == null && payer.getPaymentMode() != null){
			invoice.setPaymentMode(payer.getPaymentMode());
		}
		
		Invoice i = new Invoice();
		i = invoiceDao.save(invoice);
		return i;
	}
	
	public Invoice saveinvoice(Account customer, String FAamount, User u, Signup signup, Payer payer) {
		Invoice invoice = new Invoice();
		invoice.setCustomer(customer);
		invoice.setContact(u);
		//invoice.setOrderDate(new Date());
		invoice.setDueDate(new Date());
		invoice.setAmount(payer.getAmount());	
		invoice.setSignup(signup);
		invoice.setFAamount(Double.parseDouble(FAamount));
		invoice.setPayer(payer);
		invoice.setBillDate(new Date());
		invoice.setInvoiceDate(new Date());
		//DateFormat sdf = new SimpleDateFormat("MMddYYYY");
		//String invoiceNumber = customer.getAccountId() + signup.getSignupId() + "-" + payer.getId() + sdf.format(signup.getNextBillDate());
		invoice.setInvoiceNumber(invoiceService.generateInvoiceNumber(customer.getAccountId(), signup.getSignupId(), payer.getId(), invoice.getBillDate()));
		
		Calendar lastUpdCal = Calendar.getInstance();
		invoice.setLastUpdated(lastUpdCal);
		invoice.setPaymentMethod(payer.getPaymentMethod());
		
		Invoice i = new Invoice();
		i = invoiceDao.save(invoice);
		return i;
	}
	
	public JetPayPayment savepayment(String paymentId, String jp_request_hash, String orderNumber,
			Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, String paymentMode, Double paymentAmount) {
		Payment payment = new Payment();
		
		payment.setPaymentMode(paymentMode);
		JetPayPayment jetPayPayment = null;
		String paymentType = PaymentTypeEnum.Payment.toString();
		if(StringUtils.isNotBlank(paymentMode) && (paymentMode.equals(PaymentTypeEnum.Cash.name()) || paymentMode.equals(PaymentTypeEnum.Debit.name())|| paymentMode.equals(PaymentTypeEnum.Stock.name()))){
			String paymentMethodId = "0";
			if(StringUtils.isNotBlank(paymentId) && !paymentId.equals("0")){
				paymentMethodId = paymentId;
			}
			
			savePayment(customer, u, signupprogram, payer, invoice, payment, paymentMethodId, paymentAmount,paymentType);
		}else if(StringUtils.isNotBlank(paymentMode) && paymentMode.equals(PaymentTypeEnum.Check.name())){
			savePaymentByCheck(customer, u, signupprogram, payer, invoice, payment,paymentType);
		}else{
			jetPayPayment = savePaymentByOrderNumber(paymentId, orderNumber, customer, u, signupprogram, payer, invoice, payment, paymentAmount,paymentType);	
		}		
		return jetPayPayment;
	}
	
	public JetPayPayment savepayment(String paymentId, String jp_request_hash, String orderNumber,
			Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, String paymentMode, Double paymentAmount, String paymentType) {
		Payment payment = new Payment();
		
		payment.setPaymentMode(paymentMode);
		JetPayPayment jetPayPayment = null;
		
		if(StringUtils.isNotBlank(paymentMode) && (paymentMode.equals(PaymentTypeEnum.Cash.name()) || paymentMode.equals(PaymentTypeEnum.Debit.name())|| paymentMode.equals(PaymentTypeEnum.Stock.name()))){
			String paymentMethodId = "0";
			if(StringUtils.isNotBlank(paymentId) && !paymentId.equals("0")){
				paymentMethodId = paymentId;
			}
			
			savePayment(customer, u, signupprogram, payer, invoice, payment, paymentMethodId, paymentAmount,paymentType);
		}else if(StringUtils.isNotBlank(paymentMode) && paymentMode.equals(PaymentTypeEnum.Check.name())){
			savePaymentByCheck(customer, u, signupprogram, payer, invoice, payment,paymentType);
		}else{
			jetPayPayment = savePaymentByOrderNumber(paymentId, orderNumber, customer, u, signupprogram, payer, invoice, payment, paymentAmount,paymentType);	
		}		
		return jetPayPayment;
	}
	
	public JetPayPayment savepayment(String paymentId, String jp_request_hash, String orderNumber,
			Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, double paymentAmount) {
		
		Payment payment = new Payment();
		payment.setPaymentMode(PaymentTypeEnum.Debit.toString());
		
		String paymentType = PaymentTypeEnum.Payment.toString();
		JetPayPayment jetPayPayment = savePaymentByOrderNumber(paymentId, orderNumber, customer, u, signupprogram, payer, invoice, payment, paymentAmount,paymentType);
		
		return jetPayPayment;
	}
	
	

	private JetPayPayment savePaymentByOrderNumber(String paymentId, String orderNumber, Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, Payment payment, Double paymentAmount, String paymentType) {
		
		JetPayPayment jetPayPayment = null;
		if(!"".equals(orderNumber)){
			//jetPayPayment = jetPayPaymentDao.getByJpReturnHash(jp_request_hash);
			//jetPayPayment = jetPayPaymentDao.getByOrderNumber("968787959410");
			jetPayPayment = jetPayPaymentDao.getByOrderNumber(orderNumber);			
		}
		if(jetPayPayment!=null){
			
			payment.setPaymentNumber(jetPayPayment.getOrderNumber());
			//payment.setTransactionId(jetPayPayment.getOrderNumber());
			if(jetPayPayment.getActCode() != null && Constants.PAYMENT_ACTION_CODE_SUCCESS.equals(jetPayPayment.getActCode())){
				payment.setStatus(Constants.SUCCESS);
			}else{
				payment.setStatus(Constants.FAILURE);
			}
			//payment.setPaymentNumber(jetPayPayment.getPaymentId().toString());
			/*
			if(cartItems != null && cartItems.get(2) != null && !"".equals(cartItems.get(2).trim())){
        		jetPayPayment.setAmount(Double.parseDouble(cartItems.get(2)));
        		payment.setAmount(Double.parseDouble(cartItems.get(2)));
        	}else{
        		jetPayPayment.setAmount(0D);
        		payment.setAmount(0D);
        	}*/
			//jetPayPayment.setAmount(payer.getAmount());
			payment.setAmount(paymentAmount);
			payment.setCustomer(customer);
			payment.setSignup(signupprogram);
			payment.setContact(u);
			payment.setType(paymentType);
			if(Long.parseLong(paymentId)>0){
				List<PaymentMethod> lstPaymentMethod = paymentMethodDao.getPaymentMethodByPaymentId(Long.parseLong(paymentId));
				PaymentMethod paymentMethod = lstPaymentMethod.get(0);
				payment.setPaymentMethod(paymentMethod.getId().toString());
			}
			payment.setDescription(jetPayPayment.getResponseText());
			payment.setPaymentDate(new Date());
			payment.setPayer(payer);
			payment.setInvoice(invoice);
			Calendar cal = Calendar.getInstance();
			payment.setLastUpdated(cal);
			payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
			//payment.setPaymentMode(PaymentTypeEnum.Debit.toString());
			paymentDao.save(payment);
		}
		return jetPayPayment;
	}
	
	private void savePayment(Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, Payment payment, String paymentId, Double paymentAmount, String paymentType) {
		payment.setStatus(Constants.SUCCESS);
		//if(signupprogram != null && !StringUtils.isBlank(signupprogram.getFinalAmount())){
			payment.setAmount(paymentAmount);
		//}
					
		payment.setCustomer(customer);
		payment.setSignup(signupprogram);
		payment.setContact(u);
		if(StringUtils.isNotBlank(payment.getPaymentMode()) && payment.getPaymentMode().equals(PaymentTypeEnum.Debit.toString())){
			payment.setPaymentMode(PaymentModeEnum.Credit.toString());
		}
		payment.setType(paymentType);
		
		//payment.setDescription(jetPayPayment.getResponseText());
		payment.setPaymentDate(new Date());
		payment.setPayer(payer);
		payment.setInvoice(invoice);
		Calendar cal = Calendar.getInstance();
		payment.setLastUpdated(cal);
		if(StringUtils.isNoneBlank(paymentId) && Long.parseLong(paymentId)>0){
			List<PaymentMethod> lstPaymentMethod = paymentMethodDao.getPaymentMethodByPaymentId(Long.parseLong(paymentId));
			PaymentMethod paymentMethod = lstPaymentMethod.get(0);
			payment.setPaymentMethod(paymentMethod.getId().toString());
		}
		//payment.setPaymentMode(PaymentTypeEnum.Debit.toString());
		payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(payment);		
		//return jetPayPayment;
	}
	
	private void savePaymentByCheck(Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, Payment payment, String paymentType) {
		payment.setStatus(Constants.SUCCESS);
		if(signupprogram != null && !StringUtils.isBlank(signupprogram.getFinalAmount())){
			payment.setAmount(Double.parseDouble(signupprogram.getFinalAmount()));
		}
					
		payment.setCustomer(customer);
		payment.setSignup(signupprogram);
		payment.setContact(u);
		payment.setType(paymentType);
		
		payment.setDescription(customer.getPaymentDesc());
		payment.setPaymentNumber(customer.getCheckNumber());
		payment.setPaymentDate(new Date());
		payment.setPayer(payer);
		payment.setInvoice(invoice);
		Calendar cal = Calendar.getInstance();
		payment.setLastUpdated(cal);	
		payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(payment);				
	}
	
	
	private void saveDuePaymentByCash(Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, Payment payment) {
		payment.setStatus(Constants.SUCCESS);
		payment.setAmount(invoice.getBalance());
					
		payment.setCustomer(customer);
		payment.setSignup(signupprogram);
		payment.setContact(u);
		payment.setType(PaymentTypeEnum.Debit.toString());
		
		//payment.setDescription(jetPayPayment.getResponseText());
		payment.setPaymentDate(new Date());
		payment.setPayer(payer);
		payment.setInvoice(invoice);
		Calendar cal = Calendar.getInstance();
		payment.setLastUpdated(cal);
		//payment.setPaymentMode(PaymentTypeEnum.Debit.toString());
		payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(payment);		
		//return jetPayPayment;
	}
	
	private void saveDuePaymentByCheck(Account customer, User u, Signup signupprogram, Payer payer, Invoice invoice, Payment payment) {
		payment.setStatus(Constants.SUCCESS);
		
		payment.setAmount(invoice.getBalance());
		
					
		payment.setCustomer(customer);
		payment.setSignup(signupprogram);
		payment.setContact(u);
		payment.setType(PaymentTypeEnum.Debit.toString());
		
		payment.setDescription(customer.getPaymentDesc());
		payment.setPaymentNumber(customer.getCheckNumber());
		payment.setPaymentDate(new Date());
		payment.setPayer(payer);
		payment.setInvoice(invoice);
		Calendar cal = Calendar.getInstance();
		payment.setLastUpdated(cal);	
		payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(payment);				
	}
	
	
	
	public JetPayPayment savePastDuePayment(String paymentId,
			String jp_request_hash, String orderNumber, Account customer,
			User contact, String paymentMode,
			List<Invoice> requiredPastInvoiceList) {
		//System.out.println("savePastDuePayment -->> " + paymentId		+ " orderNumber -->> " + orderNumber);
		JetPayPayment jetPayPayment = null;
		
		if(StringUtils.isNotBlank(paymentMode) && (paymentMode.equals(PaymentTypeEnum.Cash.name()) || paymentMode.equals(PaymentTypeEnum.None.name()))){
			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
			saveDuePaymentByCash(customer,contact , invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
			
		}else if(StringUtils.isNotBlank(paymentMode) && paymentMode.equals(PaymentTypeEnum.Check.name())){
			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
				saveDuePaymentByCheck(customer, contact, invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
			
			
		}else{
		
		if (!"".equals(orderNumber)) {
			//jetPayPayment = jetPayPaymentDao.getByOrderNumber("901493230249");
			jetPayPayment = jetPayPaymentDao.getByOrderNumber(orderNumber);
		}
		if (jetPayPayment != null) {

			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();

				if (jetPayPayment.getActCode() != null
						&& Constants.PAYMENT_ACTION_CODE_SUCCESS
								.equals(jetPayPayment.getActCode())) {
					payment.setStatus(Constants.SUCCESS);
				} else {
					payment.setStatus(Constants.FAILURE);
				}

				payment.setPaymentNumber(jetPayPayment.getPaymentId()
						.toString());

				jetPayPayment.setAmount(0D);
				payment.setCustomer(customer);
				payment.setContact(contact);

				payment.setType(PaymentTypeEnum.Payment.toString());

				payment.setPaymentMode(paymentMode);

				if (Long.parseLong(paymentId) > 0) {

					List<PaymentMethod> lstPaymentMethod = paymentMethodDao
							.getPaymentMethodByPaymentId(Long
									.parseLong(paymentId));
					PaymentMethod paymentMethod = lstPaymentMethod.get(0);
					payment.setPaymentMethod(paymentMethod.getId()
							.toString());

				}
				payment.setDescription(jetPayPayment.getResponseText());

				payment.setPaymentDate(new Date());
				System.out
						.println(" invoice.getBalance() in paymentservice-- >>"
								+ invoice.getBalance());

				payment.setAmount(invoice.getBalance());
				payment.setSignup(invoice.getSignup());
				payment.setInvoice(invoice);
				payment.setPayer(invoice.getPayer());
				payment.setLastUpdated(Calendar.getInstance());
				payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
				paymentDao.save(payment);

			}

		}
		}
		return jetPayPayment;
	}

	public JetPayPayment savePastDuePayment(String paymentId,
			String jp_request_hash, String orderNumber, Account customer,
			User contact, String paymentMode,
			List<Invoice> requiredPastInvoiceList,
			List<Invoice> requiredInvoiceList) {
		//System.out.println("savePastDuePayment -->> " + paymentId 	+ "orderNumber -->> " + orderNumber);
		JetPayPayment jetPayPayment = null;
		
		if(StringUtils.isNotBlank(paymentMode) && (paymentMode.equals(PaymentTypeEnum.Cash.name()) || paymentMode.equals(PaymentTypeEnum.None.name()))){
			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
			saveDuePaymentByCash(customer,contact , invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
			for (Invoice invoice : requiredInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
				saveDuePaymentByCash(customer, contact, invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
		}else if(StringUtils.isNotBlank(paymentMode) && paymentMode.equals(PaymentTypeEnum.Check.name())){
			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
				saveDuePaymentByCheck(customer, contact, invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
			for (Invoice invoice : requiredInvoiceList) {
				Payment payment = new Payment();
				payment.setPaymentMode(paymentMode);
				saveDuePaymentByCheck(customer,contact, invoice.getSignup(), invoice.getPayer(), invoice, payment);
			}
			
		}else{
			//jetPayPayment = savePaymentByOrderNumber(paymentId, orderNumber, customer, u, signupprogram, payer, invoice, payment, paymentAmount);	
		
		if (!"".equals(orderNumber)) {

			jetPayPayment = jetPayPaymentDao.getByOrderNumber(orderNumber);
			//jetPayPayment = jetPayPaymentDao.getByOrderNumber("901493230249");
		}
		if (jetPayPayment != null) {

			for (Invoice invoice : requiredPastInvoiceList) {
				Payment payment = new Payment();

				if (jetPayPayment.getActCode() != null
						&& Constants.PAYMENT_ACTION_CODE_SUCCESS
								.equals(jetPayPayment.getActCode())) {
					payment.setStatus(Constants.SUCCESS);
				} else {
					payment.setStatus(Constants.FAILURE);
				}

				payment.setPaymentNumber(jetPayPayment.getPaymentId()
						.toString());

				jetPayPayment.setAmount(0D);

				payment.setCustomer(customer);
				payment.setContact(contact);
				payment.setType(PaymentTypeEnum.Payment.toString());
				payment.setPaymentMode(paymentMode);
				if (Long.parseLong(paymentId) > 0) {
					List<PaymentMethod> lstPaymentMethod = paymentMethodDao
							.getPaymentMethodByPaymentId(Long
									.parseLong(paymentId));
					PaymentMethod paymentMethod = lstPaymentMethod.get(0);
					payment.setPaymentMethod(paymentMethod.getId().toString());
				}
				payment.setDescription(jetPayPayment.getResponseText());
				payment.setPaymentDate(new Date());
				System.out.println(" invoice.getBalance() in paymentservice-- >> "+ invoice.getBalance());
				payment.setAmount(invoice.getBalance());
				payment.setSignup(invoice.getSignup());
				payment.setInvoice(invoice);
				payment.setPayer(invoice.getPayer());
				payment.setLastUpdated(Calendar.getInstance());
				payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
				paymentDao.save(payment);
			}

			for (Invoice invoice : requiredInvoiceList) {
				Payment payment = new Payment();

				if (jetPayPayment.getActCode() != null
						&& Constants.PAYMENT_ACTION_CODE_SUCCESS.equals(jetPayPayment.getActCode())) {
					payment.setStatus(Constants.SUCCESS);
				} else {
					payment.setStatus(Constants.FAILURE);
				}
				payment.setPaymentNumber(jetPayPayment.getPaymentId().toString());
				jetPayPayment.setAmount(0D);
				payment.setCustomer(customer);
				payment.setContact(contact);
				payment.setType(PaymentTypeEnum.Debit.toString());
				payment.setPaymentMode(paymentMode);
				if (Long.parseLong(paymentId) > 0) {
					List<PaymentMethod> lstPaymentMethod = paymentMethodDao.getPaymentMethodByPaymentId(Long.parseLong(paymentId));
					PaymentMethod paymentMethod = lstPaymentMethod.get(0);
					payment.setPaymentMethod(paymentMethod.getId().toString());
				}
				payment.setDescription(jetPayPayment.getResponseText());
				payment.setPaymentDate(new Date());
				System.out.println(" invoice.getBalance() in paymentservice-- >> "+ invoice.getBalance());
				payment.setAmount(invoice.getBalance());
				payment.setSignup(invoice.getSignup());
				payment.setInvoice(invoice);
				payment.setPayer(invoice.getPayer());
				payment.setLastUpdated(Calendar.getInstance());
				payment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
				paymentDao.save(payment);

			}
		}
		}
		return jetPayPayment;
	}
	
	public boolean invoiceExits(Long signupId, Long payerId) {
		List<Invoice> invoiceList =invoiceDao.getBySignupPayer(signupId, payerId);
		if (invoiceList != null) {
			if (invoiceList.size() >0) {
				return true;
			}
		}
		return false;
		
	}
	
	public void saveSignupAssociatedOtherContacts(Signup signup, JSONObject cartItemMap) {

		Object emgContactObj = cartItemMap.get("emgContact");
		Object authContactsObj = cartItemMap.get("authContacts");
		if(emgContactObj != null && !emgContactObj.toString().equals("") && !emgContactObj.toString().equals("undefined")){
			AssociatedOtherContacts associatedEmgContact = new AssociatedOtherContacts();
			associatedEmgContact.setSignupId(signup.getSignupId());
			associatedEmgContact.setRole(Constants.ASSOCIATED_OTHER_CONTACTS_ROLE_Emergency_Contact);
			associatedEmgContact.setStatus(Constants.ACTIVE);
			associatedEmgContact.setContactId(Long.parseLong(emgContactObj.toString()));
			associatedOtherContactsDao.save(associatedEmgContact);
		}
		
		if(authContactsObj != null && !authContactsObj.toString().equals("") && !authContactsObj.toString().equals("undefined")){
			String authContactsStr = authContactsObj.toString();
			if(authContactsStr != null && !authContactsStr.equals("")){
				String authContacts[] = authContactsStr.split(";");
				if(authContacts.length > 0){
					for (int i = 0; i < authContacts.length; i++) {
						String authContact = authContacts[i];
						AssociatedOtherContacts associatedAuthContact = new AssociatedOtherContacts();
						associatedAuthContact.setSignupId(signup.getSignupId());
						associatedAuthContact.setRole(Constants.ASSOCIATED_OTHER_CONTACTS_ROLE_Authorized_Pick_Up);
						associatedAuthContact.setStatus(Constants.ACTIVE);
						associatedAuthContact.setContactId(Long.parseLong(authContact));
						associatedOtherContactsDao.save(associatedAuthContact);
					}
				}
			}
		}
	}
	
	public void initialRefund(Double reimbursedAmount, Payment p) {
		Payment refundPayment = new Payment();
		refundPayment.setPaymentMode(p.getPaymentMode());
		refundPayment.setTransactionId(p.getTransactionId());
		refundPayment.setContact(p.getContact());
		refundPayment.setCustomer(p.getCustomer());
		refundPayment.setSignup(p.getSignup());
		refundPayment.setInvoice(p.getInvoice());
		refundPayment.setStatus(Constants.INPROGRESS);
		refundPayment.setType(Constants.CREDITMEMOREFUND);
		refundPayment.setAmount(reimbursedAmount);
		refundPayment.setPaymentDate(new Date());
		refundPayment.setPayer(p.getPayer());
		
		Calendar cal = Calendar.getInstance();
		refundPayment.setLastUpdated(cal);
		refundPayment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(refundPayment);
	}
	
	public void saveSignupPromos(Signup signup, JSONArray promos) {
		if(promos != null && promos.size() > 0){
			for (Object promoObj : promos) {
				if(promoObj != null && !promoObj.toString().equals("") && !promoObj.toString().equals("undefined")){
					JSONObject promoJson = JSONObject.fromObject(promoObj.toString());
					ItemDetail promoDetail = itemDetailDao.findOne(Long.parseLong(promoJson.get("PromoId").toString()));
					
					Double monthlyDiscountAmount = 0d, actualDiscountAmount = 0d, remDiscount = 0d;	//discountAmount = 0d, 
					if(promoJson.get("discountValue") != null && !promoJson.get("discountValue").equals("")){
						//discountAmount = Double.parseDouble(promoJson.get("discountValue").toString());
						actualDiscountAmount = Double.parseDouble(promoJson.get("actualDiscountValue").toString());
						remDiscount = Double.parseDouble(promoJson.get("remDiscount").toString());
						
						if(promoJson.get("monthlyDiscountAmount") != null && !promoJson.get("monthlyDiscountAmount").toString().equals(""))
							monthlyDiscountAmount = Double.parseDouble(promoJson.get("monthlyDiscountAmount").toString());
					
						SignupPromotion promo = new SignupPromotion();
						promo.setMonthlyDiscountAmount(monthlyDiscountAmount);
						promo.setSignup(signup);
						promo.setPromotion(promoDetail);
						promo.setPromoCode(promoDetail.getPromoCode());
						promo.setDiscountAmount(actualDiscountAmount);
						promo.setRemainingDiscountAmount(remDiscount);	//promotionService.computeRemainingDiscountAmount(promo, discountAmount, actualDiscountAmount));
						signUpPromotionDao.save(promo);
					}
				}
			}
		}
	}
	
	private Double computeTotalDiscount(JSONArray promos){
		Double totalDiscount = 0d;
		if(promos != null && promos.size() > 0){
			for (Object promoObj : promos) {
				if(promoObj != null && !promoObj.toString().equals("") && !promoObj.toString().equals("undefined")){
					JSONObject promoJson = JSONObject.fromObject(promoObj.toString());
					if(promoJson.get("discountValue") != null){
						totalDiscount += Double.parseDouble(promoJson.get("discountValue").toString());	
					}
				}
			}
		}
		return totalDiscount;
	}
	
	public Double getOpenBalanceForInvoice(Invoice invoice){
		Double openBalanceOnInvoice = 0D, sumOfDebitPayment = 0D, sumOfCreditMemoFAWaiver = 0D, sumOfCreditMemoWaiver = 0D, sumOfPayment = 0D;
		Double sumOfCreditMemoWriteOff = 0D, sumOfCreditMemoRefund = 0D, sumOfNSF = 0D, sumOfChargeBack = 0D,sumOfCreditMemoADJ = 0D;
		//System.out.println(" invoice id  : "+invoice.getInvoiceId()+"  invoice amount  :"+invoice.getAmount() );
		List<Payment> payments = paymentDao.getPaymentListForInvoice(invoice.getInvoiceId());
		for (Payment payment : payments) {
			//System.out.println(" Type : "+payment.getType()+ " TypeEnum : "+PaymentTypeEnum.getEnumByString(payment.getType())+" Amount : "+payment.getAmount());
			switch (PaymentTypeEnum.getEnumByString(payment.getType())) {
				
				case CreditMemoFAWaiver:
					sumOfCreditMemoFAWaiver += payment.getAmount();
					break;
				case CreditMemoWaiver:
					sumOfCreditMemoWaiver += payment.getAmount();
					break;
				case CreditMemoRefund:
					sumOfCreditMemoRefund += payment.getAmount();
					break;
				case CreditMemoWriteOff:
					sumOfCreditMemoWriteOff += payment.getAmount();
					break;
				case Debit:
					sumOfDebitPayment += payment.getAmount();
					break;
				case NSF:
					sumOfNSF += payment.getAmount();
					break;
				case ChargeBack:
					sumOfChargeBack += payment.getAmount();
					break;
				case CreditMemoADJ:
				    sumOfCreditMemoADJ += payment.getAmount();
				case Payment:
					sumOfPayment += payment.getAmount();
				default:
					break;
			} 
		}	
		//trueInvoiceValue = (invoice.getAmount()+sumOfCreditMemoADJ) - (sumOfCreditMemoWaiver + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff);		
		//(Inv Amt + CM/FA waiver + CM/Write Off + CM / Waiver + CM / Adj) – (Payment (previously DEBIT) + NSF + Charge Back + CM / Refund)
		openBalanceOnInvoice = (invoice.getAmount() + sumOfCreditMemoFAWaiver + sumOfCreditMemoWriteOff + sumOfCreditMemoWaiver + sumOfCreditMemoADJ )
								- (sumOfNSF + sumOfChargeBack + sumOfCreditMemoRefund + sumOfPayment);
		
		return openBalanceOnInvoice;
	}
	
	public void createCMWaiverPayment(Double openBalanceAmount, Signup s, Invoice i) {
		Payment cmWaiverPayment = new Payment();
		cmWaiverPayment.setPaymentMode(PaymentTypeEnum.CreditMemoWriteOff.getValue());
		//cmWaiverPayment.setTransactionId(p.getTransactionId());
		cmWaiverPayment.setContact(s.getContact());
		cmWaiverPayment.setCustomer(s.getCustomer());
		cmWaiverPayment.setSignup(s);
		cmWaiverPayment.setInvoice(i);
		cmWaiverPayment.setStatus(Constants.INPROGRESS);
		cmWaiverPayment.setType(Constants.CREDITMEMOWAIVER);
		cmWaiverPayment.setAmount(openBalanceAmount);
		cmWaiverPayment.setPaymentDate(new Date());
		cmWaiverPayment.setPayer(i.getPayer());
		
		Calendar cal = Calendar.getInstance();
		cmWaiverPayment.setLastUpdated(cal);
		cmWaiverPayment.setSource(Constants.PAYMENT_PORTAL_SOURCE);
		paymentDao.save(cmWaiverPayment);
	}
	
	public List<Invoice> saveMultipleInvoices(Account customer, User u, Signup signup, Payer payer, List<Invoice> invoices, ItemDetail itemDetailObj) {
		List<Invoice> invoiceLst = new ArrayList<Invoice>();
		if(invoices != null && !invoices.isEmpty() && invoices.get(0) != null ){
			invoices.get(0).setCustomer(customer);
			invoices.get(0).setContact(u);				
			invoices.get(0).setSignup(signup);			
			invoices.get(0).setPayer(payer);
			invoices.get(0).setBillDate(invoices.get(0).getDueDate());
			invoices.get(0).setInvoiceDate(new Date());			
			invoices.get(0).setLastUpdated(Calendar.getInstance());
			invoices.get(0).setInvoiceNumber(invoiceService.generateInvoiceNumber(customer.getAccountId(), signup.getSignupId(), payer.getId(), invoices.get(0).getBillDate()));
			Invoice i = invoiceDao.save(invoices.get(0));
			invoiceLst.add(i);			
		}
		int index = 0;
		if(itemDetailObj != null && itemDetailObj.getCategory() != null && itemDetailObj.getCategory().equalsIgnoreCase(Constants.ANNUAL_CAMPAIGN_SUBCATAGORY)){
			for(Invoice invoice : invoices){
				if(index != 0){
					invoice.setCustomer(customer);
					invoice.setContact(u);								
					invoice.setSignup(signup);					
					invoice.setPayer(payer);
					invoice.setBillDate(invoice.getDueDate());
					invoice.setInvoiceDate(new Date());			
					invoice.setLastUpdated(Calendar.getInstance());
					invoice.setInvoiceNumber(invoiceService.generateInvoiceNumber(customer.getAccountId(), signup.getSignupId(), payer.getId(), invoice.getBillDate()));
					Invoice i = invoiceDao.save(invoice);
					invoiceLst.add(i);
				}
				index++;
			}
		}
		
		return invoiceLst;
	}
	
	public Invoice saveDownPaymentinvoice(Account customer, Double downPaymentAmt, User u, Signup signup, Payer payer) {
		Invoice invoice = new Invoice();
		invoice.setCustomer(customer);
		invoice.setContact(u);
		//invoice.setOrderDate(new Date());
		invoice.setDueDate(new Date());
		invoice.setAmount(downPaymentAmt);	
		invoice.setSignup(signup);
		//invoice.setFAamount(FAamount);
		invoice.setPayer(payer);
		invoice.setBillDate(new Date());
		invoice.setInvoiceDate(new Date());
		
		Calendar lastUpdCal = Calendar.getInstance();
		invoice.setLastUpdated(lastUpdCal);
		
		Invoice i = new Invoice();
		i = invoiceDao.save(invoice);
		return i;
	}
	
	public List<Invoice> saveEmployerMatchInvoices(Account customer, User u, Signup signup, Payer payer, List<Invoice> invoices, ItemDetail itemDetailObj) {
		List<Invoice> invoiceLst = new ArrayList<Invoice>();
		for (Invoice invoice : invoices) {
			invoice.setCustomer(customer);
			invoice.setContact(u);
			invoice.setSignup(signup);
			invoice.setPayer(payer);
			invoice.setBillDate(invoice.getDueDate());
			invoice.setInvoiceDate(new Date());
			invoice.setLastUpdated(Calendar.getInstance());
			invoice.setInvoiceNumber(invoiceService.generateInvoiceNumber(customer.getAccountId(), signup.getSignupId(), payer.getId(), invoice.getBillDate()));
			Invoice i = invoiceDao.save(invoice);
			invoiceLst.add(i);
		}
		return invoiceLst;
	}
}
