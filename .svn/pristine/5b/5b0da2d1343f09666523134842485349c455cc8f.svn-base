/**
 * @author gpatwa
 * Factory class contains methods to setup jobs, metedata, schedular 
 */
package com.serene.job.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.serene.job.logger.ItemListener;
import com.serene.job.model.JobSchedulerMetadata;
import com.serene.job.processor.GenericItemProcessor;
import com.serene.job.reader.CustomJdbcItemReader;
import com.serene.job.scheduler.AbstractJobScheduler;
import com.serene.job.scheduler.SpringJobScheduler;
import com.serene.job.writer.AbstractFusionItemWriter;
import com.serene.job.writer.FileItemWriter;
import com.serene.job.writer.ItemWriterFactory;
import com.serene.job.writer.sc.AccountFusionItemWriter;
import com.serene.job.writer.sc.ContactFusionItemWriter;
import com.serene.ws.service.FusionWebService;

@Service
@SuppressWarnings({"rawtypes","unchecked"})
@DependsOn({"modularBatchContext"})
public class CustomJobBuilderFactory  {

	@Resource
	private ItemWriterFactory itemWriterFactory ;	
	
	public static final String JDBC_ITEM_READER = "customJdbcItemReader"; //customJdbcWriter
	
	@Resource 
	protected ResultsetToHashMap resultsetToHashMap ;
	
	@Resource 
	protected JobBuilderFactory jobBuilderFactory ;
	
	@Resource 
	protected StepBuilderFactory stepBuilderFactory  ;
	
	@Resource(name="fusionItemWriter")
	protected AbstractFusionItemWriter fusionItemWriter ;
	
	@Resource
	protected SpringJobScheduler springJobScheduler ;
	
	@Resource(name="fusionWebServiceImpl")
	protected FusionWebService fusionWebService ;

	@Resource
	public ApplicationContext applicationContext ;
	
	@Resource
	private BatchJobContext batchJobContext ;  
	
	public Job buildScToScbJob(JobSchedulerMetadata jobSchedulerMetadata,Job job) throws Exception {
		
		ItemReader reader =  (ItemReader) applicationContext.getBean("fusionItemReader");
		
		CompositeItemWriter compwriter = itemWriterFactory.getCompositeJdbcWriter(jobSchedulerMetadata);
		
		Step step = buildStep(jobSchedulerMetadata, reader, compwriter);
		return buildJob(jobSchedulerMetadata, step,job);
	}
	
	public Job buildDbToScJob(JobSchedulerMetadata jobSchedulerMetadata,Job job) throws Exception {
		
		DataSource dataSource = applicationContext.getBean(jobSchedulerMetadata.getToDataSource(), DataSource.class);
		
		// init 
		CustomJdbcItemReader reader =  (CustomJdbcItemReader) applicationContext.getBean(JDBC_ITEM_READER);
		reader.setDataSource(dataSource);
		reader.setRowMapper(resultsetToHashMap);
		reader.setSql(jobSchedulerMetadata.getFromQuery());
		
		if (StringUtils.equalsIgnoreCase("Account", jobSchedulerMetadata.getToObjectName())) fusionItemWriter = applicationContext.getBean(AccountFusionItemWriter.class) ;
		else if (StringUtils.equalsIgnoreCase("Contact", jobSchedulerMetadata.getToObjectName())) fusionItemWriter = applicationContext.getBean(ContactFusionItemWriter.class);
		
		//fusionItemWriter.setJobSchedulerMetadata(jobSchedulerMetadata);
		
		List<ItemWriter> delegates = new ArrayList<ItemWriter>();
		delegates.add(fusionItemWriter);
		
		CompositeItemWriter compwriter = itemWriterFactory.getCompositeFusionWriter(jobSchedulerMetadata,delegates );
		Step step = buildStep(jobSchedulerMetadata, reader, compwriter);
		return buildJob(jobSchedulerMetadata, step,job);
	}
	
	public Job  buildDbToDbJob(JobSchedulerMetadata jobSchedulerMetadata,Job job) throws Exception {
		
		DataSource dataSource = applicationContext.getBean(jobSchedulerMetadata.getToDataSource(), DataSource.class);
		
		CustomJdbcItemReader reader =  (CustomJdbcItemReader) applicationContext.getBean(JDBC_ITEM_READER);
		reader.setDataSource(dataSource);
		reader.setRowMapper(resultsetToHashMap);
		reader.setSql(jobSchedulerMetadata.getFromQuery());
		
		CompositeItemWriter compwriter = itemWriterFactory.getCompositeJdbcWriter(jobSchedulerMetadata);
		
		Step step = buildStep(jobSchedulerMetadata, reader, compwriter);
		return buildJob(jobSchedulerMetadata, step,job);
	}

	private Step buildStep(JobSchedulerMetadata jobSchedulerMetadata,ItemReader reader, ItemWriter compwriter) throws ClassNotFoundException {

		GenericItemProcessor itemProcessor = new GenericItemProcessor() ;
		if (StringUtils.isNoneBlank(jobSchedulerMetadata.getItemProcessor())) {
			Class processorClazz = Class.forName(jobSchedulerMetadata.getItemProcessor());
			itemProcessor = (GenericItemProcessor) applicationContext.getBean(processorClazz);
		}
		ItemListener itemListener = applicationContext.getBean(ItemListener.class);
		Step step = stepBuilderFactory.get(jobSchedulerMetadata.getJobName()+"-STEP")
		.chunk(jobSchedulerMetadata.getCommitSize())
		.reader(reader)
		.writer((ItemWriter) compwriter)
		.processor(itemProcessor)
		.listener(itemListener)
		.build();
		return step;
	}
	
	private Job buildJob(JobSchedulerMetadata jobSchedulerMetadata, Step step,Job job) {
		job = jobBuilderFactory.get(jobSchedulerMetadata.getJobName())
        .incrementer(new RunIdIncrementer())
        .listener(batchJobContext)
        .flow(step)
        .end()
        .build();
		return job ;
	}
	
	public void loadScheduler(JobSchedulerMetadata jobSchedulerMetadata,AbstractJobScheduler jobScheduler){
		if (StringUtils.isNoneBlank(jobSchedulerMetadata.getCronExpression())) {
			springJobScheduler.initializeJob(jobScheduler);
		}
	}
	
	public Job  buildDbToJetPayJob(JobSchedulerMetadata jobSchedulerMetadata,Job job) throws Exception {
		
		DataSource dataSource = applicationContext.getBean(jobSchedulerMetadata.getFromDataSource(), DataSource.class);
		
		CustomJdbcItemReader reader =  (CustomJdbcItemReader) applicationContext.getBean(JDBC_ITEM_READER);
		reader.setDataSource(dataSource);
		reader.setRowMapper(resultsetToHashMap);
		reader.setSql(jobSchedulerMetadata.getFromQuery());
		
		FileItemWriter itemWriter = itemWriterFactory.getJetPayWriter(jobSchedulerMetadata);
		
		Step step = buildStep(jobSchedulerMetadata, reader, itemWriter);
		return buildJob(jobSchedulerMetadata, step,job);
		
	}
	public Job buildJetPayToDbJob(JobSchedulerMetadata jobSchedulerMetadata,Job job) throws Exception {
		
		ItemReader reader =  (ItemReader) applicationContext.getBean("jetPayItemReader");
		
		CompositeItemWriter compwriter = itemWriterFactory.getCompositeJdbcWriter(jobSchedulerMetadata);
		
		Step step = buildStep(jobSchedulerMetadata, reader, compwriter);
		return buildJob(jobSchedulerMetadata, step,job);
	}
}
