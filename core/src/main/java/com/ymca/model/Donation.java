package com.ymca.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;




/**
 * This class represents the basic "Donation" object in YMCA Portal that allows for Payment process management.  
 */
@Entity
@Table(name = "donation")
public class Donation extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7381613385407423268L;
	public static final Comparator<Donation> DATE_ORDER_DEC_COMPARATOR = new DateOrderComparator();

	public Donation(){
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long donationId;							// required  PK

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private User contact;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="payment_method_id")
	private PaymentMethod paymentMethod;
	
    @Column(name = "donation_number")
    private Long donationNumber;
    
    @Column(name = "donation_amount")
    private Double donationAmount;
    
    @Transient
    private String donationAmtStr;
    
    @Transient
    private String donationAmtString;
    
    @Column(name = "donation_date")
    private Date donationDate;                
    
    @Column(name = "type")
    private String type;    
   
    @Column(name = "donation_frequency")
    private String donationFrequency;
    
    @Transient
    private String donationFrequencyStr;
    
    @Column(name = "donation_branch")
    private String donationBranch;
    
    @Column(name = "ymca_campaigner_name")
    private String ymcaCampaignerName;
    
    @Column(name = "donation_gift_chk")
    private boolean donationGiftChk;    
    
    @Column(name = "doner_title")
    private String donerTitle;
    
    @Column(name = "doner_first_name")
    private String donerFirstName;
    
    @Column(name = "doner_last_name")
    private String donerLastName;
    
    @Column(name = "address_line1")
    private String addressLine1;
    
    @Column(name = "address_line2")
    private String addressLine2;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "state")
    private String state;
    
    @Column(name = "zipcode")
    private String zipcode;
    
    @Column(name = "contry")
    private String contry;
    
    @Column(name = "preferred_phone")
    private String preferredPhone;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "add_email_lst_check")
    private String addEmailLstCheck;
    
    @Column(name = "card_type")
    private String cardType;
    
    @Transient
    private String cardTypeStr;
    
    @Column(name = "card_number")
    private String cardNumber;
    
    @Column(name = "csc_number")
    private String cscNumber;
    
    @Column(name = "exp_month")
    private String expMonth;
    
    @Column(name = "exp_year")
    private String expYear;
    
    @Column(name = "name_on_card")
    private String nameOnCard;
    

	/**
	 * @return the donationId
	 */
	public Long getDonationId() {
		return donationId;
	}

	/**
	 * @param donationId the donationId to set
	 */
	public void setDonationId(Long donationId) {
		this.donationId = donationId;
	}

	/**
	 * @return the donationNumber
	 */
	public Long getDonationNumber() {
		return donationNumber;
	}

	/**
	 * @param donationNumber the donationNumber to set
	 */
	public void setDonationNumber(Long donationNumber) {
		this.donationNumber = donationNumber;
	}

	/**
	 * @return the donationAmount
	 */
	public Double getDonationAmount() {
		return donationAmount;
	}

	/**
	 * @param donationAmount the donationAmount to set
	 */
	public void setDonationAmount(Double donationAmount) {
		this.donationAmount = donationAmount;
	}


	/**
	 * @return the donationDate
	 */
	public Date getDonationDate() {
		return donationDate;
	}

	/**
	 * @param donationDate the donationDate to set
	 */
	public void setDonationDate(Date donationDate) {
		this.donationDate = donationDate;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getDonationFrequency() {
		return donationFrequency;
	}

	public void setDonationFrequency(String donationFrequency) {
		this.donationFrequency = donationFrequency;
	}

	public String getDonationBranch() {
		return donationBranch;
	}

	public void setDonationBranch(String donationBranch) {
		this.donationBranch = donationBranch;
	}

	public String getYmcaCampaignerName() {
		return ymcaCampaignerName;
	}

	public void setYmcaCampaignerName(String ymcaCampaignerName) {
		this.ymcaCampaignerName = ymcaCampaignerName;
	}

	public boolean isDonationGiftChk() {
		return donationGiftChk;
	}

	public void setDonationGiftChk(boolean donationGiftChk) {
		this.donationGiftChk = donationGiftChk;
	}

	public String getDonerTitle() {
		return donerTitle;
	}

	public void setDonerTitle(String donerTitle) {
		this.donerTitle = donerTitle;
	}

	public String getDonerFirstName() {
		return donerFirstName;
	}

	public void setDonerFirstName(String donerFirstName) {
		this.donerFirstName = donerFirstName;
	}

	public String getDonerLastName() {
		return donerLastName;
	}

	public void setDonerLastName(String donerLastName) {
		this.donerLastName = donerLastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getContry() {
		return contry;
	}

	public void setContry(String contry) {
		this.contry = contry;
	}

	public String getPreferredPhone() {
		return preferredPhone;
	}

	public void setPreferredPhone(String preferredPhone) {
		this.preferredPhone = preferredPhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddEmailLstCheck() {
		return addEmailLstCheck;
	}

	public void setAddEmailLstCheck(String addEmailLstCheck) {
		this.addEmailLstCheck = addEmailLstCheck;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCscNumber() {
		return cscNumber;
	}

	public void setCscNumber(String cscNumber) {
		this.cscNumber = cscNumber;
	}

	public String getExpMonth() {
		return expMonth;
	}

	public void setExpMonth(String expMonth) {
		this.expMonth = expMonth;
	}

	public String getExpYear() {
		return expYear;
	}

	public void setExpYear(String expYear) {
		this.expYear = expYear;
	}

	public String getNameOnCard() {
		return nameOnCard;
	}

	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
	}
	

	public String getDonationAmtStr() {
		return donationAmtStr;
	}

	public void setDonationAmtStr(String donationAmtStr) {
		this.donationAmtStr = donationAmtStr;
	}

	public String getDonationFrequencyStr() {
		return donationFrequencyStr;
	}

	public void setDonationFrequencyStr(String donationFrequencyStr) {
		this.donationFrequencyStr = donationFrequencyStr;
	}

	public String getCardTypeStr() {
		return cardTypeStr;
	}

	public void setCardTypeStr(String cardTypeStr) {
		this.cardTypeStr = cardTypeStr;
	}

	public String getDonationAmtString() {
		return donationAmtString;
	}

	public void setDonationAmtString(String donationAmtString) {
		this.donationAmtString = donationAmtString;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((donationId == null) ? 0 : donationId.hashCode());
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
		Donation other = (Donation) obj;
		if (donationId == null) {
			if (other.donationId != null)
				return false;
		} else if (!donationId.equals(other.donationId))
			return false;
		return true;
	}
    
	@Override
	public String toString() {
		return "Donation [donationId=" + donationId + ", customer=" + customer
				+ ", contact=" + contact + ", paymentMethod=" + paymentMethod
				+ ", donationNumber=" + donationNumber + ", donationAmount="
				+ donationAmount + ", donationAmtStr=" + donationAmtStr
				+ ", donationAmtString=" + donationAmtString
				+ ", donationDate=" + donationDate + ", type=" + type
				+ ", donationFrequency=" + donationFrequency
				+ ", donationFrequencyStr=" + donationFrequencyStr
				+ ", donationBranch=" + donationBranch
				+ ", ymcaCampaignerName=" + ymcaCampaignerName
				+ ", donationGiftChk=" + donationGiftChk + ", donerTitle="
				+ donerTitle + ", donerFirstName=" + donerFirstName
				+ ", donerLastName=" + donerLastName + ", addressLine1="
				+ addressLine1 + ", addressLine2=" + addressLine2 + ", city="
				+ city + ", state=" + state + ", zipcode=" + zipcode
				+ ", contry=" + contry + ", preferredPhone=" + preferredPhone
				+ ", email=" + email + ", addEmailLstCheck=" + addEmailLstCheck
				+ ", cardType=" + cardType + ", cardTypeStr=" + cardTypeStr
				+ ", cardNumber=" + cardNumber + ", cscNumber=" + cscNumber
				+ ", expMonth=" + expMonth + ", expYear=" + expYear
				+ ", nameOnCard=" + nameOnCard + "]";
	}

	private static final class DateOrderComparator implements Comparator<Donation> {
		public int compare(Donation dn1, Donation dn2) {
			if (dn1.donationDate.before(dn2.donationDate) )return 1;
			if (dn1.donationDate == dn2.donationDate) return 0;
			return -1;
			
		}
	}
    
}
