package com.ymca.test.common;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ymca.WebConfig;
import com.ymca.web.model.SearchGrid;
import com.ymca.web.service.SearchItemDetailService;
import com.ymca.web.util.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WebConfig.class)
@WebIntegrationTest
public class ItemServiceTestCase {

	private static Logger log = LoggerFactory.getLogger(ItemServiceTestCase.class);

	@Resource 
	private SearchItemDetailService searchItemDetailService ;

	
	@Test
	public void searhProgram() throws Exception {
		String category = "SWIM";
		String productname = "";
		String locationId = "300000003600272";
		String startDate = "02/21/2015";
		String endDate = "04/28/2015";
		List<SearchGrid> objs = searchItemDetailService.search(Constants.PRODUCT_CATEGORY_PROGRAM,category, productname, locationId, startDate, endDate,null, null);
		log.info(objs.toString());
	} 
	
	@Test
	public void searhCamp() throws Exception {
		String category = "Day Camp";
		String productname = "";
		String locationId = "300000002167025";
		String startDate = "03/20/2015";
		String endDate = "03/27/2015";
		List<SearchGrid> objs = searchItemDetailService.search(Constants.CAMP_TYPE,category, productname, locationId, startDate, endDate,null, null);
		log.info(objs.toString());
	} 
	
}
