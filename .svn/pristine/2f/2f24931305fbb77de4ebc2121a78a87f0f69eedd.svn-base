package com.ymca.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Notes  extends BaseObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "OffenderId_c")
	private String offenderId;
	
	private String noteType;
	@Column(name = "OffenderMatch_c", columnDefinition="boolean default true")
	private Boolean offenderMatch;
	
	@Column(name="noteDescription", columnDefinition="TEXT")
	private String noteDescription;
	
	@Column(name = "PortalModifiedBy_c")
	private String portalModifiedBy;
	@Column(name = "PortalId_c")
	private String portalId;
	
	@ManyToOne
	@JoinColumn(name="contactId")
	private User contact;

	public String getOffenderId() {
		return offenderId;
	}

	public void setOffenderId(String offenderId) {
		this.offenderId = offenderId;
	}

	public String getNoteType() {
		return noteType;
	}

	public void setNoteType(String noteType) {
		this.noteType = noteType;
	}

	public Boolean getOffenderMatch() {
		return offenderMatch;
	}

	public void setOffenderMatch(Boolean offenderMatch) {
		this.offenderMatch = offenderMatch;
	}

	public String getNoteDescription() {
		return noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public String getPortalModifiedBy() {
		return portalModifiedBy;
	}

	public void setPortalModifiedBy(String portalModifiedBy) {
		this.portalModifiedBy = portalModifiedBy;
	}

	public String getPortalId() {
		return portalId;
	}

	public void setPortalId(String portalId) {
		this.portalId = portalId;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
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

}
