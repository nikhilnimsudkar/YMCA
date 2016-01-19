package com.ymca.web.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ymca.dao.AccountDao;
import com.ymca.model.Account;
import com.ymca.model.User;
import com.ymca.web.controller.BaseController;

@Component 
@Transactional
public class UserService implements UserDetailsService {
	
	private Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Resource
	private AccountDao accountDao;
	
	@Resource
	private BaseController baseController;
	
	 @Override
	 public UserDetails loadUserByUsername(String username)  
	            throws UsernameNotFoundException {  
	        // TODO Auto-generated method stub  
	          
	        //System.out.println("Entering UserService.loadUserByUsername: " + username);  
	        User user =  null;
	        UserDetails userDetails = null;  
	          
	        try  
	        {  
	        	List<Account> accountLst = accountDao.getUserLstByEmail(username.trim());
	        	if(accountLst != null && !accountLst.isEmpty()){
	        		Account account = accountLst.get(0);
		        	if (account == null ) throw new UsernameNotFoundException(" No user found with email id "  + username);
		        	if(account !=null && account.getUser() != null && account.getUser().size()>0){
		    			if(account.getUser().size()==1){
		    				for(User u: account.getUser()){
		    					user = u;
		    				}
		    			}
		    			else{
		    				boolean primaryUserFound = false;
		    				for(User u: account.getUser()){
		    					if(u.isPrimary()){
		    						primaryUserFound = true;
		    						user = u;
		    						break;
		    					}
		    				}
		    				if(user==null && !primaryUserFound){
		    					user = account.getUser().iterator().next();
		    				}
		    			}
		    		}
		        	if(StringUtils.isBlank(baseController.getAgentUidFromSession())){
		        		userDetails = getUserDetails(username.trim(), user.getPassword());
		        	}else{
		        		userDetails = getUserDetails(username.trim(), "");
		        	}
	        	}
	        }  
	          
	        catch (UsernameNotFoundException ex)  
	        {  
	        	log.error("Error while getting user ",ex);
	        	throw ex;  
	        }  
	        doAutoLogin(user);
	        return userDetails;  
	    }  
	      
	    private UserDetails getUserDetails(String username, String password) {
	    	UserDetails userDetails = null;
	    	if(StringUtils.isBlank(password)){
	    		userDetails = new org.springframework.security.core.userdetails.User(  
	            		username.trim(), "", true,  
	                    true, true, true, getAuthorities(0));
	    	}else{
	    		userDetails = new org.springframework.security.core.userdetails.User(  
	            		username.trim(), password, true,  
	                    true, true, true, getAuthorities(0));
	    	}	    	
	    	return userDetails;
	    }

		@SuppressWarnings("deprecation")
		public Collection<GrantedAuthority> getAuthorities(Integer access) {  
	        //System.out.println("Entering UserService.getAuthorities");  
	        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>(2);  
	          
	       // All users are granted with ROLE_USER access
	 	   // Therefore this user gets a ROLE_USER by default
	 	   authList.add(new GrantedAuthorityImpl("USER"));
	 	    
	 	   // Check if this user has admin access 
	 	   // We interpret Integer(1) as an admin user
	 	   if ( access.compareTo(1) == 0) {
	 	    // User has admin access
	 	    
	 		   authList.add(new GrantedAuthorityImpl("ADMIN"));
	 	   }
	 	 
	 	   // Return list of granted authorities
	 	   return authList;
	    }  
	    
	    public void doAutoLogin(User user) {
		    try {
		        // Must be called from request filtered by Spring Security, otherwise SecurityContextHolder is not updated
		    	Authentication token = new UsernamePasswordAuthenticationToken(user, user.getPassword());
		    	//SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getEmailAddress(), user.getPassword()));
		        //token.setDetails(new WebAuthenticationDetails(request));
		        //Authentication authentication = authenticationProvider.authenticate(token);
		        ////System.out.println(authentication);
		        ////System.out.println("Logging in with [{}]"+ authentication.getPrincipal());
		        SecurityContextHolder.getContext().setAuthentication(token);
		    } catch (Exception e) {
		        SecurityContextHolder.getContext().setAuthentication(null);
		    }

		}
	    
	    public String getSuccessUrl() {  
	    	return "/home";
	    }
}
