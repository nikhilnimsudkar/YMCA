/**
 * 
 */
package com.ymca.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * This class represents the basic "ChildCareInformation" object in YMCA Portal
 * that allows for ChildCare process management.
 * 
 * 
 * @author achauhan
 * 
 */

@Entity
@Table(name = "childcare_info")
public class ChildCare implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChildCare() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long childCareId; // required PK

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	@Column(name = "account_id", nullable = false)
	private Long accountId;
	@Column(name = "childcare_name")
	private String name; // required PK,FK1

	@Column(name = "childcare_number", nullable = false)
	private Long childCareNumber;

	@Column(name = "contact_name", nullable = false)
	private String contactName;

	public Long getChildCareId() {
		return childCareId;
	}

	public void setChildCareId(Long childCareId) {
		this.childCareId = childCareId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getChildCareNumber() {
		return childCareNumber;
	}

	public void setChildCareNumber(Long childCareNumber) {
		this.childCareNumber = childCareNumber;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}

	/**
	 * @param contactName
	 *            the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result
				+ ((contactName == null) ? 0 : contactName.hashCode());
		result = prime * result
				+ ((childCareNumber == null) ? 0 : childCareNumber.hashCode());
		result = prime * result
				+ ((childCareId == null) ? 0 : childCareId.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChildCare other = (ChildCare) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (contactName == null) {
			if (other.contactName != null)
				return false;
		} else if (!contactName.equals(other.contactName))
			return false;
		if (childCareNumber == null) {
			if (other.childCareNumber != null)
				return false;
		}
		if (childCareId == null) {
			if (other.childCareId != null)
				return false;
		}

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ChildCare [childCareId=" + childCareId + ", accountId="
				+ accountId + ", childCareNumber=" + childCareNumber
				+ ", contactName=" + contactName + ", accountId=" + accountId
				+ "]";
	}

}
