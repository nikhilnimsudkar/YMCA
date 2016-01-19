package com.ymca.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ymca.dao.PayerDao;
import com.ymca.dao.SignupDao;
import com.ymca.model.Account;
import com.ymca.model.Invoice;
import com.ymca.model.Payer;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.web.service.PaymentService;

@Controller
public class ThirdPartyResultController extends BaseController {
	
	@Resource
	private PaymentService paymentService;	
			
	@Resource
	private PayerDao payerDao;

	@Resource
	private SignupDao signupDao;
	
	
	@RequestMapping(value="/thirdPartyResult", method=RequestMethod.GET)
    public @ResponseBody String generateInvoice(@RequestParam String signUpId, @RequestParam String payerId,
    		final HttpServletRequest request, final HttpServletResponse response) { 
		Authentication a = SecurityContextHolder.getContext().getAuthentication();
		
		Account customer = null;
		Invoice invoice = null;
		List<User> userList = null;
		User primaryUser = null;
		boolean invoiceExists = false;
		Signup signup = null;
		Payer payer = null;
		
    	try{  	
			
			signup = (Signup)signupDao.getByScId(signUpId);
    		
			payer = (Payer)payerDao.findByScId(payerId);
			
						
    		if (signup != null && payer != null) {
    			invoiceExists = paymentService.invoiceExits(signup.getSignupId(), payer.getId());
    		}   		
    		
    		
    		if (!invoiceExists) {
    			if (signup != null && payer != null) {
    				JSONObject amountLst =  new JSONObject();
        			amountLst.put("fa", signup.getFAamount());
        			
        			customer = payer.getCustomer();
            		if (customer != null) {
            			userList = customer.getUserLst();
            			if (userList != null) {
            				for (int i=0; i< userList.size(); i++) {
            					User userTemp = userList.get(i);
            					if (userTemp != null) {
            						if (userTemp.isPrimary()) {
            							primaryUser = userTemp;
            						}
            					}
            				}
            			}
            		}
            		
            		//invoke the invoice generation service
            		invoice = paymentService.saveinvoice(customer, amountLst, primaryUser, signup, payer);
    			}  else {
        			return "INSUFFICIENT__";
        		}   			
            	
        		
    		}  else {
    			return "EXISTS__";
    		}
    		
    	}
    	catch(Exception e){
    		log.error("Error  ",e);
    		////System.out.println(e);
    		return "FAIL";
    	}
    		
		
		return "SUCCESS__"+ invoice.getInvoiceId();
	}

}
