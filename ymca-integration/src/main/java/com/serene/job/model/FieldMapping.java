/**
 * @author gpatwa
 * Model class FieldMapping
 */
package com.serene.job.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="BATCH_JOB_FIELD_MAPPING")
@Access (AccessType.FIELD)
public class FieldMapping implements Serializable {

	@Id
	@Column(name="id")
	private Long id;
	
	//@Transient
	@ManyToOne
	@JoinColumn(name="JOB_NAME", nullable=false)
	private JobSchedulerMetadata jobScheduler ;
	
	@Column(name="JOB_NAME",updatable=false,insertable=false)
	private String jobName ;
	
	@Column(name="FROM_FIELD")
	private String fromField ;
	@Column(name="TO_FIELD")
	private String toField ;
	
	@Column(name="DEFAULT_VALUE")
	private String defaultValue ;
	
	@Column(name="OPERATION_TYPE", columnDefinition="varchar2(10) DEFAULT 'ALL'")
	private String operationType ;
	
	@Column(name="STATUS", columnDefinition="boolean DEFAULT true")
	private Boolean status = true;
	
	@Column(name="CALLBACK_UPDATE", columnDefinition="boolean DEFAULT false")
	private Boolean callbackUpdate  ;
	
	@Column(name="INCLUDE_NULL_VALUE", columnDefinition="boolean DEFAULT false")
	private Boolean includeNullValue  ;
	
	@Column(name="FIELD_ORDER")
	private Integer fieldOrder ;
	
	public String getFromField() {
		return fromField;
	}
	public void setFromField(String fromField) {
		this.fromField = fromField;
	}
	public String getToField() {
		return toField;
	}
	public void setToField(String toField) {
		this.toField = toField;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public Boolean getCallbackUpdate() {
		return callbackUpdate;
	}
	public void setCallbackUpdate(Boolean callbackUpdate) {
		this.callbackUpdate = callbackUpdate;
	}
	public Boolean getIncludeNullValue() {
		return includeNullValue;
	}
	public void setIncludeNullValue(Boolean includeNullValue) {
		this.includeNullValue = includeNullValue;
	}
	public Integer getFieldOrder() {
		return fieldOrder;
	}
	public void setFieldOrder(Integer fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
}
