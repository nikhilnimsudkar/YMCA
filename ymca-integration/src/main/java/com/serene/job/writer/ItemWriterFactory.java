/**
 * @author gpatwa
 * This is the abstract fusion writer class. The write does basic write operation and calls callback writer if it is enable in the Job scheduler metadata
 * The Abstract Fusion writer does not check for any duplicate record check. 
 * In order to perform correct create and update operation fusion Id should be present in the payload
 *  
 */
package com.serene.job.writer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.serene.job.common.BaseService;
import com.serene.job.dao.ChildObjectDao;
import com.serene.job.model.ChildObject;
import com.serene.job.model.JobSchedulerMetadata;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
@Lazy(false)
public class ItemWriterFactory  extends BaseService {

	@Resource 
	private ChildObjectDao childObjectDao;

	public CompositeItemWriter getCompositeWriter(List<ItemWriter> delegates){
		CompositeItemWriter compwriter = new CompositeItemWriter();
		compwriter.setDelegates(delegates);
		return compwriter ;
	}
	
	public void addJdbcWriter(List<ItemWriter> delegates,JobSchedulerMetadata jobSchedulerMetadata) {
		DataSource  dataSource = applicationContext.getBean(jobSchedulerMetadata.getToDataSource(), DataSource.class);
		CustomJdbcItemWriter writer = (CustomJdbcItemWriter) applicationContext.getBean("customJdbcItemWriter",dataSource,jobSchedulerMetadata.getToQuery());
		delegates.add(writer);
	}
	

	/**
	 * @param delegates
	 * @param jobSchedulerMetadata
	 * Load all active child object in the composite writer
	 */
	public void addChildWriter(List<ItemWriter> delegates,JobSchedulerMetadata jobSchedulerMetadata) {
		List<ChildObject> childObjects =  childObjectDao.findByJobNameAndActive(jobSchedulerMetadata.getJobName(), true);
		for(ChildObject child : childObjects) {
			DataSource  dataSource = applicationContext.getBean(jobSchedulerMetadata.getToDataSource(), DataSource.class);
			CustomJdbcItemWriter childWriter = (CustomJdbcItemWriter) applicationContext.getBean("customJdbcItemWriter",dataSource,child.getSql());
			childWriter.setDataSource(applicationContext.getBean(jobSchedulerMetadata.getToDataSource(), DataSource.class));
			childWriter.setChildObject(child.getObjectName());
			childWriter.setSql(child.getSql());
			delegates.add(childWriter);
		}
	}
	
	public CompositeItemWriter getCompositeJdbcWriter(JobSchedulerMetadata jobSchedulerMetadata) {
		List<ItemWriter> delegates = new  ArrayList<ItemWriter>();
		addJdbcWriter(delegates,jobSchedulerMetadata);		
		addChildWriter(delegates,jobSchedulerMetadata);
		CompositeItemWriter compwriter = getCompositeWriter(delegates);
		return compwriter ;
		
	}
	
	public CompositeItemWriter getCompositeFusionWriter(JobSchedulerMetadata jobSchedulerMetadata,List<ItemWriter> delegates) {
		CompositeItemWriter compwriter = getCompositeWriter(delegates);
		return compwriter ;
		
	}
	
	public FileItemWriter getJetPayWriter(JobSchedulerMetadata jobSchedulerMetadata) {
		List<ItemWriter> delegates = new  ArrayList<ItemWriter>();
		FileItemWriter itemWriter = applicationContext.getBean("fileItemWriter",FileItemWriter.class);
		delegates.add(itemWriter);
		
		return itemWriter ;
		
	}
}