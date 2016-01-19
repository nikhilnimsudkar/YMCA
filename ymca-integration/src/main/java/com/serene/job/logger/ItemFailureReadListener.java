/**
 * @author gpatwa
 * Listener class trigged when their is fail in the reading record from Item reader
 */
package com.serene.job.logger;

import java.util.Map;

import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

@Component
public class ItemFailureReadListener implements ItemReadListener<Map<String,Object>>  {

	@Override
	public void beforeRead() {
		
	}

	@Override
	public void afterRead(Map<String, Object> item) {
	}
	
	@Override
	public void onReadError(Exception ex) {
	}

}
