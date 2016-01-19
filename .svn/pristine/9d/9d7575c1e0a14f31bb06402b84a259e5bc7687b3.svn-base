package com.ymca.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



/**
 * This class represents the basic "Membership" object in YMCA Portal that allows for Membership management.  
 *
 */
@Entity
@Table(name = "opportunity")
public class Opportunity extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO) 
      private Long id;     // required  PK*/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="OptyId")
    private Long optyId ; // Sales Cloud OptyId Id
    
    /*@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long optyId;*/

    @Column(name="Name")
    private String name;

    @Column(name="Contact_c")
    private String contact_c;

    @Column(name="CustomerAccountId_c")
    private String customerAccountId_c;

    /*@Column(name="EffectiveDate")
    private String effectiveDate;*/
    
    @Column(name = "EffectiveDate", columnDefinition="DATE")
   	private Date effectiveDate;
    
    @OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="signupId")
	private Signup signup;
    
    @Column(name="lastUpdated")
    private Calendar lastUpdated;    

	@Column(name="ExpectAmount")
    private String expectAmount;
    
    @Column(name="OptyNumber")
    private String optyNumber;
    
    @Column(name="OwnerResourcePartyId")
    private String ownerResourcePartyId;

    @Column(name="Revenue")
    private String revenue;

    @Column(name="SalesMethod")
    private String salesMethod;
    
    @Column(name="SalesMethodId")
    private String salesMethodId;
    
    @Column(name="SalesStage")
    private String salesStage;

    @Column(name="SalesStageId")
    private String salesStageId;
    
    @Column(name="TargetPartyId")
    private String targetPartyId;

    @Column(name="Type")
    private String type;

    @Column(name="PaymentMode_c")
    private String paymentMode_c;
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "optyId")
    private List<OpportunityRevenue> opptyRevenue = new ArrayList<OpportunityRevenue>();
    
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "optyId")
    private List<OpportunityContact> opptyContact;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="Campaigner")
	private User campaigner;
    
    public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public Signup getSignup() {
		return signup;
	}

	public void setSignup(Signup signup) {
		this.signup = signup;
	}

	/*public Long getOptyId() {
		return optyId;
	}

	public void setOptyId(Long optyId) {
		this.optyId = optyId;
	}*/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact_c() {
		return contact_c;
	}

	public void setContact_c(String contact_c) {
		this.contact_c = contact_c;
	}

	public String getCustomerAccountId_c() {
		return customerAccountId_c;
	}

	public void setCustomerAccountId_c(String customerAccountId_c) {
		this.customerAccountId_c = customerAccountId_c;
	}

	public String getExpectAmount() {
		return expectAmount;
	}

	public void setExpectAmount(String expectAmount) {
		this.expectAmount = expectAmount;
	}

	public String getOptyNumber() {
		return optyNumber;
	}

	public void setOptyNumber(String optyNumber) {
		this.optyNumber = optyNumber;
	}

	public String getOwnerResourcePartyId() {
		return ownerResourcePartyId;
	}

	public void setOwnerResourcePartyId(String ownerResourcePartyId) {
		this.ownerResourcePartyId = ownerResourcePartyId;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getSalesMethod() {
		return salesMethod;
	}

	public void setSalesMethod(String salesMethod) {
		this.salesMethod = salesMethod;
	}

	public String getSalesMethodId() {
		return salesMethodId;
	}

	public void setSalesMethodId(String salesMethodId) {
		this.salesMethodId = salesMethodId;
	}

	public String getSalesStage() {
		return salesStage;
	}

	public void setSalesStage(String salesStage) {
		this.salesStage = salesStage;
	}

	public String getSalesStageId() {
		return salesStageId;
	}

	public void setSalesStageId(String salesStageId) {
		this.salesStageId = salesStageId;
	}

	public String getTargetPartyId() {
		return targetPartyId;
	}

	public void setTargetPartyId(String targetPartyId) {
		this.targetPartyId = targetPartyId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPaymentMode_c() {
		return paymentMode_c;
	}

	public void setPaymentMode_c(String paymentMode_c) {
		this.paymentMode_c = paymentMode_c;
	}

	public List<OpportunityRevenue> getOpptyRevenue() {
		return opptyRevenue;
	}

	public void setOpptyRevenue(List<OpportunityRevenue> opptyRevenue) {
		this.opptyRevenue = opptyRevenue;
	}

	public User getCampaigner() {
		return campaigner;
	}

	public void setCampaigner(User campaigner) {
		this.campaigner = campaigner;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Long getOptyId() {
		return optyId;
	}

	public void setOptyId(Long optyId) {
		this.optyId = optyId;
	}

	public List<OpportunityContact> getOpptyContact() {
		return opptyContact;
	}

	public void setOpptyContact(List<OpportunityContact> opptyContact) {
		this.opptyContact = opptyContact;
	}

	@Override
	public String toString() {
		return "Opportunity [optyId=" + optyId + ", name=" + name
				+ ", contact_c=" + contact_c + ", customerAccountId_c="
				+ customerAccountId_c + ", effectiveDate=" + effectiveDate
				+ ", signup=" + signup + ", lastUpdated=" + lastUpdated
				+ ", expectAmount=" + expectAmount + ", optyNumber="
				+ optyNumber + ", ownerResourcePartyId=" + ownerResourcePartyId
				+ ", revenue=" + revenue + ", salesMethod=" + salesMethod
				+ ", salesMethodId=" + salesMethodId + ", salesStage="
				+ salesStage + ", salesStageId=" + salesStageId
				+ ", targetPartyId=" + targetPartyId + ", type=" + type
				+ ", paymentMode_c=" + paymentMode_c + ", opptyRevenue="
				+ opptyRevenue + ", campaigner=" + campaigner + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Opportunity other = (Opportunity) obj;
		if (optyId == null) {
			if (other.optyId != null)
				return false;
		} else if (!optyId.equals(other.optyId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((optyId == null) ? 0 : optyId.hashCode());
		return result;
	}

    
}
