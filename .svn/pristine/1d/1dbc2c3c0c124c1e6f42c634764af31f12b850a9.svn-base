/**
 * @author gpatwa
 * Model class ErrorLog
 */
package com.serene.job.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Entity(name="batch_error_log")
@IdClass(ErrorLogPrimaryKey.class)
@Access (AccessType.FIELD)
public class ErrorLog implements Serializable {

	@Id
	private String objectName ;
	@Id
	private String objectId ;
	
	private String errorMessage ;
	
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectId() {
		return objectId;
	}
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
