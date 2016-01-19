package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_detail_campaigner")
public class ItemDetailCampaigner extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
    private Long id;
	
	@Column(name="RecordName")
	private String recordName ;


	@Column(name="Contact_c")
	private String contact_c;

	@Column(name="ItemDetail_c")
	private String itemDetail_c;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		recordName = recordName;
	}

	public String getContact_c() {
		return contact_c;
	}

	public void setContact_c(String contact_c) {
		contact_c = contact_c;
	}

	public String getItemDetail_c() {
		return itemDetail_c;
	}

	public void setItemDetail_c(String itemDetail_c) {
		itemDetail_c = itemDetail_c;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemDetailCampaigner other = (ItemDetailCampaigner) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemDetailCampaigner [Id=" + id + ", RecordName=" + recordName
				+ ", Contact_c=" + contact_c + ", ItemDetail_c=" + itemDetail_c
				+ "]";
	}

}
