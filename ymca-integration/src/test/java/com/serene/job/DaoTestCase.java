package com.serene.job;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.serene.job.dao.JobFieldMappingDao;
import com.serene.ws.binding.AbstractSoapParser;

//@ContextConfiguration(classes = WebServiceBootStrap.class, loader = SpringApplicationContextLoader.class)
@SpringApplicationConfiguration(classes = JobBootLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTestCase {


	private static Logger log = LoggerFactory.getLogger(AbstractSoapParser.class);

	@Resource
	private JobFieldMappingDao jobFieldMappingDao ;
	
	
	@Test
	public void test(){
		System.out.println("Test");
		//List<FieldMapping> fieldMappings = jobFieldMappingDao.findByJobNameAndStatusAndOperationTypeIn("CONTACT_SYNC", true, null);
	}
	
	
}