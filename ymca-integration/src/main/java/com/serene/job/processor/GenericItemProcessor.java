/**
 * @author gpatwa
 * Generic Item Processor. This process is used be no other process is defined for the job
 */
package com.serene.job.processor;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.util.JobUtils;
import com.serene.ws.service.FusionWebService;

@Service
public class GenericItemProcessor implements ItemProcessor<Object,Object> {

	private static final Log log = LogFactory.getLog(GenericItemProcessor.class);

	@Resource(name="fusionWebServiceImpl")
	protected FusionWebService fusionWebService ;
	
	@Resource
	public ApplicationContext applicationContext ;
	
	@Resource
	protected JobUtils jobUtils ; 
	
	@Resource 
	protected BatchJobContext batchJobContext ;
	
	public Object process(Object item) throws Exception {
		return item;
	}

}
