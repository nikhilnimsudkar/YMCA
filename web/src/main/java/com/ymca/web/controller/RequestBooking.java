package com.ymca.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountContactDao;
import com.ymca.dao.AccountDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.AccountContact;
import com.ymca.model.User;
import com.ymca.web.service.ContactService;

@Controller
public class RequestBooking extends BaseController {

	@Resource
	private AccountDao accountDao; 
	
	@Resource
	private AccountContactDao accountContactDao; 
	
	@Resource
	private LocationDao locationDao;
	
	@Resource
	private UserDao userDao;
	
	@Resource
	private ContactService contactService;

	
	
	@RequestMapping(value="/requestBooking", method=RequestMethod.GET)
    public ModelAndView requestBooking() {
		
		Model model = new ExtendedModelMap();
		Authentication a = SecurityContextHolder.getContext()
				.getAuthentication();
		String userId = null;
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
		}
		
		Account account = null;
		User user = null;
		
		if (userId != null && !"".equals(userId)) {
			account = accountDao.getByEmail(userId);
			
			user = getUserByAccount(account, user);		
			model.addAttribute("accInfo", account);
			model.addAttribute("usInfo", user);
		}
		return new ModelAndView("requestBooking", model.asMap());
    }
   
	
	@RequestMapping(value="bookFacility", method = RequestMethod.POST)
    public @ResponseBody String bookFacility(Account account, final HttpServletRequest request, final HttpServletResponse response) {
		
		
		// check if user is logged in
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		String userId = null;
		JSONArray json = new JSONArray();
		
		try{
			userId = ((UserDetails) a.getPrincipal()).getUsername();
		}catch(Exception e){
			User user = userDao.getByEmailAddress(account.getEmail());
			if (user == null) {
				contactService.establishContact(account);
			} else {
				
				List<AccountContact> accountContactList = accountContactDao.getByContact(user);
				if (accountContactList != null) {					
					for (int i=0; i< accountContactList.size(); i++) {
						AccountContact accCon = accountContactList.get(i);
						if (accCon != null) {
							Account accountTemp = accCon.getCustomer();
							String name = accountTemp.getFirstName();
							if (name != null) {
								JSONObject obj = new JSONObject();
								obj.put("FirstName", name);								
								json.add(obj);
							}
						}
					}
				}
			}
			return json.toString();
		}		
		
		Account accountOrig = accountDao.getByEmail(account.getEmail());
		User user = contactService.getPrimaryUserByCustomerId(accountOrig.getAccountId());
		String desc = contactService.getFacilityDesc(account);
		contactService.saveActivity(accountOrig, user,desc , "Facility Booking Request");
		return "SUCCESS";
	}
	
	@RequestMapping(value="confirmFacility", method = RequestMethod.POST)
    public @ResponseBody String confirmFacility(Account account, final HttpServletRequest request, final HttpServletResponse response) {
		
		Account accountOrig = accountDao.getByEmail(account.getEmail());
		User user = contactService.getPrimaryUserByCustomerId(accountOrig.getAccountId());
		String desc = contactService.getFacilityDesc(account);
		contactService.saveActivity(accountOrig, user, desc, "Facility Booking Request");
		return "SUCCESS";
	}
	
	
    
}
