package com.serene.job;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.jxpath.JXPathContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.serene.job.common.JXpathFactory;
import com.serene.job.dao.JobSchedulerMetadataDao;
import com.serene.job.model.FieldMapping;
import com.serene.job.model.JobSchedulerMetadata;

@SpringApplicationConfiguration(classes = JobBootLoader.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class JxPathMappingTestCase {
	
	private static Logger log = LoggerFactory.getLogger(JxPathMappingTestCase.class);
	
	@Resource
	private JobSchedulerMetadataDao jobSchedulerMetadataDao; 

	@Test
	@Transactional
	public void testPayloadTemplate() {
		
		JobSchedulerMetadata jobSchedulerMetadata =  jobSchedulerMetadataDao.getOne("ACCOUNT_SYNC");
		List<FieldMapping> fieldMappings = jobSchedulerMetadata.getFieldMapping();
		Map<String,Object> payloadTemplate = new LinkedHashMap<String, Object>();
		JXPathContext context = JXPathContext.newContext(payloadTemplate);
		context.setFactory(new JXpathFactory());
		context.setLenient(true);
		for(FieldMapping m : fieldMappings) {
			context.createPath(m.getToField());
		}
		log.info(payloadTemplate.toString());
	}

}
