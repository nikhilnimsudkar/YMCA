/**
 * @author gpatwa
 * Generic DAO for IntermidiateTableDao object
 */
package com.serene.job.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.serene.job.model.IntermediateTable;
import com.serene.job.model.IntermediateTablePrimaryKey;


@Repository
public interface IntermidiateTableDao  extends GenericDao<IntermediateTable,IntermediateTablePrimaryKey > {    


/*	@Modifying(clearAutomatically = true)
	@Query("update IntermediateTable i set i.syncState = ?1,i.syncStatus = 'COMPLETED',i.errorMessage = NULL,i.retryCount = 0 where i.jobName = ?3 and i.objectName = ?4 and i.objectId = ?4")
	public void updateItemSucessStatus(String syncState, String jobName, String objectName, String id);
*/	
	@Modifying(clearAutomatically = true)
	@Query("update IntermediateTable i set i.syncState = ?1,i.errorMessage = ?2,i.syncStatus = 'ERROR',i.retryCount = i.retryCount+1 where i.jobName = ?3 and i.objectName = ?4 and i.objectId = ?5")
	public void updateItemErrorStatus(String syncState,String errorMessage, String jobName, String objectName, String id);
	
}