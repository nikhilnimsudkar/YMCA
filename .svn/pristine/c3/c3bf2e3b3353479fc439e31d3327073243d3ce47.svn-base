package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "associated_other_contacts") 
public class AssociatedOtherContacts extends BaseObject implements Serializable {
    
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="SignUpId_c")
	private Long signupId;
	
	@Column(name="ContactId_c")
	private Long contactId;
	
	@Column(name="SignUpPrimaryContactId_c")
	private Long signUpPrimaryContactId;
	
	@Column(name="Status_c")
	private String status;
	
	@Column(name="Role_c")
	private String role;
	
	@Column(name="PortalId_c")
	private Long portalId;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Long getSignupId() {
		return signupId;
	}

	public void setSignupId(Long signupId) {
		this.signupId = signupId;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Long getSignUpPrimaryContactId() {
		return signUpPrimaryContactId;
	}

	public void setSignUpPrimaryContactId(Long signUpPrimaryContactId) {
		this.signUpPrimaryContactId = signUpPrimaryContactId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getPortalId() {
		return portalId;
	}

	public void setPortalId(Long portalId) {
		this.portalId = portalId;
	}
}
