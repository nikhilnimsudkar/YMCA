package com.ymca.web.model;

import java.util.Date;


public class SearchGrid    {

	private Long id ;
	private String checkBoxState;
	private Long itemDetailsId ;
	private String productId ;
	private String productName ;
	private String productDesc ;
	private String productCategory ;
	private String productType ;
	private String branchName ;
	private Date startDate ;
	private Date startTime ;
	private Date endDate ;
	private Date endTime ;
	private Long capacity ;
	private String gender ;
	private Double memberprice ;
	private Double nonmembertierPrice ;
	private String tier ;
	private String lodgingType ;
	private String sessionName ;
	private String instructorName ;
	private String dayId ;
	private Integer minAge ;
	private Integer maxAge;
	private Long actualRemainingCapacity ;
	private Long webCapacity;
	private String automatedWaitlist;
	
	public SearchGrid(){
	}
	
	public SearchGrid(Long id,String productName, String productDesc,
			String productCategory, String branchName, Date startDate,
			Date startTime, Date endDate, Date endTime, Long capacity,
			String gender, Double memberprice, Double nonmembertierPrice,
			String lodgingType, String sessionName, String instructorName,
			String dayId, Integer minAge, Integer maxAge, Long actualRemainingCapacity, 
			Long webCapacity, String automatedWaitlist, String productType) {
		this.id = id;
		this.itemDetailsId = id;
		this.productName = productName;
		this.productDesc = productDesc;
		this.productCategory = productCategory;
		this.branchName = branchName;
		this.startDate = startDate;
		this.startTime = startTime;
		this.endDate = endDate;
		this.endTime = endTime;
		this.capacity = capacity;
		this.gender = gender;
		this.memberprice = memberprice;
		this.nonmembertierPrice = nonmembertierPrice;
		this.lodgingType = lodgingType;
		this.sessionName = sessionName;
		this.instructorName = instructorName;
		this.dayId = dayId;
		this.minAge = minAge;
		this.maxAge = maxAge;
		this.actualRemainingCapacity = actualRemainingCapacity;
		this.productType = productType;
		this.webCapacity = webCapacity;
		this.automatedWaitlist = automatedWaitlist;
	}



	public SearchGrid(Long id,String productName,String branchName,String dayId){
		this.id = id;
		this.productName = productName ;
		this.branchName = branchName;
		this.dayId = dayId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Long getCapacity() {
		return capacity;
	}
	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Double getMemberprice() {
		return memberprice;
	}
	public void setMemberprice(Double memberprice) {
		this.memberprice = memberprice;
	}
	public Double getNonmembertierPrice() {
		return nonmembertierPrice;
	}
	public void setNonmembertierPrice(Double nonmembertierPrice) {
		this.nonmembertierPrice = nonmembertierPrice;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getTier() {
		return tier;
	}
	public void setTier(String tier) {
		this.tier = tier;
	}
	public String getLodgingType() {
		return lodgingType;
	}
	public void setLodgingType(String lodgingType) {
		this.lodgingType = lodgingType;
	}
	public String getSessionName() {
		return sessionName;
	}
	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}
	public String getInstructorName() {
		return instructorName;
	}
	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}
	public String getDayId() {
		return dayId;
	}
	public void setDayId(String dayId) {
		this.dayId = dayId;
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
	public Long getItemDetailsId() {
		return itemDetailsId;
	}

	public void setItemDetailsId(Long itemDetailsId) {
		this.itemDetailsId = itemDetailsId;
	}

	public Long getActualRemainingCapacity() {
		return actualRemainingCapacity;
	}

	public void setActualRemainingCapacity(Long actualRemainingCapacity) {
		this.actualRemainingCapacity = actualRemainingCapacity;
	}

	@Override
	public String toString() {
		return "SearchGrid [id=" + id + ", productId=" + productId
				+ ", startDate=" + startDate
				+ ", startTime=" + startTime + ", endDate=" + endDate
				+ ", endTime=" + endTime + ", capacity=" + capacity
				+ ", gender=" + gender + ", memberprice=" + memberprice
				+ ", nonmembertierPrice=" + nonmembertierPrice
				+ ", productName=" + productName + ", productDesc="
				+ productDesc + ", productCategory=" + productCategory
				+ ", productType=" + productType + ", branchName=" + branchName
				+ ", tier=" + tier + ", lodgingType=" + lodgingType
				+ ", sessionName=" + sessionName + ", instructorName="
				+ instructorName + ", dayId=" + dayId + "]";
	}

	public String getCheckBoxState() {
		return checkBoxState;
	}

	public void setCheckBoxState(String checkBoxState) {
		this.checkBoxState = checkBoxState;
	}

	public Long getWebCapacity() {
		return webCapacity;
	}

	public void setWebCapacity(Long webCapacity) {
		this.webCapacity = webCapacity;
	}

	public String getAutomatedWaitlist() {
		return automatedWaitlist;
	}

	public void setAutomatedWaitlist(String automatedWaitlist) {
		this.automatedWaitlist = automatedWaitlist;
	}

}
