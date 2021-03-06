package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * This class represents the basic "Payment" object in YMCA Portal that allows for Payment process management.  
 */
@Entity
@Table(name = "payment_method")
public class PaymentMethod extends BaseObject implements Serializable {
	private static final long serialVersionUID = -3202944701848397747L;

	public PaymentMethod(){
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "payment_method_type")
   	private String paymentMethodType;                    	
    
    @Column(name = "card_number")
    private String cardNumber;
    
    @Column(name = "expiration_month", length = 2)
    private String expirationMonth;

    @Column(name = "expiration_year", length = 4)
    private String expirationYear;
    
    @Column(name = "full_name")
    private String fullName;
    
    @Column(name = "billing_address_line1")
    private String billingAddressLine1;                
    
    @Column(name = "billing_address_line2")
    private String billingAddressLine2;
    
    @Column(name = "billing_city")
    private String billingCity;
    
    @Column(name = "billing_state")
    private String billingState;
    
    @Column(name = "billing_country")
    private String billingCountry;
    
    @Column(name = "is_Primary")
    private Integer isPrimary;

    @Column(name = "status")
    private Integer status;
    
    @Column(name = "security_code")
    private String securityCode;
    
    @Column(name = "billing_zip")
    private String billingZip;
    
    @Column(name = "nick_name")
    private String nickName;
    
    @Column(name = "bank_routing_number")
    private String bankRoutingNumber;
    
    @Column(name = "checking_account_number")
    private String checkingAccountNumber;
    
    @Column(name = "drivers_license_number")
    private String driversLicenseNumber;
    
    @Column(name = "state_of_dl")
    private String stateOfDL;
    
    @Column(name = "order_number")
    private String orderNumber;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "token_number")
    private String tokenNumber;
    
    @Column(name = "check_number")
    private String checkNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
    
    @Column(name = "portal_status")
    private String portalStatus;  
    
    @Column(name = "card_type")
    private String cardType;  
    
	@Column(name = "trans_id")
    private String transId;    
    
    @Column(name = "oldToken")
    private String oldToken;

    @Column(name = "CCType_c")
    private String cCType_c;
    
    @Column(name = "JetPayStatus_c")
    private String jetPayStatus_c;
    
    @Column(name = "JetPayErrorMessage_c")
    private String jetPayErrorMessage_c;
    
    @Column(name = "FinanceStatus_c")
    private String financeStatus_c;
    
    private Calendar lastUpdated;
    
    @Column(name = "BankAccountType_c")
    private String bankAccountType;
    
    @Transient
	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})    
    private List<Invoice> invoices;
	/**
	 * @return the cardNumber
	 */
	public String getCardNumber() {
		return cardNumber;
	}

	/**
	 * @param cardNumber the cardNumber to set
	 */
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the billingAddressLine1
	 */
	public String getBillingAddressLine1() {
		return billingAddressLine1;
	}

	/**
	 * @param billingAddressLine1 the billingAddressLine1 to set
	 */
	public void setBillingAddressLine1(String billingAddressLine1) {
		this.billingAddressLine1 = billingAddressLine1;
	}

	/**
	 * @return the billingAddressLine2
	 */
	public String getBillingAddressLine2() {
		return billingAddressLine2;
	}

	/**
	 * @param billingAddressLine2 the billingAddressLine2 to set
	 */
	public void setBillingAddressLine2(String billingAddressLine2) {
		this.billingAddressLine2 = billingAddressLine2;
	}

	/**
	 * @return the billingCity
	 */
	public String getBillingCity() {
		return billingCity;
	}

	/**
	 * @param billingCity the billingCity to set
	 */
	public void setBillingCity(String billingCity) {
		this.billingCity = billingCity;
	}

	/**
	 * @return the billingState
	 */
	public String getBillingState() {
		return billingState;
	}

	/**
	 * @param billingState the billingState to set
	 */
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}

	/**
	 * @return the billingCountry
	 */
	public String getBillingCountry() {
		return billingCountry;
	}

	/**
	 * @param billingCountry the billingCountry to set
	 */
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}

	/**
	 * @return the isPrimary
	 */
	public Integer getIsPrimary() {
		return isPrimary;
	}

	/**
	 * @param isPrimary the isPrimary to set
	 */
	public void setIsPrimary(Integer isPrimary) {
		this.isPrimary = isPrimary;
	}

	/**
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getSecurityCode() {
		return securityCode;
	}

	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}

	public String getBillingZip() {
		return billingZip;
	}

	public void setBillingZip(String billingZip) {
		this.billingZip = billingZip;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getBankRoutingNumber() {
		return bankRoutingNumber;
	}

	public void setBankRoutingNumber(String bankRoutingNumber) {
		this.bankRoutingNumber = bankRoutingNumber;
	}

	public String getCheckingAccountNumber() {
		return checkingAccountNumber;
	}

	public void setCheckingAccountNumber(String checkingAccountNumber) {
		this.checkingAccountNumber = checkingAccountNumber;
	}

	public String getDriversLicenseNumber() {
		return driversLicenseNumber;
	}

	public void setDriversLicenseNumber(String driversLicenseNumber) {
		this.driversLicenseNumber = driversLicenseNumber;
	}

	public String getStateOfDL() {
		return stateOfDL;
	}

	public void setStateOfDL(String stateOfDL) {
		this.stateOfDL = stateOfDL;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getTokenNumber() {
		return tokenNumber;
	}

	public void setTokenNumber(String tokenNumber) {
		this.tokenNumber = tokenNumber;
	}

	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	public String getPortalStatus() {
		return portalStatus;
	}

	public void setPortalStatus(String portalStatus) {
		this.portalStatus = portalStatus;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getTransId() {
		return transId;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public String getOldToken() {
		return oldToken;
	}

	public void setOldToken(String oldToken) {
		this.oldToken = oldToken;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
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
		PaymentMethod other = (PaymentMethod) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "PaymentMethod [paymentId=" + id + ", paymentType="
				+ paymentMethodType + ", cardNumber=" + cardNumber
				+ ", expirationMonth=" + expirationMonth + ", expirationYear="
				+ expirationYear + ", fullName=" + fullName
				+ ", billingAddressLine1=" + billingAddressLine1
				+ ", billingAddressLine2=" + billingAddressLine2
				+ ", billingCity=" + billingCity + ", billingState="
				+ billingState + ", billingCountry=" + billingCountry
				+ ", isPrimary=" + isPrimary + ", status=" + status
				+ ", securityCode=" + securityCode + ", billingZip="
				+ billingZip + ", nickName=" + nickName
				+ ", bankRoutingNumber=" + bankRoutingNumber
				+ ", checkingAccountNumber=" + checkingAccountNumber
				+ ", driversLicenseNumber=" + driversLicenseNumber
				+ ", stateOfDL=" + stateOfDL + ", phoneNumber=" + phoneNumber
				+ ", tokenNumber=" + tokenNumber + ", customer=" + customer
				+ ", portalStatus=" + portalStatus + ", cardType=" + cardType
				+ ", transId=" + transId + ", ]";
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(String expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(String expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getCheckNumber() {
		return checkNumber;
	}

	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(String bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPaymentMethodType() {
		return paymentMethodType;
	}

	public void setPaymentMethodType(String paymentMethodType) {
		this.paymentMethodType = paymentMethodType;
	}

	public List<Invoice> getInvoices() {
		return invoices;
	}

	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}

	
}
