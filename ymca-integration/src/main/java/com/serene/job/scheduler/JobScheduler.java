/**
 * @author gpatwa
 *  The Job Scheduler interface.
 */
package com.serene.job.scheduler;

public interface JobScheduler  {

	/**
	 * @throws Exception 
	 * 
	 */
	
	public void loadJob() throws Exception;
	/**
	 * This is the abstract function with no argument. The class which extract AbstractJobScheduler should implement function  
	 */
	public void run() throws Exception;
	
	/**
	 * This function 
	 */
	public void runJob() throws Exception;

}