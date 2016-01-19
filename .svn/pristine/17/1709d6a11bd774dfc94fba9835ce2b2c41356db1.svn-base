package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "signup_associated_item_detail")
public class SignupAssociatedItemDetail extends BaseObject implements Serializable {
	
	private static final long serialVersionUID = -3202944701848397747L;

	public SignupAssociatedItemDetail(){
    }

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	
	@Column(name="sc_id")
    private Long scId;

	@Column(name="RecordName")
    private String recordName;

	@Column(name="Type_c")
    private String type;

	@Column(name="Description_c")
    private String description;
	
	@Column(name="Status_c")
    private String status;

	@Column(name="ActivityPriority_c")
    private String activityPriority;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="item_detail_id")
	private ItemDetail itemDetail;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="signup_Id")
	private Signup signup;
	
	@Column(name="signup_Id",insertable=false,updatable=false)
	private Long signupId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getScId() {
		return scId;
	}

	public void setScId(Long scId) {
		this.scId = scId;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActivityPriority() {
		return activityPriority;
	}

	public void setActivityPriority(String activityPriority) {
		this.activityPriority = activityPriority;
	}

	public ItemDetail getItemDetail() {
		return itemDetail;
	}

	public void setItemDetail(ItemDetail itemDetail) {
		this.itemDetail = itemDetail;
	}

	public Signup getSignup() {
		return signup;
	}

	public void setSignup(Signup signup) {
		this.signup = signup;
	}

	public Long getSignupId() {
		return signupId;
	}

	public void setSignupId(Long signupId) {
		this.signupId = signupId;
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
		SignupAssociatedItemDetail other = (SignupAssociatedItemDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SignUpAssociatedItemDetail [Id=" + id + ", RecordName="
				+ recordName + "]";
	}
	
}
