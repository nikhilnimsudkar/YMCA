package com.ymca.web.controller;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.ActivityDao;
import com.ymca.model.Account;
import com.ymca.model.Activity;
import com.ymca.model.User;
import com.ymca.web.util.Constants;
import com.ymca.web.util.PropFileUtil;

@Controller
@RequestMapping("/recovery")
public class RecoveryController extends BaseController {
	
	private SecureRandom random = new SecureRandom();
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private ActivityDao activityDao;
	
	@RequestMapping(value="/resetpassword", method=RequestMethod.GET)
    public String resetPassword(final HttpServletRequest request, final HttpServletResponse response) { 
        return "resetpassword";
    }
	
	@RequestMapping(value="/resetpassword", method=RequestMethod.POST)
    public ModelAndView resetPassword1(final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		String email = request.getParameter("email");
		if(email==null || "".equals(email)){
			model.addAttribute("errMsg", Constants.BLANK_EMAIL);
			return new ModelAndView("resetpassword", model.asMap());
		}
		
		try  
        {  
        	Account account = accountDao.getByEmail(email.trim());
        	if(account!=null && account.getUser() != null){
        		//Long accountId = account.getAccountId();
        		// create a random token and save it in database with time stamp
        		// create secure 16 digit token
        		String token = new BigInteger(130, random).toString(32);
        		
        		// get current time stamp
        		java.util.Date date= new java.util.Date();
        		
        		// save in database
        		account.setResetPassword(true);
        		account.setToken(token);
        		account.setSentdate(new Timestamp(date.getTime()));
        		Account updAccount =  accountDao.save(account);
        		
        		if(updAccount!=null){
        			User contact =  null;	
        			contact = getUserByAccount(updAccount, contact);
        			
        			// Interaction Integration block start 
					Activity interaction = new Activity();
					interaction.setType(Constants.REQUEST_RESET_PASSWORD);
					
					String resetUrl = request.getRequestURL().toString().replace("/resetpassword", "/resetpasscode");
					String queryString = "?token="+token;
					interaction.setDescription(resetUrl+queryString);
					
					// get current time stamp
	        		interaction.setCreatedDate(new Timestamp(date.getTime()));
	        		interaction.setCustomer(updAccount);
	        		interaction.setContact(contact);
	        		interaction.setShowOnPortal(true);
	        		interaction.setLastUpdated(Calendar.getInstance());
	        		activityDao.save(interaction);
					// Interaction Integration block end 
/*	        		//Create Interacton Record in Fusion	
	        		try{
	        			Long fcrmCustId = 0L;
		        		Long fcrmContactId = 0L;
		        		fcrmCustId = account.getPartyId();
		        		if(contact != null && contact.getFcrmContactId() != null && !"".equals(contact.getFcrmContactId())){
		        			fcrmContactId = Long.valueOf(contact.getFcrmContactId());
		        		}
		        		activityDao.createInteraction(Constants.REQUEST_RESET_PASSWORD, resetUrl+queryString, fcrmCustId, fcrmContactId, date);
	        		}catch(Exception e){
	        			log.error("Error  ",e);
	        		}
*/	        		
        			
        			model.addAttribute("successMsg", Constants.FORGET_PASSWORD_SUCCESS); 
        		}
        		else
        			model.addAttribute("errMsg",Constants.NO_EMAIL_RECORD_FOUND); 
    		}
        	else{
        		model.addAttribute("errMsg",Constants.NO_EMAIL_RECORD_FOUND); 
        	}
        } catch (UsernameNotFoundException ex) {  
            model.addAttribute("errMsg", ex.toString()); 
        } catch (Exception ex) {  
        	model.addAttribute("errMsg", ex.toString()); 
        }  
		return new ModelAndView("resetpassword", model.asMap());
    }
	
	@RequestMapping(value="/resetpasscode", method=RequestMethod.GET)
    public ModelAndView resetPasswordLink(final HttpServletRequest request, final HttpServletResponse response) { 
		// 1. get the token from the URL
		String token = request.getParameter("token");
		Model model = new ExtendedModelMap();
		
		// 2. check if token exists
		if(token==null || "".equals(token) ){
			model.addAttribute("errMsg", Constants.NO_TOKEN);
			return new ModelAndView("resetpasswordlink", model.asMap());
		}
		
		try  
        { 
			// 3. check if token length is valid
			//if(token.trim().length()==33){
				// 4. check if token is valid i.e. it should match with the token in db
				Account acct = accountDao.getByToken(token.trim());
				if(acct!=null && acct.isResetPassword()){
					// 5. check the time stamp.. The token will expire in 48 hours from when it was created
					long expiryhours = Long.parseLong(PropFileUtil.getValue(Constants.RESET_LINK_EXPIRY_HOURS));
					long expirysecond = expiryhours * 60 * 60;
					if(acct.getSentdate()!=null){
					
						java.util.Date currDate= new java.util.Date();
						long diff = currDate.getTime() - acct.getSentdate().getTime();
						long diffSeconds = diff / 1000;
						long diffHours = diff / (60 * 60 * 1000);
						//System.out.println(diffHours);
						//System.out.println(diffSeconds);
						
						if(diffSeconds>expirysecond || diffSeconds<0){
							model.addAttribute("errMsg", Constants.EXPIRED_LINK);
							
							// token is expired, clear it from the database
							acct.setToken("");
			        		accountDao.save(acct);
						}
						else{
							// 6. token and time stamp is valid.. show them reset password form
							model.addAttribute("token", token.trim());
							
							
						}
					}
					else{
						model.addAttribute("errMsg", Constants.INVALID_TOKEN);
					}
				}
				else{
					model.addAttribute("errMsg", Constants.INVALID_TOKEN);
				}
			//}
			//else{
			//	model.addAttribute("errMsg", Constants.INVALID_TOKEN);
			//}
        } catch (Exception ex) {  
        	model.addAttribute("errMsg", ex.toString()); 
        }  
		return new ModelAndView("resetpasswordlink", model.asMap());
    }
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
    public ModelAndView savenewpassword(final HttpServletRequest request, final HttpServletResponse response) { 
		String newpass = request.getParameter("newpassword");
		String repass = request.getParameter("repassword");
		String token = request.getParameter("token");
		Model model = new ExtendedModelMap();
		
		if(token==null || "".equals(token)){
			model.addAttribute("errMsg", Constants.INVALID_TOKEN);
			return new ModelAndView("resetpasswordlink", model.asMap());
		}
		
		if(newpass==null || "".equals(newpass) || repass==null || "".equals(repass)){
			model.addAttribute("errMsg", Constants.ALL_REQUIRED_FIELD);
			return new ModelAndView("resetpasswordlink", model.asMap());
		}
		
		if(newpass.equals(repass)){
			Account account = accountDao.getByToken(token.trim());
			if(account!=null && account.getUser()!=null){
				Set<User> userLst =  account.getUser();
				User user = null;
				user = getUserByAccount(account, user);
				
				if(user==null){
					model.addAttribute("errMsg", Constants.INVALID_TOKEN);
				}
				else{
					BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();					
					user.setPassword(bCryptPasswordEncoder.encode(newpass));
					userLst.add(user);
					account.setUser(userLst);
					
					account.setToken("");
					account.setResetPassword(false);
					
					// get current time stamp
	        		java.util.Date date= new java.util.Date();
					account.setPasswordresetdate(new Timestamp(date.getTime()));
					
					Account updAccount =  accountDao.save(account);
					if(updAccount!=null){
						User contact =  null;	
	        			contact = getUserByAccount(updAccount, contact);
	        			
						Activity activity = new Activity();
						activity.setType(Constants.INTERACTION_TYPE.PASSWORD_RESET.toString());
						
						activity.setDescription("");
						
						// get current time stamp
		        		activity.setCreatedDate(new Timestamp(date.getTime()));
		        		activity.setCustomer(updAccount);
		        		activity.setContact(contact);
		        		activity.setShowOnPortal(true);
		        		activity.setLastUpdated(Calendar.getInstance());
		        		activityDao.save(activity);
						
						model.addAttribute("successMsg", Constants.RESET_PASSWORD_SUCCESS);
					}
					else
						model.addAttribute("errMsg", Constants.INVALID_TOKEN);
				}
			}else{
				model.addAttribute("errMsg", Constants.INVALID_TOKEN);
			}
		}else{
			model.addAttribute("errMsg", Constants.PASSWORD_DONT_MATCH);
		}
		
		return new ModelAndView("resetpasswordlink", model.asMap());
    }
		
	@RequestMapping(value="/resetusername", method=RequestMethod.GET)
    public String resetUsername(final HttpServletRequest request, final HttpServletResponse response) { 
		
        return "resetusername";
    }
	
	@RequestMapping(value="/resetusername", method=RequestMethod.POST)
    public ModelAndView resetUsername1(final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		String dob = request.getParameter("dob");		
		String address1 = request.getParameter("address1");
		String zip = request.getParameter("zip");		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");		
		Date dateOfBirth = null;
		SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy");
		try {
			dateOfBirth = sdf.parse(dob);
		} catch (ParseException e) {
			model.addAttribute("errMsg", "Please correct Date of Birth");
			log.error("Error  ",e);
			return new ModelAndView("resetusername", model.asMap());
		}
		
		if(dob==null || "".equals(dob) || address1==null || "".equals(address1) || zip==null || "".equals(zip) || firstName==null || "".equals(firstName) || lastName==null || "".equals(lastName)){			
			model.addAttribute("errMsg", Constants.ALL_REQUIRED_FIELD);
			return new ModelAndView("resetusername", model.asMap());
		}
		
		////System.out.println(dob + " - " + address1 + " - " + zip);
		//Account account = accountDao.getByAddressLine1IgnoringCase(address1.trim());
		Account account = new Account();
		List<Account> accountLst = accountDao.getByFirstNameAndLastNameAndAddressLine1AndPostalCodeAndDOB(firstName, lastName, dateOfBirth, address1, zip);
		if(accountLst != null && !accountLst.isEmpty()){
			account = accountLst.get(0);
		}
		
		if(account!=null && account.getUser() !=null && account.getUser().size()>0){
			if(zip.trim().equalsIgnoreCase(account.getPostalCode())){
				try{
					User user = null;
					user = getUserByAccount(account, user);
					
					
					if(user.getDateOfBirth()!=null && !"".equals(user.getDateOfBirth())){
						if(sdf.format(sdf.parse(dob)).equals(sdf.format(user.getDateOfBirth()))){
							String username = account.getEmail();
							model.addAttribute("successMsg", Constants.FORGET_USERNAME_SUCCESS.replace("$USR_NAME$", username)); 
						}
					}
					else{
						model.addAttribute("errMsg", Constants.NO_RECORDS_FOUND);
					}
				}
				catch(Exception e){
					//System.out.println(e.getMessage());
					model.addAttribute("errMsg", Constants.NO_RECORDS_FOUND);
				}	
			}
			else{
				model.addAttribute("errMsg", Constants.NO_RECORDS_FOUND);
			}
		}
		else{
			model.addAttribute("errMsg", Constants.NO_RECORDS_FOUND);
		}
		
		
		return new ModelAndView("resetusername", model.asMap());
    }
}
