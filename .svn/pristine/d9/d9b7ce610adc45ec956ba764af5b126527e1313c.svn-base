/**
 * @author gpatwa
 * Model class ChildObject
 */
package com.serene.job.model;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity(name="batch_child_object")
@IdClass(ChildObjectPrimaryKey.class)
@Access (AccessType.FIELD)
public class ChildObject implements Serializable {

	@Id
	@Column(name="job_name")
	//@Transient 
	private String jobName;

	@Id
	@Column(name="object_name")
	//@Transient
	private String objectName;
	
	@Column(name="DB_SQL")
	private String sql ;
	
	@Column(name="FQL")
	private String fql ;
	
	@Column(name="FROM_OBJECT_NAME")
	private String fromObjectName;

	@Column(name="FROM_OBJECT_ID_FIELD")
	private String fromObjectIdField;
	
	@Column(name="TO_OBJECT_NAME")
	private String toObjectName;

	@Column(name="TO_OBJECT_ID_FIELD")
	private String toObjectIdField;
	
	@ManyToOne
	@JoinColumn(name="job_name", nullable=false,insertable=false,updatable=false)
	private JobSchedulerMetadata jobScheduler ;
	
	@Transient
/*	@OneToMany(orphanRemoval=true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name="job_name")
*/	private List<ChildFieldMapping> fieldMapping ;
	
	@Column(name="COMMIT_SIZE", columnDefinition="INT DEFAULT 1")
	private Integer commitSize ;
	
	@Column(name="CALLBACK_UPDATE", columnDefinition="boolean DEFAULT false")
	private Boolean callbackUpdate  ;
	
	@Column(name="ACTIVE",columnDefinition="boolean DEFAULT false" )
	private Boolean active;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getFql() {
		return fql;
	}

	public void setFql(String fql) {
		this.fql = fql;
	}

	public String getFromObjectName() {
		return fromObjectName;
	}

	public void setFromObjectName(String fromObjectName) {
		this.fromObjectName = fromObjectName;
	}

	public String getFromObjectIdField() {
		return fromObjectIdField;
	}

	public void setFromObjectIdField(String fromObjectIdField) {
		this.fromObjectIdField = fromObjectIdField;
	}

	public String getToObjectName() {
		return toObjectName;
	}

	public void setToObjectName(String toObjectName) {
		this.toObjectName = toObjectName;
	}

	public String getToObjectIdField() {
		return toObjectIdField;
	}

	public void setToObjectIdField(String toObjectIdField) {
		this.toObjectIdField = toObjectIdField;
	}

	public List<ChildFieldMapping> getFieldMapping() {
		return fieldMapping;
	}

	public void setFieldMapping(List<ChildFieldMapping> fieldMapping) {
		this.fieldMapping = fieldMapping;
	}

	public Integer getCommitSize() {
		return commitSize;
	}

	public void setCommitSize(Integer commitSize) {
		this.commitSize = commitSize;
	}

	public Boolean getCallbackUpdate() {
		return callbackUpdate;
	}

	public void setCallbackUpdate(Boolean callbackUpdate) {
		this.callbackUpdate = callbackUpdate;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public Map<String,String> getActiveFields(){
		Map<String,String> fields = new LinkedHashMap<String, String>();
		for (ChildFieldMapping field :  fieldMapping) {
			if (field.getStatus()) {
				fields.put(field.getToField(), field.getFromField());
			}
		}
		return fields ;
	}
}