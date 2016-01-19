/**
 * @author gpatwa
 * Generic DAO for spring repository. The other repository should extends this interface 
 * 
 */
package com.serene.job.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface GenericDao <T, ID extends Serializable> extends JpaRepository<T, ID> {    


}