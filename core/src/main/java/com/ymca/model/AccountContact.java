package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "account_contact")
public class AccountContact extends BaseObject implements Serializable {
	private static final long serialVersionUID = 2227174158653349487L;


	public AccountContact(){
    	
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)    
    private Long accountContactId;					// required  PK
	
	@Column(name="ObjectPartyId")
	private Long objectPartyId ; 			// Sales Cloud objectPartyId
	
	@Column(name="SubjectPartyId")
	private Long subjectPartyId ; 			// Sales Cloud SubjectPartyId
    
	@Column(name = "RelationshipCode")
   	private String relationshipCode;                    	
    
    @Column(name = "RelationshipType")
    private String relationshipType;      
    
    @Column(name = "StartDate", columnDefinition="DAT")
   	private Date startDate;

    @Column(name = "EndDate")
    @Temporal(TemporalType.DATE)
   	private Date endDate;    
   
    @Column(name = "Comments")
    private String comments;
    
    @Column(name = "Status")
    private String status;    
    
    @Column(name = "SubjectPartyNumber")
   	private String subjectPartyNumber;  
    
    @Column(name = "ObjectPartyNumber")
    private String objectPartyNumber;  
    
    @Column(name = "CreatedBy")
    private String createdBy; 
    
    @Column(name = "CreationDate", columnDefinition="DATETIME")
    private Date creationDate;

    @Column(name = "CreatedByModule")
    private String createdByModule;

    private Calendar lastUpdated;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private User contact;

	public Long getAccountContactId() {
		return accountContactId;
	}

	public void setAccountContactId(Long accountContactId) {
		this.accountContactId = accountContactId;
	}

	public Long getObjectPartyId() {
		return objectPartyId;
	}

	public void setObjectPartyId(Long objectPartyId) {
		this.objectPartyId = objectPartyId;
	}

	public Long getSubjectPartyId() {
		return subjectPartyId;
	}

	public void setSubjectPartyId(Long subjectPartyId) {
		this.subjectPartyId = subjectPartyId;
	}

	public String getRelationshipCode() {
		return relationshipCode;
	}

	public void setRelationshipCode(String relationshipCode) {
		this.relationshipCode = relationshipCode;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	

	public String getSubjectPartyNumber() {
		return subjectPartyNumber;
	}

	public void setSubjectPartyNumber(String subjectPartyNumber) {
		this.subjectPartyNumber = subjectPartyNumber;
	}

	public String getObjectPartyNumber() {
		return objectPartyNumber;
	}

	public void setObjectPartyNumber(String objectPartyNumber) {
		this.objectPartyNumber = objectPartyNumber;
	}

	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCreatedByModule() {
		return createdByModule;
	}

	public void setCreatedByModule(String createdByModule) {
		this.createdByModule = createdByModule;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountContactId == null) ? 0 : accountContactId.hashCode());
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
		AccountContact other = (AccountContact) obj;
		if (accountContactId == null) {
			if (other.accountContactId != null)
				return false;
		} else if (!accountContactId.equals(other.accountContactId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccountContact [accountContactId=" + accountContactId
				+ ", objectPartyId=" + objectPartyId + ", subjectPartyId="
				+ subjectPartyId + ", relationshipCode=" + relationshipCode
				+ ", relationshipType=" + relationshipType + ", startDate="
				+ startDate + ", endDate=" + endDate + ", comments=" + comments
				+ ", status=" + status + ", subjectPartyNumber="
				+ subjectPartyNumber + ", objectPartyNumber="
				+ objectPartyNumber + ", createdBy=" + createdBy
				+ ", creationDate=" + creationDate + ", createdByModule="
				+ createdByModule + ", customer=" + customer + ", contact="
				+ contact + "]";
	}	
}

