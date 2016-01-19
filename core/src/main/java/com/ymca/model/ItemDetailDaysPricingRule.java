package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "item_detail_days_pricing_rule")
@XmlRootElement
public class ItemDetailDaysPricingRule extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;



	public ItemDetailDaysPricingRule() {
		
	}
	
	@Id
	@Column(name="Id")
	private Long id;
	
	@Column(name = "RecordName")
	private String recordName;                
	    
	 @ManyToOne
	 @JoinColumn(name="item_detail_id")
	 private ItemDetail itemDetail;
	 
	 @ManyToOne
	 @JoinColumn(name="item_detail_days_id")
	 private ItemDetailDays itemDetailDays;

	 @ManyToOne
	 @JoinColumn(name="pricing_rule_id")
	 private PricingRule pricingRule;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}

	public ItemDetailDays getItemDetailDays() {
		return itemDetailDays;
	}

	public void setItemDetailDays(ItemDetailDays itemDetailDays) {
		this.itemDetailDays = itemDetailDays;
	}

	public PricingRule getPricingRule() {
		return pricingRule;
	}

	public void setPricingRule(PricingRule pricingRule) {
		this.pricingRule = pricingRule;
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
