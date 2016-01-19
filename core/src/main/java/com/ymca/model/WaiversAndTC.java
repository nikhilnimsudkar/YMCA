package com.ymca.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "waivers_and_tc")
public class WaiversAndTC extends BaseObject implements Serializable {

	private static final long serialVersionUID = -7439568389541257102L;

	@Id
    private Long id;					// required  PK
    
    @Column(name = "RecordName")
    private String recordName;
    
    @Column(name = "tc_description", nullable = false, columnDefinition="LONGTEXT")
    private String tcDescription;
    
    @Column
    private String type;
    
    @Column(name="StartDate_c")
	private Date startDate;
	 
	@Column(name="EndDate_c")
	private Date endDate;

    public Long getId() {
		return id;
	}

	public void setId(Long Id) {
		this.id = Id;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String RecordName) {
		this.recordName = RecordName;
	}

	public String getTcDescription() {
		return tcDescription;
	}

	public void setTcDescription(String tcDescription) {
		this.tcDescription = tcDescription;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		WaiversAndTC other = (WaiversAndTC) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WaiversAndTC [id=" + id + ", recordName=" + recordName
				+ ", tcDescription=" + tcDescription + ", type=" + type
				+ ", startDate=" + startDate + ", endDate=" + endDate + "]";
	}
	
}
