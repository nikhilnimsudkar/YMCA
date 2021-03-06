package com.ymca.web.controller;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.serene.bootstrap.WebServiceBootStrap;
import com.serene.ws.cache.GenericCache;
import com.serene.ws.service.FusionWebService;
import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.OpportunityDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.SystemPropertyDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.Activity;
import com.ymca.model.Address;
import com.ymca.model.ItemDetail;
import com.ymca.model.Notes;
import com.ymca.model.Opportunity;
import com.ymca.model.Signup;
import com.ymca.model.SystemProperty;
import com.ymca.model.User;
import com.ymca.web.enums.SSOTokenWebTagsEnum;
import com.ymca.web.enums.SignupStatusEnum;
import com.ymca.web.service.AccountContactService;
import com.ymca.web.service.CheckInCheckoutService;
import com.ymca.web.service.ContactService;
import com.ymca.web.service.HallPassService;
import com.ymca.web.util.Constants;
import com.ymca.web.util.MemberAge;

@Controller
@Scope("session")
@ContextConfiguration(classes = FusionWebService.class, loader = SpringApplicationContextLoader.class)
public class YAgentConsoleController extends BaseController  implements Serializable {
	
	@Resource
	private AccountDao accountDao;

	@Resource
	private UserDao userDao;

	@Resource
	private ContactService contactService;

	//@Resource(name="portalAuthenticationProvider")
	@Resource
	private UserDetailsService userDetailsService;
	
	@Resource
	private SignupDao signupDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@Resource
	private AccountContactService accountContactService;
	
	@Resource
	private AccountContactDao accountContactDao;
	
	@Resource
	private OpportunityDao opportunityDao; 
	
	@Resource
	private LocationDao locationDao;

	@Resource
	private CheckInCheckoutService checkInCheckoutService;
	
	@Resource
	private FusionWebService fusionWebService;
	
	@Resource
	private SystemPropertyDao  systemPropertyDao;

	
	private String agentUid ;
	
	private String sso ;
	
	@Resource
	private HallPassService hallPassService;
	
	@Resource
	private GenericCache genericCache ;
	
	public String getAgentUid() {
		return agentUid;
	}

	public void setAgentUid(String agentUid) {
		this.agentUid = agentUid;
	}

	public String getSso() {
		return sso;
	}

	public void setSso(String sso) {
		this.sso = sso;
	}

	@Autowired
	 private ServletContext context; 
	
	// @Resource
	// private UserCache userCache ;

	@RequestMapping(value = "/admin/YAgentConsole", method = RequestMethod.GET)
	public String yAgentConsole(String agentUid,String sso,String retUrl) {
		
		setAgentDetailsInSession(agentUid, sso);
		String url = "YAgentConsole";
		if (StringUtils.isNotBlank(retUrl)) {
			url = retUrl ;
		}
		return url;
	}

	@RequestMapping(value = "/admin/simulate", method = RequestMethod.GET)
	public String simulatePortalUrl(String agentUid,String sso,Long custPartyId,String retUrl,Model model) throws Exception {
		log.info(" agent id " + agentUid + " , sso " + sso);
		Account account = accountDao.findFirst1ByPartyId(custPartyId); 
		if (account == null) throw new Exception("User does not presnet in the portal");
		logoutCurrentUser(agentUid, sso, account.getAccountId());
		loginNewUser(agentUid, sso, account.getAccountId());
		setAgentDetailsInSession(agentUid, sso);
		return "redirect:"+retUrl;
	}

	@RequestMapping(value = "/admin/getUsersBySearchCriteria", method = RequestMethod.GET)
	public @ResponseBody String getUsersBySearchCriteria(String fName,String lName,String eMail,String phno, String cname, HttpServletRequest request, final HttpServletResponse response) {
		JSONArray json = new JSONArray();
		try {
			log.info(" agent id " + agentUid + " , sso " + sso);
			
			
			if(!"".equals(phno)){
				
				List<User> matchResult=new ArrayList<User>();
				List<User> userList=userDao.findAll();
				int count= userList.size();
				System.out.println(count);
				for (User user : userList) 
				{
					if(user.getFormattedPhoneNumber()!=null)
					{
						
						String number=user.getFormattedPhoneNumber();
			    	String countryCode="";
			         String out = number.replaceAll("[^0-9\\+]", "")        //remove all the non numbers (brackets dashes spaces etc.) except the + signs
			                        .replaceAll("(^[1-9].+)", countryCode+"$1")         //if the number is starting with no zero and +, its a local number. prepend cc
			                        .replaceAll("(.)(\\++)(.)", "$1$3")         //if there are left out +'s in the middle by mistake, remove them
			                        .replaceAll("(^0{2}|^\\+)(.+)", "$2")       //make 00XXX... numbers and +XXXXX.. numbers into XXXX...
			                        .replaceAll("^0([1-9])", countryCode+"$1");         //make 0XXXXXXX numbers into CCXXXXXXXX numbers
			         
			         
			         		//System.out.println(out); 
			         		if(StringUtils.isNotBlank(phno) && StringUtils.isNotBlank(out) && phno.equals(out)){
			         			matchResult.add(user);
			         		}
					}
				}
				for(User u:matchResult){
					System.out.println(u.getFormattedPhoneNumber()+"=="+u.getFirstName());
					JSONObject obj = new JSONObject();
					obj.put("customerId", u.getAccountId());
					obj.put("customerName", u.getCustomer().getName());
					obj.put("FirstName", u.getFirstName());
					obj.put("LastName", u.getLastName());
					obj.put("EmailAddress", u.getEmailAddress());
					obj.put("FormattedPhoneNumber", u.getFormattedPhoneNumber());
					
					if (u.getDateOfBirth() != null)
					{
						//obj.put("DateOfBirth", user.getContact().getDateOfBirth().toString());
						obj.put("DateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(u.getDateOfBirth()).toString());}
					json.add(obj);
				}
		    }
			else{
				List<Object[]> customerList = contactService.getAccountDetails(fName, lName, eMail,phno, cname);
			
			for (Object object : customerList) {
				AccountContact user = (AccountContact) object;
				JSONObject obj = new JSONObject();
				obj.put("customerId", user.getCustomer().getAccountId());
				obj.put("customerName", user.getCustomer().getName());
				obj.put("FirstName", user.getContact().getFirstName());
				obj.put("LastName", user.getContact().getLastName());
				obj.put("EmailAddress", user.getContact().getEmailAddress());
				obj.put("FormattedPhoneNumber", user.getContact().getFormattedPhoneNumber());
				
				if (user.getContact().getDateOfBirth() != null)
				{
					//obj.put("DateOfBirth", user.getContact().getDateOfBirth().toString());
					obj.put("DateOfBirth", new SimpleDateFormat("MM/dd/yyyy").format(user.getContact().getDateOfBirth()).toString());}
				json.add(obj);
			}
			}
		} catch (Exception se) {
			log.error("Error while fetching the data ",se);
			
		}
		return json.toString();
	}
	

	@RequestMapping(value = "/admin/YAgentConsoleContactSearch", method = RequestMethod.GET)
	public String getAccount(@RequestParam(value = "customerId") Long customerId) {
		log.info(" agent id " + agentUid + " , sso " + sso);
		// logout current user
		String redirectUrl = logoutCurrentUser(agentUid, sso, customerId);
		return redirectUrl;
	}
	
	@RequestMapping(value = "/admin/autologin", method = RequestMethod.GET)
	public String autologin(String agentUid,String sso,Long customerId) throws Exception {
		log.info(" agent id " + agentUid + " , sso " + sso);
		// auto login new user
		String url = loginNewUser(agentUid, sso, customerId);
		return url;
	}

	
	@RequestMapping(value = "/admin/impersonate/user", method = RequestMethod.GET)
	public String impersonateUser() {
		String url = "impersoanteUser";
		return url;
	}
	
	private String logoutCurrentUser(String agentUid, String sso,Long customerId) {
		String redirectUrl = "redirect:/admin/autologin?agentUid="+agentUid+"&sso="+sso +"&customerId=" +customerId ;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		return redirectUrl;
	}
	
	private String loginNewUser(String agentUid, String sso, Long customerId) throws Exception {
		User user = contactService.getPrimaryUserByCustomerId(customerId);
		if (user == null) throw new Exception("User does not presnet in the portal");
		String emailAddress = null;
		boolean isLoggedinUserAdmin = false;
		//if(StringUtils.isBlank(sso)){
		//	return "redirect:/login";
		//}else{
			try{
				Map<String,Object> responseToken = fusionWebService.findSelfUserDetails(sso);
				emailAddress = getEmailAddressFromResponse(responseToken);
				String userName = getUserNameFromResponse(responseToken);
				
				log.debug(">invoke");
				Map<String,Object> userRoleMap = fusionWebService.query("select * from Resource where Username = '"+userName+"'");   
				String userRoles = getUserRoleFromResponse(userRoleMap);				
				if(org.apache.commons.lang3.StringUtils.isNotBlank(userRoles) && userRoles.contains(Constants.SPECIAL_ADMIN_ROLE)){
					isLoggedinUserAdmin = true;
				}
				System.out.println(isLoggedinUserAdmin);
				log.debug("<invoke : " +isLoggedinUserAdmin);
				log.debug("<invoke");
				
				emailAddress = user.getEmailAddress();
				if(StringUtils.isBlank(emailAddress)){
					return "redirect:/login";
				}else{
					setAgentDetailsInSession(emailAddress, sso);
					UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmailAddress());					
					SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword()));

					//setAgentDetailsInSession(emailAddress, sso);
			        
			        request.getSession().setAttribute("usInfo", user);	
			        request.getSession().setAttribute("accInfo", user.getCustomer());
			        
					String url = "redirect:/home?agentUid="+agentUid;
					url = "forward:/admin/impersonate/user";
					return url;
				}
			}catch(Exception e){
				log.error("Error occured in webservice while fetching UserDetails",e);						
				return "redirect:/login";
			}			
		//}	
	}
	
	private String getEmailAddressFromResponse(Map<String, Object> response) {
		String emailAddress = null;
		Map<String,Object> value = new HashMap<String, Object>() ;  
		Object resp =response.get(SSOTokenWebTagsEnum.RESULT.getValue());
		Object objValue = null ;
		if (resp instanceof AbstractMap) {
			objValue = ((Map<String, Object>) resp).get(SSOTokenWebTagsEnum.VALUE.getValue());
		} else {
			objValue = ((List)resp).get(0) ;
		}
		if (objValue instanceof AbstractMap) {
			value = (Map<String, Object>) objValue ;
		} else if (objValue != null) {
			value = ((List<Map<String, Object>>) objValue).get(0) ;
		}	
		if (value == null) value = (Map<String, Object>) resp ;		
		Map<String,Object> respData = new HashMap<String, Object>();
		if (value.get(SSOTokenWebTagsEnum.VALUE.getValue()) instanceof AbstractList) {
			respData = ((List<Map<String, Object>>) value.get(SSOTokenWebTagsEnum.VALUE.getValue())).get(0) ;
		} else {
			respData = value ;
		}
		
		Map<String,Object> userPersonDetailsValue = new HashMap<String, Object>() ;  
		Object userPersonDetailsResp =respData.get(SSOTokenWebTagsEnum.USERPERSONDETAILS.getValue());
		if (userPersonDetailsResp instanceof AbstractMap) {
			userPersonDetailsValue = (Map<String, Object>) userPersonDetailsResp ;
		} else if (userPersonDetailsResp != null) {
			userPersonDetailsValue = ((List<Map<String, Object>>) userPersonDetailsResp).get(0) ;
		}		
		if (userPersonDetailsValue == null) userPersonDetailsValue = (Map<String, Object>) respData ;	
		if(userPersonDetailsValue.get(SSOTokenWebTagsEnum.EMAILADDRESS.getValue()) != null) {
			emailAddress = userPersonDetailsValue.get(SSOTokenWebTagsEnum.EMAILADDRESS.getValue()).toString();
		}
		return emailAddress;
	}
	
	private String getUserNameFromResponse(Map<String, Object> response) {
		String userName = null;
		Map<String,Object> value = new HashMap<String, Object>() ;  
		Object resp =response.get(SSOTokenWebTagsEnum.RESULT.getValue());
		Object objValue = null ;
		if (resp instanceof AbstractMap) {
			objValue = ((Map<String, Object>) resp).get(SSOTokenWebTagsEnum.VALUE.getValue());
		} else {
			objValue = ((List)resp).get(0) ;
		}
		if (objValue instanceof AbstractMap) {
			value = (Map<String, Object>) objValue ;
		} else if (objValue != null) {
			value = ((List<Map<String, Object>>) objValue).get(0) ;
		}	
		if (value == null) value = (Map<String, Object>) resp ;		
		Map<String,Object> respData = new HashMap<String, Object>();
		if (value.get(SSOTokenWebTagsEnum.VALUE.getValue()) instanceof AbstractList) {
			respData = ((List<Map<String, Object>>) value.get(SSOTokenWebTagsEnum.VALUE.getValue())).get(0) ;
		} else {
			respData = value ;
		}
		
		Map<String,Object> userPersonDetailsValue = new HashMap<String, Object>() ;  
		String userNameStr = null;
		Object userNameObj =respData.get(SSOTokenWebTagsEnum.USERNAME.getValue());
		if (userNameObj instanceof String) {
			userNameStr = (String) userNameObj ;
		} 
		return userNameStr;
	}
	
	private String getUserRoleFromResponse(Map<String, Object> response) {
		String userName = null;
		Map<String,Object> value = new HashMap<String, Object>() ;  
		Object resp =response.get(SSOTokenWebTagsEnum.RESULT.getValue());
		Object objValue = null ;
		if (resp instanceof AbstractMap) {
			objValue = ((Map<String, Object>) resp).get(SSOTokenWebTagsEnum.VALUE.getValue());
		} else {
			objValue = ((List)resp).get(0) ;
		}
		if (objValue instanceof AbstractMap) {
			value = (Map<String, Object>) objValue ;
		} else if (objValue != null) {
			value = ((List<Map<String, Object>>) objValue).get(0) ;
		}	
		if (value == null) value = (Map<String, Object>) resp ;		
		Map<String,Object> respData = new HashMap<String, Object>();
		if (value.get(SSOTokenWebTagsEnum.VALUE.getValue()) instanceof AbstractList) {
			respData = ((List<Map<String, Object>>) value.get(SSOTokenWebTagsEnum.VALUE.getValue())).get(0) ;
		} else {
			respData = value ;
		}
		
		Map<String,Object> userPersonDetailsValue = new HashMap<String, Object>() ;  
		String userRoleStr = null;
		Object userRolesObj =respData.get(SSOTokenWebTagsEnum.ROLES.getValue());
		if (userRolesObj instanceof String) {
			userRoleStr = (String) userRolesObj ;
		} 
		//System.out.println(userRoleStr);
		return userRoleStr;
	}

	private void setAgentDetailsInSession(String agentUid, String sso) {
		this.agentUid = agentUid;
		this.sso = sso;
		request.getSession().setAttribute("agentUid", agentUid);
		request.setAttribute("agentUid", agentUid);			
	}
	
	//-------------------------------
	@RequestMapping(value = "/admin/yAgentSearchUser", method = RequestMethod.GET)
	public ModelAndView yAgentSearchUser(String agentUid,String sso) {
		this.agentUid = agentUid;
		this.sso = sso;
		//request.getSession().setAttribute("agentUid", agentUid);
		
		 Model model = new ExtendedModelMap();
	        try {            
	            model.addAttribute("locations", locationDao.findAll());
	            model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea("Bay Area"));
	            
	        } catch (Exception se) {
	            System.out.println("Error occoured while querying Product");
	            log.error("Error  ",se);
	        }
		//return "yAgentSearchUser";
		 return new ModelAndView("yAgentSearchUser", model.asMap());    
	}
	
	
	@RequestMapping(value = "/admin/YAgentConsoleMembershipSearch", method = RequestMethod.GET)
	public @ResponseBody ModelAndView YAgentConsoleMembershipSearch(@RequestParam(value = "customerId") Long customerId ,Long mId, @RequestParam(value = "location") Long location) {
		log.info(" agent id " + agentUid + " , sso " + sso);
		
		  Account account = null;
		  Activity activity=null;
		  List<String> types = new ArrayList<String>();
		  types.add("MEMBERSHIP");
		  User user = contactService.getPrimaryUserByCustomerId(customerId);
		  account = accountDao.findByAccountId(customerId);
		  //--------------------Check Guest Count-------------------------------------------
		  List<Activity> guestCheckin=new ArrayList<Activity>();
		  guestCheckin=activityDao.findByTypeAndCustomerAndContact("Guest Pass", account, user);
	  		int GuestCountCheck=guestCheckin.size();
	  		if(GuestCountCheck==0){
	  			GuestCountCheck=0;
	  		}
		  
		  //------------------------chk running count of a customer------------------------------
		  List<Activity> guestCountIntr=new ArrayList<Activity>();
  		guestCountIntr=activityDao.findByTypeAndCustomerAndContact("CHECKIN", account, user);
  		int runningCount=guestCountIntr.size();
  		if(runningCount==0){
  			 activity=null;
  		 
  		}else if(runningCount>1){
  			activity=guestCountIntr.get(runningCount-1);
  			 
  		}else{
  			activity=guestCountIntr.get(0);
  		}
		  Model model = new ExtendedModelMap();
		  
		  //------------------------------Chk avg checkin in month------------------------------------
		  
		  Collections.sort(guestCountIntr, new Comparator<Activity>() {
			    public int compare(Activity m1, Activity m2) {
			        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
			    }
			});
		  /*for(Activity a:guestCountIntr){
			  System.out.println(a.getCreatedDate());
			  System.out.println(a.getIntegrationId()+"=="+a.getLastUpdated());
		  }*/
		  
		  
				  	//Get month start date
		  
		  	Calendar currentDate= Calendar.getInstance();
			currentDate.set(Calendar.DAY_OF_MONTH, 1);
			Date MonthStartDate=currentDate.getTime();
			
			//get month last date
			Calendar currentDate1 = Calendar.getInstance();
		    int MonthlastDateCount = currentDate1.getActualMaximum(Calendar.DATE);
		    currentDate1.set(Calendar.DATE, MonthlastDateCount);
			Date MothLastDate=currentDate1.getTime();
			
			List<Activity> monthlyCheckin= activityDao.findByCustomerAndContactAndCreatedDateBetween(account, user, MonthStartDate, MothLastDate);
			int totalMonthlyCheckin=monthlyCheckin.size();
			//System.out.println("total monthly checkin"+totalMonthlyCheckin);
			int avgmonthlycheckin=(totalMonthlyCheckin*30)/30;
			 //------------------------------Chk avg checkin in a week------------------------------------
			Date date = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
			c.add(Calendar.DAY_OF_MONTH, -dayOfWeek);

			Date weekStart = c.getTime();
			// we do not need the same day a week after, that's why use 6, not 7
			c.add(Calendar.DAY_OF_MONTH, 6); 
			Date weekEnd = c.getTime();
			List<Activity> weeklyCheckin=activityDao.findByCustomerAndContactAndCreatedDateBetween(account, user, weekStart, weekEnd);
			int totalweeklyCheckin=weeklyCheckin.size();
			int avgweeklyCheckin=(totalweeklyCheckin*7)/7;
		 // try{
		int countmembers = 0;			
		if(account != null && user !=null )	{ 
		    int userCount = account.getUser().size();
		    List<User> userS = new ArrayList<User>();
		    if (userCount > 1)	{
		     for (User u : account.getUser()) {
		      if (user.getContactId() != u.getContactId())     {
		    	  Pageable pageable = new PageRequest(0, 1);
		    	  List<Signup> signuplst=signupDao.findByContact(u.getContactId(),pageable);
		    	  if(!signuplst.isEmpty()){
		    		  countmembers = countmembers + 1;
				    	userS.add(u);
		    	  }
		    	
		      }
		     }
		    }
		    
		    //------------------------Calculate primary user age-----------------------------------------------------
		    
			if(user.getDateOfBirth()!=null){
				MemberAge mAge = new MemberAge();
				Integer age = 0;// u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
				age = mAge.getAge(user.getDateOfBirth());//pass the DOB of user to the method and it returns int Age only in Years.
				user.setAge(age);
				
				checkAndUpdateIfSexOffender(user,model);
				
				
			}
			//----------------------------Calculate other family members age-------------------------------------------
			if(!userS.isEmpty()){
				for(User u:userS){
					 MemberAge mAge = new MemberAge();
						Integer age = 0;
						if(u.getDateOfBirth()!=null){ // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
							age = mAge.getAge(u.getDateOfBirth());//pass the DOB of user to the method and it returns int Age only in Years.
							u.setAge(age);
							
							checkAndUpdateIfSexOffender(user,model);
						}
				}
			}
			
			/*List<Signup> signuplst=signupDao.findByTypeInAndContactId(types ,user.getContactId());
			
			Collections.sort(signuplst, new Comparator<Signup>() {
			    public int compare(Signup m1, Signup m2) {
			        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
			    }
			});
		  for(Signup a:signuplst){
			 System.out.println(a.getLastUpdated());
		  }*/
			Pageable pageable = new PageRequest(0, 1);
			List<Signup> signups =  signupDao.findByContact(user.getContactId(),pageable);
			//List<Signup> signuplst=signupDao.findByTypeInAndContactId(types ,user.getContactId());
			
			Collections.sort(signups, new Comparator<Signup>() {
			    public int compare(Signup m1, Signup m2) {
			        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
			    }
			});
/*		  for(Signup a:signups){
			 System.out.println(a.getLastUpdated());
		  }*/
		  
		  

			if(!signups.isEmpty() ){
		    	Signup signup=signups.get(0);
		    	//if(StringUtils.equalsIgnoreCase(signup.getStatus(), "ACTIVE")){
		    	if(signup!=null){
	    	    	model.addAttribute("accessGranted", "ACCESS GRANTED...!!");
	    	    	model.addAttribute("accessDenied", "ACCESS DENIED...!!");
	    	    	model.addAttribute("usInfo", user);
	    	    	model.addAttribute("userCount", countmembers);
	    		    model.addAttribute("userS", userS);
	    		    model.addAttribute("signup", signup);
	    		    model.addAttribute("runnignCount",runningCount);
	    		    model.addAttribute("activity",activity);
	    		    model.addAttribute("avgWeeklyCheckin",avgweeklyCheckin);
	    		    model.addAttribute("avgMonthlyCheckin",avgmonthlycheckin);
	    		    model.addAttribute("guestCountCheckIn",GuestCountCheck);
		    	    	
		    	}else {
		    		model.addAttribute("accessGranted", "ACCESS GRANTED...!!");
	    	    	model.addAttribute("accessDenied", "ACCESS DENIED...!!");
	    	    	model.addAttribute("usInfo", user);
	    	    	model.addAttribute("userCount", countmembers);
	    		    model.addAttribute("userS", userS);
	    		    model.addAttribute("signup", signup);
	    		    model.addAttribute("runnignCount",runningCount);
	    		    model.addAttribute("activity",activity);
	    		    model.addAttribute("inactiveMembership","You have INACTIVE membership.");
	    		    model.addAttribute("avgWeeklyCheckin",avgweeklyCheckin);
	    		    model.addAttribute("avgMonthlyCheckin",avgmonthlycheckin);
	    		    model.addAttribute("guestCountCheckIn",GuestCountCheck);
		    	
		    	}
		    }else{
		    	model.addAttribute("accessGranted", "ACCESS GRANTED...!!");
    	    	model.addAttribute("accessDenied", "ACCESS DENIED...!!");
    	    	model.addAttribute("usInfo", user);
    	    	model.addAttribute("userCount", countmembers);
    		    model.addAttribute("userS", userS);
    		    model.addAttribute("runnignCount",runningCount);
    		    model.addAttribute("activity",activity);
    		    model.addAttribute("guestCountCheckIn",GuestCountCheck);
		    }
	}
	return new ModelAndView("testLayout", model.asMap());
	}

	private Model checkAndUpdateIfSexOffender(User user, Model model) {
		
		Boolean allowcheckin = false;
		Boolean sendEmail = true;
		String strSexoffender =  user.getSexOffender();
		String isSexOffender = strSexoffender;
		
		if(strSexoffender==null || strSexoffender.equalsIgnoreCase("No")){
			if(hallPassService.isSexOffender(user)){
				String agentId = getAgentUidFromSession();
				List<Notes> notesObj = hallPassService.getOffenderIds(user,agentId);
				
				//String isSexOffender = "No";
				Boolean isSexOffenderMatchFound = false;
				if(user.getNotes()!=null && user.getNotes().size()>0){
					for(Notes n: user.getNotes()){
						//if(n.getOffenderMatch()==null || n.getOffenderMatch()){
						if(n!=null && n.getOffenderMatch() !=null && n.getOffenderMatch()){ 
							// set sexoffender flag on contact = Yes, if any notes as offender match = Yes
							isSexOffender = "Yes";
							allowcheckin = false;
							sendEmail = true;
							isSexOffenderMatchFound = true;
							break;
						} 
					}
					
					if(!isSexOffenderMatchFound){
						allowcheckin = true;
						if("NO".equalsIgnoreCase(strSexoffender) && notesObj.size()==0)
							sendEmail = false;
					}
				} 
				
				user.setSexOffender(isSexOffender);
				//user.setOffenderIds(StringUtils.join(offenderIds,","));
				userDao.save(user);
				
			} else {
				allowcheckin = true;
				sendEmail = false;
			}
		}
		
		model.addAttribute("allowcheckin", allowcheckin);
		model.addAttribute("sendSexOffenderEmail", sendEmail);
		model.addAttribute("isSexOffender", isSexOffender);
		
		return model;
	}
	
	
	@RequestMapping(value = "/admin/checkIn_PrimaryMember", method = RequestMethod.GET)
	public @ResponseBody String YAgentConsoleCheckIn_PrimaryMember(Long memberContactId,Long location) {
		log.info(" memberContactId " + memberContactId +  ", location " + location);
		System.out.println(" memberContactId " + memberContactId +  ", location " + location);
		return checkInCheckoutService.isValidCheckin(memberContactId, location) ;
	}		
	

	 @RequestMapping(value= "/admin/checkIn_member", method = RequestMethod.GET)
	 public @ResponseBody String checkInOtherMember(@RequestParam(value = "memberContactId") Long memberContactId, @RequestParam(value = "location") Long location,final HttpServletRequest request, final HttpServletResponse response) {
	 	JSONArray json = new JSONArray();
		JSONObject obj = new JSONObject();	
		 User user=null;
		 if(memberContactId != null && !"".equals(memberContactId)){
			 	user=userDao.getByContactId(memberContactId);
			 	Account account=user.getCustomer();
			 	try{
			 	 Pageable pageable = new PageRequest(0,1);
			 	 
			 	 
				 List<Signup> signups =  signupDao.findByContact(user.getContactId(), pageable );
				 
				  
				 Signup signup = null ;
				 if (!signups.isEmpty()) signup = signups.get(0);
				 
				 if(!StringUtils.equalsIgnoreCase(user.getSexOffender(),"Yes")) {
					checkInCheckoutService.logCheckinActivity(signup, location,"Family Memeber checkin",null);
	    	    	if (signup.getItemDetail() != null) obj.put("signupMemShipName", signup.getItemDetail().getRecordName());
	    	    	String message = "Access Granted..!!";
	    	    	long locID=signup.getLocation().getId();
	    			if (signup.getLocation() != null && (locID == location || (signup.getLocation().getRecordName().equals(Constants.LOCATION_ALL_BRANCH) || signup.getLocation().getRecordName().equals(Constants.LOCATION_BAYAREA) ))) {
	    				message = "Access Granted";
	    			} else {
	    				message = "Access Granted -- Alert! Member location is different from facility location ";
	    			}
	    	    	obj.put("accessGranted",message);
	    	    	obj.put("lastCheckedInDate", new Date());
	    	   		Long runningCount=activityDao.countByTypeAndCustomerAndContact("CHECKIN", account, user);
	    	    	obj.put("ChkedIncount", runningCount);
	    	    	obj.put("Success", "Success");
	    	    	obj.put("secUserLoc", signup.getLocation().getRecordName());
	    	    	json.add(obj);
	    	    	return json.toString();
				}else{
					if (signup.getItemDetail() != null) obj.put("signupMemShipName", signup.getItemDetail().getRecordName());
					obj.put("lastCheckedInDate", new Date());
					obj.put("secUserLoc", signup.getLocation().getRecordName());
			    	//obj.put("signupMemShipName", "Contact is sex offender..!!");
	    	    	obj.put("accessDenied","Access Denied..!!, must see executive director");
	    	   		Long runningCount=activityDao.countByTypeAndCustomerAndContact("CHECKIN", account, user);
	    	    	obj.put("ChkedIncount", runningCount);
	    	    	obj.put("SexOffender", "SexOffender");
	    	    	//Activity act=guestCountIntr.get(runningCount-1);
	    	    	//obj.put("secLastChkedInDate", act.getLastUpdated());
	    	    	json.add(obj);
	    	    	return json.toString();
			    }
		 	}catch(Exception es){
		 		log.error(" Error while checking the additonal members ",es);
		 		obj.put("signupMemShipName", "Member has No Active Membership..!!");
    	    	obj.put("accessDenied","Access Denied..!!");
    	   		Long runningCount=activityDao.countByTypeAndCustomerAndContact("CHECKIN", account, user);
    	    	obj.put("ChkedIncount", runningCount);
    	    	//Activity act=guestCountIntr.get(runningCount-1);
    	    	//obj.put("secLastChkedInDate", act.getLastUpdated());
    	    	json.add(obj);
    	    	return json.toString();
		 	}
		 }else{
			 return null;
		 }
	 }
	
	 @RequestMapping(value = "/admin/allowUnAuthorisedAccess", method = RequestMethod.GET)
		public @ResponseBody String allowUnAuthorisedAccess(@RequestParam(value = "memberContactId") Long memberContactId,@RequestParam(value = "location") Long location, @RequestParam(value="sexOffReason") String sexOffReason,	final HttpServletRequest request, final HttpServletResponse response) {
			log.info(" agent id " + agentUid + " , sso " + sso);
			System.out.println("reason"+sexOffReason);
			Account account = null;
			User user=null;
			if(memberContactId!=null){
				user=userDao.getByContactId(memberContactId);
				account =user.getCustomer();
				if(account != null && user !=null && (user.getUnauthorizedAccessCount() == null || user.getUnauthorizedAccessCount() < 3))	{ 
				    int userCount = account.getUser().size();
				    List<User> userS = new ArrayList<User>();
				    int countmembers = 0;
				    if (userCount > 1) 	{
					     for (User u : account.getUser()) {
					      if (user.getContactId() != u.getContactId()) {
						      countmembers = countmembers + 1;
						      userS.add(u);
					      }
					     }
					 }
				    Pageable pageable = new PageRequest(0,1);
					 List<Signup> signups =  signupDao.findByContact(memberContactId, pageable );
					 Signup signup = null ;
					 if (!signups.isEmpty()) signup = signups.get(0);
				    int unAutAcc= 0 ;
				    if (user.getUnauthorizedAccessCount() == null) unAutAcc= 1;
				    else unAutAcc= user.getUnauthorizedAccessCount()+1;
				    user.setUnauthorizedAccessCount(unAutAcc);
				    userDao.save(user);
				    if(sexOffReason==null){
				    checkInCheckoutService.logCheckinActivity(signup, location,"UnAuthorised Member Checkin",null);
				    }else{
				    	checkInCheckoutService.logCheckinActivity(signup, location, "UnAuthorised Member Checkin", sexOffReason);
				    }
				    return "GRANTED";
				  }
			}
			return "DENY";
		}	
	 
	 //addGuest page display
	
	 @RequestMapping(value = "/admin/addGuest", method = RequestMethod.GET)
	 public ModelAndView addGuest(final HttpServletRequest request, final HttpServletResponse response) {
			
		 Model model = new ExtendedModelMap();

		 model.addAttribute("account", new Account()); 

			return new ModelAndView("addGuest", model.asMap());
		}
	
	 
	 //addGuest checkin process
	 
	 @RequestMapping(value= "/admin/addGuest", method = RequestMethod.POST)
		public @ResponseBody String addGuest1(Account account, @RequestParam(value = "primContId") Long primContId ,final HttpServletRequest request, final HttpServletResponse response) {
		 Pageable pageable = new PageRequest(0,1);	
		 User PrimaryUser=null;
		 Account accountPrimaryUser=null;
		 if(primContId != null && !"".equals(primContId)){
				// account = accountDao.getByEmail(userId);	
			 		PrimaryUser=userDao.getByContactId(primContId);
				 	long accId=PrimaryUser.getCustomer().getAccountId();
				 	System.out.println("acc id"+accId);
				 	accountPrimaryUser=accountDao.findByAccountId(accId);
				 	long userID=PrimaryUser.getContactId();
		 }
		 
		 
		 
		 	JSONArray json = new JSONArray();
			JSONObject obj = new JSONObject();		
			boolean isContactExists = false;
	    	Account accountVO = null;
	    	List<Object[]> accLst = null;
			List<User> contactLst =  new ArrayList<User>();
			Account chkAccEx=null;
			
	    	if(account != null && !StringUtils.isBlank(account.getFirstName()) && !StringUtils.isBlank(account.getLastName()) && !StringUtils.isBlank(account.getEmail()) && account.getDateOfBirth() != null){
	    		accLst =  accountDao.getByFirstNameAndLastNameAndEmailAddressAndDateOfBirth(account.getFirstName(), account.getLastName(), account.getEmail(), account.getDateOfBirth());
	    	}else if(account != null && !StringUtils.isBlank(account.getFirstName()) && !StringUtils.isBlank(account.getLastName())  && account.getDateOfBirth() != null){
	    		accLst =accountDao.getByFirstNameAndLastNameAndDateOfBirth(account.getFirstName(), account.getLastName(),account.getDateOfBirth());
	    	}
	    	// chkAccEx= accountDao.getByEmail(account.getEmail());
	    	
	    	//int guestCount=accLst.size();
	    	//if(guestCount<5)
	    	//{
	    		
	    		Account accountDB = null;
	    		User userDb = null;
	    	//if(chkAccEx==null){
	    		
	    		if(accLst != null && !accLst.isEmpty()  ){
	    		
		    		accountDB = (Account) accLst.get(0)[0];	
		    		
		    		System.out.println(accountDB.getEmail());
		    		userDb =  (User) accLst.get(0)[1];
		    		System.out.println(userDb.getFullName());
		    		
		    	//	Signup signup=signupDao.getByCustomerAndContact(accountDB.getAccountId(), userDb.getContactId());
		    		 List<Signup> signups =  signupDao.findByContact(userDb.getContactId(), pageable );
					 Signup signup = null ;
					 if (!signups.isEmpty()) signup = signups.get(0);
		    		
		    		List<Activity> guestCountIntr=new ArrayList<Activity>();
		    		guestCountIntr=activityDao.findByTypeAndCustomerAndContact("Guest Pass", accountPrimaryUser, PrimaryUser);
		    		int guestCount=guestCountIntr.size();
	    		if( guestCount<5){
			    		Activity activity=new Activity();
		    	    	activity.setCreatedDate(new Date());
		    	    	activity.setCustomer(accountPrimaryUser);
		    	    	activity.setContact(PrimaryUser);
		    	    	activity.setSignup(signup);
		    	    	activity.setDescription("Guest Member Checkin");
		    	    	Calendar cal2_CurrentDate = Calendar.getInstance();
		    	    	activity.setLastUpdated(cal2_CurrentDate);
		    	    	activity.setType("Guest Pass");
		    	    	activityDao.saveAndFlush(activity);
		    	    	System.out.println("guest chkd in");
			    		
		    	    	obj.put("Success", "Success");
		    	    	obj.put("guestCount",guestCount+1);
						json.add(obj);
						return json.toString();
			    		}
			    		else{
			    			obj.put("Duplicate", "Duplicate");
							json.add(obj);
							isContactExists = true;
							return json.toString();
			    		}
	    			}
	    		/*}else if(chkAccEx!=null){
	    			obj.put("AccExists", "AccExists");
					json.add(obj);
					isContactExists = true;
					return json.toString();
	    		}*/
	    		else{    			
	    	    	try{
	    	    		if(account!=null){
	    	    			account.setName(account.getFirstName()+account.getLastName());
	    	    			accountVO=accountDao.saveAndFlush(account);
	    	    			System.out.println("customer created");
	    	    			User user = new User();
	    	    			
	    	    			user.setCustomer(accountVO);
	    	    			
	    	    			user.setDateOfBirth(account.getDateOfBirth());
	    	    	    	user.setEnabled(true);	
	    	    	    	user.setAccountExpired(false);
	    	    	    	user.setAccountLocked(false);
	    	    	    	user.setCredentialsExpired(false);
	    	    			user.setActive(true);    			
	    	    			user.setUsername(account.getEmail());    			
	    	     	    	user.setFirstName(account.getFirstName());
	    	    			user.setLastName(account.getLastName());
	    	    			user.setfNameAndLName(user.getFullName());     	    	
	    	     	    	if(account != null && account.getGender() != null && account.getGender().equals("Male")){
	    	     	    		user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_MALE);
	    	    			}else{
	    	    				user.setProfileImage(Constants.DEFAULT_PROFILE_IMAGE_FEMALE);
	    	    			}
	    	     	    	user.setEmailAddress(account.getEmail());
	    	     	    	user.setFormattedPhoneNumber(account.getPhoneNumber());
	    	     	    	user.setGender(account.getGender());     	    	    	
	    	     	    	user.setEnabled(true);	     	    	
	    	     	    	//user.setContactPointPurpose(account.getHomeNumber());     	    	
	    	     	    	Calendar cal = Calendar.getInstance();
	    	     	    	user.setLastUpdated(cal);
	    	     	    	
	    	     	    	Address address =  new Address();
	    	    			address.setPrimaryAddressLine1(accountVO.getAddressLine1());
	    	    			address.setPrimaryAddressLine2(accountVO.getAddressLine2());
	    	    			address.setPrimaryAddressCity(accountVO.getCity());
	    	    			address.setPrimaryAddressProvince(accountVO.getState());
	    	    			address.setPrimaryAddressPostalCode(accountVO.getPostalCode());			
	    	    			address.setPrimaryAddressCountry(Constants.COUNTRY_USA);
	    	    			user.setAddress(address);
	    	     	    	
	    	     	    	user = userDao.save(user);
	    	     	    	
	    	     	    	System.out.println("contact  created");
	    	     	    	AccountContact accContact = accountContactService.saveAccountContact(accountVO, user);
	    	     	    	
	    	     	    	List<ItemDetail> itemDetail=itemDetailDao.findByType_AndCategory_("Guest Pass", "Guest Pass");
	    	     	    	
	    	     	    	if(accountVO!=null && user!=null && itemDetail!=null){
	    	     	    		Signup signup=new Signup();
	    	     	    		signup.setCustomer(accountVO);
	    	     	    		signup.setContact(user);
	    	     	    		signup.setContactName(user.getfNameAndLName());
	    	     	    		signup.setProgramStartDate(new Date());
	    	     	    		signup.setItemDetail(itemDetail.get(0));
	    	     	    		signup.setLastUpdated(Calendar.getInstance());
	    	     	    		signup.setLocation(itemDetail.get(0).getLocation());
	    	     	    		signup.setSignupDate(new Date());
	    	     	    		signup.setStatus(SignupStatusEnum.Active.toString());
	    	     	    		signup.setType("Guest Pass");
	    	     	    		signup.setRegistrationFee(0);
	    	     	    		signup.setSetUpFee(0.0);
	    	     	    		signup.setDeposit(0);
	    	     	    		
	    	     	    		signup.setPortalLastModifiedBy(agentUid);
	    	     	    		
	    	     	    		signup=signupDao.save(signup);
	    	     	    		
	    	     	    		
	    	     	    		System.out.println("signup record created");
	    	     	    	
	    	     	    		if(signup!=null){
	    	     	    			Opportunity opportunity=new Opportunity();
	    	     	   	    	
	    	     	   	    		opportunity.setContact_c(user.getContactId().toString());
	    	     	   	    		opportunity.setCustomerAccountId_c(accountVO.getAccountId().toString());
	    	     	   	    		opportunity.setName(accountVO.getName());
	    	     	   	    		opportunity.setSignup(signup);
	    	     	   	    		opportunity.setType("MEMBERSHIP");
	    	     	   	    		Calendar LastUpdated = Calendar.getInstance();
	    	     	   		    	opportunity.setLastUpdated(LastUpdated);
	    	     	   		    	opportunity=opportunityDao.save(opportunity);
	    	     	   	    	
	    	     	   	    	System.out.println("opp save");
	    	     	   	    	
	    	     	    		}
	    	     	    		if(signup!=null){
	    	     	    			Activity activity=new Activity();
					    	    	activity.setCreatedDate(new Date());
					    	    	activity.setCustomer(accountPrimaryUser);
					    	    	activity.setContact(PrimaryUser);
					    	    	activity.setSignup(signup);
					    	    	activity.setDescription("Guest member Checked-In");
					    	    	activity.setLastUpdated(Calendar.getInstance());
					    	    	activity.setType("Guest Pass");
					    	    	activity=activityDao.saveAndFlush(activity);
					    	    	System.out.println(" guest user chkd in");
	    	     	    		}
	    	     	    	
	    	     	    	}
	    	     	    	obj.put("Success", "Success");
	    	     	    	List<Activity> guestCountIntr=new ArrayList<Activity>();
	    		    		guestCountIntr=activityDao.findByTypeAndCustomerAndContact("Guest Pass", accountPrimaryUser, PrimaryUser);
	    		    		int guestCount=guestCountIntr.size();
	    	     	    	obj.put("guestCount",guestCount);
	    					json.add(obj);
	    					return json.toString();
	    	    		}
	    	    		
	    	    	}catch(Exception e){
	    	    		log.error("Error  ",e);
	    	    		return null;
	    	    	}  
	    		}
		        
	    	
			return null;
		}
	
	
	
	
	 @RequestMapping(value= "/admin/isEmailExists", method = RequestMethod.GET)
	 public @ResponseBody String isEmailExists(final HttpServletRequest request, final HttpServletResponse response) {		
		Enumeration<String> parameterNames = request.getParameterNames();
		String email = null;
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();
			if(!StringUtils.isBlank(paramName) && paramName.contains("Email")){
				String[] paramValues = request.getParameterValues(paramName);				
				for (int i = 0; i < paramValues.length; i++) {
					email = paramValues[i];			
				}
			}
		}		
		if(email==null || "".equals(email)){
			 return "true";
		 }else{
			 try{
				 User user =  userDao.getByEmailAddress(email);
				 if(user != null){
					 return "false";
				 }else{
					 return "true";
				 }
				 
			 }catch(Exception e){
				 return null;
			 }			
		 }
	 }

	 	//SEARCH KIDS AND CHECKIN PAGE
 @RequestMapping(value = "/admin/yAgentSearchKidsNcheckin", method = RequestMethod.GET)
 public ModelAndView yAgentSearchKidsNcheckin(String agentUid,String sso) {
	this.agentUid = agentUid;
	this.sso = sso;
	request.getSession().setAttribute("agentUid", agentUid);	
	Model model = new ExtendedModelMap();
	try {            
			model.addAttribute("locations", locationDao.findAll());
			model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea("Bay Area"));        
		} catch (Exception se) {
			System.out.println("Error occoured while querying Product");
			log.error("Error  ",se);
		}
				//return "yAgentSearchUser";
				 return new ModelAndView("yAgentSearchKidsNcheckin", model.asMap());    
	}
 
 
 
 
//Kids CheckIn window display. -------------------------------------------
 
	 @RequestMapping(value = "/admin/YAgentConsoleKidsCheckInWind", method = RequestMethod.GET)
		public @ResponseBody ModelAndView YAgentConsoleKidsCheckInWind(@RequestParam(value = "customerId") Long customerId ,Long mId, @RequestParam(value = "location") Long location) {
			log.info(" agent id " + agentUid + " , sso " + sso);
			
			  Account account = null;
			  Activity activity=null;
			  List<String> types = new ArrayList<String>();
			  types.add("MEMBERSHIP");
			  User user = contactService.getPrimaryUserByCustomerId(customerId);
			  account = accountDao.findByAccountId(customerId);
			  
			  Model model = new ExtendedModelMap();
			  List<SystemProperty> adultAgeLimtLst =systemPropertyDao.getPropertyByKeyName(Constants.KIDS_CLUB_ADULT_AGE_VALIDATION);
			  Integer adultAge=Integer.parseInt(adultAgeLimtLst.get(0).getKeyValue());
			  System.out.println("adult age range"+adultAge );
			  
			  List<SystemProperty> kidsAgeLimtLst =systemPropertyDao.getPropertyByKeyName(Constants.KIDS_CLUB_KID_AGE_VALIDATION );
			  Integer kidAge=Integer.parseInt(kidsAgeLimtLst.get(0).getKeyValue());
			  System.out.println("adult age range"+kidAge );
			  
			  
			 
			int countmembers = 0;			
			if(account != null && user !=null )	{ 
			    int userCount = account.getUser().size();
			    List<User> userS = new ArrayList<User>();
			    if (userCount > 1)	{
			     for (User u : account.getUser()) {
			      if (user.getContactId() != u.getContactId())     {
			    	  Pageable pageable = new PageRequest(0, 1);
			    	  List<Signup> signuplst=signupDao.findByContact(u.getContactId(),pageable);
			    	  if(!signuplst.isEmpty()){
			    		  MemberAge mAge = new MemberAge();
							Integer age = 0;
							if(u.getDateOfBirth()!=null){ // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
								age = mAge.getAge(u.getDateOfBirth());//pass the DOB of user to the method and it returns int Age only in Years.
								if(age<=kidAge){
								u.setAge(age);
								countmembers = countmembers + 1;
								userS.add(u);}
							}
			    	  }
			    	
			      }
			     }
			    }
			    
			    //------------------------Calculate primary user age-----------------------------------------------------
			    
				if(user.getDateOfBirth()!=null){
					MemberAge mAge = new MemberAge();
					Integer age = 0;// u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
					age = mAge.getAge(user.getDateOfBirth());//pass the DOB of user to the method and it returns int Age only in Years.
					user.setAge(age);
					
					checkAndUpdateIfSexOffender(user,model);
					
					
				}
				
				//--- get person responsible for chekin above 18 yr-------
				
				int kidChkInPersonCount= 0;
			    List<User> respForKidChkIn = new ArrayList<User>();
			    if (userCount > 1)	{
			     for (User u : account.getUser()) {
			   //   if (user.getContactId() != u.getContactId())     {
			    	  Pageable pageable = new PageRequest(0, 1);
			    	  List<Signup> signuplst=signupDao.findByContact(u.getContactId(),pageable);
			    	  if(!signuplst.isEmpty()){
			    		  MemberAge mAge = new MemberAge();
							Integer age = 0;
							if(u.getDateOfBirth()!=null){ // u -> user records fetched from Contact table for the logged in Member. Refer String getContactS method on CommonController
								age = mAge.getAge(u.getDateOfBirth());//pass the DOB of user to the method and it returns int Age only in Years.
								if(age>=adultAge){
								
									kidChkInPersonCount = kidChkInPersonCount + 1;
									u.setAge(age);
									respForKidChkIn.add(u);
									checkAndUpdateIfSexOffender(user,model);
									}
							}
			    	  }
			    	
			      //}
			     }
			    }
				
			
				List<Activity> noOfTimesKidCheckin= activityDao.findByTypeAndCustomer("KIDS CLUB",account);
				int noOfTimesKidCheckinCount=noOfTimesKidCheckin.size();
				
				List<Activity> kidsCheckIn= activityDao.findBydCustomer(account);
				
				for(Activity a:kidsCheckIn){
					if(userS.contains(a.getContact())){
						userS.remove(a.getContact());
					}
				}
				countmembers=userS.size();
				System.out.println("Chkin Kids count"+countmembers);
				System.out.println("latest list for chekin kids");
				for(User u:userS){
					
					System.out.println(u.getContactId());
				}
				int chkoutKidsCount=kidsCheckIn.size();
				
				Pageable pageable = new PageRequest(0, 1);
				//List<Signup> signups =  signupDao.findByContact(user.getContactId(),pageable);
				//List<Signup> signups =  signupDao.findByContactAndType(user.getContactId(),pageable);
				
				//List<Signup> signuplst=signupDao.findByTypeInAndContactId(types ,user.getContactId());
				List<Signup> activeSignupLst=signupDao.findByContact(user.getContactId(),pageable);
				List<Signup> inactiveSignupLst= signupDao.findByContactAndStatus(user.getContactId(),pageable);
				
				/*for(Signup s:signups){
					if(s.getStatus().equals("ACTIVE")){
						activeSignupLst.add(s);
					} else{
						inactiveSignupLst.add(s);
					}
				}*/
				/*for(Signup s:activeSignupLst){
					System.out.println(s.getContactName()+"= status "+s.getStatus()+"= type "+s.getType());
				}
				for(Signup s:inactiveSignupLst){
					System.out.println(s.getContactName()+"= status "+s.getStatus()+"= type "+s.getType());
				}
				Collections.sort(signups, new Comparator<Signup>() {
				    public int compare(Signup m1, Signup m2) {
				        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
				    }
				});
				Collections.sort(activeSignupLst, new Comparator<Signup>() {
				    public int compare(Signup m1, Signup m2) {
				        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
				    }
				});
				Collections.sort(inactiveSignupLst, new Comparator<Signup>() {
				    public int compare(Signup m1, Signup m2) {
				        return m1.getLastUpdated().compareTo(m2.getLastUpdated());
				    }
				});*/
				
				
				if(!activeSignupLst.isEmpty() ){
			    	Signup signup=activeSignupLst.get(0);
			    	//if(StringUtils.equalsIgnoreCase(signup.getStatus(), "ACTIVE")){
			    			//if(signup!=null){
			    				//if(signup.getStatus().equals("ACTIVE")){
			    				Long locationId=signup.getLocation().getId();
				    	    	if(!locationId.equals(location)){
				    	    		model.addAttribute("diffLocation", "Alert..!! Selected location is different than signup location.");
				    	    	}
				    	    	model.addAttribute("usInfo", user);
				    	    	model.addAttribute("userCount", countmembers);
				    		    model.addAttribute("userS", userS);
				    		    model.addAttribute("signup", signup);
				    		    model.addAttribute("chkoutKidCount", chkoutKidsCount);
				    		  //  model.addAttribute("ChkoutKid", hashSet);
				    		    model.addAttribute("ChkoutKid", kidsCheckIn);
				    		    model.addAttribute("respForKidChkIn", respForKidChkIn);
				    		    model.addAttribute("noOfTimesKidCheckinCount", noOfTimesKidCheckinCount);
		    		    
			    		
					    	//}
				}else if(!inactiveSignupLst.isEmpty()) {
					Signup signup=inactiveSignupLst.get(0);
							//if(signup!=null){
				    	    	model.addAttribute("usInfo", user);
				    	    	model.addAttribute("userCount", countmembers);
				    		    model.addAttribute("userS", userS);
				    		    model.addAttribute("signup", signup);
				    		    model.addAttribute("chkoutKidCount", chkoutKidsCount);
				    		    //model.addAttribute("ChkoutKid", hashSet);
				    		    model.addAttribute("ChkoutKid", kidsCheckIn);
				    		    model.addAttribute("respForKidChkIn", respForKidChkIn);
				    		    model.addAttribute("noOfTimesKidCheckinCount", noOfTimesKidCheckinCount);
					    	
					    //	}
			    }else{
			    	
	    	    	//model.addAttribute("usInfo", user);
	    	    	//model.addAttribute("userCount", countmembers);
	    		  //  model.addAttribute("userS", userS);
	    		   model.addAttribute("noSignup", "You are not member.");
			    }
		/*}else{
			model.addAttribute("noSignup", "You are not member.");
		}*/
	}
		return new ModelAndView("KidsClubCheckinWind", model.asMap());
}

	 
	//---CHECKIN A KID -----
	 
		 @RequestMapping(value = "/admin/checkInKids", method = RequestMethod.GET)
			public @ResponseBody Long checkInKids(@RequestParam(value = "primContID") Long primContID,@RequestParam(value = "date_list")String date_list,@RequestParam(value = "chkinMemId")Long chkinMemId,@RequestParam(value = "location") Long location, HttpServletRequest request, final HttpServletResponse response) throws ParseException {
				log.info(" agent id " + agentUid + " , sso " + sso);
				System.out.println("primary id"+primContID);
				System.out.println("list of kids"+date_list);
				System.out.println("Chekin member id"+chkinMemId);
				System.out.println("Checked in location"+location);
				Pageable pageable = new PageRequest(0, 1);
				Account account = null;
				User user=null;
				if(primContID!=null){
					user=userDao.getByContactId(primContID);
					account=user.getCustomer();
					
					List<Long> listOFChildIdLong = new  ArrayList<Long>();
					List<String> listOfChildId = Arrays.asList(date_list.split(","));
					for(String s:listOfChildId){
						System.out.println(s);
						listOFChildIdLong.add(Long.valueOf(s));
					}
					for(Long s:listOFChildIdLong){
						System.out.println(s);
						//listOFChildIdLong.add(Long.valueOf(s));
						User kidCont=userDao.getByContactId(s);
						List<Signup> signups =  signupDao.findByContact(s,pageable);
						Signup signup=signups.get(0);
						
						Calendar cal2_CurrentDate = Calendar.getInstance();
				    	//if(cal1_EndDate.after(cal2_CurrentDate)) {
					    	Activity activity=new Activity();
					    	//DateFormat df = new SimpleDateFormat("dd-MM-yy HH:mm:SS z");
					    	//df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
					    	//String date=df.format(new Date());
					    	//String date1= df.format(new Date());
					    	//Date date = df.parse(date1);
					    	activity.setCreatedDate(new Date());
					    	activity.setCustomer(account);
					    	activity.setContact(kidCont);
					    	activity.setSignup(signup);
					    	//activity.setDescription("Member Checkin");
					    	activity.setDescription("Kids Checkin");
					    	activity.setLastUpdated(cal2_CurrentDate);
					    	activity.setType("KIDS CLUB");
					    	//activity.setPersonResponsibleForCheck_in(chkinMemId);
					    	activity.setCheckInPerson(userDao.getByContactId(chkinMemId));
					    	//activity.setUnauthorizedAccessReason(sexOffReason);
					    	activity.setFacilityLoationCheckedInTo(location);
					    	activityDao.save(activity);
						
					}
					return account.getAccountId();
				}else{
					return null;
				}
		 }
		 
	 
		 
		//Kid CheckOut 
			
		 @RequestMapping(value = "/admin/checkOutKids", method = RequestMethod.GET)
			public @ResponseBody Long checkOutKids(@RequestParam(value = "primContID") Long primContID,@RequestParam(value = "date_list")String date_list,@RequestParam(value = "chkOutMemId")Long chkOutMemId, @RequestParam(value = "location") Long location, HttpServletRequest request, final HttpServletResponse response) {
				log.info(" agent id " + agentUid + " , sso " + sso);
				System.out.println("primary id"+primContID);
				System.out.println("list of kids"+date_list);
				System.out.println("Chekout member id"+chkOutMemId);
				Pageable pageable = new PageRequest(0, 1);
				Account account = null;
				User user=null;
				
				if(primContID!=null ){
					user=userDao.getByContactId(primContID);
					account=user.getCustomer();
					List<Activity> kidsCheckIn= activityDao.findBydCustomer(account);
					for(Activity a:kidsCheckIn){
						
						System.out.println(a.getContact().getContactId());
					}
					List<Long> listOFChildIdLong = new  ArrayList<Long>();
					List<String> listOfChildId = Arrays.asList(date_list.split(","));
					for(String s:listOfChildId){
						System.out.println(s);
						listOFChildIdLong.add(Long.valueOf(s));
					}
					
					//if(!kidsCheckIn.isEmpty()){
						
					for(Long s:listOFChildIdLong){
					for(Activity a:kidsCheckIn){
					
						if(a.getContact().getContactId().equals(s))
						{
							
							a.setCheckoutDatetime(new Date());
							//a.setPersonResponsibleForCheck_out(chkOutMemId);
							a.setCheckOutPerson(userDao.getByContactId(chkOutMemId));
							activityDao.save(a);
							System.out.println("Activity save");
						}
						
					}
				    	
					    	
						
					}
					return account.getAccountId();
				}else{
					return null;
				}
		 }
		 
		 
		 
		//display already checked-in kids page.
		 @RequestMapping(value = "/admin/alreadyCheckedInKids", method = RequestMethod.GET)
			public ModelAndView alreadyCheckedInKids(String agentUid,String sso) {
				this.agentUid = agentUid;
				this.sso = sso;
				request.getSession().setAttribute("agentUid", agentUid);
				
				 Model model = new ExtendedModelMap();
			        try { 
			        	//List<Activity> testActivities=activityDao.findAll();
			            List<Activity> alreadyCheckedInKidsList= activityDao.findByType();
			           for(Activity a:alreadyCheckedInKidsList){
			        	   if(a.getFacilityLoationCheckedInTo()!= 0L){
			        		   a.setLocation(locationDao.getLocationsByLocationId(a.getFacilityLoationCheckedInTo()));
			        	   }
			        	   DateTime chkindate= new DateTime(a.getCreatedDate());
			          	   
			          	   Hours diffofhrs= Hours.hoursBetween(chkindate,new DateTime());
			          	   int hrs= diffofhrs.getHours();
			          	  // int val=diffofhrs.getValue(hrs);
			          	  a.setCheckedInhrs(hrs);
			          	  // System.out.println("hours ="+ hrs );
			           }
			            
			           
			            int checkedInKidsCount=alreadyCheckedInKidsList.size();
			            model.addAttribute("alreadyCheckedInKidsList", alreadyCheckedInKidsList);
			            model.addAttribute("checkedInKidsCount", checkedInKidsCount);
			        } catch (Exception se) {
			            System.out.println("Error occoured while querying Product");
			            log.error("Error  ",se);
			        }
				//return "yAgentSearchUser";
				 return new ModelAndView("alreadyCheckedInKids", model.asMap());    
			}
		 
		 
		 
		 
		 //--------------TEST PEGINATION -----------------------
		 @RequestMapping(value = "/admin/kidsPagination", method = RequestMethod.GET)
		 public ModelAndView kidsPagination(String agentUid,String sso) {
		 
			 this.agentUid = agentUid;
				this.sso = sso;
				request.getSession().setAttribute("agentUid", agentUid);
				Model model = new ExtendedModelMap();
				try {            
						model.addAttribute("locations", locationDao.findAll());
						model.addAttribute("bayAreaLocations", locationDao.getLocationsByArea("Bay Area"));        
					} catch (Exception se) {
						System.out.println("Error occoured while querying Product");
						log.error("Error  ",se);
					}
							//return "yAgentSearchUser";
							 return new ModelAndView("kidsPagination", model.asMap());  
		 
				// return "kidsPagination"; 
				//return "alreadyCheckedInKidspagination";
			}
		
		 
		 
		 
		 @RequestMapping(value = "/admin/kidsPaginationajax", method = RequestMethod.GET)
		 public @ResponseBody String kidsPaginationajx(@RequestParam(value = "location") Long location,String agentUid,String sso) {
			// public String kidsPagination(String agentUid,String sso) {
			 this.agentUid = agentUid;
				this.sso = sso;
				System.out.println("Location ID"+ location);
				request.getSession().setAttribute("agentUid", agentUid);
				JSONArray json = new JSONArray();
				String contextPath = request.getContextPath();
				 Model model = new ExtendedModelMap();
			        try { 
			        	//List<Activity> testActivities=activityDao.findAll();
			           // List<Activity> alreadyCheckedInKidsList= activityDao.findByType();
			        	 List<Activity> alreadyCheckedInKidsList= activityDao.findByTypeAndFacilityLoationCheckedInTo(location);
			           for(Activity a:alreadyCheckedInKidsList){
			        	   if(a.getFacilityLoationCheckedInTo()!= 0L){
			        		   a.setLocation(locationDao.getLocationsByLocationId(a.getFacilityLoationCheckedInTo()));
			        	   }
			        	  
			        	   DateTime chkindate= new DateTime(a.getCreatedDate());
			          	   Hours diffofhrs= Hours.hoursBetween(chkindate,new DateTime());
			          	   int hrs= diffofhrs.getHours();
			          	  // int val=diffofhrs.getValue(hrs);
			          	  a.setCheckedInhrs(hrs);
			          	//  a.setCreatedDate(date);
			          	 //  System.out.println("hours ="+ hrs );
			           }
			           for(Activity a:alreadyCheckedInKidsList){
			        	   JSONObject obj = new JSONObject();
			        	   /*if(a.getCreatedDate().getHours()>=2){
			        		   obj.put("ActivityID","<font color=\"red\">"+ a.getIntegrationId()+"</font>");
				        	   obj.put("ProfilePic",contextPath+"/"+a.getContact().getProfileImage());
				        	   obj.put("KidName","<font color=\"red\">"+ a.getContact().getfNameAndLName()+"</font>");
				        	   
					        	  // SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
					        	 //  SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
					        	 //  Date date = printFormat.parse(a.getCreatedDate().toString());
					        	  // a.setCreatedDate(date);
				        	   obj.put("CheckedInTime","<font color=\"red\">\" "+ a.getCreatedDate().getHours()+"\"</font>\"");
				        	   obj.put("CheckedInPerson","\"<font color=\"red\">\" "+ a.getCheckInPerson().getfNameAndLName()+"\"</font>\"");
				        	   obj.put("CheckInPersonId","\"<font color=\"red\">\" "+a.getCheckInPerson().getEmailAddress()+"\"</font>\"");
				        	   obj.put("CheckInPersonPhn", "\"<font color=\"red\">\" "+a.getCheckInPerson().getFormattedPhoneNumber()+"\"</font>\"");
				        	   obj.put("CheckedInLoc","\"<font color=\"red\">\" "+ a.getLocation().getRecordName()+"\"</font>\"");
				        	   
			        		   
				        	   json.add(obj); 
			        		   
			        	   }else{*/
			        	   obj.put("ActivityID", a.getIntegrationId());
			        	   obj.put("ProfilePic",contextPath+"/"+a.getContact().getProfileImage());
			        	   obj.put("KidName", a.getContact().getfNameAndLName());
			        	   
				        	  // SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
				        	 //  SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
				        	 //  Date date = printFormat.parse(a.getCreatedDate().toString());
				        	  // a.setCreatedDate(date);
			        	  // System.out.println("hours in EST = "+a.getCreatedDate().getHours());
			        	   DateFormat df = new SimpleDateFormat("MM-dd-yy hh:mm:ss a");
			        	   df.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
			        	   String PST = df.format(a.getCreatedDate());
			        	   System.out.println("Date in PST Timezone : " + PST);
			        	   Date date = df.parse(PST);
			       			System.out.println(date);
			        	   System.out.println("hours new ="+date.getHours());
			        	   	DateTime chkindate= new DateTime(a.getCreatedDate());
			          	   
			          	   Hours diffofhrs= Hours.hoursBetween(chkindate,new DateTime());
			          	   int hrs= diffofhrs.getHours();
			          	   System.out.println("hrs using joda Hours"+hrs);
			          	   obj.put("CheckedInhrs",hrs);
			        	   obj.put("CheckedInTime",PST);
			        	   obj.put("CheckedInPerson", a.getCheckInPerson().getfNameAndLName());
			        	   obj.put("CheckInPersonId", a.getCheckInPerson().getEmailAddress());
			        	   obj.put("CheckInPersonPhn", a.getCheckInPerson().getFormattedPhoneNumber());
			        	   obj.put("CheckedInLoc", a.getLocation().getRecordName());
			        	   
			        	   json.add(obj);
			        	   }
			          // }
			            
			           
			           // int checkedInKidsCount=alreadyCheckedInKidsList.size();
			            //System.out.println(checkedInKidsCount);
			           // model.addAttribute("alreadyCheckedInKidsList", alreadyCheckedInKidsList);
			           // model.addAttribute("checkedInKidsCount", checkedInKidsCount);
			        } catch (Exception se) {
			            System.out.println("Error occoured while querying Product");
			            log.error("Error  ",se);
			        }
				//
				//return new ModelAndView("kidsPagination", model.asMap());    
				 
				// return "kidsPagination"; 
			        return json.toString();
			}
		 
		 
		 @RequestMapping(value = "/admin/kidsPaginationajsonobj", method = RequestMethod.GET)
		 public @ResponseBody ModelAndView kidsPaginationajsonobj(String agentUid,String sso) {
			// public String kidsPagination(String agentUid,String sso) {
			 this.agentUid = agentUid;
				this.sso = sso;
				request.getSession().setAttribute("agentUid", agentUid);
				JSONArray json = new JSONArray();
				String contextPath = request.getContextPath();
				 Model model = new ExtendedModelMap();
			        try { 
			        	//List<Activity> testActivities=activityDao.findAll();
			            List<Activity> alreadyCheckedInKidsList= activityDao.findByType();
			           for(Activity a:alreadyCheckedInKidsList){
			        	   if(a.getFacilityLoationCheckedInTo()!= 0L){
			        		   a.setLocation(locationDao.getLocationsByLocationId(a.getFacilityLoationCheckedInTo()));
			        	   }
			        	  
			        	   DateTime chkindate= new DateTime(a.getCreatedDate());
			          	   Hours diffofhrs= Hours.hoursBetween(chkindate,new DateTime());
			          	   int hrs= diffofhrs.getHours();
			          	  // int val=diffofhrs.getValue(hrs);
			          	  a.setCheckedInhrs(hrs);
			          	//  a.setCreatedDate(date);
			          	 //  System.out.println("hours ="+ hrs );
			           }
			           for(Activity a:alreadyCheckedInKidsList){
			        	   JSONObject obj = new JSONObject();
			        	   
			        	   obj.put("ActivityID", a.getIntegrationId().toString());
			        	   obj.put("ProfilePic",contextPath+"/"+a.getContact().getProfileImage().toString());
			        	   obj.put("KidName", a.getContact().getfNameAndLName().toString());
			        	   
				        	  // SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
				        	 //  SimpleDateFormat printFormat = new SimpleDateFormat("HH:mm:ss");
				        	 //  Date date = printFormat.parse(a.getCreatedDate().toString());
				        	  // a.setCreatedDate(date);
			        	 //  obj.put("CheckedInTime", a.getCreatedDate().getHours());
			        	   obj.put("CheckedInPerson", a.getCheckInPerson().getfNameAndLName().toString());
			        	   obj.put("CheckInPersonId", a.getCheckInPerson().getEmailAddress().toString());
			        	   obj.put("CheckInPersonPhn", a.getCheckInPerson().getFormattedPhoneNumber().toString());
			        	   obj.put("CheckedInLoc", a.getLocation().getRecordName().toString());
			        	   
			        	   json.add(obj);
			        	   }
			           
			            
			           
			           int checkedInKidsCount=alreadyCheckedInKidsList.size();
			            System.out.println(checkedInKidsCount);
			            
			           // model.addAttribute("alreadyCheckedInKidsList", alreadyCheckedInKidsList);
			            model.addAttribute("checkedInKidsCount", checkedInKidsCount);
			            model.addAttribute("json", json);
			        } catch (Exception se) {
			            System.out.println("Error occoured while querying Product");
			            log.error("Error  ",se);
			        }
				//
				return new ModelAndView("kidsPaginationjson", model.asMap());    
				 
				// return "kidsPagination"; 
			        //return json.toString();
			}
}


