package com.ymca.web.ws;

import javax.annotation.PostConstruct;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.ymca.WebAppBootLoader;
import com.ymca.web.service.FacilityBookingService;

//@WebService
public class FacilityBookingWebServiceImpl extends SpringBeanAutowiringSupport implements FacilityBookingServiceWebService {

	private Logger log = LoggerFactory.getLogger(FacilityBookingWebServiceImpl.class);
	
	@Autowired
	private FacilityBookingService facilityBookingService ;  
	
    @PostConstruct
	public void init() {
	    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
    @WebMethod
    public void createFaciltyBoking(Long optyId) throws Exception {
    	log.info(" Validate the Opportunity Id " + optyId);
    	if (facilityBookingService == null) {
    		log.warn("Spring dependency injection failed, adding depending manually");
    		facilityBookingService = WebAppBootLoader.applicationContext.getBean(FacilityBookingService.class);
    	}
    	
    	facilityBookingService.createFaciltyBoking(optyId);
    }
}
