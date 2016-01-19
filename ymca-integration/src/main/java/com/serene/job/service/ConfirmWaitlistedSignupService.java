package com.serene.job.service;

import java.util.Map;

import com.serene.job.common.BatchJobContext;

public interface ConfirmWaitlistedSignupService {
	
	public Map<String,Object> confirmWaitlistedSignup(Map<String,Object> data, BatchJobContext batchJobContext);
}
