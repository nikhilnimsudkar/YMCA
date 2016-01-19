package com.ymca.web.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.SignUpDaysDao;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.JetPayPayment;
import com.ymca.model.Payer;
import com.ymca.model.PaymentMethod;
import com.ymca.model.SignUpDays;
import com.ymca.model.Signup;
import com.ymca.web.enums.PaymentTypeEnum;
import com.ymca.web.enums.ProductTypeEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.util.Constants;

@Service
public class CapacityManagementService extends BaseService {

	@Resource
	private PayerDao payerDao ;
	
	@Resource
	private PaymentService paymentService ;
	
	@Resource
	private SignUpDaysDao signUpDaysDao ;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	
	public synchronized void signupCapacityManagement(ItemDetail itemDetail, Signup signup, boolean update) {
		
		signupConfirmCapacityManagement(itemDetail, signup, update, false);
		
		/*String signupStatus = SignupStatusEnum.Active.toString();
		Long waitListPriority = 0L;
		Integer noofTickets = 1;
		
		if(itemDetail!=null){
			Long remainingCapacity = convertNullToZero(itemDetail.getRemainingCapacity());
			//Long actualRemainingCapacity = convertNullToZero(itemDetail.getActualRemainingCapacity());
			//Long waitListCounter = convertNullToZero(itemDetail.getWaitlistCounter());
			
			if(signup.getNoOfTickets()!=null){
				noofTickets = signup.getNoOfTickets();
			}
			
			if(update)
				updateCapacity(itemDetail,noofTickets);
			
			if( remainingCapacity.compareTo(0L)<=0 || remainingCapacity < noofTickets){
				//remaining capacity is less than 0
				signupStatus = SignupStatusEnum.Waitlisted.toString();
				Object sObj = signupDao.getMaxWaitlistPriorityByItemDetail(itemDetail);
				if(sObj!=null)
					waitListPriority = convertNullToZero(Long.parseLong(sObj.toString()));
				waitListPriority = waitListPriority+1;
				//waitListCounter = waitListCounter+1;
			}
			
			
		}
		
		if(update)
			signup.setStatus(signupStatus);
		signup.setWaitlist(waitListPriority);
		Calendar cal = Calendar.getInstance();
		signup.setLastUpdated(cal);
		signupDao.save(signup);*/
	}

	public synchronized void signupConfirmCapacityManagement(ItemDetail itemDetail, Signup signup, boolean update, boolean isAgent) {
		String signupStatus = SignupStatusEnum.Active.toString();
		Long waitListPriority = 0L;
		Integer noofTickets = 1;
		
		if(itemDetail!=null){
			Long remainingCapacity = convertNullToZero(itemDetail.getRemainingCapacity());
			//Long actualRemainingCapacity = convertNullToZero(itemDetail.getActualRemainingCapacity());
			//Long waitListCounter = convertNullToZero(itemDetail.getWaitlistCounter());
			
			if(signup.getNoOfTickets()!=null){
				noofTickets = signup.getNoOfTickets();
			}
			
			if(update)
				updateCapacity(itemDetail,noofTickets);
			
			if(!isAgent && (remainingCapacity.compareTo(0L)<=0 || remainingCapacity < noofTickets)){
				//remaining capacity is less than 0
				signupStatus = SignupStatusEnum.Waitlisted.toString();
				Object sObj = signupDao.getMaxWaitlistPriorityByItemDetail(itemDetail);
				if(sObj!=null)
					waitListPriority = convertNullToZero(Long.parseLong(sObj.toString()));
				waitListPriority = waitListPriority+1;
				//waitListCounter = waitListCounter+1;
			}
		}
		
		if(update)
			signup.setStatus(signupStatus);
		signup.setWaitlist(waitListPriority);
		Calendar cal = Calendar.getInstance();
		signup.setLastUpdated(cal);
		signupDao.save(signup);
	}
	
	public void cancelCapacityManagement(ItemDetail program, Signup signup) {
		Long webCapacity = convertNullToZero(program.getWebCapacity());
		Long actualCapacity = convertNullToZero(program.getActualCapacity());
		Long remainingCapacity = convertNullToZero(program.getRemainingCapacity());
		Long actualRemainingCapacity = convertNullToZero(program.getActualRemainingCapacity());
		//Long waitListCounter = 0L;
		Long totalConfirmedSignup = 0L;
		
		List<Signup> lstConfirmedSignup = signupDao.getByItemDetailAndStatus(program,SignupStatusEnum.Active.toString());
		
		if(lstConfirmedSignup!=null && lstConfirmedSignup.size()>0){
			totalConfirmedSignup = (long) lstConfirmedSignup.size();
		}
		
		/*if(ProductTypeEnum.PROGRAM.toString().equalsIgnoreCase(program.getCategory()) || ProductTypeEnum.CAMP.toString().equalsIgnoreCase(program.getCategory()) 
				|| (Constants.CHILDCARE_TYPE.equalsIgnoreCase(program.getType()) && Constants.PRODUCT_CATEGORY_AFTER_SCHOOL.equalsIgnoreCase(program.getCategory()))){
			
		} 
		else*/ if(ProductTypeEnum.EVENT.toString().equalsIgnoreCase(program.getCategory())){
			remainingCapacity = remainingCapacity + signup.getNoOfTickets();
			actualRemainingCapacity = actualRemainingCapacity + signup.getNoOfTickets();
		}else{
			remainingCapacity = webCapacity - totalConfirmedSignup;
			actualRemainingCapacity = actualCapacity - totalConfirmedSignup;
		}
		
		program.setRemainingCapacity(remainingCapacity);
		program.setActualRemainingCapacity(actualRemainingCapacity);
		//program.setWaitlistCounter(waitListCounter);
		populateAndSaveItemDetail(program);
	}
	
	public void automaticWaitListProcessing(ItemDetail itemDetail) throws Exception {
		if(itemDetail!=null){
			List<Signup> lstWLSignup = signupDao.getByItemDetailAndStatusOrderByWaitlistAsc(itemDetail,SignupStatusEnum.Waitlisted.toString()); // get WL signup associated to program
			if(lstWLSignup!=null && lstWLSignup.size()>0){
				Long remainingCapacity = convertNullToZero(itemDetail.getRemainingCapacity());

				if(itemDetail.getAutomatedWaitlist().equalsIgnoreCase("Yes") && remainingCapacity>0){ // automated waitlist is true and remaining capacity is > 0
					for(Signup WLsignp : lstWLSignup){
						if((WLsignp.getNoOfTickets() !=null && WLsignp.getNoOfTickets() < remainingCapacity) || WLsignp.getNoOfTickets()==null){
							List<Payer> lstPayer = payerDao.findBySignupAndType(WLsignp,Constants.SELF);
							
							// process payment block
							if(lstPayer!=null && lstPayer.size()>0){
								Payer payer = lstPayer.get(0);
								if(payer!=null){
									String paymentMode = payer.getPaymentMode();
									PaymentMethod paymentMethod = payer.getPaymentMethod();
									if(paymentMethod!=null && paymentMode!=null && !"".equals(paymentMode)){
										String payAmount = WLsignp.getFinalAmount();
										JetPayPayment jp = null;
										
										// comment this line
										//payAmount = "10";
										if(Double.parseDouble(payAmount)>0){
											if(Constants.ACH.equalsIgnoreCase(paymentMode)){
												// process ACH payment
												jp = processACHjetPayTransaction(paymentMethod.getTokenNumber(),payAmount,paymentMethod.getCheckNumber(),paymentMethod.getFullName());
											}
											else if(Constants.CREDIT.equalsIgnoreCase(paymentMode)){
												// process CC payment
												jp = processCCjetPayTransaction(paymentMethod.getTokenNumber(),payAmount);
											}
										}
										
										if(jp==null){
											Signup s = new Signup();
											s.setStatus(SignupStatusEnum.Inactive.toString());
											Calendar cal = Calendar.getInstance();
											s.setLastUpdated(cal);
											s.setSignupId(WLsignp.getSignupId());
											signupDao.save(s);
										} else {
											signupCapacityManagement(itemDetail,WLsignp, true);
											Invoice invoice = paymentService.saveinvoice(WLsignp.getCustomer(), WLsignp.getFAamount().toString(), WLsignp.getContact(), WLsignp, payer);
							    			JetPayPayment jetpay = paymentService.savepayment(paymentMethod.getId().toString(), jp.getJpReturnHash(), jp.getOrderNumber(), WLsignp.getCustomer(), WLsignp.getContact(), WLsignp, payer, invoice, paymentMode, invoice.getAmount());
							    			// added on 24072015 - if FA amount on invoice > 0, create payment record of type "Credit Memo/FA Waiver" and amount will be FA_AMOUNT
						    				Object fa = WLsignp.getFAamount();
							    			if(fa!=null && Double.parseDouble(fa.toString())>0){
						    					String paymentType = PaymentTypeEnum.CreditMemoFAWaiver.toString();
						    					paymentService.savepayment(paymentMethod.getId().toString(), jp.getJpReturnHash(), jp.getOrderNumber(), WLsignp.getCustomer(), WLsignp.getContact(), WLsignp, payer, invoice, paymentMode, Double.parseDouble(fa.toString()),paymentType);
						    				}
						    				//end
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	public Boolean updateCapacityCancelledSignupDays(Signup signup,ItemDetail program, String inserviceSelectedFutureDays) { // this method will be called by inservice and child care 
		Boolean allFutureDaysCancelled = true;
		if(signup.getStatus()!=null && !SignupStatusEnum.Waitlisted.toString().equalsIgnoreCase(signup.getStatus())){
			List<SignUpDays> lstSignupDays = new ArrayList<SignUpDays>();
			if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory())){
				Date today = new Date();
				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
				try {
					if(inserviceSelectedFutureDays!=null){
						List<String> inserviceSelectedFutureDaysLst = Arrays.asList(inserviceSelectedFutureDays.split(","));
						if(inserviceSelectedFutureDaysLst!=null && inserviceSelectedFutureDaysLst.size()>0){
							List<SignUpDays> lstSignupFutureDays = signUpDaysDao.findBySignupAndStatusAndSignupDateAfter(signup, Constants.ACTIVE, sdf.parse(sdf.format(today)));
							
							if(lstSignupFutureDays!=null && lstSignupFutureDays.size()>0){
								for(SignUpDays signUpDay: lstSignupFutureDays){
									if(signUpDay!=null && signUpDay.getDay()!=null && !"".equalsIgnoreCase(signUpDay.getDay())){
										if(inserviceSelectedFutureDaysLst.contains(signUpDay.getDay())){
											// day exists i.e. day was selected on cancel wizard screen
											lstSignupDays.add(signUpDay);
										} else {
											allFutureDaysCancelled = false;
										}
										
									}
								}
							}
						}	
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else{
				lstSignupDays = signUpDaysDao.findBySignup(signup);
			}
			
			if(lstSignupDays!=null && lstSignupDays.size()>0){
				String selDays = "";
				String selDaysId = "";
				for(SignUpDays idys : lstSignupDays){
			    	  if(idys!= null && idys.getDay()!=null){
			    		  	String day = idys.getDay();
			    		  	if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory())){
			    		  		// set signup day inactive for in service
			    		  		idys.setStatus_c(Constants.INACTIVE);
			    		  		signUpDaysDao.save(idys);
			    		  	}
			    		  	
					    	if(day!=null && !"".equals(day)){
					    	  	selDaysId = selDaysId + ";" + daysArr.indexOf(day);
					    		selDays = selDays + ";" + day;
			    		  	}
			    	  }
			    }
				selDaysId = selDaysId.replaceFirst(";", "");
				if(selDaysId!=null && !"".equals(selDaysId))
					updatecapacityIncrementForDays(program,program.getId().toString(),selDaysId ,"");
			}
		}
		return allFutureDaysCancelled;
	}
	
	public void updatecapacityIncrementForDays(ItemDetail itemDetail, String childcareItemDetailId, String selDaysId, String WLDays) {
		if(childcareItemDetailId!=null && !"".equals(childcareItemDetailId) && selDaysId!=null && !"".equals(selDaysId)){
			if(itemDetail.getId().equals(Long.parseLong(childcareItemDetailId))){
				List<String> lstSelDayId = Arrays.asList(selDaysId.split(";"));
				if(lstSelDayId.size()>0){
					List<String> lstWLDays = Arrays.asList(WLDays.split(";"));
					for(String dayId: lstSelDayId){
						boolean updateDay = true;
						if(lstWLDays.size()>0){
							for(String wlDays: lstWLDays){
								if(daysArr.get(Integer.parseInt(dayId)).equalsIgnoreCase(wlDays)){
									updateDay = false;
									break;
								}
							}
						}
						
						// update capacity for only confirmed days
						if(updateDay){

							ItemDetailDays itemDetailDays = itemDetailDaysDao.getByItemDetailAndDay(itemDetail, dayId);
							
							if(itemDetailDays!=null){
								Long remainingCapacity = convertNullToZero(itemDetailDays.getRemainingCapacity());
								Long actualRemainingCapacity = convertNullToZero(itemDetailDays.getActualRemainingCapacity());
								remainingCapacity = remainingCapacity+1;
								actualRemainingCapacity = actualRemainingCapacity+1;

								itemDetailDays.setRemainingCapacity(remainingCapacity);
								itemDetailDays.setActualRemainingCapacity(actualRemainingCapacity);
								populateAndSaveItemDetailDays(itemDetailDays);
							}
						}
					}
				}
			}
		}
	}
	
	public boolean isFutureCancelDate(String cancelDt) {
		boolean isFutureCancelDate = false;
		if(cancelDt != null && !cancelDt.equals("")){
			Date cancelDate = new Date(cancelDt);
			Date today = new Date();
			if(cancelDate.after(today)){
				isFutureCancelDate = true;
			}
		}
		return isFutureCancelDate;
	}
	
	public void checkStopConfirmedSignups(ItemDetail itemDetail, ItemDetailDays itemDetailDays) {
		if(itemDetailDays == null){
			if((itemDetail.getAutomatedWaitlist() == null || itemDetail.getAutomatedWaitlist().equalsIgnoreCase("No"))
				&& (itemDetail.getStopConfirmedSignUps() == null || !itemDetail.getStopConfirmedSignUps().equalsIgnoreCase("Yes"))){
				itemDetail.setStopConfirmedSignUps("Yes");
				itemDetailDao.save(itemDetail);
			}
		}else{
			if((itemDetail.getAutomatedWaitlist() == null || itemDetail.getAutomatedWaitlist().equalsIgnoreCase("No"))
				&& (itemDetailDays.getStopConfirmedSignUps() == null || !itemDetailDays.getStopConfirmedSignUps().equalsIgnoreCase("Yes"))){
				itemDetailDays.setStopConfirmedSignUps("Yes");
				itemDetailDaysDao.save(itemDetailDays);
			}
		}
	}
}
