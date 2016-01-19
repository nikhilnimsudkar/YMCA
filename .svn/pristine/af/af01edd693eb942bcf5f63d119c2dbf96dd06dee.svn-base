package com.ymca.web.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.serene.ws.service.FusionWebService;
import com.ymca.dao.AccountDao;
import com.ymca.dao.InvoiceDao;
import com.ymca.dao.ItemDetailDao;
import com.ymca.dao.LocationDao;
import com.ymca.dao.PayerDao;
import com.ymca.dao.SignupDao;
import com.ymca.dao.UserDao;
import com.ymca.model.Account;
import com.ymca.model.Invoice;
import com.ymca.model.ItemDetail;
import com.ymca.model.Location;
import com.ymca.model.Payer;
import com.ymca.model.Signup;
import com.ymca.model.User;
import com.ymca.web.ws.FacilityBookingWebServiceImpl;

@Component
public class FacilityBookingService {

	private Logger log = LoggerFactory.getLogger(FacilityBookingWebServiceImpl.class);
	
	@Resource
	private SignupDao signUpDao ;

	@Autowired
	private PayerDao payerDao ;
	
	@Autowired
	private InvoiceDao invoiceDao ;	
	
	@Autowired
	private AccountDao accountDoa ;
	
	@Autowired
	private FusionWebService fusionWebService ;  
	
	@Autowired
	private ItemDetailDao  itemDetailDao;
	
	@Resource
	private LocationDao locationDao ;
	
	@Resource
	private UserDao userDao ;
	

    public void createFaciltyBoking(Long optyId) throws Exception {
    	log.info(" Validate the Opportunity Id " + optyId);
    	
    	// Get opportunity based on id and data validation 
    	
    	Map<String,Object> objs = fusionWebService.query("select Name,TargetPartyId,LocationId,PrimaryContactPartyId,ChildRevenue,ChildRevenue.ItemDetail_Id_c,ChildRevenue.RevnAmount from Opportunity where OptyId = " + optyId);
    	
    	if (objs == null || objs.isEmpty()) throw new Exception(" Invalid opportunity Id ");
    	List<Map<String,Object>> list = (List<Map<String, Object>>) objs.get("result");
    	
    	Map<String,Object> oppty = list.get(0);
    	
    	String partyId = (String) oppty.get("TargetPartyId");
    	if (StringUtils.isBlank(partyId)) throw new Exception("Please select customer before creating facility booking");
    	
    	List<Map<String,Object>> revenues = (List<Map<String, Object>>) oppty.get("ChildRevenue");
    	
    	if (revenues == null || revenues.isEmpty()) throw new Exception("No revenue line item found for this opportunity");

    	Account customer = accountDoa.findFirst1ByPartyId(Long.parseLong(partyId));
    	if (customer == null) throw new Exception(" Customer record is not in sync to portal ");

    	Signup oldSignup = signUpDao.findFirst1ByOpptyId(optyId.toString()); 
    	if (oldSignup != null) throw new Exception(" Sign already created for this opportunity");

    	User contact =null ;
    	if (oppty.get("PrimaryContactPartyId") != null) {
    		contact = userDao.findFirst1ByPartyId(Long.parseLong((String) oppty.get("PrimaryContactPartyId")));
    	}
    	if (contact == null) throw new Exception(" Please select primary contact for this opportunity");
    	
    	Location location =null ;
    	if (oppty.get("LocationId") != null) {
    		location = locationDao.findOne(Long.parseLong((String) oppty.get("LocationId")));
    	}
    	
    	for (Map<String,Object> revenue :  revenues) {
    		Signup signup = new Signup();
    		ItemDetail itemDetail = itemDetailDao.getOne(300000003644619l);
			signup.setItemDetail(itemDetail );
			signup.setProgramStartDate(new Date());
			signup.setOpptyId(optyId.toString());
			signup.setLastUpdated(Calendar.getInstance());
			signup.setLocation(location);
			signup.setContact(contact);
			
    		signup.setSignUpName((String) oppty.get("Name"));
    		signup.setCustomer(customer);
    		signup.setFinalAmount((String) revenue.get("RevnAmount"));
    		signUpDao.save(signup);
    		
    		Payer payer = new Payer();
    		payer.setSignup(signup);
    		payer.setCustomer(customer);
    		payer.setAmount(Integer.parseInt(signup.getFinalAmount()));
    		payer.setLastUpdated(Calendar.getInstance());
    		payerDao.save(payer);
    		
    		Invoice invoice = new Invoice();
    		invoice.setAmount(payer.getAmount());
    		invoice.setPayer(payer);
    		invoice.setSignup(signup);
    		invoice.setCustomer(customer);
    		invoice.setLastUpdated(Calendar.getInstance());
    		invoiceDao.save(invoice);
    	}
    }
}
