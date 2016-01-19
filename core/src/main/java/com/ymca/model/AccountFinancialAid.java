package com.ymca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * This class represents the basic "Membership" object in YMCA Portal that allows for Membership management.  
 *
 */
@Entity
@Table(name = "account_financial_aid")
public class AccountFinancialAid extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    @Id
    private String Id;

    @Column(name="RecordName")
    private String RecordName;

    @Column(name="ProductCategory_c")
    private String productCategory_c;

    @Column(name="Account_c")
    private String account_c;
    
    @Column(name="Account_id")
    private Long account_id;
    
    public Long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Long account_id) {
		account_id = account_id;
	}

	@Column(name="FaPerCent_c")
    private String faPerCent_c;
    
    @Column(name="FaStartDate_c")
    private Date faStartDate_c;
    
    @Column(name="FaEndDate_c")
    private Date faEndDate_c;
    
    @Column(name="Status_c")
    private String status_c;
    
    public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getRecordName() {
		return RecordName;
	}

	public void setRecordName(String recordName) {
		RecordName = recordName;
	}

	public String getProductCategory_c() {
		return productCategory_c;
	}

	public void setProductCategory_c(String productCategory_c) {
		productCategory_c = productCategory_c;
	}

	public String getAccount_c() {
		return account_c;
	}

	public void setAccount_c(String account_c) {
		account_c = account_c;
	}

	public String getFaPerCent_c() {
		return faPerCent_c;
	}

	public void setFaPerCent_c(String faPerCent_c) {
		faPerCent_c = faPerCent_c;
	}

	public String getStatus_c() {
		return status_c;
	}

	public Date getFaStartDate_c() {
		return faStartDate_c;
	}

	public void setFaStartDate_c(Date faStartDate_c) {
		faStartDate_c = faStartDate_c;
	}

	public Date getFaEndDate_c() {
		return faEndDate_c;
	}

	public void setFaEndDate_c(Date faEndDate_c) {
		faEndDate_c = faEndDate_c;
	}

	public void setStatus_c(String status_c) {
		status_c = status_c;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

    
}
