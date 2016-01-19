package com.ymca.test.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.WebConfig;
import com.ymca.test.common.ItemServiceTestCase;
import com.ymca.web.ws.FacilityBookingServiceWebService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebConfig.class)
@WebIntegrationTest
public class FacilityBookingServiceTestCase {

	private static Logger log = LoggerFactory.getLogger(ItemServiceTestCase.class);
	
	@Resource
	private FacilityBookingServiceWebService facilityBookingService ;
	
	@Test
	@Transactional
	public void testCreateFacilityBooking() throws Exception {
		Long optyId = 300000003289039L;
		facilityBookingService.createFaciltyBoking(optyId );
	}
}
