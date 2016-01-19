package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * This class represents the basic "Membership" object in YMCA Portal that allows for Membership management.  
 *
 */
@Entity
@Table(name = "opportunity_contact")
public class OpportunityContact extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 4547102754754040385L;
	
	@Id
    @Column(name="OptyConId")
    private String optyConId ; // Sales Cloud Opty contact Id
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="optyId")
	private Opportunity opportunity;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private User contact;
    
    @Column(name="PrimaryFlg")
    private String primaryFlg;

    @Column(name="RoleCd")
    private String role;
    
    @Column(name="lastUpdated")
    private Calendar lastUpdated;
	
	public String getOptyConId() {
		return optyConId;
	}

	public void setOptyConId(String optyConId) {
		this.optyConId = optyConId;
	}

	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getPrimaryFlg() {
		return primaryFlg;
	}

	public void setPrimaryFlg(String primaryFlg) {
		this.primaryFlg = primaryFlg;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((optyConId == null) ? 0 : optyConId.hashCode());
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
		OpportunityContact other = (OpportunityContact) obj;
		if (optyConId == null) {
			if (other.optyConId != null)
				return false;
		} else if (!optyConId.equals(other.optyConId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpportunityContact [optyConId=" + optyConId + ", opportunity="
				+ opportunity + ", contact=" + contact + ", primaryFlg="
				+ primaryFlg + ", role=" + role + ", lastUpdated="
				+ lastUpdated + "]";
	}    

	
}
