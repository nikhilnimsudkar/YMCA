/**
 * @author gpatwa
 * Generic DAO for ChildObjectDao object
 */
package com.serene.job.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.serene.job.model.ChildObject;
import com.serene.job.model.ChildObjectPrimaryKey;


@Repository
public interface ChildObjectDao  extends GenericDao<ChildObject,ChildObjectPrimaryKey> {    

	public List<ChildObject> findByJobNameAndActive(String jobName,Boolean active); 
}