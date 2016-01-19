package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@Table(name = "jetpay_payment")
@Table(name = "payment_audit_log")
public class JetPayPayment extends BaseObject implements Serializable {
	private static final long serialVersionUID = 2752909403141879269L;

	public JetPayPayment(){
    }
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long paymentId;
	
	private String transId;
	private String jpReturnHash;
	private String responseText;
	private String cid;
	private String name;
	private String cardNum;
	private String card;
	private String feeAmount;
	private String actCode;
	private String apprCode;
	private String cvvMatch;
	private String addressMatch;
	private String zipMatch;
	private String avsMatch;
	private String ccToken;
	private String oldToken;
	private String customerEmail;
	private String orderNumber;
	private String billingAddress1;
	private String billingAddress2;
	private String billingCity;
	private String billingState;
	private String billingZip;
	private String billingCountry;
	
	private String bankAccountNumber;
	private String bankRoutingNumber;
	private String checkNumber;
	private String paymentType;
	private String errMsg;
	
	@Column(name = "amount", nullable = false)
	private double amount;

	public String getJpReturnHash() {
		return jpReturnHash;
	}

	public void setJpReturnHash(String jpReturnHash) {
		this.jpReturnHash = jpReturnHash;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getActCode() {
		return actCode;
	}

	public void setActCode(String actCode) {
		this.actCode = actCode;
	}

	public String getApprCode() {
		return apprCode;
	}

	public void setApprCode(String apprCode) {
		this.apprCode = apprCode;
	}

	public String getCvvMatch() {
		return cvvMatch;
	}

	public void setCvvMatch(String cvvMatch) {
		this.cvvMatch = cvvMatch;
	}

	public String getAddressMatch() {
		return addressMatch;
	}

	public void setAddressMatch(String addressMatch) {
		this.addressMatch = addressMatch;
	}

	public String getZipMatch() {
		return zipMatch;
	}

	public void setZipMatch(String zipMatch) {
		this.zipMatch = zipMatch;
	}

	public String getAvsMatch() {
		return avsMatch;
	}

	public void setAvsMatch(String avsMatch) {
		this.avsMatch = avsMatch;
	}

	public String getCcToken() {
		return ccToken;
	}

	public void setCcToken(String ccToken) {
		this.ccToken = ccToken;
	}

	public String getOldToken() {
		return oldToken;
	}

	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	public String getBillingCity() {
		return billingCity;
	}

	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	public String getBillingState() {
		return billingState;
	}

	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public String getBillingCountry() {
		return billingCountry;
	}

	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}

	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actCode == null) ? 0 : actCode.hashCode());
		result = prime * result
				+ ((addressMatch == null) ? 0 : addressMatch.hashCode());
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((apprCode == null) ? 0 : apprCode.hashCode());
		result = prime * result
				+ ((avsMatch == null) ? 0 : avsMatch.hashCode());
		result = prime
				* result
				+ ((bankAccountNumber == null) ? 0 : bankAccountNumber
						.hashCode());
		result = prime
				* result
				+ ((bankRoutingNumber == null) ? 0 : bankRoutingNumber
						.hashCode());
		result = prime * result
				+ ((billingAddress1 == null) ? 0 : billingAddress1.hashCode());
		result = prime * result
				+ ((billingAddress2 == null) ? 0 : billingAddress2.hashCode());
		result = prime * result
				+ ((billingCity == null) ? 0 : billingCity.hashCode());
		result = prime * result
				+ ((billingCountry == null) ? 0 : billingCountry.hashCode());
		result = prime * result
				+ ((billingState == null) ? 0 : billingState.hashCode());
		result = prime * result
				+ ((billingZip == null) ? 0 : billingZip.hashCode());
		result = prime * result + ((card == null) ? 0 : card.hashCode());
		result = prime * result + ((cardNum == null) ? 0 : cardNum.hashCode());
		result = prime * result + ((ccToken == null) ? 0 : ccToken.hashCode());
		result = prime * result
				+ ((checkNumber == null) ? 0 : checkNumber.hashCode());
		result = prime * result + ((cid == null) ? 0 : cid.hashCode());
		result = prime * result
				+ ((customerEmail == null) ? 0 : customerEmail.hashCode());
		result = prime * result
				+ ((cvvMatch == null) ? 0 : cvvMatch.hashCode());
		result = prime * result
				+ ((feeAmount == null) ? 0 : feeAmount.hashCode());
		result = prime * result
				+ ((jpReturnHash == null) ? 0 : jpReturnHash.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((oldToken == null) ? 0 : oldToken.hashCode());
		result = prime * result
				+ ((orderNumber == null) ? 0 : orderNumber.hashCode());
		result = prime * result
				+ ((paymentId == null) ? 0 : paymentId.hashCode());
		result = prime * result
				+ ((paymentType == null) ? 0 : paymentType.hashCode());
		result = prime * result
				+ ((responseText == null) ? 0 : responseText.hashCode());
		result = prime * result + ((transId == null) ? 0 : transId.hashCode());
		result = prime * result
				+ ((zipMatch == null) ? 0 : zipMatch.hashCode());
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
		JetPayPayment other = (JetPayPayment) obj;
		if (actCode == null) {
			if (other.actCode != null)
				return false;
		} else if (!actCode.equals(other.actCode))
			return false;
		if (addressMatch == null) {
			if (other.addressMatch != null)
				return false;
		} else if (!addressMatch.equals(other.addressMatch))
			return false;
		if (Double.doubleToLongBits(amount) != Double
				.doubleToLongBits(other.amount))
			return false;
		if (apprCode == null) {
			if (other.apprCode != null)
				return false;
		} else if (!apprCode.equals(other.apprCode))
			return false;
		if (avsMatch == null) {
			if (other.avsMatch != null)
				return false;
		} else if (!avsMatch.equals(other.avsMatch))
			return false;
		if (bankAccountNumber == null) {
			if (other.bankAccountNumber != null)
				return false;
		} else if (!bankAccountNumber.equals(other.bankAccountNumber))
			return false;
		if (bankRoutingNumber == null) {
			if (other.bankRoutingNumber != null)
				return false;
		} else if (!bankRoutingNumber.equals(other.bankRoutingNumber))
			return false;
		if (billingAddress1 == null) {
			if (other.billingAddress1 != null)
				return false;
		} else if (!billingAddress1.equals(other.billingAddress1))
			return false;
		if (billingAddress2 == null) {
			if (other.billingAddress2 != null)
				return false;
		} else if (!billingAddress2.equals(other.billingAddress2))
			return false;
		if (billingCity == null) {
			if (other.billingCity != null)
				return false;
		} else if (!billingCity.equals(other.billingCity))
			return false;
		if (billingCountry == null) {
			if (other.billingCountry != null)
				return false;
		} else if (!billingCountry.equals(other.billingCountry))
			return false;
		if (billingState == null) {
			if (other.billingState != null)
				return false;
		} else if (!billingState.equals(other.billingState))
			return false;
		if (billingZip == null) {
			if (other.billingZip != null)
				return false;
		} else if (!billingZip.equals(other.billingZip))
			return false;
		if (card == null) {
			if (other.card != null)
				return false;
		} else if (!card.equals(other.card))
			return false;
		if (cardNum == null) {
			if (other.cardNum != null)
				return false;
		} else if (!cardNum.equals(other.cardNum))
			return false;
		if (ccToken == null) {
			if (other.ccToken != null)
				return false;
		} else if (!ccToken.equals(other.ccToken))
			return false;
		if (checkNumber == null) {
			if (other.checkNumber != null)
				return false;
		} else if (!checkNumber.equals(other.checkNumber))
			return false;
		if (cid == null) {
			if (other.cid != null)
				return false;
		} else if (!cid.equals(other.cid))
			return false;
		if (customerEmail == null) {
			if (other.customerEmail != null)
				return false;
		} else if (!customerEmail.equals(other.customerEmail))
			return false;
		if (cvvMatch == null) {
			if (other.cvvMatch != null)
				return false;
		} else if (!cvvMatch.equals(other.cvvMatch))
			return false;
		if (feeAmount == null) {
			if (other.feeAmount != null)
				return false;
		} else if (!feeAmount.equals(other.feeAmount))
			return false;
		if (jpReturnHash == null) {
			if (other.jpReturnHash != null)
				return false;
		} else if (!jpReturnHash.equals(other.jpReturnHash))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (oldToken == null) {
			if (other.oldToken != null)
				return false;
		} else if (!oldToken.equals(other.oldToken))
			return false;
		if (orderNumber == null) {
			if (other.orderNumber != null)
				return false;
		} else if (!orderNumber.equals(other.orderNumber))
			return false;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		if (paymentType == null) {
			if (other.paymentType != null)
				return false;
		} else if (!paymentType.equals(other.paymentType))
			return false;
		if (responseText == null) {
			if (other.responseText != null)
				return false;
		} else if (!responseText.equals(other.responseText))
			return false;
		if (transId == null) {
			if (other.transId != null)
				return false;
		} else if (!transId.equals(other.transId))
			return false;
		if (zipMatch == null) {
			if (other.zipMatch != null)
				return false;
		} else if (!zipMatch.equals(other.zipMatch))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JetPayPayment [paymentId=" + paymentId + ", transId=" + transId
				+ ", jpReturnHash=" + jpReturnHash + ", responseText="
				+ responseText + ", cid=" + cid + ", name=" + name
				+ ", cardNum=" + cardNum + ", card=" + card + ", feeAmount="
				+ feeAmount + ", actCode=" + actCode + ", apprCode=" + apprCode
				+ ", cvvMatch=" + cvvMatch + ", addressMatch=" + addressMatch
				+ ", zipMatch=" + zipMatch + ", avsMatch=" + avsMatch
				+ ", ccToken=" + ccToken + ", oldToken=" + oldToken
				+ ", customerEmail=" + customerEmail + ", orderNumber="
				+ orderNumber + ", billingAddress1=" + billingAddress1
				+ ", billingAddress2=" + billingAddress2 + ", billingCity="
				+ billingCity + ", billingState=" + billingState
				+ ", billingZip=" + billingZip + ", billingCountry="
				+ billingCountry + ", bankAccountNumber=" + bankAccountNumber
				+ ", bankRoutingNumber=" + bankRoutingNumber + ", checkNumber="
				+ checkNumber + ", paymentType=" + paymentType + ", amount="
				+ amount + "]";
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
