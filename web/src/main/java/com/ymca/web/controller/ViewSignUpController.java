package com.ymca.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.PaymentDao;
import com.ymca.dao.PaymentMethodDao;
import com.ymca.dao.SignUpDaysDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.Activity;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.Payer;
import com.ymca.model.Payment;
import com.ymca.model.PaymentMethod;
import com.ymca.model.SignUpDays;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.InvoiceService;
import com.ymca.web.service.SignUpService;
import com.ymca.web.util.Constants;
import com.ymca.web.util.PropFileUtil;
import com.ymca.web.util.StringUtil;

@Controller
public class ViewSignUpController extends BaseController {

	@PersistenceContext
	public EntityManager em; 
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private PaymentMethodDao paymentMethodDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private PaymentDao paymentDao;
	
	@Resource
	private ItemDetailDao itemDetailsDao;
	
	@Resource
	private ActivityDao interactionDao;
	
	@Resource
	private SignUpDaysDao signUpDaysDao;
	
	@Resource
	private SignUpService signUpService ;
	
	@Resource
	private InvoiceService invoiceService;
	
	@Resource
	private PayerDao payerDao;
	
	@Resource
	private InvoiceDao invoiceDao;
	
	@RequestMapping(value="/viewAllPrograms", method=RequestMethod.GET)
    public ModelAndView viewAllPrograms(final HttpServletRequest request, final HttpServletResponse response) {
		Model model = new ExtendedModelMap();
		//DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		
		try{
			String itemType = request.getParameter("itemType");
			String contactName = request.getParameter("contactName");
			String programStDt = request.getParameter("programStDt");
			String programEndDt = request.getParameter("programEndDt");
			
			// check if user is logged in
			Authentication a = SecurityContextHolder.getContext().getAuthentication();
			String userId = null;
			
			try{
				userId = ((UserDetails) a.getPrincipal()).getUsername();
			}catch(Exception e){
				//model.addAttribute("errMsg", "Your session is expired");
				//return new ModelAndView("login", model.asMap());
				////System.out.println(e);
			}
			
			Account account = null;
			User user =  null;	
	
			if(userId != null && !"".equals(userId)){
		    	account = accountDao.getByEmail(userId);
				request.setAttribute("userId", userId);				
				user = getUserByAccount(account, user);
			}
			
			if(account != null){	    		    	    	
		        model.addAttribute("accInfo", account);
		        model.addAttribute("usInfo", user);	
		        
		        int userCount = account.getUser().size();
		        List<User> userS = new ArrayList<User>();
		        int countmembers = 0;
		        if(userCount>1){
			        for(User u: account.getUser()){
			        	if(user.getPartyId() != u.getPartyId() && u.isActive()){
			        		countmembers = countmembers + 1;
			        		userS.add(u);
			        	}
			        }
				}
		        model.addAttribute("userCount", countmembers);
		        model.addAttribute("userS", userS);
		        model.addAttribute("AlluserS", account.getUser());
		        model.addAttribute("userCount", countmembers);
		        
		        DateFormat outsdf = new SimpleDateFormat("MM/dd/yyyy"); 
		        
		        
		        model.addAttribute("allItemType", PropFileUtil.getValue(Constants.ALL_ITEM_TYPES));
		        model.addAttribute("itemType", itemType);
		        model.addAttribute("contactName", contactName);
		        if(programStDt==null || "".equals(programStDt)){
		        	programStDt = outsdf.format(new Date());
		        }
		        if(programEndDt==null || "".equals(programEndDt)){
		        	Calendar cal = Calendar.getInstance();  
		        	Integer endDateYearCount = Integer.parseInt(PropFileUtil.getValue("view_program_search_end_date_year_count"));
		    		cal.add(Calendar.YEAR, endDateYearCount); // to get previous year add -1
		    		// end date is 3 years from todays date
		        	programEndDt = outsdf.format(cal.getTime());
				}
		        model.addAttribute("programStDt", programStDt);
		        model.addAttribute("programEndDt", programEndDt);
		        
		        JSONArray currentConfirmedProgramsArr = setCurrentConfirmedProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("currentConfirmedProgramsArr", currentConfirmedProgramsArr);
				
				JSONArray currentWaitlistedProgramsArr = setCurrentWaitlistedProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("currentWaitlistedProgramsArr", currentWaitlistedProgramsArr);
				
				JSONArray upcomingConfirmedProgramsArr = setUpcomingConfirmedProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("upcomingConfirmedProgramsArr", upcomingConfirmedProgramsArr);
				 
				JSONArray upcomingWaitlistedProgramsArr = setUpcomingWaitlistedProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("upcomingWaitlistedProgramsArr", upcomingWaitlistedProgramsArr);
				
				JSONArray cancelledProgramsArr = setCancelledProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("cancelledProgramsArr", cancelledProgramsArr);
				
				JSONArray pastConfirmedProgramsArr = setPastConfirmedProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("pastConfirmedProgramsArr", pastConfirmedProgramsArr);
				
				// future cancellation program
				JSONArray futureCancelledProgramsArr = setFutureCancelledProgramsObj(itemType, contactName, programStDt, programEndDt, account);
				model.addAttribute("futureCancelledProgramsArr", futureCancelledProgramsArr);
			}
		}catch(Exception e){
			log.error("Error  ",e);
		}
		
	    return new ModelAndView("viewAllPrograms", model.asMap());
	}

	private JSONArray setFutureCancelledProgramsObj(String itemType,
			String contactName, String programStDt, String programEndDt,
			Account account) {
 
		JSONArray futureCancelledProgramsArr = new JSONArray();
		List<Object[]> lstFutureCancelledProgramsObj = getSignupPrograms(account, Constants.FUTURE_CANCELLED, Constants.ACTIVE, SignupStatusEnum.Inactive.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object futureCancelledProgramsObj: lstFutureCancelledProgramsObj){
			
			Object futureCancelledPrograms[] = (Object[]) futureCancelledProgramsObj;
			
			Signup signup = (Signup) futureCancelledPrograms[0];
			ItemDetail program = (ItemDetail) futureCancelledPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			futureCancelledProgramsArr.add(jsonObj);
		}
		return futureCancelledProgramsArr;
	}

	private JSONArray setPastConfirmedProgramsObj(String itemType,
			String contactName, String programStDt, String programEndDt,
			Account account) {

		JSONArray pastConfirmedProgramsArr = new JSONArray();
		List<Object[]> lstPastConfirmedProgramsObj = getSignupPrograms(account, Constants.PAST, Constants.ACTIVE, SignupStatusEnum.Active.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object pastConfirmedProgramsObj: lstPastConfirmedProgramsObj){
			
			Object pastConfirmedPrograms[] = (Object[]) pastConfirmedProgramsObj;
			
			Signup signup = (Signup) pastConfirmedPrograms[0];
			ItemDetail program = (ItemDetail) pastConfirmedPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			pastConfirmedProgramsArr.add(jsonObj);
		}
		
		return pastConfirmedProgramsArr;
	}

	private JSONArray setCancelledProgramsObj(String itemType, String contactName,
			String programStDt, String programEndDt, Account account) {
		
		JSONArray cancelledProgramsArr = new JSONArray();
		List<Object[]> lstCancelledProgramsObj = getSignupPrograms(account, Constants.CANCELLED, "", SignupStatusEnum.Inactive.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object cancelledProgramsObj: lstCancelledProgramsObj){
			
			Object cancelledPrograms[] = (Object[]) cancelledProgramsObj;
			
			Signup signup = (Signup) cancelledPrograms[0];
			ItemDetail program = (ItemDetail) cancelledPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			cancelledProgramsArr.add(jsonObj);
		}
		return cancelledProgramsArr;
	}

	private JSONArray setUpcomingWaitlistedProgramsObj(String itemType,
			String contactName, String programStDt, String programEndDt,
			Account account) {
		 
		JSONArray upcomingWaitlistedProgramsArr = new JSONArray();
		List<Object[]> lstupcomingWaitlistedProgramsObj = getSignupPrograms(account, Constants.UPCOMING, Constants.ACTIVE, SignupStatusEnum.Waitlisted.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object upcomingWaitlistedProgramsObj: lstupcomingWaitlistedProgramsObj){
			
			Object upcomingWaitlistedPrograms[] = (Object[]) upcomingWaitlistedProgramsObj;
			
			Signup signup = (Signup) upcomingWaitlistedPrograms[0];
			ItemDetail program = (ItemDetail) upcomingWaitlistedPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			upcomingWaitlistedProgramsArr.add(jsonObj);
		}
		return upcomingWaitlistedProgramsArr;
	}

	private JSONArray setUpcomingConfirmedProgramsObj(String itemType,
			String contactName, String programStDt, String programEndDt,
			Account account) {
		
		JSONArray upcomingConfirmedProgramsArr = new JSONArray();
		List<Object[]> lstupcomingConfirmedProgramsObj = getSignupPrograms(account, Constants.UPCOMING, Constants.ACTIVE, SignupStatusEnum.Active.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object upcomingConfirmedProgramsObj: lstupcomingConfirmedProgramsObj){
			
			Object upcomingConfirmedPrograms[] = (Object[]) upcomingConfirmedProgramsObj;
			
			Signup signup = (Signup) upcomingConfirmedPrograms[0];
			ItemDetail program = (ItemDetail) upcomingConfirmedPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			upcomingConfirmedProgramsArr.add(jsonObj);
		}
		return upcomingConfirmedProgramsArr;
	}
	
	private JSONArray setCurrentWaitlistedProgramsObj(String itemType, String contactName, String programStDt, String programEndDt, Account account) {
		 
		JSONArray currentWaitlistedProgramsArr = new JSONArray();
		List<Object[]> lstCurrentWaitlistedProgramsObj = getSignupPrograms(account, Constants.CURRENT, Constants.ACTIVE, SignupStatusEnum.Waitlisted.toString(), itemType, contactName, programStDt, programEndDt);
		for(Object currentWaitlistedProgramsObj: lstCurrentWaitlistedProgramsObj){
			
			Object currentWaitlistedPrograms[] = (Object[]) currentWaitlistedProgramsObj;
			
			Signup signup = (Signup) currentWaitlistedPrograms[0];
			ItemDetail program = (ItemDetail) currentWaitlistedPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			currentWaitlistedProgramsArr.add(jsonObj);
		}
		return currentWaitlistedProgramsArr;
	}
	
	private JSONArray setCurrentConfirmedProgramsObj(String itemType, String contactName, String programStDt,
			String programEndDt, Account account) {
		
		JSONArray currentConfirmedProgramsArr = new JSONArray();
		List<Object[]> lstCurrentConfirmedProgramsObj = getSignupPrograms(account, Constants.CURRENT, Constants.ACTIVE, SignupStatusEnum.Active.toString(), itemType, contactName, programStDt, programEndDt);
		
		for(Object currentConfirmedProgramsObj: lstCurrentConfirmedProgramsObj){
			
			Object currentConfirmedPrograms[] = (Object[]) currentConfirmedProgramsObj;
			
			Signup signup = (Signup) currentConfirmedPrograms[0];
			ItemDetail program = (ItemDetail) currentConfirmedPrograms[1];
			
			JSONObject jsonObj = populateJSONobj(signup, program, Constants.ACTIVE);
			
			currentConfirmedProgramsArr.add(jsonObj);
		}
		return currentConfirmedProgramsArr;
	}
	
	private List<Object[]> getSignupPrograms(Account acct, String strProgramTime, String programstatus, String signupstatus, String itemType, String contactName, String programStDt, String programEndDt) {
		Date today = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
		DateFormat inputsdf = new SimpleDateFormat("MM/dd/yyyy");  
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);

		Root<Signup> s = query.from(Signup.class);
		Join<Signup, ItemDetail> i = s.join("itemDetail",JoinType.INNER);
		
		ParameterExpression<Account> account = builder.parameter(Account.class);
		//ParameterExpression<String> pTime = builder.parameter(String.class);
		ParameterExpression<String> pStatus = builder.parameter(String.class);
		ParameterExpression<String> sStatus = builder.parameter(String.class);
		ParameterExpression<String> iType = builder.parameter(String.class);
		ParameterExpression<String> cName = builder.parameter(String.class);
		ParameterExpression<Date> sd = builder.parameter(Date.class);
		ParameterExpression<Date> ed = builder.parameter(Date.class);
		ParameterExpression<Date> currentDate = builder.parameter(Date.class);
		ParameterExpression<String> isAllowedToPickStartDate = builder.parameter(String.class);
		
		List<Predicate> predicates = getFilterCriteria(strProgramTime,
				programstatus, itemType, contactName, programStDt,
				programEndDt, builder, s, i, account, pStatus, sStatus, iType,
				cName, sd, ed, currentDate, isAllowedToPickStartDate);
		
		query.multiselect(s,i);
		query.where(predicates.toArray(new Predicate[]{}));

		TypedQuery<Object[]> typequery = createQuery(acct, programstatus,
				signupstatus, itemType, contactName, programStDt, programEndDt,
				today, sdf, inputsdf, query, account, pStatus, sStatus, iType,
				cName, sd, ed, currentDate);
		
		List<Object[]> lstObj = typequery.getResultList();
		return lstObj;
	}

	private TypedQuery<Object[]> createQuery(Account acct,
			String programstatus, String signupstatus, String itemType,
			String contactName, String programStDt, String programEndDt,
			Date today, DateFormat sdf, DateFormat inputsdf,
			CriteriaQuery<Object[]> query,
			ParameterExpression<Account> account,
			ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<String> iType,
			ParameterExpression<String> cName, ParameterExpression<Date> sd,
			ParameterExpression<Date> ed, ParameterExpression<Date> currentDate) {
		
		TypedQuery<Object[]> typequery = em.createQuery(query);
		typequery.setParameter(account, acct);
		if(itemType!=null && !"".equals(itemType.trim())){
			typequery.setParameter(iType, itemType);
		}
		if(contactName!=null && !"".equals(contactName.trim())){
			typequery.setParameter(cName, contactName);
		}
		if(programStDt!=null && !"".equals(programStDt) && programEndDt!=null && !"".equals(programEndDt)){
			
			Date startDate;
	        Date endDate;

			try {
				startDate = sdf.parse(sdf.format(inputsdf.parse(programStDt)));
		        endDate = sdf.parse(sdf.format(inputsdf.parse(programEndDt)));
		        
		        typequery.setParameter(sd, startDate);
				typequery.setParameter(ed, endDate);
			} catch (ParseException e) {
				//log.error("Error  ",e);
				//return null;
			} catch (Exception e1) {
				//log.error("Error  ",e1);
				//return null;
			}
		}
		
		// sign up status
		if(Constants.FUTURE_CANCELLED.equals(signupstatus.trim())){
			typequery.setParameter(sStatus, Constants.CANCELLED);
		} else {
			typequery.setParameter(sStatus, signupstatus);
		}
		
		// program status
		if(!"".equals(programstatus.trim())){
			typequery.setParameter(pStatus, programstatus);
		}
		
		// current date
		try {
			typequery.setParameter(currentDate, sdf.parse(sdf.format(today)));
		} catch (ParseException e) {
			//log.error("Error  ",e);
			//return null;
		} catch (Exception e1) {
			//log.error("Error  ",e1);
			//return null;
		}
		return typequery;
	}

	private List<Predicate> getFilterCriteria(String strProgramTime,
			String programstatus, String itemType, String contactName,
			String programStDt, String programEndDt, CriteriaBuilder builder,
			Root<Signup> s, Join<Signup, ItemDetail> i,
			ParameterExpression<Account> account,
			ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<String> iType,
			ParameterExpression<String> cName, ParameterExpression<Date> sd,
			ParameterExpression<Date> ed,
			ParameterExpression<Date> currentDate,
			ParameterExpression<String> isAllowedToPickStartDate) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		predicates.add(
				builder.and(
						builder.equal(s.get("customer"), account)
				)
		);
		if(itemType!=null && !"".equals(itemType.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("type"), iType)));
		}
		if(contactName!=null && !"".equals(contactName.trim())){
			predicates.add(builder.and(builder.equal(s.<String>get("contactName"), cName)));
		}
		if(programStDt!=null && !"".equals(programStDt) && programEndDt!=null && !"".equals(programEndDt)){
			predicates.add(builder.and(builder.greaterThanOrEqualTo(i.<Date>get("endDate"), sd)));
			predicates.add(builder.and(builder.lessThanOrEqualTo(i.<Date>get("endDate"), ed)));
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.CURRENT)){
			// filter for current confirmed and waitlisted
			filterCurrent(programstatus, builder, s, i, pStatus, sStatus, currentDate, isAllowedToPickStartDate, predicates);
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.UPCOMING)){
			// filter for upcoming confirmed and waitlisted
			filterUpcoming(programstatus, builder, s, i, pStatus, sStatus, currentDate, isAllowedToPickStartDate, predicates);
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.CANCELLED)){
			// filter for cancelled
			filterCancelled(programstatus, builder, s, i, pStatus, sStatus, currentDate, isAllowedToPickStartDate, predicates);
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.PAST)){
			// filter for past confirmed
			filterPast(programstatus, builder, s, i, pStatus, sStatus, currentDate, isAllowedToPickStartDate, predicates);
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.FUTURE_CANCELLED)){
			// filter for future cancelled
			filterFutureCancelled(programstatus, builder, s, i, pStatus, sStatus, currentDate, isAllowedToPickStartDate, predicates);
		}
		/*
		if(Constants.FUTURE_CANCELLED.equals(signupstatus.trim())){
			predicates.add(builder.and(builder.notEqual(s.get("status"), sStatus)));
		} else {
			predicates.add(builder.and(builder.equal(s.get("status"), sStatus)));
		}
		
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
		
		if(itemType!=null && !"".equals(itemType.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("type"), iType)));
		}
		
		if(contactName!=null && !"".equals(contactName.trim())){
			predicates.add(builder.and(builder.equal(s.<String>get("contactName"), cName)));
		}
		
		if(programStDt!=null && !"".equals(programStDt) && programEndDt!=null && !"".equals(programEndDt)){
			predicates.add(builder.and(builder.greaterThanOrEqualTo(i.<Date>get("startDate"), sd)));
			predicates.add(builder.and(builder.lessThanOrEqualTo(i.<Date>get("startDate"), ed)));
		}
		
		if(strProgramTime.equalsIgnoreCase(Constants.CURRENT)){
			predicates.add(builder.and(builder.lessThanOrEqualTo(i.<Date>get("startDate"), currentDate)));
			predicates.add(builder.and(builder.greaterThanOrEqualTo(i.<Date>get("endDate"), currentDate)));
		}
		else if(strProgramTime.equalsIgnoreCase(Constants.UPCOMING)){
			predicates.add(builder.and(builder.greaterThan(i.<Date>get("startDate"), currentDate)));
			predicates.add(builder.and(builder.greaterThan(i.<Date>get("endDate"), currentDate)));
		}
		else if(strProgramTime.equalsIgnoreCase(Constants.PAST)){
			predicates.add(builder.and(builder.lessThan(i.<Date>get("startDate"), currentDate)));
			predicates.add(builder.and(builder.lessThan(i.<Date>get("endDate"), currentDate)));
		}
		else if(strProgramTime.equalsIgnoreCase(Constants.SCHEDULED)){
			predicates.add(builder.and(builder.greaterThanOrEqualTo(i.<Date>get("endDate"), currentDate)));
		}
		
		// check for cancel date
		if(Constants.FUTURE_CANCELLED.equals(signupstatus.trim())){
			predicates.add(builder.and(builder.isNotNull(s.<String>get("cancelDate"))));
			predicates.add(builder.and(builder.greaterThanOrEqualTo(s.<Date>get("cancelDate"), currentDate)));
		} else if(Constants.CANCELLED.equals(signupstatus.trim())){
			
		} else {
			predicates.add(builder.and(builder.isNull(s.<String>get("cancelDate"))));
		}
		*/
		return predicates;
	}

	private void filterCurrent(String programstatus,
			CriteriaBuilder builder, Root<Signup> s,
			Join<Signup, ItemDetail> i, ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<Date> currentDate, ParameterExpression<String> isAllowedToPickStartDate, List<Predicate> predicates) {
		
		// signup status
		predicates.add(builder.and(builder.equal(s.get("status"), sStatus)));
		// program status
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
		
		/*
		predicates.add(
				//builder.or(
				builder.selectCase().when(i.<Date>get("allowToPickStartDate").equals("Yes"),
						// if allowToPickStartDate is YES
						builder.and(
								builder.equal(i.<Date>get("allowToPickStartDate"), isAllowedToPickStartDate),
								builder.lessThan(i.<Date>get("startDate"), currentDate), 
								builder.greaterThan(i.<Date>get("endDate"), currentDate), 
								builder.lessThanOrEqualTo(s.<Date>get("signupDate"), currentDate)
						)
						).otherwise
						
						// if allowToPickStartDate is NO
						builder.and(
								builder.lessThan(i.<Date>get("startDate"), currentDate),
								builder.greaterThan(i.<Date>get("endDate"), currentDate)
						)
				)
		);*/
		
		
		predicates.add(
			    builder.or(
			      // if allowToPickStartDate is YES
			      builder.and(
			        builder.equal(i.<Date>get("allowToPickStartDate"), "Yes"),
			        builder.lessThanOrEqualTo(i.<Date>get("startDate"), currentDate), 
			        builder.greaterThanOrEqualTo(i.<Date>get("endDate"), currentDate), 
			        builder.lessThanOrEqualTo(s.<Date>get("signupDate"), currentDate)
			      ),
			      
			      // if allowToPickStartDate is NO
			      builder.and(
			    	builder.equal(i.<Date>get("allowToPickStartDate"), "No"),
			    	builder.lessThanOrEqualTo(i.<Date>get("startDate"), currentDate),
			        builder.greaterThanOrEqualTo(i.<Date>get("endDate"), currentDate)
			      )
			    )
		);
		
		predicates.add(builder.and(builder.isNull(s.<String>get("cancelDate"))));
	}
	
	private void filterUpcoming(String programstatus,
			CriteriaBuilder builder, Root<Signup> s,
			Join<Signup, ItemDetail> i, ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<Date> currentDate, ParameterExpression<String> isAllowedToPickStartDate, List<Predicate> predicates) {
		
		// signup status
		predicates.add(builder.and(builder.equal(s.get("status"), sStatus)));
		// program status
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
		predicates.add(
			    builder.or(
			      // if allowToPickStartDate is YES
			      builder.and(
			        builder.equal(i.<Date>get("allowToPickStartDate"), "Yes"),
			        builder.greaterThan(s.<Date>get("signupDate"), currentDate)
			      ),
			      
			      // if allowToPickStartDate is NO
			      builder.and(
			    	builder.equal(i.<Date>get("allowToPickStartDate"), "No"),
			    	builder.greaterThan(i.<Date>get("startDate"), currentDate)
			      )
			    )
		);
		
		predicates.add(builder.and(builder.isNull(s.<String>get("cancelDate"))));
	}
	
	private void filterCancelled(String programstatus,
			CriteriaBuilder builder, Root<Signup> s,
			Join<Signup, ItemDetail> i, ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<Date> currentDate, ParameterExpression<String> isAllowedToPickStartDate, List<Predicate> predicates) {
		
		// signup status
		predicates.add(builder.and(builder.equal(s.get("status"), sStatus)));
		// program status
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
	}
	
	private void filterPast(String programstatus,
			CriteriaBuilder builder, Root<Signup> s,
			Join<Signup, ItemDetail> i, ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<Date> currentDate, ParameterExpression<String> isAllowedToPickStartDate, List<Predicate> predicates) {
		
		// signup status
		predicates.add(builder.and(builder.equal(s.get("status"), sStatus)));
		// program status
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
		predicates.add(builder.and(builder.lessThan(i.<Date>get("endDate"), currentDate)));
		predicates.add(builder.and(builder.isNull(s.<String>get("cancelDate"))));
	}
	
	private void filterFutureCancelled(String programstatus,
			CriteriaBuilder builder, Root<Signup> s,
			Join<Signup, ItemDetail> i, ParameterExpression<String> pStatus,
			ParameterExpression<String> sStatus,
			ParameterExpression<Date> currentDate, ParameterExpression<String> isAllowedToPickStartDate, List<Predicate> predicates) {
		
		// signup status
		predicates.add(builder.and(builder.notEqual(s.get("status"), sStatus)));
		// program status
		if(!"".equals(programstatus.trim())){
			predicates.add(builder.and(builder.equal(i.<String>get("status"), pStatus)));
		}
		
		predicates.add(builder.and(builder.isNotNull(s.<String>get("cancelDate"))));
		predicates.add(builder.and(builder.greaterThanOrEqualTo(s.<Date>get("cancelDate"), currentDate)));
	}
	
	private JSONObject populateJSONobj(Signup signup, ItemDetail program, String status) {
		
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");  
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("programId", program.getId());
		jsonObj.put("signupId", signup.getSignupId());
		jsonObj.put("itemType", program.getType());
		jsonObj.put("itemTypeTxt", StringUtil.toCamelCase(program.getCategory()));
		jsonObj.put("contactName", signup.getContactName());
		jsonObj.put("programName", program.getRecordName());
		jsonObj.put("programDescription", program.getDescription());
		jsonObj.put("programStartDate", program.getStartDate());
		jsonObj.put("programEndDate", program.getEndDate());
		jsonObj.put("programStartTime", program.getStartTime());
		jsonObj.put("programEndTime", program.getEndTime());
		jsonObj.put("programStatus", program.getStatus());
		jsonObj.put("programEnrollmentStatus", signup.getStatus());
		jsonObj.put("signupDate", signup.getSignupDate());
		jsonObj.put("actualRemainingCapacity", program.getActualRemainingCapacity());
		
		List<SignUpDays> lstSignupDays = signUpDaysDao.findBySignupAndStatus(signup, status);
		String selDays = "";
		String selDates = "";
		for(SignUpDays idys : lstSignupDays){
			  if(idys!= null && idys.getDay()!=null){
		    	  	String day = idys.getDay();
		    	  	if(day!=null && !"".equals(day)){
			    		selDays = selDays + "," + day;
		    	  	}
		    	  	
		    	  	if(idys!= null && idys.getSignupDate()!= null && !"".equals(idys.getSignupDate()))
		    	  		selDates = selDates + "," + sdf.format(idys.getSignupDate());
			  }
		}
		selDays = selDays.replaceFirst(",", "");
		selDates = selDates.replaceFirst(",", "");
		
		if(Constants.PRODUCT_CATEGORY_CHILD_CARE.equalsIgnoreCase(signup.getType()) && Constants.PRODUCT_CATEGORY_INSERVICE.equalsIgnoreCase(program.getCategory())){
			jsonObj.put("signupDays", selDates);
		}else{
			jsonObj.put("signupDays", selDays);
		}
		return jsonObj;
	}
	
	@RequestMapping(value="/viewProgramDetails", method=RequestMethod.GET)
    public ModelAndView viewProgramDetails(@RequestParam String sId, @RequestParam(required = false) String showSuccessMsg) { 
		Model model = new ExtendedModelMap();
		try{
			JSONArray enrolledProgramDetailsArr = new JSONArray(); 
			JSONArray paymentDetailsArr = new JSONArray(); 
			JSONArray interactionsBySignupArr = new JSONArray(); 
			JSONArray activityPriority = new JSONArray();
			JSONArray payerArr = new JSONArray();
			boolean isSaveSignup = false;
			List<PaymentMethod> paymentMethodList = null;
			
			String signupId = request.getParameter("sId");
			
			/*String[] signupIdA = signupId.split(",");
			
			if(signupIdA.length == 2){
				signupId = signupIdA[0];
			}*/
			
			if(!"".equals(signupId)){
				
				// get signup details and item details associated with sign up
/*				Object programObj[] = (Object[]) signupDao.getSignupProgramById(Long.parseLong(signupId));
		    	Signup signup = (Signup) programObj[0];
		    	ItemDetail program = (ItemDetail) programObj[1];
*/		    	
		    	Signup signup = signupDao.getOne(Long.parseLong(signupId));
		    	ItemDetail program = signup.getItemDetail();
		    	model.addAttribute("program", program);
		    	
		    	JSONObject jsonObj = new JSONObject();
		    	jsonObj.put("programId", program.getId());
		    	jsonObj.put("programType", program.getType());
		    	jsonObj.put("signupId", signup.getSignupId());
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
				jsonObj.put("discountAmount", signup.getDiscountAmount());
				jsonObj.put("finalAmount", signup.getFinalAmount());
				jsonObj.put("signupPrice", signup.getSignupPrice());
				jsonObj.put("registrationFee", signup.getRegistrationFee());
				jsonObj.put("setUpFee", signup.getSetUpFee());
				jsonObj.put("FAamount", signup.getFAamount());
				jsonObj.put("deposit", signup.getDeposit());
				jsonObj.put("joinFee", signup.getJoinFee());
				jsonObj.put("contactName", signup.getContactName());
				jsonObj.put("actualRemainingCapacity", program.getActualRemainingCapacity());
				boolean isDraftCycle = false;
				if(signup.getDraftCycle() != null){
					String[] dratfCycleA = signup.getDraftCycle().split(",");
					if(dratfCycleA.length == 2){
						isDraftCycle = true;
						isSaveSignup = true;
						jsonObj.put("draftCycle", signup.getDraftCycle());
						jsonObj.put("draftCycleNumber", dratfCycleA[1]);
					}
				}
				jsonObj.put("isDraftCycle", isDraftCycle);
				enrolledProgramDetailsArr.add(jsonObj);			
				
				// get payment details associated with sign up
				List<Payment> lstPaymentDetails = paymentDao.findBySignupAndType(signup,Constants.PAYMENT_TYPE_Payment);
				if(lstPaymentDetails.size()>0){
					
					//String ccNum = "";
					for(Payment payment: lstPaymentDetails){
						JSONObject payObj = new JSONObject();
						payObj.put("transactionId", payment.getPaymentNumber());
						if (payment.getPayer() != null && payment.getPayer().getPaymentMethod() != null) {
							payObj.put("ccnumber", payment.getPayer().getPaymentMethod().getCardNumber());
						}
						payObj.put("amount", payment.getAmount());
						payObj.put("paymentDate", payment.getPaymentDate());
						
						paymentDetailsArr.add(payObj);
					}
					
				}
				
				// get interactions associated with sign up
				List<Activity> activities = interactionDao.findBySignupAndType(signup, Constants.ATTENDANCE);
				for(Activity interaction: activities){
					JSONObject intObj = new JSONObject();
			    	intObj.put("checkinDateTime", interaction.getCheckinDatetime());
			    	intObj.put("checkoutDateTime", interaction.getCheckoutDatetime());
					
					interactionsBySignupArr.add(intObj);
				}

				// load activities and transport signup for Residence Camp
				if (StringUtils.equalsIgnoreCase(program.getType(), Constants.CAMP_TYPE)) {
					JSONObject obj = new JSONObject();
					
					for(int i=0; i<10; i++){
						String activity = null;
						int priority = 0;
						switch (i) {
						case 1:
							if(signup.getActivity1() != null){
								activity = signup.getActivity1();
								priority = signup.getPriority1();
							}
							break;
						case 2:
							if(signup.getActivity2() != null){
								activity = signup.getActivity2();
								priority = signup.getPriority2();
							}
							break;
						case 3:
							if(signup.getActivity3() != null){
								activity = signup.getActivity3();
								priority = signup.getPriority3();
							}
							break;
						case 4:
							if(signup.getActivity4() != null){
								activity = signup.getActivity4();
								priority = signup.getPriority4();
							}
							break;
						case 5:
							if(signup.getActivity5() != null){
								activity = signup.getActivity5();
								priority = signup.getPriority5();
							}
							break;
						case 6:
							if(signup.getActivity6() != null){
								activity = signup.getActivity6();
								priority = signup.getPriority6();
							}
							break;
						case 7:
							if(signup.getActivity7() != null){
								activity = signup.getActivity7();
								priority = signup.getPriority7();
							}
							break;
						case 8:
							if(signup.getActivity8() != null){
								activity = signup.getActivity8();
								priority = signup.getPriority8();
							}
							break;
						case 9:
							if(signup.getActivity9() != null){
								activity = signup.getActivity9();
								priority = signup.getPriority9();
							}
							break;
						case 10:
							if(signup.getActivity10() != null){
								activity = signup.getActivity10();
								priority = signup.getPriority10();
							}
							break;
						default:
							break;
						}
						if(activity != null){
							//ItemDetail activityItem = itemDetailDao.findOne(Long.parseLong(activity));
							obj.put("activity", activity);
							obj.put("priority", priority);
							activityPriority.add(obj);
						}
					}
					model.addAttribute("activitiesSignUps", activityPriority);
					//model.addAttribute("activitiesSignUps", signUpAssociatedItemDetailDao.findBySignupIdOrderByActivityPriorityAsc(signup.getSignupId()));

					List<Signup> parentSignups = signupDao.findByParentSignUpIdAndStatus(signup.getSignupId(), SignupStatusEnum.Active.toString());
					List<Signup> relatedSignUps = new ArrayList<Signup>();
					List<Signup> transportSignUps = new ArrayList<Signup>();
					
					for(Signup s : parentSignups){
						if(s.getItemDetail() != null && s.getItemDetail().getCategory().equalsIgnoreCase(Constants.CAMP_CATERORY_TRANSPORTATION)){
							transportSignUps.add(s);
						}else{
							relatedSignUps.add(s);
						}
					}
					
					model.addAttribute("relatedSignUps", relatedSignUps);
					model.addAttribute("transportSignUps", transportSignUps);
				}
				
				/*if (StringUtils.equalsIgnoreCase(program.getCategory(), Constants.CAMP_TYPE_FAMILY)) {
					model.addAttribute("relatedSignUps", signupDao.findByParentSignUpIdAndStatus(signup.getSignupId(), SignupStatusEnum.Active.toString()));
				}*/
			
				
				List<Payer> lstPayer = payerDao.findBySignup(signup);
				/*Payer p =  new Payer();
				p.setId(1l);
				
				List<Invoice> invoices = new ArrayList<Invoice>();
				Invoice i = new Invoice();
				i.setInvoiceId(101l);
				invoices.add(i);
				
				p.setInvoices(invoices);
				lstPayer.add(p);*/
				
				if(lstPayer.size()>0){
					
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				    
					for(Payer payer: lstPayer){
						JSONObject payObj = new JSONObject();
						payObj.put("payerId", payer.getId());
						payObj.put("payerAmount", payer.getAmount());
						payObj.put("payerType", payer.getType());
						
						PaymentMethod pm = payer.getPaymentMethod();
						if(pm != null){
							payObj.put("payerPMId", pm.getId());
						}else if(payer.getPaymentMode() != null){
							payObj.put("payerPM", payer.getPaymentMode());
						}
						
						List<Invoice> invoices = invoiceDao.findByPayer(payer);
						
						JSONArray invoiceArr = new JSONArray();
						
						if(invoices != null && !invoices.isEmpty()){
							
							for (Invoice invoice : invoices) {
								JSONObject invoiceObj = new JSONObject();
								
								invoiceObj.put("invoiceId", invoice.getInvoiceId());
								invoiceObj.put("invoiceDate", sdf.format(invoice.getInvoiceDate()));
								if(invoice.getDueDate() != null){
									invoiceObj.put("invoiceDueDate", sdf.format(invoice.getDueDate()));
								}
								if(invoice.getBillDate() != null){
									invoiceObj.put("invoiceBillDate", sdf.format(invoice.getBillDate()));	
								}
								invoiceObj.put("invoiceAmount", invoice.getAmount());
								
								PaymentMethod ipm = invoice.getPaymentMethod();
								
								if(ipm != null){
									invoiceObj.put("invoicePMId", ipm.getId());	
								}else if(invoice.getPaymentMode() != null){
									invoiceObj.put("invoicePM", invoice.getPaymentMode());
								}
								
								invoiceArr.add(invoiceObj);
							}
							
							
						}
						
						payObj.put("invoices", invoiceArr);
						
						if(StringUtils.isBlank(getAgentUidFromSession()) && payer.getType() != null && payer.getType().equalsIgnoreCase(Constants.SELF)){
							payerArr.add(payObj);
						}
						if(!StringUtils.isBlank(getAgentUidFromSession())){
							payerArr.add(payObj);
						}
					}
					
				}
			
				if(signup.getCustomer() != null){
					paymentMethodList = paymentMethodDao.getPaymentMethodListForAccountID(signup.getCustomer().getAccountId());
				}
				
			}
			
			/*if(payerArr != null){
				for(int i = 0; i<payerArr.size(); i++){
					
					JSONObject jo = (JSONObject)payerArr.get(i);
					
					jo.get
					
				}
			}*/
			
			if(paymentMethodList != null && !paymentMethodList.isEmpty()){
				isSaveSignup = true;
			}
			
			model.addAttribute("paymentInfoLst" , paymentMethodList);
			model.addAttribute("payerList", payerArr);
			model.addAttribute("enrolledProgramDetailsArr", enrolledProgramDetailsArr);
			model.addAttribute("paymentDetailsArr", paymentDetailsArr);
			model.addAttribute("interactionsBySignupArr", interactionsBySignupArr);
			model.addAttribute("isSaveSignup", isSaveSignup);
			if(showSuccessMsg != null){
				model.addAttribute("isShowSuccessMsg", true);
			}else{
				model.addAttribute("isShowSuccessMsg", false);
			}
			
		}catch(Exception e){
			log.error("Error  ",e);
		}
		
		return new ModelAndView("viewProgramDetails", model.asMap());
	}
	
	
	@RequestMapping(value="/viewScheduledProgram", method=RequestMethod.GET)
    public ModelAndView viewScheduledProgram(final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		
		String itemType = request.getParameter("itemType");
		String contactName = request.getParameter("contactName");
		String programStDt = request.getParameter("programStDt");
		String programEndDt = request.getParameter("programEndDt");
			
		// check if user is logged in
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			//model.addAttribute("errMsg", "Your session is expired");
			//return new ModelAndView("login", model.asMap());
			////System.out.println(e);
		}
		
		Account account = null;
		User user =  null;	

		if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			request.setAttribute("userId", userId);				
			user = getUserByAccount(account, user);
		}
		
		if(account != null){	    		    	    	
	        model.addAttribute("accInfo", account);
	        model.addAttribute("usInfo", user);	
	        
	        int userCount = account.getUser().size();
	        List<User> userS = new ArrayList<User>();
	        int countmembers = 0;
	        if(userCount>1){
		        for(User u: account.getUser()){
		        	if(user.getPartyId() != u.getPartyId() && u.isActive()){
		        		countmembers = countmembers + 1;
		        		userS.add(u);
		        	}
		        }
			}
	        model.addAttribute("userCount", countmembers);
	        model.addAttribute("userS", userS);
	        model.addAttribute("AlluserS", account.getUser());
     
	        model.addAttribute("userCount", countmembers);

	        JSONArray schdeuledProgramsArr = new JSONArray();
	        
	        List<Object[]> lstSchdeuledProgramsObj = getSignupPrograms(account, Constants.SCHEDULED, Constants.ACTIVE, SignupStatusEnum.Active.toString(), itemType, contactName, programStDt, programEndDt);
	        
	        for(Object schdeuledProgramsObj: lstSchdeuledProgramsObj){
	        	
	        	Object schdeuledPrograms[] = (Object[]) schdeuledProgramsObj;
	        	
	        	Signup signup = (Signup) schdeuledPrograms[0];
	        	ItemDetail program = (ItemDetail) schdeuledPrograms[1];
	        	
	        	JSONObject jsonObj = new JSONObject();
	        	jsonObj.put("programId", program.getId());
	        	jsonObj.put("signupId", signup.getSignupId());
	        	jsonObj.put("itemType", program.getCategory());
	        	jsonObj.put("contactName", signup.getContactName());
				jsonObj.put("programName", program.getRecordName());
				jsonObj.put("programStartDate", program.getStartDate());
				jsonObj.put("programEndDate", program.getEndDate());
				jsonObj.put("programStartTime", program.getStartTime());
				jsonObj.put("programEndTime", program.getEndTime());
				jsonObj.put("programStatus", program.getStatus());
				jsonObj.put("programEnrollmentStatus", signup.getStatus());
				
				schdeuledProgramsArr.add(jsonObj);
	        }
			model.addAttribute("schdeuledProgramsArr", schdeuledProgramsArr);
			
		}
		
	    return new ModelAndView("viewScheduledProgram", model.asMap());
	}
	
	@RequestMapping(value="/updateSignup", method=RequestMethod.POST)
	public @ResponseBody boolean updateSignup(@RequestParam("dataMap") String data){
		System.out.println(" data "+data);
		String draftCycleNumber = null;
		JSONObject dataMap = JSONObject.fromObject(data);
		Long signupId = Long.parseLong(dataMap.get("signupId").toString());
		if(dataMap.get("draftCycleNumber") != null){
			draftCycleNumber = dataMap.get("draftCycleNumber").toString();
		}
		if(dataMap.get("payerInvoiceList") != null){
			String payerInvoiceList = dataMap.get("payerInvoiceList").toString();
			HashMap<String, String> payerPMMap = new LinkedHashMap<String, String>();
			HashMap<String, String> invoicePMMap = new LinkedHashMap<String, String>();
			if(payerInvoiceList != null && !payerInvoiceList.isEmpty()){
				String payerInvoiceListA[] = payerInvoiceList.split("p__");
				if(payerInvoiceListA != null && payerInvoiceListA.length > 0){
					for (int i = 0; i < payerInvoiceListA.length; i++) {
						String payerInvoiceStr = payerInvoiceListA[i];
						String payerInvoiceStrA[] = payerInvoiceStr.split("i__");
						if(payerInvoiceStrA != null && payerInvoiceStrA.length > 1){
							for (int j = 0; j < payerInvoiceStrA.length; j++) {
								System.out.println("  payerInvoiceStrA[j] "+payerInvoiceStrA[j]);
								if(j == 0){
									String payerStr =  payerInvoiceStrA[j];
									String payerStrA[] = payerStr.split("&&");
									if(payerStrA != null && payerStrA.length == 2){
										payerPMMap.put(payerStrA[0], payerStrA[1]);	
									}
								}else{
									String invoiceStr =  payerInvoiceStrA[j];
									String invoiceStrA[] = invoiceStr.split("&&");
									if(invoiceStrA != null && invoiceStrA.length == 2){
										invoicePMMap.put(invoiceStrA[0], invoiceStrA[1]);	
									}
								}
							}
						}
					}
				}
			}
			
			System.out.println("  payerPM map "+payerPMMap.size());
			
			if(payerPMMap != null && !payerPMMap.isEmpty()){
				for(Map.Entry<String, String> e : payerPMMap.entrySet()){
					Long payerId = Long.valueOf(e.getKey());
					Long payerPMId = 0l;
					String paymentMode = null;
					try{
						payerPMId = Long.valueOf(e.getValue());
					}catch(Exception e1){
						paymentMode = e.getValue();
					}
					
					if(payerId != null){
						System.out.println("  payerId "+payerId);
						Payer payer = payerDao.getOne(payerId);
						if(payerPMId > 0){
							PaymentMethod paymentMethod = paymentMethodDao.getOne(payerPMId);
							//System.out.println("  payerPMId  "+payerPMId +"  =  PMId  "+payer.getPaymentMethod().getId());
							if(payer != null && (payer.getPaymentMethod() == null || !payerPMId.equals(payer.getPaymentMethod().getId()))){
								payer.setPaymentMethod(paymentMethod);
								payer.setPaymentMode(paymentMethod.getPaymentMethodType());
								System.out.println("  save payer "+payer.getPaymentMethod().getId());
							}
						}else if(paymentMode != null){
							System.out.println("  paymentMode  "+paymentMode);
							if(payer != null){
								payer.setPaymentMode(paymentMode);
								payer.setPaymentMethod(null);
							}
						}
						if(payer != null)
							payerDao.save(payer);
					}
				}
			}
			
			if(invoicePMMap != null && !invoicePMMap.isEmpty()){
				for(Map.Entry<String, String> e : invoicePMMap.entrySet()){
					Long invoiceId = Long.valueOf(e.getKey());
					Long invoicePMId = 0l;
					String paymentMode = null;
					try{
						invoicePMId = Long.valueOf(e.getValue());
					}catch(Exception e1){
						paymentMode = e.getValue();
					}
					
					if(invoiceId != null){
						System.out.println("  invoiceId "+invoiceId);
						Invoice invoice = invoiceDao.findOne(invoiceId);
						if(invoicePMId > 0){
							PaymentMethod paymentMethod = paymentMethodDao.findOne(invoicePMId);
							//System.out.println("  save invoice : invoicePMId "+invoicePMId+" =  "+invoice.getPaymentMethod().getId());
							if(invoice != null && (invoice.getPaymentMethod() == null || !invoicePMId.equals(invoice.getPaymentMethod().getId()))){
								invoice.setPaymentMethod(paymentMethod);
								invoice.setPaymentMode(paymentMethod.getPaymentMethodType());
							}
						}else if(paymentMode != null){
							System.out.println("  paymentMode  "+paymentMode);
							if(invoice != null){
								invoice.setPaymentMode(paymentMode);
								invoice.setPaymentMethod(null);
							}
						}
						if(invoice != null)
							invoiceDao.save(invoice);
					}
				}
			}
			
			System.out.println("  invoicePM map "+invoicePMMap.size());
		}

		Signup signup = signupDao.getOne(signupId);
		
		if(draftCycleNumber != null){
			String[] draftCycleA = signup.getDraftCycle().split(",");
			StringBuffer draftCycle = new StringBuffer(draftCycleA[0]);
			draftCycle.append(","+draftCycleNumber);
			signup.setDraftCycle(draftCycle.toString());
			Signup s = signUpService.updateSignup(signup);
		
			invoiceService.checkAndUpdateInvoiceDraftDate(s);
		}
		
		return true;
	}
	
}