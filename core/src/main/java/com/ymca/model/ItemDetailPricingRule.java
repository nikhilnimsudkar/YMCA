package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This class represents the basic "Membership" object in YMCA Portal that allows for Membership management.  
 *
 */

@Entity
@Table(name = "item_detail_pricing_rule")
@XmlRootElement
public class ItemDetailPricingRule extends BaseObject implements Serializable {
    private static final long serialVersionUID = 3832626162173359411L;
    
    public ItemDetailPricingRule() {
		
	}
    
	@Id
	@Column(name="Id")
	private Long id;					// required  PK
    
    @Column(name = "RecordName")
    private String recordName;                
    
    @ManyToOne
	@JoinColumn(name="item_detail_id")
	private ItemDetail itemDetail;

    
    @ManyToOne
	@JoinColumn(name="pricing_rule_id")
	private PricingRule pricingRule;
    
    @Column(name="IncludedInMinimumPayment_c")
    private String includedInMinimumPayment;
    
    @Column(name="AdditiveOrSubtractive_c")
    private String additiveOrSubtractive;
    
    @Column(name="Status")
    private String status;
    
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

	public PricingRule getPricingRule() {
		return pricingRule;
	}

	public void setPricingRule(PricingRule pricingRule) {
		this.pricingRule = pricingRule;
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
		ItemDetailPricingRule other = (ItemDetailPricingRule) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItemPricingRule [Id=" + id + ", RecordName=" + recordName
				+ ", itemDetail=" + itemDetail + "]";
	}

	public String getIncludedInMinimumPayment() {
		return includedInMinimumPayment;
	}

	public void setIncludedInMinimumPayment(String includedInMinimumPayment) {
		this.includedInMinimumPayment = includedInMinimumPayment;
	}

	public String getAdditiveOrSubtractive() {
		return additiveOrSubtractive;
	}

	public void setAdditiveOrSubtractive(String additiveOrSubtractive) {
		this.additiveOrSubtractive = additiveOrSubtractive;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
