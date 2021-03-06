/**
 * @author gpatwa
 * Custom jdbc item writer. This class convert the input data into hashmap before it pass to spring jdbc writer
 */
package com.serene.job.writer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.jxpath.JXPathContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.serene.job.common.BatchJobContext;
import com.serene.job.model.ChildFieldMapping;
import com.serene.job.model.ChildObject;
import com.serene.job.model.FieldMapping;
import com.serene.job.util.JobUtils;

@Service
@Lazy(true)
@Scope("prototype")
public class CustomJdbcItemWriter<T> extends JdbcBatchItemWriter<T> {

	private String childObject = null;
	
	@Resource
	private FusionCallbackWriter fusionCallbackWriter ;

	@Resource
	private BatchJobContext batchJobContext ;
	
	@Resource 
	private JobUtils jobUtils ;
	
	public CustomJdbcItemWriter() {
		
	}
	public CustomJdbcItemWriter(DataSource dataSource,String sql) {
		setDataSource(dataSource);
		setSql(sql);
	}
	
	@Override
	public void write(final List<? extends T> items) throws Exception {
		if (StringUtils.isNoneBlank(childObject)) writeChild(items);
		else writeParent(items);
	}
	
	@SuppressWarnings("unchecked")
	public void writeChild(final List<? extends T> items) throws Exception {
		// return if no child field mapping object is disabled or null
		if (batchJobContext.getChildObjs() == null || batchJobContext.getChildFieldMappings() == null) {
			return ;
		}
		List<ChildFieldMapping> fieldMappings = batchJobContext.getChildFieldMappings().get(childObject);
		List<Map<String,Object>> values = new ArrayList<Map<String, Object>>();
		
		ChildObject c = batchJobContext.getChildObjs().get(childObject);
		
		// return if no child object is disabled or null
		if (c == null || !c.getActive()) {
			return ;
		}
		setSql(c.getSql());
		for (int i = 0 ; i < items.size() ; i++) {
			Map<String,Object> item = (Map<String, Object>) items.get(i);
			JXPathContext context = JXPathContext.newContext(item);
			context.setLenient(true);			
			for(Iterator<Map<String,Object>> iter = context.iterate(c.getObjectName()); iter.hasNext();){	
				Map<String,Object> childItem = iter.next();
				JXPathContext childContext = JXPathContext.newContext(childItem);
				context.setLenient(true);			
				
				Map<String,Object> value = new HashMap<String,Object>();
				for (ChildFieldMapping m : fieldMappings) {
					Object fv = childContext.getValue(m.getFromField());
					if (fv != null && StringUtils.equalsIgnoreCase("",fv.toString()) ){
						fv = null ;
					}
					if (fv == null && StringUtils.isNoneBlank(m.getDefaultValue())) {
						fv = m.getDefaultValue();
					}
					value.put(m.getFromField(), fv);
				}
				values.add(value);
			}
		}
		try {
			super.write((List<? extends T>) values);
		} catch (Exception e)  {
			for (Map<String,Object> item : values) {
				try {
					jobUtils.logItemStatus(e,batchJobContext.getJobSchedulerMetadata().getJobName(), batchJobContext.getJobSchedulerMetadata().getFromObjectName(), batchJobContext.getJobSchedulerMetadata().getFromObjectIdField(),item);
				} catch (Exception e1) {
					logger.error("Error while loggging item in the intermediate table",e1);
				}
			}
			logger.error("Error while child object writing in db",e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public void writeParent(final List<? extends T> items) throws Exception {
		List<Map<String,Object>> values = new ArrayList<Map<String, Object>>();
		List<FieldMapping> fieldMappings = batchJobContext.getFieldMappings();
		for (int i = 0 ; i < items.size() ; i++) {
			Map<String,Object> item = (Map<String, Object>) items.get(i);
			
			JXPathContext context = JXPathContext.newContext(item);
			context.setLenient(true);			
			
			Map<String,Object> value = new HashMap<String,Object>();
			for (FieldMapping m : fieldMappings) {
				Object fv = context.getValue(m.getFromField());
				/*if (StringUtils.equalsIgnoreCase(jobSchedulerMetadata.getToObjectIdField(), m.getToField()) && fv != null && StringUtils.equalsIgnoreCase("",fv.toString()) ){
					fv = null ;
				}*/
				if (fv != null && StringUtils.equalsIgnoreCase("",fv.toString()) ){
					fv = null ;
				}
				if (fv == null && StringUtils.isNoneBlank(m.getDefaultValue())) {
					fv = m.getDefaultValue();
				}
				value.put(m.getFromField(), fv);
			}
			values.add(value);
		}
		try {
			super.write((List<? extends T>) values);
			if (batchJobContext.getJobSchedulerMetadata().getCallbackUpdate()) {
				List<String> operationTypes = new ArrayList<String>();
				fusionCallbackWriter.updateResponse(values, null, operationTypes );
			}
		} catch (Exception e)  {
			for (Map<String,Object> item : values) {
				try {
					jobUtils.logItemStatus(e,batchJobContext.getJobSchedulerMetadata().getJobName(), batchJobContext.getJobSchedulerMetadata().getFromObjectName(), batchJobContext.getJobSchedulerMetadata().getFromObjectIdField(),item);
				} catch (Exception e1) {
					logger.error("Error while loggging item in the intermediate table",e1);
				}
			}
			logger.error("Error while writing in db",e);
		}
	}

	public void setSql(String sql) {
		super.setSql(sql);
	}

	public String getChildObject() {
		return childObject;
	}
	public void setChildObject(String childObject) {
		this.childObject = childObject;
	}
}
