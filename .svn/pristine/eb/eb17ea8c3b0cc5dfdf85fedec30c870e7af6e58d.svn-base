/**
 * @author gpatwa
 * This is the main class to initialize Spring Batch
 */
package com.serene.job;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.serene.bootstrap.WebServiceBootStrap;

@Configuration
@ComponentScan(basePackages={"com.serene.job"})
@EnableAutoConfiguration
@EnableBatchProcessing
@EnableTransactionManagement(proxyTargetClass=true)
@EnableScheduling
@Import(WebServiceBootStrap.class)
@ImportResource({"classpath:/META-INF/spring/**/*-context.xml"})
public class JobBootLoader   {

	/*
	@Bean
	@Scope("prototype")
	public ItemReader jdbcItemReader(String sql,DataSource dataSource,RowMapper resultsetToHashMap) {
		CustomJdbcItemReader  reader = new CustomJdbcItemReader();
		reader.setSql(sql);
		reader.setRowMapper(resultsetToHashMap);
		reader.setDataSource(dataSource);
		return reader;
	}
	
	@Bean
	@Scope("prototype")
	public ItemReader fusionItemReader(String fql) {
		FusionItemReader  reader = new FusionItemReader();
		reader.setFql(fql);
		return reader;
	}*/
	
	@Bean(name="GROUP-1")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup1() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-2")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup2() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}

	
	@Bean(name="GROUP-3")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup3() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-4")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup4() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-5")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup5() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-6")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup6() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-7")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup7() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}	
	@Bean(name="GROUP-8")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup8() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}	
	
	@Bean(name="GROUP-9")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup9() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}
	
	@Bean(name="GROUP-10")
	public ThreadPoolTaskScheduler threadPoolTaskSchedulerGroup10() {
		ThreadPoolTaskScheduler  threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		return threadPoolTaskScheduler;
	}	

}
