package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "invoice")
public class Invoice extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)   
	@Column(name ="InvoiceId")
    private Long invoiceId;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="accountId")
	private Account customer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="contact_id")
	private User contact;	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="signupId")
	private Signup signup;

	@Column(name = "InvoiceNumber")
   	private String invoiceNumber;                    	
    
	/*@Column(name = "Contact")
    private String contact;     */           
    
   /* @Column(name = "Customer")
    private String customer;*/
    
    @Column(name = "ContactName")
    private String contactName;
    
    @Column(name = "OrderDate")
    private String orderDate;
    
    @Column(name = "DueDate")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    
    @Column(name = "amount")
	private double amount;
    
    private double balance;
    
    @Column(name = "InvoiceDate")
    @Temporal(TemporalType.DATE)
    private Date invoiceDate;
    
    @Column(name = "BillDate")
    @Temporal(TemporalType.DATE)
    private Date billDate;
    
    @Column(name="FAamount_c")
	private Double FAamount;
    
    private Calendar lastUpdated;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payerId")
    private Payer payer;
    
    @Column(name = "DraftDate_c")
    private Date draftDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="paymentmethodId")
	private PaymentMethod paymentMethod;
    
    @Column(name = "PaymentMode_c")
    private String paymentMode;
    
    public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Signup getSignup() {
		return signup;
	}

	public void setSignup(Signup signup) {
		this.signup = signup;
	}
	
	/*public String getOrderNumber() {
		return invoiceNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.invoiceNumber = orderNumber;
	}*/

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public Account getCustomer() {
		return customer;
	}

	public void setCustomer(Account customer) {
		this.customer = customer;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

    
    public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Double getFAamount() {
		return FAamount;
	}

	public void setFAamount(Double fAamount) {
		FAamount = fAamount;
	}

	public Payer getPayer() {
		return payer;
	}

	public void setPayer(Payer payer) {
		this.payer = payer;
	}

	@OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinColumn(name = "InvoiceId")
    private List<Payment> payment ;
    
	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((invoiceId == null) ? 0 : invoiceId.hashCode());
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
		Invoice other = (Invoice) obj;
		if (invoiceId == null) {
			if (other.invoiceId != null)
				return false;
		} else if (!invoiceId.equals(other.invoiceId))
			return false;
		return true;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getDraftDate() {
		return draftDate;
	}

	public void setDraftDate(Date draftDate) {
		this.draftDate = draftDate;
	}

	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
}
