package com.ymca.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "promotion")
public class Promotion extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6814557406194920292L;
	
	@Id
    private Long id;
	
	private String promoName;
	private String promoCode;
	private Double discountvalue;
	private BigDecimal discountpercentage;
	private String status;
	private String discounttype;
	private Integer autoApply;
	
	@Column(name = "start_date", columnDefinition="DATE")
	private Date startdate;
	@Column(name = "end_date", columnDefinition="DATE")
	private Date enddate;
	
	@OneToMany(mappedBy = "promotion")
	private List<ItemDetailPromotion> itemPromoDetails;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Double getDiscountvalue() {
		return discountvalue;
	}

	public void setDiscountvalue(Double discountvalue) {
		this.discountvalue = discountvalue;
	}

	public BigDecimal getDiscountpercentage() {
		return discountpercentage;
	}

	public void setDiscountpercentage(BigDecimal discountpercentage) {
		this.discountpercentage = discountpercentage;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDiscounttype() {
		return discounttype;
	}

	public void setDiscounttype(String discounttype) {
		this.discounttype = discounttype;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Integer getAutoApply() {
		return autoApply;
	}

	public void setAutoApply(Integer autoApply) {
		this.autoApply = autoApply;
	}

	public List<ItemDetailPromotion> getItemPromoDetails() {
		return itemPromoDetails;
	}

	public void setItemPromoDetails(List<ItemDetailPromotion> itemPromoDetails) {
		this.itemPromoDetails = itemPromoDetails;
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
