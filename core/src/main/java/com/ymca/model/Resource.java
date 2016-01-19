package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resource")
public class Resource extends BaseObject implements Serializable {
	private static final long serialVersionUID = 1286939797514787165L;
	
	public Resource(){
			
	}	
	
	
	@Id
	@Column(name="ResourceProfileId")
    private Long resourceProfileId ;
	
	@Column(name="Roles")
	private String roles;
	
	@Column(name="EmailAddress")
    private String emailAddress;

	public Long getResourceProfileId() {
		return resourceProfileId;
	}

	public void setResourceProfileId(Long resourceProfileId) {
		this.resourceProfileId = resourceProfileId;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((emailAddress == null) ? 0 : emailAddress.hashCode());
		result = prime
				* result
				+ ((resourceProfileId == null) ? 0 : resourceProfileId
						.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		Resource other = (Resource) obj;
		if (emailAddress == null) {
			if (other.emailAddress != null)
				return false;
		} else if (!emailAddress.equals(other.emailAddress))
			return false;
		if (resourceProfileId == null) {
			if (other.resourceProfileId != null)
				return false;
		} else if (!resourceProfileId.equals(other.resourceProfileId))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Resource [resourceProfileId=" + resourceProfileId + ", roles="
				+ roles + ", emailAddress=" + emailAddress + "]";
	}

		
}
