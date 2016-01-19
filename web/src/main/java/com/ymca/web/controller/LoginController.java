package com.ymca.web.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ymca.dao.AccountDao;
import com.ymca.dao.DonationDao;
import com.ymca.dao.TandCDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.TandC;
import com.ymca.model.User;

@Controller
public class LoginController extends BaseController{
	private Logger log = LoggerFactory.getLogger(LoginController.class);

	@Resource
	private AccountDao accountDao;
	
	@Resource
	private DonationDao donationDao;
	
	@Resource
	private TandCDao tandcDao;
	
	@Resource
	private UserDao userDao;
	
	@RequestMapping(value="/", method=RequestMethod.GET)
    public String login() {    	
        return "login";
    }
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginPage() {    	
        return "login";
    }
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
    public String logout(final HttpServletRequest request, final HttpServletResponse response) { 
		HttpSession session = request.getSession();			            
        session.setAttribute("userId", null);
        return "login";
    }
	
	/*@RequestMapping(value="/login", method=RequestMethod.POST)
    public ModelAndView validateUserLogin(final HttpServletRequest request, final HttpServletResponse response) {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		
		String frmUsername = request.getParameter("username");
		String frmPassword = request.getParameter("password");		
		Model model = new ExtendedModelMap();
		User user =  null;
		
		if("".equalsIgnoreCase(frmUsername) || "".equalsIgnoreCase(frmPassword)){
			model.addAttribute("errMsg", "Please enter the correct Email or password");
			return new ModelAndView("login", model.asMap());
		}
		Account account = accountDao.getByEmail(frmUsername.trim());		
		
		if(account !=null && account.getUser() != null){
			for (Iterator<User> it = account.getUser().iterator(); it.hasNext(); ) {
	    		user = it.next();
					        
		    }
			if(user != null && user.getUsername() !=null && user.getPassword() != null){
				if(bCryptPasswordEncoder.matches(frmPassword, user.getPassword())){
				//if(user.getPassword().equals(request.getParameter("Password"))){
					//List<Program> programList = programDao.findAll();			    	
			    	List<Donation> donationList = donationDao.findAll();			    	
			        model.addAttribute("programList", null); // TODO fixed this to get it from sign
			        model.addAttribute("donationList", donationList);
			        HttpSession session = request.getSession();			            
		            session.setAttribute("userId", request.getParameter("Username"));		            
			        //return new ModelAndView("customer_home", model.asMap());	            
			        return showMembershipInfo(request,response);
			        
				}else{
					model.addAttribute("errMsg", "Please enter the correct Email or password");
					return new ModelAndView("login", model.asMap());
				}
			}
		}else{
			model.addAttribute("errMsg", "Please enter the correct Email or password");
			return new ModelAndView("login", model.asMap());
		}
		return null;
    }*/
	
	public ModelAndView showMembershipInfo(final HttpServletRequest request, final HttpServletResponse response) {
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");    	
    	Account account = null;
    	User user =  null;	
    	if(userId != null && !"".equals(userId)){
	    	account = accountDao.getByEmail(userId);
			request.setAttribute("userId", userId);				
			if(account !=null && account.getUser() != null){
				for (Iterator<User> it = account.getUser().iterator(); it.hasNext(); ) {
		    		user = it.next();
						        
			    }
			}
    	}
		
    	Model model = new ExtendedModelMap();
		if(account != null){	    		    	    	
	        model.addAttribute("accInfo", account);
	        model.addAttribute("usInfo", user);		
	        
	        int userCount = account.getUser().size();
	        List<User> userS = new ArrayList<User>();
	        if(userCount>1){
		        for(User u: account.getUser()){
		        	if(!user.getUsername().equalsIgnoreCase(u.getUsername())){
		        		userS.add(u);
		        	}
		        }
			}
	        model.addAttribute("userCount", userCount);
	        model.addAttribute("userS", userS);
	        
	        model.addAttribute("account", new Account());		
			return new ModelAndView("myProfileView", model.asMap());
		}else {
			model.addAttribute("errMsg", "Please Login");
			return new ModelAndView("login", model.asMap());
			
		}		
	}
	
    /*@RequestMapping(value="/home", method=RequestMethod.GET)
    public String index() {    
        return "home";
    }*/
	@RequestMapping(value="/viewTandC", method=RequestMethod.GET)
    public ModelAndView viewTandC(final HttpServletRequest request, final HttpServletResponse response) { 
		String scid = request.getParameter("scId");
		Model model = new ExtendedModelMap();
		
		if(scid==null && "".equals(scid)){
			model.addAttribute("errMsg", "Required Id Paramater is missing");
			return new ModelAndView("viewTandC", model.asMap());
		}
		try  
		   {  
				Long sc_id = Long.parseLong(scid);
				TandC tandc = tandcDao.getByScId(sc_id);
				
				model.addAttribute("scid", sc_id);
				model.addAttribute("tandc", tandc);
				return new ModelAndView("viewTandC", model.asMap());
		   }  
		   catch( Exception e)  
		   {  
			   model.addAttribute("errMsg", "Required Id Paramater is missing");
				return new ModelAndView("viewTandC", model.asMap());
		   }  
    }
	
	@RequestMapping(value="/viewTandC", method=RequestMethod.POST)
    public ModelAndView viewTandC1(TandC tandc, final HttpServletRequest request, final HttpServletResponse response) { 
		Model model = new ExtendedModelMap();
		
		if(tandc.getScId()!=null && !"".equals(tandc.getScId())){
			tandcDao.save(tandc);
			model.addAttribute("scid", tandc.getScId());
			model.addAttribute("tandc", tandc);
			
		    model.addAttribute("successMsg", "Successfully added. Please close this window");
		}else{
			 model.addAttribute("errMsg", "Required Id Paramater is missing");
		}
		return new ModelAndView("viewTandC", model.asMap());
    }
}
