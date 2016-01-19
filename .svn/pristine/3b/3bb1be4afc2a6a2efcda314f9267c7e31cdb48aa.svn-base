package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "campaigner")
public class Campaigner extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = -3202944701848397747L;

	public Campaigner(){
    }

	@Id
	@Column(name="Id")
    private Long Id;
	
	@Column(name="RecordName")
	private String recordName ;
	
	@ManyToOne
	@JoinColumn(name="signup_id")
	private ItemDetail signupId;
	
	@ManyToOne
	@JoinColumn(name="item_detail_id")
	private ItemDetail itemDetail;
	
	
	@Column(name="account_party_id")
	private String acountPartyId ;
	
	@Column(name="contact_party_id")
	private String contactPartyId ;
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public ItemDetail getSignupId() {
		return signupId;
	}

	public void setSignupId(ItemDetail signupId) {
		this.signupId = signupId;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}

	public String getAcountPartyId() {
		return acountPartyId;
	}

	public void setAcountPartyId(String acountPartyId) {
		this.acountPartyId = acountPartyId;
	}

	public String getContactPartyId() {
		return contactPartyId;
	}

	public void setContactPartyId(String contactPartyId) {
		this.contactPartyId = contactPartyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		Campaigner other = (Campaigner) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Campaigner [recordName=" + recordName + ", signupId="
				+ signupId + ", itemDetail=" + itemDetail + ", acountPartyId="
				+ acountPartyId + ", contactPartyId=" + contactPartyId + "]";
	}
	
}
