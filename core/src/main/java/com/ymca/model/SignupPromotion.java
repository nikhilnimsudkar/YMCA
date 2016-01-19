package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "signup_promotion")
@XmlRootElement
public class SignupPromotion extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne  
	@JoinColumn(name = "promotion_id")  
    private ItemDetail promotion;
	
	@Column(name="PromoCode_c")
	private String promoCode;
	
	@ManyToOne  
	@JoinColumn(name = "signup_id")  
	private Signup signup;
	
	/*@Column(name="DiscountPercent_c")
	private String discountPercent;*/
	
	@Column(name="DiscountAmount_c")
	private Double discountAmount;

	@Column(name="OverrideBy_c")
	private String overrideBy;

	@Column(name="OverrideDate_c")
	private Calendar overrideDate;

	@Column(name="OverrideReason_c")
	private String overrideReason;

	@Column(name = "MonthlyDiscountAmount_c")
	private Double monthlyDiscountAmount;
	
	@Column(name = "RemainingDiscountAmount_c")
	private Double remainingDiscountAmount;
	
	public Signup getSignup() {
		return signup;
	}

	public void setSignup(Signup signup) {
		this.signup = signup;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getOverrideBy() {
		return overrideBy;
	}

	public void setOverrideBy(String overrideBy) {
		this.overrideBy = overrideBy;
	}

	public Calendar getOverrideDate() {
		return overrideDate;
	}

	public void setOverrideDate(Calendar overrideDate) {
		this.overrideDate = overrideDate;
	}

	public String getOverrideReason() {
		return overrideReason;
	}

	public void setOverrideReason(String overrideReason) {
		this.overrideReason = overrideReason;
	}

	public ItemDetail getPromotion() {
		return promotion;
	}

	public void setPromotion(ItemDetail promotion) {
		this.promotion = promotion;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getMonthlyDiscountAmount() {
		return monthlyDiscountAmount;
	}

	public void setMonthlyDiscountAmount(Double monthlyDiscountAmount) {
		this.monthlyDiscountAmount = monthlyDiscountAmount;
	}

	public Double getRemainingDiscountAmount() {
		return remainingDiscountAmount;
	}

	public void setRemainingDiscountAmount(Double remainingDiscountAmount) {
		this.remainingDiscountAmount = remainingDiscountAmount;
	}

}
