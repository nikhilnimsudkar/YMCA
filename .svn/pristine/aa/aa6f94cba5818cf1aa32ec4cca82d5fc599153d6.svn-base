package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "signup_pricing_rule")
@XmlRootElement
public class SignupPricingRule extends BaseObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long signupPricingRuleId;
	
	@OneToOne  
	@JoinColumn(name = "pricing_rule_id")  
    private PricingRule pricingRule;
	
	@ManyToOne  
	@JoinColumn(name = "signupId")  
	private Signup signup;
	
	public PricingRule getPricingRule() {
		return pricingRule;
	}

	public void setPricingRule(PricingRule pricingRule) {
		this.pricingRule = pricingRule;
	}

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

}
