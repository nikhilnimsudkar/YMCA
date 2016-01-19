package com.ymca.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="system_property")
public class SystemProperty extends BaseObject implements GenericModel {
	private static final long serialVersionUID = 2170838067182799183L;

	@Id
	@Column(name ="keyName")
	private String keyName ;

	@Column(name ="picklistName")
	private String picklistName;

	@Column(name ="keyValue")
	private String keyValue;
	
	@Column(name ="pageName")
	private String pageName;
	
	@Column(name ="description")
	private String description ;
	
	@Column(name ="fieldStatus")
	private Boolean fieldStatus = true;

	@Column(name ="fieldOrder")
	private Integer fieldOrder = 0;
	

	public String getPicklistName() {
		return picklistName;
	}

	public void setPicklistName(String picklistName) {
		this.picklistName = picklistName;
	}

	public String getKeyName() {
		return keyName;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isFieldStatus() {
		return fieldStatus;
	}

	public void setFieldStatus(boolean fieldStatus) {
		this.fieldStatus = fieldStatus;
	}

	public int getFieldOrder() {
		return fieldOrder;
	}

	public void setFieldOrder(int fieldOrder) {
		this.fieldOrder = fieldOrder;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.keyName == null) ? 0 : this.keyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemProperty other = (SystemProperty) obj;
		if (this.keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!this.keyName.equals(other.keyName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SystemProperty [keyName=" + keyName + ", picklistName="
				+ picklistName + ", keyValue=" + keyValue + ", description="
				+ description + ", fieldStatus=" + fieldStatus
				+ ", fieldOrder=" + fieldOrder + "]";
	}


	
}

