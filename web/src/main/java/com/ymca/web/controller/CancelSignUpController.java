package com.ymca.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.ActivityDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.ItemDetailDaysDao;
import com.ymca.dao.ItemDetailPricingRuleDao;
import com.ymca.dao.JetPayPaymentDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SignUpDaysDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.WaiversAndTCDao;
import com.ymca.model.ItemDetail;
import com.ymca.model.ItemDetailDays;
import com.ymca.model.ItemDetailPricingRule;
import com.ymca.model.Payment;
import com.ymca.model.SignUpDays;
import com.ymca.model.Signup;
import com.ymca.model.WaiversAndTC;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.CancelSignUpService;
import com.ymca.web.service.CapacityManagementService;
import com.ymca.web.service.PaymentService;
import com.ymca.web.service.SignUpService;
import com.ymca.web.util.Constants;
import com.ymca.web.util.MemberAge;
import com.ymca.web.util.PropFileUtil;

@Controller
public class CancelSignUpController extends BaseController {

	@PersistenceContext
	public EntityManager em; 
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private ItemDetailPricingRuleDao itemDetailPricingRuleDao;
	
	@Resource
	private JetPayPaymentDao jetPayPaymentDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private ItemDetailDaysDao itemDaysDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private PaymentService paymentService;
	
	@Resource
	private SignUpDaysDao signUpDaysDao;
	
	@Resource
	private WaiversAndTCDao waiversAndTCDao;
	
	@Resource
	private CancelSignUpService cancelSignUpService ; 
	
	@Resource
	private CapacityManagementService capacityManagementService ;
	
	@Resource
	private SignUpService signUpService ;
	
	List<String> daysArr = Arrays.asList("Sun","Mon","Tues","Wed","Thurs","Fri","Sat");
	
	@RequestMapping(value="/cancelEvent", method=RequestMethod.GET)
    public ModelAndView cancelEventWizard(final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		try{
			JSONArray enrolledProgramDetailsArr = new JSONArray(); 
			JSONArray errorMessageArr = new JSONArray(); 
			String agentUid = getAgentUidFromSession();
			String signupId = request.getParameter("sId");
			
			if(!"".equals(signupId)){
				Object programObj[] = (Object[]) signupDao.getSignupProgramById(Long.parseLong(signupId));
				Signup signup = (Signup) programObj[0];
		    	ItemDetail program = (ItemDetail) programObj[1];
		    	
		    	
		    	//Date programStartDate = program.getStartDate();
		    	Date programEndDate = program.getEndDate();
		    	java.util.Date currDate= new java.util.Date();
		    	
		    	long diff = programEndDate.getTime() - currDate.getTime();
		    	long diffDays = diff / (24 * 60 * 60 * 1000);
		    	
		    	//System.out.println(diff);
		    	//System.out.println(diffDays);
		    	int cancellationCutOffDays = program.getCancellationCutOffPeriod();
		    	
		    	if(((agentUid != null && !agentUid.equals("") && diffDays > 0) || (agentUid == null && diffDays >= cancellationCutOffDays))){
		    		JSONObject jsonObj = new JSONObject();
		    		jsonObj.put("programId", program.getId());
			    	jsonObj.put("signupId", signup.getSignupId());
			    	jsonObj.put("contactName", signup.getContactName());
					jsonObj.put("programName", program.getRecordName());
					jsonObj.put("programDescription", program.getDescription());
					jsonObj.put("programStartDate", program.getStartDate());
					jsonObj.put("programEndDate", program.getEndDate());
					jsonObj.put("programStartTime", program.getStartTime());
					jsonObj.put("programEndTime", program.getEndTime());
					jsonObj.put("programStatus", program.getStatus());
					if(program.getCategory() != null && program.getCategory().equalsIgnoreCase("EVENT")){
						jsonObj.put("noOfTickets", signup.getNoOfTickets());	
					}else{
						jsonObj.put("noOfTickets", "0");
					}
					jsonObj.put("programEnrollmentStatus", signup.getStatus());
					jsonObj.put("programEnrollmentDate", signup.getSignupDate());
					
					
					List<ItemDetailPricingRule> lstCancellationPR = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(signup.getItemDetail().getId(), Constants.Status_Active, Constants.Status_Active, Constants.CANCEL);
					double cancellationPrice = 0D;
					if(lstCancellationPR.size()>0){
						for(ItemDetailPricingRule cancellationPR: lstCancellationPR){
							cancellationPrice += cancellationPR.getPricingRule().getTierPrice();
						}
					}
					jsonObj.put("cancellationPrice", cancellationPrice);
					enrolledProgramDetailsArr.add(jsonObj);			
		    	} else {
		    		JSONObject e = new JSONObject();
					e.put("errMsg","Your cancellation period has expired, Please call Y agent to cancel the program.");
					errorMessageArr.add(e);
				}
		    	
			}
			model.addAttribute("enrolledProgramDetailsArr", enrolledProgramDetailsArr);
			
			List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
			if(terms != null && !terms.isEmpty()){
	        	model.addAttribute("terms", terms.get(0).getTcDescription());
	        }
/*			TandC terms = tandCDao.findByType(Constants.CANCELLATION);
			if(terms!=null){
				model.addAttribute("terms", terms.getTandc());
			}*/
			model.addAttribute("errorMessageArr", errorMessageArr);
			model.addAttribute("allItemType", PropFileUtil.getValue(Constants.ALL_ITEM_TYPES));
		}catch(Exception e){
			log.error("Error  ",e);
		}
		
		return new ModelAndView("cancelProgram", model.asMap());
	}

	
	@RequestMapping(value="/cancelProgram", method=RequestMethod.GET)
    public ModelAndView cancelProgramWizard(String sId) { 
		Model model = new ExtendedModelMap();
		JSONArray enrolledProgramDetailsArr = new JSONArray(); 
		JSONArray errorMessageArr = new JSONArray(); 
		JSONArray inServiceFutureDaysArr = new JSONArray(); 
		
		String signupId = sId ;
		boolean allowCancel = true;
		
		if(!"".equals(signupId)){
			Signup signup = signupDao.getOne(Long.parseLong(signupId));
	    	ItemDetail program =signup.getItemDetail();
	    	
	    	String agentUid = getAgentUidFromSession();
	    	
	    	Date programEndDate = program.getEndDate();
	    	java.util.Date currDate= new java.util.Date();
	    	
	    	long diff = programEndDate.getTime() - currDate.getTime();
	    	long diffDays = diff / (24 * 60 * 60 * 1000);
	    	
	    	int cancellationCutOffDays = Integer.parseInt(convertNullToZero(Long.parseLong(program.getCancellationCutOffPeriod().toString())).toString());
	    	
	    	Boolean skipItemDetailCancellationCutoffPeriod = false;
	    	if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory())){
	    		skipItemDetailCancellationCutoffPeriod = true;
    		}
	    	
	    	if(((agentUid != null && !agentUid.equals("") && diffDays > 0) || (agentUid == null && diffDays >= cancellationCutOffDays)) 
	    			|| skipItemDetailCancellationCutoffPeriod){
	    		// for inservice check on package option
	    		if(agentUid == null && Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory()) && "PACKAGE".equalsIgnoreCase(signup.getSignUpPricingOption())){
	    			JSONObject e = new JSONObject();
	    			e.put("errMsg","You cannot cancel In-Service package. Please contact Y Agent");
	    			errorMessageArr.add(e);
	    			allowCancel = false;
	    		}
	    		else{
	    			
	    			try {
		    			// for inservice show future days to cancel
		    			if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory())){
		    				Date today = new Date();
		    				DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		    				List<SignUpDays> lstSignupDays = signUpDaysDao.findBySignupAndStatusAndSignupDateAfter(signup, Constants.ACTIVE, sdf.parse(sdf.format(today)));
		    				
		    				for(SignUpDays signUpDays: lstSignupDays){
		    					Integer selDaysId = daysArr.indexOf(signUpDays.getDay());
		    					ItemDetailDays itemDetailDays = itemDaysDao.getByItemDetailAndDay(program,selDaysId.toString());
		    					if(itemDetailDays!=null && itemDetailDays.getCancellationCutOffPeriod()!=null){
		    						int itemdayCancellationCutOffDays = Integer.parseInt(convertNullToZero(Long.parseLong(itemDetailDays.getCancellationCutOffPeriod().toString())).toString());
		    						Date itemDaySignupDate = signUpDays.getSignupDate();
		    						long itemDayDiff = itemDaySignupDate.getTime() - currDate.getTime();
		    				    	long itemDayDiffDays = itemDayDiff / (24 * 60 * 60 * 1000);
		    						
		    						if(itemDayDiffDays >= itemdayCancellationCutOffDays){
			    						JSONObject obj = new JSONObject();
				    					obj.put("signupdayId", daysArr.indexOf(signUpDays.getDay()));
				    					obj.put("signupday", signUpDays.getDay());
				    					obj.put("signupdate", signUpDays.getSignupDate());
				    					inServiceFutureDaysArr.add(obj);
			    					}
		    					}
		    				}
		    				
		    				// check if in service has no future days
		    				if(inServiceFutureDaysArr!=null && inServiceFutureDaysArr.size()<=0){
		    					JSONObject e = new JSONObject();
		    	    			e.put("errMsg","No future days to Cancel");
		    	    			errorMessageArr.add(e);
		    	    			allowCancel = false;
		    				}
		    			}
		    			
		    			if(allowCancel){
							JSONObject jsonObj = new JSONObject();
							jsonObj.put("programId", program.getId());
							jsonObj.put("signupId", signup.getSignupId());
							jsonObj.put("contactName", signup.getContactName());
							jsonObj.put("programName", program.getRecordName());
							jsonObj.put("programDescription", program.getDescription());
							jsonObj.put("programStartDate", program.getStartDate());
							jsonObj.put("programEndDate", program.getEndDate());
							jsonObj.put("programStartTime", program.getStartTime());
							jsonObj.put("programEndTime", program.getEndTime());
							jsonObj.put("programStatus", program.getStatus());
							if(program.getCategory() != null && program.getCategory().equalsIgnoreCase("EVENT")){
								jsonObj.put("noOfTickets", signup.getNoOfTickets());	
							}else{
								jsonObj.put("noOfTickets", "0");
							}
							jsonObj.put("programEnrollmentStatus", signup.getStatus());
							jsonObj.put("programEnrollmentDate", signup.getSignupDate());
							if(program.getFutureCancellationAllowed() != null && program.getFutureCancellationAllowed().equalsIgnoreCase("Yes")){
								jsonObj.put("futureCancellationAllowed", program.getFutureCancellationAllowed());	
							}else{
								jsonObj.put("futureCancellationAllowed", "No");
							}
							
							double cancellationPrice = 0D;
							if(!SignupStatusEnum.Waitlisted.toString().equalsIgnoreCase(signup.getStatus())){
								List<ItemDetailPricingRule> lstCancellationPR = itemDetailPricingRuleDao.findByItemDetailIdAndStatusAndPricingRule_StatusAndPricingRule_Type(signup.getItemDetail().getId(), Constants.Status_Active, Constants.Status_Active, Constants.CANCEL);
								
								if(lstCancellationPR.size()>0){
									for(ItemDetailPricingRule cancellationPR: lstCancellationPR){
										cancellationPrice += cancellationPR.getPricingRule().getTierPrice();
									}
								}
							}
							jsonObj.put("cancellationPrice", cancellationPrice);
							
							if(program.getType().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_PROGRAM)){
							
								double reimbursedAmount = 0D;
								reimbursedAmount = cancelSignUpService.evaluateRefundExpressionAndReturnReimbursedAmount(signup, cancellationPrice);
								if(reimbursedAmount>0){
									Double debitPayment = 0D;
									Double nsfOrCBorRefundPayment = 0D;
									List<Payment> lstAllPaymentsBySignup = paymentDao.findBySignupOrderByAmountDesc(signup);
									for(Payment payment: lstAllPaymentsBySignup){
										if(Constants.DEBIT.equalsIgnoreCase(payment.getType())){
											debitPayment += payment.getAmount();
										}
										else if(Constants.NSF.equalsIgnoreCase(payment.getType()) || Constants.CHARGEBACK.equalsIgnoreCase(payment.getType()) || Constants.CREDITMEMOREFUND.equalsIgnoreCase(payment.getType())){
											nsfOrCBorRefundPayment += payment.getAmount();
										}
									}
									
									if((debitPayment-nsfOrCBorRefundPayment)-reimbursedAmount < 0){
										reimbursedAmount = 0D;
									}
								}
								jsonObj.put("reimbursedAmount", reimbursedAmount);
								
							}else if(program.getType().equalsIgnoreCase(Constants.PRODUCT_CATEGORY_CHILD_CARE)){
								
							}
							
							// get related sign ups
							if (StringUtils.equalsIgnoreCase(program.getType(),Constants.CAMP_TYPE )) {
								List<Signup> relatedSignUps = signupDao.findByParentSignUpIdAndStatus(signup.getSignupId(), SignupStatusEnum.Active.toString()); 
								
								Signup newParentSignup = null ;
								for (Signup s : relatedSignUps) {
									MemberAge mAge = new MemberAge();
									Integer contactAge  = mAge.getAge(s.getContact().getDateOfBirth());
									 if (contactAge >= 18) {
										 newParentSignup = s ;
										 break ;
									 }
								}
								if (newParentSignup == null)	model.addAttribute("relatedSignUps", relatedSignUps);
							} else if (StringUtils.equalsIgnoreCase(program.getCategory(),Constants.CAMP_TYPE_RESIDENCE)) {
								List<Signup> relatedSignUps = signupDao.findByParentSignUpIdAndStatus(signup.getSignupId(), SignupStatusEnum.Active.toString()); 
								model.addAttribute("relatedSignUps", relatedSignUps);
							}
							
							enrolledProgramDetailsArr.add(jsonObj);
		    			}
					} catch (Exception e) {
						log.error("Error  ",e);
						JSONObject e1 = new JSONObject();
		    			e1.put("errMsg",e.getMessage());
		    			errorMessageArr.add(e1);
		    			allowCancel = false;
					}		
	    		}
	    	}
	    	
		}
		model.addAttribute("enrolledProgramDetailsArr", enrolledProgramDetailsArr);
		
		//TandC terms = tandCDao.findByType(Constants.CANCELLATION);
		List<WaiversAndTC> terms = waiversAndTCDao.getTcByTypeAndDate(Constants.WAIVERS_TC_TYPE_COMMON);
		if(terms != null && !terms.isEmpty()){
        	model.addAttribute("terms", terms.get(0).getTcDescription());
        }
		
		if(allowCancel && enrolledProgramDetailsArr.size()<=0){
			JSONObject e = new JSONObject();
			e.put("errMsg","Your cancellation period has expired, Please call Y agent to cancel the program.");
			errorMessageArr.add(e);
		}
		model.addAttribute("errorMessageArr", errorMessageArr);
		model.addAttribute("inServiceFutureDaysArr", inServiceFutureDaysArr);
		model.addAttribute("allItemType", PropFileUtil.getValue(Constants.ALL_ITEM_TYPES));
		
		return new ModelAndView("cancelProgram", model.asMap());
	}
	
	@RequestMapping(value="/cancelsignup", method=RequestMethod.GET)
    public @ResponseBody String  cancelSignup(@RequestParam String sId, @RequestParam String cancelDt) throws Exception { 	
		if(!"".equals(sId)){
			cancelSignUpService.cancelSignUp(sId, cancelDt,getPortalLastModifiedBy(),request.getParameter("selDays"));
		}
		return Constants.SUCCESS;
	}

}