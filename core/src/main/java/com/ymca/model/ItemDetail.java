package com.ymca.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "item_detail") 
public class ItemDetail extends BaseObject implements Serializable {
private static final long serialVersionUID = 1L;
	
	public ItemDetail(){
		
	}	
	
	@Id
	@Column(name="Id")
	public Long id;
	
	@Column(name = "RecordName", nullable = false)
	private String recordName;

	@Column(name = "Duration_c")
	private String duration;

	@Column(name = "ClassID_c", nullable = false)
	private String classID;

	@Column(name = "Description_c", nullable = false)
	private String description;
	
	@Column(name = "StartTime_c", columnDefinition="TIME")
	private Date startTime;
	
	@Column(name = "EndTime_c", columnDefinition="TIME")
	private Date endTime;

	@Column(name="locationId",insertable=false,updatable=false)
	private Long locationId;	

	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="locationId")
	private Location location;	

	@Column(name = "Gender_c")
	private String gender;
	
	@Column(name = "MinAge_c")
	private Integer minAge;
	
	@Column(name = "MaxAge_c")
	private Integer maxAge;

	@Column(name = "Type_c")
	private String type;
	
	@Column(name = "Category_c")
	private String category;

/*	@Column(name = "Type_c")
	private String subType;*/
	
	@Column(name = "subCategory_c")
	private String subCategory;
	
	@Column(name = "ActualCapacity_c")
	private Long actualCapacity;

	@Column(name = "WebCapacity_c")
	private Long webCapacity;
	
	@Column(name = "WebRemainingCapacity_c")
	private Long remainingCapacity;
	
	@Column(name = "actualRemainingCapacity_c")
	private Long actualRemainingCapacity;
	
	@Column(name = "WaitlistCounter_c")
	private Long waitlistCounter;

	@Column(name = "FinancialAid_c")
	private String financialAid;

	@Column(name = "BillingRules_c")
	private String billingRules;

	@Column(name = "PaymentPlan_c")
	private String paymentPlan;

	@Column(name = "AutomaticPayment_c")
	private String automaticPayment;
	
	@Column(name = "DueDateOffset_c")
	private Integer dueDateOffset;

	/*@Column(name = "SchoolDaysPerYear_c")
	private Integer schoolDaysPerYear;*/

	@Column(name = "AutomatedWaitlist_c")
	private String automatedWaitlist;

	@Column(name = "CancellationCutOffPeriod_c")
	private Integer cancellationCutOffPeriod;
	
	//@Temporal(TemporalType.DATE)
	@Column(name = "MemberRegistrationStartDate_c")
	private Date memberRegistrationStartDate;
	
	//@Temporal(TemporalType.DATE)
	@Column(name = "NonMemberRegistrationStartDate_c")
	private Date nonMemberRegistrationStartDate;

	//@Temporal(TemporalType.DATE)
	@Column(name = "MemberRegistrationEndDate_c")
	private Date memberRegistrationEndDate;

	//@Temporal(TemporalType.DATE)
	@Column(name = "NonMemberRegistrationEndDate_c")
	private Date nonMemberRegistrationEndDate;

	@Column(name = "PreRequisities_c")
	private String preRequisities;

	@Column(name = "RecurringPeriod_c")
	private Integer recurringPeriod;
	
	@Column(name = "Status_c")
	private String status;
	
	@Column(name = "PackageSize_c")
	private Integer packageSize;

	@Column(name = "MaxAdultsPerMembership_c")
	private Integer maxAdultsPerMembership;

	@Column(name = "MaxHoldPeriod_c")
	private Integer maxHoldPeriod;

	@Column(name = "MinHoldPeriod_c")
	private Integer minHoldPeriod;

	@Column(name = "ProgramDirector_c")
	private String programDirector;
	
	/*@Column(name = "FacilityManager_c")
	private String facilityManager;*/

	@Column(name = "DonationManager_c")
	private String donationManager;

	@Column(name = "LodgingType_c")
	private String lodgingType;

	@Column(name = "TransportationLocation_c")
	private String transportationLocation;

	@Column(name = "Direction_c")
	private String direction;
	
	@Column(name = "FAEligible_c")
	private String fAEligible;

	@OneToOne
	@JoinColumn(name="waivers_and_tc_id_FK")
	private WaiversAndTC waiversAndTC;
	
	/*@Column(name = "price", nullable = false)
	private Double price;*/

	/*@Column(name = "registrationPrice", nullable = false)
	private Double registrationPrice;*/
	
	@Column(name = "StartDate_c", columnDefinition="DATE")
	private Date startDate;
	
	@Column(name = "EndDate_c", columnDefinition="DATE")
	private Date endDate;
	
	@Column(name="SchoolDaysPerYear_c")
	private Integer noofdaysinschoolyear;
	
	@Column(name="SchoolMonthsPerYear_c")
	private Integer noofmonthsinschoolyear;
	
	@Column(name="RequiresSetUpFee")
	private String requiresSetUpFee;
	
	@Column(name="RequiresRegistrationFee")
	private Boolean requiresRegistrationFee;
	
	@Column(name="NeedsHealthHistory_c")
	private String needsHealthHistory;
	
	@Column(name="RequiresEmergencyContact_c")
	private String requiresEmergencyContact;
	
	@Column(name="RequiresAuthorisedContact_c")
	private String requiresAuthorisedContact;
	
	@Column(name="FutureCancellationAllowed_c")
	private String futureCancellationAllowed;
	
	@Column(name="allowToPickStartDate_c", columnDefinition="varchar(50) default 'No'")
	private String allowToPickStartDate;
	
	@Column(name="allowToPickGrade_c")
	private String allowToPickGrade;
	
	@Column(name="NeedTC_c")
	private String needTC_c;
	
	@Column(name="PreventDuplicateSignup_c")
	private String preventDuplicateSignup;
	
	@Column(name = "DueDate_c")
	private String dueDate;
	
	@Column(name="BillDateOffset_c")
	private Integer billDateOffset;
	
	@Column(name="AllowMinimumPaymentBasedOn_c")
	private String allowMinimumPaymentBasedOn;
	
	@Column(name="BillDate_c")
	private String billDate;
	
	@Column(name="StopConfirmedSignUps_c")
	private String stopConfirmedSignUps;
	
	/*
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	private Product product;*/
	
	/*
	@OneToMany(mappedBy = "itemDetails")
	private List<ItemDetailsSession> itemDetailsSession;
	*/
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="itemDetail")
	private List<ItemDetailDays> itemDays;
	
	@ManyToMany
    @JoinTable(name="item_detail_pricing_rule", 
    joinColumns=@JoinColumn(name="item_detail_id"),
    inverseJoinColumns=@JoinColumn(name="pricing_rule_id"))
	private List<PricingRule> pricingRules;
	
	@OneToMany(mappedBy = "associatedItemDetail")
	private List<ItemDetailAssociatedItemDetail> associatedItemDetails;
	
	@OneToMany(mappedBy = "itemDetailPromotion")
	private List<ItemDetailPromotion> itemDetailPromotion;
	
	@OneToMany(mappedBy = "itemDetail")
	private List<ContactPromotion> contactPromotion;
	
	@Column(name="PromotionRuleType_c")
	private String promotionRuleType;
	
	@Column(name="AutoPromotion_c")
	private String autoPromotion;
	
	private Calendar lastUpdated;
	
	/*@Column(name="AssignTo_c")
	private String assignTo;*/
	
	@Column(name="PromoCode_c")
	private String promoCode;
	
	@Column(name="PromotionType_c")
	private String promotionType;
	
	@Column(name="PromotionDiscountValue_c")
	private String promotionDiscountValue;
	
	@Column(name="PromoApplicableToAllContacts_c")
	private String promoApplicableToAllContacts;
	
	@Column(name="PromotionExpression_c")
	private String promotionExpression;

	@Column(name="PromotionExpressionOperator_c")
	private String promotionExpressionOperator;
	
	@Transient
	private Date planEndDate;
	
	@Column(name="GenerateRecurringInvoicesAtTimeOfSignUp_c")
	private String generateRecurringInvoicesAtTimeOfSignUp;
	
	/*@Column(name="CustomerSpecialInstruction_c")
	private String customerSpecialInstruction;
	
	@Column(name="YAgentSpecialInstruction_c")
	private String yAgentSpecialInstruction;*/
	
	/*public String getCustomerSpecialInstruction() {
		return this.customerSpecialInstruction;
	}

	public void setCustomerSpecialInstruction(String customerSpecialInstruction) {
		this.customerSpecialInstruction = customerSpecialInstruction;
	}

	public String getyAgentSpecialInstruction() {
		return this.yAgentSpecialInstruction;
	}

	public void setyAgentSpecialInstruction(String yAgentSpecialInstruction) {
		this.yAgentSpecialInstruction = yAgentSpecialInstruction;
	}*/
	@Column(name="SpecialInstruction_c")
	private String specialInstruction;
	
	@Column(name = "SpecialAgentCapacity_c")
	private Long specialAgentCapacity;
	
	@Column(name = "SpecialAgentRemainingCapacity_c")
	private Long specialAgentRemainingCapacity;
	
	public String getSpecialInstruction() {
		return this.specialInstruction;
	}



	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}



	public Boolean getRequiresRegistrationFee() {
		return requiresRegistrationFee;
	}
	
	
	
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

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getMinAge() {
		return minAge;
	}

	public void setMinAge(Integer minAge) {
		this.minAge = minAge;
	}

	public Integer getMaxAge() {
		return maxAge;
	}

	public void setMaxAge(Integer maxAge) {
		this.maxAge = maxAge;
	}

	public String getType() {
		return type;
	}

	public Long getActualCapacity() {
		return actualCapacity;
	}

	public void setActualCapacity(Long actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public Long getWebCapacity() {
		return webCapacity;
	}

	public void setWebCapacity(Long webCapacity) {
		this.webCapacity = webCapacity;
	}

	public Long getRemainingCapacity() {
		return remainingCapacity;
	}

	public void setRemainingCapacity(Long remainingCapacity) {
		this.remainingCapacity = remainingCapacity;
	}

	public Long getWaitlistCounter() {
		return waitlistCounter;
	}

	public void setWaitlistCounter(Long waitlistCounter) {
		this.waitlistCounter = waitlistCounter;
	}

	public String getFinancialAid() {
		return financialAid;
	}

	public void setFinancialAid(String financialAid) {
		this.financialAid = financialAid;
	}

	public String getBillingRules() {
		return billingRules;
	}

	public void setBillingRules(String billingRules) {
		this.billingRules = billingRules;
	}

	public String getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public String getAutomaticPayment() {
		return automaticPayment;
	}

	public void setAutomaticPayment(String automaticPayment) {
		this.automaticPayment = automaticPayment;
	}

	public Integer getDueDateOffset() {
		return dueDateOffset;
	}

	public void setDueDateOffset(Integer dueDateOffset) {
		this.dueDateOffset = dueDateOffset;
	}

	/*public Integer getSchoolDaysPerYear() {
		return schoolDaysPerYear;
	}

	public void setSchoolDaysPerYear(Integer schoolDaysPerYear) {
		this.schoolDaysPerYear = schoolDaysPerYear;
	}*/

	public Date getMemberRegistrationStartDate() {
		return memberRegistrationStartDate;
	}

	public void setMemberRegistrationStartDate(Date memberRegistrationStartDate) {
		this.memberRegistrationStartDate = memberRegistrationStartDate;
	}

	public Date getNonMemberRegistrationStartDate() {
		return nonMemberRegistrationStartDate;
	}

	public void setNonMemberRegistrationStartDate(
			Date nonMemberRegistrationStartDate) {
		this.nonMemberRegistrationStartDate = nonMemberRegistrationStartDate;
	}

	public Date getMemberRegistrationEndDate() {
		return memberRegistrationEndDate;
	}

	public void setMemberRegistrationEndDate(Date memberRegistrationEndDate) {
		this.memberRegistrationEndDate = memberRegistrationEndDate;
	}

	public Date getNonMemberRegistrationEndDate() {
		return nonMemberRegistrationEndDate;
	}

	public void setNonMemberRegistrationEndDate(
			Date nonMemberRegistrationEndDate) {
		this.nonMemberRegistrationEndDate = nonMemberRegistrationEndDate;
	}

	public Integer getRecurringPeriod() {
		return recurringPeriod;
	}

	public void setRecurringPeriod(Integer recurringPeriod) {
		this.recurringPeriod = recurringPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(Integer packageSize) {
		this.packageSize = packageSize;
	}

	public Integer getMaxAdultsPerMembership() {
		return maxAdultsPerMembership;
	}

	public void setMaxAdultsPerMembership(Integer maxAdultsPerMembership) {
		this.maxAdultsPerMembership = maxAdultsPerMembership;
	}

	public Integer getMaxHoldPeriod() {
		return maxHoldPeriod;
	}

	public void setMaxHoldPeriod(Integer maxHoldPeriod) {
		this.maxHoldPeriod = maxHoldPeriod;
	}

	public Integer getMinHoldPeriod() {
		return minHoldPeriod;
	}

	public void setMinHoldPeriod(Integer minHoldPeriod) {
		this.minHoldPeriod = minHoldPeriod;
	}
/*
	public String getFacilityManager() {
		return facilityManager;
	}

	public void setFacilityManager(String facilityManager) {
		this.facilityManager = facilityManager;
	}*/

	public String getDonationManager() {
		return donationManager;
	}

	public void setDonationManager(String donationManager) {
		this.donationManager = donationManager;
	}

	public String getLodgingType() {
		return lodgingType;
	}

	public void setLodgingType(String lodgingType) {
		this.lodgingType = lodgingType;
	}

	public String getTransportationLocation() {
		return transportationLocation;
	}

	public void setTransportationLocation(String transportationLocation) {
		this.transportationLocation = transportationLocation;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getFAEligible() {
		return fAEligible;
	}

	public void setFAEligible(String fAEligible) {
		this.fAEligible = fAEligible;
	}

	public WaiversAndTC getWaiversAndTC() {
		return waiversAndTC;
	}

	public void setWaiversAndTC(WaiversAndTC waiversAndTC) {
		this.waiversAndTC = waiversAndTC;
	}

	/*public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}*/

	/*public Double getRegistrationPrice() {
		return registrationPrice;
	}

	public void setRegistrationPrice(Double registrationPrice) {
		this.registrationPrice = registrationPrice;
	}*/

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getCancellationCutOffPeriod() {
		return cancellationCutOffPeriod;
	}

	public void setCancellationCutOffPeriod(Integer cancellationCutOffPeriod) {
		this.cancellationCutOffPeriod = cancellationCutOffPeriod;
	}

	public Integer getNoofdaysinschoolyear() {
		return noofdaysinschoolyear;
	}

	public void setNoofdaysinschoolyear(Integer noofdaysinschoolyear) {
		this.noofdaysinschoolyear = noofdaysinschoolyear;
	}

	public Integer getNoofmonthsinschoolyear() {
		return noofmonthsinschoolyear;
	}

	public void setNoofmonthsinschoolyear(Integer noofmonthsinschoolyear) {
		this.noofmonthsinschoolyear = noofmonthsinschoolyear;
	}

	public List<ItemDetailDays> getItemDays() {
		return itemDays;
	}

	public void setItemDays(List<ItemDetailDays> itemDays) {
		this.itemDays = itemDays;
	}

	public List<PricingRule> getPricingRules() {
		return pricingRules;
	}

	public void setPricingRules(List<PricingRule> pricingRules) {
		this.pricingRules = pricingRules;
	}

	
	
	public String getProgramDirector() {
		return programDirector;
	}

	public void setProgramDirector(String programDirector) {
		this.programDirector = programDirector;
	}

	public Boolean isRequiresRegistrationFee() {
		return requiresRegistrationFee;
	}

	public void setRequiresRegistrationFee(Boolean requiresRegistrationFee) {
		this.requiresRegistrationFee = requiresRegistrationFee;
	}

	public Long getActualRemainingCapacity() {
		return actualRemainingCapacity;
	}

	public void setActualRemainingCapacity(Long actualRemainingCapacity) {
		this.actualRemainingCapacity = actualRemainingCapacity;
	}

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
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public Long getLocationId() {
		return locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

	public String getfAEligible() {
		return fAEligible;
	}

	public void setfAEligible(String fAEligible) {
		this.fAEligible = fAEligible;
	}

	public List<ItemDetailAssociatedItemDetail> getAssociatedItemDetails() {
		return associatedItemDetails;
	}

	public void setAssociatedItemDetails(
			List<ItemDetailAssociatedItemDetail> associatedItemDetails) {
		this.associatedItemDetails = associatedItemDetails;
	}

	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getFutureCancellationAllowed() {
		return futureCancellationAllowed;
	}

	public void setFutureCancellationAllowed(String futureCancellationAllowed) {
		this.futureCancellationAllowed = futureCancellationAllowed;
	}

	public String getAllowToPickStartDate() {
		return allowToPickStartDate;
	}

	public void setAllowToPickStartDate(String allowToPickStartDate) {
		this.allowToPickStartDate = allowToPickStartDate;
	}

	public String getAllowToPickGrade() {
		return allowToPickGrade;
	}

	public void setAllowToPickGrade(String allowToPickGrade) {
		this.allowToPickGrade = allowToPickGrade;
	}

	public String getNeedTC_c() {
		return needTC_c;
	}

	public void setNeedTC_c(String needTC_c) {
		this.needTC_c = needTC_c;
	}

	public String getPreventDuplicateSignup() {
		return preventDuplicateSignup;
	}

	public void setPreventDuplicateSignup(String preventDuplicateSignup) {
		this.preventDuplicateSignup = preventDuplicateSignup;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public Integer getBillDateOffset() {
		return billDateOffset;
	}

	public void setBillDateOffset(Integer billDateOffset) {
		this.billDateOffset = billDateOffset;
	}

	public String getAllowMinimumPaymentBasedOn() {
		return allowMinimumPaymentBasedOn;
	}

	public void setAllowMinimumPaymentBasedOn(String allowMinimumPaymentBasedOn) {
		this.allowMinimumPaymentBasedOn = allowMinimumPaymentBasedOn;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	/*public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}*/

	public List<ItemDetailPromotion> getItemDetailPromotion() {
		return itemDetailPromotion;
	}

	public void setItemDetailPromotion(List<ItemDetailPromotion> itemDetailPromotion) {
		this.itemDetailPromotion = itemDetailPromotion;
	}

	public String getPromotionRuleType() {
		return promotionRuleType;
	}

	public void setPromotionRuleType(String promotionRuleType) {
		this.promotionRuleType = promotionRuleType;
	}

	public String getAutoPromotion() {
		return autoPromotion;
	}

	public void setAutoPromotion(String autoPromotion) {
		this.autoPromotion = autoPromotion;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public String getPromotionType() {
		return promotionType;
	}

	public void setPromotionType(String promotionType) {
		this.promotionType = promotionType;
	}

	public String getPromotionDiscountValue() {
		return promotionDiscountValue;
	}

	public void setPromotionDiscountValue(String promotionDiscountValue) {
		this.promotionDiscountValue = promotionDiscountValue;
	}

	public List<ContactPromotion> getContactPromotion() {
		return contactPromotion;
	}

	public void setContactPromotion(List<ContactPromotion> contactPromotion) {
		this.contactPromotion = contactPromotion;
	}

	public String getPromoApplicableToAllContacts() {
		return promoApplicableToAllContacts;
	}

	public void setPromoApplicableToAllContacts(String promoApplicableToAllContacts) {
		this.promoApplicableToAllContacts = promoApplicableToAllContacts;
	}

	public String getPromotionExpression() {
		return promotionExpression;
	}

	public void setPromotionExpression(String promotionExpression) {
		this.promotionExpression = promotionExpression;
	}

	public String getPromotionExpressionOperator() {
		return promotionExpressionOperator;
	}

	public void setPromotionExpressionOperator(String promotionExpressionOperator) {
		this.promotionExpressionOperator = promotionExpressionOperator;
	}

	public Date getPlanEndDate() {
		return planEndDate;
	}

	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}

	public String getGenerateRecurringInvoicesAtTimeOfSignUp() {
		return generateRecurringInvoicesAtTimeOfSignUp;
	}

	public void setGenerateRecurringInvoicesAtTimeOfSignUp(
			String generateRecurringInvoicesAtTimeOfSignUp) {
		this.generateRecurringInvoicesAtTimeOfSignUp = generateRecurringInvoicesAtTimeOfSignUp;
	}

	public String getAutomatedWaitlist() {
		return automatedWaitlist;
	}

	public void setAutomatedWaitlist(String automatedWaitlist) {
		this.automatedWaitlist = automatedWaitlist;
	}

	public String getPreRequisities() {
		return preRequisities;
	}

	public void setPreRequisities(String preRequisities) {
		this.preRequisities = preRequisities;
	}

	public String getRequiresSetUpFee() {
		return requiresSetUpFee;
	}

	public void setRequiresSetUpFee(String requiresSetUpFee) {
		this.requiresSetUpFee = requiresSetUpFee;
	}

	public String getNeedsHealthHistory() {
		return needsHealthHistory;
	}

	public void setNeedsHealthHistory(String needsHealthHistory) {
		this.needsHealthHistory = needsHealthHistory;
	}

	public String getRequiresEmergencyContact() {
		return requiresEmergencyContact;
	}

	public void setRequiresEmergencyContact(String requiresEmergencyContact) {
		this.requiresEmergencyContact = requiresEmergencyContact;
	}

	public String getRequiresAuthorisedContact() {
		return requiresAuthorisedContact;
	}

	public void setRequiresAuthorisedContact(String requiresAuthorisedContact) {
		this.requiresAuthorisedContact = requiresAuthorisedContact;
	}



	public String getStopConfirmedSignUps() {
		return stopConfirmedSignUps;
	}



	public void setStopConfirmedSignUps(String stopConfirmedSignUps) {
		this.stopConfirmedSignUps = stopConfirmedSignUps;
	}



	public Long getSpecialAgentCapacity() {
		return specialAgentCapacity;
	}



	public void setSpecialAgentCapacity(Long specialAgentCapacity) {
		this.specialAgentCapacity = specialAgentCapacity;
	}



	public Long getSpecialAgentRemainingCapacity() {
		return specialAgentRemainingCapacity;
	}



	public void setSpecialAgentRemainingCapacity(Long specialAgentRemainingCapacity) {
		this.specialAgentRemainingCapacity = specialAgentRemainingCapacity;
	}
	
}
