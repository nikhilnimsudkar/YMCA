package com.ymca.model;


import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;



@Entity
@Table(name = "activity")
public class Activity extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = 3832626162173359411L;
    
	public Activity(){

    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)    
    private Long integrationId;	
	
	private String type;
	private String description;
	@Column(name = "createddate", columnDefinition="DATETIME")
	private Date createdDate;
	@Column(name = "checkindatetime", columnDefinition="DATETIME")
	private Date checkinDatetime;
	@Column(name = "checkoutdatetime", columnDefinition="DATETIME")
	private Date checkoutDatetime;
	
	@Column(name = "show_on_portal", nullable = false, columnDefinition="boolean default false")
	private boolean showOnPortal;
	
	private String reason1;
	private String reason2;
	private String reason3;
	private String reason4;
	private String reason5;
	private String reason6;
	private String whydissatisfied;
	private String howtoimprove;
	private String otherjoined;
	private String otherreason;
	private String otherreason1;
	
	private Calendar lastUpdated;
	
	@Column(name = "PortalModifiedBy_c")
	private String portalModifiedBy;
	
	@Column(name="AssignTo_c")
	private String assignTo;
	
	@ManyToOne
	@JoinColumn(name="accountId")
	private Account customer;
	
	@ManyToOne
	@JoinColumn(name="contact_id")
	private User contact;
	
	@ManyToOne
	@JoinColumn(name = "signupId")
    private Signup signup;
	
	private long facilityLoationCheckedInTo;
	private String unauthorizedAccessReason;
	
	@ManyToOne
	@JoinColumn(name = "checkInPersonContactId")
	private User checkInPerson;
	
	@ManyToOne
	@JoinColumn(name = "checkOutPersonContactId")
	private User checkOutPerson;
	
	
	@Transient
    private Location location;
	
	@Transient
    private int checkedInhrs;
	
	public User getCheckInPerson() {
		return checkInPerson;
	}

	public void setCheckInPerson(User checkInPerson) {
		this.checkInPerson = checkInPerson;
	}

	public User getCheckOutPerson() {
		return checkOutPerson;
	}

	public void setCheckOutPerson(User checkOutPerson) {
		this.checkOutPerson = checkOutPerson;
	}

	public String getUnauthorizedAccessReason() {
		return unauthorizedAccessReason;
	}

	public void setUnauthorizedAccessReason(String unauthorizedAccessReason) {
		this.unauthorizedAccessReason = unauthorizedAccessReason;
	}

	public long getFacilityLoationCheckedInTo() {
		return facilityLoationCheckedInTo;
	}

	public void setFacilityLoationCheckedInTo(long facilityLoationCheckedInTo) {
		this.facilityLoationCheckedInTo = facilityLoationCheckedInTo;
	}

	public Long getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(Long integrationId) {
		this.integrationId = integrationId;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public boolean isShowOnPortal() {
		return showOnPortal;
	}

	public void setShowOnPortal(boolean showOnPortal) {
		this.showOnPortal = showOnPortal;
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

	public String getReason1() {
		return reason1;
	}

	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}

	public String getReason2() {
		return reason2;
	}

	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}

	public String getReason3() {
		return reason3;
	}

	public void setReason3(String reason3) {
		this.reason3 = reason3;
	}

	public String getReason4() {
		return reason4;
	}

	public void setReason4(String reason4) {
		this.reason4 = reason4;
	}

	public String getReason5() {
		return reason5;
	}

	public void setReason5(String reason5) {
		this.reason5 = reason5;
	}

	public String getReason6() {
		return reason6;
	}

	public void setReason6(String reason6) {
		this.reason6 = reason6;
	}

	public String getWhydissatisfied() {
		return whydissatisfied;
	}

	public void setWhydissatisfied(String whydissatisfied) {
		this.whydissatisfied = whydissatisfied;
	}

	public String getHowtoimprove() {
		return howtoimprove;
	}

	public void setHowtoimprove(String howtoimprove) {
		this.howtoimprove = howtoimprove;
	}

	public String getOtherjoined() {
		return otherjoined;
	}

	public void setOtherjoined(String otherjoined) {
		this.otherjoined = otherjoined;
	}

	public String getOtherreason() {
		return otherreason;
	}

	public void setOtherreason(String otherreason) {
		this.otherreason = otherreason;
	}

	public String getOtherreason1() {
		return otherreason1;
	}

	public void setOtherreason1(String otherreason1) {
		this.otherreason1 = otherreason1;
	}

	public Date getCheckinDatetime() {
		return checkinDatetime;
	}

	public void setCheckinDatetime(Date checkinDatetime) {
		this.checkinDatetime = checkinDatetime;
	}

	public Date getCheckoutDatetime() {
		return checkoutDatetime;
	}

	public void setCheckoutDatetime(Date checkoutDatetime) {
		this.checkoutDatetime = checkoutDatetime;
	}

	public Signup getSignup() {
		return signup;
	}

	public void setSignup(Signup signup) {
		this.signup = signup;
	}

	public Calendar getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(Calendar lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public String getPortalModifiedBy() {
		return portalModifiedBy;
	}

	public void setPortalModifiedBy(String portalModifiedBy) {
		this.portalModifiedBy = portalModifiedBy;
	}

	
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public int getCheckedInhrs() {
		return checkedInhrs;
	}

	public void setCheckedInhrs(int checkedInhrs) {
		this.checkedInhrs = checkedInhrs;
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
				+ ((integrationId == null) ? 0 : integrationId.hashCode());
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
		Activity other = (Activity) obj;
		if (integrationId == null) {
			if (other.integrationId != null)
				return false;
		} else if (!integrationId.equals(other.integrationId))
			return false;
		return true;
	}
	
}
