package com.ymca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "item_detail_days") 
public class ItemDetailDays extends BaseObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1815094381086631561L;
	
	@Id
	@Column(name="Id")
	private Long id;
	
	@Column(name="RecordName")
	private String recordName;
	
	@Column(name="Day")
	private String day;

	@Column(name="Status_c")
	private String status_c;
	
	@Column(name="StartTime_c")
	private String startTime_c;
	
	@Column(name="EndTime_c")
	private String endTime_c;
	
	@ManyToOne
	@JoinColumn(name="item_detail_id")
	private ItemDetail itemDetail;
	
	//private Long capacity;
	private String instructorName;
	private String sessionName;
	
	@Column(name = "ActualCapacity_c")
	private Long actualCapacity;

	@Column(name = "WebCapacity_c")
	private Long webCapacity;
	
	@Column(name = "webRemainingCapacity_c")
	private Long remainingCapacity;
	
	@Column(name = "actualRemainingCapacity_c")
	private Long actualRemainingCapacity;
	
	@Column(name = "WaitlistCounter_c")
	private Long waitlistCounter;
	
	@Column(name = "Date_c", columnDefinition="DATE")
	private Date date_c;
	
	@Column(name = "CancellationCutOffPeriod_c")
	private Integer cancellationCutOffPeriod;
	
	@Column(name="PreventDuplicateSignup_c")
	private String preventDuplicateSignup;
	
	@Column(name="StopConfirmedSignUps_c")
	private String stopConfirmedSignUps;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		recordName = recordName;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}
/*
	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}*/

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public String getSessionName() {
		return sessionName;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
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

	public Long getActualRemainingCapacity() {
		return actualRemainingCapacity;
	}

	public void setActualRemainingCapacity(Long actualRemainingCapacity) {
		this.actualRemainingCapacity = actualRemainingCapacity;
	}

	public Long getWaitlistCounter() {
		return waitlistCounter;
	}

	public void setWaitlistCounter(Long waitlistCounter) {
		this.waitlistCounter = waitlistCounter;
	}

	public Date getDate_c() {
		return date_c;
	}

	public void setDate_c(Date date_c) {
		this.date_c = date_c;
	}

	public Integer getCancellationCutOffPeriod() {
		return cancellationCutOffPeriod;
	}

	public void setCancellationCutOffPeriod(Integer cancellationCutOffPeriod) {
		this.cancellationCutOffPeriod = cancellationCutOffPeriod;
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

	public String getPreventDuplicateSignup() {
		return preventDuplicateSignup;
	}

	public void setPreventDuplicateSignup(String preventDuplicateSignup) {
		this.preventDuplicateSignup = preventDuplicateSignup;
	}

	public String getStopConfirmedSignUps() {
		return stopConfirmedSignUps;
	}

	public void setStopConfirmedSignUps(String stopConfirmedSignUps) {
		this.stopConfirmedSignUps = stopConfirmedSignUps;
	}
}
