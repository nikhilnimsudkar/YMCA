package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "contact_waivers_and_tc")
@XmlRootElement
public class ContactWaiversAndTC extends BaseObject implements Serializable {
	private static final long serialVersionUID = 3832626162173359411L;

    @Id
    @GeneratedValue
    @Column(name = "waivers_and_tc", nullable = false)
    private Long waiversAndTC;					// required  PK
    
    @Column(name = "contact_party_id")
   	private Long contactPartyId;              	// required  PK,FK1
    
    @Column(name = "customer_party_id")
    private Long customerPartyId;                 // required  PK,FK2
    
    @Column(name = "RecordName")
    private String recordName;
    
    @Column(name = "tc_description", nullable = false, columnDefinition="LONGTEXT")
    private String tcDescription;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private User contact;

	public Long getContactPartyId() {
		return contactPartyId;
	}

	public void setContactPartyId(Long contactPartyId) {
		this.contactPartyId = contactPartyId;
	}

	public Long getCustomerPartyId() {
		return customerPartyId;
	}

	public void setCustomerPartyId(Long customerPartyId) {
		this.customerPartyId = customerPartyId;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getTcDescription() {
		return tcDescription;
	}

	public void setTcDescription(String tcDescription) {
		this.tcDescription = tcDescription;
	}

	public Long getWaiversAndTC() {
		return waiversAndTC;
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

	public void setWaiversAndTC(Long waiversAndTC) {
		this.waiversAndTC = waiversAndTC;
	}

	@Override
	public String toString() {
		return "ContactWaiversAndTC [waiversAndTC=" + waiversAndTC
				+ ", contactPartyId=" + contactPartyId + ", customerPartyId="
				+ customerPartyId + ", recordName=" + recordName
				+ ", tcDescription=" + tcDescription + ", customer=" + customer
				+ ", contact=" + contact + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ContactWaiversAndTC other = (ContactWaiversAndTC) obj;
		if (waiversAndTC == null) {
			if (other.waiversAndTC != null)
				return false;
		} else if (!waiversAndTC.equals(other.waiversAndTC))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((waiversAndTC == null) ? 0 : waiversAndTC.hashCode());
		return result;
	}

}
