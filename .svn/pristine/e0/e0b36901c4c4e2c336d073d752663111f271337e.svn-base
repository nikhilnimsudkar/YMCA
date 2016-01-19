package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;

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
@Table(name = "opportunity_revenue")
public class OpportunityRevenue extends BaseObject implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	public OpportunityRevenue(){}
	
	/*@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;*/
	
	@Id
	@Column(name = "RevnId")
    private Long revnId ;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="optyId")
	private Opportunity opportunity;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="itemDetailId")
	private ItemDetail itemDetail;
	
	 @Column(name="lastUpdated")
	    private Calendar lastUpdated;

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}
	
	public Opportunity getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(Opportunity opportunity) {
		this.opportunity = opportunity;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}

	public Long getRevnId() {
		return revnId;
	}

	public void setRevnId(Long revnId) {
		this.revnId = revnId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((revnId == null) ? 0 : revnId.hashCode());
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
		OpportunityRevenue other = (OpportunityRevenue) obj;
		if (revnId == null) {
			if (other.revnId != null)
				return false;
		} else if (!revnId.equals(other.revnId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OpportunityRevenue [revnId=" + revnId + ", opportunity="
				+ opportunity + ", itemDetail=" + itemDetail + ", lastUpdated="
				+ lastUpdated + "]";
	}
	
	

}
