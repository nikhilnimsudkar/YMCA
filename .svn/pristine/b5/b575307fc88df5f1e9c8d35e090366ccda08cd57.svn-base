package com.ymca.model;

import java.io.Serializable;
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

@Entity
@Table(name = "contact_promotion")
public class ContactPromotion extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)    
    private Long id;

    @Column(name="RecordName")
    private String recordName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="contact_id")
    private User contact;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_detail_id")
	private ItemDetail itemDetail;
	
	/*@Column(name = "Status")
    private String status;*/
    
	@Column(name = "CreatedBy")
    private String createdBy; 
    
    @Column(name = "CreationDate", columnDefinition="DATETIME")
    private Date creationDate;
	
    @Column(name = "lastUpdatedBy")
    private String lastUpdatedBy; 
    
    @Column(name = "lastUpdateDate", columnDefinition="DATETIME")
    private Date lastUpdateDate;
	
    @Column(name = "Status_c")
    private String status;
    
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

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
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

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

    
}
